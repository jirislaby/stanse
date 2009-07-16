
package cz.muni.stanse.threadchecker.graph;

/**
 * Interface for manipulation with RAG
 * @author Jan Kučera
 */
public interface Edge {

    Vertex getResource();
    Vertex getProcess();
    @Override
    boolean equals(Object obj);
    String toDot();

}
