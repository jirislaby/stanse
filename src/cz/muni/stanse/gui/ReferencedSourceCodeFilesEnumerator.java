package cz.muni.stanse.gui;

class ReferencedSourceCodeFilesEnumerator extends SourceCodeFilesEnumerator {
    ReferencedSourceCodeFilesEnumerator(final String file) {
        super();
        this.file = file;
    }

    final String getReferenceFile() {
        return file;
    }

    private final String file;
}
