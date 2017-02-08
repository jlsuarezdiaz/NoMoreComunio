drop table Comunidad cascade constraints;

create table Comunidad(
  nombre_comunidad varchar(20) NOT NULL PRIMARY KEY,
  pass varchar(20) NOT NULL
);

insert into Comunidad (nombre_comunidad,pass) values ('ComunioDDSI','passComunioDDSI');
insert into Comunidad (nombre_comunidad,pass) values ('ComunioDDSI2','passComunioDDSI2');