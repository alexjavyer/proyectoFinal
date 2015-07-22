/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.Image;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicListUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author javy
 */
public class bodegueros extends javax.swing.JInternalFrame {
    DefaultTableModel model;
    
    // mandar fotos
    
    JFileChooser seleccionado = new JFileChooser(); 
    File archivo;
    byte[] bytesImg;
    GestionA gestion = new GestionA();   
    String username = System.getenv("USERNAME");
    int longitud = 0;
    FileInputStream foto = null;
    /**
     * Creates new form clientes
     */
    public bodegueros() {
        initComponents();
        botonesIniciales();
        bloquear();
        cargarTabla("");
        cargarFoto();
        
        //cargar tabla
        
        jTable2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
             if(jTable2.getSelectedRow()!=-1){
                 int fila = jTable2.getSelectedRow();
                    desbloquear();
                    txtBodCedula.setText(jTable2.getValueAt(fila, 0).toString());
                    txtBodNombre.setText(jTable2.getValueAt(fila, 1).toString());
                    txtBodApellido.setText(jTable2.getValueAt(fila, 2).toString());
                    txtBodSueldo.setText(jTable2.getValueAt(fila, 3).toString());
                    txtBodFoto.setText(jTable2.getValueAt(fila, 4).toString());
                   txtBodCedula.setEnabled(false);
             //mirar imagen
                    File abrir = new File(jTable2.getValueAt(fila, 4).toString());
                    bytesImg = gestion.dirimg(abrir);
                    ImageIcon imagen = new ImageIcon(bytesImg);
                    lblIma.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(lblIma.getWidth(), lblIma.getHeight(), Image.SCALE_DEFAULT)));
 
