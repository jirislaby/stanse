/*
 * XMLTree.java
 *
 * Created on 26. duben 2006, 10:50
 *
 */

package cz.muni.stanse.scvgui;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;

/**
 * The <CODE>XMLTree</CODE> is a component for displaying and working with XML tree.
 * @author Ondrej Zivotsky ( ondrej@zivotsky.cz )
 */
public class XMLTree extends JTree {
    
    private Document documentXML; // XML reprezentace kodu
    private List highligtedNodes = new ArrayList(); // zvyraznene uzly
    private int highligtedNodeIndex = -1; // prave zvyrazneny uzel
    private boolean highligting = false; // zvyraznovat uzly ?
    private ArrayList nonVisibleAttributes = new ArrayList(); // seznam atributu, ktere se nezobrazi
    
    /** Creates a new instance of XMLTree */
    public XMLTree() {
        super(); // zavolej konstruktor JTree
        documentXML = null; // zadny dokument k zobrazeni
        setModel(null); // nastav model stromu
    }
    
    /**
     * Finds in the text nodes elected text and return <CODE>List</CODE> of <CODE>Elements</CODE> which contains it.
     * @param expr finding text
     * @return <CODE>List</CODE> of <CODE>org.dom4j.Element</CODE>
     */
    public List findText(String expr) {
        XPath xpathSelector = DocumentHelper.createXPath("//*[contains(text(),'"+expr+"')]"); // XPath vyraz hledajici text v uzlech
        List result = xpathSelector.selectNodes(documentXML); // vyber uzly, ktere obsahuji text
        return result;
    }
    
    /**
     * Finds the nodes in the XML document matching elected XPath expression and return that <CODE>Element</CODE>s in the <CODE>List</CODE>.
     * @param expr XPath expression
     * @return <CODE>List</CODE> of <CODE>org.dom4j.Element</CODE>
     */
    public List findXPath(String expr) {
        XPath xpathSelector = DocumentHelper.createXPath(expr); // vytvor XPath
        List result = xpathSelector.selectNodes(documentXML); // najdi uzly
        return result;
    }
    
    /**
     * Sets the nodes in the <CODE>List</CODE> as highlighted and set highlighting to <i>true</i>.
     * @param nodes <CODE>List</CODE> of <CODE>org.dom4j.Element</CODE>
     */
    public void setHighlightedNodes(List nodes) {
        highligtedNodes = nodes; // nastav uzly
        highligtedNodeIndex = -1; // nasledujici bude index 0 (prvni)
        setHighlighting(true); // zvyrazni je
    }
    
    /**
     * Returns a nodes which was highlighted.
     * @return <CODE>List</CODE> of <CODE>org.dom4j.Element</CODE>
     */
    public List getHighlightedNodes() {
        return highligtedNodes;
    }
    
    /**
     * Show elected node in <CODE>XMLTree</CODE> component and selects it.
     * @param node a XML Element
     */
    public void goToNode(Element node) {
        TreePath path = createPath(node); // vytvor cestu k uzlu
        scrollPathToVisible(path); // rozbal uzly na ceste a umisti tam kurzor
        setSelectionPath(path); // oznac uzel
    }
    
    /**
     * Displays next highlighted node. After last node is displayed the first. If no nodes was highlighted, the <i>false</i> is returned.
     * @return whether going to next highlighted is possible
     */
    public boolean goToNextHighlighted() {
        if (highligtedNodes == null || highligtedNodes.isEmpty()) { return false; } // pokud neni nic zvyrazneno vrat "neuspech"
        highligtedNodeIndex++; // zvyrazni dalsi
        if (highligtedNodeIndex >= highligtedNodes.size()) { // posledni -> prvni
            highligtedNodeIndex = 0;
        }
        goToNode((Element) highligtedNodes.get(highligtedNodeIndex)); // zobraz a oznac uzel
        return true; // vrat "uspech"
    }
    
    // vytvori cestu k uzlu
    private TreePath createPath(Element node) { 
        if (node == null) { // misto null dej korenovy uzel
            return (new TreePath(documentXML.getRootElement()));
        }
        List elements = new ArrayList(); // seznam uzlu
        while (node != null) {
            elements.add(0, node); // vkladej na zacatek uzly = tvor cestu
            node = node.getParent();
        }
        return (new TreePath(elements.toArray())); // vytvor cestu
    }
    
    /**
     * Ses the selection highlighting to <i>true</i> or <i>false</i>
     * @param value boolean value
     */
    public void setHighlighting(boolean value) {
        if (highligtedNodes == null || highligtedNodes.isEmpty()) { // pokud neni co zvyraznit, pak nekompromisne nepovol
            highligting = false;
        } else {                    // nastav zvyraznovani uzlu
            highligting = value;
        }
        repaint(); // provede se prekresleni
    }
    
    /**
     * Adds the attribute name which not will be show in XML tree
     * @param attribute Attribute name
     */
    public void addNonVisibleAttribute(String attribute) {
        if (! nonVisibleAttributes.contains(attribute)) {
            nonVisibleAttributes.add(attribute);
        }
    }
    
    /**
     * Removes the attribute name which not will be show in XML tree
     * @param attribute Attribute name
     */
    public void delNonVisibleAttribute(String attribute) {
        if ( nonVisibleAttributes.contains(attribute)) {
            nonVisibleAttributes.remove(attribute);
        }
    }
    
