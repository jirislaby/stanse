package cz.muni.stanse.pointeranalyzer.shapirohorwitz;

/**
 * Represents a general pointer type.
 *
 * @author Michal Strehovsky
 */
public interface PointerType {
    public void unifyWith(PointerType that);
}
