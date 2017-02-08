CREATE OR REPLACE PACKAGE PKG_CONNECTION AS

PROCEDURE login(nombre_usuario VARCHAR2, passwd IN VARCHAR2, oklog OUT VARCHAR2);
FUNCTION existe_usuario(nombre_usuario VARCHAR2) return INTEGER;
PROCEDURE registrar(nombre_usuario VARCHAR2, nombre VARCHAR2, apellido VARCHAR2, pssword VARCHAR2, mail VARCHAR2);
FUNCTION pertenece(usuario VARCHAR2, comunidad VARCHAR2) return INTEGER;
PROCEDURE proceso_acceso(usuario VARCHAR2, comunidad VARCHAR2);
PROCEDURE acceder(usuario VARCHAR2, comunidad VARCHAR2, pass_comunidad VARCHAR2, okaccess OUT INTEGER);
PROCEDURE registrarComunidad(nombre_comunidad VARCHAR2, pass_comunidad VARCHAR2);
FUNCTION yaexisteComunidad(nombre VARCHAR2) return INTEGER;

END PKG_CONNECTION;