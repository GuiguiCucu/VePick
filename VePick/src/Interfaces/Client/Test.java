package Interfaces.Client;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) {

		 System.out.println("Votre location a été enregistré. Vous pouvez retirer le vélo n°"+2);
		 System.out.println("La station dans laquelle vous empruntez actuellement est de type "+"vnul");
		 java.util.Calendar cal = java.util.Calendar.getInstance();
		 java.util.Date utilDate = cal.getTime();
		 cal.setTime(utilDate);
		 cal.add(Calendar.HOUR,12);
		 Date dateFinLoc = new Date(cal.getTimeInMillis());
		 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
		 System.out.println("La location doit-être rendu avant "+
		 sdf.format(dateFinLoc) +" au plus tard");

//		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
//		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MMM-yy");
//		Calendar cal = java.util.Calendar.getInstance();
//		java.util.Date utilDate = cal.getTime();
//		Date dateAbonnement = new Date(utilDate.getTime());
//		System.out.println(sdf.format(dateAbonnement));
//		System.out.println(sdf1.format(dateAbonnement));

//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(dateAbonnement);
//		calendar.add(Calendar.YEAR, 1);
//		Date dateFinAbo = new Date(calendar.getTimeInMillis());
//		System.out.println(sdf.format(dateFinAbo));

		Scanner sc = new Scanner(System.in);
		String strdate = sc.nextLine();

		if (!strdate.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) {
			System.exit(0);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		java.util.Date dateNaissanceUtil = null;
		try {
			dateNaissanceUtil = formatter.parse(strdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cdr = Calendar.getInstance();
		cdr.setTime(dateNaissanceUtil);
		Date dateNaissance = new Date(cdr.getTimeInMillis());
		System.out.println(dateNaissance.toString());
		System.out.println(sdf.format(dateNaissance));
		//Date dateNaissance = new Date(dateNaissanceUtil.getTime());
		System.out.println(dateNaissance.toString());
		System.out.println(sdf.format(dateNaissance));

	}

}
