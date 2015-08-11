/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes; 
/**
 *
 * @author Javy
 */
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.swing.*;

import com.sun.pdfview.*;

import java.security.Principal;

public class verPDF extends JInternalFrame {
	
	PagePanel panelpdf;
	JFileChooser selector;
	PDFFile pdffile;
	int indice=0;
	
	public verPDF(final String path){
		panelpdf=new PagePanel();
                JPanel pabajo=new JPanel();
                
                JButton mirar=new JButton("VER REPORTE");
		mirar.addActionListener(new ActionListener(){

			@Override
                public void actionPerformed(ActionEvent e) {
                     try{   
                      File file = new File(path);
		      RandomAccessFile raf = new RandomAccessFile(file, "r");
		      FileChannel channel = raf.getChannel();
		      ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY,0, channel.size());
		      pdffile = new PDFFile(buf);
		      PDFPage page = pdffile.getPage(indice);
		      panelpdf.showPage(page);
		      repaint();
                    
		}catch(Exception ioe){
                    JOptionPane.showMessageDialog(null, "Error al abrir el archivo");
		}
                     
                }
              
		});
                
		
		JButton bsiguiente=new JButton("Siguiente");
		bsiguiente.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				indice++;
				PDFPage page = pdffile.getPage(indice);
			        panelpdf.showPage(page);
			}
			
		});
                
		JButton banterior=new JButton("Anterior");
		banterior.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				indice--;
				PDFPage page = pdffile.getPage(indice);
			        panelpdf.showPage(page);
			}
			
		});
                
                JButton salir = new JButton("Salir");
		salir.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
                                JOptionPane.showMessageDialog(null,"Para volver a mirar otro reporte debe reinicializar el sistema");
   
                                        
			}
			
		});
		
//		archivo.add(ver);
//		barra.add(archivo);
//		setJMenuBar(barra);
                pabajo.add(mirar);
               // pabajo.add(banterior);
	//	pabajo.add(bsiguiente);
                pabajo.add(salir);
                add(panelpdf);
		add(pabajo,BorderLayout.SOUTH);
                mirar.requestFocus(true);

                //JOptionPane.showMessageDialog(null, "Presione Siguiente y se visualizara el documento");
                //banterior.requestFocus();
                
	}
	
 

}
