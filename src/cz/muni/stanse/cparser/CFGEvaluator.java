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

    static CFGNode evaluateExprConnect(Element cond, String code,
	    CFGNode _then, CFGNode _else) {
	ExprEvaluator ee = new ExprEvaluator(cond);
	return ExprEvaluator.connect(ee.eval(cond, code), _then, _else);
    }

    static Triple<CFGNode,CFGNode,CFGNode> evaluateExpr(Element cond, String code) {
	ExprEvaluator ee = new ExprEvaluator(cond);
	return ee.eval(cond, code);
    }

    static private CFGNode createAssert(Element cond, String code, String bl, boolean neg) {
	Element ae = xmlFactory.createElement("assert");
	ae.addAttribute("bl", bl);
	if (cond.getParent() != null)
	    cond = cond.createCopy();
	else if (cond.attribute("bl") == null)
	    cond.addAttribute("bl", bl);
	if (neg) {
	    ae.addElement("prefixExpression").
		addAttribute("op", "!").
		addAttribute("bl", bl).
		add(cond);
	    code = "!(" + code + ")";
	} else
	    ae.add(cond);
	return new CFGNode(ae, "assert(" + code + ")");
    }
    static CFGNode addAssert(CFGNode n1, Element label, CFGNode n2,
	    Element cond, String code, boolean neg, Element lineElem) {
	if (lineElem == null || lineElem.attribute("bl") == null)
	    lineElem = n1.getElement();
	assert(lineElem != null);
	String bl = lineElem.attributeValue("bl");
	assert(bl != null);

	CFGNode an = createAssert(cond, code, bl, neg);
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

    public static CFGNode connect(Triple<CFGNode,CFGNode,CFGNode> tree,
	    CFGNode _then, CFGNode _else) {
	tree.getSecond().addEdge(_then);
	tree.getThird().addEdge(_else);
	return tree.getFirst();
    }

    protected Triple<CFGNode,CFGNode,CFGNode> eval(Element cond, String code) {
	Triple<CFGNode,CFGNode,CFGNode> ret, l, r;
	if (cond.getName().equals("binaryExpression") &&
		    cond.attributeValue("op").equals("&&")) {
	    l = eval((Element)cond.elements().get(0), code); /* FIXME incorrect */
	    r = eval((Element)cond.elements().get(1), code); /* FIXME incorrect */
	    CFGJoinNode f = new CFGJoinNode();
	    l.getSecond().addEdge(r.getFirst());
	    l.getThird().addEdge(f);
	    r.getThird().addEdge(f);
	    ret = Triple.<CFGNode,CFGNode,CFGNode>make(l.getFirst(),
		    r.getSecond(), f);
	} else if (cond.getName().equals("binaryExpression") &&
		    cond.attributeValue("op").equals("||")) {
	    l = eval((Element)cond.elements().get(0), code); /* FIXME incorrect */
	    r = eval((Element)cond.elements().get(1), code); /* FIXME incorrect */
	    CFGJoinNode t = new CFGJoinNode();
	    l.getSecond().addEdge(t); // l.true
	    l.getThird().addEdge(r.getFirst()); // l.false
	    r.getSecond().addEdge(t); // r.true
	    ret = Triple.<CFGNode,CFGNode,CFGNode>make(l.getFirst(), t,
		    r.getThird());
	} else {
	    Integer constVal = CFGEvaluator.getExprValue(cond);
	    if (constVal != null) {
		CFGNode node = new CFGNode(cond);
		CFGNode oNode = new CFGJoinNode();
		node.addOptEdge(oNode);
		if (constVal != 0)
		    ret = Triple.<CFGNode,CFGNode,CFGNode>make(node, node,
			    oNode);
		else
		    ret = Triple.<CFGNode,CFGNode,CFGNode>make(node, oNode,
			    node);
	    } else {
		CFGBranchNode branch = new CFGBranchNode(cond, code);
		CFGNode t, f;
		t = CFGEvaluator.addAssert(branch, CFGEmitter.defaultLabel,
			null, cond, code, false, lineElem);
		f = CFGEvaluator.addAssert(branch, CFGEmitter.falseLabel, null,
			cond, code, true, lineElem);
		ret = Triple.<CFGNode,CFGNode,CFGNode>make(branch, t, f);
	    }
	}
	return ret;
    }
}
