package aad.p2.jdbc.model;

/**
* Esta clase se encarga de crear objetos de tipo Cliente
*
* @author Daniel
*/


public class Cliente {

	private int id;
	private String nombre;
	private String apellido;
	private String dni;
	private String ciudad;
	

	public Cliente() {

	}

	
	
	public Cliente(int id, String nombre, String apellido, String dni, String ciudad) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.ciudad = ciudad;
	}




	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
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


	public String getDni() {
		return dni;
	}


	public void setDni(String dni) {
		this.dni = dni;
	}


	public String getCiudad() {
		return ciudad;
	}


	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	
	
}