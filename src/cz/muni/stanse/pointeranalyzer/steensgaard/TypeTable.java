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
    
    private HashMap<CFGHandle, HashMap<String, EquivalenceClass>>
            typeTable = new HashMap<CFGHandle, HashMap<String, EquivalenceClass>>();
    
    private HashMap<EquivalenceClass, List<Pair<CFGHandle, String>>>
            inverseTypeTable = new HashMap<EquivalenceClass, List<Pair<CFGHandle, String>>>();

    private HashMap<String, EquivalenceClass> getContextOf(CFGHandle cfg)
    {
        HashMap<String, EquivalenceClass> context = typeTable.get(cfg);
        if (context == null)
        {
            context = new HashMap<String, EquivalenceClass>();
            typeTable.put(cfg, context);
        }

        return context;
    }

    public TypeTable()
    {
        EquivalenceClass.addJoinListener(this);
    }

    public EquivalenceClass getTypeOf(CFGHandle cfg, String name)
    {
        HashMap<String, EquivalenceClass> context = getContextOf(cfg);
        
        EquivalenceClass result = context.get(name);
        if (result == null)
        {
            // symbol not yet in the table, so create an equivalence class for it
            result = EquivalenceClass.createRefBottomBottom(name);
            context.put(name, result);

            // add entry to the inverse table
            List<Pair<CFGHandle, String>> backList = new ArrayList<Pair<CFGHandle, String>>();
            backList.add(new Pair<CFGHandle, String>(cfg, name));
            inverseTypeTable.put(result, backList);
        }
        
        return result;
    }

    public EquivalenceClass getTypeOf(String name)
    {
        return getTypeOf(null, name);
    }

    public EquivalenceClass getTypeOf(CFGHandle cfg)
    {
        return getTypeOf(cfg, null);
    }


    public void addFunction(CFGHandle handle, EquivalenceClass functionClass)
    {
        HashMap<String, EquivalenceClass> context = getContextOf(null);
        context.put(handle.getFunctionName(), functionClass);

        List<Pair<CFGHandle, String>> backList
                = new ArrayList<Pair<CFGHandle, String>>();
        backList.add(new Pair<CFGHandle, String>(null, handle.getFunctionName()));
        List<Pair<CFGHandle, String>> res = inverseTypeTable.put(functionClass, backList);
        assert res == null;
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
                HashMap<String, EquivalenceClass> context = getContextOf(le.getFirst());
                context.put(le.getSecond(), newClass);
            }

            // update inverse type table
            inverseTypeTable.remove(oldClass);
            List<Pair<CFGHandle, String>> newList = inverseTypeTable.get(newClass);
            if (newList != null)
            {
                oldList.addAll(newList);
            }
            inverseTypeTable.put(newClass, oldList);
        }
    }
    
    private Set<Pair<CFGHandle, String>> getPointsToSetOf(CFGHandle cfg, String id, EquivalenceClass sourceClass)
    {
        Set<Pair<CFGHandle, String>> result = new HashSet<Pair<CFGHandle, String>>();

        EquivalenceClass destinationClass = ((LocationPointerType)sourceClass.getType()).getTau();

        List<Pair<CFGHandle, String>> equivList = inverseTypeTable.get(destinationClass);
        if (equivList != null)
        {
            result.addAll(equivList);
            /*for (Pair<CFGHandle, String> listEnt: equivList)
            {
                if (listEnt.getFirst() != cfg || !listEnt.getSecond().equals(id))
                {
                    result.add(listEnt);
                }
            }*/
        }
        
        return result;
    }

    public Set<Pair<CFGHandle, String>> getPointsToSetOf(CFGHandle cfg, String id)
    {
        EquivalenceClass sourceClass = getTypeOf(cfg, id);
        if (!(sourceClass.getType() instanceof LocationPointerType))
        {
            return null;
        }

        return getPointsToSetOf(cfg, id, sourceClass);
    }

    public void toDotFile()
    {
        System.out.println("digraph {");

        for (Entry<CFGHandle, HashMap<String, EquivalenceClass>> entry: typeTable.entrySet())
        {
            for (String name: entry.getValue().keySet())
            {
                System.out.printf("%s__%s [label=\"%s\"];\n", entry.getKey() != null ? entry.getKey().getFunctionName() : "", name, name);
            }
        }

        for (Entry<CFGHandle, HashMap<String, EquivalenceClass>> functionEntry: typeTable.entrySet())
        {
            CFGHandle cfg = functionEntry.getKey();

            for (Entry<String, EquivalenceClass> symbolEntry: functionEntry.getValue().entrySet())
            {
                String name = symbolEntry.getKey();
                EquivalenceClass type = symbolEntry.getValue();

                if (!(type.getType() instanceof LocationPointerType))
                    continue;

                Set<Pair<CFGHandle, String>> aliases = getPointsToSetOf(cfg, name, type);

                for (Pair<CFGHandle, String> alias: aliases)
                {
                    System.out.printf("%s__%s -> %s__%s;\n",
                            cfg != null ? cfg.getFunctionName() : "",
                            name,
                            alias.getFirst() != null ? alias.getFirst().getFunctionName() : "",
                            alias.getSecond());
                }

            }
        }

        System.out.println("}");
    }
}
