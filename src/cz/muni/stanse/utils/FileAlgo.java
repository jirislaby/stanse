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

    // private section

    private FileAlgo() {
    }
}
