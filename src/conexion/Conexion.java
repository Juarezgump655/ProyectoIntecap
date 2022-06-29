package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
	Connection con;
	String url = "jdbc:mysql://localhost:3306/evalti";
	String user = "root";
	String pass= "";
	
	public Connection Conectar() {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(url, user, pass);
		} catch (Exception e) {
				System.out.println("no funciona");
		}
		return con;
	}
}
