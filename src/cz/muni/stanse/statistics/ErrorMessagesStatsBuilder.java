package cz.muni.stanse.statistics;

import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.Triple;

import java.util.Vector;
import java.util.List;
import java.util.HashMap;
import java.util.Collections;

import org.dom4j.Document;
import org.dom4j.Element;

final class Compute {
    static double percentage(final int part, final int base) {
        return (base == 0) ? 0.0 : ( (double)part / (double)base ) * 100.0;
    }
}

class MessagesStat {

    // package-private section

    MessagesStat(final List errElements, final List failElements) {
        numMsgs = errElements.size();

        final Triple<Vector<Element>,Vector<Element>,Vector<Element>>
                categories = CheckerErrorsSorter.splitByResolved(errElements);
        numBugs = categories.getFirst().size();
        numFalses = categories.getSecond().size();

        numTouched = numBugs + numFalses;
        numUntouched = numMsgs - numTouched;

        numFailures = failElements.size();

        assert(numUntouched == categories.getThird().size());

        bugsPercentage = Compute.percentage(numBugs,numTouched);
        falsesPercentage = Compute.percentage(numFalses,numTouched);
        touchedPercentage = Compute.percentage(numTouched,numMsgs);
    }

    final int getNumMsgs() { return numMsgs; }
    final int getNumBugs() { return numBugs; }
    final int getNumFalses() { return numFalses; }
    final int getNumTouched() { return numTouched; }
    final int getNumUntouched() { return numUntouched; }
    final int getNumFailures() { return numFailures; }
    final double getBugsPercentage() { return bugsPercentage; }
    final double getFalsesPercentage() { return falsesPercentage; }
    final double getTouchedPercentage() { return touchedPercentage; }

    // private section

    private final int numMsgs;
    private final int numBugs;
    private final int numFalses;
    private final int numTouched;
    private final int numUntouched;
    private final int numFailures;

    private final double bugsPercentage;
    private final double falsesPercentage;
    private final double touchedPercentage;
}

class SectionedMessagesStat extends MessagesStat {

    // package-private section

    SectionedMessagesStat(final String sectionName, final List errElements,
                          final List failElements, final MessagesStat overalls){
        super(errElements,failElements);

        this.sectionName = sectionName;

        bugsToBugsPercentage =
                Compute.percentage(getNumBugs(),overalls.getNumBugs());
        falsesToFalsesPercentage =
                Compute.percentage(getNumFalses(),overalls.getNumFalses());
        bugsToTouchedPercentage =
                Compute.percentage(getNumBugs(),overalls.getNumTouched());
        falsesToTouchedPercentage =
                Compute.percentage(getNumFalses(),overalls.getNumTouched());
        failuresToFailuresPercentage =
                Compute.percentage(getNumFailures(),overalls.getNumFailures());
    }

    final String getSectionName() { return sectionName; }

    final double getBugsToAllBugsPercentage() {
        return bugsToBugsPercentage;
    }

    final double getFalsesToAllFalsesPercentage() {
        return falsesToFalsesPercentage;
    }

    final double getBugsToAllTouchedPercentage() {
        return bugsToTouchedPercentage;
    }

    final double getFalsesToAllTouchedPercentage() {
        return falsesToTouchedPercentage;
    }

    final double getFailuresToAllFailuresPercentage() {
        return failuresToFailuresPercentage;
    }

    // private section

    private final String sectionName;
    private final double bugsToBugsPercentage;
    private final double falsesToFalsesPercentage;
    private final double bugsToTouchedPercentage;
    private final double falsesToTouchedPercentage;
    private final double failuresToFailuresPercentage;
}

final class ComposedSectionedMessagesStat extends SectionedMessagesStat {

    // package-private section

    ComposedSectionedMessagesStat(final String sectionName,
                            final List errElements, final List failElements,
                            final MessagesStat overalls,
                            final Vector<SectionedMessagesStat> descStats) {
        super(sectionName,errElements,failElements,overalls);
        this.descStats = descStats;
    }

    Vector<SectionedMessagesStat> getStats() { return descStats; }

    // private section

    private final Vector<SectionedMessagesStat> descStats;
}

public final class ErrorMessagesStatsBuilder {

    // public section

    public static void run(final Document database, final String outputFile,
                           final String outputFormat) {
        System.out.print("Build of statistics of checker error messages\n"+
                         "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");

        System.out.println("(1/4) Collecting error and failure nodes...");
        final List errors = database.selectNodes("database/errors/error");
        final List failures = database.selectNodes("database/checkfails/fail");
        System.out.println("      Done.");

        System.out.println("(2/4) Computing overall statistics...");
        final MessagesStat overalls = new MessagesStat(errors,failures);
        System.out.println("      Done.");

        System.out.println("(3/4) Computing per-checker statistics...");
        final Vector<ComposedSectionedMessagesStat> perCheckers =
                buildPerCheckerStats(errors,failures,overalls);
        System.out.println("      Done.");

        System.out.println("(4/4) Writting results to output...");
        final String perfContent =
            outputFormat.equals("simple-text") ?
                buildOutputSimpleText(overalls,perCheckers) :
                null;
        assert(perfContent != null);
        StringToFileWriter.write(perfContent,outputFile);
        System.out.print("      Done.\n\n\n");
    }

