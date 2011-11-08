
var controls;
var map;
var selectControl;
var clientesSelectControl;

// coordenadas para centrar Loja
var lat = - 3.9912;
var lon = - 79.20733;
var zoom = 13;

var id = 1;
var vectorLayerClientes;

var lienzoRecorridos;

//Lienzos por Cooperativa
var vectorCV;

var lienzoCentral;

var coop = new Array();

var distanciaKM;

function init(){

    var extent = new OpenLayers.Bounds();
    extent.extend(new OpenLayers.LonLat(-81.29405,1.49170  ));
    extent.extend(new OpenLayers.LonLat(-75.47129,-4.62250));
    extent.transform( new OpenLayers.Projection( "EPSG:4326" ),
        new OpenLayers.Projection( "EPSG:900913" ));



    // Mapa
    map = new OpenLayers.Map( "map", {
        controls : [
        new OpenLayers.Control.Navigation(), 
        new OpenLayers.Control.MousePosition(),
        new OpenLayers.Control.PanZoomBar(),
        new OpenLayers.Control.KeyboardDefaults(),
        new OpenLayers.Control.LayerSwitcher(),
        ],
        restrictedExtent : extent,
        displayProjection : new OpenLayers.Projection( "EPSG:4326" ),
        projection : new OpenLayers.Projection( "EPSG:4326" ),
        units : 'm',
        numZoomLevels : 19,
        maxResolution : 'auto'
    }
    );

    /* ESTILOS DE CAPAS*/
    
    //Estilo para la Central
    var centralStyle = new OpenLayers.StyleMap( {
        externalGraphic : "img/central.png",
        graphicWidth : 25,
        graphicHeight : 25,
        fillOpacity : 0.85,
        id_central : "${n_central}",
        fono : "${fono}",
        label : " ${n_central}",
        fontColor: "${favColor}",
        fontSize: "12px",
        fontFamily: "Courier New, monospace",
        fontWeight: "bold",
        labelAlign: "${align}",
        labelOffset: new OpenLayers.Pixel(0,-20)
    }
    );
    // Estilo de los Taxis
    var styleLienzo = new OpenLayers.StyleMap( {
        //externalGraphic : "img/taxi.png",
        externalGraphic : "${img}",
        graphicWidth : 25,
        graphicHeight : 25,
        fillOpacity : 0.85,
        id_taxi : "${taxi}",
        velocidad : "${velo}",
        fecha : "${fecha}",
        hora : "${hora}",
        label : "..${taxi}",
        fontColor: "${favColor}",
        fontSize: "12px",
        fontFamily: "Courier New, monospace",
        fontWeight: "bold",
        labelAlign: "${align}",
        labelOffset: new OpenLayers.Pixel(0,-20)
    }
    );

    stylePuntos = new OpenLayers.StyleMap( {
        fillOpacity : 0.7,
        pointRadius: 8,
        id_estado : "${id_estado}",
        fecha : "${fecha}",
        hora : "${hora}",
        label : " ${id_estado}",
        estTax : "${estTax}",
        estTaxM : "${estTaxM}",
        otroEstado : "${otroEstado}",
        soft : "${soft}",
        fono : "${fono}",
        fontColor: "white",
        fontSize: "12px",
        fontFamily: "Courier New, monospace",
        fontWeight: "bold"
    }
    );

    var styleClientes = new OpenLayers.StyleMap( {

        externalGraphic : "img/cliente.png",
        graphicWidth : 25,
        graphicHeight : 25,
        taxi : "${taxi}",
        cliente : "${cliente}",
        fono : "${fono}",
        nombre: "${nombre}",
        barrio: "${barrio}",
        fecha : "${fecha}",
        hora : "${hora}",
        label : "${cliente}__.",
        fontColor: "${favColor}",
        fontSize: "12px",
        fontFamily: "Courier New, monospace",
        fontWeight: "bold",
        labelAlign: "rt",
        labelOffset: new OpenLayers.Pixel(15,-30)
    }
    );


    /* CREAR LIENZOS */
    
    lienzoCentral = new OpenLayers.Layer.Vector("Call Center", {
        styleMap : centralStyle
    }
    );
    vectorCV = new OpenLayers.Layer.Vector( "Taxis", {
        styleMap : styleLienzo
    }
    );
    vectorCV.id = 'CVLayer';

    lienzoRecorridos = new OpenLayers.Layer.Vector("Recorridos");
    lienzoPuntos = new OpenLayers.Layer.Vector('Points', {
        styleMap: stylePuntos
    });
    markerInicioFin = new OpenLayers.Layer.Markers( "Inicio-Fin" );
    markerEdificios = new OpenLayers.Layer.Markers( "KRADAC" );
    vectorLayerClientes = new OpenLayers.Layer.Vector( "Clientes", {
        styleMap : styleClientes
    }
    );


    /* AÑADIR CAPAS */
  
    map.addLayer( new OpenLayers.Layer.OSM.Mapnik( "Loja Map" ) );    
    map.addLayer(lienzoCentral);
    map.addLayer( vectorCV );
    map.addLayer(lienzoRecorridos);
    map.addLayer(lienzoPuntos);
    map.addLayer(markerInicioFin);
    map.addLayer(markerEdificios);
    map.addLayer(vectorLayerClientes);

    /* AÑADIR Y ACTIVAR EVENTOS */
    
    coop.push('CV');


    selectFeatures = new OpenLayers.Control.SelectFeature(
        [ lienzoPuntos, lienzoCentral , vectorCV, vectorLayerClientes ],
        {
            clickout: true,
            toggle: false,
            multiple: false,
            hover : false,
            onSelect : function(feature){

                var cant = lienzoCentral.selectedFeatures;
                var lPoint = lienzoPuntos.selectedFeatures;
                var clientSelec = vectorLayerClientes.selectedFeatures;
                
                if (cant.length>0) {
                    selectCentral( feature );
                }else{
                    if (lPoint.length>0) {
                        onPuntoSelect(feature );
                    }else{
                        if (clientSelec.length>0) {
                            onSelectCliente(feature);
                        }else{
                            onVehiculoSelect( feature );
                        }
                    }    
                }

            },
            onUnselect : function(feature){

                var cant = lienzoCentral.selectedFeatures;
                var iden = feature.attributes.id_estado;                
                
                if (iden != null) {
                    onPuntoUnselect(feature );
                }else{
                    if (cant.length>0) {
                        unselectCentral( feature );
                    }else {
                        onVehiculoUnselect( feature );
                        onClienteUnselect(feature);
                    }
                }                               
            }
        }
        );

    map.addControl( selectFeatures );
    selectFeatures.activate();

    /* OTRAS FUNCIONES */
    var lonLat = new OpenLayers.LonLat( lon, lat ).transform(
        new OpenLayers.Projection( "EPSG:4326" ),
        map.getProjectionObject() );
    map.setCenter ( lonLat, zoom );



    edificios();
    getPosCentral();
    graficarCoop(coop);

    map.events.register('click', map, function (e) {
        var coord = map.getLonLatFromViewPortPx(e.xy);
        var aux =  new OpenLayers.Geometry.Point( coord.lon, coord.lat );
        aux.transform( new OpenLayers.Projection( "EPSG:900913" ),new OpenLayers.Projection( "EPSG:4326" ) );
        setCoordenadasClienteEscritorio(aux.x, aux.y);
        Event.stop(e);
    });


    map.events.register('zoomend', this, function() {
        if (map.getZoom() < 7)
        {
            map.zoomTo(7);
        }
    });

    graficarClientes();

}

