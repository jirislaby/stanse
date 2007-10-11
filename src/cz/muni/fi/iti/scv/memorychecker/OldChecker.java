package cz.muni.fi.iti.scv.memorychecker;

import cz.muni.fi.iti.scv.xml2cfg.*;

/*import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;*/

import java.util.Set;
import java.util.HashSet;

public abstract class OldChecker {

    protected Set<ControlFlowGraph> cfgs;
    
    public OldChecker() {
        cfgs = new HashSet<ControlFlowGraph>();
    }
    
    public final void addCFG(ControlFlowGraph cfg) {
        cfgs.add(cfg);             
    }
    
    public final void removeCFG(ControlFlowGraph cfg) {
        cfgs.remove(cfg);             
    }    
    
    public abstract Set<ErrorNode> check();     
    
}
