package Requetes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.spi.DirStateFactory.Result;

public class Station {
	public static void afficherStations(Connection conn) throws SQLException {
		PreparedStatement stStation = conn
				.prepareStatement("SELECT numStation, adresse, cp, ville FROM Station");
		ResultSet rsStation = stStation.executeQuery();

		while (rsStation.next()) {
			PreparedStatement stType = conn
					.prepareStatement("SELECT idType, libelle FROM TypeStation WHERE idType IN  (SELECT idType FROM PlageHoraire WHERE numStation = ? AND trunc(dateDebut) - trunc(sysdate) = 0  )");
			stType.setInt(1, rsStation.getInt("numStation"));
			//DONE
			// SELECT * FROM plagehoraire WHERE trunc(dateDebut) -
			// trunc(sysdate) = 0;
			// INSERT INTO PlageHoraire values (4, 1, 3, TO_DATE('2015/01/28
			// 12:30:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/28
			// 15:30:00', 'yyyy/mm/dd hh24:mi:ss'));
			ResultSet rsType = stType.executeQuery();
			if (rsType.next()) {
				PreparedStatement stPlageHoraire = conn
						.prepareStatement("SELECT * FROM PlageHoraire WHERE idType = ? AND numStation = ?");
				stPlageHoraire.setInt(2, rsStation.getInt("numStation"));
				stPlageHoraire.setInt(1, rsType.getInt("idType"));
				ResultSet rsPlageHoraire = stPlageHoraire.executeQuery();
				if (rsPlageHoraire.next()) {
					System.out.println("Station n°"
							+ rsStation.getInt("numStation")
							+ ", "
							+ rsStation.getString("adresse")
							+ " "
							+ rsStation.getString("cp")
							+ " "
							+ rsStation.getString("ville")
							+ " ( Type "
							+ rsType.getString("libelle")
							+ " de "
							+ rsPlageHoraire.getDate("dateDebut")
							+ " à "
							+ new java.util.Date(rsPlageHoraire.getTimestamp(
									"datefin").getTime()));
				}
				stPlageHoraire.close();
				rsPlageHoraire.close();
			}
			stType.close();
			rsType.close();
		}
		stStation.close();
		rsStation.close();
	}

	/**
	 * Affiche les bornes sans v�lo � la station et en �tat OK
	 * @param conn
	 * @param numStation
	 * @throws SQLException
	 */
	public static void afficherBornesLibres(Connection conn, int numStation)
			throws SQLException {
		PreparedStatement stBornetteLibre = conn
				.prepareStatement("SELECT count(numBornette) AS nbBornetteLibre FROM Bornette WHERE numStation = ? and numVelo IS NULL");
		stBornetteLibre.setInt(1, numStation);
		ResultSet rsBornetteLibre = stBornetteLibre.executeQuery();
		if (rsBornetteLibre.next()) {
			if (rsBornetteLibre.getInt("nbBornetteLibre") != 0) {
				PreparedStatement stBornette = conn
						.prepareStatement("SELECT numBornette FROM Bornette WHERE numStation = ? and numVelo IS NULL");
				stBornette.setInt(1, numStation);
				ResultSet rsBornette = stBornette.executeQuery();
				System.out.println("Bornette libre dans la station n�"
						+ numStation+" : ");
				while (rsBornette.next()) {
					System.out.println("Bornette n�"
							+ rsBornette.getInt("numBornette"));
				}
				stBornette.close();
				rsBornette.close();
			} else {
				System.out.println("Aucune bornette libre dans cette station.");
			}
		}
		stBornetteLibre.close();
		rsBornetteLibre.close();

	}

	/**
	 * Retourne le nombre  de v�los libres OK attach�s � une bornette OK
	 * @param conn
	 * @param numStation
	 * @param nbResa
	 * @return
	 * @throws SQLException
	 */
	public static int getNbVeloDispo(Connection conn, int numStation)
			throws SQLException {
		int nbVelo = 0;
		int numVelo = 0;
		PreparedStatement stVelo = conn
				.prepareStatement("SELECT COUNT(*) AS nbVelo FROM Bornette WHERE numStation = ? AND etat = 'OK' AND numVelo IS NOT NULL AND numVelo IN (SELECT numVelo FROM Velo WHERE etat='OK')");
		stVelo.setInt(1, numStation);
		ResultSet rsVelo = stVelo.executeQuery();
		if (rsVelo.next()) {
			nbVelo = rsVelo.getInt("nbVelo");
		}
		stVelo.close();
		rsVelo.close();
		return nbVelo;

	}

