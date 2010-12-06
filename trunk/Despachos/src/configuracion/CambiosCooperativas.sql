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

DROP TABLE IF EXISTS `server`;
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
CONNECTION='mysql://riveralcazar:1A9l4c9A7r5i2v0E1r7@200.0.29.121:3306/rastreosatelital/ASIGNADOS_RA';

DROP TABLE IF EXISTS `respaldo_asignacion_server`;
CREATE TABLE  `respaldo_asignacion_server` (
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

ALTER TABLE VEHICULOS
ADD USUARIO VARCHAR(120) DEFAULT NULL AFTER NUM_CHASIS,
ADD REG_MUNICIPAL INT(5) DEFAULT NULL AFTER USUARIO;

DROP TABLE IF EXISTS `posicion_clientes`;
CREATE TABLE `posicion_clientes` (
  `COD_CLIENTE` int(11) NOT NULL,
  `N_UNIDAD` int(11) DEFAULT NULL,
  `NOMBRE` varchar(255) DEFAULT NULL,
  `BARRIO` varchar(125) DEFAULT NULL,
  `FONO` varchar(10) DEFAULT NULL,
  `LATITUD` double NOT NULL,
  `LONGITUD` double NOT NULL,
  `FECHA` date NOT NULL,
  `HORA` time NOT NULL,
  PRIMARY KEY (`COD_CLIENTE`,`LATITUD`,`LONGITUD`,`FECHA`,`HORA`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `recorridos`;
CREATE TABLE  `recorridos` (
  `ID` int(12) DEFAULT NULL,
  `N_UNIDAD` int(11) NOT NULL,
  `ID_EMPRESA` varchar(10) NOT NULL,
  `LATITUD` double DEFAULT NULL,
  `LONGITUD` double DEFAULT NULL,
  `FECHA` date NOT NULL,
  `HORA` time NOT NULL,
  `VELOCIDAD` double DEFAULT NULL,
  `G1` int(1) unsigned DEFAULT NULL,
  `G2` int(1) unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC
PARTITION BY HASH (ID)
PARTITIONS 1024;