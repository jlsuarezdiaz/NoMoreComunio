CREATE OR REPLACE PACKAGE PKG_GLOBAL AS

FUNCTION calcMax(comunidad VARCHAR2) return INTEGER;
FUNCTION totalJugadores return INTEGER;
PROCEDURE escribeNoticia(usuario VARCHAR2, comunidad VARCHAR2, noticia VARCHAR2);
PROCEDURE registrarJugador(nombre VARCHAR2, equipo VARCHAR2, pos VARCHAR2, precio INTEGER);
PROCEDURE listaComunidades(usuario VARCHAR2, devolver OUT SYS_REFCURSOR);
PROCEDURE getNoticias(comunidad VARCHAR2, devolver OUT SYS_REFCURSOR);

END PKG_GLOBAL;