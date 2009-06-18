package cz.muni.stanse.gui;

import java.util.LinkedList;
import java.util.List;

final class FileChooserFileFilter extends javax.swing.filechooser.FileFilter{

    // public section

    @Override
    public String getDescription() {
        return getDesctiption();
    }

    @Override
    public boolean accept(java.io.File file) {
        return file.isDirectory() ? true :
                         getExtensions().contains(cz.muni.stanse.utils.FileAlgo.
                                    getExtension(file.getName()).toUpperCase());
    }

    // package-private section

    FileChooserFileFilter(final String description,
                             final List<String> extensions) {
        this.extensions = createUppercaseExtensiosList(extensions);
        this.description = description;
    }

    FileChooserFileFilter(final String description,
                             final List<String> extensions,
                             final Object foo) {
        this.extensions = createUppercaseExtensiosList(extensions);
        this.description = createFinalDescription(description,getExtensions());
    }

    // private section

    private static List<String> createUppercaseExtensiosList(
                                          final List<String> extensions) {
        final List<String> result = new LinkedList<String>();
        for (String extension : extensions)
            result.add(extension.toUpperCase());
        return result;
    }

    private static String createFinalDescription(final String description,
                                          final List<String> extensions) {
        String result = description + ": ";
        boolean first = true;
        for (String extension : extensions) {
            result = result + ((first) ? "" : ", ") + extension.toUpperCase();
            first = false;
        }
        return result;
    }

    private String getDesctiption() {
        return description;
    }

    private List<String> getExtensions() {
        return extensions;
    }

    private final List<String> extensions;
    private final String description;
}
