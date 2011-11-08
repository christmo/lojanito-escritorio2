/*
 * Copyright(c) 2010
 */

var ctnAcerca;
var vntAcerca;

Ext.onReady(function(){

    ctnAcerca = new Ext.FormPanel({
        labelAlign: 'left',
        frame:true,
        bodyStyle:'padding:5px 5px 0',
        labelWidth:60,
        width: 500,

        items: [
            
        {
//            xtype: 'txtlabel',
//            id: 'acercaID',
//            text: 'Historico'
        }
            
        ],

        buttons: [{
            text: 'Generar',
            handler: function() {
                
            }
        }]
    });
});

function ventAcerca(){
    if(!vntAcerca){
        vntAcerca = new Ext.Window({
            layout:'fit',
            title:'Dream Team',
            resizable : false,
            width:500,
            height:170,
            closeAction:'hide',
            plain: true,
            items: [ctnAcerca]
        });
    }
    vntAcerca.show(this);
}