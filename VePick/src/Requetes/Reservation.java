package Requetes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reservation {
	public static int getNbResaAjd(Connection conn, int numStation) throws SQLException{
		int nbResaAjd = 0;
		PreparedStatement stJoursReserves = conn
				.prepareStatement("SELECT COUNT(*) AS nbResaAJD FROM JourReserve WHERE numStation = ? AND trunc(dateJourReservation) - trunc(sysdate) = 0");
		stJoursReserves.setInt(1, numStation);
		ResultSet rsJoursReserves = stJoursReserves.executeQuery();
		
		if(rsJoursReserves.next()){
			nbResaAjd= rsJoursReserves.getInt("nbResaAJD");
		}
		stJoursReserves.close();
		rsJoursReserves.close();
		return nbResaAjd;
	}
}
