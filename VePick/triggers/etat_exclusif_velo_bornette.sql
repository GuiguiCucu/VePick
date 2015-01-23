CREATE OR REPLACE TRIGGER ETAT_EXCLUSIF_VELO_BORNETTE
  BEFORE UPDATE ON Bornette
  FOR EACH ROW
DECLARE
  dateDernierChargement date;
  dateDernierDepot date;
  estLoue int;
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
  THEN raise_application_error(-20000 , 'Impossible d accorcher un vélo à une bornette s il se trouve dans un vehicule, il doit d abord etre deposé.');
  END IF;
  
  SELECT numClient INTO estLoue
  FROM Location
  WHERE dateFinLocation IS NULL
  AND numVelo = :new.numVelo;
  
  IF(numClient IS NOT NULL)
  THEN 
  UPDATE Location SET dateFinLocation = NOW()
  END IF;
  
 
END;
/

/*
===== TESTS =====

-- On sait que vélo X est en location par un Client Y, on tente de l'acrocher à une bornette, verifier que la dateFinLocation est bien remplie


--On sait que le Vélo X est dans le vehicule Y, on tente de l'accrocher à une bornette

*/
