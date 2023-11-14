package aad.p2.jdbc.implementdao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import aad.p1.librerialog.logger.Logger;
import aad.p2.jdbc.dao.OperacionesDAO;
import aad.p2.jdbc.model.*;

/**
 * Esta clase se encarga de realizar las operacions CRUD en la base de datos
 * prac2
 *
 * @author Daniel
 */

public class OperacionDBPrac2 implements OperacionesDAO {

	private Logger logger;
	private DataBasePrac2 dbPrac2;
	private Cuenta cuenta;
	private PreparedStatement sentencia;
	private ResultSet resultConsulta;

	public OperacionDBPrac2() {
		dbPrac2 = new DataBasePrac2();
		logger = new Logger("TransaccionDBPrac2");
	}
	
	@Override
	public int contarRegistros(String tabla) throws SQLException {
		String sql = "select count(*) from "+tabla;
		sentencia = dbPrac2.getConn().prepareStatement(sql);
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
			sentencia = dbPrac2.getConn().prepareStatement("select * from cuenta where id_cuenta = ?");
			sentencia.setInt(1, id);
			resultConsulta = sentencia.executeQuery();
			while (resultConsulta.next()) {
				cuenta.setId_cuenta(id);
				cuenta.setId_cliente(resultConsulta.getInt("id_cliente"));
				cuenta.setSaldo(resultConsulta.getDouble("saldo"));
				cuenta.setTipoCuenta(resultConsulta.getString("tipo_cuenta"));
			}
			return cuenta;
		}
		return null;
	}

	@Override
	public void insert(Cuenta cuenta, boolean esMigrado) throws Exception {
		if (cuenta != null && existCliente(cuenta.getId_cliente())) {
			sentencia = dbPrac2.getConn().prepareStatement(
					"insert into cuenta (id_cliente, saldo, tipo_cuenta) values (?,?,?)");
			sentencia.setInt(1, cuenta.getId_cliente());
			sentencia.setDouble(2, cuenta.getSaldo());
			sentencia.setString(3, cuenta.getTipoCuenta());
			sentencia.executeUpdate();
			return;
			}
			
		logger.warn("No se puede registrar una cuenta con id cliente no almacenado en la tabla clientes!");
}

	@Override
	public void insert(Cliente cliente, boolean esMigrado) throws SQLException {
		if (cliente != null) {
			sentencia = dbPrac2.getConn()
					.prepareStatement("insert into cliente (nombre,apellido,dni,ciudad) values (?,?,?,?)");
			sentencia.setString(1, cliente.getNombre());
			sentencia.setString(2, cliente.getApellido());
			sentencia.setString(3, cliente.getDni());
			sentencia.setString(4, cliente.getCiudad());
			sentencia.executeUpdate();
		}
	}

	@Override
	public void insert(Transaccion transaccion, boolean esMigrado) throws SQLException {
		if (transaccion != null && existCuenta(transaccion.getId_cuenta())) {
			sentencia = dbPrac2.getConn().prepareStatement(
					"insert into transaccion (id_cuenta, tipo_transaccion, cantidad, fecha) values(?,?,?,?)");
			sentencia.setInt(1, transaccion.getId_cuenta());
			sentencia.setString(2, transaccion.getTipoTransaccion());
			sentencia.setDouble(3, transaccion.getMonto());
			sentencia.setTimestamp(4, transaccion.getFecha());
			sentencia.executeUpdate();
		}

	}

	@Override
	public void update(Cuenta cuenta, boolean esMigrado) throws SQLException {
		if(existCuenta(cuenta.getId_cuenta())) {
			sentencia = dbPrac2.getConn().prepareStatement("update cuenta set saldo = ? where id_cuenta = ?");
			sentencia.setDouble(1, cuenta.getSaldo());
			sentencia.setInt(2, cuenta.getId_cuenta());
			sentencia.executeUpdate();
			logger.info("Cuenta " + cuenta.getId_cuenta() + " actualizada en pract2!");
			return;
		}
		
		logger.warn("AVISO: No se ha actualizado ninguna cuenta");
	}

	@Override
	public void deleteByid(int id) throws SQLException {
		if(existCuenta(id)) {
			sentencia = dbPrac2.getConn().prepareStatement("delete from cuenta where id_cuenta = ?");
			sentencia.setInt(1, id);
			sentencia.executeUpdate();
			logger.info("Se ha borrado la cuenta " + id + " de pract2");
			return;
		}
		
		logger.warn("AVISO, el id introducido no se encuentra registrado en la Base de datos pract2");
	}

	@Override
	public boolean existCuenta(int id) throws SQLException {
		sentencia = dbPrac2.getConn().prepareStatement("select * from cuenta where id_cuenta = ?");
		sentencia.setInt(1, id);
		resultConsulta = sentencia.executeQuery();
		if (resultConsulta.next() && resultConsulta.getInt(1) > 0)
			return true;

		return false;
	}

	@Override
	public boolean existCliente(int id) throws Exception {
		sentencia = dbPrac2.getConn().prepareStatement("select * from cliente where id_cliente = ?");
		sentencia.setInt(1, id);
		resultConsulta = sentencia.executeQuery();
		if (resultConsulta.next() && resultConsulta.getInt(1) > 0)
			return true;

		return false;
	}

	@Override
	public List<Cuenta> findAllCuenta() throws SQLException {
		ArrayList<Cuenta> listaCuentas = new ArrayList<>();
		sentencia = dbPrac2.getConn().prepareStatement("select * from cuenta");
		resultConsulta = sentencia.executeQuery();
		while (resultConsulta.next()) {
			cuenta = new Cuenta();
			cuenta.setId_cuenta(resultConsulta.getInt("id_cuenta"));
			cuenta.setId_cliente(resultConsulta.getInt("id_cliente"));
			cuenta.setSaldo(resultConsulta.getDouble("saldo"));
			cuenta.setTipoCuenta(resultConsulta.getString("tipo_cuenta"));
			listaCuentas.add(cuenta);
		}
		return listaCuentas;
	}

	@Override
	public List<Cuenta> findCuentaByAttributes(String tipo_cuenta) throws SQLException {
		ArrayList<Cuenta> listaCuentas = new ArrayList<>();
		sentencia = dbPrac2.getConn().prepareStatement("select * from cuenta where tipo_cuenta = ?");
		sentencia.setString(1, tipo_cuenta);
		resultConsulta = sentencia.executeQuery();
		while (resultConsulta.next()) {
			cuenta = new Cuenta();
			cuenta.setId_cuenta(resultConsulta.getInt("id_cuenta"));
			cuenta.setId_cliente(resultConsulta.getInt("id_cliente"));
			cuenta.setSaldo(resultConsulta.getDouble("saldo"));
			cuenta.setTipoCuenta(resultConsulta.getString("tipo_cuenta"));
			listaCuentas.add(cuenta);
		}
		return listaCuentas;
	}

	@Override
	public List<Transaccion> findAllTransaccion() throws SQLException {
		ArrayList<Transaccion> listaTransacciones = new ArrayList<>();
		sentencia = dbPrac2.getConn().prepareStatement("select * from transaccion");
		resultConsulta = sentencia.executeQuery();
		while (resultConsulta.next()) {
			Transaccion transaccion = new Transaccion();
			transaccion.setId_transaccion(resultConsulta.getInt("id_transaccion"));
			transaccion.setId_cuenta(resultConsulta.getInt("id_cuenta"));
			transaccion.setMonto(resultConsulta.getDouble("cantidad"));
			transaccion.setTipoTransaccion(resultConsulta.getString("tipo_transaccion"));
			transaccion.setFecha(resultConsulta.getTimestamp("fecha"));
			listaTransacciones.add(transaccion);
		}
		return listaTransacciones;
	}

	@Override
	public List<Cliente> findAllCliente() throws SQLException {
		ArrayList<Cliente> listaCliente = new ArrayList<>();
		sentencia = dbPrac2.getConn().prepareStatement("select * from cliente");
		resultConsulta = sentencia.executeQuery();
		while (resultConsulta.next()) {
			Cliente cliente = new Cliente();
			cliente.setId(resultConsulta.getInt("id_cliente"));
			cliente.setNombre(resultConsulta.getString("nombre"));
			cliente.setApellido(resultConsulta.getString("apellido"));
			cliente.setDni(resultConsulta.getString("dni"));
			cliente.setCiudad(resultConsulta.getString("ciudad"));
			listaCliente.add(cliente);
		}
		return listaCliente;
	}
}
