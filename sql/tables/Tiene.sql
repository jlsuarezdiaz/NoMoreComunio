/*Creación de la tabla Tiene*/

drop TABLE Tiene CASCADE CONSTRAINTS;

create table Tiene(
  nombre_usuario varchar(20) NOT NULL,
  nombre_comunidad varchar(20) NOT NULL,
  codigo_jugador int NOT NULL,
  PRIMARY KEY (nombre_usuario, nombre_comunidad, codigo_jugador),
  FOREIGN KEY(nombre_usuario, nombre_comunidad) REFERENCES Pertenece(nombre_usu, nombre_comunidad),
  FOREIGN KEY (codigo_jugador) REFERENCES Jugadores(cod)

);


INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('rbnuria', 'ComunioDDSI2',1);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('rbnuria', 'ComunioDDSI2',5);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('rbnuria', 'ComunioDDSI2',7);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('rbnuria', 'ComunioDDSI2',8);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('rbnuria', 'ComunioDDSI2',11);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('rbnuria', 'ComunioDDSI2',23);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('rbnuria', 'ComunioDDSI2',27);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('rbnuria', 'ComunioDDSI2',28);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('rbnuria', 'ComunioDDSI2',31);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('rbnuria', 'ComunioDDSI2',32);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('rbnuria', 'ComunioDDSI2',35);
/*
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('elenaro','ComunioDDSI2',4);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('elenaro','ComunioDDSI2',16);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('elenaro','ComunioDDSI2',19);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('elenaro','ComunioDDSI2',20);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('elenaro','ComunioDDSI2',22);
*/

INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('moyita222','ComunioDDSI',1);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('moyita222','ComunioDDSI',2);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('moyita222','ComunioDDSI',4);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('moyita222','ComunioDDSI',5);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('moyita222','ComunioDDSI',8);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('moyita222','ComunioDDSI',10);

INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('juanikerbrahimi','ComunioDDSI',12);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('juanikerbrahimi','ComunioDDSI',14);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('juanikerbrahimi','ComunioDDSI',15);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('juanikerbrahimi','ComunioDDSI',17);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('juanikerbrahimi','ComunioDDSI',18);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('juanikerbrahimi','ComunioDDSI',20);

INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('jpoyatos','ComunioDDSI',23);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('jpoyatos','ComunioDDSI',26);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('jpoyatos','ComunioDDSI',27);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('jpoyatos','ComunioDDSI',29);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('jpoyatos','ComunioDDSI',31);
INSERT into Tiene(nombre_usuario, nombre_comunidad, codigo_jugador) VALUES
('jpoyatos','ComunioDDSI',32);
