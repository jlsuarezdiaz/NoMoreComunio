drop table TablonAnuncios;

create table TablonAnuncios(
  nombre_comunidad varchar(20) NOT NULL,
  codigo_noticia int NOT NULL,
  PRIMARY KEY (nombre_comunidad,codigo_noticia),
  FOREIGN KEY (nombre_comunidad) REFERENCES Comunidad(nombre_comunidad)
);

insert into TablonAnuncios (nombre_comunidad,codigo_noticia) values ('ComunioDDSI',1);
insert into TablonAnuncios (nombre_comunidad,codigo_noticia) values ('ComunioDDSI',2);
insert into TablonAnuncios (nombre_comunidad,codigo_noticia) values ('ComunioDDSI',3);