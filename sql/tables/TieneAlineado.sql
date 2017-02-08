/*Creación de la tabla Aparece En*/

drop TABLE TieneAlineado cascade constraints;

create table TieneAlineado(
  nombre_usuario varchar(40) NOT NULL,
  nombre_comunidad varchar(40) NOT NULL,
  codigo_jugador int NOT NULL,
  jornada int NOT NULL,
  PRIMARY KEY (nombre_usuario, nombre_comunidad, codigo_jugador, jornada),
  FOREIGN KEY (nombre_usuario, nombre_comunidad, codigo_jugador) REFERENCES TIENE(nombre_usuario, nombre_comunidad, codigo_jugador),
  FOREIGN KEY (codigo_jugador) REFERENCES Jugadores(cod)
);

select * from Tiene;
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador,jornada) VALUES
('rbnuria', 'ComunioDDSI2',1,2);
/*INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('rbnuria', 'ComunioDDSI2',4,1);*/
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('rbnuria', 'ComunioDDSI2',5,2);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('rbnuria', 'ComunioDDSI2',7,2);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('rbnuria', 'ComunioDDSI2',8,2);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('rbnuria', 'ComunioDDSI2',11,2);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('rbnuria', 'ComunioDDSI2',21,2);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('rbnuria', 'ComunioDDSI2',35,2);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('rbnuria', 'ComunioDDSI2',27,2);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('rbnuria', 'ComunioDDSI2',28,2);
/*
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('rbnuria', 'ComunioDDSI2',34,2);*/

INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('elenaro','ComunioDDSI2',14,1);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('elenaro','ComunioDDSI2',16,2);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('elenaro','ComunioDDSI2',19,2);

INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('moyita222','ComunioDDSI',3,1);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('moyita222','ComunioDDSI',6,2);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('moyita222','ComunioDDSI',3,2);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('moyita222','ComunioDDSI',4,2);

INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('juanikerbrahimi','ComunioDDSI',7,1);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('juanikerbrahimi','ComunioDDSI',14,2);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('juanikerbrahimi','ComunioDDSI',15,2);


INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('jpoyatos','ComunioDDSI',8,1);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('jpoyatos','ComunioDDSI',9,1);
INSERT into TieneAlineado(nombre_usuario, nombre_comunidad, codigo_jugador, jornada) VALUES
('jpoyatos','ComunioDDSI',27,2);


/*select count(*) from TieneAlineado where nombre_usuario = 'rbnuria' and jornada = 2;*/
