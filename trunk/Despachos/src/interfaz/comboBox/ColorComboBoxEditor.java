
package interfaz.comboBox;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.event.EventListenerList;

/**
 *
 * @author KRADAC
 */
public class ColorComboBoxEditor implements ComboBoxEditor {

    final protected JButton editor;
    ArrayList<String> codigo;
    ArrayList<String> color;
    Map eticol;
    transient protected EventListenerList listenerList = new EventListenerList();

    public ColorComboBoxEditor(ArrayList<String> cod, ArrayList<String> col) {

        editor = new JButton("");
        codigo = cod;
        color = col;

    }

    public ColorComboBoxEditor(Map etCol) {

        editor = new JButton("");
        eticol = etCol;
    }

    public void addActionListener(ActionListener l) {
        listenerList.add(ActionListener.class, l);
    }

    public Component getEditorComponent() {
        return editor;
    }

    public Object getItem() {
        return editor.getBackground();
    }

    public void removeActionListener(ActionListener l) {
        listenerList.remove(ActionListener.class, l);
    }

    public void selectAll() {
        // ignore
    }

    public void setItem(Object newValue) {
        String codStr = (String) eticol.get(newValue.toString());
        if (codStr != null) {

            Color cl = new Color(Integer.valueOf(codStr));
            editor.setBackground(cl);
            editor.setText(newValue.toString());
        }
    }
}
