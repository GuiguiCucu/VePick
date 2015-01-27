CREATE OR REPLACE TRIGGER RANG_TACHE
	BEFORE INSERT ON TacheRoutine
	FOR EACH ROW
DECLARE
	rangMax int;
BEGIN
	-- On récupère le rang max des tâches associées à ce véhicule
	SELECT MAX(rang) INTO rangMax
  	FROM TacheRoutine
  	WHERE numVehicule = :new.numVehicule;
	-- On vérifie que la nouvelle tâches correspond bien au rang suivant
	IF (:new.rang <> rangMax + 1)
	THEN raise_application_error(-20000 , 'Le rang choisi n est pas valide, ils doivent être consécutifs');
	END IF;
END;
/
/*
===== TESTS =====

SELECT * FROM TacheRoutine;
-- on essaie d'entrer un rang  valide
INSERT INTO TacheRoutine values (1, 1, 3, 'En attente');

-- rang trop grand
INSERT INTO TacheRoutine values (1, 1, 5, 'En attente');
-- rang trop petit
INSERT INTO TacheRoutine values (1, 1, 1, 'En attente');
-- rang trop grand d'un aute véhicule
INSERT INTO TacheRoutine values (2, 1, 3, 'En attente');




*/
