CREATE OR REPLACE PACKAGE BODY PKG_GLOBAL AS


FUNCTION calcMax(comunidad VARCHAR2) return INTEGER IS maximo INTEGER;
contador INT;
BEGIN
  select count(*) into contador from TablonAnuncios where comunidad=nombre_comunidad;
  
  IF contador > 0 THEN
      select max(codigo_noticia) into maximo from TablonAnuncios where comunidad=nombre_comunidad; 
  ELSE
     return(0);
  END IF;
  return(maximo);
END;

FUNCTION totalJugadores return INTEGER IS total INTEGER;
BEGIN
  SELECT COUNT (*) INTO total FROM Jugadores;
  return (total);
END;

PROCEDURE escribeNoticia(usuario VARCHAR2, comunidad VARCHAR2, noticia VARCHAR2) AS
maximo INTEGER;
BEGIN
  insert into TablonAnuncios(nombre_comunidad,codigo_noticia,noticia) values (comunidad,calcMax(comunidad)+1,noticia);
  insert into EscribeNoticia(nombre_us,nombre_comunidad,codigo_noticia,fecha) values (usuario,comunidad,calcMax(comunidad),SYSDATE);

END;

PROCEDURE registrarJugador(nombre VARCHAR2, equipo VARCHAR2, pos VARCHAR2, precio INTEGER) AS
BEGIN
  INSERT INTO Jugadores  VALUES (totalJugadores+1, nombre, equipo, pos, precio);
END;


/*
DECLARE devolver SYS_REFCURSOR;
BEGIN
  PKG_global.listaComunidades('rbnuria', devolver);
END;
*/
PROCEDURE listaComunidades(usuario VARCHAR2, devolver OUT SYS_REFCURSOR) AS
  nombre_comunidad VARCHAR(40);
BEGIN
  OPEN devolver FOR
  SELECT nombre_comunidad FROM PERTENECE where nombre_usu = usuario;
  
  /*
  LOOP
    FETCH devolver INTO nombre_comunidad;
    dbms_output.put_line(nombre_comunidad);
    EXIT WHEN (devolver%NOTFOUND);
  END LOOP;
  
    WHILE(devolver%FOUND) LOOP
      FETCH devolver INTO nombre_comunidad;
      dbms_output.put_line(nombre_comunidad);
    END LOOP;
*/  

END;

/*
PROCEDURE getNoticias(comunidad VARCHAR2) AS
noticia VARCHAR(400);
fecha DATE;
usuario VARCHAR(20);
BEGIN
  OPEN devolver FOR
  SELECT 
END;

*/
END PKG_GLOBAL;