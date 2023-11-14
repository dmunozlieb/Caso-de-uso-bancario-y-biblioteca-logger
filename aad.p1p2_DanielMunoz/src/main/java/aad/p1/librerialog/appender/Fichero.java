package aad.p1.librerialog.appender;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import aad.p1.librerialog.pattern.ConversionBytes;

/**
* Esta clase se encarga de crear objetos de tipo Fichero
* para implementar la interfaz Appender, para imprimirlo dentro del fichero
* y tambien se encarga de la rotación
*
* @author Daniel
*/

public class Fichero implements Appender {

	private String nombre;
	private String nombreFichero;
	private String pattern;
	private PrintWriter print;
	private String maxSize;
	private String nmFicheroRotacion;
	private static File ficheroLog;
	private static File directorio;

	public Fichero(String nombreFichero, String nombre, String pattern) {
		this.nombre = nombre;
		this.pattern = pattern;
		this.nombreFichero = nombreFichero;

		try {
			creacionFichero(nombreFichero);
			
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public Fichero(String nombreFichero, String nombre, String pattern, String maxSize, String nmFicheroRotacion) {
		this(nombreFichero, nombre, pattern);
		this.maxSize = maxSize;
		this.nmFicheroRotacion = nmFicheroRotacion;
	}

	public void append(String mensaje) {

		if (superaTamanio()) {
			try {
				rotarFicheroLog();
				vaciarFicheroLog(); 
			} catch (IOException io) {
				io.printStackTrace();
			}

		}
		try {
			print = new PrintWriter(new FileWriter(nombreFichero, true));
			print.write(mensaje);
			print.println();
			print.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public void creacionFichero(String nombreFichero) throws IOException {
		directorio = new File(nombreFichero).getParentFile();
		if (!directorio.exists()) {
			directorio.mkdirs();
		}
		ficheroLog = new File(nombreFichero);
		if (!ficheroLog.exists()) {
			ficheroLog.createNewFile();
		}
	}

	public String getNombre() {
		return nombre;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}

	public String getPattern() {
		return pattern;
	}

	public boolean superaTamanio() {
		
		return ConversionBytes.parseSizeToBytes(maxSize) != -1
				&& ficheroLog.length() > ConversionBytes.parseSizeToBytes(maxSize);
	}

	public void vaciarFicheroLog() throws IOException {
		FileWriter vaciar = new FileWriter(ficheroLog);
		vaciar.close();
	}

	public void rotarFicheroLog() throws IOException {
		File ficheroRotacion = crearFicheroRotacion();

		if(!ficheroRotacion.exists()) {
			ficheroRotacion.createNewFile();
		}
		BufferedReader lectorArchivoLog = new BufferedReader(new FileReader(ficheroLog));
		char[] cbuff = new char[(int) ficheroLog.length()];
		lectorArchivoLog.read(cbuff);
		BufferedWriter writeArchivoRotacion = new BufferedWriter(new FileWriter(ficheroRotacion));
		writeArchivoRotacion.write(cbuff);
		lectorArchivoLog.close();
		writeArchivoRotacion.close();

	}

	public File crearFicheroRotacion() {
		List<String> filesDirectorio = Arrays.asList(directorio.list());
		long numFicherosLog = filesDirectorio.stream().count();
		if (numFicherosLog == 1) {
			File ficheroRot = new File(ficheroLog.getParentFile() + "\\" + nmFicheroRotacion);
			return ficheroRot;
		}
		int indiceExtension = nmFicheroRotacion.lastIndexOf(".");
		String nombreFile = nmFicheroRotacion.substring(0, indiceExtension);
		String extension = nmFicheroRotacion.substring(indiceExtension);
		File ficheroRot = new File(ficheroLog.getParentFile() + "\\" + nombreFile + numFicherosLog + extension);
		return ficheroRot;
	}

	public static File getFicheroLog() {
		return ficheroLog;
	}

}
