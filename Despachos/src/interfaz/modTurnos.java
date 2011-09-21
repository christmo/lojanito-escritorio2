package interfaz;//GEN-FIRST:event_btnCancelarActionPerformed
//GEN-LAST:event_btnCancelarActionPerformed
import BaseDatos.ConexionBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kradac
 */
public class modTurnos extends javax.swing.JDialog {

    ConexionBase bd;
    ResultSet rs;

    /** Creates new form modTurnos */
    public modTurnos(JFrame padre, ConexionBase conec) {
        super(padre, "Modificar Turnos");
        this.bd = conec;
        super.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());

        initComponents();
        modIntervaloSpinner(horaTurno);

        //cargar turnos existentes
        extraerTurnos(tblTurnos);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        btnCalcular = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTurnos = new javax.swing.JTable();
        cantidad = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        horaTurno = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCalcular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/calcular.png"))); // NOI18N
        btnCalcular.setText("Calcular");
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });
        getContentPane().add(btnCalcular, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 60, -1, 50));

        tblTurnos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Turno", "H. Inicio", "H. Fin"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTurnos.setToolTipText("Turnos para Despacho");
        tblTurnos.getTableHeader().setResizingAllowed(false);
        tblTurnos.getTableHeader().setReorderingAllowed(false);
        tblTurnos.setUpdateSelectionOnSort(false);
        jScrollPane1.setViewportView(tblTurnos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 170, 120));

        cantidad.setModel(new javax.swing.SpinnerNumberModel(4, 1, 23, 1));
        getContentPane().add(cantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 40, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("Cantidad de Turnos");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, 22));

        jLabel3.setText("Primer Turno");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 80, 22));

        horaTurno.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1282603115156L), null, null, java.util.Calendar.HOUR));
        getContentPane().add(horaTurno, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 68, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Hora de Inicio");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 100, -1));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 130, 50));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 160, 130, 50));
    }// </editor-fold>                        

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {                                            
        colocarNuevosTurnos(tblTurnos, calcularTurnos(horaTurno));
    }                                           

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {                                            
        try {
            super.dispose();
        } catch (Throwable ex) {
            Logger.getLogger(modTurnos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }                                           

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {                                           
        grabarTurnos(tblTurnos);

}                                          
    // Variables declaration - do not modify                     
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JSpinner cantidad;
    private javax.swing.JSpinner horaTurno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblTurnos;
    // End of variables declaration                   

    /**
     * Cambia el formato del Spinner para presentar
     * solamente hora : minutos
     * @param obj JSpinner
     */
    private void modIntervaloSpinner(JSpinner obj) {
        JFormattedTextField textField = ((JSpinner.DefaultEditor) obj.getEditor()).getTextField();
        DefaultFormatterFactory dff = (DefaultFormatterFactory) textField.getFormatterFactory();
        DateFormatter formatter = (DateFormatter) dff.getDefaultFormatter();
        formatter.setFormat(new SimpleDateFormat("HH:mm"));

        obj.setValue((new GregorianCalendar()).getTime());
    }

    /**
     * Extrae los turnos existentes en la empresa
     * y los visualiza en la Tabla
     * @param tblTur Tabla en donde se añadirán los turnos
     * @return
     */
    private ArrayList<String> extraerTurnos(JTable tblTur) {
        DefaultTableModel model = (DefaultTableModel) tblTur.getModel();


        ArrayList<String> turnos = new ArrayList();

        try {
            String[] values = new String[3];

            String sql = "SELECT HORA_INI, HORA_FIN FROM TURNOS ORDER BY ID_TURNO";
            rs = bd.ejecutarConsulta(sql);
            int i = 1;
            while (rs.next()) {
                values[0] = String.valueOf(i);
                values[1] = rs.getString("HORA_INI");
                values[2] = rs.getString("HORA_FIN");
                i++;
                model.addRow(values);
            }

        } catch (SQLException ex) {
            Logger.getLogger(modTurnos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return turnos;
    }

    /**
     * Devuelve los minutos de la parte decimal
     * de una hora
     * @param num Valor a extraer los minutos
     * @return
     */
    private int minutos(double num) {
        double rta = num - (int) num;
        int min = (int) (rta * 60);
        return min;
    }

    /**
     * Calcula la cantidad de turnos y las horas
     * de Inicio y Fin
     * @param obj JSpinner
     * @return un ArrayList<String> conteniendo los turnos
     */
    private ArrayList<String> calcularTurnos(JSpinner obj) {

        //Hora y Minutos del Spinner
        Date tmpSpinner = (Date) obj.getValue();
        Calendar calendarioSpinner = new GregorianCalendar();
        calendarioSpinner.setTimeInMillis(tmpSpinner.getTime());

        int numTurnos = Integer.parseInt(cantidad.getValue().toString());
        double numHoraXturno = 24 / Double.valueOf(numTurnos);

        int hora = (int) numHoraXturno;
        int min = minutos(numHoraXturno);

        String turnoIni = calendarioSpinner.get(Calendar.HOUR_OF_DAY) + ":"
                + calendarioSpinner.get(Calendar.MINUTE) + ":"
                + calendarioSpinner.get(Calendar.SECOND);

        ArrayList<String> turnos = new ArrayList();


        for (int i = 0; i < numTurnos; i++) {

            turnoIni = calendarioSpinner.get(Calendar.HOUR_OF_DAY) + ":"
                    + calendarioSpinner.get(Calendar.MINUTE) + ":"
                    + calendarioSpinner.get(Calendar.SECOND);
            turnos.add(turnoIni);

            calendarioSpinner.add(Calendar.HOUR, hora);
            calendarioSpinner.add(Calendar.MINUTE, min);
            calendarioSpinner.add(Calendar.SECOND, -1);

            turnoIni = calendarioSpinner.get(Calendar.HOUR_OF_DAY) + ":"
                    + calendarioSpinner.get(Calendar.MINUTE) + ":"
                    + calendarioSpinner.get(Calendar.SECOND);
            turnos.add(turnoIni);

            calendarioSpinner.add(Calendar.SECOND, 1);

        }
        return turnos;

    }

    /**
     * Coloca los turnos en la tabla para ser visualizados
     * @param tblTur
     * @param tur
     */
    private void colocarNuevosTurnos(JTable tblTur, ArrayList<String> tur) {

        DefaultTableModel model = (DefaultTableModel) tblTur.getModel();

        int numRow = model.getRowCount();

        for (int i = 0; i < numRow; i++) {
            model.removeRow(0);
        }

        for (int j = 0; j < tur.size(); j = j + 2) {
            String[] fila = new String[3];

            fila[0] = String.valueOf((j + 2) / 2);
            fila[1] = tur.get(j);
            fila[2] = tur.get(j + 1);
            model.addRow(fila);

        }
        tblTur.setModel(model);

    }

    private void grabarTurnos(JTable tblTur) {

        if (tblTur.getRowCount() > 0) {

            DefaultTableModel model = (DefaultTableModel) tblTur.getModel();

            bd.ejecutarSentencia("TRUNCATE TURNOS");

            for (int i = 0; i < model.getRowCount(); i++) {

                String sql =
                        "INSERT INTO TURNOS VALUES(" + model.getValueAt(i, 0)
                        + ",'" + model.getValueAt(i, 1) + "','" + model.getValueAt(i, 2)
                        + "')";
                bd.ejecutarSentencia(sql);

            }
            JOptionPane.showMessageDialog(this, "DATOS ACTUALIZADOS",
                    "LISTO",
                    JOptionPane.INFORMATION_MESSAGE);
            Principal.reiniciarTurno();
            super.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "DEBE CREAR TURNOS ANTES DE GUARDAR");
        }
    }
}
