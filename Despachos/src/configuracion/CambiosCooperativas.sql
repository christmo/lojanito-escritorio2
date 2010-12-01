/*
 * Cambios en la cooperativas para que se igualen las base de datos
 * christmo ;-)
 */

/*
Configuracion MySQL
[mysqld]
port=3306
federated
long_query_time = 5
bind-address = 0.0.0.0

consultar para ver si federated esta activado
show engines;

-----------------------------------------------------------------------------
COOP                USUARIO         CLAVE                       TABLA
LojaNorte           lojanort        l3O4j1a7n3O0r8t13           ASIGNADOS_LN
Ciudad Victoria     cityvictory     C5i9t1Y0V4i7c9t0O2r7y	ASIGNADOS_CV
Riveras de Alcazar  riveralcazar    1A9l4c9A7r5i2v0E1r7         ASIGNADOS_RA
TransPeñaSur        trpesur         9s4U89p3E0r2t5Ru6a3N9s0	ASIGNADOS_TP
-----------------------------------------------------------------------------

*/

/*29-11-2010*/
ALTER TABLE respaldo_asignacion_server
ADD DIRECCION VARCHAR(100) NOT NULL AFTER USUARIO;

ALTER TABLE server
ADD DIRECCION VARCHAR(100) NOT NULL AFTER USUARIO;

create table server(
  n_unidad int(12),
  cod_cliente int(11),
  estado varchar(25) NOT NULL,
  fecha date,
  hora time,
  fono varchar(25) NOT NULL,
  valor int(12),
  estado_insert varchar(3) DEFAULT NULL,
  usuario varchar(25) DEFAULT NULL,
  direccion varchar(100) NOT NULL
)
ENGINE=FEDERATED
CONNECTION='mysql://USER:PASSWD@200.0.29.121:3306/rastreosatelital/TABLA';

CREATE TABLE  `rastreo`.`respaldo_asignacion_server` (
  `N_UNIDAD` int(12) NOT NULL,
  `COD_CLIENTE` int(11) DEFAULT NULL,
  `ESTADO` varchar(25) NOT NULL,
  `FECHA` date NOT NULL,
  `HORA` int(11) NOT NULL,
  `FONO` varchar(25) DEFAULT NULL,
  `HORA_INSERT` bigint(20) NOT NULL,
  `USUARIO` varchar(25) NOT NULL,
  `DIRECCION` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;