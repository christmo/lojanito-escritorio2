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
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `SP_ACTUALIZAR_USUARIOS`(IN PID_EMPRESA varchar(10),
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
IN PVEL VARCHAR(25),
IN PG1 DOUBLE,
IN PG2 CHAR(10)
)
BEGIN

/*INSERT INTO RECORRIDOS(N_UNIDAD, ID_EMPRESA,LATITUD,LONGITUD,FECHA,HORA,EST_TAXI,VELOCIDAD,EST_TAXIM)
VALUES (PN_UNIDAD,PID_EMPRESA,PLAT,PLON,PFECHA,PHORA,PES_TAXI,PVEL,PES_TAXIM);*/

INSERT INTO RECORRIDOS(ID,N_UNIDAD, ID_EMPRESA,LATITUD,LONGITUD,FECHA,HORA,VELOCIDAD,G1,G2)
VALUES (PID_PART,PN_UNIDAD,PID_EMPRESA,PLAT,PLON,PFECHA,PHORA,PVEL,PG1,PG2);
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
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `SP_INSERTAR_USUARIOS`(IN PID_EMPRESA varchar(10),
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

-- Dump completed on 2010-12-09 15:27:28
