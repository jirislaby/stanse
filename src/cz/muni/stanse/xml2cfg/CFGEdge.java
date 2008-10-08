package cz.muni.stanse.xml2cfg;


public class CFGEdge {

    private CFGNode from;
    private CFGNode to;

    
    /**
     * Private -- use getInstace instead.
     * @param from From node (parent)
     * @param to To node (child)
     */
    private CFGEdge(CFGNode from, CFGNode to) {
        this.from = from;
        this.to = to;
    }
    
    /**
     * Factory. Cache may be introduced.
     * @param from From node (parent)
     * @param to To node (child)
     * @return Instance of CFGEdge
     */
    public static CFGEdge getInstance(CFGNode from, CFGNode to) {
        
        CFGEdge retEdge;
        retEdge= new CFGEdge(from, to);
        
        return retEdge;
    }

    public CFGNode getFrom() {
        return from;
    }

    public CFGNode getTo() {
        return to;
    }
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CFGEdge other = (CFGEdge) obj;
        if (this.from != other.from && (this.from == null || !this.from.equals(other.from))) {
            return false;
        }
        if (this.to != other.to && (this.to == null || !this.to.equals(other.to))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.from != null ? this.from.hashCode() : 0);
        hash = 41 * hash + (this.to != null ? this.to.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "From: "+getFrom()+"---"+getTo();
    }
    
    
}
