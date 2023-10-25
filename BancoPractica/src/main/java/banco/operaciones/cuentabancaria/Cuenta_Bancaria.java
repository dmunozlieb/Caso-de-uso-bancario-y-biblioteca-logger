package banco.operaciones.cuentabancaria;

import java.util.Date;
import java.util.Scanner;

public class Cuenta_Bancaria {
	
	private long numCuenta;
	private String titular;
	private double saldo;
	private Date fechaCreacion;
	private int pin;
	
	
	
	
	public Cuenta_Bancaria() {
		
		fechaCreacion = new Date();
	}


	public Cuenta_Bancaria(long numCuenta, String titular, double saldo, int pin) {
		this();
		this.numCuenta = numCuenta;
		this.titular = titular;
		this.saldo = saldo;
		this.pin = pin;
	}


	public long getNumCuenta() {
		return numCuenta;
	}


	public void setNumCuenta(long numCuenta) {
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


	public int getPin() {
		return pin;
	}


	public void setPin(int pin) {
		this.pin = pin;
	}
	
	public static long solicitarNumCuenta(Scanner scanner) {
		System.out.print("\nNúmero de cuenta: ");
		return scanner.nextLong();
	}
	
	public static String solicitarTitular(Scanner scanner) {
		System.out.print("\nNombre de titular: ");
		return scanner.next();
	}
	
	public static double solicitarSaldo(Scanner scanner) {
		double saldo;
	do {	
		System.out.print("\nDeposita un saldo mínimo: ");
	    saldo = scanner.nextDouble();
		if(saldo<=0) {
			  System.out.println("El saldo mínimo debe ser mayor que 0.");
		}
	}while(saldo<=0);
		return saldo;
	}
}
