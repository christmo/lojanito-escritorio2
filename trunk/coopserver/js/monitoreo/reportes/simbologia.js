var winCred;
var tabla = '<TABLE id="tablestados">' +
'<TR > ' +
'    <TD  align="center"> ' +
'       <div style ="background-color:#734D26; color: #734D26; width:15px">. </div>'+
'    </TD> ' +

'    <TD align="left">Ocupado ' +
'    </TD> ' +
'</TR> ' +
' <TR class="alt"> ' +
'     <TD align="center"> ' +
'       <div style ="background-color:purple; color: purple; width:15px">. </div>'+
'     </TD> ' +
'     <TD align="left">Asignado ' +
'     </TD> ' +
'  </TR> ' +
'  <TR> ' +
'     <TD align="center"> ' +
'       <div style ="background-color:green; color: green; width:15px">. </div>'+
'     </TD> ' +
'     <TD align="left">Libre ' +
'     </TD> ' +
'  </TR> ' +
'  <TR class="alt"> ' +
'     <TD align="center"> ' +
'          <div style ="background-color:black; color: black; width:15px">. </div>'+
'    </TD> ' +
'    <TD align="left">Apagado ' +
'    </TD> ' +
'</TR> ' +
'<TR> ' +
'   <TD align="center"> ' +
'          <div style ="background-color:red; color: red; width:15px">. </div>'+
'   </TD> ' +
'   <TD align="left">Conflicto '+
'   </TD> ' +
' </TR>'+
'  <TR class="alt"> ' +
'     <TD> <IMG SRC="img/inicio.png">' +
'    </TD> ' +
'    <TD align="left">Inicio de Recorrido' +
'    </TD> ' +
'  <TR > ' +
'     <TD> <IMG SRC="img/fin.png">' +
'    </TD> ' +
'    <TD align="left">Fin de Recorrido' +
'    </TD> ' +
'</TR> ' +
'</TR> ' +
' </TABLE>';
var vSim;

panelEst = new Ext.FormPanel({
    labelAlign: 'center',
    frame:true,
    bodyStyle:'padding:5px 5px 0',
    labelWidth:60,
    width: 350,
    items: [{
        html: tabla,
        width:150
    //anchor:'right 20%'
    }],

    buttons: [{
        text: 'OK',
        handler: function() {
            vSim.hide();
        }
    }]
});



function ventanaSimbolos(){
    if(!vSim){
        vSim = new Ext.Window({
            layout:'fit',
            title:'SIMBOLOGIA',
            resizable : false,
            width:190,
            height:265,
            closeAction:'hide',
            plain: true,
            items: [panelEst]
        });
    }
    vSim.show(this);
}

var myData;

function colorEstados(val){

    if (val == 'ASIGNADO'){
        return '<span style="color:purple;">' + val + '</span>';
    }else if (val == 'OCUPADO'){
        return '<span style="color:#734D26;">' + val + '</span>';
    }else if (val == 'LIBRE'){
        return '<span style="color:green;">' + val + '</span>';
    }else if (val == 'S/E'){
        return '<span style="color:blue;">' + val + '</span>';
    }else {
        return '<span style="color:red;">' + val + '</span>';
    }
}

/**
 * Calcula la velocidad a presentar
 */
function velocidad(val) {
    var velo = val * 1.85;
    return velo;
}

function agregarTab(){

    var encabezado = "Unidad ["+nUnidad+"] de ["+nameCoop+"] desde ["+fInicio+"] hasta ["+fFin+"]";

    // Almacen de Datos
    var store = new Ext.data.ArrayStore({
        fields: [
        {
            name: 'fecha'
        },
        {
            name: 'hora'
        },
        {
            name: 'lat'
        },
        {
            name: 'lon'
        },
        {
            name: 'est'
        },
        {
            name: 'estx'
        },
        {
            name: 'otro'
        },

        {
            name: 'soft'
        },

        {
            name: 'fono'
        }
        ]
    });

    // Cargar datos desde el arreglo
    store.loadData(myData);

    //    var linkButton = new Ext.LinkButton({
    //        id: 'grid-excel-button',
    //        text: 'Export to Excel'
    //    });
    //

    // Crear Grid
    var grid = new Ext.grid.GridPanel({
        store: store,
        //        bbar: new Ext.Toolbar({
        //            buttons: [linkButton]
        //        }),
        columns: [
        {
            id:'fecha',
            header: '<center>Fecha</center>',
            width: 85,
            sortable: true,
            dataIndex: 'fecha'
        },
        {
            header: '<center>Hora</center>',
            width: 75,
            sortable: true,
            dataIndex: 'hora'
        },
        {
            header: '<center>Latitud</center>',
            width: 75,
            sortable: true,
            //   renderer: change,
            dataIndex: 'lat'
        },

        {
            header: '<center>Longitud</center>',
            width: 75,
            sortable: true,
            //       renderer: pctChange,
            dataIndex: 'lon'
        },
        {
            header: '<center>Est Taxi</center>',
            width: 85,
            sortable: true,
            //  renderer: Ext.util.Format.dateRenderer('m/d/Y'),
            dataIndex: 'est'
        },
        {
            header: '<center>Est TaxMet</center>',
            width: 85,
            sortable: true,
            //  renderer: Ext.util.Format.dateRenderer('m/d/Y'),
            dataIndex: 'estx'
        },
        {
            header: '<center>Velocidad</center>',
            width: 85,
            sortable: true,
            renderer: velocidad,
            dataIndex: 'otro'
        },
        {
            header: '<center>Soft</center>',
            width: 85,
            sortable: true,
            renderer: colorEstados,
            dataIndex: 'soft'
        },
        {
            header: '<center>Fono</center>',
            width: 85,
            sortable: true,
            dataIndex: 'fono'
        }
        ],
        stripeRows: true,
        //autoExpandColumn: 'fecha',  //==> CUAL ¿?
        height: 450,
        width: 900,
        title: '<center>'+encabezado+'</center>',
        // config options for stateful behavior
        stateful: true,
        stateId: 'grid'
    });

    //  linkButton.href = 'data:application/vnd.ms-excel;base64,' + Base64.encode(grid.getExcelXml());

    


    //Traer datos con ajax
    //Armar el array
    //Crear el Grid
    var tab = new Ext.Panel({
        title: 'Reporte Recorrido',
        closable: true, //<-- este tab se puede cerrar
        iconCls: 'app-icon',
        //tbar:[{iconCls:'save-icon'},{iconCls:'spell-icon'},{iconCls:'search-icon'},{iconCls:'send-icon'},{iconCls:'print-icon'}],
        items: grid
    });

    start.add(tab);
    start.setActiveTab(tab);
}


