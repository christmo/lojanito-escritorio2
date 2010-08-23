/*
 * NewJPanel.java
 *
 * Created on 09/08/2010, 08:38:49 AM
 */
package interfaz;

import BaseDatos.ConexionBase;
import java.io.File;
import java.sql.ResultSet;
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
public class modVehiculo extends javax.swing.JDialog {

    ConexionBase bd;
    ArrayList<String[]> vehiculos;
    private ResourceBundle rb;
    private File Ffoto = null;
    private funcionesUtilidad funciones = new funcionesUtilidad();
    private String img;
    private String imgOriginal;
    private String idEmpresa;
    ResultSet rs;
    Icon icError;
    Icon icOk;
    private String[] sesion;

    public modVehiculo(JFrame padre, String[] ses, ConexionBase con) {
        super(padre, "Busqueda de Vehículos");
        super.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());


        this.sesion = ses;
        this.bd = con;

        initComponents();
        leerProperties();
        cargarConductores(cmbConductor);
        cargarConductores(cmbConductorAux);
        icError = new ImageIcon(getClass().getResource("/interfaz/iconos/error.png"));
        icOk = new ImageIcon(getClass().getResource("/interfaz/iconos/correcto.png"));
        this.idEmpresa = ses[1];

        limpiarCajas();
        preBuscar(cmbParametro.getSelectedIndex(), txtCoincidencia.getText().toUpperCase());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblResultado = new javax.swing.JTable();
        cmbParametro = new javax.swing.JComboBox();
        btnBuscar = new javax.swing.JButton();
        txtCoincidencia = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnCargarImagen = new javax.swing.JButton();
        lblFoto = new javax.swing.JLabel();
        lblNumCoincidencias = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblPlaca = new javax.swing.JLabel();
        txtNUnidad = new javax.swing.JTextField();
        txtPropietario = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtInfor = new javax.swing.JTextArea();
        cmbConductor = new javax.swing.JComboBox();
        cmbConductorAux = new javax.swing.JComboBox();
        lblEtiquetaImagen = new javax.swing.JLabel();
        spAño = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtNumChasis = new javax.swing.JTextField();
        txtNumMotor = new javax.swing.JTextField();
        txtMarca = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        lblBorrar = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();

        tblResultado.setFont(new java.awt.Font("Tahoma", 0, 14));
        tblResultado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PLACA", "NUM UNIDAD", "PROPIETARIO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
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

        cmbParametro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PLACA", "NUM UNIDAD" }));
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

        txtCoincidencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCoincidenciaKeyPressed(evt);
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

        btnCargarImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/cargar.png"))); // NOI18N
        btnCargarImagen.setText("CAMBIAR");
        btnCargarImagen.setEnabled(false);
        btnCargarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarImagenActionPerformed(evt);
            }
        });

        lblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoto.setToolTipText("Foto del Vehículo");
        lblFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        lblFoto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("VEHICULOS");

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/rojo.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jLabel1.setText("PLACA :");

        jLabel3.setText("NUM UNIDAD :");

        jLabel4.setText("CONDUCTOR :");

        jLabel5.setText("CONDUCTOR AUX :");

        jLabel11.setText("PROPIETARIO :");

        jLabel12.setText("INFOR. ADICIONAL :");

        lblPlaca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPlaca.setBorder(new org.jdesktop.swingx.border.DropShadowBorder());

        txtInfor.setColumns(20);
        txtInfor.setRows(5);
        jScrollPane2.setViewportView(txtInfor);

        cmbConductor.setEnabled(false);
        cmbConductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbConductorActionPerformed(evt);
            }
        });

        cmbConductorAux.setEnabled(false);

        lblEtiquetaImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        spAño.setModel(new javax.swing.SpinnerNumberModel(2000, 1999, 2222, 1));

        jLabel9.setText("AÑO :");

        txtModelo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtModeloFocusLost(evt);
            }
        });

        jLabel13.setText("MODELO :");

        jLabel14.setText("NUM MOTOR :");

        jLabel15.setText("NUM CHASIS :");

        txtNumChasis.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumChasisFocusLost(evt);
            }
        });

        txtNumMotor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumMotorFocusLost(evt);
            }
        });

        txtMarca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMarcaFocusLost(evt);
            }
        });

        jLabel16.setText("MARCA :");

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("CERRAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(186, 186, 186)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(cmbParametro, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtCoincidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(lblNumCoincidencias, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(97, 97, 97)
                        .addComponent(jLabel1)
                        .addGap(6, 6, 6)
                        .addComponent(lblPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtNUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(lblEtiquetaImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(31, 31, 31)
                                    .addComponent(jLabel4))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(8, 8, 8)
                                    .addComponent(jLabel5))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(26, 26, 26)
                                    .addComponent(jLabel11))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(53, 53, 53)
                                    .addComponent(jLabel13))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(33, 33, 33)
                                    .addComponent(jLabel14))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(33, 33, 33)
                                    .addComponent(jLabel15))
                                .addComponent(jLabel12))
                            .addComponent(jLabel16))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbConductorAux, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabel9)
                                .addGap(4, 4, 4)
                                .addComponent(spAño, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumChasis, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnCargarImagen)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(185, 185, 185)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(60, 60, 60)
                        .addComponent(btnGuardar))
                    .addComponent(lblBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(cmbParametro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(txtCoincidencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(lblNumCoincidencias, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1))
                    .addComponent(lblPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel3))
                    .addComponent(txtNUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lblEtiquetaImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel4)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel5)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel11)
                        .addGap(17, 17, 17)
                        .addComponent(jLabel13)
                        .addGap(68, 68, 68)
                        .addComponent(jLabel14)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel12))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbConductor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(cmbConductorAux, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(txtPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel9))
                            .addComponent(spAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addGap(18, 18, 18)
                        .addComponent(txtNumMotor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(txtNumChasis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(btnCargarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancelar)))
                .addGap(18, 18, 18)
                .addComponent(lblBorrar, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        limpiarCajas();
        preBuscar(cmbParametro.getSelectedIndex(), txtCoincidencia.getText().toUpperCase());
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblResultadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblResultadoMouseClicked
        int fila = tblResultado.rowAtPoint(evt.getPoint());
        int columna = tblResultado.columnAtPoint(evt.getPoint());

        if ((fila > -1) && (columna > -1)) {
            cargarVehiculoSeleccionado(fila);
        }

    }//GEN-LAST:event_tblResultadoMouseClicked

    private void txtCoincidenciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCoincidenciaKeyPressed
        if (evt.getKeyCode() == 10) {
            limpiarCajas();
            preBuscar(cmbParametro.getSelectedIndex(), txtCoincidencia.getText());
        }
    }//GEN-LAST:event_txtCoincidenciaKeyPressed

    private void tblResultadoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblResultadoKeyPressed
        int fila;

        if (evt.getKeyCode() == 38) {
            if (tblResultado.getSelectedRow() != 0) {
                fila = tblResultado.getSelectedRow() - 1;
            } else {
                fila = tblResultado.getSelectedRow();
            }
            cargarVehiculoSeleccionado(fila);
        }

        if (evt.getKeyCode() == 40) {
            if ((tblResultado.getRowCount() - 1) == tblResultado.getSelectedRow()) {
                fila = tblResultado.getSelectedRow();
            } else {
                fila = tblResultado.getSelectedRow() + 1;
            }
            cargarVehiculoSeleccionado(fila);
        }
    }//GEN-LAST:event_tblResultadoKeyPressed

    private void cmbParametroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbParametroActionPerformed
        txtCoincidencia.setText("");
        limpiarCajas();
        limpiarTabla(tblResultado);
    }//GEN-LAST:event_cmbParametroActionPerformed

    private void btnCargarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarImagenActionPerformed
        Ffoto = funciones.cargarImagen(lblEtiquetaImagen, lblFoto);
    }//GEN-LAST:event_btnCargarImagenActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
