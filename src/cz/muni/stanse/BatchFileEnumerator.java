package cz.muni.stanse;

import cz.muni.stanse.utils.ClassLogger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public final class BatchFileEnumerator
                                   extends ReferencedSourceCodeFilesEnumerator {

    // public section

    public BatchFileEnumerator(final String file) {
        super(file);
    }

    @Override
    public List<String> getSourceCodeFiles() throws Exception {
        final List<String> result = new LinkedList<String>();
        try {
            final BufferedReader reader = new BufferedReader(
                new FileReader(getReferenceFile()));
            while (true) {
                String readLine = reader.readLine();
                if (readLine == null)
                    break;
                readLine = readLine.trim();
                if (readLine.length() > 0)
                    result.add(readLine);
            }
            reader.close();
        } catch (final IOException exception) {
            ClassLogger.error(this,"Cannot read files in batch file '" +
                                    getReferenceFile() + "'. See exception " +
                                   "trace for details:");
            ClassLogger.error(this,exception);
            ClassLogger.error(this,exception.getStackTrace());
        }
        return result;
    }
}
