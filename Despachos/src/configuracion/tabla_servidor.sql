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
LojaNorte           lojanort	l3O4j1a7n3O0r8t13                   ASIGNADOS_LN
Ciudad Victoria     cityvictory	C5i9t1Y0V4i7c9t0O2r7y               ASIGNADOS_CV
Riveras de Alcazar  riveralcazar	1A9l4c9A7r5i2v0E1r7         ASIGNADOS_RA
TransPeñaSur        trpesur	9s4U89p3E0r2t5Ru6a3N9s0             ASIGNADOS_TP
Andina Sur          adnsur	c827dc4d8976H962505f9288            ASIGNADOS_AS
Taxopel             taxopel	336c2cb0b84800172f6e5dfcc570faeb    ASIGNADOS_TX
Loja Turistica      lojaturist	f9817fc5cbb25fc9d7da53e1dfc14cd3    ASIGNADOS_LT
DiscaTaxi           discataxi	N3g13r20dsk199tx38k2r3c             ASIGNADOS_DT
Kradac              krctest	krctesting                          ASIGNADOS_KR
-----------------------------------------------------------------------------

*/

use rastreosatelital;

--
-- Table structure for table `server_federada`
--

DROP TABLE IF EXISTS `server_federada`;


/*LOJATURISTICA*/
CREATE TABLE `server_federada` (
  `n_unidad` int(12) DEFAULT NULL,
  `cod_cliente` int(11) DEFAULT NULL,
  `estado` varchar(25) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `fono` varchar(25) DEFAULT NULL,
  `valor` int(12) DEFAULT NULL,
  `estado_insert` varchar(3) DEFAULT NULL,
  `usuario` varchar(25) DEFAULT NULL,
  `direccion` varchar(150) DEFAULT NULL
) ENGINE=FEDERATED DEFAULT CHARSET=latin1 CONNECTION='mysql://lojaturist:f9817fc5cbb25fc9d7da53e1dfc14cd3@200.0.29.121:3306/rastreosatelital/ASIGNADOS_LT';

/*TAXOPEL*/
CREATE TABLE `server_federada` (
  `n_unidad` int(12) DEFAULT NULL,
  `cod_cliente` int(11) DEFAULT NULL,
  `estado` varchar(25) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `fono` varchar(25) DEFAULT NULL,
  `valor` int(12) DEFAULT NULL,
  `estado_insert` varchar(3) DEFAULT NULL,
  `usuario` varchar(25) DEFAULT NULL,
  `direccion` varchar(150) DEFAULT NULL
) ENGINE=FEDERATED DEFAULT CHARSET=latin1 CONNECTION='mysql://taxopel:336c2cb0b84800172f6e5dfcc570faeb@200.0.29.121:3306/rastreosatelital/ASIGNADOS_TX';

/*ANDINASUR*/
CREATE TABLE `server_federada` (
  `n_unidad` int(12) DEFAULT NULL,
  `cod_cliente` int(11) DEFAULT NULL,
  `estado` varchar(25) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `fono` varchar(25) DEFAULT NULL,
  `valor` int(12) DEFAULT NULL,
  `estado_insert` varchar(3) DEFAULT NULL,
  `usuario` varchar(25) DEFAULT NULL,
  `direccion` varchar(150) DEFAULT NULL
) ENGINE=FEDERATED DEFAULT CHARSET=latin1 CONNECTION='mysql://adnsur:c827dc4d8976H962505f9288@200.0.29.121:3306/rastreosatelital/ASIGNADOS_AS';

/*TRANSPENASUR*/
CREATE TABLE `server_federada` (
  `n_unidad` int(12) DEFAULT NULL,
  `cod_cliente` int(11) DEFAULT NULL,
  `estado` varchar(25) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `fono` varchar(25) DEFAULT NULL,
  `valor` int(12) DEFAULT NULL,
  `estado_insert` varchar(3) DEFAULT NULL,
  `usuario` varchar(25) DEFAULT NULL,
  `direccion` varchar(150) DEFAULT NULL
) ENGINE=FEDERATED DEFAULT CHARSET=latin1 CONNECTION='mysql://trpesur:9s4U89p3E0r2t5Ru6a3N9s0@200.0.29.121:3306/rastreosatelital/ASIGNADOS_TP';

