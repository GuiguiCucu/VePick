CREATE OR REPLACE TRIGGER ETAT_EXCLUSIF_VELO_BORNETTE
  BEFORE UPDATE ON Bornette
  FOR EACH ROW
DECLARE
  dateDernierChargement date;
  dateDernierDepot date;
  estLoue int;
  
  curDate date;
BEGIN
	
	DBMS_OUTPUT.ENABLE;
	
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
  THEN raise_application_error(-20000 , 'Impossible d accorcher un vélo à une bornette s il se trouve dans un vehicule, il doit d abord etre deposé.');
  END IF;
  
  SELECT COUNT(numClient) INTO estLoue
  FROM Location
  WHERE dateFinLocation IS NULL
  AND numVelo = :new.numVelo;
  		
  DBMS_OUTPUT.PUT_LINE(estLoue);
  
  IF(estLoue <> 0)
  THEN 
  	DBMS_OUTPUT.PUT_LINE('le velo est loue');
  
  		UPDATE Location SET dateFinLocation = SYSDATE
  		WHERE dateFinLocation IS NULL 
  		AND numVelo = :new.numVelo;
  END IF;
  
  EXCEPTION
   When NO_DATA_FOUND then
   	curDate := SYSDATE; 
  
END;
/

/*
===== TESTS =====

-- On sait que vélo X est en location par un Client Y, on tente de l'acrocher à une bornette, verifier que la dateFinLocation est bien remplie
UPDATE Bornette SET numVelo = 4 WHERE numBornette = 4;

--On sait que le Vélo X est dans le vehicule Y, on tente de l'accrocher à une bornette
UPDATE Bornette SET numVelo = 5 WHERE numBornette = 5;
*/
