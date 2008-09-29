
package cz.muni.fi.iti.scv.newchecker;

import cz.muni.fi.iti.scv.xml2cfg.CFGNode;


/**
 * All the user defined params have to implement this interface.
 * @author xstastn
 */
public interface AutomatonParam {

    /**
     * It is highly recommended to override this method.
     * The natural implementation will compare IDs of the params.
     * @param other
     * @return
     */
    @Override
    boolean equals(Object other);
    
    /**
     * Initializes the value of the param and returns the assigned param as AssignedParam instance
     * @param from From node
     * @param to To node
     * @param index Index of the param starting from 0
     * @return instance of AssignedParam with the assigned value
     */
    AssignedParam initValue(CFGNode from, CFGNode to, int index);
    
    /**
     * Returns the ID of the param.
     * @return ID of the param
     */
    String getId();
    
    /**
     * Sets the ID of the param. The ID has to be unique within automaton.
     * @param id ID of the param.
     */
    void setId(String id);
}
