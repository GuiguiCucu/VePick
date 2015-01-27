package Requetes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehiculeRegulation {
	
	/**
	 * Consultation des vehicules
	 * @param conn
	 * @throws SQLException 
	 */
	public static void afficherVehicules(Connection conn) throws SQLException{
		PreparedStatement stVehicule = conn.prepareStatement("SELECT * FROM VehiculeRegulation");
		ResultSet rsVehicule = stVehicule.executeQuery();
		while(rsVehicule.next()){
			System.out.println("\nVehicule numero "+rsVehicule.getInt("numVehicule"));
			System.out.println("Modele : "+rsVehicule.getString("modele"));
			System.out.println("Capacite : "+rsVehicule.getInt("capacite"));
			System.out.println("Mise en service : "+new java.util.Date(rsVehicule.getTimestamp("dateMiseEnService").getTime()));
			System.out.println("\n-----------------------------------");
		}
	}
}
