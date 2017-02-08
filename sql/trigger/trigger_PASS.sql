create or replace TRIGGER PASS
BEFORE INSERT ON USUARIO
FOR EACH ROW
BEGIN
  IF(length(:new.pass) <= 3) THEN
    RAISE_APPLICATION_ERROR( -20001, 
                             'Introduzca una contraseña de al menos 4 caracteres.' );
  END IF;
END;