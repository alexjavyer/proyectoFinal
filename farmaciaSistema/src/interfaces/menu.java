/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.beans.PropertyVetoException;
import javax.swing.JOptionPane;

/**
 *
 * @author javy
 */
public class menu extends javax.swing.JFrame {

    /**
     * Creates new form clientes
     */
    public menu() {
        initComponents();
//        jDesktopPane1.setBorder(new ImagenFondo());
                 setExtendedState(menu.MAXIMIZED_BOTH);
          setTitle("F A R M A C I A");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelprincipal = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jmFarmaceuticos = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jmBodegueros = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jmAdministrador = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MENU PRINCIPAL");

        javax.swing.GroupLayout panelprincipalLayout = new javax.swing.GroupLayout(panelprincipal);
        panelprincipal.setLayout(panelprincipalLayout);
        panelprincipalLayout.setHorizontalGroup(
            panelprincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        panelprincipalLayout.setVerticalGroup(
            panelprincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
        );

        jmFarmaceuticos.setText("FARMACEUTICOS");

        jMenuItem10.setText("FARMACEUTICOS");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jmFarmaceuticos.add(jMenuItem10);

        jMenuItem1.setText("LISTA DE FARMACEUTICOS");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jmFarmaceuticos.add(jMenuItem1);

        jMenuItem2.setText("CLIENTES");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jmFarmaceuticos.add(jMenuItem2);

        jMenuItem3.setText("VENTAS");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jmFarmaceuticos.add(jMenuItem3);

        jMenuItem4.setText("MEDICAMENTOS");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jmFarmaceuticos.add(jMenuItem4);

        jMenuBar1.add(jmFarmaceuticos);

        jmBodegueros.setText("BODEGUEROS");

        jMenuItem11.setText("BODEGUEROS");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jmBodegueros.add(jMenuItem11);

        jMenuItem5.setText("LISTA DE BODEGUEROS");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jmBodegueros.add(jMenuItem5);

        jMenuItem6.setText("VISITADORES");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jmBodegueros.add(jMenuItem6);

        jMenuItem7.setText("PEDIDOS");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jmBodegueros.add(jMenuItem7);

        jMenuItem8.setText("MEDICAMENTOS");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jmBodegueros.add(jMenuItem8);

        jMenuBar1.add(jmBodegueros);

        jMenu1.setText("REPORTES");

        jMenuItem14.setText("jMenuItem14");
        jMenu1.add(jMenuItem14);

        jMenuItem15.setText("jMenuItem15");
        jMenu1.add(jMenuItem15);

        jMenuItem16.setText("jMenuItem15");
        jMenu1.add(jMenuItem16);

        jMenuItem17.setText("jMenuItem15");
        jMenu1.add(jMenuItem17);

        jMenuItem18.setText("jMenuItem15");
        jMenu1.add(jMenuItem18);

        jMenuItem19.setText("jMenuItem15");
        jMenu1.add(jMenuItem19);

        jMenuItem20.setText("jMenuItem15");
        jMenu1.add(jMenuItem20);

        jMenuItem21.setText("jMenuItem15");
        jMenu1.add(jMenuItem21);

        jMenuItem22.setText("jMenuItem15");
        jMenu1.add(jMenuItem22);

        jMenuBar1.add(jMenu1);

        jmAdministrador.setText("USUARIOS");

        jMenuItem9.setText("USUARIOS");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jmAdministrador.add(jMenuItem9);

        jMenuBar1.add(jmAdministrador);

        jMenu2.setText("OPCIONES");

        jMenuItem12.setText("CAMBIAR DE USUARIO");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem12);

        jMenuItem13.setText("SALIR");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem13);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelprincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelprincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
//        Mostrar_Farmaceuticos far=new Mostrar_Farmaceuticos();
//    panelprincipal.add(far);
//        try {
//            far.setMaximum(true);
//        } catch (PropertyVetoException ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
//    far.show();
        Mostrar_Farmaceuticos far = new Mostrar_Farmaceuticos();
           jDesktopPane1.add(far);
        far.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
//        clientes cli=new clientes();
//    panelprincipal.add(cli);
//        try {
//            cli.setMaximum(true);
//        } catch (PropertyVetoException ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
//    cli.show();
        clientes far = new clientes();
        jDesktopPane1.add(far);
        far.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
//        ventas ven=new ventas();
//    panelprincipal.add(ven);
//        try {
//            ven.setMaximum(true);
//        } catch (PropertyVetoException ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
//    ven.show();
        ventas far = new ventas();
        jDesktopPane1.add(far);
        far.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
//        pedidos ped=new pedidos();
//    panelprincipal.add(ped);
//        try {
//            ped.setMaximum(true);
//        } catch (PropertyVetoException ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
//    ped.show();
         pedidos far = new pedidos();
           jDesktopPane1.add(far);
        far.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
//        usuarios usu=new usuarios();
//    panelprincipal.add(usu);
//        try {
//            usu.setMaximum(true);
//        } catch (PropertyVetoException ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
//    usu.show();
        usuarios far = new usuarios();
        jDesktopPane1.add(far);
        far.setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
//        Mostrar_medicamentos med=new Mostrar_medicamentos();
//    panelprincipal.add(med);
//        try {
//            med.setMaximum(true);
//        } catch (PropertyVetoException ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
//    med.show();
        medicamentos far = new medicamentos();
        jDesktopPane1.add(far);
        far.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
//        Mostrar_Bodegueros bod=new Mostrar_Bodegueros();
//    panelprincipal.add(bod);
//        try {
//            bod.setMaximum(true);
//        } catch (PropertyVetoException ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
//    bod.show();
                Mostrar_Bodegueros far = new Mostrar_Bodegueros();
           jDesktopPane1.add(far);
        far.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
//        farmaceuticos vis=new farmaceuticos();
//    panelprincipal.add(vis);
//        try {
//            vis.setMaximum(true);
//        } catch (PropertyVetoException ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
//    vis.show();
        visitadores_medicos far = new visitadores_medicos();
           jDesktopPane1.add(far);
        far.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
//        medicamentos med=new medicamentos();
//    panelprincipal.add(med);
//        try {
//            med.setMaximum(true);
//        } catch (PropertyVetoException ex) {
//            JOptionPane.showMessageDialog(null, ex);
//        }
//    med.show();os
                medicamentos far = new medicamentos();
           jDesktopPane1.add(far);
        far.setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
         dispose();
        ingresoAlSistema ingre = new ingresoAlSistema();
        ingre.show();
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        farmaceuticos far = new farmaceuticos();
        //far.show();
           jDesktopPane1.add(far);
        far.setVisible(true);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
           bodegueros far = new bodegueros();
           jDesktopPane1.add(far);
        far.setVisible(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

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
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    public static javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    public javax.swing.JMenu jmAdministrador;
    public javax.swing.JMenu jmBodegueros;
    public javax.swing.JMenu jmFarmaceuticos;
    public static javax.swing.JPanel panelprincipal;
    // End of variables declaration//GEN-END:variables
}
