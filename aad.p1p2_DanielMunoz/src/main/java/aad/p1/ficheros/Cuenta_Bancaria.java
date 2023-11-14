package aad.p1.ficheros;

import java.util.Date;


/**
* Esta clase se encarga de crear objetos de tipo
* Cuenta para la practica de ficheros
*
* @author Daniel
*/

public class Cuenta_Bancaria {
	
	private int numCuenta;
	private String titular;
	private double saldo;
	private Date fechaCreacion;
	
	
	
	
	public Cuenta_Bancaria() {
		
		fechaCreacion = new Date();
	}


	public Cuenta_Bancaria(int numCuenta, String titular, double saldo) {
		this();
		this.numCuenta = numCuenta;
		this.titular = titular;
		this.saldo = saldo;
		
	}


	public int getNumCuenta() {
		return numCuenta;
	}


	public void setNumCuenta(int numCuenta) {
		this.numCuenta = numCuenta;
	}


	public String getTitular() {
		return titular;
	}


	public void setTitular(String titular) {
		this.titular = titular;
	}


	public double getSaldo() {
		return saldo;
	}


	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}


	public Date getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
