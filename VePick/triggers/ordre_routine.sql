CREATE OR REPLACE TRIGGER ORDRE_ROUTINE
	AFTER UPDATE ON TacheRoutine
DECLARE
	nb int;
BEGIN
	-- On recherche pour chaque véhicule s'il n'a pas de tâche validé de rang > à la prochaine
FOR LIGNE IN (SELECT DISTINCT T.numVehicule, T.numTache, MIN(rang) AS minimum 
		FROM TacheRoutine T, VehiculeRegulation V WHERE T.numVehicule = V.numVehicule 
		AND T.etat='En attente' GROUP BY T.numVehicule, T.numTache) LOOP
	--dbms_output.put_line('Début avec : idRoutines '|| LIGNE.idRoutines || 'et minimum : ' || LIGNE.minimum);	
	SELECT count(numTache) INTO nb
	FROM TacheRoutine 
	WHERE numVehicule = LIGNE.numVehicule AND numTache = LIGNE.numTache
	AND rang > LIGNE.minimum
	AND numVehicule IN (SELECT numVehicule
						FROM TacheRoutine
						WHERE etat <> 'En attente')
	AND numTache IN (SELECT numTache
						FROM TacheRoutine
						WHERE etat <> 'En attente')
	AND rang IN (SELECT rang
						FROM TacheRoutine
						WHERE etat <> 'En attente');
	--dbms_output.put_line('idRoutines '|| LIGNE.idRoutines || 'avec' || nb);	
	if(nb > 0) then
		Raise_application_error(-20013,'Vous devez valider toutes les taches précédentes');
	end if;
END LOOP ;
	
END;
/

/*
 * 
 * jeej
 * 
 * 
 TacheRoutine(numVehicule int, numTache int, rang int, etat varchar(25))
SELECT * FROM TacheRoutine;

===== TESTS =====
INSERT INTO TacheRoutine values (1, 1, 2, 'En attente');
INSERT INTO TacheRoutine values (1, 1, 3, 'En attente');

Valider la prochaine tache:
UPDATE TacheRoutine 
SET etat='Echec'
WHERE numVehicule=1 AND numTache=1 AND rang=1;

Valider une tache qui n'est pas la prochaine:
UPDATE TacheRoutine 
SET etat='Echec'
WHERE numVehicule=1 AND numTache=1 AND rang=3;

SELECT count(numTache)
	FROM TacheRoutine 
	WHERE numVehicule = 1 AND numTache = 1 
	AND rang > 2
	AND numVehicule IN (SELECT numVehicule
						FROM TacheRoutine
						WHERE etat <> 'En attente')
	AND numTache IN (SELECT numTache
						FROM TacheRoutine
						WHERE etat <> 'En attente')
	AND rang IN (SELECT rang
						FROM TacheRoutine
						WHERE etat <> 'En attente');

SELECT MIN(rang) FROM TacheRoutine WHERE numVehicule=1 AND etat='En attente';
*/
