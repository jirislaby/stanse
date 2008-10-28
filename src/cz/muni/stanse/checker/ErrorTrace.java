package cz.muni.stanse.checker;

import cz.muni.stanse.xml2cfg.CFGNode;
import cz.muni.stanse.utils.Pair;
import java.util.List;

public final class ErrorTrace {
    public ErrorTrace(String shortDesc, String fullDesc,
                      List< Pair<CFGNode,String> > trace) {
        this.shortDesc = shortDesc;
        this.fullDesc = fullDesc;
        this.trace = trace;
    }

    public String getShortDescription() {
        return shortDesc;
    }

    public String getFullDescription() {
        return fullDesc;
    }

    public List< Pair<CFGNode,String> > getErrorTrace() {
        return trace;
    }

    private final String shortDesc;
    private final String fullDesc;
    private final List< Pair<CFGNode,String> > trace;
}


// TODO: Error description is related to CFG-nodes only. There could arise
//       a need to attach description also to edges (in the future).
