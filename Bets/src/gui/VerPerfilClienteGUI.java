package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Software Engineering teachers
 */
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import domain.Cliente;

import javax.swing.JTextField;
import java.awt.Font;


public class VerPerfilClienteGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	
	private Cliente cl;

    private static BLFacade appFacadeInterface;
    private JTextField textFieldNombre;
    private JTextField textFieldUsername;
    private JTextField textFieldEmail;
    private JTextField textFieldMonto;
    private JTextField textFieldTarjeta;
    private JTextField textFieldCC;
    JButton btnModificar = new JButton("Modificar");
	JButton btnConfirmar = new JButton("Guardar");
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	
	/**
	 * This is the default constructor
	 */
	public VerPerfilClienteGUI(Cliente pcl) {
		super();
		this.cl=pcl;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Error: "+e1.toString()+" , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});

		initialize();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setSize(271, 295);
		this.setSize(506, 374);
		this.setContentPane(getJContentPane());
		this.setTitle("Ver perfil");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			
			JButton atras = new JButton("Cancelar");
			atras.setBounds(6, 290, 129, 29);
			jContentPane.add(atras);
			atras.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					close1();
					}
				}
			);
			

			//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setUndecorated(true);
			getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			

			BLFacade facade=MainGUI.getBusinessLogic();
			
			Cliente clientedb = facade.getClientByUsername(cl.getUsername());
			
			//Estado inicial de los botones sobrepuestos
			btnModificar.setVisible(true);
			btnConfirmar.setVisible(false);
			
			
			
			btnConfirmar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if (facade.confirmarEditarPerfilCliente(textFieldNombre.getText(), textFieldUsername.getText(), textFieldEmail.getText(), textFieldTarjeta.getText(), textFieldCC.getText())) {
						textFieldNombre.setEditable(false);
						textFieldEmail.setEditable(false);
						textFieldTarjeta.setEditable(false);
						textFieldCC.setEditable(false);
						btnModificar.setVisible(true);
						btnConfirmar.setVisible(false);
					} else {
						JOptionPane.showMessageDialog(null, "Error de guardado.");
					}
				}
			});
			btnConfirmar.setBounds(360, 290, 129, 29);
			jContentPane.add(btnConfirmar);
			
			
			btnModificar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					textFieldNombre.setEditable(true);
					textFieldEmail.setEditable(true);
					textFieldTarjeta.setEditable(true);
					textFieldCC.setEditable(true);
					btnModificar.setVisible(false);
					btnConfirmar.setVisible(true);
				}
			});
			btnModificar.setBounds(360, 290, 129, 29);
			jContentPane.add(btnModificar);
			
			JLabel lblNombre = new JLabel("Nombre: ");
			lblNombre.setBounds(21, 100, 178, 16);
			jContentPane.add(lblNombre);
			
			JLabel lblUsername = new JLabel("Username: ");
			lblUsername.setBounds(21, 68, 178, 16);
			jContentPane.add(lblUsername);
			
			JLabel lblEmail = new JLabel("Email: ");
			lblEmail.setBounds(20, 134, 178, 16);
			jContentPane.add(lblEmail);
			
			JLabel lblTarjeta = new JLabel("Nº Tarjeta: ");
			lblTarjeta.setBounds(21, 168, 129, 16);
			jContentPane.add(lblTarjeta);
			
			JLabel lblCC = new JLabel("Cuenta Corriente: ");
			lblCC.setBounds(21, 204, 178, 16);
			jContentPane.add(lblCC);
			
			JLabel lblMonto = new JLabel("Saldo: ");
			lblMonto.setBounds(21, 239, 178, 16);
			jContentPane.add(lblMonto);
			
			textFieldNombre = new JTextField();
			textFieldNombre.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldNombre.setBounds(210, 97, 278, 26);
			jContentPane.add(textFieldNombre);
			textFieldNombre.setColumns(10);
			textFieldNombre.setEditable(false);
			textFieldNombre.setText(clientedb.getName());
			
			textFieldUsername = new JTextField();
			textFieldUsername.setText((String) null);
			textFieldUsername.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldUsername.setColumns(10);
			textFieldUsername.setBounds(211, 63, 278, 26);
			jContentPane.add(textFieldUsername);
			textFieldUsername.setEditable(false);
			textFieldUsername.setText(clientedb.getUsername());
			
			textFieldEmail = new JTextField();
			textFieldEmail.setText((String) null);
			textFieldEmail.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldEmail.setColumns(10);
			textFieldEmail.setBounds(210, 129, 278, 26);
			jContentPane.add(textFieldEmail);
			textFieldEmail.setText(clientedb.getEmail());
			textFieldEmail.setEditable(false);
			
			textFieldTarjeta= new JTextField();
			textFieldTarjeta.setText((String) null);
			textFieldTarjeta.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldTarjeta.setColumns(10);
			textFieldTarjeta.setBounds(211, 163, 278, 26);
			jContentPane.add(textFieldTarjeta);
			textFieldTarjeta.setEditable(false);
			textFieldTarjeta.setText(clientedb.getTarjeta());
			
			textFieldCC = new JTextField();
			textFieldCC.setText((String) null);
			textFieldCC.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldCC.setColumns(10);
			textFieldCC.setBounds(211, 199, 278, 26);
			jContentPane.add(textFieldCC);
			textFieldCC.setEditable(false);
			textFieldCC.setText(clientedb.getCC());

			textFieldMonto = new JTextField();
			textFieldMonto.setText((String) null);
			textFieldMonto.setHorizontalAlignment(SwingConstants.CENTER);
			textFieldMonto.setColumns(10);
			textFieldMonto.setBounds(211, 234, 278, 26);
			jContentPane.add(textFieldMonto);
			textFieldMonto.setEditable(false);
			textFieldMonto.setText(clientedb.getSaldo()+" €");
			
			JLabel lblVerPerfil = new JLabel("Ver/editar perfil");
			lblVerPerfil.setHorizontalAlignment(SwingConstants.CENTER);
			lblVerPerfil.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
			lblVerPerfil.setBounds(130, 6, 249, 26);
			jContentPane.add(lblVerPerfil);
			
		}

		return jContentPane;
	}

	private void close1(){
		this.dispose();
	}	
} // @jve:decl-index=0:visual-constraint="0,0"