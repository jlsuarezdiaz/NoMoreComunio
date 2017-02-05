CREATE OR REPLACE PACKAGE PKG_ADMIN AS

FUNCTION estaEnComunidad(nombre VARCHAR2, comunidad VARCHAR2) return INTEGER;
FUNCTION esAdmin(nombre VARCHAR2, comunidad VARCHAR2) return INTEGER;
PROCEDURE cambiar_privilegios_admin(usu VARCHAR2, comunidad VARCHAR2);
PROCEDURE otorgarCreditos(usu VARCHAR2, creditos INTEGER);

END PKG_ADMIN;