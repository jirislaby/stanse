package cz.muni.stanse.configuration.source_enumeration;

import cz.muni.stanse.utils.Make;

import java.util.List;

public final class SingleFileEnumerator
                                   extends ReferencedSourceCodeFileEnumerator {
    public SingleFileEnumerator(final String file) {
        super(file);
    }
    @Override
    public List<String> getSourceCodeFiles() throws SourceCodeFilesException {
        return Make.linkedList(getReferenceFile());
    }
}
