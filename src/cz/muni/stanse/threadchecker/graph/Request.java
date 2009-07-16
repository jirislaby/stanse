
package cz.muni.stanse.threadchecker.graph;

/**
 * Class represents request edge in Resource Allocate graph
 * @author Jan Kučera
 */
public class Request implements Edge {
    private final ProcessVertex process;
    private final ResourceVertex resource;

    public Request(Vertex process, Vertex resource) {
        if(resource instanceof ResourceVertex
                && process instanceof ProcessVertex) {
            this.process = (ProcessVertex) process;
            this.resource = (ResourceVertex) resource;
        } else {
            throw new IllegalArgumentException("Wrong assignment parameters");
        }
    }

    @Override
    public ProcessVertex getProcess() {
        return process;
    }

    @Override
    public ResourceVertex getResource() {
        return resource;
    }

        @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Request other = (Request) obj;
        if (this.process != other.process && (this.process == null
                                    || !this.process.equals(other.process))) {
            return false;
        }
        if (this.resource != other.resource && (this.resource == null
                                    || !this.resource.equals(other.resource))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = this.toString().hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return process.getName()+" -> "+resource.getName();
    }

    @Override
    public String toDot() {
        String resourceName = resource.getName().replace("->", "_");
        resourceName = resourceName.replace("\\.", "_");
        String processName = process.getName().replace("->", "_");
        processName = processName.replace("\\.", "_");
        return processName+" -> "+resourceName+";";
    }

}
