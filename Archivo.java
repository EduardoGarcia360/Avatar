import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Archivo {
	
	boolean ArchivoAbierto = false;
	File ArchivoSeleccionado;
	
	public String AbrirARCHIVO(String ruta){
		File archivo = new File(ruta);
		String temp="", retorno="";
		try {
			FileReader leer = new FileReader(archivo);
			BufferedReader BR = new BufferedReader(leer);
			try {
				while((temp = BR.readLine()) != null){
					retorno = retorno + temp + "\n";
				}	
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "No hay datos en el archivo seleccionado","Advertencia",1);
				e.printStackTrace();
			}
			try {
				BR.close();
			} catch (IOException e) {}
		} catch (FileNotFoundException e) {}
		return retorno;
	}
	
	public void Guardar(String texto, String Ruta, boolean abierto){
		if(abierto == true){
			try{
				File archivo = new File(Ruta);
				if(archivo.delete() == true){
					File reemplazo = new File(Ruta);
					PrintWriter printwriter = new PrintWriter(reemplazo);
	                printwriter.print(texto);
	                printwriter.close();
	                JOptionPane.showMessageDialog(null, "Archivo Guardado Exitosamente.","Notificacion", JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "Error.","Notificacion", JOptionPane.WARNING_MESSAGE);
				}
			}catch(Exception e){}
		}else
			if(abierto == false){
				JFileChooser FC = new JFileChooser("C:\\Users\\Edu\\Desktop\\");
				FileNameExtensionFilter filtro = new FileNameExtensionFilter("lfp","ava");
		        FC.addChoosableFileFilter(filtro);
		        int s = FC.showSaveDialog(null);
		        try{
		            if (s == JFileChooser.APPROVE_OPTION){
		                File JFC = FC.getSelectedFile();
		                String ruta = JFC.getAbsolutePath();
		                PrintWriter printwriter = new PrintWriter(JFC);
		                printwriter.print(texto);
		                printwriter.close();
		                
		                if( !(ruta.endsWith(".ava")) && !(ruta.endsWith(".lfp")) ){
		                	File temp = new File(ruta+".lfp");
		                    JFC.renameTo(temp);
		                    JOptionPane.showMessageDialog(null, "El Archivo se Guardo con la extencion .lfp","Notificacion", JOptionPane.INFORMATION_MESSAGE);
		                }else{
		                	JOptionPane.showMessageDialog(null, "Archivo Guardado","Notificacion", JOptionPane.INFORMATION_MESSAGE);
		                }
		            }
		        }catch (Exception e){
		            JOptionPane.showMessageDialog(null,"Error al guardar", "Notificacion", JOptionPane.ERROR_MESSAGE);
		        }
			}
		
	}
	
	public void GuardarComo(String texto){
		JFileChooser FC = new JFileChooser("C:\\Users\\Edu\\Desktop\\");
		FC.setDialogTitle("Guardar como...");
        int s = FC.showSaveDialog(null);
        try{
            if (s == JFileChooser.APPROVE_OPTION){
                File JFC = FC.getSelectedFile();
                String ruta = JFC.getAbsolutePath();
                PrintWriter printwriter = new PrintWriter(JFC);
                printwriter.print(texto);
                printwriter.close();
                
                if( !(ruta.endsWith(".ava")) && !(ruta.endsWith(".lfp")) ){
                	File temp = new File(ruta+".lfp");
                    JFC.renameTo(temp);
                    JOptionPane.showMessageDialog(null, "El Archivo se Guardo con la extencion .lfp","Notificacion", JOptionPane.INFORMATION_MESSAGE);
                }else{
                	JOptionPane.showMessageDialog(null, "Archivo Guardado","Notificacion", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error al guardar", "Notificacion", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	public void GenerarHTMLLexemas(String datos){
		String Titulo = "<html><head><link rel=\"stylesheet\" type= \"text/css\" href=\"Estilo.css\" /><title>Tabla de Lexemas</title></head><body><h1><font face=\"Calibri\">Tabla de Lexemas por token</font></h1>";
		String miTabla = "<table id=\"miTabla\"><tr><td>No.</td><td>Lexema</td><td>Token</td><td>Posicion</td></tr>";
		String cierrePagina = "</table></body></html>";
		String contenidoFila = "";
		//FORMATO PARA FILAS:<tr><td>1</td><td>enojado</td><td>3</td><td>Fila 1 columna 8</td></tr>
		String datosv2 = datos.substring(0, datos.length()-1);
		String [] LexemasenCodigo = datosv2.split("%");
		
		int PosFinal = LexemasenCodigo.length;
			
			for(int i=0; i<PosFinal; i++){
				contenidoFila = contenidoFila + "<tr><td>"+LexemasenCodigo[i]+"</td><td>"+LexemasenCodigo[i+1]+"</td><td>"+LexemasenCodigo[i+2]+"</td><td>"+LexemasenCodigo[i+3]+"</td></tr>";
				i = i + 3;
			}
			
		
		String PaginaCompleta = Titulo + miTabla + contenidoFila + cierrePagina;
		
		try{
			File correcto = new File("tabla de lexemas.html");
			FileWriter escritor = new FileWriter(correcto);
			BufferedWriter BW = new BufferedWriter(escritor);
			PrintWriter salida = new PrintWriter(BW);
			
			salida.write(PaginaCompleta);
			
			salida.close();
			BW.close();
			
			Desktop des = Desktop.getDesktop();
			if(correcto.exists()){
				des.open(correcto);
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Error al guardar", "Notificacion", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void GenerarHTMLErrores(String datos){
		String Titulo = "<html><head><link rel=\"stylesheet\" type= \"text/css\" href=\"Estilo.css\" /><title>Errores Lexicos</title></head><body><h1><font face=\"Calibri\">Tabla de Lexemas por token</font></h1>";
		String miTabla = "<table id=\"miTabla\"><tr><td>No.</td><td>Lexema</td><td>Token</td><td>Posicion</td></tr>";
		String cierrePagina = "</table></body></html>";
		String contenidoFila = "<tr><td>1</td><td>enojado</td><td>3</td><td>Fila 1 columna 8</td></tr>";
		//FORMATO PARA FILAS:<tr><td>1</td><td>enojado</td><td>3</td><td>Fila 1 columna 8</td></tr>
		
		String [] ErroresenCodigo = datos.split("%");
		
		String PaginaCompleta = Titulo + miTabla + contenidoFila + cierrePagina;
		
		try{
			File errores = new File("errores lexicos.html");
			FileWriter escritor = new FileWriter(errores);
			BufferedWriter BW = new BufferedWriter(escritor);
			PrintWriter salida = new PrintWriter(BW);
			
			salida.write(PaginaCompleta);
			
			salida.close();
			BW.close();
			
			Desktop des = Desktop.getDesktop();
			if(errores.exists()){
				des.open(errores);
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,"Error al guardar", "Notificacion", JOptionPane.ERROR_MESSAGE);
		}
	}

}
