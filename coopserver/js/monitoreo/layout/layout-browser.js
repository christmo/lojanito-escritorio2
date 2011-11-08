/**
 * Base de los componentes de la interfaz grafica 
 * todo el contenedor de la aplicaci�n
 */
var sb;

Ext.onReady(function(){
	
    var tb = new Ext.Toolbar();
    tb.add(
        '-',{
            xtype: 'tbbutton',
            cls: 'x-btn-text-icon',
            icon: 'img/historico.png',
            text: 'Historico',
            menu: [
            {
                text: 'Recorridos',
                iconCls: 'add16',
                icon: 'img/icon.png',
                menu: [{
                    text: 'Trazar Nuevo',
                    icon: 'img/lapiz.png',
                    handler: function(){
                        ventanaRecorridos();
                    }
                },
                {
                    text: 'Borrar Actual',
                    icon: 'img/borrar.png',
                    handler: function(){
                        limpiarRecorrido();
                    }
                },'-',
                {
                    text: 'Simbología',
                    icon: 'img/paleta.png',
                    handler: function(){
                        ventanaSimbolos();
                    }
                }]
            }
            ]
        },'-',
        {
            xtype: 'tbbutton',
            icon: 'img/veh.png',
            text: 'Vehiculo',
            menu: [{
                text: 'Localizar',
                icon: 'img/buscar.png',
                handler: function(){
                    ventBusquedaWin();
                }
            }]
        },'-',{
            xtype: 'tbbutton',
            icon: 'img/mundo.png',
            text: 'Mapa',
            menu: [{
                text: 'Imprimir',
                icon : 'img/print.png',
                text: 'Imprimir',
                handler: function(){
                    alert ('under construction...');
                }
            }]
        },'-',
        {
            xtype: 'tbbutton',
            icon: 'img/panel.png',
            text: 'Aplicacion',
            menu: [{
                text: 'Salir',
                icon : 'img/salir.png',
                handler: function(){
                    window.location = 'php/login/logout.php';
                }
            },'-',

            {
                text: 'Acerca de...',
                icon : 'img/info.png',
                handler: function(){
                    credits();
                }
            },
            {
                text: 'Ayuda',
                icon : 'img/help.png',
                handler: function(){
                //Ayuda sobre la App
                }
            }]
        }
        );

    tb.doLayout();

    var BarTool = {
        id: 'content-panel-bar',
        region: 'center',
        layout: 'fit',
        margins: '0 0 0 0',
        tbar: tb,
        border: false
    };

    new Ext.Viewport({
        layout: 'border',
        items: [
        {
            region: 'north',
            height: 75,
            items:[{
                height: 45,
                html: '<div id = "header"><h1><CENTER> <b>SISTEMA DE RASTREO SATELITAL  <br /> Loja-Ecuador</b></CENTER></h1></div>'
            },
            BarTool
            ]
        },
        {
            region: 'west',
            id: 'west-panel', 
            split: true,
            width: 200,
            minSize: 175,
            maxSize: 400,
            title: 'Vehiculos',
            collapsible: true,
            margins: '0 0 0 5',            
            xtype: 'treepanel',
            autoScroll: true,
            icon : 'img/help.png',
            dataUrl: 'php/monitoreo/json_vehic.php',
            root: {
                nodeType: 'async',
                text: 'Ext JS',
                draggable: false,
                id: 'src'
            },
            rootVisible: false,
            listeners: {
                click: function(n) {
                    buscarVehiculo("T" + n.attributes.id);
                }
            }
        },
        start],
        renderTo: Ext.getBody()
    });
});