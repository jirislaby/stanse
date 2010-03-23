package cz.muni.stanse.pointeranalyzer.steensgaard;

import java.util.Arrays;
import java.util.List;


/**
 *
 * @author Michal Strehovsky
 */
public final class FunctionPointerType implements PointerType {

    private EquivalenceClass returnType;

    private EquivalenceClass[] parameterTypes;

    public FunctionPointerType(EquivalenceClass[] parameterTypes, EquivalenceClass returnType)
    {
        this.returnType = returnType;
        this.parameterTypes = parameterTypes;

        returnType.notifyPointedFrom(this);
        for (EquivalenceClass parameterType: parameterTypes)
        {
            parameterType.notifyPointedFrom(this);
        }
    }

    public List<EquivalenceClass> getParameterTypes()
    {
        return Arrays.asList(parameterTypes);
    }

    public EquivalenceClass getReturnType()
    {
        return returnType;
    }

    public void notifyEquivalenceClassJoined(EquivalenceClass oldClass, EquivalenceClass newClass)
    {
        if (returnType == oldClass)
        {
            returnType = newClass;
        }

        for (int i = 0; i < parameterTypes.length; i++)
        {
            if (parameterTypes[i] == oldClass)
            {
                parameterTypes[i] = newClass;
            }
        }
    }

     public void unifyWith(PointerType that)
     {
         FunctionPointerType t2 = (FunctionPointerType)that;

         returnType.joinWith(t2.returnType);

         for (int i = 0; i < parameterTypes.length; i++)
         {
             parameterTypes[i].joinWith(t2.parameterTypes[i]);
         }

     }
}
