package cz.muni.stanse.threadchecker.graph;

/**
 * Class represents assignment edge in Resource Allocate graph
 * @author Jan Kučera
 */
public class Assignment implements Edge {
    private final ProcessVertex process;
    private final ResourceVertex resource;

    Assignment(Vertex resource, Vertex process) {
        if(resource instanceof ResourceVertex
                && process instanceof ProcessVertex) {
            this.resource = (ResourceVertex) resource;
            this.process = (ProcessVertex) process;
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
        final Assignment other = (Assignment) obj;
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
        return resource.getName()+" -> "+process.getName();
    }


    @Override
    public String toDot() {
        String resourceName = resource.getName().replace("->", "_");
        resourceName = resourceName.replace("\\.", "_");
        String processName = process.getName().replace("->", "_");
        processName = processName.replace("\\.", "_");
        return resourceName+" -> "+processName+";";
    }


}
