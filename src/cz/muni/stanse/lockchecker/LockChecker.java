package cz.muni.stanse.lockchecker;

import org.apache.log4j.Logger;

import cz.muni.stanse.checker.Checker;
import cz.muni.stanse.checker.CheckerErrorReceiver;
import cz.muni.stanse.checker.CheckerException;
import cz.muni.stanse.checker.CheckerProgressMonitor;
import cz.muni.stanse.checker.CheckingResult;
import cz.muni.stanse.checker.CheckingSuccess;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.LazyInternalStructures;

/**
 * Lock Checker class used to find possible locking errors
 * 
 * 
 * @author Radim Cebis
 *
 */
public class LockChecker extends Checker {
   
    private Configuration conf;
    
    private final static Logger logger =
        Logger.getLogger(SummariesBuilder.class.getName());

    
	/**
	 * Constructs the Lock Checker using given configuration
	 * @param conf Configuration
	 */
	public LockChecker(Configuration conf) {
		this.conf = conf;
	}

	@Override
    public CheckingResult check(
                        final LazyInternalStructures internals,
                        final CheckerErrorReceiver errReciver,
                        final CheckerProgressMonitor monitor)
                                                       throws CheckerException {
    	
    	SummariesBuilder summariesBuilder = new SummariesBuilder(internals.getNodeToCFGdictionary(), internals.getArgumentPassingManager(), conf);
    	for(CFGHandle handle : internals.getStartFunctions()) {
    		summariesBuilder.traverse(handle.getStartNode(), internals.getNavigator(), new State());
    	}
    	
    	Summaries sum = summariesBuilder.getSummaries();
    	CheckerErrorFilter filter = new CheckerErrorFilter();
    	
    	
    	if(conf.onlyTopFunctions()) {
    		for(CFGHandle handle : internals.getStartFunctions()) {
	    		FunctionSummary functionSummary = sum.get(handle.getStartNode());
	    		for( FunctionStateSummary fss : functionSummary.getFunctionStateSummaries()) {
	    			ErrorGenerator generator = new ErrorGenerator(fss, conf.countFlows(), conf.countPairs(), conf.getThreshold(), conf.generateMoreLocksErrors());
	        		generator.generateErrors(filter);
	        		if(conf.generateDoubleErrors())
	        			fss.getErrHolder().save(errReciver);
	    		}
	    	}
    	} else {    		
    		for(FunctionStateSummary fss : sum.getAllFunctionStateSummaries()) {
	    		ErrorGenerator generator = new ErrorGenerator(fss, conf.countFlows(), conf.countPairs(), conf.getThreshold(), conf.generateMoreLocksErrors());
	    		generator.generateErrors(filter);
	    		if(conf.generateDoubleErrors())
        			fss.getErrHolder().save(errReciver);
	    	}	    	
    	}
    	
    	filter.generateErrors(errReciver);
    	
	logger.debug(sum);
    	
        return new CheckingSuccess();
    }    
    
    @Override
    public String getName() {
        return "Lock Checker for finding variables which should be locked but are not";
    }
}
