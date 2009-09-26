package cz.muni.stanse.statistics;

import java.util.Vector;
import java.util.LinkedList;
import java.util.Arrays;

import java.io.File;
import java.io.FileFilter;

import org.dom4j.Document;
import org.dom4j.Element;

public final class MergeDocuments {

    // public section

    public static void run(final Document database, final String dirsRoot,
                           final String outFile) {
        System.out.print("Merging of statistics database documents\n"+
                         "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");

        System.out.println("(1/3) Collecting directory structure files...");
        final Vector<File> xmlFiles = collectXmlFilesFromDirStructure(dirsRoot);
        System.out.println("      Done.");

        System.out.println("(1/3) Merging reports form collected files...");
        final Vector<Element> result = mergeReportsFromFiles(xmlFiles);
        System.out.println("      Done.");

        System.out.println("(3/3) Writting merge result into output file...");
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

    private static Vector<File>
    collectXmlFilesFromDirStructure(final String rootDir) {
        final Vector<File> result = new Vector<File>();
        final LinkedList<File> progressQueue = new LinkedList<File>();
        progressQueue.add(new File(rootDir));
        do {
            final File curDir = progressQueue.poll();
            progressQueue.addAll(
                Arrays.asList(curDir.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return file.isDirectory();
                    }
                })));
            result.addAll(
                Arrays.asList(curDir.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        return !file.isDirectory() &&
                               file.toString().toLowerCase().endsWith(".xml");
                    }
                })));
        } while (!progressQueue.isEmpty());
        return result;
    }

    private static Vector<Element>
    mergeReportsFromFiles(final Vector<File> xmlFiles) {
        final Vector<Element> result = new Vector<Element>();
        for (final File file : xmlFiles)
            result.addAll(
                StatisticalDatabaseLoader.run(file)
                                         .selectNodes("database/errors/error"));
        return result;
    }

    private MergeDocuments() {
    }
}
