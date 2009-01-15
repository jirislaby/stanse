/**
 * @file CFGInstrumentationBuilder.java 
 * @brief Implements final class CFGInstrumentationBuilder which builds deep
 *        copy of CFG (or whole set of CFGs) and the copied CFG has replaced all
 *        its branch nodes by assertion nodes (i.e. common CFGNodes with special
 *        XML element assigned to them).
 *
 * Copyright (c) 2008-2009 Marek Trtik
 *
 * Licensed under GPLv2.
 */
package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.codestructures.CFGBranchNode;
import cz.muni.stanse.utils.Pair;

import org.dom4j.Element;
import org.dom4j.DocumentFactory;

import java.util.Set;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * @brief Implements class which builds deep copy of CFG (or whole set of CFGs)
 *        and the copied CFG has replaced all its branch nodes by assertion
 *        nodes (i.e. common CFGNodes with special XML element assigned to
 *        them).
 *
 * Class is not intended to be instantiated.
 */
final class CFGInstrumentationBuilder {

    // package-private section

    /**
     * @brief Accepts set of CFGs and for each of them calls method run(CFG) to
     *        produce deep copy and branch-to-asserts-modified of original ones. 
     *
     * @param CFGs Set of CFGs to be deep-copied and branch-to-asserts-modified.
     * @return Set of deep-copied and branch-to-asserts-modified CFGs.
     * @see cz.muni.stanse.automatonchecker.CFGInstrumentationBuilder#run(CFG)
     */
    static Pair< HashMap<CFG,CFG>,HashMap<CFGNode,CFGNode> >
    run(final Set<CFG> CFGs) {
        final HashMap<CFGNode,CFGNode> nodeBackMapping =
            new HashMap<CFGNode,CFGNode>();
        final HashMap<CFG,CFG> cfgBackMapping = new HashMap<CFG,CFG>();

        for (CFG cfg : CFGs) {
            final Pair<CFG,HashMap<CFGNode,CFGNode>> CFGmapping = run(cfg);
            cfgBackMapping.put(CFGmapping.getFirst(),cfg);
            nodeBackMapping.putAll(CFGmapping.getSecond());
        }

        return new Pair< HashMap<CFG,CFG>,HashMap<CFGNode,CFGNode> >
                                               (cfgBackMapping,nodeBackMapping);
    }

    /**
     * @brief Produces deep copy of passed CFG which is additionally modified
     *        the way, that each branch node is replaced by assert nodes (i.e.
     *         common CFGNodes with special XML element assigned to them).
     *
     * @param CFG CFG to be deep-copied and branch-to-asserts-modified.
     * @return Deep-copied and branch-to-asserts-modified CFGs.
     */
    static Pair< CFG,HashMap<CFGNode,CFGNode> >
    run(final CFG origCFG) {
        final Pair< HashMap<CFGNode,CFGNode>,HashMap<CFGNode,CFGNode> >
            nodeMappings = createCFGdeepNonbranchCopyWithMappings(origCFG);
        final HashMap<CFGNode,CFGNode> forwardMapping = nodeMappings.getFirst();
        final HashMap<CFGNode,CFGNode> backwardMapping=nodeMappings.getSecond();

        convertBranchesConditionsToAssertions(forwardMapping,backwardMapping);

        final CFG insrumentedCFG = new CFG(origCFG);
        insrumentedCFG.setStartNode(forwardMapping.get(origCFG.getStartNode()));
        insrumentedCFG.setEndNode(forwardMapping.get(origCFG.getEndNode()));
        
        return new Pair< CFG,HashMap<CFGNode,CFGNode> >(insrumentedCFG,
                                                        backwardMapping);
    }
    
    // private section

    private static Pair< HashMap<CFGNode,CFGNode>,HashMap<CFGNode,CFGNode> >
    createCFGdeepNonbranchCopyWithMappings(final CFG origCFG) {
        final HashMap<CFGNode,CFGNode> forwardMapping =
            new HashMap<CFGNode,CFGNode>();
        final HashMap<CFGNode,CFGNode> backMapping =
            new HashMap<CFGNode,CFGNode>();

        for (CFGNode node : origCFG.getAllNodes()) {
            final CFGNode nodeCopy = new CFGNode(
                        (node instanceof CFGBranchNode) ?
                                        DocumentFactory.getInstance().
                                                       createElement("branch") :
                                        // TODO: End CFGNode have assigned no
                                        //       XML element to it (i.e. it is
                                        //       null),so following code creates
                                        //       XML element for it. When CFG
                                        //       sets valid XML element for end
                                        //       CFGNode, then the operator ()?:
                                        //       here can be replace by code
                                        //       'node.getElement()'.
                                        (node == origCFG.getEndNode()) ? 
                                            DocumentFactory.getInstance().
                                                         createElement("exit") :
                                        node.getElement());
            forwardMapping.put(node,nodeCopy);
            if (!(node instanceof CFGBranchNode))
                backMapping.put(nodeCopy,node);
        }

        addNonbranchEdgesToCFGdeepCopy(forwardMapping);

        // TODO: this code introduces entry node to CFG (i.e. node WITHOUT any
        //       code statement corresponding to. When original CFG suppors this
        //       node, then following block should be removed.)
        {
            final CFGNode entryNode = new CFGNode(DocumentFactory.getInstance().
                                                       createElement("entry"));
            entryNode.addEdge(forwardMapping.get(origCFG.getStartNode()));
            forwardMapping.put(origCFG.getStartNode(),entryNode);
            backMapping.put(entryNode,origCFG.getStartNode());
        }

        return new Pair< HashMap<CFGNode,CFGNode>,HashMap<CFGNode,CFGNode> >
                                                   (forwardMapping,backMapping);
    }