//Verifica si existe ese cliente
function existe(idC){
    //Buscar si existe
    for(var i=0; i< puntosGraficados.length; i++) {
        if (puntosGraficados[i]==idC){
            return true;
            break;
        }
    }
    return false;
}

function capaClientesActiva(){
    return vectorLayerClientes.getVisibility();
}

function graficarClientes(){

    if (capaClientesActiva()) {
        getClientes();
    }

    var elementos = vectorLayerClientes.features;
    var i = 0;
    for(i=0; i<elementos.length; i++){
        var info =  elementos[i];
        if (!existe(info.id)){
            info.destroy();
            if (map.popups.length == 1) {
                map.removePopup(map.popups[0]);
            }
        }
    }

    // Volvemos a comprobar datos a los 5 segundos
    setTimeout( function(){
        graficarClientes()
    }
    , 3000 );
}

function getClientes(){
    $.ajax( {
        url : 'php/clientes/datosClienteLatLon.php',
        type : "POST",
        async : true,
        success : function( datos ){
            graficarClientesProceso(datos);
        }
    }
    );
}

function graficarClientesProceso(datos){ 

    var filas = datos.split( "#" );

    // Se concatena al final dos signos raros
    // por eso resto uno ( 1 )
    puntosGraficados =  new Array();
    for ( var i = 0;  i < filas.length - 1; i ++ ){
        // Extraigo columnas
        var columnas = filas[i].split( "%" );

        //id == codigo cliente + num taxi

        var id = columnas[0] +"-"+columnas[1];
        puntosGraficados.push(id);

        var clienteFeature = vectorLayerClientes.getFeatureById( id );

        //CREA UN NUEVO ELEMENTO
        if ( clienteFeature == null ){

            // Coordenadas
            var x = columnas[2];
            var y = columnas[3];

            // Posicion lon : lat
            var point = new OpenLayers.Geometry.Point( x, y );

            // Transformacion de coordendas
            point.transform( new OpenLayers.Projection( "EPSG:4326" ),
                new OpenLayers.Projection( "EPSG:900913" ) );

            clienteFeature = new OpenLayers.Feature.Vector( point, {

                taxi : columnas[1],
                cliente : columnas[0],
                fecha : columnas[4],
                hora : columnas[5],
                nombre: columnas[6],
                barrio: columnas[7],
                fono: columnas[8],
                favColor : 'green',
                poppedup : false
            }
            );

            // Se coloca el ID de veh�culo a la imagen
            clienteFeature.id = columnas[0] +"-"+columnas[1];

            //Se añade a la capa que corresponda
            vectorLayerClientes.addFeatures( [clienteFeature] );
        }
    }
}

