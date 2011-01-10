/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import interfaz.subVentanas.Despachos;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author root
 */
public class TrabajoTablas {

    private ArrayList<Despachos> listaDespachados = new ArrayList<Despachos>();
    private ArrayList<Despachos> listaPorDespacharTMP = new ArrayList<Despachos>();
    private DefaultTableModel dtm;

    /**
     * Inserta Una fila en la Tabla PorDEspachar de la Parte Superior de la
     * Ventana
     * @param tabla
     * @param despacho
     * @param dtmTabla
     */
    public void InsertarFilas(JTable tabla, Despachos despacho, DefaultTableModel dtmTabla) {

        listaPorDespacharTMP.add(despacho);

        dtm = dtmTabla;
        //tabla.setModel(dtmTabla);
        //AjustarAnchoColumnasTabla(tabla);


        for (int i = 0; i < listaPorDespacharTMP.size(); i++) {
            String[] datos = {
                listaPorDespacharTMP.get(i).getStrHora(),
                listaPorDespacharTMP.get(i).getStrTelefono(),
                "" + listaPorDespacharTMP.get(i).getIntCodigo(),
                listaPorDespacharTMP.get(i).getStrNombre(),
                listaPorDespacharTMP.get(i).getStrBarrio(),
                listaPorDespacharTMP.get(i).getStrDireccion(),
                "",
                "",
                "0",
                listaPorDespacharTMP.get(i).getStrNota()
            };
            dtm.insertRow(0, datos);
        }
    }

    public void InsertarFilaPendiente(JTable tabla, Despachos despacho, DefaultTableModel dtmTabla) {

        listaPorDespacharTMP.add(despacho);

        dtm = dtmTabla;
        //tabla.setModel(dtmTabla);
        //AjustarAnchoColumnasTabla(tabla);
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        //tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tcr.setForeground(Color.red);
        for (int i = 0; i < 5; i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }

        String[] datos = {
            listaPorDespacharTMP.get(listaPorDespacharTMP.size() - 1).getStrHora(),
            listaPorDespacharTMP.get(listaPorDespacharTMP.size() - 1).getStrTelefono(),
            "" + listaPorDespacharTMP.get(listaPorDespacharTMP.size() - 1).getIntCodigo(),
            listaPorDespacharTMP.get(listaPorDespacharTMP.size() - 1).getStrNombre(),
            listaPorDespacharTMP.get(listaPorDespacharTMP.size() - 1).getStrBarrio(),
            listaPorDespacharTMP.get(listaPorDespacharTMP.size() - 1).getStrDireccion(),
            "",
            "",
            "0",
            listaPorDespacharTMP.get(listaPorDespacharTMP.size() - 1).getStrNota()
        };
        dtm.insertRow(0, datos);

    }

    /**
     * Inserta una fila en la tabla de Despachados en la parte inferior de la Ventana
     * @param tabla
     * @param despacho
     * @param dtmTabla
     */
    public void InsertarFilasDespachados(JTable tabla, Despachos despacho, DefaultTableModel dtmTabla) {

        try {
            listaDespachados.add(despacho);

            dtm = dtmTabla;
            //tabla.setModel(dtmTabla);
            //AjustarAnchoColumnasTabla(tabla);


            for (int i = 0; i < listaDespachados.size(); i++) {
                String[] datos = {
                    listaDespachados.get(i).getStrHora(),
                    listaDespachados.get(i).getStrTelefono(),
                    "" + listaDespachados.get(i).getIntCodigo(),
                    listaDespachados.get(i).getStrNombre(),
                    listaDespachados.get(i).getStrBarrio(),
                    listaDespachados.get(i).getStrDireccion(),
                    "" + listaDespachados.get(i).getIntUnidad(),
                    "" + listaDespachados.get(i).getIntMinutos(),
                    "" + listaDespachados.get(i).getIntAtraso(),
                    listaDespachados.get(i).getStrNota()
                };
                dtm.insertRow(0, datos);
            }
        } catch (NullPointerException ex) {
        }
    }
}
