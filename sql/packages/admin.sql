CREATE OR REPLACE PACKAGE BODY PKG_ADMIN AS

FUNCTION obtenerPrimero(comunidad VARCHAR2) return VARCHAR2 IS nombre VARCHAR2(20);
BEGIN
  select nombre_usu into nombre from (select nombre_usu from Pertenece where nombre_comunidad=comunidad order by dbms_random.value) where rownum=1;
  return(nombre);
END;

FUNCTION hayUsuarios(comunidad VARCHAR2) return INTEGER IS numero INTEGER;
BEGIN  
  select count(*) into numero from Pertenece where nombre_comunidad=comunidad; 
  return(numero);
END;

FUNCTION numeroAdmin(comunidad VARCHAR2) return INTEGER IS numero INTEGER;
BEGIN
  select count(*) into numero from Pertenece where comunidad=nombre_comunidad and administrador=1;
  return(numero);
END;

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
      IF ((numeroAdmin(comunidad)-1) != 0) THEN
        update Pertenece set administrador = 0 where usu=nombre_usu and nombre_comunidad=comunidad;
      END IF;
    END IF;
  END IF;
END;

PROCEDURE otorgarCreditos(usu VARCHAR2, premio INTEGER, comunidad VARCHAR2) AS
BEGIN
  IF (estaEnComunidad(usu,comunidad) = 1) THEN
    update Pertenece  set creditos = creditos + premio where usu=nombre_usu and comunidad = nombre_comunidad;
  END IF;
END;

PROCEDURE echarDeComunidad(usu VARCHAR2, comunidad VARCHAR2) AS
BEGIN
  IF(estaEnComunidad(usu,comunidad) = 1) THEN
    delete from Pertenece where nombre_usu=usu and nombre_comunidad=comunidad;
    IF (hayUsuarios(comunidad) != 0) THEN
      IF(numeroAdmin(comunidad) = 0) THEN 
        cambiar_privilegios_admin(obtenerPrimero(comunidad),comunidad);
      END IF;
    END IF;
  END IF;
END;

END PKG_ADMIN;