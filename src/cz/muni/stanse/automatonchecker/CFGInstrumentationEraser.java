package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.Triple;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

final class CFGInstrumentationEraser {

    // package-private section

    static List<cz.muni.stanse.checker.CheckerError> run(
          final Pair< HashMap<CFG,CFG>,HashMap<CFGNode,CFGNode> > backMapping,
          final List<cz.muni.stanse.checker.CheckerError> errors) {
        for (cz.muni.stanse.checker.CheckerError e : errors)
            for (cz.muni.stanse.checker.ErrorTrace trace : e.getErrorTraces())
                run(trace.getErrorTrace(),backMapping);
        return errors;
    }

    static List< Triple<CFGNode,String,CFG> > run(
          final List< Triple<CFGNode,String,CFG> > errorTrace,
          final Pair< HashMap<CFG,CFG>,HashMap<CFGNode,CFGNode> > backMapping) {
        for (ListIterator< Triple<CFGNode,String,CFG> > it =
                errorTrace.listIterator(); it.hasNext(); ) {
            final Triple<CFGNode,String,CFG> item = it.next();
            final CFGNode origNode=backMapping.getSecond().get(item.getFirst());
            if (origNode == null) {
                it.remove();
                continue;
            }
            item.setFirst(origNode);
            item.setThird(backMapping.getFirst().get(item.getThird()));
        }
        return errorTrace;
    }

    // private section

    private CFGInstrumentationEraser() {
    }
}
