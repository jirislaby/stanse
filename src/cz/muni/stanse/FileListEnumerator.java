/**
 * 
 */
package cz.muni.stanse;
import java.util.List;

/**
 * @author xobdrzal
 *
 */
public class FileListEnumerator extends SourceCodeFilesEnumerator {
	private final List <String> fileNames;
	
	/**
	 * 
	 */
	public FileListEnumerator(final List<String> sources ) {
		super();
		this.fileNames=sources;
	}

    @Override
    public java.util.List<String> getSourceCodeFiles() throws Exception {
        return fileNames;
    }
}
