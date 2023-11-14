package aad.p2.jdbc;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;

import aad.p1.librerialog.logger.Logger;
import aad.p2.jdbc.dao.OperacionesDAO;
import aad.p2.jdbc.implementdao.GenerarDatosRandoms;
import aad.p2.jdbc.implementdao.MigracionDataBase;
import aad.p2.jdbc.implementdao.OperacionDBPrac2;
import aad.p2.jdbc.implementdao.OperacionDBPrac2Migra;
import aad.p2.jdbc.model.Cuenta;
import aad.p2.jdbc.model.GestorTransaccionesJDBC;

public class EjecutarJDBC {
	
	private static Logger logger;
	private static int id_cuenta_depositar = 9;
	private static int id_cuenta_retirar = 23;
	private static int id_cuenta_consultar = 50;
	private static int id_cuenta_eliminar = 2;

	private static String getErrorSql(SQLException sql) {
		StringBuilder sb = new StringBuilder("\nSQL Error Code: " + sql.getErrorCode());
		sb.append("\nSQL State: " + sql.getSQLState());
		sb.append("\nSQL Message " + sql.getMessage());
		return sb.toString();
		}
	
	public static void ejecutarJDBC() {
		logger = new Logger("MainPrac2jdbc");
		OperacionesDAO dataBase_pract2 = new OperacionDBPrac2();
		OperacionesDAO dataBase_pract2Migra = new OperacionDBPrac2Migra();

		try {
			// Base de datos pract2
			System.out.println(" - - - Operaciones en la base de datos PRACT2 - - - \n");
			GestorTransaccionesJDBC.operacionDeposito(dataBase_pract2, id_cuenta_depositar, logger);
			logger.info("NUEVA TRANSACCION REGISTRADA EN PRACT2");
			GestorTransaccionesJDBC.operacionRetiro(dataBase_pract2, id_cuenta_retirar, logger);
			logger.info("NUEVA TRANSACCION REGISTRADA EN PRACT2");
			GestorTransaccionesJDBC.consultarSaldo(dataBase_pract2, id_cuenta_consultar, logger);
			logger.info("NUEVA TRANSACCION REGISTRADA EN PRACT2");
			System.out.println();
			System.out.println(" - - - Operaciones en la base de datos PRACT2MIGRA - - - \n");
			// Base de datos pract2Migra
			GestorTransaccionesJDBC.operacionDeposito(dataBase_pract2Migra, id_cuenta_depositar, logger);
			logger.info("NUEVA TRANSACCION REGISTRADA EN PRACT2MIGRA");
			GestorTransaccionesJDBC.operacionRetiro(dataBase_pract2Migra, id_cuenta_retirar, logger);
			logger.info("NUEVA TRANSACCION REGISTRADA EN PRACT2MIGRA");
			GestorTransaccionesJDBC.consultarSaldo(dataBase_pract2Migra, id_cuenta_consultar, logger);
			logger.info("NUEVA TRANSACCION REGISTRADA EN PRACT2MIGRA");

			/*
			 * Mostrar un tipo específico de cuenta en la base de datos que se quiera
			 * realizar Lo pasamos como argumento
			 */

			imprimirTipoCuenta(dataBase_pract2Migra);
			System.out.println();
			GestorTransaccionesJDBC.eliminarCuenta(dataBase_pract2Migra, id_cuenta_eliminar);
			Cuenta cuenta = new Cuenta(4, 3, 2000.00, GenerarDatosRandoms.getTIpoCuentaRandom());
			GestorTransaccionesJDBC.insertCuenta(dataBase_pract2Migra, cuenta);
			logger.info("Cuenta registrada en pract2 correctamente!");
			MigracionDataBase.migrateDB(dataBase_pract2, dataBase_pract2Migra);
			logger.info("Migración realizado con éxito");
		} catch (SQLException e) {
			logger.error(getErrorSql(e));
			e.printStackTrace();
		} catch (InputMismatchException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (NullPointerException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}finally {
			DatabaseConnection.cerrarPools();
		}

		}
	
	public static void imprimirTipoCuenta(OperacionesDAO operacion) throws Exception {
		String tipo_Cuenta = GenerarDatosRandoms.getTIpoCuentaRandom();
		List<Cuenta> listTipoCuenta = operacion.findCuentaByAttributes(tipo_Cuenta);
		System.out.println("\n" + tipo_Cuenta + ": ");
		for (Cuenta cuenta1 : listTipoCuenta) {
			System.out.println(cuenta1.toString());
		}
	}
	}

