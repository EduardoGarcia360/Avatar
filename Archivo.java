import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Archivo {
	
	public String ArbirARCHIVO(String ruta){
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
	
	public void Guardar(String texto){
		JFileChooser FC = new JFileChooser();
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
	
	public void GuardarComo(){
		
	}

}
