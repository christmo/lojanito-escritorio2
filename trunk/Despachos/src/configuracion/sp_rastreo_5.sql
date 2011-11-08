-- MySQL dump 10.13  Distrib 5.1.36, for Win32 (ia32)
--
-- Host: localhost    Database: rastreosatelital
-- ------------------------------------------------------
-- Server version	5.1.36-community-log

use rastreosatelital;

--
-- Dumping routines for database 'rastreosatelital'
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
/*!50003 DROP FUNCTION IF EXISTS `SF_DISTANCIA2PUNTOSKM` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 FUNCTION `SF_DISTANCIA2PUNTOSKM`(
lat_ini double,
lon_ini double,
lat_fin double,
lon_fin double
) RETURNS double
    DETERMINISTIC
BEGIN

DECLARE distancia DOUBLE;
DECLARE RADIOTIERRA DOUBLE;

SET RADIOTIERRA = 6371;

SET distancia = RADIOTIERRA
* ((2 * ASIN(SQRT(POW(SIN((RADIANS(lat_ini) - RADIANS(lat_fin)) / 2), 2)
+ COS(RADIANS(lat_ini)) * COS(RADIANS(lat_fin))
* POW(SIN((RADIANS(lon_ini) - RADIANS(lon_fin)) / 2), 2)))));

RETURN distancia;

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

SELECT DISTINCT ID_CODIGO

INTO ESTADO

FROM REGCODESTTAXI

WHERE N_UNIDAD=UNIDAD AND

FECHA_HORA = (

  SELECT MAX(FECHA_HORA)

	FROM REGCODESTTAXI

	WHERE N_UNIDAD=UNIDAD AND FECHA_HORA = (SELECT MAX(FECHA_HORA) FROM REGCODESTTAXI WHERE N_UNIDAD=UNIDAD)

	GROUP BY FECHA_HORA

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
/*!50003 DROP PROCEDURE IF EXISTS `SP_ACTUALIZAR_RESPALDOS_SERVER` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `SP_ACTUALIZAR_RESPALDOS_SERVER`()
BEGIN







   DECLARE PN_UNIDAD INT(12);

   DECLARE PCOD_CLIENTE INT(11);

   DECLARE PESTADO VARCHAR(25);

   DECLARE PFONO VARCHAR(25);

  DECLARE PHORA_INSERT BIGINT(20);

  DECLARE PHORA INT(11);

  DECLARE PUSUARIO VARCHAR(25);

  DECLARE PDIRECCION VARCHAR(150);

  
  DECLARE n_respaldos INT(10);



  DECLARE no_mas_respaldos INT;



  DECLARE csr_respaldos CURSOR FOR

  SELECT

    N_UNIDAD,

    COD_CLIENTE,

    ESTADO,

    FONO,

    HORA_INSERT,

    HORA,

    USUARIO,

    DIRECCION

  FROM RESPALDO_ASIGNACION_SERVER;



  DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_mas_respaldos=1;



  SET @i=0;



  SELECT COUNT(*) INTO @n_respaldos FROM RESPALDO_ASIGNACION_SERVER;



  



  OPEN csr_respaldos;



  REPEAT

    FETCH csr_respaldos INTO PN_UNIDAD, PCOD_CLIENTE, PESTADO, PFONO, PHORA_INSERT, PHORA, PUSUARIO, PDIRECCION;



    INSERT INTO SERVER1(N_UNIDAD,COD_CLIENTE,ESTADO,FONO,VALOR,ESTADO_INSERT,USUARIO,DIRECCION)

    VALUES(PN_UNIDAD,PCOD_CLIENTE,PESTADO,PFONO,TRUNCATE(((UNIX_TIMESTAMP()-PHORA_INSERT)/60)+PHORA,0),'RES',PUSUARIO,PDIRECCION);



    DELETE FROM RESPALDO_ASIGNACION_SERVER

    WHERE N_UNIDAD = PN_UNIDAD AND HORA_INSERT = PHORA_INSERT;



    SET @i=@i+1;



  UNTIL no_mas_respaldos

  END REPEAT;



  CLOSE csr_respaldos;



  SET no_mas_respaldos=0;



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
/*!50003 DROP PROCEDURE IF EXISTS `SP_ELIMINAR_PENDIENTE` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `SP_ELIMINAR_PENDIENTE`(
IN PCOD INT(10),
IN PFI  DATE,
IN PFF  DATE,
IN PH   TIME
)
BEGIN

DELETE FROM PENDIENTES
WHERE CODIGO = PCOD AND FECHA_INI = PFI AND FECHA_FIN = PFF AND HORA = PH;

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
/*!50003 DROP PROCEDURE IF EXISTS `SP_INSERTAR_PENDIENTES` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `SP_INSERTAR_PENDIENTES`(
IN PCODIGO INTEGER,
IN PFECHA_INI DATE,
IN PFECHA_FIN DATE,
IN PHORA TIME,
IN PMIN_RECUERDO INT,
IN PCUANDO_RECORDAR VARCHAR(50),
IN PNOTA VARCHAR(225),
IN PESTADO  VARCHAR(10)
)
BEGIN

INSERT INTO PENDIENTES(CODIGO, FECHA_INI, FECHA_FIN, HORA, MIN_RECUERDO, CUANDO_RECORDAR,NOTA,ESTADO)
VALUES (PCODIGO,PFECHA_INI,PFECHA_FIN,PHORA,PMIN_RECUERDO,PCUANDO_RECORDAR,PNOTA,PESTADO);

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

INSERT INTO RECORRIDOS(ID,N_UNIDAD, ID_EMPRESA,LATITUD,LONGITUD,FECHA_HORA,VELOCIDAD,G1,G2)
VALUES (PID_PART,PN_UNIDAD,PID_EMPRESA,PLAT,PLON,CONCAT(PFECHA,' ',PHORA),PVEL,PG1,PG2);


END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SP_INSERTAR_RESPALDAR_SERVER` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = latin1 */ ;
/*!50003 SET character_set_results = latin1 */ ;
/*!50003 SET collation_connection  = latin1_swedish_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50020 DEFINER=`root`@`localhost`*/ /*!50003 PROCEDURE `SP_INSERTAR_RESPALDAR_SERVER`(
IN PN_UNIDAD INTEGER,
IN PCOD_CLIENTE INTEGER,
IN PESTADO VARCHAR(50),
IN PFONO VARCHAR(50),
IN PVALOR INT,
IN PESTADO_INSERT VARCHAR(50),
IN PUSUARIO VARCHAR(50),
IN PDIRECCION  VARCHAR(150),
out x varchar(150)
)
BEGIN

DECLARE NO_ACCESO_BD CONDITION FOR 1296;
DECLARE NO_PERMISO_USER CONDITION FOR 1429;
DECLARE MUCHO_TIEMPO_ESPERANDO CONDITION FOR 1159;
DECLARE TABLA_NO_EXISTE CONDITION FOR 1146;

 DECLARE CONTINUE HANDLER FOR NO_ACCESO_BD 
  BEGIN
    SET x = 1;
  END;

  DECLARE CONTINUE HANDLER FOR NO_PERMISO_USER 
  BEGIN
    SET x = 1;
  END;

  DECLARE CONTINUE HANDLER FOR MUCHO_TIEMPO_ESPERANDO
  BEGIN
    SET x = 1;
  END;

  DECLARE CONTINUE HANDLER FOR TABLA_NO_EXISTE
  BEGIN
    SET x = 1;
  END;
/*
tabla XX comodin para que siempre se guarde en la otra tabla de respaldos
esto se hizo para que por este trigger insertar al servidor automaticamente en 
la tabla federada server, por problemas de conexion en las centrales se elimino
la tabla server y se creo la tabla independiente server_federada para subir los
datos de respaldo...
*/
INSERT INTO XX(N_UNIDAD,COD_CLIENTE,ESTADO,FONO,VALOR,ESTADO_INSERT,USUARIO,DIRECCION)
VALUES (PN_UNIDAD,PCOD_CLIENTE,PESTADO,PFONO,PVALOR,PESTADO_INSERT,PUSUARIO,PDIRECCION);

