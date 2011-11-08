<?php

/**
 * EL ARCHIVO EXTRAE LOS ULTIMOS GPS DE CADA VEHICULO
 * PARA SER GRAFICADOS EN EL MAPA
 * qmarqeva@gmail.com
 */
require_once('../../dll/conect.php');

extract($_GET);

$consultaSql = "
    SELECT A.N_UNIDAD, A.LONGITUD, A.LATITUD, A.VELOCIDAD, A.FECHA_HORA, A.ID_CODIGO
    FROM ULTIMOS_GPS A";

consulta($consultaSql);
$resulset = variasFilas();

$salida = "";

for ($i = 0; $i < count($resulset); $i++) {
    $fila = $resulset[$i];

    $par = explode(" ", $fila["FECHA_HORA"]);

    $salida .= $fila["N_UNIDAD"] . "%" .
            $fila["LONGITUD"] . "%" .
            $fila["LATITUD"] . "%" .
            $fila["VELOCIDAD"] . "%" .
            $par[0] . "%" .
            $par[1] . "%" .
            $fila["ID_CODIGO"] . "#";
}

echo $salida;
?>

