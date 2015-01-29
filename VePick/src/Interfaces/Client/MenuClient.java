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
						emprunterUneJournee(numCLient);
						break;
					case 2:
						empruntRepetSurPeriode(numCLient);
						break;
					case 3:
						emprunterPeriodeComplete(numCLient);
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

	private void empruntRepetSurPeriode(int numCLient) {
		Scanner sc = new Scanner(System.in);
		System.out
				.println("Veuillez saisir le numéro de la station ou vous êtes : ");
		int numStation = sc.nextInt();

		String dateDebutPeriode = "";
		while (!dateDebutPeriode.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
			System.out
					.println("Veuillez saisir le jour de début de la période (jj-mm-yyyy) :");
			dateDebutPeriode = sc.nextLine();
		}
		String dateFinPeriode = "";
		while (!dateFinPeriode.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
			System.out
					.println("Veuillez saisir le jour de fin de la période (jj-mm-yyyy) :");
			dateFinPeriode = sc.nextLine();
		}

		int delaiRepet = 0;
		System.out
				.println("Veuillez saisir le délai entre deux réservations :");
		delaiRepet = sc.nextInt();
		System.out.println("END SAISIES");

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		// date de début de la période
		java.util.Date dateReservationDebutPeriodeUtil = null;
		try {
			dateReservationDebutPeriodeUtil = formatter.parse(dateDebutPeriode);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cdr = Calendar.getInstance();
		cdr.setTime(dateReservationDebutPeriodeUtil);
		cdr.set(Calendar.HOUR_OF_DAY, 0);
		cdr.set(Calendar.MINUTE, 0);
		cdr.set(Calendar.SECOND, 0);
		cdr.set(Calendar.MILLISECOND, 0);
		java.sql.Date dateReservationDebutPeriode = new Date(
				dateReservationDebutPeriodeUtil.getTime());

		// date de fin de la période
		java.util.Date dateReservationFinPeriodeUtil = null;
		try {
			dateReservationFinPeriodeUtil = formatter.parse(dateFinPeriode);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cdr2 = Calendar.getInstance();
		cdr2.setTime(dateReservationFinPeriodeUtil);
		cdr2.set(Calendar.HOUR_OF_DAY, 0);
		cdr2.set(Calendar.MINUTE, 0);
		cdr2.set(Calendar.SECOND, 0);
		cdr2.set(Calendar.MILLISECOND, 0);
		java.sql.Date dateReservationFinPeriode = new Date(
				dateReservationFinPeriodeUtil.getTime());

		// dateCourante
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		java.util.Date utilDate = cal.getTime();
		java.sql.Date now = new Date(utilDate.getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String dateAjd = sdf.format(now);

		System.out.println("END DATE");

		if (dateReservationFinPeriode.after(dateReservationDebutPeriode)) {
			if (dateReservationDebutPeriode.after(now)) {
				if (dateReservationFinPeriode.after(now)) {
					String etat = "En attente";
					if (dateReservationDebutPeriodeUtil.equals(now)) {
						etat = "En cours";
					}
					try {
						System.out.println("GO INSERT");
						Reservation.reserverJourPeriode(
								Connexion.getConnexion(), numCLient,
								numStation, dateReservationDebutPeriodeUtil,
								dateDebutPeriode, dateFinPeriode, etat,
								delaiRepet);
					} catch (SQLException e) {
						e.printStackTrace();
					}

				} else {
					System.out
							.println("La date de fin de la période est déjà passée");
				}
			} else {
				System.out
						.println("La date de début de la période est déjà passée");
			}

		} else {
			System.out.println("Vos dates de période sont incohérentes");
		}

	}

	private void emprunterPeriodeComplete(int numCLient) {
		Scanner sc = new Scanner(System.in);
		System.out
				.println("Veuillez saisir le numéro de la station ou vous êtes : ");
		int numStation = sc.nextInt();

		String dateDebutPeriode = "";
		while (!dateDebutPeriode.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
			System.out
					.println("Veuillez saisir le jour de début de la période (jj-mm-yyyy) :");
			dateDebutPeriode = sc.nextLine();
		}
		String dateFinPeriode = "";
		while (!dateFinPeriode.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
			System.out
					.println("Veuillez saisir le jour de fin de la période (jj-mm-yyyy) :");
			dateFinPeriode = sc.nextLine();
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		// date de début de la période
		java.util.Date dateReservationDebutPeriodeUtil = null;
		try {
			dateReservationDebutPeriodeUtil = formatter.parse(dateDebutPeriode);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cdr = Calendar.getInstance();
		cdr.setTime(dateReservationDebutPeriodeUtil);
		cdr.set(Calendar.HOUR_OF_DAY, 0);
		cdr.set(Calendar.MINUTE, 0);
		cdr.set(Calendar.SECOND, 0);
		cdr.set(Calendar.MILLISECOND, 0);
		java.sql.Date dateReservationDebutPeriode = new Date(
				dateReservationDebutPeriodeUtil.getTime());

		// date de fin de la période
		java.util.Date dateReservationFinPeriodeUtil = null;
		try {
			dateReservationFinPeriodeUtil = formatter.parse(dateFinPeriode);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cdr2 = Calendar.getInstance();
		cdr2.setTime(dateReservationFinPeriodeUtil);
		cdr2.set(Calendar.HOUR_OF_DAY, 0);
		cdr2.set(Calendar.MINUTE, 0);
		cdr2.set(Calendar.SECOND, 0);
		cdr2.set(Calendar.MILLISECOND, 0);
		java.sql.Date dateReservationFinPeriode = new Date(
				dateReservationFinPeriodeUtil.getTime());

		// dateCourante
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		java.util.Date utilDate = cal.getTime();
		java.sql.Date now = new Date(utilDate.getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String dateAjd = sdf.format(now);

		if (dateReservationFinPeriode.after(dateReservationDebutPeriode)) {
			if (dateReservationDebutPeriode.after(now)) {
				if (dateReservationFinPeriode.after(now)) {
					String etat = "En attente";
					if (dateReservationDebutPeriodeUtil.equals(now)) {
						etat = "En cours";
					}
					try {
						Reservation.reserverUnePeriode(
								Connexion.getConnexion(), numCLient,
								numStation, dateReservationDebutPeriodeUtil,
								dateDebutPeriode, dateFinPeriode, etat);
					} catch (SQLException e) {
						e.printStackTrace();
					}

				} else {
					System.out
							.println("La date de fin de la période est déjà passée");
				}
			} else {
				System.out
						.println("La date de début de la période est déjà passée");
			}

		} else {
			System.out.println("Vos dates de période sont incohérentes");
		}

	}

	private void emprunterUneJournee(int numCLient) throws SQLException {
		Scanner sc = new Scanner(System.in);
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
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date dateReservationUtil = null;
		try {
			dateReservationUtil = formatter.parse(dateReservationUnique);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cdr = Calendar.getInstance();
		cdr.setTime(dateReservationUtil);
		cdr.set(Calendar.HOUR_OF_DAY, 0);
		cdr.set(Calendar.MINUTE, 0);
		cdr.set(Calendar.SECOND, 0);
		cdr.set(Calendar.MILLISECOND, 0);
		java.sql.Date dateReservation = new Date(dateReservationUtil.getTime());

		// dateCourante
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		java.util.Date utilDate = cal.getTime();
		java.sql.Date now = new Date(utilDate.getTime());

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		String dateResa = sdf.format(now);
		if (dateReservation.after(now)) {
			Reservation.reserverUneJournee(Connexion.getConnexion(),
					numStation, numCLient, dateReservationUnique, "En attente");
		} else if (dateReservation.equals(now)) {
			Reservation.reserverUneJournee(Connexion.getConnexion(),
					numStation, numCLient, dateReservationUnique, "En cours");
		} else {
			System.out
					.println("La date de réservation est antérieure à aujourd'hui.");
		}

	}

	private void rendreVelo() {
		System.out.println("\n--------------------------------");
		System.out.println("---Client - Rendre v�lo---");
		System.out.println("--------------------------------");

		// Afficher borne libre station Saisir code client Yolo

		System.out
				.println("[Simulation]Veuillez saisir le num�ro de la station ou vous �tes : ");
		Scanner sc = new Scanner(System.in);
		int numStation = sc.nextInt();
		try {
			Station.afficherBornesLibres(Connexion.getConnexion(), numStation);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		boolean choixBornetteOK = false;
		int numBornette = 0;
		while (!choixBornetteOK) {
			System.out
					.println("Veuillez choisir une borne parmi celles list�es ci-dessus : ");
			numBornette = sc.nextInt();
			try {
				choixBornetteOK = Station.checkDisponibiliteBornette(
						Connexion.getConnexion(), numBornette, numStation);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		System.out
				.println("[SIMULATION RFID]Saisir le num�ro du v�lo � rendre : ");
		int numVeloRendu = sc.nextInt();
		System.out.println("Saisissez votre code secret : ");
		int codeSecret = sc.nextInt();
		try {
			int numClient = Client.getClientFromCode(Connexion.getConnexion(),
					codeSecret);
			if (numClient != 0) {
				
				int numVeloARendre = Client.getVeloLocation(
						Connexion.getConnexion(), numClient);
				System.out.println("NumVerlo a rendre : "+numVeloARendre);
				if (numVeloRendu == numVeloARendre && numVeloARendre != 0) {
					System.out.println("Same v�lo");

					Client.checkAmende(Connexion.getConnexion(), numClient,
							numVeloRendu);
					String typeRetourStation = Station.getTypeStation(
							Connexion.getConnexion(), numStation);
					System.out.println("Type retour station : "+typeRetourStation);
					
					Client.decompterRemise(Connexion.getConnexion(), numClient);
					Client.checkNouvelleRemise(Connexion.getConnexion(),
							typeRetourStation, numClient, numVeloRendu);
					Station.attacherVeloABornette(Connexion.getConnexion(),numBornette, numVeloRendu);
				}
			}
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
							System.out.println("NBRESA = "+nbResa);
							int nbVeloDispo = Station.getNbVeloDispo(
									Connexion.getConnexion(), numStation);
							System.out.println("nbvelodispo : "+nbVeloDispo);
							int numVelo = Station.getVelo(
									Connexion.getConnexion(), numStation,
									nbVeloDispo, nbResa);
							System.out.println("numVelo : "+numVelo);
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
					System.out
							.println("Veuillez saisir votre num�ro de CB : ");
					Scanner sc2 = new Scanner(System.in);
					String numCB = "";
					numCB = sc2.nextLine();
					int numClient = Client.creerNonAbonne(
							Connexion.getConnexion(), numCB);
					int nbResa = Reservation.getNbResaAjd(
							Connexion.getConnexion(), numStation);
					int nbVeloDispo = Station.getNbVeloDispo(
							Connexion.getConnexion(), numStation);
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