/*
 * SourceAndXMLWindow.java
 *
 * Created on 21. duben 2006, 15:30
 *
 */

package cz.muni.stanse.scvgui;

import cz.muni.stanse.automatonchecker.AutomatonChecker;
//import cz.muni.stanse.ownershipchecker.OwnershipChecker;
import cz.muni.stanse.callgraph.CallGraph;
import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.parser.CFGNode;
import cz.muni.stanse.parser.CFG;
import cz.muni.stanse.parser.CParser;
import cz.muni.stanse.utils.Pair;

import java.util.concurrent.ExecutionException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.Set;
import java.util.Vector;

import org.antlr.runtime.RecognitionException;

import org.apache.log4j.Logger;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.PrintWriter;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * Component for working with source file and XML tree
 * @author Ondrej Zivotsky ( ondrej@zivotsky.cz )
 */
public class SourceAndXMLWindow extends JPanel {
    
    private Document documentXML = null; // XML reprezentace kodu
    private Set<CFG> cfgs = null; // CFG reprezentace kodu
    private File sourceFile = null; // zdrojovy kod
    private boolean mappingXMLtoSource = false;
    private TreeSelectionListener treeXMLMappingListener; // pri kliku na XML se zvyrazni odpovidajici zdrojovy text (pokud je to v artibutech uzlu)
    
    // graficke komponenty
    private JTextField jTextFieldFindInSource;
    private JTextField jTextFieldFindInXML;
    private JCheckBox jCheckBoxXPath;
    private JCheckBox jCheckBoxRegEx;
    private JPanel jPanelFindInXML;
    private JPanel jPanelFindInSource;
    private JSplitPane jSplitPaneOutputAndOther;
    private JSplitPane jSplitPaneSourceAndXML;
    private HighligtedTextPane sourceText;
    private JTextPane jTextPaneOutput;
    private XMLTree treeXML;
    
    private static Logger logger = Logger.getLogger(GuiMain.class);
    
