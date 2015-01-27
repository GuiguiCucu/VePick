-- idée du truc: faire une seule grosse requête (sans "for each row") pour voir s'il n'existe pas de TacheRoutine de rang > qui serait validée (jeeeeeeeej)

CREATE OR REPLACE TRIGGER ORDRE_ROUTINE
	BEFORE UPDATE ON TacheRoutine
DECLARE
	prochaineTache int;
BEGIN
	-- On enregistre la prochaine tâche, c'est à dire le rang le plus petit qui est "en attente"
	SELECT MIN(rang) INTO prochaineTache
	FROM TacheRoutine
	WHERE numVehicule=:old.numVehicule AND etat='En attente';

  	-- si on essaye d'en modifier un autre --> erreur
	if (:old.rang <> prochaineTache) then
		raise_application_error(-20005 , 'Il ya a des taches précédentes encore en attente');
	end if;
	
END;
/

/*
 * 
 * 
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
SET etat="Echec"
WHERE numVehicule=1 AND numTache=1 AND rang=1;



SELECT MIN(rang) FROM TacheRoutine WHERE numVehicule=1 AND etat='En attente';
*/
