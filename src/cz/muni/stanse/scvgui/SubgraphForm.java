/*
 * SubgraphForm.java
 *
 * Created on 18. prosinec 2006, 12:10
 */

package cz.muni.stanse.scvgui;

import cz.muni.stanse.callgraph.CallGraph;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedSubgraph;

/**
 *
 * @author  xstastn
 */
public class SubgraphForm extends javax.swing.JFrame {
    
    private List<DirectedSubgraph<String, DefaultEdge>> directedSubgraphs;
    
    /** Creates new form SubgraphForm */
    public SubgraphForm(List<DirectedSubgraph<String, DefaultEdge>> subgraphs) {
        initComponents();
        
        this.directedSubgraphs = subgraphs;
        
        int i = 1;
        for(DirectedSubgraph<String, DefaultEdge> subgraph : subgraphs) {
            JScrollPane pane = new JScrollPane();
            JLabel image = new JLabel();
            
            GraphViz gv = new GraphViz();
            gv.graph.append(CallGraph.directedGraphToDot(subgraph));
            
            try {
                File out = new File("");
                out = out.createTempFile("Directed Subgraph " + i,".png");
                gv.writeGraphToFile(gv.getGraph(gv.getDotSource()), out);
                image.setIcon(new ImageIcon(out.getAbsolutePath()));
            } catch (IOException ex) {
                ex.printStackTrace();
             
            }
        
            //image.setText("Choose error first.");
            pane.setViewportView(image);
            
            //Component tab = new JScrollPane();
            
            jTabbedPane1.insertTab("Subset " + i,null, pane, "", jTabbedPane1.getComponentCount());
            i++;
        }

        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jTabbedPane1 = new javax.swing.JTabbedPane();

        setTitle("Strongly Connected Subsets");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 638, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
    
}