function setCoordenadasClienteEscritorio(lon,lat){
    var coor = null;

    $.ajax( {
        url : 'php/escritorio/escritorioGPS.php?lon=' +  lon  + '&lat=' + lat,
        type : "GET",
        async : true
    }
    );
    return coor;
}

//Distancia entre dos puntos
function distancia (lon1, lat1, lon2,lat2){
    var punto1 = new OpenLayers.LonLat( lon1, lat1 );
    var punto2 = new OpenLayers.LonLat( lon2, lat2 );
    var distanciaKM =  OpenLayers.Util.distVincenty ( punto1, punto2 );
    return distanciaKM;
}

//Extrae todos los puntos para el recorridos histórico
function getCoordenadasVehiculo(numUni, feIni, feFin, hIni, hFin){
    var coor = null;

    $.ajax( {
        url : 'php/monitoreo/extraerRuta.php?UNIDAD=' +  numUni  + 
        '&FECHAINI=' + feIni + '&FECHAFIN=' + feFin + '&HORAINI='
        + hIni + '&HORAFIN=' + hFin,
        type : "GET",
        async : false,
        success : function( datos ){
            coor = datos;
        }
    }
    );
    return coor;
}

//Extrae las coodenadas de una cooperativa desde la BD
function coordenadas(j){

    $.ajax( {
        url : 'php/monitoreo/ultimosGPS.php',
        type : "GET",
        async : true,
        success : function( datos ){
            graficarVehiculos2(datos,j);
        }
    }
    );
}

