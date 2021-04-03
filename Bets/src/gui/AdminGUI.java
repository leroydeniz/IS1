package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Admin;
import domain.Apuesta;
import domain.Event;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import businessLogic.BLFacade;

public class AdminGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Admin adm=null;
	

	
	public AdminGUI(Admin admin, JPanel jContentPane2) {
		super();
		getContentPane().setLayout(null);

		adm=admin;
		
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

		this.setSize(800, 500);
		JPanel jContentPane = new JPanel();
		jContentPane.setLayout(null);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		
		JLabel lblNewLabel = new JLabel("Vista Admin");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblNewLabel.setBounds(350, 22, 109, 16);
		jContentPane.add(lblNewLabel);
		this.setContentPane(jContentPane);
		this.setTitle("Vista admin");
		

		/**************************************** INICIO MENU ****************************************/
		
		//MENU
		JMenuBar barraMenu	= new JMenuBar();
		
		//ELEMENTOS DEL MENU
		JMenu gestion = new JMenu("Gestión");
		JMenu crear	= new JMenu("Crear");
		JMenu editar = new JMenu("Editar");
		barraMenu.add(gestion);
		barraMenu.add(crear);
		barraMenu.add(editar);

		//SUBELEMENTOS DE GESTIÓN
		JMenuItem MovimientosDeCuenta = new JMenuItem("Movimientos de cuenta");
		MovimientosDeCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MovimientosCuentaGUI();
				a.setLocationRelativeTo(null);
				a.setVisible(true);
			}
		});
		JMenuItem DefinirResultados = new JMenuItem("Definir resultados");
		DefinirResultados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new SetResultsGUI(new Vector<Event>(),adm);
				a.setLocationRelativeTo(null);
				a.setVisible(true);
			}
		});
		JMenuItem CrearUsuarioAdmin = new JMenuItem("Crear usuario Admin");
		CrearUsuarioAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CrearAdminGUI();
				a.setLocationRelativeTo(null);
				a.setVisible(true);
			}
		});
		JMenuItem VerPreguntasYEventos = new JMenuItem("Ver como invitado");
		VerPreguntasYEventos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new FindQuestionsGUI();
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
		gestion.add(MovimientosDeCuenta);
		gestion.add(DefinirResultados);
		gestion.add(CrearUsuarioAdmin);
		gestion.add(VerPreguntasYEventos);
		gestion.add(new JSeparator());
		gestion.add(Salir);

		//SUBELEMENTOS DE CREAR
		JMenuItem CrearEvento = new JMenuItem("Evento");
		CrearEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CreateEventGUI();
				a.setLocationRelativeTo(null);
				a.setVisible(true);
			}
		});
		JMenuItem CrearPregunta = new JMenuItem("Pregunta");
		CrearPregunta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CreateQuestionGUI(new Vector<Event>());
				a.setLocationRelativeTo(null);
				a.setVisible(true);
			}
		});
		JMenuItem CrearRespuesta = new JMenuItem("Respuesta");
		CrearRespuesta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CreateAnswerGUI();
				a.setLocationRelativeTo(null);
				a.setVisible(true);
			}
		});
		JMenuItem CrearDeporte = new JMenuItem("Deporte");
		CrearDeporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CrearDeporteGUI();
				a.setLocationRelativeTo(null);
				a.setVisible(true);
			}
		});
		crear.add(CrearEvento);
		crear.add(CrearPregunta);
		crear.add(CrearRespuesta);
		crear.add(CrearDeporte);
		 
		//SUBELEMENTOS DE EDITAR
		JMenuItem EditarEvento = new JMenuItem("Evento");
		EditarEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new UpdateEventGUI(new Vector<Event>());
				a.setLocationRelativeTo(null);
				a.setVisible(true);
			}
		});
		JMenuItem EditarPregunta = new JMenuItem("Pregunta");
		EditarPregunta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new UpdateQuestionGUI(new Vector<Event>());
				a.setLocationRelativeTo(null);
				a.setVisible(true);
			}
		});
		JMenuItem EditarRespuesta = new JMenuItem("Respuesta");
		EditarRespuesta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new UpdateAnswerGUI(new Vector<Event>());
				a.setLocationRelativeTo(null);
				a.setVisible(true);
			}
		});
		JMenuItem EditarDeporte = new JMenuItem("Deporte");
		EditarDeporte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new UpdateDeporteGUI();
				a.setLocationRelativeTo(null);
				a.setVisible(true);
			}
		});
		editar.add(EditarEvento);
		editar.add(EditarPregunta);
		editar.add(EditarRespuesta);
		editar.add(EditarDeporte);
		
		//INDICAMOS MENÚ POR DEFECTO
		setJMenuBar(barraMenu);
		
		JPanel panelPaGrafica = new JPanel();
        panelPaGrafica.setBounds(8, 59, 784, 391);
        jContentPane.add(panelPaGrafica);

		
		/**************************************** FIN MENU ****************************************/
		
		/**************************************** INICIO DASHBOARD ****************************************/
		
		
		  
		JFrame JFrameActivo = this;
		
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrameActivo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                JFrameActivo.setVisible(true);
                
                XYDataset ds = createDataset();
                JFreeChart chart = ChartFactory.createXYLineChart("", "Fecha", "Cantidad", ds, PlotOrientation.VERTICAL, true, true, false);
                chart.getXYPlot().getRangeAxis().setRange(0, 10);
             
             // Create an NumberAxis
                NumberAxis xAxis = new NumberAxis();
                xAxis.setTickUnit(new NumberTickUnit(1));
                
                
                Calendar today = Calendar.getInstance();
        		int month=today.get(Calendar.MONTH);
        		int ano=today.get(Calendar.YEAR);
                //RANGO DEL EJE X
                xAxis.setLowerBound(1);
                
                //Controlo el final del eje X según la cantidad de días del mes
                switch(month+1) {
                	case 1:	
                	case 3:
                	case 5:
                	case 7:
                	case 8:
                	case 10:
                	case 12: {
                        xAxis.setUpperBound(31);
                        break;
                	}
                	case 4:
                	case 6:
                	case 9:
                	case 11: {
                        xAxis.setUpperBound(30);
                        break;
                	}
                	case 2: {
                		GregorianCalendar calendar = new GregorianCalendar();
                		if (calendar.isLeapYear(ano)) {
                			System.out.println("Febrero de año bisiesto");
                        	xAxis.setUpperBound(29);
                		}  else {
                			System.out.println("Febrero de año no bisiesto");
                        	xAxis.setUpperBound(28);
                		}
                		break;
                	}
                	
                }
                

                // Assign it to the chart
                XYPlot plot = (XYPlot) chart.getPlot();
                plot.setDomainAxis(xAxis);
                
                ChartPanel cp = new ChartPanel(chart);
                
                cp.setPreferredSize(new Dimension(788, 391));
                panelPaGrafica.add(cp);
                panelPaGrafica.repaint();
                chart.getXYPlot().getRangeAxis().setRange(0, 10);
            }
        });
		  
		
		/**************************************** FIN DASHBOARD ****************************************/
		
	}
	

	 
	    
    
    
    @SuppressWarnings("deprecation")
	private static XYDataset createDataset() {

    	//Obtenemos el mes para consultar las apuestas del mes
    	Calendar today = Calendar.getInstance();
		int month=today.get(Calendar.MONTH);
		month+=1;
		
        DefaultXYDataset ds = new DefaultXYDataset();
        double dias[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};
        double cantidades[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        
        BLFacade facade = MainGUI.getBusinessLogic();
        ArrayList<Apuesta> apuestas = facade.getApuestas();
        
        for(Apuesta a: apuestas) {
        	if (a.getBetDate().getMonth() == month) {
        		cantidades[a.getBetDate().getDate()-1] +=1;
        	}
        }

        double data[][] = new double[2][31];
        
        for(int i=0; i<31; i++) {
        	data[0][i]=dias[i];
        	data[1][i]=cantidades[i];
        }

        //ds.addSeries("Evolución de las apuestas del mes "+Month.of(month), data);
        String[] monthName = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Deciembre"};
        ds.addSeries("Evolución de las apuestas del mes de "+monthName[month-1], data);
        return ds;
    }
    
    
    
 
	
	private void close1(){
		this.dispose();
	}
}
