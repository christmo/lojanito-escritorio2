<?php

require_once('../../dll/conect.php');
session_start();
cerrarConexion();
session_destroy();

echo '<script type="text/javascript">';
echo "window.location='../../index.php';";
echo '</script>';
?>

