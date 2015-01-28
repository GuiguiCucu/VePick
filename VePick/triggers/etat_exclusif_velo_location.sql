CREATE OR REPLACE TRIGGER ETAT_EXCLUSIF_VELO_LOCATION
  BEFORE INSERT ON Location
  FOR EACH ROW
DECLARE
  dateDernierChargement date;
  dateDernierDepot date;
  surBornette int;
  
  nbDateCharg int;
  nbDateDepot int;
  
  bothNotNull boolean;
  
BEGIN
	
  SELECT COUNT(dateAction) INTO nbDateCharg
  FROM ActionVehiculeVelo AVV, ActionVehicule AV
  WHERE AVV.idAction = AV.idAction
  AND numVelo = :new.numVelo
  AND nomAction = 'Chargement velo';
	
  IF(nbDateCharg <> 0)
  THEN
	  SELECT MAX(dateAction) INTO dateDernierChargement
	  FROM ActionVehiculeVelo AVV, ActionVehicule AV
	  WHERE AVV.idAction = AV.idAction
	  AND numVelo = :new.numVelo
	  AND nomAction = 'Chargement velo'
	  GROUP BY dateAction;
  END IF;
  
  
  SELECT COUNT(dateAction) INTO nbDateDepot
  FROM ActionVehiculeVelo AVV, ActionVehicule AV
  WHERE AVV.idAction = AV.idAction
  AND numVelo = :new.numVelo
  AND nomAction = 'Depot velo';
  
  
  IF(nbDateDepot <> 0)
  THEN
	  SELECT MAX(dateAction) INTO dateDernierDepot
	  FROM ActionVehiculeVelo AVV, ActionVehicule AV
	  WHERE AVV.idAction = AV.idAction
	  AND numVelo = :new.numVelo
	  AND nomAction = 'Depot velo'
	  GROUP BY dateAction;
  END IF;
  
--  bothNotNull := (dateDernierChargement IS NOT NULL AND dateDernierDepot IS NULL) OR (dateDernierChargement IS NULL AND dateDernierDepot IS NOT NULL) OR (dateDernierChargement IS NOT NULL AND dateDernierDepot IS NOT NULL);
  IF(dateDernierDepot IS NOT NULL AND dateDernierChargement IS NOT NULL)
  	  THEN
	  IF(dateDernierDepot < dateDernierChargement OR (dateDernierDepot IS NULL AND dateDernierChargement IS NOT NULL))
	  		THEN raise_application_error(-20000 , 'Impossible de louer un vélo s il se trouve dans un vehicule, il doit d abord etre deposé puis accroché sur une bornette.');
	  END IF;
  END IF;
  
  SELECT COUNT(numVelo) INTO surBornette
  FROM Bornette
  WHERE numVelo = :new.numVelo;
  
  SELECT COUNT(numVelo)
  FROM Bornette
  WHERE numVelo = 6;
  
  IF( surBornette <> 0)
  THEN UPDATE Bornette SET numVelo = null WHERE numVelo = :new.numVelo;
  END IF;
  
  
 
END;
/

/*
===== TESTS =====

-- On sait que vélo 6 est accroché à une bornette 6, on tente de le louer par un client, verifier que le vélo est bien decroché de cette bornette
--INSERT INTO Location values (6, 6, SYSDATE,NULL, 'Vplus', NULL);


--On sait que le Vélo X est dans un vehicule, on tente de le louer par un client, verifier qu'une erreur est retourné
--TODO
*/
