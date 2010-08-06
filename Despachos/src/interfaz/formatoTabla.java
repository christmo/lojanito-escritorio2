
package interfaz;

import java.awt.Component;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class formatoTabla extends DefaultTableCellRenderer {

    private ArrayList<String> encab = new ArrayList();
    private ArrayList<String> codigo = new ArrayList();
    private ArrayList<String> color = new ArrayList();
    private Map numCod;
    int[] cod;

    public formatoTabla(int[] c) {
        cod = c;
    }

    public formatoTabla(ArrayList<String> en,
            Map nCod,
            ArrayList<String> cod,
            ArrayList<String> col) {
        encab = en;
        codigo = cod;
        color = col;
        numCod = nCod;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected,
            boolean hasFocus,
            int row, int col) {

        String cabCol = encab.get(col);
        String codColor = (String) numCod.get(cabCol);
        if (codColor != null) {
            int ind = codigo.indexOf(codColor);
            if (ind != -1) {
                String colRGB = color.get(ind);
                Color cl = new Color(Integer.parseInt(colRGB));
                setBackground(cl);
            }
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
    }
}
