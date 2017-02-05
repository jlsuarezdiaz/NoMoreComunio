/*Creación de la tabla Aparece En*/

/*drop TABLE TieneAlineado;*/

create table TieneAlineado(
  nombre_usuario varchar(20) NOT NULL,
  nombre_comunidad varchar(20) NOT NULL,
  codigo_jugador int NOT NULL,
  jornada int,
  PRIMARY KEY (nombre_usuario, nombre_comunidad, codigo_jugador, jornada),
  FOREIGN KEY (nombre_usuario) REFERENCES Usuario(nom_usu),
  FOREIGN KEY (nombre_comunidad) REFERENCES Comunidad(nombre_comunidad),
  FOREIGN KEY (codigo_jugador) REFERENCES Jugadores(cod)

);

INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador,jornada) VALUES
('rbnuria', 'ComunioDDSI2',1,1);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('rbnuria', 'ComunioDDSI2',4,1);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('rbnuria', 'ComunioDDSI2',5,2);

INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('elenaro','ComunioDDSI2',14,1);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('elenaro','ComunioDDSI2',16,2);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('elenaro','ComunioDDSI2',19,2);

INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('moyita222','ComunioDDSI',1,1);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('moyita222','ComunioDDSI',2,1);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('moyita222','ComunioDDSI',4,2);

INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('juanikerbrahimi','ComunioDDSI',12,1);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('juanikerbrahimi','ComunioDDSI',14,2);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('juanikerbrahimi','ComunioDDSI',15,2);


INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('jpoyatos','ComunioDDSI',23,1);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('jpoyatos','ComunioDDSI',26,1);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('jpoyatos','ComunioDDSI',27,2);