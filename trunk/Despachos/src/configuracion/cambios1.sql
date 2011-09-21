
use rastreosatelital;

DROP TABLE IF EXISTS `server`;
DROP TABLE IF EXISTS `configuraciones`;

CREATE TABLE `configuraciones` (
  `id_config` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `key` varchar(100) NOT NULL,
  `value` varchar(255) NOT NULL,
  `desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_config`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;


INSERT INTO `configuraciones` VALUES 
(1,'dirImgConductores','ImgConductores','Directorio para almacenar las imagenes de los conductores'),
(2,'dirImgVehiculos','ImgVehiculos','Directorio para almacenar las imagenes de los vehículos'),
(3,'dirProyecto','C:\\\\Despachos\\\\','Directorio principal del Proyecto para guardar alli los logs'),
(4,'comm','0','Puerto serial del computador el valor es con la palabra ej.COM1, 0 significa que no activar el puerto serial'),
(5,'separador','\\r\\n','Separador de ENTER para activar el identificador de llamadas'),
(6,'ip_kradac','200.0.29.121','IP pública del servidor KRADAC, para recolectar la posiciones de los carros'),
(7,'puerto_kradac','666','Puerto para hacer las consultas al servidor KRADAC por la posición de los carros'),
(8,'puerto_mapa','65000','Puerto para recoger la posición de los clientes dentro del mapa local'),
(9,'ip_mapa_local','localhost','IP para abrir el mapa local'),
(10,'puerto_mapa_local','8080','Puerto para abrir el mapa local'),
(11,'url','coopserver/','Directorio donde se encuentra el sistema de rastreo loca siempre / al final'),
(12,'url_home','index.php','Página principal del sistema de rastreo local'),
(13,'sonido_pendiente','pendiente.wav',NULL),
(14,'sonido_nueva_pendiente','nuevapendiente.wav',NULL),
(15,'tiempo_sonido','3','Tiempo de reproducción de los sonidos (segundos)'),
(16,'posicion_gps','si','Consultar posiciones de gps de las unidades del servidor KRADAC'),
(17,'enviar_mensajes','si','Sistema de envio de mensajes a las unidades con la info del cliente'),
(18,'actualizar_respaldos','no','Sistema de Actualización de Respaldos en el servidor KRADAC'),
(19,'tv','6','Versión del Servicio de TeamView'),
(20,'not_pago','si','Notificación de pago');


ALTER TABLE respaldo_asignacion_server
ADD COLUMN `ESTADO_INSERT` VARCHAR(50) DEFAULT 'RES' AFTER `DIRECCION`;

DROP PROCEDURE IF EXISTS SP_INSERTAR_RESPALDAR_SERVER;
CREATE PROCEDURE `SP_INSERTAR_RESPALDAR_SERVER`(
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


END;






