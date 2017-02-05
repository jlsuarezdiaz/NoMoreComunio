CREATE OR REPLACE PACKAGE BODY PKG_ADMIN AS

FUNCTION estaEnComunidad(nombre VARCHAR2, comunidad VARCHAR2) return INTEGER IS nc INTEGER;
BEGIN
  select count(*) into nc from Pertenece where nombre_usu=nombre and comunidad = nombre_comunidad;
  return(nc);
END;

FUNCTION esAdmin(nombre VARCHAR2, comunidad VARCHAR2) return INTEGER IS ad INTEGER;
BEGIN
  select administrador into ad from Pertenece where nombre_usu=nombre and comunidad=nombre_comunidad;
  return(ad);
END;

PROCEDURE cambiar_privilegios_admin(usu VARCHAR2, comunidad VARCHAR2) AS
BEGIN
  IF (estaEnComunidad(usu,comunidad) = 1) THEN
    IF (esAdmin(usu,comunidad) = 0) THEN
      update Pertenece set administrador = 1 where usu=nombre_usu and nombre_comunidad=comunidad;
    ELSE
      update Pertenece set administrador = 0 where usu=nombre_usu and nombre_comunidad=comunidad;
    END IF;
  END IF;
END;

PROCEDURE otorgarCreditos(usu VARCHAR2, premio INTEGER, comunidad VARCHAR2) AS
BEGIN
  IF (estaEnComunidad(usu,comunidad) = 1) THEN
    update Pertenece  set creditos = creditos + premio where usu=nombre_usu and comunidad = nombre_comunidad;
  END IF;
END;

END PKG_ADMIN;