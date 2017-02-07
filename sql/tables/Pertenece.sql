/*drop table Pertenece cascade constraints;*/

create table Pertenece(
  nombre_usu varchar(20) NOT NULL,
  nombre_comunidad varchar(20) NOT NULL,
  creditos int,
  administrador int,
  puntos INT,
  PRIMARY KEY (nombre_usu,nombre_comunidad),
  FOREIGN KEY(nombre_usu) REFERENCES Usuario(nom_usu),
  FOREIGN KEY(nombre_comunidad) REFERENCES Comunidad(nombre_comunidad)
);


insert into Pertenece(nombre_usu,nombre_comunidad,creditos,administrador, puntos) values ('jpoyatos','ComunioDDSI',20.0,0,0);
insert into Pertenece(nombre_usu,nombre_comunidad,creditos,administrador, puntos) values ('moyita222','ComunioDDSI',20.0,0,0);
insert into Pertenece(nombre_usu,nombre_comunidad,creditos,administrador, puntos) values ('juanikerbrahimi','ComunioDDSI',200000000000000.0,1,0);
insert into Pertenece(nombre_usu,nombre_comunidad,creditos,administrador, puntos) values ('elenaro','ComunioDDSI2',100000000000000.0,0,0);
insert into Pertenece(nombre_usu,nombre_comunidad,creditos,administrador, puntos) values ('rbnuria','ComunioDDSI2',20.0,0,0);

select * from Pertenece;