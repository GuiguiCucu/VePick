CREATE OR REPLACE TRIGGER REMISE_UNIQUE
  BEFORE INSERT ON RemiseNonAbonne
  FOR EACH ROW
DECLARE
  remise int;
BEGIN
  SELECT numRemise INTO remise
  FROM NonAbonne
  WHERE numRemise =:new.numRemise;
    
  IF remise IS NOT NULL
  THEN raise_application_error(-20000 , 'Vous avez deja une remise');
  END IF;
END;
/

/*Lorsqu'on conssome une remise, on passe la reference numremise de client a null et on supprime le tuple remise correspondant */

