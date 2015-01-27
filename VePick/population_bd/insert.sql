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
INSERT INTO TypeStation values (3, 'Vnul');

INSERT INTO Station values (1, 'Grenoble', '24 rue de Paris', '38000', 3);
INSERT INTO Station values (2, 'Grenoble', '24 rue de Paris', '38000', 3);
INSERT INTO Station values (3, 'Grenoble', '24 rue de Paris', '38000', 3);
INSERT INTO Station values (4, 'Grenoble', '24 rue de Paris', '38000', 3);

INSERT INTO PlageHoraire values (1, 1, 3, TO_DATE('2015/01/01 12:30:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/01 15:30:00', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO PlageHoraire values (2, 2, 3, TO_DATE('2015/01/01 10:00:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/01 12:00:00', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO PlageHoraire values (3, 3, 3, TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/01 18:00:00', 'yyyy/mm/dd hh24:mi:ss'));

INSERT INTO Velo values (1, 'VTC', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 'OK');
INSERT INTO Velo values (2, 'VTC', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 'HS');
INSERT INTO Velo values (3, 'VTC', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 'OK');
INSERT INTO Velo values (4, 'VTC', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 'OK');
INSERT INTO Velo values (5, 'VTC', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 'OK');
INSERT INTO Velo values (6, 'VTC', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 'OK');
INSERT INTO Velo values (7, 'VTC', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 'OK');
INSERT INTO Velo values (8, 'VTC', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 'OK');
INSERT INTO Velo values (9, 'VTC', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 'OK');
INSERT INTO Velo values (10, 'VTC', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 'OK');
INSERT INTO Velo values (11, 'VTC', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 'OK');
INSERT INTO Velo values (12, 'VTC', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 'OK');
INSERT INTO Velo values (13, 'VTC', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 'OK');
INSERT INTO Velo values (14, 'VTC', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 'OK');
INSERT INTO Velo values (15, 'VTC', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 'OK');
INSERT INTO Velo values (16, 'VTC', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 'OK');

INSERT INTO Bornette values (1, 'OK', 1, 1);
INSERT INTO Bornette values (2, 'OK', 1, 2);
INSERT INTO Bornette values (3, 'OK', 2, 3);
INSERT INTO Bornette values (4, 'OK', 2, NULL);
INSERT INTO Bornette values (5, 'OK', 3, NULL);
INSERT INTO Bornette values (6, 'OK', 3, 6);
INSERT INTO Bornette values (7, 'OK', 4, 7);
INSERT INTO Bornette values (8, 'OK', 4, 8);
INSERT INTO Bornette values (9, 'OK', 4, NULL);
INSERT INTO Bornette values (10, 'HS', 4, NULL);

INSERT INTO VehiculeRegulation values (1, 'Traffic', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 10);
INSERT INTO VehiculeRegulation values (2, 'Transporteur T4', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 10);
INSERT INTO VehiculeRegulation values (3, 'X-Wing', TO_DATE('2015/01/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'), 10);

INSERT INTO Client values (1, 422, '4578 7144 7889 4512');
INSERT INTO Client values (2, 423, '4578 7144 7889 7412');
INSERT INTO Client values (3, 512, '4578 7144 7889 7892');
INSERT INTO Client values (4, 321, '4578 7144 7889 7493');

INSERT INTO NonAbonne values (1, TO_DATE('2015/01/16 14:34:11', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO NonAbonne values (2, TO_DATE('2015/01/16 14:34:11', 'yyyy/mm/dd hh24:mi:ss'));

INSERT INTO Abonne values (2, 'Michel', 'Mathieu', TO_DATE('1992/01/16 18:34:11', 'yyyy/mm/dd hh24:mi:ss'), 'Homme', 'Grenoble', '5 place Verdun', 38000, TO_DATE('2015/01/10 18:34:11', 'yyyy/mm/dd hh24:mi:ss'));

INSERT INTO RemiseNonAbonne values (1, '0000001', 10, TO_DATE('2015/02/01 16:00:00', 'yyyy/mm/dd hh24:mi:ss'),1);

INSERT INTO RemiseAbonne values (1,10, 2);


INSERT INTO Location values (1, 1, TO_DATE('2015/01/16 14:34:11', 'yyyy/mm/dd hh24:mi:ss'),NULL, 'Vplus', 'Vmoins');
INSERT INTO Location values (2, 3, TO_DATE('2015/01/12 12:20:35', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/12 13:05:11', 'yyyy/mm/dd hh24:mi:ss'), 'Vnul','Vnul');

INSERT INTO Location values (4, 4, TO_DATE('2015/01/10 14:34:00', 'yyyy/mm/dd hh24:mi:ss'),NULL, 'Vplus', 'Vmoins');

INSERT INTO ActionVehicule values (1, TO_DATE('2015/01/15 00:34:11', 'yyyy/mm/dd hh24:mi:ss'));
INSERT INTO ActionVehicule values (2, TO_DATE('2015/01/15 08:34:11', 'yyyy/mm/dd hh24:mi:ss'));

INSERT INTO ActionVehiculeVelo values (1, 2, 4, 'Reparation velo');
INSERT INTO ActionVehiculeVelo values (2, 2, 5, 'Chargement velo');

INSERT INTO ActionVehiculeBornette values (1, 1, 7, 'Reparation borne');

INSERT INTO Tache values (1, 'Aller au centre de reparation', 4);

INSERT INTO TacheRoutine values (1, 1, 1, 'En attente');
INSERT INTO TacheRoutine values (2, 1, 1, 'En attente');

INSERT INTO Amende values (1,3.2,1,1,TO_DATE('2015/01/16 14:34:11', 'yyyy/mm/dd hh24:mi:ss'));

INSERT INTO Reservation values (1,2,TO_DATE('2015/01/09 22:00:00', 'yyyy/mm/dd hh24:mi:ss'),'jour','Termine');

INSERT INTO JourReserve values (1,2,TO_DATE('2015/01/09 22:00:00', 'yyyy/mm/dd hh24:mi:ss'), TO_DATE('2015/01/29 12:00:00', 'yyyy/mm/dd hh24:mi:ss'));