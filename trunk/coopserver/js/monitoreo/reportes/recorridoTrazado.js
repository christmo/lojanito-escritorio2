function estaticos(){
    var p1 = new OpenLayers.Geometry.Point( -105.205078125, 38.056640625);
    var p2 = new OpenLayers.Geometry.Point( -103.974609375, 24.345803125 );

    p1.transform( new OpenLayers.Projection( "EPSG:4326" ),new OpenLayers.Projection( "EPSG:900913" ) );
    p2.transform( new OpenLayers.Projection( "EPSG:4326" ),new OpenLayers.Projection( "EPSG:900913" ) );

    var punrut = new Array();
    punrut.push(p1);
    punrut.push(p2);


    var ruta = new OpenLayers.Geometry.LineString(punrut);

    var style = {
        strokeColor: '#0000ff',
        strokeOpacity: 0.5,
        strokeWidth: 5
    };

    var lineFeature = new OpenLayers.Feature.Vector(ruta, null, style);
    lienzoRecorridos.addFeatures([lineFeature]);

    return punrut;
}

function dinamicos(coord){

    var fil = coord.split("#");
	
    var cantPuntos = 0;
		
    //PUNTOS PARA RUTA
    var puntosRuta = new Array();

    for ( i=0; i<fil.length-1; i++ ) {
        
        var col = fil[i].split("%");        

        for ( j=0; j<col.length; j++ ) {

            var pt = new OpenLayers.Geometry.Point(col[0],col[1]);
           
            pt.transform( new OpenLayers.Projection( "EPSG:4326" ),new OpenLayers.Projection( "EPSG:900913" ) );
            puntosRuta.push(pt);			
            cantPuntos++;
        }
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
        
    }else{
        alert ("NO HAY REGISTROS ENTRE ESA FECHA Y HORA");
    }
}

function limpiarRecorrido(){
    var lineFeature = lienzoRecorridos.getFeatureById( "trazado" );
    if (lineFeature != null){
        lineFeature.destroy();
    }

    lienzoPuntos.destroyFeatures();
    markerInicioFin.clearMarkers();

    var div1 = document.getElementById("infor");
    div1.style.display = "none";
}
