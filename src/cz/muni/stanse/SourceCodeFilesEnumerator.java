package cz.muni.stanse;

import java.util.List;

public class SourceCodeFilesEnumerator {

    public List<String> getSourceCodeFiles() throws SourceCodeFilesException {
        throw new SourceCodeFilesException("Cannot provide source code " +
			"files. None is available. Operation has FAILED!");
    }
}
