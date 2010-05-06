package cz.muni.stanse.pointeranalyzer.shapirohorwitz;

/**
 * Represents the categorization provider for Andersen's analysis.
 *
 * @author Michal Strehovsky
 */
public class AndersenCategorizationProvider implements CategorizationProvider {

    private int numberOfCategories;

    private int currentCategory = -1;

    public AndersenCategorizationProvider(int numberOfProgramVariables) {
        numberOfCategories = numberOfProgramVariables;
    }

    public int assignNextCategory() {

        currentCategory++;

        assert currentCategory < numberOfCategories;

        return currentCategory;
    }

    public int getNumberOfCategories() {
        return numberOfCategories;
    }

}
