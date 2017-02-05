CREATE OR REPLACE PACKAGE PKG_ADMIN AS

FUNCTION estaEnComunidad(nombre VARCHAR2) return VARCHAR2;
FUNCTION esAdmin(nombre VARCHAR2) return INTEGER;
PROCEDURE cambiar_privilegios_admin(usu_admin VARCHAR2, usu VARCHAR2);

END PKG_ADMIN;