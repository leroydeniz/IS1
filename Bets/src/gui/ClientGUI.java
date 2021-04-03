package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Apuesta;
import domain.Cliente;
import domain.Event;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class ClientGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	
	private Cliente cli=null;
	
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneBets = new JScrollPane();
	private JTable tableEvents = new JTable();
	private JTable tableBets = new JTable();
	
	private String[] columnNamesEvents = new String[] {"Fecha", "Nombre", "Deporte", };
	private String[] columnNamesBets = new String[] {"Evento", "Pregunta", "Respuesta","€", };
	
	private DefaultTableModel tableModelEvents = new DefaultTableModel(null, columnNamesEvents);
	private DefaultTableModel tableModelBets = new DefaultTableModel(null, columnNamesBets);
	
	
	public ClientGUI(Cliente user, JPanel jContentPane2) {
		super();
		cli = user;
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

		
		
		JPanel jContentPane = new JPanel();
		jContentPane.setLayout(null);
		JPanel panel = new JPanel();
		panel.setBounds(6, 50, 788, 422);
		panel.setLayout(null);
		jContentPane.add(panel);
		
		scrollPaneEvents.setBounds(new Rectangle(6, 82, 287, 334));
		
		//tableEvents.setBounds(20, 82, 426, 307);

		
		tableModelEvents.setDataVector(null, columnNamesEvents);
		tableModelEvents.setColumnCount(4); // another column added to allocate ev objects
		
		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);
		tableEvents.setModel(tableModelEvents);
		BLFacade facade=MainGUI.getBusinessLogic();
		
		
		String strDateFormat = "dd-MM-yyyy";
		SimpleDateFormat fecha = new SimpleDateFormat(strDateFormat);
		Vector<Event> events = facade.getNextEvents();
		for (Event ev:events){
			Vector<Object> row = new Vector<Object>();

			System.out.println("Events "+ev);

			row.add(fecha.format(ev.getEventDate()));
			row.add(ev.getDescription());
			row.add(ev.getTipoDeporte());
			row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
			tableModelEvents.addRow(row);		
		}
		
		//tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(3));
		panel.add(scrollPaneEvents);
		
		JLabel lblEventos = new JLabel("Proximos eventos");
		lblEventos.setBounds(104, 57, 117, 14);
		panel.add(lblEventos);
		
		scrollPaneBets.setBounds(new Rectangle(305, 82, 477, 334));
		//tableBets.setBounds(467, 82, 311, 307);
		
		tableModelBets.setDataVector(null, columnNamesBets);
		tableModelBets.setColumnCount(5); // another column added to allocate ev objects
		
		scrollPaneBets.setViewportView(tableBets);
		tableModelBets = new DefaultTableModel(null, columnNamesBets);
		tableBets.setModel(tableModelBets);
		
		ArrayList <Apuesta> apuestas_by_client= facade.BetsByClient(cli);
		
		for (Apuesta c : apuestas_by_client) {
			if(c.getEstado()==0) {
				Vector<Object> row = new Vector<Object>();
				row.add(c.getAnswer().getQuestion().getEvent().getDescription());
				row.add(c.getAnswer().getQuestion());
				row.add(c.getAnswer());
				row.add(c.getAmount());
				row.add(c);
				tableModelBets.addRow(row);
			}
		}
		tableBets.getColumnModel().getColumn(3).setPreferredWidth(10);
		
		panel.add(scrollPaneBets);
		
		JLabel lblApuestasEnCurso = new JLabel("Apuestas en curso");
		lblApuestasEnCurso.setBounds(501, 57, 150, 14);
		panel.add(lblApuestasEnCurso);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
		JLabel labelEventos = new JLabel("Vista Cliente");
		labelEventos.setHorizontalAlignment(SwingConstants.CENTER);
		labelEventos.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		labelEventos.setBounds(350, 22, 109, 16);
		jContentPane.add(labelEventos);
		this.setContentPane(jContentPane);
		this.setTitle("Vista cliente");
		this.setSize(800, 500);
		

		/**************************************** INICIO MENU ****************************************/
		
		//MENU
		JMenuBar barraMenu	= new JMenuBar();
		
		//ELEMENTOS DEL MENU
		JMenu apuestas = new JMenu("Apuestas");
		JMenu perfil	= new JMenu("Perfil");
		barraMenu.add(apuestas);
		barraMenu.add(perfil);

		//SUBELEMENTOS DE APUESTAS
		JMenuItem Apostar = new JMenuItem("Apostar");
		Apostar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new ApostarGUI(new Vector<Event>(), cli, null);
				a.setLocationRelativeTo(null);
				a.setVisible(true);
			}
		});
		JMenuItem Historial = new JMenuItem("Historial");
		Historial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new UserBetsHistoryGUI(cli);
				a.setLocationRelativeTo(null);
				a.setVisible(true);
			}
		});
		JMenuItem AnularApuesta = new JMenuItem("Anular");
		AnularApuesta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new AnularApuestaGUI(cli);
				a.setLocationRelativeTo(null);
				a.setVisible(true);
			}
		});
		JMenuItem Salir = new JMenuItem("Salir");
		Salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jContentPane2.setVisible(true);
				close1();
			}
		});
		apuestas.add(Apostar);
		apuestas.add(Historial);
		apuestas.add(AnularApuesta);
		apuestas.add(new JSeparator());
		apuestas.add(Salir);

		JMenuItem EditarPerfil = new JMenuItem("Ver/Editar");
		EditarPerfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new VerPerfilClienteGUI(cli);
				a.setLocationRelativeTo(null);
				a.setVisible(true);
			}
		});
		JMenuItem CargarMonedero = new JMenuItem("Cargar monedero");
		CargarMonedero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CargarMonederoGUI(cli);
				a.setLocationRelativeTo(null);
				a.setVisible(true);
			}
		});

		perfil.add(EditarPerfil);
		perfil.add(CargarMonedero);

		//INDICAMOS MENÚ POR DEFECTO
		setJMenuBar(barraMenu);
		
		
		
		/**************************************** FIN MENU ****************************************/
		
		/**************************************** INICIO DASHBOARD ****************************************/
		
		//
		
		/**************************************** FIN DASHBOARD ****************************************/
		
		
		
		
		
	}



	
	
	
	private void close1(){
		this.dispose();
	}
} // @jve:decl-index=0:visual-constraint="0,0"