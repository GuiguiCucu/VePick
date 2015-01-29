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
	
	/**
	 * permet au superviseur de modifier une routine
	 * 
	 * @param conn
	 * @param numVehicule
	 * @throws SQLException
	 */
	public static void modifierRoutine() throws SQLException {
		
	}

	public static void supprimerRoutine(Connection con, int numVehicule, int numRang) {
		PreparedStatement stTache;
		try {
			stTache = con.prepareStatement("DELETE FROM TacheRoutine WHERE numVehicule = ? AND rang >= ?");
			stTache.setInt(1, numVehicule);		
			stTache.setInt(2, numRang);
			
			ResultSet rsTache = stTache.executeQuery();
			
			if(rsTache.next()){
				System.out.println("La routine a été supprimé à partir du rang : "+numRang);
			}
			else{
				System.out.println("ERREUR - Impossible de supprimer la routine!");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}

	public static void afficherTaches(Connection con) {
		PreparedStatement stTache;
		
		
		
		try {
			stTache = con.prepareStatement("SELECT * FROM Tache ORDER BY numTache");
			ResultSet rsTache = stTache.executeQuery();
			
			if(rsTache.next()){
				System.out.println("num Tache: "+rsTache.getString("numTache"));
				System.out.println("nom Tache: "+rsTache.getString("nomTache"));
				System.out.println("nb Unité: "+rsTache.getString("nbUnite"));
			}
			else{
				System.out.println("ERREUR - Impossible d'afficher les taches!");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	public static void ajouterTacheRoutine(Connection con, int numVehicule, int numTache) {
		int rang = 0;
		PreparedStatement stTache;
		PreparedStatement stTache2;
		try {
			stTache = con.prepareStatement("SELECT MAX(rang) AS rang FROM TacheRoutine WHERE numVehicule = ?");
			stTache.setInt(1, numVehicule);
			
			ResultSet rsTache = stTache.executeQuery();
			
			if(rsTache.next()){
				rang = rsTache.getInt("rang") + 1;
				System.out.println("Prochain rang:" + rang);
			}
			else{
				System.out.println("ERREUR - Impossible de calculer le prochain rang!");
			}

			stTache2 = con.prepareStatement("INSERT INTO TacheRoutine values(?, ?, ?, 'En attente')");
			stTache2.setInt(1, numVehicule);
			stTache2.setInt(2, numTache);
			stTache2.setInt(3, rang);
			
			ResultSet rsTache2 = stTache2.executeQuery();
			
			if(rsTache2.next()){
				System.out.println("Tache routine ajouté au rang:" + rang);
			}
			else{
				System.out.println("ERREUR - Impossible d'ajouter la tache routine!");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

	public static int ajouterTache(Connection con, int numVehicule, String nomTache,int nbUnite) {
		PreparedStatement stTache;
		PreparedStatement stTache2;
		int numTache = 0;
		try {
			
			stTache = con.prepareStatement("SELECT MAX(numTache) AS numTache FROM Tache");
			
			ResultSet rsTache = stTache.executeQuery();
			
			if(rsTache.next()){
				numTache = rsTache.getInt("numTache") + 1;
				System.out.println("Prochain numero de tache:" + numTache);
			}
			else{
				System.out.println("ERREUR - Impossible de calculer le prochain numero de tache!");
			}
			
			
			stTache2 = con.prepareStatement("INSERT INTO Tache values(?, ?, ?)");
			stTache2.setInt(1, numTache);
			stTache2.setString(2, nomTache);
			stTache2.setInt(3, nbUnite);
			
			ResultSet rsTache2 = stTache2.executeQuery();
			
			if(rsTache2.next()){
				System.out.println("Tache ajouté avec le numéro:" + numTache);
			}
			else{
				System.out.println("ERREUR - Impossible d'ajouter la nouvelle tache!");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return numTache;
		
	} 
}
