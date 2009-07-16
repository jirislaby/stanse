
package cz.muni.stanse.threadchecker.graph;

/**
 * Class represents one Process node in Resource Alloce graph
 * @author Jan Kučera
 */
public class ProcessVertex implements Vertex {
    private String name;

    public ProcessVertex(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toDot() {
        String procesName = getName().replace("->", "_");
        procesName = procesName.replace("\\.", "_");
        return procesName+" [label=\""+getName()+"\"shape = circle];";
    }

    @Override
    public String toString() {
        return this.name;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProcessVertex other = (ProcessVertex) obj;
        if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = this.name.hashCode();
        return hash;
    }

}
