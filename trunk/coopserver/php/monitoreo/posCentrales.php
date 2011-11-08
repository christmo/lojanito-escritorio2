<?php

//session_start();
//include('../login/isLogin.php');
require_once('../../dll/conect.php');

$idEmpresa = $_SESSION["empresa"];
$fin = "%";

if ($idEmpresa == 'KRADAC' || $idEmpresa== 'ML') {
    $consultaSql = " SELECT ID_EMPRESA, LATITUD, LONGITUD, DIRECCION, TELEFONO
        FROM CENTRALES ";
    $fin = "#";
} else {
    $consultaSql = " SELECT ID_EMPRESA, LATITUD, LONGITUD, DIRECCION, TELEFONO
    FROM CENTRALES WHERE ID_EMPRESA = '$idEmpresa'";
}

consulta($consultaSql);
$resulset = variasFilas();

$salida = "";

for ($i = 0; $i < count($resulset); $i++) {
    $fila = $resulset[$i];
    $salida .= $fila["ID_EMPRESA"] . "%" .
            $fila["LATITUD"] . "%" .
            $fila["LONGITUD"] . "%" .
            utf8_encode ( $fila["DIRECCION"] ) . "%" .
            $fila["TELEFONO"].$fin;
}
echo $salida;
?>
