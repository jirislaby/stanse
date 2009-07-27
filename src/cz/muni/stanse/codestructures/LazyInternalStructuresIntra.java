package cz.muni.stanse.codestructures;

import java.util.Collection;
import java.util.Map;

public final class LazyInternalStructuresIntra
			extends LazyInternalStructures {

    public LazyInternalStructuresIntra(final Collection<Unit> units,
		final Map<CFG,Unit> cfgToUnitDictionary) {
	super(units, cfgToUnitDictionary);
    }

    @Override
    synchronized void setNavigator() {
        if (navigator == null)
            navigator = new IntraproceduralCFGsNavigator(getCFGs());
    }
}
