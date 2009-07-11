package cz.muni.stanse.utils;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.Unit;

import java.util.Collection;
import java.util.Vector;

public final class UnitsToCFGs {

    // public section

    public static Vector<CFG> run(final Collection<Unit> units) {
        final Vector<CFG> result = new Vector<CFG>();
        for (final Unit unit : units)
            result.addAll(unit.getCFGs());
        return result;
    }

    // private section

    private UnitsToCFGs() {
    }
}
