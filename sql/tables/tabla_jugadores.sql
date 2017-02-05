/*Creación de la tabla de jugadores*/

/*Por si ya estaba creado*/
/*drop TABLE Jugadores;
*/
create TABLE Jugadores(
  cod int NOT NULL PRIMARY KEY,
  nombre varchar(20),
  equipo varchar(20),
  pos varchar(20),
  precio real
);

/*Inserción de los jugadores*/
/*NOTA: NO SE QUE HACER CON EL COD, LE VOY PONIENDO EL ORDEN EN EL QUE LO INSERTO*/

/*REAL MADRID*/
INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(1, 'Keylor Navas', 'Real Madrid', 'Portero', 2830000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(2, 'Sergio Ramos', 'Real Madrid', 'Defensa', 6930000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(3, 'Daniel Carvajal', 'Real Madrid', 'Defensa', 3760000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(4, 'Raphael Varane', 'Real Madrid', 'Defensa', 4620000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(5, 'Marcelo Vieira', 'Real Madrid', 'Defensa', 5510000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(6, 'Toni Kroos', 'Real Madrid', 'Mediocentro', 7830000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(7, 'Luka Modric', 'Real Madrid', 'Mediocentro', 9070000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(8, 'Lucas Vázquez', 'Real Madrid', 'Mediocentro'7030000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(9, 'Karim Benzema', 'Real Madrid', 'Delantero'10540000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(10, 'Gareth Bale', 'Real Madrid', 'Delantero', 11550000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(11, 'Cristiano Ronaldo', 'Real Madrid', 'Delantero', 15870000);





/*ATLETICO DE MADRID*/
INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(12, 'Jan Oblak', 'Atlético de Madrid', 'Portero', 1290000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(13, 'Diego Godín', 'Atlético de Madrid', 'Defensa', 5180000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(14, 'Filipe Luis', 'Atlético de Madrid', 'Defensa', 4490000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(15, 'Juanfran Torres', 'Atlético de Madrid', 'Defensa', 4010000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(16, 'Stefan Savic', 'Atlético de Madrid', 'Defensa', 3600000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(17, 'Koke', 'Atlético de Madrid', 'Mediocentro', 5710000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(18, 'Yannick Carrasco', 'Atlético de Madrid', 'Mediocentro', 7950000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(19, 'Gabriel Fernández', 'Atlético de Madrid', 'Mediocentro', 1850000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(20, 'Saúl Níguez', 'Atlético de Madrid', 'Mediocentro', 3280000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(21, 'Antoine Griezmann', 'Atlético de Madrid', 'Delantero', 14200000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(22, 'Kevin Gameiro', 'Atlético de Madrid', 'Delantero', 8260000);


/*FC BARCELONA*/
INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(23, 'Ter Stegen', 'FB Barcelona', 'Portero', 2290000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(24, 'Gerard Piqué', 'FB Barcelona', 'Defensa', 8770000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(25, 'Jordi Alba', 'FB Barcelona', 'Defensa', 4280000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(26, 'Javier Mascherano', 'FB Barcelona', 'Defensa', 2690000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(27, 'Sergi Roberto', 'FB Barcelona', 'Defensa', 5100000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(28, 'Andrés Iniesta', 'FB Barcelona', 'Mediocentro', 8710000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(29, 'Sergio Busquets', 'FB Barcelona', 'Mediocentro', 4540000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(30, 'Arda Turan', 'FB Barcelona', 'Mediocentro', 4090000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(31, 'Luis Suárez', 'FB Barcelona', 'Delantero', 17600000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(32, 'Neymar Santos', 'FB Barcelona', 'Delantero', 14000000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(33, 'Lionel Messi', 'FB Barcelona', 'Delantero', 24770000);




/*MÁLAGA FC*/
INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(34, 'Carlos Kameni', 'Málaga FC', 'Portero', 1210000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(35, 'Juan Carlos Pérez', 'Málaga FC', 'Defensa', 880000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(36, 'Diego LLorente', 'Málaga FC', 'Defensa', 1000000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(37, 'Roberto Rosales', 'Málaga FC', 'Defensa', 2680000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(38, 'Mikel Villanueva', 'Málaga FC', 'Defensa', 640000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(39, 'Ignacio Camacho', 'Málaga FC', 'Mediocentro', 6680000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(40, 'Juan Pablo Añor', 'Málaga FC', 'Mediocentro', 3540000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(41, 'Recio', 'Málaga FC', 'Mediocentro', 690000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(42, 'Sandro Ramírez', 'Málaga FC', 'Delantero', 5420000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(43, 'Charles Dias', 'Málaga FC', 'Delantero',1720000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(44, 'Michael Santos', 'Málaga FC', 'Delantero', 1890000);


/*GRANADA FC*/
INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(45, 'Guillermo Ochoa', 'Granada FC', 'Portero', 2690000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(46, 'David Lombán', 'Granada FC', 'Defensa', 910000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(47, 'Dmitri Foulquier', 'Granada FC', 'Defensa', 510000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(48, 'Gastón Silva', 'Granada FC', 'Defensa', 320000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(49, 'Matthieu Saunier', 'Granada FC', 'Defensa', 360000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(50, 'Jérémie Boga', 'Granada FC', 'Mediocentro', 1820000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(51, 'Sergi Samper', 'Granada FC', 'Mediocentro', 820000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(52, 'Mehdi Carcela', 'Granada FC', 'Mediocentro', 1610000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(53, 'Isaac Cuenca', 'Granada FC', 'Mediocentro', 570000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(54, 'Ezequiel Ponce', 'Granada FC', 'Delantero', 650000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(55, 'Artem Kravets', 'Granada FC', 'Delantero', 2950000);



/*CÓRDOBA FC*/
INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(56, 'Pawel Kieszek', 'Córdoba FC', 'Portero', 200000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(57, 'Domingo Cisma', 'Córdoba FC', 'Defensa',260000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(58, 'Héctor Rodas', 'Córdoba FC', 'Defensa',450000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(59, 'José Antonio Caro', 'Córdoba FC', 'Defensa', 860000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(60, 'Zakarya Bergdich', 'Córdoba FC', 'Defensa', 125000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(61, 'Pedro Ríos', 'Córdoba FC', 'Mediocentro', 680000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(62, 'Eduardo Ramos', 'Córdoba FC', 'Mediocentro', 1250000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(63, 'Alejandro Alfaro', 'Córdoba FC', 'Mediocentro', 850000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(64, 'Carlos Caballero', 'Córdoba FC', 'Mediocentro', 200000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(65, 'Rodrigo Ríos', 'Córdoba FC', 'Delantero', 2400000);

INSERT into Jugadores (cod,nombre,equipo,pos, precio) VALUES 
(66, 'Federico Piovaccari', 'Córdoba FC', 'Delantero', 1520000);

