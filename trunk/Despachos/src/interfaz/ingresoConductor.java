/*
 * ingresoConductor.java
 *
 * Created on 09/08/2010, 08:21:31 AM
 */
package interfaz;

import BaseDatos.ConexionBase;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JFrame;

/**
 *
 * @author Kradac
 */
public class ingresoConductor extends javax.swing.JDialog {

    private File Ffoto = null;
    private funcionesUtilidad utilidad = new funcionesUtilidad();
    ConexionBase bd;
    ResultSet rs;
    Properties arcConfig;

    /** Creates new form ingresoConductor */
    public ingresoConductor(JFrame padre, ConexionBase con, Properties arcConfig) {
        super(padre, "Ingreso de Conductores");
        super.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());

        this.bd = con;
        this.arcConfig = arcConfig;

        initComponents();
        //leerProperties();
        Icon fot = new ImageIcon(getClass().getResource("/interfaz/iconos/defaultcon.jpg"));
        lblFoto.setIcon(fot);
        lblFotoEtiqueta.setText("defaultcon.jpg");
    }

    /**
     * Comprueba en la BD si la cédula ingresada ya existe.
     * @param cedula
     * @return
     */
    private boolean existeCedula(String cedula) {
        try {
            String sql = "SELECT ID_CON FROM CONDUCTORES WHERE CEDULA_CONDUCTOR = " + cedula;
            rs = bd.ejecutarConsulta(sql);
            if (!rs.next()) {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ingresoConductor.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    /**
     * Guarda el nuevo Conductor con los datos
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
        if (lblFotoEtiqueta.getText().equals("defaultcon.jpg")) {
            imgName = "defaultcon.jpg";
        } else {
            //imgName = utilidad.guardarImagen(Ffoto, rb.getString("dirImgConductores"));
            //String path = arcConfig.getProperty("dirProyecto") + arcConfig.getProperty("dirImgConductores");
            String path = bd.getValorConfiguiracion("dirProyecto") + bd.getValorConfiguiracion("dirImgConductores");
            imgName = utilidad.guardarImagen(Ffoto, path);

        }


        if ((imgName != null) && (!existeCedula(ced))) {
            String sql = "INSERT INTO CONDUCTORES (CEDULA_CONDUCTOR,"
                    + "NOMBRE_APELLIDO_CON,DIRECCION_CON,"
                    + "NUM_CASA_CON,TIPO_SANGRE,ESTADO_CIVIL,"
                    + "CONYUGE,MAIL,"
                    + "FOTO) VALUES ('" + ced + "','" + nom
                    + "','" + dir + "','" + numc + "','" + tipos + "','" + estcivil
                    + "','" + con + "','" + mail + "','" + imgName + "')";

            if (bd.ejecutarSentencia(sql)) {
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * Limpia todos los campos del formulario.
     */
    private void limpiarCampos() {
        txtCedula.setText("");
        txtConyuge.setText("");
        txtDireccion.setText("");
        txtEstadoCivil.setText("");
        txtNomApe.setText("");
        txtNumCasa.setText("");
        txtTipoSangre.setText("");
        txtemail.setText("");
        lblFotoEtiqueta.setText("");
        Icon fot = new ImageIcon(getClass().getResource("/interfaz/iconos/defaultcon.jpg"));
        lblFoto.setIcon(fot);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCargarImagen = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblFoto = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtConyuge = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtTipoSangre = new javax.swing.JTextField();
        txtCedula = new javax.swing.JTextField();
        txtEstadoCivil = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNumCasa = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtNomApe = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lblFotoEtiqueta = new javax.swing.JLabel();

        setTitle("Ingreso de Conductores");

        btnCargarImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/cargar.png"))); // NOI18N
        btnCargarImagen.setText("Cargar Foto");
        btnCargarImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCargarImagenActionPerformed(evt);
            }
        });

        jLabel2.setText("Nombres y Apellido:");

        lblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoto.setToolTipText("Foto del Conductor");
        lblFoto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        lblFoto.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel3.setText("Dirección:");

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel1.setText("Cedula:");

        jLabel7.setText("Cónyuge:");

        txtConyuge.setToolTipText("En caso de ser Casado, sino dejar en blanco");
        txtConyuge.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtConyugeFocusLost(evt);
            }
        });

        jLabel8.setText("E-mail: ");

        txtemail.setToolTipText("Si no posee dejar en blanco");

        jLabel9.setText("Foto:");

        txtTipoSangre.setToolTipText("Tipo de Sangre");
        txtTipoSangre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTipoSangreFocusLost(evt);
            }
        });

        txtCedula.setColumns(10);
        txtCedula.setToolTipText("Cedula (sin guión)");
        txtCedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCedulaKeyPressed(evt);
            }
        });

        txtEstadoCivil.setToolTipText("Estado Civil");
        txtEstadoCivil.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEstadoCivilFocusLost(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("CONDUCTOR");

        jLabel4.setText("Num. Casa:");

        jLabel5.setText("Tipo de Sangre:");

        txtNumCasa.setToolTipText("Domicilio del Conductor");
        txtNumCasa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumCasaFocusLost(evt);
            }
        });

        txtDireccion.setToolTipText("Número de Casa");
        txtDireccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDireccionFocusLost(evt);
            }
        });

        txtNomApe.setToolTipText("Nombres y Apellidos del Conductor");
        txtNomApe.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomApeFocusLost(evt);
            }
        });

        jLabel6.setText("Estado Civil:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(19, 19, 19)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel1)
                                                .addComponent(jLabel4)
                                                .addComponent(jLabel6)
                                                .addComponent(jLabel8)
                                                .addComponent(jLabel9)
                                                .addComponent(jLabel5)
                                                .addComponent(jLabel3)))
                                        .addComponent(jLabel2))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(btnCargarImagen)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtTipoSangre, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 239, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 136, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnGuardar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                                .addComponent(btnCancelar))
                            .addComponent(lblFotoEtiqueta, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                            .addComponent(txtemail, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                            .addComponent(txtConyuge, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                            .addComponent(txtEstadoCivil, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                            .addComponent(txtNumCasa, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
                            .addComponent(txtNomApe, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNomApe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtNumCasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTipoSangre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtConyuge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(lblFotoEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnCargarImagen)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCargarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarImagenActionPerformed
        Ffoto = utilidad.cargarImagen(lblFotoEtiqueta, lblFoto);
}//GEN-LAST:event_btnCargarImagenActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        funcionesUtilidad objFun = new funcionesUtilidad();

        if (!objFun.esCedulaValida(txtCedula.getText())) {
            JOptionPane.showMessageDialog(this, "REVISE EL NUMERO DE CEDULA",
                    "ERROR CEDULA",
                    JOptionPane.ERROR_MESSAGE);
            txtCedula.setText("");
        } else {
            if (existeCedula(txtCedula.getText())) {
                JOptionPane.showMessageDialog(this, "LA CEDULA YA EXISTE",
                        "ERROR CEDULA",
                        JOptionPane.ERROR_MESSAGE);
                txtCedula.setText("");
            } else {

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
                } else if (lblFotoEtiqueta.getText().length() == 0) {
                    JOptionPane.showMessageDialog(this, "SELECCIONE LA FOTO DEL CONDUCTOR",
                            "ERROR FOTOGRAFÍA",
                            JOptionPane.ERROR_MESSAGE);
                } else if (!objFun.isEmail(txtemail.getText()) && txtemail.getText().length() > 0) {
                    JOptionPane.showMessageDialog(this, "e-Mail no válido",
                            "e-mail",
                            JOptionPane.ERROR_MESSAGE);
                } else {

                    boolean rta = guardarRegistro(txtCedula.getText(),
                            txtNomApe.getText(),
                            txtDireccion.getText(),
                            txtNumCasa.getText(),
                            txtTipoSangre.getText(),
                            txtEstadoCivil.getText(),
                            txtConyuge.getText(),
                            txtemail.getText());

                    if (rta) {
                        JOptionPane.showMessageDialog(this, "NUEVO CHOFER GUARDADO",
                                "LISTO",
                                JOptionPane.INFORMATION_MESSAGE);
                        limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(this, "NO SE PUDO GUARDAR EL CHOFER",
                                "ERROR",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
}//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        super.dispose();
}//GEN-LAST:event_btnCancelarActionPerformed

    private void txtConyugeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtConyugeFocusLost
        txtConyuge.setText(txtConyuge.getText().toUpperCase());
}//GEN-LAST:event_txtConyugeFocusLost

    private void txtTipoSangreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTipoSangreFocusLost
        txtTipoSangre.setText(txtTipoSangre.getText().toUpperCase());
}//GEN-LAST:event_txtTipoSangreFocusLost

    private void txtCedulaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCedulaKeyPressed
        if (txtCedula.getText().length() >= 10) {
            txtCedula.setText(txtCedula.getText().substring(0, 9));
            evt.setKeyChar(' ');
        }
}//GEN-LAST:event_txtCedulaKeyPressed

    private void txtEstadoCivilFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEstadoCivilFocusLost
        txtEstadoCivil.setText(txtEstadoCivil.getText().toUpperCase());
}//GEN-LAST:event_txtEstadoCivilFocusLost

    private void txtNumCasaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumCasaFocusLost
        txtNumCasa.setText(txtNumCasa.getText().toUpperCase());
}//GEN-LAST:event_txtNumCasaFocusLost

    private void txtDireccionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDireccionFocusLost
        txtDireccion.setText(txtDireccion.getText().toUpperCase());
}//GEN-LAST:event_txtDireccionFocusLost

    private void txtNomApeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomApeFocusLost
        txtNomApe.setText(txtNomApe.getText().toUpperCase());
}//GEN-LAST:event_txtNomApeFocusLost
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCargarImagen;
    private javax.swing.JButton btnGuardar;
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
    private javax.swing.JLabel lblFoto;
    private javax.swing.JLabel lblFotoEtiqueta;
    private javax.swing.JTextField txtCedula;
    private javax.swing.JTextField txtConyuge;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEstadoCivil;
    private javax.swing.JTextField txtNomApe;
    private javax.swing.JTextField txtNumCasa;
    private javax.swing.JTextField txtTipoSangre;
    private javax.swing.JTextField txtemail;
    // End of variables declaration//GEN-END:variables
}
