package cz.muni.stanse.pointeranalyzer.shapirohorwitz;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Collection;
import java.util.Map.Entry;


/**
 * Represents a single abstract location (equivanece class) used to
 * describe memory locations.
 *
 * Every class that stores references to AbstractLocation has to implement
 * the AbstractLocationJoinListener interface and call notifyPointedFrom method
 * on the AbstractLocation class to receive notifications about class merging.
 *
 * @author Michal Strehovsky
 */
public final class AbstractLocation implements AbstractLocationJoinListener {

    static boolean debugNames = false;

    /**
     * Type associated with this abstract location.
     */
    private PointerType type;

    /**
     * Pending joins that will be performed as soon as a type is associated
     * with this abstract location.
     */
    private HashSet<AbstractLocation> pendingJoins
            = new HashSet<AbstractLocation>();

    /**
     * Maintains a set of abstract locations being joined with this location.
     * Used to prevent recursive loops during joinWith operations.
     */
    private HashSet<AbstractLocation> joiningWith
            = new HashSet<AbstractLocation>();

    /**
     * Maintains a list of AbstractLocationJoinListeners interested in being
     * notified about equivalence class joins.
     */
    private HashMap<AbstractLocationJoinListener, Integer> pointedFrom
            = new HashMap<AbstractLocationJoinListener, Integer>();

    /**
     * Name of the abstract location. Used for debugging purposes only.
     */
    private String name;

    /**
     * Identifier of the category to which this abstract location belongs.
     */
    private int category;

    /**
     * Returns the type associated with this AbstractLocation.
     */
    public PointerType getType() {
        return type;
    }

    /**
     * Sets the type associated with this AbstractLocation.
     */
    public void setType(PointerType type) {
        assert this.type == null;

        this.type = type;

        // we now have a type, so lets process the joins waiting for it
        performPendingJoins();
    }

    /**
     * Returns the name of this AbstractLocation.
     */
    public String getName() {
        return name;
    }

    public int getCategory() {
        return category;
    }

    public AbstractLocation(int category, String name, PointerType type) {
        this.category = category;
        this.type = type;

        if (debugNames) {
            this.name = name;
        }
    }

    public AbstractLocation(int category, String name) {
        this(category, name, null);
    }

    private void performPendingJoins() {

        // It is essential to keep this semantic despite being ugly because
        // joinWith may cause the pendingJoins set to modify, invalidating the
        // iterator. The Java HashSet is just too damn stupid and has no other
        // means of accessing it's elements apart from the iterator.
        while (!pendingJoins.isEmpty()) {

            AbstractLocation current = pendingJoins.iterator().next();
            pendingJoins.remove(current);
            current.notifyPointerRemoved(this);

            joinWith(current);
        }
    }

    private void addPendingJoin(AbstractLocation with) {
        if (!pendingJoins.contains(with)) {
            pendingJoins.add(with);
            with.notifyPointedFrom(this);
        }
    }

    private void addPendingJoins(Collection<AbstractLocation> with) {
        for (AbstractLocation aloc: with) {
            addPendingJoin(aloc);
        }
    }

    public void conditionalJoinWith(AbstractLocation that) {
        // taken straight from Steensgaard's article

        if (that.getType() == null) {
            that.addPendingJoin(this);
        } else {
            this.joinWith(that);
        }
    }

    private void adjustPointedFromRefCount(AbstractLocationJoinListener what, int adjust) {

        assert what != this;
        assert adjust > 0 || pointedFrom.containsKey(what);

        int previousRefCount = 0;
        if (pointedFrom.containsKey(what)) {
            previousRefCount = pointedFrom.get(what);
        }

        int newRefCount = previousRefCount + adjust;

        assert newRefCount >= 0;

        if (newRefCount == 0) {
            pointedFrom.remove(what);
        } else {
            pointedFrom.put(what, newRefCount);
        }
    }

    public void notifyPointedFrom(AbstractLocationJoinListener what) {
        adjustPointedFromRefCount(what, 1);
    }

    public void notifyPointerRemoved(AbstractLocationJoinListener what) {
        adjustPointedFromRefCount(what, -1);
    }

    private void joinPointedFromSet(HashMap<AbstractLocationJoinListener, Integer> other) {
        for (Entry<AbstractLocationJoinListener, Integer> e: other.entrySet()) {
            adjustPointedFromRefCount(e.getKey(), e.getValue());
        }
    }

    public void joinWith(AbstractLocation that) {
        // taken straight from Steensgaard's article

        // prevent an odd situation when joining with self
        if (that == this) {
            return;
        }

        // avoid recursive calls and loops
        if (joiningWith.contains(that)) {
            return;
        } else {
            joiningWith.add(that);
        }

        if (this.type == null) {
            this.type = that.type;

            if (that.type == null) {
                addPendingJoins(that.pendingJoins);
            } else {
                performPendingJoins();
            }
        } else {
            if (that.type == null) {
                assert pendingJoins.isEmpty();
                pendingJoins = that.pendingJoins;
                performPendingJoins();
            } else {
                this.type.unifyWith(that.getType());
            }
        }

        for (AbstractLocationJoinListener listener: that.pointedFrom.keySet()) {
            listener.notifyAbstractLocationsJoined(that, this);
        }

        //System.out.println("Joined " + that.toString() + " to " + this.toString());

        if (debugNames) {
            this.name = this.name + "|" + that.getName();
        }

        joinPointedFromSet(that.pointedFrom);

        // join process ended, so remove the anti-loop flag
        joiningWith.remove(that);
    }

    public void notifyAbstractLocationsJoined(AbstractLocation oldClass, AbstractLocation newClass) {
        if (pendingJoins.contains(oldClass)) {
            pendingJoins.remove(oldClass);
            pendingJoins.add(newClass);
        }
        
        if (pointedFrom.containsKey(oldClass)) {
            int refCount = pointedFrom.get(oldClass);
            pointedFrom.remove(oldClass);
            adjustPointedFromRefCount(newClass, refCount);
        }

    }

    @Override
    public String toString() {
        if (debugNames) {
            return name;
        } else { return ""; }
    }

    public static AbstractLocation createVariable(CategorizationProvider catProvider, String name) {
        
        int category = catProvider.assignNextCategory();

        // creates the type "ref(bottom, bottom)"
        AbstractLocation result =
                new AbstractLocation(
                    category,
                    name,
                    // ref(
                    new LocationPointerType(
                        catProvider,
                        // bottom,
                        new AbstractLocation(
                            category,
                            name + ".tau",
                            new LocationPointerType(
                                catProvider,
                                new AbstractLocation(
                                    category,
                                    name + ".tau.tau"),
                                new AbstractLocation(
                                    category,
                                    name + ".tau.lambda"))),
                        // bottom
                        new AbstractLocation(
                            category,
                            name + ".lambda"))
                        // )
                    );

        return result;
    }
}
