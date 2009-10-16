package cz.muni.stanse.configuration.source_enumeration;

import cz.muni.stanse.utils.ClassLogger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public final class BatchFileEnumerator
                                   extends ReferencedSourceCodeFileEnumerator {

    // public section

    public BatchFileEnumerator(final String file) {
        super(file);
    }

    @Override
    public List<String> getSourceCodeFiles() throws SourceCodeFilesException {
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
	} catch (final FileNotFoundException e) {
	    ClassLogger.error(this, "Cannot open batch file '" +
			    getReferenceFile() + "'.");
	    /* jsut return empty list */
	} catch (final IOException e) {
	    ClassLogger.error(this, "Cannot read files in batch file '" +
				    getReferenceFile() + "'. See exception " +
				   "trace for details:", e);
        }
        return result;
    }
}
