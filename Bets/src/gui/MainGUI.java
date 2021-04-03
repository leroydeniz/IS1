package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Admin;
import domain.Cliente;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null; 

    private static BLFacade appFacadeInterface;
    private JTextField loginUsername;
    private JTextField registerUsername;
    private JTextField registerEmail;
    private JPasswordField registerPassword1;
    private JPasswordField registerPassword2;
    private JPasswordField loginPassword;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	
	/**
	 * This is the default constructor
	 */
	public MainGUI() {
		super();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
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
		this.setSize(700, 450);
		this.setContentPane(getJContentPane());
		this.setTitle("Login");

		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
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
			
			JLabel lblNewLabel = new JLabel("Bets & Ruins"); //$NON-NLS-1$ //$NON-NLS-2$
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Lucida Sans", Font.PLAIN, 20));
			lblNewLabel.setBounds(226, 26, 250, 42);
			jContentPane.add(lblNewLabel);
			
			JSeparator separator = new JSeparator();
			separator.setForeground(Color.BLACK);
			separator.setBounds(164, 70, 377, 12);
			jContentPane.add(separator);
			
			JLabel lblNewLabel_1 = new JLabel("User: "); //$NON-NLS-1$ //$NON-NLS-2$
			lblNewLabel_1.setBounds(73, 141, 61, 16);
			jContentPane.add(lblNewLabel_1);
			
			JLabel lblPass = new JLabel("Pass: "); //$NON-NLS-1$ //$NON-NLS-2$
			lblPass.setBounds(73, 183, 61, 16);
			jContentPane.add(lblPass);
			
			JSeparator separator_1 = new JSeparator();
			separator_1.setOrientation(SwingConstants.VERTICAL);
			separator_1.setForeground(Color.BLACK);
			separator_1.setBounds(336, 109, 12, 273);
			jContentPane.add(separator_1);
			
			loginUsername = new JTextField();
			loginUsername.setBounds(144, 136, 130, 26);
			jContentPane.add(loginUsername);
			loginUsername.setColumns(10);
			
			JLabel lblNewLabel_2 = new JLabel("Log in"); //$NON-NLS-1$ //$NON-NLS-2$
			lblNewLabel_2.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel_2.setBounds(134, 94, 131, 32);
			jContentPane.add(lblNewLabel_2);
			
			JLabel label = new JLabel("User:");
			label.setBounds(377, 141, 61, 16);
			jContentPane.add(label);
			
			JLabel lblPass_1 = new JLabel("Pass: "); //$NON-NLS-1$ //$NON-NLS-2$
			lblPass_1.setBounds(377, 183, 61, 16);
			jContentPane.add(lblPass_1);
			
			JLabel lblRepass = new JLabel("Re-Pass: ");
			lblRepass.setBounds(379, 222, 61, 16);
			jContentPane.add(lblRepass);
			
			JLabel lblEmail = new JLabel("Email: ");
			lblEmail.setBounds(377, 261, 61, 16);
			jContentPane.add(lblEmail);
			
			registerUsername = new JTextField();
			registerUsername.setColumns(10);
			registerUsername.setBounds(450, 136, 130, 26);
			jContentPane.add(registerUsername);
			
			registerEmail = new JTextField();
			registerEmail.setColumns(10);
			registerEmail.setBounds(450, 256, 130, 26);
			jContentPane.add(registerEmail);
			
			JLabel lblRegister = new JLabel("Registro"); //$NON-NLS-1$ //$NON-NLS-2$
			lblRegister.setHorizontalAlignment(SwingConstants.CENTER);
			lblRegister.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
			lblRegister.setBounds(424, 94, 131, 32);
			jContentPane.add(lblRegister);
			
			registerPassword1 = new JPasswordField();
			registerPassword1.setBounds(450, 178, 130, 26);
			jContentPane.add(registerPassword1);
			
			registerPassword2 = new JPasswordField();
			registerPassword2.setBounds(450, 217, 130, 26);
			jContentPane.add(registerPassword2);
			
			loginPassword = new JPasswordField();
			loginPassword.setBounds(144, 178, 130, 26);
			jContentPane.add(loginPassword);
			
			JButton btnLogin = new JButton("Acceder"); //$NON-NLS-1$ //$NON-NLS-2$
			btnLogin.setBounds(143, 216, 131, 42);

			btnLogin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				
					System.out.println("Evento producido sobre componente: " +e.getActionCommand());
					String us=loginUsername.getText();
					String ps= new String(loginPassword.getPassword());
					//JOptionPane.showMessageDialog(null, us+" - "+ps);
					boolean b=appFacadeInterface.doLogin(us, ps);
					if (b) {
						
						//Check user permissions
						if (appFacadeInterface.isAdmin(us, ps)) {

							loginUsername.setText("");
							loginPassword.setText("");
							registerUsername.setText("");
							registerPassword1.setText("");
							registerPassword2.setText("");
							registerEmail.setText("");
							Admin user = appFacadeInterface.getAdminByUsername(us);
							JFrame a = new AdminGUI(user, jContentPane);
							a.setLocationRelativeTo(null);
							a.setVisible(true);
							jContentPane.setVisible(false);
										
						} else {
							
							Cliente user = appFacadeInterface.getClientByUsername(us);
							JFrame a = new ClientGUI(user, jContentPane);
							a.setLocationRelativeTo(null);
							a.setVisible(true);
							jContentPane.setVisible(false);
							
						}
					} else {
						JOptionPane.showMessageDialog(null, "Login and password does not match.", "LOGIN INFO",JOptionPane.ERROR_MESSAGE, null);
					}
				}
			});
			jContentPane.add(btnLogin);
			
			JButton btnRegistrarse = new JButton("Registrarse"); //$NON-NLS-1$ //$NON-NLS-2$
			btnRegistrarse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					System.out.println("Evento producido sobre componente: " +e.getActionCommand());
					String us=registerUsername.getText();
					String ps= new String(registerPassword1.getPassword());
					String ps2= new String(registerPassword2.getPassword());
					String em=registerEmail.getText();
					//JOptionPane.showMessageDialog(null, us+" - "+ps);
					
					//VERIFICO QUE PASSWORDS COINCIDEN
					if (verificarPasswords(ps, ps2)) {
						
						//VERIFICO EL EMAIL CON EXPRESIÓN REGULAR
						if (verificarEmail(em)) {
							boolean b=appFacadeInterface.doRegister(us, ps, em);
							
							//doRegister DEVUELVE FALSE SI YA ESTÁ EXISTE
							if (b) {
								JOptionPane.showMessageDialog(null, "Register as client", "REGISTER INFO",JOptionPane.INFORMATION_MESSAGE, null);
								loginUsername.setText("");
								loginPassword.setText("");
								registerUsername.setText("");
								registerPassword1.setText("");
								registerPassword2.setText("");
								registerEmail.setText("");
							} else {
								JOptionPane.showMessageDialog(null, "Username/email already exists.", "REGISTER INFO",JOptionPane.ERROR_MESSAGE, null);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Wrong email address.", "REGISTER INFO",JOptionPane.ERROR_MESSAGE, null);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Passwords does not match.", "REGISTER INFO",JOptionPane.ERROR_MESSAGE, null);
					}
				}
			});
			btnRegistrarse.setBounds(449, 294, 131, 42);
			jContentPane.add(btnRegistrarse);
			
			JSeparator separator_2 = new JSeparator();
			separator_2.setForeground(Color.BLACK);
			separator_2.setBounds(108, 270, 214, 12);
			jContentPane.add(separator_2);
			
			JLabel lblInvitado = new JLabel("Invitado");
			lblInvitado.setHorizontalAlignment(SwingConstants.CENTER);
			lblInvitado.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
			lblInvitado.setBounds(144, 281, 131, 32);
			jContentPane.add(lblInvitado);
			
			JButton buttonInvitado = new JButton("Acceder");
			buttonInvitado.setBounds(144, 325, 131, 42);
			buttonInvitado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new GuestGUI(jContentPane);
					a.setLocationRelativeTo(null);
					a.setVisible(true);
					jContentPane.setVisible(false);
				}
			});
			
			jContentPane.add(buttonInvitado);
			//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setUndecorated(true);
			getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			
			JButton button = new JButton("Salir");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					close1();
				}
			});
			button.setBounds(563, 402, 131, 42);
			jContentPane.add(button);
			
			
		}
		return jContentPane;
	}
	
	
	public boolean verificarPasswords(String ppass1, String ppass2) {
		return ppass1.equals(ppass2);
	}
	

	private void close1(){
		System.exit(0);
	}
	
	
	public boolean verificarEmail(String pemail) {
        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(pemail);
        if (mather.find() == true) {
            return true;
        } else {
            return false;
        }
        
	}
} // @jve:decl-index=0:visual-constraint="0,0"