package cz.muni.stanse.threadchecker.graph;

import cz.muni.stanse.threadchecker.exceptions.CycleException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.CycleDetector;
import org.jgrapht.alg.StrongConnectivityInspector;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DirectedMultigraph;

/**
 * Singleton-type Class which detects cycle in oriented multi-graph
 * @author Jan Kučera
 */
public class DependencyCycleDetector {
    private DirectedMultigraph<ResourceVertex,AbstractDependencyRule> graph;
    private CycleDetector<ResourceVertex,AbstractDependencyRule> detector;
    private static DependencyCycleDetector instance;
    private static final Logger logger = Logger.getLogger(
                                            DependencyCycleDetector.class.toString());
    private final Map<ResourceVertex,List<AbstractDependencyRule>> rulesBySourceNode
                            = new HashMap<ResourceVertex, List<AbstractDependencyRule>>();
    private final Map<ResourceVertex,List<AbstractDependencyRule>> rulesByTargetNode
                            = new HashMap<ResourceVertex, List<AbstractDependencyRule>>();
                
    private DependencyCycleDetector() {
        this.graph = new DirectedMultigraph<ResourceVertex,AbstractDependencyRule>(
                            new ClassBasedEdgeFactory<ResourceVertex,AbstractDependencyRule>(
                               AbstractDependencyRule.class
                    )
        );
        this.detector = new CycleDetector<ResourceVertex,AbstractDependencyRule>(graph);
        
    }

    /**
    * Function returns singleton instance of class DependencyCycleDetector
    * @return DependencyCycleDetector instance of DependencyCycleDetector
    */
    public static DependencyCycleDetector getInstance() {
        if(instance == null) {
            instance = new DependencyCycleDetector();
        }

        return DependencyCycleDetector.instance;
    }

    /**
     * Method picks up every rule and add it to graph
     * @param dependencyGraph 
     * @return Set<Cycle> found cycles
     */
    public Set<Cycle> detect(DependencyGraph dependencyGraph) {
        this.clearGraph();
        Collection abstractRules = dependencyGraph.getRules();
        this.prepareRules(abstractRules);

        for(AbstractDependencyRule rule
                        : (Collection<AbstractDependencyRule>)abstractRules) {
            this.addEdge(rule);
        }

        return findCycles();
    }

    /**
     * Function gets list of dependency rules and return list of simple cycles.
     * @param rules List<AbstractDependencyRule> list of all rules in graph
     * @return List<Cycle> list of simple cycles.
     */
    private Set<Cycle> findCycles() {
        StrongConnectivityInspector<ResourceVertex,AbstractDependencyRule> 
                inspector = new StrongConnectivityInspector
                                <ResourceVertex,AbstractDependencyRule>(graph);
        Set<Cycle> result = new HashSet<Cycle>();
        Set<Set<ResourceVertex>> cycles = new HashSet<Set<ResourceVertex>>();
        List<Set<ResourceVertex>> components
                                            = inspector.stronglyConnectedSets();

        for (Set<ResourceVertex> component : components) {
            if(component.size()>1) {
                result.addAll(this.analyseCycle(component));
            }
        }

        return result;
    }

