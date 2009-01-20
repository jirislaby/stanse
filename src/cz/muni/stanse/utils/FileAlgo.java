package cz.muni.stanse.utils;

import java.io.File;

public class FileAlgo {

    // public section

    public static String getExtension(final File file) {
        return getExtension(file.getName());
    }

    public static String getExtension(final String fileName) {
        final int idx = fileName.lastIndexOf(".");
        return (idx > 0 &&  idx < fileName.length() - 1) ?
                     fileName.substring(idx+1) : fileName;
    }

    public static java.util.Set<File> enumerateFiles(final File startDirectory,
                                                final java.io.FileFilter filter,
                                                final boolean recursive) {
        final java.util.HashSet<File> result = new java.util.HashSet<File>();
        for (File file : startDirectory.listFiles(filter))
            if (file.isDirectory())
                result.addAll(enumerateFiles(file,filter,recursive));
            else
                result.add(file);
        return result;
    }

    // private section

    private FileAlgo() {
    }
}
