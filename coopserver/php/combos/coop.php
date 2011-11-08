<?php

//session_start();
require_once('../../dll/conect.php');

$salida = "{failure:true}";

$consultaSql = "SELECT E.ID_EMPRESA, E.NOMBRE_EMP FROM EMPRESAS E";

consulta($consultaSql);
$resulset = variasFilas();

$salida = "{\"unidades\": [";

for ($i = 0; $i < count($resulset); $i++) {
    $fila = $resulset[$i];
    $salida .= "{
            \"id\":\"" . $fila["ID_EMPRESA"] . "\",
            \"name\":\"" . utf8_encode ( $fila["NOMBRE_EMP"] ) . "\"
        }";
    if ($i != count($resulset) - 1) {
        $salida .= ",";
    }
}

$salida .="]}";

echo $salida;
?>
