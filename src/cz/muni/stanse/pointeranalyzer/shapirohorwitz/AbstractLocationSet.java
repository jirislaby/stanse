package cz.muni.stanse.pointeranalyzer.shapirohorwitz;

import java.util.Iterator;

/**
 * Represents a set of k abstract location, where k is the number of
 * categories.
 *
 * @author Michal Strehovsky
 */
public class AbstractLocationSet implements Iterable<AbstractLocation>, AbstractLocationJoinListener {

    /**
     * Array backing the set of abstract location. Array index represents the
     * AbstractLocation's category.
     */
    private AbstractLocation[] locations;

    /**
     * Gets i'th AbstractLocation in the set.
     */
    public AbstractLocation get(int i) {
        assert i < locations.length;

        if (locations[i] == null) {
            locations[i] = new AbstractLocation(i, "backPtr");
        }

        return locations[i];
    }

    public AbstractLocationSet(CategorizationProvider provider, AbstractLocation... initList) {
        locations = new AbstractLocation[provider.getNumberOfCategories()];

        for (AbstractLocation aloc: initList) {
            assert aloc.getCategory() < provider.getNumberOfCategories();
            assert locations[aloc.getCategory()] == null;

            locations[aloc.getCategory()] = aloc;
            aloc.notifyPointedFrom(this);
        }
    }

    /**
     * Joins the specified abstract location to the set.
     * If AbstractLocation having the same category is already contained,
     * it will join it with specified abstract location.
     */
    public void joinWith(AbstractLocation that) {

        if (locations[that.getCategory()] != that) {

            if (locations[that.getCategory()] == null) {
                locations[that.getCategory()] = that;
                that.notifyPointedFrom(this);
            } else {
                locations[that.getCategory()].joinWith(that);
            }

        }
    }

    /**
     * Joins this AbstractLocationSet with another.
     */
    public void joinWith(AbstractLocationSet that) {

        for (int i = 0; i < locations.length; i++) {

            if (this.locations[i] == null && that.locations[i] == null) {
                AbstractLocation aloc = new AbstractLocation(i, "backPtr");
                this.locations[i] = aloc;
                that.locations[i] = aloc;
                aloc.notifyPointedFrom(this);
                aloc.notifyPointedFrom(that);
            } else {
                if (this.locations[i] != that.locations[i]) {
                    
                    if (this.locations[i] == null) {
                        this.locations[i] = that.locations[i];
                        that.locations[i].notifyPointedFrom(this);
                    } else if (that.locations[i] == null) {
                        that.locations[i] = this.locations[i];
                        this.locations[i].notifyPointedFrom(that);
                    } else {
                        this.locations[i].joinWith(that.locations[i]);
                    }

                }
            }
        }
    }

    public Iterator<AbstractLocation> iterator() {
        return java.util.Arrays.asList(locations).iterator();
    }

    public void notifyAbstractLocationsJoined(AbstractLocation oldClass, AbstractLocation newClass) {

        for (int i = 0; i < locations.length; i++) {
            if (locations[i] == oldClass) {
                locations[i] = newClass;
            }
        }

    }
}
