package Requetes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import Connexion.Connexion;

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
	 * On calcule la date de reservation ici (jour courant, format dd-MMM-yyyy
	 * hh:mm:00)
	 * 
	 * @param conn
	 * @param numStation
	 * @param numClient
	 * @param dateJourResa
	 *            date du jour a reserver
	 * @param etat
	 *            en attente, en cours (test sur les dates en fonction de la
	 *            datejourresa et de la date courante en amont
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

	public static void reserverUnePeriode(Connection conn, int numClient,
			int numStation, java.util.Date dateReservationDebutPeriodeUtil,
			String dateDebutPeriode, String dateFinPeriode, String etat)
			throws SQLException {

		// Création de la réservation
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		java.sql.Date now = new Date(utilDate.getTime());
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
		String dateResa = sdf1.format(now);

		PreparedStatement stReservation = conn
				.prepareStatement("INSERT INTO Reservation values (?, ?, TO_DATE(?, 'dd-mm-yyyy hh24:mi:ss'), 'ttJoursPer', ?)");
		stReservation.setInt(1, numStation);
		stReservation.setInt(2, numClient);
		stReservation.setString(3, dateResa);
		stReservation.setString(4, etat);
		stReservation.executeUpdate();

		// Creation des jours réservés
		long diffDay = getDayCount(dateDebutPeriode, dateFinPeriode);
		for (int i = 0; i <= diffDay; i++) {
			java.util.Calendar cal2 = java.util.Calendar.getInstance();
			cal2.setTime(dateReservationDebutPeriodeUtil);
			cal2.add(Calendar.DAY_OF_MONTH, i);
			java.util.Date jourAReserver = cal2.getTime();
			java.sql.Date dateFinLoc = new Date(cal2.getTimeInMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			String dateJourResa = sdf.format(dateFinLoc);
			PreparedStatement stJourReserve = conn
					.prepareStatement("INSERT INTO JourReserve values (?, ?, TO_DATE(?, 'dd-mm-yyyy hh24:mi:ss'),TO_DATE(?, 'dd-mm-yyyy') )");
			stJourReserve.setInt(1, numStation);
			stJourReserve.setInt(2, numClient);
			stJourReserve.setString(3, dateResa);
			stJourReserve.setString(4, dateJourResa);
			stJourReserve.executeUpdate();
			stJourReserve.close();
		}
		stReservation.close();

	}

	/**
	 * Calcul le nombre de jours entre 2 dates
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long getDayCount(String start, String end) {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"dd-MM-yyyy");
		long diff = -1;
		try {
			java.util.Date dateStart = simpleDateFormat.parse(start);
			java.util.Date dateEnd = simpleDateFormat.parse(end);
			diff = Math.round((dateEnd.getTime() - dateStart.getTime())
					/ (double) 86400000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diff;
	}

	public static void reserverJourPeriode(Connection conn, int numClient,
			int numStation, java.util.Date dateReservationDebutPeriodeUtil,
			String dateDebutPeriode, String dateFinPeriode, String etat,
			int delaiRepet) throws SQLException {

		// Création de la réservation
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		java.sql.Date now = new Date(utilDate.getTime());
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
		String dateResa = sdf1.format(now);

		PreparedStatement stReservation = conn
				.prepareStatement("INSERT INTO Reservation values (?, ?, TO_DATE(?, 'dd-mm-yyyy hh24:mi:ss'), 'jourRepPer', ?)");
		stReservation.setInt(1, numStation);
		stReservation.setInt(2, numClient);
		stReservation.setString(3, dateResa);
		stReservation.setString(4, etat);
		stReservation.executeUpdate();

		// Creation des jours réservés
		long diffDay = getDayCount(dateDebutPeriode, dateFinPeriode);
		long nbJourSurPeriode = diffDay/delaiRepet;
		System.out.println("diffday = " + diffDay);
		System.out.println("nbJourSurPer = "+nbJourSurPeriode);

		for (int i = 0; i <= nbJourSurPeriode; i++) {
			java.util.Calendar cal2 = java.util.Calendar.getInstance();
			cal2.setTime(dateReservationDebutPeriodeUtil);
			//incremente du nombre de jour jusqu'a la borne max (date de fin de période) représenté par nombre de jours cocnernés sur la période
			cal2.add(Calendar.DAY_OF_MONTH, i*delaiRepet);
			java.util.Date jourAReserver = cal2.getTime();
			java.sql.Date dateFinLoc = new Date(cal2.getTimeInMillis());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			String dateJourResa = sdf.format(dateFinLoc);
			PreparedStatement stJourReserve = conn
					.prepareStatement("INSERT INTO JourReserve values (?, ?, TO_DATE(?, 'dd-mm-yyyy hh24:mi:ss'),TO_DATE(?, 'dd-mm-yyyy') )");
			stJourReserve.setInt(1, numStation);
			stJourReserve.setInt(2, numClient);
			stJourReserve.setString(3, dateResa);
			stJourReserve.setString(4, dateJourResa);
			stJourReserve.executeUpdate();
			stJourReserve.close();
		}
		stReservation.close();
	}
}
