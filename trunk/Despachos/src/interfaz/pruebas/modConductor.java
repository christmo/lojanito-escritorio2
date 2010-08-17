/*
 * NewJPanel.java
 *
 * Created on 09/08/2010, 08:38:49 AM
 */
package interfaz.pruebas;

import BaseDatos.ConexionBase;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class modConductor extends javax.swing.JDialog {

    ConexionBase conec = new ConexionBase();
    ArrayList<String[]> conductores;
    private ResourceBundle rb;
    private File Ffoto = null;
    private funcionesUtilidad funciones = new funcionesUtilidad();
    private String img;
    ConexionBase bd = new ConexionBase();
    ResultSet rs;

    /** Creates new form NewJPanel */
    public modConductor(JFrame padre) {
        super(padre,"Búsqueda de Conductores");
        super.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());

        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblResultado = new javax.swing.JTable();
        cmbParametro = new javax.swing.JComboBox();
        btnBuscar = new javax.swing.JButton();
        txtCoincidencia = new javax.swing.JTextField();
        txtNumCasa = new javax.swing.JTextField();
        txtTipoSangre = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        txtConyuge = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtCedula = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        btnCargarImagen = new javax.swing.JButton();
        txtNomApe = new javax.swing.JTextField();
        txtEstadoCivil = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lblFoto = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblNumCoincidencias = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblEtiquetaImagen = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        tblResultado.setFont(new java.awt.Font("Tahoma", 0, 14));
        tblResultado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cédula", "Nombre y Apellido", "Direccion"
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
        tblResultado.getColumnModel().getColumn(0).setPreferredWidth(45);
        tblResultado.getColumnModel().getColumn(1).setResizable(false);
        tblResultado.getColumnModel().getColumn(1).setPreferredWidth(300);
        tblResultado.getColumnModel().getColumn(2).setResizable(false);
        tblResultado.getColumnModel().getColumn(2).setPreferredWidth(120);

        cmbParametro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CEDULA", "NOMBRE" }));
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

        txtNumCasa.setToolTipText("Domicilio del Conductor");

        txtTipoSangre.setToolTipText("Tipo de Sangre");

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        txtConyuge.setToolTipText("En caso de ser Casado, sino dejar en blanco");

        jLabel7.setText("Cónyuge:");

        jLabel3.setText("Dirección:");

        jLabel6.setText("Estado Civil:");

        txtemail.setToolTipText("Si no posee dejar en blanco");

        jLabel8.setText("E-mail: ");

        jLabel1.setText("Cedula:");

        txtCedula.setColumns(10);
        txtCedula.setEditable(false);
        txtCedula.setToolTipText("Cedula (sin guión)");

        jLabel9.setText("Foto:");

        txtDireccion.setToolTipText("Número de Casa");

        btnCargarImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/cargar.png"))); // NOI18N
        btnCargarImagen.setText("CARGAR");
        btnCargarImagen.setEnabled(false);
        btnCargarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarImagenActionPerformed(evt);
            }
        });

        txtNomApe.setToolTipText("Nombres y Apellidos del Conductor");

        txtEstadoCivil.setToolTipText("Estado Civil");

        jLabel5.setText("Tipo de Sangre:");

        lblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoto.setToolTipText("Foto del Conductor");
        lblFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        lblFoto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel4.setText("Num. Casa:");

        jLabel2.setText("Nombres y Apellido:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("CONDUCTORES");

        lblEtiquetaImagen.setForeground(new java.awt.Color(51, 51, 255));

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblNumCoincidencias, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(cmbParametro, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtCoincidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8)
                            .addComponent(jLabel1)
                            .addComponent(jLabel9))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblEtiquetaImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCargarImagen))
                            .addComponent(txtEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTipoSangre, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtConyuge, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDireccion)
                            .addComponent(txtNomApe))
                        .addGap(18, 18, 18)
                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(btnEliminar)
                .addGap(35, 35, 35)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(txtNomApe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel4))
                            .addComponent(txtNumCasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel5))
                            .addComponent(txtTipoSangre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel6))
                            .addComponent(txtEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel7))
                            .addComponent(txtConyuge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel8))
                            .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9)
                                    .addComponent(lblEtiquetaImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCargarImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnCancelar)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        limpiarCajas();
        preBuscar(cmbParametro.getSelectedIndex(), txtCoincidencia.getText());
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblResultadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblResultadoMouseClicked
        int fila = tblResultado.rowAtPoint(evt.getPoint());
        int columna = tblResultado.columnAtPoint(evt.getPoint());

        if ((fila > -1) && (columna > -1)) {
            cargarConductorSeleccionado(fila);
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
            cargarConductorSeleccionado(fila);
        }

        if (evt.getKeyCode() == 40) {
            if ((tblResultado.getRowCount() - 1) == tblResultado.getSelectedRow()) {
                fila = tblResultado.getSelectedRow();
            } else {
                fila = tblResultado.getSelectedRow() + 1;
            }
            cargarConductorSeleccionado(fila);
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

        if (txtCedula.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "FALTA EL NUMERO DE CEDULA",
                    "ERROR CEDULA",
                    JOptionPane.ERROR_MESSAGE);
        } else if (txtNomApe.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "NO HA ESPECIFICADO UN NOMBRE",
                    "ERROR NOMBRES",
                    JOptionPane.ERROR_MESSAGE);
        } else if (txtDireccion.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "NO HA ESPECIFICADO UNA DIRECCION",
                    "ERROR DIRECCION",
                    JOptionPane.ERROR_MESSAGE);
        } else if (lblEtiquetaImagen.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "SELECCIONE LA FOTO DEL CONDUCTOR",
                    "ERROR FOTOGRAFÍA",
                    JOptionPane.ERROR_MESSAGE);
        } else if (!funciones.isEmail(txtemail.getText()) && txtemail.getText().length()>0){
                    JOptionPane.showMessageDialog(this, "e-Mail no válido",
                            "e-mail",
                            JOptionPane.ERROR_MESSAGE);
                }else {
            boolean rta = guardarRegistro(txtCedula.getText(),
                    txtNomApe.getText(),
                    txtDireccion.getText(),
                    txtNumCasa.getText(),
                    txtTipoSangre.getText(),
                    txtEstadoCivil.getText(),
                    txtConyuge.getText(),
                    txtemail.getText());
            if (rta) {
                JOptionPane.showMessageDialog(this, "DATOS ACTUALIZADOS",
                        "LISTO",
                        JOptionPane.INFORMATION_MESSAGE);
                limpiarCajas();
                super.dispose();

            } else {
                JOptionPane.showMessageDialog(this, "NO SE PUEDO ACTUALIZAR LOS DATOS",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed

        Icon ic;

        if (conec.eliminarConductor(txtCedula.getText())) {

            ic = new ImageIcon(getClass().getResource("/interfaz/iconos/correcto.png"));

            JOptionPane.showMessageDialog(this, "CONDUCTOR ELIMINADO",
                    "OK",
                    JOptionPane.INFORMATION_MESSAGE,
                    ic);
            limpiarCajas();
            limpiarTabla(tblResultado);

        } else {
            ic = new ImageIcon(getClass().getResource("/interfaz/iconos/incorrecto.png"));
            JOptionPane.showMessageDialog(this, "NO SE PUEDO ELIMINAR",
                    "OK",
                    JOptionPane.INFORMATION_MESSAGE,
                    ic);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        super.dispose();
}//GEN-LAST:event_btnCancelarActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCargarImagen;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox cmbParametro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEtiquetaImagen;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JLabel lblNumCoincidencias;
    private javax.swing.JTable tblResultado;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtCoincidencia;
    private javax.swing.JTextField txtConyuge;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEstadoCivil;
    private javax.swing.JTextField txtNomApe;
    private javax.swing.JTextField txtNumCasa;
    private javax.swing.JTextField txtTipoSangre;
    private javax.swing.JTextField txtemail;
    // End of variables declaration//GEN-END:variables

    /**
     * Consulta en el BD todos los conductores que coincidan
     * con el parámetro de busqueda y los ubica en la tabla
     * @param id Identificador de Cedula(0) o Nombre(1)
     * @param param Condición de búsqueda
     * @param resultado JTable en donde se cargaran los resultados
     */
    private void buscarConductores(int id, String param, JTable resultado) {
        // Se eliminan las filas de la consulta anterior (si existen)
        DefaultTableModel model = (DefaultTableModel) resultado.getModel();
        limpiarTabla(resultado);

        conductores = conec.buscarConductores(param, id);
        int numConductores = conductores.size();
        String msj;

        if (numConductores == 0) {
            //Deshabilitar botones de accion
            btnCargarImagen.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnEliminar.setEnabled(false);

            msj = "Se encontraron 0 coincidencias para [" + param + "]";
        } else {
            msj = "Se encontraron " + numConductores + " coincidencias para [" + param + "]";
            //Recorrer resultados y añadir filas

            for (String[] con : conductores) {
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
    private void cargarConductorSeleccionado(int id) {
        //Habilitar botones de accion
        btnCargarImagen.setEnabled(true);
        btnGuardar.setEnabled(true);
        btnEliminar.setEnabled(true);

        String[] aux = conductores.get(id);

        txtCedula.setText(aux[0]);
        txtNomApe.setText(aux[1]);
        txtDireccion.setText(aux[2]);
        txtNumCasa.setText(aux[3]);
        txtTipoSangre.setText(aux[4]);
        txtEstadoCivil.setText(aux[5]);
        txtConyuge.setText(aux[6]);
        txtemail.setText(aux[7]);
        lblEtiquetaImagen.setText(aux[8]);

        img = aux[8];

        leerProperties();

        if (aux[8] == null || aux[8].equals("")) {

            //Icon fot = new ImageIcon(rb.getString("dirImgConductores") + "\\default.jpg");

            Icon fot = new ImageIcon(getClass().getResource("/interfaz/iconos/defaultcon.jpg"));
            lblFoto.setIcon(fot);
            lblEtiquetaImagen.setText("default.jpg");
            lblFoto.setText("");
        } else {

            Icon fot = new ImageIcon(rb.getString("dirImgConductores") + "\\" + aux[8]);

            if (fot.getIconWidth() == -1) {
                //fot = new ImageIcon(rb.getString("dirImgConductores") + "\\default.jpg");
                //fot = new ImageIcon(getClass().getResource("/interfaz/iconos/defaultcon.jpg"));
                lblFoto.setText("IMAGEN NO ENCONTRADA");
                lblFoto.setIcon(null);
            } else {
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

        txtCedula.setText("");
        txtNomApe.setText("");
        txtDireccion.setText("");
        txtNumCasa.setText("");
        txtTipoSangre.setText("");
        txtEstadoCivil.setText("");
        txtConyuge.setText("");
        txtemail.setText("");
        lblEtiquetaImagen.setText("");
        lblFoto.setIcon(null);
    }

    /**
     * Determina si la cédula ingresada es válida antes
     * de realizar la búsqueda
     */
    private void preBuscar(int id, String parametro) {
        funcionesUtilidad objFun = new funcionesUtilidad();


        if ((cmbParametro.getSelectedIndex() == 0) && (objFun.esCedulaValida(parametro))) {
            buscarConductores(id, parametro, tblResultado);
        } else if (cmbParametro.getSelectedIndex() == 1) {
            buscarConductores(id, parametro, tblResultado);
        } else {
            JOptionPane.showMessageDialog(this, "La cédula no es válida");
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
        if (lblEtiquetaImagen.getText().equals("defaultcon.jpg")) {
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
}
