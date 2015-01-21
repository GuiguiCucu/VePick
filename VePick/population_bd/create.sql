drop table JourReserve;
drop table Reservation;
drop table Amende;
drop table TacheRoutine;
drop table Tache;
drop table ActionVehiculeBornette;
drop table ActionVehiculeVelo;
drop table ActionVehicule;
drop table Location;
drop table Abonne;
drop table NonAbonne;
drop table RemiseAbonne;
drop table RemiseNonAbonne;
drop table Client;
drop table VehiculeRegulation;
drop table Velo;
drop table Bornette;
drop table PlageHoraire;
drop table Station;
drop table TypeStation;


create table TypeStation (
	idType int NOT NULL,
	libelle varchar(6),
	constraint type_station_pk primary key (idType),
	constraint station_libelle check (libelle = 'Vmoins' OR libelle = 'Vplus' OR libelle ='Vnul')
);

create table Station (
	numStation int NOT NULL,
	ville varchar(45) NOT NULL,
	adresse varchar(45)NOT NULL,
	cp int NOT NULL,
	idType int NOT NULL,
	constraint station_pk primary key (numStation),
	constraint station_fk1 foreign key (idType) references TypeStation(idType)
);

create table PlageHoraire (
	idPh int NOT NULL,
	numStation int NOT NULL,
	idType int NOT NULL,
	dateDebut date NOT NULL,
	dateFin date NOT NULL,
	constraint plage_horaire_pk primary key (idPh),
	constraint plage_horaire_fk1 foreign key (numStation) references Station(numStation),
	constraint plage_horaire_fk2 foreign key (idType) references TypeStation(idType)
);

create table Bornette (
	numBornette int NOT NULL,
	etat varchar(2) NOT NULL,
	numStation int NOT NULL,
	constraint bornette_pk primary key (numBornette),
	constraint bornette_fk foreign key (numStation) references Station(numStation),
	constraint bornette_etat check (etat = 'OK' OR etat = 'HS')
);

create table Velo (
	numVelo int NOT NULL,
	modele varchar(45),
	dateMiseEnService date NOT NULL,
	etat varchar(2) NOT NULL,
	numBornette int,
	constraint velo_pk primary key (numVelo),
	constraint velo_fk1 foreign key (numBornette) references Bornette(numBornette),
	constraint velo_etat check (etat = 'OK' OR etat = 'HS')
);

create table VehiculeRegulation (
	numVehicule int NOT NULL,
	modele varchar(45),
	dateMiseEnService date NOT NULL,
	capacite int NOT NULL,
	constraint vehicule_pk primary key (numVehicule)
);

create table Client (
	numClient int NOT NULL,
	codeSecret int NOT NULL,
	numeroCB varchar(45) NOT NULL,
	constraint client_pk primary key (numClient)
);

create table RemiseNonAbonne (
	numRemise int NOT NULL,
	codeRemise varchar(7) NOT NULL,
	pourCentRemise int NOT NULL,
	datePeremption date NOT NULL,
	constraint remise_non_abo_pk primary key (numRemise)
);

create table RemiseAbonne (
	numRemise int NOT NULL,
	pourCentRemise int NOT NULL,
	constraint remise_abo_pk primary key (numRemise)
);

create table NonAbonne (
	numClient int NOT NULL,
	dateEnregistrement date,
	numRemise int,
	constraint non_abonne_pk primary key (numClient),
	constraint non_abonne_fk1 foreign key (numClient) references Client(numClient),
	constraint non_abonne_fk2 foreign key (numRemise) references RemiseNonAbonne(numRemise)
);

create table Abonne (
	numClient int NOT NULL,
	nom varchar(45) NOT NULL,
	prenom varchar(45) NOT NULL,
	dateNaissance date NOT NULL,
	sexe varchar(5) NOT NULL,
	ville varchar(45) NOT NULL,
	adresse varchar(45) NOT NULL,
	cp int NOT NULL,
	dateAbonnement date NOT NULL,
	numRemise int,
	constraint abonne_pk primary key (numClient),
	constraint abonne_sexe check (sexe = 'Homme' OR sexe = 'Femme'),
	constraint abonne_fk1 foreign key (numClient) references Client(numClient),
	constraint abonne_fk2 foreign key (numRemise) references RemiseNonAbonne(numRemise)
);

