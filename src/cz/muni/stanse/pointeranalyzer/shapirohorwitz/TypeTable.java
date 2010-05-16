package cz.muni.stanse.pointeranalyzer.shapirohorwitz;

import java.util.HashMap;
import java.util.List;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.utils.Pair;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Maintains relationships between symbols and equivalence classes for the
 * pointer representation.
 *
 * @author Michal Strehovsky
 */
public final class TypeTable implements AbstractLocationJoinListener {

    private HashMap<CFGHandle, HashMap<String, AbstractLocation>>
            typeTable = new HashMap<CFGHandle, HashMap<String, AbstractLocation>>();

    private HashMap<AbstractLocation, List<Pair<CFGHandle, String>>>
            inverseTypeTable = new HashMap<AbstractLocation, List<Pair<CFGHandle, String>>>();

    private CategorizationProvider categorizationProvider;

    public TypeTable(CategorizationProvider categorizationProvider) {
        this.categorizationProvider = categorizationProvider;
    }

    /**
     * Gets the symbol context for the specified function. If context for the
     * specified function doesn't exist, it will be created.
     *
     * @param cfg Function to get the context for. If null, global context is returned.
     * @return
     */
    private HashMap<String, AbstractLocation> getContextOf(CFGHandle cfg) {

        // is the function context already present in the type table?
        HashMap<String, AbstractLocation> context = typeTable.get(cfg);
        if (context == null) {

            // first request for this function context, so create it
            context = new HashMap<String, AbstractLocation>();
            typeTable.put(cfg, context);
        }

        return context;
    }

    /**
     * Gets the type of specified symbol. If the type doesn't exist, it will be created.
     *
     * @param cfg
     * @param name
     * @return
     */
    public AbstractLocation getTypeOf(CFGHandle cfg, String name) {

        HashMap<String, AbstractLocation> context = getContextOf(cfg);

        // find the abstract location for the symbol in the type table
        AbstractLocation result = context.get(name);
        if (result == null) {
            // symbol not yet in the table, so create an equivalence class for it
            result = AbstractLocation.createVariable(categorizationProvider, name);
            context.put(name, result);

            // add an entry to the inverse table
            List<Pair<CFGHandle, String>> backList = new ArrayList<Pair<CFGHandle, String>>();
            backList.add(new Pair<CFGHandle, String>(cfg, name));
            inverseTypeTable.put(result, backList);

            result.notifyPointedFrom(this);
        }

        return result;
    }

    /**
     * Gets the type associated with specified global symbol.
     *
     * @param name
     * @return
     */
    public AbstractLocation getTypeOf(String name) {
        return getTypeOf(null, name);
    }

    /**
     * Gets the type associated with specified function.
     *
     * @param cfg
     * @return
     */
    public AbstractLocation getTypeOf(CFGHandle cfg) {
        return getTypeOf(cfg, null);
    }

    /**
     * Adds a new function to the type table.
     *
     * @param handle
     * @param paramNames
     */
    public void addFunction(CFGHandle handle, List<String> paramNames) {

        List<AbstractLocation> parameters = new ArrayList<AbstractLocation>(paramNames.size());

        // create abstract locations for parameters
        for (int i = 0; i < paramNames.size(); i++) {
            parameters.add(getTypeOf(handle, paramNames.get(i)));
        }

        // create abstract location for return value
        AbstractLocation returnValue = getTypeOf(handle, "__stanse_retVal");

        // create abstract location for the function itself
        int category = categorizationProvider.assignNextCategory();

        AbstractLocation functionClass = new AbstractLocation(
                category,
                "",
                new LocationPointerType(
                    categorizationProvider,
                    new AbstractLocation(category, "functiondummytau"),
                    new AbstractLocation(
                        category,
                        handle.getFunctionName(),
                        new FunctionPointerType(parameters, returnValue))));

        // functions are stored as global variables in the type table:
        // first, add an entry to the type table
        HashMap<String, AbstractLocation> context = getContextOf(null);
        context.put(handle.getFunctionName(), functionClass);

        // next, add an entry to the inverse type table
        List<Pair<CFGHandle, String>> backList
                = new ArrayList<Pair<CFGHandle, String>>();
        backList.add(new Pair<CFGHandle, String>(null, handle.getFunctionName()));
        inverseTypeTable.put(functionClass, backList);

        functionClass.notifyPointedFrom(this);
    }

