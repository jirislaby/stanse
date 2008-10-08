package cz.muni.stanse.newchecker;

import java.util.EmptyStackException;
import java.util.Stack;

class EdgeStack {

    private Stack<EdgeWithAutomata> edges = new Stack<EdgeWithAutomata>();

    public EdgeStack() {
    }

    public EdgeWithAutomata push(EdgeWithAutomata item) {
        for (EdgeWithAutomata edge : edges) {
            if (edge.getEdge().equals(item.getEdge())) {
                edge.getAutomataSet().getAutomata().addAll(item.getAutomataSet().getAutomata());
                return edge;
            }
        }
        return edges.push(item);
    }

    public EdgeWithAutomata pop() throws EmptyStackException {
        return edges.pop();
    }

    public boolean isEmpty() {
        return edges.isEmpty();
    }

    @Override
    public String toString() {
        return this.edges.toString();
    }
}
