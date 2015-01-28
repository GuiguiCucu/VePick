package Interfaces.Client;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
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
		System.out.println("5 - Réserver");
		System.out.println("6 - Quitter");
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
			reserverVelo();
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

	private void reserverVelo() {
		System.out.println("\n--------------------------------");
		System.out.println("---Client - Réserver vélo---");
		System.out.println("--------------------------------\n");

		Scanner sc = new Scanner(System.in);
		boolean connexion = false;
		while (!connexion) {
			System.out.println("Veuillez saisir votre n°client: ");
			int numCLient = sc.nextInt();
			System.out.println("Veuillez saisir votre code secret: ");
			int codeSecretClient = sc.nextInt();
			try {
				connexion = Client.identifierClient(Connexion.getConnexion(),
						numCLient, codeSecretClient);
				if (connexion) {

					System.out
							.println("Saisissez le type de réservation à effectuer : ");
					System.out.println("1 -- Une journée");
					System.out
							.println("2 -- Une journée répétée sur une période");
					System.out.println("3 -- Tous les jours d'une période");

					int choix = sc.nextInt();
					switch (choix) {
					case 1:
						System.out
								.println("Veuillez saisir le numéro de la station ou vous êtes : ");
						int numStation = sc.nextInt();

						String dateReservationUnique = "";
						while (!dateReservationUnique
								.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
							System.out
									.println("Veuillez saisir le jour à réserver (jj-mm-yyyy) :");
							dateReservationUnique = sc.nextLine();
						}

						// date du jour réservé
						SimpleDateFormat formatter = new SimpleDateFormat(
								"dd-MM-yyyy");
						java.util.Date dateReservationUtil = null;
						try {
							dateReservationUtil = formatter
									.parse(dateReservationUnique);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						Calendar cdr = Calendar.getInstance();
						cdr.setTime(dateReservationUtil);
						cdr.set(Calendar.HOUR_OF_DAY, 0);
						cdr.set(Calendar.MINUTE, 0);
						cdr.set(Calendar.SECOND, 0);
						cdr.set(Calendar.MILLISECOND, 0);
						java.sql.Date dateReservation = new Date(
								dateReservationUtil.getTime());

						// dateCourante
						java.util.Calendar cal = java.util.Calendar
								.getInstance();
						cal.set(Calendar.HOUR_OF_DAY, 0);
						cal.set(Calendar.MINUTE, 0);
						cal.set(Calendar.SECOND, 0);
						cal.set(Calendar.MILLISECOND, 0);
						java.util.Date utilDate = cal.getTime();
						java.sql.Date now = new Date(utilDate.getTime());

						SimpleDateFormat sdf = new SimpleDateFormat(
								"dd-MMM-yyyy");
						String dateResa = sdf.format(now);
						if (dateReservation.after(now)) {
							Reservation.reserverUneJournee(
									Connexion.getConnexion(), numStation,
									numCLient, dateReservationUnique,
									"En attente");
						} else if (dateReservation.equals(now)) {
							Reservation.reserverUneJournee(
									Connexion.getConnexion(), numStation,
									numCLient, dateReservationUnique,
									"En cours");
						} else {
							System.out
									.println("La date de réservation est antérieure à aujourd'hui.");
						}
						break;
					case 2:
						break;
					case 3:
						break;
					default:
						reserverVelo();
						break;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	private void rendreVelo() {
		System.out.println("\n--------------------------------");
		System.out.println("---Client - Rendre vélo---");
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
		System.out
				.println("Veuillez saisir le numéro de la station ou vous êtes : ");
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
						connexion = Client.identifierClient(
								Connexion.getConnexion(), numCLient,
								codeSecretClient);
						if (connexion) {
							int nbResa = Reservation.getNbResaAjd(
									Connexion.getConnexion(), numStation);
							int nbVeloDispo = Station.getNbVeloDispo(
									Connexion.getConnexion(), numStation,
									nbResa);
							int numVelo = Station.getVelo(
									Connexion.getConnexion(), numStation,
									nbVeloDispo, nbResa);
							if (numVelo != 0) {
								Location.louerAbo(Connexion.getConnexion(),
										numCLient, numVelo, numStation);

							} else {
								System.out
										.println("Aucun vélo disponible dans votre station");
							}
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				break;
			case 2:
				try {
					System.out.println("Veuillez saisir votre num�ro de CB : ");
					Scanner sc2 = new Scanner(System.in);
					String numCB = "";
					numCB = sc2.nextLine();
					int numClient = Client.creerNonAbonne(
							Connexion.getConnexion(), numCB);
					System.out.println("Num client et num nonabo : "
							+ numClient);
					int nbResa = Reservation.getNbResaAjd(
							Connexion.getConnexion(), numStation);
					int nbVeloDispo = Station.getNbVeloDispo(
							Connexion.getConnexion(), numStation, nbResa);
					int numVelo = Station.getVelo(Connexion.getConnexion(),
							numStation, nbVeloDispo, nbResa);
					if (numVelo != 0) {
						Location.louerNonAbo(Connexion.getConnexion(),
								numClient, numVelo, numStation);
					}
				} catch (SQLException e) {
					e.printStackTrace();

				}
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
		Scanner sc2 = new Scanner(System.in);
		System.out.println("\n--------------------------------");
		System.out.println("-------Client - Abonnement------");
		System.out.println("--------------------------------");
		System.out.println("Veuillez saisir votre nom :");
		String nom = sc.nextLine();
		System.out.println("Veuillez saisir votre prenom :");
		String prenom = sc.nextLine();
		String dateNaissance = "";
		do {
			System.out
					.println("Veuillez saisir votre date de naissance (jj-mm-yyyy) :");
			dateNaissance = sc.nextLine();
		} while (!dateNaissance.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})"));

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
		int cp = sc.nextInt();
		System.out.println("Veuillez saisir votre numéro de carte bleue :");
		String cb = sc2.nextLine();
		System.out
				.println("Veuillez saisir votre code secret lié à votre abonnement :");
		int codeSecret = sc.nextInt();

		try {
			Client.creerAbonnement(Connexion.getConnexion(), nom, prenom,
					dateNaissance, sexe, adresse, ville, cp, cb, codeSecret);
		} catch (SQLException e) {
			e.printStackTrace();
		}

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