	/**
	 * Retourne un v�lo libre OK attach� � une bornette OK
	 * @param conn
	 * @param numStation
	 * @param nbVeloDispo
	 * @param nbResa
	 * @return
	 * @throws SQLException
	 */
	public static int getVelo(Connection conn, int numStation, int nbVeloDispo,
			int nbResa) throws SQLException {
		int numVelo = 0;
		if (nbVeloDispo - nbResa != 0) {
			PreparedStatement stVeloUnique = conn
					.prepareStatement("SELECT numVelo FROM Bornette WHERE numStation = ? AND etat = 'OK' AND numVelo IS NOT NULL AND numVelo IN (SELECT numVelo FROM Velo WHERE etat='OK')");
			stVeloUnique.setInt(1, numStation);
			stVeloUnique.setMaxRows(1);
			ResultSet rsVeloUnique = stVeloUnique.executeQuery();
			if (rsVeloUnique.next()) {
				//System.out.println(rsVeloUnique.getInt("numVelo"));
				numVelo = rsVeloUnique.getInt("numVelo");
			}
			stVeloUnique.close();
			rsVeloUnique.close();
		}
		return numVelo;

	}

	public static String getTypeStation(Connection conn, int numStation)
			throws SQLException {
		String typeStation = "";
		PreparedStatement stType = conn
				.prepareStatement("SELECT idType, libelle FROM TypeStation WHERE idType IN  (SELECT idType FROM PlageHoraire WHERE numStation = ? )");
		// AND ? BETWEEN dateDebut AND dateFin
		// Dangereux : n'affiche que les stations avec une plage horaire
		// renseignée (sans les lignes du dessus)
		stType.setInt(1, numStation);
		ResultSet rsType = stType.executeQuery();

		if (rsType.next()) {
			PreparedStatement stPlageHoraire = conn
					.prepareStatement("SELECT * FROM PlageHoraire WHERE idType = ? AND numStation = ?");
			stPlageHoraire.setInt(1, rsType.getInt("idType"));
			stPlageHoraire.setInt(2, numStation);
			ResultSet rsPlageHoraire = stPlageHoraire.executeQuery();
			if (rsPlageHoraire.next()) {
				typeStation = rsType.getString("libelle");
			}
			stPlageHoraire.close();
			rsPlageHoraire.close();
		}
		stType.close();
		rsType.close();
		return typeStation;
	}

	public static void majVeloBornette(Connection conn, int numVelo)
			throws SQLException {
		PreparedStatement stBornette = conn
				.prepareStatement("UPDATE Bornette SET numVelo = NULL WHERE numVelo = ? ");
		stBornette.setInt(1, numVelo);
		stBornette.executeUpdate();
		stBornette.close();
	}

	/**
	 * Attache un vélo à une bornette
	 * 
	 * @param conn
	 * @param numVelo
	 * @throws SQLException
	 */
	public static void rattacherVeloBornette(Connection conn, int numVelo,
			int numStation) throws SQLException {
		// récup d'une bornette inoccupée
		PreparedStatement stBorne = conn
				.prepareStatement("SELECT numBornette FROM Bornette WHERE numVelo IS NULL AND numStation = ? AND etat='OK'");
		stBorne.setInt(1, numStation);
		ResultSet rsBorne = stBorne.executeQuery();
		if (rsBorne.next()) {
			int numBornette = rsBorne.getInt("numBornette");
			System.out.println("---> Rattachement du vélo " + numVelo
					+ " a la borne " + numBornette);
			PreparedStatement stBornette = conn
					.prepareStatement("UPDATE Bornette SET numVelo = ? WHERE numBornette = ? ");
			stBornette.setInt(1, numVelo);
			stBornette.setInt(2, numBornette);
			stBornette.executeUpdate();
			stBornette.close();

		} else {
			System.out
					.println("DEPOT IMPOSSIBLE - Aucune borne n'est libre ou la station n'existe pas.");
		}
		stBorne.close();
		rsBorne.close();
	}

	/**
	 * Verifie si un numero de bornette sp�cifi� correspond a une bornette libre
	 * @param conn
	 * @param numBornette
	 * @param numStation
	 * @throws SQLException
	 */
	public static boolean checkDisponibiliteBornette(Connection conn,
			int numBornette, int numStation) throws SQLException {
		boolean estLibre = false;
		PreparedStatement stBorne = conn
				.prepareStatement("SELECT count(numBornette) AS nbResult FROM Bornette WHERE numVelo IS NULL AND numStation = ? AND numBornette = ? AND etat='OK'");
		stBorne.setInt(1, numStation);
		stBorne.setInt(2, numBornette);
		ResultSet rsBorne = stBorne.executeQuery();
		if (rsBorne.next()) {			
			if(rsBorne.getInt("nbResult")!=0){
				estLibre = true;
			}else{
				System.out.println("Erreur. Veuillez saisir un num�ro de borne disponible");
			}
		}
		stBorne.close();
		rsBorne.close();
		return estLibre;
	}

	public static void attacherVeloABornette(Connection conn,
			int numBornette, int numVeloRendu) throws SQLException {
		PreparedStatement stBornette = conn.prepareStatement("UPDATE Bornette SET numVelo = ? WHERE numBornette = ?");
		stBornette.setInt(1, numVeloRendu);
		stBornette.setInt(2, numBornette);
		stBornette.executeUpdate();
		stBornette.close();
	}
}
