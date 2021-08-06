package aplicacion;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDayChooser;

import bean.DetalleExposicion;
import bean.Exposicion;
import bean.Sede;
import bean.Usuario;
import conectorBD.MySQLConexion;
import service.ControlRegistrarVisitas;

import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class app_registrarVentaEntradas extends JFrame {

	private JPanel contentPane;
	private JTextField txtCantVisitantes;
	private JTextField txtDuracionVisita;
	private JComboBox comboEscuela;
	private JComboBox comboSedeVisitar;
	private JComboBox comboTipoVisita;
	private JDateChooser dateSelectFechaReserva;
	private JButton btnRegistrarReserva;
	private JButton btnCancelarReserva;
	private JLabel lblEscuela;
	private JLabel lblCantidadVisitantes;
	private JLabel lblSedeVisitar;
	private JLabel lblTipoVisita;
	private JLabel lblExpoVisitar; 
	private JLabel lblFechaReserva; 
	private JLabel lblDuracionVisita;
	private JLabel lblGuiasDisp;
	private JLabel lblHoraInicio;
	private JLabel lblHoraFin;
	ArrayList<String> listaEscuela = new ArrayList<String>();
	ArrayList<String> listaSedeVisitar = new ArrayList<String>();
	ArrayList<String> listaTipoVisita = new ArrayList<String>();
	ArrayList<Exposicion> listaExpoVisitar = new ArrayList<Exposicion>();
	ArrayList<String> listaGuia = new ArrayList<String>();
	ControlRegistrarVisitas registrarVisitas = new ControlRegistrarVisitas();
	ArrayList<Exposicion> listaDuracionReserva = new ArrayList<Exposicion>();
	private int duracionExtendida;
	private int duracionResumida;
	Object tipoVisitaSeleccionada;
	Object sedeSeleccionada; 
	Object guiaSeleccionado;
	app_login login = new app_login();
	private JTextField txtHoraInicio;
	private JTextField txtHoraFin;
	private JScrollPane scrollPaneExpo;
	private JTable tablaExpo;
	private JScrollPane scrollPaneGuias;
	private JTable tablaGuias;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					app_registrarVentaEntradas frame = new app_registrarVentaEntradas();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public app_registrarVentaEntradas() {
		inicializarVariables();
		registrarVisitas.tomarFechaHoraActual();
	}

	
	public void inicializarVariables() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblEscuela = new JLabel("Escuela:");
		lblEscuela.setBounds(63, 45, 111, 14);
		contentPane.add(lblEscuela);

		lblCantidadVisitantes = new JLabel("Cantidad de visitantes:");
		lblCantidadVisitantes.setBounds(63, 87, 135, 14);
		contentPane.add(lblCantidadVisitantes);

		lblSedeVisitar = new JLabel("Sede a visitar:");
		lblSedeVisitar.setBounds(63, 131, 131, 14);
		contentPane.add(lblSedeVisitar);

		lblTipoVisita = new JLabel("Tipo de Visita:");
		lblTipoVisita.setBounds(63, 177, 79, 14);
		contentPane.add(lblTipoVisita);

		lblExpoVisitar = new JLabel("Exposicion a visitar:");
		lblExpoVisitar.setBounds(63, 217, 135, 14);
		contentPane.add(lblExpoVisitar);

		lblFechaReserva = new JLabel("Fecha de Reserva:");
		lblFechaReserva.setBounds(63, 382, 111, 14);
		contentPane.add(lblFechaReserva);

		lblDuracionVisita = new JLabel("Duracion Aproximada de la visita:");
		lblDuracionVisita.setBounds(62, 429, 209, 14);
		contentPane.add(lblDuracionVisita);

		lblGuiasDisp = new JLabel("Guias Disponibles:");
		lblGuiasDisp.setBounds(62, 464, 147, 14);
		contentPane.add(lblGuiasDisp);

		btnRegistrarReserva = new JButton("Registrar Reserva");
		btnRegistrarReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//registrarReserva();
				cambiarVista();
			}
		});
		btnRegistrarReserva.setBounds(607, 602, 129, 23);
		contentPane.add(btnRegistrarReserva);

		btnCancelarReserva = new JButton("Cancelar");
		btnCancelarReserva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salir();
			}
		});
		btnCancelarReserva.setBounds(746, 602, 89, 23);
		contentPane.add(btnCancelarReserva);

		comboEscuela = new JComboBox();
		

		comboEscuela.setBounds(269, 41, 111, 22);
		contentPane.add(comboEscuela);
		comboEscuela.requestFocus();

		txtCantVisitantes = new JTextField();
		txtCantVisitantes.setBounds(269, 85, 109, 20);
		contentPane.add(txtCantVisitantes);
		txtCantVisitantes.setColumns(10);

		comboSedeVisitar = new JComboBox();
		comboSedeVisitar.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
			}
		});

		/*comboSedeVisitar.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				exposicionSedeSeleccionada();
			}
		});
		comboSedeVisitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exposicionSedeSeleccionada();
			}
		});*/

		comboSedeVisitar.setBounds(269, 127, 111, 22);
		contentPane.add(comboSedeVisitar);

		comboTipoVisita = new JComboBox();
		

		comboTipoVisita.setBounds(269, 173, 111, 22);
		contentPane.add(comboTipoVisita);

		dateSelectFechaReserva = new JDateChooser();
		dateSelectFechaReserva.setBounds(269, 382, 129, 20);
		contentPane.add(dateSelectFechaReserva);

		txtDuracionVisita = new JTextField();
		txtDuracionVisita.setEnabled(false);
		txtDuracionVisita.setBounds(269, 427, 129, 20);
		contentPane.add(txtDuracionVisita);
		//txtDuracionVisita.setColumns(10);
		
		lblHoraInicio = new JLabel("Desde:");
		lblHoraInicio.setBounds(416, 389, 45, 13);
		contentPane.add(lblHoraInicio);
		
		txtHoraInicio = new JTextField();
		txtHoraInicio.setBounds(451, 386, 96, 19);
		contentPane.add(txtHoraInicio);
		txtHoraInicio.setColumns(10);
		
		lblHoraFin = new JLabel("Hasta:");
		lblHoraFin.setBounds(557, 389, 45, 13);
		contentPane.add(lblHoraFin);
		
		txtHoraFin = new JTextField();
		txtHoraFin.setBounds(592, 386, 96, 19);
		contentPane.add(txtHoraFin);
		txtHoraFin.setColumns(10);
		
		scrollPaneExpo = new JScrollPane(tablaExpo);
		scrollPaneExpo.setBounds(251, 217, 443, 136);
		contentPane.add(scrollPaneExpo);
		
		tablaExpo = new JTable();
		tablaExpo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
	            final JTable jTable= (JTable)e.getSource();
	            final int row = jTable.getSelectedRow();
	            final int column = 3;
	            final String valueInCell = (String)jTable.getValueAt(row, column);
	            txtDuracionVisita.setText(valueInCell);
			}
		});
		tablaExpo.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tablaExpo.setFillsViewportHeight(true);

		scrollPaneExpo.setViewportView(tablaExpo);
		
		scrollPaneGuias = new JScrollPane();
		scrollPaneGuias.setBounds(245, 497, 443, 71);
		contentPane.add(scrollPaneGuias);
		
		tablaGuias = new JTable();
		scrollPaneGuias.setViewportView(tablaGuias);
		
		JLabel lblGuiasASeleccionar = new JLabel("Seleccione ... guía/s");
		lblGuiasASeleccionar.setBounds(245, 464, 147, 14);
		contentPane.add(lblGuiasASeleccionar);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"14:00"}));
		comboBox.setBounds(451, 413, 96, 22);
		contentPane.add(comboBox);
		
        
      
		cargarCombos();
	}

	protected void registrarReserva() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	    System.out.println("dd/MM/yyyy HH:mm:ss-> "+dtf.format(LocalDateTime.now()));
	    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	    String date = formatter.format(dateSelectFechaReserva.getDate());
