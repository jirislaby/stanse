package cz.muni.stanse.threadchecker.graph;

import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.utils.Pair;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 * Class represents Control Flow Graph during traversing analysis. Instance
 * holds reachable nodes in graph and also provide info about wrong builded
 * cycles to prevend deadlock during CFGTraverse analysis.
 * @author Jan Kučera
 */

public class CFGGraphState {
        private static final Logger logger =
                                Logger.getLogger(CFGGraphState.class.getName());
        private final CFGHandle cfg;
        private final Set<CFGNode> visitedNodes = new HashSet<CFGNode>();
        private final Set<CFGNode> reachableNodes = new HashSet<CFGNode>();
        private final Map<Integer,CFGNode> idToNode
                                            = new HashMap<Integer, CFGNode>();
        private final Map<CFGNode,Integer> wrongNodes
                                            = new HashMap<CFGNode,Integer>();
        private final Map<Pair<CFGNode,CFGNode>,Set<CFGNode>> cycleCache
                            = new HashMap<Pair<CFGNode,CFGNode>,Set<CFGNode>>();
                                       
    public CFGGraphState(CFGHandle cfg) {
        this.cfg = cfg;
        this.findReachableNodes(cfg); 
    }

    public CFGHandle getCfg() {
        return this.cfg;
    }

    public Set<CFGNode> getReachableNodes() {
        return reachableNodes;
    }

    public Set<CFGNode> getVisitedNodes() {
        return visitedNodes;
    }

    public CFGNode getIdToNode(Integer id) {
        return idToNode.get(id);
    }

    /**
     * Method returns CFGNodes which are predecessors of actualNode and
     * aren't marked as visited.
     * @param actualNode
     * @return List of CFGNodes which aren't marked as visited
     */
    public List<CFGNode> waitingFor(final CFGNode actualNode) {
        List<CFGNode> waitForNodes = new Vector<CFGNode>();
        for (CFGNode predecessor : actualNode.getPredecessors()) {
            if(!this.visitedNodes.contains(predecessor)
                                && this.reachableNodes.contains(predecessor)){
                waitForNodes.add(predecessor);
            }
        }

        return waitForNodes;
    }

    /**
     * Method determines, whether actualNode and any node from waitList are in
     * cycle. If so, method returns Set of nodes which are first succesors of
     * actualNode in founded cycle.
     * @param actualNode CFGNode
     * @param waitList List<CFGNode> list of predecessors of actualNode
     * @return Set<CFGNode> contains nodes where cycle starts
     */
    public Set<CFGNode> detectCycles(final CFGNode actualNode,
                                                    List<CFGNode> waitList) {
        List<CFGNode> nodesInCycle = new Vector<CFGNode>() ;
        Set<CFGNode> branchNodes = new HashSet<CFGNode>();
        Set<CFGNode> result;
        Pair<CFGNode,CFGNode> cycleKey;
        int counter;
        //Detect possible deadlock
        if(wrongNodes.containsKey(actualNode)) {
            counter = wrongNodes.get(actualNode).intValue();
            if(counter > 200) {
                for(CFGNode node : waitList) {
                    cycleKey = new Pair<CFGNode,CFGNode>(actualNode,node);
                    result = this.cycleCache.get(cycleKey);
                    if(result == null) {
                        result = this.detectWrongCycle(actualNode,node);
                        this.cycleCache.put(cycleKey, result);
                        logger.error("Found possible wrong numbering "
                                            +"in cycle with node "+actualNode);
                    }
                    branchNodes.addAll(result);
                }
                return branchNodes;
            } else {
                counter++;
                wrongNodes.put(actualNode, new Integer(counter));
            }
        } else {
            wrongNodes.put(actualNode, new Integer(0));
        }
        
        for (CFGNode node : waitList) {
            if(actualNode.getNumber() < node.getNumber()) {
                nodesInCycle.add(node);
            }
        }

        for(CFGNode succ : actualNode.getSuccessors()) {
            for (CFGNode cycleNode : nodesInCycle) {
                if(succ.getNumber() <= cycleNode.getNumber()) {
                    branchNodes.add(succ);
                    break;
                }
            }
        }
        return branchNodes;
     }
    
    /**
    * Method traverse CFG and sets reachable nodes from startNode of CFG
    * @param cfg CFG
    */
    private void findReachableNodes(CFGHandle cfg) {
        CFGNode node;
        LinkedList<CFGNode> queue = new LinkedList<CFGNode>();
        queue.add(cfg.getCFG().getStartNode());

        while(!queue.isEmpty()) {
            node = queue.poll();
            reachableNodes.add(node);
            idToNode.put(node.getNumber(), node);
            for(CFGNode succ : node.getSuccessors()) {
                if(!reachableNodes.contains(succ) && !queue.contains(succ))
                    queue.add(succ);
            }
        }
    }
    
    /**
     * Method tries to find, wheter graph contains wrong numbering between node
     * in cycle.
     * @param lastNode CFGNode predecessor of startNode
     * @param startNode CFGNode where cycle might start
     * @return Set<CFGNode> contains succesors of startNode which are in cycle.
     */
    private Set<CFGNode> detectWrongCycle(CFGNode lastNode, CFGNode startNode) {
        LinkedList<CFGNode> queue = new LinkedList<CFGNode>();
        CFGNode node;
        Set<CFGNode> branchNodes = new HashSet<CFGNode>();
        Set<CFGNode> visited = new HashSet<CFGNode>();
        queue.add(startNode);
        
        while(!queue.isEmpty()) {
            node = queue.poll();
            visited.add(node);

            for(CFGNode pred : node.getPredecessors()) {
                if(!reachableNodes.contains(pred) || visited.contains(pred)
                                                || queue.contains(pred)) {
                    continue;
                }
                if(pred.equals(lastNode)) {
                    branchNodes.add(node);
                    break;
                }
                queue.add(pred);
            }
        }
        
        return branchNodes;
    }

}
