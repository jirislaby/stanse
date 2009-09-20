package cz.muni.stanse.statistics;

public final class StringToFileWriter {

    // public section

    public static boolean write(final String data, final String fileName) {
        try {
            final java.io.BufferedWriter file =
                new java.io.BufferedWriter(new java.io.FileWriter(fileName));
            file.write(data);
            file.close();
        } catch (java.io.IOException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }

    // private section

    private StringToFileWriter() {
    }
}
