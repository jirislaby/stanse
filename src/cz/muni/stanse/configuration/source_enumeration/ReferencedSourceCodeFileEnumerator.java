package cz.muni.stanse.configuration.source_enumeration;

public class ReferencedSourceCodeFileEnumerator
                                             extends SourceCodeFilesEnumerator {
    // public section

    public ReferencedSourceCodeFileEnumerator(final String file) {
        super();
        this.file = file;
    }

    public final String getReferenceFile() {
        return file;
    }

    // private section

    private final String file;
}
