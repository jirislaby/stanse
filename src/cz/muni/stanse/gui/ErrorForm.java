/*
 * CallGraphForm.java
 *
 * Created on 21. listopad 2006, 14:57
 *
 * @author Jan Stastny
 */

package cz.muni.stanse.gui;

import cz.muni.stanse.checker.CheckerError;
import cz.muni.stanse.checker.ErrorTrace;
import cz.muni.stanse.codestructures.CFG;
import cz.muni.stanse.codestructures.CFGNode;
import cz.muni.stanse.utils.Triple;

import java.awt.Component;
import java.io.File;
import java.util.List;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.TreeSelectionEvent;
import org.dom4j.Element;

/**
 * Form for viewing the errors found by static checker.
 * Includes error tracking
 *
 * @author  xstastn
 *
 */

public class ErrorForm extends javax.swing.JFrame {
    
    /**
     * @todo What happens when finding errors in more source codes - we will need set of XML trees
     */
    
    private XMLTree treeXML;
    private SourceAndXMLWindow sourceAndXMLWindow;
    private ErrorTrace actualTrace;
    // Index of the selected node when tracing
    private int actualIndex;
    
    
    /** Creates new form CallGraphForm
     * @param errors Set of errors found by static cehcker
     * @param treeXML XML tree that represents the given source Code
     * @param window source and xml window with the source codes
     */
    public ErrorForm(final List<CheckerError> errors, final XMLTree treeXML,
                     final SourceAndXMLWindow window) {

        this.treeXML = treeXML;
        this.sourceAndXMLWindow = window;
        initComponents();
        loadList(errors);
        class ErrorTraceCellRenderer extends DefaultListCellRenderer {
            @Override
            public Component getListCellRendererComponent(JList jList, Object object, int i, boolean b, boolean b0) {
                Component retValue;
                
                if(object instanceof ErrorTrace) {
                    object = ((ErrorTrace) object).getFullDescription();
                }
                retValue = super.getListCellRendererComponent(jList, object, i, b, b0);
                return retValue;
            }
            
        }
        jComboTraces.setRenderer(new ErrorTraceCellRenderer());
        
        class CheckerErrorCellRenderer extends DefaultListCellRenderer {
            @Override
            public Component getListCellRendererComponent(JList jList, Object object, int i, boolean b, boolean b0) {
                Component retValue;
                
                if(object instanceof CheckerError) {
                    object = ((CheckerError) object).getFullDescription();
                }
                retValue = super.getListCellRendererComponent(jList, object, i, b, b0);
                return retValue;
            }
            
        }
        jComboError.setRenderer(new CheckerErrorCellRenderer());
        
    }
    
    /**
     * Highlight the xml element in the source window
     * @param cfg Control flow graph
     * @param bottom bottom control flow graph node
     * @param bottom top control flow graph node
     */
    private void highlightElement(CFGNode node) {
        final Element element = node.getElement();
// TODO: remove this check, when ALL the CFG nodes will contain related XML
//       element. 
if (element == null)
    return;
        sourceAndXMLWindow.setMappingXMLToSource(true); // TODO: check the checkbox in the menu.
        treeXML.goToNode(element);
        TreeSelectionEvent event = new TreeSelectionEvent(element,null, false, null, null);
        event.cloneWithSource(element);
        sourceAndXMLWindow.getTreeXMLMappingListener().valueChanged(event);
    }
    
    /**
     * Load all the errors in the set to the list of errors
     * @param errors Set of the errors to be added to the list
     */
    private void loadList(final List<CheckerError> errors) {
        for (CheckerError error : errors) {
            this.addItemToList(error);
        }
        
    }
    
    /**
     * Fills the list of error traces
     * @param error Selected error
     */
    private void loadTracesList(CheckerError error) {
        cleanTracesList();
        boolean first = true;
        for(ErrorTrace errorTrace : error.getErrorTraces()) {
            jComboTraces.addItem(errorTrace);
            // select the first trace as active
            if(first) {
                selectErrorTrace(errorTrace);
                jComboTraces.setSelectedIndex(1);
                
                first = false;
            }
        }
    }
    
    /**
     * Cleans all error traces from list of error traces
     */
    private void cleanTracesList() {
        for(int i = 0; i < jComboTraces.getItemCount(); ) {
            if(jComboTraces.getItemAt(i) instanceof ErrorTrace) {
                jComboTraces.removeItemAt(i);
            }
            else
                ++i;
        }
        
        setButtonEnabled(jButtonNext,false);
        setButtonEnabled(jButtonPrevious,false);
        setButtonEnabled(jButtonFirst,false);
        setButtonEnabled(jButtonLast,false);
        
    }
    
