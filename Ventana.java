import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
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
	JButton btnAbrir, btnGuardar, btnGuardarc;
	JTextArea textArea;
	JMenuItem mntmInformacion;
	JLabel lblImagen;
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
		setBounds(100, 100, 725, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnAbrir = new JButton("Abrir");
		btnAbrir.addActionListener(this);
		btnAbrir.setBounds(54, 334, 89, 23);
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
		
		lblImagen = new JLabel("Pedro Pomez, 18", JLabel.LEFT);
		lblImagen.setOpaque(true);
		lblImagen.setBorder(BorderFactory.createLineBorder(Color.RED, 6));
		lblImagen.setIcon(avatarM);
		lblImagen.setBounds(343, 32, 288, 255);
		contentPane.add(lblImagen);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(this);
		btnGuardar.setBounds(182, 334, 89, 23);
		contentPane.add(btnGuardar);
		
		btnGuardarc = new JButton("Guardar Como..");
		btnGuardarc.setEnabled(false);
		btnGuardarc.addActionListener(this);
		btnGuardarc.setBounds(311, 334, 125, 23);
		contentPane.add(btnGuardarc);
	}
	
	void ObtenerRutaDeArchivo(){
		try{
			String ruta, datos;
			JFileChooser archivo = new JFileChooser();
			FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos AVA", "ava", "Archivos LFP", "lfp");
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
			
		}
	}
}
