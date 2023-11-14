package aad.p1.librerialog.appender;

/**
* Esta clase se encarga de crear objetos de tipo Consola
* para implementar la interfaz Appender, para imprimirlo por consola
*
* @author Daniel
*/


public class Consola implements Appender {

	private String nombre;
	private String pattern;
	
	public Consola(String nombre, String layout) {
		this.nombre = nombre;
		this.pattern = layout;
	}

	public void append(String mensaje) {
		System.out.println(mensaje);
	}

	public String getNombre() {
		return nombre;
	}

	public String getPattern() {
		return pattern;
	}

}
