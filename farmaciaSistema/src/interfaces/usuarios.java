/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.Image;
import java.io.File;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author javy
 */
public class usuarios extends javax.swing.JInternalFrame {

   DefaultTableModel model;
    
    public usuarios() {
        initComponents();
        getContentPane().setBackground(new java.awt.Color(10,120,200));
        setTitle("U S U A R I O S");
        botonesIniciales();
        bloquear();
        cargarTabla("");
        
        
        tblUsuarios.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
             if(tblUsuarios.getSelectedRow()!=-1){
                 int fila = tblUsuarios.getSelectedRow();
                    desbloquear();
                    txtUsuCedula.setText(tblUsuarios.getValueAt(fila, 0).toString());
                    txtUsuNombre.setText(tblUsuarios.getValueAt(fila, 1).toString());
                    txtUsuApellido.setText(tblUsuarios.getValueAt(fila, 2).toString());
                    txtUsuClave.setText(tblUsuarios.getValueAt(fila, 3).toString());
//                    jcbTipo.setText(tblUsuarios.getValueAt(fila, 4).toString());
                   txtUsuCedula.setEnabled(false);
             
                    btnActualizar.setEnabled(true);
                    btnBorrar.setEnabled(true);
                    btnCancelar.setEnabled(true);
             }
            }
        });
    }
    
    void botonesIniciales(){
        btnNuevo.setEnabled(true);
        btnBorrar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnSalir.setEnabled(true);
    }
    
    void limpiar(){
        txtUsuApellido.setText("");
        txtUsuCedula.setText("");
        txtUsuNombre.setText("");
        txtUsuClave.setText("");
    }
    
    void desbloquear(){
        txtUsuApellido.setEnabled(true);
        txtUsuCedula.setEnabled(true);
        txtUsuNombre.setEnabled(true);
        txtUsuClave.setEnabled(true);
    }
    
    void bloquear(){
        txtUsuApellido.setEnabled(false);
        txtUsuCedula.setEnabled(false);
        txtUsuNombre.setEnabled(false);
        txtUsuClave.setEnabled(false);
    }
    
    void botonNuevo(){
        desbloquear();
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
        limpiar();
    }
    
    void botonCancelar(){
        botonesIniciales();
        limpiar();
        bloquear();
    }
    
    void botonGuardar(){
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql="";
            String cedula,nombre,apellido,clave,tipo,encriptado;
            cedula = txtUsuCedula.getText();
            nombre = txtUsuNombre.getText();
            apellido = txtUsuApellido.getText();
            clave = txtUsuClave.getText();
            tipo= jcbTipo.getSelectedItem().toString();
            encriptado=Encriptar(clave);
            
            sql="insert into usuarios (ci_usu,nom_usu,ape_usu,cla_usu,foto_bod) values (?,?,?,?,?)";
            
            PreparedStatement psd = cn.prepareStatement(sql);
            psd.setString(1, cedula);
            psd.setString(2, nombre);
            psd.setString(3, apellido);
            psd.setString(4, encriptado);
            psd.setString(5, tipo);
            int n=psd.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(null, "SE INSERTO CORRECTAMENTE");
                limpiar();
                botonesIniciales();
                bloquear();
                cargarTabla("");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    void botonActualizar(){
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql="";
       
        sql="UPDATE usuarios SET "
                + "nom_usu='"+txtUsuNombre.getText()+
                "',ape_usu='"+txtUsuApellido.getText()+
                "',cla_usu = '"+Encriptar(txtUsuClave.getText())+
                "',tip_usu='"+jcbTipo.getSelectedItem().toString()+
                        "' WHERE ci_usu='"+txtUsuCedula.getText()+"'";
        try{
            PreparedStatement psd = cn.prepareStatement(sql);
            if(psd.executeUpdate()>0){
                JOptionPane.showMessageDialog(null,"SE ACTUALIZO CORRECTAMENTE");
                cargarTabla("");
                botonesIniciales();
                limpiar();
                bloquear();
               
            }
        }catch(Exception ex ){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    void botonEliminar(){
        if(JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR", "ELIMINAR", JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE)==JOptionPane.YES_OPTION){
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql="";
        sql="DELETE USUARIOS WHERE CI_USU = "+txtUsuCedula.getText();
        try{
            PreparedStatement psd = cn.prepareStatement(sql);
            if(psd.executeUpdate()>0){
                JOptionPane.showMessageDialog(null,"Se eliminó correctamente");
                cargarTabla("");
                botonesIniciales();
                limpiar();
                bloquear();
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        
    }
    }
    
    void cargarTabla(String dato){
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String titulo[] = {"CEDULA","NOMBRE","APELLIDO","CLAVE","TIPO"};
        model = new DefaultTableModel(null,titulo);
        String registros [] = new String[5];
        String sql;
        sql = "select * from USUARIOS where ci_usu like '%"+dato+"%'";
        try{
        Statement psd = cn.createStatement();
        ResultSet rs = psd.executeQuery(sql);
        while(rs.next()){
            registros[0]=rs.getString("ci_usu");
            registros[1]=rs.getString("nom_usu");
            registros[2]=rs.getString("ape_usu");
            registros[3]=Desencriptar(rs.getString("cla_usu"));
            registros[4]=rs.getString("tip_usu");
            model.addRow(registros);
        }
        tblUsuarios.setModel(model);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public String Encriptar(String texto){
        String secretKey = "qualityinfosolutions"; //llave para encriptar datos
        String base64EncryptedString = "";
 
        try {
 
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
 
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);
 
            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
}
    
 public String Desencriptar(String textoEncriptado){
 
 String secretKey = "qualityinfosolutions"; //llave para desenciptar datos
        String base64EncryptedString = "";
 
        try {
            byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
 
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);
 
            byte[] plainText = decipher.doFinal(message);
 
            base64EncryptedString = new String(plainText, "UTF-8");
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtUsuApellido = new javax.swing.JTextField();
        txtUsuNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtUsuCedula = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jcbTipo = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txtUsuClave = new javax.swing.JPasswordField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtBodBusqueda = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(10, 120, 200));

        btnNuevo.setText("NUEVO ");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnBorrar.setText(" BORRAR");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });

        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnActualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBorrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(10, 120, 200));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPanel3FocusLost(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("CLAVE:");

        txtUsuApellido.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsuApellidoFocusLost(evt);
            }
        });

        txtUsuNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsuNombreFocusLost(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("NOMBRE:");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CÉDULA:");

        txtUsuCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuCedulaActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("APELLIDO:");

        jcbTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ADMINISTRADOR", "FARMACEUTICO", "BODEGUERO" }));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("TIPO DE USUARIO:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jLabel4))
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jcbTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUsuNombre)
                    .addComponent(txtUsuApellido)
                    .addComponent(txtUsuCedula)
                    .addComponent(txtUsuClave))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsuCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsuNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsuApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtUsuClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblUsuarios);

        jPanel4.setBackground(new java.awt.Color(10, 120, 200));

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("BÚSQUEDA POR CÉDULA");

        txtBodBusqueda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBodBusquedaFocusLost(evt);
            }
        });
        txtBodBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBodBusquedaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(txtBodBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtBodBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("U S U A R I O S ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(81, 81, 81)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(197, 197, 197)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8)
                        .addGap(11, 11, 11)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        botonNuevo();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        botonGuardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        botonCancelar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        botonActualizar();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
        botonEliminar();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtUsuApellidoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsuApellidoFocusLost
        // TODO add your handling code here:
        txtUsuApellido.setText(txtUsuApellido.getText().toUpperCase());
    }//GEN-LAST:event_txtUsuApellidoFocusLost

    private void txtUsuNombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsuNombreFocusLost
        // TODO add your handling code here:
        txtUsuNombre.setText(txtUsuNombre.getText().toUpperCase());
    }//GEN-LAST:event_txtUsuNombreFocusLost

    private void txtUsuCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsuCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuCedulaActionPerformed

    private void jPanel3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel3FocusLost
        // TODO add your handling code here:
        txtUsuNombre.setText(txtUsuNombre.getText().toUpperCase());
    }//GEN-LAST:event_jPanel3FocusLost

    private void txtBodBusquedaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBodBusquedaFocusLost
        // TODO add your handling code here:

    }//GEN-LAST:event_txtBodBusquedaFocusLost

    private void txtBodBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBodBusquedaKeyReleased
        // TODO add your handling code here:
        cargarTabla(txtBodBusqueda.getText());
    }//GEN-LAST:event_txtBodBusquedaKeyReleased

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
            java.util.logging.Logger.getLogger(usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(usuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new usuarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnActualizar;
    public static javax.swing.JButton btnBorrar;
    public static javax.swing.JButton btnCancelar;
    public static javax.swing.JButton btnGuardar;
    public static javax.swing.JButton btnNuevo;
    public static javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox jcbTipo;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtBodBusqueda;
    private javax.swing.JTextField txtUsuApellido;
    private javax.swing.JTextField txtUsuCedula;
    private javax.swing.JPasswordField txtUsuClave;
    private javax.swing.JTextField txtUsuNombre;
    // End of variables declaration//GEN-END:variables
}
