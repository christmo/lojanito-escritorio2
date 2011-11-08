/*!
 * Ext JS Library 3.2.0
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
//
// Note that these are all defined as panel configs, rather than instantiated
// as panel objects.  You could just as easily do this instead:
//
// var absolute = new Ext.Panel({ ... });
//
// However, by passing configs into the main container instead of objects, we can defer
// layout AND object instantiation until absolutely needed.  Since most of these panels
// won't be shown by default until requested, this will save us some processing
// time up front when initially rendering the page.
//
// Since all of these configs are being added into a layout container, they are
// automatically assumed to be panel configs, and so the xtype of 'panel' is
// implicit.  To define a config of some other type of component to be added into
// the layout, simply provide the appropriate xtype config explicitly.
//
/*
 * ================  Start page config  =======================
 */
// The default start page, also a simple example of a FitLayout.

var start = new Ext.TabPanel({
    region: 'center', // a center region is ALWAYS required for border layout
    deferredRender: false,
    margins:'3 3 3 0',
    activeTab: 0,
    

    defaults : {
        bodyStyle : 'padding:0px'
    },
    items : [ {
        title : 'Mapa',
        html : '<div style="position: absolute;" id="map"></div>  <div id="infor" align= "right" '+
    'style="position:absolute; bottom:0; ' +
    'margin-bottom: 3px;' +
    ' display:none;'+
    ' background-color:#CCFF99;">	'+
    '<TABLE id="tablestados">'+
    '<TR >'+
    '	<TD rowspan="1" colspan="6" align="center" ><b><label id="idVeh"></label></b></td> '+
    '</TR>'+
    '<TR class="alt">'+
    '	<TD rowspan="1" colspan="2" align="center" ><b>Dist KM:</b></td> '+
    '	<TD rowspan="1" colspan="2" align="center" ><label id="distKM"></label></td> '+
    '</TR>'+
    '<TR>'+
    '	<TD align="right"> <b>Num: </b>	'		+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="num1"></label>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="num2"></label>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="num3"></label>'+
    '	</TD>'+
    '</TR>	'+
    '<TR class="alt">'+
    '	<TD align="right"><b>Fech:</b>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="fecha1"></label>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="fecha2"></label>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="fecha3"></label>'+
    '	</TD>'+
    '</TR>'+
    '<TR>'+
    '	<TD align="right"><b>Hor:</b>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="hora1"></label>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="hora2"></label>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="hora3"></label>'+
    '	</TD>'+

    '</TR>'+
    '<TR class="alt">'+
    '	<TD align="right"><b>Est:</b>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="est1"></label>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="est2"></label>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="est3"></label>'+
    '	</TD>'+

    '</TR>'+
    '<TR>'+
    '	<TD align="right"><b>EsTx:</b>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="estx1"></label>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="estx2"></label>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="estx3"></label>'+
    '	</TD>'+

    '</TR>'+
    '<TR class="alt">'+
    '	<TD align="right"><b>Velo:</b>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="otr1"></label>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="otr2"></label>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="otr3"></label>'+
    '	</TD>'+
    '</TR>'+
    '<TR>'+
    '	<TD align="right"><b>Soft:</b>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="soft1"></label>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="soft2"></label>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="soft3"></label>'+
    '	</TD>'+
    '</TR>'+

    '<TR>'+
    '	<TD align="right"><b>Fono:</b>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="fono1"></label>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="fono2"></label>'+
    '	</TD>'+
    '	<TD align="center">'+
    '		<label id="fono3"></label>'+
    '	</TD>'+
    '</TR>'+

    '</TABLE>'+
    '</div>'+
    
    '<div style="position: absolute;" id="map1"></div>  <div id="sugerencia" align= "right" '+
    'style="position:absolute; bottom:0; ' +
    'margin-bottom: 3px;' +
    ' display:none;'+
    ' background-color:#ffaa23;">	'+
    '<TABLE id="tablestados">'+
    '<TR >'+
    '	<TD rowspan="1" colspan="7" align="center" ><b><label id="titulo">Recomendaci\xF3n Unidades Cercanas</label></b></td> '+
    '</TR>'+
    
    '<TR class="alt">'+
    '	<TD rowspan="1" colspan="2" align="center" ><b>Orden</b></td> '+
    '	<TD rowspan="1" colspan="2" align="center" ><b>Unidad</b></td> '+
    '	<TD rowspan="1" colspan="2" align="center" ><b>Distancia (Km)</b></td> '+
    '	<TD rowspan="1" colspan="2" align="center" ><b>Tiempo (Min)</b></td> '+
    '</TR>'+
    
    '<TR class="alt">'+
    '	<TD rowspan="1" colspan="2" align="center" ><b>1</b></td> '+
    '	<TD rowspan="1" colspan="2" align="center" ><label id="unidad1"></label></td> '+
    '	<TD rowspan="1" colspan="2" align="center" ><label id="distancia1"></label></td> '+
    '	<TD rowspan="1" colspan="2" align="center" ><label id="tiempo1"></label></td> '+
    '</TR>'+
    '<TR class="alt">'+
    '	<TD rowspan="1" colspan="2" align="center" ><b>2</b></td> '+
    '	<TD rowspan="1" colspan="2" align="center" ><label id="unidad2"></label></td> '+
    '	<TD rowspan="1" colspan="2" align="center" ><label id="distancia2"></label></td> '+
    '	<TD rowspan="1" colspan="2" align="center" ><label id="tiempo2"></label></td> '+
    '</TR>'+
    '<TR class="alt">'+
    '	<TD rowspan="1" colspan="2" align="center" ><b>3</b></td> '+
    '	<TD rowspan="1" colspan="2" align="center" ><label id="unidad3"></label></td> '+
    '	<TD rowspan="1" colspan="2" align="center" ><label id="distancia3"></label></td> '+
    '	<TD rowspan="1" colspan="2" align="center" ><label id="tiempo3"></label></td> '+
    '</TR>'+

    '</TABLE>'+
    '</div>'


    }]
});