/*Creación de la tabla Aparece En*/

drop TABLE ApareceEn cascade constraints;

create table ApareceEn(
  nombre_vendedor varchar(20) NOT NULL,
  nombre_comunidad varchar(20) NOT NULL,
  codigo_jugador int NOT NULL,
  precio_min INT,
  PRIMARY KEY (nombre_comunidad, codigo_jugador),
  FOREIGN KEY (nombre_comunidad) REFERENCES Mercado(nombre_com),
  FOREIGN KEY (codigo_jugador) REFERENCES Jugadores(cod)

);
select * from ApareceEN;

INSERT into ApareceEn( nombre_vendedor, nombre_comunidad, codigo_jugador, precio_min) VALUES
('rbnuria','ComunioDDSI2',1, 100000);

INSERT into ApareceEn( nombre_vendedor, nombre_comunidad, codigo_jugador, precio_min) VALUES
('elenaro','ComunioDDSI2',4, 100000);
INSERT into ApareceEn( nombre_vendedor, nombre_comunidad, codigo_jugador, precio_min) VALUES
('moyita222','ComunioDDSI',1, 200000);
/*INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI2',5);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI2',7);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI2',8);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI2',11);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI2',14);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI2',16);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI2',19);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI2',20);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI2',22);*/

/*INSERT into ApareceEn( nombre_comunidad, codigo_jugador, precio_min) VALUES
('ComunioDDSI',1, 100000);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI',2, 150000);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI',4, 200000);/*
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI',5);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI',8);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI',10);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI',12);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI',14);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI',15);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI',17);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI',18);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI',20);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI',23);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI',26);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI',27);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI',29);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI',31);
INSERT into ApareceEn( nombre_comunidad, codigo_jugador) VALUES
('ComunioDDSI',32);*/
