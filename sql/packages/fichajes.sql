CREATE OR REPLACE PACKAGE BODY PKG_FICHAJES AS

/*PROCEDURE Pujar(nomb_usuario VARCHAR2, nomb_comunidad VARCHAR2,id_jugador INTEGER, cantidad REAL) AS
BEGIN 
 
 IF (select precio_min from ApareceEn where codigo_jugador=id_jugador and NOMBRE_COMUNIDAD=nomb_comunidad) <= cantidad THEN
  INSERT into RealizarOferta(nombre_usuario,nombre_comunidad,codigo_jugador,precio) values (nomb_usuario, nomb_comunidad, id_jugador, cantidad);
  END IF;
END Pujar;*/

PROCEDURE ofrecer_sistema(nomb_comunidad VARCHAR2,id_jugador INTEGER) AS
BEGIN 
  
  INSERT into ApareceEn(NOMBRE_VENDEDOR, nombre_comunidad,codigo_jugador,precio_min) values ('COMPUTER',nomb_comunidad, id_jugador, (select precio from Jugadores where cod=id_jugador));
END ofrecer_sistema;

PROCEDURE ofrecer_jugador(nomb_usuario VARCHAR2, nomb_comunidad VARCHAR2, id_jugador INTEGER, precio REAL) AS
BEGIN 
  
  INSERT into ApareceEn(NOMBRE_VENDEDOR, nombre_comunidad,codigo_jugador,precio_min) values (nomb_usuario,nomb_comunidad, id_jugador, precio);
END ofrecer_jugador;



END PKG_FICHAJES;
