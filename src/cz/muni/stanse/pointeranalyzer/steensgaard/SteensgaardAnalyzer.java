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
    // return value of functions is a local variable with name @
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
        Element functionDefinition = cfg.getElement();
        Element declarator = (Element)functionDefinition.selectSingleNode("declarator");
        Element functionDecl = (Element)declarator.selectSingleNode("functionDecl");

        // create a list of parameters
        EquivalenceClass[] parameters = new EquivalenceClass[functionDecl.elements().size()];
        for (int i = 0; i < functionDecl.elements().size(); i++)
        {
            // get the class representation of the parameter (the call will create it if neccessary)
            EquivalenceClass param = typeTable.getTypeOf(cfg,
                    ((Node)functionDecl.elements().get(i)).selectSingleNode("descendant::id").getText());

            parameters[i] = param;
        }

        // create a class for the return value
        EquivalenceClass returnValue = typeTable.getTypeOf(cfg, returnVariableName);

        // create a class for the function
        EquivalenceClass function = new EquivalenceClass(
                new LocationPointerType(
                    new EquivalenceClass("functiondummytau"),
                    new EquivalenceClass(
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

    private EquivalenceClass getSymbolClass(CFGHandle cfg, String name)
    {
        EquivalenceClass idClass;

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
        EquivalenceClass idClass = getSymbolClass(cfg, id.getText());

        if (idClass.getType() instanceof LocationPointerType)
        {
            LocationPointerType pt = (LocationPointerType)idClass.getType();
            return new EvaluatedType(pt.getTau(), pt.getLambda());
        }
        else
        {
            throw new UnsupportedOperationException();
        }
    }

    public EvaluatedType handleIntConst(CFGHandle cfg, Element intConst)
    {
        return new EvaluatedType(new EquivalenceClass("intConst.tau"), new EquivalenceClass("intConst.lambda"));
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
        EquivalenceClass idClass = getSymbolClass(cfg, name);

        return new EvaluatedType(idClass, new EquivalenceClass("addrExpressionDummyLambda"));
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

        EquivalenceClass function = refFunction.getLambda();

        int paramCount = functionCall.nodeCount() - 1;

        // calling a function without any knowledge about the called function?
        if (function.getType() == null)
        {
            EquivalenceClass params[] = new EquivalenceClass[paramCount];
            for (int i = 0; i < params.length; i++) {
                params[i] = new EquivalenceClass("unknownFunctionCallParam",
                                new LocationPointerType(
                                    new EquivalenceClass("unknownFunctionCallParam.tau"),
                                    new EquivalenceClass("unknownFunctionCallParam.lambda")));
            }

            EquivalenceClass returnValue = new EquivalenceClass("unknownFunctionCallRetval",
                    new LocationPointerType(
                        new EquivalenceClass("unknownFunctionCallRetval.tau"),
                        new EquivalenceClass("unknownFunctionCallRetval.lambda")));

            function.setType(new FunctionPointerType(params, returnValue));
        }

        FunctionPointerType functionType = (FunctionPointerType)function.getType();
        List<EquivalenceClass> parameterTypes = functionType.getParameterTypes();

        for (int i = 1; i < functionCall.elements().size(); i++)
        {
            EvaluatedType parameterVariable = new EvaluatedType(
                ((LocationPointerType)parameterTypes.get(i - 1).getType()).getTau(),
                ((LocationPointerType)parameterTypes.get(i - 1).getType()).getLambda());

            parameterVariable.join(handleExpression(cfg, (Element)functionCall.elements().get(i)));
        }

        return new EvaluatedType(
            ((LocationPointerType)functionType.getReturnType().getType()).getTau(),
            ((LocationPointerType)functionType.getReturnType().getType()).getLambda());
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

    public EvaluatedType handleExpression(CFGHandle cfg, Element expr)
    {
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

        if (expr.getName().equals("intConst")) {
            return handleIntConst(cfg, expr);
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


        throw new UnsupportedOperationException(expr.getName());
    }

    public void handleReturnStatement(CFGHandle cfg, Element returnStatement)
    {
        // don't care about return, unless it returns something
        if (returnStatement.elements().size() == 0) {
            return;
        }

        FunctionPointerType containingFunction =
                (FunctionPointerType)((LocationPointerType)typeTable.getTypeOf(
                    cfg.getFunctionName()).getType()).getLambda().getType();

        EvaluatedType returnVariable = new EvaluatedType(
                ((LocationPointerType)containingFunction.getReturnType().getType()).getTau(),
                ((LocationPointerType)containingFunction.getReturnType().getType()).getLambda());

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

        if (statement.getName().equals("assert")) {
            // TODO: wtf is this?
            return;
        }

        // probably an expression statement
        handleExpression(cfg, statement);
    }



}
