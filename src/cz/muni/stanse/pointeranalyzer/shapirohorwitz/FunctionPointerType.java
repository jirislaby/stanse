package cz.muni.stanse.pointeranalyzer.shapirohorwitz;

import java.util.List;

/**
 * Represents a type for functions.
 *
 * @author Michal Strehovsky
 */
public class FunctionPointerType implements PointerType, AbstractLocationJoinListener {

    /**
     * List of abstract locations representing function parameters.
     */
    List<AbstractLocation> parameters;

    /**
     * Abstract location representing the return value.
     */
    AbstractLocation returnParameter;

    public FunctionPointerType(List<AbstractLocation> parameters, AbstractLocation returnParameter) {
        this.parameters = parameters;
        this.returnParameter = returnParameter;

        for (AbstractLocation aloc: parameters) {
            aloc.notifyPointedFrom(this);
        }

        returnParameter.notifyPointedFrom(this);
    }

    /**
     * Unifies this type with another FunctionPointerType.
     */
    public void unifyWith(PointerType that) {

        assert that instanceof FunctionPointerType;

        // unify the return value
        returnParameter.joinWith(((FunctionPointerType)that).returnParameter);
        
        // TODO: varargs support
        int paramCount = Math.min(((FunctionPointerType)that).parameters.size(), parameters.size());

        // unify the parameters
        for (int i = 0; i < paramCount; i++) {
            parameters.get(i).joinWith(((FunctionPointerType)that).parameters.get(i));
        }
       
    }

    public void notifyAbstractLocationsJoined(AbstractLocation oldClass, AbstractLocation newClass) {

        for (int i = 0; i < parameters.size(); i++) {
            if (parameters.get(i) == oldClass) {
                parameters.set(i, newClass);
            }
        }

        if (returnParameter == oldClass) {
            returnParameter = newClass;
        }

    }

    /*public static FunctionPointerType createEmpty(CategorizationProvider catProvider, int parameterCount) {

        List<AbstractLocationSet> parameters = new ArrayList<AbstractLocationSet>(parameterCount);
        for (int i = 0; i < parameterCount; i++) {
            parameters.add(new AbstractLocationSet(catProvider));
        }

        return new FunctionPointerType(parameters, new AbstractLocationSet(catProvider));
    }

    public static LocationPointerType extractReturnParameter(AbstractLocationSet alocSet, CategorizationProvider provider) {

        LocationPointerType result = null;

        for (AbstractLocation aloc: alocSet) {

            FunctionPointerType alocType = (FunctionPointerType)aloc.getType();
            if (alocType == null) {
                alocType = createEmpty(provider, 0);
                aloc.setType(alocType);
            }

            if (result == null) {
                result = alocType.returnParameter;
            }

        }

    }*/

}
