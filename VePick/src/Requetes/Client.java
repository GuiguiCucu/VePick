package Requetes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
			// dateCourante
			java.util.Calendar cal = java.util.Calendar.getInstance();
			java.util.Date utilDate = cal.getTime();
			java.sql.Date now = new Date(utilDate.getTime());
			// DateFin abo (date debut + 1 an)
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(rsClient.getDate("dateAbonnement"));
			calendar.add(Calendar.YEAR, 1);
			Date dateFinAbo = new Date(calendar.getTimeInMillis());
			// test validitï¿½ abonemment
			if (dateFinAbo.after(now)) {
				abonnementValide = true;
			}
		}
		stClient.close();
		rsClient.close();
		return abonnementValide;
	}

	public static int creerNonAbonne(Connection conn, String numCB)
			throws SQLException {
		// Gï¿½nï¿½ration automatique d'un id
		int numCLient = 0;
		PreparedStatement stNumClient = conn
				.prepareStatement("SELECT count(numClient) as nbIdClient FROM Client");
		ResultSet rsNumClient = stNumClient.executeQuery();

		if (rsNumClient.next()) {
			numCLient = rsNumClient.getInt("nbIdClient") + 1;
		}

		// CrÃ©ation du client

		PreparedStatement stClient = conn
				.prepareStatement("INSERT INTO Client values (?, ?, ?)");
		int codeSecret = 100 + (int) (Math.random() * ((999 - 100) + 1));
		stClient.setInt(1, numCLient);
		stClient.setInt(2, codeSecret);
		stClient.setString(3, numCB);
		stClient.executeUpdate();

		// Crï¿½ation du non abonnï¿½
		PreparedStatement stNonAbo = conn
				.prepareStatement("INSERT INTO NonAbonne values (?, sysdate)");

		stNonAbo.setInt(1, numCLient);
		stNonAbo.executeUpdate();

		stNumClient.close();
		rsNumClient.close();
		stClient.close();
		stNonAbo.close();

		return numCLient;
	}

	public static void creerAbonnement(Connection conn, String nom,
			String prenom, String dateNaissance, String sexe, String adresse,
			String ville, int cp, String cb, int codeSecret)
			throws SQLException {

		// Gï¿½nï¿½ration automatique d'un id
		int numCLient = 0;
		PreparedStatement stNumClient = conn
				.prepareStatement("SELECT count(numClient) as nbIdClient FROM Client");
		ResultSet rsNumClient = stNumClient.executeQuery();

		if (rsNumClient.next()) {
			numCLient = rsNumClient.getInt("nbIdClient") + 1;
		}

		// CrÃ©ation du client

		PreparedStatement stClient = conn
				.prepareStatement("INSERT INTO Client values (?, ?, ?)");
		stClient.setInt(1, numCLient);
		stClient.setInt(2, codeSecret);
		stClient.setString(3, cb);
		stClient.executeUpdate();

		// Crï¿½ation de l'abonnÃ©
		PreparedStatement stAbo = conn
				.prepareStatement("INSERT INTO Abonne values (?,?,?,TO_DATE(?, 'dd-mm-yyyy'),?, ?, ?, ?,sysdate)");

		stAbo.setInt(1, numCLient);
		stAbo.setString(2, nom);
		stAbo.setString(3, prenom);
		stAbo.setString(4, dateNaissance);
		// 4 date naissance
		stAbo.setString(5, sexe);
		stAbo.setString(6, ville);
		stAbo.setString(7, adresse);
		stAbo.setInt(8, cp);

		stAbo.executeUpdate();

		stNumClient.close();
		rsNumClient.close();
		stClient.close();
		stAbo.close();
	}

	public static int getClientFromCode(Connection conn, int codeSecret)
			throws SQLException {
		int numClient = 0;
		PreparedStatement stClient = conn
				.prepareStatement("SELECT * FROM Client WHERE codeSecret = ?");
		stClient.setInt(1, codeSecret);
		ResultSet rsClient = stClient.executeQuery();
		if (rsClient.next()) {
			numClient = rsClient.getInt("numClient");
		} else {
			System.out.println("Code secret incorrect");
		}
		stClient.close();
		rsClient.close();
		return numClient;
	}

	public static int getVeloLocation(Connection conn, int numClient)
			throws SQLException {
		int numVeloARendre = 0;
		PreparedStatement stClient = conn
				.prepareStatement("SELECT numVelo FROM Location WHERE numClient = ? AND dateFinLocation IS NULL");
		stClient.setInt(1, numClient);
		ResultSet rsClient = stClient.executeQuery();
		if (rsClient.next()) {
			numVeloARendre = rsClient.getInt("numVelo");
		} else {
			System.out.println("Vous n'avez pas de location non finalisée!");
		}
		stClient.close();
		rsClient.close();
		return numVeloARendre;
	}

	public static void checkAmende(Connection conn, int numClient,
			int numVeloRendu) throws SQLException {
		PreparedStatement stClient = conn
				.prepareStatement("SELECT dateLocation FROM Location WHERE numClient = ? AND dateFinLocation IS NULL AND numVelo = ?");
		stClient.setInt(1, numClient);
		stClient.setInt(2, numVeloRendu);
		ResultSet rsClient = stClient.executeQuery();
		if (rsClient.next()) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
			java.sql.Date dateLoc = rsClient.getDate("dateLocation");
			// System.out.println(sdf.format(dateLoc));

			java.util.Calendar cal = java.util.Calendar.getInstance();
			cal.add(Calendar.HOUR, 1);
			java.util.Date utilDate = cal.getTime();
			java.sql.Date now = new Date(utilDate.getTime());
			// System.out.println(sdf.format(now));

			int retard = hoursDifference(now, dateLoc) - 12;
			if (retard >= 12) {
				System.out.println("Vous avez rendu votre vélo avec " + retard
						+ " heures de retards");
				System.out.println("Vous écoppez d'une amende de 10€");
				Client.genererAmende(conn, dateLoc, numClient, numVeloRendu);
			}
		}
		stClient.close();
		rsClient.close();
	}

	public static void genererAmende(Connection conn, Date dateLoc,
			int numClient, int numVeloRendu) throws SQLException {
		int numAmende = 0;
		PreparedStatement stNbAmende = conn
				.prepareStatement("SELECT COUNT(*) AS nbAmende FROM AMENDE");
		ResultSet rsNbAmende = stNbAmende.executeQuery();
		if (rsNbAmende.next()) {
			numAmende = rsNbAmende.getInt("nbAmende") + 1;
			PreparedStatement stAmende = conn
					.prepareStatement("INSERT INTO Amende values (?,10.0,?,?,?)");
			stAmende.setInt(1, numAmende);
			stAmende.setInt(2, numVeloRendu);
			stAmende.setInt(3, numClient);
			stAmende.setDate(4, dateLoc);
			//ORA-02291: violation de contrainte d'intégrité (CUTRONEG.AMENDE_FK) - clé parent introuvable :TODO
			stAmende.executeUpdate();
			stAmende.close();
		}
		stNbAmende.close();
		rsNbAmende.close();
	}

	private static int hoursDifference(Date date1, Date date2) {
		final int MILLI_TO_HOUR = 1000 * 60 * 60;
		return (int) (date1.getTime() - date2.getTime()) / MILLI_TO_HOUR;
	}

	public static void checkRemise(Connection conn, String typeRetourStation,
			int numClient, int numVeloRendu) throws SQLException {
		PreparedStatement stClient = conn
				.prepareStatement("SELECT TypeStationDepart FROM Location WHERE numClient = ? AND dateFinLocation IS NULL AND numVelo = ?");
		stClient.setInt(1, numClient);
		stClient.setInt(2, numVeloRendu);
		ResultSet rsClient = stClient.executeQuery();
		if (rsClient.next()) {
			if (rsClient.getString("TypeStationDepart").equals("Vmoins")
					&& typeRetourStation.equals("Vplus")) {
				if (Client.isAbonne(conn, numClient)) {
					int numRemiseAbonne = 0;
					PreparedStatement stNumAbo = conn
							.prepareStatement("SELECT COUNT(*) AS numRemiseAbonne FROM RemiseAbonne");
					ResultSet rsNumAbo = stNumAbo.executeQuery();
					if (rsNumAbo.next()) {
						numRemiseAbonne = rsNumAbo.getInt("numRemiseAbonne") + 1;

						PreparedStatement stRemiseAbo = conn
								.prepareStatement("INSERT INTO RemiseAbonne values (?,10,?)");
						stRemiseAbo.setInt(1, numRemiseAbonne);
						stRemiseAbo.setInt(2, numClient);
						stRemiseAbo.executeUpdate();
						stRemiseAbo.close();
					}
					stNumAbo.close();
					rsNumAbo.close();
				} else {
					int numRemiseNonAbonne = 0;
					PreparedStatement stNumNonAbo = conn
							.prepareStatement("SELECT COUNT(*) AS numRemiseNonAbonne FROM RemiseNonAbonne");
					ResultSet rsNumNonAbo = stNumNonAbo.executeQuery();
					if (rsNumNonAbo.next()) {
						numRemiseNonAbonne = rsNumNonAbo
								.getInt("numRemiseNonAbonne") + 1;

						PreparedStatement stRemiseNonAbo = conn
								.prepareStatement("INSERT INTO RemiseNonAbonne values (?,10,sysdate+30,?)");
						stRemiseNonAbo.setInt(1, numRemiseNonAbonne);
						stRemiseNonAbo.setInt(2, numClient);
						stRemiseNonAbo.executeUpdate();
						stRemiseNonAbo.close();
					}
					stNumNonAbo.close();
					rsNumNonAbo.close();
				}
			}
		}
		stClient.close();
		rsClient.close();

	}

	private static boolean isAbonne(Connection conn, int numClient)
			throws SQLException {
		boolean isAbo = false;
		PreparedStatement stClient = conn
				.prepareStatement("SELECT COUNT(*) AS isAbo FROM Abonne WHERE numClient = ? ");
		stClient.setInt(1, numClient);
		ResultSet rsClient = stClient.executeQuery();
		if (rsClient.next()) {
			if (rsClient.getInt("isAbo") != 0) {
				isAbo = true;
			}
		}
		stClient.close();
		rsClient.close();
		return isAbo;
	}

}
