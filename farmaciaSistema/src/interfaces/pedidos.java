/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.Image;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author javy
 */
public class pedidos extends javax.swing.JInternalFrame {
    DefaultTableModel model;

    /**
     * Creates new form clientes
     */
    public pedidos() {
        initComponents();
         getContentPane().setBackground(new java.awt.Color(10,120,200));
        setTitle("P E D I D O S");
        
         if(ingresoAlSistema.usuarios=="BODEGUERO"){
         jbtActualizar.setEnabled(false);
         jbtBorrar.setEnabled(false);
         jbtCancelar.setEnabled(false);
         jbtGuardar.setEnabled(false);
         jbtNuevo.setEnabled(true);
         jbtSalir.setEnabled(true);
     }else{
        botonesIniciales();
         }
         
        jtbPedidos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
             if(jtbPedidos.getSelectedRow()!=-1){
                 int fila = jtbPedidos.getSelectedRow();
                    desbloquear();
                    txtNumero.setText(jtbPedidos.getValueAt(fila, 0).toString());
                    date.setDateFormatString(jtbPedidos.getValueAt(fila, 1).toString());
                    txtTotal.setText(jtbPedidos.getValueAt(fila, 2).toString());
                    txtBodeguero.setText(jtbPedidos.getValueAt(fila, 3).toString());
                    txtVisitador.setText(jtbPedidos.getValueAt(fila, 4).toString());
                            if(ingresoAlSistema.usuarios=="BODEGUERO"){
                    jbtActualizar.setEnabled(true);
                    jbtBorrar.setEnabled(false);
                    jbtCancelar.setEnabled(true);
                            }else{
                    jbtActualizar.setEnabled(true);
                    jbtBorrar.setEnabled(true);
                    jbtCancelar.setEnabled(true);
             }
            }
            }
        }
        );
        cargarPedidos("");
        CargarBodeguero();
        CargarVisitador();
        //botonesIniciales();
        bloquear();
        limpiar();
    }
    public void botonesIniciales(){
        jbtNuevo.setEnabled(true);
        jbtBorrar.setEnabled(false);
        jbtActualizar.setEnabled(false);
        jbtCancelar.setEnabled(false);
        jbtGuardar.setEnabled(false);
        jbtSalir.setEnabled(true);
        jcbBodegueros.setEnabled(false);
        jcmVisitador.setEnabled(false);
    }
    
    public void limpiar(){
        txtNumero.setText("");
        txtTotal.setText("");
        date.setDate(null);
        txtBodeguero.setText("");
        txtVisitador.setText("");
    }
    
    public void desbloquear(){
        txtNumero.setEnabled(true);
        date.setEnabled(true);
        txtBodeguero.setEnabled(true);
        txtVisitador.setEnabled(true);
        txtBusqueda.setEnabled(true);
        jcbBodegueros.setEnabled(true);
        jcmVisitador.setEnabled(true);
    }
    
    public void bloquear(){
        txtNumero.setEnabled(false);
        date.setEnabled(false);
        txtBodeguero.setEnabled(false);
        txtVisitador.setEnabled(false);
        jcbBodegueros.setEnabled(false);
        jcmVisitador.setEnabled(false);
    }
    
    public void Nuevo(){
        desbloquear();
        jbtGuardar.setEnabled(true);
        jbtCancelar.setEnabled(true);
        limpiar();
    }
    
    public void CargarBodeguero() {
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "SELECT * FROM BODEGUEROS ORDER BY CI_BOD";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                jcbBodegueros.addItem(rs.getString("CI_BOD"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    public void CargarVisitador() {
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "SELECT * FROM VISITADORES_MEDICOS ORDER BY CI_VIS";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                jcmVisitador.addItem(rs.getString("CI_VIS"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void cargarPedidos(String dato){
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String titulo[] = {"NÚMERO","FECHA","TOTAL","CÉDULA BODEGUERO","CÉDULA VISITADOR"};
        model = new DefaultTableModel(null,titulo);
        String registros [] = new String[5];
        String sql;
        sql = "select * from pedidos where NUM_PED like '%"+dato+"%'";
        try{
        Statement psd = cn.createStatement();
        ResultSet rs = psd.executeQuery(sql);
        while(rs.next()){
            registros[0]=rs.getString("NUM_PED");
            registros[1]=rs.getString("FEC_HOR_PED");
            registros[2]=rs.getString("TOTAL_PED");
            registros[3]=rs.getString("CI_BOD_PER");
            registros[4]=rs.getString("CI_VIS_PER");
            model.addRow(registros);
        }
        jtbPedidos.setModel(model);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void Guardar(){
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql="";
            String fecha=new SimpleDateFormat("dd/MM/yyyy").format(date.getDate());
            sql="insert into pedidos (NUM_PED,FEC_HOR_PED,TOTAL_PED,CI_BOD_PER,CI_VIS_PER) values (?,TO_DATE('"+fecha+"','DD/MM/YYYY'),?,?,?)";
            PreparedStatement psd = cn.prepareStatement(sql);
            psd.setString(1, txtNumero.getText());
            psd.setInt(2, 0);
            psd.setString(3, txtBodeguero.getText());
            psd.setString(4, txtVisitador.getText());
            int n=psd.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(null, "Se inserto correctamente");
                limpiar();
                botonesIniciales();
                bloquear();
                cargarPedidos("");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
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
        sql="UPDATE PEDIDOS SET FEC_HOR_PED='"+date.getDate()+"',TOTAL_PED = '"+txtTotal.getText()+"',CI_BOD_PER='"+txtBodeguero.getText()+"',CI_VIS_PER='"+txtVisitador.getText()+"' WHERE NUM_PED='"+txtNumero.getText()+"'";
        try{
            PreparedStatement psd = cn.prepareStatement(sql);
            if(psd.executeUpdate()>0){
                JOptionPane.showMessageDialog(null,"Se actualizó correctamente");
                cargarPedidos("");
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
        sql="DELETE FROM pedidos WHERE NUM_PED = '"+txtNumero.getText()+"'";
        try{
            PreparedStatement psd = cn.prepareStatement(sql);
            if(psd.executeUpdate()>0){
                JOptionPane.showMessageDialog(null,"Se eliminó correctamente");
                cargarPedidos("");
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

        jpnBotones = new javax.swing.JPanel();
        jbtNuevo = new javax.swing.JButton();
        jbtGuardar = new javax.swing.JButton();
        jbtCancelar = new javax.swing.JButton();
        jbtActualizar = new javax.swing.JButton();
        jbtBorrar = new javax.swing.JButton();
        jbtSalir = new javax.swing.JButton();
        jbtDetalles = new javax.swing.JButton();
        jpnBusqueda = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtBusqueda = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbPedidos = new javax.swing.JTable();
        jpnDatos = new javax.swing.JPanel();
        txtNumero = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        txtBodeguero = new javax.swing.JTextField();
        txtVisitador = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        date = new com.toedter.calendar.JDateChooser();
        jcbBodegueros = new javax.swing.JComboBox();
        jcmVisitador = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("PEDIDOS");
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                formPropertyChange(evt);
            }
        });

        jpnBotones.setBackground(new java.awt.Color(10, 120, 200));
        jpnBotones.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jbtNuevo.setText("NUEVO");
        jbtNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtNuevoActionPerformed(evt);
            }
        });

        jbtGuardar.setText("GUARDAR");
        jbtGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtGuardarActionPerformed(evt);
            }
        });

        jbtCancelar.setText("CANCELAR");
        jbtCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtCancelarActionPerformed(evt);
            }
        });

        jbtActualizar.setText("ACTUALIZAR");
        jbtActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtActualizarActionPerformed(evt);
            }
        });

        jbtBorrar.setText("BORRAR");
        jbtBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtBorrarActionPerformed(evt);
            }
        });

        jbtSalir.setText("SALIR");
        jbtSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtSalirActionPerformed(evt);
            }
        });

        jbtDetalles.setText("DETALLES");
        jbtDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtDetallesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnBotonesLayout = new javax.swing.GroupLayout(jpnBotones);
        jpnBotones.setLayout(jpnBotonesLayout);
        jpnBotonesLayout.setHorizontalGroup(
            jpnBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnBotonesLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jpnBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbtActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtDetalles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnBotonesLayout.setVerticalGroup(
            jpnBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnBotonesLayout.createSequentialGroup()
                .addContainerGap()
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
                .addComponent(jbtDetalles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtSalir)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jpnBusqueda.setBackground(new java.awt.Color(10, 120, 200));
        jpnBusqueda.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel6.setText("Busqueda");

        javax.swing.GroupLayout jpnBusquedaLayout = new javax.swing.GroupLayout(jpnBusqueda);
        jpnBusqueda.setLayout(jpnBusquedaLayout);
        jpnBusquedaLayout.setHorizontalGroup(
            jpnBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnBusquedaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(48, 48, 48)
                .addComponent(txtBusqueda)
                .addContainerGap())
        );
        jpnBusquedaLayout.setVerticalGroup(
            jpnBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnBusquedaLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpnBusquedaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jtbPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jtbPedidos);

        jpnDatos.setBackground(new java.awt.Color(10, 120, 200));
        jpnDatos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtTotal.setEnabled(false);

        jLabel1.setText("Número");

        jLabel2.setText("Fecha");

        jLabel3.setText("Total");

        jLabel4.setText("Cédula del Bodeguero");

        jLabel5.setText("Cédula del Visitador");

        jcbBodegueros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbBodeguerosActionPerformed(evt);
            }
        });

        jcmVisitador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmVisitadorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnDatosLayout = new javax.swing.GroupLayout(jpnDatos);
        jpnDatos.setLayout(jpnDatosLayout);
        jpnDatosLayout.setHorizontalGroup(
            jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(32, 32, 32)
                .addGroup(jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnDatosLayout.createSequentialGroup()
                        .addComponent(txtBodeguero, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcbBodegueros, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpnDatosLayout.createSequentialGroup()
                        .addComponent(txtVisitador, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcmVisitador, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpnDatosLayout.setVerticalGroup(
            jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnDatosLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpnDatosLayout.createSequentialGroup()
                        .addGroup(jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpnDatosLayout.createSequentialGroup()
                                .addGroup(jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBodeguero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jcbBodegueros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jpnDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtVisitador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcmVisitador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("P E D I D O S");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpnDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jpnBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
                        .addComponent(jpnBusqueda, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(40, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(266, 266, 266))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jpnDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpnBotones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpnBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                .addContainerGap())
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

    private void jbtDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtDetallesActionPerformed
        // TODO add your handling code here:
        detalles_pedidos pe=new detalles_pedidos();
        menu1.jDesktopPane1.add(pe);
        pe.setVisible(true);
        pe.show();
    }//GEN-LAST:event_jbtDetallesActionPerformed

    private void formPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_formPropertyChange
        // TODO add your handling code here:
        
    }//GEN-LAST:event_formPropertyChange

    private void jcbBodeguerosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbBodeguerosActionPerformed
        // TODO add your handling code here:
        txtBodeguero.setText(jcbBodegueros.getSelectedItem().toString());
    }//GEN-LAST:event_jcbBodeguerosActionPerformed

    private void jcmVisitadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmVisitadorActionPerformed
        // TODO add your handling code here:
        txtVisitador.setText(jcmVisitador.getSelectedItem().toString());
    }//GEN-LAST:event_jcmVisitadorActionPerformed

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
            java.util.logging.Logger.getLogger(pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pedidos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser date;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JButton jbtActualizar;
    public javax.swing.JButton jbtBorrar;
    public javax.swing.JButton jbtCancelar;
    public javax.swing.JButton jbtDetalles;
    public javax.swing.JButton jbtGuardar;
    public javax.swing.JButton jbtNuevo;
    public javax.swing.JButton jbtSalir;
    private javax.swing.JComboBox jcbBodegueros;
    private javax.swing.JComboBox jcmVisitador;
    private javax.swing.JPanel jpnBotones;
    private javax.swing.JPanel jpnBusqueda;
    private javax.swing.JPanel jpnDatos;
    private javax.swing.JTable jtbPedidos;
    private javax.swing.JTextField txtBodeguero;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtVisitador;
    // End of variables declaration//GEN-END:variables
}
