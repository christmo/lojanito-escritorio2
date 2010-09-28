package interfaz;

import BaseDatos.ConexionBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Kradac
 */
public class asignarMultas extends javax.swing.JDialog {

    funcionesUtilidad objUtilidad = new funcionesUtilidad();
    ConexionBase bd;
    ResultSet rs;
    public static String strUsuario;

    /** Creates new form modTurnos */
    public asignarMultas(JFrame padre, String strSesion[], ConexionBase con) {
        super(padre, "Asignacion de Multas");
        super.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());

        this.bd = con;
        asignarMultas.strUsuario = strSesion[0];
        initComponents();
        txtFecha.setText(objUtilidad.getFecha());
        txtHora.setText(objUtilidad.getHora());
        consultarNrosUnidad();
        consultarCodigoMultas();
        cmbCodigoM.setSelectedIndex(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        cmbCodigoM = new javax.swing.JComboBox();
        txtHora = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cmbNroUnidad = new javax.swing.JComboBox();

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cerrar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel6.setText("CODIGO MULTA:");

        cmbCodigoM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione" }));

        txtHora.setEditable(false);

        jLabel5.setText("HORA:");

        jLabel4.setText("FECHA:");

        txtFecha.setEditable(false);

        jLabel3.setText("Nro. UNIDAD:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel1.setText("ASIGNAR MULTA");

        cmbNroUnidad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
            .addGroup(layout.createSequentialGroup()
                .addGap(107, 107, 107)
                .addComponent(jLabel1)
                .addContainerGap(127, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbNroUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbCodigoM, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(cmbNroUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(cmbCodigoM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        try {
            super.dispose();
        } catch (Throwable ex) {
            Logger.getLogger(Multas.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (cmbNroUnidad.getSelectedIndex() >= 0
                && cmbCodigoM.getSelectedIndex() > 0) {

            if (objUtilidad.isNumeric(cmbNroUnidad.getSelectedItem().toString())) {

                if (guardarRegistro(Integer.parseInt(
                        cmbNroUnidad.getSelectedItem().toString()),
                        txtFecha.getText(), txtHora.getText(),
                        cmbCodigoM.getSelectedItem().toString()
                        )) {
                    JOptionPane.showMessageDialog(this, "REGISTRO GUARDADO EXITOSAMENTE",
                            "REGISTRO GUARDADO",
                            JOptionPane.INFORMATION_MESSAGE);
                    limpiar();
                }

            } else {
                JOptionPane.showMessageDialog(this, "EL NUMERO DE UNIDAD DEBE SER NUMERICO",
                        "VALOR DEL CAMPO INCORRECTO",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "FALTAN SELECCIONAR ALGUNOS CAMPOS",
                    "FALTAN DATOS",
                    JOptionPane.ERROR_MESSAGE);
        }

}//GEN-LAST:event_btnGuardarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox cmbCodigoM;
    private javax.swing.JComboBox cmbNroUnidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtHora;
    // End of variables declaration//GEN-END:variables

    private void limpiar() {
        cmbNroUnidad.setSelectedIndex(-1);
        cmbCodigoM.setSelectedIndex(-1);
    }

    private boolean guardarRegistro(int intNroUnidad, String strFecha, String strHora, String strCodMulta) {
        String sql = "INSERT INTO MULTAS_ASIGNADAS(USUARIO, N_UNIDAD, FECHA, HORA, COD_MULTA )"
                + "VALUES('" + strUsuario + "'," + intNroUnidad + ",'" + strFecha + "','" + strHora + "','" + strCodMulta + "')";
        System.out.println(sql);
        if (bd.ejecutarSentencia(sql)) {
            return true;
        }
        return false;
    }

    private void consultarCodigoMultas() {
        String sql = "select COD_MULTA from COD_MULTAS";
        System.out.println("consulta realizada");
        //ArrayList arrayCodigos= new ArrayList();
        try {
            rs = bd.ejecutarConsulta(sql);
            while (rs.next()) {
                cmbCodigoM.addItem(rs.getString("COD_MULTA"));
            }
            //return arrayCodigos;
        } catch (SQLException ex) {
            //Logger.getLogger(Multas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "NO EXISTEN AUN MULTAS REGISTRADAS",
                    "NO EXISTEN MULTAS",
                    JOptionPane.ERROR_MESSAGE);
            limpiar();
        }
    }

    private void consultarNrosUnidad() {
        String sql = "SELECT N_UNIDAD FROM VEHICULOS";
        System.out.println("consulta realizada");
        //ArrayList arrayCodigos= new ArrayList();
        try {
            rs = bd.ejecutarConsulta(sql);
            while (rs.next()) {
                cmbNroUnidad.addItem(rs.getString("N_UNIDAD"));
            }
            //return arrayCodigos;
        } catch (SQLException ex) {
            //Logger.getLogger(Multas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "NO EXISTEN UNIDADES INGRESADAS",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            limpiar();
        }
    }
}
