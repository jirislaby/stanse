/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.iti.scv.newchecker;

import cz.muni.fi.iti.scv.xml2cfg.CFGNode;

/**
 *
 * @author xstastn
 */
interface TransitionTrigger {

    String createTrigger(String param);
    
    boolean isTriggered(CFGNode from, CFGNode to);
}
