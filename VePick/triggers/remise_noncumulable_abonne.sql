CREATE OR REPLACE TRIGGER REMISE_UNIQUE_ABONNE
  BEFORE INSERT ON RemiseAbonne
  FOR EACH ROW
DECLARE
  nbRemise int;
BEGIN
  SELECT COUNT(numRemise) INTO nbRemise
  FROM RemiseAbonne
  WHERE numClient =:new.numClient;
    
  IF (nbRemise <> 0)
  THEN raise_application_error(-20000 , 'Vous avez deja une remise abonne');
  END IF;
END;
/

/*Lorsqu'on conssome une remise, on passe la reference numremise de client a null et on supprime le tuple remise correspondant */
/*On considere que l'abonne 2 a deja la remiseAbo 1
--INSERT INTO RemiseAbonne values (2,10, 2);