//Verifica si la capa de la cooperativa está activa
function estaActivo(idCoop){

    if (idCoop == 'CV'){
        return vectorCV.getVisibility();
    }
    return false;
}

//Busca por cada una de las Cooperativas
// y grafica las unidades
function graficarCoop(coop){

    for ( var j = 0;  j < coop.length; j ++ ){
	
        var coopN = coop[j];
        if (estaActivo(coopN)) {            
            coordenadas(j);
        }	
    }

    // Volvemos a comprobar datos a los 5 segundos
    setTimeout( function(){
        graficarCoop( coop )
    }
    , 5000 );
}

//Recuperar la posición de una central
function getPosCentral(){
    $.ajax( {
        url : 'php/monitoreo/posCentrales.php',
        type : "GET",
        async : true,
        success : function( datos ){
            separarFilas(datos);
        }
    }
    );
}

//Grafica los vehiculos luego de consultar a la BD
function graficarVehiculos2(cordGrap,j){

    var filas = cordGrap.split( "#" );

    // Se concatena al final dos signos raros
    // por eso resto uno ( 1 )
    for ( var i = 0;  i < filas.length - 1; i ++ ){
        // Extraigo columnas
        var columnas = filas[i].split( "%" );


        //var idtaxiBD = cName + columnas[0];
        var idtaxiBD = "T" + columnas[0];

        var taxiFeature = null;

        //Extracción dependiendo del Layer
        switch (j)
        {
            case 0:
                taxiFeature = vectorCV.getFeatureById( idtaxiBD );
                break;
        }

        //CREA UN NUEVO ELEMENTO PARA EL TAXI PORQUE NO EXISTE
        if ( taxiFeature == null ){

            // Coordenadas
            var x = columnas[1];
            var y = columnas[2];

            // Posici�n lon : lat
            var point = new OpenLayers.Geometry.Point( x, y );

            // Transformaci�n de coordendas
            point.transform( new OpenLayers.Projection( "EPSG:4326" ),
                new OpenLayers.Projection( "EPSG:900913" ) );

            var imgName = "img/tx4.png";
            if (columnas[6] == 'ASI') {
                imgName = "img/tx2.png";
            }else if (columnas[6] == 'OCU') {
                imgName = "img/tx3.png";
            }else if (columnas[6] == 'AC') {
                imgName = "img/taxi.png";
            }

            taxiFeature = new OpenLayers.Feature.Vector( point, {
                img: imgName,
                taxi : columnas[0],
                velo : columnas[3],
                fecha : columnas[4],
                hora : columnas[5],
                favColor : 'blue',
                align: "lt",
                poppedup : false
            }
            );

            // Se coloca el ID de veh�culo a la imagen
            taxiFeature.id = "T" + columnas[0];

            //Se añade a la capa que corresponda
            switch (j)
            {
                case 0:
                    vectorCV.addFeatures( [taxiFeature] );
                    break;
            }
        }else{

            // Comprobar si los datos graficados estan desactualizados
            if ( taxiFeature.attributes.fecha != columnas[4]
                || taxiFeature.attributes.hora != columnas[5] ){

                var poppedup
                if (taxiFeature == null){
                    poppedup = false;
                }else{
                    poppedup = taxiFeature.attributes.poppedup;
                }

                //var

                if ( poppedup == true ) {
                    selectFeatures.unselect( taxiFeature );
                }
                // Coordenadas
                x = columnas[1];
                y = columnas[2];

                // Nuevo punto
                var newPoint = new OpenLayers.LonLat( x, y );
                newPoint.transform( new OpenLayers.Projection( "EPSG:4326" ),
                    new OpenLayers.Projection( "EPSG:900913" ) );

                // Movemos el vehiculo
                taxiFeature.move( newPoint );

                imgName = "img/tx4.png";
                if (columnas[6] == 'ASI') {
                    imgName = "img/tx2.png";
                }else if (columnas[6] == 'OCU') {
                    imgName = "img/tx3.png";
                }else if (columnas[6] == 'AC') {
                    imgName = "img/taxi.png";
                }

                taxiFeature.attributes.img = imgName;

                // Actualizamos Fecha y Hora
                taxiFeature.attributes.fecha = columnas[4];
                taxiFeature.attributes.hora = columnas[5];

                //redibujado de lienzo
                vectorCV.redraw(true);

                if ( poppedup == true ) {
                    selectFeatures.select( taxiFeature );
                }
            }
        } //FIN DE ELSE DEL OBJETO NULO
    }
}

