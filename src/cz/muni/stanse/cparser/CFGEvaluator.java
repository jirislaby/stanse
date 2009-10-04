package cz.muni.stanse.cparser;

import cz.muni.stanse.codestructures.CFGBranchNode;
import cz.muni.stanse.codestructures.CFGJoinNode;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.utils.Triple;

import org.dom4j.DocumentFactory;
import org.dom4j.Element;

public class CFGEvaluator {
    private static DocumentFactory xmlFactory = DocumentFactory.getInstance();
    static Element defaultLabel = xmlFactory.createElement("default");
    static Element falseLabel = xmlFactory.createElement("intConst");
    static {
	falseLabel.setText("0");
    }

    static CFGNode evaluateExprConnect(Element cond, CFGNode _then,
	    CFGNode _else) {
	ExprEvaluator ee = new ExprEvaluator(cond);
	return ExprEvaluator.connect(ee.eval(cond), _then, _else);
    }

    static Triple<CFGNode,CFGNode,CFGNode> evaluateExpr(Element cond) {
	ExprEvaluator ee = new ExprEvaluator(cond);
	return ee.eval(cond);
    }

    static private CFGNode createAssert(Element cond, String bl, boolean neg) {
	Element ae = xmlFactory.createElement("assert");
	ae.addAttribute("bl", bl);
	if (cond.getParent() != null)
	    cond = cond.createCopy();
	else if (cond.attribute("bl") == null)
	    cond.addAttribute("bl", bl);
	if (neg)
	    ae.addElement("prefixExpression").
		addAttribute("op", "!").
		addAttribute("bl", bl).
		add(cond);
	else
	    ae.add(cond);
	return new CFGNode(ae);
    }
    static CFGNode addAssert(CFGNode n1, Element label, CFGNode n2,
	    Element cond, boolean neg, Element lineElem) {
	if (lineElem == null || lineElem.attribute("bl") == null)
	    lineElem = n1.getElement();
	assert(lineElem != null);
	String bl = lineElem.attributeValue("bl");
	assert(bl != null);

	CFGNode an = createAssert(cond, bl, neg);
	if (n1 instanceof CFGBranchNode) {
	    CFGBranchNode n1b = (CFGBranchNode)n1;
	    n1b.addEdge(an, label);
	} else
	    n1.addEdge(an);
	if (n2 != null)
	    an.addEdge(n2);
	return an;
    }
    static Integer getExprValue(Element cond) {
	if (cond.getName().equals("intConst"))
	    return Integer.decode(cond.getText());
	else if (cond.getName().equals("emptyStatement"))
	    return 1;
	return null;
    }
}

class ExprEvaluator {
    private static DocumentFactory xmlFactory = DocumentFactory.getInstance();
    private Element lineElem;

    protected ExprEvaluator(Element lineElem) {
	this.lineElem = lineElem;
    }

    protected static void addEdge(CFGNode from, CFGNode to) {
	if (from != null)
	    from.addEdge(to);
    }

    public static CFGNode connect(Triple<CFGNode,CFGNode,CFGNode> tree,
	    CFGNode _then, CFGNode _else) {
	addEdge(tree.getSecond(), _then);
	addEdge(tree.getThird(), _else);
	return tree.getFirst();
    }

    protected Triple<CFGNode,CFGNode,CFGNode> eval(Element cond) {
	Triple<CFGNode,CFGNode,CFGNode> ret, l, r;
	if (cond.getName().equals("binaryExpression") &&
		    cond.attributeValue("op").equals("&&")) {
	    l = eval((Element)cond.elements().get(0));
	    r = eval((Element)cond.elements().get(1));
	    CFGJoinNode f = new CFGJoinNode();
	    addEdge(l.getSecond(), r.getFirst());
	    addEdge(l.getThird(), f);
	    addEdge(r.getThird(), f);
	    ret = Triple.<CFGNode,CFGNode,CFGNode>make(l.getFirst(),
		    r.getSecond(), f);
	} else if (cond.getName().equals("binaryExpression") &&
		    cond.attributeValue("op").equals("||")) {
	    l = eval((Element)cond.elements().get(0));
	    r = eval((Element)cond.elements().get(1));
	    CFGJoinNode t = new CFGJoinNode();
	    addEdge(l.getSecond(), t); // l.true
	    addEdge(l.getThird(), r.getFirst()); // l.false
	    addEdge(r.getSecond(), t); // r.true
	    ret = Triple.<CFGNode,CFGNode,CFGNode>make(l.getFirst(), t,
		    r.getThird());
	} else {
	    Integer constVal = CFGEvaluator.getExprValue(cond);
	    if (constVal != null) {
		CFGNode node = new CFGNode(cond);
		if (constVal != 0)
		    ret = Triple.<CFGNode,CFGNode,CFGNode>make(node, node,
			    null);
		else
		    ret = Triple.<CFGNode,CFGNode,CFGNode>make(node, null,
			    node);
	    } else {
		CFGBranchNode branch = new CFGBranchNode(cond);
		CFGNode t, f;
		t = CFGEvaluator.addAssert(branch, CFGEmitter.defaultLabel,
			null, cond, false, lineElem);
		f = CFGEvaluator.addAssert(branch, CFGEmitter.falseLabel, null,
			cond, true, lineElem);
		ret = Triple.<CFGNode,CFGNode,CFGNode>make(branch, t, f);
	    }
	}
	return ret;
    }
}
