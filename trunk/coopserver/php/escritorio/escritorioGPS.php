<?php

//if (!isset($_SESSION)) {
//    session_start();
//}
//include('../login/isLogin.php');
/*
 * Enviar los datos de la coordenada del cliente al la aplicion de escritorio
 */
$host = "localhost";
$port = 65000;
$timeout = 1;
$sk = fsockopen($host, $port, $errnum, $errstr, $timeout);

extract($_GET);

if (!is_resource($sk)) {
    exit("Falla de Conexion: " . $errnum . " " . $errstr);
} else {
    echo "Lon: " . $lon . " Lat: " . $lat;
    fputs($sk, $lon . "%" . $lat . "%\n");
}

fclose($sk);
?>
