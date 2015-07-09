/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;

/**
 *
 * @author Javy
 */
public class GestionA {
    FileInputStream entrada;
    File archivo;    
    FileOutputStream salida;
    int tamaño;

 public byte[] dirimg (File archivo){
    byte[] bytesImg = new byte[1024*100];
    try{
        entrada = new FileInputStream(archivo);
        entrada.read(bytesImg);
        tamaño= (int) archivo.length();
    }catch(Exception ex){}
    return bytesImg;
}
   
 public void GuardarImagen(File archivoGuardar,byte[] byteImagen){
     try{
         salida = new FileOutputStream(archivoGuardar);
         salida.write(byteImagen);
     }catch(Exception ex){      
     }
   
 }
 
 
}
