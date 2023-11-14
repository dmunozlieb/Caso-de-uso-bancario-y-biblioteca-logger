package aad.p1.librerialog.logger;

/**
* Esta clase se encarga de implementar los metodos de cada nivel de seguridad
*  y separarlo de la clase configLog
*
* @author Daniel
*/

public class Logger {

	private ConfigLog configuracion;
	private static final String pathXML = "configlog\\configlog.xml";
	private String classMain;
	
	public Logger(String classMain) {
		this.classMain = classMain;
		this.configuracion = new ConfigLog(pathXML);
	}

	public void trace(String message) {
		configuracion.log(message, "trace",classMain);
	}

	public void debug(String message) {
		configuracion.log(message, "debug",classMain);
	}

	public void info(String message) {
		configuracion.log(message, "info",classMain);
	}

	public void warn(String message) {
		configuracion.log(message, "warn",classMain);
	}

	public void error(String message) {
		configuracion.log(message, "error",classMain);
	}
}
