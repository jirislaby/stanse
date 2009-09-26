package cz.muni.stanse.statistics;

import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerErrorTrace;
import cz.muni.stanse.checker.CheckerErrorTraceLocation;
import cz.muni.stanse.gui.MainWindow;
import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.Triple;

import java.util.Vector;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

public final class CheckerErrorsGuiTracing {

    // public section

    public static void run(final Document database, final String outFile) {
        run(database,Pair.make("",""),outFile);
    }

    public static void run(final Document database,
                           final Pair<String,String> relocation,
                           final String outFile) {
        System.out.print("Checker errors GUI tracing\n"+
                         "~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");

        System.out.println("(1/8) Collecting report nodes...");
        final List errElems = database.selectNodes("database/errors/error");
        System.out.println("      Done.");

        System.out.println("(2/8) Splitting reports into categories...");
        final Triple<Vector<Element>,Vector<Element>,Vector<Element>>
            categories = CheckerErrorsSorter.splitByResolved(errElems);
        System.out.println("      Done.");

        System.out.println("(3/8) Building checker errors...");
        final Vector<CheckerError> origBugs =
                buildMessages(categories.getFirst(),relocation);
        final Vector<CheckerError> origFalses =
                buildMessages(categories.getSecond(),relocation);
        final Vector<CheckerError> origUnchecked =
                buildMessages(categories.getThird(),relocation);
        System.out.println("      Done.");

        System.out.println("(4/8) Starting GUI in default style...");
        MainWindow.setLookAndFeel("DEFAULT");

        final MainWindow mainWindow = MainWindow.getInstance();

        mainWindow.setVisible(true);
        System.out.println("      Done.");

        System.out.println("(5/8) Delivering reports to GUI...");
        mainWindow.addBugs(origBugs);
        mainWindow.addFalsePositives(origFalses);
        mainWindow.addUnchecked(origUnchecked);
        mainWindow.refreshErrorsTree();
        System.out.println("      Done.");

        System.out.println("(6/8) Waiting till user resolves reports...");
        waitTillGUIEnds();
        System.out.println("      Done.");

        System.out.println("(7/8) Collecting results from GUI...");
        final Vector<Element> result = new Vector<Element>();
        result.addAll(updateReports(
                            StatisticalDatabaseBuilder
                                    .toElements(mainWindow.getBugs()),
                            "real-bug"));
        result.addAll(updateReports(
                            StatisticalDatabaseBuilder
                                    .toElements(mainWindow.getFalsePositives()),
                            "false-positive"));
        result.addAll(StatisticalDatabaseBuilder
                                    .toElements(mainWindow.getUnchecked()));
        System.out.println("      Done.");

        System.out.println("(8/8) Writting reports into output file...");
        DocumentToFileWriter.writeErrorReports(
            database.selectNodes("database/files/file"),
            database.selectNodes("database/internals/internal"),
            database.selectNodes("database/checkers/checker"),
            database.selectNodes("database/checkfails/fail"),
            result,
            outFile
        );
        System.out.print("      Done.\n\n\n");
    }

    // private section

    private static void waitTillGUIEnds() {
        while (MainWindow.isRunning()) {
        }
    }

    private static Vector<CheckerError> buildMessages(final List elements,
                                         final Pair<String,String> relocation) {
        final Vector<CheckerError> errors = new Vector<CheckerError>();
        for (final Object obj : elements)
            if (obj instanceof Element)
                errors.add(buildError((Element)obj,relocation));
        return errors;
    }

    private static CheckerError buildError(final Element errElem,
                                         final Pair<String,String> relocation) {
        final Vector<CheckerErrorTrace> traces =new Vector<CheckerErrorTrace>();
        for (final Object obj : errElem.selectNodes("traces/trace"))
            if (obj instanceof Element)
                traces.add(buildTrace((Element)obj,relocation));
        return new CheckerError(
                        errElem.selectSingleNode("short_desc").getText(),
                        errElem.selectSingleNode("full_desc").getText(),
                        new Integer(errElem.selectSingleNode("importance")
                                           .getText()).intValue(),
                        errElem.selectSingleNode("checker_name").getText(),
                        traces);
    }

    private static CheckerErrorTrace buildTrace(final Element traceElem,
                                         final Pair<String,String> relocation) {
        final Vector<CheckerErrorTraceLocation> locations =
                new Vector<CheckerErrorTraceLocation>();
        for (final Object obj : traceElem.selectNodes("locations/location"))
            if (obj instanceof Element)
                locations.add(buildLocation((Element)obj,relocation));
        return new CheckerErrorTrace(locations,
                        traceElem.selectSingleNode("description").getText());
    }

    private static CheckerErrorTraceLocation
    buildLocation(final Element locElem, final Pair<String,String> relocation) {
        return new CheckerErrorTraceLocation(
                        locElem.selectSingleNode("unit")
                               .getText()
                               .replaceFirst(relocation.getFirst(),
                                             relocation.getSecond()),
                        new Integer(locElem.selectSingleNode("line")
                                           .getText()).intValue(),
                        locElem.selectSingleNode("description").getText());
    }

    private static Vector<Element>
    updateReports(final Vector<Element> elements, final String flagName) {
        for (final Element elem : elements)
            elem.addElement(flagName);
        return elements;
    }

    private CheckerErrorsGuiTracing() {
    }
}
