
package cz.muni.stanse.threadchecker.graph;

/**
 * Class represents one Resource node in Resource Alloce graph
 * @author Jan Kučera
 */
public class ResourceVertex implements Vertex {
    private String name;
    private Integer nodeId;

    public ResourceVertex(String name, Integer nodeID) {
        this.name = name;
        this.nodeId = nodeID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Integer getNodeID() {
        return nodeId;
    }

    @Override
    public String toDot() {
        String resourceName = getName().replace("->", "_");
        resourceName = resourceName.replace("\\.", "_");
        return resourceName+" [label=\""+getName()+"\", shape = box];";
    }

    @Override
    public String toString() {
        return name;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ResourceVertex other = (ResourceVertex) obj;
        if (!this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    

}
