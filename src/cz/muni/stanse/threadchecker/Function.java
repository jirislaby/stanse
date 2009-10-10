
package cz.muni.stanse.threadchecker;

import cz.muni.stanse.Stanse;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.threadchecker.locks.BackTrack;
import java.util.List;
import java.util.Vector;
import org.apache.log4j.Logger;

/**
 * Class Function is basic element in traverse alg. from CFGTransit. It's
 * envelope for CFGNode, List of FunctionState
 * @author Jan Kučera
 */
public class Function implements Cloneable {
    private CFGNode actualNode;
    private String functionName;
    private String fileName;
    private final List<FunctionState> states = new Vector<FunctionState>();

    private final static Logger logger 
                                = Logger.getLogger(Function.class.getName());

    public Function(CFGHandle cfg) {
        functionName = cfg.getFunctionName();
        fileName = Stanse.getUnitManager().getUnitName(cfg);
        actualNode = cfg.getStartNode();
        FunctionState data = new FunctionState();
        CFGNode node = cfg.getStartNode();
        data.getBackTrack().addLast(new BackTrack(node.getNumber(),
                        node.getLine(),"start of function", fileName));
        this.states.add(data);
    }

    protected Function(String functionName) { //Added only for test class
        this.functionName = functionName;
        this.fileName = "no_filename";
        FunctionState data = new FunctionState();

        data.getBackTrack().addLast(new BackTrack(0,0,"start of function",
                                                                "no_filename"));
        this.states.add(data);
    }

    protected Function(String functionName,String fileName) { 
        this.functionName = functionName;
        this.fileName = fileName;
        FunctionState data = new FunctionState();
        data.getBackTrack().addLast(new BackTrack(0,0,"start of function",
                                                                        null));
        this.states.add(data);
    }

    public Function(Function function) {
        this(function, function.getActualNode());
    }

    public Function(Function function, CFGNode actualNode) {
        this.functionName = function.functionName;
        this.fileName = function.fileName;
        this.actualNode = actualNode;
        for(FunctionState data : function.getFunctionStates()) {
            states.add(data.clone());
        }
    }

    public CFGNode getActualNode() {
        return actualNode;
    }
    
    public void setActualNode(CFGNode actualNode) {
        this.actualNode = actualNode;
    }

    public String getFileName() {
        return fileName;
    }

    public List<FunctionState> getFunctionStates() {
        return states;
    }

    @Override
    public String toString() {
        String result = states.toString();
        return result;
    }

    public void join(Function other) {
        logger.debug("Joining:\n\t"+this.getFunctionStates()+"\n and \n\t"
                                                    +other.getFunctionStates());
        for(FunctionState data : other.states) {
            if(!states.contains(data)) {
                states.add(data);
            }
        }
        this.mergeData();
    }

    public String getName() {
        return this.functionName;
    }
    
    /**
     * Method choose from FunctionState instance with most rules and set it as
     * selectedData. Every other data is now checked whether is subset of this
     * selectedData. If so, data is removed from dataList.
     */
    private void mergeData() {
        List<FunctionState> toRemove = new Vector<FunctionState>();
        int maximumRules=0;
        int markedIndex=0;
        FunctionState selectedState = null;
        FunctionState actualState = null;

        //Skip if there are not enough FunctionState objects
        if(getFunctionStates().size()<2)
            return;

        selectedState = getFunctionStates().get(0);
        //Choose FunctionState object with most rules
        for(int i = 1; i < getFunctionStates().size(); i++) {
            actualState = getFunctionStates().get(i);
            if(actualState.getRules().size()>maximumRules) {
                selectedState = actualState;
                maximumRules = selectedState.getRules().size();
                markedIndex=i;
            } else if(actualState.getRules().size()==maximumRules) {
                selectedState = actualState;
                markedIndex=i;
            }
        }

        for(int i = 0; i < getFunctionStates().size(); i++) {
            if(i == markedIndex)
                continue;
            if(selectedState.isSubset(getFunctionStates().get(i))) {
                toRemove.add(getFunctionStates().get(i));
            }
        }

        logger.info("Merging:");
        for(FunctionState state : getFunctionStates()) {
            logger.info("\t"+state.toString());
        }
        getFunctionStates().removeAll(toRemove);
        logger.info("To this:");
        for(FunctionState state : getFunctionStates()) {
            logger.info("\t"+state.toString());
        }
    }

    @Override
    public Function clone() {
      final Function clone = new Function(this);
      return clone;
    }
}
