---------------------------------------------------------
-- christmo
---------------------------------------------------------
-- Obtenida con el comando: mysqldump -u root -p -R -d rastreo > rastreo.sql
---------------------------------------------------------
-- MySQL dump 10.13  Distrib 5.1.36, for Win32 (ia32)
--
-- Host: localhost    Database: rastreo
-- ------------------------------------------------------
-- Server version	5.1.36-community-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accesos`
--

DROP TABLE IF EXISTS `accesos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accesos` (
  `IP` varchar(255) DEFAULT NULL,
  `HOST` varchar(255) DEFAULT NULL,
  `USUARIO` varchar(255) DEFAULT NULL,
  `FECHA` date DEFAULT NULL,
  `HORA` time DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asignados`
--

DROP TABLE IF EXISTS `asignados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asignados` (
  `ID_ASIGNADOS` int(11) NOT NULL AUTO_INCREMENT,
  `COD_CLIENTE` int(11) DEFAULT NULL,
  `FECHA` date NOT NULL,
  `HORA` time NOT NULL,
  `TELEFONO` varchar(25) NOT NULL,
  `NOMBRE_APELLIDO_CLI` varchar(125) DEFAULT NULL,
  `DIRECCION_CLI` varchar(125) DEFAULT NULL,
  `SECTOR` varchar(125) DEFAULT NULL,
  `MINUTOS` int(11) NOT NULL,
  `N_UNIDAD` int(11) DEFAULT NULL,
  `ATRASO` int(11) NOT NULL,
  `NOTA` varchar(255) DEFAULT NULL,
  `ESTADO` varchar(5) DEFAULT NULL,
  `ID_TURNO` int(11) DEFAULT NULL,
  `USUARIO` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID_ASIGNADOS`)
) ENGINE=MyISAM AUTO_INCREMENT=7468 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `centrales`
--

DROP TABLE IF EXISTS `centrales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `centrales` (
  `ID_EMPRESA` varchar(10) DEFAULT NULL,
  `LATITUD` double DEFAULT NULL,
  `LONGITUD` double DEFAULT NULL,
  `DIRECCION` varchar(150) DEFAULT NULL,
  `TELEFONO` varchar(150) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientes` (
  `TELEFONO` varchar(25) NOT NULL DEFAULT '',
  `CODIGO` int(10) DEFAULT NULL,
  `NOMBRE_APELLIDO_CLI` varchar(125) NOT NULL,
  `DIRECCION_CLI` varchar(125) NOT NULL,
  `SECTOR` varchar(125) DEFAULT NULL,
  `NUM_CASA_CLI` varchar(10) DEFAULT NULL,
  `LATITUD` double DEFAULT NULL,
  `LONGITUD` double DEFAULT NULL,
  `INFOR_ADICIONAL` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`NOMBRE_APELLIDO_CLI`,`DIRECCION_CLI`,`TELEFONO`) USING BTREE
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cod_multas`
--

DROP TABLE IF EXISTS `cod_multas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cod_multas` (
  `IDENTIFICADOR` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `COD_MULTA` varchar(5) NOT NULL,
  `DESCRIPCION` varchar(255) DEFAULT NULL,
  `VALOR` double DEFAULT NULL,
  PRIMARY KEY (`IDENTIFICADOR`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `codesttaxi`
--

DROP TABLE IF EXISTS `codesttaxi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `codesttaxi` (
  `ID_CODIGO` varchar(50) NOT NULL,
  `ETIQUETA` varchar(255) DEFAULT NULL,
  `COLOR` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID_CODIGO`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `conductores`
--

DROP TABLE IF EXISTS `conductores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conductores` (
  `ID_CON` int(11) NOT NULL AUTO_INCREMENT,
  `CEDULA_CONDUCTOR` varchar(10) NOT NULL,
  `NOMBRE_APELLIDO_CON` varchar(125) NOT NULL,
  `DIRECCION_CON` varchar(125) NOT NULL,
  `NUM_CASA_CON` varchar(10) DEFAULT NULL,
  `TIPO_SANGRE` varchar(10) DEFAULT NULL,
  `ESTADO_CIVIL` varchar(25) DEFAULT NULL,
  `CONYUGE` varchar(125) DEFAULT NULL,
  `MAIL` varchar(125) DEFAULT NULL,
  `FOTO` varchar(125) DEFAULT NULL,
  PRIMARY KEY (`ID_CON`,`CEDULA_CONDUCTOR`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=80 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `empresas`
--

DROP TABLE IF EXISTS `empresas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `empresas` (
  `ID_EMPRESA` varchar(10) NOT NULL,
  `NOMBRE_EMP` varchar(100) NOT NULL,
  `MODEM` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID_EMPRESA`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `multas`
--

DROP TABLE IF EXISTS `multas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `multas` (
  `USUARIO` varchar(100) DEFAULT NULL,
  `N_UNIDAD` int(11) DEFAULT NULL,
  `FECHA` date DEFAULT NULL,
  `HORA` time DEFAULT NULL,
  `COD_MULTA` varchar(5) DEFAULT NULL,
  `ESTADO` int(1) DEFAULT NULL,
  KEY `FK_REFERENCE_13` (`COD_MULTA`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `multas_asignadas`
--

DROP TABLE IF EXISTS `multas_asignadas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `multas_asignadas` (
  `ID_ASIG` int(10) NOT NULL AUTO_INCREMENT,
  `USUARIO` varchar(100) DEFAULT NULL,
  `N_UNIDAD` int(11) DEFAULT NULL,
  `FECHA` date DEFAULT NULL,
  `HORA` time DEFAULT NULL,
  `COD_MULTA` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`ID_ASIG`),
  KEY `FK_CODMULTAS` (`COD_MULTA`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `multas_pagadas`
--

DROP TABLE IF EXISTS `multas_pagadas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `multas_pagadas` (
  `ID_ASIG` int(10) DEFAULT NULL,
  `USUARIO` varchar(100) DEFAULT NULL,
  `FECHA` date DEFAULT NULL,
  `HORA` time DEFAULT NULL,
  KEY `FK_REFERENCE_14` (`ID_ASIG`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `posicion_clientes`
--

DROP TABLE IF EXISTS `posicion_clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recorridos`
--

DROP TABLE IF EXISTS `recorridos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
PARTITIONS 1024 ;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `regcodesttaxi`
--

DROP TABLE IF EXISTS `regcodesttaxi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `regcodesttaxi` (
  `FECHA` date NOT NULL,
  `HORA` time NOT NULL,
  `ID_CODIGO` varchar(50) DEFAULT NULL,
  `USUARIO` varchar(100) DEFAULT NULL,
  `N_UNIDAD` int(11) NOT NULL,
  PRIMARY KEY (`FECHA`,`HORA`,`N_UNIDAD`),
  KEY `FK_REFERENCE_10` (`ID_CODIGO`),
  KEY `FK_REFERENCE_12` (`USUARIO`),
  KEY `FK_N_UNIDAD` (`N_UNIDAD`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `respaldo_asignacion_server`
--

DROP TABLE IF EXISTS `respaldo_asignacion_server`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `respaldo_asignacion_server` (
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `telefonos`
--

DROP TABLE IF EXISTS `telefonos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `telefonos` (
  `ID_TELEFONO` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ID_CON` int(11) DEFAULT NULL,
  `NUMERO` varchar(25) DEFAULT NULL,
  `TIPO` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`ID_TELEFONO`),
  KEY `FK_TIENEN` (`ID_CON`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `turnos`
--

DROP TABLE IF EXISTS `turnos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `turnos` (
  `ID_TURNO` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `HORA_INI` time DEFAULT NULL,
  `HORA_FIN` time DEFAULT NULL,
  PRIMARY KEY (`ID_TURNO`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuarios` (
  `ID_EMPRESA` varchar(10) NOT NULL,
  `USUARIO` varchar(100) NOT NULL,
  `CLAVE` varchar(255) NOT NULL,
  `NOMBRE_USUARIO` varchar(100) DEFAULT NULL,
  `DIRECCION` varchar(255) DEFAULT NULL,
  `TELEFONO` varchar(255) DEFAULT NULL,
  `ID_TURNO` int(10) DEFAULT NULL,
  `ESTADO` varchar(255) DEFAULT NULL,
  `OPERADOR` varchar(255) DEFAULT NULL,
  `CEDULA_USUARIO` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ID_EMPRESA`,`USUARIO`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `vehiculos`
--

DROP TABLE IF EXISTS `vehiculos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehiculos` (
  `PLACA` varchar(10) NOT NULL,
  `N_UNIDAD` int(11) NOT NULL,
  `ID_EMPRESA` varchar(10) NOT NULL,
  `ID_CON` int(11) DEFAULT NULL,
  `CONDUCTOR_AUX` int(11) DEFAULT NULL,
  `MODELO` varchar(25) DEFAULT NULL,
  `ANIO` int(5) DEFAULT NULL,
  `PROPIETARIO` varchar(125) DEFAULT NULL,
  `INF_ADICIONAL` varchar(255) DEFAULT NULL,
  `IMAGEN` varchar(125) DEFAULT NULL,
  `MARCA` varchar(45) DEFAULT NULL,
  `NUM_MOTOR` varchar(125) DEFAULT NULL,
  `NUM_CHASIS` varchar(125) DEFAULT NULL,
  `USUARIO` varchar(125) DEFAULT NULL,
  `REG_MUNICIPAL` int(5) DEFAULT NULL,
  PRIMARY KEY (`PLACA`,`N_UNIDAD`,`ID_EMPRESA`),
  KEY `FK_REFERENCE_6` (`ID_EMPRESA`),
  KEY `FK_REFERENCE_8` (`ID_CON`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'rastreo'
--
/*!50003 DROP FUNCTION IF EXISTS `SF_CONDUCTOR_ASIGNADO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 FUNCTION `SF_CONDUCTOR_ASIGNADO`(
                                                             CON VARCHAR(125),
                                                            PL VARCHAR(10) ) RETURNS int(11)
    DETERMINISTIC
BEGIN

  DECLARE NUM_COINCIDENCIAS INT;
  DECLARE ID_CONDUCTOR INT;

  SELECT ID_CON
  INTO ID_CONDUCTOR
  FROM CONDUCTORES
  WHERE NOMBRE_APELLIDO_CON = CON;


  SELECT COUNT(PLACA)
  INTO NUM_COINCIDENCIAS
  FROM VEHICULOS
  WHERE ID_CON = ID_CONDUCTOR
  AND PLACA <> PL;

  RETURN NUM_COINCIDENCIAS;


END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `SF_ESTADO_UNIDAD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 FUNCTION `SF_ESTADO_UNIDAD`(UNIDAD INT) RETURNS varchar(10) CHARSET latin1
    DETERMINISTIC
BEGIN
DECLARE ESTADO VARCHAR(10);
SELECT ID_CODIGO
INTO ESTADO 
FROM REGCODESTTAXI 
WHERE N_UNIDAD=UNIDAD AND 
FECHA = (
	SELECT MAX(FECHA)
	FROM REGCODESTTAXI 
	WHERE N_UNIDAD=UNIDAD 
)
AND
HORA =(
	SELECT MAX(HORA)
	FROM REGCODESTTAXI 
	WHERE N_UNIDAD=UNIDAD AND FECHA = (SELECT MAX(FECHA) FROM REGCODESTTAXI WHERE N_UNIDAD=UNIDAD)
	GROUP BY FECHA
);

RETURN ESTADO;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `SF_OBTENER_CARRERAS_TAXI` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 FUNCTION `SF_OBTENER_CARRERAS_TAXI`(UNIDAD INT, PTURNO INT, PUSUARIO VARCHAR(100)) RETURNS int(11)
    DETERMINISTIC
BEGIN
	DECLARE CARRERAS INT;
	SET CARRERAS=0;
	SELECT COUNT(*)
	INTO CARRERAS
	FROM ASIGNADOS
	WHERE ESTADO='F' AND N_UNIDAD = UNIDAD AND FECHA = CURDATE() 
	AND USUARIO = PUSUARIO AND ID_TURNO = PTURNO
	GROUP BY N_UNIDAD;
	RETURN CARRERAS;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP FUNCTION IF EXISTS `SF_TURNOS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 FUNCTION `SF_TURNOS`(hora TIME) RETURNS varchar(50) CHARSET latin1
    DETERMINISTIC
BEGIN
DECLARE turno VARCHAR(50);
	SELECT concat(HORA_INI," - ",HORA_FIN)
	INTO turno
	FROM TURNOS
	WHERE (hora BETWEEN HORA_INI AND HORA_FIN)
	OR
	(
		HORA_INI = (SELECT MAX(HORA_INI) FROM TURNOS) AND 
		hora BETWEEN (SELECT MAX(HORA_INI) FROM TURNOS) AND '23:59:59'
		OR
		HORA_FIN = (SELECT MIN(HORA_FIN) FROM TURNOS) AND
		hora BETWEEN '00:00:00' AND (SELECT MIN(HORA_FIN) FROM TURNOS)
	);
	RETURN turno;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SP_ACTUALIZAR_USUARIOS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `SP_ACTUALIZAR_USUARIOS`(
IN PID_EMPRESA varchar(10),
IN PUSUARIO varchar(100),
IN PCLAVE varchar(255),
IN PNOMBRE_USUARIO VARCHAR(100),
IN PDIRECCION VARCHAR(255),
IN PTELEFONO VARCHAR(255),
IN PID_TURNO INT(10),
IN PESTADO VARCHAR(255),
IN POPERADOR varchar(255),
IN PCI VARCHAR(10)
)
BEGIN

UPDATE USUARIOS
SET 
ID_EMPRESA = PID_EMPRESA,
CLAVE = PCLAVE,
NOMBRE_USUARIO = PNOMBRE_USUARIO,
DIRECCION = PDIRECCION,
TELEFONO = PTELEFONO,
ID_TURNO = PID_TURNO,
ESTADO = PESTADO,
OPERADOR = POPERADOR,
CEDULA_USUARIO = PCI
WHERE USUARIO = PUSUARIO;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SP_INSERTAR_DESPACHOS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `SP_INSERTAR_DESPACHOS`(
	IN PCOD_CLIENTE INT(10),
	IN PFECHA DATE,
	IN PHORA TIME,
	IN PTELEFONO VARCHAR(25),
	IN PNOMBRE_APELLIDO_CLI VARCHAR(125),
	IN PDIRECCION_CLI VARCHAR(125),
	IN PSECTOR VARCHAR(125),
	IN PMINUTOS INT(10),
	IN PN_UNIDAD INT(10),
	IN PATRASO INT(10),
	IN PNOTA VARCHAR(255),
	IN PESTADO VARCHAR(5),
	IN PID_TURNO INT(10),
	IN PUSUARIO VARCHAR(100)
)
BEGIN
	INSERT INTO ASIGNADOS(
		COD_CLIENTE,
		FECHA,
		HORA,
		TELEFONO,
		NOMBRE_APELLIDO_CLI,
		DIRECCION_CLI,
		SECTOR,
		MINUTOS,
		N_UNIDAD,
		ATRASO,
		NOTA,
		ESTADO,
		ID_TURNO,
		USUARIO
  )
  VALUES(
		PCOD_CLIENTE,
		PFECHA,
		PHORA,
		PTELEFONO,
		PNOMBRE_APELLIDO_CLI,
		PDIRECCION_CLI,
		PSECTOR,
		PMINUTOS,
		PN_UNIDAD,
		PATRASO,
		PNOTA,
		PESTADO,
		PID_TURNO,
		PUSUARIO
  );
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SP_INSERTAR_RECORRIDOS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `SP_INSERTAR_RECORRIDOS`(
IN PID_PART varchar(10),
IN PN_UNIDAD INTEGER,
IN PID_EMPRESA varchar(10),
IN PLAT DOUBLE,
IN PLON DOUBLE,
IN PFECHA DATE,
IN PHORA TIME,
IN PES_TAXI VARCHAR(25),
IN PVEL DOUBLE,
IN PES_TAXIM CHAR(10)
)
BEGIN

INSERT INTO RECORRIDOS(N_UNIDAD, ID_EMPRESA,LATITUD,LONGITUD,FECHA,HORA,EST_TAXI,VELOCIDAD,EST_TAXIM)
VALUES (PN_UNIDAD,PID_EMPRESA,PLAT,PLON,PFECHA,PHORA,PES_TAXI,PVEL,PES_TAXIM);

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SP_INSERTAR_USUARIOS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `SP_INSERTAR_USUARIOS`(
IN PID_EMPRESA varchar(10),
IN PUSUARIO varchar(100),
IN PCLAVE varchar(255),
IN PNOMBRE_USUARIO VARCHAR(100),
IN PDIRECCION VARCHAR(255),
IN PTELEFONO VARCHAR(255),
IN PID_TURNO INT(10),
IN PESTADO VARCHAR(255),
IN POPERADOR varchar(255),
IN PCI VARCHAR(10)
)
BEGIN

INSERT INTO USUARIOS
VALUES (PID_EMPRESA,PUSUARIO,PCLAVE,PNOMBRE_USUARIO,PDIRECCION,PTELEFONO,PID_TURNO,PESTADO,POPERADOR,PCI);

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SP_INSERT_VEHICULO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `SP_INSERT_VEHICULO`(
IN PL VARCHAR(10),
IN NUNI INT(11),
IN EMP VARCHAR(10),
IN CON VARCHAR(125),
IN CONAUX VARCHAR(125),
IN MODEL VARCHAR(25),
IN AN INT(5),
IN PRO VARCHAR(125),
IN INF VARCHAR(225),
IN IMG VARCHAR(125),
IN MAR VARCHAR(45),
IN MOT VARCHAR(125),
IN CHA VARCHAR(125),
IN IDUSER VARCHAR(100),
IN REG_MUN int(5)
)
BEGIN

DECLARE IDCON INT(11);
DECLARE IDCONAUX INT(11);

SELECT ID_CON
INTO IDCON
FROM CONDUCTORES
WHERE NOMBRE_APELLIDO_CON = CON;

SELECT ID_CON
INTO IDCONAUX
FROM CONDUCTORES
WHERE NOMBRE_APELLIDO_CON = CONAUX;

INSERT INTO
VEHICULOS
VALUES (
PL,
NUNI,
EMP,
IDCON,
IDCONAUX,
MODEL,
AN,
PRO,
INF,
IMG,
MAR,
MOT,
CHA,
IDUSER,
REG_MUN
);

INSERT INTO REGCODESTTAXI
VALUES (NOW(),NOW(),'AC',IDUSER,NUNI);

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SP_UPDATE_VEHICULO` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `SP_UPDATE_VEHICULO`(
                                                          IN PL VARCHAR(10),
                                                          IN NUNI INT(11),
                                                          IN EMP VARCHAR(10),
                                                          IN CON VARCHAR(125),
                                                          IN CONAUX VARCHAR(125),
                                                          IN MODEL VARCHAR(25),
                                                          IN AN INT(5),
                                                          IN PRO VARCHAR(125),
                                                          IN INF VARCHAR(225),
                                                          IN IMG VARCHAR(125),
                                                          IN MAR VARCHAR(45),
                                                          IN MOT VARCHAR(125),
                                                          IN CHA VARCHAR(125),
                                                          IN REG_MUN int(5))
BEGIN

  DECLARE IDCON INT(11);
  DECLARE IDCONAUX INT(11);

  SELECT ID_CON
  INTO IDCON
  FROM CONDUCTORES
  WHERE NOMBRE_APELLIDO_CON = CON;

  SELECT ID_CON
  INTO IDCONAUX
  FROM CONDUCTORES
  WHERE NOMBRE_APELLIDO_CON = CONAUX;

  UPDATE
    VEHICULOS
  SET
    N_UNIDAD = NUNI,
    ID_EMPRESA = EMP,
    ID_CON = IDCON,
    CONDUCTOR_AUX = IDCONAUX,
    MODELO = MODEL,
    ANIO = AN,
    PROPIETARIO = PRO,
    INF_ADICIONAL = INF,
    IMAGEN = IMG,
    MARCA = MAR,
    NUM_MOTOR = MOT,
    NUM_CHASIS = CHA,
    REG_MUNICIPAL = REG_MUN
  WHERE
    PLACA = PL;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2010-11-19 19:25:46
