drop table EscribeNoticia;

create table EscribeNoticia(
  nombre_us varchar(20) NOT NULL,
  nombre_comunidad varchar(20) NOT NULL,
  codigo_noticia int NOT NULL,
  fecha date,
  PRIMARY KEY (nombre_usu, nombre_comunidad, codigo_noticia),
  FOREIGN KEY (nombre_usu) REFERENCES Usuario(nombre_usu),
  FOREIGN KEY (nombre_comunidad, codigo_noticia) REFERENCES TablonAnuncios(nombre_comunidad,codigo_noticia)
);

