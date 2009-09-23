package cz.muni.stanse.statistics;

import cz.muni.stanse.utils.Pair;
import cz.muni.stanse.utils.Triple;

import org.dom4j.Document;
import org.dom4j.Element;
import java.util.Vector;
import java.util.List;
import java.util.HashMap;
import java.util.Collections;

final class PerfRecord {

    // package-private section

    PerfRecord(final int numItems,
               final double sum, final double min, final double max,
               final double average, final double median) {
        this.numItems = numItems;
        this.sum = sum;
        this.min = min;
        this.max = max;
        this.average = average;
        this.median = median;
    }

    int    getNumItems() { return numItems; }
    double getSum() { return sum; }
    double getMin() { return min; }
    double getMax() { return max; }
    double getAverage() { return average; }
    double getMedian() { return median; }

    // private section

    private int numItems;
    private double sum;
    private double min;
    private double max;
    private double average;
    private double median;
}

final class PerformanceData {

    // package-private section

    PerformanceData(final Document database) {
        filesPerfData = buildFilesPerformanceData(database);
        precachesPerfData = buildPrecachePerformanceData(database);
        checkersPerfData = buildCheckersPerformanceData(database);
        perCheckersPerfData = buildPerCheckersPerformanceData(database);
    }

    Pair<PerfRecord,PerfRecord> getFilesPerfData() {
        return filesPerfData;
    }

    Pair<PerfRecord,PerfRecord> getPrecachesPerfData() {
        return precachesPerfData;
    }

    Pair<PerfRecord,PerfRecord> getCheckersPerfData() {
        return checkersPerfData;
    }

    Vector<Triple<String,PerfRecord,PerfRecord>> getPerCheckersPerfData() {
        return perCheckersPerfData;
    }

    // private section

    private static Pair<PerfRecord,PerfRecord>
    buildFilesPerformanceData(final Document database) {
        return buildPerfDataPair(database,"database/files");
    }

    private static Pair<PerfRecord,PerfRecord>
    buildPrecachePerformanceData(final Document database) {
        return buildPerfDataPair(database,"database/internals");
    }

    private static Pair<PerfRecord,PerfRecord>
    buildCheckersPerformanceData(final Document database) {
        return buildPerfDataPair(database,"database/checkers");
    }

    private static Vector<Triple<String,PerfRecord,PerfRecord>>
    buildPerCheckersPerformanceData(final Document database) {
        final HashMap<String,Pair<Vector<Element>,Vector<Element>>>
            perCheckerElemDict = new HashMap<String,Pair<Vector<Element>,
                                                         Vector<Element>>>();
        for (final Object obj:database.selectNodes("database/checkers/checker"))
            if (obj instanceof Element) {
                final String checkerName =
                        ((Element)obj).selectSingleNode("name").getText();
                if (!perCheckerElemDict.containsKey(checkerName))
                    perCheckerElemDict.put(checkerName,
                                           Pair.make(new Vector<Element>(),
                                                     new Vector<Element>()));
                perCheckerElemDict.get(checkerName)
                                  .getFirst()
                                  .add( ((Element)obj).element("time") );
                perCheckerElemDict.get(checkerName)
                                  .getSecond()
                                  .add( ((Element)obj).element("space") );
            }

        final Vector<Triple<String,PerfRecord,PerfRecord>> result =
            new Vector<Triple<String,PerfRecord,PerfRecord>>();
        for (final String checkerName : perCheckerElemDict.keySet())
            result.add(
                Triple.make(checkerName,
                            buildPerfData(perCheckerElemDict.get(checkerName)
                                                            .getFirst()),
                            buildPerfData(perCheckerElemDict.get(checkerName)
                                                            .getSecond())));
        return result;
    }

    private static Pair<PerfRecord,PerfRecord>
    buildPerfDataPair(final Document database, final String path) {
        final PerfRecord timesPerf =
            buildPerfData(database.selectNodes(path + "//time"));
        final PerfRecord spacesPerf =
            buildPerfData(database.selectNodes(path + "//space"));
        assert(timesPerf.getNumItems() == spacesPerf.getNumItems());
        return Pair.make(timesPerf,spacesPerf);
    }

