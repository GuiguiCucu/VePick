package Interfaces.Client;

import java.sql.Date;
import java.util.Calendar;
import java.util.Scanner;

public class MenuClient {

	public static void main(String[] args) {
		MenuClient mc = new MenuClient();
		do{
			mc.afficherMenu();
			mc.analyseChoix();
		}while(true);		
	}
	
	public void afficherMenu(){
		System.out.println("--------------------------------");
		System.out.println("-----------Menu Client----------");
		System.out.println("--------------------------------");
		
		System.out.println("Choisissez une action : ");
		System.out.println("1 - S'abonner");
		System.out.println("2 - Consulter les bornes V-, V+");
		System.out.println("3 - Emprunter un vélo");
		System.out.println("4 - Emprunter un vélo");
		System.out.println("5 - Quitter");
	}
	
	public void analyseChoix(){
		Scanner sc = new Scanner(System.in);
		int choix = sc.nextInt();
		switch (choix) {
		case 1:
			System.out.println("--------------------------------");
			System.out.println("-------Client - Abonnement------");
			System.out.println("--------------------------------");
			System.out.println("Veuillez saisir votre nom :");
			String nom = sc.nextLine();
			System.out.println("Veuillez saisir votre prénom :");
			String prenom = sc.nextLine();
			System.out.println("Veuillez saisir votre date de naissance :");
			String dateNaissance = sc.nextLine();
			System.out.println("Veuillez saisir votre sexe (Homme|Femme) :");
			String sexe = sc.nextLine();
			System.out.println("Veuillez saisir votre adresse :");
			String adresse = sc.nextLine();
			System.out.println("Veuillez saisir votre ville :");
			String ville = sc.nextLine();
			System.out.println("Veuillez saisir votre code postal :");
			String cp = sc.nextLine();
			
			Calendar cal = java.util.Calendar.getInstance();
			java.util.Date utilDate = cal.getTime();
			Date dateAbonnement = new Date(utilDate.getTime());
			
		
			
			break;

		default:
			break;
		}
		
	}

}
