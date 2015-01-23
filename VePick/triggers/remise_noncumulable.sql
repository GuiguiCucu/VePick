CREATE OR REPLACE TRIGGER REMISE_UNIQUE
  BEFORE INSERT ON RemiseNonAbonne
  FOR EACH ROW
DECLARE
  nbRemise int;
BEGIN
  SELECT COUNT(numRemise) INTO nbRemise
  FROM RemiseNonAbonne
  WHERE numClient =:new.numClient;
    
  IF (nbRemise <> 0)
  THEN raise_application_error(-20000 , 'Vous avez deja une remise ');
  END IF;
END;
/

/*Lorsqu'on conssome une remise, on passe la reference numremise de client a null et on supprime le tuple remise correspondant */
/*
On considere que le client non abonne 1 a deja la remiseNonAbo 1
INSERT INTO RemiseNonAbonne values (2, '0000001', 10, TO_DATE('2015/02/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'),1);
*/