    // private section

    private static Vector<ComposedSectionedMessagesStat>
    buildPerCheckerStats(final List errElements, final List failures,
                         final MessagesStat overalls) {
        final HashMap<String,Vector<Element>> failuresMap =
            splitFailuresByChecker(failures);
        Vector<ComposedSectionedMessagesStat> result =
                new Vector<ComposedSectionedMessagesStat>();
        for (final Pair<String,Vector<Element>> item :
                    CheckerErrorsSorter.splitByChecker(errElements)) {
            Vector<Element> checkerFailures = failuresMap.get(item.getFirst());
            if (checkerFailures == null)
                checkerFailures = new Vector<Element>();
            result.add(new ComposedSectionedMessagesStat(
                                item.getFirst(),item.getSecond(),
                                checkerFailures,overalls,
                                buildDescStats(item.getSecond(),
                                               Collections.emptyList(),
                                               overalls)));
        }
        return result;
    }

    private static HashMap<String,Vector<Element>>
    splitFailuresByChecker(final List failures){
        final HashMap<String,Vector<Element>> failuresMap =
                new HashMap<String,Vector<Element>>();
        for (final Pair<String,Vector<Element>> item :
                    CheckerErrorsSorter.splitByLastOf("checker",failures))
            failuresMap.put(item.getFirst(),item.getSecond());
        return failuresMap;
    }

    private static Vector<SectionedMessagesStat>
    buildDescStats(final List errElements, final List failures,
                   final MessagesStat overalls) {
        Vector<SectionedMessagesStat> result =
                new Vector<SectionedMessagesStat>();
        for (final Pair<String,Vector<Element>> item :
                    CheckerErrorsSorter.splitByDescription(errElements))
            result.add(new SectionedMessagesStat(item.getFirst(),
                                                 item.getSecond(),
                                                 failures,overalls));
        return result;
    }

    private static String buildOutputSimpleText(final MessagesStat overalls,
                      final Vector<ComposedSectionedMessagesStat> perCheckers) {
        final TabbedStringStream stream = new TabbedStringStream();

        stream.append("Error message reports statistics\n")
              .append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n\n");

        stream.appendln("(1/2) Overall :").push().push();
        buildOutputSimpleText(overalls,stream);
        stream.pop().pop();

        stream.appendln("");

        stream.appendln("(2/2) Per-checker and per-report description:")
              .push().push();
        for (final ComposedSectionedMessagesStat compStat : perCheckers) {
            stream.append("+ ").append(compStat.getSectionName()).appendln(':')
                  .push();
            buildOutputSimpleText(compStat,stream);
            stream.appendln("")
                  .pop();
        }
        stream.pop().pop();

        return stream.getString();
    }

    private static TabbedStringStream
    buildOutputSimpleText(final MessagesStat stat,
                          final TabbedStringStream stream) {
        stream.append("Num reports          : ")
              .appendln(stat.getNumMsgs())
              .append("Num checked reports  : ")
              .appendln(stat.getNumTouched())
              .append("Num unchecked reports: ")
              .appendln(stat.getNumUntouched())
              .append("Num real bugs        : ")
              .appendln(stat.getNumBugs())
              .append("Num false-positives  : ")
              .appendln(stat.getNumFalses())
              .append("Num failures         : ")
              .appendln(stat.getNumFailures())
              .append("Checked reports %    : ")
              .appendln(stat.getTouchedPercentage())
              .append("Bugs %               : ")
              .appendln(stat.getBugsPercentage())
              .append("False-positives %    : ")
              .appendln(stat.getFalsesPercentage())
              ;
        return stream;
    }

    private static TabbedStringStream
    buildOutputSimpleText(final SectionedMessagesStat stat,
                          final TabbedStringStream stream) {
        buildOutputSimpleText((MessagesStat)stat,stream);
        stream.appendln("-----------------------------------------")
              .append("Bugs to all ones %                      : ")
              .appendln(stat.getBugsToAllBugsPercentage())
              .append("Bugs to all checked reports %           : ")
              .appendln(stat.getBugsToAllTouchedPercentage())
              .append("False-positives to all ones %           : ")
              .appendln(stat.getFalsesToAllFalsesPercentage())
              .append("False-positives to all checked reports %: ")
              .appendln(stat.getBugsToAllTouchedPercentage())
              .append("Failures to all ones %                  : ")
              .appendln(stat.getFailuresToAllFailuresPercentage())
              ;
        return stream;
    }

    private static TabbedStringStream
    buildOutputSimpleText(final ComposedSectionedMessagesStat stat,
                          final TabbedStringStream stream) {
        buildOutputSimpleText((SectionedMessagesStat)stat,stream);
        stream.appendln("");
        for (final SectionedMessagesStat secStat : stat.getStats()) {
            stream.append("+ ").append(secStat.getSectionName()).appendln(':')
                  .push();
            buildOutputSimpleText(secStat,stream);
            stream.appendln("")
                  .pop();
        }
        return stream;
    }

    private ErrorMessagesStatsBuilder() {
    }
}
