/*
 * NewJPanel.java
 *
 * Created on 09/08/2010, 08:38:49 AM
 */
package interfaz.pruebas;

import BaseDatos.ConexionBase;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class modAsignarMultas extends javax.swing.JDialog {

    ConexionBase conec = new ConexionBase();
    ArrayList<String[]> multas;
    private ResourceBundle rb;
    private File Ffoto = null;
    private funcionesUtilidad funciones = new funcionesUtilidad();
    private String img;
    private String imgOriginal;
    private String idEmpresa;
    ConexionBase bd = new ConexionBase();
    ResultSet rs;
    Icon icError;
    Icon icOk;

    public modAsignarMultas(JFrame padre) {

        super(padre,"Busqueda de Vehículos");
        super.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());


        initComponents();
        cmb2Parametro.setVisible(false);
        txtFecha.setVisible(false);
        this.setVisible(true);
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
        lblEtiquetaImagen = new javax.swing.JLabel();
        lblBorrar = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        cmb2Parametro = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        txtNroUnidad = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbCodigoM = new javax.swing.JComboBox();
        cmbEstado = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtFechaB = new javax.swing.JTextField();
        txtHora = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        tblResultado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
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
        tblResultado.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblResultado.getColumnModel().getColumn(1).setResizable(false);
        tblResultado.getColumnModel().getColumn(1).setPreferredWidth(30);
        tblResultado.getColumnModel().getColumn(2).setResizable(false);
        tblResultado.getColumnModel().getColumn(2).setPreferredWidth(300);

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

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("ASIGNACION DE MULTAS");

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/rojo.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        lblEtiquetaImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

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

        jLabel3.setText("Nro. UNIDAD:");

        jLabel6.setText("CODIGO MULTA:");

        cmbCodigoM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione" }));
        cmbCodigoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCodigoMActionPerformed(evt);
            }
        });

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "Pendiente", "Pagada" }));

        jLabel7.setText("ESTADO:");

        jLabel4.setText("FECHA:");

        txtFechaB.setEditable(false);

        txtHora.setEditable(false);

        jLabel5.setText("HORA:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(23, 23, 23)
                            .addComponent(jLabel2)
                            .addGap(18, 18, 18)
                            .addComponent(cmbParametro, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cmb2Parametro, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBuscar))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGap(82, 82, 82)
                            .addComponent(lblNumCoincidencias, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(33, 33, 33)
                                        .addComponent(txtNroUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(9, 9, 9)
                                        .addComponent(cmbCodigoM, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(36, 36, 36)
                                        .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(23, 23, 23)
                                        .addComponent(txtFechaB, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(17, 17, 17)
                                        .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(180, 180, 180)
                                .addComponent(lblEtiquetaImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(345, 345, 345)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addComponent(btnGuardar)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbParametro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmb2Parametro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(lblNumCoincidencias, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(lblEtiquetaImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtNroUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(cmbCodigoM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtFechaB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancelar)))
                .addGap(252, 252, 252)
                .addComponent(lblBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        limpiar();
        if(cmbParametro.getSelectedIndex()>0){
            if(!cmbParametro.getSelectedItem().equals("FECHA") && !cmb2Parametro.getSelectedItem().equals("")){
                BuscarMultas(cmbParametro.getSelectedItem().toString(), cmb2Parametro.getSelectedItem().toString(), tblResultado);
            }else if(cmbParametro.getSelectedItem().equals("FECHA") && !txtFecha.getText().equals("")){
                BuscarMultas(cmbParametro.getSelectedItem().toString(), txtFecha.getText(), tblResultado);
            }else
                JOptionPane.showMessageDialog(this, "NO HA SELECCIONADO UN CRITERIO DE BUSQUEDA",
                    "SELECCIONE UN CRITERIO DE BUSQUEDA",
                    JOptionPane.ERROR_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(this, "NO HA SELECCIONADO UN CRITERIO DE BUSQUEDA",
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
        //txtValorABuscar.setText("");
        //limpiarCajas();
        //limpiarTabla(tblResultado);
        if(!cmbParametro.getSelectedItem().equals("FECHA")){
            preConsultaDeDatos(cmbParametro.getSelectedItem().toString());
            cmb2Parametro.setVisible(true);
            txtFecha.setVisible(false);
        } else{
            txtFecha.setVisible(false);
            cmb2Parametro.setVisible(false);
        }
    }//GEN-LAST:event_cmbParametroActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
      /*  if (conec.eliminarVehiculo(lblPlaca.getText())) {

            JOptionPane.showMessageDialog(this, "VEHICULO ELIMINADO",
                    "OK",
                    JOptionPane.INFORMATION_MESSAGE,
                    icOk);
            //limpiarCajas();
           // limpiarTabla(tblResultado);

        } else {
            JOptionPane.showMessageDialog(this, "NO SE PUEDO ELIMINAR",
                    "OK",
                    JOptionPane.INFORMATION_MESSAGE,
                    icError);
        }*/
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBorrar;
    private javax.swing.JLabel lblEtiquetaImagen;
    private javax.swing.JLabel lblNumCoincidencias;
    private javax.swing.JTable tblResultado;
    private javax.swing.JTextField txtFecha;
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
        String sql=null;
        cmb2Parametro.removeAllItems();
        if(strCriterioBusqueda.equals("N_UNIDAD")){
            sql = "SELECT N_UNIDAD FROM VEHICULOS";
        }else if(strCriterioBusqueda.equals("COD_MULTA")){
            sql="SELECT COD_MULTA FROM COD_MULTAS";
        }else if(strCriterioBusqueda.equals("USUARIO")){
            sql="SELECT USUARIO FROM USUARIOS;";
        }else if(strCriterioBusqueda.equals("ESTADO")){
            cmb2Parametro.addItem("Pendiente");
            cmb2Parametro.addItem("Pagado");
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
    private ArrayList<String[]> consultaDeMultasAsignadas(String strCriterio, String strValor) {
        String sql=null;
        ArrayList<String[]> rta = new ArrayList();
        if(!strCriterio.equals("ESTADO")){
            sql="SELECT * FROM MULTAS WHERE "+ strCriterio + " = "+strValor;
        }else{
            int intEstado=0;
            if(strValor.equals("Pendiente"))
                intEstado=1;
            else
                intEstado=2;
            sql="SELECT * FROM MULTAS WHERE "+ strCriterio + " = "+intEstado;
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
                aux[5] = rs.getString("ESTADO");
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

    public void BuscarMultas(String strCriterio, String strValor,JTable resultado){
        limpiarTabla(tblResultado);

        DefaultTableModel model = (DefaultTableModel) resultado.getModel();
        multas = consultaDeMultasAsignadas(strCriterio, strValor);
        int numConductores = multas.size();
        String msj;

        if (numConductores == 0) {
            //Deshabilitar botones de accion
            btnGuardar.setEnabled(false);
            btnEliminar.setEnabled(false);
            msj = "Se encontraron 0 coincidencias para [" + strCriterio + "]";
        } else {
            msj = "Se encontraron " + numConductores + " coincidencias para [" + strCriterio + "]";
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


        String[] aux = multas.get(intFila);

        txtNroUnidad.setText(aux[1]);
        txtFechaB.setText(aux[2]);
        txtHora.setText(aux[3]);
        cmbCodigoM.setSelectedItem(aux[4]);
        if(cmbEstado.getSelectedItem().equals("Pendiente"))
            cmbEstado.setSelectedIndex(1);
        else
            cmbEstado.setSelectedIndex(2);
      
    }
    private void limpiar(){
        txtNroUnidad.setText("");
        txtFechaB.setText("");
        cmbCodigoM.setSelectedIndex(0);
        cmbEstado.setSelectedIndex(0);
    }

}
