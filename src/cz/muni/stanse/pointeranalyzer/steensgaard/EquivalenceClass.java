package cz.muni.stanse.pointeranalyzer.steensgaard;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Michal Strehovsky
 */
public final class EquivalenceClass {

    private PointerType type;

    private String name;

    private HashSet<EquivalenceClass> pendingJoins
            = new HashSet<EquivalenceClass>();

    private HashSet<EquivalenceClassJoinListener> pointedFrom
            = new HashSet<EquivalenceClassJoinListener>();

    static List<EquivalenceClassJoinListener> joinListener
            = new LinkedList<EquivalenceClassJoinListener>();

    public static void addJoinListener(EquivalenceClassJoinListener listener)
    {
        joinListener.add(listener);
    }

    public EquivalenceClass(String name, PointerType type)
    {
        this.type = type;
        this.name = name;
    }

    public EquivalenceClass(String name)
    {
        this(name, null);
    }

    public EquivalenceClass(PointerType type)
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

    public PointerType getType()
    {
        return type;
    }

    public void setType(PointerType type)
    {
        this.type = type;

        for (EquivalenceClass x: this.pendingJoins)
        {
            this.joinWith(x);
        }
    }

    public void conditionalJoinWith(EquivalenceClass that)
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

    public void joinWith(EquivalenceClass that)
    {
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
                for (EquivalenceClass x: that.pendingJoins)
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

        for (EquivalenceClassJoinListener l: joinListener)
        {
            l.notifyEquivalenceClassJoined(that, this);
        }

        java.lang.System.out.println("Joined " + that.toString() + " to " + this.toString());

        this.name = this.name + "|" + that.name;

        this.pointedFrom.addAll(that.pointedFrom);
    }

    @Override
    public String toString()
    {
        return name;
    }

    public static EquivalenceClass createRefBottomBottom(String name)
    {
        // creates the type "ref(bottom, bottom)"
        EquivalenceClass result =
                new EquivalenceClass(name,
                    new LocationPointerType(
                        new EquivalenceClass(name + ".tau",
                            new LocationPointerType(new EquivalenceClass(name + ".tau.tau"), new EquivalenceClass(name + ".tau.lambda"))
                        ), new EquivalenceClass(name + ".lambda"))
                    );

        return result;
    }

    public static EquivalenceClass createRefBottomBottom()
    {
        return createRefBottomBottom("");
    }
}
