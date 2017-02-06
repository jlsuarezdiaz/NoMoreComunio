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

PROCEDURE proceso_acceso(usuario VARCHAR2, comunidad VARCHAR2) AS 
BEGIN
  
  INSERT into PERTENECE(nombre_usu,nombre_comunidad,creditos,administrador) values (usuario,comunidad,20.0, 0);
END;

PROCEDURE acceder(usuario VARCHAR2, comunidad VARCHAR2, pass_comunidad VARCHAR2, okaccess OUT VARCHAR2) AS
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

PROCEDURE registrarComunidad(nombre_comunidad VARCHAR2, pass_comunidad VARCHAR2) AS
BEGIN
  IF (yaexisteComunidad(nombre_comunidad) = 0) THEN
    INSERT into Comunidad(nombre_comunidad,pass) values (nombre_comunidad,pass_comunidad);
  END IF;
END registrarComunidad;

END PKG_CONNECTION;