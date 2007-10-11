package cz.muni.fi.iti.scv.pthreadchecker;

import cz.muni.fi.iti.scv.checker.*;
import cz.muni.fi.iti.scv.xml2cfg.*;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class PThreadChecker {
   
    private Map<String, ControlFlowGraph> cfgs;
    
    private Document document;

    public PThreadChecker(Document document) {
        
        this.document = document;
        cfgs = new HashMap<String, ControlFlowGraph>();
        
        //for every all function definitions from document
        for (Element functionDef : (List<Element>) document.selectNodes("//functionDefinition")) {         
            ControlFlowGraph cfg = new ControlFlowGraph(functionDef);  
           
            if (cfgs.put(cfg.getFunctionName(), cfg)!=null) {
                System.out.println("PThreadChecker: !!! document contains nested function definition with the same name !!!");
            }

        } 
        
    }
    
   
    public Set<CheckerError> check() {
        
        Set<CheckerError> errors = new HashSet<CheckerError>();
        
        //find all functions called via pthread_create
        for (Element pthreadCreate : (List<Element>) document.selectNodes("//functionCall[1 and id=\"pthread_create\"]/*[4]")) {         
            System.out.println("PThreadChecker: pthread_create starts " + pthreadCreate.getText());
        
        } 
        
        return errors;
    
    }
    
   
    
}