IF x = 1 THEN
     
     IF PVALOR = -2 THEN
        INSERT INTO RESPALDO_ASIGNACION_SERVER(N_UNIDAD,COD_CLIENTE,ESTADO,FECHA,HORA,FONO,HORA_INSERT,USUARIO,DIRECCION,ESTADO_INSERT)
        VALUES (PN_UNIDAD,PCOD_CLIENTE,PESTADO,NOW(),0,PFONO,UNIX_TIMESTAMP(),PUSUARIO,PDIRECCION,PESTADO_INSERT);

     ELSE
        INSERT INTO RESPALDO_ASIGNACION_SERVER(N_UNIDAD,COD_CLIENTE,ESTADO,FECHA,HORA,FONO,HORA_INSERT,USUARIO,DIRECCION,ESTADO_INSERT)
        VALUES (PN_UNIDAD,PCOD_CLIENTE,PESTADO,NOW(),PVALOR,PFONO,UNIX_TIMESTAMP(),PUSUARIO,PDIRECCION,PESTADO_INSERT);

     END IF;
       
END IF;
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

IN REG_MUN VARCHAR(10),

IN SOAT VARCHAR(15))
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

REG_MUN,

SOAT

);



INSERT INTO REGCODESTTAXI

VALUES ('AC',IDUSER,NUNI,NOW());





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
                                                          IN REG_MUN int(5),
                                                          IN PSOAT VARCHAR(15))
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
    REG_MUNICIPAL = REG_MUN,
    SOAT = PSOAT
  WHERE
    PLACA = PL;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

-- Dump completed on 2011-10-14 17:25:18
