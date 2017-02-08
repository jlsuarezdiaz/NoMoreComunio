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
    IF (esPortero(cod) > 0) THEN
      INSERT INTO Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas,valoracion) VALUES (cod,jornada,goles_encajados,asist,amarillas,rojas,val);
    ELSE
      INSERT INTO Puntos (cod_jugador, cod_jornada, goles, asistencias, t_amarillas, t_rojas,valoracion) VALUES (cod,jornada,goles_jugador,asist,amarillas,rojas,val);
    END IF;
  ELSE
    IF (esPortero(cod) > 0) THEN
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
  resultado:=0;
  IF (esPortero(cod) > 0) THEN
    resultado:= -1 *obtenerGoles(cod,jornada) -2 + 4*obtenerValoracion(cod,jornada);
  END IF;
  
  IF (esDefensa(cod) > 0) THEN
    resultado:= 5 *obtenerGoles(cod,jornada) -2 + 4*obtenerValoracion(cod,jornada);
    
    IF (obtenerAmarillas(cod,jornada) = 2) THEN
      resultado:= resultado -3;
    ELSE 
      IF (obtenerRojas(cod,jornada) = 1) THEN
        resultado:= resultado -6;
      END IF;
    END IF;
  END IF;

  IF (esMediocentro(cod) > 0) THEN
    resultado:= 4 *obtenerGoles(cod,jornada) -2 + 4*obtenerValoracion(cod,jornada);
    
    IF (obtenerAmarillas(cod,jornada) = 2) THEN
      resultado:= resultado -3;
    ELSE 
      IF (obtenerRojas(cod,jornada) = 1) THEN
        resultado:= resultado -6;
      END IF;
    END IF;
  END IF;

  IF (esDelantero(cod) > 0) THEN
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


FUNCTION calcularPuntosTotales(jugador INT) RETURN INTEGER IS suma INTEGER;

cod_jornada INT;
cod_jugador INT;
auxiliar INT;
valoracion int;
CURSOR recorrer IS SELECT cod_jornada,valoracion FROM (select * from Puntos 
where cod_jugador=jugador);

BEGIN

  suma:=0;
  OPEN recorrer;
  
  LOOP
    FETCH recorrer INTO cod_jornada, valoracion;

    EXIT WHEN (recorrer%NOTFOUND);
    calcularPuntos(jugador, cod_jornada, auxiliar);
    suma := suma + auxiliar;
  END LOOP;
  CLOSE recorrer;
  return(suma);
END;

FUNCTION obtenerPuntosUsuario(usuario VARCHAR2, jor INT, comunidad VARCHAR2) return INTEGER IS suma INTEGER;
codigo_jugador INT;
auxiliar INT;
CURSOR recorrer IS SELECT codigo_jugador FROM (select * from TieneAlineado 
  where  nombre_comunidad=comunidad and jornada=jor and nombre_usuario = usuario);

BEGIN

  suma:=0;
  OPEN recorrer;
  
  LOOP
    FETCH recorrer INTO codigo_jugador;

    EXIT WHEN (recorrer%NOTFOUND);
    calcularPuntos(codigo_jugador, jor, auxiliar);
    
    suma := suma + auxiliar;
  END LOOP;
  CLOSE recorrer;
  return(suma);
END;

PROCEDURE obtenerPuntosJornada(jor INT, comunidad VARCHAR2, devolver OUT SYS_REFCURSOR) AS
nombre_usu VARCHAR(30);
resultado INT;
BEGIN
  OPEN devolver FOR SELECT nombre_usu, obtenerPuntosUsuario(nombre_usu, jor, comunidad) FROM pertenece where nombre_comunidad=comunidad
  order by obtenerPuntosUsuario(nombre_usu, jor, comunidad) desc;
END;


FUNCTION obtenerPuntosTotalesUsuario(usuario VARCHAR2, comunidad VARCHAR2) return INTEGER IS suma INTEGER;
jornada INT;
codigo_jugador INT;
auxiliar INT;
CURSOR recorrer IS SELECT codigo_jugador,jornada FROM (select * from TieneAlineado 
  where nombre_comunidad=comunidad and nombre_usuario = usuario);

BEGIN

  suma:=0;
  OPEN recorrer;
  
  LOOP
    FETCH recorrer INTO codigo_jugador, jornada;

    EXIT WHEN (recorrer%NOTFOUND);
    calcularPuntos(codigo_jugador, jornada, auxiliar);
    suma := suma + auxiliar;
  END LOOP;
  CLOSE recorrer;
  return(suma);
END;


PROCEDURE obtenerPuntosTotales(comunidad VARCHAR2, devolver OUT SYS_REFCURSOR)AS
nombre_usu VARCHAR(30);
resultado INT;
BEGIN
  OPEN devolver FOR SELECT nombre_usu, obtenerPuntosTotalesUsuario(nombre_usu, comunidad) FROM pertenece where nombre_comunidad=comunidad
  order  by obtenerPuntosTotalesUsuario(nombre_usu, comunidad) desc;
  
  LOOP
    FETCH devolver INTO nombre_usu, resultado;

    EXIT WHEN (devolver%NOTFOUND);
    dbms_output.put_line(nombre_usu);
    dbms_output.put_line(resultado);
   
  END LOOP;
  
  CLOSE devolver;
  
END;

END PKG_PUNTOS;