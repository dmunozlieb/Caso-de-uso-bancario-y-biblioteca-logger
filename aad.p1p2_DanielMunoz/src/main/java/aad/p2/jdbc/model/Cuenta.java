package aad.p2.jdbc.model;

/**
 * Esta clase se encarga de crear objetos Cuenta con sus respectivos atributos *
 * @author Daniel
 */

public class Cuenta {

	private int id_cuenta;
	private int id_cliente;
	private double saldo;
	private String tipoCuenta;

	public Cuenta() {

	}

	public Cuenta(int id_cuenta, int id_cliente, double saldo, String tipoCuenta) {

		this.id_cuenta = id_cuenta;
		this.id_cliente = id_cliente;
		this.saldo = saldo;
		this.tipoCuenta = tipoCuenta;
	}

	public int getId_cuenta() {
		return id_cuenta;
	}

	public void setId_cuenta(int id_cuenta) {
		this.id_cuenta = id_cuenta;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	@Override
	public String toString() {
		return "Cuenta id cuenta = " + id_cuenta + ", id cliente = " + id_cliente + ", saldo=" + saldo + ", Tipo Cuenta = "
				+ tipoCuenta;
	}

	
	
}
