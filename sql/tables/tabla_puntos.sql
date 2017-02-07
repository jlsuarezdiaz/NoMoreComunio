/*Creación tabla de puntos*/

drop TABLE Puntos cascade constraints;

create TABLE Puntos
(
  cod_jugador int NOT NULL,
  cod_jornada int NOT NULL,
  goles int,
  asistencias int,
  t_amarillas int check(t_amarillas >= 0 and t_amarillas <=2),
  t_rojas int check(t_rojas >= 0 and t_rojas <=1),
  valoracion int check (valoracion >= 0 and valoracion <= 4),
  PRIMARY KEY(cod_jugador, cod_jornada),
  FOREIGN KEY(cod_jugador) REFERENCES Jugadores(cod)
);

/* DATOS DE LA PRIMERA JORNADA */
/*

INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas,valoracion) VALUES (1,1,1,0,1,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas,valoracion) VALUES (2,1,0,0,1,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas,valoracion) VALUES (3,1,0,1,0,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas, valoracion) VALUES (4,1,0,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas, valoracion) VALUES (5,1,0,2,0,0,2);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas, valoracion) VALUES (6,1,0,0,0,1,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas, valoracion) VALUES (7,1,0,2,0,0,3);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas, valoracion) VALUES (8,1,0,1,1,0,2);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas, valoracion) VALUES (9,1,0,3,1,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas, valoracion) VALUES (10,1,0,2,0,0,2);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas, valoracion) VALUES (11,1,3,3,0,0,4);


/*
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas,valoracion) VALUES (12,1,2,0,1,0,0);

/*
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (13,1,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (14,1,0,1,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (15,1,0,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (16,1,0,1,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (17,1,1,0,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (18,1,0,2,2,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (19,1,0,5,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (20,1,0,1,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (21,1,1,3,2,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (22,1,1,1,0,0);



INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (23,1,2,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (24,1,0,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (25,1,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (26,1,0,1,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (27,1,1,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (28,1,1,0,2,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (29,1,0,2,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (30,1,2,0,1,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (31,1,0,2,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (32,1,2,2,1,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (33,1,0,1,0,0);


INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (34,1,4,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (35,1,0,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (36,1,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (37,1,0,1,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (38,1,0,0,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (39,1,0,2,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (40,1,0,1,2,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (41,1,1,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (42,1,0,3,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (43,1,1,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (44,1,1,0,0,0);

INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (45,1,4,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (46,1,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (47,1,0,1,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (48,1,0,0,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (49,1,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (50,1,0,1,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (51,1,0,3,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (52,1,1,1,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (53,1,0,3,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (54,1,2,2,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (55,1,1,3,1,0);

INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (56,1,4,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (57,1,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (58,1,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (59,1,0,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (60,1,0,1,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (61,1,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (62,1,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (63,1,0,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (64,1,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (65,1,1,2,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (66,1,1,3,0,0);
*/
/* DATOS DE LA SEGUNDA JORNADA */


INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas, valoracion) VALUES (1,2,2,1,0,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas, valoracion) VALUES (2,2,0,0,1,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas, valoracion) VALUES (3,2,0,0,1,0,2);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas, valoracion) VALUES (4,2,0,0,0,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas, valoracion) VALUES (5,2,0,0,0,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas, valoracion) VALUES (6,2,0,0,1,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas, valoracion) VALUES (7,2,0,1,0,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas,valoracion) VALUES (8,2,0,1,0,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas, valoracion) VALUES (9,2,0,1,1,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas, valoracion) VALUES (10,2,1,2,0,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas,valoracion ) VALUES (11,2,3,2,2,0,1);

/*
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (12,2,4,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (13,2,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (14,2,0,1,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (15,2,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (16,2,0,0,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (17,2,0,1,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (18,2,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (19,2,0,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (20,2,0,1,1,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (21,2,0,3,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (22,2,1,1,0,0);

INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (23,2,1,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (24,2,0,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (25,2,0,0,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (26,2,0,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (27,2,0,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (28,2,0,2,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (29,2,0,1,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (30,2,0,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (31,2,0,2,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (32,2,1,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (33,2,2,1,0,0);

INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (34,2,4,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (35,2,0,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (36,2,0,1,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (37,2,0,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (38,2,0,0,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (39,2,0,1,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (40,2,0,1,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (41,2,1,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (42,2,0,1,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (43,2,2,1,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (44,2,1,2,0,0);

INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (45,2,4,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (46,2,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (47,2,0,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (48,2,0,0,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (49,2,0,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (50,2,0,1,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (51,2,0,1,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (52,2,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (53,2,0,1,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (54,2,1,1,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (55,2,1,3,1,0);

INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (56,2,3,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (57,2,0,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (58,2,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (59,2,0,0,0,1);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (60,2,0,1,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (61,2,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (62,2,0,0,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (63,2,0,1,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (64,2,0,0,0,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (65,2,2,2,1,0);
INSERT into Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas) VALUES (66,2,1,3,0,0);
*/
SELECT COUNT(*) from puntos;