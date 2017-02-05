CREATE OR REPLACE PACKAGE BODY PKG_GLOBAL AS

FUNCTION calcMax(comunidad VARCHAR2) return INTEGER IS maximo INTEGER;
BEGIN
  select max(codigo_noticia) into maximo from TablonAnuncios where comunidad=nombre_comunidad; 
  return(maximo);
END;

PROCEDURE escribeNoticia(usuario VARCHAR2, comunidad VARCHAR2, noticia VARCHAR2) AS
BEGIN
  insert into TablonAnuncios (nombre_comunidad,codigo_noticia,noticia) values (comunidad,calcMax(comunidad)+1,noticia);
  insert into EscribeNoticia (nombre_us,nombre_comunidad,codigo_noticia,fecha) values (usuario,comunidad,calcMax(comunidad),SYSDATE);
END;

END PKG_GLOBAL;