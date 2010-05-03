package cz.muni.stanse.pointeranalyzer;

import java.util.Collection;
import java.util.Set;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.codestructures.CFGHandle;

/**
 *
 * @author Michal
 */
public interface PointsToAnalyzer {

    public void analyze(Collection<CFGHandle> cfgs);

    public Set<Pair<CFGHandle, String>> getPointsToSetOf(CFGHandle cfg, String id);

}
