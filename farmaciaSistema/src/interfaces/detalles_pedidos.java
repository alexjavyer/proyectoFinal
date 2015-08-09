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
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author javy
 */
public class detalles_pedidos extends javax.swing.JInternalFrame {
    DefaultTableModel model;

    /**
     * Creates new form detalles_pedidos
     */
    public detalles_pedidos() {
        initComponents();
        jtbDetallesPedidos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
             if(jtbDetallesPedidos.getSelectedRow()!=-1){
                 int fila = jtbDetallesPedidos.getSelectedRow();
                    desbloquear();
                    txtCodigo.setText(jtbDetallesPedidos.getValueAt(fila, 0).toString());
                    txtCantidad.setText(jtbDetallesPedidos.getValueAt(fila, 1).toString());
                    txtNumero.setText(jtbDetallesPedidos.getValueAt(fila, 2).toString());
                    jbtActualizar.setEnabled(true);
                    jbtBorrar.setEnabled(true);
                    jbtCancelar.setEnabled(true);                   
             }
            }
        }
        );
        bloquear();
        botonesIniciales();
        cargarDetallePedidos();
        CargarPedido();
        CargarMedicamento();
        limpiar();
    }
    public void botonesIniciales(){
        jbtNuevo.setEnabled(true);
        jbtBorrar.setEnabled(false);
        jbtActualizar.setEnabled(false);
        jbtCancelar.setEnabled(false);
        jbtGuardar.setEnabled(false);
        jbtSalir.setEnabled(true);
        jcbMedicamento.setEnabled(false);
        jcbPedido.setEnabled(false);
    }
    
    public void limpiar(){
        txtNumero.setText("");
        txtCodigo.setText("");
        txtCantidad.setText("");
    }
    
    public void desbloquear(){
        txtNumero.setEnabled(true);
        txtCodigo.setEnabled(true);
        txtCantidad.setEnabled(true);
        jcbMedicamento.setEnabled(true);
        jcbPedido.setEnabled(true);
    }
    
    public void bloquear(){
        txtNumero.setEnabled(false);
        txtCantidad.setEnabled(false);
        txtCodigo.setEnabled(false);
        jcbMedicamento.setEnabled(false);
        jcbPedido.setEnabled(false);
    }
    
    public void Nuevo(){
        desbloquear();
        jbtGuardar.setEnabled(true);
        jbtCancelar.setEnabled(true);
        limpiar();
    }
    
    public void CargarPedido() {
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "SELECT * FROM PEDIDO ORDER BY NUM_PED";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                jcbPedido.addItem(rs.getString("NUM_PED"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void CargarMedicamento() {
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "SELECT * FROM MEDICAMENTOS ORDER BY COD_MED";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                jcbMedicamento.addItem(rs.getString("COD_MED"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void cargarDetallePedidos(){
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String titulo[] = {"CÓDIGO DE MEDICINA","CANTIDAD","NÚMERO DE PEDIDO"};
        model = new DefaultTableModel(null,titulo);
        String registros [] = new String[3];
        String sql,dato=txtBusqueda.getText();
        sql = "select * from DETALLE_PEDIDO where COD_MED_P like '%"+dato+"%'";
        try{
        Statement psd = cn.createStatement();
        ResultSet rs = psd.executeQuery(sql);
        while(rs.next()){
            registros[0]=rs.getString("COD_MED_P");
            registros[1]=rs.getString("CANT_P");
            registros[2]=rs.getString("NUM_PED");
            model.addRow(registros);
        }
        jtbDetallesPedidos.setModel(model);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
      
    public void Guardar(){
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql="";
            sql="insert into DETALLE_PEDIDO (COD_MED_P,CANT_P,NUM_PED) values (?,?,?)";
            PreparedStatement psd = cn.prepareStatement(sql);
            psd.setString(1, txtCodigo.getText());
            psd.setInt(2, Integer.valueOf(txtCantidad.getText()));
            psd.setString(3, txtNumero.getText());
            int n=psd.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(null, "Se inserto correctamente");
                limpiar();
                botonesIniciales();
                bloquear();
                cargarDetallePedidos();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void controlCantidad(){
        pedidos pe =new pedidos();
        pe.cargarPedidos("");
        
    }
    
    public void Cancelar(){
        botonesIniciales();
        limpiar();
        bloquear();
    }
    
    public void Actualizar(){
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql="";
        sql="UPDATE DETALLE_PEDIDO SET CANT_P='"+txtCantidad.getText()+"' WHERE COD_MED_P = '"+txtCodigo.getText()+"' AND NUM_PED = '"+txtNumero.getText()+"' AND CANT_P = '"+txtCantidad.getText()+"'";
        try{
            PreparedStatement psd = cn.prepareStatement(sql);
            if(psd.executeUpdate()>0){
                JOptionPane.showMessageDialog(null,"Se actualizó correctamente");
                cargarDetallePedidos();
                botonesIniciales();
                limpiar();
                bloquear();
            }
        }catch(Exception ex ){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void Borrar(){
        if(JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR", "ELIMINAR", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE)==JOptionPane.YES_OPTION){
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql="";
        sql="DELETE FROM detalle_pedido WHERE COD_MED_P = '"+txtCodigo.getText()+"' AND NUM_PED = '"+txtNumero.getText()+"' AND CANT_P = '"+txtCantidad.getText()+"'";
        try{
            PreparedStatement psd = cn.prepareStatement(sql);
            if(psd.executeUpdate()>0){
                JOptionPane.showMessageDialog(null,"Se eliminó correctamente");
                cargarDetallePedidos();
                botonesIniciales();
                limpiar();
                bloquear();
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
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

        jpnDatos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtNumero = new javax.swing.JTextField();
        jcbMedicamento = new javax.swing.JComboBox();
        jcbPedido = new javax.swing.JComboBox();
        jpnBotones = new javax.swing.JPanel();
        jbtNuevo = new javax.swing.JButton();
        jbtGuardar = new javax.swing.JButton();
        jbtCancelar = new javax.swing.JButton();
        jbtActualizar = new javax.swing.JButton();
        jbtBorrar = new javax.swing.JButton();
        jbtSalir = new javax.swing.JButton();
        jpnBusqueda = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtBusqueda = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbDetallesPedidos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("DETALLE DE PEDIDOS");

        jpnDatos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("Código Medicamento");

        jLabel2.setText("Cantidad");

        jLabel3.setText("Número de Pedido");

        jcbMedicamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbMedicamentoActionPerformed(evt);
            }
        });

        jcbPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbPedidoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnDatosLayout = new javax.swing.GroupLayout(jpnDatos);
        jpnDatos.setLayout(jpnDatosLayout);
        jpnDatosLayout.setHorizontalGroup(
            jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnDatosLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(31, 31, 31)
                .addGroup(jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNumero))
                .addGap(35, 35, 35)
                .addGroup(jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbMedicamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jcbPedido, 0, 93, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpnDatosLayout.setVerticalGroup(
            jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnDatosLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addGroup(jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcbMedicamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jcbPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jpnBotones.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jbtNuevo.setText("Nuevo");
        jbtNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtNuevoActionPerformed(evt);
            }
        });

        jbtGuardar.setText("Guardar");
        jbtGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtGuardarActionPerformed(evt);
            }
        });

        jbtCancelar.setText("Cancelar");
        jbtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCancelarActionPerformed(evt);
            }
        });

        jbtActualizar.setText("Actualizar");
        jbtActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtActualizarActionPerformed(evt);
            }
        });

        jbtBorrar.setText("Borrar");
        jbtBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtBorrarActionPerformed(evt);
            }
        });

        jbtSalir.setText("Salir");
        jbtSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnBotonesLayout = new javax.swing.GroupLayout(jpnBotones);
        jpnBotones.setLayout(jpnBotonesLayout);
        jpnBotonesLayout.setHorizontalGroup(
            jpnBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnBotonesLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jpnBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtSalir)
                    .addComponent(jbtBorrar)
                    .addComponent(jbtActualizar)
                    .addComponent(jbtCancelar)
                    .addComponent(jbtGuardar)
                    .addComponent(jbtNuevo))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jpnBotonesLayout.setVerticalGroup(
            jpnBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnBotonesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtActualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtBorrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtSalir))
        );

        jpnBusqueda.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setText("Busqueda");

        javax.swing.GroupLayout jpnBusquedaLayout = new javax.swing.GroupLayout(jpnBusqueda);
        jpnBusqueda.setLayout(jpnBusquedaLayout);
        jpnBusquedaLayout.setHorizontalGroup(
            jpnBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(55, 55, 55)
                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jpnBusquedaLayout.setVerticalGroup(
            jpnBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnBusquedaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpnBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jtbDetallesPedidos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jtbDetallesPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jtbDetallesPedidos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpnBusqueda, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpnDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jpnBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpnBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpnBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtNuevoActionPerformed
        // TODO add your handling code here:
        Nuevo();
    }//GEN-LAST:event_jbtNuevoActionPerformed

    private void jbtGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtGuardarActionPerformed
        // TODO add your handling code here:
        Guardar();
    }//GEN-LAST:event_jbtGuardarActionPerformed

    private void jbtCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtCancelarActionPerformed
        // TODO add your handling code here:
        Cancelar();
    }//GEN-LAST:event_jbtCancelarActionPerformed

    private void jbtActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtActualizarActionPerformed
        // TODO add your handling code here:
       Actualizar();
    }//GEN-LAST:event_jbtActualizarActionPerformed

    private void jbtBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtBorrarActionPerformed
        // TODO add your handling code here:
        Borrar();
    }//GEN-LAST:event_jbtBorrarActionPerformed

    private void jbtSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jbtSalirActionPerformed

    private void jcbMedicamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbMedicamentoActionPerformed
        // TODO add your handling code here:
        txtCodigo.setText(jcbMedicamento.getSelectedItem().toString());
    }//GEN-LAST:event_jcbMedicamentoActionPerformed

    private void jcbPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbPedidoActionPerformed
        // TODO add your handling code here:
        txtNumero.setText(jcbPedido.getSelectedItem().toString());
    }//GEN-LAST:event_jcbPedidoActionPerformed

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
            java.util.logging.Logger.getLogger(detalles_pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(detalles_pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(detalles_pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(detalles_pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new detalles_pedidos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtActualizar;
    private javax.swing.JButton jbtBorrar;
    private javax.swing.JButton jbtCancelar;
    private javax.swing.JButton jbtGuardar;
    private javax.swing.JButton jbtNuevo;
    private javax.swing.JButton jbtSalir;
    private javax.swing.JComboBox jcbMedicamento;
    private javax.swing.JComboBox jcbPedido;
    private javax.swing.JPanel jpnBotones;
    private javax.swing.JPanel jpnBusqueda;
    private javax.swing.JPanel jpnDatos;
    private javax.swing.JTable jtbDetallesPedidos;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNumero;
    // End of variables declaration//GEN-END:variables
}
