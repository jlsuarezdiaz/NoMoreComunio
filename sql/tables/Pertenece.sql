drop table Pertenece cascade constraints;

create table Pertenece(
  nombre_usu varchar(20) NOT NULL,
  nombre_comunidad varchar(20) NOT NULL,
  creditos int,
  administrador int,
  PRIMARY KEY (nombre_usu,nombre_comunidad),
  FOREIGN KEY(nombre_usu) REFERENCES Usuario(nom_usu),
  FOREIGN KEY(nombre_comunidad) REFERENCES Comunidad(nombre_comunidad)
);


insert into Pertenece(nombre_usu,nombre_comunidad,creditos,administrador) values ('jpoyatos','ComunioDDSI',1000000.0,0);
insert into Pertenece(nombre_usu,nombre_comunidad,creditos,administrador) values ('moyita222','ComunioDDSI',1000000.0,0);
insert into Pertenece(nombre_usu,nombre_comunidad,creditos,administrador) values ('juanikerbrahimi','ComunioDDSI',1000000.0,1);
insert into Pertenece(nombre_usu,nombre_comunidad,creditos,administrador) values ('elenaro','ComunioDDSI2',10000000.0,0);
insert into Pertenece(nombre_usu,nombre_comunidad,creditos,administrador) values ('rbnuria','ComunioDDSI2',1000000.0,0);

