package cz.muni.stanse.threadchecker.config;

import cz.muni.stanse.threadchecker.*;
import cz.muni.stanse.codestructures.Unit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;

/**
 * Class creates configuration file.
 * @author Jan Kučera
 */
public class ConfigurationCreator {
    private final static Logger logger =
                                Logger.getLogger(ThreadChecker.class.getName());
    private String lockRegexp;
    private String unlockRegexp;
    private String threadRegexp;
    private Pattern lockPattern;
    private Pattern unlockPattern;
    private Pattern threadPattern;
    private Document doc;
    private final Set<String> foundCalls = new HashSet<String>();
    private final List<String> declaredFunctions = new Vector<String>();
    private Unit unit;
    private String filename;
    
    /**
     * Method opens file and creates there default XML elements, if file exists,
     * doesn't create elements and store already defined function calls.
     * @throws org.dom4j.DocumentException
     */
    private void init() throws DocumentException {
        File document = new File(filename);
        if(document.exists()) {
            try {
                doc = (new org.dom4j.io.SAXReader()).read(document);
                List<Element> calls = doc.getRootElement().selectNodes(
                                   "patterns//functionCall/id");
                for(Element el : calls) {
                    this.foundCalls.add(el.getText());
                }
            } catch(DocumentException ex) {
                logger.error(ex);
            }
        } else {
            doc = DocumentHelper.createDocument();
            Element root = new DefaultElement("threadChecker");
            root.addElement("analyseType").addAttribute("type", "local");
            root.addElement("configurationBuilder");
            root.addElement("patterns");
            doc.setRootElement(root);
            this.writeDocument(doc, filename);
        }
    }

    public ConfigurationCreator(List<Element> configuration) 
                                                      throws DocumentException {
        Element element;
        for(int index = 0; index < configuration.size(); index++) {
            element = configuration.get(index);
            if(element.getName().equals("file"))
                this.filename = element.getText();
            if(element.getName().equals("lockingRegexp"))
                this.lockRegexp = element.getText();
            if(element.getName().equals("unlockingRegexp"))
                this.unlockRegexp = element.getText();
            if(element.getName().equals("threadRegexp"))
                this.threadRegexp = element.getText();
        }
        try {
            this.lockPattern = Pattern.compile(lockRegexp);
            this.unlockPattern= Pattern.compile(unlockRegexp);
            this.threadPattern = Pattern.compile(threadRegexp);
        } catch (PatternSyntaxException ex) {
            throw new DocumentException("Wrong settings:" +ex);
        }
        init();
    }

    /**
     * Store every function name of function which definition is in unit. Also
     * for every function call in unit checks, whether matches with
     * locking/thread patterns and write document to file.
     * @param unit CUnit
     * @throws org.dom4j.DocumentException
     */
    public void findFunctions(Unit unit) throws DocumentException  {
        List<Element> functionCalls;
        List<Element> declaredFunctionsElements;
        String functionName;
        Matcher matcher;
        declaredFunctions.clear();
        
        this.unit = unit;
        //Load all function calls
        functionCalls = unit.getXMLDocument().selectNodes("//functionCall");
        //Load all function definitions in document
        declaredFunctionsElements = unit.getXMLDocument().selectNodes(
                                       "//functionDefinition/declarator[1]/id");
        for(Element functionDef : declaredFunctionsElements) {
            declaredFunctions.add(functionDef.getText());
        }

        //Transform functionCalls which haven't already transformed and also
        //isn't declared in this document
        for(Element function : functionCalls) {

            if(this.matchFunction(unlockPattern, function, "unlockFunction"))
                continue;
            if(this.matchFunction(lockPattern, function, "lockFunction"))
                continue;
            if(this.matchFunction(threadPattern, function,
                                                        "createThreadFunction"))
                continue;
        }
        this.writeDocument(doc, filename);
    }
    
    /**
     * Method decide, whether element matches pattern.
     * @param pattern Pattern
     * @param function Element representing function call
     * @param type String type of pattern which element matches
     * @return true whether function call matches some of patterns and also is
     * not declared in CUnit.
     */
    private boolean matchFunction(Pattern pattern,Element function,
                                                                String type) {
        Matcher matcher;
        String functionName = function.node(0).getText();
        matcher = pattern.matcher(functionName);

        if(matcher.matches()) {
            if(foundCalls.add(functionName)
                            && !declaredFunctions.contains(functionName)) {
                this.transformFunction(doc.getRootElement(),function,type);
            }
            return true;
        }
        return false;
    }

    /**
     * Function creates element pattern similar to element functionCall
     * @param root - root element of config document
     * @param functionCallElement XML element representing function call
     */
    private void transformFunction(Element root, Element functionCallElement,
                                                                String type) {

        Element varElement;
        Element patternElement = new DefaultElement("pattern");
        Element element;
        Element id;
        String functionName;
        
        patternElement.addComment(
                           CodeAnalyzer.parseStringVariable(functionCallElement)
                            +" in "+this.unit.getName());
        
        patternElement.addAttribute("name", type);

        element = patternElement.addElement("nested");
        element = element.addElement("functionCall");
        functionName = functionCallElement.node(0).getText();
        id = element.addElement("id");
        id.addText(functionName);

        //If function has only one param -> this must means lock/funcition
        if(functionCallElement.nodeCount()==2) {
            if(type.equals("createThreadFunction")) {
               varElement = element.addElement("var");
               varElement.addAttribute("name", "function");
            } else {
               varElement = element.addElement("var");
               varElement.addAttribute("name", "lock");
            }
        } else {
            for(int i=1; i<functionCallElement.nodeCount();i++) {
                element.addElement("ignore");
            }
        }
        
        Element patterns = (Element) root.selectSingleNode("patterns");
        patterns.add(patternElement);
    }
    
    /**
     * Method writes XML document to file specified in outputName
     * @param document
     * @param outputName
     * @throws org.dom4j.DocumentException
     */
    public void writeDocument(Document document, String outputName) 
                                                    throws DocumentException {
        XMLWriter writer;
        try {
            OutputFormat format = OutputFormat.createPrettyPrint();
            writer = new XMLWriter(new FileWriter(outputName),format);
            writer.write(document);
            writer.close();
        } catch (IOException ex) {
            logger.error(ex);
            throw new DocumentException(ex);
        }
    }

}
