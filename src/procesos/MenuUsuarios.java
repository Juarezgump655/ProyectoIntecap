package procesos;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import conexion.Conexion;
import modelo.Usuario;

public class MenuUsuarios {
	JFrame inicio = new JFrame();
	JPanel p1 = new JPanel();
	JTable tabla = new JTable();
	JScrollPane sp = new JScrollPane(tabla);
	PreparedStatement ps;
	ResultSet rs;
	Connection con;
	private String nombre;
	JLabel nUsuario = new JLabel();
	Color color1 = new Color(0x516FFF);
	Conexion ingresar = new Conexion();
	int estado = 1, rol;
	DefaultTableModel modelo = new DefaultTableModel();

	Object[] usuarios = new Object[6];

	private void frame() {
		inicio.setLocationRelativeTo(null);
		inicio.setTitle("Hoteles GT- Usuarios");

		// x y ancho y alto
		inicio.setBounds(500, 200, 1000, 700);
		inicio.setVisible(true);
		inicio.setResizable(false);

		inicio.add(p1);
		p1.setSize(1000, 700);
		p1.setLayout(null);
		p1.setVisible(true);
		p1.setBackground(Color.white);

		JLabel l5 = new JLabel("Usuario: " + nombre);
		l5.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		l5.setBounds(750, 75, 250, 25);
		p1.add(l5);

	}

