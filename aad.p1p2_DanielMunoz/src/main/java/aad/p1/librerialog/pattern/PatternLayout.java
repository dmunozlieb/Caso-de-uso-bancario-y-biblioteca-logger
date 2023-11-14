package aad.p1.librerialog.pattern;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
* Esta clase se encarga de darle un patrón de diseño
* cuando lo imprima, a partir del pattern que le paso por argumento
*
* @author Daniel
*/

public class PatternLayout {

	
	public static String format(String mensaje, String nivel, String pattern,String classMain) {
		String mensajeFormat = pattern
				.replace("%d", obtenerFecha())
				.replace("%p", classMain)
				.replace("%n", nivel.toUpperCase())
				.replace("%m", mensaje); 
		
		
		return mensajeFormat;
	}
	
	
	public static String obtenerFecha() {
		SimpleDateFormat fecha = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		
		return fecha.format(new Date());
	}
	
	
}
