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
							+ rsStation.getInt("numStation") + ", "
							+ rsStation.getString("adresse") + " "
							+ rsStation.getString("cp") + " "
							+ rsStation.getString("ville") + " ( Type "
							+ rsType.getString("libelle")+" de "
							+ rsPlageHoraire.getDate("dateDebut") + " à "
							+ new java.util.Date(rsPlageHoraire.getTimestamp("datefin").getTime()));
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
}
