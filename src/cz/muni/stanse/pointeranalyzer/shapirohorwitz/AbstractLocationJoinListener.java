package cz.muni.stanse.pointeranalyzer.shapirohorwitz;

/**
 * Represents the ability of implementing class to listen to
 * join operations on abstract locations.
 *
 * @author Michal
 */
public interface AbstractLocationJoinListener {

    /**
     * Called when the abstract location this class points to has been
     * joined with a new class. Implementers should replace all stored references
     * to oldClass by newClass.
     *
     * @param oldClass
     * @param newClass
     */
    public void notifyAbstractLocationsJoined(AbstractLocation oldClass, AbstractLocation newClass);
}
