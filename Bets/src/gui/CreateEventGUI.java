package gui;

import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Deporte;
import domain.Event;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.Font;

public class CreateEventGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarMio = null;
	private final JLabel lblEligeDia = new JLabel("Elige dia:");
	private final JLabel lblDescripcionDeEvento = new JLabel("Nombre de evento:");
	private final JTextField textFieldDescripcion = new JTextField();
	private final JButton btnCrearEvento = new JButton("Crear evento");
	private Date firstDay;
	private JComboBox<Deporte> jComboBoxDeportes = new JComboBox<Deporte>();
	DefaultComboBoxModel<Deporte> modelDeportes = new DefaultComboBoxModel<Deporte>();
	private final JLabel lblNewLabel = new JLabel("Evento creado con éxito");
	private final JLabel lblCrearEvento = new JLabel("Crear evento");


	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public CreateEventGUI() {
		this.setTitle("Añadir evento");
		textFieldDescripcion.setBounds(275, 75, 315, 20);
		textFieldDescripcion.setColumns(10);
		setBounds(100, 100, 627, 305);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);

	
		this.setTitle("Crear Evento");


		
		jCalendar.setBounds(new Rectangle(40, 80, 225, 150));
		contentPane.add(jCalendar);
		lblEligeDia.setBounds(40, 54, 78, 14);

		contentPane.add(lblEligeDia);
		lblDescripcionDeEvento.setBounds(275, 50, 193, 14);

		contentPane.add(lblDescripcionDeEvento);

		contentPane.add(textFieldDescripcion);
		btnCrearEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				//TODO Hay que cambiar esto ya que no deberiamos crearlo de esta forma
				BLFacade facade = MainGUI.getBusinessLogic();
				
				firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
				
				System.out.println(firstDay);
				Event NewEv = new Event(textFieldDescripcion.getText(), firstDay, (Deporte)jComboBoxDeportes.getSelectedItem());
				boolean saveEvent = facade.insertEvent(NewEv);
				
				if(saveEvent) {
					lblNewLabel.setText("Evento ingresado.");
					lblNewLabel.setVisible(true);
					textFieldDescripcion.setText("");
				} else {
					lblNewLabel.setText("Error al ingresar evento.");
					lblNewLabel.setVisible(true);
				}
			}
		});
		btnCrearEvento.setBounds(415, 241, 193, 36);

		lblNewLabel.setVisible(false);
		
		contentPane.add(btnCrearEvento);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				contentPane.setVisible(false);
				close1();
			}
		});
		btnCerrar.setBounds(20, 241, 193, 36);
		contentPane.add(btnCerrar);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(0, 128, 0));
		lblNewLabel.setBounds(427, 222, 173, 16);
		
		contentPane.add(lblNewLabel);
		
		JLabel lblTipoDeDeporte = new JLabel("Tipo de deporte:");
		lblTipoDeDeporte.setBounds(277, 120, 193, 14);
		contentPane.add(lblTipoDeDeporte);
		
		jComboBoxDeportes.setBounds(277, 150, 313, 27);
		contentPane.add(jComboBoxDeportes);
		jComboBoxDeportes.removeAllItems();
		try {
			BLFacade facade=MainGUI.getBusinessLogic();
			ArrayList <Deporte> deportes= new ArrayList<Deporte>(facade.getActiveSports());
				for (Deporte c : deportes) {
					modelDeportes.addElement(c);
				}
				jComboBoxDeportes.setModel(modelDeportes);
				lblCrearEvento.setHorizontalAlignment(SwingConstants.CENTER);
				lblCrearEvento.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
				lblCrearEvento.setBounds(190, 6, 249, 26);
				
				contentPane.add(lblCrearEvento);
				jComboBoxDeportes.repaint();
		} catch(Exception ex) {
			lblNewLabel.setText("Exception >> CreateEventGUI: "+ex.getMessage());
			lblNewLabel.setForeground(new Color(128,0,0));
		}
		
		


		jCalendar.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				// TODO Auto-generated method stub
				if (evt.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) evt.getNewValue());
				} else if (evt.getPropertyName().equals("calendar")) {
					calendarMio = (Calendar) evt.getNewValue();
					jCalendar.setCalendar(calendarMio);
					firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));

					System.out.println(firstDay);


				}
			}
		});

	}

	private void close1(){
		this.dispose();
	}
}
