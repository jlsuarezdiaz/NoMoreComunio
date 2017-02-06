CREATE OR REPLACE PACKAGE BODY PKG_GLOBAL AS

FUNCTION calcMax(comunidad VARCHAR2) return INTEGER IS maximo INTEGER;
BEGIN
  select max(codigo_noticia) into maximo from TablonAnuncios where comunidad=nombre_comunidad; 
  return(maximo);
END;

FUNCTION totalJugadores return INTEGER IS total INTEGER;
BEGIN
  SELECT COUNT (*) INTO total FROM Jugadores;
  return (total);
END;

PROCEDURE escribeNoticia(usuario VARCHAR2, comunidad VARCHAR2, noticia VARCHAR2) AS
BEGIN
  insert into TablonAnuncios (nombre_comunidad,codigo_noticia,noticia) values (comunidad,calcMax(comunidad)+1,noticia);
  insert into EscribeNoticia (nombre_us,nombre_comunidad,codigo_noticia,fecha) values (usuario,comunidad,calcMax(comunidad),SYSDATE);
END;

PROCEDURE registrarJugador(nombre VARCHAR2, equipo VARCHAR2, pos VARCHAR2, precio INTEGER) AS
BEGIN
  INSERT INTO Jugadores VALUES (totalJugadores+1, nombre, equipo, pos, precio);
END;

/*Probarlo*/
PROCEDURE listaComunidades(usuario VARCHAR2) AS
BEGIN
  SELECT nombre_comunidad FROM Pertenece WHERE nombre_usu = usuario;
END;

PROCEDURE getNoticias(comunidad VARCHAR2) AS
BEGIN
  SELECT * FROM EscribeNoticia WHERE nombre_comunidad = comunidad;
END;


END PKG_GLOBAL;