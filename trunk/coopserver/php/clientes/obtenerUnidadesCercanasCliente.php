<?php

//session_start();
//include('../login/isLogin.php');
require_once('../../dll/conect.php');

extract($_POST);

$consultaSql = "SELECT N_UNIDAD, FORMAT(SF_DISTANCIA2PUNTOSKM($lat, $lon,LATITUD,LONGITUD),3) AS DISTANCIA,
FORMAT((SF_DISTANCIA2PUNTOSKM($lat, $lon,LATITUD,LONGITUD)/20)*60,2) AS 'TIEMPO'
FROM ULTIMOS_GPS
WHERE ID_CODIGO = 'AC'
ORDER BY DISTANCIA ASC LIMIT 3;";

consulta($consultaSql);
$resulset = variasFilas();

$salida = "{\"datos\": [";

for ($i = 0; $i < count($resulset); $i++) {
    $fila = $resulset[$i];
    $salida .= "{
            \"n_unidad\":\"" . $fila["N_UNIDAD"] . "\",
            \"distancia\":\"" . $fila["DISTANCIA"] . "\",
            \"tiempo\":\"" . $fila["TIEMPO"] . "\"
        }";
    if ($i != count($resulset) - 1) {
        $salida .= ",";
    }
}

$salida .="]}";

echo $salida;
?>
