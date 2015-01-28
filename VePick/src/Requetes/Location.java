package Requetes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import Connexion.Connexion;

public class Location {

	public static void louerAbo(Connection conn, int numCLient, int numVelo,
			int numStation) throws SQLException {
		if (Client.checkAbonnement(conn, numCLient)) {
			PreparedStatement stLocAbo = conn
					.prepareStatement("INSERT INTO Location values (?, ?, sysdate ,NULL, ?, NULL)");
			stLocAbo.setInt(1, numCLient);
			stLocAbo.setInt(2, numVelo);
			String typeStation = Station.getTypeStation(conn, numStation);
			stLocAbo.setString(3, typeStation);
			stLocAbo.executeUpdate();
			System.out
					.println("Votre location a été enregistré. Vous pouvez retirer le vélo n°"
							+ numVelo);
			System.out
					.println("La station dans laquelle vous empruntez actuellement est de type "
							+ typeStation);
			java.util.Calendar cal = java.util.Calendar.getInstance();
			java.util.Date utilDate = cal.getTime();
			cal.setTime(utilDate);
			cal.add(Calendar.HOUR, 12);
			Date dateFinLoc = new Date(cal.getTimeInMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
			System.out.println("La location doit-être rendu avant "
					+ sdf.format(dateFinLoc) + " au plus tard");

			// MAJ BORNETTE numVelo a NUL
			Station.majVeloBornette(Connexion.getConnexion(), numVelo);
			stLocAbo.close();
		} else {
			System.out.println("Vous n'êtes plus abonné");
		}

	}

	public static void louerNonAbo(Connection conn, int numCLient, int numVelo,
			int numStation) throws SQLException {
		PreparedStatement stLocAbo = conn
				.prepareStatement("INSERT INTO Location values (?, ?, sysdate ,NULL, ?, NULL)");
		stLocAbo.setInt(1, numCLient);
		stLocAbo.setInt(2, numVelo);
		String typeStation = Station.getTypeStation(conn, numStation);
		stLocAbo.setString(3, typeStation);
		stLocAbo.executeUpdate();
		System.out
				.println("Votre location a été enregistré. Vous pouvez retirer le vélo n°"
						+ numVelo);
		System.out
				.println("La station dans laquelle vous empruntez actuellement est de type "
						+ typeStation);
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		cal.setTime(utilDate);
		cal.add(Calendar.HOUR, 12);
		Date dateFinLoc = new Date(cal.getTimeInMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
		System.out.println("La location doit-être rendu avant "
				+ sdf.format(dateFinLoc) + " au plus tard");

		// MAJ BORNETTE numVelo a NUL
		Station.majVeloBornette(Connexion.getConnexion(), numVelo);
		stLocAbo.close();
	}

}
