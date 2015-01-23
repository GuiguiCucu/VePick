CREATE OR REPLACE TRIGGER SUPERPOSITION_PLAGE_HORAIRE
  BEFORE INSERT OR UPDATE ON PlageHoraire
  FOR EACH ROW
DECLARE
  dateDeb varchar2(45);
BEGIN
  SELECT dateDebut INTO dateDeb
  FROM PlageHoraire
  WHERE numStation =:new.numStation;
  
  IF TO_DATE(dateDeb, 'yyyy/mm/dd hh24:mi:ss') > :new.dateDebut OR TO_DATE(dateF, 'yyyy/mm/dd hh24:mi:ss') < :new.dateFin
  	THEN raise_application_error(-20000 , 'Une plage horaire equivalente existe deja');
  END IF;
END;
/
/*
Ligne existante :
INSERT INTO PlageHoraire values (1, 1, 3, TO_DATE('2015/01/01 12:30:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/01 15:30:00', 'yyyy/mm/dd hh24:mi:ss'));

Test : 
INSERT INTO PlageHoraire values (4, 1, 3, TO_DATE('2015/01/01 12:40:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/01 15:20:00', 'yyyy/mm/dd hh24:mi:ss'));
*/