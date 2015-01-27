package Interfaces.Client;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import Connexion.Connexion;
import Requetes.Client;
import Requetes.Location;
import Requetes.Reservation;
import Requetes.Station;

public class MenuClient {

	public static void main(String[] args) {
		MenuClient mc = new MenuClient();
		do {
			mc.afficherMenu();
			mc.analyseChoix();
		} while (true);
	}

	public void afficherMenu() {
		System.out.println("\n--------------------------------");
		System.out.println("-----------Menu Client----------");
		System.out.println("--------------------------------\n\r");
		System.out.println("Choisissez une action : ");
		System.out.println("1 - S'abonner");
		System.out.println("2 - Consulter les bornes V-, V+");
		System.out.println("3 - Emprunter un velo");
		System.out.println("4 - Rendre un velo");
		System.out.println("5 - Quitter");
	}

	private void analyseChoix() {
		Scanner sc = new Scanner(System.in);
		int choix = sc.nextInt();
		switch (choix) {
		case 1:
			actionAbonnement();
			break;
		case 2:
			consultationStations();
			break;
		case 3:
			emprunterVelo();
			break;
		case 4:
			rendreVelo();
			break;
		case 5:
			Connexion.close();
			System.out.println("Au revoir");
			System.exit(0);
			break;
		default:
			break;
		}

	}

	private void rendreVelo() {
		System.out.println("\n--------------------------------");
		System.out.println("---Client - Rendre v�lo---");
		System.out.println("--------------------------------");
		System.out.println("TODO");

		/*
		 * Afficher borne libre station Saisir code client Yolo
		 */
		System.out
				.println("(Simulation)Veuillez saisir le numéro de la station ou vous êtes : ");
		Scanner sc = new Scanner(System.in);
		int numStation = sc.nextInt();
		try {
			Station.afficherBornesLibres(Connexion.getConnexion(), numStation);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void emprunterVelo() {
		System.out.println("\n--------------------------------");
		System.out.println("---Client - Emprunter vélo---");
		System.out.println("--------------------------------\n");

		Scanner sc = new Scanner(System.in);
		int choix = 0;
		System.out.println("Veuillez saisir le numéro de la station ou vous êtes : ");
		int numStation = sc.nextInt();
		do {
			System.out.println("1 - J'ai une carte d'abonnement");
			System.out.println("2 - Je suis un nouveau client");
			choix = sc.nextInt();
			switch (choix) {
			case 1:
				boolean connexion = false;
				while (!connexion) {
					System.out.println("Veuillez saisir votre n°client: ");
					int numCLient = sc.nextInt();
					System.out.println("Veuillez saisir votre code secret: ");
					int codeSecretClient = sc.nextInt();
					try {
						connexion = Client.identifierClient(Connexion.getConnexion(),
								numCLient, codeSecretClient);
						if(connexion){
							int nbResa = Reservation.getNbResaAjd(Connexion.getConnexion(), numStation);
							int nbVeloDispo = Station.getNbVeloDispo(Connexion.getConnexion(),numStation,nbResa);
							int numVelo = Station.getVelo(Connexion.getConnexion(), numStation, nbVeloDispo, nbResa);
							if(numVelo!=0){
								 Location.locationAbonne(Connexion.getConnexion(),numCLient, numVelo, numStation);
							}else{
								System.out.println("Aucun vélo disponible dans votre station");
							}
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}

				}

				break;
			case 2:
				/* afficher code secret généré */
				Location.locationNonAbonne();
				break;
			default:
				break;
			}
		} while (choix != 1 && choix != 2);
		/* Afficher Borne et heure de rendu max (+12h) */

	}

	private void consultationStations() {
		System.out.println("\n--------------------------------");
		System.out.println("-Client - Consultation stations-");
		System.out.println("--------------------------------");

		try {
			Station.afficherStations(Connexion.getConnexion());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void actionAbonnement() {
		Scanner sc = new Scanner(System.in);
		System.out.println("\n--------------------------------");
		System.out.println("-------Client - Abonnement------");
		System.out.println("--------------------------------");
		System.out.println("Veuillez saisir votre nom :");
		String nom = sc.nextLine();
		System.out.println("Veuillez saisir votre prenom :");
		String prenom = sc.nextLine();
		String strdate = "";
		do {
			System.out
					.println("Veuillez saisir votre date de naissance (jj-mm-yyyy) :");
			strdate = sc.nextLine();
		} while (!strdate.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})"));

		String sexe = "";
		do {
			System.out.println("Veuillez saisir votre sexe (Homme|Femme) :");
			sexe = sc.nextLine();
		} while (!sexe.equals("Homme") && !sexe.equals("Femme"));

		System.out.println("Veuillez saisir votre adresse :");
		String adresse = sc.nextLine();
		System.out.println("Veuillez saisir votre ville :");
		String ville = sc.nextLine();
		System.out.println("Veuillez saisir votre code postal :");
		String cp = sc.nextLine();

		Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		Date dateAbonnement = new Date(utilDate.getTime());

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateAbonnement);
		calendar.add(Calendar.YEAR, 1);
		Date dateFinAbo = new Date(calendar.getTimeInMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		System.out
				.println("Votre abonnement a ete pris en compte. Date d'expiration : "
						+ sdf.format(dateFinAbo));
	}
}