function colocarCentral(data){
    var dat = data.split("%");

    //lon - lat
    var punto = new OpenLayers.Geometry.Point( dat[2], dat[1]);

    // Creaci�n del punto
    // Transformaci�n de coordendas
    punto.transform( new OpenLayers.Projection( "EPSG:4326" ),
        new OpenLayers.Projection( "EPSG:900913" ) );

    var pointFeature = new OpenLayers.Feature.Vector( punto, {
        n_central : "call center " + dat[0],
        fono : dat[4],
        poppedup : false,
        favColor: 'blue',
        align: "lt"
    }
    );

    // Se coloca el ID de la central
    pointFeature.id = "central"+dat[0];

    // Anadir  central al mapa
    lienzoCentral.addFeatures( [pointFeature] );
}

function separarFilas(data){
    var fil = data.split("#");
    colocarCentral(fil[0] + "%");
}

function lienzosRecorridoHistorico(idVehCoop, coordPuntos){

    //buscar DIV y colocar identificador
    var idVehiculoR = document.getElementById("idVeh");
    idVehiculoR.innerHTML = idVehCoop;

    lienzoPuntos.destroyFeatures();

    var features = new Array();

    //Recuperar posiciones del recorrido
    var fil = coordPuntos.split("#");
    var cantPuntos = 0;

    //PUNTOS PARA RUTA
    var puntosRuta = new Array();

    //punto Inicial y Final

    var size = new OpenLayers.Size(32, 32);
    var iconIni = new OpenLayers.Icon(
        'img/inicio.png',
        size, null, 0);

    var iconFin = new OpenLayers.Icon(
        'img/fin.png',
        size, null, 0);

    var filIni = fil[0].split("%");

    markerInicioFin.clearMarkers();

    var pInicio = new OpenLayers.LonLat(filIni[0],filIni[1]);
    pInicio.transform(new OpenLayers.Projection( "EPSG:4326" ),
        new OpenLayers.Projection( "EPSG:900913" ) );
    markerInicioFin.addMarker(new OpenLayers.Marker(pInicio, iconIni));

    var filFin = fil[fil.length-2].split("%");

    var pFin = new OpenLayers.LonLat(filFin[0],filFin[1]);
    pFin.transform(new OpenLayers.Projection( "EPSG:4326" ),
        new OpenLayers.Projection( "EPSG:900913" ) );
    markerInicioFin.addMarker(new OpenLayers.Marker(pFin, iconFin));

    var lonIni;
    var latIni;
    var lonFin;
    var latFin;
    distanciaKM = 0;
    for ( i=0; i<fil.length-1; i++ ) {

        var col = fil[i].split("%");
        var pt = new OpenLayers.Geometry.Point(col[0],col[1]);
        pt.transform( new OpenLayers.Projection( "EPSG:4326" ),
            new OpenLayers.Projection( "EPSG:900913" ) );

        //Cargar punto de inicio
        if (i==0) {
            lonIni = col[0];
            latIni = col[1];
        }else{
            var tramo = distancia(lonIni, latIni, col[0], col[1]);
            distanciaKM += tramo;
            lonIni = col[0];
            latIni = col[1];
        }


        // SEPARAR TELEFONO DEL ESTADO SEPARADOR ==> ||
        var dat = col[7].split("||");

        var std = dat[0];
        var tlf = dat[1];

        puntosRuta.push(pt);

        var puntoMap = new OpenLayers.Feature.Vector( pt, {
            id_estado : i,
            fecha : col[2],
            hora : col[3],

            //estTax : col[4],
            //estTaxM : col[5],
            estTax : 'LIBRE',
            estTaxM : 'APAGADO',

            otroEstado : col[6],
            soft : std,
            fono : tlf,
            poppedup : false
        }
        );

        puntoMap.id = i;


        //Aqui debería colocar el estado del taxi
        //para hacer la relación con el color
        //puntoMap.state = col[7];
        if (std.length >= 10){
            puntoMap.state = "DOSESTADOS";
        }else if(std.length == 3){
            puntoMap.state = "SINESTADO";
        }else{
            puntoMap.state = std;
        }

        features.push(puntoMap);

        cantPuntos++;
    }

    if (puntosRuta.length > 0){
        var ruta = new OpenLayers.Geometry.LineString(puntosRuta);
        //Estilo de Linea de Recorrido
        var style = {
            strokeColor: '#0000ff',
            strokeOpacity: 0.3,
            strokeWidth: 5
        };

        var lineFeature = lienzoRecorridos.getFeatureById( "trazado" );
        if (lineFeature != null){
            lineFeature.destroy();
        }

        lineFeature = new OpenLayers.Feature.Vector(ruta, null, style);
        lineFeature.id = "trazado";
        lienzoRecorridos.addFeatures([lineFeature]);

        var context = function(feature) {
            return feature;
        }
        var lookup = {};

        lookup["OCUPADO"] = {
            fillColor: "#734D26"
        };
        lookup["ASIGNADO"] = {
            fillColor: "purple"
        };
        lookup["LIBRE"] = {
            fillColor: "green"
        };
        lookup["APAGADO"] = {
            fillColor: "black"
        };
        lookup["DOSESTADOS"] = {
            fillColor: "red"
        };
        lookup["SINESTADO"] = {
            fillColor: "blue"
        };
        stylePuntos.addUniqueValueRules("default", "state", lookup, context);

        lienzoPuntos.addFeatures(features);

    }else{
        alert ("NO HAY REGISTROS ENTRE ESA FECHA Y HORA");
    }
}

