package Requetes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Velo {
	
	/**
	 * affiche tous les velos
	 * @param conn
	 * @throws SQLException
	 */
	public static void afficherVelos(Connection conn) throws SQLException{
		PreparedStatement stVelos = conn.prepareStatement("SELECT * FROM Velo");
		ResultSet rsVelos = stVelos.executeQuery();
		while(rsVelos.next()){
			System.out.println("------");
			System.out.println("-Velo numero : "+rsVelos.getInt("numVelo"));
			System.out.println("-Modele : "+rsVelos.getString("modele"));
			System.out.println("-Mise en service : "+new java.util.Date(rsVelos.getTimestamp("dateMiseEnService").getTime()));
			System.out.println("-Etat : "+rsVelos.getString("etat"));
			System.out.println("------");
		}
		stVelos.close();
		rsVelos.close();
	}
	
	/**
	 * Affiche tous les velos HS
	 * @param conn
	 * @throws SQLException
	 */
	public static void afficherVelosHS(Connection conn) throws SQLException{
		PreparedStatement stVelos = conn.prepareStatement("SELECT * FROM Velo WHERE etat='HS'");
		ResultSet rsVelos = stVelos.executeQuery();
		while(rsVelos.next()){
			System.out.println("------");
			System.out.println("-Velo numero : "+rsVelos.getInt("numVelo"));
			System.out.println("-Modele : "+rsVelos.getString("modele"));
			System.out.println("-Mise en service : "+new java.util.Date(rsVelos.getTimestamp("dateMiseEnService").getTime()));
			System.out.println("-Etat : "+rsVelos.getString("etat"));
			System.out.println("------");
		}
		stVelos.close();
		rsVelos.close();
	}
	
	/**
	 * affichage des velos d'une station
	 * @param conn
	 * @param numStation
	 * @throws SQLException 
	 */
	public static void afficherVeloStation(Connection conn, int numStation) throws SQLException{
		// on récupère toutes les bornettes qui ont un velo dans la station
		PreparedStatement stBornette = conn.prepareStatement("SELECT numVelo FROM Bornette WHERE numStation = ? AND numVelo IS NOT NULL");
		stBornette.setInt(1, numStation);
		ResultSet rsBornette = stBornette.executeQuery();
		while(rsBornette.next()){
			// puis on récupère le vélo de chaque bornette
			PreparedStatement stVelo = conn.prepareStatement("SELECT numVelo, modele, dateMiseEnService, etat FROM Velo WHERE numVelo = ?");
			stVelo.setInt(1, rsBornette.getInt("numVelo"));
			ResultSet rsVelo = stVelo.executeQuery();
			
			if(rsVelo.next()){
				System.out.println("- Velo numero "+rsVelo.getInt("numVelo"));
				System.out.println("- Modele : "+rsVelo.getString("modele"));
				System.out.println("- Mise en service : "+new java.util.Date(rsVelo.getTimestamp("dateMiseEnService").getTime()));
				System.out.println("- Etat : "+rsVelo.getString("etat"));
				System.out.println("------");
			}
			stVelo.close();
			rsVelo.close();
		}
		System.out.println("\n-----------------------------------");
		stBornette.close();
		rsBornette.close();
	}
	
	/**
	 * affiche le nombre de velos dans une station
	 * @param conn
	 * @param numStation
	 * @throws SQLException
	 */
	public static void afficherNombreVelosStation(Connection conn, int numStation) throws SQLException{
		// on recupere les bornettes de la station qui ont un velo
		PreparedStatement stNbVelos = conn.prepareStatement("SELECT numVelo FROM Bornette WHERE numStation = ? AND numVelo IS NOT NULL");
		stNbVelos.setInt(1, numStation);
		ResultSet rsNbVelos = stNbVelos.executeQuery();
		int i=0;
		// on compte chaque resultat
		while(rsNbVelos.next()){
			i++;
		}
		System.out.println("La station "+numStation+" contient "+i+" velos.");
		System.out.println("-----------------------------------");
		stNbVelos.close();
		rsNbVelos.close();
	}
	
	/**
	 * etat d'un velo OK -> HS
	 * @param conn
	 * @param numVelo
	 * @throws SQLException
	 */
	public static void declarerEndommage(Connection conn, int numVelo) throws SQLException{
		PreparedStatement stVelo = conn.prepareStatement("UPDATE Velo SET etat='HS' WHERE numVelo = ?");
		stVelo.setInt(1, numVelo);
		ResultSet rsVelo = stVelo.executeQuery();
		if(rsVelo.next()){
			System.out.println("L'état du velo a été mis à jour de OK à HS.");
		}
		stVelo.close();
		rsVelo.close();
	}
	
	/**
	 * etat d'un velo HS -> OK
	 * @param conn
	 * @param numVelo
	 * @throws SQLException
	 */
	public static void reparerVelo(Connection conn, int numVelo) throws SQLException{
		PreparedStatement stVelo = conn.prepareStatement("UPDATE Velo SET etat='OK' WHERE numVelo = ?");
		stVelo.setInt(1, numVelo);
		ResultSet rsVelo = stVelo.executeQuery();
		if(rsVelo.next()){
			System.out.println("L'état du velo a été mis à jour de HS à OK.");
		}
		stVelo.close();
		rsVelo.close();
	}
}
