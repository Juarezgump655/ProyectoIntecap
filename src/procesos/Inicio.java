package procesos;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import conexion.Conexion;
import modelo.Usuario;

public class Inicio {
	JFrame inicio = new JFrame();
	JPanel p1 = new JPanel();
	JLabel label2 = new JLabel("Usuario");
	JLabel label3 = new JLabel("Contraseña");
	JTextField txtUsuario = new JTextField();
	JPasswordField txtContraseña = new JPasswordField();
	JButton btnIniciar = new JButton("INICIAR SESION");

	PreparedStatement ps;
	ResultSet rs;
	Connection con;
	int contador = 3;
	Conexion ingresar = new Conexion();
	String Nombre1;
	int rol1;

	public static void main(String[] args) throws IOException {
		Inicio p1 = new Inicio();
		p1.ejecutar();

	}

	private void frame() {
		inicio.setLocationRelativeTo(null);
		inicio.setTitle("INICIO DE SESION");

		// x y ancho y alto
		inicio.setBounds(700, 300, 500, 500);
		inicio.setVisible(true);
		inicio.setResizable(false);

		inicio.add(p1);
		p1.setSize(800, 500);
		p1.setLayout(null);
		p1.setVisible(true);
		p1.setBackground(Color.white);

	}

	private void label() throws IOException {

		JLabel label1 = new JLabel();

		label1.setBounds(150, 10, 300, 100);
		ImageIcon img = new ImageIcon("src/icon/Logo.png");
		Icon icono = new ImageIcon(
				img.getImage().getScaledInstance(label1.getWidth(), label1.getHeight(), Image.SCALE_DEFAULT));
		label1.setIcon(icono);
		label1.setBounds(90, 10, 300, 100);
		p1.add(label1);

		label1.setFont(new Font("Roboto Black", Font.PLAIN, 22));

		p1.add(label2);
		label2.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		label2.setBounds(120, 100, 125, 93);

		p1.add(label3);
		label3.setFont(new Font("Roboto Light", Font.PLAIN, 22));
		label3.setBounds(120, 250, 125, 93);

		p1.add(txtUsuario);
		txtUsuario.setBounds(120, 180, 200, 25);
		txtUsuario.setFont(new Font("Roboto Light", Font.PLAIN, 22));

		p1.add(txtContraseña);
		txtContraseña.setBounds(120, 320, 200, 25);
		txtContraseña.setFont(new Font("Roboto Light", Font.PLAIN, 22));

	}

	public void ejecutar() throws IOException {
		frame();
		label();
		button();
	}

	private void button() {

		btnIniciar.setBounds(140, 375, 200, 50);
		btnIniciar.setFont(new Font("Roboto Medium", Font.PLAIN, 20));

		btnIniciar.setBackground(Color.white);
		p1.add(btnIniciar);

		// Funcionalidad
		ActionListener ingresar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					if (verificar(txtUsuario.getText(), txtContraseña.getText())) {
						JOptionPane.showMessageDialog(null, "Bienvenido al sitema: " + Nombre1);
						inicio.setVisible(false);
						MenuUsuarios lv = new MenuUsuarios();
						lv.setNombre(Nombre1);
						lv.setRol(rol1);
						lv.ejecutar();

					} else {
						contador--;
						JOptionPane.showMessageDialog(null,
								"DATOS EQUIVOCADOS O EL USUARIO NO EXISTE " + "\n LE QUEDAN " + contador + " INTENTOS");
						if (contador == 0) {
							System.exit(0);
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		};

		// Acción del evento
		btnIniciar.addActionListener(ingresar);
	}

	public boolean verificar(String correo, String contraseña) throws SQLException {
		boolean pase = false;
		con = ingresar.Conectar();
		String sql = "SELECT nombre, contraseña, rol_id FROM usuarios WHERE correo_electronico=?";
		try {

			ps = con.prepareStatement(sql);
			ps.setString(1, correo);
			rs = ps.executeQuery();

			if (rs.next()) {

				if (contraseña.equals(rs.getString(2))) {
					Nombre1 = rs.getString(1);
					rol1 = rs.getInt(3);
					pase = true;
				}

			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return pase;

	}

}
