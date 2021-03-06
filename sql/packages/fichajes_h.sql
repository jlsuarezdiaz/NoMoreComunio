CREATE OR REPLACE PACKAGE PKG_FICHAJES AS

FUNCTION precio_minimo(codigo INT, comunidad VARCHAR2) return INTEGER;

FUNCTION obtener_creditos(nombre_usuario VARCHAR2, nombre_comunidad VARCHAR2) return INTEGER;

PROCEDURE Pujar(nomb_usuario VARCHAR2, nomb_comunidad VARCHAR2,id_jugador INTEGER, cantidad INT);

PROCEDURE ofrecer_sistema(nomb_comunidad VARCHAR2, id_jugador INTEGER);

PROCEDURE ofrecer_jugador(nomb_usuario VARCHAR2, nomb_comunidad VARCHAR2, id_jugador INTEGER, precio INT);

PROCEDURE retirar_jugador(nomb_usuario VARCHAR2, nomb_comunidad VARCHAR2, id_jugador INTEGER);

PROCEDURE deshacer_fichaje(usuario VARCHAR2, comunidad VARCHAR2, jugador INT);

PROCEDURE realizar_fichaje(jugador INT, comunidad VARCHAR2);

PROCEDURE obtener_jugadores(comunidad VARCHAR2, devolver OUT SYS_REFCURSOR);

PROCEDURE obtenerAlineacion(usuario VARCHAR2, comunidad VARCHAR2, devolver OUT SYS_REFCURSOR);

PROCEDURE obtenerMisJugadores(usuario VARCHAR2, comunidad VARCHAR2, devolver OUT SYS_REFCURSOR);

PROCEDURE ponerJugadorEnOnce(usu VARCHAR2, comunidad VARCHAR2, cod INTEGER, ronda INTEGER);

PROCEDURE borrarAlineacion(usu VARCHAR2, comunidad VARCHAR2, ronda INTEGER);

END PKG_FICHAJES;