    /**
     * Sets and display a XML document.
     * @param xmlDom4jDocument XML document for dislpaying
     */
    public void setXMLDocument(Document xmlDom4jDocument) {
        documentXML = xmlDom4jDocument; // nastav XML dokument
        
        setRootVisible(true); // korenovy uzel viditelny
        putClientProperty("JTree.lineStyle", "None"); // styl car stromu
        
        setModel(new XMLTreeModel(documentXML.getRootElement())); // nastav zobrazovaci model
        
        // prepise zobrazovani uzlu, aby se zobrazovalo jmeno uzlu spravne, barevne a s formatovanim
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer() {
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                Element uzel = (Element) value; // prave zobrazovany uzel
                String jmenoUzlu = "<html>"; // jmeno uzlu je v HTML (formatovani, barvy, ...)
                boolean setBackground = false; // je highlighted?
                
                if ((highligting)&&(highligtedNodes.contains(uzel))) { // je mezi zvyraznenymi (highlighted) ?
                    setBackground = true; // budeme nastavovat pozadi                 
                    expanded = true; // rozbalit
                }
                
                if (setBackground) { // je mezi zvyraznenymi
                    if (sel) { jmenoUzlu = jmenoUzlu + "<body bgcolor=#CCCC99>"; } // je mezi zvyraznenymi, vybrany
                    else { jmenoUzlu = jmenoUzlu + "<body bgcolor=#B0D0FF>"; } // je mezi zvyraznenymi, neaktivni
                } else {
                    jmenoUzlu = jmenoUzlu + "<body>"; // normalni uzel
                }
                
                jmenoUzlu = jmenoUzlu + "&lt;<b>" + uzel.getQualifiedName() + "</b>"; // jmeno uzlu
                for (int i = 0; i < uzel.attributeCount(); i++){ // pridej barevne atributy
                    if (! nonVisibleAttributes.contains(uzel.attribute(i).getQualifiedName())) // zobrazujeme to?
                    {
                        jmenoUzlu = jmenoUzlu + " <font color=#00AA00>" + uzel.attribute(i).getQualifiedName() + "</font>=\"<font color=#AA0000>" + uzel.attribute(i).getStringValue() + "</font>\"";
                    }
                }
                jmenoUzlu = jmenoUzlu + "&gt;"; // ukonci tag
                if (uzel.getTextTrim().length() != 0) { // pokud je to textovy uzel, zobraz i text
                    jmenoUzlu = jmenoUzlu + "<br>&nbsp;&nbsp;&nbsp;&nbsp;<i><font color=#0000AA>" + uzel.getText() + "</font></i>"; 
                }
                
                jmenoUzlu = jmenoUzlu + "</body></html>"; // ukonci HTML
                super.getTreeCellRendererComponent(tree, jmenoUzlu, sel, expanded, leaf, row, hasFocus); // zobraz uzel
                if (hasFocus) { fireValueChanged(new TreeSelectionEvent(uzel, null, false, null, null)); }
                return this;
            }
        };
        renderer.setOpenIcon(null); // uzly budou bez ikony
        renderer.setClosedIcon(null); // uzly budou bez ikony
        renderer.setLeafIcon(null); // uzly budou bez ikony
        renderer.setBackgroundSelectionColor(new Color(255,255,175)); // barva vyberu
        setCellRenderer(renderer); // nastav novy, upraveny, zobrazovac uzlu
    }
    
    
    /* Model for displaying a XML document */
    private class XMLTreeModel implements TreeModel {
        
        private Vector treeModelListeners = new Vector();
        private Element rootElement;
        
        public XMLTreeModel(Element root) {
            rootElement = root;
        }
        
        /**
         * The only event raised by this model is TreeStructureChanged with the
         * root as path, i.e. the whole tree has changed.
         */
        protected void fireTreeStructureChanged(Element oldRoot) {
            int len = treeModelListeners.size();
            TreeModelEvent e = new TreeModelEvent(this, new Object[] {oldRoot});
            for (int i = 0; i < len; i++) {
                ((TreeModelListener)treeModelListeners.elementAt(i)).
                        treeStructureChanged(e);
            }
        }
        
        /**
         * Adds a listener for the TreeModelEvent posted after the tree changes.
         */
        public void addTreeModelListener(TreeModelListener l) {
            treeModelListeners.addElement(l);
        }
        
        /**
         * Returns the child of parent at index index in the parent's child array.
         */
        public Object getChild(Object parent, int index) {
            return ((Element) ((Element)parent).elements().toArray()[index]);
        }
        
        /**
         * Returns the number of children of parent.
         */
        public int getChildCount(Object parent) {
            return ((Element)parent).elements().size();
        }
        
        /**
         * Returns the index of child in parent.
         */
        public int getIndexOfChild(Object parent, Object child) {
            Element ch = (Element)child;
            int i = 0;
            Iterator it = ((Element)parent).elementIterator();
            while ((it.hasNext())&&(!child.equals(parent))) { i++; }
            return i;
        }
        
        /**
         * Returns the root of the tree.
         */
        public Object getRoot() {
            return rootElement;
        }
        
        /**
         * Returns true if node is a leaf.
         */
        public boolean isLeaf(Object node) {
            return getChildCount(node) == 0;
        }
        
        /**
         * Removes a listener previously added with addTreeModelListener().
         */
        public void removeTreeModelListener(TreeModelListener l) {
            treeModelListeners.removeElement(l);
        }
        
        /**
         * Messaged when the user has altered the value for the item
         * identified by path to newValue.  Not used by this model.
         */
        public void valueForPathChanged(TreePath path, Object newValue) {
            System.out.println("******************************");
            System.out.println("*** Not implemented method ***");
            System.out.println("*** valueForPathChanged : " + path + " --> " + newValue + " ***");
            System.out.println("******************************");
        }
        
    }
    
    
}
