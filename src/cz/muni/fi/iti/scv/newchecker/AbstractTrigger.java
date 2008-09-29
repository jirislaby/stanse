
package cz.muni.fi.iti.scv.newchecker;

import cz.muni.fi.iti.scv.xml2cfg.CFGNode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * AbstractTrigger implements the most common methods of the TranisitionTrigger
 * @author xstastn
 */
abstract public class AbstractTrigger implements TransitionTrigger {
    
    protected List<AutomatonParam> params = new ArrayList<AutomatonParam>();
    
    protected String pass;
    
    public List<AutomatonParam> getTriggerParams() {
        return params;
    }

    public void addTriggerParam(AutomatonParam param) {
        this.params.add(param);
    }
    
    
    public Set<AssignedParam> assignParams(CFGNode from, CFGNode to) {
        Set<AssignedParam> retParams = new HashSet<AssignedParam>();
        for(int i = 0; i < params.size(); i++) {
            AutomatonParam param = params.get(i);
            retParams.add(param.initValue(from, to, i));
        }
        return retParams;
    }

    public String getPassString() {
        return pass;    
    }

    
    
    @Override
    public String toString() {
        return pass;
    }
    
    
    
    
}
