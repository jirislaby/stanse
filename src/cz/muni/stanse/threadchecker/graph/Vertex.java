
package cz.muni.stanse.threadchecker.graph;

/**
 * Interface for manipulation with RAG
 * @author Jan Kučera
 */
public interface Vertex {
    public String getName();
    public void setName(String name);
    public String toDot();
}
