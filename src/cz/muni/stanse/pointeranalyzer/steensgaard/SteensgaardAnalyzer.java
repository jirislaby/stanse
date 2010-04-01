package cz.muni.stanse.pointeranalyzer.steensgaard;

import cz.muni.stanse.codestructures.*;
import org.dom4j.*;
import java.util.*;

/**
 *
 * @author Michal Strehovsky
 */
public final class SteensgaardAnalyzer {

    // inside the type table: functions are represented as global variables
    // where type of the equivalence class is FunctionPointerType
    // global variables have CFGHandle == null, local variables have CFGHandle == handle of function
    // parameters of function are local variables
    // return value of functions is a local variable with name "__retVal"
    TypeTable typeTable = new TypeTable();

    final String returnVariableName = "__retVal";

    public void analyze(Collection<CFGHandle> cfgs)
    {
        for (CFGHandle cfg: cfgs)
        {
            addFunction(cfg);
        }

        for (CFGHandle cfg: cfgs)
        {
            //System.out.printf("*********** in function %s\n", cfg.getFunctionName());

            for (CFGNode node: cfg.getAllNodes())
            {
                if (!node.getElement().getName().equals("exit"))
                    handleStatement(cfg, node.getElement());
            }
        }

        typeTable.toDotFile();
    }
    
    private void addFunction(CFGHandle cfg)
    {
        Element functionDecl = cfg.getElement().element("declarator").element("functionDecl");

        // create a list of parameters
        EquivalenceClass[] parameters = new EquivalenceClass[functionDecl.elements().size()];
        for (int i = 0; i < parameters.length; i++)
        {
            // get the class representation of the parameter (the call will create it if neccessary)
            EquivalenceClass param = typeTable.getTypeOf(cfg,
                    ((Node)functionDecl.elements().get(i)).selectSingleNode("declarator/id").getText());

            parameters[i] = param;
        }

        // create a class for the return value
        EquivalenceClass returnValue = typeTable.getTypeOf(cfg, returnVariableName);

        // create a class for the function
        EquivalenceClass function = new EquivalenceClass<LocationPointerType>(
                new LocationPointerType(
                    new EquivalenceClass<LocationPointerType>("functiondummytau"),
                    new EquivalenceClass<FunctionPointerType>(
                        cfg.getFunctionName(),
                        new FunctionPointerType(parameters, returnValue))));

        // add the function to the type table
        typeTable.addFunction(cfg, function);
    }

    public EvaluatedType handleAssignment(CFGHandle cfg, Element lhs, Element rhs)
    {
        EvaluatedType lhsType = handleExpression(cfg, lhs);
        EvaluatedType rhsType = handleExpression(cfg, rhs);

        lhsType.join(rhsType);

        return lhsType;
    }

    private EquivalenceClass<LocationPointerType> getSymbolClass(CFGHandle cfg, String name)
    {
        EquivalenceClass<LocationPointerType> idClass;

        if (cfg.isSymbolLocal(name))
        {
            idClass = typeTable.getTypeOf(cfg, name);
        }
        else
        {
            idClass = typeTable.getTypeOf(name);
        }

        return idClass;
    }

    public EvaluatedType handleId(CFGHandle cfg, Element id)
    {
        EquivalenceClass<LocationPointerType> idClass = getSymbolClass(cfg, id.getText());

        return new EvaluatedType(idClass.getType().getTau(), idClass.getType().getLambda());
        
    }

    public EvaluatedType handleIntConst(CFGHandle cfg, Element intConst)
    {
        return new EvaluatedType(
                new EquivalenceClass<LocationPointerType>("intConst.tau"),
                new EquivalenceClass<FunctionPointerType>("intConst.lambda"));
    }

    public EvaluatedType handleStringConst(CFGHandle cfg, Element stringConst)
    {
        return new EvaluatedType(
                new EquivalenceClass<LocationPointerType>("stringConst.tau"),
                new EquivalenceClass<FunctionPointerType>("stringConst.lambda"));
    }

