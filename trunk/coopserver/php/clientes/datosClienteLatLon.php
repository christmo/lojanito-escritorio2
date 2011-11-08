<?php

//session_start();
//include('../login/isLogin.php');
require_once('../../dll/conect.php');

$consultaSql = "SELECT
  COD_CLIENTE, N_UNIDAD, LONGITUD, LATITUD, FECHA, HORA, NOMBRE, BARRIO, FONO
FROM
  POSICION_CLIENTES";


consulta($consultaSql);
$resulset = variasFilas();

$salida = "";

for ($i = 0; $i < count($resulset); $i++) {
    $fila = $resulset[$i];
    $salida .= $fila["COD_CLIENTE"] . "%" .
            $fila["N_UNIDAD"] . "%" .
            $fila["LONGITUD"] . "%" .
            $fila["LATITUD"] . "%" .
            $fila["FECHA"] . "%" .
            $fila["HORA"] . "%" .
            $fila["NOMBRE"] . "%" .
            $fila["BARRIO"] . "%" .
            $fila["FONO"] . "%" . "#";
}
echo $salida;
?>