function edificios(){
    var size = new OpenLayers.Size(32, 41);
    var calculateOffset = function(size) {
        return new OpenLayers.Pixel(-(size.w/2), -size.h);
    };

    var iconMun = new OpenLayers.Icon(
        'img/muni.png',
        size, null, calculateOffset);

    var iconTal = new OpenLayers.Icon(
        'img/taller.png',
        size, null, calculateOffset);

    markerEdificios.clearMarkers();

    var pMun = new OpenLayers.LonLat(-79.20276,-4.01231);
    var pTal = new OpenLayers.LonLat(-79.20593,-4.03841);

    pMun.transform(new OpenLayers.Projection( "EPSG:4326" ),
        new OpenLayers.Projection( "EPSG:900913" ) );
    pTal.transform(new OpenLayers.Projection( "EPSG:4326" ),
        new OpenLayers.Projection( "EPSG:900913" ) );

    markerEdificios.addMarker(new OpenLayers.Marker(pTal, iconTal));
    markerEdificios.addMarker(new OpenLayers.Marker(pMun, iconMun));
}

function buscarVehiculo(numVeh){
    
    if (vectorCV == null){
        Ext.MessageBox.show({
            title: 'Error...',
            msg: 'Par&aacute;metros no v&aacute;lidos',
            buttons: Ext.MessageBox.OK,
            icon: Ext.MessageBox.ERROR
        });
        return null;
    }else{
        if (vectorCV.getVisibility()){
            var vehiculo = vectorCV.getFeatureById( numVeh );
            
            if (vehiculo == null){
                Ext.MessageBox.show({
                    title: 'Error...',
                    msg: 'Veh&iacute;culo no encontrado',
                    buttons: Ext.MessageBox.OK,
                    icon: Ext.MessageBox.ERROR
                });
                return null;
            }else{
                //onFeatureSelect(vehiculo); //Activar Globo
                centrarMapa(vehiculo.geometry.x,vehiculo.geometry.y);
            }
        }else{
            Ext.MessageBox.show({
                title: 'Capa Desactivada',
                msg: 'Debe activar primero la capa de la Cooperativa<br>en la parte deracha (+)',
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.ERROR
            });
            return null;
        }
    }
}

