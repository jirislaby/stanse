/*
 * GuiMain.java
 *
 * Created on 11. duben 2006, 15:15
 */

package cz.muni.stanse.gui;

import cz.muni.stanse.callgraph.CallGraph;
import cz.muni.stanse.props.LoggerConfigurator;
import cz.muni.stanse.props.Properties;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import java.util.Set;
import java.util.HashSet;
import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedSubgraph;

/**
 * Gui for code verifier
 * @author Ondrej Zivotsky ( ondrej@zivotsky.cz )
 */
public class GuiMain extends javax.swing.JFrame {
    
    ExitAction exitAction = new ExitAction();
    OpenSourceAction openSourceAction = new OpenSourceAction();
    CloseSourceAction closeSourceAction = new CloseSourceAction();
    CloseAllAction closeAllAction = new CloseAllAction();
    FindInSourceAction findInSourceAction = new FindInSourceAction();
    FindInXMLAction findInXMLAction = new FindInXMLAction();
    MappingXMLtoSourceAction mappingXMLtoSourceAction = new MappingXMLtoSourceAction(false);
    OpenBatchFileAction openBatchFileAction = new OpenBatchFileAction();
    
    RunStaticCheckerAction runStaticCheckerAction = new RunStaticCheckerAction();
    RunAllStaticCheckerAction runAllStaticCheckerAction = new RunAllStaticCheckerAction();
    CheckOwnershipAction checkOwnershipAction = new CheckOwnershipAction();
    
    ShowCallGraphAction showCallGraphAction = new ShowCallGraphAction();
    ShowCallGraphAllAction showCallGraphAllAction = new ShowCallGraphAllAction();
    
    ShowStronglyConnectedSubsets showStronglyConnectedSubsets = new ShowStronglyConnectedSubsets();
    
    
     private static Logger logger = Logger.getLogger(GuiMain.class);
        
    
    // whether to highlight source code or not
    // This can be for example loaded from a properties file in constructor. So fas just a constant
    private static boolean highlight = Boolean.valueOf(Properties.getProperty("highlightSourceCode", "false"));
    
    private String workingDir = "."; // pamatuj si pracovni adresar
    
    class ExitAction extends AbstractAction {
        ExitAction() {
            putValue( AbstractAction.NAME,              "Exit program" );
            putValue( AbstractAction.SHORT_DESCRIPTION, "Exit"          );
//           putValue( AbstractAction.MNEMONIC_KEY,      new Integer(KeyEvent.VK_M) );
            putValue( AbstractAction.SMALL_ICON,        null                         );
        }
        public void actionPerformed(ActionEvent e) {
//        For ... closeSourceAction....
            System.exit(0);
        }
    }
    
