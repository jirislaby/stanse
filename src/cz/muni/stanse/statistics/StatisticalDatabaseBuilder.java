package cz.muni.stanse.statistics;

import cz.muni.stanse.Stanse;
import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.CheckerErrorReceiver;
import cz.muni.stanse.checker.CheckerProgressMonitor;

import java.util.Vector;

public final class StatisticalDatabaseBuilder {

    // public section

    public static void run(final String outputFile) {
        final Vector<CheckerError> errors = new Vector<CheckerError>();
        final BasicEvaluationStatistic evalStats=new BasicEvaluationStatistic();

        System.out.print("Statistical database build\n"+
                         "~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");

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
        final String data =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<database>\n" +
                evalStats.xmlDump(seek,seek) +
                xmlDump(errors,seek,seek) +
                "</database>\n";

        System.out.println("Conversion done.\n\n(3/3) Writing statistics in " +
                           "XML format into output file...");

        if (!StringToFileWriter.write(data,outputFile))
            return;

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

    private StatisticalDatabaseBuilder() {
    }
}
