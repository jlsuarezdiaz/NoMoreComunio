create or replace TRIGGER NOMBREVENDEDOR
BEFORE INSERT ON APARECEEN
FOR EACH ROW
DECLARE
contador INT;
BEGIN
  select count(*) into contador from Tiene where :new.nombre_comunidad = nombre_comunidad and :new.nombre_vendedor = nombre_usuario and
  :new.codigo_jugador = codigo_jugador;
  
  IF(:new.nombre_vendedor <> 'COMPUTER' and contador=0)
  THEN
    RAISE_APPLICATION_ERROR( -20003, 
                             'El usuario no puede vender ese jugador.' );
  END IF;
END;

