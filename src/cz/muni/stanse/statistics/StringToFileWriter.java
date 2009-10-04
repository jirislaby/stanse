package cz.muni.stanse.statistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class StringToFileWriter {

    // public section

    public static boolean write(final String data, final String fileName) {
        final File pathName = new File(fileName);
        if (pathName.getParentFile() != null)
            pathName.getParentFile().mkdirs();
        try {
            final BufferedWriter file = new BufferedWriter(
		    new FileWriter(fileName));
            file.write(data);
            file.close();
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    // private section

    private StringToFileWriter() {
    }
}