function agregarTabAsig(){

    var encabezado = "Unidad ["+nUnidadAsig+"] de ["+nameCoopAsig+"] desde ["+fInicioAsig+"] hasta ["+fFinAsig+"]";

    // Almacen de Datos
    var storeAsig = new Ext.data.ArrayStore({
        fields: [
        {
            name: 'COD_CLIENTE'
        },
        {
            name: 'ESTADO'
        },
        {
            name: 'FECHA'
        },
        {
            name: 'HORA'
        },
        {
            name: 'FONO'
        }
        ]
    });

    // Cargar datos desde el arreglo
    storeAsig.loadData(dataAsig);

    // Crear Grid
    var grid = new Ext.grid.GridPanel({
        store: storeAsig,
        columns: [
        {
            id:'codCliente',
            header: '<center>COD_CLIENTE</center>',
            width: 85,
            sortable: true,
            dataIndex: 'COD_CLIENTE'
        },
        {
            header: '<center>ESTADO</center>',
            width: 75,
            sortable: true,
            renderer: colorEstados,
            dataIndex: 'ESTADO'
        },
        {
            header: '<center>FECHA</center>',
            width: 75,
            sortable: true,
            dataIndex: 'FECHA'
        },

        {
            header: '<center>HORA</center>',
            width: 75,
            sortable: true,
            dataIndex: 'HORA'
        },
        {
            header: '<center>FONO</center>',
            width: 85,
            sortable: true,
            dataIndex: 'FONO'
        }
        ],
        stripeRows: true,
        height: 450,
        width: 450,
        title: '<center>'+encabezado+'</center>',
        stateful: true,
        stateId: 'grid',

        sm: new Ext.grid.RowSelectionModel({
            singleSelect: true,
            listeners: {
                rowselect: function(sm, row, rec) {
                    if (chkInicio.getValue()) {
                        txtInicio.setValue(row);
                    }
                }
            }
        })


    });

    var tab = new Ext.Panel({
        title: 'Asignaciones del Software',
        closable: true, //<-- este tab se puede cerrar
        iconCls: 'app-icon',
        //        layout: 'column',
        width : 900,
        labelWidth: 120,
        items: [
        grid
        ]
    });

    start.add(tab);
    start.setActiveTab(tab);
}

function credits() {

    if(!winCred){

        contenedorWin = new Ext.FormPanel({
            labelAlign: 'left',
            frame:true,
            bodyStyle:'padding:5px 5px 0',
            labelWidth:60,
            width: 500,
            items: [
            {
                layout: 'form',
                items: [{
                    html: '<div id="efecto"><center> <IMG SRC="img/logo.png"> '+
                        '<br> <br>info@kradac.com <br><br>'+
                        'Developers: <br>'+
                        'cumar.cueva@kradac.com<br>'+
                        'christian.mora@kradac.com</center></div>',
                    xtype: "panel"
                }]
            }
            ],
            buttonAlign: 'center',
            buttons: [{
                text: 'OK',

                handler: function() {
                   winCred.hide();
                }
            }]
        });

        winCred = new Ext.Window({
            layout:'fit',
            title:'Credits',
            resizable : false,
            width:300,
            height:240,
            closeAction:'hide',
            plain: true,
            items: [contenedorWin]
        });
    }
    winCred.show(this);

        var el = Ext.get('efecto');

    el.fadeIn({
        duration:10,
        easing: ''
    });
}