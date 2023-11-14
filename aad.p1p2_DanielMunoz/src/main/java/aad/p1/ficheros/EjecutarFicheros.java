package aad.p1.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;

import aad.p1.librerialog.logger.Logger;

public class EjecutarFicheros {
	
	private static Logger logger;
	private static RandomAccessFile archivo;
	private static Cuenta_Bancaria cuenta;

	private static int idCuenta = 2;
	private static String nombreTitular = "Manuel";
	private static double saldo = 1200.00;
	private static double cantidadDepositar = 400.00;
	private static double cantidadRetirar = 200.00;
	
	public static void ejecutarFicheros() {
		logger = new Logger("CajeroMain");
		logger.trace("Bibliotecas implementadas java.io.* com.aad.log4j.*  java.text.SimpleDateFormat");
		logger.debug("El programa comenzó");
		logger.warn("Aviso: Ruta del fichero existente");
		try {
			File cuentasBancarias = new File("src\\main\\java\\aad\\p1\\ficheros\\cuentasBancarias.dat");
			archivo = new RandomAccessFile(cuentasBancarias, "rw");
			logger.debug("Archivo abierto para lectura/escritura");
			existeFichero(cuentasBancarias);
			cuenta = new Cuenta_Bancaria(idCuenta, nombreTitular, saldo);
			if (!existeCuenta(archivo, idCuenta)) {
				escribirCuenta(archivo, cuenta);
				logger.info("Cuenta " + cuenta.getTitular() + " registrada en el fichero");
			}
			GestorTransacciones.consultarSaldo(archivo, idCuenta, logger);
			logger.debug(
					"Ejecutando la función depositarSaldo(RandomAccessFile arc, int numCuenta, int sueldo, Logger log)");
			GestorTransacciones.depositarSaldo(archivo, idCuenta, cantidadDepositar, logger);
			logger.debug("Saliendo de la función depositarSaldo");
			logger.debug(
					"Ejecutando la función retirarSaldo(RandomAccessFile arc, int numCuenta, int sueldo, Logger log)");
			GestorTransacciones.retirarSaldo(archivo, idCuenta, cantidadRetirar, logger);
			logger.debug("Saliendo de la función retirarSaldo");
			GestorTransacciones.consultarSaldo(archivo, idCuenta, logger);

		} catch (EOFException io) {
			logger.error(io.getMessage());
			io.printStackTrace();
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void existeFichero(File cuentasBancarias) throws IOException {
		if (!cuentasBancarias.exists()) {
			if (cuentasBancarias.createNewFile())
				logger.info("Fichero " + cuentasBancarias.getName() + " creado correctamente");
			return;
		}
		logger.info("Fichero empleado " + cuentasBancarias.getName());
	}

	public static void escribirCuenta(RandomAccessFile archivo, Cuenta_Bancaria cuenta) throws IOException {
		if (cuenta.getSaldo() < 100) {
			logger.warn("AVISO: Saldo mínimo 100 para abrir cuenta");
		}

		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd");
		String fechaCreacion = formatDate.format(cuenta.getFechaCreacion());
		archivo.seek((cuenta.getNumCuenta() - 1) * 52);
		archivo.writeInt(idCuenta);
		StringBuffer nombreTitular = new StringBuffer(cuenta.getTitular());
		nombreTitular.setLength(10);
		archivo.writeChars(nombreTitular.toString());
		StringBuffer fecha = new StringBuffer(fechaCreacion);
		fecha.setLength(10);
		archivo.writeChars(fecha.toString());
		archivo.writeDouble(cuenta.getSaldo());
	}

	public static boolean existeCuenta(RandomAccessFile archivo, int numCuenta) throws IOException {
		int cont = 0;
		while (true) {
			try {
				archivo.seek(cont * 52);
				int cuentaId = archivo.readInt();
				if (cuentaId == numCuenta) {
					return true;
				}
				cont++;
			} catch (EOFException eof) {

				return false;
			}
		}

	}
	
}
