var js_comboVehicBusq;
var contenBusqueda;
var ventBusqueda;
var nUnidadBusq;
var nameCoopBusq;

function ventBusquedaWin(){

    var js_comboVehicBB = new Ext.data.JsonStore({
        url:'php/combos/unidades_coop.php?xdfq=8et251',
        root: 'unidades',
        fields: [{
            name:'id'
        },{
            name:'name'
        }]
    });

    var comboVehicBB = new Ext.form.ComboBox({
        fieldLabel: 'Vehiculo',
        id:'vehGen2',
        store: js_comboVehicBB,
        hiddenName: 'idVeh2',
        valueField: 'id',
        displayField: 'name',
        typeAhead: true,
        disabled: false,
        mode: 'remote',
        triggerAction: 'all',
        emptyText:'Seleccionar Unidad...',
        allowBlank:false,
        resizable:true,
        minListWidth:300,
        selectOnFocus:true,
        listeners:{
            select: function(cmb,record,index){
                nUnidadBusq = record.get('id');
            }
        }
    });


    //Contenedor de Elementos
    contenBusqueda = new Ext.FormPanel({
        labelAlign: 'left',
        frame:true,
        bodyStyle:'padding:5px 5px 0',
        labelWidth:60,
        width: 500,
        items: [comboVehicBB],
        buttons: [{
            text: 'Localizar',
            handler: function() {                
                limpiar_busqueda(contenBusqueda,ventBusqueda);
                buscarVehiculo("T" + nUnidadBusq);
            }
        },{
            text: 'Cancelar',
            handler: function(){
                limpiar_busqueda(contenBusqueda,ventBusqueda);
            }
        }
        ]
    });

    // Visualizar Ventana
    if(!ventBusqueda){
        ventBusqueda = new Ext.Window({
            layout:'fit',
            title:'Localizar Unidad',
            resizable : false,
            width:280,
            height:140,
            closeAction:'hide',
            plain: true,
            items: [contenBusqueda]
        });
    }
    ventBusqueda.show(this);
}

/* oculta la venta y limpia los datos no guardados */
function limpiar_busqueda(ctBusq, winBusq){
    ctBusq.getForm().reset();
    winBusq.hide();
}



