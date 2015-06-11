import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.StringTokenizer;

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
		setBounds(100, 100, 875, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnAbrir = new JButton("Abrir");
		btnAbrir.addActionListener(this);
		btnAbrir.setBounds(54, 368, 89, 23);
		contentPane.add(btnAbrir);
		
		textArea = new JTextArea();
		textArea.append("linea 1\n");
		textArea.append("linea 2");
		//textArea.setForeground(Color.BLUE);
		textArea.setBounds(10, 32, 501, 255);
		contentPane.add(textArea);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 969, 21);
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
		lblImagen.setBounds(580, 32, 273, 255);
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
	
	void Unir(){
		String Codigo = textArea.getText();

			StringTokenizer TokensCodigo = new StringTokenizer(Codigo,"\n");
			String tmp2="";
			while( TokensCodigo.hasMoreTokens() ){
				String linea = TokensCodigo.nextToken();
				tmp2 = tmp2 + linea;
			}//UNE LO INGRESADO EN UNA SOLA LINEA
			
			StringTokenizer Tokens1 = new StringTokenizer(tmp2,">");
			String tmp3="";
			while( Tokens1.hasMoreTokens() ){
				String linea = Tokens1.nextToken();
					tmp3 = tmp3 + linea + ">%";
			}
			
			char [] aTmp = tmp3.toCharArray();
			String union="", a="", b="";
			for(int i=0; i<aTmp.length; i++){
				a = Character.toString(aTmp[i]);
				if(a.equals("<")){
					try{
						b = Character.toString(aTmp[i-1]);
						if(!b.equals("%")){
							union = union + "%" + a;
						}else{
							union = union + a;
						}
					}catch(Exception e){
						union = union + a;
					}
				}else{
					union = union + a;
				}
			}//DIVIDE CADA TOKEN CON "%"
			Analizar(union);
	}
	
	void Analizar(String Codigo){
		String [] TokenAnalizar = Codigo.split("%");
		int Pf = TokenAnalizar.length;
		if( !TokenAnalizar[0].equals("<avatar>") && !TokenAnalizar[Pf-1].equals("</avatar>")){
			JOptionPane.showMessageDialog(null, "Faltan las etiquetas principales.","Error",JOptionPane.ERROR_MESSAGE);
		}else{
			
			
			String nombreUsuario = "";
			for(int i=1; i<(Pf-2); i++){
				String TokenActual = TokenAnalizar[i];
				if( EtiquetaAbre(TokenActual)==false && EtiquetaCierre(TokenActual)==false ){
					JOptionPane.showMessageDialog(null, "error en: "+TokenActual);
					break;
					
				}else{
					
					if(TokenActual.equals("<usuario>")){
						
						char [] nombre = TokenAnalizar[i+1].toCharArray();
						int f = nombre.length;
						if(nombre[0] == 34 && nombre[f-1] == 34){
							String nom="";
							for(int n=1; n<nombre.length-2; n++){
								nom = nom + Character.toString(nombre[n]);
							}
							nombreUsuario = nombreUsuario + " Nombre: " + nom;
							
						}else{
							JOptionPane.showMessageDialog(null, "Los nombres de usuario van entre comillas.","Error",JOptionPane.ERROR_MESSAGE);
							break;
						}
						
						if(EtiquetaCierre(TokenAnalizar[i+2]) == false){
							String no = TokenAnalizar[i+2];
							lblImagen.setText("");
							JOptionPane.showMessageDialog(null, no + " No es una etiqueta de cierre.","Error",JOptionPane.ERROR_MESSAGE);
							break;
						}else{
							lblImagen.setText(nombreUsuario);
							i = i +2;
						}
						
					}else if(TokenActual.equals("<edad>")){
						
						if( esnumero(TokenAnalizar[i+1]) == false){
							JOptionPane.showMessageDialog(null, TokenAnalizar[i+1] + " No es una edad.","Error",JOptionPane.ERROR_MESSAGE);
							break;
						}else{
							nombreUsuario = nombreUsuario + " Edad: " + TokenAnalizar[i+1];
						}
						if( EtiquetaCierre(TokenAnalizar[i+2]) == false){
							JOptionPane.showMessageDialog(null, TokenAnalizar[i+2] + " No es una etiqueta de cierre.","Error",JOptionPane.ERROR_MESSAGE);
							break;
						}else{
							lblImagen.setText(nombreUsuario);
							i = i + 2;
						}
						
					}else if(TokenActual.equals("<complexion>")){
						
						if( PalabraReservada(TokenAnalizar[i+1], "complexion")== false){
							JOptionPane.showMessageDialog(null, TokenAnalizar[i+1] + " No forma parte de las palabras reservadas.","Error",JOptionPane.ERROR_MESSAGE);
							break;
						}else{
							//ALGO
						}
						if( EtiquetaCierre(TokenAnalizar[i+2]) == false){
							JOptionPane.showMessageDialog(null, TokenAnalizar[i+2] + " No es una etiqueta de cierre.","Error",JOptionPane.ERROR_MESSAGE);
							break;
						}else{
							//ALGO
							i = i + 2;
						}
						
					}else if(TokenActual.equals("<personalidad>")){
						
						if( PalabraReservada(TokenAnalizar[i+1], "personalidad")== false){
							JOptionPane.showMessageDialog(null, TokenAnalizar[i+1] + " No forma parte de las palabras reservadas.","Error",JOptionPane.ERROR_MESSAGE);
							break;
						}
						if( EtiquetaCierre(TokenAnalizar[i+2]) == false){
							JOptionPane.showMessageDialog(null, TokenAnalizar[i+2] + " No es una etiqueta de cierre.","Error",JOptionPane.ERROR_MESSAGE);
							break;
						}else{
							if(TokenAnalizar[i+1].equals("enojado")){
								lblImagen.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
							}else if(TokenAnalizar[i+1].equals("alegre")){
								lblImagen.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 5));
							}else if(TokenAnalizar[i+1].equals("neutro")){
								lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
							}
							i = i + 2;
						}
						
					}else if(TokenActual.equals("<sexo>")){
						
						if( PalabraReservada(TokenAnalizar[i+1], "sexo")== false){
							JOptionPane.showMessageDialog(null, TokenAnalizar[i+1] + " No forma parte de las palabras reservadas.","Error",JOptionPane.ERROR_MESSAGE);
							break;
						}
						if( EtiquetaCierre(TokenAnalizar[i+2]) == false){
							JOptionPane.showMessageDialog(null, TokenAnalizar[i+2] + " No es una etiqueta de cierre.","Error",JOptionPane.ERROR_MESSAGE);
							break;
						}else{
							if(TokenAnalizar[i+1].equals("maculino")){
								lblImagen.setForeground(Color.BLUE);
								lblImagen.setIcon(avatarH);
							}else if(TokenAnalizar[i+1].equals("femenino")){
								lblImagen.setForeground(Color.PINK);
								lblImagen.setIcon(avatarM);
							}
							i = i + 2;
						}
					}
					
					
				}
			}
			
		}
	
	}
	
	boolean EtiquetaAbre(String Dato){
		String [] PalabrasReservadas = {"<avatar>","<usuario>","<complexion>","<personalidad>","<sexo>","<edad>"};
		boolean correcto = false;
		for(int i=0; i<PalabrasReservadas.length; i++){
			if(Dato.equals( PalabrasReservadas[i] ) ){
				correcto = true;
			}
		}
		return correcto;
	}
	
	boolean PalabraReservada(String Dato, String Etiqueta){
		boolean correcto = false;
		if(Etiqueta.equals("complexion")){
			String [] Complexion = {"delgado","normal","gordo"};
			for(int i=0; i<Complexion.length; i++){
				if(Dato.equals(Complexion[i])){
					correcto = true;
				}
			}
		}else if(Etiqueta.equals("personalidad")){
			String [] Personalidad = {"enojado","alegre","neutro"};
			for(int i=0; i<Personalidad.length; i++){
				if(Dato.equals(Personalidad[i])){
					correcto = true;
				}
			}
		}else if(Etiqueta.equals("sexo")){
			String [] Sexo = {"masculino","femenino"};
			for(int i=0; i<Sexo.length; i++){
				if(Dato.equals(Sexo[i])){
					correcto = true;
				}
			}
		}
		return correcto;
	}
	
	boolean EtiquetaCierre(String Dato){
		String [] Fin = {"</avatar>","</usuario>","</complexion>","</personalidad>","</sexo>","</edad>"};
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
				Unir();
			}
		}
	}
}
