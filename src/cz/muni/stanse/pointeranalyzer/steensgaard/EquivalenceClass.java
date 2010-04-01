package cz.muni.stanse.pointeranalyzer.steensgaard;

import java.util.HashSet;

/**
 *
 * @author Michal Strehovsky
 */
public final class EquivalenceClass<T extends PointerType> {

    private T type;

    private String name;

    private HashSet<EquivalenceClass<T>> pendingJoins
            = new HashSet<EquivalenceClass<T>>();

    private HashSet<EquivalenceClassJoinListener> pointedFrom
            = new HashSet<EquivalenceClassJoinListener>();

    // used during the joinWith operations to prevent loops
    private HashSet<EquivalenceClass<T>> joiningWith
            = new HashSet<EquivalenceClass<T>>();

    public EquivalenceClass(String name, T type)
    {
        this.type = type;
        this.name = name;
    }

    public EquivalenceClass(String name)
    {
        this(name, null);
    }

    public EquivalenceClass(T type)
    {
        this("", type);
    }

    public EquivalenceClass()
    {
        this("", null);
    }

    public boolean notifyPointedFrom(EquivalenceClassJoinListener p)
    {
        return pointedFrom.add(p);
    }

    public T getType()
    {
        return type;
    }

    public void setType(T type)
    {
        this.type = type;

        for (EquivalenceClass<T> x: this.pendingJoins)
        {
            this.joinWith(x);
        }
    }

    public void conditionalJoinWith(EquivalenceClass<T> that)
    {
        if (that.type == null)
        {
            that.pendingJoins.add(this);
        } 
        else
        {
            this.joinWith(that);
        }
    }

    public void joinWith(EquivalenceClass<T> that)
    {
        // do nothing if joining with itself
        if (this == that) {
            return;
        }

        // avoid recursive calls and loops
        if (joiningWith.contains(that)) {
            return;
        } else {
            joiningWith.add(that);
        }

        if (this.type == null)
        {
            this.type = that.type;

            if (that.type == null)
            {
                this.pendingJoins.addAll(that.pendingJoins);
            }
            else
            {
                for (EquivalenceClass x: this.pendingJoins)
                {
                    this.joinWith(x);
                }
            }
        }
        else
        {
            if (that.type == null)
            {
                for (EquivalenceClass<T> x: that.pendingJoins)
                {
                    this.joinWith(x);
                }
            }
            else
            {
                this.type.unifyWith(that.type);
            }
        }

        for (EquivalenceClassJoinListener p: that.pointedFrom)
        {
            p.notifyEquivalenceClassJoined(that, this);
        }

        //System.out.println("Joined " + that.toString() + " to " + this.toString());

        this.name = this.name + "|" + that.name;

        this.pointedFrom.addAll(that.pointedFrom);

        // join process ended, so remove the anti-loop flag
        joiningWith.remove(that);
    }

    @Override
    public String toString()
    {
        return name;
    }

    public static EquivalenceClass<LocationPointerType> createRefBottomBottom(String name)
    {
        // creates the type "ref(bottom, bottom)"
        EquivalenceClass<LocationPointerType> result =
                new EquivalenceClass<LocationPointerType>(name,
                    new LocationPointerType(
                        new EquivalenceClass<LocationPointerType>(name + ".tau",
                            new LocationPointerType(
                                new EquivalenceClass<LocationPointerType>(name + ".tau.tau"),
                                new EquivalenceClass<FunctionPointerType>(name + ".tau.lambda"))),
                        new EquivalenceClass<FunctionPointerType>(name + ".lambda"))
                    );

        return result;
    }

    public static EquivalenceClass<LocationPointerType> createRefBottomBottom()
    {
        return createRefBottomBottom("");
    }
}
