package cz.muni.stanse.pointeranalyzer.steensgaard;

/**
 *
 * @author Michal Strehovsky
 */
public final class LocationPointerType implements PointerType {
    
    // tau
    private EquivalenceClass<LocationPointerType> tau;
    
    // lambda
    private EquivalenceClass<FunctionPointerType> lambda;
    
    public LocationPointerType(
            EquivalenceClass<LocationPointerType> tau,
            EquivalenceClass<FunctionPointerType> lambda)
    {
        this.tau = tau;
        this.lambda = lambda;

        if (tau != null)
            tau.notifyPointedFrom(this);

        if (lambda != null)
            lambda.notifyPointedFrom(this);
    }

    public EquivalenceClass<LocationPointerType> getTau()
    {
        return tau;
    }

    public EquivalenceClass<FunctionPointerType> getLambda()
    {
        return lambda;
    }

    public void notifyEquivalenceClassJoined(EquivalenceClass oldClass, EquivalenceClass newClass)
    {
        if (oldClass == tau)
        {
            tau = newClass;
        }
        else if (oldClass == lambda)
        {
            lambda = newClass;
        }
    }

     public void unifyWith(PointerType that)
     {
         LocationPointerType t2 = (LocationPointerType)that;

         // BUGBUG: wonder why the order has to be like this?
         //refLocation.joinWith(t2.refLocation);
         //refFunction.joinWith(t2.refFunction);
         t2.tau.joinWith(tau);
         t2.lambda.joinWith(lambda);
     }
}
