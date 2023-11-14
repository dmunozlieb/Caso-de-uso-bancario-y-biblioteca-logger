package aad.p2.jdbc.implementdao;

import java.util.Random;

/**
* Esta clase realiza/se encarga de generar los datos que voy a insertar en la base de datos
* de forma aleatoria
*
* @author Daniel
*/

public abstract class GenerarDatosRandoms {

	public static double getSaldoRandom() {
		return 1000 + Math.random() * 4000.00;
	}

	public static String getTIpoCuentaRandom() {
		String[] tiposCuenta = { "Cuenta Ahorros", "Cuenta Corriente", "Cuenta Inversión", "Cuenta Empresarial",
				"Cuenta Nómina" };
		return tiposCuenta[(int) (Math.random() * tiposCuenta.length)];
	}

	public static String getNumDnirandom() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			sb.append(random.nextInt(10));
		}
		return sb.toString();
	}

	public static char getLetraDnirandom(String numeroDni) {
		char[] letras = { 'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H',
				'L', 'C', 'K', 'E' };
		int indice = Integer.parseInt(numeroDni)%23;
		return letras[indice];
	}
	public static String generarDNI() {
		String numeros = getNumDnirandom();
		char letraDNI = getLetraDnirandom(numeros);
		return numeros+letraDNI;
	}
	public static String getCiudadRandom() {
		String [] ciudad = {"Madrid","Barcelona","Valencia","Murcia","Málaga","Santander","Vigo","Badajoz","Toledo","Burgos"};
		return ciudad[(int) (Math.random()*ciudad.length)];
	}
}
