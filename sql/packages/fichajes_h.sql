CREATE OR REPLACE PACKAGE PKG_FICHAJES AS

/*PROCEDURE Pujar(nomb_usuario VARCHAR2, nomb_comunidad VARCHAR2,id_jugador INTEGER, cantidad REAL);*/

PROCEDURE ofrecer_sistema(nomb_comunidad VARCHAR2, id_jugador INTEGER);

PROCEDURE ofrecer_jugador(nomb_usuario VARCHAR2, nomb_comunidad VARCHAR2, id_jugador INTEGER, precio REAL);

END PKG_FICHAJES;
