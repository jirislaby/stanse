package cz.muni.stanse.pointeranalyzer.shapirohorwitz;

import cz.muni.stanse.codestructures.*;
import java.util.*;
import org.dom4j.*;

/**
 * Counts the number of variables in a source file.
 *
 * @author Michal Strehovsky
 */
public final class VariableCounter {

    private int stackVariableCount;

    private HashSet<String> globalVariables = new HashSet<String>();;
    
    private VariableCounter() { }
    
    private void addFunction(CFGHandle handle) {
        
        HashSet<String> localVariables = new HashSet<String>();
        
        Element elem = handle.getElement();
        
        List ids = elem.selectNodes("descendant::id");
        
        for (Object o: ids) {
            
            Element current = (Element)o;
            String symbol = current.getText();
            
            if (handle.isSymbolLocal(symbol)) {
                localVariables.add(symbol);
            } else {
                globalVariables.add(symbol);
            }
        }
        
        stackVariableCount += localVariables.size() + 1 /* +1 for the function name */;
    }
    
    private int getNumberOfVariables() {
        return stackVariableCount + globalVariables.size();
    }

    public static int countVariables(Collection<CFGHandle> cfgs) {

        VariableCounter vc = new VariableCounter();

        for (CFGHandle cfg: cfgs) {
            vc.addFunction(cfg);
        }

        return vc.getNumberOfVariables();
    }

}
