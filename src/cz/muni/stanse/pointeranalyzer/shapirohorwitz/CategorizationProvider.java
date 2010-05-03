package cz.muni.stanse.pointeranalyzer.shapirohorwitz;

/**
 *
 * @author Michal Strehovsky
 */
public interface CategorizationProvider {

    public int assignNextCategory();

    public int getNumberOfCategories();

}
