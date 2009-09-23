/**
 * @brief
 *
 * Copyright (c) 2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */

package cz.muni.stanse.statistics;

import cz.muni.stanse.checker.CheckingResult;
import cz.muni.stanse.checker.CheckingFailed;
import cz.muni.stanse.utils.Triple;
import cz.muni.stanse.utils.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.dom4j.DocumentFactory;
import org.dom4j.Element;

public final class BasicEvaluationStatistic implements EvaluationStatistic {

    // public section

    public BasicEvaluationStatistic() {
        file = makeEmptyRecord();
        files = new Vector<Triple<String,Double,Double>>();

        internal = makeEmptyRecord();
        internals = new Vector<Triple<String,Double,Double>>();

        checker = makeEmptyRecord();
        checkers = new Vector<Triple<String,Double,Double>>();

        checkerFailures = new Vector<Pair<String,String>>();
    }

    @Override
    synchronized public void fileStart(final String fileName) {
        assert(!isValid(file));
        start(fileName,file);
    }

    @Override
    synchronized public void fileEnd() {
        assert(isValid(file));
        end(files,file);
        file = makeEmptyRecord();
    }

    @Override
    synchronized public void internalsStart() {
        assert(!isValid(internal));
        start("internals",internal);
    }
    @Override
    synchronized public void internalsEnd() {
        assert(isValid(internal));
        end(internals,internal);
        internal = makeEmptyRecord();
    }

    @Override
    synchronized public void checkerStart(final String checkerName) {
        assert(!isValid(checker));
        start(checkerName,checker);
    }
    @Override
    synchronized public void checkerEnd(final CheckingResult result) {
        assert(isValid(checker));
        if (result instanceof CheckingFailed)
            checkerFailures.add(Pair.make(checker.getFirst(),
                                          ((CheckingFailed)result).what()));
        end(checkers,checker);
        checker = makeEmptyRecord();
    }

    synchronized public Vector<Triple<String, Double, Double>> getCheckers() {
        return checkers;
    }

    synchronized public Vector<Triple<String, Double, Double>> getFiles() {
        return files;
    }

    synchronized public Vector<Triple<String, Double, Double>> getInternals() {
        return internals;
    }

    synchronized public Vector<Pair<String, String>> getCheckerFailures() {
        return checkerFailures;
    }

    public List<Element> xmlDump() {
	List<Element> result = new LinkedList<Element>();
	result.add(xmlDump("files", getFiles(), "file"));
	result.add(xmlDump("internals", getInternals(), "internal"));
	result.add(xmlDump("checkers", getCheckers(), "checker"));
        result.add(xmlDump("checkfails","fail",getCheckerFailures()));
        return result;
    }

    // private section

    private static Triple<String,Double,Double> makeEmptyRecord() {
        return Triple.<String,Double,Double>make(null,-1.0,-1.0);
    }

    private static void start(final String fileName,
                              final Triple<String,Double,Double> data) {
        data.setFirst(fileName);
        data.setSecond(getCurrTimeSEC());
        clearSpace();
        data.setThird(getCurrSpaceMB());
    }

    private static void end(final Vector<Triple<String,Double,Double>>container,
                            final Triple<String,Double,Double> data) {
        final double space = getCurrSpaceMB() - data.getThird();
        container.add(Triple.make(data.getFirst(),
                                  getCurrTimeSEC() - data.getSecond(),
                                  space < 0.0 ? 0.0 : space));
    }

    private static boolean isValid(final Triple<String,Double,Double> data) {
        return data.getFirst() != null;
    }

    private static double getCurrTimeSEC() {
        return System.currentTimeMillis() / 1000.0;
    }

    private static double getCurrSpaceMB() {
        return (Runtime.getRuntime().totalMemory() -
                Runtime.getRuntime().freeMemory()) / (double)(1024*1024);
    }

    private static void clearSpace() {
        Runtime.getRuntime().runFinalization();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
        Runtime.getRuntime().gc();
    }

    private static Element
    xmlDump(String elName, final Vector<Triple<String,Double,Double>> container,
            final String type) {
	Element result = DocumentFactory.getInstance().createElement(elName);
        for (final Triple<String,Double,Double> data: container) {
	    Element typeEl = result.addElement(type);
            typeEl.addElement("name").addText(data.getFirst());
            typeEl.addElement("time").addText(Double.toString(
			data.getSecond()));
            typeEl.addElement("space").addText(Double.toString(
			data.getThird()));
        }
        return result;
    }

    private static Element
    xmlDump(final String elName, final String type,
            final Vector<Pair<String,String>> container) {
        final Element result = DocumentFactory.getInstance()
                                              .createElement(elName);
        for (final Pair<String,String> data: container) {
            final Element item = result.addElement(type);
            item.addElement("checker").addText(data.getFirst());
            item.addElement("what").addText(data.getSecond());
        }
        return result;
    }

    private Triple<String,Double,Double> file;
    private final Vector<Triple<String,Double,Double>> files;

    private Triple<String,Double,Double> internal;
    private final Vector<Triple<String,Double,Double>> internals;

    private Triple<String,Double,Double> checker;
    private final Vector<Triple<String,Double,Double>> checkers;

    private final Vector<Pair<String,String>> checkerFailures;
}
