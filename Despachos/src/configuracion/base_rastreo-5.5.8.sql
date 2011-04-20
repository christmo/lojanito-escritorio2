/*
---------------------------------------------------------
-- christmo
---------------------------------------------------------
-- Obtenida con el comando: mysqldump -u root -p -d rastreosatelital > base.sql
-- cambio de version de la base de datos...
---------------------------------------------------------
*/

-- MySQL dump 10.13  Distrib 5.5.8, for Win32 (x86)
--
-- Host: localhost    Database: rastreosatelital
-- ------------------------------------------------------
-- Server version	5.5.8-log

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

--DROP DATABASE IF EXISTS rastreosatelital;
create database rastreosatelital;
use rastreosatelital;

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
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asignados_local`
--

DROP TABLE IF EXISTS `asignados_local`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asignados_local` (
  `id` int(8) NOT NULL DEFAULT '0',
  `n_unidad` int(12) NOT NULL DEFAULT '0',
  `cod_cliente` int(11) NOT NULL DEFAULT '0',
  `estado` varchar(25) NOT NULL,
  `fecha` date NOT NULL DEFAULT '0000-00-00',
  `hora` time NOT NULL DEFAULT '00:00:00',
  `fono` varchar(25) NOT NULL DEFAULT '',
  `valor` int(12) NOT NULL DEFAULT '0',
  `estado_insert` varchar(3) NOT NULL,
  `usuario` varchar(25) NOT NULL,
  `direccion` varchar(150) NOT NULL,
  PRIMARY KEY (`id`,`n_unidad`,`cod_cliente`,`estado`,`fecha`,`hora`,`fono`,`valor`,`estado_insert`,`usuario`,`direccion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1
/*!50100 PARTITION BY HASH (ID)
PARTITIONS 400 */;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `rastreosatelital`.`TGR_ASIGNADOS_LOCAL_SERVER` BEFORE INSERT

    ON rastreosatelital.asignados_local FOR EACH ROW

BEGIN

    DECLARE FECHA DATETIME;

    DECLARE LIMITE INT(12);

    SET LIMITE = -2;

    IF (STRCMP(NEW.ESTADO, 'ASIGNADO')=0) THEN

      

      SET FECHA = DATE_SUB(NOW(),INTERVAL NEW.VALOR MINUTE);

      SET NEW.HORA = DATE_FORMAT(FECHA,'%H:%i:%s');

      SET NEW.FECHA = DATE_FORMAT(FECHA,'%Y-%m-%d');

    ELSE

      

        IF (LIMITE = NEW.VALOR) THEN

            SET NEW.HORA = CURTIME();

            SET NEW.FECHA = CURDATE();

        ELSE

            SET FECHA = DATE_SUB(NOW(),INTERVAL NEW.VALOR MINUTE);

            SET NEW.HORA = DATE_FORMAT(FECHA,'%H:%i:%s');

            SET NEW.FECHA = DATE_FORMAT(FECHA,'%Y-%m-%d');

        END IF;

    END IF;

    

    SET NEW.ID = DATE_FORMAT(CURDATE(),'%Y%m%d');



    CALL SP_INSERTAR_RESPALDAR_SERVER(

      NEW.N_UNIDAD,

      NEW.COD_CLIENTE,

      NEW.ESTADO,

      NEW.FONO,

      NEW.VALOR,

      NEW.ESTADO_INSERT,

      NEW.USUARIO,

      NEW.DIRECCION,@x);

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `centrales`
--

DROP TABLE IF EXISTS `centrales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `centrales` (
  `ID_EMPRESA` varchar(10) NOT NULL,
  `LATITUD` double DEFAULT NULL,
  `LONGITUD` double DEFAULT NULL,
  `DIRECCION` varchar(150) DEFAULT NULL,
  `TELEFONO` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`ID_EMPRESA`)
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
) ENGINE=MyISAM AUTO_INCREMENT=141 DEFAULT CHARSET=latin1;
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
-- Table structure for table `pendientes`
--

DROP TABLE IF EXISTS `pendientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pendientes` (
  `codigo` int(10) NOT NULL DEFAULT '0',
  `fecha_ini` date NOT NULL DEFAULT '0000-00-00',
  `fecha_fin` date NOT NULL DEFAULT '0000-00-00',
  `hora` time NOT NULL DEFAULT '00:00:00',
  `min_recuerdo` int(10) DEFAULT NULL,
  `cuando_recordar` varchar(50) DEFAULT NULL,
  `nota` varchar(225) DEFAULT NULL,
  `estado` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`codigo`,`fecha_ini`,`fecha_fin`,`hora`)
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
CREATE TABLE `recorridos` (
  `ID` int(12) NOT NULL,
  `N_UNIDAD` int(11) NOT NULL,
  `ID_EMPRESA` varchar(10) NOT NULL,
  `LATITUD` double NOT NULL,
  `LONGITUD` double NOT NULL,
  `FECHA` date NOT NULL,
  `HORA` time NOT NULL,
  `VELOCIDAD` double DEFAULT NULL,
  `G1` int(1) unsigned DEFAULT NULL,
  `G2` int(1) unsigned DEFAULT NULL,
  PRIMARY KEY (`LATITUD`,`LONGITUD`,`FECHA`,`HORA`,`N_UNIDAD`,`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC
/*!50100 PARTITION BY HASH (ID)
PARTITIONS 400 */;
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
  `COD_CLIENTE` int(11) NOT NULL,
  `ESTADO` varchar(25) NOT NULL,
  `FECHA` date NOT NULL,
  `HORA` int(11) NOT NULL,
  `FONO` varchar(25) NOT NULL,
  `HORA_INSERT` bigint(20) NOT NULL,
  `USUARIO` varchar(125) NOT NULL,
  `DIRECCION` varchar(125) NOT NULL,
  PRIMARY KEY (`N_UNIDAD`,`COD_CLIENTE`,`ESTADO`,`FECHA`,`HORA`,`FONO`,`HORA_INSERT`,`USUARIO`,`DIRECCION`)
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
-- Creacion del usuario KRADAC por defecto
--
INSERT INTO `usuarios` VALUES ('KR','KRADAC','47c6330f847f1342c4a306ef20869d7c','KRADAC',NULL,NULL,1,'Activo','Administrador',NULL);

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
  `USUARIO` varchar(120) DEFAULT NULL,
  `REG_MUNICIPAL` int(5) DEFAULT NULL,
  `SOAT` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`PLACA`,`N_UNIDAD`,`ID_EMPRESA`),
  KEY `FK_REFERENCE_6` (`ID_EMPRESA`),
  KEY `FK_REFERENCE_8` (`ID_CON`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-04-15 18:29:41
