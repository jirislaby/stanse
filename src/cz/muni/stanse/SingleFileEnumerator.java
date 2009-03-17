package cz.muni.stanse;

public final class SingleFileEnumerator
                                   extends ReferencedSourceCodeFilesEnumerator {
    public SingleFileEnumerator(final String file) {
        super(file);
    }
    @Override
    public java.util.List<String> getSourceCodeFiles() throws Exception {
        return cz.muni.stanse.utils.Make.linkedList(getReferenceFile());
    }
}
