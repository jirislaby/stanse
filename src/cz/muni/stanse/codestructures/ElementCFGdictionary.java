package cz.muni.stanse.codestructures;

import cz.muni.stanse.codestructures.builders.XMLLinearizeASTElement;
import cz.muni.stanse.utils.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;

// TODO: param count doesn't make much sense in some languages
public final class ElementCFGdictionary {

    // public section

    public ElementCFGdictionary(final Collection<CFGHandle> CFGs) {
        dictionary = buildDictionary(CFGs);
    }

    public CFGHandle get(final CFGNode node) {
        final Pair<String,Integer> key = buildKey(node);
        return (key == null) ? null : getDictionary().get(key);
    }

    // private section

    private static HashMap<Pair<String,Integer>,CFGHandle>
    buildDictionary(final Collection<CFGHandle> CFGs) {
        final HashMap<Pair<String,Integer>, CFGHandle> dictionary =
            new HashMap<Pair<String,Integer>, CFGHandle>();
        for (final CFGHandle cfgh: CFGs)
            dictionary.put(Pair.make(cfgh.getFunctionName(), cfgh.getParams().size()), cfgh);
        return dictionary;
    }

    private static Pair<String,Integer>
    buildKey(final CFGNode node) {
        if (node.getNodeType() != null) {
            if (node.getNodeType().equals("call")) {
                List<CFGNode.Operand> operands = node.getOperands();
                assert operands.size() > 0;
                if (operands.get(0).type == CFGNode.OperandType.function)
                    return Pair.make((String)node.getOperands().get(0).id, operands.size() - 1);
            }
        } else if (node.getElement() != null) {
            final java.util.Vector<Element> linerCall = XMLLinearizeASTElement.functionCall(node.getElement());
            if (linerCall != null)
                return Pair.make(linerCall.firstElement().getText(), linerCall.size() - 1);
        }
        return null;
    }

    private HashMap<Pair<String, Integer>, CFGHandle> getDictionary() {
        return dictionary;
    }

    private final HashMap<Pair<String,Integer>,CFGHandle> dictionary;
}