    public EvaluatedType handleAddrExpression(CFGHandle cfg, Element addrExpression)
    {
        // this can be either address of a varible or address of a field in an array
        Element addressedExpression = (Element)addrExpression.selectSingleNode("*[1]");
        if (addressedExpression.getName().equals("arrayAccess"))
        {
            // evaluate the type of array accessor (we don't really care about it, but it might have side effects)
            handleExpression(cfg, (Element)addressedExpression.selectSingleNode("*[2]"));

            // the real addressed variable is this
            addressedExpression = (Element)addressedExpression.selectSingleNode("*[1]");
        }

        assert addressedExpression.getName().equals("id");

        String name = addressedExpression.getText();
        EquivalenceClass<LocationPointerType> idClass = getSymbolClass(cfg, name);

        return new EvaluatedType(idClass, new EquivalenceClass<FunctionPointerType>("addrExpressionDummyLambda"));
    }

    public EvaluatedType handleArrayAccess(CFGHandle cfg, Element arrayAccess)
    {
        // we don't really care about the accessor, but it might have side effects, so evaluate it
        handleExpression(cfg, (Element)arrayAccess.selectSingleNode("*[2]"));

        return handleExpression(cfg, (Element)arrayAccess.selectSingleNode("*[1]"));
    }

    public EvaluatedType handleDerefExpression(CFGHandle cfg, Element derefExpression)
    {
        EvaluatedType expr = handleExpression(cfg, (Element)derefExpression.selectSingleNode("*[1]"));

        return expr.dereference();
    }

    public EvaluatedType handleFunctionCall(CFGHandle cfg, Element functionCall)
    {
        EvaluatedType refFunction = handleExpression(cfg, (Element)functionCall.selectSingleNode("*[1]"));

        EquivalenceClass<FunctionPointerType> function = refFunction.getLambda();

        int paramCount = functionCall.nodeCount() - 1;

        // calling a function without any knowledge about the called function?
        if (function.getType() == null)
        {
            EquivalenceClass<LocationPointerType> params[] = new EquivalenceClass[paramCount];
            for (int i = 0; i < params.length; i++) {
                params[i] = new EquivalenceClass<LocationPointerType>("unknownFunctionCallParam",
                                new LocationPointerType(
                                    new EquivalenceClass<LocationPointerType>("unknownFunctionCallParam.tau"),
                                    new EquivalenceClass<FunctionPointerType>("unknownFunctionCallParam.lambda")));
            }

            EquivalenceClass<LocationPointerType> returnValue =
                    new EquivalenceClass<LocationPointerType>("unknownFunctionCallRetval",
                        new LocationPointerType(
                            new EquivalenceClass<LocationPointerType>("unknownFunctionCallRetval.tau"),
                            new EquivalenceClass<FunctionPointerType>("unknownFunctionCallRetval.lambda")));

            function.setType(new FunctionPointerType(params, returnValue));
        }

        FunctionPointerType functionType = function.getType();
        List<EquivalenceClass<LocationPointerType>> parameterTypes = functionType.getParameterTypes();

        for (int i = 1; i < functionCall.elements().size(); i++)
        {
            EvaluatedType parameterVariable = new EvaluatedType(
                parameterTypes.get(i - 1).getType().getTau(),
                parameterTypes.get(i - 1).getType().getLambda());

            parameterVariable.join(handleExpression(cfg, (Element)functionCall.elements().get(i)));
        }

        return new EvaluatedType(
            functionType.getReturnType().getType().getTau(),
            functionType.getReturnType().getType().getLambda());
    }

    public EvaluatedType handlePostfixExpression(CFGHandle cfg, Element postfixExpression)
    {
        return handleExpression(cfg, (Element)postfixExpression.elements().get(0));
    }

    public EvaluatedType handleArrowExpression(CFGHandle cfg, Element arrowExpression)
    {
        EvaluatedType accessedLocation = handleExpression(cfg, (Element)arrowExpression.elements().get(0));
        return accessedLocation.dereference();
    }

