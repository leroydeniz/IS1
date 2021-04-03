package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import domain.Deporte;
import java.awt.Font;
import java.awt.TextArea;

import javax.swing.UIManager;

public class CrearDeporteGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private String texto="";
	private TextArea textArea = new TextArea();

	private JTextArea result = new JTextArea();

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	
	/**
	 * This is the default constructor
	 */
	public CrearDeporteGUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Error: "+e1.toString()+" , probably problems with Business Logic or Database");
				}
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});

		initialize();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initialize() {
		// this.setSize(271, 295);
		this.setSize(550, 299);
		this.setContentPane(getJContentPane());
		this.setTitle("Crear deporte");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			
			JLabel lblNombre = new JLabel("Nombre del nuevo deporte:");
			lblNombre.setBounds(70, 82, 200, 14);
			jContentPane.add(lblNombre);
			

			JTextField textFieldNombre = new JTextField();
			textFieldNombre.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldNombre.setBounds(60, 108, 170, 26);
			jContentPane.add(textFieldNombre);
			textFieldNombre.setColumns(18);
			textFieldNombre.setEditable(true);
			
			
			JButton close = new JButton("Cancelar");
			JButton aceptar = new JButton("Aceptar");
			aceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					String texto=textFieldNombre.getText();
			        texto=texto.replaceAll(" ", "");
					
					if (texto.length()==0) {
						JOptionPane.showMessageDialog(null, "Nombre del deporte no puede ser vacío");
					} else {
						BLFacade facade=MainGUI.getBusinessLogic();
						int insertado = facade.addSport(textFieldNombre.getText());
						switch(insertado) {
							case 0: {
								result.setText("EL DEPORTE YA EXISTE Y ESTABA ACTIVO");
								break;
							}
							case 1: {
								result.setText("EL DEPORTE YA EXISTÍA Y ACABA DE ACTIVARSE");
								break;
							}
							case 2: {
								result.setText("ERROR AL INGRESAR DEPORTE");
								break;
							}
							case 3: {
								result.setText("DEPORTE INGRESADO CORRECTAMENTE");
								break;
							}
						}
						textFieldNombre.setText("");
						updateDeportesTextArea();
					}
					
				}
			});
			close.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					close1();
				}
			});
			close.setBounds(6, 227, 117, 29);
			aceptar.setBounds(178, 227, 117, 29);
			jContentPane.add(close);
			jContentPane.add(aceptar);
			
			
			textArea.setEditable(false);
			textArea.setBounds(307, 59, 237, 197);
			updateDeportesTextArea();
			jContentPane.add(textArea);
			
			JLabel lblCrearDeporte = new JLabel("Crear deporte");
			lblCrearDeporte.setHorizontalAlignment(SwingConstants.CENTER);
			lblCrearDeporte.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
			lblCrearDeporte.setBounds(153, 6, 249, 26);
			jContentPane.add(lblCrearDeporte);
			result.setLineWrap(true);
			result.setWrapStyleWord(true);
			result.setEditable(false);
			
			result.setBackground(UIManager.getColor("Button.background"));
			result.setBounds(60, 137, 170, 63);
			jContentPane.add(result);
		}
		return jContentPane;
	}
	
	
	public void updateDeportesTextArea() {
		BLFacade facade=MainGUI.getBusinessLogic();
		ArrayList <Deporte> deportes= new ArrayList<Deporte>(facade.getActiveSports());
		texto="";
			for (Deporte c : deportes) {
				
				texto=texto+c.getId()+" \t "+c.getNombre()+"\n";
				
			}
			textArea.setText(texto);
		
	}

	private void close1(){
		this.dispose();
	}	
}
