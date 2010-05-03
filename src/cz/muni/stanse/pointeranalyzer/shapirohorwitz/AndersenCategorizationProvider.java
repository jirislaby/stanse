package cz.muni.stanse.pointeranalyzer.shapirohorwitz;

/**
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
