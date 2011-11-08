<?php

/*
 * GENERA LA ESTRUCTURA JSON PARA
 * LA CARGA DE LOS VEHICULOS
 * EN EL PANEL IZQUIERDO
 * Author : qmarqeva
 */

require_once('../../dll/conect.php');

$consultaSql = "SELECT
VEH.N_UNIDAD, CON.NOMBRE_APELLIDO_CON
FROM
  CONDUCTORES CON,
  VEHICULOS VEH
WHERE CON.ID_CON = VEH.ID_CON
ORDER BY N_UNIDAD;";

consulta($consultaSql);
$resulset = variasFilas();

$salida = "[ ";

for ($i = 0; $i < count($resulset); $i++) {

    if ($i > 0) {
        $salida .= ',';
    }
    $fila = $resulset[$i];
    $salida.= '{"children":[],"text":"' . $fila["N_UNIDAD"] . '    |    ' . utf8_encode ( $fila["NOMBRE_APELLIDO_CON"] )
            . '","iconCls":"cars","id":' . $fila["N_UNIDAD"] . ',"leaf":true}';
}

$salida .= ']';

echo $salida;



//echo '
//[
//{
//	"text":"Cooperativa A",
//	"children":[
//		{"text":"58    |    SEGUNDO ROSENDINO MACAS CHALCO","iconCls":"cars","id":58,"leaf":true},
//		{"text":"59    |    ANGEL SALVADOR SARITAMA JIMENEZ","iconCls":"cars","id":59,"leaf":true},
//		{"text":"60    |    FRANKLIN MARCELO ERAZO SALGADO","iconCls":"cars","id":60,"leaf":true}
//	]
//},
//{
//	"text":"Cooperativa B",
//	"children":[
//		{ "text":"56", "children":[ {"text": "1104065612","children":[]},{"text": "4578","children":[]} ] },
//		{ "text":"45", "children":[ {"text": "1104065612","children":[]},{"text": "4569","children":[]} ] },
//		{ "text":"12", "children":[ {"text": "1104065612","children":[]},{"text": "1265","children":[]} ] },
//		{ "text":"78", "children":[ {"text": "1104065612","children":[]},{"text": "3026","children":[]} ] },
//		{ "text":"99", "children":[ {"text": "1104065612","children":[]},{"text": "1489","children":[]} ] },
//	]
//}
//]
//'

?>
