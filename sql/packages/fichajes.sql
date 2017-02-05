CREATE OR REPLACE PACKAGE BODY PKG_FICHAJES AS

FUNCTION precio_minimo(codigo_jugador INT, nombre_comunidad VARCHAR2) return INTEGER IS precio INTEGER;
BEGIN
  select precio_min into precio from ApareceEn where codigo_jugador=codigo_jugador and nombre_comunidad=nombre_comunidad;
  return(precio);
END;

PROCEDURE Pujar(nomb_usuario VARCHAR2, nomb_comunidad VARCHAR2,id_jugador INTEGER, cantidad INT) AS
BEGIN 
 
 IF (precio_minimo(id_jugador, nomb_comunidad) <= cantidad) THEN
   INSERT into RealizarOferta(nombre_usuario,nombre_comunidad,codigo_jugador,precio, estado) values (nomb_usuario, nomb_comunidad, id_jugador, cantidad, 0);
 END IF;
END Pujar;

PROCEDURE ofrecer_sistema(nomb_comunidad VARCHAR2,id_jugador INTEGER) AS
BEGIN 
  
  INSERT into ApareceEn(NOMBRE_VENDEDOR, nombre_comunidad,codigo_jugador,precio_min) values ('COMPUTER',nomb_comunidad, id_jugador, (select precio from Jugadores where cod=id_jugador));
END ofrecer_sistema;

PROCEDURE ofrecer_jugador(nomb_usuario VARCHAR2, nomb_comunidad VARCHAR2, id_jugador INTEGER, precio INT) AS
BEGIN  
  INSERT into ApareceEn(NOMBRE_VENDEDOR, nombre_comunidad,codigo_jugador,precio_min) values (nomb_usuario,nomb_comunidad, id_jugador, precio);
END ofrecer_jugador;



END PKG_FICHAJES;
