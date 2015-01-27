package Interfaces.Superviseur;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import Connexion.Connexion;
import Interfaces.Client.MenuClient;
import Requetes.Station;
import Requetes.VehiculeRegulation;

public class MenuSuperviseur {
	
	public static void main(String[] args) {
		MenuSuperviseur ms = new MenuSuperviseur();
		do {
			ms.afficherMenu();
			ms.analyseChoix();
		} while (true);
	}
	
	public void afficherMenu() {
		System.out.println("\n--------------------------------");
		System.out.println("--------Menu Superviseur--------");
		System.out.println("--------------------------------\n\r");
		System.out.println("Choisissez une action : ");
		System.out.println("1 - Consulter une routine");
		System.out.println("2 - Modifier une routine");
		System.out.println("3 - Consulter une station");
		System.out.println("4 - Consulter les bornes V-, V+");
		System.out.println("5 - Modifier les plages horaires V-, V+");
		System.out.println("6 - Quitter");
	}
	
	private void analyseChoix() {
		Scanner sc = new Scanner(System.in);
		int choix = sc.nextInt();
		switch (choix) {
		case 1:
			consulterRoutine();
			break;
		case 2:
			System.out.println("souce");
			break;
		case 3:
			System.out.println("fuuf");
			break;
		case 4 :
			System.out.println("suus");
			break;
		case 5 :
			System.out.println("moi mon nez");
			break;
		case 6:
			Connexion.close();
			System.out.println("Au revoir");
			System.exit(0);
			break;
		default:
			break;
		}

	}
	
	/**
	 * consultation d'une routine en indiquant le numVehicule
	 */
	private void consulterRoutine(){
		System.out.println("\n--------------------------------");
		System.out.println("Superviseur - Consulter routine");
		System.out.println("--------------------------------");
		
		System.out.println("\nTous les vehicules de régulation :");	
		try {
			VehiculeRegulation.afficherVehicules(Connexion.getConnexion());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("\nSaisissez un numero de Vehicule :");
		// TODO : affichage de la routine de ce vehicule
	}

}
