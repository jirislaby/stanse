package cz.muni.stanse.gui;

import cz.muni.stanse.utils.FileAlgo;

final class DirectorySourceEnumerator extends
                                           ReferencedSourceCodeFilesEnumerator {

    // package-private section

    DirectorySourceEnumerator(final String startDirectory,
                              final String extension,
                              final boolean searchSubdirectories) {
        super(startDirectory);
        this.extensions = cz.muni.stanse.utils.Make.
                                            linkedList(extension.toUpperCase());
        this.searchSubdirectories = searchSubdirectories;
    }

    boolean getSearchSubdirectories() {
        return searchSubdirectories;
    }

    @Override
    java.util.List<String> getSourceCodeFiles() throws Exception {
        return toStringList(
                   FileAlgo.enumerateFiles(new java.io.File(getReferenceFile()),
                        new java.io.FileFilter() {
                        @Override public boolean accept(java.io.File file) {
                            return (file.isDirectory()) ?
                                        getSearchSubdirectories() :
                                        getExtensions().contains(
                                            FileAlgo.getExtension(
                                                file.toString().toUpperCase()));
                        }},getSearchSubdirectories()));
    }

    // private section

    private static java.util.List<String>
    toStringList(final java.util.Set<java.io.File> fileSet) {
        final java.util.LinkedList<String> result =
                                             new java.util.LinkedList<String>();
        for (java.io.File file : fileSet)
            result.add(file.toString());
        return result;
    }

    private java.util.LinkedList<String> getExtensions() {
        return extensions;
    }

    private final java.util.LinkedList<String> extensions;
    private final boolean searchSubdirectories;
}
