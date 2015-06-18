import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


public class Ventana extends JFrame implements ActionListener {

	private JPanel contentPane;
	Archivo ar = new Archivo();
	JButton btnAbrir, btnGuardar, btnGuardarc, btnAnalizar, btnLimpiar;
	JTextArea textArea;
	JMenuItem mntmInformacion, mntmManualu, mntmManualt;
	JLabel lblImagen;
	int contador=0;
	public String RutadeArchivo="";
	boolean ArchivoAbierto = false;
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
	 * Agregado todo lo visual: botones, label, textarea, etc.
	 */
	public Ventana() {
		setBounds(100, 100, 959, 440);
		this.setLocationRelativeTo(null);
		this.setTitle("Practica 1");
		cerrar();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnAbrir = new JButton("Abrir");
		btnAbrir.addActionListener(this);
		btnAbrir.setBounds(54, 368, 89, 23);
		contentPane.add(btnAbrir);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 32, 501, 255);
		contentPane.add(textArea);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1001, 21);
		contentPane.add(menuBar);
		
		JMenu mnAcercaDe = new JMenu("Acerca de..");
		menuBar.add(mnAcercaDe);
		
		mntmInformacion = new JMenuItem("Informacion");
		mntmInformacion.addActionListener(this);
		
		mntmManualt = new JMenuItem("Manual Tecnico");
		mntmManualt.addActionListener(this);
		mnAcercaDe.add(mntmManualt);
		
		mntmManualu = new JMenuItem("Manual Usuario");
		mntmManualu.addActionListener(this);
		mnAcercaDe.add(mntmManualu);
		mnAcercaDe.add(mntmInformacion);
		
		lblImagen = new JLabel();
		lblImagen.setBorder(BorderFactory.createLineBorder(Color.black));
		lblImagen.setOpaque(true);
		lblImagen.setVisible(true);
		lblImagen.setBounds(580, 32, 347, 255);
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
	
	/**
	 * Metodo para preguntarle al usuario si quiere salir o no.
	 */
	public void cerrar(){
		try{
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					PreguntarSalida();
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo que muestra en pantalla las opciones para salir.
	 */
	public void PreguntarSalida(){
		int respuesta = JOptionPane.showConfirmDialog(this, "¿Quiere salir?", "Mensaje",JOptionPane.YES_NO_OPTION);
		if(respuesta == JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}
	
	/**
	 * La funcion es que va a retornar el texto ingresado en el area para introducir el codigo en una sola linea
	 * de esta forma no importa si el usuario ingresa el codigo en una sola linea, ejecutara el codigo igual.
	 * la forma de salida del codigo estaria dividida por el simbolo '%' siendo el siguiente un ejemplo de la salida:
	 * <etiqueta>%palabra/texto%<etiqueta>%palabra/texto%<etiqueta>
	 */
	private void Unir(){
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
	
	/**
	 * La funcion es que analiza el codigo previamente unido en una linea con el metodo 'Unir'
	 * verifica primeramente que existan la etiquetas de 'avatar' en el codigo de lo contrario no funcionara
	 * seguidamente verifica si existen las etiquetas respectivas de las palabras reservadas, si estuvieran mal
	 * escritas se ignorara la linea y pasara a la siguiente por el contrario si la etiqueta de abrir esta correcta
	 * lo siguiente en verificar seria la etiqueta de cerrado si es incorrecta se ignorara la linea, pero si es correcta
	 * se pasara a revisar el contenido dentro de las etiquetas ver si pertenece a la palabras reservadas o si
	 * existen los sibolos necesarios para el cumplimiento de las mismas, cuando encuentre un error o este todo o algunas etiquetas
	 * correctas se generara un archivo en el cual estaran especificados los errores y las palabras correctas.
	 * @param Codigo recibe el codigo unido en una linea
	 */
	private void Analizar(String Codigo){
		String ErroresdeCodigo = "", LexemasdeCodigo="";
		int ContadordeErrores = 0, ContadordeLexemas=0;
		boolean ErrorEncontrado = true, Error = false;
		String [] TokenAnalizar = Codigo.split("%");
		int Pf = TokenAnalizar.length;
		if( !TokenAnalizar[0].equals("<avatar>") && !TokenAnalizar[Pf-1].equals("</avatar>")){
			JOptionPane.showMessageDialog(null, "Faltan las etiquetas principales.","Error",JOptionPane.ERROR_MESSAGE);
			ContadordeErrores++;
			ErroresdeCodigo = ErroresdeCodigo + ContadordeErrores + "%" + TokenAnalizar[0] + "%" + "desconocido" + "%" + "Fila 1 columna "+0+"%"+"error lexico"+"%";
			ContadordeErrores++;
			ErroresdeCodigo = ErroresdeCodigo + ContadordeErrores + "%" + TokenAnalizar[Pf-1] + "%" + "desconocido" + "%" + "Fila 1 columna "+(Pf-1)+"%"+"error lexico"+"%";
			ErrorEncontrado = true;
		}else{
			String nombreUsuario = "";
			for(int i=1; i<(Pf-2); i++){
				String TokenActual = TokenAnalizar[i];
				if( EtiquetaAbre(TokenActual)==false && EtiquetaCierre(TokenActual)==false && PalabraReservada(TokenActual,"")==false ){
					JOptionPane.showMessageDialog(null, "Etiqueta invalida: "+TokenActual);
					i = i +2;
					ContadordeErrores++;
					ErroresdeCodigo = ErroresdeCodigo + ContadordeErrores + "%" + TokenActual + "%" + "desconocido" + "%" + "Fila 1 columna "+i+"%"+"Etiqueta desconocida" + "%";
					Error = true;
					
				}else{
					
					if(TokenActual.equals("<usuario>")){
						
						char [] nombre = TokenAnalizar[i+1].toCharArray();
						int f = nombre.length;
						String nom="";
						if(nombre[0] == 34 && nombre[f-1] == 34){
							for(int n=1; n<nombre.length-1; n++){
								nom = nom + Character.toString(nombre[n]);
							}
							nombreUsuario = nombreUsuario + " Nombre: " + nom;
							if(EtiquetaCierre(TokenAnalizar[i+2]) == false){
								String no = TokenAnalizar[i+2];
								lblImagen.setText("");
								JOptionPane.showMessageDialog(null, no + " No es una etiqueta de cierre.","Error",JOptionPane.ERROR_MESSAGE);
								ContadordeErrores++;
								ErroresdeCodigo = ErroresdeCodigo+ContadordeErrores+"%"+TokenAnalizar[i+2]+"%"+"desconocido"+"%"+"Fila 1 columna "+(i+2)+"%"+"Etiqueta desconocida" + "%";
								Error = true;
								i = i + 2;
							}else{
								ContadordeLexemas++;
								ErrorEncontrado = false;
								LexemasdeCodigo = LexemasdeCodigo+ContadordeLexemas +"%"+TokenAnalizar[i]+"%"+(i)+"%"+"Fila 1 Columna "+(i)+"%";
								ContadordeLexemas++;
								LexemasdeCodigo = LexemasdeCodigo+ContadordeLexemas +"%"+ nom+"%"+(i+1)+"%"+"Fila 1 Columna "+(i+1)+"%";
								ContadordeLexemas++;
								LexemasdeCodigo = LexemasdeCodigo+ContadordeLexemas +"%"+TokenAnalizar[i+2]+"%"+(i+2)+"%"+"Fila 1 Columna "+(i+2)+"%";
								lblImagen.setText(nombreUsuario);
								i = i +2;
							}
						}else{
							JOptionPane.showMessageDialog(null, "Los nombres de usuario van entre comillas.","Error",JOptionPane.ERROR_MESSAGE);
							ContadordeErrores++;
							ErroresdeCodigo = ErroresdeCodigo + ContadordeErrores + "%" + TokenAnalizar[i+1] + "%" + "Desconocido" + "%" + "Fila 1 columna "+(i+1)+"%"+"Error en Simbolos" + "%";
							Error = true;
							i = i + 2;
						}
					}else if(TokenActual.equals("<edad>")){
						String edad="";
						if( esnumero(TokenAnalizar[i+1]) == false){
							JOptionPane.showMessageDialog(null, TokenAnalizar[i+1] + " No es una edad.","Error",JOptionPane.ERROR_MESSAGE);
							ContadordeErrores++;
							ErroresdeCodigo = ErroresdeCodigo + ContadordeErrores + "%" + TokenAnalizar[i+1] + "%" + "desconocido" + "%" + "Fila 1 columna "+(i+1)+"%"+"Edad incorrecta" + "%";
							Error = true;
							i = i + 2;
						}else{
							edad = TokenAnalizar[i+1];
							nombreUsuario = nombreUsuario + " Edad: " + TokenAnalizar[i+1];
							if( EtiquetaCierre(TokenAnalizar[i+2]) == false){
								JOptionPane.showMessageDialog(null, TokenAnalizar[i+2] + " No es una etiqueta de cierre.","Error",JOptionPane.ERROR_MESSAGE);
								ContadordeErrores++;
								ErroresdeCodigo = ErroresdeCodigo + ContadordeErrores + "%" + TokenAnalizar[i+2] + "%" + "desconocido" + "%" + "Fila 1 columna "+(i+2)+"%"+"Etiqueta desconocida" + "%";
								Error = true;
								i = i + 2;
							}else{
								ErrorEncontrado = false;
								ContadordeLexemas++;
								LexemasdeCodigo = LexemasdeCodigo+ContadordeLexemas +"%"+TokenAnalizar[i]+"%"+(i)+"%"+"Fila 1 Columna "+(i)+"%";
								ContadordeLexemas++;
								LexemasdeCodigo = LexemasdeCodigo+ContadordeLexemas +"%"+edad+"%"+(i+1)+"%"+"Fila 1 Columna "+(i+1)+"%";
								ContadordeLexemas++;
								LexemasdeCodigo = LexemasdeCodigo+ContadordeLexemas +"%"+TokenAnalizar[i+2]+"%"+(i+2)+"%"+"Fila 1 Columna "+(i+2)+"%";
								lblImagen.setText(nombreUsuario);
								i = i + 2;
							}
						}
					}else if(TokenActual.equals("<complexion>")){
						
						if( PalabraReservada(TokenAnalizar[i+1], "complexion")== false){
							JOptionPane.showMessageDialog(null, TokenAnalizar[i+1] + " No forma parte de las palabras reservadas de esta etiqueta.","Error",JOptionPane.ERROR_MESSAGE);
							ContadordeErrores++;
							ErroresdeCodigo = ErroresdeCodigo + ContadordeErrores + "%" + TokenAnalizar[i+1] + "%" + "desconocido" + "%" + "Fila 1 columna "+(i+1)+"%"+"No es Palabra reservada" + "%";
							Error = true;
							i = i + 2;
						}else{
							if( EtiquetaCierre(TokenAnalizar[i+2]) == false){
								JOptionPane.showMessageDialog(null, TokenAnalizar[i+2] + " No es una etiqueta de cierre.","Error",JOptionPane.ERROR_MESSAGE);
								ContadordeErrores++;
								ErroresdeCodigo = ErroresdeCodigo+ContadordeErrores+"%"+TokenAnalizar[i+2]+"%"+"desconocido"+"%"+"Fila 1 columna "+(i+2)+"%"+"Etiqueta desconocida" + "%";
								Error = true;
								i = i + 2;
							}else{
								ContadordeLexemas++;
								ErrorEncontrado = false;
								LexemasdeCodigo = LexemasdeCodigo+ContadordeLexemas +"%"+TokenAnalizar[i]+"%"+(i)+"%"+"Fila 1 Columna "+(i)+"%";
								ContadordeLexemas++;
								LexemasdeCodigo = LexemasdeCodigo+ContadordeLexemas +"%"+TokenAnalizar[i+1]+"%"+(i+1)+"%"+"Fila 1 Columna "+(i+1)+"%";
								ContadordeLexemas++;
								LexemasdeCodigo = LexemasdeCodigo+ContadordeLexemas +"%"+TokenAnalizar[i+2]+"%"+(i+2)+"%"+"Fila 1 Columna "+(i+2)+"%";
								i = i + 2;
							}
						}
					}else if(TokenActual.equals("<personalidad>")){
						
						if( PalabraReservada(TokenAnalizar[i+1], "personalidad")== false){
							lblImagen.setBackground(null);
							JOptionPane.showMessageDialog(null, TokenAnalizar[i+1] + " No forma parte de las palabras reservadas de esta etiqueta.","Error",JOptionPane.ERROR_MESSAGE);
							ContadordeErrores++;
							ErroresdeCodigo = ErroresdeCodigo + ContadordeErrores + "%" + TokenAnalizar[i+1] + "%" + "desconocido" + "%" + "Fila 1 columna "+(i+1)+"%"+"No es Palabra reservada" + "%";
							Error = true;
							i = i + 2;
						}else{
							if( EtiquetaCierre(TokenAnalizar[i+2]) == false){
								lblImagen.setBackground(null);
								JOptionPane.showMessageDialog(null, TokenAnalizar[i+2] + " No es una etiqueta de cierre.","Error",JOptionPane.ERROR_MESSAGE);
								ContadordeErrores++;
								ErroresdeCodigo = ErroresdeCodigo + ContadordeErrores + "%" + TokenAnalizar[i+2] + "%" + "desconocido" + "%" + "Fila 1 columna "+(i+2)+"%"+"Etiqueta desconocida" + "%";
								Error = true;
								i = i + 2;
							}else{
								ContadordeLexemas++;
								ErrorEncontrado = false;
								LexemasdeCodigo = LexemasdeCodigo+ContadordeLexemas +"%"+TokenAnalizar[i]+"%"+(i)+"%"+"Fila 1 Columna "+(i)+"%";
								ContadordeLexemas++;
								LexemasdeCodigo = LexemasdeCodigo+ContadordeLexemas +"%"+TokenAnalizar[i+1]+"%"+(i+1)+"%"+"Fila 1 Columna "+(i+1)+"%";
								ContadordeLexemas++;
								LexemasdeCodigo = LexemasdeCodigo+ContadordeLexemas +"%"+TokenAnalizar[i+2]+"%"+(i+2)+"%"+"Fila 1 Columna "+(i+2)+"%";
								Correcto(TokenAnalizar[i+1],"personalidad");
								i = i + 2;
							}
						}
					}else if(TokenActual.equals("<sexo>")){
						
						if( PalabraReservada(TokenAnalizar[i+1], "sexo")== false){
							lblImagen.setForeground(null);
							JOptionPane.showMessageDialog(null, TokenAnalizar[i+1] + " No forma parte de las palabras reservadas de esta etiqueta.","Error",JOptionPane.ERROR_MESSAGE);
							ContadordeErrores++;
							ErroresdeCodigo = ErroresdeCodigo + ContadordeErrores + "%" + TokenAnalizar[i+1] + "%" + "desconocido" + "%" + "Fila 1 columna "+(i+1)+"%"+"No es palabra reservada" + "%";
							Error = true;
							i = i + 2;
						}else{
							if( EtiquetaCierre(TokenAnalizar[i+2]) == false){
								lblImagen.setForeground(null);
								JOptionPane.showMessageDialog(null, TokenAnalizar[i+2] + " No es una etiqueta de cierre.","Error",JOptionPane.ERROR_MESSAGE);
								ContadordeErrores++;
								ErroresdeCodigo = ErroresdeCodigo + ContadordeErrores + "%" + TokenAnalizar[i+2] + "%" + "desconocido" + "%" + "Fila 1 columna "+(i+2)+"%"+"Etiqueta desconocida" + "%";
								Error = true;
								i = i + 2;
							}else{
								ContadordeLexemas++;
								ErrorEncontrado = false;
								LexemasdeCodigo = LexemasdeCodigo+ContadordeLexemas +"%"+TokenAnalizar[i]+"%"+(i)+"%"+"Fila 1 Columna "+(i)+"%";
								ContadordeLexemas++;
								LexemasdeCodigo = LexemasdeCodigo+ContadordeLexemas +"%"+TokenAnalizar[i+1]+"%"+(i+1)+"%"+"Fila 1 Columna "+(i+1)+"%";
								ContadordeLexemas++;
								LexemasdeCodigo = LexemasdeCodigo+ContadordeLexemas +"%"+TokenAnalizar[i+2]+"%"+(i+2)+"%"+"Fila 1 Columna "+(i+2)+"%";
								Correcto(TokenAnalizar[i+1],"sexo");
								i = i + 2;
							}
					   }
					}
					
					
				}
			}
			
		}
		if( Error == true){
			ar.GenerarHTMLErrores(ErroresdeCodigo);
		}
		if( ErrorEncontrado == false ){
			ar.GenerarHTMLLexemas(LexemasdeCodigo);
		}
	
	}
	
	/**
	 * booleano que verificara la existencia de las etiquetas para abrir.
	 * @param Dato recibe la etiqueta
	 * @return retornara 'true' si la etiqueta existe de no existir retornara 'false'
	 */
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
	
	/**
	 * 
	 * @param Dato
	 * @param Etiqueta
	 * @return
	 */
	boolean PalabraReservada(String Dato, String Etiqueta){
		boolean correcto = false;
		if(Etiqueta.equals("")){
			correcto = false;
		}
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
	
	boolean esnumero(String dato){
        try{
            Integer.parseInt(dato);
        }catch(NumberFormatException nfe){
            return false;
        }
        return true;
    }
	
	void Correcto(String Dato, String Etiqueta){
		if(Etiqueta.equals("complexion")){
			
		}else if(Etiqueta.equals("personalidad")){
			if(Dato.equals("enojado")){
				lblImagen.setBackground(Color.RED);
			}else
			
			if(Dato.equals("alegre")){
				lblImagen.setBackground(Color.YELLOW);
			}else
			
			if(Dato.equals("neutro")){
				lblImagen.setBackground(Color.GRAY);
			}
		}else if(Etiqueta.equals("sexo")){
			if(Dato.equals("masculino")){
				lblImagen.setIcon(avatarH);
				lblImagen.setForeground(Color.BLUE);
				
			}else
			
			if(Dato.equals("femenino")){
				lblImagen.setIcon(avatarM);
				lblImagen.setForeground(Color.PINK);
				
			}
		}
	}
	
	void ObtenerRutaDeArchivo(){
		try{
			String ruta, datos;
			JFileChooser archivo = new JFileChooser("C:\\Users\\Edu\\Desktop\\");
			FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos AVA & LFP", "ava", "lfp");
			archivo.setFileFilter(filtro);
			archivo.showOpenDialog(archivo);
			ruta = archivo.getSelectedFile().getAbsolutePath();
			RutadeArchivo = ruta;
			datos = ar.AbrirARCHIVO(ruta);
			textArea.setText(datos);
			btnGuardarc.setEnabled(true);
			ArchivoAbierto = true;
		}catch(Exception ex){
			ArchivoAbierto = false;
			JOptionPane.showMessageDialog(null,"No seleccionaste ningun archivo","Advertencia", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == btnAbrir){
			ObtenerRutaDeArchivo();
		}
		
		if(e.getSource() == mntmInformacion){
			JOptionPane.showMessageDialog(null, "Realizado por: Eduardo Antonio Garcia Franco"
					+ "\nCarnet: 2012-12961"
					+ "\nCurso: Lenguajes Formales y de Programacion"
					+ "\nVacaciones de Junio 2015","Acerda de..",JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(e.getSource() == mntmManualu){
			try{
				File musuario = new File("Usuario.pdf");
				Desktop des = Desktop.getDesktop();
				if(musuario.exists()){
					des.open(musuario);
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, "Error en abrir","Advertencia",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		if(e.getSource() == mntmManualt){
			try{
				File mtecnico = new File("Tecnico.pdf");
				Desktop des = Desktop.getDesktop();
				if(mtecnico.exists()){
					des.open(mtecnico);
				}
			}catch(Exception ex){
				JOptionPane.showMessageDialog(null, "Error en abrir","Advertencia",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		if(e.getSource() == btnGuardar){
			if(textArea.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Aun no has ingresado ninguna linea de codigo","Advertencia",JOptionPane.INFORMATION_MESSAGE);
			}else{
				String texto = textArea.getText();
				ar.Guardar(texto, RutadeArchivo, ArchivoAbierto);
				ArchivoAbierto = false;
			}
		}
		
		if(e.getSource() == btnGuardarc){
			if(textArea.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Aun no has seleccionado un archivo","Advertencia",JOptionPane.INFORMATION_MESSAGE);
			}else{
				String datos = textArea.getText();
				ar.GuardarComo(datos);
			}
		}
		
		if(e.getSource() == btnLimpiar){
			textArea.setText("");
			lblImagen.setText("");
			lblImagen.setBackground(null);
			lblImagen.setForeground(null);
			lblImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