    /**
     * Function analyse strongly connected components and return simple cycles.
     * @param cycle Set<ResourceVertex> Set of strongly connected components
     * @return List<Cycle> list of simple cycles.
     */
    private List<Cycle> analyseCycle(Set<ResourceVertex> cycle) {
        GraphState actualState;
        GraphState clone;
        Queue<GraphState> queue = new LinkedList<GraphState>();
        ResourceVertex actualVertex;
        ResourceVertex nextVertex;
        AbstractDependencyRule rule;
        List<AbstractDependencyRule> nextRules
                                        = new Vector<AbstractDependencyRule>();
        List<Cycle> result = new Vector<Cycle>();
        
        actualState = new GraphState();
        actualState.getResources().addFirst(cycle.iterator().next());
        queue.add(actualState);
        
        while(!queue.isEmpty()) {
            actualState = queue.remove();
            actualVertex = actualState.getResources().removeFirst();
            
            while(true) {

                //Cycle found!
                if(actualState.getResources().contains(actualVertex)) {
                    List<AbstractDependencyRule> cycleRules
                                        = new Vector<AbstractDependencyRule>();
                    while(true) {
                        rule = actualState.getRules().removeFirst();
                        if(rule.getSource().equals(actualVertex)) {
                            cycleRules.add(rule);
                            break;
                        } else {
                            cycleRules.add(rule);
                        }
                    }
                    try {
                        result.add(new Cycle(cycleRules));
                    } catch (CycleException ex) {
                        logger.error(ex.getMessage());
                    }
                    break;
                } else {
                    actualState.getResources().addFirst(actualVertex);
                }

                //Clear previous rules and fill with rules starts by
                //actualVertex and end of this edge is also
                //strongly connected component
                nextRules.clear();
                for(AbstractDependencyRule newRule
                                    : this.rulesBySourceNode.get(actualVertex)){
                    if(cycle.contains(newRule.getTarget())) {
                        nextRules.add(newRule);
                    }
                }

                //If rules are 2 and more, create clone of actual graph state
                for (int index = 1; index < nextRules.size(); index++) {
                    rule = nextRules.get(index);
                    clone = actualState.clone();
                    clone.getResources().addFirst(rule.getTarget());
                    clone.getRules().addFirst(rule);
                    queue.add(clone);
                }

                rule = nextRules.get(0);
                nextVertex = rule.getTarget();
                actualState.getRules().addFirst(rule);
                actualVertex = nextVertex;
            }

        }

        return result;
    }
    
    /**
     * Method adds edge to RAG.
     * @param rule AbstractDependencyRule which should be added to RAG
     */
    private void addEdge(AbstractDependencyRule rule) {
        ResourceVertex source = rule.getSource();
        ResourceVertex target = rule.getTarget();
        if(! this.graph.containsVertex(source)) {
            this.graph.addVertex(source);
        }
        
        if(! this.graph.containsVertex(target)) {
            this.graph.addVertex(target);
        }
                
        this.graph.addEdge(source, target, rule);
        return;
    }

    private void clearGraph() {
        this.graph = new DirectedMultigraph<ResourceVertex,AbstractDependencyRule>(
                        new ClassBasedEdgeFactory<ResourceVertex,AbstractDependencyRule>(
                               AbstractDependencyRule.class
                        )
        );
        this.detector
              = new CycleDetector<ResourceVertex,AbstractDependencyRule>(graph);
        this.rulesBySourceNode.clear();
        this.rulesByTargetNode.clear();
    }

    /**
     * Procedure creates Map structures for easier manipulation with Rules.
     * @param rules List<AbstractDependencyRule>
     */
    private void prepareRules(Collection<AbstractDependencyRule> rules) {
        List<AbstractDependencyRule> ruleList;
        for(AbstractDependencyRule rule : rules) {

            if(rulesBySourceNode.containsKey(rule.getSource())) {
                rulesBySourceNode.get(rule.getSource()).add(rule);
            } else {
                ruleList = new Vector<AbstractDependencyRule>();
                ruleList.add(rule);
                rulesBySourceNode.put(rule.getSource(),ruleList);
            }

            if(rulesByTargetNode.containsKey(rule.getTarget())) {
                rulesByTargetNode.get(rule.getTarget()).add(rule);
            } else {
                ruleList = new Vector<AbstractDependencyRule>();
                ruleList.add(rule);
                rulesByTargetNode.put(rule.getTarget(),ruleList);
            }
        }
    }

    /**
     * Private class for algorithm finding simple cycles
     */
    private class GraphState {
        private LinkedList<ResourceVertex> resources
                                            = new LinkedList<ResourceVertex>();
        private LinkedList<AbstractDependencyRule> rules
                                    = new LinkedList<AbstractDependencyRule>();

        public LinkedList<ResourceVertex> getResources() {
            return resources;
        }

        public LinkedList<AbstractDependencyRule> getRules() {
            return rules;
        }

        @Override
        protected GraphState clone() {
            GraphState clone = new GraphState();
            clone.resources.addAll(this.resources);
            clone.rules.addAll(this.rules);
            return clone;
        }

        @Override
        public String toString() {
            return this.resources + " "+this.rules;
        }
    }
}
