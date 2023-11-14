package aad.p2.jdbc.model;

import aad.p1.librerialog.logger.Logger;
import aad.p2.jdbc.dao.OperacionesDAO;

/**
 * Esta clase se encarga de realizar las operacions bancarias que voy a ejecutar
 * desde el main
 *
 * @author Daniel
 */

public class GestorTransaccionesJDBC {

	private static Transaccion transaccion;
	private static Cuenta cuenta;

	public static void operacionDeposito(OperacionesDAO dataBase, int id_cuenta, Logger logger) throws Exception {
		cuenta = dataBase.get(id_cuenta);
		if (cuenta == null) {
			logger.warn("La cuenta con id = " + id_cuenta + " no existe en la base de datos");
			logger.info("No puede realizarse el deposito de dinero de " + id_cuenta + "!");
			return;
		}
		logger.info("Saldo actual de la cuenta " + cuenta.getId_cuenta() + " -> " + cuenta.getSaldo());
		transaccion = new Transaccion();
		double monto = 100;
		if (transaccion.generardepositarDinero(cuenta, monto)) {
			System.out.println("Cuenta " + cuenta.getId_cuenta() + " HA DEPOSITADO -> " + monto);
			System.out.println("Cuenta " + cuenta.getId_cuenta() + " NUEVO SALDO -> " + cuenta.getSaldo());
			dataBase.insert(transaccion, false);
			dataBase.update(cuenta, false);
			return;
		}
		logger.warn("Cantidad mínima a depositar 10 euros");
	}

	public static void operacionRetiro(OperacionesDAO dataBase, int id_cuenta, Logger logger) throws Exception {
		cuenta = dataBase.get(id_cuenta);
		if (cuenta == null) {
			logger.warn("La cuenta con id = " + id_cuenta + " no existe en la base de datos");
			logger.info("No puede realizarse el retiro de dinero de " + id_cuenta + "!");
			return;
		}
		logger.info("Saldo actual de la cuenta " + cuenta.getId_cuenta() + " -> " + cuenta.getSaldo());
		transaccion = new Transaccion();
		double monto = 100;
		if (transaccion.generarretirarDinero(cuenta, monto)) {
			System.out.println("Cuenta " + cuenta.getId_cuenta() + " HA RETIRADO -> " + monto);
			System.out.println("Cuenta " + cuenta.getId_cuenta() + " NUEVO SALDO -> " + cuenta.getSaldo());
			dataBase.insert(transaccion, false);
			dataBase.update(cuenta, false);
			return;
		}
		logger.warn("Cantidad incorrecta a retirar! cantidad>0 && cantidad < saldo de la cuenta");
	}

	public static void consultarSaldo(OperacionesDAO dataBase, int id_cuenta, Logger logger) throws Exception {
		cuenta = dataBase.get(id_cuenta);
		if (cuenta == null) {
			logger.warn("La cuenta con id = " + id_cuenta + " no existe en la base de datos");
			logger.info("No puede realizarse la consulta de dinero de " + id_cuenta + "!");
			return;
		}
		logger.info("Saldo actual de la cuenta " + cuenta.getId_cuenta() + " -> " + cuenta.getSaldo());
		transaccion = new Transaccion();
		transaccion.generarconsultarSaldo(cuenta);
		dataBase.insert(transaccion, false);

	}

	public static void eliminarCuenta(OperacionesDAO dataBase, int id_cuenta) throws Exception {
		dataBase.deleteByid(id_cuenta);
	}

	public static void insertCuenta(OperacionesDAO dataBase, Cuenta cuenta) throws Exception {
		dataBase.insert(cuenta, false);
	}

}
