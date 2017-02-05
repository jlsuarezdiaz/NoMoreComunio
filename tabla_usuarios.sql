drop table Usuario;

create table Usuario(
  nom_usu varchar(20) NOT NULL PRIMARY KEY,
  nombre varchar(20),
  apellidos varchar(40),
  pass varchar(20),
  email varchar(30)
);

/*Inserción de los usuarios*/

insert into Usuario(nom_usu,nombre,apellidos,pass,email) values ('rbnuria','nuria','rodriguez barrado','nuriaadra','rbnuria6@gmail.com');
insert into Usuario(nom_usu,nombre,apellidos,pass,email) values ('elenaro','elena','romero contreras','elena95','elenaromero@gmail.com');
insert into Usuario(nom_usu,nombre,apellidos,pass,email) values ('moyita222','antonio','moya martin-castanio','cordoba','anmomar85@gmail.com');
insert into Usuario(nom_usu,nombre,apellidos,pass,email) values ('juanikerbrahimi','juan luis','suarez diaz','juanlu1995','jlsuarezdiaz@hotmmail.com');
insert into Usuario(nom_usu,nombre,apellidos,pass,email) values ('jpoyatos','javier','poyatos amador','javimalaga','poyatosamador@gmail.com');