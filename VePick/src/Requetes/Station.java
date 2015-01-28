package Requetes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Station {
	public static void afficherStations(Connection conn) throws SQLException {
		PreparedStatement stStation = conn
				.prepareStatement("SELECT numStation, adresse, cp, ville FROM Station");
		ResultSet rsStation = stStation.executeQuery();

		while (rsStation.next()) {
			PreparedStatement stType = conn
					.prepareStatement("SELECT idType, libelle FROM TypeStation WHERE idType IN  (SELECT idType FROM PlageHoraire WHERE numStation = ? )");
			stType.setInt(1, rsStation.getInt("numStation"));
			ResultSet rsType = stType.executeQuery();

			// AND ? BETWEEN dateDebut AND dateFin
			// Dangereux : n'affiche que les stations avec une plage horaire
			// renseignée (sans les lignes du dessus)
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

	/*
	 * NOT USE (FUTUR)
	 */
	public static void afficherBornesLibres(Connection conn, int numStation)
			throws SQLException {
		PreparedStatement stBornette = conn
				.prepareStatement("SELECT numBornette FROM Bornette WHERE numStation = ?");
		stBornette.setInt(1, numStation);
		ResultSet rsBornette = stBornette.executeQuery();
		// int rowcount = 0;
		// if(rowcount ==0){
		// System.out.println("La station renseignée n'existe pas");
		// }else{
		while (rsBornette.next()) {
			System.out.println(rsBornette.getInt("numBornette"));
		}
		// }

		stBornette.close();
		rsBornette.close();

	}

	public static int getNbVeloDispo(Connection conn, int numStation, int nbResa)
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
				System.out.println(rsVeloUnique.getInt("numVelo"));
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

	public static void majVeloBornette(Connection conn, int numVelo) throws SQLException {
		PreparedStatement stBornette = conn
				.prepareStatement("UPDATE Bornette SET numVelo = NULL WHERE numVelo = ? ");
		stBornette.setInt(1, numVelo);
		stBornette.executeUpdate();
		stBornette.close();
	}
}
