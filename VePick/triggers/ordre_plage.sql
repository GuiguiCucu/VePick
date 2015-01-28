CREATE OR REPLACE TRIGGER SUPERPOSITION_PLAGE_HORAIRE
  BEFORE INSERT OR UPDATE ON PlageHoraire
  FOR EACH ROW
BEGIN
  IF :new.dateDebut>:new.dateFin
  THEN raise_application_error(-20000 , 'La date debut doit etre anterieur a la date de fin');
  END IF;
END;
/
/*
Ligne existante :
INSERT INTO PlageHoraire values (1, 1, 3, TO_DATE('2015/01/01 12:30:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/01 15:30:00', 'yyyy/mm/dd hh24:mi:ss'));
Test : 
INSERT INTO PlageHoraire values (5, 1, 3, TO_DATE('2015/01/02 12:40:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/01 11:20:00', 'yyyy/mm/dd hh24:mi:ss'));
*/