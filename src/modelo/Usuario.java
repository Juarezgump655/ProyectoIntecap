package modelo;

public class Usuario {
	
 private int Usuarioid;	
 private String  nombre, apellido;
 private int telefono;
 private String direccion, correo, fechaNacimiento;
 private int Rolid, activo;
 private String Contrasena;
 private String nombreRol;
 
 
 
 
 
 


public Usuario() {
	
}



public int getUsuarioid() {
	return Usuarioid;
}



public void setUsuarioid(int usuarioid) {
	Usuarioid = usuarioid;
}



public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public String getApellido() {
	return apellido;
}
public void setApellido(String apellido) {
	this.apellido = apellido;
}
public int getTelefono() {
	return telefono;
}
public void setTelefono(int telefono) {
	this.telefono = telefono;
}
public String getDireccion() {
	return direccion;
}
public void setDireccion(String direccion) {
	this.direccion = direccion;
}
public String getCorreo() {
	return correo;
}
public void setCorreo(String correo) {
	this.correo = correo;
}
public String getFechaNacimiento() {
	return fechaNacimiento;
}
public void setFechaNacimiento(String fechaNacimiento) {
	this.fechaNacimiento = fechaNacimiento;
}
public int getRolid() {
	return Rolid;
}
public void setRolid(int rolid) {
	Rolid = rolid;
}
public int getActivo() {
	return activo;
}
public void setActivo(int activo) {
	this.activo = activo;
}
public String getContrasena() {
	return Contrasena;
}
public void setContrasena(String contrasena) {
	Contrasena = contrasena;
}
public String getNombreRol() {
	return nombreRol;
}



public void setNombreRol(String nombreRol) {
	this.nombreRol = nombreRol;
}


 
}
