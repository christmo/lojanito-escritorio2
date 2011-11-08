<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Rastreo Satelital</title>
        <link rel="shortcut icon" href="img/taxi.png" type="image/x-icon" />
            <!--ExtJS-->
            <link rel="stylesheet" type="text/css" href="css/resources/css/ext-all.css"/>

            <script type="text/javascript" src="js/ext-js/adapter/ext/ext-base.js"></script>
            <script type="text/javascript" src="js/ext-js/ext-all.js"></script>

            <!-- overrides to base library -->
            <!-- extensions -->
            <script language="javascript" type="text/javascript" src="js/monitoreo/ux/CenterLayout.js"></script>
            <script language="javascript" type="text/javascript" src="js/monitoreo/ux/RowLayout.js"></script>

            <!-- page specific -->
            <script language="javascript" type="text/javascript" src="js/monitoreo/layout/layouts/basic.js"></script>
            <script language="javascript" type="text/javascript" src="js/monitoreo/layout/layout-browser.js"></script>

            <!--OpenLayers-->
            <link rel="stylesheet" type="text/css" href="js/OpenLayer/theme/default/style.css"/>
            <script language="javascript" type="text/javascript" src="js/OpenLayer/lib/OpenLayers.js"></script>

            <!--OpenStreetMap-->
            <script language="javascript" type="text/javascript" src="js/OpenStreetMap/OpenStreetMap.js"></script>

            <!--JQuery-->
            <script language="javascript" type="text/javascript" src="js/jquery/jquery-1.4.2.min.js"></script>

            <!--Maquetacion-->
            <link rel="stylesheet" type="text/css" href="css/style.css"/>
            <link rel="stylesheet" type="text/css" href="css/estados.css"/>
            <link rel="stylesheet" type="text/css" href="js/monitoreo/ux/css/CenterLayout.css" />
            <link rel="stylesheet" type="text/css" href="js/monitoreo/layout/layout-browser.css"/>

            <!-- spinner -->
            <link rel="stylesheet" type="text/css" href="js/monitoreo/ux/spinner/Spinner.css"/>
            <script language="javascript" type="text/javascript" src="js/monitoreo/ux/spinner/Spinner.js"></script>
            <script language="javascript" type="text/javascript" src="js/monitoreo/ux/spinner/SpinnerStrategy.js"></script>

            <!--SOFTWARE-->
            <script language="javascript" type="text/javascript" src="js/map_kradac.js"></script>
            <script language="javascript" type="text/javascript" src="js/monitoreo/reportes/recorridos_win.js"></script>

            <script language="javascript" type="text/javascript" src="js/monitoreo/reportes/recorridoTrazado.js"></script>
            <script language="javascript" type="text/javascript" src="js/monitoreo/reportes/simbologia.js"></script>

            <script language="javascript" type="text/javascript" src="js/monitoreo/reportes/localizar_vh.js"></script>
            <script language="javascript" type="text/javascript" src="js/monitoreo/reportes/win_general_repor.js"></script>





    </head>

    <body onload="init()">

        <div id="header"></div>
        <div style="position: absolute; bottom: 0px; right: 0px;" >
            <a href='http://www.kradac.com'>
                <img alt="www.kradac.com"  src='img/credits.png'/>
            </a>
        </div>
    </body>
</html>