    /** Creates a new instance of SourceAndXMLWindow */
    public SourceAndXMLWindow() {
        super();
        setMinimumSize(new Dimension(200,150));
        initComponents();
        findInSourcePanelVisible(false);
        findInXMLPanelVisible(false);
        //treeXML.addNonVisibleAttribute("bl");
        treeXMLMappingListener = new TreeSelectionListener() { // pri kliku na XML se zvyrazni odpovidajici zdrojovy text (pokud je to v artibutech uzlu)
            public void valueChanged(final TreeSelectionEvent e) {
                if (!(e.getSource() instanceof Element))
                    return;
                Element element = (Element)e.getSource();
                if (element.attributeCount() < 1 ||
                    element.valueOf("@bl") == null) {
                    element = element.getParent();
                    if (element.attributeCount() < 1 ||
                        element.valueOf("@bl") == null) {
                        logger.error("TreeSelectionListener::valueChanged() :: " +
                                     "error : element of accepted selection " +
                                     "event does not contain attribute 'bl'." +
                                     " [element=" + element.toString() + "]");
                        return;
                    }
                }
                final Integer row = Integer.decode(element.valueOf("@bl"));
                if (row > 0)
                    SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run() {
                                sourceText.selectLines(row,row);
                            }
                        }
                    );
                }
            };
        
    }
    
    private JPanel initSourceFindPanel() {
        JPanel jPanel = new JPanel();
        
        jTextFieldFindInSource = new javax.swing.JTextField();
        final JButton jButtonFindFind = new javax.swing.JButton();
        JButton jButtonFindNext = new javax.swing.JButton();
        jCheckBoxRegEx = new javax.swing.JCheckBox();
        JButton jButtonFindClear = new javax.swing.JButton();
        JButton jButtonFindClose = new javax.swing.JButton();
        
        jPanel.setMaximumSize(new java.awt.Dimension(32767, 40));
        jPanel.setMinimumSize(new java.awt.Dimension(80, 70));
        jPanel.setPreferredSize(new java.awt.Dimension(90, 70));
        
        
        final ActionListener findClick = new ActionListener() { // listener pro stisk tlacitka Find nebo ENTER v TextFieldu
            public void actionPerformed(ActionEvent e) {
                if (jCheckBoxRegEx.isSelected()) {
                    sourceText.findRegExpr(jTextFieldFindInSource.getText(), 0);
                } else {
                    sourceText.findText(jTextFieldFindInSource.getText(), 0);
                }
            }
        };
        
        jTextFieldFindInSource.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == evt.VK_ENTER) {
                    findClick.actionPerformed(new ActionEvent(evt.getComponent(), evt.getID(), "find"));
                }
            }
        });
        
        jButtonFindFind.setText("find");
        jButtonFindFind.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButtonFindFind.setPreferredSize(new java.awt.Dimension(88, 12));
        jButtonFindFind.addActionListener(findClick);
        
        jButtonFindNext.setText("show next");
        jButtonFindNext.setMargin(new java.awt.Insets(2, 4, 2, 4));
        jButtonFindNext.setPreferredSize(new java.awt.Dimension(88, 18));
        jButtonFindNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (jCheckBoxRegEx.isSelected()) {
                    sourceText.findRegExpr(jTextFieldFindInSource.getText(), sourceText.getCaretPosition());
                } else {
                    sourceText.findText(jTextFieldFindInSource.getText(), sourceText.getCaretPosition());
                }
            }
        });
        
        jCheckBoxRegEx.setText("regular expr.");
        jCheckBoxRegEx.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxRegEx.setMargin(new java.awt.Insets(0, 0, 0, 0));
        
        jButtonFindClose.setText("close");
        jButtonFindClose.setMargin(new java.awt.Insets(2, 4, 2, 4));
        jButtonFindClose.setPreferredSize(new java.awt.Dimension(88, 18));
        jButtonFindClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jPanelFindInSource.setVisible(false);
            }
        });
        
        jButtonFindClear.setText("clear");
        jButtonFindClear.setMargin(new java.awt.Insets(2, 4, 2, 4));
        jButtonFindClear.setPreferredSize(new java.awt.Dimension(88, 18));
        jButtonFindClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jTextFieldFindInSource.setText("");
                jTextFieldFindInSource.requestFocusInWindow();
            }
        });
        
        org.jdesktop.layout.GroupLayout jPanelLayout = new org.jdesktop.layout.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
                jPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanelLayout.createSequentialGroup()
                .add(jButtonFindFind, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 52, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButtonFindNext, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jTextFieldFindInSource, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE))
                .add(15, 15, 15)
                .add(jPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanelLayout.createSequentialGroup()
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButtonFindClear, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButtonFindClose, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 58, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jCheckBoxRegEx, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addContainerGap())
                );
        jPanelLayout.setVerticalGroup(
                jPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jCheckBoxRegEx)
                .add(jTextFieldFindInSource, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 9, Short.MAX_VALUE)
                .add(jPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jButtonFindClose, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jButtonFindFind, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jButtonFindNext, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jButtonFindClear, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
                );
        
        return jPanel;
    }
    
    private JPanel initXMLFindPanel() {
        JButton jButtonXMLFindFind = new javax.swing.JButton();
        JButton jButtonXMLFindNext = new javax.swing.JButton();
        JButton jButtonXMLFindClear = new javax.swing.JButton();
        JButton jButtonXMLFindClose = new javax.swing.JButton();
        jTextFieldFindInXML = new javax.swing.JTextField();
        jCheckBoxXPath = new javax.swing.JCheckBox();
        
        JPanel jPanelFind = new JPanel();
        jPanelFind.setMaximumSize(new java.awt.Dimension(32767, 70));
        jPanelFind.setMinimumSize(new java.awt.Dimension(80, 70));
        jPanelFind.setPreferredSize(new java.awt.Dimension(90, 70));
        
        jCheckBoxXPath.setText("find XPath expr.");
        jCheckBoxXPath.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBoxXPath.setMargin(new java.awt.Insets(0, 0, 0, 0));
        
        final ActionListener findKlik = new ActionListener() { // listener pro stisk tlacitka Find nebo ENTER v TextFieldu
            public void actionPerformed(ActionEvent e) {
                if (documentXML == null) { return; }
                if (jCheckBoxXPath.isSelected()) {
                    treeXML.setHighlightedNodes(treeXML.findXPath(jTextFieldFindInXML.getText()));
                } else {
                    treeXML.setHighlightedNodes(treeXML.findText(jTextFieldFindInXML.getText()));
                }
                treeXML.goToNextHighlighted();
                
            }
        };
        
        final ActionListener findNextKlik = new ActionListener() { // listener pro stisk tlacitka FindNext nebo ENTER v TextFieldu
            public void actionPerformed(ActionEvent e) {
                if (documentXML == null) { return; }
                treeXML.goToNextHighlighted();
            }
        };
        
        jTextFieldFindInXML.addKeyListener(new KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == evt.VK_ENTER) {
                    findKlik.actionPerformed(new ActionEvent(evt.getComponent(),evt.getID(),"find"));
                }
            }
        });
        
        jButtonXMLFindFind.setText("find");
        jButtonXMLFindFind.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButtonXMLFindFind.setPreferredSize(new java.awt.Dimension(88, 12));
        jButtonXMLFindFind.addActionListener(findKlik);
        
        jButtonXMLFindNext.setText("show next");
        jButtonXMLFindNext.setMargin(new java.awt.Insets(2, 4, 2, 4));
        jButtonXMLFindNext.setPreferredSize(new java.awt.Dimension(88, 18));
        jButtonXMLFindNext.addActionListener(findNextKlik);
        
        jButtonXMLFindClear.setText("clear");
        jButtonXMLFindClear.setMargin(new java.awt.Insets(2, 4, 2, 4));
        jButtonXMLFindClear.setPreferredSize(new java.awt.Dimension(88, 18));
        jButtonXMLFindClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                treeXML.setHighlightedNodes(null);
                treeXML.setHighlighting(false);
                jTextFieldFindInXML.setText("");
                jTextFieldFindInXML.requestFocusInWindow();
            }
        });
        
        jButtonXMLFindClose.setText("close");
        jButtonXMLFindClose.setMargin(new java.awt.Insets(2, 4, 2, 4));
        jButtonXMLFindClose.setPreferredSize(new java.awt.Dimension(88, 18));
        jButtonXMLFindClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jPanelFindInXML.setVisible(false);
            }
        });
        
        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanelFind);
        jPanelFind.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel5Layout.createSequentialGroup()
                .add(jButtonXMLFindFind, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 52, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButtonXMLFindNext, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jTextFieldFindInXML, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE))
                .add(15, 15, 15)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel5Layout.createSequentialGroup()
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButtonXMLFindClear, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 57, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButtonXMLFindClose, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 58, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jCheckBoxXPath, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addContainerGap())
                );
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jCheckBoxXPath)
                .add(jTextFieldFindInXML, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 9, Short.MAX_VALUE)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jButtonXMLFindClose, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jButtonXMLFindFind, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jButtonXMLFindNext, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 17, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(jButtonXMLFindClear, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
                );
        return jPanelFind;
    }
    
    private void initComponents() {
        JScrollPane jScrollPaneOutput;
        
        jSplitPaneSourceAndXML = new JSplitPane();
        jSplitPaneOutputAndOther = new JSplitPane();
        // okno Output
        jTextPaneOutput = new JTextPane();
        jTextPaneOutput.setEditable(false);
        jTextPaneOutput.setText("");
        jTextPaneOutput.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        jScrollPaneOutput = new JScrollPane(jTextPaneOutput);
        // oddelovace
        jSplitPaneOutputAndOther.setDividerSize(10);
        jSplitPaneOutputAndOther.setLeftComponent(jSplitPaneSourceAndXML);
        jSplitPaneOutputAndOther.setRightComponent(jScrollPaneOutput);
        jSplitPaneOutputAndOther.setOrientation(JSplitPane.VERTICAL_SPLIT);
        // panel se zdrojovym kodem
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new java.awt.BorderLayout());
        // okno zdrojoveho kodu bez monosti editace
        sourceText = new HighligtedTextPane();
        sourceText.addKeyListener(new KeyAdapter() { // reaguj na CTRL+F
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyCode()==e.VK_F) && (e.isControlDown())) {
                    findInSourcePanelVisible(true);
                }
            }
        });
        sourceText.setEditableSpecial(false);
        JScrollPane jScrollPaneSource = new JScrollPane(sourceText);
        jPanelFindInSource = initSourceFindPanel();
        jPanel.add(jPanelFindInSource, java.awt.BorderLayout.NORTH);
        jPanel.add(jScrollPaneSource, java.awt.BorderLayout.CENTER);
        // panel se stromem
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new java.awt.BorderLayout());
        treeXML = new XMLTree();
        treeXML.addKeyListener(new KeyAdapter() { // reaguj na CTRL+F
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyCode()==e.VK_F) && (e.isControlDown())) {
                    findInXMLPanelVisible(true);
                }
            }
        });
        JScrollPane jScrollPaneTree = new JScrollPane(treeXML);
        jPanelFindInXML = initXMLFindPanel();
        jPanel1.add(jPanelFindInXML, java.awt.BorderLayout.NORTH);
        jPanel1.add(jScrollPaneTree, java.awt.BorderLayout.CENTER);
        // oddelovace
        jSplitPaneSourceAndXML.setDividerSize(6);
        jSplitPaneSourceAndXML.setRightComponent(jPanel);
        jSplitPaneSourceAndXML.setLeftComponent(jPanel1);
        org.jdesktop.layout.GroupLayout Layout = new org.jdesktop.layout.GroupLayout(this);
        setLayout(Layout);
        // Layouty
        Layout.setHorizontalGroup(
                Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(0, 366, Short.MAX_VALUE)
                .add(jSplitPaneOutputAndOther, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                );
        Layout.setVerticalGroup(
                Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(0, 234, Short.MAX_VALUE)
                .add(jSplitPaneOutputAndOther, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                );
        // pri zmene velikosti okna se posunou i oddelovace (na pevnou hodnotu)
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jSplitPaneOutputAndOther.setDividerLocation(jSplitPaneOutputAndOther.getHeight()*4/5);
                jSplitPaneSourceAndXML.setDividerLocation(jSplitPaneSourceAndXML.getWidth()/2);
            }
        });
    }
    
    /**
     * Opens a new source file
     * @param file file for open
     * @param highlight Whether to highlight source code or not
     */
    public void openSourceFile(final File file, boolean highlight) {
        sourceFile = file;
        if(highlight) {
            sourceText.openFile(file, sourceText.DOCUMENT_STYLE_C);
        } else {
            sourceText.openFile(file, null);
        }
        try {
            transformCSourceFileToXML();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public HighligtedTextPane getSourceText() {
        return sourceText;
    }
    
    
    
    /**
     * transform C source file to XML tree
     * Called automatically when opening a source code.
     */
    public void transformCSourceFileToXML() throws FileNotFoundException,
					IOException, RecognitionException {
        if(sourceFile.length() <= 0) {
            return;
        }
        Cursor originalCursor = getCursor();
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        CParser parser = new CParser(new FileInputStream(sourceFile));
        parser.run();
        documentXML = parser.getXMLDocument();
        documentXML.setName(sourceFile.getName());
        if(documentXML != null) {
            treeXML.setXMLDocument(documentXML);
        }
        cfgs = parser.getCFGs();
        setCursor(originalCursor);
    }
    
    
    /**
     * Show or hide a panel for finding in source file
     * @param value boolean value to set
     */
    public void findInSourcePanelVisible(boolean value) {
        jPanelFindInSource.setVisible(value);
        if (value) {
            jTextFieldFindInSource.requestFocus();
        }
    }
    
    /**
     * Show or hide a panel for finding in XML file
     * @param value boolean value to set
     */
    public void findInXMLPanelVisible(boolean value) {
        jPanelFindInXML.setVisible(value);
        if (value) {
            jTextFieldFindInXML.requestFocus();
        }
    }
    
    /**
     * Sets mapping from XML to source.
     * <BR>If value is <i>true</i> and if user select node with attribute pointing to source file, this text is selected in source code panel.
     * @param value boolean value to set
     */
    public void setMappingXMLToSource(boolean value) {
        if (value) {
            if (! mappingXMLtoSource) { treeXML.addTreeSelectionListener(treeXMLMappingListener); }
            mappingXMLtoSource = value;
        } else {
            if (mappingXMLtoSource) { treeXML.removeTreeSelectionListener(treeXMLMappingListener); }
            mappingXMLtoSource = value;
        }
    }
    
    private static final HashSet<CFG> buildCFGsFromXML(
                                                   final Document documentXML) {
        if (documentXML == null)
            return null;

	final HashSet<CFG> cfgs = new HashSet<CFG>();
	{
	    final Element rootElement = documentXML.getRootElement();
	    List<Element> edecls = rootElement.elements("externalDeclaration");
	    for (Element e: edecls)
		if (e.element("functionDefinition") != null)
		    cfgs.add(new CFG(e.element("functionDefinition")));
	}

        return cfgs;
    }

    private static final String convertCheckerErrorListToString(
                                             final List<CheckerError> errList) {
        String result = "";
        for (CheckerError err : errList)
            result += err.toString() + "\n\n";
        return result;
    }

    /**
     * Open a new window with Automaton (static) checker results.
     * @param files files with definition of the checker.
     */
    public void runStaticChecker(final Set<File> configFiles) {
        if (configFiles == null || configFiles.isEmpty()) {
            logger.error("Error: No automaton XML file provided.");
            return;
        }
/*
        final HashSet<CFG> cfgs = buildCFGsFromXML(documentXML);
        if (cfgs == null) {
            logger.error("Error: No CFG provided.");
            return;
        }
*/
        final LinkedList<CheckerError> errors = new LinkedList<CheckerError>(); 
        for (File f : configFiles)
            try {
                errors.addAll(
                   (new AutomatonChecker((new SAXReader()).read(f))).check(cfgs)
                );
            }
            catch(Exception e) {
                logger.error(getStackTrace(e));
            }

        jTextPaneOutput.setText(convertCheckerErrorListToString(errors));
        ErrorForm errorForm = new ErrorForm(errors, treeXML, this);
        errorForm.setVisible(true);
    }

    /**
     * Run ownership checker.
     */
    public void checkOwnership() {
/*
        final HashSet<CFG> cfgs = buildCFGsFromXML(documentXML);
        if (cfgs == null)
            return;
*/
        final LinkedList<CheckerError> errors = new LinkedList<CheckerError>(); 
        try {
            //errors.addAll((new OwnershipChecker()).check(cfgs));
        }
        catch(Exception e) {
            logger.error(getStackTrace(e));
        }

        jTextPaneOutput.setText(convertCheckerErrorListToString(errors));
        ErrorForm errorForm = new ErrorForm(errors, treeXML, this);
        errorForm.setVisible(true);

    }
    
    public TreeSelectionListener getTreeXMLMappingListener() {
        return treeXMLMappingListener;
    }
    
    /**
     * Show call graph of the current document
     */
    void showCallGraph() {
        CallGraph callGraph = new CallGraph(documentXML.getRootElement().selectNodes("//functionDefinition"));
        GraphForm graph = new GraphForm(sourceFile.getName() + " -> Call Graph",callGraph.toDot());
    }
    
    /**
     * Show call graphs of all procedures specified by the argument
     * @param rootElements definition of the procedures for the call graph
     */
    void showCallGraph(List rootElements) {
        
        CallGraph callGraph = new CallGraph(rootElements);
        GraphForm graph = new GraphForm("All Procedures -> Call Graph",callGraph.toDot());
    }
    
    /**
     * Gets the docuementXML
     * @return documentXML
     */
    public Document getDocumentXML() {
        return documentXML;
    }
    /*
    private static void addFunctionsToChecker(Checker checker, Document document) {
        
        //for every functionDefinition add CFG to checker
        Element rootElement = document.getRootElement();
        for (int i = 0, size = rootElement.nodeCount(); i < size; i++) {
            Node node = rootElement.node(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                
                if (element.getName().equals("functionDefinition")) {
                    checker.addCFG(new CFG(element));
                }
            }
        }
    }
    */


   /**
     * @brief Creates and returns a {@link java.lang.String} from
     *        <code>t</code>’s stacktrace 
     * @param t Throwable whose stack trace is required
     * @return String representing the stack trace of the exception
     */
    private String getStackTrace(Throwable t) {
        StringWriter stringWritter = new StringWriter();
        PrintWriter printWritter = new PrintWriter(stringWritter, true);
        t.printStackTrace(printWritter);
        printWritter.flush();
        stringWritter.flush(); 

       return stringWritter.toString();
    } 
    
    
}