    class OpenSourceAction extends AbstractAction {
        OpenSourceAction() {
            putValue( AbstractAction.NAME,              "Open new source file ..." );
            putValue( AbstractAction.SHORT_DESCRIPTION, "Open file"                );
//           putValue( AbstractAction.MNEMONIC_KEY,      new Integer(KeyEvent.VK_M) );
            putValue( AbstractAction.SMALL_ICON,        null                         );
        }
        public void actionPerformed(ActionEvent e) {
            JFileChooser openFileDialog = new JFileChooser();
            openFileDialog.setCurrentDirectory(new File(workingDir));
            openFileDialog.setDialogTitle("Open source file");
            openFileDialog.setMultiSelectionEnabled(true);
            openFileDialog.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(File f) {
                    if (f.isDirectory()) { return true; }
                    if (! f.getName().endsWith(".c")) { return false; }
                    return true;
                }
                public String getDescription() {
                    return "C source files (*.c)";
                }
            });
            if (openFileDialog.showOpenDialog(null) != openFileDialog.CANCEL_OPTION) {
                workingDir = openFileDialog.getCurrentDirectory().getAbsolutePath();
                for (int i = 0; i < openFileDialog.getSelectedFiles().length; i++) {
                    File file = openFileDialog.getSelectedFiles()[i];
                    openSourceFile(file);
//                    SourceAndXMLWindow dataPanel = new SourceAndXMLWindow();
//                    dataPanel.openSourceFile(file);
//                    jTabbedPane1.addTab(file.getName(), dataPanel);
//                    jTabbedPane1.setSelectedIndex(jTabbedPane1.getTabCount()-1);
//                    // vrat zpet nastaveni, ktere zmenily jine akce
//                    closeSourceAction.setEnabled(true);
//                    jTabbedPane1.setVisible(true);
//                    
//                    jMenuEdit.setEnabled(true);
//                    jMenuCheck.setEnabled(true);
//                    jMenuCallGraph.setEnabled(true);
                
                    
                }
            }
        }
    }
    
    
    class OpenBatchFileAction extends AbstractAction {
        OpenBatchFileAction() {
            putValue( AbstractAction.NAME,              "Open batch file ..." );
            putValue( AbstractAction.SHORT_DESCRIPTION, "Open batch file"                );
//           putValue( AbstractAction.MNEMONIC_KEY,      new Integer(KeyEvent.VK_M) );
            putValue( AbstractAction.SMALL_ICON,        null                         );
        }
        public void actionPerformed(ActionEvent e) {
            JFileChooser openFileDialog = new JFileChooser();
            openFileDialog.setCurrentDirectory(new File(workingDir));
            openFileDialog.setDialogTitle("Open batch file");
            openFileDialog.setMultiSelectionEnabled(true);
            openFileDialog.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(File f) {
                    if (f.isDirectory()) { return true; }
                    if (! f.getName().endsWith(".batch")) { return false; }
                    return true;
                }
                public String getDescription() {
                    return "Batch files (*.batch)";
                }
            });
            if (openFileDialog.showOpenDialog(null) != openFileDialog.CANCEL_OPTION) {
                workingDir = openFileDialog.getCurrentDirectory().getAbsolutePath();
                int numOpened = openFileDialog.getSelectedFiles().length;
                for (int i = 0; i < numOpened; i++) {
                    File file = openFileDialog.getSelectedFiles()[i];
                    String fileName = openFileDialog.getName(file);
                    BufferedReader bufferIn = null;
                    try {
                        bufferIn = new BufferedReader(new FileReader(file));
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    String oneFile;
                    try {
                        while((oneFile = bufferIn.readLine()) != null) {
                            File sourceFile = new File(oneFile);
                            openSourceFile(sourceFile);
//                            SourceAndXMLWindow dataPanel = new SourceAndXMLWindow();
//                            dataPanel.openSourceFile(sourceFile, highlight);
//                            
//                            jTabbedPane1.addTab(sourceFile.getName(), dataPanel);
//                            jTabbedPane1.setSelectedIndex(jTabbedPane1.getTabCount()-1);
//                            // vrat zpet nastaveni, ktere zmenily jine akce
//                            closeSourceAction.setEnabled(true);
//                            closeAllAction.setEnabled(true);
//                            jTabbedPane1.setVisible(true);
//
//                            jMenuEdit.setEnabled(true);
//                            jMenuCheck.setEnabled(true);
//                            jMenuCallGraph.setEnabled(true);
//                
                            
                            
                        }
                        bufferIn.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    
                    
                }
            }
        }
    }
    
    
    class CloseSourceAction extends AbstractAction {
        CloseSourceAction() {
            putValue( AbstractAction.NAME,              "Close source file" );
            putValue( AbstractAction.SHORT_DESCRIPTION, "Close file"        );
//           putValue( AbstractAction.MNEMONIC_KEY,      new Integer(KeyEvent.VK_M) );
            putValue( AbstractAction.SMALL_ICON,        null                         );
        }
        public void actionPerformed(ActionEvent e) {
//            ((SourceAndXMLWindow) jTabbedPane1.getSelectedComponent()).canClose();
//            jTabbedPane1.remove(jTabbedPane1.getSelectedIndex());
//            if (jTabbedPane1.getTabCount() <= 0) {
//                // nastav vlastnosti pro jine akce
//                setEnabled(false);
//                jTabbedPane1.setVisible(false);
//
//                jMenuEdit.setEnabled(false);
//                jMenuCheck.setEnabled(false);
//                jMenuCallGraph.setEnabled(false);
//                
//            }
            closeSource(jTabbedPane1.getSelectedIndex());
        }
    }
    
    class CloseAllAction extends AbstractAction {
        CloseAllAction() {
            putValue( AbstractAction.NAME,              "Close all source files" );
            putValue( AbstractAction.SHORT_DESCRIPTION, "Close all"        );
//           putValue( AbstractAction.MNEMONIC_KEY,      new Integer(KeyEvent.VK_M) );
            putValue( AbstractAction.SMALL_ICON,        null                         );
        }
        public void actionPerformed(ActionEvent e) {
//            int count = jTabbedPane1.getTabCount();
//            for(int i = count; i > 0; i--) {
//                closeSource(i);
//            }
            while(jTabbedPane1.getSelectedIndex() >= 0) {
                closeSource(jTabbedPane1.getSelectedIndex());
            }
        }
    }
    
    private void closeSource(int sourceTabIndex) {
        if(sourceTabIndex >= 0) { 
            jTabbedPane1.remove(sourceTabIndex);
            if (jTabbedPane1.getTabCount() <= 0) {
                // nastav vlastnosti pro jine akce
                closeSourceAction.setEnabled(false);
                closeAllAction.setEnabled(false);
                
                jTabbedPane1.setVisible(false);

                jMenuEdit.setEnabled(false);
                jMenuCheck.setEnabled(false);
                jMenuCallGraph.setEnabled(false);
                
            }
        }
    }
    
    
    class FindInXMLAction extends AbstractAction {
        FindInXMLAction() {
            putValue( AbstractAction.NAME,              "Find in XML tree ..." );
            putValue( AbstractAction.SHORT_DESCRIPTION, "Find in XML"          );
//           putValue( AbstractAction.MNEMONIC_KEY,      new Integer(KeyEvent.VK_M) );
            putValue( AbstractAction.SMALL_ICON,        null                         );
        }
        public void actionPerformed(ActionEvent e) {
            ((SourceAndXMLWindow) jTabbedPane1.getSelectedComponent()).findInXMLPanelVisible(true);
        }
        
    }
    
    class FindInSourceAction extends AbstractAction {
        FindInSourceAction() {
            putValue( AbstractAction.NAME,              "Find in source file ..." );
            putValue( AbstractAction.SHORT_DESCRIPTION, "Find in source"          );
//           putValue( AbstractAction.MNEMONIC_KEY,      new Integer(KeyEvent.VK_M) );
            putValue( AbstractAction.SMALL_ICON,        null                         );
        }
        public void actionPerformed(ActionEvent e) {
            ((SourceAndXMLWindow) jTabbedPane1.getSelectedComponent()).findInSourcePanelVisible(true);
        }
        
    }
    
    class MappingXMLtoSourceAction extends AbstractAction {
        boolean mapping;
        MappingXMLtoSourceAction(boolean value) {
            putValue( AbstractAction.NAME,              "Mapping XML to source file" );
            putValue( AbstractAction.SHORT_DESCRIPTION, "Mapping from XML"           );
//           putValue( AbstractAction.MNEMONIC_KEY,      new Integer(KeyEvent.VK_M) );
            putValue( AbstractAction.SMALL_ICON,        null                         );
            mapping = value;
        }
        public void actionPerformed(ActionEvent e) {
            mapping = ((JCheckBoxMenuItem)(e.getSource())).isSelected(); // v isSelected je nova hodnota!!!!!
            ((SourceAndXMLWindow) jTabbedPane1.getSelectedComponent()).setMappingXMLToSource(mapping);
        }
        public void setEnabled(boolean value) {
            super.setEnabled(value);
            if (value) {
                ((SourceAndXMLWindow) jTabbedPane1.getSelectedComponent()).setMappingXMLToSource(mapping);
            }
        }
    }
    
    class RunStaticCheckerAction extends AbstractAction {
        RunStaticCheckerAction() {
            putValue( AbstractAction.NAME,              "Static checker (current)");
            putValue( AbstractAction.SHORT_DESCRIPTION, "Run Static Checker on actual source code");
//           putValue( AbstractAction.MNEMONIC_KEY,      new Integer(KeyEvent.VK_M)       );
            putValue( AbstractAction.SMALL_ICON,        null                             );
        }
        public void actionPerformed(ActionEvent e) {
            Set<File> files = new HashSet<File>();
            JFileChooser openFileDialog = new JFileChooser();
            openFileDialog.setCurrentDirectory(new File(workingDir));
            openFileDialog.setDialogTitle("Open checker definition");
            openFileDialog.setMultiSelectionEnabled(true);
            openFileDialog.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(File f) {
                    if (f.isDirectory()) { return true; }
                    if (! f.getName().endsWith(".xml")) { return false; }
                    return true;
                }
                public String getDescription() {
                    return "Static Checker def (*.xml)";
                }
            });
            if (openFileDialog.showOpenDialog(null) != openFileDialog.CANCEL_OPTION) {
                workingDir = openFileDialog.getCurrentDirectory().getAbsolutePath();
                for (int i = 0; i < openFileDialog.getSelectedFiles().length; i++) {
                    File file = openFileDialog.getSelectedFiles()[i];                    
                    
                    files.add(file);
                }
            }
            
            ((SourceAndXMLWindow) jTabbedPane1.getSelectedComponent()).runStaticChecker(files);
        }
    }
    

    class RunAllStaticCheckerAction extends AbstractAction {
        RunAllStaticCheckerAction() {
            putValue( AbstractAction.NAME,              "Static checker (all)");
            putValue( AbstractAction.SHORT_DESCRIPTION, "Run Static Checker on all opened source codes");
//           putValue( AbstractAction.MNEMONIC_KEY,      new Integer(KeyEvent.VK_M)       );
            putValue( AbstractAction.SMALL_ICON,        null                             );
        }
        public void actionPerformed(ActionEvent e) {
            Set<File> files = new HashSet<File>();
            JFileChooser openFileDialog = new JFileChooser();
            openFileDialog.setCurrentDirectory(new File(workingDir));
            openFileDialog.setDialogTitle("Open checker definition");
            openFileDialog.setMultiSelectionEnabled(true);
            openFileDialog.setFileFilter(new javax.swing.filechooser.FileFilter() {
                public boolean accept(File f) {
                    if (f.isDirectory()) { return true; }
                    if (! f.getName().endsWith(".xml")) { return false; }
                    return true;
                }
                public String getDescription() {
                    return "Static Checker def (*.xml)";
                }
            });
            if (openFileDialog.showOpenDialog(null) != openFileDialog.CANCEL_OPTION) {
                workingDir = openFileDialog.getCurrentDirectory().getAbsolutePath();
                for (int i = 0; i < openFileDialog.getSelectedFiles().length; i++) {
                    File file = openFileDialog.getSelectedFiles()[i];                    
                    
                    files.add(file);
                }
            }
            
            for(int i = 0; i < jTabbedPane1.getTabCount(); i++) {
                ((SourceAndXMLWindow) jTabbedPane1.getComponentAt(i)).runStaticChecker(files);
            }
                
            
        }
    }
        
    class CheckOwnershipAction extends AbstractAction {
        CheckOwnershipAction() {
            putValue( AbstractAction.NAME,              "Ownership checker");
            putValue( AbstractAction.SHORT_DESCRIPTION, "Check correct memory ownership");
            putValue( AbstractAction.SMALL_ICON,        null                             );
        }
        public void actionPerformed(ActionEvent e) {
            ((SourceAndXMLWindow) jTabbedPane1.getSelectedComponent()).checkOwnership();
        }
    }

    
     class ShowCallGraphAction extends AbstractAction {
        ShowCallGraphAction() {
            putValue( AbstractAction.NAME,              "Show Call graph (single document)");
            putValue( AbstractAction.SHORT_DESCRIPTION, "Show Call graph of the current document");
            putValue( AbstractAction.SMALL_ICON,        null                             );
        }
        public void actionPerformed(ActionEvent e) {        
            
            ((SourceAndXMLWindow) jTabbedPane1.getSelectedComponent()).showCallGraph();
            
        }
    }

     class ShowCallGraphAllAction extends AbstractAction {
        ShowCallGraphAllAction() {
            putValue( AbstractAction.NAME,              "Show Call graph");
            putValue( AbstractAction.SHORT_DESCRIPTION, "Show Call graph of all procedures");
            putValue( AbstractAction.SMALL_ICON,        null                             );
        }
        public void actionPerformed(ActionEvent e) {        
            
            
            List<Element> rootElements = new ArrayList<Element>();
                for(int i = 0; i < jTabbedPane1.getTabCount(); i++) {
                rootElements.addAll(((SourceAndXMLWindow) jTabbedPane1.getComponentAt(i)).getDocumentXML().getRootElement().selectNodes("//functionDefinition"));
            }
            ((SourceAndXMLWindow) jTabbedPane1.getSelectedComponent()).showCallGraph(rootElements);
        }
    }
     
     class ShowStronglyConnectedSubsets extends AbstractAction {
        ShowStronglyConnectedSubsets() {
            putValue( AbstractAction.NAME,              "Show strongly connected components");
            putValue( AbstractAction.SHORT_DESCRIPTION, "Show strongly connected components");
            putValue( AbstractAction.SMALL_ICON,        null                             );
        }
        public void actionPerformed(ActionEvent e) {        
            
            
            List<Element> rootElements = new ArrayList<Element>();
            for(int i = 0; i < jTabbedPane1.getTabCount(); i++) {
                rootElements.addAll(((SourceAndXMLWindow) jTabbedPane1.getComponentAt(i)).getDocumentXML().getRootElement().selectNodes("//functionDefinition"));
            }
            CallGraph callGraph = new CallGraph(rootElements);
            
            List<DirectedSubgraph<String, DefaultEdge>> stronglyConnectedSubgraphs = callGraph.stronglyConnected();
            
//            SubgraphForm subgraphForm = new SubgraphForm(stronglyConnectedSubgraphs);
//            subgraphForm.setVisible(true);
//            
            
            String dotAll = CallGraph.directedSubgraphsToDot(callGraph.generateDirectedGraph(), stronglyConnectedSubgraphs);
//            System.out.println(dotAll);
            GraphForm graphForm = new GraphForm("All subgraphs", dotAll);
            graphForm.setVisible(true);
            
        }
    }

     
    
    /**
     * Creates new form GuiMain
     */
    public GuiMain() {
        // nastav vzhled
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
            // use default
        }
        // inicializace komponent
        initComponents();
        
        //pocatecni nastaveni akci
        if (jTabbedPane1.getTabCount() <= 0) {
            closeSourceAction.setEnabled(false);
            closeAllAction.setEnabled(false);
            jTabbedPane1.setVisible(false);
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jMenuItem8 = new javax.swing.JMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItemCloseAll = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuEdit = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenuCheck = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuCallGraph = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        jMenuItem13 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Static Code Verifier");
        setName("mainFrame");
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                MainWindowResized(evt);
            }
        });

        getAccessibleContext().setAccessibleDescription("Static Code Verifier");
        jTabbedPane1.setBackground(new java.awt.Color(238, 238, 238));
        jTabbedPane1.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(100, 125));

        jMenuFile.setText("File");
        jMenuFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuFileActionPerformed(evt);
            }
        });

        jMenuItem1.setAction(openSourceAction);
        jMenuFile.add(jMenuItem1);

        jMenuItem5.setAction(openBatchFileAction);
        jMenuFile.add(jMenuItem5);

        jMenuItem2.setAction(closeSourceAction);
        jMenuFile.add(jMenuItem2);

        jMenuItemCloseAll.setAction(closeAllAction);
        jMenuItemCloseAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCloseAllActionPerformed(evt);
            }
        });

        jMenuFile.add(jMenuItemCloseAll);

        jMenuFile.add(jSeparator2);

        jMenuItem6.setAction(exitAction);
        jMenuFile.add(jMenuItem6);

        jMenuBar1.add(jMenuFile);

        jMenuEdit.setText("Edit");
        jMenuEdit.setEnabled(false);
        jMenuItem10.setAction(findInSourceAction);
        jMenuEdit.add(jMenuItem10);

        jMenuItem11.setAction(findInXMLAction);
        jMenuEdit.add(jMenuItem11);

        jMenuEdit.add(jSeparator3);

        jCheckBoxMenuItem1.setAction(mappingXMLtoSourceAction);
        jMenuEdit.add(jCheckBoxMenuItem1);

        jMenuBar1.add(jMenuEdit);

        jMenuCheck.setText("Check");
        jMenuCheck.setEnabled(false);
        jMenuItem4.setAction(runStaticCheckerAction);
        jMenuCheck.add(jMenuItem4);

        jMenuItem7.setAction(runAllStaticCheckerAction);
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });

        jMenuCheck.add(jMenuItem7);

        jMenuItem3.setAction(checkOwnershipAction);
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });

        jMenuCheck.add(jMenuItem3);

        jMenuBar1.add(jMenuCheck);

        jMenuCallGraph.setText("Call Graph");
        jMenuCallGraph.setEnabled(false);
        jMenuItem9.setAction(showCallGraphAction);
        jMenuCallGraph.add(jMenuItem9);

        jMenuItem12.setAction(showCallGraphAllAction);
        jMenuCallGraph.add(jMenuItem12);

        jMenuCallGraph.add(jSeparator4);

        jMenuItem13.setAction(showStronglyConnectedSubsets);
        jMenuCallGraph.add(jMenuItem13);

        jMenuBar1.add(jMenuCallGraph);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemCloseAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCloseAllActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemCloseAllActionPerformed

    private void jMenuFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuFileActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuFileActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed
    
    private void MainWindowResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_MainWindowResized
        boolean err = false;
        Dimension newSize = ((JFrame) evt.getComponent()).getSize();
        if (newSize.getWidth() < 700) { newSize.setSize(700,newSize.getHeight()); err = true; }
        if (newSize.getHeight() < 400) { newSize.setSize(newSize.getWidth(), 400); err = true; }
        if (err) {this.setSize(newSize);}
    }//GEN-LAST:event_MainWindowResized
    
    public void openSourceFile(File file) {
        SourceAndXMLWindow dataPanel = new SourceAndXMLWindow();
        dataPanel.openSourceFile(file, highlight);
        
        jTabbedPane1.addTab(file.getName(), dataPanel);
        
        jTabbedPane1.setSelectedIndex(jTabbedPane1.getTabCount()-1);
        
        
        final String CLOSE_KEY = "close-action";
        dataPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), CLOSE_KEY);
        dataPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK), CLOSE_KEY);

        dataPanel.getActionMap().put(CLOSE_KEY, new AbstractAction() {
            public void actionPerformed(ActionEvent event) {
                closeSource(jTabbedPane1.getSelectedIndex());
            }
        });
        
        
        // vrat zpet nastaveni, ktere zmenily jine akce
        closeSourceAction.setEnabled(true);
        closeAllAction.setEnabled(true);
        jTabbedPane1.setVisible(true);

        jMenuEdit.setEnabled(true);
        jMenuCheck.setEnabled(true);
        jMenuCallGraph.setEnabled(true);
        
    }

    /**
     * @Todo: this is not a good way of solving a problem. This has to be fixed
     * @return Tabbed pane
     */
    public JTabbedPane getJTabbedPane1() {
        return jTabbedPane1;
    }
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuCallGraph;
    private javax.swing.JMenu jMenuCheck;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JMenuItem jMenuItemCloseAll;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
    
}