function centrarMapa(ln, lt){
    //zoom max = 18
    var nivelZoom = 17;
    var lonlatCenter = new OpenLayers.LonLat(ln,lt);
    map.setCenter ( lonlatCenter, nivelZoom );
}

/*********** ACCIONES DE FEATURES ***********/

//Acciones de los Puntos de Recorridos
function onPopupPuntoClose( feature ) {
    //Ocultar Información
    var div1 = document.getElementById("infor");
    div1.style.display = "none";
}

function onPuntoUnselect( feature ) {
    //Ocultar la tabla de Información
    var div1 = document.getElementById("infor");
    div1.style.display = "none";
}

function onPuntoSelect( feature ) {

    var id = feature.id;
    //Cargar contenido en la tabla
    var factor = [-1,0,1];
    var i;

    for (i=0; i<3; i++){        
        var idP = id+factor[i];
        var puntoInfo = lienzoPuntos.getFeatureById(idP);

        var fecP = "";
        var horP = "";
        var estP = "";
        var esTxP = "";
        var otroP = "";
        var softP = "";
        var fonoP = "";
        if (puntoInfo!=null){
            fecP = puntoInfo.attributes.fecha;
            horP = puntoInfo.attributes.hora;
            estP = puntoInfo.attributes.estTax;
            esTxP = puntoInfo.attributes.estTaxM;
            otroP = puntoInfo.attributes.otroEstado;
            softP = puntoInfo.attributes.soft;
            fonoP = puntoInfo.attributes.fono;
        }else{
            idP = "";
        }

        //Añadirlos a la tabla
        var idPos = i + 1;
        
        var numX = document.getElementById("num"+idPos);
        numX.innerHTML = idP;

        var fechaX = document.getElementById("fecha"+idPos);
        fechaX.innerHTML = fecP;

        var horaX = document.getElementById("hora"+idPos);
        horaX.innerHTML = horP;

        var estX = document.getElementById("est"+idPos);
        estX.innerHTML = estP;

        var estxX = document.getElementById("estx"+idPos);
        estxX.innerHTML = esTxP;

        var otrX = document.getElementById("otr"+idPos);
        otrX.innerHTML = otroP;

        var softX = document.getElementById("soft"+idPos);
        softX.innerHTML = softP;

        var fonoX = document.getElementById("fono"+idPos);
        fonoX.innerHTML = fonoP;

        //Cargar Distancia
        var distX = document.getElementById("distKM");
        distX.innerHTML =   distanciaKM;
    }



    //Visualizar la tabla de Información
    var div1 = document.getElementById("infor");
    div1.style.display = "";
}

//Acciones Vehiculos
function onVehiculoSelect( feature ) {

    var idtaxi = feature.attributes.taxi;
    var fec = feature.attributes.fecha;
    var hor = feature.attributes.hora;

    var p1 = new OpenLayers.Geometry.Point(feature.geometry.x , feature.geometry.y);

    // Transformaci�n de coordendas
    p1.transform( new OpenLayers.Projection( "EPSG:900913" ),
        new OpenLayers.Projection( "EPSG:4326" ) );

    var popup = new OpenLayers.Popup.FramedCloud( null,
        new OpenLayers.LonLat( feature.geometry.x, feature.geometry.y ),
        null,
        "<div style='font-size:.8em'><br>Num Unidad: [  <b>"
        + idtaxi + "</b>  ] <BR>" + hor + "    " + fec +  "</div>",
        null, true,  function () {
            onVehiculoClose( feature )
        }
        );
    feature.popup = popup;
    feature.attributes.poppedup = true;
    map.addPopup( popup );
}

