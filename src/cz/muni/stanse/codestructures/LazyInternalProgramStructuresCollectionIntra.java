package cz.muni.stanse.codestructures;

import java.util.Collection;
import java.util.Map;

public final class LazyInternalProgramStructuresCollectionIntra
                            extends LazyInternalProgramStructuresCollection {

    public LazyInternalProgramStructuresCollectionIntra(
                                      final Collection<Unit> units,
                                      final Map<CFG,Unit> cfgToUnitDictionary) {
	super(units, cfgToUnitDictionary);
    }

    @Override
    synchronized void setNavigator() {
        if (navigator == null)
            navigator = new IntraproceduralCFGsNavigator(getCFGs());
    }
}
