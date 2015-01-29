CREATE OR REPLACE TRIGGER CAPA_VEHICULE
  BEFORE INSERT ON ActionVehiculeVelo
  FOR EACH ROW
DECLARE
  nbCharge int;
  nbDepot int;
  capaciteVehicule int;
BEGIN
	SELECT COUNT(idAction) into nbCharge
	FROM ActionVehiculeVelo
	WHERE numVehicule=:new.numVehicule AND nomAction='Chargement velo';
	
	SELECT COUNT(idAction) into nbDepot
	FROM ActionVehiculeVelo
	WHERE numVehicule=:new.numVehicule AND nomAction='Depot velo';
	
	SELECT capacite into capaciteVehicule
	FROM VehiculeRegulation
	WHERE numVehicule=:new.numVehicule;
  
	IF(nbCharge - nbDepot >= capaciteVehicule)
	  		THEN raise_application_error(-20000 , 'Le vehicule est plein');
  END IF;
  
END;
/

/*

	SELECT * FROM ActionVehiculeVelo;	
	
On insert des 
INSERT INTO ActionVehiculeVelo values (2, 4, 16, 'Chargement velo');
INSERT INTO ActionVehiculeVelo values (2, 4, 15, 'Depot velo');
INSERT INTO ActionVehiculeVelo values (2, 4, 14, 'Chargement velo');
INSERT INTO ActionVehiculeVelo values (2, 4, 13, 'Chargement velo');
INSERT INTO ActionVehiculeVelo values (2, 4, 12, 'Chargement velo');


*/