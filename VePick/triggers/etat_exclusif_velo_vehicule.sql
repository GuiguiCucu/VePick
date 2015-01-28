CREATE OR REPLACE TRIGGER ETAT_EXCLUSIF_VELO_VEHICULE
  BEFORE INSERT ON ActionVehiculeVelo
  FOR EACH ROW
DECLARE
  dateDernierChargement date;
  dateDernierDepot date;
  surBornette int;
  estLoue int;
BEGIN
   
	IF(:new.nomAction =  'Chargement velo') THEN	
		  SELECT COUNT(numVelo) INTO surBornette
		  FROM Bornette
		  WHERE numVelo = :new.numVelo;
		  
		  IF( surBornette <> 0)
		  		THEN UPDATE Bornette SET numVelo = null WHERE numVelo = :new.numVelo;
		  END IF;
		  
		  
		  SELECT COUNT(numClient) INTO estLoue
		  FROM Location
		  WHERE dateFinLocation IS NULL
		  AND numVelo = :new.numVelo;
		  
		  IF(estLoue <> 0) THEN
		  		raise_application_error(-20000 , 'Impossible de charger le vélo il est en cours de location, il doit d abord etre remis à une bornette par le client.');
		  END IF;
	 
	END IF;
 
END;
/

/*
===== TESTS =====

-- On sait que vélo 7 est accroché à la bornette 7, on tente de charger le vélo, verifier que le vélo est bien decroché de cette bornette
-- INSERT INTO ActionVehicule values (3, SYSDATE);
-- INSERT INTO ActionVehiculeVelo values (3, 3, 7, 'Chargement velo');

--On sait que le Vélo 1 est en location, on tente de le charger, verifier qu'une erreur est retourné (EXTENSION: on peut autorisé le cas si c'est un vélo abandonné)
-- INSERT INTO ActionVehiculeVelo values (3, 3, 1, 'Chargement velo');
*/
