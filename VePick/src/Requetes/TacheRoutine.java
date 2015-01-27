package Requetes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TacheRoutine {

	/**
	 * affichage de la routine d'un vehicule
	 * @param conn
	 * @param numVehicule
	 * @throws SQLException 
	 */
	public static void afficherRoutine(Connection conn, int numVehicule) throws SQLException{
		PreparedStatement stRoutine = conn.prepareStatement("SELECT rang, numTache FROM TacheRoutine WHERE numVehicule = ? ORDER BY rang");
		stRoutine.setInt(1, numVehicule);
		ResultSet rsRoutine = stRoutine.executeQuery();
		while(rsRoutine.next()){
			PreparedStatement stTache = conn.prepareStatement("SELECT nomTache FROM Tache WHERE numTache = ?");
			stTache.setInt(1, rsRoutine.getInt("numTache"));
			ResultSet rsTache = stTache.executeQuery();
			
			if(rsTache.next()){
				System.out.println("\nTache :"+rsTache.getString("nomTache"));
				System.out.println("rang "+rsRoutine.getInt("rang"));
			}
		}
		System.out.println("\n-----------------------------------");
	}
}
