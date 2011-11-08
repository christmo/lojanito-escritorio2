<?php

//session_start();
//include('../login/isLogin.php');
require_once('../../dll/conect.php');
extract($_GET);

//if (isset($xdfq) && ($xdfq=='8et251')) {
//    $idC = $_SESSION["empresa"];
//}


$salida = "{failure:true}";

//tomar en cuenta el nombre del registro municipal
$consultaSql = "SELECT V.REG_MUNICIPAL,  V.PLACA, V.N_UNIDAD, C.NOMBRE_APELLIDO_CON
         FROM VEHICULOS V, CONDUCTORES C
         WHERE C.ID_CON = V.ID_CON
         ORDER BY V.N_UNIDAD, V.REG_MUNICIPAL, V.PLACA ASC";

consulta($consultaSql);

$resulset = variasFilas();

$salida = "{\"unidades\": [";

for ($i = 0; $i < count($resulset); $i++) {
    $fila = $resulset[$i];
    $salida .= "{
            \"id\":\"" . $fila["N_UNIDAD"] . "\",
            \"name\":\"". $fila["REG_MUNICIPAL"] . " - " . $fila["PLACA"]
    . " - " . $fila["N_UNIDAD"] . " - " . utf8_encode ( $fila["NOMBRE_APELLIDO_CON"] ) . "\"
        }";
    if ($i != count($resulset) - 1) {
        $salida .= ",";
    }
}

$salida .="]}";


echo $salida;
?>