    private void moveInTrace(MoveInTraceDirection direction) {
        if(actualTrace == null || actualTrace.getErrorTrace().size() < 1) {
            setButtonEnabled(jButtonNext,false);
            setButtonEnabled(jButtonPrevious,false);
            setButtonEnabled(jButtonFirst,false);
            setButtonEnabled(jButtonLast,false);
            return;
        } else if(actualTrace.getErrorTrace().size() == 1) {
            setButtonEnabled(jButtonNext,false);
            setButtonEnabled(jButtonPrevious,false);
            setButtonEnabled(jButtonFirst,false);
            setButtonEnabled(jButtonLast,false);
            
            highlightElement(actualTrace.getErrorTrace().
                             get(actualIndex).getFirst());
            return;
        }
        
        
        int bottom = actualIndex;
        int top = bottom - 1;
        
        switch(direction) {
            case none:
                // we are not at the first node
                if(actualIndex > 0) {
                    bottom = actualIndex;
                    top = actualIndex - 1;
                }
                // we are at the first node
                else {
                    bottom = actualIndex + 1;
                    top = actualIndex;
                }
                break;
                
            case up:
                // we are at the top - no move
                if(actualIndex == 0) {
                    bottom = actualIndex;
                    top = actualIndex - 1;
                }
                // move up
                else {
                    actualIndex--;
                    bottom = actualIndex;
                    top = actualIndex - 1;
                }
                break;
                
            case down:
                // we are at the end - no move
                if(actualIndex == actualTrace.getErrorTrace().size() - 1) {
                    bottom = actualIndex;
                    top = actualIndex - 1;
                }
                // move down
                else {
                    actualIndex++;
                    bottom = actualIndex;
                    top = actualIndex - 1;
                }
                break;
                
        }

        highlightElement(actualTrace.getErrorTrace().
                         get(actualIndex).getFirst());
        
        
        // we are at the last node
        if(actualIndex == actualTrace.getErrorTrace().size() - 1) {
            setButtonEnabled(jButtonNext,false);
            setButtonEnabled(jButtonPrevious,true);
            setButtonEnabled(jButtonFirst,true);
            setButtonEnabled(jButtonLast,false);
        }
        // we are at the first node
        else if(actualIndex == 0) {
            setButtonEnabled(jButtonNext,true);
            setButtonEnabled(jButtonPrevious,false);
            setButtonEnabled(jButtonFirst,false);
            setButtonEnabled(jButtonLast,true);
        }
        // we are somewhere in between
        else {
            setButtonEnabled(jButtonNext,true);
            setButtonEnabled(jButtonPrevious,true);
            setButtonEnabled(jButtonFirst,true);
            setButtonEnabled(jButtonLast,true);
        }
        
        
    }
    
    private void selectErrorTrace(Object trace) {
        if(trace instanceof ErrorTrace) {
            this.actualTrace = (ErrorTrace) trace;

            //CheckerErrorQueries.findFirstNode(actualTrace, node)
            
            this.actualIndex = 0;
            // tady je chyba
            moveInTrace(MoveInTraceDirection.none);
            jTextPane1.setText(buildActualCFGNodeDescription());
        } else {
            setButtonEnabled(jButtonNext,false);
            setButtonEnabled(jButtonPrevious,false);
            setButtonEnabled(jButtonFirst,false);
            setButtonEnabled(jButtonLast,false);
        }
        if(jComboTraces.getSelectedItem() != trace) {
            jComboTraces.setSelectedItem(trace);
        }
    }
    
    /**
     * Adds new item to the list of errors
     * @param errorName error to be added to the list
     */
    private void addItemToList(Object errorName) {
        jComboError.addItem(errorName);
    }
    
    
    /**
     * Enable or disables the button
     * @param state State to switch it to
     * @param button Button to be switched
     */
    private void setButtonEnabled(JButton button, boolean state) {
        if(state != button.isEnabled()) {
            button.setEnabled(state);
        }
    }
    
