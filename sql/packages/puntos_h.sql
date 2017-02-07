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

END PKG_PUNTOS;