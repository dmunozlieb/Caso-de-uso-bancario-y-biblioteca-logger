package aad.p2.jdbc.implementdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import aad.p2.jdbc.DatabaseConnection;

/**
 * Esta clase se encarga de insertar los datos por defecto Y crear las 3 tablas
 * mínimas en la base de datos prac2
 *
 * @author Daniel
 */

public class DataBasePrac2 {

	private static Connection conn;
	private static final String INSERT_CLIENTE = "insert into cliente (nombre,apellido,dni,ciudad) values (?,?,?,?)";
	private static final String INSERT_CUENTA = "insert into cuenta (id_cliente, saldo, tipo_cuenta) values (?,?,?)";

	static {
		try {
			conn = DatabaseConnection.getConnectionPrac2();
			crearTablaCliente(conn);
			crearTablaCuenta(conn);
			crearTablaTransaccion(conn);
			insertCuenta(conn);
			insertCliente(conn);
		} catch (SQLException sql) {
			System.err.println(getErrorSql(sql));
			sql.printStackTrace();
		}
	}

	private static String getErrorSql(SQLException sql) {
		StringBuilder sb = new StringBuilder("\nSQL Error Code: " + sql.getErrorCode());
		sb.append("\nSQL State: " + sql.getSQLState());
		sb.append("\nSQL Message " + sql.getMessage());
		return sb.toString();
	}

	private static void crearTablaCuenta(Connection conn) throws SQLException {
		Statement createAnddropTable = conn.createStatement();
		createAnddropTable.addBatch("drop table if exists cuenta;");
		createAnddropTable.addBatch("CREATE TABLE cuenta ("
						+ "id_cuenta INT AUTO_INCREMENT NOT NULL, "
						+ "id_cliente INT NOT NULL, " + "saldo DECIMAL(10,2), " 
						+ "tipo_cuenta VARCHAR(20) NULL, "
						+ "PRIMARY KEY (id_cuenta)" + ");");
		createAnddropTable.executeBatch();
	}

	private static void crearTablaCliente(Connection conn) throws SQLException {
		Statement createAnddropTable = conn.createStatement();
		createAnddropTable.addBatch("drop table if exists cliente;");
		createAnddropTable.addBatch("create table cliente (" 
				+ "id_cliente Int not null AUTO_INCREMENT, "
				+ "nombre varchar(20), " 
				+ "apellido varchar(20), " + "dni varchar(20), " 
				+ "ciudad varchar(20), "
				+ "PRIMARY KEY (id_cliente)" + ");");
		createAnddropTable.executeBatch();

	}

	private static void crearTablaTransaccion(Connection conn) throws SQLException {
		Statement createAnddropTable = conn.createStatement();
		createAnddropTable.addBatch("drop table if exists transaccion;");
		createAnddropTable.addBatch("create table transaccion ("
				+ "id_transaccion INT not null AUTO_INCREMENT, "
				+ "id_cuenta INT, " 
				+ "tipo_transaccion VARCHAR(20),"
				+ "cantidad DECIMAL(10, 2)," 
				+ "fecha TIMESTAMP,"
				+ "PRIMARY KEY (id_transaccion)" + ");");
		createAnddropTable.executeBatch();

	}

	private static void insertCuenta(Connection conn) throws SQLException {
		PreparedStatement insert = conn.prepareStatement(INSERT_CUENTA);
		for (int i = 1; i <= 50; i++) {
			insert.setInt(1, i);
			insert.setDouble(2, GenerarDatosRandoms.getSaldoRandom());
			insert.setString(3, GenerarDatosRandoms.getTIpoCuentaRandom());
			insert.addBatch();

		}
		insert.executeBatch();
	}

	private static void insertCliente(Connection conn) throws SQLException {
		PreparedStatement insert = conn.prepareStatement(INSERT_CLIENTE);
		for (int i = 1; i <= 50; i++) {

			insert.setString(1, "Cliente" + i);
			insert.setString(2, "Apellido" + i);
			insert.setString(3, GenerarDatosRandoms.generarDNI());
			insert.setString(4, GenerarDatosRandoms.getCiudadRandom());
			insert.addBatch();
		}

		insert.executeBatch();

	}

	public Connection getConn() {
		return conn;
	}

	
}
