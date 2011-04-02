
package cz.muni.stanse.threadchecker;

import cz.muni.stanse.checker.CheckerException;
import cz.muni.stanse.checker.CheckerProgressMonitor;
import cz.muni.stanse.codestructures.CFGHandle;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.threadchecker.locks.BackTrack;
import cz.muni.stanse.threadchecker.locks.Lock;
import cz.muni.stanse.threadchecker.locks.LockingException;
import java.util.List;
import java.util.Vector;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.dom4j.Node;


/**
 * Class with static methods designed to analyse Dom4J Element and perform
 * appropriate actions like creating new thread, execute function analysis
 * and "stitch" caller and calle (see analyzeFunction) or marking lock as
 * free/locked etc.
 * @author Jan Kučera
 */
public class CodeAnalyzer {
    private static CheckerSettings settings = CheckerSettings.getInstance();
    private final static Logger logger
                               = Logger.getLogger(CodeAnalyzer.class.getName());

    /**
     * Function extract lock's name and execute lockUp method from 
     * FunctionsState. Also correct backTrack info for this command.
     * @param node CFGNode is added to backTrack history of traversing
     * @param state Function where all realized changes are inserted
     * @param parameter Element where lockname is included.
     */
    static void analyzeLockingFunction(CFGNode node, Function function,
	    Element parameter) throws CheckerException {
        String lockName;
        String description;
        BackTrack backTrackNode;

        lockName = CodeAnalyzer.parseStringVariable(parameter);
        logger.debug("Locking function detected - locking "+lockName);

        //Add new lock to every possibilty Data state
        for(FunctionState data : function.getFunctionStates()) {
            description = "locking "+lockName
                                    +" - already locked:"+ data.getLockStack();
	    try {
		data.lockUp(lockName, node);
	    } catch (final LockingException e) {
		throw new CheckerException("A problem with unlocking " +
			"detected. Probably an imbalanced lock.", e);
	    }
            backTrackNode = data.getBackTrack().peekLast();

            //Set description to triple node (history of traversing)
            if(backTrackNode.getCFGNodeID().equals(node.getNumber())) {
                backTrackNode.setDescription(description);
            } else {
                backTrackNode = new BackTrack(node.getNumber(),node.getLine(),
                                              node.getColumn(),description,
                                              function.getFileName());
                data.getBackTrack().addLast(backTrackNode);
            }
        }

    }


    /**
     * Function extract lock's name and execute lockDown method from
     * FunctionsState. Also correct backTrack info for this command.
     * @param node CFGNode is added to backTrack history of traversing
     * @param state  Function where all realized changes are inserted
     * @param parameter Element where lockname is included.
     */
    static void analyzeUnlockingFunction(CFGNode node, Function function,
	    Element parameter) throws CheckerException {
        String lockName;
        String description;
        BackTrack backTrackNode;
        lockName = CodeAnalyzer.parseStringVariable(parameter);
        logger.debug("Unlocking function detected - unlocking "+lockName);

        for(FunctionState data : function.getFunctionStates()) {
	    try {
		data.lockDown(lockName);
	    } catch (final LockingException e) {
		throw new CheckerException("A problem with locking " +
			"detected. Probably an imbalanced lock.", e);
	    }
            description = "unlocking "+lockName
                                    + " - still locked: "+data.getLockStack();

            backTrackNode = data.getBackTrack().peekLast();
            if(backTrackNode.getCFGNodeID().equals(node.getNumber())) {
                backTrackNode.setDescription(description);
            } else {
                backTrackNode = new BackTrack(node.getNumber(),node.getLine(),
                                              node.getColumn(),description,
                                              function.getFileName());
                data.getBackTrack().addLast(backTrackNode);
            }
        }
    }


