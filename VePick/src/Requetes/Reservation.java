package Requetes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Reservation {

	/**
	 * 
	 * @param conn
	 * @param numStation
	 * @return le nombre de reservation concernant la journée actuelle
	 * @throws SQLException
	 */
	public static int getNbResaAjd(Connection conn, int numStation)
			throws SQLException {
		int nbResaAjd = 0;
		PreparedStatement stJoursReserves = conn
				.prepareStatement("SELECT COUNT(*) AS nbResaAJD FROM JourReserve WHERE numStation = ? AND trunc(dateJourReservation) - trunc(sysdate) = 0");
		stJoursReserves.setInt(1, numStation);
		ResultSet rsJoursReserves = stJoursReserves.executeQuery();

		if (rsJoursReserves.next()) {
			nbResaAjd = rsJoursReserves.getInt("nbResaAJD");
		}
		stJoursReserves.close();
		rsJoursReserves.close();
		return nbResaAjd;
	}

	/**
	 * On calcule la date de reservation ici (jour courant, format dd-MMM-yyyy hh:mm:00)
	 * 
	 * @param conn
	 * @param numStation
	 * @param numClient
	 * @param dateJourResa date du jour a reserver
	 * @param etat en attente, en cours (test sur les dates en fonction de la datejourresa et de la date courante en amont
	 * @throws SQLException
	 */
	public static void reserverUneJournee(Connection conn, int numStation,
			int numClient, String dateJourResa, String etat)
			throws SQLException {

		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		java.sql.Date now = new Date(utilDate.getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
		String dateResa = sdf.format(now);

		PreparedStatement stReservation = conn
				.prepareStatement("INSERT INTO Reservation values (?, ?, TO_DATE(?, 'dd-mm-yyyy hh24:mi:ss'), 'jour', ?)");
		stReservation.setInt(1, numStation);
		stReservation.setInt(2, numClient);
		stReservation.setString(3, dateResa);
		stReservation.setString(4, etat);
		stReservation.executeUpdate();

		PreparedStatement stJourReserve = conn
				.prepareStatement("INSERT INTO JourReserve values (?, ?, TO_DATE(?, 'dd-mm-yyyy hh24:mi:ss'),TO_DATE(?, 'dd-mm-yyyy') )");
		stJourReserve.setInt(1, numStation);
		stJourReserve.setInt(2, numClient);
		stJourReserve.setString(3, dateResa);
		stJourReserve.setString(4, dateJourResa);
		stJourReserve.executeUpdate();
		
		stReservation.close();
		stJourReserve.close();
	}
}
