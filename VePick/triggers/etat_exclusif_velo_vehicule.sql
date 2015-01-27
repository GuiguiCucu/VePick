CREATE OR REPLACE TRIGGER ETAT_EXCLUSIF_VELO_VEHICULE
  BEFORE INSERT ON ActionVehiculeVelo
  FOR EACH ROW
DECLARE
  dateDernierChargement date;
  dateDernierDepot date;
  surBornetter int;
BEGIN

  
  
  SELECT COUNT(numVelo) INTO surBornette
  FROM Bornette
  WHERE numVelo = :new.numVelo
  
  IF( surBornette <> 0)
  THEN UPDATE Bornette SET numVelo = null WHERE numVelo = :new.numVelo
  END IF;
  
  SELECT COUNT(numClient) INTO estLoue
  FROM Location
  WHERE dateFinLocation IS NULL
  AND numVelo = :new.numVelo;
  
  IF(estLoue <> 0)
  THEN 
  	--TODO
  END IF;
  
 
END;
/

/*
===== TESTS =====

-- On sait que vélo X est accroché à une bornette, on tente de charger le vélo, verifier que le vélo est bien decroché de cette bornette
--TODO

--On sait que le Vélo X est en location, on tente de charger un vélo loué, verifier qu'une erreur est retourné 
(TODO DEMANDER LES CONDITION OU CA PEUT MARCHER)
--TODO
*/
