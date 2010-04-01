package cz.muni.stanse.pointeranalyzer.steensgaard;

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
public final class TypeTable implements EquivalenceClassJoinListener {
    
    private HashMap<CFGHandle, HashMap<String, EquivalenceClass<LocationPointerType>>>
            typeTable = new HashMap<CFGHandle, HashMap<String, EquivalenceClass<LocationPointerType>>>();
    
    private HashMap<EquivalenceClass<LocationPointerType>, List<Pair<CFGHandle, String>>>
            inverseTypeTable = new HashMap<EquivalenceClass<LocationPointerType>, List<Pair<CFGHandle, String>>>();

    private HashMap<String, EquivalenceClass<LocationPointerType>> getContextOf(CFGHandle cfg)
    {
        // is the function context already present in the type table?
        HashMap<String, EquivalenceClass<LocationPointerType>> context = typeTable.get(cfg);
        if (context == null)
        {
            // first request for this function context, so create it
            context = new HashMap<String, EquivalenceClass<LocationPointerType>>();
            typeTable.put(cfg, context);
        }

        return context;
    }

    public EquivalenceClass<LocationPointerType> getTypeOf(CFGHandle cfg, String name)
    {
        HashMap<String, EquivalenceClass<LocationPointerType>> context = getContextOf(cfg);

        // find the equivalence class for the symbol in the type table
        EquivalenceClass<LocationPointerType> result = context.get(name);
        if (result == null)
        {
            // symbol not yet in the table, so create an equivalence class for it
            result = EquivalenceClass.createRefBottomBottom(name);
            context.put(name, result);

            // add an entry to the inverse table
            List<Pair<CFGHandle, String>> backList = new ArrayList<Pair<CFGHandle, String>>();
            backList.add(new Pair<CFGHandle, String>(cfg, name));
            inverseTypeTable.put(result, backList);

            result.notifyPointedFrom(this);
        }
        
        return result;
    }

    public EquivalenceClass<LocationPointerType> getTypeOf(String name)
    {
        return getTypeOf(null, name);
    }

    public EquivalenceClass<LocationPointerType> getTypeOf(CFGHandle cfg)
    {
        return getTypeOf(cfg, null);
    }


    public void addFunction(CFGHandle handle, EquivalenceClass<LocationPointerType> functionClass)
    {
        // functions are stored as global variables in the type table:
        // first, add an entry to the type table
        HashMap<String, EquivalenceClass<LocationPointerType>> context = getContextOf(null);
        context.put(handle.getFunctionName(), functionClass);

        // next, add an entry to the inverse type table
        List<Pair<CFGHandle, String>> backList
                = new ArrayList<Pair<CFGHandle, String>>();
        backList.add(new Pair<CFGHandle, String>(null, handle.getFunctionName()));
        inverseTypeTable.put(functionClass, backList);

        functionClass.notifyPointedFrom(this);
    }

    public void notifyEquivalenceClassJoined(EquivalenceClass oldClass, EquivalenceClass newClass)
    {
        // get a list of variables associated with joined class
        List<Pair<CFGHandle, String>> oldList = inverseTypeTable.get(oldClass);
        if (oldList != null)
        {
            // update type table
            for (Pair<CFGHandle, String> le: oldList)
            {
                HashMap<String, EquivalenceClass<LocationPointerType>> context = getContextOf(le.getFirst());
                context.put(le.getSecond(), newClass);
            }

            // update inverse type table
            inverseTypeTable.remove(oldClass);
            List<Pair<CFGHandle, String>> newList = inverseTypeTable.get(newClass);
            if (newList != null)
            {
                oldList.addAll(newList);
            }
            else
            {
                newClass.notifyPointedFrom(this);
            }
            inverseTypeTable.put(newClass, oldList);
        }
    }
    
    private Set<Pair<CFGHandle, String>> getPointsToSetOf(EquivalenceClass<LocationPointerType> sourceClass)
    {
        Set<Pair<CFGHandle, String>> result = new HashSet<Pair<CFGHandle, String>>();

        // get the equivalence class to which this class points to
        EquivalenceClass<LocationPointerType> destinationClass = sourceClass.getType().getTau();

        List<Pair<CFGHandle, String>> equivList = inverseTypeTable.get(destinationClass);

        // any variables pointed to?
        if (equivList != null)
        {
            result.addAll(equivList);
        }
        
        return result;
    }

    public Set<Pair<CFGHandle, String>> getPointsToSetOf(CFGHandle cfg, String id)
    {
        EquivalenceClass<LocationPointerType> sourceClass = getTypeOf(cfg, id);

        return getPointsToSetOf(sourceClass);
    }

    public void toDotFile()
    {
        System.out.println("digraph {");

        for (Entry<CFGHandle, HashMap<String, EquivalenceClass<LocationPointerType>>> entry: typeTable.entrySet())
        {
            for (String name: entry.getValue().keySet())
            {
                System.out.printf("%s__%s [label=\"%s\"];\n", entry.getKey() != null ? entry.getKey().getFunctionName() : "", name, name);
            }
        }

        for (Entry<CFGHandle, HashMap<String, EquivalenceClass<LocationPointerType>>> functionEntry: typeTable.entrySet())
        {
            CFGHandle cfg = functionEntry.getKey();

            for (Entry<String, EquivalenceClass<LocationPointerType>> symbolEntry: functionEntry.getValue().entrySet())
            {
                String name = symbolEntry.getKey();
                EquivalenceClass type = symbolEntry.getValue();

                Set<Pair<CFGHandle, String>> pointsToSet = getPointsToSetOf(type);

                for (Pair<CFGHandle, String> pointedTo: pointsToSet)
                {
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
