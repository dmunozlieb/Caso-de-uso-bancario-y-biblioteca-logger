package aad.demop1p2;

import aad.p1.ficheros.EjecutarFicheros;
import aad.p2.jdbc.EjecutarJDBC;

public class MainP1P2 {

	public static void main(String[] args) {
		System.out.println(" - - - Práctica Ficheros - - - \n");
		EjecutarFicheros.ejecutarFicheros();
		System.out.println("\n - - - Práctica JDBC - - - \n");
		EjecutarJDBC.ejecutarJDBC();

	}

}
