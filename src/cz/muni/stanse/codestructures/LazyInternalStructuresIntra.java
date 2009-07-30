package cz.muni.stanse.codestructures;

import java.util.Collection;

public final class LazyInternalStructuresIntra
			extends LazyInternalStructures {

    public LazyInternalStructuresIntra(final Collection<Unit> units,
		final Collection<CFGHandle> cfgs) {
	super(units, cfgs);
    }

    @Override
    synchronized void setNavigator() {
        if (navigator == null)
            navigator = new IntraproceduralCFGsNavigator(getCFGHandles());
    }
}
