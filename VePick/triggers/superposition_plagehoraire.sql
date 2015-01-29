CREATE OR REPLACE TRIGGER SUPERPOSITION_PLAGE_HORAIRE
  AFTER INSERT OR UPDATE ON PlageHoraire
DECLARE
  nb int;
BEGIN
FOR LIGNE IN (SELECT P.dateDebut, P.dateFin, P.numStation 
		FROM PlageHoraire P, Station S WHERE P.numStation = S.numStation 
		GROUP BY P.dateDebut, P.dateFin, P.numStation) LOOP
	SELECT count(idPH) INTO nb
	FROM PlageHoraire 
	WHERE numStation = LIGNE.numStation
	AND (dateDebut > LIGNE.dateDebut AND dateDebut < LIGNE.dateFin) OR (dateFin > LIGNE.dateDebut AND dateFin < LIGNE.dateFin)
	AND numStation IN (SELECT numStation
						FROM PlageHoraire);
	if(nb > 0) then
		Raise_application_error(-20013,'Deux plages horaires ne peuvent pas se chevaucher');
	end if;
END LOOP ;
END;
/

/*
Ligne existante :
INSERT INTO PlageHoraire values (1, 1, 3, TO_DATE('2015/01/04 12:30:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/06 15:30:00', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO PlageHoraire values (7, 1, 3, TO_DATE('2015/01/04 12:30:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/06 15:30:00', 'yyyy/mm/dd hh24:mi:ss'));
Test:
DELETE FROM PlageHoraire WHERE idPh>3;
SELECT * FROM PlageHoraire ORDER BY numStation;
Pas de chevauchement
INSERT INTO PlageHoraire values (4, 1, 3, TO_DATE('2015/01/15 15:40:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/20 19:20:00', 'yyyy/mm/dd hh24:mi:ss'));
date de début dans une plage:
INSERT INTO PlageHoraire values (5, 1, 3, TO_DATE('2015/01/05 12:40:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/07 20:30:00', 'yyyy/mm/dd hh24:mi:ss'));
date de fin dans une plage:
INSERT INTO PlageHoraire values (6, 1, 3, TO_DATE('2015/01/02 12:00:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/05 14:30:00', 'yyyy/mm/dd hh24:mi:ss'));
date de début et de fin dans une plage:
INSERT INTO PlageHoraire values (7, 1, 3, TO_DATE('2015/01/05 12:40:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/05 14:30:00', 'yyyy/mm/dd hh24:mi:ss'));
 Pas de chevauchement:
INSERT INTO PlageHoraire values (8, 1, 3, TO_DATE('2015/01/22 15:40:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/29 19:20:00', 'yyyy/mm/dd hh24:mi:ss'));

*/