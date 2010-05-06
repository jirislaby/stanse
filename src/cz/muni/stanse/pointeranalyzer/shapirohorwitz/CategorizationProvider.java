package cz.muni.stanse.pointeranalyzer.shapirohorwitz;

/**
 * Provides variable categorization functionality for the analysis.
 * 
 * @author Michal Strehovsky
 */
public interface CategorizationProvider {

    public int assignNextCategory();

    public int getNumberOfCategories();

}
