/*
 * ingresoConductor.java
 *
 * Created on 09/08/2010, 08:21:31 AM
 */
package interfaz.pruebas;

import BaseDatos.ConexionBase;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Kradac
 */
public class ingresoConductor extends javax.swing.JPanel {

    private File Ffoto = null;
    private ResourceBundle rb;
    private funcionesUtilidad utilidad = new funcionesUtilidad();

    ConexionBase bd = new ConexionBase();
    ResultSet rs;

    /** Creates new form ingresoConductor */
    public ingresoConductor() {
        initComponents();

        leerProperties();
        Icon fot = new ImageIcon(getClass().getResource("/interfaz/iconos/defaultcon.jpg"));
        lblFoto.setIcon(fot);
        lblFotoEtiqueta.setText("defaultcon.jpg");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
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

        btnCargarImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/cargar.png"))); // NOI18N
        btnCargarImagen.setText("Cargar");
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
        txtemail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtemailFocusLost(evt);
            }
        });

        jLabel9.setText("Foto:");

        txtTipoSangre.setToolTipText("Tipo de Sangre");
        txtTipoSangre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTipoSangreFocusLost(evt);
            }
        });

        txtCedula.setColumns(10);
        txtCedula.setToolTipText("Cedula (sin guión)");
        txtCedula.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCedulaFocusLost(evt);
            }
        });
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addGap(588, 588, 588))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(lblFotoEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCargarImagen))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNomApe, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTipoSangre, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtNumCasa, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtConyuge, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtDireccion, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addContainerGap(197, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNomApe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtNumCasa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTipoSangre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEstadoCivil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtConyuge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(btnCargarImagen)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblFotoEtiqueta, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(11, 11, 11))))
                    .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCargarImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCargarImagenActionPerformed
       
        Ffoto = utilidad.cargarImagen(lblFotoEtiqueta, lblFoto);
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
        } else if (lblFotoEtiqueta.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "SELECCIONE LA FOTO DEL CONDUCTOR",
                    "ERROR FOTOGRAFÍA",
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

            } else {
                JOptionPane.showMessageDialog(this, "NO SE PUDO GUARDAR EL CHOFER",
                        "ERROR",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
}//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
}//GEN-LAST:event_btnCancelarActionPerformed

    private void txtConyugeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtConyugeFocusLost
        txtConyuge.setText(txtConyuge.getText().toUpperCase());
}//GEN-LAST:event_txtConyugeFocusLost

    private void txtemailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtemailFocusLost
        funcionesUtilidad objFun = new funcionesUtilidad();
        
        if (!objFun.isEmail(txtemail.getText()) && (txtemail.getText().length() > 0)) {
            JOptionPane.showMessageDialog(this, "SINTAXIS INCORRECTA EN CAMPO E-MAIL",
                    "ERROR E-MAIL",
                    JOptionPane.ERROR_MESSAGE);
        }
}//GEN-LAST:event_txtemailFocusLost

    private void txtTipoSangreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTipoSangreFocusLost
        txtTipoSangre.setText(txtTipoSangre.getText().toUpperCase());
}//GEN-LAST:event_txtTipoSangreFocusLost

    private void txtCedulaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCedulaFocusLost

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
            }
        }
    }//GEN-LAST:event_txtCedulaFocusLost

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
            imgName = utilidad.guardarImagen(Ffoto,rb.getString("dirImgConductores"));
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
     * Cierra la ventana
     */
    private void salir() {
    }

    /**
     * Carga el archivo de propiedades del sistema
     */
    private void leerProperties() {
        rb = ResourceBundle.getBundle("configuracion.configsystem");
    }
}
