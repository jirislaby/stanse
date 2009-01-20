package cz.muni.stanse.gui;

final class SingleFileEnumerator extends ReferencedSourceCodeFilesEnumerator {
    SingleFileEnumerator(final String file) {
        super(file);
    }
    @Override
    java.util.List<String> getSourceCodeFiles() throws Exception {
        return cz.muni.stanse.utils.Make.linkedList(getReferenceFile());
    }
}
