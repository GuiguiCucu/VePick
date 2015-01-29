CREATE OR REPLACE TRIGGER RENDU_VELO
	BEFORE UPDATE ON Bornette
	FOR EACH ROW
DECLARE
	oldNumVelo int;
	newNumVelo int;
	etat varchar(2);
BEGIN
	oldNumVelo:=:old.numVelo;
	newNumVelo:=:new.numVelo;
	etat:=:old.etat;
	
	-- si on veut rendre un vélo sur une borne deja occupee
	if (oldNumVelo IS NOT NULL AND oldNumVelo <> newNumVelo) then
		raise_application_error(-20001 , 'La borne est deja occupee');
	end if;
	
	-- si on veut rendre un vélo sur une borne HS
	if (oldNumVelo IS NULL AND newNumVelo IS NOT NULL AND etat = 'HS') then
		raise_application_error(-20002 , 'La borne est HS');
	end if;
END;
/

/*
===== TESTS =====

-- on essaie de rendre un vélo sur une borne occupée 

UPDATE Bornette
SET numVelo = 16
WHERE numBornette = 8;

-- sur une borne HS

UPDATE Bornette
SET numVelo = 16
WHERE numBornette = 10;

*/
