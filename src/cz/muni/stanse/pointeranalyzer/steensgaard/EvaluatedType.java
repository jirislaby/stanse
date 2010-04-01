package cz.muni.stanse.pointeranalyzer.steensgaard;

/**
 * This class is used to pass type information when evaluating expression types.
 *
 * @author Michal Strehovsky
 */
public class EvaluatedType implements EquivalenceClassJoinListener {

    private EquivalenceClass<LocationPointerType> tau;

    private EquivalenceClass<FunctionPointerType> lambda;

    // if true, do a settype(this.tau, ref(lhs.tau, lhs.lambda))
    // generally, this will be true if the previous operation was a dereference
    // and there was no type associated with the dereferenced value
    private boolean isDereference;

    public EquivalenceClass<LocationPointerType> getTau()
    {
        return tau;
    }

    public EquivalenceClass<FunctionPointerType> getLambda()
    {
        return lambda;
    }

    public EvaluatedType dereference()
    {
        // is this already a dereference?
        if (!isDereference)
        {
            // can we dereference it already?
            if (tau.getType() != null)
            {
                EquivalenceClass derefTau = tau.getType().getTau();
                EquivalenceClass derefLambda = tau.getType().getLambda();
                return new EvaluatedType(derefTau, derefLambda);
            }
            else
            {
                // no type associated with tau yet, so let's defer the dereference
                return new EvaluatedType(tau, null, true);
            }
        }
        else
        {
            throw new UnsupportedOperationException("bah");
        }
    }

    private EvaluatedType(EquivalenceClass<LocationPointerType> tau,
            EquivalenceClass<FunctionPointerType> lambda, boolean isDereference)
    {
        this.tau = tau;
        this.tau.notifyPointedFrom(this);

        if (isDereference)
        {
            this.isDereference = true;
        }
        else
        {
            this.lambda = lambda;
            this.lambda.notifyPointedFrom(this);
            this.isDereference = false;
        }
    }

    public EvaluatedType(EquivalenceClass tau, EquivalenceClass lambda)
    {
        this(tau, lambda, false);
    }


    public void join(EvaluatedType other)
    {
        if (!isDereference)
        {
            if (!other.isDereference)
            {
                tau.conditionalJoinWith(other.tau);
                lambda.conditionalJoinWith(other.lambda);
            }
            else
            {
                assert other.tau.getType() == null;
                other.tau.setType(new LocationPointerType(tau, lambda));
            }
        }
        else
        {
            if (!other.isDereference)
            {
                assert tau.getType() == null;
                tau.setType(new LocationPointerType(other.tau, other.lambda));

                isDereference = false;
                tau = other.tau;
                tau.notifyPointedFrom(this);
                lambda = other.lambda;
                lambda.notifyPointedFrom(this);
            }
            else
            {
                tau.conditionalJoinWith(other.tau);
            }
        }
    }

    public void notifyEquivalenceClassJoined(EquivalenceClass oldClass, EquivalenceClass newClass)
    {
        if (tau == oldClass)
        {
            tau = newClass;
        }

        if (lambda == oldClass)
        {
            lambda = newClass;
        }
    }
}