/*RIVERAS*/
CREATE TABLE `server_federada` (
  `n_unidad` int(12) DEFAULT NULL,
  `cod_cliente` int(11) DEFAULT NULL,
  `estado` varchar(25) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `fono` varchar(25) DEFAULT NULL,
  `valor` int(12) DEFAULT NULL,
  `estado_insert` varchar(3) DEFAULT NULL,
  `usuario` varchar(25) DEFAULT NULL,
  `direccion` varchar(150) DEFAULT NULL
) ENGINE=FEDERATED DEFAULT CHARSET=latin1 CONNECTION='mysql://riveralcazar:1A9l4c9A7r5i2v0E1r7@200.0.29.121:3306/rastreosatelital/ASIGNADOS_RA';

/*CIUDAD VICTORIA*/
CREATE TABLE `server_federada` (
  `n_unidad` int(12) DEFAULT NULL,
  `cod_cliente` int(11) DEFAULT NULL,
  `estado` varchar(25) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `fono` varchar(25) DEFAULT NULL,
  `valor` int(12) DEFAULT NULL,
  `estado_insert` varchar(3) DEFAULT NULL,
  `usuario` varchar(25) DEFAULT NULL,
  `direccion` varchar(150) DEFAULT NULL
) ENGINE=FEDERATED DEFAULT CHARSET=latin1 CONNECTION='mysql://cityvictory:C5i9t1Y0V4i7c9t0O2r7y@200.0.29.121:3306/rastreosatelital/ASIGNADOS_CV';

/*LOJANORTE*/
CREATE TABLE `server_federada` (
  `n_unidad` int(12) DEFAULT NULL,
  `cod_cliente` int(11) DEFAULT NULL,
  `estado` varchar(25) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `fono` varchar(25) DEFAULT NULL,
  `valor` int(12) DEFAULT NULL,
  `estado_insert` varchar(3) DEFAULT NULL,
  `usuario` varchar(25) DEFAULT NULL,
  `direccion` varchar(150) DEFAULT NULL
) ENGINE=FEDERATED DEFAULT CHARSET=latin1 CONNECTION='mysql://lojanort:l3O4j1a7n3O0r8t13@200.0.29.121:3306/rastreosatelital/ASIGNADOS_LN';

/*DISCATAXI*/
CREATE TABLE `server_federada` (
  `n_unidad` int(12) DEFAULT NULL,
  `cod_cliente` int(11) DEFAULT NULL,
  `estado` varchar(25) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `fono` varchar(25) DEFAULT NULL,
  `valor` int(12) DEFAULT NULL,
  `estado_insert` varchar(3) DEFAULT NULL,
  `usuario` varchar(25) DEFAULT NULL,
  `direccion` varchar(150) DEFAULT NULL
) ENGINE=FEDERATED DEFAULT CHARSET=latin1 CONNECTION='mysql://discataxi:N3g13r20dsk199tx38k2r3c@200.0.29.121:3306/rastreosatelital/ASIGNADOS_DT';

/*
CREATE TABLE `server_federada` (
  `n_unidad` int(12) DEFAULT NULL,
  `cod_cliente` int(11) DEFAULT NULL,
  `estado` varchar(25) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `fono` varchar(25) DEFAULT NULL,
  `valor` int(12) DEFAULT NULL,
  `estado_insert` varchar(3) DEFAULT NULL,
  `usuario` varchar(25) DEFAULT NULL,
  `direccion` varchar(150) DEFAULT NULL
) ENGINE=FEDERATED DEFAULT CHARSET=latin1 CONNECTION='mysql://usuario:clave@200.0.29.121:3306/rastreosatelital/TABLA';
*/