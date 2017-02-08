CREATE OR REPLACE PACKAGE PKG_GLOBAL AS

FUNCTION obtenerCodigoPrimero return INTEGER;
PROCEDURE obtenerUsuarios(comunidad VARCHAR2, devolver OUT SYS_REFCURSOR);
PROCEDURE modificarMercado(numjug INTEGER, comunidad VARCHAR2);
FUNCTION calcMax(comunidad VARCHAR2) return INTEGER;
FUNCTION estaEnComunidad(nombre VARCHAR2, comunidad VARCHAR2) return INTEGER;
FUNCTION totalJugadores return INTEGER;
PROCEDURE escribeNoticia(usuario VARCHAR2, comunidad VARCHAR2, noticia VARCHAR2);
PROCEDURE registrarJugador(nombre VARCHAR2, equipo VARCHAR2, pos VARCHAR2, precio INTEGER);
PROCEDURE listaComunidades(usuario VARCHAR2, devolver OUT SYS_REFCURSOR);
PROCEDURE getNoticias(comunidad VARCHAR2, devolver OUT SYS_REFCURSOR);
PROCEDURE obtenerDinero(usuario VARCHAR2, comunidad VARCHAR2, dinero OUT INTEGER);

END PKG_GLOBAL;
