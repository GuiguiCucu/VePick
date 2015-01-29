CREATE OR REPLACE TRIGGER EMPRUNT_HS
  BEFORE INSERT ON Location
  FOR EACH ROW
DECLARE
  etat varchar(2);
BEGIN
  SELECT Velo.etat INTO etat
  FROM Velo
  WHERE Velo.numVelo=:new.numVelo;

  if (etat='HS')
  then raise_application_error(-20000 , 'Impossible de louer un velo HS');
  end if;
END;
/

/*
===== TESTS =====

-- on sait que le Vélo 2 est HS, on tente une location sur ce vélo

INSERT INTO Location values (6, 2, TO_DATE('2015/01/16 14:34:11', 'yyyy/mm/dd hh24:mi:ss'),NULL, 'Vplus', 'Vmoins');

*/
