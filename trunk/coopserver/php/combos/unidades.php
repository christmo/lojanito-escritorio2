<?php

session_start();
require_once('../../dll/conect.php');

$salida = "{failure:true}";

if ($_SESSION['empresa'] != "") {
    if ($_SESSION['empresa'] == 'ML' || $_SESSION['empresa'] == 'KRADAC') {
        $consultaSql = "
            SELECT V.N_UNIDAD, V.ID_EMPRESA, C.NOMBRE_APELLIDO_CON, V.PLACA
            FROM VEHICULOS V, CONDUCTORES C
            WHERE C.ID_CON = V.ID_CON
            ORDER BY V.ID_EMPRESA, V.N_UNIDAD ASC
            "
        ;
    }else{
        $consultaSql = "
            SELECT V.N_UNIDAD, V.ID_EMPRESA, C.NOMBRE_APELLIDO_CON, V.PLACA
            FROM VEHICULOS V, CONDUCTORES C
            WHERE V.ID_EMPRESA = '" . $_SESSION['empresa'] . "' AND C.ID_CON = V.ID_CON
            ORDER BY V.ID_EMPRESA,  V.N_UNIDAD ASC
            "
        ;
    }

    echo $consultaSql;
    

    consulta($consultaSql);
    $resulset = variasFilas();

    $salida = "{\"unidades\": [";

    for ($i = 0; $i < count($resulset); $i++) {
        $fila = $resulset[$i];
        $salida .= "{
            \"id\":\"" .$fila["ID_EMPRESA"]. $fila["N_UNIDAD"] . "\",
            \"name\":\"" . $fila["N_UNIDAD"] . " - " .$fila["ID_EMPRESA"]." - " 
        . $fila["PLACA"] ." - " . utf8_encode ( $fila["NOMBRE_APELLIDO_CON"] ) . "\"
        }";
        if ($i != count($resulset) - 1) {
            $salida .= ",";
        }
    }

    $salida .="]}";
}

echo $salida;
?>
