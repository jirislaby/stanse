package cz.muni.stanse.statistics;

import cz.muni.stanse.utils.Triple;
import java.util.Vector;

public final class BasicEvaluationStatistic implements EvaluationStatistic {

    // public section

    public BasicEvaluationStatistic() {
        file = makeEmptyRecord();
        files = new Vector<Triple<String,Double,Double>>();

        internal = makeEmptyRecord();
        internals = new Vector<Triple<String,Double,Double>>();

        checker = makeEmptyRecord();
        checkers = new Vector<Triple<String,Double,Double>>();
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
    synchronized public void checkerEnd() {
        assert(isValid(checker));
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

    public String xmlDump(final String tab, final String seek) {
        String result = "";

        result += tab + "<files>\n";
        result += xmlDump(getFiles(),"file",tab + seek,seek);
        result += tab + "</files>\n";

        result += tab + "<internals>\n";
        result += xmlDump(getInternals(),"internal",tab + seek,seek);
        result += tab + "</internals>\n";

        result += tab + "<checkers>\n";
        result += xmlDump(getCheckers(),"checker",tab + seek,seek);
        result += tab + "</checkers>\n";

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
        data.setThird(getCurrSpaceMB());
    }

    private static void end(final Vector<Triple<String,Double,Double>>container,
                            final Triple<String,Double,Double> data) {
        container.add(Triple.make(data.getFirst(),
                                  getCurrTimeSEC() - data.getSecond(),
                                  getCurrSpaceMB() - data.getThird()));
    }

    private static boolean isValid(final Triple<String,Double,Double> data) {
        return data.getFirst() != null;
    }

    private static double getCurrTimeSEC() {
        return System.currentTimeMillis() / 1000.0;
    }

    private static double getCurrSpaceMB() {
        return (Runtime.getRuntime().totalMemory() -
                Runtime.getRuntime().freeMemory()) / (1024*1024);
    }

    private static String
    xmlDump(final Vector<Triple<String,Double,Double>> container,
            final String type, final String tab, final String seek) {
        String result = "";
        for (final Triple<String,Double,Double> data : container) {
            result += tab + '<' + type + ">\n";
            result += tab + seek + "<name>" + data.getFirst() + "</name>\n";
            result += tab + seek + "<time>" + data.getSecond() + "</time>\n";
            result += tab + seek + "<space>" + data.getThird() + "</space>\n";
            result += tab + "</" + type + ">\n";
        }
        return result;
    }


    private Triple<String,Double,Double> file;
    private final Vector<Triple<String,Double,Double>> files;

    private Triple<String,Double,Double> internal;
    private final Vector<Triple<String,Double,Double>> internals;

    private Triple<String,Double,Double> checker;
    private final Vector<Triple<String,Double,Double>> checkers;
}
