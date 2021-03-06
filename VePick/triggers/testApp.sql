DELETE FROM JourReserve;
DELETE FROM Reservation;
DELETE FROM Amende;
DELETE FROM TacheRoutine;
DELETE FROM Tache;
DELETE FROM ActionVehiculeBornette;
DELETE FROM ActionVehiculeVelo;
DELETE FROM ActionVehicule;
DELETE FROM Location;
DELETE FROM RemiseAbonne;
DELETE FROM RemiseNonAbonne;
DELETE FROM Abonne;
DELETE FROM NonAbonne;
DELETE FROM Client;
DELETE FROM VehiculeRegulation;
DELETE FROM Bornette;
DELETE FROM Velo;
DELETE FROM PlageHoraire;
DELETE FROM Station;
DELETE FROM TypeStation;

INSERT INTO TypeStation values (1, 'Vmoins');
INSERT INTO TypeStation values (2, 'Vplus');
INSERT INTO Station values (1, 'Grenoble', '24 rue de Paris', '38000', 1);
INSERT INTO Station values (2, 'Grenoble', 'place Victor Hugo', '38100', 1);
INSERT INTO PlageHoraire values (1, 1, 1, TO_DATE('2015/01/30 12:30:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/30 15:30:00', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO PlageHoraire values (2, 2, 2, TO_DATE('2015/01/30 12:30:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/30 15:30:00', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO Velo values (1, 'VTC', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 'OK');
INSERT INTO Velo values (2, 'VTC', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 'OK');
INSERT INTO Bornette values (1, 'OK', 1, 1);
INSERT INTO Bornette values (2, 'OK', 1, NULL);
INSERT INTO Bornette values (3, 'OK', 2, NULL);

INSERT INTO Client values (1, 422, '4578 7144 7889 4512');
INSERT INTO Abonne values (1, 'Michel', 'Mathieu', TO_DATE('1992/01/16 18:34:11', 'yyyy/mm/dd hh24:mi:ss'), 'Homme', 'Grenoble', '5 place Verdun', 38000, TO_DATE('2015/01/10 18:34:11', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO Location values (1, 2, TO_DATE('2015/01/12', 'yyyy/mm/dd'), NULL, 'Vmoins',NULL);