    public EvaluatedType handleCommaExpression(CFGHandle cfg, Element commaExpression)
    {
        // evaluate the left side because it might have side effects
        handleExpression(cfg, (Element)commaExpression.elements().get(0));

        // evaluate the right side and return evaluated type
        return handleExpression(cfg, (Element)commaExpression.elements().get(1));
    }

    public EvaluatedType handleConditionalExpression(CFGHandle cfg, Element conditionalExpression)
    {
        handleExpression(cfg, (Element)conditionalExpression.elements().get(0));

        return handleAssignment(cfg,
                (Element)conditionalExpression.elements().get(1),
                (Element)conditionalExpression.elements().get(2));
    }

    public EvaluatedType handleExpression(CFGHandle cfg, Element expr)
    {
        //System.out.printf("eval: %s\n", expr.getName());
        
        if (expr.getName().equals("initDeclarator")) {
            return handleAssignment(cfg,
                    (Element)expr.selectSingleNode("declarator/id"),
                    (Element)expr.selectSingleNode("initializer/*[1]"));
        }

        if (expr.getName().equals("assignExpression")) {
            return handleAssignment(cfg,
                    (Element)expr.selectSingleNode("*[1]"),
                    (Element)expr.selectSingleNode("*[2]"));
        }

        if (expr.getName().equals("id")) {
            return handleId(cfg, expr);
        }

        if (expr.getName().equals("arrayAccess")) {
            return handleArrayAccess(cfg, expr);
        }

        if (expr.getName().equals("intConst") || expr.getName().equals("sizeofExpression")) {
            return handleIntConst(cfg, expr);
        }

        if (expr.getName().equals("stringConst")) {
            return handleStringConst(cfg, expr);
        }

        if (expr.getName().equals("addrExpression")) {
            return handleAddrExpression(cfg, expr);
        }

        if (expr.getName().equals("derefExpression")) {
            return handleDerefExpression(cfg, expr);
        }

        if (expr.getName().equals("binaryExpression")) {
            // binary expression works just like an assignment for Steensgaard
            return handleAssignment(cfg,
                    (Element)expr.selectSingleNode("*[1]"),
                    (Element)expr.selectSingleNode("*[2]"));
        }

        if (expr.getName().equals("functionCall")) {
            return handleFunctionCall(cfg, expr);
        }

        if (expr.getName().equals("postfixExpression")) {
            return handlePostfixExpression(cfg, expr);
        }

        if (expr.getName().equals("prefixExpression")) {
            // it's cheating, but this is the same case as prefixExpression
            return handlePostfixExpression(cfg, expr);
        }

        if (expr.getName().equals("dotExpression")) {
            // cheating, but it's the same thing again
            return handlePostfixExpression(cfg, expr);
        }

        if (expr.getName().equals("arrowExpression")) {
            return handleArrowExpression(cfg, expr);
        }

        if (expr.getName().equals("commaExpression")) {
            return handleCommaExpression(cfg, expr);
        }

        if (expr.getName().equals("conditionalExpression")) {
            return handleConditionalExpression(cfg, expr);
        }


        throw new UnsupportedOperationException(expr.getName());
    }

    public void handleReturnStatement(CFGHandle cfg, Element returnStatement)
    {
        // don't care about return, unless it returns something
        if (returnStatement.elements().size() == 0) {
            return;
        }

        FunctionPointerType containingFunction =
                typeTable.getTypeOf(cfg.getFunctionName()).getType().getLambda().getType();

        EvaluatedType returnVariable = new EvaluatedType(
                containingFunction.getReturnType().getType().getTau(),
                containingFunction.getReturnType().getType().getLambda());

        returnVariable.join(handleExpression(cfg, (Element)returnStatement.selectSingleNode("*[1]")));
    }

    public void handleStatement(CFGHandle cfg, Element statement)
    {
        if (statement.getName().equals("returnStatement")) {
            handleReturnStatement(cfg, statement);
            return;
        }

        if (statement.getName().equals("emptyStatement")) {
            return;
        }

        if (statement.getName().equals("breakStatement")) {
            return;
        }

        if (statement.getName().equals("assert")) {
            return;
        }

        // probably an expression statement
        handleExpression(cfg, statement);
    }



}
