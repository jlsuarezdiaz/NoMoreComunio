create or replace TRIGGER SOLO11
BEFORE INSERT ON TIENEALINEADO
FOR EACH ROW
DECLARE 
numero INT;
delanteros INT;
mediocentro INT;
defensa INT;
portero INT;
BEGIN
  select count(*) into numero from TieneAlineado where nombre_usuario = :new.nombre_usuario and nombre_comunidad = :new.nombre_comunidad and jornada = :new.jornada;
     dbms_output.put_line(numero);

  select count(*) into portero from TieneAlineado, JUGADORES
  where cod = codigo_jugador and nombre_usuario = :new.nombre_usuario and nombre_comunidad = :new.nombre_comunidad and jornada = :new.jornada
  and pos = 'Portero';
    dbms_output.put_line(portero);

  IF (numero = 11) THEN
    RAISE_APPLICATION_ERROR( -20005, 
                           'Solo puede tener alineado 11 jugadores en una misma jornada.' );
              
  END IF;
  
  IF(portero = 1) THEN
    RAISE_APPLICATION_ERROR( -20006, 
                             'Solo puede tener alineado 1 portero en una misma jornada' );
  END IF;
END;