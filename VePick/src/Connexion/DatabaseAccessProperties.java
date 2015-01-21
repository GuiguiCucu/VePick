package Connexion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * The BenchmarkProperties class describes the properties of the benchmark to
 * run.
  *
  * @version 1
  * @author <a href="mailto:Sara.Bouchenak@imag.fr">Sara Bouchenak</a>
  * @version 1.0, 29/01/2013
 * 
 */
public class DatabaseAccessProperties {

	private Properties prop = new Properties();
	
	private String jdbcDriver;
	private String dbUrl;
	private String username, password;

	public DatabaseAccessProperties(String propertiesFile) {

		try {
			prop = new Properties();
			prop.load(new FileInputStream(propertiesFile));
		} catch (FileNotFoundException e) {
		      System.err.println( "FileNotFoundException: " + e.getMessage()) ;
		      e.printStackTrace();
		      return;
		} catch (IOException e) {
		      System.err.println( "IOException: " + e.getMessage()) ;
		      e.printStackTrace();
		      return;
		}
		
		jdbcDriver 	= prop.getProperty("jdbc.driver");
		dbUrl 	= prop.getProperty("database.url");
		username 	= prop.getProperty("database.username");
		password 	= prop.getProperty("database.password");

	}

	/**
	 * Return the JDBC driver.
	 * 
	 * @return JDBC driver
	 */
	public String getJdbcDriver() {
		return jdbcDriver;
	}

	/**
	 * Return the database URL.
	 * 
	 * @return database URL
	 */
	public String getDatabaseUrl() {
		return dbUrl;
	}

	/**
	 * Return the username of the database.
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Return the user password of the database.
	 * 
	 * @return user password
	 */
	public String getPassword() {
		return password;
	}
	


}
