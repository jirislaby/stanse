package cz.muni.stanse.gui;

final class MakefileSourceEnumerator extends
                                           ReferencedSourceCodeFilesEnumerator {
    MakefileSourceEnumerator(final String makeFile) {
        super(makeFile);
    }

    @Override
    java.util.List<String> getSourceCodeFiles() throws Exception {
        throw new Exception("MakefileSourceEnumerator is not implemented yet!");
    }
}
