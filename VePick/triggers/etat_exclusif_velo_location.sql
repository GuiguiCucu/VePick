CREATE OR REPLACE TRIGGER ETAT_EXCLUSIF_VELO_LOCATION
  BEFORE INSERT ON Location
  FOR EACH ROW
DECLARE
  dateDernierChargement date;
  dateDernierDepot date;
  surBornetter int;
BEGIN
	
  SELECT MAX(dateAction) INTO dateDernierChargement
  FROM ActionVehiculeVelo AVV, ActionVehicule AV
  WHERE AVV.idAction = AV.idAction
  AND numVelo = :new.numVelo
  AND nomAction = 'Chargement velo'
  GROUP BY dateAction;
  
  SELECT MAX(dateAction) INTO dateDernierDepot
  FROM ActionVehiculeVelo AVV, ActionVehicule AV
  WHERE AVV.idAction = AV.idAction
  AND numVelo = :new.numVelo
  AND nomAction = 'Depot velo'
  GROUP BY dateAction;
  
  IF(dateDernierDepot < dateDernierChargement)
  THEN raise_application_error(-20000 , 'Impossible de louer un vélo s il se trouve dans un vehicule, il doit d abord etre deposé puis accroché sur une bornette.');
  END IF;
  
  
  SELECT COUNT(numVelo) INTO surBornette
  FROM Bornette
  WHERE numVelo = :new.numVelo
  
  IF( surBornette <> 0)
  THEN UPDATE Bornette SET numVelo = null WHERE numVelo = :new.numVelo
  END IF;
  
  
 
END;
/

/*
===== TESTS =====

-- On sait que vélo X est accroché à une bornette, on tente de le louer par un client, verifier que le vélo est bien decroché de cette bornette
--TODO

--On sait que le Vélo X est dans un vehicule, on tente de le louer par un client, verifier qu'une erreur est retourné
--TODO
*/
