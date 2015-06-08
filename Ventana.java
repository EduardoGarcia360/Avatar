import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.jws.soap.SOAPBinding.Style;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextPane;


public class Ventana extends JFrame implements ActionListener {

	private JPanel contentPane;
	Archivo ar = new Archivo();
	JButton btnAbrir, btnGuardar, btnGuardarc, btnAnalizar, btnLimpiar;
	JTextArea textArea;
	JMenuItem mntmInformacion;
	JLabel lblImagen;
	int contador=0;
	ImageIcon avatarH = new ImageIcon(getClass().getResource("/Imagenes/avatar_hombre.png"));
	ImageIcon avatarM = new ImageIcon(getClass().getResource("/Imagenes/avatar_mujer.png"));

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ventana frame = new Ventana();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ventana() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 662, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnAbrir = new JButton("Abrir");
		btnAbrir.addActionListener(this);
		btnAbrir.setBounds(54, 368, 89, 23);
		contentPane.add(btnAbrir);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 32, 294, 255);
		contentPane.add(textArea);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 709, 21);
		contentPane.add(menuBar);
		
		JMenu mnAcercaDe = new JMenu("Acerca de..");
		menuBar.add(mnAcercaDe);
		
		mntmInformacion = new JMenuItem("Informacion");
		mntmInformacion.addActionListener(this);
		mnAcercaDe.add(mntmInformacion);
		
		//centrar texto en label ("pomez, 18", JLabel.LEFT)
		lblImagen = new JLabel();
		lblImagen.setOpaque(true);
		lblImagen.setVisible(true);
		lblImagen.setBounds(343, 32, 273, 255);
		contentPane.add(lblImagen);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(this);
		btnGuardar.setBounds(182, 368, 89, 23);
		contentPane.add(btnGuardar);
		
		btnGuardarc = new JButton("Guardar Como...");
		btnGuardarc.setEnabled(false);
		btnGuardarc.addActionListener(this);
		btnGuardarc.setBounds(310, 368, 125, 23);
		contentPane.add(btnGuardarc);
		
		btnAnalizar = new JButton("Analizar");
		btnAnalizar.addActionListener(this);
		btnAnalizar.setBounds(270, 313, 89, 23);
		contentPane.add(btnAnalizar);
		
		btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addActionListener(this);
		btnLimpiar.setBounds(467, 368, 89, 23);
		contentPane.add(btnLimpiar);
		
		
	}
	
	void AnalizarS0(){
		String texto = textArea.getText();
		String [] temp = texto.split("\n");
		int posf = temp.length;
		if( !temp[0].equals("<avatar>") ){
			MarcarError(temp,0);
		}else if( !temp[posf-1].equals("</avatar>") ){
			MarcarError(temp, (posf-1));
		}else{
			AnalizarResto(temp);
		}
	}
	
	//PE -> POSICION ERROR
	void MarcarError(String [] texto, int Pe){
		int Ta = texto.length;
		String [] tmp2 = new String[Ta];
		String dato="", mostrar="";
		for( int i=0; i<(Ta); i++){
			dato = texto[i];
			if( Pe == i ){
				dato = dato + "<<Error";
				tmp2[i] = dato;
				mostrar = mostrar + tmp2[i] + "\n";
			}else{
				tmp2[i] = dato;
				mostrar = mostrar + tmp2[i] + "\n";
			}
		}
		textArea.setText(mostrar);
		textArea.setEditable(true);
	}
	/**
	 * 
	 *------da como resultado----------------
	 *texto: 	<usuario>"pedro pomez"</usuario>
	 *partido usando >
	 *<usuario 0
	 *"pedro pomez"</usuario 1
	 * 
	 */
	
	void AnalizarResto(String [] texto){
		String tmp1 = texto[1];
		String [] atmp1 = tmp1.split(">");
		String tmp2 = atmp1[1];
		String [] atmp2 = tmp2.split("<");
		String [] atmp3 = new String [3];
		String dato="";
		for(int i=0; i<(atmp3.length); i++){
			if(i == 0){
				dato = atmp1[i];
				atmp3[i] = dato;
			}else if(i == 1){
				dato = atmp2[0];
				atmp3[i] = dato;
			}else if(i == 2){
				dato = atmp2[1];
				atmp3[i] = dato;
			}
		}//FIN FOR -> RESULTADO [..] [..] [..]
		
		String cero = atmp3[0];
		String uno = atmp3[1];
		String dos = atmp3[2];
		
		if(!VerificarInicio(cero)){
			
		}else if(!VerificarMedio(dos)){
			
		}else if(VerificarFinal(uno)){
			
		}
		
		if(!atmp3[0].contains("<sexo")){
			MarcarError(texto,1);
		}else if(!atmp3[2].contains("/sexo")){
			MarcarError(texto,1);
		}else{
			String enviar = atmp3[1];
			
			Correcto(enviar,null);
		}
		
	}
	
	boolean VerificarInicio(String Dato){
		String [] Inicio = {"<usuario","<complexion","<personalidad","<sexo"};
		boolean correcto = false;
		for(int i=0; i<Inicio.length; i++){
			if(Dato.equals(Inicio[i])){
				correcto = true;
			}
		}
		return correcto;
	}
	
	boolean VerificarMedio(String Dato){
		String [] Medio = {"delgado","normal","gordo","enojado","alegre","neutro","masculino","femenino"};
		boolean correcto = false;
		for(int i=0; i<Medio.length; i++){
			if(Dato.equals(Medio[i]) || esnumero(Dato)){
				correcto = true;
			}
		}
		return correcto;
	}
	
	boolean VerificarFinal(String Dato){
		String [] Fin = {"/usuario", "/complexion", "/personalidad","/sexo"};
		boolean correcto = false;
		for(int i=0; i<Fin.length; i++){
			if(Dato.equals(Fin[i])){
				correcto = true;
			}
		}
		return correcto;
	}
	
	public static boolean esnumero(String dato){
        try{
            Integer.parseInt(dato);
        }catch(NumberFormatException nfe){
            return false;
        }
        return true;
    }
	
	private boolean Buscar(CharSequence letra, String palab){
        boolean encontrar;
        encontrar = palab.contains(letra);
        return encontrar;
    }
	
	void Correcto(String DatoMedio, String nombre){
		
		lblImagen.setText(nombre);
		if(DatoMedio.equals("masculino")){
			lblImagen.setIcon(avatarH);
			lblImagen.setForeground(Color.BLUE);
			
		}
		
		if(DatoMedio.equals("femenino")){
			lblImagen.setIcon(avatarM);
			lblImagen.setForeground(Color.PINK);
			
		}
		
		if(DatoMedio.equals("enojado")){
			lblImagen.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
		}
		
		if(DatoMedio.equals("alegre")){
			lblImagen.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
		}
		
		if(DatoMedio.equals("neutro")){
			lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4));
		}
		
	}
	
	void ObtenerRutaDeArchivo(){
		try{
			String ruta, datos;
			JFileChooser archivo = new JFileChooser();
			FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos AVA & LFP", "ava", "lfp");
			archivo.setFileFilter(filtro);
			archivo.showOpenDialog(archivo);
			ruta = archivo.getSelectedFile().getAbsolutePath();
			datos = ar.ArbirARCHIVO(ruta);
			textArea.setText(datos);
			textArea.setEditable(false);
			btnGuardarc.setEnabled(true);
		}catch(Exception ex){
			JOptionPane.showMessageDialog(null,"No seleccionaste ningun archivo","Advertencia", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == btnAbrir){
			ObtenerRutaDeArchivo();
		}
		
		if(e.getSource() == mntmInformacion){
			JOptionPane.showMessageDialog(null, "Realizado por: Eduardo Antonio Garcia Franco"
					+ "\nCarnet: 2012-12961","Acerda de..",JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(e.getSource() == btnGuardar){
			if(textArea.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Aun no has ingresado ninguna linea de codigo","Advertencia",JOptionPane.INFORMATION_MESSAGE);
			}else{
				String texto = textArea.getText();
				ar.Guardar(texto);
			}
		}
		
		if(e.getSource() == btnGuardarc){
			if(textArea.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Aun no has seleccionado un archivo","Advertencia",JOptionPane.INFORMATION_MESSAGE);
			}else{
				
			}
		}
		
		if(e.getSource() == btnLimpiar){
			textArea.setText("");
			lblImagen.setText("");
			lblImagen.setIcon(null);
			lblImagen.setBorder(null);
			textArea.setEditable(true);
		}
		
		if(e.getSource() == btnAnalizar){
			if(textArea.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Nada que analizar","Notificacion",JOptionPane.INFORMATION_MESSAGE);
			}else{
				lblImagen.setText("");
				lblImagen.setIcon(null);
				lblImagen.setForeground(null);
				lblImagen.setBorder(null);
				AnalizarS0();
			}
		}
	}
}