    /**
     * Gets callee's name and looks up whether it's already analysed,
     * if not, it executes analyseCFG and result is stitched with caller.
     * @param node CFGNode where function gets an Element
     * @param caller Function which CFGNode represents callee's execution
     * @param parameter Element  where callee name is included.
     */
    public static void analyzeFunction(CFGNode node, Function caller,
	    Element parameter, final CheckerProgressMonitor mon)
	    throws CheckerException {
        Function callee = null;
        BackTrack backTrackNode;
        FunctionState dataBeforeStitch = null;
        List<FunctionState> dataToRemove = new Vector<FunctionState>();
        List<FunctionState> dataToAdd = new Vector<FunctionState>();
        String description;
        boolean alreadyInserted = false;
        
        callee = CodeAnalyzer.getCallee(parameter, caller.getName(), mon);
        if(callee == null) {
            String functionCall = CodeAnalyzer.parseStringVariable(parameter);
            logger.debug("Can't find CFG for functionCall " + functionCall +
		    " in function: " + caller.getName());
            return;
        }

        logger.debug("Stitching caller ("+caller.getName()
                                    +") and callee ("+callee.getName()+"):");
        logger.debug("=============================================");
        logger.debug("Caller:"+caller);
        logger.debug("Callee:"+callee);

        /*if Data from callee contains something usefull (rules, threads,
        *locked locks) function executes stitchFunctions on dataCalle and 
        * dataCaller and also generate proper backTrack nodes
        */
	long states = caller.getFunctionStates().size() *
		callee.getFunctionStates().size();
	if (states > 5000) {
		throw new CheckerException("Too many states (" + states +
			") when trying to stitch " + callee.getName() + " into " +
			caller.getName() + ".\nIsn't there a locking imbalance?\n");
	}
        for(FunctionState dataCaller : caller.getFunctionStates()) {
            for(FunctionState dataCallee : callee.getFunctionStates()) {
                if(dataCallee.isEmpty()) {
                    continue;       //in dataCalle isn't anything useful
                }
                dataCallee = dataCallee.clone(); // make clone of data

                //Mark copy of this - set original FunctionState
                if(!alreadyInserted) {
                    alreadyInserted = true;
                    dataBeforeStitch = dataCaller;
                    dataCaller = dataCaller.clone();
                    dataToRemove.add(dataBeforeStitch);
                } else {
                    dataCaller = dataBeforeStitch.clone();
                }

                backTrackNode = dataCaller.getBackTrack().peekLast();

                //Edit backTrack description or create new record
                description = "calling function "+callee.getName()
                            +" - already locked:"+dataCaller.getLockStack();

                if(backTrackNode.getCFGNodeID().equals(node.getNumber())) {
                    backTrackNode.setDescription(description);
                } else {
                    backTrackNode = new BackTrack(node.getNumber(),
                                                  node.getLine(),
                                                  node.getColumn(),description,
                                                  callee.getFileName());
                    dataCaller.getBackTrack().addLast(backTrackNode);
                }

                //Stitch functions
                dataCaller.stitchFunctions(dataCallee);
                dataToAdd.add(dataCaller);

                //Add backTrack record showing return from calle function
                description = "return from "+callee.getName()
                            +" - already locked:"+dataCaller.getLockStack();
                backTrackNode = new BackTrack(node.getNumber(),node.getLine(),
                                              node.getColumn(),description,
                                              callee.getFileName());
                dataCaller.getBackTrack().addLast(backTrackNode);

            }
            alreadyInserted = false;
        }

        //Remove old data and insert newly created
        caller.getFunctionStates().removeAll(dataToRemove);
        caller.getFunctionStates().addAll(dataToAdd);
        logger.debug("After:" + caller);
    }

    /**
     * Method parse parameter to get functionName and look up in
     * CheckerSettings, whether this calle has instance Function, if not, gets
     * it's CFG and exectu CFGTransit.
     * @param parameter Element contains callee name
     * @param callerName String 
     * @return Function callee
     * @throws NoCFGException whether CFG for callee doesn't exist
     */
    private static Function getCallee(Element parameter, String callerName,
	    final CheckerProgressMonitor mon) throws CheckerException {
        String functionCall;
        Function callee;
        CFGHandle cfg;

        functionCall = CodeAnalyzer.parseStringVariable(parameter);
        logger.debug("Analysing functionCall: " + functionCall);
        callee = settings.getFunction(functionCall);

        if(callee == null) {
            logger.debug("Callee " + functionCall +
		    " is a new function, analysing CFG");
            cfg = settings.getCFG(functionCall);
            if(cfg == null) {
                return null;
            }
            if(!settings.isOnStack(cfg)) {
                callee = CFGTransit.analyseCFG(cfg, mon);
                settings.addFunction(callee,cfg);
            } else {
                callee = new Function(cfg);
            }
        } else {
            logger.debug("Using function cache for callee " + functionCall);
        }
        return callee;
    }

