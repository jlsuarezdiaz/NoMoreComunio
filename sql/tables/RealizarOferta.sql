/*Creación tabla Realizar Oferta*/

drop TABLE REALIZAROFERTA CASCADE CONSTRAINTS;
create table RealizarOferta(
  nombre_usuario varchar(20) NOT NULL,
  nombre_comunidad varchar(20) NOT NULL,
  codigo_jugador INT NOT NULL,
  precio INT,
  estado INT, /*0: no fichado, 1: fichado */
  PRIMARY KEY (nombre_usuario, nombre_comunidad, codigo_jugador),
  FOREIGN KEY (nombre_usuario) REFERENCES Usuario(nom_usu),
  FOREIGN KEY(nombre_comunidad, codigo_jugador) REFERENCES APARECEEN(nombre_comunidad, codigo_jugador) 
);



select * from RealizarOferta;



INSERT into RealizarOferta(nombre_usuario, nombre_comunidad, codigo_jugador, precio, estado) VALUES
('elenaro', 'ComunioDDSI2',2, 13, 0);

INSERT into RealizarOferta(nombre_usuario, nombre_comunidad, codigo_jugador, precio, estado) VALUES
('elenaro', 'ComunioDDSI2',11, 15, 0);
/*
INSERT into RealizarOferta(nombre_usuario, nombre_comunidad, codigo_jugador, precio) VALUES
('rbnuria', 'ComunioDDSI2',17, 5713200);
INSERT into RealizarOferta(nombre_usuario, nombre_comunidad, codigo_jugador, precio) VALUES
('rbnuria', 'ComunioDDSI2',66, 1525100);

INSERT into RealizarOferta(nombre_usuario, nombre_comunidad, codigo_jugador, precio) VALUES
('elenaro','ComunioDDSI2',55, 2950999);
INSERT into RealizarOferta(nombre_usuario, nombre_comunidad, codigo_jugador, precio) VALUES
('elenaro','ComunioDDSI2',18, 7970000);
INSERT into RealizarOferta(nombre_usuario, nombre_comunidad, codigo_jugador, precio) VALUES
('elenaro','ComunioDDSI2',6, 7834400);

INSERT into RealizarOferta(nombre_usuario, nombre_comunidad, codigo_jugador, precio) VALUES
('moyita222','ComunioDDSI',56, 200050);
INSERT into RealizarOferta(nombre_usuario, nombre_comunidad, codigo_jugador, precio) VALUES
('moyita222','ComunioDDSI',60, 129000);
INSERT into RealizarOferta(nombre_usuario, nombre_comunidad, codigo_jugador, precio) VALUES
('moyita222','ComunioDDSI',65, 2444000);

INSERT into RealizarOferta(nombre_usuario, nombre_comunidad, codigo_jugador, precio) VALUES
('juanikerbrahimi','ComunioDDSI',19, 1850022);
INSERT into RealizarOferta(nombre_usuario, nombre_comunidad, codigo_jugador, precio) VALUES
('juanikerbrahimi','ComunioDDSI',39, 6880000);
INSERT into RealizarOferta(nombre_usuario, nombre_comunidad, codigo_jugador, precio) VALUES
('juanikerbrahimi','ComunioDDSI',49, 361000);

INSERT into RealizarOferta(nombre_usuario, nombre_comunidad, codigo_jugador, precio) VALUES
('jpoyatos','ComunioDDSI',25, 4280000);
INSERT into RealizarOferta(nombre_usuario, nombre_comunidad, codigo_jugador, precio) VALUES
('jpoyatos','ComunioDDSI',52, 1612000);
INSERT into RealizarOferta(nombre_usuario, nombre_comunidad, codigo_jugador, precio) VALUES
('jpoyatos','ComunioDDSI',45, 2690001);*/
