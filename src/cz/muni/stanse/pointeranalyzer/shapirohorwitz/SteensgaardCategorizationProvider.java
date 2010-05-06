package cz.muni.stanse.pointeranalyzer.shapirohorwitz;

/**
 * Represents the categorization provider for Steensgaards's analysis.
 *
 * @author Michal Strehovsky
 */
public class SteensgaardCategorizationProvider implements CategorizationProvider {

    public int assignNextCategory() {
        return 0;
    }

    public int getNumberOfCategories() {
        return 1;
    }

}
