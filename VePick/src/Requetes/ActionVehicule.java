package Requetes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActionVehicule {
	
	/**
	 * enregistre une action dans ActionVehiculeVelo
	 * @param conn
	 * @param action {reparation velo, chargement velo, depot velo}
	 * @throws SQLException
	 */
	public static void actionVelo(Connection conn, String action, int numVehicule, int numVelo) throws SQLException{
		// pour simuler l'auto increment
		PreparedStatement stActions = conn.prepareStatement("SELECT max(idAction) FROM ActionVehicule");
		ResultSet rsActions = stActions.executeQuery();
		int idAction = 1;
		if(rsActions.next()){
			idAction = rsActions.getInt("max(idAction)");
			idAction++;
		}
		
		// on insert d'abord dans ActionVehicule puis dans ActionVehiculeVelo
		PreparedStatement stAction = conn.prepareStatement("INSERT INTO ActionVehicule values(?, sysdate)");
		stAction.setInt(1, idAction);
		ResultSet rsAction = stAction.executeQuery();
		if(rsAction.next()){
			// reparation
			if(action.equals("reparation velo")){
				PreparedStatement stVelo = conn.prepareStatement("INSERT INTO ActionVehiculeVelo values(?, ?, ?, 'Reparation velo')");
				stVelo.setInt(1, idAction);
				stVelo.setInt(2, numVehicule);
				stVelo.setInt(3, numVelo);
				ResultSet rsVelo = stVelo.executeQuery();
				if(rsVelo.next()){
					System.out.println("La réparation du velo a été enregistrée.");
				}
				stVelo.close();
				rsVelo.close();
			}
			// chargement
			else if (action.equals("chargement velo")){
				PreparedStatement stVelo = conn.prepareStatement("INSERT INTO ActionVehiculeVelo values(?, ?, ?, 'Chargement velo')");
				stVelo.setInt(1, idAction);
				stVelo.setInt(2, numVehicule);
				stVelo.setInt(3, numVelo);
				ResultSet rsVelo = stVelo.executeQuery();
				if(rsVelo.next()){
					System.out.println("Le chargement du velo a été enregistré.");
				}
				stVelo.close();
				rsVelo.close();
			}
			// depot
			else if (action.equals("depot velo")){
				PreparedStatement stVelo = conn.prepareStatement("INSERT INTO ActionVehiculeVelo values(?, ?, ?, 'Depot velo')");
				stVelo.setInt(1, idAction);
				stVelo.setInt(2, numVehicule);
				stVelo.setInt(3, numVelo);
				ResultSet rsVelo = stVelo.executeQuery();
				if(rsVelo.next()){
					System.out.println("Le dépot du velo a été enregistré.");
				}
				stVelo.close();
				rsVelo.close();
			}
		}
		
		stActions.close();
		rsActions.close();
		stAction.close();
		rsAction.close();
	}
}
