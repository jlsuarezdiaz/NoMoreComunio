CREATE OR REPLACE PACKAGE PKG_FICHAJES AS

FUNCTION precio_minimo(codigo_jugador INT, nombre_comunidad VARCHAR2) return INTEGER;

PROCEDURE Pujar(nomb_usuario VARCHAR2, nomb_comunidad VARCHAR2,id_jugador INTEGER, cantidad INT);

PROCEDURE ofrecer_sistema(nomb_comunidad VARCHAR2, id_jugador INTEGER);

PROCEDURE ofrecer_jugador(nomb_usuario VARCHAR2, nomb_comunidad VARCHAR2, id_jugador INTEGER, precio INT);

END PKG_FICHAJES;
