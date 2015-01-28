CREATE OR REPLACE TRIGGER SUPERPOSITION_PLAGE_HORAIRE
  BEFORE INSERT OR UPDATE ON PlageHoraire
DECLARE
  nb int;
BEGIN
FOR LIGNE IN (SELECT P.dateDebut, P.dateFin, P.numStation 
		FROM PlageHoraire P, Station S WHERE P.numStation = S.numStation 
		GROUP BY P.dateDebut, P.dateFin, P.numStation) LOOP
	SELECT count(idPH) INTO nb
	FROM PlageHoraire 
	WHERE numStation = LIGNE.numStation
	AND dateDebut > LIGNE.dateDebut AND dateDebut < LIGNE.dateFin;  
--	AND numStation IN (SELECT numStation
--						FROM PlageHoraire
--						WHERE etat <> 'En attente')
	
	if(nb > 0) then
		Raise_application_error(-20013,'Deux plages horaires ne peuvent pas se chevaucher');
	end if;
END LOOP ;
END;
/
	
  SELECT dateDebut, dateFin INTO dateDeb, dateF
  FROM PlageHoraire
  WHERE numStation =:new.numStation;
  
  IF (dateDeb < :new.dateFin AND :new.dateFin < dateF) -- OR (dateDeb < :new.dateDebut AND :new.dateDebut < dateF)
  THEN raise_application_error(-20000 , 'Deux plages horaires ne peuvent pas se chevaucher');
  END IF;
END;
/
/*
Ligne existante :
INSERT INTO PlageHoraire values (1, 1, 3, TO_DATE('2015/01/04 12:30:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/06 15:30:00', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO PlageHoraire values (7, 1, 3, TO_DATE('2015/01/04 12:30:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/06 15:30:00', 'yyyy/mm/dd hh24:mi:ss'));
Test:
Pas de chevauchement
INSERT INTO PlageHoraire values (4, 1, 3, TO_DATE('2015/01/07 15:40:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/08 19:20:00', 'yyyy/mm/dd hh24:mi:ss'));
date de début dans une plage:
INSERT INTO PlageHoraire values (5, 1, 3, TO_DATE('2015/01/05 12:40:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/07 20:30:00', 'yyyy/mm/dd hh24:mi:ss'));
date de fin dans une plage:
INSERT INTO PlageHoraire values (6, 1, 3, TO_DATE('2015/01/02 12:00:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/05 14:30:00', 'yyyy/mm/dd hh24:mi:ss'));
date de début et de fin dans une plage:
INSERT INTO PlageHoraire values (7, 1, 3, TO_DATE('2015/01/05 12:40:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/05 14:30:00', 'yyyy/mm/dd hh24:mi:ss'));
 
SELECT count(idPH) 
	FROM PlageHoraire 
	WHERE numStation = 1
	AND dateDebut > TO_DATE('2015/01/04 12:30:00', 'yyyy/mm/dd hh24:mi:ss') AND dateDebut < TO_DATE('2015/01/06 15:30:00', 'yyyy/mm/dd hh24:mi:ss'); 
*/