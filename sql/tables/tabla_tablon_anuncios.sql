drop table TablonAnuncios cascade constraints;

create table TablonAnuncios(
  nombre_comunidad varchar(20) NOT NULL,
  codigo_noticia int NOT NULL,
  noticia VARCHAR(400),
  PRIMARY KEY (nombre_comunidad,codigo_noticia),
  FOREIGN KEY (nombre_comunidad) REFERENCES Comunidad(nombre_comunidad)
);

select * from TablonAnuncios;