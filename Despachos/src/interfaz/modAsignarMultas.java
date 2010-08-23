/*
 * NewJPanel.java
 *
 * Created on 09/08/2010, 08:38:49 AM
 */
package interfaz;

import BaseDatos.ConexionBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class modAsignarMultas extends javax.swing.JDialog {

    ArrayList<String[]> multas;
    private static String[] auxItemFila;
    private funcionesUtilidad funciones = new funcionesUtilidad();
    ConexionBase bd;
    ResultSet rs;
    public static String strUsuario;

    public modAsignarMultas(JFrame padre, ConexionBase conec, String strSesion[]) {

        super(padre, "Busqueda de Multas");
        super.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());

        this.bd = conec;
        strUsuario = strSesion[0];

        initComponents();
        cmb2Parametro.setVisible(false);
        dpFechaIni.setVisible(false);
        dpFechaFin.setVisible(false);
        lblFecha.setVisible(false);
        lblFecha1.setVisible(false);

        consultarCodigoMultas();
        cmbEstado.setSelectedIndex(0);
        cmbCodigoM.setSelectedIndex(0);

        this.setVisible(true);

        bloquearArea(false);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblResultado = new javax.swing.JTable();
        cmbParametro = new javax.swing.JComboBox();
        btnBuscar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        lblNumCoincidencias = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        cmb2Parametro = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        pnlDatos = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        cmbEstado = new javax.swing.JComboBox();
        cmbCodigoM = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNroUnidad = new javax.swing.JTextField();
        txtFechaB = new javax.swing.JTextField();
        txtHora = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        dpFechaIni = new org.jdesktop.swingx.JXDatePicker();
        dpFechaFin = new org.jdesktop.swingx.JXDatePicker();
        lblFecha1 = new javax.swing.JLabel();

        tblResultado.setFont(new java.awt.Font("Tahoma", 0, 14));
        tblResultado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "USUARIO", "NUM UNIDAD", "FECHA", "HORA", "COD MULTA", "ESTADO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblResultado.setRowHeight(20);
        tblResultado.getTableHeader().setReorderingAllowed(false);
        tblResultado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblResultadoMouseClicked(evt);
            }
        });
        tblResultado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblResultadoKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblResultado);
        tblResultado.getColumnModel().getColumn(0).setResizable(false);
        tblResultado.getColumnModel().getColumn(0).setPreferredWidth(100);
        tblResultado.getColumnModel().getColumn(1).setResizable(false);
        tblResultado.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblResultado.getColumnModel().getColumn(2).setResizable(false);
        tblResultado.getColumnModel().getColumn(2).setPreferredWidth(100);

        cmbParametro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "N_UNIDAD", "COD_MULTA", "FECHA", "USUARIO", "ESTADO", " " }));
        cmbParametro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbParametroActionPerformed(evt);
            }
        });

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/buscar.png"))); // NOI18N
        btnBuscar.setText("BUSCAR");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("MULTAS");

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/rojo.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("CERRAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        cmb2Parametro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        cmb2Parametro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmb2ParametroActionPerformed(evt);
            }
        });

        jLabel2.setText("BUSCAR POR:");

        lblFecha.setText("DESDE EL :");

        pnlDatos.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel7.setText("ESTADO:");

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "Pendiente", "Pagada" }));

        cmbCodigoM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione" }));
        cmbCodigoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCodigoMActionPerformed(evt);
            }
        });

        jLabel3.setText("Nro. UNIDAD:");

        jLabel6.setText("CODIGO MULTA:");

        txtFechaB.setEditable(false);

        txtHora.setEditable(false);

        jLabel5.setText("HORA:");

        jLabel8.setText("FECHA:");

        javax.swing.GroupLayout pnlDatosLayout = new javax.swing.GroupLayout(pnlDatos);
        pnlDatos.setLayout(pnlDatosLayout);
        pnlDatosLayout.setHorizontalGroup(
            pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFechaB, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNroUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCodigoM, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        pnlDatosLayout.setVerticalGroup(
            pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(txtNroUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(cmbCodigoM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDatosLayout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel7))
                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtFechaB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(pnlDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                    .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21))
        );

        lblFecha1.setText("HASTA EL :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(279, 279, 279)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(126, 126, 126)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbParametro, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb2Parametro, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addComponent(btnBuscar))
            .addGroup(layout.createSequentialGroup()
                .addGap(146, 146, 146)
                .addComponent(lblFecha)
                .addGap(18, 18, 18)
                .addComponent(dpFechaIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(lblFecha1)
                .addGap(12, 12, 12)
                .addComponent(dpFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(lblNumCoincidencias, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(194, 194, 194)
                .addComponent(pnlDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(btnGuardar)
                .addGap(29, 29, 29)
                .addComponent(btnCancelar))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbParametro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(cmb2Parametro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lblFecha))
                    .addComponent(dpFechaIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lblFecha1))
                    .addComponent(dpFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(lblNumCoincidencias, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(pnlDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        limpiar();
        if (cmbParametro.getSelectedIndex() > 0) {
            if (!cmbParametro.getSelectedItem().equals("FECHA")
                    && !cmb2Parametro.getSelectedItem().equals("")) {

                BuscarMultas(cmbParametro.getSelectedItem().toString(),
                        cmb2Parametro.getSelectedItem().toString(),
                        tblResultado, null, null);

            } else if (cmbParametro.getSelectedItem().equals("FECHA")
                    && dpFechaFin != null
                    && dpFechaIni != null) {

                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                String fechaIni = formato.format(dpFechaIni.getDate());
                String fechaFin = formato.format(dpFechaFin.getDate());

                BuscarMultas(cmbParametro.getSelectedItem().toString(),
                        "", tblResultado,
                        fechaIni, fechaFin);

            } else {
                JOptionPane.showMessageDialog(this, "FALTAN PARÁMETROS DE  "
                        + "BUSQUEDA",
                        "SELECCIONE CRITERIO",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "NO HA SELECCIONADO UN "
                    + "CRITERIO DE BUSQUEDA",
                    "SELECCIONE UN CRITERIO DE BUSQUEDA",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblResultadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblResultadoMouseClicked
        int fila = tblResultado.rowAtPoint(evt.getPoint());
        int columna = tblResultado.columnAtPoint(evt.getPoint());

        if ((fila > -1) && (columna > -1)) {
            cargarAsignacionDeMultaSeleccionada(fila);
        }
    }//GEN-LAST:event_tblResultadoMouseClicked

    private void tblResultadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblResultadoKeyPressed
        int fila;
        if (evt.getKeyCode() == 38) {
            if (tblResultado.getSelectedRow() != 0) {
                fila = tblResultado.getSelectedRow() - 1;
            } else {
                fila = tblResultado.getSelectedRow();
            }
            cargarAsignacionDeMultaSeleccionada(fila);
        }

        if (evt.getKeyCode() == 40) {
            if ((tblResultado.getRowCount() - 1) == tblResultado.getSelectedRow()) {
                fila = tblResultado.getSelectedRow();
            } else {
                fila = tblResultado.getSelectedRow() + 1;
            }
            //cargarVehiculoSeleccionado(fila);
        }
    }//GEN-LAST:event_tblResultadoKeyPressed

    private void cmbParametroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbParametroActionPerformed

        if (!cmbParametro.getSelectedItem().equals("FECHA")) {
            preConsultaDeDatos(cmbParametro.getSelectedItem().toString());
            cmb2Parametro.setVisible(true);
            dpFechaIni.setVisible(false);
            dpFechaFin.setVisible(false);
            lblFecha.setVisible(false);
            lblFecha1.setVisible(false);
        } else {
            dpFechaIni.setVisible(true);
            dpFechaFin.setVisible(true);
            lblFecha.setVisible(true);
            cmb2Parametro.setVisible(false);
            lblFecha1.setVisible(true);
        }
    }//GEN-LAST:event_cmbParametroActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (funciones.isNumeric(txtNroUnidad.getText())) {
            if (cmbCodigoM.getSelectedIndex() > 0 && cmbEstado.getSelectedIndex() > 0) {
                if (modificarRegistro(strUsuario, Integer.parseInt(txtNroUnidad.getText()),
                        cmbCodigoM.getSelectedItem().toString(),
                        cmbEstado.getSelectedIndex())) {
                    JOptionPane.showMessageDialog(this, "MULTA MODIFICADA SATISFACTORIAMENTE",
                            "OK",
                            JOptionPane.INFORMATION_MESSAGE);
                    limpiar();
                    limpiarTabla(tblResultado);
                } else {
                    JOptionPane.showMessageDialog(this, "NO SE PUDO MODIFICAR EL REGISTRO",
                            "ERROR",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "FALTA SELECCIONAR EL CODIGO DE MULTA O EL ESTADO",
                        "FALTAN VALORES",
                        JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this, "EL NUMERO DE VEHICULO ASIGNADO ES INCORRECTO",
                    "NUMERO DE VEHICULO INCORRECTO",
                    JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (eliminarRegistro()) {
            JOptionPane.showMessageDialog(this, "MULTA ELIMINADA SATISFACTORIAMENTE",
                    "OK",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiar();
            limpiarTabla(tblResultado);

        } else {
            JOptionPane.showMessageDialog(this, "NO SE PUEDO ELIMINAR EL REGISTRO",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        super.dispose();
}//GEN-LAST:event_btnCancelarActionPerformed

    private void cmb2ParametroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmb2ParametroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmb2ParametroActionPerformed

    private void cmbCodigoMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCodigoMActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_cmbCodigoMActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox cmb2Parametro;
    private javax.swing.JComboBox cmbCodigoM;
    private javax.swing.JComboBox cmbEstado;
    private javax.swing.JComboBox cmbParametro;
    private org.jdesktop.swingx.JXDatePicker dpFechaFin;
    private org.jdesktop.swingx.JXDatePicker dpFechaIni;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblFecha1;
    private javax.swing.JLabel lblNumCoincidencias;
    private javax.swing.JPanel pnlDatos;
    private javax.swing.JTable tblResultado;
    private javax.swing.JTextField txtFechaB;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtNroUnidad;
    // End of variables declaration//GEN-END:variables

    /**
     * Realiza una pre-Busqueda de acuerdo al criterio de busqueda enviado para llenar
     * el 2do combo de busqueda con sus datos correspondientes.
     *
     * @param strCriterioBusqueda
     */
    private boolean preConsultaDeDatos(String strCriterioBusqueda) {
        String sql = null;
        cmb2Parametro.removeAllItems();
        if (strCriterioBusqueda.equals("N_UNIDAD")) {
            sql = "SELECT N_UNIDAD FROM VEHICULOS";
        } else if (strCriterioBusqueda.equals("COD_MULTA")) {
            sql = "SELECT COD_MULTA FROM COD_MULTAS";
        } else if (strCriterioBusqueda.equals("USUARIO")) {
            sql = "SELECT USUARIO FROM USUARIOS;";
        } else if (strCriterioBusqueda.equals("ESTADO")) {
            cmb2Parametro.addItem("Pendiente");
            cmb2Parametro.addItem("Pagada");
            return true;
        }
        try {
            rs = bd.ejecutarConsulta(sql);
            while (rs.next()) {
                cmb2Parametro.addItem(rs.getString(1));
            }
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "NO EXISTEN DATOS PARA EL CRITERIO DE BUSQUEDA SELECCIONADO",
                    "NO EXISTEN DATOS",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    /**
     * Realiza una busqueda de las multas asignadas, recibe como parametros dos criterios de busqueda
     *
     * @param nroUnidad
     * @param nroUnidad
     */
    private ArrayList<String[]> consultaDeMultasAsignadas(String strCriterio, String strValor, String fi, String ff) {

        String sql = null;

        ArrayList<String[]> rta = new ArrayList();


        if (strCriterio.equals("ESTADO")) {

            int intEstado = 0;
            if (strValor.equals("Pendiente")) {
                intEstado = 1;
            } else {
                intEstado = 2;
            }
            sql = "SELECT * FROM MULTAS WHERE " + strCriterio + " = " + intEstado;

        } else if (strCriterio.equals("FECHA")) {

            sql = "SELECT * FROM MULTAS WHERE FECHA BETWEEN '" + fi + "' AND '" + ff + "'";

        } else {
            sql = "SELECT * FROM MULTAS WHERE " + strCriterio + " = '" + strValor + "'";
        }


        try {
            rs = bd.ejecutarConsulta(sql);
            while (rs.next()) {
                String[] aux = new String[6];
                aux[0] = rs.getString("USUARIO");
                aux[1] = rs.getString("N_UNIDAD");
                aux[2] = rs.getString("FECHA");
                aux[3] = rs.getString("HORA");
                aux[4] = rs.getString("COD_MULTA");
                if (rs.getString("ESTADO").equals("1")) {
                    aux[5] = "Pendiente";
                } else {
                    aux[5] = "Pagada";
                }
                rta.add(aux);
            }
            return rta;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "NO EXISTEN MULTAS CON LOS CRITERIOS DE BUSQUEDA SELECCIONADOS",
                    "NO EXISTEN DATOS",
                    JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public void BuscarMultas(String strCriterio, String strValor, JTable resultado, String fi, String ff) {
        limpiarTabla(tblResultado);

        DefaultTableModel model = (DefaultTableModel) resultado.getModel();
        multas = consultaDeMultasAsignadas(strCriterio, strValor, fi, ff);
        int numMultas = multas.size();
        String msj;

        if (numMultas == 0) {
            //Deshabilitar botones de accion
            btnGuardar.setEnabled(false);
            btnEliminar.setEnabled(false);
            msj = "Se encontraron 0 coincidencias para [" + strValor + "]";
            
        } else {
            msj = "Se encontraron " + numMultas + " coincidencias para el valor de [" + strCriterio + "]";
            //Recorrer resultados y añadir filas

            for (String[] con : multas) {
                String[] fil = new String[6];
                fil[0] = con[0];
                fil[1] = con[1];
                fil[2] = con[2];
                fil[3] = con[3];
                fil[4] = con[4];
                fil[5] = con[5];
                model.addRow(fil);
            }
        }
        lblNumCoincidencias.setText(msj);
    }

    private void limpiarTabla(JTable tblAlimpiar) {
        // Se eliminan las filas de la consulta anterior (si existen)
        DefaultTableModel model = (DefaultTableModel) tblAlimpiar.getModel();
        int numRow = tblAlimpiar.getRowCount();

        for (int i = 0; i < numRow; i++) {
            model.removeRow(0);
        }
    }

    private void cargarAsignacionDeMultaSeleccionada(int intFila) {
        //Habilitar botones de accion
        btnGuardar.setEnabled(true);
        btnEliminar.setEnabled(true);
        bloquearArea(true);


        auxItemFila = multas.get(intFila);

        txtNroUnidad.setText(auxItemFila[1]);
        txtFechaB.setText(auxItemFila[2]);
        txtHora.setText(auxItemFila[3]);
        cmbCodigoM.setSelectedItem(auxItemFila[4]);
        cmbEstado.setSelectedItem(auxItemFila[5]);
    }

    private void limpiar() {
        txtNroUnidad.setText("");
        txtFechaB.setText("");
        txtHora.setText("");
        cmbCodigoM.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
        lblNumCoincidencias.setText("");
        bloquearArea(false);
    }

    private boolean eliminarRegistro() {
        String strFecha = auxItemFila[2];
        String strHora = auxItemFila[3];
        String strCodM = auxItemFila[4];

        String sql = "delete from MULTAS where COD_MULTA='" + strCodM + "' AND FECHA = '" + strFecha + "' AND HORA ='" + strHora + "'";
        System.out.println("consulta realizada");
        if (bd.ejecutarSentencia(sql)) {
            return true;
        }
        return false;
    }

    private boolean modificarRegistro(String nomUsuario, int intNroU, String strCodMulta, int intEstado) {
        String strFecha = auxItemFila[2];
        String strHora = auxItemFila[3];
        String strCodM = auxItemFila[4];
        String sql = "UPDATE MULTAS SET USUARIO= '" + nomUsuario + "', N_UNIDAD=" + intNroU
                + ", COD_MULTA='" + strCodMulta + "', ESTADO=" + intEstado
                + " WHERE COD_MULTA='" + strCodM + "' AND FECHA = '"
                + strFecha + "' AND HORA ='" + strHora + "'";
        System.out.println("consulta realizada");
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
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "NO EXISTEN AUN MULTAS REGISTRADAS",
                    "NO EXISTEN MULTAS",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void bloquearArea(boolean est) {
        txtNroUnidad.setEnabled(est);
        cmbCodigoM.setEnabled(est);
        cmbEstado.setEnabled(est);
        btnEliminar.setEnabled(est);
        btnGuardar.setEnabled(est);

    }
}
