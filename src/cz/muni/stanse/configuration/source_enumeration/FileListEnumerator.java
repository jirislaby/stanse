package cz.muni.stanse.configuration.source_enumeration;

import java.util.List;

/**
 * @author xobdrzal
 *
 */
public class FileListEnumerator extends SourceCodeFilesEnumerator {
	private final List<String> fileNames;
	
	public FileListEnumerator(final List<String> sources) {
		super();
		this.fileNames = sources;
	}

    @Override
    public List<String> getSourceCodeFiles() throws SourceCodeFilesException {
        return fileNames;
    }
}
