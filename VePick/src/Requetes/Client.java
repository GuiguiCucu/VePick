package Requetes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	public static boolean checkAbonnement(Connection conn, int numCLient) throws SQLException{
		boolean abonnementValide = false;
		PreparedStatement stClient = conn
				.prepareStatement("SELECT * FROM Client WHERE numClient = ?");
		stClient.setInt(1, numCLient);
		ResultSet rsClient = stClient.executeQuery();

		if(rsClient.next()) {
			int ecart=0;
			
			if(ecart <= 365){
				abonnementValide = true;				
			}
		}
		
		return abonnementValide;
	}
}
