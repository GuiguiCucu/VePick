package Requetes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Location {

	public static void locationAbonne(Connection conn, int numCLient, int numVelo, int numStation) throws SQLException {
		PreparedStatement stLocAbo = conn
				.prepareStatement("INSERT INTO Location values (?, ?, sysdate ,NULL, ?, NULL);");
		stLocAbo.setInt(1, numCLient);
		stLocAbo.setInt(2, numVelo);
		String typeStation = Station.getTypeStation(conn, numStation);
		stLocAbo.setString(3, typeStation);
		stLocAbo.executeUpdate();
	}

	public static void locationNonAbonne() {
		
	}

}
