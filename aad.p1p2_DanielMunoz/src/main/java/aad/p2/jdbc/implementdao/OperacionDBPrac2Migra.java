package aad.p2.jdbc.implementdao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import aad.p1.librerialog.logger.Logger;
import aad.p2.jdbc.dao.OperacionesDAO;
import aad.p2.jdbc.model.*;
/**
 * Esta clase se encarga de realizar las operacions CRUD en la base de datos
 * prac2Migra
 *
 * @author Daniel
 */

public class OperacionDBPrac2Migra implements OperacionesDAO {
	private Logger logger;
	private DataBasePrac2Migra dbPrac2Migra;
	private Cuenta cuenta;
	private PreparedStatement sentencia;
	private ResultSet resultConsulta;

	public OperacionDBPrac2Migra() {
		dbPrac2Migra = new DataBasePrac2Migra();
		logger = new Logger("TransaccionDBPrac2Migra");
	}

	@Override
	public int contarRegistros(String tabla) throws SQLException {
		String sql = "select count(*) from "+tabla;
		sentencia = dbPrac2Migra.getConn().prepareStatement(sql);
		resultConsulta = sentencia.executeQuery();
		if(resultConsulta.next()) {
			return resultConsulta.getInt(1);
		}
		return 0;
	}
	@Override
	public Cuenta get(int id) throws SQLException {
		if (existCuenta(id)) {
			cuenta = new Cuenta();
			sentencia = dbPrac2Migra.getConn().prepareStatement("select * from cuenta_migra where id_cuenta_migra = ?");
			sentencia.setInt(1, id);
			resultConsulta = sentencia.executeQuery();
			while (resultConsulta.next()) {
				cuenta.setId_cuenta(id);
				cuenta.setId_cliente(resultConsulta.getInt("id_cliente_migra"));
				cuenta.setSaldo(resultConsulta.getDouble("saldo_migra"));
				cuenta.setTipoCuenta(resultConsulta.getString("tipo_cuenta_migra"));
			}
			return cuenta;
		}
		return null;
	}

	@Override
	public void insert(Cuenta cuenta, boolean esMigrado) throws Exception {
		if (cuenta != null && existCliente(cuenta.getId_cliente())) {
			sentencia = dbPrac2Migra.getConn().prepareStatement(
					"insert into cuenta_migra (id_cliente_migra, saldo_migra, tipo_cuenta_migra, migrado) values (?,?,?,?)");
			sentencia.setInt(1, cuenta.getId_cliente());
			sentencia.setDouble(2, cuenta.getSaldo());
			sentencia.setString(3, cuenta.getTipoCuenta());
			sentencia.setBoolean(4, esMigrado);
			sentencia.executeUpdate();
			return;
		}

		logger.error("La cuenta" + cuenta.getId_cuenta() + "ya existe en la base de datos o es null");

	}

	@Override
	public void insert(Cliente cliente, boolean esMigrado) throws SQLException {
		if (cliente != null) {
			sentencia = dbPrac2Migra.getConn()
					.prepareStatement("insert into cliente_migra (dni_migra,ciudad_migra,migrado) values (?,?,?)");
			sentencia.setString(1, cliente.getDni());
			sentencia.setString(2, cliente.getCiudad());
			sentencia.setBoolean(3, esMigrado);
			sentencia.executeUpdate();
		}
	}

	@Override
	public void insert(Transaccion transaccion, boolean esMigrado) throws SQLException {
		if (transaccion != null && existCuenta(transaccion.getId_cuenta())) {
			sentencia = dbPrac2Migra.getConn()
					.prepareStatement("insert into transaccion_migra "
							+ "(id_cuenta_migra, tipo_transaccion_migra, cantidad_migra, fecha_migra, migrado) "
							+ "values(?,?,?,?,?)");
			sentencia.setInt(1, transaccion.getId_cuenta());
			sentencia.setString(2, transaccion.getTipoTransaccion());
			sentencia.setDouble(3, transaccion.getMonto());
			sentencia.setTimestamp(4, transaccion.getFecha());
			sentencia.setBoolean(5, esMigrado);
			sentencia.executeUpdate();
				
		}

	}

	@Override
	public void update(Cuenta cuenta, boolean esMigrado) throws SQLException {
		sentencia = dbPrac2Migra.getConn()
				.prepareStatement("update cuenta_migra set saldo_migra = ? where id_cuenta_migra = ?");
		sentencia.setDouble(1, cuenta.getSaldo());
		sentencia.setInt(2, cuenta.getId_cuenta());
		int filasActualizadas = sentencia.executeUpdate();

		if (filasActualizadas > 0) {
			logger.info("Cuenta " + cuenta.getId_cuenta() + " actualizada en pract2Migra!");
			return;
		}
		logger.warn("AVISO: No se ha actualizado ninguna cuenta");
	}

