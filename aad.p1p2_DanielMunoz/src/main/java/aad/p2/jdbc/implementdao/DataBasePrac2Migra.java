package aad.p2.jdbc.implementdao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import aad.p2.jdbc.DatabaseConnection;

/**
 * Esta clase se encarga de insertar los datos por defecto Y crear las 3 tablas
 * mínimas en la base de datos prac2migra
 *
 * @author Daniel
 */

public class DataBasePrac2Migra {

	private static Connection conn;
	private static final String INSERT_CUENTA = "insert into cuenta_migra (id_cliente_migra, saldo_migra, tipo_cuenta_migra) values (?,?,?)";
	private static final String INSERT_CLIENTE = "insert into cliente_migra (dni_migra, ciudad_migra) values (?,?)";
	static {
		try {
			conn = DatabaseConnection.getConnectionPrac2migra();
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
		createAnddropTable.addBatch("drop table if exists cuenta_migra;");
		createAnddropTable.addBatch("CREATE TABLE cuenta_migra ("
				+ "id_cuenta_migra INT AUTO_INCREMENT NOT NULL, "
				+ "id_cliente_migra INT NOT NULL, " 
				+ "saldo_migra DECIMAL(10,2), "
				+ "tipo_cuenta_migra VARCHAR(20) NULL, " 
				+ "migrado BOOLEAN DEFAULT FALSE, "
				+ "PRIMARY KEY (id_cuenta_migra)" + ");");
		createAnddropTable.executeBatch();
	}

	private static void crearTablaCliente(Connection conn) throws SQLException {

		Statement createAnddropTable = conn.createStatement();
		createAnddropTable.addBatch("drop table if exists cliente_migra;");
		createAnddropTable.addBatch("create table cliente_migra (" 
				+ "id_cliente_migra Int not null AUTO_INCREMENT, "
				+ "dni_migra varchar(20), "
				+ "ciudad_migra varchar(20), "
				+ "migrado boolean DEFAULT FALSE,"
				+ "PRIMARY KEY (id_cliente_migra)" + ");");
		createAnddropTable.executeBatch();
	}

	private static void crearTablaTransaccion(Connection conn) throws SQLException {
		Statement createAnddropTable = conn.createStatement();
		createAnddropTable.addBatch("drop table if exists transaccion_migra;");
		createAnddropTable.addBatch("create table transaccion_migra ("
				+ "id_transaccion_migra INT not null AUTO_INCREMENT, " 
				+ "id_cuenta_migra INT, "
				+ "tipo_transaccion_migra VARCHAR(20)," 
				+ "cantidad_migra DECIMAL(10, 2)," 
				+ "fecha_migra TIMESTAMP, "
				+ "migrado BOOLEAN DEFAULT FALSE, "
				+ "PRIMARY KEY (id_transaccion_migra)" + ");");
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
			insert.setString(1, GenerarDatosRandoms.generarDNI());
			insert.setString(2, GenerarDatosRandoms.getCiudadRandom());
			insert.addBatch();
		}

		insert.executeBatch();

	}

	public Connection getConn() {
		return conn;
	}

}
