package Interfaces.Conducteur;

import java.sql.SQLException;
import java.util.Scanner;

import Connexion.Connexion;
import Requetes.ActionVehicule;
import Requetes.Station;
import Requetes.TacheRoutine;
import Requetes.VehiculeRegulation;
import Requetes.Velo;

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
			veloEndommage();
			break;
		case 2:
			consulterRoutine();
			break;
		case 3:
			System.out.println("TODO valid routine");
			break;
		case 4:
			deplacerVeloChoix();
			deplacerVelo();
			break;
		case 5:
			reparerVelo();
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
	 * permet de changer l'etat d'un vélo OK -> HS
	 */
	private void veloEndommage() {
		System.out.println("\n--------------------------------");
		System.out.println("-- Conducteur - Velo edommage --");
		System.out.println("--------------------------------");

		// affichage de tous les velos
		System.out.println("Tous les velos enregistrés :");
		try {
			Velo.afficherVelos(Connexion.getConnexion());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// mise a jour de l'etat d'un velo
		System.out.println("\nSaisissez un numero de velo :");
		Scanner sc = new Scanner(System.in);
		int choix = sc.nextInt();

		try {
			Velo.declarerEndommage(Connexion.getConnexion(), choix);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * consulter la routine d'un vehicule
	 */
	private void consulterRoutine() {
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

	/**
	 * Reparer un velo
	 */
	private void reparerVelo() {
		System.out.println("\n--------------------------------");
		System.out.println("--- Conducteur - Reparer velo --");
		System.out.println("--------------------------------");

		// affichage de tous les velos HS
		System.out.println("Tous les velos HS enregistrés :");
		try {
			Velo.afficherVelosHS(Connexion.getConnexion());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// selection du vehicule, et du velo a reparer
		System.out.println("\nSaisissez un numero de vehicule :");
		Scanner sc = new Scanner(System.in);
		int numVehicule = sc.nextInt();
		System.out.println("\nSaisissez un numero de velo :");
		int numVelo = sc.nextInt();
		// MAJ de l'etat du velo
		try {
			Velo.reparerVelo(Connexion.getConnexion(), numVelo);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// enregistrerment dans ActionVehiculeVelo
		try {
			ActionVehicule.actionVelo(Connexion.getConnexion(),
					"reparation velo", numVehicule, numVelo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * présente les choix de deplacement d'un velo
	 */
	private void deplacerVeloChoix() {
		System.out.println("\n--------------------------------");
		System.out.println("-Conducteur - Déplacer un velo--");
		System.out.println("--------------------------------\n\r");
		System.out.println("Choisissez une action : ");
		System.out.println("1 - Déposer un vélo");
		System.out.println("2 - Charger un vélo dans le vehicule");
	}

	/**
	 * analise du choix de deplacerVeloChoix
	 */
	private void deplacerVelo() {
		Scanner sc = new Scanner(System.in);
		int choix = sc.nextInt();
		switch (choix) {
		case 1:
			deposerVelo();
			break;
		case 2:
			chargerVelo();
			break;
		default:
			break;
		}
	}

	/**
	 * enregistrement du depot de velo
	 */
	private void deposerVelo() {
		// selection du vehicule, et du velo a reparer
		System.out.println("\nSaisissez le numero de votre vehicule :");
		Scanner sc = new Scanner(System.in);
		int numVehicule = sc.nextInt();
		System.out.println("\nSaisissez le numero du velo à déposer:");
		int numVelo = sc.nextInt();
		System.out.println("\nSaisissez le numero de station dans laquelle vous voulez deposer le vélo :");
		int numStation = sc.nextInt();

		// enregistrement de l'action
		try {
			ActionVehicule.actionVelo(Connexion.getConnexion(), "depot velo",
					numVehicule, numVelo);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// rattacher le velo à la borne
		try {
			Station.rattacherVeloBornette(Connexion.getConnexion(), numVelo,
					numStation);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * enregistrement du chargement
	 */
	private void chargerVelo(){
		System.out.println("\nSaisissez le numero de votre vehicule :");
		Scanner sc = new Scanner(System.in);
		int numVehicule = sc.nextInt();
		System.out.println("\nSaisissez le numero du velo à charger:");
		int numVelo = sc.nextInt();
		
		// enregistrement du chargement (un trigger détache automatiquement le velo de sa bornette)
		try {
			ActionVehicule.actionVelo(Connexion.getConnexion(), "chargement velo", numVehicule, numVelo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
