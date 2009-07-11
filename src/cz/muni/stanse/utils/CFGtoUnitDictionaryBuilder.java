package cz.muni.stanse.utils;

import cz.muni.stanse.codestructures.Unit;
import cz.muni.stanse.codestructures.CFG;

import java.util.HashMap;
import java.util.Collection;

public class CFGtoUnitDictionaryBuilder {

    // public section

    public static HashMap<CFG,Unit> run(final Collection<Unit> units) {
        final HashMap<CFG,Unit> result = new HashMap<CFG,Unit>();
        for (final Unit unit : units)
            for (final CFG cfg : unit.getCFGs())
                result.put(cfg,unit);
        return result;
    }

    // private section

    private CFGtoUnitDictionaryBuilder() {
    }
}
