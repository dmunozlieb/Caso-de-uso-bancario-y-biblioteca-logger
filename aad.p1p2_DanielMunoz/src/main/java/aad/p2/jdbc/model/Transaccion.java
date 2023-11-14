package aad.p2.jdbc.model;


import java.sql.Timestamp;
import java.util.Date;

/**
* Esta clase se encarga de crear objetos de tipo Transaccion
*
* @author Daniel
*/


public class Transaccion {

	private int id_transaccion;
	private int id_cuenta;
	private String tipoTransaccion;
	private double monto;
	private Timestamp fecha;
	
	
	
	public Transaccion() {
		
	}

	public Transaccion(int id_transaccion, int id_cuenta, String tipoTransaccion, double monto, Timestamp fecha) {
		
		this.id_transaccion = id_transaccion;
		this.id_cuenta = id_cuenta;
		this.tipoTransaccion = tipoTransaccion;
		this.monto = monto;
		this.fecha = fecha;
	}

	public int getId_transaccion() {
		return id_transaccion;
	}

	public void setId_transaccion(int id_transaccion) {
		this.id_transaccion = id_transaccion;
	}

	public int getId_cuenta() {
		return id_cuenta;
	}

	public void setId_cuenta(int id_cuenta) {
		this.id_cuenta = id_cuenta;
	}



	public String getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	
	public boolean generardepositarDinero(Cuenta cuenta, double monto) {
		if(monto>=10) {
			cuenta.setSaldo(cuenta.getSaldo()+monto);
			this.setFecha(getTime());
			this.setId_cuenta(cuenta.getId_cuenta());
			this.setMonto(monto);
			this.setTipoTransaccion("Depósito");
			return true;
		}
		return false;
	}
	
	public boolean generarretirarDinero(Cuenta cuenta, double monto) {
		if(monto<cuenta.getSaldo() && monto>0) {
			cuenta.setSaldo(cuenta.getSaldo()-monto);
			this.setFecha(getTime());
			this.setId_cuenta(cuenta.getId_cuenta());
			this.setMonto(monto);
			this.setTipoTransaccion("Retiro");
			return true;
		}
		return false;
	}
	
	public void generarconsultarSaldo(Cuenta cuenta) {
		this.setFecha(getTime());
		this.setId_cuenta(cuenta.getId_cuenta());
		this.setMonto(cuenta.getSaldo());
		this.setTipoTransaccion("Consulta");
	}
	
	private Timestamp getTime() {
		Timestamp time = new Timestamp(new Date().getTime());
		return time;
	}
}
