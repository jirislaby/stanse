
package cz.muni.stanse.threadchecker;

import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.threadchecker.locks.BackTrack;
import cz.muni.stanse.threadchecker.locks.Lock;
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
                                                            Element parameter) {
        String lockName;
        String description;
        BackTrack backTrackNode;

        lockName = CodeAnalyzer.parseStringVariable(parameter);
        logger.info("Locking function detected - locking "+lockName);

        //Add new lock to every possibilty Data state
        for(FunctionState data : function.getFunctionStates()) {
            description = "locking "+lockName
                                    +" - already locked:"+ data.getLockStack();
            data.lockUp(lockName, node);
            backTrackNode = data.getBackTrack().peekLast();

            //Set description to triple node (history of traversing)
            if(backTrackNode.getCFGNodeID().equals(node.getNumber())) {
                backTrackNode.setDescription(description);
            } else {
                backTrackNode = new BackTrack(node.getNumber(),node.getLine(),
                                           description,function.getFileName());
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
                                    Element parameter) {
        String lockName;
        String description;
        BackTrack backTrackNode;
        lockName = CodeAnalyzer.parseStringVariable(parameter);
        logger.info("Unlocking function detected - unlocking "+lockName);

        for(FunctionState data : function.getFunctionStates()) {
            description = "unlocking "+lockName
                                    + " - still locked: "+data.getLockStack();
            data.lockDown(lockName);

            backTrackNode = data.getBackTrack().peekLast();
            if(backTrackNode.getCFGNodeID().equals(node.getNumber())) {
                backTrackNode.setDescription(description);
            } else {
                backTrackNode = new BackTrack(node.getNumber(),node.getLine(),
                                            description,function.getFileName());
                data.getBackTrack().addLast(backTrackNode);
            }
        }
    }


    /**
     * Function get callee's name and look up, whether it's already analysed,
     * if not, function executes analyseCFG and results is stitched with caller.
     * @param node CFGNode where function get Element
     * @param caller Function which CFGNode represents callee's execution
     * @param parameter Element  where callee name is included.
     */
    public static void analyzeFunction(CFGNode node, Function caller,
                                                            Element parameter) {
        Function callee = null;
        BackTrack backTrackNode;
        FunctionState dataBeforeStitch = null;
        List<FunctionState> dataToRemove = new Vector<FunctionState>();
        List<FunctionState> dataToAdd = new Vector<FunctionState>();
        String description;
        boolean alreadyInserted = false;
        
        callee = CodeAnalyzer.getCallee(parameter, caller.getName());
        if(callee == null) {
            String functionCall = CodeAnalyzer.parseStringVariable(parameter);
            logger.info("Can't find CFG for functionCall "+functionCall
                                            +" in function: "+caller.getName());
            return;
        }

        logger.info("Stitching caller ("+caller.getName()
                                    +") and callee ("+callee.getName()+"):");
        logger.info("=============================================");
        logger.info("Caller:"+caller);
        logger.info("Callee:"+callee);

        /*if Data from callee contains something usefull (rules, threads,
        *locked locks) function executes stitchFunctions on dataCalle and 
        * dataCaller and also generate proper backTrack nodes
        */
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
                                                    node.getLine(),description,
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
                                                        description,
                                                        callee.getFileName());
                dataCaller.getBackTrack().addLast(backTrackNode);

            }
            alreadyInserted = false;
        }

        //Remove old data and insert newly created
        caller.getFunctionStates().removeAll(dataToRemove);
        caller.getFunctionStates().addAll(dataToAdd);
        logger.info("After:"+caller);
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
    private static Function getCallee(Element parameter, String callerName) {
        String functionCall;
        Function callee;
        CFG cfg;

        functionCall = CodeAnalyzer.parseStringVariable(parameter);
        logger.info("Analyzing functionCall "+functionCall);
        callee = settings.getFunction(functionCall);

        if(callee == null) {
            logger.info("Callee "+functionCall
                                            +" is new function analysing CFG");
            cfg = settings.getCFG(functionCall);
            if(cfg == null) {
                return null;
            }
            if(!settings.isOnStack(cfg)) {
                callee = CFGTransit.analyseCFG(cfg);
                settings.addFunction(callee,cfg);
            } else {
                callee = new Function(cfg);
            }
        } else {
            logger.info("Using for calle "+functionCall+"function cache");
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
                                                            Element parameter) {
        ThreadInfo thread;
        String threadFunction;
        BackTrack backTrackNode;
        CFG cfg;

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
            thread = new ThreadInfo(cfg);
            settings.addThread(thread);
        }

        for(FunctionState data : function.getFunctionStates()) {
            backTrackNode = data.getBackTrack().peekLast();
            if(backTrackNode.getCFGNodeID().equals(node.getNumber())) {
                backTrackNode.setDescription("creating thread ("
                                            +thread.getFunctionName()+")");
            } else {
                backTrackNode = new BackTrack(node.getNumber(),node.getLine(),
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
            Node funcName = node.selectSingleNode("id[1]");
            List<Node> paramsNodes = node.selectNodes("*[position()!=1]");
            String params = "";

            if(!paramsNodes.isEmpty()) { //First argument is without comma
                params = parseStringVariable(paramsNodes.get(0));
            }
            for(int index=1; index < paramsNodes.size(); index++) {
                params += ","+parseStringVariable(paramsNodes.get(index));
            }
            return funcName.getText()+"("+params+")";
        }

        return null;
    }
}