//Comprobar que los campos obligatorios tengan valor.

        icError = new ImageIcon(getClass().getResource("/interfaz/iconos/error.png"));
        String nunidad = txtNUnidad.getText();

        if (!(nunidad.equals("")) || (funciones.isNumeric(nunidad))) {
            if (!bd.existeUnidad(lblPlaca.getText(), Integer.parseInt(nunidad))) {

                String nomConductor = cmbConductor.getSelectedItem().toString();
                if (!nomConductor.equals("< NO ASIGNADO >")) {

                    boolean canCoincidencias = bd.conductorAsignado(nomConductor, lblPlaca.getText());
                    if (!canCoincidencias) {

                        String nomConductorAux = cmbConductorAux.getSelectedItem().toString();
                        if (!nomConductorAux.equals(nomConductor)) {

                            String imgActual = lblEtiquetaImagen.getText();

                            System.out.println("[" + imgOriginal + "]");
                            System.out.println("[" + imgActual + "]");

                            if (!imgOriginal.equals(imgActual) && !(imgActual.equals("defaultveh.png"))) {
                                imgActual = funciones.guardarImagen(Ffoto, rb.getString("dirImgVehiculos"));
                            }

                            if (nomConductorAux.equals("< NO ASIGNADO >")) {
                                nomConductorAux = "";
                            }
                            boolean salida = bd.actualizarVehiculo(lblPlaca.getText(),
                                    Integer.parseInt(nunidad),
                                    idEmpresa,
                                    nomConductor,
                                    nomConductorAux,
                                    txtModelo.getText(),
                                    Integer.parseInt(spAño.getValue().toString()),
                                    txtPropietario.getText(),
                                    txtInfor.getText(),
                                    imgActual,
                                    txtMarca.getText(),
                                    txtNumMotor.getText(),
                                    txtNumChasis.getText());

                            if (salida) {
                                JOptionPane.showMessageDialog(this, "DATOS ACTUALIZADOS",
                                        "OK",
                                        JOptionPane.ERROR_MESSAGE,
                                        icOk);
                                limpiarCajas();
                                limpiarTabla(tblResultado);
                                super.dispose();
                            } else {
                                JOptionPane.showMessageDialog(this, "NO SE PUDO ACTUALIZAR LOS DATOS",
                                        "ACTUALIZAR",
                                        JOptionPane.ERROR_MESSAGE,
                                        icError);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "CONDUCTOR Y CONDUCTOR AUXILIAR \n NO PUEDEN SER IGUALES",
                                    "ERROR COINCIDENCIA",
                                    JOptionPane.ERROR_MESSAGE,
                                    icError);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "EL CONDUCTOR YA PERTENECE A OTRO VEHÍCULO",
                                "ERROR CONDUCTOR",
                                JOptionPane.ERROR_MESSAGE,
                                icError);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "DEBE ESPECIFICAR UN CONDUCTOR",
                            "ERROR NOM CONDUCTOR",
                            JOptionPane.ERROR_MESSAGE,
                            icError);
                }
            } else {
                JOptionPane.showMessageDialog(this, "EL NUMERO DE UNIDAD YA EXISTE",
                        "ERROR NUM UNIDAD",
                        JOptionPane.ERROR_MESSAGE,
                        icError);
            }
        } else {
            JOptionPane.showMessageDialog(this, "REVISE EL NUMERO DE UNIDAD",
                    "ERROR NUM UNIDAD",
                    JOptionPane.ERROR_MESSAGE,
                    icError);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (bd.eliminarVehiculo(lblPlaca.getText())) {

            JOptionPane.showMessageDialog(this, "VEHICULO ELIMINADO",
                    "OK",
                    JOptionPane.INFORMATION_MESSAGE,
                    icOk);
            limpiarCajas();
            limpiarTabla(tblResultado);
            Principal.redimencionarTablaVehiculos();
        } else {
            JOptionPane.showMessageDialog(this, "NO SE PUDO ELIMINAR",
                    "OK",
                    JOptionPane.INFORMATION_MESSAGE,
                    icError);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtModeloFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtModeloFocusLost
        txtModelo.setText(txtModelo.getText().toUpperCase());
}//GEN-LAST:event_txtModeloFocusLost

    private void txtNumChasisFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumChasisFocusLost
        txtNumChasis.setText(txtNumChasis.getText().toUpperCase());
}//GEN-LAST:event_txtNumChasisFocusLost

    private void txtNumMotorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumMotorFocusLost
        txtNumMotor.setText(txtNumMotor.getText().toUpperCase());
}//GEN-LAST:event_txtNumMotorFocusLost

    private void cmbConductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbConductorActionPerformed
    }//GEN-LAST:event_cmbConductorActionPerformed

    private void txtMarcaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMarcaFocusLost
        txtMarca.setText(txtMarca.getText().toUpperCase());
}//GEN-LAST:event_txtMarcaFocusLost

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        super.dispose();
}//GEN-LAST:event_btnCancelarActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCargarImagen;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox cmbConductor;
    private javax.swing.JComboBox cmbConductorAux;
    private javax.swing.JComboBox cmbParametro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBorrar;
    private javax.swing.JLabel lblEtiquetaImagen;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JLabel lblNumCoincidencias;
    private javax.swing.JLabel lblPlaca;
    private javax.swing.JSpinner spAño;
    private javax.swing.JTable tblResultado;
    private javax.swing.JTextField txtCoincidencia;
    private javax.swing.JTextArea txtInfor;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNUnidad;
    private javax.swing.JTextField txtNumChasis;
    private javax.swing.JTextField txtNumMotor;
    private javax.swing.JTextField txtPropietario;
    // End of variables declaration//GEN-END:variables

    /**
     * Busca los vehículos que coincidan con el parámetro
     * de búsqueda
     *
     * @param id
     * @param param
     * @param resultado
     */
    private void buscarVehiculos(int id, String param, JTable resultado) {
        // Se eliminan las filas de la consulta anterior (si existen)
        DefaultTableModel model = (DefaultTableModel) resultado.getModel();
        limpiarTabla(resultado);

        vehiculos = bd.buscarVehiculos(param, id);
        int numConductores = vehiculos.size();
        String msj;

        if (numConductores == 0) {
            //Deshabilitar botones de accion
            btnCargarImagen.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnEliminar.setEnabled(false);
            cmbConductor.setEnabled(false);
            cmbConductorAux.setEnabled(false);

            msj = "Se encontraron 0 coincidencias para [" + param + "]";
        } else {
            msj = "Se encontraron " + numConductores + " coincidencias para [" + param + "]";
            //Recorrer resultados y añadir filas

            for (String[] con : vehiculos) {
                String[] fil = new String[3];
                fil[0] = con[0];
                fil[1] = con[1];
                fil[2] = con[2];
                model.addRow(fil);
            }
        }
        lblNumCoincidencias.setText(msj);
    }

    /**
     * Carga los datos de los conductores en los recuadros
     * de la parte inferior
     * @param id
     */
    private void cargarVehiculoSeleccionado(int id) {
        //Habilitar botones de accion
        btnCargarImagen.setEnabled(true);
        btnGuardar.setEnabled(true);
        btnEliminar.setEnabled(true);
        cmbConductor.setEnabled(true);
        cmbConductorAux.setEnabled(true);

        String[] aux = vehiculos.get(id);

        lblPlaca.setText(aux[0]);
        txtNUnidad.setText(aux[1]);
        txtModelo.setText(aux[6]);
        txtPropietario.setText(aux[8]);
        txtInfor.setText(aux[9]);

        txtNumChasis.setText(aux[13]);
        txtNumMotor.setText(aux[12]);
        txtMarca.setText(aux[11]);

        //Año
        if (aux[7] != null) {
            spAño.setValue(Integer.valueOf(aux[7]));
        } else {
            spAño.setValue(1999);
        }


        //CONDUCTORES
        String name;
        if (aux[3] == null || aux[3].equals("")) {
            cmbConductor.setSelectedItem("< NO ASIGNADO >");
        } else {
            name = bd.getNombreConductor(Integer.parseInt(aux[3]));
            if (name != null) {
                cmbConductor.setSelectedItem(name);
            } else {
                cmbConductor.setSelectedItem("< NO ASIGNADO >");
            }
        }

        //CONDUCTORES AUXILIARES
        if (aux[4] == null || aux[4].equals("")) {
            cmbConductorAux.setSelectedItem("< NO ASIGNADO >");
        } else {
            name = bd.getNombreConductor(Integer.parseInt(aux[4]));
            if (name != null) {
                cmbConductorAux.setSelectedItem(name);
            } else {
                cmbConductorAux.setSelectedItem("< NO ASIGNADO >");
            }

        }

        //CARGA DE IMÁGENES
        img = aux[10];
        imgOriginal = img;

        if (aux[10] == null || aux[10].equals("") || aux[10].equals("defaultveh.png")) {

            img = "defaultveh.png";
            imgOriginal = img;
            Icon fot = new ImageIcon(getClass().getResource("/interfaz/iconos/defaultveh.png"));
            lblEtiquetaImagen.setText(img);
            lblFoto.setIcon(fot);
            lblFoto.setText("");

        } else {

            Icon fot = new ImageIcon(rb.getString("dirImgVehiculos") + "\\" + aux[10]);
            img = aux[10];
            imgOriginal = img;
            if (fot.getIconWidth() == -1) {

                lblEtiquetaImagen.setText(img);
                lblFoto.setText("IMAGEN NO ENCONTRADA");
                lblFoto.setIcon(null);
                lblEtiquetaImagen.setText("defaultveh.png");
            } else {
                lblEtiquetaImagen.setText(img);
                lblFoto.setIcon(fot);
                lblFoto.setText("");
            }
        }
    }

    /**
     * Carga el archivo de propiedades del sistema
     */
    private void leerProperties() {
        rb = ResourceBundle.getBundle("configuracion.configsystem");
    }

    /**
     * Limpia contenido de las cajas
     */
    private void limpiarCajas() {
        //Deshabilitar botones de accion
        btnCargarImagen.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnEliminar.setEnabled(false);
        cmbConductor.setEnabled(false);
        cmbConductorAux.setEnabled(false);

        lblPlaca.setText("");
        lblNumCoincidencias.setText("");
        txtNUnidad.setText("");
        txtInfor.setText("");
        txtPropietario.setText("");

        txtMarca.setText("");
        txtNumChasis.setText("");
        txtNumMotor.setText("");
        txtModelo.setText("");
        spAño.setValue(1995);

        lblFoto.setIcon(null);
        lblFoto.setText("");
        lblEtiquetaImagen.setText("");
    }

    /**
     * Determina si la cédula ingresada es válida antes
     * de realizar la búsqueda
     */
    private void preBuscar(int id, String parametro) {

        if ((cmbParametro.getSelectedIndex() == 0)) {

            buscarVehiculos(id, parametro, tblResultado);

        } else if ((cmbParametro.getSelectedIndex() == 1) && (funciones.isNumeric(parametro))) {

            buscarVehiculos(id, parametro, tblResultado);

        } else {

            JOptionPane.showMessageDialog(this, "REVISE EL NUMERO DE UNIDAD",
                    "ERROR NUM UNIDAD",
                    JOptionPane.INFORMATION_MESSAGE,
                    icError);

        }
    }

    /**
     * Elimina todas las filas de la tabla
     * @param tblAlimpiar
     */
    private void limpiarTabla(JTable tblAlimpiar) {
        // Se eliminan las filas de la consulta anterior (si existen)
        DefaultTableModel model = (DefaultTableModel) tblAlimpiar.getModel();
        int numRow = tblAlimpiar.getRowCount();

        for (int i = 0; i < numRow; i++) {
            model.removeRow(0);
        }
    }

    /**
     * Actualiza los datos del Conductor con los datos
     * ingresados.
     *
     * @param ced
     * @param nom
     * @param dir
     * @param numc
     * @param tipos
     * @param estcivil
     * @param con
     * @param mail
     * @param foto
     * @return
     */
    private boolean guardarRegistro(String ced,
            String nom,
            String dir,
            String numc,
            String tipos,
            String estcivil,
            String con,
            String mail) {

        String imgName;
        if (img.equals("defaultcon.jpg")) {
            imgName = "defaultcon.jpg";
        } else {
            imgName = funciones.guardarImagen(Ffoto, rb.getString("dirImgConductores"));
        }

        if (imgName != null) {

            String sql = "UPDATE CONDUCTORES SET NOMBRE_APELLIDO_CON = '" + nom + "', "
                    + "DIRECCION_CON = '" + dir + "', NUM_CASA_CON = '" + numc + "', "
                    + "TIPO_SANGRE = '" + tipos + "', ESTADO_CIVIL = '" + estcivil + "', "
                    + "CONYUGE = '" + con + "', MAIL = '" + mail + "', FOTO = '" + imgName + "' "
                    + "WHERE CEDULA_CONDUCTOR = '" + ced + "'";

            if (bd.ejecutarSentencia(sql)) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * Carga todos los conductores existentes
     * en la BD para relacionarlos con el
     * vehículo  (1) Auxiliares  (0) Conductores
     * @param condu ComboBox en donde se cargarán
     * @id Identificar del combo a llenar
     */
    private void cargarConductores(JComboBox condu) {

        ArrayList<String[]> conductores = bd.buscarConductores("", 1);
        String[] nomConductores = new String[conductores.size() + 1];
        int i = 1;

        nomConductores[0] = "< NO ASIGNADO >";
        for (String[] name : conductores) {
            nomConductores[i] = name[1];
            i++;
        }
        condu.setModel(new javax.swing.DefaultComboBoxModel(nomConductores));
    }
}
