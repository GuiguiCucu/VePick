package Interfaces.Conducteur;

import java.sql.SQLException;
import java.util.Scanner;

import Connexion.Connexion;
import Requetes.TacheRoutine;
import Requetes.VehiculeRegulation;

public class MenuConducteur {
	
	public static void main(String[] args) {
		MenuConducteur mc = new MenuConducteur();
		do {
			mc.afficherMenu();
			mc.analyseChoix();
		} while (true);
	}
	
	public void afficherMenu() {
		System.out.println("\n--------------------------------");
		System.out.println("---------Menu Conducteur--------");
		System.out.println("--------------------------------\n\r");
		System.out.println("Choisissez une action : ");
		System.out.println("1 - Déclarer un vélo endommagé");
		System.out.println("2 - Consulter une routine");
		System.out.println("3 - Valider une routine");
		System.out.println("4 - Déplacer un vélo");
		System.out.println("5 - Réparer un vélo");
		System.out.println("6 - Quitter");
	}
	
	private void analyseChoix() {
		Scanner sc = new Scanner(System.in);
		int choix = sc.nextInt();
		switch (choix) {
		case 1:
			System.out.println("TODO déclarer velo endommagé");
			break;
		case 2:
			consulterRoutine();
			break;
		case 3:
			System.out.println("TODO valid routine");
			break;
		case 4:
			System.out.println("TODO déplacer vélo");
			break;
		case 5:
			System.out.println("TODO réparer vélo");
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
	
	private void consulterRoutine(){
		System.out.println("\n--------------------------------");
		System.out.println("-Conducteur - Consulter routine-");
		System.out.println("--------------------------------");

		// affichage de tous les vehicules
		System.out.println("\nTous les vehicules de régulation :");
		try {
			VehiculeRegulation.afficherVehicules(Connexion.getConnexion());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// affichage de la routine
		System.out.println("\nSaisissez un numero de Vehicule :");
		Scanner sc = new Scanner(System.in);
		int choix = sc.nextInt();

		try {
			TacheRoutine.afficherRoutine(Connexion.getConnexion(), choix);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
