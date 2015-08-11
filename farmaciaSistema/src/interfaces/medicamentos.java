/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class medicamentos extends javax.swing.JInternalFrame {

    /**
     * Creates new form clientes
     */
        int fila=0;
    static DefaultTableModel model;
    public medicamentos() {
     initComponents();
     
     if(ingresoAlSistema.usuarios=="BODEGUERO"){
         btnActualizar.setEnabled(false);
         btnBorrar.setEnabled(false);
         btnCancelar.setEnabled(false);
         btnIngreso.setEnabled(false);
         btnNuevo.setEnabled(false);
         btnSalir.setEnabled(true);
     }else{
         if(ingresoAlSistema.usuarios=="FARMACEUTICO"){
         btnActualizar.setEnabled(false);
         btnBorrar.setEnabled(false);
         btnCancelar.setEnabled(false);
         btnIngreso.setEnabled(false);
         btnNuevo.setEnabled(false);
         btnSalir.setEnabled(true);
     }else{
          btnActualizar.setEnabled(true);
         btnBorrar.setEnabled(true);
         btnCancelar.setEnabled(true);
         btnIngreso.setEnabled(true);
         btnNuevo.setEnabled(true);
         btnSalir.setEnabled(true);   
         }
     }
     
     cargarTabla("");
      //  botonesIniciales();
        getContentPane().setBackground(new java.awt.Color(12,120,200));      
        setBounds(0, 0, 500, 455);
        setTitle("M E D I C A M E N T O S");  
        tblFarmaceuticos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            //override sobre cargar metodos para realizar varias cosas
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if(tblFarmaceuticos.getSelectedRow()!=-1){
                    fila = tblFarmaceuticos.getSelectedRow();
                    txtMedCodigo.setText(tblFarmaceuticos.getValueAt(fila, 0).toString());
                    txtMedrNombre.setText(tblFarmaceuticos.getValueAt(fila, 1).toString());
                    //txtMedPrecio2.setText(tblFarmaceuticos.getValueAt(fila, 2).toString());
                    txtMedPrecio.setText(tblFarmaceuticos.getValueAt(fila, 2).toString());
                    txtMedStock.setText(tblFarmaceuticos.getValueAt(fila, 3).toString());
                    txtMedFecha1.setDate(StringADate(tblFarmaceuticos.getValueAt(fila, 4).toString()));
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
        txtMedCodigo.setEnabled(true);
       limpiar();
    }
    
    void botonesIniciales(){
       // jButton2.setEnabled(true);
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
        txtMedCodigo.setEnabled(false);
    }
    void limpiar(){
        txtMedCodigo.setText("");
        txtMedrNombre.setText("");
        txtMedPrecio.setText("");
        txtMedStock.setText("");
        txtMedFecha1.setDate(StringADate(""));
//        txtMedPrecio2.setText("");
    }
    
     public java.util.Date StringADate(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaE = null;
        try {
            fechaE = formato.parse(fecha);
            return fechaE;
        } catch (Exception ex) {
            return null;
        }
    }
    
         void buscarClavePrimaria(){
                conexion cc = new conexion();
                Connection cn = cc.conectar();    
             
                String sql = "";
                sql="Select count(*) as contar from medicamentos where COD_MED='"+txtMedCodigo.getText()+"'";
                try {
                    Statement psd  = cn.createStatement();
                    ResultSet rs = psd.executeQuery(sql);                           
                    while(rs.next()){
                        int contar = rs.getInt("contar");
                        if(contar>0){
                            JOptionPane.showMessageDialog(null, "dato ya existe");
                              txtMedCodigo.setText("");
                              txtMedCodigo.requestFocus();
                        }
                        
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);  
                }
             }  
            
    void botonIngreso(){
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String ci_bod,nom_bod,ape_bod,sue_bod,fec_bod,ape_bod1;
            ci_bod  = txtMedCodigo.getText();
            nom_bod = txtMedrNombre.getText();
            ape_bod  = txtMedPrecio.getText();
//            ape_bod1  = txtMedPrecio2.getText();
            sue_bod  = txtMedStock.getText();
            fec_bod  = new SimpleDateFormat("yyyy-MM-dd").format(txtMedFecha1.getDate());
           
            String sql="";
            sql="INSERT INTO medicamentos (COD_MED,NOM_MED,PRE_MED,STOCK,FEC_CAD_MED,PRE_PRO) VALUES(?,?,?,?,?,?)";
           PreparedStatement psd = cn.prepareStatement(sql);
         
           psd.setString(1, ci_bod);
           psd.setString(2, nom_bod);
           psd.setString(3, ape_bod);
           psd.setString(4, sue_bod);
           psd.setDate(5, java.sql.Date.valueOf(fec_bod));
          // psd.setString(6, ape_bod1);
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
        sql ="DELETE FROM medicamentos where COD_MED = '"+txtMedCodigo.getText()+"'"; 
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
        }catch(Exception ex){ JOptionPane.showMessageDialog(null, ex); }
    }
    }
    void botonActualizar(){
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql="";
        String f1=new SimpleDateFormat("dd-MM-yyyy").format(txtMedFecha1.getDate());
        sql ="update medicamentos "
                + "SET NOM_MED ='"+txtMedrNombre.getText()+"',"
                + "PRE_MED='"+txtMedPrecio.getText()+"',"
                + "STOCK='"+txtMedStock.getText()+"',"
                + "FEC_CAD_MED = TO_DATE('"+f1+"','DD/MM/YYYY'),"
                + "WHERE COD_MED = '"+txtMedCodigo.getText()+"'";
        
        try{
            PreparedStatement psd = cn.prepareStatement(sql);
            int n = psd.executeUpdate(sql);
             if(n>0){
               JOptionPane.showMessageDialog(null, "Se actualizo correctamente");
               limpiar();
               cargarTabla("");
                                        
             }
        }catch(Exception ex){
        JOptionPane.showMessageDialog(null, ex);
        }
    
    }
   
    
    public static void cargarTabla(String dato){
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String titulos []= {"CODIGO","NOMBRE","PRECIO VENTA","STOCK","FECHA CADUCIDAD"};
        model= new DefaultTableModel(null,titulos);
        String registro[] = new String [5]; 
        String sql="";
        sql = "Select * from  medicamentos where COD_MED like '%"+dato+"%'";
        try{
        Statement psd = cn.createStatement();
        ResultSet rs = psd.executeQuery(sql);
        while(rs.next()){
            registro[0]=rs.getString("COD_MED");
            registro[1]=rs.getString("NOM_MED");
            registro[2]=rs.getString("PRE_MED");
            registro[3]=rs.getString("STOCK");
            registro[4]=rs.getString("FEC_CAD_MED");
            
            model.addRow(registro);
        }
          tblFarmaceuticos.setModel(model);
        }catch(Exception ex){ JOptionPane.showMessageDialog(null, ex); }
    }

    /**s
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtMedCodigo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMedrNombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFarmaceuticos = new javax.swing.JTable();
        txtMedPrecio = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtMedStock = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMedFecha1 = new com.toedter.calendar.JDateChooser();
        jPanel1 = new javax.swing.JPanel();
        btnIngreso = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        txtMedCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMedCodigoFocusLost(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("BÚSQUEDA POR CÓDIGO");

        txtMedrNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMedrNombreFocusLost(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("MEDICAMENTOS");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CÓDIGO");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("PRECIO VENTA");

        tblFarmaceuticos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblFarmaceuticos);

        txtMedPrecio.setEditable(false);
        txtMedPrecio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMedPrecioFocusLost(evt);
            }
        });

        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("NOMBRE");

        txtMedStock.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMedStockFocusLost(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("STOCK");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("FECHA CADUCACION");

        jPanel1.setBackground(new java.awt.Color(10, 120, 200));

        btnIngreso.setText("GUARDAR");
        btnIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresoActionPerformed(evt);
            }
        });

        btnBorrar.setText("BORRAR");
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

        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnNuevo.setText("NUEVO");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnBorrar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnIngreso, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                            .addComponent(btnNuevo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnActualizar, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnSalir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(btnIngreso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBorrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnActualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalir)
                .addGap(25, 25, 25))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(txtMedFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(14, 14, 14)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtMedPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtMedStock, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(76, 76, 76)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMedCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMedrNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtMedCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMedrNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtMedPrecio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtMedStock, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtMedFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMedCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMedCodigoFocusLost
        // TODO add your handling code here:
        //buscarClavePrimaria();
         txtMedCodigo.setText(txtMedCodigo.getText().toUpperCase());
    }//GEN-LAST:event_txtMedCodigoFocusLost

    private void txtMedrNombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMedrNombreFocusLost
        // TODO add your handling code here:
        txtMedrNombre.setText(txtMedrNombre.getText().toUpperCase());
    }//GEN-LAST:event_txtMedrNombreFocusLost

    private void btnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresoActionPerformed
        // TODO add your handling code here:
        botonIngreso();
    }//GEN-LAST:event_btnIngresoActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        botonActualizar();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalirActionPerformed

    private void txtMedPrecioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMedPrecioFocusLost
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txtMedPrecioFocusLost

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
        botonEliminar();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        // TODO add your handling code here:
        cargarTabla(jTextField4.getText());
    }//GEN-LAST:event_jTextField4KeyReleased

    private void txtMedStockFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMedStockFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMedStockFocusLost

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        botonNuevo();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        //iniciar();
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(medicamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(medicamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(medicamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(medicamentos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new medicamentos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnActualizar;
    public javax.swing.JButton btnBorrar;
    public javax.swing.JButton btnCancelar;
    public javax.swing.JButton btnIngreso;
    public javax.swing.JButton btnNuevo;
    public javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField4;
    private static javax.swing.JTable tblFarmaceuticos;
    private javax.swing.JTextField txtMedCodigo;
    private com.toedter.calendar.JDateChooser txtMedFecha1;
    private javax.swing.JTextField txtMedPrecio;
    private javax.swing.JTextField txtMedStock;
    private javax.swing.JTextField txtMedrNombre;
    // End of variables declaration//GEN-END:variables
}
