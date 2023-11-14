package aad.p1.ficheros;

import java.io.IOException;
import java.io.RandomAccessFile;



import aad.p1.librerialog.logger.Logger;

/**
* Esta clase se encarga de realizar todas las operaciones con 
* el fichero binario que 
* ejecuto desde el main.
*
* @author Daniel
*/

public class GestorTransacciones {
	
	private static long offset;
	
	public static void consultarSaldo(RandomAccessFile archivo, int numCuenta, Logger logger) throws IOException {
		if(!EjecutarFicheros.existeCuenta(archivo, numCuenta)) {
			logger.error("El id de cuenta no se encuentra registrada");
			logger.info("No se pudo realizar la transacción consultar saldo");
			return;
		}
		archivo.seek((numCuenta-1)*52+4);
		String nombre = "";
		logger.debug("Entramos en la iteración for en consultarSaldo...");
		for(int i=0;i<10;i++) {
			nombre+=archivo.readChar();
		}
		logger.debug("Salimos de la iteración for consultarSaldo...");
		archivo.seek((numCuenta-1)*52+44);
		double saldo = archivo.readDouble();
		logger.info("Cuenta titular: "+nombre+" Id Cuenta:  "+numCuenta);
		logger.info("Saldo actual "+saldo);
	}
	
	public static void depositarSaldo(RandomAccessFile archivo, int numCuenta, double cantidad, Logger logger) throws IOException {
		if(cantidad <= 0) {
			logger.debug("Al introducir cantidad negativa se resta");
			logger.error("Cantidad insuficiente para depositar en la cuenta");
			return;
		}
		if(!EjecutarFicheros.existeCuenta(archivo, numCuenta)) {
			logger.error("El id de cuenta no se encuentra registrada");
			logger.info("No se pudo realizar la transacción depositar saldo");
			return;
		}
		offset =  (numCuenta-1)*52+44;
		archivo.seek(offset);
		double saldo = archivo.readDouble();
		saldo += cantidad;
		archivo.seek(offset);
		archivo.writeDouble(saldo);
		archivo.seek(offset);
		logger.info("Ingreso en la cuenta "+numCuenta+" de "+cantidad);
		
	}
	
	public static void retirarSaldo(RandomAccessFile archivo, int numCuenta, double cantidad, Logger logger) throws IOException {
		if(cantidad <= 0) {
			logger.debug("Al introducir cantidad negativa se suma");
			logger.error("Cantidad insuficiente para retirar en la cuenta");
			return;
		}
		if(!EjecutarFicheros.existeCuenta(archivo, numCuenta)) {
			logger.error("El id de cuenta no se encuentra registrada");
			logger.info("No se pudo realizar la transacción retirar saldo");
			return;
		}
		offset =  (numCuenta-1)*52+44;
		archivo.seek(offset);
		double saldo = archivo.readDouble();
		if(cantidad>saldo) {
			logger.error("No saldo suficiente para retirar");
			return;
		}
		
		saldo -=cantidad;
		archivo.seek(offset);
		archivo.writeDouble(saldo);
		logger.info("Retiro en la cuenta "+numCuenta+" de "+cantidad);
	} 
}
