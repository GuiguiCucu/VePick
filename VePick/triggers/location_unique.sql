CREATE OR REPLACE TRIGGER LOCATION_UNIQUE
  BEFORE INSERT ON Location
  FOR EACH ROW
DECLARE
  loc int;
BEGIN
  SELECT COUNT(numClient) INTO loc
  FROM Location
  WHERE numClient =:new.numClient
  AND dateFinLocation IS NULL;
    
  IF (loc <> 0)
  THEN raise_application_error(-20000 , 'Impossible de louer deux vélos en même temps!');
  END IF;
  
END;
/

/*
===== TESTS =====

-- on sait que le Vélo 1 est en cours de location par Client 1 mais toujours pas rendu, on tente une location sur un autre vélo par ce client.

INSERT INTO Location values (1, 3, TO_DATE('2015/01/16 14:34:12', 'yyyy/mm/dd hh24:mi:ss'),NULL, 'Vplus', 'Vmoins');

*/
