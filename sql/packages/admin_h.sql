CREATE OR REPLACE PACKAGE PKG_ADMIN AS

FUNCTION obtenerPrimero(comunidad VARCHAR2) return VARCHAR2;
FUNCTION hayUsuarios(comunidad VARCHAR2) return INTEGER;
FUNCTION numeroAdmin(comunidad VARCHAR2) return INTEGER;
FUNCTION estaEnComunidad(nombre VARCHAR2, comunidad VARCHAR2) return INTEGER;
FUNCTION esAdmin(nombre VARCHAR2, comunidad VARCHAR2) return INTEGER;
PROCEDURE cambiar_privilegios_admin(usu VARCHAR2, comunidad VARCHAR2);
PROCEDURE otorgarCreditos(usu VARCHAR2, premio INTEGER, comunidad VARCHAR2);
PROCEDURE echarDeComunidad(usu VARCHAR2, comunidad VARCHAR2);
END PKG_ADMIN;