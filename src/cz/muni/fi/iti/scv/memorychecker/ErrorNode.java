package cz.muni.fi.iti.scv.memorychecker;

import cz.muni.fi.iti.scv.xml2cfg.*;

public class ErrorNode {
    
    private CFGNode node;
    
    public enum ErrorType {POSSIBLE, DEFINITIVE, DOUBLE_FREED, UNKNOWN_FREED, IS_NULL_FREED, NOT_NULL_RETURN, FREED_RETURN, UNKNOWN_RETURN};
    
    private ErrorType type;
    
    public ErrorNode(CFGNode node, ErrorType type) {
        this.node = node;
        this.type = type;
    }
    
    public String toString() {
        String output;
        if (type == ErrorType.DOUBLE_FREED) {
            output = "Double freed";
        } else if (type == ErrorType.UNKNOWN_FREED) {
            output = "Unknown->freed";
        } else if (type == ErrorType.IS_NULL_FREED) {
            output = "Is_null->freed";
        } else if (type == ErrorType.NOT_NULL_RETURN) {
            output = "Not_null->return";
        } else if (type == ErrorType.FREED_RETURN) {
            output = "Freed return";
        } else if (type == ErrorType.UNKNOWN_RETURN) {
            output = "Unknown->return";
        } else if (type == ErrorType.DEFINITIVE) {
            output = "Definitive";
        } else {
            output = "Possible";
        }
        output += " error in CFG <" + node.getCFG().toString() + "> node <" + node.getNumber() + ">";
        return output;
    }
    
}