	@Override
	public void deleteByid(int id) throws SQLException {
		sentencia = dbPrac2Migra.getConn().prepareStatement("delete from cuenta_migra where id_cuenta_migra = ?");
		sentencia.setInt(1, id);
		int filasBorradas = sentencia.executeUpdate();
		if (filasBorradas > 0) {
			logger.info("Se ha borrado la cuenta " + id + " de pract2Migra");
			return;
		}
		logger.warn("AVISO, el id introducido no se encuentra registrado en la Base de datos pract2Migra");
	}

	@Override
	public boolean existCuenta(int id) throws SQLException {
		sentencia = dbPrac2Migra.getConn().prepareStatement("select * from cuenta_migra where id_cuenta_migra = ?");
		sentencia.setInt(1, id);
		resultConsulta = sentencia.executeQuery();
		if (resultConsulta.next() && resultConsulta.getInt(1) > 0)
			return true;

		return false;
	}

	@Override
	public boolean existCliente(int id) throws Exception {
		sentencia = dbPrac2Migra.getConn().prepareStatement("select * from cliente_migra where id_cliente_migra = ?");
		sentencia.setInt(1, id);
		resultConsulta = sentencia.executeQuery();
		if (resultConsulta.next() && resultConsulta.getInt(1) > 0)
			return true;

		return false;
	}

	@Override
	public List<Cuenta> findAllCuenta() throws SQLException {
		ArrayList<Cuenta> listaCuentas = new ArrayList<>();
		sentencia = dbPrac2Migra.getConn().prepareStatement("select * from cuenta_migra");
		resultConsulta = sentencia.executeQuery();
		while (resultConsulta.next()) {
			cuenta = new Cuenta();
			cuenta.setId_cuenta(resultConsulta.getInt("id_cuenta_migra"));
			cuenta.setId_cliente(resultConsulta.getInt("id_cliente_migra"));
			cuenta.setSaldo(resultConsulta.getDouble("saldo_migra"));
			cuenta.setTipoCuenta(resultConsulta.getString("tipo_cuenta_migra"));
			listaCuentas.add(cuenta);
		}
		return listaCuentas;
	}

	@Override
	public List<Cuenta> findCuentaByAttributes(String tipo_cuenta) throws SQLException {
		ArrayList<Cuenta> listaCuentas = new ArrayList<>();
		sentencia = dbPrac2Migra.getConn().prepareStatement("select * from cuenta_migra where tipo_cuenta_migra = ?");
		sentencia.setString(1, tipo_cuenta);
		resultConsulta = sentencia.executeQuery();
		while (resultConsulta.next()) {
			cuenta = new Cuenta();
			cuenta.setId_cuenta(resultConsulta.getInt("id_cuenta_migra"));
			cuenta.setId_cliente(resultConsulta.getInt("id_cliente_migra"));
			cuenta.setSaldo(resultConsulta.getDouble("saldo_migra"));
			cuenta.setTipoCuenta(resultConsulta.getString("tipo_cuenta_migra"));
			listaCuentas.add(cuenta);
		}
		return listaCuentas;
	}

	@Override
	public List<Transaccion> findAllTransaccion() throws SQLException {
		ArrayList<Transaccion> listaTransacciones = new ArrayList<>();
		sentencia = dbPrac2Migra.getConn().prepareStatement("select * from transaccion_migra");
		resultConsulta = sentencia.executeQuery();
		while (resultConsulta.next()) {
			Transaccion transaccion = new Transaccion();
			transaccion.setId_transaccion(resultConsulta.getInt("id_transaccion_migra"));
			transaccion.setId_cuenta(resultConsulta.getInt("id_cuenta_migra"));
			transaccion.setMonto(resultConsulta.getDouble("cantidad_migra"));
			transaccion.setTipoTransaccion(resultConsulta.getString("tipo_transaccion_migra"));
			transaccion.setFecha(resultConsulta.getTimestamp("fecha_migra"));
			listaTransacciones.add(transaccion);
		}
		return listaTransacciones;
	}

	@Override
	public List<Cliente> findAllCliente() throws SQLException {
		ArrayList<Cliente> listaCliente = new ArrayList<>();
		sentencia = dbPrac2Migra.getConn().prepareStatement("select * from cliente_migra");
		resultConsulta = sentencia.executeQuery();
		while (resultConsulta.next()) {
			Cliente cliente = new Cliente();
			cliente.setId(resultConsulta.getInt("id_cliente"));
			cliente.setDni(resultConsulta.getString("dni"));
			cliente.setCiudad(resultConsulta.getString("ciudad"));
			listaCliente.add(cliente);
		}
		return listaCliente;
	}

}
