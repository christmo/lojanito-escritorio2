
use rastreosatelital;

/*Regcodesttaxi*/

ALTER TABLE `regcodesttaxi`
ADD COLUMN `FECHA_HORA` TIMESTAMP NOT NULL AFTER `N_UNIDAD`;

UPDATE regcodesttaxi
set FECHA_HORA = concat(FECHA,' ',HORA);

ALTER TABLE `regcodesttaxi`
DROP PRIMARY KEY,
ADD PRIMARY KEY USING BTREE(`ID_CODIGO`, `USUARIO`, `N_UNIDAD`, `FECHA_HORA`);

ALTER TABLE `regcodesttaxi`
DROP COLUMN `FECHA`,
DROP COLUMN `HORA`;

ALTER TABLE `regcodesttaxi` ADD INDEX `Index_regcodesttaxi`(`FECHA_HORA`);

/*Asignados Local*/

ALTER TABLE `asignados_local`
ADD COLUMN `FECHA_HORA` TIMESTAMP NOT NULL AFTER `ESTADO`;

UPDATE asignados_local
set FECHA_HORA = concat(FECHA,' ',HORA);

ALTER TABLE `asignados_local`
DROP PRIMARY KEY,
ADD PRIMARY KEY  USING BTREE(`id`, `n_unidad`, `cod_cliente`, `estado`, `fecha`, `hora`, `fono`, `valor`, `estado_insert`, `usuario`, `direccion`, `FECHA_HORA`);

ALTER TABLE `asignados_local`
DROP COLUMN `FECHA`,
DROP COLUMN `HORA`;

ALTER TABLE `asignados_local` ADD INDEX `Index_asignados_local`(`FECHA_HORA`);

/*recorridos*/

ALTER TABLE `recorridos`
ADD COLUMN `FECHA_HORA` TIMESTAMP NOT NULL AFTER `LONGITUD`;

UPDATE recorridos
set FECHA_HORA = concat(FECHA,' ',HORA);

ALTER TABLE `recorridos`
DROP PRIMARY KEY,
ADD PRIMARY KEY USING BTREE(`ID`, `N_UNIDAD`,`ID_EMPRESA`,`LATITUD`,`LONGITUD`,`FECHA_HORA`);

ALTER TABLE `recorridos`
DROP COLUMN `FECHA`,
DROP COLUMN `HORA`;

ALTER TABLE `recorridos` ADD INDEX `Index_recorridos`(`FECHA_HORA`);