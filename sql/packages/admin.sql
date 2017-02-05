CREATE OR REPLACE PACKAGE BODY PKG_ADMIN AS

FUNCTION estaEnComunidad(nombre VARCHAR2) return VARCHAR2 IS nc VARCHAR2;
BEGIN
  select nombre_comunidad into nc from Pertenece where nombre_usu=nombre;
  return(nc);
END;

FUNCTION esAdmin(nombre VARCHAR2) return INTEGER IS ad INTEGER;
BEGIN
  select administrador into ad from Pertenece where nombre_usu=nombre;
  return(ad);
END;

PROCEDURE cambiar_privilegios_admin(usu_admin VARCHAR2, usu VARCHAR2) AS
BEGIN
  IF (estaEnComunidad(usu_admin) = estaEnComunidad(usu)) THEN
    IF (esAdmin(usu) = 0) THEN
      update Pertenece set administrador = 1 where usu=nombre_usu;
    ELSE
      update Pertenece set administrador = 0 where usu=nombre_usu;
    END IF;
  END IF;
END;

END PKG_ADMIN;