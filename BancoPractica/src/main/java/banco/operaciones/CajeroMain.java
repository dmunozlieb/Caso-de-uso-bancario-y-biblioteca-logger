package banco.operaciones;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import com.aad.log4j2.Log4j2;
import com.aad.log4j2.Logger2;

import banco.operaciones.cuentabancaria.Cuenta_Bancaria;
import banco.operaciones.excepciones.SaldoMinimo;

public class CajeroMain {

	private static Logger2 logger;
	private RandomAccessFile archivo;
	private static Cuenta_Bancaria cuenta;
	
	public static void main(String[] args) {
		
		logger = new Log4j2("CajeroMain");
		File cuentasBancarias = new File("fichero\\cuentasBancarias.dat");
		try (Scanner scanner = new Scanner(System.in)){
			existeFichero(cuentasBancarias);
			if(vacioFichero(cuentasBancarias)) {
				cuenta = new Cuenta_Bancaria();
				crearCuenta(scanner,cuenta);
			}
		} catch (IOException io) {
			logger.error(io.getMessage());
		}catch (SaldoMinimo saldMin) {
			logger.error(saldMin.getMessage());
		}
		

	}

	public static void existeFichero (File cuentasBancarias) throws IOException {
		if(!cuentasBancarias.exists()) {
			cuentasBancarias.createNewFile();
			logger.info("Fichero del banco generado en: "+cuentasBancarias.getCanonicalPath());
		}
	}
	
	public static boolean vacioFichero(File cuentasBancarias){
		 return cuentasBancarias.length() == 0;
	}
	
	public static String mensajeIntroduccion() {
		StringBuilder sb = new StringBuilder();
		sb.append("Para la funcionalidad del banco ");
		sb.append("se requiere de la cantidad mínima ");
		sb.append("de una cuenta registrada...");
		return sb.toString();
	}
	
	public static void crearCuenta(Scanner scanner, Cuenta_Bancaria cuenta) throws SaldoMinimo {
		System.out.println(mensajeIntroduccion());
		System.out.println("Empezando creación de cuenta: ");
		long numCuenta = Cuenta_Bancaria.solicitarNumCuenta(scanner);
		System.out.print("\nNombre del titular: ");
		String nombreTitular = scanner.next();
		cuenta.setTitular(nombreTitular);
		logger.warn("Deposita una cantidad mínima > 0");
		System.out.print("\nDeposita un saldo mínimo: ");
		double saldo = scanner.nextDouble();
		if(saldo<=0) throw new SaldoMinimo("Saldo mínimo > 0");
		cuenta.setSaldo(saldo);
		System.out.print("\nPin de la cuenta: ");
		int pin = scanner.nextInt();
		cuenta.setPin(pin);
	}
}