	public void ejecutar() {
		button();
		frame();
		try {
			table();
		} catch (ClassNotFoundException e) {

			JOptionPane.showMessageDialog(null, e);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);

		}

	}

	private void table() throws ClassNotFoundException, SQLException {
		modelo.addColumn("No.");
		modelo.addColumn("Nombre");
		modelo.addColumn("Apellido");
		modelo.addColumn("Rol");
		modelo.addColumn("Correo");
		modelo.addColumn("Telefono");
		tabla.setModel(modelo);
		sp.setBounds(50, 150, 900, 400);
		p1.add(sp);

		List<Usuario> Listau = listaUsuario();

		for (int i = 0; i < Listau.size(); i++) {
			usuarios[0] = Listau.get(i).getUsuarioid();
			usuarios[1] = Listau.get(i).getNombre();
			usuarios[2] = Listau.get(i).getApellido();
			usuarios[3] = Listau.get(i).getNombreRol();
			usuarios[4] = Listau.get(i).getCorreo();
			usuarios[5] = Listau.get(i).getTelefono();
			modelo.addRow(usuarios);
		}

	}

	public List listaUsuario() {
		String sql = "select u.usuario_id, u.nombre, u.apellido, r.nombre, u.correo_electronico , u.telefono  from usuarios u, roles r WHERE u.rol_id = r.rol_id";
		List<Usuario> listaus = new ArrayList<>();

		try {
			con = ingresar.Conectar();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				Usuario p = new Usuario();
				p.setUsuarioid(rs.getInt("u.usuario_id"));
				p.setNombre(rs.getString("u.nombre"));
				p.setApellido(rs.getString("u.apellido"));
				p.setTelefono(rs.getInt("u.telefono"));
				p.setCorreo(rs.getString("u.correo_electronico"));
				p.setNombreRol(rs.getString("r.nombre"));
				listaus.add(p);
			}

		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null, e);
		}
		return listaus;
	}

	// metodo para obtener el nombre del vendedor

	private void button() {

		JButton cerrarProgra = new JButton("Salir");
		cerrarProgra.setBounds(250, 0, 100, 25);
		cerrarProgra.setForeground(Color.BLACK);
		cerrarProgra.setBackground(Color.WHITE);
		cerrarProgra.setFont(new Font("Roboto black", Font.PLAIN, 22));
		p1.add(cerrarProgra);

		JButton ModificarUsuario = new JButton("Modificar Usuario");
		ModificarUsuario.setBounds(200, 600, 250, 25);
		ModificarUsuario.setForeground(Color.WHITE);
		ModificarUsuario.setBackground(color1);
		ModificarUsuario.setFont(new Font("Roboto black", Font.PLAIN, 22));
		p1.add(ModificarUsuario);

		JButton EliminarUsuario = new JButton("Eliminar Usuario");
		EliminarUsuario.setBounds(500, 600, 250, 25);
		EliminarUsuario.setForeground(Color.WHITE);
		EliminarUsuario.setBackground(color1);
		EliminarUsuario.setFont(new Font("Roboto black", Font.PLAIN, 22));
		p1.add(EliminarUsuario);

		ActionListener funcionCerrarapp = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				inicio.setVisible(false);

				Inicio log = new Inicio();

				try {
					log.ejecutar();
				} catch (IOException e1) {

					JOptionPane.showMessageDialog(null, e);
				}

			}
		};

		// Acción del evento
		cerrarProgra.addActionListener(funcionCerrarapp);

		JButton AgregarU = new JButton("Agregar Usuario");
		AgregarU.setBounds(10, 0, 200, 25);
		AgregarU.setForeground(Color.BLACK);
		AgregarU.setBackground(Color.WHITE);
		AgregarU.setFont(new Font("Roboto black", Font.PLAIN, 22));
		p1.add(AgregarU);

		ActionListener agregar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				crear();

			}
		};

		// Acción del evento
		AgregarU.addActionListener(agregar);

		ActionListener modificarus = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				modificarUS();

			}
		};

		// Acción del evento
		ModificarUsuario.addActionListener(modificarus);

		ActionListener Eliminar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				EliminarUs();

			}
		};

		// Acción del evento
		EliminarUsuario.addActionListener(Eliminar);
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private void crear() {
		String nombre1 = nombre;
		int Rol1= rol;
		inicio.setVisible(false);
		JFrame crearUsuarios = new JFrame();
		JPanel p1 = new JPanel();
		crearUsuarios.setLocationRelativeTo(null);
		crearUsuarios.setTitle("Hoteles GT-Administrador");
		crearUsuarios.setBackground(Color.WHITE);

		crearUsuarios.setLayout(null);
		// x y ancho y alto
		crearUsuarios.setBounds(750, 150, 500, 850);
		crearUsuarios.setVisible(true);
		crearUsuarios.setResizable(false);
		crearUsuarios.add(p1);
		p1.setSize(500, 850);
		p1.setLayout(null);
		p1.setVisible(true);
		p1.setBackground(Color.white);

		ButtonGroup bg = new ButtonGroup();
		JRadioButton activo = new JRadioButton("Activo");
		JRadioButton inactivo = new JRadioButton("Inactivo");
		bg.add(activo);
		bg.add(inactivo);

		activo.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		activo.setBounds(120, 670, 100, 25);
		p1.add(activo);

		inactivo.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		inactivo.setBounds(260, 670, 150, 25);
		p1.add(inactivo);

		JLabel l0 = new JLabel("Agregar Usuario");
		JLabel l1 = new JLabel("Nombre");
		JLabel l2 = new JLabel("Apellido");
		JLabel l3 = new JLabel("Telefono");
		JLabel l4 = new JLabel("Direccion");
		JLabel l5 = new JLabel("Correo");
		JLabel l6 = new JLabel("Fecha Nacimiento");
		JLabel formatoFecha = new JLabel("Año/Mes/Dia");
		JLabel l7 = new JLabel("Contraseña");

		JTextField T1 = new JTextField();
		JTextField T2 = new JTextField();
		JTextField T3 = new JTextField();
		JTextField T4 = new JTextField();
		JTextField T5 = new JTextField();
		JTextField T6 = new JTextField();
		JPasswordField T7 = new JPasswordField();
		JButton B1 = new JButton("Guardar");
		JButton B2 = new JButton("Cancelar");

		l0.setFont(new Font("Roboto Black", Font.PLAIN, 22));
		l0.setBounds(175, 10, 250, 25);
		p1.add(l0);

		l1.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		l1.setBounds(50, 70, 100, 25);
		p1.add(l1);

		l2.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		l2.setBounds(50, 150, 100, 25);
		p1.add(l2);

		l3.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		l3.setBounds(50, 250, 125, 25);
		p1.add(l3);

		l4.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		l4.setBounds(50, 350, 100, 25);
		p1.add(l4);

		l5.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		l5.setBounds(75, 430, 100, 25);
		p1.add(l5);

		formatoFecha.setFont(new Font("Roboto Black", Font.PLAIN, 22));
		formatoFecha.setBounds(200, 500, 200, 25);
		p1.add(formatoFecha);

		l6.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		l6.setBounds(20, 530, 200, 25);
		p1.add(l6);

		l7.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		l7.setBounds(50, 610, 200, 25);
		p1.add(l7);

		T1.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		T1.setBounds(200, 70, 200, 25);
		p1.add(T1);

		T2.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		T2.setBounds(200, 150, 200, 25);
		p1.add(T2);

		T3.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		T3.setBounds(200, 250, 200, 25);
		p1.add(T3);

		T4.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		T4.setBounds(200, 350, 200, 25);
		p1.add(T4);

		T5.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		T5.setBounds(200, 430, 200, 25);
		p1.add(T5);

		T6.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		T6.setBounds(200, 530, 200, 25);
		p1.add(T6);

		T7.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		T7.setBounds(200, 610, 200, 25);
		p1.add(T7);

		B1.setFont(new Font("Roboto Medium", Font.PLAIN, 22));
		B1.setForeground(Color.white);
		B1.setBackground(color1);
		B1.setBounds(80, 740, 150, 50);
		p1.add(B1);

		B2.setFont(new Font("Roboto Medium", Font.PLAIN, 22));
		B2.setForeground(Color.white);
		B2.setBackground(color1);
		B2.setBounds(250, 740, 150, 50);
		p1.add(B2);

		ActionListener agregarbd = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (activo.isSelected()) {
					estado = 1;
				} else if (inactivo.isSelected()) {
					estado = 0;
				}

				if (T1.getText().isEmpty() || T2.getText().isEmpty() || T3.getText().isEmpty() || T4.getText().isEmpty()
						|| T5.getText().isEmpty() || T6.getText().isEmpty() || T7.getText().isEmpty()) {

					JOptionPane.showMessageDialog(null, "DATOS INCORRECTOS");
				} else {
					agregar(T1.getText(), T2.getText(), Integer.parseInt(T3.getText()), T4.getText(), T5.getText(),
							T6.getText(), rol, estado, T7.getText());
					JOptionPane.showMessageDialog(null, "SE HA AGREGADO CORRECTAMENTE");
					crearUsuarios.setVisible(false);
					MenuUsuarios mu = new MenuUsuarios();
					mu.setNombre(nombre1); 
					mu.setRol(Rol1);
					mu.ejecutar();

				}

			}
		};

		// Acción del evento
		B1.addActionListener(agregarbd);

		ActionListener cancelar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				crearUsuarios.setVisible(false);
				MenuUsuarios mu = new MenuUsuarios();
				mu.setNombre(nombre1); 
				mu.setRol(Rol1);
				mu.ejecutar();
			}
		};

		// Acción del evento
		B2.addActionListener(cancelar);
	}

	public void delete(int id) {
		String sql = "delete from usuarios where usuario_id =?";

		try {
			con = ingresar.Conectar();
			ps = con.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void agregar(String nombre, String apellido, int telefono, String direccion, String correo,
			String fecha_nacimiento, int rol_id, int estado, String contrasenia) {
		String sql = "insert into usuarios(nombre, apellido,telefono,direccion,correo_electronico,fecha_nacimiento, rol_id, activo,contraseña)values(?,?,?,?,?,?,?,?,?)";

		try {
			con = ingresar.Conectar();
			ps = con.prepareStatement(sql);
			ps.setString(1, nombre);
			ps.setString(2, apellido);
			ps.setInt(3, telefono);
			ps.setString(4, direccion);
			ps.setString(5, correo);
			ps.setString(6, fecha_nacimiento);
			ps.setInt(7, rol_id);
			ps.setInt(8, estado);
			ps.setString(9, contrasenia);
			ps.executeUpdate();

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	public void modificar(String nombre, String apellido, int telefono, String direccion, String correo,
			String fecha_nacimiento,  String contrasenia, int id) {
		String sql = "update usuarios set nombre=?, apellido=?, telefono=?, direccion=?, correo_electronico=?, fecha_nacimiento=?, contraseña=? where usuario_id=?";

		try {
			con = ingresar.Conectar();
			ps = con.prepareStatement(sql);
			ps.setString(1, nombre);
			ps.setString(2, apellido);
			ps.setInt(3, telefono);
			ps.setString(4, direccion);
			ps.setString(5, correo);
			ps.setString(6, fecha_nacimiento);
			ps.setString(7, contrasenia);
			ps.setInt(8, id);
			
			ps.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public void setRol(int rol) {
		this.rol = rol;
	}

	private void modificarUS() {
		int seleccionar = tabla.getSelectedRow();
		if (seleccionar != -1) {
			String nombre1 = nombre;
			int Rol1= rol;
			inicio.setVisible(false);
			JFrame crearUsuarios = new JFrame();
			JPanel p1 = new JPanel();
			crearUsuarios.setLocationRelativeTo(null);
			crearUsuarios.setTitle("Hoteles GT-Administrador");
			crearUsuarios.setBackground(Color.WHITE);

			crearUsuarios.setLayout(null);
			// x y ancho y alto
			crearUsuarios.setBounds(750, 150, 500, 850);
			crearUsuarios.setVisible(true);
			crearUsuarios.setResizable(false);
			crearUsuarios.add(p1);
			p1.setSize(500, 850);
			p1.setLayout(null);
			p1.setVisible(true);
			p1.setBackground(Color.white);

			

			JLabel l0 = new JLabel("Modificar Usuario");
			JLabel l1 = new JLabel("Nombre");
			JLabel l2 = new JLabel("Apellido");
			JLabel l3 = new JLabel("Telefono");
			JLabel l4 = new JLabel("Direccion");
			JLabel l5 = new JLabel("Correo");
			JLabel l6 = new JLabel("Fecha Nacimiento");
			JLabel formatoFecha = new JLabel("Año/Mes/Dia");
			JLabel l7 = new JLabel("Contraseña");

			JTextField T1 = new JTextField();
			JTextField T2 = new JTextField();
			JTextField T3 = new JTextField();
			JTextField T4 = new JTextField();
			JTextField T5 = new JTextField();
			JTextField T6 = new JTextField();
			JPasswordField T7 = new JPasswordField();
			JButton B1 = new JButton("Guardar Cambios");
			JButton B2 = new JButton("Cancelar");

			l0.setFont(new Font("Roboto Black", Font.PLAIN, 22));
			l0.setBounds(175, 10, 250, 25);
			p1.add(l0);

			l1.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			l1.setBounds(50, 70, 100, 25);
			p1.add(l1);

			l2.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			l2.setBounds(50, 150, 100, 25);
			p1.add(l2);

			l3.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			l3.setBounds(50, 250, 125, 25);
			p1.add(l3);

			l4.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			l4.setBounds(50, 350, 100, 25);
			p1.add(l4);

			l5.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			l5.setBounds(75, 430, 100, 25);
			p1.add(l5);

			formatoFecha.setFont(new Font("Roboto Black", Font.PLAIN, 22));
			formatoFecha.setBounds(200, 500, 200, 25);
			p1.add(formatoFecha);

			l6.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			l6.setBounds(20, 530, 200, 25);
			p1.add(l6);

			l7.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			l7.setBounds(50, 610, 200, 25);
			p1.add(l7);

			T1.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			T1.setBounds(200, 70, 200, 25);
			p1.add(T1);

			T2.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			T2.setBounds(200, 150, 200, 25);
			p1.add(T2);

			T3.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			T3.setBounds(200, 250, 200, 25);
			p1.add(T3);

			T4.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			T4.setBounds(200, 350, 200, 25);
			p1.add(T4);

			T5.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			T5.setBounds(200, 430, 200, 25);
			p1.add(T5);

			T6.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			T6.setBounds(200, 530, 200, 25);
			p1.add(T6);

			T7.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			T7.setBounds(200, 610, 200, 25);
			p1.add(T7);

			B1.setFont(new Font("Roboto Medium", Font.PLAIN, 22));
			B1.setForeground(Color.white);
			B1.setBackground(color1);
			B1.setBounds(40, 740, 250, 50);
			p1.add(B1);

			B2.setFont(new Font("Roboto Medium", Font.PLAIN, 22));
			B2.setForeground(Color.white);
			B2.setBackground(color1);
			B2.setBounds(300, 740, 150, 50);
			p1.add(B2);

			con = ingresar.Conectar();
			String sql = "SELECT nombre,apellido,telefono,direccion,correo_electronico,fecha_nacimiento,contraseña FROM usuarios WHERE usuario_id=? ";
			
			int id = Integer.parseInt(this.tabla.getValueAt(seleccionar, 0).toString());
			
			try { 

				ps = con.prepareStatement(sql);
				ps.setInt(1, id);
				rs = ps.executeQuery();
				
				if (rs.next()) {
					T1.setText(rs.getString(1));
					T2.setText(rs.getString(2));
					T3.setText(rs.getInt(3) +"");
					T4.setText(rs.getString(4));
					T5.setText(rs.getString(5));
					T6.setText(rs.getString(6));
					T7.setText(rs.getString(7));
				}
				
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
			
			ActionListener cancelar = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					crearUsuarios.setVisible(false);
					MenuUsuarios mu = new MenuUsuarios();
					mu.setNombre(nombre1); 
					mu.setRol(Rol1);
					mu.ejecutar();
				}
			};

			// Acción del evento
			B2.addActionListener(cancelar);
			
			
			ActionListener GuardarCambios = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					modificar(T1.getText(), T2.getText(),Integer.parseInt(T3.getText()), T4.getText(), T5.getText(),T6.getText(),T7.getText(), id);
					crearUsuarios.setVisible(false);
					MenuUsuarios mu = new MenuUsuarios();
					mu.setNombre(nombre1); 
					mu.setRol(Rol1);
					mu.ejecutar();
				}
			};

			// Acción del evento
			B1.addActionListener(GuardarCambios);
		}else {
			JOptionPane.showMessageDialog(null, "Debe seleccionar a un usuario");
		}
		
		
		

	}


	private void EliminarUs() {
		int seleccionar = tabla.getSelectedRow();
		
		if (seleccionar != -1) {
			String nombre1 = nombre;
			int Rol1= rol;
			inicio.setVisible(false);
			JFrame crearUsuarios = new JFrame();
			JPanel p1 = new JPanel();
			crearUsuarios.setLocationRelativeTo(null);
			crearUsuarios.setTitle("Hoteles GT-Administrador");
			crearUsuarios.setBackground(Color.WHITE);

			crearUsuarios.setLayout(null);
			// x y ancho y alto
			crearUsuarios.setBounds(750, 150, 500, 850);
			crearUsuarios.setVisible(true);
			crearUsuarios.setResizable(false);
			crearUsuarios.add(p1);
			p1.setSize(500, 850);
			p1.setLayout(null);
			p1.setVisible(true);
			p1.setBackground(Color.white);

			

			JLabel l0 = new JLabel("Eliminar Usuario");
			JLabel l1 = new JLabel("Nombre");
			JLabel l2 = new JLabel("Apellido");
			JLabel l3 = new JLabel("Telefono");
			JLabel l4 = new JLabel("Direccion");
			JLabel l5 = new JLabel("Correo");
			JLabel l6 = new JLabel("Fecha Nacimiento");
			JLabel formatoFecha = new JLabel("Año/Mes/Dia");
			JLabel l7 = new JLabel("Contraseña");

			JTextField T1 = new JTextField();
			JTextField T2 = new JTextField();
			JTextField T3 = new JTextField();
			JTextField T4 = new JTextField();
			JTextField T5 = new JTextField();
			JTextField T6 = new JTextField();
			JPasswordField T7 = new JPasswordField();
			JButton B1 = new JButton("Eliminar");
			JButton B2 = new JButton("Cancelar");

			l0.setFont(new Font("Roboto Black", Font.PLAIN, 22));
			l0.setBounds(175, 10, 250, 25);
			p1.add(l0);

			l1.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			l1.setBounds(50, 70, 100, 25);
			p1.add(l1);

			l2.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			l2.setBounds(50, 150, 100, 25);
			p1.add(l2);

			l3.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			l3.setBounds(50, 250, 125, 25);
			p1.add(l3);

			l4.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			l4.setBounds(50, 350, 100, 25);
			p1.add(l4);

			l5.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			l5.setBounds(75, 430, 100, 25);
			p1.add(l5);

			formatoFecha.setFont(new Font("Roboto Black", Font.PLAIN, 22));
			formatoFecha.setBounds(200, 500, 200, 25);
			p1.add(formatoFecha);

			l6.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			l6.setBounds(20, 530, 200, 25);
			p1.add(l6);

			l7.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			l7.setBounds(50, 610, 200, 25);
			p1.add(l7);

			T1.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			T1.setBounds(200, 70, 200, 25);
			p1.add(T1);

			T2.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			T2.setBounds(200, 150, 200, 25);
			p1.add(T2);

			T3.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			T3.setBounds(200, 250, 200, 25);
			p1.add(T3);

			T4.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			T4.setBounds(200, 350, 200, 25);
			p1.add(T4);

			T5.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			T5.setBounds(200, 430, 200, 25);
			p1.add(T5);

			T6.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			T6.setBounds(200, 530, 200, 25);
			p1.add(T6);

			T7.setFont(new Font("Roboto Light", Font.PLAIN, 22));
			T7.setBounds(200, 610, 200, 25);
			p1.add(T7);

			B1.setFont(new Font("Roboto Medium", Font.PLAIN, 22));
			B1.setForeground(Color.white);
			B1.setBackground(color1);
			B1.setBounds(40, 740, 250, 50);
			p1.add(B1);

			B2.setFont(new Font("Roboto Medium", Font.PLAIN, 22));
			B2.setForeground(Color.white);
			B2.setBackground(color1);
			B2.setBounds(300, 740, 150, 50);
			p1.add(B2);

			con = ingresar.Conectar();
			String sql = "SELECT nombre,apellido,telefono,direccion,correo_electronico,fecha_nacimiento,contraseña FROM usuarios WHERE usuario_id=? ";
			
			int id = Integer.parseInt(this.tabla.getValueAt(seleccionar, 0).toString());
			
			try { 

				ps = con.prepareStatement(sql);
				ps.setInt(1, id);
				rs = ps.executeQuery();
				
				if (rs.next()) {
					T1.setText(rs.getString(1));
					T2.setText(rs.getString(2));
					T3.setText(rs.getInt(3) +"");
					T4.setText(rs.getString(4));
					T5.setText(rs.getString(5));
					T6.setText(rs.getString(6));
					T7.setText(rs.getString(7));
					T1.setEditable(false);
					T2.setEditable(false);
					T3.setEditable(false);
					T4.setEditable(false);
					T5.setEditable(false);
					T6.setEditable(false);
					T7.setEditable(false);
				}
				
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}
			
			ActionListener cancelar = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					crearUsuarios.setVisible(false);
					MenuUsuarios mu = new MenuUsuarios();
					mu.setNombre(nombre1); 
					mu.setRol(Rol1);
					mu.ejecutar();
				}
			};

			// Acción del evento
			B2.addActionListener(cancelar);
			
			
			ActionListener borrar = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					delete(id);
					crearUsuarios.setVisible(false);
					MenuUsuarios mu = new MenuUsuarios();
					mu.setNombre(nombre1); 
					mu.setRol(Rol1);
					mu.ejecutar();
				}
			};

			// Acción del evento
			B1.addActionListener(borrar);
		}else {
			JOptionPane.showMessageDialog(null, "Debe seleccionar a un usuario");
		}
		
	}
}