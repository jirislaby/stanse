package cz.muni.stanse;

import cz.muni.stanse.utils.Make;

import java.util.List;

public final class SingleFileEnumerator
                                   extends ReferencedSourceCodeFilesEnumerator {
    public SingleFileEnumerator(final String file) {
        super(file);
    }
    @Override
    public List<String> getSourceCodeFiles() throws SourceCodeFilesException {
        return Make.linkedList(getReferenceFile());
    }
}
