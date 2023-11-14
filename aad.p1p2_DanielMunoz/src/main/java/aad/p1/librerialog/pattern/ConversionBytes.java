package aad.p1.librerialog.pattern;

/**
* Esta clase se encarga de realizar la conversion
* de la parte maxSize en bytes para realizar la rotacion
* del fichero
*
* @author Daniel
*/

public class ConversionBytes {
	
	public static long parseSizeToBytes(String sizeString) {
		int partEntera;
		sizeString = sizeString.trim().toLowerCase();
		String [] partes = splitNumberAndUnit(sizeString);
		try {
			switch (partes[1].toLowerCase()) {
			case "kb": 
				partEntera = Integer.parseInt(partes[0]);
				return partEntera * 1000;
				
			case "mb":
				partEntera = Integer.parseInt(partes[0]);
				return partEntera * 1000 * 1000;
			case "gb":
				partEntera = Integer.parseInt(partes[0]);
				return partEntera * 1000 * 1000 * 1000;
			default:
				return -1;
			
		}
		}catch (ArrayIndexOutOfBoundsException e) {
			System.err.println(e.getMessage());
			return -1;
		}catch (NumberFormatException n) {
			System.err.println(n.getMessage());
			return -1;
		}catch (Exception e) {
			System.err.println(e.getMessage());
			return -1;
		}
		
		
	}
	
	public static String [] splitNumberAndUnit(String sizeString) {
		String [] partes = sizeString.split(" ");
		return partes;
	}
}
