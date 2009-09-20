package cz.muni.stanse.statistics;

import cz.muni.stanse.Stanse;
import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerErrorReceiver;
import cz.muni.stanse.checker.CheckerProgressMonitor;

import java.util.Vector;

public final class StatisticBuilder {

    // public section

    public static void run(final String outputFile) {
        final Vector<CheckerError> errors = new Vector<CheckerError>();
        final BasicEvaluationStatistic evalStats=new BasicEvaluationStatistic();

        System.out.println("(1/3) Executing checking procedure on passed " +
                           "configuration.\nThis may take very long time...");

        Stanse.getInstance().getConfiguration().evaluateWait_EachUnitSeparately(
            new CheckerErrorReceiver() {
                @Override
                public void receive(final CheckerError error) {
                    errors.add(error);
                }
            },
            new CheckerProgressMonitor() {
                @Override
                public void write(final String s) {
                    System.out.print(s);
                }
            },
            evalStats);

        System.out.println("Checking done.\n\n(2/3) Starting conversion of " +
                           "collected data into XML format...");

        final String seek = "  ";
        final String data = evalStats.xmlDump("",seek) +
                            xmlDump(errors,"",seek);

        System.out.println("Conversion done.\n\n(3/3) Writing statistics in " +
                           "XML format into output file...");

        try {
            final java.io.BufferedWriter file =
                new java.io.BufferedWriter(new java.io.FileWriter(outputFile));
            file.write(data);
            file.close();
        } catch (java.io.IOException exception) {
            exception.printStackTrace();
            return;
        }

        System.out.println("Writting output done.\n\n\nSee results in " +
                           "file:\n    " + outputFile + "\n\n\n");
    }

    // private section

    private static String xmlDump(final Vector<CheckerError> errors,
                                  final String tab, final String seek) {
        String result = tab + "<errors>\n";
        for (final CheckerError error : errors)
            result += error.xmlDump(tab + seek,seek);
        return result + tab + "</errors>\n";
    }

    StatisticBuilder() {
    }

}
