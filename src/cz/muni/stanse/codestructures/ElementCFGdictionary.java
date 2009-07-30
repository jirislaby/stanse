package cz.muni.stanse.codestructures;

import cz.muni.stanse.codestructures.builders.XMLLinearizeASTElement;
import cz.muni.stanse.utils.Pair;

import java.util.Collection;
import java.util.HashMap;

import org.dom4j.Element;

public final class ElementCFGdictionary {

    // public section

    public ElementCFGdictionary(final Collection<CFGHandle> CFGs) {
        dictionary = buildDictionary(CFGs);
    }

    public CFG get(final Element elem) {
        final Pair<String,Integer> key = buildKey(elem);
        return (key == null) ? null : getDictionary().get(key);
    }

    // private section

    private static HashMap<Pair<String,Integer>,CFG>
    buildDictionary(final Collection<CFGHandle> CFGs) {
        final HashMap<Pair<String,Integer>,CFG> dictionary =
            new HashMap<Pair<String,Integer>,CFG>();
        for (final CFGHandle cfgh: CFGs) {
            CFG cfg = cfgh.getCFG();
            final java.util.Vector<Element> linerDecl =
                XMLLinearizeASTElement.functionDeclaration(cfg.getElement());
            assert(linerDecl != null);
            dictionary.put(Pair.make(linerDecl.firstElement().getText(),
                                     linerDecl.size() - 1),cfg);
        }
        return dictionary;
    }

    private static Pair<String,Integer>
    buildKey(final Element elem) {
        final java.util.Vector<Element> linerCall =
            XMLLinearizeASTElement.functionCall(elem);
        return (linerCall == null) ?
                    null : Pair.make(linerCall.firstElement().getText(),
                                     linerCall.size() - 1);
    }

    private HashMap<Pair<String, Integer>, CFG> getDictionary() {
        return dictionary;
    }

    private final HashMap<Pair<String,Integer>,CFG> dictionary;
}
