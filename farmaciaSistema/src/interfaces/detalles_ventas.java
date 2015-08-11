/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author javy
 */
public class detalles_ventas extends javax.swing.JFrame  {

    /**
     * Creates new form clientes
     */
     int fila=0;
    DefaultTableModel model;
    public detalles_ventas() {
        initComponents();
        cargarTabla("");
       getContentPane().setBackground(new java.awt.Color(12,120,200));
        // botonesIniciales();
        cargarMedicamento();
        cargarVenta();
        setTitle("D E T A L L E  D E  L A  V E N T A");
        setBounds(550, 0, 550, 465);
        
        //setBounds(800, 0, 565, 500);
//        venta.setText(ventas.txtVenNum.getText());
        //vent.setSelectedItem(ventas.txtVenNum.getText());
        tblFarmaceuticos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            //override sobre cargar metodos para realizar varias cosas
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if(tblFarmaceuticos.getSelectedRow()!=-1){
                    fila = tblFarmaceuticos.getSelectedRow();
                    //txtVisCedula1.setSelectedItem(tblFarmaceuticos.getValueAt(fila, 0).toString());
                    medicamento.setText(tblFarmaceuticos.getValueAt(fila, 0).toString());
                    txtVisrNombre.setText(tblFarmaceuticos.getValueAt(fila, 1).toString());
                    venta.setText(tblFarmaceuticos.getValueAt(fila, 2).toString());
                    //txtVisApellido1.setSelectedItem(tblFarmaceuticos.getValueAt(fila, 2).toString());
                    //txtFarSueldo.setText(tblFarmaceuticos.getValueAt(fila, 3).toString());
                  //  botonesDesbloqueados();
                  //  Bloquear();
                }                    
                //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
    }

    void botonNuevo(){
       botonesIniciales();
       btnIngreso.setEnabled(true);
        medica.setEnabled(true);
       limpiar();
    }
    
    void botonesIniciales(){
 //       jButton2.setEnabled(true);
        btnBorrar.setEnabled(false);
        btnIngreso.setEnabled(false);
        btnActualizar.setEnabled(false); 
    }
    
