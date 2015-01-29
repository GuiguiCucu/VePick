CREATE OR REPLACE TRIGGER LOCATION_AMENDE
	BEFORE INSERT ON Location
	FOR EACH ROW
DECLARE
	nbAmende1 int;
	nbAmende2 int;
BEGIN
	SELECT count(Amende.numAmende) INTO nbAmende1
	FROM Amende
	WHERE Amende.numClient=:new.numClient;

  	-- si le client a deux amendes
	if (nbAmende1>=2) then
		raise_application_error(-20005 , 'Impossible de louer, vous avez deja deux amendes');
	end if;
	
	-- si le client a une amende impayee depuis plus d'un mois
	SELECT count(Amende.numAMende) INTO nbAmende2
	FROM Amende
	WHERE Amende.numCLient=:new.numClient
	AND Amende.dateLocation < sysdate-31;
	
	if (nbAmende2>=1) then
		raise_application_error(-20005 , 'Impossible de louer, vous avez une amende impayee de plus de 1 mois');
	end if;
END;
/

/*
===== TESTS =====

--1er test : on insert deux amendes pour le client 2. On essaie de lui attribuer une location.

INSERT INTO Amende values (2,3.2,3,2,TO_DATE('2015/01/12 12:20:35', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO Amende values (3,3.2,3,2,TO_DATE('2015/01/12 12:20:35', 'yyyy/mm/dd hh24:mi:ss'));

INSERT INTO Location values (2, 6, TO_DATE('201/02/17 14:34:11', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2015/02/18 14:34:11', 'yyyy/mm/dd hh24:mi:ss'), 'Vplus', 'Vmoins');

--2eme test : on insert une location de plus d'un mois pour le client 3, puis on lui crée un amende, ensuite on tente de de louer un velo pour le client 3

INSERT INTO Location values (3, 4, TO_DATE('2014/12/10 14:34:11', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2014/12/11 14:34:11', 'yyyy/mm/dd hh24:mi:ss'), 'Vplus', 'Vmoins');
INSERT INTO Amende values (4,3.2,4,3,TO_DATE('2014/12/10 14:34:11', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO Location values (3, 4, TO_DATE('2015/01/10 14:34:11', 'yyyy/mm/dd hh24:mi:ss'),TO_DATE('2015/01/10 14:55:11', 'yyyy/mm/dd hh24:mi:ss'), 'Vplus', 'Vmoins');


*/
