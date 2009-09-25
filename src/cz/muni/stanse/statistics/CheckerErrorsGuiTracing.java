package cz.muni.stanse.statistics;

import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerErrorTrace;
import cz.muni.stanse.checker.CheckerErrorTraceLocation;
import cz.muni.stanse.gui.MainWindow;
import cz.muni.stanse.utils.Pair;

import java.util.Vector;
import java.util.List;
import java.util.Set;

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

        System.out.println("(1/7) Collecting report nodes...");
        final List errElems = database.selectNodes("database/errors/error");
        System.out.println("      Done.");

        System.out.println("(2/7) Building checker errors...");
        final Vector<CheckerError> errors = buildMessages(errElems,relocation);
        System.out.println("      Done.");

        assert(errElems.size() == errors.size());

        System.out.println("(3/7) Starting GUI in default style...");
        MainWindow.setLookAndFeel("DEFAULT");

        final MainWindow mainWindow = MainWindow.getInstance();

        mainWindow.setVisible(true);
        System.out.println("      Done.");

        System.out.println("(4/7) Delivering reports to GUI...");
        mainWindow.addErrorMessages(errors);
        System.out.println("      Done.");

        System.out.println("(5/7) Waiting till user resolves reports...");
        waitTillGUIEnds();
        System.out.println("      Done.");

        System.out.println("(6/7) Updating reports with resolution states...");
        updateReports(errElems,errors,mainWindow.getBugs(),
                      mainWindow.getFalsePositives());
        System.out.println("      Done.");

        System.out.println("(7/7) Writting reports into output file...");
        DocumentToFileWriter.writeErrorReports(errElems,outFile);
        System.out.println("      Done.");
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

    private static void
    updateReports(final List elements, final Vector<CheckerError> errors,
                  final Set<CheckerError> bugs, final Set<CheckerError> falses){
        assert(elements.size() == errors.size());
        for (int i = 0; i < elements.size(); ++i)
            if (bugs.contains(errors.get(i)))
                ((Element)elements.get(i)).addElement("real-bug");
            else if (falses.contains(errors.get(i)))
                ((Element)elements.get(i)).addElement("false-positive");
    }

    private CheckerErrorsGuiTracing() {
    }
}
