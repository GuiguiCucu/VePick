CREATE OR REPLACE TRIGGER RECOUVREMENT_RESERVATION_JOUR
	BEFORE INSERT ON JourReserve
	FOR EACH ROW
DECLARE
	nbReservation int;
BEGIN
	SELECT COUNT(*) INTO nbReservation
	FROM Reservation R, JourReserve JR
	WHERE R.etat = 'En cours' 
	OR R.etat = 'En attente'
	AND R.numClient = :new.numClient
	AND JR.dateJourReservation = :new.dateJourReservation;
	
	IF(nbReservation <> 0) THEN
		raise_application_error(-20000 , 'La reservation du '|| TRUNC(:new.dateJourReservation,'DD') || ' n a pas été enregistré car vous en avez deja une pour ce jour.');
	END IF;
END;
/

/*
===== TESTS =====
-- On sait que le client X a reservé pour un jour Y, on tente de reservé sur ce jour, une erreur est retourné.
--INSERT INTO Reservation values (2,2,TO_DATE('2015/01/09 22:20:00', 'yyyy/mm/dd hh24:mi:ss'),'jour','En attente');
--INSERT INTO JourReserve values (2,2,TO_DATE('2015/01/09 22:20:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/02/05 12:00:00', 'yyyy/mm/dd hh24:mi:ss'));

--INSERT INTO Reservation values (2,2,TO_DATE('2015/01/09 22:21:00', 'yyyy/mm/dd hh24:mi:ss'),'jour','En attente');
--INSERT INTO JourReserve values (2,2,TO_DATE('2015/01/09 22:21:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/02/05 12:00:00', 'yyyy/mm/dd hh24:mi:ss'));

*/
