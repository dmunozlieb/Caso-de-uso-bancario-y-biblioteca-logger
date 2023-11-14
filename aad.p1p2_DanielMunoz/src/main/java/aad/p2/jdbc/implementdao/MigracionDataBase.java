package aad.p2.jdbc.implementdao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import aad.p2.jdbc.dao.OperacionesDAO;
import aad.p2.jdbc.model.*;

/**
* Esta clase realiza/se encarga de crear el fichero donde voy a almacenar la contabilidad
* de los datos de cada tabla y base de datos,y de hacer la migración
*
* @author Daniel
*/

public class MigracionDataBase {

	private static File resultMigrate;
	private static PrintWriter print;
	private static List<Cuenta> listaCuenta;
	private static List<Cliente> listaCliente;
	private static List<Transaccion> listaTransaccion;
	private static int contadorTotal = 0;
	static {
		try {
			resultMigrate = new File("src\\main\\java\\aad\\p2\\jdbc\\resultadoMigracion.txt");
			if (!resultMigrate.exists()) {
				resultMigrate.createNewFile();
			}
			print = new PrintWriter(new FileWriter(resultMigrate));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void migrateDB(OperacionesDAO dataBaseOrigen, OperacionesDAO dataBaseDestino) throws Exception {
		listaCuenta = dataBaseOrigen.findAllCuenta();
		listaCliente = dataBaseOrigen.findAllCliente();
		listaTransaccion = dataBaseOrigen.findAllTransaccion();
		contarTablaOrigen(dataBaseOrigen);
		contarTablaDestino(dataBaseDestino);
		insertarDatosCuentas(listaCuenta, dataBaseDestino);
		insertarDatosCliente(listaCliente,dataBaseDestino);
		insertarDatosTransaccion(listaTransaccion,dataBaseDestino);
		escribirFichero("Datos migrados 'prac2' -> 'prac2migra':"+contadorTotal);
		contarTotalDestino(dataBaseDestino);
		print.close();
	}
	
	public static void insertarDatosCuentas(List<Cuenta>lista,OperacionesDAO dataBaseDestino) throws Exception {
		for(Cuenta cuenta:lista) {
			dataBaseDestino.insert(cuenta, true);
			contadorTotal++;
		}
		
	}
	public static void insertarDatosCliente(List<Cliente>lista,OperacionesDAO dataBaseDestino) throws Exception {
		for(Cliente cliente:lista) {
			dataBaseDestino.insert(cliente, true);
			contadorTotal++;
		}
		
	}
	public static void insertarDatosTransaccion(List<Transaccion>lista,OperacionesDAO dataBaseDestino) throws Exception {
		for(Transaccion transaccion:lista) {
			dataBaseDestino.insert(transaccion, true);
			contadorTotal++;
		}
		
	}
	
	public static void contarTablaOrigen(OperacionesDAO dataBaseOrigen) throws Exception {
		escribirFichero("Esquema Original 'pract2' - Tabla 'Clientes': "+dataBaseOrigen.contarRegistros(" cliente "));
		escribirFichero("Esquema Original 'pract2' - Tabla 'Cuentas': "+dataBaseOrigen.contarRegistros(" cuenta "));
		escribirFichero("Esquema Original 'pract2' - Tabla 'Transacciones': "+dataBaseOrigen.contarRegistros(" transaccion "));
	}
	
	public static void contarTablaDestino(OperacionesDAO dataBaseDestino) throws Exception {
		escribirFichero("Esquema Destino 'pract2' - Tabla 'Clientes': "+dataBaseDestino.contarRegistros(" cliente_migra "));
		escribirFichero("Esquema Destino 'pract2' - Tabla 'Cuentas': "+dataBaseDestino.contarRegistros(" cuenta_migra "));
		escribirFichero("Esquema Destino 'pract2' - Tabla 'Transacciones': "+dataBaseDestino.contarRegistros(" transaccion_migra "));
	}
	
	public static void contarTotalDestino(OperacionesDAO dataBaseDestino) throws Exception {
		escribirFichero("Total 'pract2' - Tabla 'Clientes': "+dataBaseDestino.contarRegistros("cliente_migra"));
		escribirFichero("Total 'pract2 'pract2' - Tabla 'Cuentas': "+dataBaseDestino.contarRegistros("cuenta_migra"));
		escribirFichero("Total 'pract2 'pract2' - Tabla 'Transacciones': "+dataBaseDestino.contarRegistros("transaccion_migra"));
	}
	
	public static void escribirFichero(String texto) {
		print.write(texto);
		print.println();
	}
}
