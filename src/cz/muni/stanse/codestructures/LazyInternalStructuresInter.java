package cz.muni.stanse.codestructures;

import java.util.Collection;
import java.util.Map;

public final class LazyInternalStructuresInter
			extends LazyInternalStructures {

    public LazyInternalStructuresInter(final Collection<Unit> units,
		final Collection<CFGHandle> cfgs) {
	super(units, cfgs);
    }

    @Override
    synchronized void setNavigator() {
        if (navigator == null)
            navigator = new InterproceduralCFGsNavigator(getCFGHandles(),
                                                   getElementToCFGdictionary());
    }
}
