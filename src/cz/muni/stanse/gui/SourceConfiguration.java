package cz.muni.stanse.gui;

import cz.muni.stanse.codestructures.Unit;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;
import java.util.Collections;
import java.io.File;

final class SourceConfiguration {

    // package-private section

    public enum SourceType {
        ActualOpenedFile(0),
        AllOpenedFiles(1),
        SingleSourceFile(2),
        MakefileProject(3);

        SourceType(int value) {
            this.value = value;
        }

        int getValue() {
            return value;
        }

        private final int value;
    }

    SourceConfiguration(final MutableSourceConfigurationAcessor
                                                         mutableSourceAcessor) {
        this.sourcePathName = null;
        this.sourceType = SourceType.ActualOpenedFile;
        this.mutableSourceAcessor = mutableSourceAcessor;
    }

    SourceConfiguration(final File sourcePathName,
                        final SourceType sourceType) {
        this.sourcePathName = sourcePathName;
        this.sourceType = sourceType;
        this.mutableSourceAcessor = null;
    }

    SourceConfiguration(final File sourcePathName,
                        final SourceType sourceType,
                        final MutableSourceConfigurationAcessor
                                                         mutableSourceAcessor) {
        this.sourcePathName = sourcePathName;
        this.sourceType = sourceType;
        this.mutableSourceAcessor = mutableSourceAcessor;
    }

    List<Unit> getUnits() throws Exception {
        final LinkedList<Unit> result = new LinkedList<Unit>();
        switch(getSourceType()) {
            case ActualOpenedFile:
                java.io.File activeFile = getMutableSourceAcessorSafe().
                                                              getActiveSource();
                if (activeFile != null)
                    result.add(fromSingleSourceFile(activeFile));
                break;
            case AllOpenedFiles:
                result.addAll(fromAllSourceFiles(
                              getMutableSourceAcessorSafe().getAllSources()));
                break;
            case SingleSourceFile:
                result.add(fromSingleSourceFile(getSourcePathNameSafe()));
                break;
            case MakefileProject:
                // TODO: not implemented yet!
                break;
            default:
                throw new EnumConstantNotPresentException(SourceType.class,
                                                        getSourceType().name());
        }
        return Collections.unmodifiableList(result);
    }

    SourceType getSourceType() {
        return sourceType;
    }

    File getSourcePathName() {
        return sourcePathName;
    }

    MutableSourceConfigurationAcessor getMutableSourceAcessor() {
        return mutableSourceAcessor;
    }

    // private section

    File getSourcePathNameSafe() throws Exception {
        assert(sourcePathName != null);
        if (sourcePathName == null)
            throw new Exception("Acessing source path-name, but " +
                                "it is 'null'.");
        return sourcePathName;
    }

    MutableSourceConfigurationAcessor getMutableSourceAcessorSafe()
                                                              throws Exception {
        assert(mutableSourceAcessor != null);
        if (mutableSourceAcessor == null)
            throw new Exception("Acessing mutable source acessor, but " +
                                "it is 'null'.");
        return mutableSourceAcessor;
    }

    private static Unit fromSingleSourceFile(final File sourcePathName)
                                                              throws Exception {
        return new cz.muni.stanse.cparser.CUnit(sourcePathName);
    }

    private static LinkedList<Unit>
    fromAllSourceFiles(final Set<File> sourcePathNames) throws Exception {
        final LinkedList<Unit> result = new LinkedList<Unit>();
        for (File pathName : sourcePathNames)
            result.add(new cz.muni.stanse.cparser.CUnit(pathName));
        return result;
    }

    private final File sourcePathName;
    private final SourceType sourceType;
    private final MutableSourceConfigurationAcessor mutableSourceAcessor;
}
