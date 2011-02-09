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
AndinaSur           andinasur       9s4U89p3E0r2t5Ru6a3N9s0     ASIGNADOS_AS
TaxOpel             taxopel         9s4U89p3E0r2t5Ru6a3N9s0     ASIGNADOS_TO
LojaTuristica       lojaturist      9s4U89p3E0r2t5Ru6a3N9s0     ASIGNADOS_LT
-----------------------------------------------------------------------------

*/

--
-- Table structure for table `server`
--

DROP TABLE IF EXISTS `server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `server` (
  `n_unidad` int(12) DEFAULT NULL,
  `cod_cliente` int(11) DEFAULT NULL,
  `estado` varchar(25) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `hora` time DEFAULT NULL,
  `fono` varchar(25) DEFAULT NULL,
  `valor` int(12) DEFAULT NULL,
  `estado_insert` varchar(3) DEFAULT NULL,
  `usuario` varchar(25) DEFAULT NULL,
  `direccion` varchar(250) DEFAULT NULL
) ENGINE=FEDERATED DEFAULT CHARSET=latin1 CONNECTION='mysql://usuario:clave@200.0.29.121:3306/rastreosatelital/TABLA';
/*!40101 SET character_set_client = @saved_cs_client */;
