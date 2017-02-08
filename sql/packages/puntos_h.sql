CREATE OR REPLACE PACKAGE PKG_PUNTOS AS

FUNCTION obtenerAmarillas(cod INTEGER, jornada INTEGER) return INTEGER;
FUNCTION obtenerRojas(cod INTEGER, jornada INTEGER) return INTEGER;
FUNCTION obtenerGoles(cod INTEGER, jornada INTEGER) return INTEGER;
FUNCTION obtenerValoracion(cod INTEGER, jornada INTEGER) return INTEGER;
FUNCTION hayPuntosParaJugador(cod INTEGER,jornada INTEGER) return INTEGER;
FUNCTION esPortero(codigo INTEGER) return INTEGER;
FUNCTION esMediocentro(codigo INTEGER) return INTEGER;
FUNCTION esDefensa(codigo INTEGER) return INTEGER;
FUNCTION esDelantero(codigo INTEGER) return INTEGER;


PROCEDURE asignarPuntos(cod INTEGER, jornada INTEGER, goles_encajados INTEGER, goles_jugador INTEGER, amarillas INTEGER, rojas INTEGER, asist INTEGER, val INTEGER);
PROCEDURE calcularPuntos(cod INTEGER, jornada INTEGER, resultado OUT INTEGER);
FUNCTION calcularPuntosTotales(jugador INT) RETURN INTEGER;
FUNCTION obtenerPuntosUsuario(usuario VARCHAR2, jor INT, comunidad VARCHAR2) return INTEGER;
PROCEDURE obtenerPuntosJornada(jor INT, comunidad VARCHAR2, devolver OUT SYS_REFCURSOR);
FUNCTION obtenerPuntosTotalesUsuario(usuario VARCHAR2, comunidad VARCHAR2) return INTEGER;
PROCEDURE obtenerPuntosTotales(comunidad VARCHAR2, devolver OUT SYS_REFCURSOR);


END PKG_PUNTOS;