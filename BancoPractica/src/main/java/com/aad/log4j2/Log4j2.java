package com.aad.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

public class Log4j2 implements Logger2{
	private final Logger logger;
	private static final String PATH_CONFIGLOG = "src\\main\\resources\\configlog.xml";

	public Log4j2(String logName) {
		logger = LogManager.getLogger(logName);
		Configurator.initialize(null, PATH_CONFIGLOG);
	}

	@Override
	public void info(String message) {
		logger.info(message);
	}

	@Override
	public void error(String message) {
		logger.error(message);
	}

	@Override
	public void debug(String message) {
		logger.debug(message);
	}

	@Override
	public void trace(String message) {
		logger.trace(message);

	}

	@Override
	public void warn(String message) {
		logger.warn(message);

	}
}

