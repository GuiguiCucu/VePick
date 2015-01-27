package Requetes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class Client {

	public static boolean identifierClient(Connection conn, int numCLient,
			int codeSecretClient) throws SQLException {
		boolean identification = false;
		PreparedStatement stClient = conn
				.prepareStatement("SELECT * FROM Client WHERE numClient = ? AND codeSecret = ?");
		stClient.setInt(1, numCLient);
		stClient.setInt(2, codeSecretClient);
		ResultSet rsClient = stClient.executeQuery();

		if (rsClient.next()) {
			identification = true;
		} else {
			System.out.println("Identifiants incorrects");
		}

		stClient.close();
		rsClient.close();

		return identification;

	}

	public static boolean checkAbonnement(Connection conn, int numCLient)
			throws SQLException {
		boolean abonnementValide = false;
		PreparedStatement stClient = conn
				.prepareStatement("SELECT dateAbonnement FROM Abonne WHERE numClient = ?");
		stClient.setInt(1, numCLient);
		ResultSet rsClient = stClient.executeQuery();

		if (rsClient.next()) {
			//dateCourante
			java.util.Calendar cal = java.util.Calendar.getInstance();
			java.util.Date utilDate = cal.getTime();
			java.sql.Date now = new Date(utilDate.getTime());
			//DateFin abo (date debut + 1 an)
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(rsClient.getDate("dateAbonnement"));
			calendar.add(Calendar.YEAR, 1);
			Date dateFinAbo = new Date(calendar.getTimeInMillis());
			if (dateFinAbo.after(now)) {
				abonnementValide = true;
			}
		}
		return abonnementValide;
	}

	public static int creerNonAbonne(Connection conn) {
		//Générer code abo non utilisé
//		PreparedStatement stLocAbo = conn
//				.prepareStatement("INSERT INTO Client values (?, ?, ?)");
//		stLocAbo.setInt(1, numCLient);
//		stLocAbo.setInt(2, codeSecret);
//		stLocAbo.setString(2, codeCB);
//		String typeStation = Station.getTypeStation(conn, numStation);
//		stLocAbo.setString(3, typeStation);
//		stLocAbo.executeUpdate();
		return 0;
	}
}
