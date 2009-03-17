package cz.muni.stanse;

public class ReferencedSourceCodeFilesEnumerator
                                             extends SourceCodeFilesEnumerator {
    // public section

    public ReferencedSourceCodeFilesEnumerator(final String file) {
        super();
        this.file = file;
    }

    public final String getReferenceFile() {
        return file;
    }

    // private section

    private final String file;
}
