package aad.p2.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
* Esta clase se encarga de crear la conectividad a través de pools
* a las bases de datos
*
* @author Daniel
*/


public abstract class DatabaseConnection {

	private static HikariDataSource dataSourceDB;
	private static HikariDataSource dataSourceDB2;
	
	static {
		HikariConfig configDB1 = new HikariConfig();
		configDB1.setJdbcUrl("jdbc:mysql://localhost:3306/prac2");
		configDB1.setUsername("root");
		configDB1.setPassword("root");
		configDB1.setMaximumPoolSize(10);
		
		HikariConfig configDB2 = new HikariConfig();
		configDB2.setJdbcUrl("jdbc:mysql://localhost:3306/prac2migra");
		configDB2.setUsername("root");
		configDB2.setPassword("root");
		configDB2.setMaximumPoolSize(10);
		
		dataSourceDB = new HikariDataSource(configDB1);
		dataSourceDB2 = new HikariDataSource(configDB2);
	}
	
	public static Connection getConnectionPrac2() throws SQLException {
		
		return dataSourceDB.getConnection();
	}
	
	public static Connection getConnectionPrac2migra() throws SQLException {
		
		return dataSourceDB2.getConnection();
	}
	
	public static void cerrarPools() {
		if(dataSourceDB != null) {
			dataSourceDB.close();
		}
		if(dataSourceDB2 != null) {
			dataSourceDB2.close();
		}
	}
}
