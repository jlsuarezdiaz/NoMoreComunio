drop table Fichajes;

create table Fichajes(
 nombre_usuario varchar(20) NOT NULL,
  nombre_comunidad varchar(20) NOT NULL,
  codigo_jugador INT NOT NULL,
  precio INT,
  PRIMARY KEY (nombre_usuario, nombre_comunidad, codigo_jugador) 
);