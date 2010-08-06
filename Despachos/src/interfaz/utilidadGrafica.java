

package interfaz;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author kradac
 */
public class utilidadGrafica {
  public static void redimencionarTabla(JTable tblRecibidos){

        //Centrar valores en la celda
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        //Columnas

        // HORA
        tblRecibidos.getColumnModel().getColumn(0).setMaxWidth(60);
        tblRecibidos.getColumnModel().getColumn(0).setMinWidth(60);
        tblRecibidos.getColumnModel().getColumn(0).setPreferredWidth(60);

        //TELEFONO
        tblRecibidos.getColumnModel().getColumn(1).setMaxWidth(115);
        tblRecibidos.getColumnModel().getColumn(1).setMinWidth(115);
        tblRecibidos.getColumnModel().getColumn(1).setPreferredWidth(115);

        //CLIENTE
        tblRecibidos.getColumnModel().getColumn(3).setMinWidth(10);
        tblRecibidos.getColumnModel().getColumn(3).setPreferredWidth(20);

        //MIN
        tblRecibidos.getColumnModel().getColumn(4).setMaxWidth(30);
        tblRecibidos.getColumnModel().getColumn(4).setMinWidth(30);
        tblRecibidos.getColumnModel().getColumn(4).setPreferredWidth(30);
        tblRecibidos.getColumnModel().getColumn(4).setCellRenderer(tcr);

        //UNI
        tblRecibidos.getColumnModel().getColumn(5).setMaxWidth(40);
        tblRecibidos.getColumnModel().getColumn(5).setMinWidth(40);
        tblRecibidos.getColumnModel().getColumn(5).setPreferredWidth(40);
        tblRecibidos.getColumnModel().getColumn(5).setCellRenderer(tcr);

        //EST
        tblRecibidos.getColumnModel().getColumn(6).setMaxWidth(40);
        tblRecibidos.getColumnModel().getColumn(6).setMinWidth(40);
        tblRecibidos.getColumnModel().getColumn(6).setPreferredWidth(40);
        tblRecibidos.getColumnModel().getColumn(6).setCellRenderer(tcr);

        //ATRASO
        tblRecibidos.getColumnModel().getColumn(7).setMaxWidth(40);
        tblRecibidos.getColumnModel().getColumn(7).setMinWidth(40);
        tblRecibidos.getColumnModel().getColumn(7).setPreferredWidth(40);
        tblRecibidos.getColumnModel().getColumn(7).setCellRenderer(tcr);

        //TARIFA
        tblRecibidos.getColumnModel().getColumn(9).setMaxWidth(40);
        tblRecibidos.getColumnModel().getColumn(9).setMinWidth(40);
        tblRecibidos.getColumnModel().getColumn(9).setPreferredWidth(40);
        tblRecibidos.getColumnModel().getColumn(9).setCellRenderer(tcr);

    }

}
