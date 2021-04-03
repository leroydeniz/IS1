package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;
import domain.Question;
import domain.Answer;
import domain.Event;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

//import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;


public class GuestGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 

	private JButton jButtonClose = new JButton("Cerrar");

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarMio = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();

	private JTable tableEvents= new JTable();
	private JTable tableQueries = new JTable();

	private float apuesta;
	
	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;

	
	private String[] columnNamesEvents = new String[] {"#", "Nombre", "Deporte", };
	private String[] columnNamesQueries = new String[] {"#","Pregunta", "Ejemplo de apuesta", "Ganancia Max"}; //Apuesta de 10€ de ejemplo

	public GuestGUI(JPanel jContentPane2)
	{
		try
		{
			this.getContentPane().setLayout(null);
			this.setSize(new Dimension(800, 500));
			this.setTitle("Vista Invitado");
			//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setUndecorated(true);
			getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			
			//MENU
			JMenuBar barraMenu	= new JMenuBar();
			JMenu invitado = new JMenu("Archivo");
			barraMenu.add(invitado);
			
			JMenuItem Salir = new JMenuItem("Salir");
			Salir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jContentPane2.setVisible(true);
					close1();
				}
			});
			invitado.add(Salir);

			setJMenuBar(barraMenu);

			JLabel GuestViewLabel = new JLabel("Vista Invitado");
			GuestViewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			GuestViewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
			GuestViewLabel.setBounds(350, 22, 109, 16);
			getContentPane().add(GuestViewLabel);
			
			jLabelEventDate.setBounds(new Rectangle(82, 63, 140, 25));
			jLabelEvents.setBounds(319, 67, 259, 16);

			this.getContentPane().add(jLabelEventDate, null);
			this.getContentPane().add(jLabelEvents);

			jButtonClose.setBounds(new Rectangle(343, 413, 155, 37));

			jButtonClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jContentPane2.setVisible(true);
					jButton2_actionPerformed(e);
				}
			});

			this.getContentPane().add(jButtonClose, null);


			jCalendar1.setBounds(new Rectangle(82, 95, 225, 150));


			// Code for JCalendar
			this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
			{
				public void propertyChange(PropertyChangeEvent propertychangeevent)
				{

					if (propertychangeevent.getPropertyName().equals("locale"))
					{
						jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
					}
					else if (propertychangeevent.getPropertyName().equals("calendar"))
					{
						calendarMio = (Calendar) propertychangeevent.getNewValue();
						DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
						jCalendar1.setCalendar(calendarMio);
						Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));



						try {
							tableModelEvents.setDataVector(null, columnNamesEvents);
							tableModelEvents.setColumnCount(4); // another column added to allocate ev objects

							BLFacade facade=MainGUI.getBusinessLogic();

							Vector<Event> events=facade.getOpenEvents(firstDay);

							if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarMio.getTime()));
							else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarMio.getTime()));
							for (Event ev:events){
								Vector<Object> row = new Vector<Object>();

								System.out.println("Events "+ev);

								row.add(ev.getEventNumber());
								row.add(ev.getDescription());
								row.add(ev.getTipoDeporte());
								row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
								tableModelEvents.addRow(row);		
							}
							tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
							tableEvents.getColumnModel().getColumn(1).setPreferredWidth(180);
							tableEvents.getColumnModel().getColumn(2).setPreferredWidth(84);
							tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(3)); // not shown in JTable
						} catch (Exception e1) {

							JOptionPane.showMessageDialog(null,e1.getMessage());
						}

					}
					CreateQuestionGUI.paintDaysWithEvents(jCalendar1);
				} 
			});

			this.getContentPane().add(jCalendar1, null);
			
			scrollPaneEvents.setBounds(new Rectangle(319, 95, 392, 150));
			scrollPaneQueries.setBounds(new Rectangle(82, 257, 629, 150));

			tableEvents.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int i=tableEvents.getSelectedRow();
					Event ev=(Event)tableModelEvents.getValueAt(i,3); // obtain ev object
					Vector<Question> queries=ev.getQuestions();

					tableModelQueries.setDataVector(null, columnNamesQueries);
					float coefMax=(float)1.0;
					for (Question q:queries){

						coefMax=(float)1.0;
						Vector<Object> row = new Vector<Object>();

						row.add(q.getQuestionNumber());
						row.add(q.getQuestion());
						for (Answer ans: q.getAnswers()) {
							if (coefMax<ans.getCoeficienteGanancia()) {
								coefMax = ans.getCoeficienteGanancia();
							}
						}
						apuesta=(float) Math.random()*100;
						row.add(String.format("%.2f", apuesta));
						row.add(String.format("%.2f", apuesta*coefMax));
						tableModelQueries.addRow(row);	
					}
					tableQueries.getColumnModel().getColumn(0).setPreferredWidth(10);
					tableQueries.getColumnModel().getColumn(1).setPreferredWidth(195);
					tableQueries.getColumnModel().getColumn(2).setPreferredWidth(54);
					tableQueries.getColumnModel().getColumn(3).setPreferredWidth(34);
				}
			});

			scrollPaneEvents.setViewportView(tableEvents);
			tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

			tableEvents.setModel(tableModelEvents);
			tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
			tableEvents.getColumnModel().getColumn(1).setPreferredWidth(180);
			tableEvents.getColumnModel().getColumn(2).setPreferredWidth(84);


			scrollPaneQueries.setViewportView(tableQueries);
			tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

			tableQueries.setModel(tableModelQueries);
			tableQueries.getColumnModel().getColumn(0).setPreferredWidth(10);
			tableQueries.getColumnModel().getColumn(1).setPreferredWidth(195);
			tableQueries.getColumnModel().getColumn(2).setPreferredWidth(54);
			tableQueries.getColumnModel().getColumn(3).setPreferredWidth(34);

			this.getContentPane().add(scrollPaneEvents, null);
			this.getContentPane().add(scrollPaneQueries, null);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
	private void close1(){
		this.dispose();
	}
	

}