    private static void addNonbranchEdgesToCFGdeepCopy(
                                final HashMap<CFGNode,CFGNode> forwardMapping) {
        for (CFGNode node : forwardMapping.keySet()) {
            if (node instanceof CFGBranchNode)
                continue;
            final CFGNode nodeCopy = forwardMapping.get(node);
            for (CFGNode succ : node.getSuccessors())
                nodeCopy.addEdge(forwardMapping.get(succ));
        }
    }

    private static void convertBranchesConditionsToAssertions(
                               final HashMap<CFGNode,CFGNode> forwardMapping,
                               final HashMap<CFGNode,CFGNode> backwardMapping) {
        for (CFGNode node : forwardMapping.keySet()) {
            if (!(node instanceof CFGBranchNode))
                continue;
            addAssertions(forwardMapping.get(node),(CFGBranchNode)node,
                          forwardMapping,backwardMapping);
        }
    }

    private static void addAssertions(final CFGNode nodeCopy,
                               final CFGBranchNode node,
                               final HashMap<CFGNode,CFGNode> forwardMapping,
                               final HashMap<CFGNode,CFGNode> backwardMapping) {
        for (CFGNode assertNode :
                addDefaultAssertions(nodeCopy,
                             addNonDefaultAssertions(nodeCopy,node,
                                                     forwardMapping),
                             forwardMapping.get(node.getSuccessors().get(
                                     getDefaultBranchElementIndex(node)))))
            backwardMapping.put(assertNode,node);
    }

    private static LinkedList<CFGNode> addNonDefaultAssertions(
                                final CFGNode nodeCopy,final CFGBranchNode node,
                                final HashMap<CFGNode,CFGNode> forwardMapping) {
        final LinkedList<CFGNode> nonDefaultAssertNodes =
            new LinkedList<CFGNode>(); 
        for (int i = 0; i < node.getSuccessors().size(); ++i) {
            if (isDefaultBranchElement(node.getEdgeLabel(i)))
                continue;
            final CFGNode assertNode = new CFGNode(
                    createEqualityAssertElement(node.getElement().createCopy(),
                                            node.getEdgeLabel(i).createCopy()));
            nodeCopy.addEdge(assertNode);
            assertNode.addEdge(forwardMapping.get(node.getSuccessors().get(i)));
            nonDefaultAssertNodes.add(assertNode);
        }
        return nonDefaultAssertNodes;
    }

    private static int getDefaultBranchElementIndex(
                                                 final CFGBranchNode condNode) {
        for (int i = 0; i < condNode.getSuccessors().size(); ++i)
            if (isDefaultBranchElement(condNode.getEdgeLabel(i)))
                return i;
        assert(false);
        return -1;
    }

    private static LinkedList<CFGNode>  addDefaultAssertions(
                              CFGNode predecessorNode,
                              final LinkedList<CFGNode> nonDefaultAssertNodes,
                              final CFGNode targetNode) {
        for (CFGNode nonDefaultAssertNode : nonDefaultAssertNodes) {
            final CFGNode assertNode = new CFGNode(
                createNegationAssertElement(
                             ((Element)nonDefaultAssertNode.getElement().
                                       elementIterator().next()).createCopy()));
            predecessorNode.addEdge(assertNode);
            predecessorNode = assertNode;
        }
        predecessorNode.addEdge(targetNode);
        nonDefaultAssertNodes.add(predecessorNode);
        return nonDefaultAssertNodes;
    }

    private static Element createEqualityAssertElement(final Element predicate,
                                                       final Element constExpr){
        final Element equalityElement = DocumentFactory.getInstance().
                                          createElement("binaryExpression");
        equalityElement.addAttribute("op","==");
        equalityElement.add(predicate);
        equalityElement.add(constExpr);

        final Element assertElement = DocumentFactory.getInstance().
                                                        createElement("assert");
        assertElement.add(equalityElement);
        return assertElement;
    }

    private static Element createNegationAssertElement(
                                                      final Element predicate) {
        final Element notElement = DocumentFactory.getInstance().
                                          createElement("prefixExpression");
        notElement.addAttribute("op","!");
        notElement.add(predicate);
        final Element assertElement = DocumentFactory.getInstance().
                                                        createElement("assert");
        assertElement.add(notElement);
        return assertElement;
    }
    
    private static boolean isDefaultBranchElement(final Element element) {
        return element.getName().equals("default"); 
    }

    private CFGInstrumentationBuilder() {
    }
}
