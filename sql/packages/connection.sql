CREATE OR REPLACE PACKAGE BODY PKG_CONNECTION AS

PROCEDURE login(nombre_usuario VARCHAR2, passwd IN VARCHAR2, oklog OUT VARCHAR2) AS
BEGIN
  select count(*) into oklog from USUARIO where nom_usu = nombre_usuario and pass = passwd;
END;

FUNCTION existe_usuario(nombre_usuario VARCHAR2) return INTEGER is existe INTEGER;
BEGIN
  select count(*) into existe from USUARIO where nombre_usuario = nom_usu;
  return(existe);
END;

PROCEDURE registrar(nombre_usuario VARCHAR2, nombre VARCHAR2, apellido VARCHAR2, pssword VARCHAR2, mail VARCHAR2) AS
BEGIN 
  IF(existe_usuario(nombre_usuario) = 0) THEN
    INSERT into USUARIO (nom_usu,nombre,apellidos,pass,email) values (nombre_usuario, nombre, apellido, pssword, mail);
  END IF;
END registrar;


FUNCTION pertenece(usuario VARCHAR2, comunidad VARCHAR2) return INTEGER IS pert INTEGER;
BEGIN
  select count(*) into pert from PERTENECE where nombre_comunidad=comunidad and nombre_usu = usuario;
  return(pert);
END;

FUNCTION generaPortero RETURN INTEGER IS codigo INTEGER;
BEGIN
  select cod into codigo from (select cod from Jugadores where pos='Portero' order by dbms_random.value) where rownum=1;
  return(codigo);
END;

FUNCTION generaCentro RETURN INTEGER IS codigo INTEGER;
BEGIN
  select cod into codigo from (select cod from Jugadores where pos='Mediocentro' order by dbms_random.value) where rownum=1;
  return(codigo);
END;

FUNCTION generaDefensas RETURN INTEGER IS codigo INTEGER;
BEGIN
  select cod into codigo from (select cod from Jugadores where pos='Defensa' order by dbms_random.value) where rownum=1;
  return(codigo);
END;

FUNCTION generaDelantero RETURN INTEGER IS codigo INTEGER;
BEGIN
  select cod into codigo from (select cod from Jugadores where pos='Delantero' order by dbms_random.value) where rownum=1;
  return(codigo);
END;


PROCEDURE addJugadores(usuario VARCHAR2, comunidad VARCHAR2) AS
num_porteros INT;
num_porteros_usuario INT;
portero INT;
num_centros INT;
num_centros_usuario INT;
centro INT;
num_defensas INT;
num_defensas_usuario INT;
defensa INT;
num_delanteros INT;
num_delanteros_usuario INT;
delantero INT;
BEGIN
  num_porteros_usuario:=0;
  LOOP
    
    portero := generaPortero;
    select count(*) into num_porteros from Tiene where portero = codigo_jugador and nombre_comunidad = comunidad;
    dbms_output.put_line(num_porteros);
    dbms_output.put_line(portero);
    IF (num_porteros = 0) THEN
      insert into tiene values(usuario, comunidad, portero);
      num_porteros_usuario:=num_porteros_usuario+1;
    END IF;
  EXIT WHEN(num_porteros_usuario=1);
  END LOOP;
  
  num_centros_usuario:=0;
  LOOP
    centro := generaCentro;
    select count(*) into num_centros from Tiene where centro = codigo_jugador and nombre_comunidad = comunidad;
    IF (num_centros = 0) THEN
      insert into tiene values(usuario, comunidad, centro);
      num_centros_usuario:=num_centros_usuario+1;
    END IF;
  EXIT WHEN(num_centros_usuario=4);
  END LOOP;
  
  num_defensas_usuario:=0;
  LOOP
    defensa := generaDefensas;
    select count(*) into num_defensas from Tiene where defensa = codigo_jugador and nombre_comunidad = comunidad;
    IF (num_defensas = 0) THEN
      insert into tiene values(usuario, comunidad, defensa);
      num_defensas_usuario:=num_defensas_usuario+1;
    END IF;
  EXIT WHEN(num_defensas_usuario=4);
  END LOOP;
  
  num_delanteros_usuario:=0;
  LOOP
    delantero := generaDelantero;
    select count(*) into num_delanteros from Tiene where delantero = codigo_jugador and nombre_comunidad = comunidad;
    IF (num_delanteros = 0) THEN
      insert into tiene values(usuario, comunidad, delantero);
      num_delanteros_usuario:=num_delanteros_usuario+1;
    END IF;
  EXIT WHEN(num_delanteros_usuario=3);
  END LOOP;
END;

PROCEDURE proceso_acceso(usuario VARCHAR2, comunidad VARCHAR2) AS 
BEGIN
  INSERT into PERTENECE(nombre_usu,nombre_comunidad,creditos,administrador) values (usuario,comunidad,20000000.0, 0);
  addJugadores(usuario, comunidad);
END;

PROCEDURE acceder(usuario VARCHAR2, comunidad VARCHAR2, pass_comunidad VARCHAR2, okaccess OUT INTEGER) AS
BEGIN
  select count(*) into okaccess from COMUNIDAD where nombre_comunidad=comunidad and pass_comunidad=pass;
  IF(pertenece(usuario,comunidad) = 0 and okaccess=1) THEN
    proceso_acceso(usuario,comunidad);
  END IF;
END;

FUNCTION yaexisteComunidad(nombre VARCHAR2) return INTEGER IS existe INTEGER;
BEGIN
  select count(*) into existe from COMUNIDAD where nombre_comunidad=nombre;
  return(existe);
END;

PROCEDURE registrarComunidad(nombre_comunidad VARCHAR2, pass_comunidad VARCHAR2, existe OUT INTEGER) AS
BEGIN
  existe:=yaexisteComunidad(nombre_comunidad);
  IF (existe = 0) THEN
    INSERT into Comunidad(nombre_comunidad,pass) values (nombre_comunidad,pass_comunidad);
  END IF;
  
END registrarComunidad;

END PKG_CONNECTION;