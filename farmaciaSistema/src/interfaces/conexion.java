/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;




/**
 *
 * @author javy
 */
public class conexion {
    Connection connect = null;
    public Connection conectar(){
        try{
            Class.forName("org.postgresql.Driver");
            connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/farmacia","postgres","desarrollo");
            JOptionPane.showMessageDialog(null, "Conexion Exitosa");
        }catch(Exception ex){
             JOptionPane.showMessageDialog(null, "No se pudo conectar"+ex);
        }
        return connect;
    }
}
