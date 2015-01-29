CREATE OR REPLACE TRIGGER RECOUVREMENT_RESERVATION_JOUR
	BEFORE INSERT ON JourReserve
	FOR EACH ROW
DECLARE
	nbReservation int;
BEGIN
	SELECT COUNT(*) INTO nbReservation
	FROM JourReserve 
	WHERE numClient = :new.numClient
	AND numStation = :new.numStation
	AND dateJourReservation = :new.dateJourReservation;
	
	IF(nbReservation <> 0) THEN
		raise_application_error(-20000 , 'La reservation du '|| TRUNC(:new.dateJourReservation,'DD') || ' n a pas t enregistr car vous en avez deja une pour ce jour.');
	END IF;
END;
/

/*
===== TESTS =====
-- On sait que le client X a reserv pour un jour Y, on tente de reserv sur ce jour, une erreur est retourn.
--INSERT INTO Reservation values (2,2,TO_DATE('2015/01/09 22:20:00', 'yyyy/mm/dd hh24:mi:ss'),'jour','En attente');
--INSERT INTO JourReserve values (2,2,TO_DATE('2015/01/09 22:20:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/02/05 12:00:00', 'yyyy/mm/dd hh24:mi:ss'));

--INSERT INTO Reservation values (2,2,TO_DATE('2015/01/09 22:21:00', 'yyyy/mm/dd hh24:mi:ss'),'jour','En attente');
--INSERT INTO JourReserve values (2,2,TO_DATE('2015/01/09 22:21:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/02/05 12:00:00', 'yyyy/mm/dd hh24:mi:ss'));

*/
