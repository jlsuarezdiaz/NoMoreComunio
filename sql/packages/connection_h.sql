CREATE OR REPLACE PACKAGE PKG_CONNECTION AS

FUNCTION login(nombre_usuario VARCHAR2, passwd IN VARCHAR2) return INTEGER;
PROCEDURE registrar(nombre_usuario VARCHAR2, nombre VARCHAR2, apellido VARCHAR2, pssword VARCHAR2, mail VARCHAR2);
FUNCTION pertenece(usuario VARCHAR2, comunidad VARCHAR2) return INTEGER;
PROCEDURE proceso_acceso(usuario VARCHAR2, comunidad VARCHAR2);
FUNCTION acceder(usuario VARCHAR2, comunidad VARCHAR2, pass_comunidad VARCHAR2) return INTEGER;

END PKG_CONNECTION;




