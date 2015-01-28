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
			//test validit� abonemment
			if (dateFinAbo.after(now)) {
				abonnementValide = true;
			}
		}
		stClient.close();
		rsClient.close();
		return abonnementValide;
	}

	public static int creerNonAbonne(Connection conn, String numCB) throws SQLException {
		//G�n�ration automatique d'un id
        int numCLient = 0;
        PreparedStatement stNumClient = conn
				.prepareStatement("SELECT count(numClient) as nbIdClient FROM Client");
		ResultSet rsNumClient = stNumClient.executeQuery();

		if (rsNumClient.next()) {
			numCLient = rsNumClient.getInt("nbIdClient") +1;
		}
        
		//Création du client
		
		PreparedStatement stClient = conn.prepareStatement("INSERT INTO Client values (?, ?, ?)");
		int codeSecret = 100 + (int)(Math.random() * ((999 - 100) + 1));
		stClient.setInt(1, numCLient);
		stClient.setInt(2, codeSecret);
		stClient.setString(3, numCB);
		stClient.executeUpdate();
		

        //Cr�ation du non abonn�
        PreparedStatement stNonAbo = conn.prepareStatement("INSERT INTO NonAbonne values (?, sysdate)");
        
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
			String ville, int cp, String cb, int codeSecret) throws SQLException {
		
		//G�n�ration automatique d'un id
        int numCLient = 0;
        PreparedStatement stNumClient = conn
				.prepareStatement("SELECT count(numClient) as nbIdClient FROM Client");
		ResultSet rsNumClient = stNumClient.executeQuery();

		if (rsNumClient.next()) {
			numCLient = rsNumClient.getInt("nbIdClient") +1;
		}
        
		//Création du client
		
		PreparedStatement stClient = conn.prepareStatement("INSERT INTO Client values (?, ?, ?)");
		stClient.setInt(1, numCLient);
		stClient.setInt(2, codeSecret);
		stClient.setString(3, cb);
		stClient.executeUpdate();
		

        //Cr�ation de l'abonné
        PreparedStatement stAbo = conn.prepareStatement("INSERT INTO Abonne values (?,?,?,TO_DATE(?, 'dd-mm-yyyy'),?, ?, ?, ?,sysdate)");
        
        stAbo.setInt(1, numCLient);
        stAbo.setString(2, nom);
        stAbo.setString(3, prenom);
        stAbo.setString(4, dateNaissance);
        //4 date naissance
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
}
