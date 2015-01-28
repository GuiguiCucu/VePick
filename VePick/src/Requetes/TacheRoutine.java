package Requetes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TacheRoutine {

	/**
	 * affichage de la routine d'un vehicule
	 * 
	 * @param conn
	 * @param numVehicule
	 * @throws SQLException
	 */
	public static void afficherRoutine(Connection conn, int numVehicule)
			throws SQLException {
		PreparedStatement stRoutine = conn
				.prepareStatement("SELECT rang, numTache, etat FROM TacheRoutine WHERE numVehicule = ? ORDER BY rang");
		stRoutine.setInt(1, numVehicule);
		ResultSet rsRoutine = stRoutine.executeQuery();
		while (rsRoutine.next()) {
			PreparedStatement stTache = conn
					.prepareStatement("SELECT nomTache FROM Tache WHERE numTache = ?");
			stTache.setInt(1, rsRoutine.getInt("numTache"));
			ResultSet rsTache = stTache.executeQuery();

			if (rsTache.next()) {
				System.out.println("\nTache : " + rsTache.getString("nomTache"));
				System.out.println("Rang " + rsRoutine.getInt("rang"));
				System.out.println("Etat : " + rsRoutine.getString("etat"));
			}

			stTache.close();
			rsTache.close();
		}
		System.out.println("\n-----------------------------------");
		stRoutine.close();
		rsRoutine.close();
	}

	/**
	 * permet au conducteur de valider une routine
	 * 
	 * @param conn
	 * @param numVehicule
	 * @throws SQLException
	 */
	public static void MAJRoutine(Connection conn, int numVehicule, int numTache, int rang, String etat) throws SQLException {
		PreparedStatement stTache = conn.prepareStatement("UPDATE TacheRoutine SET etat = ? WHERE numVehicule = ? AND numTache = ? AND rang = ?");
		stTache.setString(1, etat);
		stTache.setInt(2, numVehicule);
		stTache.setInt(3, numTache);
		stTache.setInt(4, rang);
		ResultSet rsTache = stTache.executeQuery();
		if(rsTache.next()){
			System.out.println("L'etat de la tache a été mis a jour : "+etat);
		}
		else{
			System.out.println("ERREUR - Impossible de mettre à jour la tache");
		}
		stTache.close();
		rsTache.close();
	}
}
