/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.stanse.automatonchecker;

import cz.muni.stanse.automatonchecker.exceptions.AutomatonException;
import cz.muni.stanse.xml2cfg.CFGNode;

import org.apache.log4j.Logger;
import org.dom4j.Element;


/**
 *
 * @author xstastn
 */
public class AutomatonParamParamNames implements AutomatonParam {

    private static Logger logger = Logger.getLogger(Automaton.class);
    
    private String id;
    
    public AssignedParam initValue(CFGNode from, CFGNode to, int index) {
        AssignedParam assignedParam = null;
        try {
            String value = ((Element) from.getCFG().getEdgeElement(from, to).selectSingleNode("./id["+(index+2)+"]")).getTextTrim();
            assignedParam = new AssignedParam(this, value);
        } catch (AutomatonException ex) {
            logger.error(null, ex);
        }
        return assignedParam;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AutomatonParamParamNames other = (AutomatonParamParamNames) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
    
    

}
