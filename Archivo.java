import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JOptionPane;


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
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e + " " +
					"\nError!","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
		return retorno;
	}

}
