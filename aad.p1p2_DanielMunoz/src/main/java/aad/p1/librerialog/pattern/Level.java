package aad.p1.librerialog.pattern;

import java.util.HashMap;

/**
* Esta clase se encarga de asignar un valor a cada nivel de logger
* para comprobar a partir de qué nivel puedo imprimir
*
* @author Daniel
*/

public class Level {
	
	private static HashMap <String, Integer> map = new HashMap<>();
	
	public static void agregaElementos() {
		map.put("trace", 1);
		map.put("debug", 2);
		map.put("info", 3);
		map.put("warn", 4);
		map.put("error", 5);
	}
	
	public static int intValue(String level) {
		
		return map.get(level);
	}
}
