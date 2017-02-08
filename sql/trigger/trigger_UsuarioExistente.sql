create or replace TRIGGER USUARIOEXISTENTE
BEFORE INSERT ON USUARIO
FOR EACH ROW
DECLARE
contador INT;
BEGIN
  select count(*) into contador from Usuario where nom_usu = :new.nom_usu;
  
  IF(contador>0) THEN
    RAISE_APPLICATION_ERROR( -20008, 
                             'El nombre de usuario ya está siendo utilizado.' );
  END IF;
END;