                    btnActualizar.setEnabled(true);
                    btnBorrar.setEnabled(true);
                    btnCancelar.setEnabled(true);
             }
            }
        }
        );
    }
   //tablas
    void cargarTabla(String dato){
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String titulo[] = {"CEDULA","NOMBRE","APELLIDO","SUELDO","FOTO"};
        model = new DefaultTableModel(null,titulo);
        String registros [] = new String[5];
        String sql;
        sql = "select * from bodegueros where ci_bod like '%"+dato+"%'";
        try{
        Statement psd = cn.createStatement();
        ResultSet rs = psd.executeQuery(sql);
        while(rs.next()){
            registros[0]=rs.getString("ci_bod");
            registros[1]=rs.getString("nom_bod");
            registros[2]=rs.getString("ape_bod");
            registros[3]=rs.getString("sue_bod");
            registros[4]=rs.getString("foto_bod");
            model.addRow(registros);
        }
        jTable2.setModel(model);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
   // buscar
    
    void buscarClavePrimaria(String dato){
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql =""; 
        sql="select count(*) as contar from bodegueros where CI_BOD = '"+dato+"'";
        try{
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);                           
                    while(rs.next()){
                        int contar = rs.getInt("contar");
                        if(contar>0){
                            JOptionPane.showMessageDialog(null, "Dato ya existe");
                              txtBodCedula.setText("");
                              txtBodCedula.requestFocus();
                        }
                    }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
   //controles
    void botonesIniciales(){
        btnNuevo.setEnabled(true);
        btnBorrar.setEnabled(false);
        btnActualizar.setEnabled(false);
        btnCancelar.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnSalir.setEnabled(true);
    }
    
    void limpiar(){
        txtBodApellido.setText("");
        txtBodCedula.setText("");
        txtBodFoto.setText("");
        txtBodNombre.setText("");
        txtBodSueldo.setText("");
    }
    
    void desbloquear(){
        txtBodApellido.setEnabled(true);
        txtBodCedula.setEnabled(true);
        txtBodFoto.setEnabled(true);
        txtBodNombre.setEnabled(true);
        txtBodSueldo.setEnabled(true);
        btnSubirFoto.setEnabled(true);
    }
    
    void bloquear(){
        txtBodApellido.setEnabled(false);
        txtBodCedula.setEnabled(false);
        txtBodFoto.setEnabled(false);
        txtBodNombre.setEnabled(false);
        txtBodSueldo.setEnabled(false);
        btnSubirFoto.setEnabled(false);
    }
    //botones
    void botonNuevo(){
        desbloquear();
        btnGuardar.setEnabled(true);
        btnCancelar.setEnabled(true);
        limpiar();
        cargarFoto();
    }
    
    void botonGuardar(){
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql="";
            String cedula,nombre,apellido,sueldo,foto;
            cedula = txtBodCedula.getText();
            nombre = txtBodNombre.getText();
            apellido = txtBodApellido.getText();
            sueldo = txtBodSueldo.getText();
            foto = "C:\\FOTOSFARMACIA\\"+txtBodCedula.getText()+".jpg";
            // pasa la iamgen a c;\\Foto\\
            File archivoGuardar = new File (foto);       
            gestion.GuardarImagen(archivoGuardar,bytesImg); 
            
            
            sql="insert into bodegueros (ci_bod,nom_bod,ape_bod,sue_bod,foto_bod) values (?,?,?,?,?)";
            
            PreparedStatement psd = cn.prepareStatement(sql);
            psd.setString(1, cedula);
            psd.setString(2, nombre);
            psd.setString(3, apellido);
            psd.setString(4, sueldo);
            psd.setString(5, foto);
            int n=psd.executeUpdate();
            if(n>0){
                JOptionPane.showMessageDialog(null, "Se inserto correctamente");
                limpiar();
                botonesIniciales();
                bloquear();
                cargarTabla("");
                cargarFoto();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    void botonCancelar(){
        botonesIniciales();
        limpiar();
        bloquear();
        cargarFoto();
    }
    
    void botonActualizar(){
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql="";
        File archivoGuardar = new File ("C:/FOTOSFARMACIA/"+txtBodCedula.getText()+".jpg");       
        gestion.GuardarImagen(archivoGuardar,bytesImg);
        sql="UPDATE bodegueros SET NOM_BOD='"+txtBodNombre.getText()+"',APE_BOD='"+txtBodApellido.getText()+"',sue_bod = '"+txtBodSueldo.getText()+"',foto_bod='"+"C:\\FOTOSFARMACIA\\"+txtBodCedula.getText()+".jpg"+"' WHERE CI_BOD='"+txtBodCedula.getText()+"'";
        try{
            PreparedStatement psd = cn.prepareStatement(sql);
            if(psd.executeUpdate()>0){
                JOptionPane.showMessageDialog(null,"Se actualizó correctamente");
                cargarTabla("");
                botonesIniciales();
                limpiar();
                bloquear();
                cargarFoto();
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
        sql="DELETE bodeguero WHERE CI_BOD = "+txtBodCedula.getText();
        try{
            PreparedStatement psd = cn.prepareStatement(sql);
            if(psd.executeUpdate()>0){
                JOptionPane.showMessageDialog(null,"Se eliminó correctamente");
                cargarTabla("");
                botonesIniciales();
                limpiar();
                bloquear();
                cargarFoto();
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
        
    }
    }
    
    
    // FOTOS
    void seleccionarFoto(){
        String a="C:\\Users\\"+username+"\\Desktop\\Desktop\\autos\\";
        File archivo1 = new File(a);
        seleccionado.setSelectedFile(archivo1);
        if(seleccionado.showDialog(this,"ABRIR ARCHIVO")==JFileChooser.APPROVE_OPTION){
          archivo = seleccionado.getSelectedFile();
          if(archivo.canRead()){
              if(archivo.getName().endsWith("png")||archivo.getName().endsWith("jpg")||archivo.getName().endsWith("gif")||archivo.getName().endsWith("PNG")||archivo.getName().endsWith("GIF")||archivo.getName().endsWith("JPG")){
                 bytesImg = gestion.dirimg(archivo);
                 ImageIcon imagen = new ImageIcon(bytesImg);
                 lblIma.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(lblIma.getWidth(), lblIma.getHeight(), Image.SCALE_DEFAULT)));
                 //txtAutoImagen.setText("C:\\FOTOS\\"+txtAutoPlaca.getText()+".jpg");
                 txtBodFoto.setText(archivo1.toString());
              }
                  
          }
        }
       }
      
      void cargarFoto(){
          File abrir = new File("C:\\FOTOSFARMACIA\\caras.jpg");
                        bytesImg = gestion.dirimg(abrir);
                        ImageIcon imagen = new ImageIcon(bytesImg);
                        lblIma.setIcon(imagen);
                        lblIma.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(lblIma.getWidth(), lblIma.getHeight(), Image.SCALE_DEFAULT)));

      }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtBodBusqueda = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtBodSueldo = new javax.swing.JTextField();
        txtBodApellido = new javax.swing.JTextField();
        txtBodNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtBodCedula = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnSubirFoto = new javax.swing.JButton();
        txtBodFoto = new javax.swing.JTextField();
        lblIma = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel4.setText("TABLA BODEGUEROS");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBorrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
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

        jLabel6.setText("BÚSQUEDA POR CÉDULA");

        txtBodBusqueda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBodBusquedaFocusLost(evt);
            }
        });
        txtBodBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBodBusquedaActionPerformed(evt);
            }
        });
        txtBodBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBodBusquedaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(txtBodBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 14, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtBodBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jPanel3FocusLost(evt);
            }
        });

        jLabel5.setText("SUELDO");

        txtBodApellido.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBodApellidoFocusLost(evt);
            }
        });

        txtBodNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBodNombreFocusLost(evt);
            }
        });

        jLabel2.setText("NOMBRE");

        jLabel1.setText("CÉDULA");

        txtBodCedula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBodCedulaActionPerformed(evt);
            }
        });

        jLabel3.setText("APELLIDO");

        btnSubirFoto.setText("FOTO");
        btnSubirFoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirFotoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 12, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(23, 23, 23))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnSubirFoto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtBodNombre)
                    .addComponent(txtBodApellido)
                    .addComponent(txtBodCedula)
                    .addComponent(txtBodSueldo)
                    .addComponent(txtBodFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBodCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBodNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBodApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBodSueldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubirFoto)
                    .addComponent(txtBodFoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        lblIma.setText("jLabel7");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(300, 300, 300)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 85, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblIma, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(66, 66, 66))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblIma, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtBodCedulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBodCedulaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBodCedulaActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
        botonEliminar();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

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

    private void txtBodBusquedaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBodBusquedaFocusLost
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtBodBusquedaFocusLost

    private void jPanel3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel3FocusLost
        // TODO add your handling code here:
        txtBodNombre.setText(txtBodNombre.getText().toUpperCase());
    }//GEN-LAST:event_jPanel3FocusLost

    private void txtBodApellidoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBodApellidoFocusLost
        // TODO add your handling code here:
        txtBodApellido.setText(txtBodApellido.getText().toUpperCase());
    }//GEN-LAST:event_txtBodApellidoFocusLost

    private void btnSubirFotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirFotoActionPerformed
        // TODO add your handling code here:
        seleccionarFoto();
    }//GEN-LAST:event_btnSubirFotoActionPerformed

    private void txtBodNombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBodNombreFocusLost
        // TODO add your handling code here:
        txtBodNombre.setText(txtBodNombre.getText().toUpperCase());
    }//GEN-LAST:event_txtBodNombreFocusLost

    private void txtBodBusquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBodBusquedaKeyReleased
        // TODO add your handling code here:
         cargarTabla(txtBodBusqueda.getText());
    }//GEN-LAST:event_txtBodBusquedaKeyReleased

    private void txtBodBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBodBusquedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBodBusquedaActionPerformed

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
            java.util.logging.Logger.getLogger(bodegueros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(bodegueros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(bodegueros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(bodegueros.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new bodegueros().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSubirFoto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblIma;
    private javax.swing.JTextField txtBodApellido;
    private javax.swing.JTextField txtBodBusqueda;
    private javax.swing.JTextField txtBodCedula;
    private javax.swing.JTextField txtBodFoto;
    private javax.swing.JTextField txtBodNombre;
    private javax.swing.JTextField txtBodSueldo;
    // End of variables declaration//GEN-END:variables
}
