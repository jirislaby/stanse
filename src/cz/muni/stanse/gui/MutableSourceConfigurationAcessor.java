package cz.muni.stanse.gui;

abstract class MutableSourceConfigurationAcessor {

    // package-private section

    abstract java.io.File getActiveSource();
    abstract java.util.Set<java.io.File> getAllSources();
}
