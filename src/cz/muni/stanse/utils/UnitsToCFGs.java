package cz.muni.stanse.utils;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.Unit;

import java.util.Collection;
import java.util.LinkedList;

public final class UnitsToCFGs {

    // public section

    public static LinkedList<CFG> run(final Collection<Unit> units) {
        final LinkedList<CFG> result = new LinkedList<CFG>();
        for (final Unit unit : units)
            result.addAll(unit.getCFGs());
        return result;
    }

    // private section

    private UnitsToCFGs() {
    }
}
