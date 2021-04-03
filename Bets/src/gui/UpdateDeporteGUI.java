package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import domain.Deporte;
import javax.swing.JComboBox;
import java.awt.Font;

public class UpdateDeporteGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	JTextField copia = new JTextField();
	JButton close = new JButton("Cancelar");
	JButton aceptar = new JButton("Aceptar");
	JButton eliminar = new JButton("Eliminar deporte");
	JButton confirmarEliminar = new JButton("Confirmar eliminar");
	JLabel success = new JLabel("");

    private static BLFacade appFacadeInterface;

	private JComboBox<Deporte> jComboBoxDeportes = new JComboBox<Deporte>();
	DefaultComboBoxModel<Deporte> modelDeportes = new DefaultComboBoxModel<Deporte>();
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	
	/**
	 * This is the default constructor
	 */
	public UpdateDeporteGUI() {
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

		this.setSize(550, 242);
		this.setContentPane(getJContentPane());
		this.setTitle("Editar deporte");
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
	}
	

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			
			JLabel lblNombre = new JLabel("Seleccionar deporte:");
			lblNombre.setBounds(69, 56, 200, 14);
			jContentPane.add(lblNombre);
			
			copia.setHorizontalAlignment(SwingConstants.CENTER);
			copia.setBounds(323, 89, 170, 26);
			jContentPane.add(copia);
			copia.setColumns(18);
			copia.setEditable(true);
			
			close.setBounds(16, 167, 162, 40);
			close.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					close1();
				}
			});

			actualizarDeportes();
			//copia.setText(jComboBoxDeportes.getSelectedItem().toString());
			aceptar.setBounds(369, 167, 162, 40);
			aceptar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BLFacade facade=MainGUI.getBusinessLogic();
					if(facade.updateSports((Deporte)jComboBoxDeportes.getSelectedItem(), copia.getText())) {
						success.setText("Deporte actualizado.");
						success.setForeground(new Color(0,128,0));
						success.setVisible(true);
						actualizarDeportes();
						copia.setText("");
					} else {
						success.setText("Error en actualizar deporte.");
						success.setForeground(new Color(128,0,0));
						success.setVisible(true);
					}
				}
			});
			
			jContentPane.add(close);
			jContentPane.add(aceptar);
			jComboBoxDeportes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					eliminar.setVisible(true);
					eliminar.setEnabled(true);
					confirmarEliminar.setVisible(false);
					confirmarEliminar.setEnabled(false);
					success.setText("");
				}
			});
			
			jComboBoxDeportes.setBounds(70, 90, 170, 27);
			jContentPane.add(jComboBoxDeportes);
			
			JLabel lblNombre_1 = new JLabel("Nuevo nombre:");
			lblNombre_1.setBounds(323, 55, 200, 14);
			jContentPane.add(lblNombre_1);
			
			eliminar.setBounds(190, 167, 162, 40);
			jContentPane.add(eliminar);
			eliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jButtonDelete_actionPerformed(e);
				}
			});
			
			confirmarEliminar.setBounds(190, 167, 162, 40);
			jContentPane.add(confirmarEliminar);
			
			
			success.setHorizontalAlignment(SwingConstants.CENTER);
			success.setBounds(134, 139, 270, 16);
			success.setText("");
			jContentPane.add(success);
			
			JLabel lblNewLabel = new JLabel("Editar deporte");
			lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setBounds(210, 6, 122, 26);
			jContentPane.add(lblNewLabel);
			confirmarEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jButtonDeleteConfirm_actionPerformed(e);
				}
			});
			
		}
		return jContentPane;
	}
	
	
	public void actualizarDeportes () {
		try {
			jComboBoxDeportes.removeAllItems();
			BLFacade facade=MainGUI.getBusinessLogic();
			ArrayList <Deporte> deportes= new ArrayList<Deporte>(facade.getActiveSports());
			for (Deporte c : deportes) {
				modelDeportes.addElement(c);
			}
			jComboBoxDeportes.setModel(modelDeportes);
			jComboBoxDeportes.repaint();

			success.setText("Deporte actualizado.");
			success.setForeground(new Color(0,128,0));
			success.setVisible(true);
		} catch(Exception ex) {
			success.setText("Deporte no actualizado.");
			success.setForeground(new Color(128,0,0));
			success.setVisible(true);
		}
	}
	
	private void jButtonDelete_actionPerformed(ActionEvent e) {
		eliminar.setVisible(false);
		eliminar.setEnabled(false);
		confirmarEliminar.setVisible(true);
		confirmarEliminar.setEnabled(true);
	}
	
	private void jButtonDeleteConfirm_actionPerformed(ActionEvent e) {
		BLFacade facade = MainGUI.getBusinessLogic();
		if (facade.deleteSport((Deporte)jComboBoxDeportes.getSelectedItem())) {
			success.setText("The sport was deleted.");
			success.setForeground(new Color(0,128,0));
			success.setVisible(true);
			actualizarDeportes();
		} else {
			success.setText("The event was not deleted.");
			success.setForeground(new Color(128,0,0));
			success.setVisible(true);
		}
	}

	private void close1(){
		this.dispose();
	}	
}
