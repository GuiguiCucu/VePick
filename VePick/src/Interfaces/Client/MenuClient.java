package Interfaces.Client;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class MenuClient {

	public static void main(String[] args) {
		MenuClient mc = new MenuClient();
		do {
			mc.afficherMenu();
			mc.analyseChoix();
		} while (true);
	}

	public void afficherMenu() {
		System.out.println("--------------------------------");
		System.out.println("-----------Menu Client----------");
		System.out.println("--------------------------------\n\r");
		System.out.println("Choisissez une action : ");
		System.out.println("1 - S'abonner");
		System.out.println("2 - Consulter les bornes V-, V+");
		System.out.println("3 - Emprunter un vélo");
		System.out.println("4 - Emprunter un vélo");
		System.out.println("5 - Quitter");
	}

	private void analyseChoix() {
		Scanner sc = new Scanner(System.in);
		int choix = sc.nextInt();
		switch (choix) {
		case 1:
			actionAbonnement();
			break;
			

		default:
			break;
		}
	}

	private void actionAbonnement() {
		Scanner sc = new Scanner(System.in);
		System.out.println("--------------------------------");
		System.out.println("-------Client - Abonnement------");
		System.out.println("--------------------------------");
		System.out.println("Veuillez saisir votre nom :");
		String nom = sc.nextLine();
		System.out.println("Veuillez saisir votre prénom :");
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
				.println("Votre abonnement a été pris en compte. Date d'expitayion : "
						+ sdf.format(dateFinAbo));
	}
}