    void botonesDesbloqueados(){
        btnBorrar.setEnabled(true);
        btnIngreso.setEnabled(false);
        btnActualizar.setEnabled(true); 
    }
    
    
    void Bloquear(){
        medica.setEnabled(false);
    }
    void limpiar(){
        medicamento.setText("");
        txtVisrNombre.setText("");
       // venta.setText("");
        //txtFarSueldo.setText("");
    }
    
void cargarMedicamento(){
        try{
           //medica.removeAllItems();
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql="";
            sql= "Select * from medicamentos ORDER BY COD_MED";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while(rs.next()){
                medica.addItem(rs.getString("COD_MED")+" "+rs.getString("NOM_MED"));
            }
        }catch(Exception ex){
         JOptionPane.showMessageDialog(null, ex);
        }
    }
void cargarVenta(){
        try{
           //vent.removeAllItems();
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql="";
            sql= "Select * from ventas ORDER BY NUM_VEN";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while(rs.next()){
                vent.addItem(rs.getString("NUM_VEN"));
            }
        }catch(Exception ex){
        JOptionPane.showMessageDialog(null, ex);
        }
    }
    void botonIngreso(){
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String ci_bod;
            int nom_bod,ape_bod;
            //ci_bod  = txtVisCedula1.getSelectedItem().toString();
            ci_bod = medicamento.getText();
            nom_bod = Integer.valueOf(txtVisrNombre.getText());
            ape_bod = Integer.valueOf(venta.getText());
            //ape_bod  = txtVisApellido1.getSelectedItem().toString();
           // sue_bod  = txtFarSueldo.getText();
            String sql="";
            sql="INSERT INTO detalles_ventas (COD_MED_V,CANT_VEN,NUM_VEN) VALUES (?,?,?)";
           PreparedStatement psd = cn.prepareStatement(sql);
         
           psd.setString(1, ci_bod);
           psd.setInt(2, nom_bod);
           psd.setInt(3, ape_bod);
           //psd.setString(4, sue_bod);
            int n = psd.executeUpdate();
            if(n>0){
               JOptionPane.showMessageDialog(null, "Se inserto correctamente");
               limpiar();
 //              bloquear();
 //              botonesiniciales();
               cargarTabla("");
                                        
             }
        } catch (Exception ex) {
      JOptionPane.showMessageDialog(null, ex);
                 }
        
    }
    
    void botonEliminar(){
           if(JOptionPane.showConfirmDialog(null, "Estas seguro que deseas borrar el dato","Borrar registro",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE)==JOptionPane.YES_OPTION){        
             
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql="";
        sql ="DELETE FROM detalles_ventas where COD_MED_V = '"+medicamento.getText()+"'AND CANT_VEN = '"+txtVisrNombre.getText()+"'AND NUM_VEN = '"+venta.getText()+"'"; 
           try{
            PreparedStatement psd = cn.prepareStatement(sql);
            int n = psd.executeUpdate(sql);
             if(n>0){
               JOptionPane.showMessageDialog(null, "Se borro correctamente");
               limpiar();
 //              bloquear();
 //              botonesiniciales();
              cargarTabla("");
                                        
             }
        }catch(Exception ex){
        JOptionPane.showMessageDialog(null, ex);
        }
    }
    }
    void botonActualizar(){
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql="";
        sql ="UPDATE detalles_ventas  SET CANT_VEN = '"+txtVisrNombre.getText()+"' where COD_MED_V = '"+medicamento.getText()+"'AND NUM_VEN = '"+venta.getText()+"'"; 
           try{
            PreparedStatement psd = cn.prepareStatement(sql);
            int n = psd.executeUpdate(sql);
             if(n>0){
               JOptionPane.showMessageDialog(null, "Se actualizo correctamente");
               limpiar();
 //              bloquear();
 //              botonesiniciales();
               cargarTabla("");
                                        
             }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    
    }
   
    
    void cargarTabla(String dato){
         
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String titulos []= {"VENTA","CANTIDAD","MEDICAMENTO"};
        model= new DefaultTableModel(null,titulos);
        String registro[] = new String [3]; 
        String sql="";
        sql = "Select * from detalles_ventas where NUM_VEN like '%"+dato+"%'order by num_ven";
        try{
        Statement psd = cn.createStatement();
        ResultSet rs = psd.executeQuery(sql);
        while(rs.next()){
            registro[2]=rs.getString("COD_MED_V");
            registro[1]=rs.getString("CANT_VEN");
            registro[0]=rs.getString("NUM_VEN");
           // registro[3]=rs.getString("SUE_FAR");
            model.addRow(registro);
        }
          tblFarmaceuticos.setModel(model);
        }catch(Exception ex){
        JOptionPane.showMessageDialog(null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFarmaceuticos = new javax.swing.JTable();
        btnIngreso = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtVisrNombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnBorrar = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        vent = new javax.swing.JComboBox();
        medica = new javax.swing.JComboBox();
        medicamento = new javax.swing.JTextField();
        venta = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("CANTIDAD");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("NÚMERO DE VENTA");

        tblFarmaceuticos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblFarmaceuticos);

        btnIngreso.setText("GUARDAR");
        btnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresoActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CÓDIGO MEDICAMENTO");

        txtVisrNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtVisrNombreFocusLost(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("DETALLE VENTAS");

        btnBorrar.setText("BORRAR");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("BÚSQUEDA POR VENTA");

        jButton1.setText("SALIR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        vent.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "VENTAS" }));
        vent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ventActionPerformed(evt);
            }
        });

        medica.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MEDICAMENTOS" }));
        medica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medicaActionPerformed(evt);
            }
        });

        medicamento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                medicamentoFocusLost(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtVisrNombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                    .addComponent(medicamento, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(venta))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(medica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(vent, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(medicamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(medica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtVisrNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(venta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(vent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnIngreso)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBorrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnActualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addGap(18, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresoActionPerformed
        // TODO add your handling code here:
        botonIngreso();
        ventas.cargarTabla("");
       
    }//GEN-LAST:event_btnIngresoActionPerformed

    private void txtVisrNombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVisrNombreFocusLost
        // TODO add your handling code here:
        txtVisrNombre.setText(txtVisrNombre.getText().toUpperCase());
    }//GEN-LAST:event_txtVisrNombreFocusLost

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
        botonEliminar();
        ventas.cargarTabla("");
       
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        // TODO add your handling code here:
        cargarTabla(jTextField4.getText());
    }//GEN-LAST:event_jTextField4KeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        botonActualizar();
        ventas.cargarTabla("");
        
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void medicamentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_medicamentoFocusLost
        // TODO add your handling code here:
        medicamento.setText(medicamento.getText().toString());
    }//GEN-LAST:event_medicamentoFocusLost

    private void medicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medicaActionPerformed
        // TODO add your handling code here:
        medicamento.setText(medica.getSelectedItem().toString().substring(0, 2));
    }//GEN-LAST:event_medicaActionPerformed

    private void ventActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ventActionPerformed
        // TODO add your handling code here:
        venta.setText(vent.getSelectedItem().toString());
    }//GEN-LAST:event_ventActionPerformed

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
            java.util.logging.Logger.getLogger(detalles_ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(detalles_ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(detalles_ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(detalles_ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new detalles_ventas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnIngreso;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JComboBox medica;
    private javax.swing.JTextField medicamento;
    private javax.swing.JTable tblFarmaceuticos;
    private javax.swing.JTextField txtVisrNombre;
    private javax.swing.JComboBox vent;
    public static javax.swing.JTextField venta;
    // End of variables declaration//GEN-END:variables
}
