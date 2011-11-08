<?php

require_once('../../dll/conect.php');
extract($_POST);


if (isset($fechaIni)) {

}

$consultaSql =
        "
SELECT B.N_UNIDAD, B.FECHA_HORA, B.LATITUD,
B.LONGITUD, B.G2, (B.VELOCIDAD * 1.852) AS VELOCIDAD, B.G1
FROM
RECORRIDOS B
WHERE
B.ID BETWEEN REPLACE('$fechaIni','-','') AND REPLACE('$fechaFin','-','')
AND
B.FECHA_HORA BETWEEN '$fechaIni $horaIni' AND '$fechaFin $horaFin'
AND
  B.N_UNIDAD =  '$idVeh'
ORDER BY B.FECHA_HORA
";

consulta($consultaSql);
$resulset = variasFilas();

$salida = "";
$estTemporal = "S/E";


$salida = "{failure:true}";
$datos = "";

if (count($resulset) >= 1) {

    $estadosFormat = "";
    $estActual = "S/E";
    $fono = "S/T";

    for ($i = 0; $i < count($resulset); $i++) {

        $fila = $resulset[$i];
        $variosEstados = false;
        $estadosFormat = "";
        $estadosV = getVariosEstados($idVeh, $fila["FECHA_HORA"]);

        $rep = count($estadosV);

        //Es el primer registro y no tiene estado ¿?
        //empezar a buscar hacia atras.
        if ($i == 0 && $rep == 0) {
            $estAnterior = getUltimoEstadoSemana($fila["FECHA_HORA"], $idVeh);
            if ($estAnterior != null) {
                $estActual = $estAnterior;
            }
        }

        for ($j = 0; $j < count($estadosV); $j++) {
            $dat = $estadosV[$j];
            $estadosFormat .= $dat["ESTADO"];

            if ($j < ($rep - 1)) {
                $estadosFormat .= "<br/>";
            }

            $estActual = $dat["ESTADO"];
            $fono = $dat["FONO"];
        }

        $estadosFormat .= "||$fono";

        if ($rep == 0) {
            $estadosFormat = $estActual . "||$fono";
        }

        $G1 = $fila["G1"];
        $G2 = $fila["G2"];
        if ($G1 == null || $G1 == 'null' || $G1 == '') {
            $G1 = "OFF";
            $G2 = getValor($G2);
        } else {
            if ($G1 == 1) {
                $G1 = "ON";
                $G2 = getValor($G2);
            } else {
                $G1 = "OFF";
                $G2 = "LIBRE";
            }
        }

        $dataFechaHora = explode(" ", $fila["FECHA_HORA"]);

        $datos .= $fila["LONGITUD"] . "%" . $fila["LATITUD"] . "%" .
                $dataFechaHora[0] . "%" . $dataFechaHora[1] . "%" . $G2 . "%"
                . $G1 . "%" . $fila["VELOCIDAD"] . "%" . $estadosFormat . "#";
    }

    $salida = "{success:true,datos: { coordenadas: '$datos' }}";
}

echo $salida;

//Devuelve todos los estados que existan para
//un determinado vehiculo
//en un determinado tiempo
function getVariosEstados($nu, $fecha_hora) {

    $dataID = explode(" ", $fecha_hora);
    $dataID[0] = str_replace("-", "", $dataID[0]);


    $conSql = "SELECT ESTADO, FONO
               FROM  ASIGNADOS_LOCAL
               WHERE
               ID = $dataID[0]
               AND N_UNIDAD = $nu
               AND SUBSTRING(FECHA_HORA,1,16) = SUBSTRING('$fecha_hora',1,16)";
    consulta($conSql);
    $conjuntoEstados = variasFilas();
    return $conjuntoEstados;
}

//Comprobando en los últimos 7 días
function getUltimoEstado($fh, $n, $val) {

    $sql = "SELECT ESTADO FROM ASIGNADOS_LOCAL
            WHERE FECHA_HORA = (
            SELECT max(FECHA_HORA) FROM ASIGNADOS_LOCAL
            WHERE ID =  $fh
            AND N_UNIDAD = $n
            ) AND N_UNIDAD = $n LIMIT 1
    ";

   consulta($sql);

    $RstEst = unicaFila();
    $est = $RstEst["ESTADO"];
    return $est;
}

function getUltimoEstadoSemana($fh, $n) {

    $dataID = explode(" ", $fh);
    $dataID[0] = str_replace("-", "", $dataID[0]);

    for ($i = 0; $i < 7; $i++) {
        $dataID[0] = $dataID[0] - 0;

        $estado = getUltimoEstado($dataID[0], $n, $i);
        if ($estado != null) {
            return $estado;
            break;
        }
    }
    return null;
}

function getValor($val) {
    if ($val == null || $val == 'null' || $val == '') {
        $val = "LIBRE";
    } else {
        if ($val == 1) {
            $val = "LIBRE";
        } else {
            $val = "OCUPADO";
        }
    }
    return $val;
}

?>