    /**
     * Set the image as the label of the image tab
     * @param image image to be set as label
     */
    private void setImage(Icon image) {
        this.jLabelImage.setIcon(image);
    }
    
    
    private CheckerError getActualError() {
        if(this.jComboError.getItemAt(jComboError.getSelectedIndex()) instanceof CheckerError) {
            return (CheckerError) this.jComboError.getItemAt(jComboError.getSelectedIndex());
        } else {
            return null;
        }
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jButtonNext = new javax.swing.JButton();
        jButtonFirst = new javax.swing.JButton();
        jButtonLast = new javax.swing.JButton();
        jButtonPrevious = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPaneText = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPaneImage = new javax.swing.JScrollPane();
        jLabelImage = new javax.swing.JLabel();
        jComboError = new javax.swing.JComboBox();
        jComboTraces = new javax.swing.JComboBox();

        setTitle("Static Checker");
        jButtonNext.setText("Next Node >>");
        jButtonNext.setEnabled(false);
        jButtonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextActionPerformed(evt);
            }
        });

        jButtonFirst.setText("First");
        jButtonFirst.setEnabled(false);
        jButtonFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFirstActionPerformed(evt);
            }
        });

        jButtonLast.setText("Last");
        jButtonLast.setEnabled(false);
        jButtonLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLastActionPerformed(evt);
            }
        });

        jButtonPrevious.setText("<< Previous Node");
        jButtonPrevious.setEnabled(false);
        jButtonPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPreviousActionPerformed(evt);
            }
        });

        jScrollPaneText.setViewportView(jTextPane1);

        jTabbedPane1.addTab("Text", jScrollPaneText);

        jLabelImage.setText("Choose error first.");
        jScrollPaneImage.setViewportView(jLabelImage);

        jTabbedPane1.addTab("Picture", jScrollPaneImage);

        jComboError.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<< Choose Error >>" }));
        jComboError.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboErrorActionPerformed(evt);
            }
        });

        jComboTraces.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<< Choose Error Trace >>" }));
        jComboTraces.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboTracesActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 852, Short.MAX_VALUE)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jComboError, 0, 828, Short.MAX_VALUE)
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jComboTraces, 0, 394, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButtonPrevious)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButtonNext, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 131, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(16, 16, 16)
                .add(jButtonFirst)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButtonLast)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jComboError, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(7, 7, 7)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButtonPrevious)
                    .add(jButtonNext, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButtonFirst)
                    .add(jButtonLast)
                    .add(jComboTraces, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jComboTracesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboTracesActionPerformed
        selectErrorTrace(jComboTraces.getSelectedItem());
        if(jComboError.getSelectedItem() instanceof CheckerError) {
            jTextPane1.setText(buildActualCFGNodeDescription());
            buildTraceGraphPictureAndShowIt();
        }
    }//GEN-LAST:event_jComboTracesActionPerformed
    
    private void jButtonLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLastActionPerformed
        actualIndex = actualTrace.getErrorTrace().size() - 1;
        moveInTrace(MoveInTraceDirection.none);
        jTextPane1.setText(buildActualCFGNodeDescription());
    }//GEN-LAST:event_jButtonLastActionPerformed
    
    private void jButtonFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFirstActionPerformed
        actualIndex = 0;
        moveInTrace(MoveInTraceDirection.none);
        jTextPane1.setText(buildActualCFGNodeDescription());//GEN-LAST:event_jButtonFirstActionPerformed
    }                                            
    
    private void jButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextActionPerformed
        moveInTrace(MoveInTraceDirection.down);
        jTextPane1.setText(buildActualCFGNodeDescription());//GEN-LAST:event_jButtonNextActionPerformed
    }                                           
    
    private void jButtonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPreviousActionPerformed
        moveInTrace(MoveInTraceDirection.up);
        jTextPane1.setText(buildActualCFGNodeDescription());//GEN-LAST:event_jButtonPreviousActionPerformed
    }                                               
    
    private void jComboErrorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboErrorActionPerformed
        cleanTracesList();
        
        if(jComboError.getSelectedItem() instanceof CheckerError) {
            
            loadTracesList((CheckerError) jComboError.getSelectedItem());
            
            jTextPane1.setText(buildActualCFGNodeDescription());
            buildTraceGraphPictureAndShowIt();

        } else {
            setButtonEnabled(jButtonPrevious, false);
            setButtonEnabled(jButtonNext, false);
            setButtonEnabled(jButtonFirst, false);
            setButtonEnabled(jButtonLast, false);
            
            this.jTextPane1.setText("Choose error first.");
            this.jLabelImage.setText("Choose error first.");
            this.setImage(null);
            
        }
    }//GEN-LAST:event_jComboErrorActionPerformed
    
    private final void buildTraceGraphPictureAndShowIt() {
        final GraphViz gv = new GraphViz();
        gv.graph.append(CheckerErrorToDot.run(actualTrace));
        try {
            final File out = File.createTempFile("SCVGraph",".png");
            out.deleteOnExit();
            gv.writeGraphToFile(gv.getGraph(gv.getDotSource()), out);
            this.setImage(new ImageIcon(out.getAbsolutePath()));
            this.jLabelImage.setText(null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private final String buildActualCFGNodeDescription() {

        final Triple<CFGNode,String,CFG> node =
                actualTrace.getErrorTrace().get(actualIndex);
        return "<" + node.getFirst().toString() + ','
                   + node.getSecond() + ","
                   + node.getThird().toString() + ">\n";
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonFirst;
    private javax.swing.JButton jButtonLast;
    private javax.swing.JButton jButtonNext;
    private javax.swing.JButton jButtonPrevious;
    private javax.swing.JComboBox jComboError;
    private javax.swing.JComboBox jComboTraces;
    private javax.swing.JLabel jLabelImage;
    private javax.swing.JScrollPane jScrollPaneImage;
    private javax.swing.JScrollPane jScrollPaneText;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
    
}

/**
 * Movement directions
 */
enum MoveInTraceDirection {
    up, down, none;
}

