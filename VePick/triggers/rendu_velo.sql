CREATE OR REPLACE TRIGGER RENDU_VELO
	BEFORE UPDATE ON Bornette
	FOR EACH ROW
BEGIN
	-- si on veut rendre un vélo
	if (:old.numvelo <> :new.numVelo) then
	
		raise_application_error(-20001 , 'fuuf');
		
		-- si la borne est déjà occupée
		if(:old.numVelo <> NULL)
			then raise_application_error(-20001 , 'La borne est déjà occupée');
		end if;
		
		-- si la borne est HS
		if (:old.etat = 'HS')
			then raise_application_error(-20002 , 'La borne est HS');
		end if;
		
	end if;
END;
/

/*
===== TESTS =====

-- on essaie de rendre un vélo sur une borne occupée 

UPDATE Bornette
SET numVelo = 2
WHERE numBornette = 1;

-- sur une borne HS

UPDATE Bornette
SET numVelo = 1
WHERE numBornette = 10;

*/
