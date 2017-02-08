CREATE OR REPLACE PACKAGE BODY PKG_GLOBAL AS

FUNCTION obtenerCodigoPrimero return INTEGER IS codigo INTEGER;
BEGIN
  select cod into codigo from (select cod from Jugadores order by dbms_random.value) where rownum=1;
  return(codigo);
END;

PROCEDURE obtenerUsuarios(comunidad VARCHAR2, devolver OUT SYS_REFCURSOR) AS
BEGIN
  OPEN devolver FOR
  select nombre_usu from Pertenece where NOMBRE_COMUNIDAD=comunidad;
END;

PROCEDURE modificarMercado(numjug INTEGER, comunidad VARCHAR2) AS
  contador INTEGER;
  codigo INTEGER;
  precioj INTEGER;
  estaen INTEGER;
  cantidad INTEGER;
  
BEGIN
  select count(*) into cantidad from ApareceEn where nombre_comunidad=comunidad and nombre_vendedor='COMPUTER';
  IF (cantidad > 0) THEN
    delete from ApareceEn where nombre_comunidad=comunidad and nombre_vendedor='COMPUTER';
  END IF;
  
  FOR contador IN 1..numjug LOOP
    codigo:=obtenerCodigoPrimero;
      
    select count(*) into estaen from ApareceEn where codigo_jugador=codigo and nombre_comunidad=comunidad;   
    IF (estaen = 0) THEN
      select precio into precioj from Jugadores where cod=codigo; 
      insert into ApareceEn values ('COMPUTER',comunidad,codigo,precioj);
    END IF;
    
  END LOOP;
END;

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

FUNCTION estaEnComunidad(nombre VARCHAR2, comunidad VARCHAR2) return INTEGER IS nc INTEGER;
BEGIN
  select count(*) into nc from Pertenece where nombre_usu=nombre and comunidad = nombre_comunidad;
  return(nc);
END;

FUNCTION totalJugadores return INTEGER IS total INTEGER;
contador INT;
BEGIN
  SELECT COUNT (*) INTO contador FROM Jugadores;
  
  IF contador > 0 THEN
    select max(cod) into total from Jugadores;
  ELSE
    return(0);
  END IF;
  return (total);
END;

PROCEDURE escribeNoticia(usuario VARCHAR2, comunidad VARCHAR2, noticia VARCHAR2) AS
maximo INTEGER;
BEGIN
  IF (estaEnComunidad(usuario,comunidad) =  1) THEN
    insert into TablonAnuncios(nombre_comunidad,codigo_noticia,noticia) values (comunidad,calcMax(comunidad)+1,noticia);
    insert into EscribeNoticia(nombre_us,nombre_comunidad,codigo_noticia,fecha) values (usuario,comunidad,calcMax(comunidad),SYSDATE);
  END IF;
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
DECLARE devolver SYS_REFCURSOR;
BEGIN
  PKG_global.getNoticias('ComunioDDSI2', devolver);
END;
*/
PROCEDURE getNoticias(comunidad VARCHAR2, devolver OUT SYS_REFCURSOR) AS
noticia VARCHAR(400);
fecha DATE;
usuario VARCHAR(20);
BEGIN
  OPEN devolver FOR
  SELECT noticia, fecha, nombre_us FROM 
  (select * from EscribeNoticia, TABLONANUNCIOS 
  where escribenoticia.nombre_comunidad = tablonanuncios.nombre_comunidad 
  and escribenoticia.codigo_noticia = tablonanuncios.codigo_noticia and escribenoticia.nombre_comunidad = comunidad);
  /*
  LOOP
    FETCH devolver INTO noticia, fecha, usuario;
    EXIT WHEN (devolver%NOTFOUND);

  END LOOP;
  CLOSE devolver;
  */
END;

PROCEDURE obtenerDinero(usuario VARCHAR2, comunidad VARCHAR2, dinero OUT INTEGER) AS
BEGIN
  select creditos into dinero from pertenece where nombre_usu=usuario and nombre_comunidad=comunidad;
END;

END PKG_GLOBAL;