    /**
     * Gets a list of symbols backed by specified abstract location.
     *
     * @param aloc
     * @return
     */
    private List<Pair<CFGHandle, String>> getInverseMapping(AbstractLocation aloc) {

        List<Pair<CFGHandle, String>> result = new ArrayList<Pair<CFGHandle, String>>();

        AbstractLocation find = aloc;

        for (Entry<AbstractLocation,List<Pair<CFGHandle, String>>> e: inverseTypeTable.entrySet()) {
            AbstractLocation t = e.getKey();
            if (find == t) {
                result.addAll(e.getValue());
            }
        }

        return result;
    }

    /**
     * Gets a set of symbols to which specified abstract location points to.
     *
     * @param sourceClass
     * @return
     */
    private Set<Pair<CFGHandle, String>> getPointsToSetOf(AbstractLocation sourceClass) {

        Set<Pair<CFGHandle, String>> result = new HashSet<Pair<CFGHandle, String>>();

        // get the equivalence classes to which this class points to
        AbstractLocationSet destinationClass =
                ((LocationPointerType)sourceClass.getType()).getTau();

        for (AbstractLocation aloc: destinationClass) {
            List<Pair<CFGHandle, String>> equivList = getInverseMapping(aloc);

            // any variables pointed to?
            if (equivList != null) {
                result.addAll(equivList);
            }
        }

        return result;
    }

    /**
     * Gets a set of symbols to which specified symbol points to.
     *
     * @param cfg
     * @param id
     * @return
     */
    public Set<Pair<CFGHandle, String>> getPointsToSetOf(CFGHandle cfg, String id)
    {
        AbstractLocation sourceClass = getTypeOf(cfg, id);

        return getPointsToSetOf(sourceClass);
    }

    public void notifyAbstractLocationsJoined(AbstractLocation oldClass, AbstractLocation newClass) {

        // get a list of variables associated with joined class
        List<Pair<CFGHandle, String>> oldList = inverseTypeTable.get(oldClass);
        if (oldList != null) {
            // update type table
            for (Pair<CFGHandle, String> le: oldList) {
                HashMap<String, AbstractLocation> context = getContextOf(le.getFirst());

                assert context.get(le.getSecond()) == oldClass;

                context.put(le.getSecond(), newClass);
            }

            // update inverse type table
            inverseTypeTable.remove(oldClass);
            List<Pair<CFGHandle, String>> newList = inverseTypeTable.get(newClass);
            if (newList != null) {
                oldList.addAll(newList);
            }
            inverseTypeTable.put(newClass, oldList);
        }
    }

    /**
     * Converts the type table and points-to relationships in it to a DOT file viewable with GraphViz.
     */
    public void toDotFile()
    {
        System.out.println("digraph {");

        for (Entry<CFGHandle, HashMap<String, AbstractLocation>> entry: typeTable.entrySet()) {
            for (String name: entry.getValue().keySet()) {
                System.out.printf("%s__%s [label=\"%s\"];\n", entry.getKey() != null ? entry.getKey().getFunctionName() : "", name, name);
            }
        }

        for (Entry<CFGHandle, HashMap<String, AbstractLocation>> functionEntry: typeTable.entrySet()) {
            CFGHandle cfg = functionEntry.getKey();

            for (Entry<String, AbstractLocation> symbolEntry: functionEntry.getValue().entrySet()) {
                String name = symbolEntry.getKey();
                AbstractLocation type = symbolEntry.getValue();

                Set<Pair<CFGHandle, String>> pointsToSet = getPointsToSetOf(type);

                for (Pair<CFGHandle, String> pointedTo: pointsToSet) {
                    System.out.printf("%s__%s -> %s__%s;\n",
                            cfg != null ? cfg.getFunctionName() : "",
                            name,
                            pointedTo.getFirst() != null ? pointedTo.getFirst().getFunctionName() : "",
                            pointedTo.getSecond());
                }

            }
        }

        System.out.println("}");
    }
}
