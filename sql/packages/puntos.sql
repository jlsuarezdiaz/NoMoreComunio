CREATE OR REPLACE PACKAGE BODY PKG_PUNTOS AS


FUNCTION obtenerAmarillas(cod INTEGER, jornada INTEGER) return INTEGER IS numero INTEGER;
BEGIN
  select t_amarillas into numero from Puntos where cod_jugador=cod and cod_jornada=jornada;
  return (numero);
END;

FUNCTION obtenerRojas(cod INTEGER, jornada INTEGER) return INTEGER IS numero INTEGER;
BEGIN
  select t_rojas into numero from Puntos where cod_jugador=cod and cod_jornada=jornada;
  return (numero);
END;

FUNCTION obtenerGoles(cod INTEGER, jornada INTEGER) return INTEGER IS numero INTEGER;
BEGIN
  select goles into numero from Puntos where cod_jugador=cod and cod_jornada=jornada;
  return (numero);
END;

FUNCTION obtenerValoracion(cod INTEGER, jornada INTEGER) return INTEGER IS numero INTEGER;
BEGIN
  select valoracion into numero from Puntos where cod_jugador=cod and cod_jornada=jornada;
  return (numero);
END;

FUNCTION hayPuntosParaJugador(cod INTEGER,jornada INTEGER) return INTEGER IS numero INTEGER;
BEGIN
  select count(*) into numero from Puntos where cod_jugador=cod and cod_jornada=jornada;
  return(numero);
END;

FUNCTION esPortero(codigo INTEGER) return INTEGER IS numero INTEGER;
 BEGIN
  select count(*) into numero from Puntos where cod_jugador=(select cod from Jugadores where pos='Portero' and cod=codigo);
  return(numero);
END;

FUNCTION esMediocentro(codigo INTEGER) return INTEGER IS numero INTEGER;
 BEGIN
  select count(*) into numero from Puntos where cod_jugador=(select cod from Jugadores where pos='Mediocentro' and cod=codigo);
  return(numero);
END;

FUNCTION esDefensa(codigo INTEGER) return INTEGER IS numero INTEGER;
 BEGIN
  select count(*) into numero from Puntos where cod_jugador=(select cod from Jugadores where pos='Defensa' and cod=codigo);
  return(numero);
END;

FUNCTION esDelantero(codigo INTEGER) return INTEGER IS numero INTEGER;
 BEGIN
  select count(*) into numero from Puntos where cod_jugador=(select cod from Jugadores where pos='Delantero' and cod=codigo);
  return(numero);
END;

PROCEDURE asignarPuntos(cod INTEGER, jornada INTEGER, goles_encajados INTEGER, goles_jugador INTEGER, amarillas INTEGER, rojas INTEGER, asist INTEGER, val INTEGER) AS
BEGIN
  IF (hayPuntosParaJugador(cod,jornada) = 0) THEN
    IF (esPortero(cod) = 1) THEN
      INSERT INTO Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas,valoracion) VALUES (cod,jornada,goles_encajados,asist,amarillas,rojas,val);
    ELSE
      INSERT INTO Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas,valoracion) VALUES (cod,jornada,goles_jugador,asist,amarillas,rojas,val);
    END IF;
  ELSE
    IF (esPortero(cod) = 1) THEN
      update Puntos set goles = goles_encajados where cod_jugador=cod and cod_jornada=jornada;
      update Puntos set asistencias = asist where cod_jugador=cod and cod_jornada=jornada;
      update Puntos set t_amarillas = amarillas where cod_jugador=cod and cod_jornada=jornada;
      update Puntos set t_rojas = rojas where cod_jugador=cod and cod_jornada=jornada;
      update Puntos set valoracion = val where cod_jugador=cod and cod_jornada=jornada;
    ELSE
      update Puntos set goles = goles_jugador where cod_jugador=cod and cod_jornada=jornada;
      update Puntos set asistencias = asist where cod_jugador=cod and cod_jornada=jornada;
      update Puntos set t_amarillas = amarillas where cod_jugador=cod and cod_jornada=jornada;
      update Puntos set t_rojas = rojas where cod_jugador=cod and cod_jornada=jornada;
      update Puntos set valoracion = val where cod_jugador=cod and cod_jornada=jornada;
    END IF;
  END IF;
END;



PROCEDURE calcularPuntos(cod INTEGER, jornada INTEGER, resultado OUT INTEGER) AS
BEGIN  
  IF (esPortero(cod) = 1) THEN
    resultado:= -1 *obtenerGoles(cod,jornada) -2 + 4*obtenerValoracion(cod,jornada);
  END IF;
  
  IF (esDefensa(cod) = 1) THEN
    resultado:= 5 *obtenerGoles(cod,jornada) -2 + 4*obtenerValoracion(cod,jornada);
    
    IF (obtenerAmarillas(cod,jornada) = 2) THEN
      resultado:= resultado -3;
    ELSE 
      IF (obtenerRojas(cod,jornada) = 1) THEN
        resultado:= resultado -6;
      END IF;
    END IF;
  END IF;

  IF (esMediocentro(cod) = 1) THEN
    resultado:= 4 *obtenerGoles(cod,jornada) -2 + 4*obtenerValoracion(cod,jornada);
    
    IF (obtenerAmarillas(cod,jornada) = 2) THEN
      resultado:= resultado -3;
    ELSE 
      IF (obtenerRojas(cod,jornada) = 1) THEN
        resultado:= resultado -6;
      END IF;
    END IF;
  END IF;

  IF (esDelantero(cod) = 1) THEN
    resultado:= 3 *obtenerGoles(cod,jornada) -2 + 4*obtenerValoracion(cod,jornada);
    
    IF (obtenerAmarillas(cod,jornada) = 2) THEN
      resultado:= resultado -3;
    ELSE 
      IF (obtenerRojas(cod,jornada) = 1) THEN
        resultado:= resultado -6;
      END IF;
    END IF;
  END IF;

END;

PROCEDURE obtenerPuntosUsuario(usuario VARCHAR2, jor INT, comunidad VARCHAR2,  suma OUT INT) IS
cod_jugador INT;
auxiliar INT;
CURSOR recorrer IS SELECT cod_jugador FROM (select * from Puntos,TieneAlineado 
  where Puntos.cod_jugador = TieneAlineado.codigo_jugador and nombre_comunidad=comunidad and jornada=jor and nombre_usuario = usuario);

BEGIN

  suma:=0;
  OPEN recorrer;
  
  LOOP
    FETCH recorrer INTO cod_jugador;

    EXIT WHEN (recorrer%NOTFOUND);
    calcularPuntos(cod_jugador, jor, auxiliar);
    suma := suma + auxiliar;
  END LOOP;
  CLOSE recorrer;
END;

/*
PROCEDURE obtenerPuntos(jor INT, coumidad varchar, devolver OUT SYS_REFCURSOR) AS
usuario VARCHAR2;
puntos INT;
BEGIN
  OPEN devolver FOR SELECT usuario FROM pertenece;
END;

*/
END PKG_PUNTOS;