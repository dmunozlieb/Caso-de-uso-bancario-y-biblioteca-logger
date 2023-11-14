package aad.p2.jdbc.dao;


import java.util.List;

import aad.p2.jdbc.model.*;

public interface OperacionesDAO {
	
	Cuenta get(int id) throws Exception;
	void insert(Cuenta cuenta,boolean esMigrado) throws Exception;
	void insert(Cliente cliente, boolean esMigrado) throws Exception;
	void insert(Transaccion transaccion,boolean esMigrado) throws Exception;
	void update(Cuenta cuenta,boolean esMigrado) throws Exception;
	void deleteByid(int id) throws Exception;
	int contarRegistros(String tabla) throws Exception;
	boolean existCuenta(int id) throws Exception;
	boolean existCliente(int id) throws Exception;
	List <Cuenta> findAllCuenta() throws Exception;
	List <Cuenta> findCuentaByAttributes(String tipo_cuenta) throws Exception;
	List <Transaccion> findAllTransaccion() throws Exception;
	List <Cliente> findAllCliente() throws Exception;
}