//	    System.out.println(formatter.format(dateSelectFechaReserva));
	    
		/*: Registra la reserva con estado Pendiente de Confirmación, generando un número único, empleado 
			que registró, fecha y hora de creación, fecha y hora de reserva, duración estimada de la visita, exposiciones a 
			visitar, escuela, cantidad de visitantes y guías asignados para la visita.*/
		// this.login.getUsuarioLogueado(), fechaCreacion, dateSelectFechaReserva, txtHoraInicio, txtHoraFin,txtDuracionVisita.getText(),comboExpoVisitar.getSelectedItem(), comboEscuela.getSelectedItem(),txtCantVisitantes.getText(),this.guiaSeleccionado,this.sedeSeleccionada
		// this.login.getUsuarioLogueado(), txtDuracionVisita.getText(), comboExpoVisitar.getSelectedItem(), comboEscuela.getSelectedItem(),txtCantVisitantes.getText(),this.guiaSeleccionado,this.sedeSeleccionada
//		this.guiaSeleccionado = comboGuiasDisp.getSelectedItem();
	    //empleado, fechaCreacion, fechaReserva, horaReservaInicio, horaReservaFin, duracionEstimadaVisita, exposiciones, escuela, cantidadVisitantes, guias, sede
	    String usuarioLogueado = "vparis";
