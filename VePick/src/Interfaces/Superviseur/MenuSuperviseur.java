package Interfaces.Superviseur;

import java.sql.SQLException;
import java.util.Scanner;

import Connexion.Connexion;
import Requetes.Station;
import Requetes.TacheRoutine;
import Requetes.VehiculeRegulation;
import Requetes.Velo;

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
		System.out.println("4 - Consulter les stations V-, V+");
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
			System.out.println("TODO modif routine");
			break;
		case 3:
			consulterStation();
			break;
		case 4:
			consulterTypeStation();
			break;
		case 5:
			System.out.println("TODO modif PH");
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
	private void consulterRoutine() {
		System.out.println("\n--------------------------------");
		System.out.println("Superviseur - Consulter routine");
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

	/**
	 * affichage des velos d'une station
	 */
	private void consulterStation() {
		System.out.println("\n--------------------------------");
		System.out.println("-Superviseur - Consulter station");
		System.out.println("--------------------------------");

		// affichage de toutes les stations
		System.out.println("\nToutes les stations :");
		try {
			Station.afficherStations(Connexion.getConnexion());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// affichage des velos de la station choisie
		System.out.println("\nSaisissez un numero de station :");
		Scanner sc = new Scanner(System.in);
		int choix = sc.nextInt();

		try {
			System.out
					.println("Tous les velos de la station " + choix + " :\n");
			Velo.afficherVeloStation(Connexion.getConnexion(), choix);
			Velo.afficherNombreVelosStation(Connexion.getConnexion(), choix);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * affichage du statut d'une station
	 */
	private void consulterTypeStation() {
		System.out.println("\n--------------------------------");
		System.out.println("Superviseur - Type station");
		System.out.println("--------------------------------");

		// affichage de toutes les stations
		System.out.println("\nToutes les stations :");
		try {
			Station.afficherStations(Connexion.getConnexion());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
