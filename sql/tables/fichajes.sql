CREATE OR REPLACE PACKAGE BODY PKG_FICHAJES AS

FUNCTION precio_minimo(codigo_jugador INT, nombre_comunidad VARCHAR2) return INTEGER IS precio INTEGER;
BEGIN
  select precio_min into precio from ApareceEn where codigo_jugador=codigo_jugador and nombre_comunidad=nombre_comunidad;
  return(precio);
END;

FUNCTION obtener_creditos(nombre_usuario VARCHAR2, nombre_comunidad VARCHAR2) return INTEGER IS credit INTEGER;
BEGIN
  select creditos into credit from PERTENECE where nombre_usu = nombre_usuario and nombre_comunidad = nombre_comunidad;
  return(credit);
END;

PROCEDURE Pujar(nomb_usuario VARCHAR2, nomb_comunidad VARCHAR2,id_jugador INTEGER, cantidad INT) AS
BEGIN 
 
 IF (precio_minimo(id_jugador, nomb_comunidad) <= cantidad) THEN
   INSERT into RealizarOferta(nombre_usuario,nombre_comunidad,codigo_jugador,precio, estado) values (nomb_usuario, nomb_comunidad, id_jugador, cantidad, 0);
 END IF;
END Pujar;

PROCEDURE ofrecer_sistema(nomb_comunidad VARCHAR2,id_jugador INTEGER) AS
BEGIN 
  
  INSERT into ApareceEn(NOMBRE_VENDEDOR, nombre_comunidad,codigo_jugador,precio_min) values ('COMPUTER',nomb_comunidad, id_jugador, (select precio from Jugadores where cod=id_jugador));
END ofrecer_sistema;

PROCEDURE ofrecer_jugador(nomb_usuario VARCHAR2, nomb_comunidad VARCHAR2, id_jugador INTEGER, precio INT) AS
BEGIN  
  INSERT into ApareceEn(NOMBRE_VENDEDOR, nombre_comunidad,codigo_jugador,precio_min) values (nomb_usuario,nomb_comunidad, id_jugador, precio);
END ofrecer_jugador;

PROCEDURE deshacer_fichaje(usuario VARCHAR2, comunidad VARCHAR2, jugador INT) AS
contador int;
BEGIN
     
    select count(*) into contador from FICHAJES where nombre_usuario = usuario and nombre_comunidad = comunidad and codigo_jugador = jugador;
    IF(contador >0) THEN
    update PERTENECE set creditos= (select creditos from PERTENECE where nombre_usu = usuario and nombre_comunidad = comunidad) + 
    (select precio from FICHAJES where nombre_usuario = usuario and nombre_comunidad = comunidad and codigo_jugador = jugador)
    where nombre_usu = usuario and nombre_comunidad = comunidad;
    
    delete from TIENEALINEADO where nombre_usuario = usuario and nombre_comunidad = comunidad and codigo_jugador = jugador;
    delete from TIENE where nombre_usuario = usuario and nombre_comunidad = comunidad and codigo_jugador = jugador;
    delete from FICHAJES where nombre_usuario = usuario and nombre_comunidad = comunidad and codigo_jugador = jugador;
    
    
    END IF;
END;

PROCEDURE realizar_fichaje(jugador INT, comunidad VARCHAR2) AS
usuario VARCHAR(40);
vendedor VARCHAR(40);
BEGIN
  /*Cambiamos estado a 1 en realizar oferta.*/
  select nombre_usuario into usuario from REALIZAROFERTA where codigo_jugador = jugador and nombre_comunidad = comunidad and 
  precio = (select max(precio) from REALIZAROFERTA where codigo_jugador = jugador and nombre_comunidad = comunidad);
  
  update REALIZAROFERTA set estado = 1 where codigo_jugador = jugador and nombre_comunidad = comunidad and nombre_usuario = usuario;
  

  /*Retiramos créditos del usuario*/
  update PERTENECE set creditos = (select creditos from PERTENECE where nombre_usu = usuario) - 
  (select precio from REALIZAROFERTA where codigo_jugador = jugador and nombre_comunidad = comunidad and nombre_usuario = usuario)
  where nombre_usu = usuario;
  
  /*Quitamos de realizar oferta todos los jugadores que hayan pujado por ese jugador (manteniendo el realizado para la posibilidad de deshacer)*/
  delete from REALIZAROFERTA where codigo_jugador=jugador and estado=0 and nombre_comunidad = comunidad;
  
  /*Si el jugador era de otro usuario, se lo quitamos*/

  select nombre_vendedor into vendedor from APARECEEN where nombre_comunidad = comunidad and codigo_jugador = jugador;
    IF(vendedor <> 'COMPUTER') THEN
      delete from TIENEALINEADO where nombre_usuario = vendedor and nombre_comunidad = comunidad and codigo_jugador = jugador;
      delete from TIENE where nombre_usuario = vendedor and nombre_comunidad = comunidad and codigo_jugador = jugador;
      update PERTENECE set creditos = (select creditos from PERTENECE where nombre_usu = vendedor) +
      (select precio from REALIZAROFERTA where codigo_jugador = jugador and nombre_comunidad = comunidad and nombre_usuario = usuario)
      where nombre_usu = vendedor;
    ELSE
       insert into Fichajes (nombre_usuario, codigo_jugador,nombre_comunidad, precio) values (usuario,jugador, comunidad, (select max(precio) from REALIZAROFERTA where codigo_jugador = jugador and nombre_comunidad = comunidad)); 

    END IF;

  delete from REALIZAROFERTA where codigo_jugador=jugador and estado=1 and nombre_comunidad = comunidad;

  delete from APARECEEN where codigo_jugador = jugador and nombre_comunidad = comunidad ; 
    
  /*Añadir el trio jugador,usuario,comunidad a Tiene*/
  insert into TIENE(nombre_usuario, nombre_comunidad, codigo_jugador) values (usuario, comunidad, jugador);  
  

END;

/* Hay que añadirle los puntos -> tener en cuenta que se duplica el ultimo!!!!!!*/
PROCEDURE obtener_jugadores(comunidad VARCHAR2, devolver OUT SYS_REFCURSOR) AS
  nombre VARCHAR(40);
  precio_min INT;
  equipo VARCHAR(40);
  nombre_vendedor VARCHAR(40);
BEGIN
  OPEN devolver FOR
  SELECT nombre, equipo, precio_min, nombre_vendedor
  FROM (select * from APARECEEN, JUGADORES 
  where apareceen.codigo_jugador = Jugadores.cod and nombre_comunidad = comunidad);
/*  
  LOOP
    FETCH devolver INTO nombre, equipo, precio_min, nombre_vendedor;

    EXIT WHEN (devolver%NOTFOUND);

  END LOOP;
  CLOSE devolver;*/
END;

END PKG_FICHAJES;