    /**
     * Function parse Element parameter where it find name of the
     * function. Then look up for whether thread already exits, if not, gets CFG
     * and create new ThreadInfo.
     * @param node CFGNode
     * @param function Function
     * @param parameter Element
     */
    static void analyzeThreadFunction(CFGNode node, Function function,
	    Element parameter, final CheckerProgressMonitor mon) {
        ThreadInfo thread;
        String threadFunction;
        BackTrack backTrackNode;
        CFGHandle cfg;

        threadFunction = CodeAnalyzer.parseStringVariable(parameter);
        logger.info("Creating thread ("+threadFunction+") detected");
        thread = settings.getThread(threadFunction);
        if(thread == null) {
            cfg = settings.getCFG(threadFunction);
            if(cfg == null) {
                logger.error("Can't find CFG for building a thread "
                                                               +threadFunction);
                return;
            }
            thread = new ThreadInfo(cfg, mon);
            settings.addThread(thread);
        }

        for(FunctionState data : function.getFunctionStates()) {
            backTrackNode = data.getBackTrack().peekLast();
            if(backTrackNode.getCFGNodeID().equals(node.getNumber())) {
                backTrackNode.setDescription("creating thread ("
                                            +thread.getFunctionName()+")");
            } else {
                backTrackNode = new BackTrack(node.getNumber(),node.getLine(),
                                              node.getColumn(),
                                              "creating thread ("+
                                                   thread.getFunctionName()+")",
                                              function.getFileName());
                data.getBackTrack().addLast(backTrackNode);
            }
        }

    }
    /**
     * Returns string representation of XML element, which represents some
     * variable in source code.
     * @param node
     * @return String name of variable
     */
    public static String parseStringVariable(Node node) {
        Lock var;
        String name;

        if(node.getName().equals("id")) {
           return node.getText();
        }
        if(node.getName().equals("arrowExpression")) {
            List<Node> args = node.selectNodes("*");

            String rName = CodeAnalyzer.parseStringVariable(args.get(0));
            String lname = args.get(1).getText();

            return rName+"->"+lname;
        }
        if(node.getName().equals("addrExpression")) {
            return parseStringVariable(node.selectSingleNode("*"));
        }

        if(node.getName().equals("castExpression")) {
            List<Node> args = node.selectNodes("*");
            String rName = CodeAnalyzer.parseStringVariable(args.get(1));
            return rName;
        }

        if(node.getName().equals("dotExpression")) {
            List<Node> args = node.selectNodes("*");

            String rName = CodeAnalyzer.parseStringVariable(args.get(0));
            String lname = args.get(1).getText();

            return rName+"."+lname;
        }
        if(node.getName().equals("arrayAccess")) {
            List<Node> args = node.selectNodes("*");

            String lname=CodeAnalyzer.parseStringVariable(args.get(0));
            return lname+"[]";
        }
        if(node.getName().equals("stringConst")) {
            return node.getText();
        }
        if(node.getName().equals("intConst")) {
            return node.getText();
        }
        if(node.getName().equals("sizeofExpression")) {
            return "";
        }

        if(node.getName().equals("derefExpression")) {
            Node arg = node.selectSingleNode("*");
            return "*("+parseStringVariable(arg)+")";
        }

        if(node.getName().equals("functionCall")) {
            final Node funcName = node.selectSingleNode("id[1]");
            final List<Node> paramsNodes = node.selectNodes("*[position()!=1]");
            final StringBuilder fun = new StringBuilder(funcName.getText());
	    boolean first = true;

	    fun.append('(');
            for (final Node param : paramsNodes) {
		    if (!first)
			    fun.append(',');
		    fun.append(parseStringVariable(param));
		    first = false;
            }
	    fun.append(')');

            return fun.toString();
        }

        return null;
    }
}
