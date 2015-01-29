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
			modifierRoutine();
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
	
	private void modifierRoutine() {
		System.out.println("\n--------------------------------");
		System.out.println("Superviseur - Modifier Routine");
		System.out.println("--------------------------------");

		// affichage de toutes les stations
		try {
			// Demande du numero de vehicule
			System.out.println("\nSaisissez un numero de vehicule :");
			Scanner sc = new Scanner(System.in);
			int numVehicule = sc.nextInt();
			
			System.out.println("\nRoutine du vehicule :");
			TacheRoutine.afficherRoutine(Connexion.getConnexion(),numVehicule);
			
			System.out.println("\nVoulez vous modifier cette routine? Cela implique de supprimer et de refaire entierement ou partiellement la routine");
			System.out.println("\n1- Oui");
			System.out.println("\n2- Non");
			int choix = sc.nextInt();
			switch (choix) {
			case 1:
				System.out.println("\n Donner le rang à partir du quel vous voulez supprimer les routines afin de reorganiser à partir de celui ci \n"
						+ "(donnez le rang 1 pour supprimer toute la routine, donnez le rang 0 pour ne supprimer aucune tache):");
				int rang = sc.nextInt();
				
				if(rang != 0){
					TacheRoutine.supprimerRoutine(Connexion.getConnexion(),numVehicule, rang);
				}
				System.out.println("\nToutes les taches :");
				TacheRoutine.afficherTaches(Connexion.getConnexion());
				
				//TODO Demander s'il veut creer une tache ou en choisir une dans la liste
				boolean cond = true;
				while(cond){
					System.out.println("\nSelectionnez:");
					System.out.println("\n1- Choisir une tache deja existante");
					System.out.println("\n2- Creer une tache");
					System.out.println("\n3- Terminer");

					int choix3 = sc.nextInt();
					int numTache;
					String nomTache = "";
					
					switch (choix3) {
					case 1:
						System.out.println("\nDonnez le numero de la tache à ajouté:");
						numTache = sc.nextInt();
						TacheRoutine.ajouterTacheRoutine(Connexion.getConnexion(), numVehicule, numTache);
						break;
					
					case 2:
						System.out.println("\nDonnez le nom de la nouvelle tache:");
						sc.nextLine();
						nomTache = sc.nextLine();
						System.out.println("\nDonnez le nombre de vélo pour cette tache si necessaire (0 sinon):");
						int nbUnite = sc.nextInt();
						numTache = TacheRoutine.ajouterTache(Connexion.getConnexion(), numVehicule, nomTache, nbUnite);
						TacheRoutine.ajouterTacheRoutine(Connexion.getConnexion(), numVehicule, numTache);
					break;
					
					case 3:
						cond = false;
						break;				
					}
				}
				
				break;
			case 2:
				
				break;
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
