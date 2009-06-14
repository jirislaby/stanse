/**
 * @file CFGInstrumentationBuilder.java 
 * @brief Implements final class CFGInstrumentationEraser which is responsible
 *        for returning CFG from its deep copy and branch-to-asserts-modified
 *        CFG back to original one.
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.utils;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

/**
 * @brief Whole checking for errors is done of deep copies and
 *        branch-to-asserts-modified CFGs. So when some errors are found, then
 *        it is necessary somehow convert theirs error traces, to refer to
 *        original CFGs and not to copied ones. And this is exactly the job of
 *        this class.
 *
 * Class is not intended to be instantiated.
 */
public final class CFGInstrumentationEraser {

    // package-private section

    /**
     * @brief Converts list of checker-error found by some AutomatonChecker
     *        instance, which refers to deep-copied and branch-to-asserts-
     *        modified CFGs, back to original ones.
     *
     * It is only necessary to convers error-traces of all the checker errors,
     * so this method uses method another method run(), which provides
     * conversion of error-traces itself.
     *
     * The conversion is based on dictionary, which provides mapping between
     * copied CFGs and original ones. This dictionary is created in class
     * CFGInstrumentationBuilder 
     *
     * @param backMapping Dictionary, which provides mapping from deep-copied
     *               and branch-to-asserts-modified CFGs to original ones. 
     * @param errors List of checker-errors, which refer to deep-copied and
     *               branch-to-asserts-modified CFGs. 
     * @return List of checker-errors, which refer to original CFGs.
     * @see cz.muni.stanse.automatonchecker.CFGInstrumentationEraser#
     *          run(List,Pair)
     * @see cz.muni.stanse.automatonchecker.CFGInstrumentationBuilder
     */
    public static List<cz.muni.stanse.checker.CheckerError> run(
          final Pair< HashMap<CFG,CFG>,HashMap<CFGNode,CFGNode> > backMapping,
          final List<cz.muni.stanse.checker.CheckerError> errors) {
        for (cz.muni.stanse.checker.CheckerError e : errors)
            for (cz.muni.stanse.checker.ErrorTrace trace : e.getErrorTraces())
                run(trace.getErrorTrace(),backMapping);
        return errors;
    }

    /**
     * @brief Converts one error-trace (of some checker-error), which refers to
     *        deep-copied and branch-to-asserts-modified CFGs, back to original
     *        ones.
     *
     * The conversion is based on dictionary, which provides mapping between
     * copied CFGs and original ones. This dictionary is created in class
     * CFGInstrumentationBuilder 
     *
     * @param errorTrace An error-trace, which refer to deep-copied and
     *               branch-to-asserts-modified CFGs. 
     * @param backMapping Dictionary, which provides mapping from deep-copied
     *               and branch-to-asserts-modified CFGs to original ones. 
     * @return An error-trace, which refer to original CFGs.  
     * @see cz.muni.stanse.automatonchecker.CFGInstrumentationBuilder
     */
    public static List< Triple<CFGNode,String,CFG> > run(
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
