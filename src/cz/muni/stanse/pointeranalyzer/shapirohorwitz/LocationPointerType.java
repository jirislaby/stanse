package cz.muni.stanse.pointeranalyzer.shapirohorwitz;

/**
 *
 * @author Michal Strehovsky
 */
public class LocationPointerType implements PointerType, AbstractLocationJoinListener {

    private AbstractLocationSet tau;
    
    private AbstractLocation lambda;

    private CategorizationProvider catProvider;
    
    public LocationPointerType(CategorizationProvider catProvider,
            AbstractLocation tau, AbstractLocation lambda) {
        this.tau = new AbstractLocationSet(catProvider, tau);
        this.lambda = lambda;
        this.catProvider = catProvider;

        lambda.notifyPointedFrom(this);
    }

    public LocationPointerType(CategorizationProvider catProvider) {
        this.tau = new AbstractLocationSet(catProvider);
        this.lambda = new AbstractLocation(0, "");
        this.catProvider = catProvider;

        lambda.notifyPointedFrom(this);
    }

    public AbstractLocationSet getTau() {
        return tau;
    }

    public AbstractLocation getLambda() {
        return lambda;
    }

    public LocationPointerType dereference() {

        AbstractLocationSet newTau = null;
        AbstractLocation newLambda = null;

        for (int i = 0; i < catProvider.getNumberOfCategories(); i++) {

            AbstractLocation current = tau.get(i);
            LocationPointerType currentType = (LocationPointerType)current.getType();

            if (currentType == null) {
                // make up a type if there is no type yet
                currentType = new LocationPointerType(catProvider);
                current.setType(currentType);
            }

            if (i == 0) {
                newTau = currentType.getTau();
                newLambda = currentType.getLambda();
            } else {
                newTau.joinWith(currentType.getTau());
                newLambda.joinWith(currentType.getLambda());
            }
        }

        return (LocationPointerType)tau.get(0).getType();
    }

    public void unifyWith(PointerType that) {

        assert that instanceof LocationPointerType;

        LocationPointerType t2 = (LocationPointerType)that;

        t2.tau.joinWith(tau);
        t2.lambda.joinWith(lambda);
     }

    public void notifyAbstractLocationsJoined(AbstractLocation oldClass, AbstractLocation newClass) {
        if (lambda == oldClass) {
            lambda = newClass;
        }
    }
}
