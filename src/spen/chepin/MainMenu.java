/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spen.chepin;

import edu.uci.ics.jung.algorithms.filters.KNeighborhoodFilter;
import edu.uci.ics.jung.algorithms.layout.BalloonLayout;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout2;
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.LayoutDecorator;
import edu.uci.ics.jung.algorithms.layout.RadialTreeLayout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.layout.ObservableCachingLayout;
import edu.uci.ics.jung.visualization.layout.PersistentLayoutImpl;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class MainMenu extends javax.swing.JFrame {

    public MainMenu() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser = new javax.swing.JFileChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jOpen = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTextPane1.setEditable(false);
        jScrollPane2.setViewportView(jTextPane1);

        jOpen.setText("Open");
        jOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jOpenMenuItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jOpen, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jOpen))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    static public String code;
    static public String code2;
    static public String path;
    static public HashSet<String> usesSet;
    static public Hashtable<String, Atributes> vars;
    static public Hashtable<String, Matrix> matrix;

    /*
    public void createGraph(Matrix m,String name)
    {
        Graph<String, String>g = new SparseMultigraph<String, String>();
        for (int i=0;i<m.getLength();i++)
            g.addVertex(i+"."+m.getName(i));
        int edge=0;
        for (int i=0; i<m.getLength(); i++)
               for (int j=0; j<m.getLength(); j++)
                   if (m.get(i, j)==1)
                   {
                        g.addEdge("E"+edge, i+"."+m.getName(i), j+"."+m.getName(j),EdgeType.DIRECTED);
                        edge++;
                   }
                Layout<Integer, String> layout = new KKLayout(g);//KK
		layout.setSize(new Dimension(1000,800)); 
		BasicVisualizationServer<Integer,String> vv =
		new BasicVisualizationServer<Integer,String>(layout);
		vv.setPreferredSize(new Dimension(1000,700)); 
                
                vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
                
		JFrame frame = new JFrame(name);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().add(vv);
		frame.pack();
                frame.setMinimumSize(new Dimension(1000,700));
		frame.setVisible(true);
    }
    */
    private void jOpenMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jOpenMenuItemActionPerformed
        // TODO add your handling code here:
        matrix = new Hashtable<String, Matrix>();
        vars = new Hashtable<String, Atributes>();
        usesSet = new HashSet<String>();
        code = "";
        code2 = "";
        path = "";
        String buf;
        jFileChooser.showOpenDialog(null);
        File f = jFileChooser.getSelectedFile();
        path = f.getParent();
        FileReader fr = null; 
        try {
            fr = new FileReader(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader br = new BufferedReader(fr);
        try {
            while ((buf = br.readLine()) != null)
                code = code + buf+" ";
        } catch (IOException ex) {
            Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        code2 = new String(code);
       code = Pars.LoadAllUses(code);
    //   System.out.println(code2);
       Pars.analiz();
       code2= Pars2.LoadAllUses(code2);
       Pars2.analiz();
       
       
     /*  for (String name : matrix.keySet())
       {
           int e=0,v;
           Matrix mx = matrix.get(name);
           jTextArea1.append(name+": ");
           v=mx.getLength();
           for (int i=0; i<v; i++)
           {
               for (int j=0; j<v; j++)
                   System.out.print(mx.get(i,j));
               System.out.println();
           }
       }*/
       
       /*
       jTextArea1.setText("Метрика Майерса:\n");
       for (String name : matrix.keySet())
       {
           int e=0,v;
           Matrix mx = matrix.get(name);
          // createGraph(mx, name);
           jTextArea1.append(name+": ");
           v=mx.getLength();
           int h=mx.getH();
           for (int i=0; i<v; i++)
               for (int j=0; j<v; j++)
                   e+=mx.get(i,j);
           int g = e-v+2;
           jTextArea1.append(String.format("[%d,%d]\n", g,g+h));
       }
       */
       jTable1.setModel(new javax.swing.table.DefaultTableModel(new String[] 
       {"Перем.","P","M","C","T","Спен"},vars.size()+1));

       int P=0,M=0,C=0,T=0;
       int i=0;
       for (String var : vars.keySet())
       {
           jTable1.setValueAt(var, i, 0);
           if (vars.get(var).P)
           {
               jTable1.setValueAt("+", i, 1);
               P++;
           }
           else
                jTable1.setValueAt("-", i, 1);
           if (vars.get(var).M)
           {
               jTable1.setValueAt("+", i, 2);
               M++;
           }
           else
                jTable1.setValueAt("-", i, 2);
           if (vars.get(var).C)
           {
               jTable1.setValueAt("+", i, 3);
               C++;
           }
           else
                jTable1.setValueAt("-", i, 3);
           if (vars.get(var).T)
           {
               jTable1.setValueAt("+", i, 4);
               T++;
           }
           else
                jTable1.setValueAt("-", i, 4);
           jTable1.setValueAt(vars.get(var).count, i, 5);
           i++;
       }
       jTable1.setValueAt("Всего:", i, 0);
       jTable1.setValueAt(P, i, 1);
       jTable1.setValueAt(M, i, 2);
       jTable1.setValueAt(C, i, 3);
       jTable1.setValueAt(T, i, 4);
       
       String s = String.format("Q = P + 2*M + 3*C + 0.5*T = %.1f", 
               (double)(P+2*M+3*C)+(0.5*(double)T));
       jTextPane1.setText(s);
       
  /*     System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
       for (String s : vars.keySet())
           System.out.println(s+" "+vars.get(s).count+" "+
                   vars.get(s).M+" "+vars.get(s).C+" "+
                        vars.get(s).P+" "+vars.get(s).T);*/
    }//GEN-LAST:event_jOpenMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser;
    private javax.swing.JButton jOpen;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}
