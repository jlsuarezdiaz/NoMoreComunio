create or replace TRIGGER PAGAR
AFTER UPDATE ON REALIZAROFERTA 
FOR EACH ROW
BEGIN
  UPDATE PERTENECE SET creditos = creditos - :new.precio where pertenece.nombre_usu = :new.nombre_usuario 
  and pertenece.nombre_comunidad = :new.nombre_comunidad;
END;
