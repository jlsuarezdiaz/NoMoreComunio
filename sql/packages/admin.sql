CREATE OR REPLACE PACKAGE BODY PKG_ADMIN AS

FUNCTION estaEnComunidad(nombre VARCHAR2) return VARCHAR2 IS nc VARCHAR2(20);
BEGIN
  select nombre_comunidad into nc from Pertenece where nombre_usu=nombre;
  return(nc);
END;

FUNCTION esAdmin(nombre VARCHAR2, comunidad VARCHAR2) return INTEGER IS ad INTEGER;
BEGIN
  select administrador into ad from Pertenece where nombre_usu=nombre and comunidad=nombre_comunidad;
  return(ad);
END;

PROCEDURE cambiar_privilegios_admin(usu VARCHAR2, comunidad VARCHAR2) AS
BEGIN
  IF (esAdmin(usu,comunidad) = 0) THEN
    update Pertenece set administrador = 1 where usu=nombre_usu and nombre_comunidad=comunidad;
  ELSE
    update Pertenece set administrador = 0 where usu=nombre_usu and nombre_comunidad=comunidad;
  END IF;
END;

END PKG_ADMIN;