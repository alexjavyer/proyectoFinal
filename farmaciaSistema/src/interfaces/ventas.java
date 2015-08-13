/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

//import static interfaces.ingresoAlSistema.conectar;
//import static interfaces.ingresoAlSistema.tabla;
//import static interfaces.pedidos.txtVenNum;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class ventas extends javax.swing.JInternalFrame {

    /**
     * Creates new form clientes
     */
    int fila = 0;
    public static DefaultTableModel model;

    public ventas() {
        initComponents();
        cargarTabla("");
        // botonesIniciales();
        
        
         if(ingresoAlSistema.usuarios=="FARMACEUTICO"){
         btnActualizar.setEnabled(false);
         btnBorrar.setEnabled(false);
         btnDetalle.setEnabled(true);
         btnIngreso.setEnabled(true);
                  btnCancelar.setEnabled(false);
     }else{
           btnActualizar.setEnabled(false);
         btnBorrar.setEnabled(false);
         btnDetalle.setEnabled(true);
         btnIngreso.setEnabled(true);
                  btnCancelar.setEnabled(false);
         
     }
        
        
        
        
        
        cargarClientes();
        cargarFarmaceutico();
        getContentPane().setBackground(new java.awt.Color(12,120,200));
        setBounds(0, 0, 615, 600);
        setTitle("V E N T A S");
        //fecha.setDate(new Date());
        fecha.setDate(StringADate(new SimpleDateFormat("dd-MM-yyyy").format(new Date())));
        txtVenNum.setText(String.valueOf(cargarVentas()));
        tblFarmaceuticos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            //override sobre cargar metodos para realizar varias cosas
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (tblFarmaceuticos.getSelectedRow() != -1) {
                    fila = tblFarmaceuticos.getSelectedRow();
                    txtVenNum.setText(tblFarmaceuticos.getValueAt(fila, 0).toString());
                    fecha.setDate(StringADate(tblFarmaceuticos.getValueAt(fila, 1).toString()));
                    txtTotVen.setText(tblFarmaceuticos.getValueAt(fila, 2).toString());
//                    cbClientes1.setSelectedItem(tblFarmaceuticos.getValueAt(fila, 3).toString());
//                    cbFarmaceutico1.setSelectedItem(tblFarmaceuticos.getValueAt(fila, 3).toString());
                    cbClientes.setText(tblFarmaceuticos.getValueAt(fila, 3).toString());
                    cbFarmaceutico.setText(tblFarmaceuticos.getValueAt(fila, 4).toString());
                    //   botonesDesbloqueados();
                    //   Bloquear();
                     if(ingresoAlSistema.usuarios=="FARMACEUTICO"){
         btnActualizar.setEnabled(false);
         btnBorrar.setEnabled(false);
         btnDetalle.setEnabled(true);
         btnIngreso.setEnabled(true);
                  btnCancelar.setEnabled(false);
     }else{
           btnActualizar.setEnabled(true);
         btnBorrar.setEnabled(true);
         btnDetalle.setEnabled(true);
         btnIngreso.setEnabled(false);
                  btnCancelar.setEnabled(true);
     }
                }
                //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });




    }

    void botonNuevo() {
        botonesIniciales();
        btnIngreso.setEnabled(true);
        txtVenNum.setEnabled(true);
        limpiar();
    }

    void botonesIniciales() {
//        jButton2.setEnabled(true);
        btnBorrar.setEnabled(false);
        btnIngreso.setEnabled(false);
        btnActualizar.setEnabled(false);
    }

    void botonesDesbloqueados() {
        btnBorrar.setEnabled(true);
        btnIngreso.setEnabled(false);
        btnActualizar.setEnabled(true);
    }

    void Bloquear() {
        txtVenNum.setEnabled(false);
    }

    void limpiar() {
        txtVenNum.setText("");
        fecha.setDate(StringADate(""));
        txtTotVen.setText("");
        cbClientes.setText("");
        cbFarmaceutico.setText("");
        //txtFarSueldo.setText("");
    }

    void cargarClientes() {
        try {
            //cbClientes1.removeAllItems();
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "Select * from clientes order by ci_cli";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                cbClientes1.addItem(rs.getString("CI_CLI"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    void cargarFarmaceutico() {
        try {
            //cbFarmaceutico1.removeAllItems();
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql = "";
            sql = "Select * from farmaceuticos order by ci_far";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                cbFarmaceutico1.addItem(rs.getString("CI_FAR"));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    int cargarVentas() {
        try {
            int valor = 0;
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String sql = "";
            //sql= "Select secu.nextval as numero from dual";
            sql="SELECT last_value+1 as numero from secu ";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                valor = Integer.valueOf(rs.getString("numero"));
            }
            return valor;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return 0;
    }

    void buscarClavePrimaria() {
        conexion cc = new conexion();
        Connection cn = cc.conectar();

        String sql = "";
        sql = "Select count(*) as contar from ventas where NUM_VEN='" + txtVenNum.getText() + "'";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                int contar = rs.getInt("contar");
                if (contar > 0) {
                    JOptionPane.showMessageDialog(null, "dato ya existe");
                    txtVenNum.setText("");
                    txtVenNum.requestFocus();
                }

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public java.util.Date StringADate(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date fechaE = null;
        try {
            fechaE = formato.parse(fecha);
            return fechaE;
        } catch (Exception ex) {
            return null;
        }
    }

    void botonIngreso() {
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
            String nom_bod, ape_bod, sue_bod,FECHA;
            FECHA = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            nom_bod = "0";
            ape_bod = cbClientes.getText();
            sue_bod = cbFarmaceutico.getText();

            String sql = "";
            
            sql = "INSERT INTO ventas (NUM_VEN,FEC_HOR_VEN,TOTAL_VEN,CI_CLI_PER,CI_FAR_PER) VALUES(?,?,?,?,?)";
            PreparedStatement psd = cn.prepareStatement(sql);
            
            psd.setString(1, String.valueOf(txtVenNum.getText()));
            psd.setDate(2, java.sql.Date.valueOf(FECHA));
            psd.setDouble(3, Double.valueOf(nom_bod));
            psd.setString(4, ape_bod);
            psd.setString(5, sue_bod);
            int n = psd.executeUpdate();
            if (n > 0) {
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

    String fechaActual(){
        try {
            conexion cc = new conexion();
            Connection cn = cc.conectar();
        
           String  sql1="";
           sql1= "select sysdate as fecha from dual";
           Statement psd1 = cn.createStatement();
           ResultSet rs = psd1.executeQuery(sql1);
           while(rs.next()){
               return rs.getString("fecha");
           }
             
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        return null;
    }
    
    void botonEliminar() {
           if(JOptionPane.showConfirmDialog(null, "Estas seguro que deseas borrar el dato","Borrar registro",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE)==JOptionPane.YES_OPTION){        
             
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        sql = "DELETE FROM ventas where NUM_VEN = '" + txtVenNum.getText() + "'";
        try {
            PreparedStatement psd = cn.prepareStatement(sql);
            int n = psd.executeUpdate(sql);
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Se borro correctamente");
                limpiar();
                //              bloquear();
                //              botonesiniciales();
                cargarTabla("");

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    }
    void botonActualizar() {
        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String sql = "";
        String f1=new SimpleDateFormat("dd-MM-yyyy").format(fecha.getDate());
                sql = "UPDATE ventas  "
                        + "SET CI_CLI_PER ='"+cbClientes.getText()+"'"
                        + ",CI_FAR_PER ='"+cbFarmaceutico.getText()+"'"
                        + ", FEC_HOR_VEN = TO_DATE('"+f1+"','DD/MM/YYYY HH24:MI:SS') "
                        + "where NUM_VEN = '"+txtVenNum.getText()+"'";
        
        try {
            PreparedStatement psd = cn.prepareStatement(sql);
            int n = psd.executeUpdate(sql);
            if (n > 0) {
                JOptionPane.showMessageDialog(null, "Se actualizo correctamente");
                limpiar();
                //              bloquear();
                //              botonesiniciales();
                cargarTabla("");

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }
    
        public static void cargarTabla(String dato) {

        conexion cc = new conexion();
        Connection cn = cc.conectar();
        String titulos[] = {"NUMERO VENTA", "FECHA", "TOTAL", "CLIENTE", "FARMACEUTICO"};
        model = new DefaultTableModel(null, titulos);
        String registro[] = new String[5];
        String sql = "";
        sql = "Select * from ventas where NUM_VEN like '%" + dato + "%'";
        try {
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                registro[0] = rs.getString("NUM_VEN");
                registro[1] = rs.getString("FEC_HOR_VEN");
                registro[2] = rs.getString("TOTAL_VEN");
                registro[3] = rs.getString("CI_CLI_PER");
                registro[4] = rs.getString("CI_FAR_PER");
                model.addRow(registro);
            }
            tblFarmaceuticos.setModel(model);
        } catch (Exception ex) {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblFarmaceuticos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnIngreso = new javax.swing.JButton();
        btnBorrar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtTotVen = new javax.swing.JTextField();
        cbFarmaceutico = new javax.swing.JTextField();
        cbClientes = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtVenNum = new javax.swing.JTextField();
        fecha = new com.toedter.calendar.JDateChooser();
        cbClientes1 = new javax.swing.JComboBox();
        cbFarmaceutico1 = new javax.swing.JComboBox();
        btnDetalle = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblFarmaceuticos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblFarmaceuticos);

        jPanel1.setBackground(new java.awt.Color(10, 120, 200));
        jPanel1.setToolTipText("");

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

        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBorrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnIngreso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(btnIngreso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBorrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnActualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(23, 23, 23))
        );

        jPanel2.setBackground(new java.awt.Color(10, 120, 200));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("CLIENTE");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("FARMACEUTICO");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("TOTAL");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("NÚMERO ");

        txtTotVen.setEditable(false);
        txtTotVen.setText("0");
        txtTotVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTotVenFocusLost(evt);
            }
        });

        cbClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbClientesActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("FECHA ");

        txtVenNum.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtVenNumFocusLost(evt);
            }
        });

        fecha.setDateFormatString("dd/MM/yyyy hh:mm:ss");

        cbClientes1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CLIENTES" }));
        cbClientes1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbClientes1ActionPerformed(evt);
            }
        });

        cbFarmaceutico1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "FARMACEUTICO" }));
        cbFarmaceutico1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbFarmaceutico1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbFarmaceutico))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(44, 44, 44)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtVenNum, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbClientes, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTotVen, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fecha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbClientes1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFarmaceutico1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtVenNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotVen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbClientes1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cbFarmaceutico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbFarmaceutico1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnDetalle.setText("REALIZAR DETALLE");
        btnDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetalleActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(10, 120, 200));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("BÚSQUEDA POR VENTA");

        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField4KeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(10, 120, 200));

        jLabel5.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("TABLA VENTAS");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 260, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 104, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(58, 58, 58)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnDetalle))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 557, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDetalle)
                        .addGap(14, 14, 14)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtVenNumFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtVenNumFocusLost
        // TODO add your handling code here:
        //  buscarClavePrimaria();
    }//GEN-LAST:event_txtVenNumFocusLost

    private void btnIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresoActionPerformed
        // TODO add your handling code here:
        botonIngreso();
        int x=cargarVentas()+1;
        txtVenNum.setText(String.valueOf(x));
      
    }//GEN-LAST:event_btnIngresoActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        botonActualizar();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtTotVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTotVenFocusLost
        // TODO add your handling code here:
        txtTotVen.setText(txtTotVen.getText().toUpperCase());
    }//GEN-LAST:event_txtTotVenFocusLost

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        // TODO add your handling code here:
        botonEliminar();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void jTextField4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField4KeyReleased
        // TODO add your handling code here:
        cargarTabla(jTextField4.getText());
    }//GEN-LAST:event_jTextField4KeyReleased

    private void cbClientes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbClientes1ActionPerformed
        // TODO add your handling code here:
        cbClientes.setText(cbClientes1.getSelectedItem().toString());
    }//GEN-LAST:event_cbClientes1ActionPerformed

    private void cbClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbClientesActionPerformed

    private void btnDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetalleActionPerformed
        // TODO add your handling code here:
        detalles_ventas deven = new detalles_ventas();
        menu.jDesktopPane1.add(deven);
        deven.setVisible(true);
        deven.show();
    }//GEN-LAST:event_btnDetalleActionPerformed

    private void cbFarmaceutico1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbFarmaceutico1ActionPerformed
        // TODO add your handling code here:
        cbFarmaceutico.setText(cbFarmaceutico1.getSelectedItem().toString());
    }//GEN-LAST:event_cbFarmaceutico1ActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
          btnActualizar.setEnabled(false);
         btnBorrar.setEnabled(false);
         btnDetalle.setEnabled(true);
         btnIngreso.setEnabled(true);
         btnCancelar.setEnabled(false);
         limpiar();
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
            java.util.logging.Logger.getLogger(ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventas().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnActualizar;
    public javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnCancelar;
    public javax.swing.JButton btnDetalle;
    public javax.swing.JButton btnIngreso;
    private javax.swing.JTextField cbClientes;
    private javax.swing.JComboBox cbClientes1;
    private javax.swing.JTextField cbFarmaceutico;
    private javax.swing.JComboBox cbFarmaceutico1;
    private com.toedter.calendar.JDateChooser fecha;
    public javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField4;
    public static javax.swing.JTable tblFarmaceuticos;
    private javax.swing.JTextField txtTotVen;
    public static javax.swing.JTextField txtVenNum;
    // End of variables declaration//GEN-END:variables
}
