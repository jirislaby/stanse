package cz.muni.stanse.gui;

import cz.muni.stanse.utils.ClassLogger;

final class SourceFilesListEnumerator
                                   extends ReferencedSourceCodeFilesEnumerator {
    SourceFilesListEnumerator(final String file) {
        super(file);
    }

    @Override
    java.util.List<String> getSourceCodeFiles() throws Exception {
        final java.util.LinkedList<String> result =
                                             new java.util.LinkedList<String>();
        try {
            final java.io.BufferedReader reader =
                        new java.io.BufferedReader(new java.io.FileReader(
                                                           getReferenceFile()));
            String readedLine;
            while ((readedLine = reader.readLine()) != null)
                if (readedLine.toUpperCase().lastIndexOf(".C") != -1)
                    result.add(readedLine);
            reader.close();
        }
        catch (final java.io.IOException exception) {
            ClassLogger.error(this,"Cannot read files in batch file '" +
                                    getReferenceFile() + "'. See exception " +
                                   "trace for details:");
            ClassLogger.error(this,exception);
            ClassLogger.error(this,exception.getStackTrace());
        }
        return result;
    }
}
