package aad.p1.librerialog.logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import aad.p1.librerialog.appender.Consola;
import aad.p1.librerialog.appender.Fichero;
import aad.p1.librerialog.pattern.Level;
import aad.p1.librerialog.pattern.PatternLayout;

/**
* Esta clase se encarga de realizar el parse del xml
* y almacenar los atributos necesarios para configurarlo
*
* @author Daniel
*/

public class ConfigLog {

	private Consola console;
	private Fichero fichero;
	private String levelLimite;
	private List<String> refAppender = new ArrayList<>();

	public ConfigLog(String xml) {
		Level.agregaElementos();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document documento = builder.parse(xml);
			documento.getDocumentElement().normalize();
			Element root = documento.getDocumentElement();
			parseAppenders(root);
			parseLoggers(root);
		} catch (ParserConfigurationException parser) {
			System.err.println(parser.getMessage());
		} catch (SAXException sax) {
			System.err.println(sax.getMessage());
		} catch (IOException io) {
			System.err.println(io.getMessage());
		}
	}

	public void log(String message, String level, String classMain) {
		if (Level.intValue(level) < Level.intValue(levelLimite))
			return;
		if (refAppender.contains(console.getNombre()))
			console.append(PatternLayout.format(message, level, console.getPattern(),classMain));
		if (refAppender.contains(fichero.getNombre()))
			fichero.append(PatternLayout.format(message, level, fichero.getPattern(),classMain));

	}

	private String obtenerAtributo(Element appender, String nombreAtributo) {

		return appender.getAttribute(nombreAtributo);
	}

	private void parseAppenders(Element root) {
		NodeList nodosHijos = root.getElementsByTagName("Appenders");
		NodeList appenderNodes = nodosHijos.item(0).getChildNodes();
		for (int i = 0; i < appenderNodes.getLength(); i++) {
			Node nodo = appenderNodes.item(i);
			if (nodo.getNodeType() == Node.ELEMENT_NODE) {
				Element appender = (Element) nodo;
				String nombre;
				String pattern;
				switch (appender.getTagName()) {
				case "Consola":
					nombre = obtenerAtributo(appender, "nombre");
					NodeList childConsola = appender.getChildNodes();
					Element patternLayout = (Element) childConsola.item(1);
					pattern = patternLayout.getAttribute("pattern");
					console = new Consola(nombre, pattern);
					break;
				case "Fichero":
					nombre = obtenerAtributo(appender, "nombre");
					String maxSize = obtenerAtributo(appender, "maxSize");
					String nombreFichero = obtenerAtributo(appender, "nombreFichero");
					String ficheroRotacion = obtenerAtributo(appender, "ficheroRotacion");
					NodeList childFile = appender.getChildNodes();
					Element patternLayout2 = (Element) childFile.item(1);
					pattern = patternLayout2.getAttribute("pattern");
					fichero = new Fichero(nombreFichero, nombre, pattern, maxSize, ficheroRotacion);
					
				}
			}
		}
	}

	private void parseLoggers(Element root) {
		NodeList nodosHijosLogger = root.getElementsByTagName("Loggers");
		NodeList loggerNodes = nodosHijosLogger.item(0).getChildNodes();
		Element logger = (Element) loggerNodes.item(1);
		levelLimite = logger.getAttribute("nivel");
		NodeList refNodes = logger.getChildNodes();
		for (int i = 0; i < refNodes.getLength(); i++) {
			Node nodoRef = refNodes.item(i);
			if (nodoRef.getNodeType() == Node.ELEMENT_NODE) {
				Element ref = (Element) refNodes.item(i);
				refAppender.add(ref.getAttribute("ref"));
			}

		}
	}

}
