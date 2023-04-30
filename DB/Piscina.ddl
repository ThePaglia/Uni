-- *********************************************
-- * SQL MySQL generation                      
-- *--------------------------------------------
-- ********************************************* 


-- Database Section
-- ________________ 

create database Piscina;
use Piscina;


-- Tables Section
-- _____________ 

create table ABBONAMENTO (
     numero int not null auto_increment,
     dataInizioValidita date not null,
     dataPagamento date not null,
     CF char(16) not null,
     idFascia int not null,
     durata int not null,
     constraint ID_ABBONAMENTO_ID primary key (numero));

create table CORSIA (
     vasca int not null,
     corsia int not null,
     constraint ID_CORSIA_ID primary key (vasca, corsia));

create table CORSO (
     codCorso int not null auto_increment,
     nome varchar(20) not null,
     descrizione varchar(200) not null,
     durataLezioni int not null,
     prezzo float(6) not null,
     livello varchar(10),
     tipo char(1) not null,
     CF char(16) not null,
     constraint ID_CORSO_ID primary key (codCorso));

create table FASCIA_ORARIA (
     idFascia int not null auto_increment,
     oraInizio time not null,
     oraFine time not null,
     constraint SID_FASCIA_ORARIA_ID unique (oraInizio),
     constraint ID_FASCIA_ORARIA_ID primary key (idFascia));

create table ISCRITTO (
     CF char(16) not null,
     nome varchar(20) not null,
     cognome varchar(20) not null,
     indirizzo varchar(50) not null,
     recapito varchar(10) not null,
     constraint ID_ISCRITTO_ID primary key (CF));

create table iscrizione (
     CF char(16) not null,
     codCorso int not null,
     numeroTurno int not null,
     constraint ID_iscrizione_ID primary key (codCorso, numeroTurno, CF));

create table ISTRUTTORE (
     CF char(16) not null,
     nome varchar(20) not null,
     cognome varchar(20) not null,
     indirizzo varchar(50) not null,
     recapito varchar(10) not null,
     constraint ID_ISTRUTTORE_ID primary key (CF));

create table LEZIONE (
     codCorso int not null,
     numeroTurno int not null,
     giorno varchar(10) not null,
     ora time not null,
     vasca int not null,
     corsia int not null,
     constraint ID_LEZIONE_ID primary key (codCorso, numeroTurno, giorno));

create table TIPO_ABBONAMENTO (
     idFascia int not null,
     durata int not null,
     prezzo float(6) not null,
     constraint ID_TIPO_ABBONAMENTO_ID primary key (idFascia, durata));

create table TURNO (
     codCorso int not null,
     numeroTurno int not null,
     constraint ID_TURNO_ID primary key (codCorso, numeroTurno));


-- Constraints Section
-- ___________________ 

alter table ABBONAMENTO add constraint FKsottoscrizione_FK
     foreign key (CF)
     references ISCRITTO (CF);

alter table ABBONAMENTO add constraint FKtipologia_FK
     foreign key (idFascia, durata)
     references TIPO_ABBONAMENTO (idFascia, durata);

alter table CORSO add constraint FKdocenza_FK
     foreign key (CF)
     references ISTRUTTORE (CF);

alter table iscrizione add constraint FKisc_TUR
     foreign key (codCorso, numeroTurno)
     references TURNO (codCorso, numeroTurno);

alter table iscrizione add constraint FKisc_ISC_FK
     foreign key (CF)
     references ISCRITTO (CF);

alter table LEZIONE add constraint FKluogo_FK
     foreign key (vasca, corsia)
     references CORSIA (vasca, corsia);

alter table LEZIONE add constraint FKprevede
     foreign key (codCorso, numeroTurno)
     references TURNO (codCorso, numeroTurno);

alter table TIPO_ABBONAMENTO add constraint FKper
     foreign key (idFascia)
     references FASCIA_ORARIA (idFascia);

alter table TURNO add constraint FKorganizzazione
     foreign key (codCorso)
     references CORSO (codCorso);


-- Index Section
-- _____________ 

create unique index ID_ABBONAMENTO_IND
     on ABBONAMENTO (numero);

create index FKsottoscrizione_IND
     on ABBONAMENTO (CF);

create index FKtipologia_IND
     on ABBONAMENTO (idFascia, durata);

create unique index ID_CORSIA_IND
     on CORSIA (vasca, corsia);

create unique index ID_CORSO_IND
     on CORSO (codCorso);

create index FKdocenza_IND
     on CORSO (CF);

create unique index SID_FASCIA_ORARIA_IND
     on FASCIA_ORARIA (oraInizio);

create unique index ID_FASCIA_ORARIA_IND
     on FASCIA_ORARIA (idFascia);

create unique index ID_ISCRITTO_IND
     on ISCRITTO (CF);

create unique index ID_iscrizione_IND
     on iscrizione (codCorso, numeroTurno, CF);

create index FKisc_ISC_IND
     on iscrizione (CF);

create unique index ID_ISTRUTTORE_IND
     on ISTRUTTORE (CF);

create unique index ID_LEZIONE_IND
     on LEZIONE (codCorso, numeroTurno, giorno);

create index FKluogo_IND
     on LEZIONE (vasca, corsia);

create unique index ID_TIPO_ABBONAMENTO_IND
     on TIPO_ABBONAMENTO (idFascia, durata);

create unique index ID_TURNO_IND
     on TURNO (codCorso, numeroTurno);