function onVehiculoClose( feature ) {
    selectFeatures.unselect( feature );
}

function onVehiculoUnselect( feature ) {
    map.removePopup( feature.popup );
    feature.popup.destroy();
    feature.attributes.poppedup = false;
    feature.popup = null;
}

//Acciones de la Central
function selectCentral( feature ) {
    //selectedFeature = feature;

    var idcentral = feature.attributes.n_central;
    var fono = feature.attributes.fono;

    var popup = new OpenLayers.Popup.FramedCloud( null,
        new OpenLayers.LonLat( feature.geometry.x, feature.geometry.y ),
        null,
        "<div style='font-size:.8em'><br>Central : [" + idcentral +
        "] <BR>Telefono: ["+ fono + "]</div>",
        null, true,  function () {
            onCloseCentral( feature )
        }
        );
    feature.popup = popup;
    feature.attributes.poppedup = true;
    map.addPopup( popup );
}

function unselectCentral( feature ) {
    map.removePopup( feature.popup );
    feature.popup.destroy();
    feature.attributes.poppedup = false;
    feature.popup = null;
}

function onCloseCentral( feature ) {
    selectFeatures.unselect( feature );
}

//Acciones para los Clientes
function onCloseCliente(feature){
    selectFeatures.unselect( feature );
    var div1 = document.getElementById("sugerencia");
    div1.style.display = "none";
}

function onClienteUnselect( feature ) {
    //Ocultar la tabla de Información
    var div1 = document.getElementById("sugerencia");
    div1.style.display = "none";
}

function onSelectCliente(feature){

    var idTaxi = feature.attributes.taxi;
    var idClit = feature.attributes.cliente;
    var fecha = feature.attributes.fecha;
    var hora = feature.attributes.hora;
    var fono = feature.attributes.fono;
    var barrio = feature.attributes.barrio;
    var nombre = feature.attributes.nombre;

    var popupCliente = new OpenLayers.Popup.FramedCloud( null,
        new OpenLayers.LonLat( feature.geometry.x, feature.geometry.y ),
        null,
        "<div style='font-size:.8em'><b>Taxi : </b>" + idTaxi
        + "<br /><b>Cod : </b>" + idClit
        + "<br /><b>Fono : </b>" + fono
        + "<br /><b>Nomb : </b>" +  nombre
        + "<br /><b>Barr : </b>" +  barrio
        + "<br /><b>Fecha : </b>" + fecha
        + "<br /><b>Hora : </b>" +  hora + "</div>",
        null, true,  function () {
            onCloseCliente(feature);
        }
        );
    feature.popup = popupCliente;
    feature.attributes.poppedup = true;
    map.addPopup( popupCliente );
    
    cargarTablaVehiculosCercanos(feature);
}

/**
 * Carga la tabla de los vehiculos mas cercanos a un cliente
 */
function cargarTablaVehiculosCercanos(feature){
    var lonlat = new OpenLayers.LonLat( feature.geometry.x, feature.geometry.y ).transform( 
            new OpenLayers.Projection( "EPSG:900913" ),
            new OpenLayers.Projection( "EPSG:4326" )
        );
    Ext.Ajax.request({
        url: 'php/clientes/obtenerUnidadesCercanasCliente.php',
        method: 'POST',
        success: function (result) {
            var r = Ext.util.JSON.decode(result.responseText);
            if(typeof r.datos != "undefined"){
                for(var i=0;i<r.datos.length;i++){
                    document.getElementById("unidad"+(i+1)).innerHTML=r.datos[i].n_unidad;
                    document.getElementById("distancia"+(i+1)).innerHTML=r.datos[i].distancia;
                    document.getElementById("tiempo"+(i+1)).innerHTML=r.datos[i].tiempo;
                }
            }
        },
        timeout: 1000,
        params: {
            lat: lonlat.lat,
            lon: lonlat.lon
        }
    });
    
    //Visualizar la tabla de Información
    var div1 = document.getElementById("sugerencia");
    div1.style.display = "";
}