//	    System.out.println("Usuario Logueado: " + usuarioLogueado);
//	    System.out.println("Fecha actual " +  dtf.format(LocalDateTime.now()));
//	    System.out.println("Fecha Seleccionada " + date);
//	    System.out.println("Desde: " + Integer.parseInt(txtHoraInicio.getText()));
//	    System.out.println("Hasta: " + Integer.parseInt(txtHoraFin.getText()));
//	    System.out.println("Duracion de la visita: " +Integer.parseInt(txtDuracionVisita.getText()));
//	    System.out.println("Expo a visitar: " + comboExpoVisitar.getSelectedItem().toString());
//	    System.out.println("Escuela seleccionada:" + comboEscuela.getSelectedItem().toString());
//	    System.out.println("Cantidad de visitantes: " + Integer.parseInt(txtCantVisitantes.getText()));
//	    System.out.println("Guia seleccionado: " + comboGuiasDisp.getSelectedItem().toString());
//	    System.out.println("Sede seleccionada: " + comboSedeVisitar.getSelectedItem().toString());
	     
		/*registrarVisitas.RegistrarReserva(usuarioLogueado, dtf.format(LocalDateTime.now()), date, Integer.parseInt(txtHoraInicio.getText()), Integer.parseInt(txtHoraFin.getText()),
										  Integer.parseInt(txtDuracionVisita.getText()),comboExpoVisitar.getSelectedItem().toString(), 
										  comboEscuela.getSelectedItem().toString(),Integer.parseInt(txtCantVisitantes.getText()),
										  comboGuiasDisp.getSelectedItem().toString(),comboSedeVisitar.getSelectedItem().toString());*/
	}
	
	/*protected void exposicionSedeSeleccionada() {
		this.sedeSeleccionada = comboSedeVisitar.getSelectedItem();

		registrarVisitas.buscarExpSedeSeleccionada(this.sedeSeleccionada);
		listaExpoVisitar = registrarVisitas.getSede().getListaExposicion();

		tablaExpo.removeAll();
		for (int i = 0; i < listaExpoVisitar.size(); i++) {
			tablaExpo.addItem("" + listaExpoVisitar.get(i).getNombre() + " - "
					+ listaExpoVisitar.get(i).getPublicoDestino().getNombre() + " - " + " Atención de: "
					+ listaExpoVisitar.get(i).getHoraApertura().getHours() + " a "
					+ listaExpoVisitar.get(i).getHoraCierre().getHours() + " hs.");
		}
	}*/

	/*private void cargarCombos() {

		listaEscuela = registrarVisitas.buscarEscuela();
		for (int i = 0; i < listaEscuela.size(); i++) {
			comboEscuela.addItem(listaEscuela.get(i));
		}
		
		listaSedeVisitar = registrarVisitas.buscarSede();
		for (int i = 0; i < listaSedeVisitar.size(); i++) {
			comboSedeVisitar.addItem(listaSedeVisitar.get(i));
		}
		listaTipoVisita = registrarVisitas.buscarTipoVisitar();
		for (int i = 0; i < listaTipoVisita.size(); i++) {
			comboTipoVisita.addItem(listaTipoVisita.get(i));
		}
		tablaGuias.setModel(new DefaultTableModel(
		new Object[][] {
			{null},
			{null},
			{null},
		},
		new String[] {
			"Nombre"
		}
				));
		
		//listaGuia = registrarVisitas.calcGuiasDispFechaReserva();
		//for (int i = 0; i < listaGuia.size(); i++) {
			//comboGuiasDisp.addItem(listaGuia.get(i));
		//}
	}*/
	
	private void cargarCombos() {
		
		comboEscuela.setModel(new DefaultComboBoxModel(new String[] {"Trinitarios", "Rivadavia", "Manuel Belgrano"}));
		
		comboSedeVisitar.setModel(new DefaultComboBoxModel(new String[] {"Sede1", "Sede2", "Sede3"}));
		
		comboTipoVisita.setModel(new DefaultComboBoxModel(new String[] {"Completa", "Por Exposici\u00F3n"}));
		
		
		tablaExpo.setModel(new DefaultTableModel(
			new Object[][] {
				{"Expo1", "Todo publico", "8", "12"},
				{"Expo2", "Estudiantes", "13", "15"},
				{"Expo3", "Todo Publico", "10", "11"},
				{"Expo4", "Niños", "16", "17"},
				{null, null, null, null},
			},
			new String[] {
				"Nombre", "Publico destino", "Hora Inicio", "Hora Fin"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		

		
		tablaGuias.setModel(new DefaultTableModel(
			new Object[][] {
				{"Alejandro"},
				{"Valentin"},
				{"Julian"},
			},
			new String[] {
				"Nombre"
			}
		));
		
		
	}

	protected void salir() {
		this.dispose();
		app_menuPrincipal menuPrincipal = new app_menuPrincipal();
		menuPrincipal.setVisible(true);
	}
	
	protected void cambiarVista() {
		app_registrado registrado = new app_registrado();
		registrado.setVisible(true);
	}
}
