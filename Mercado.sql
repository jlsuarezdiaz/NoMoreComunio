/*drop table Mercado;*/

create table Mercado(
  nombre_com varchar(20) PRIMARY KEY NOT NULL,
  FOREIGN KEY (nombre_com) REFERENCES Comunidad(nombre_comunidad)
);

insert into Mercado (nombre_com) values ('ComunioDDSI');
insert into Mercado (nombre_com) values ('ComunioDDSI2');