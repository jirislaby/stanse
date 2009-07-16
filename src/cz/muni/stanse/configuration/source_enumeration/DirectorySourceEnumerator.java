package cz.muni.stanse.configuration.source_enumeration;

import cz.muni.stanse.utils.FileAlgo;
import cz.muni.stanse.utils.Make;

import java.io.File;
import java.io.FileFilter;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public final class DirectorySourceEnumerator extends
                                           ReferencedSourceCodeFileEnumerator {

    // public section

    public DirectorySourceEnumerator(final String startDirectory,
                              final String extension,
                              final boolean searchSubdirectories) {
        super(startDirectory);
        this.extensions = Make.linkedList(extension.toUpperCase());
        this.searchSubdirectories = searchSubdirectories;
    }

    public boolean getSearchSubdirectories() {
        return searchSubdirectories;
    }

    @Override
    public List<String> getSourceCodeFiles() throws SourceCodeFilesException {
        return toStringList(
                   FileAlgo.enumerateFiles(new File(getReferenceFile()),
                        new FileFilter() {
                        @Override public boolean accept(File file) {
                            return (file.isDirectory()) ?
                                        getSearchSubdirectories() :
                                        getExtensions().contains(
                                            FileAlgo.getExtension(
                                                file.toString().toUpperCase()));
                        }}, getSearchSubdirectories()));
    }

    // private section

    private static List<String> toStringList(final Set<File> fileSet) {
        final List<String> result = new LinkedList<String>();
        for (File file : fileSet)
            result.add(file.toString());
        return result;
    }

    private List<String> getExtensions() {
        return extensions;
    }

    private final List<String> extensions;
    private final boolean searchSubdirectories;
}