create table Location (
	numClient int NOT NULL,
	numVelo int NOT NULL,
	dateLocation date NOT NULL,
	dateFinLocation date,
	TypeStationDepart varchar(6) NOT NULL,
	TypeStationArrivee varchar(6) NOT NULL,
	constraint location_pk primary key (numClient, numVelo, dateLocation),
	constraint location_fk1 foreign key (numClient) references Client(numClient),
	constraint location_fk2 foreign key (numVelo) references Velo(numVelo),
	constraint typestation1 check (TypeStationDepart = 'Vmoins' OR TypeStationDepart = 'Vplus' OR TypeStationDepart ='Vnul'),
	constraint typestation2 check (TypeStationArrivee = 'Vmoins' OR TypeStationArrivee = 'Vplus' OR TypeStationArrivee ='Vnul')
);

create table ActionVehicule (
	idAction int NOT NULL,
	dateAction date NOT NULL,
	constraint action_vehicule_pk primary key (idAction)
);

create table ActionVehiculeVelo (
	idAction int NOT NULL,
	numVehicule int NOT NULL,
	numVelo int NOT NULL,
	nomAction varchar(45) NOT NULL,
	constraint action_vehic_velo_pk primary key (idAction, numVehicule, numVelo),
	constraint action_vehic_velo_fk1 foreign key (idAction) references ActionVehicule(idAction),
	constraint action_vehic_velo_fk2 foreign key (numVehicule) references VehiculeRegulation(numVehicule),
	constraint action_vehic_velo_fk3 foreign key (numVelo) references Velo(numVelo),
	constraint action_vehic_velo_nom check (nomAction = 'Reparation velo' OR nomAction = 'Chargement velo' OR nomAction = 'Depot velo')
);

create table ActionVehiculeBornette (
	idAction int NOT NULL,
	numVehicule int NOT NULL,
	numBornette int NOT NULL,
	nomAction varchar(45) NOT NULL,
	constraint action_vehic_bornette_pk primary key (idAction, numVehicule, numBornette),
	constraint action_vehic_bornette_fk1 foreign key (idAction) references ActionVehicule(idAction),
	constraint action_vehic_bornette_fk2 foreign key (numVehicule) references VehiculeRegulation(numVehicule),
	constraint action_vehic_bornette_fk3 foreign key (numBornette) references Bornette(numBornette),
	constraint action_vehic_bornette_nom check (nomAction = 'Reparation borne')
);

create table Tache (
	numTache int NOT NULL,
	nomTache varchar(45) NOT NULL,
	nbUnite int,
	constraint tache_pk primary key (numTache)
);

create table TacheRoutine (
	numVehicule int NOT NULL,
	numTache int NOT NULL,
	rang int NOT NULL,
	etat varchar(25) NOT NULL,
	constraint routine_pk primary key (numVehicule, numTache),
	constraint routine_fk1 foreign key (numVehicule) references VehiculeRegulation(numVehicule),
	constraint routine_fk2 foreign key (numTache) references Tache(numTache),
	constraint routine_etat check (etat = 'En attente' OR etat = 'Termine')
);

create table Amende(
	numAmende int NOT NULL,
	montant float(5) NOT NULL,
	numVelo int NOT NULL,
	numClient int NOT NULL,
	dateLocation date NOT NULL,
	constraint amende_pk primary key (numAmende),
	constraint amende_fk foreign key (numClient, numVelo, dateLocation) references Location(numClient, numVelo, dateLocation)
);

create table Reservation (
	numStation int NOT NULL,
	numClient int NOT NULL,
	dateReservation date NOT NULL,
	typeReservation varchar(45) NOT NULL,
	etat varchar(25) NOT NULL,
	constraint res_pk primary key (numStation, numClient, dateReservation),
	constraint res_fk1 foreign key (numStation) references Station(numStation),
	constraint res_fk2 foreign key (numClient) references Client(numClient),
	constraint res_type check (typeReservation = 'jour' OR typeReservation = 'jourRepPer' OR typeReservation = 'ttJoursPer'),
	constraint res_etat check (etat = 'En cours' OR etat = 'Termine' OR etat = 'En attente')
);

create table JourReserve (
	numStation int NOT NULL,
	numClient int NOT NULL,
	dateReservation date NOT NULL,
	dateJourReservation date NOT NULL,
	constraint jour_res_pk primary key (numStation, numClient, dateReservation, dateJourReservation),
	constraint jour_res_fk1 foreign key (numStation, numClient, dateReservation) references Reservation(numStation, numClient, dateReservation)
);