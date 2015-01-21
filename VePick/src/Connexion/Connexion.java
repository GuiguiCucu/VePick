package Connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {

	static Connection conn = null;
	private static final String configurationFile = "BD.properties";

	private Connexion() {
	}

	public static Connection getConnexion() {
		if (conn == null) {
			try {
				String jdbcDriver, dbUrl, username, password;
				DatabaseAccessProperties dap = new DatabaseAccessProperties(
						configurationFile);
				jdbcDriver = dap.getJdbcDriver();
				dbUrl = dap.getDatabaseUrl();
				username = dap.getUsername();
				password = dap.getPassword();
				Class.forName(jdbcDriver);
				conn = DriverManager.getConnection(dbUrl, username, password);
				SQLWarningsExceptions.printWarnings(conn);
				// java.sql.Date dateRep = new java.sql.Date(2009-1900,1-1,9);
				// RequeteTickets.reservePlaceFromCatAndDateRep(conn, 2,
				// dateRep);
			} catch (SQLException se) {
				SQLWarningsExceptions.printExceptions(se);
			} catch (Exception e) {
				System.err.println("Exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
		return conn;
	}

	public static void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
