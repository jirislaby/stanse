package cz.muni.stanse.statistics;

import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerErrorTrace;
import cz.muni.stanse.checker.CheckerErrorTraceLocation;
import cz.muni.stanse.gui.MainWindow;

import org.dom4j.Document;
import org.dom4j.Element;
import java.util.Vector;

public final class CheckerErrorsGuiTracing {

    // public section

    public static void run(final Document database) {
        System.out.print("Checker errors GUI tracing\n"+
                         "~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");

        System.out.println("(1/3) Building checker errors...");
        final Vector<CheckerError> errors = buildMessages(database);
        System.out.println("      Done.");

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                System.out.println("(2/3) Starting GUI in default style...");
                MainWindow.setLookAndFeel("DEFAULT");
                MainWindow.getInstance().setVisible(true);
                System.out.println("      Done.");

                System.out.println("(3/3) Delivering errors to GUI...");
                MainWindow.getInstance().addErrorMessages(errors);
                System.out.println("      Done.");
            }
        });
    }

    // private section

    private static Vector<CheckerError> buildMessages(final Document database) {
        final Vector<CheckerError> errors = new Vector<CheckerError>();
        for (final Object obj : database.selectNodes("database/errors/error"))
            if (obj instanceof Element)
                errors.add(buildError((Element)obj));
        return errors;
    }

    private static CheckerError buildError(final Element errElem) {
        final Vector<CheckerErrorTrace> traces =new Vector<CheckerErrorTrace>();
        for (final Object obj : errElem.selectNodes("traces/trace"))
            if (obj instanceof Element)
                traces.add(buildTrace((Element)obj));
        return new CheckerError(
                        errElem.selectSingleNode("short_desc").getText(),
                        errElem.selectSingleNode("full_desc").getText(),
                        new Integer(errElem.selectSingleNode("importance")
                                           .getText()).intValue(),
                        errElem.selectSingleNode("checker_name").getText(),
                        traces);
    }

    private static CheckerErrorTrace buildTrace(final Element traceElem) {
        final Vector<CheckerErrorTraceLocation> locations =
                new Vector<CheckerErrorTraceLocation>();
        for (final Object obj : traceElem.selectNodes("locations/location"))
            if (obj instanceof Element)
                locations.add(buildLocation((Element)obj));
        return new CheckerErrorTrace(locations,
                        traceElem.selectSingleNode("description").getText());
    }

    private static CheckerErrorTraceLocation
    buildLocation(final Element locElem) {
        return new CheckerErrorTraceLocation(
                        locElem.selectSingleNode("unit").getText(),
                        new Integer(locElem.selectSingleNode("line")
                                           .getText()).intValue(),
                        locElem.selectSingleNode("description").getText());
    }

    private CheckerErrorsGuiTracing() {
    }
}