    private static PerfRecord buildPerfData(final List elements) {
        final Vector<Double> data = getSortedPropertyVector(elements);
        final int n = data.size();
        double sum = 0.0;
        for (int i = 0; i < n; ++i)
            sum += data.get(i);
        return new PerfRecord(n,sum,data.get(0),data.get(n-1),
                                sum/n,data.get(n/2));
    }

    private static Vector<Double> getSortedPropertyVector(final List elements) {
        final Vector<Double> result = getPropertyVector(elements);
        Collections.sort(result);
        return result;
    }
    private static Vector<Double> getPropertyVector(final List elements) {
        final Vector<Double> result = new Vector<Double>();
        for (final Object obj : elements)
            if (obj instanceof Element)
                result.add(new Double(((Element)obj).getText()));
        return result;
    }

    private Pair<PerfRecord,PerfRecord> filesPerfData;
    private Pair<PerfRecord,PerfRecord> precachesPerfData;
    private Pair<PerfRecord,PerfRecord> checkersPerfData;
    private final Vector<Triple<String,PerfRecord,PerfRecord>>
                perCheckersPerfData;
}

public final class PerformanceDataBuilder {

    // public section

    public static void run(final Document database, final String outputFile,
                           final String outputFormat) {
        System.out.print("Performace statistical data building\n"+
                         "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");

        System.out.println("(1/2) Collecting data into internal structures...");
        final PerformanceData perfData = new PerformanceData(database);
        System.out.println("      Done.");

        System.out.println("(2/2) Writting collected data into output...");
        final String perfContent =
            outputFormat.equals("simple-text") ?
                buildOutputSimpleText(perfData) :
                null;
        assert(perfContent != null);
        StringToFileWriter.write(perfContent,outputFile);
        System.out.println("      Done.");
    }

    // private section

    private static String buildOutputSimpleText(final PerformanceData perfData){
        StringBuilder stream = new StringBuilder();
        stream.append("Performance statistics\n")
              .append("~~~~~~~~~~~~~~~~~~~~~~\n\n\n")
              .append("(1/4) Files procession profile:\n")
              .append("      (a) Time(s):\n")
              .append(toSimpleText(perfData.getFilesPerfData().getFirst(),
                      "              "))
              .append("      (b) Space(MB):\n")
              .append(toSimpleText(perfData.getFilesPerfData().getSecond(),
                      "              "))
              .append("(2/4) Precaching LazyInternals before checker " +
                                " start checking profile:\n")
              .append("      (a) Time(s):\n")
              .append(toSimpleText(perfData.getPrecachesPerfData().getFirst(),
                      "              "))
              .append("      (b) Space(MB):\n")
              .append(toSimpleText(perfData.getPrecachesPerfData().getSecond(),
                      "              "))
              .append("(3/4) Checkers summary working profile:\n")
              .append("      (a) Time(s):\n")
              .append(toSimpleText(perfData.getCheckersPerfData().getFirst(),
                      "              "))
              .append("      (b) Space(MB):\n")
              .append(toSimpleText(perfData.getCheckersPerfData().getSecond(),
                      "              "))
              .append("(4/4) Per checker working profile:\n")
              ;

        stream.append("      (a) Time(s):\n");
        for (final Triple<String,PerfRecord,PerfRecord> rec :
                perfData.getPerCheckersPerfData())
            stream.append("              ").append(rec.getFirst()).append('\n')
                  .append(toSimpleText(rec.getSecond(),"                  "))
                  ;
        stream.append("      (b) Space(MB):\n");
        for (final Triple<String,PerfRecord,PerfRecord> rec :
                perfData.getPerCheckersPerfData())
            stream.append("              ").append(rec.getFirst()).append('\n')
                  .append(toSimpleText(rec.getThird(),"                  "))
                  ;

        return stream.toString();
    }

    private static String
    toSimpleText(final PerfRecord data, final String tab) {
        StringBuilder stream = new StringBuilder();
        stream.append(tab)
              .append("Count  : ").append(data.getNumItems()).append('\n')
              .append(tab)
              .append("Sum    : ").append(data.getSum()).append('\n')
              .append(tab)
              .append("Min    : ").append(data.getMin()).append('\n')
              .append(tab)
              .append("Max    : ").append(data.getMax()).append('\n')
              .append(tab)
              .append("Average: ").append(data.getAverage()).append('\n')
              .append(tab)
              .append("Median : ").append(data.getMedian()).append('\n')
              ;
        return stream.toString();
    }

    private PerformanceDataBuilder() {
    }
}
