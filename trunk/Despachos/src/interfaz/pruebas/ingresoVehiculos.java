/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ingresoVehiculos.java
 *
 * Created on 09/08/2010, 06:07:39 PM
 */
package interfaz.pruebas;

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

/**
 *
 * @author Usuario
 */
public class ingresoVehiculos extends javax.swing.JDialog {

    ConexionBase conec = new ConexionBase();
    private ResourceBundle rb;
    private File Ffoto = null;
    private funcionesUtilidad funciones = new funcionesUtilidad();
    private String img;
    ConexionBase bd = new ConexionBase();
    ResultSet rs;
    private String ID_EMPRESA;
    Icon ic;

    /**
     * ENVIAR EL ID_EMPRESA POR EL CONSTRUCTOR
     * @param id_empresa
     */
    public ingresoVehiculos(JFrame padre, String id_empresa) {
        super(padre,"Ingreso de Vehiculos");
        super.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());

        initComponents();
        cargarConductores(cmbConductor, 0);
        cargarConductores(cmbCondAux, 1);
        cargarImgDefault();
        this.ID_EMPRESA = id_empresa;

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtPlaca = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNUnidad = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtPropietario = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtInformacion = new javax.swing.JTextArea();
        lblEtiquetaImagen = new javax.swing.JLabel();
        lblImagen = new javax.swing.JLabel();
        cmbCondAux = new javax.swing.JComboBox();
        cmbConductor = new javax.swing.JComboBox();
        spAño = new javax.swing.JSpinner();
        btnCambiar = new javax.swing.JButton();
        btnguardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtNumMotor = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtNumChasis = new javax.swing.JTextField();

        jLabel6.setText("jLabel6");

        jLabel1.setText("PLACA :");

        txtPlaca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPlacaFocusLost(evt);
            }
        });

        jLabel3.setText("NUM UNIDAD :");

        txtNUnidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNUnidadFocusLost(evt);
            }
        });

        jLabel4.setText("CONDUCTOR :");

        jLabel5.setText("CONDUCTOR AUX :");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("VEHICULOS");

        jLabel9.setText("AÑO :");

        jLabel10.setText("MODELO :");

        txtModelo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtModeloFocusLost(evt);
            }
        });

        jLabel11.setText("PROPIETARIO :");

        txtPropietario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPropietarioFocusLost(evt);
            }
        });

        jLabel12.setText("INFORMACION AD. :");

        txtInformacion.setColumns(20);
        txtInformacion.setRows(5);
        txtInformacion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtInformacionFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(txtInformacion);

        lblEtiquetaImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEtiquetaImagen.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        lblImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagen.setToolTipText("Foto del Conductor");
        lblImagen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));
        lblImagen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        spAño.setModel(new javax.swing.SpinnerNumberModel(2010, 1999, 2222, 1));

        btnCambiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/cargar.png"))); // NOI18N
        btnCambiar.setText("CAMBIAR");
        btnCambiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarActionPerformed(evt);
            }
        });

        btnguardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/guardar.png"))); // NOI18N
        btnguardar.setText("GUARDAR");
        btnguardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnguardarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel13.setText("MARCA :");

        txtMarca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMarcaFocusLost(evt);
            }
        });

        jLabel14.setText("NUM MOTOR :");

        txtNumMotor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumMotorFocusLost(evt);
            }
        });

        jLabel15.setText("NUM CHASIS :");

        txtNumChasis.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumChasisFocusLost(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel3)
                .addGap(4, 4, 4)
                .addComponent(txtNUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel11))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jLabel13))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel10))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel14))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel15))
                    .addComponent(jLabel12))
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbCondAux, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addGap(11, 11, 11)
                        .addComponent(spAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNumMotor, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumChasis, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEtiquetaImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(btnCambiar))))
            .addGroup(layout.createSequentialGroup()
                .addGap(213, 213, 213)
                .addComponent(btnguardar)
                .addGap(59, 59, 59)
                .addComponent(btnCancelar))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel3))
                    .addComponent(txtNUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jLabel4)
                        .addGap(19, 19, 19)
                        .addComponent(jLabel5)
                        .addGap(21, 21, 21)
                        .addComponent(jLabel11)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel13)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel10)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel14)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel15)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel12))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(cmbConductor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(cmbCondAux, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtPropietario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(spAño, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(txtNumMotor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtNumChasis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(lblEtiquetaImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(btnCambiar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnguardar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCambiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarActionPerformed
        Ffoto = funciones.cargarImagen(lblEtiquetaImagen, lblImagen);
    }//GEN-LAST:event_btnCambiarActionPerformed

    private void txtPlacaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPlacaFocusLost
        txtPlaca.setText(txtPlaca.getText().toUpperCase());
    }//GEN-LAST:event_txtPlacaFocusLost

    private void txtNUnidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNUnidadFocusLost
        if (!funciones.isNumeric(txtNUnidad.getText())) {
            ic = new ImageIcon(getClass().getResource("/interfaz/iconos/error.png"));
            JOptionPane.showMessageDialog(this, "NO ES UN NÚMERO VÁLIDO DE UNIDAD",
                    "ERROR NUMERO DE UNIDAD",
                    JOptionPane.ERROR_MESSAGE,
                    ic);
            txtNUnidad.setText("");
        }
    }//GEN-LAST:event_txtNUnidadFocusLost

    private void txtModeloFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtModeloFocusLost
        txtModelo.setText(txtModelo.getText().toUpperCase());
    }//GEN-LAST:event_txtModeloFocusLost

    private void txtPropietarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPropietarioFocusLost
        txtPropietario.setText(txtPropietario.getText().toUpperCase());
    }//GEN-LAST:event_txtPropietarioFocusLost

    private void txtInformacionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtInformacionFocusLost
        txtInformacion.setText(txtInformacion.getText().toUpperCase());
    }//GEN-LAST:event_txtInformacionFocusLost

    private void btnguardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnguardarActionPerformed
        String con = cmbConductor.getSelectedItem().toString();
        String conAux = "";

        ic = new ImageIcon(getClass().getResource("/interfaz/iconos/error.png"));

        if (cmbCondAux.getSelectedIndex() != 0) {
            conAux = cmbCondAux.getSelectedItem().toString();
        }

        if ((txtPlaca.getText().length() > 0) && !conec.placaExiste(txtPlaca.getText())) {

            if ((txtNUnidad.getText().length() > 0)
                    && (funciones.isNumeric(txtNUnidad.getText()))
                    && !conec.validarUnidad(Integer.parseInt(txtNUnidad.getText()))) {

                if (!con.equals(conAux)) {


                    //Primero Guardar Imagen

                    String imgName = lblEtiquetaImagen.getText();

                    if (!imgName.equals("defaultveh.png")) {
                        imgName = funciones.guardarImagen(Ffoto, rb.getString("dirImgVehiculos"));
                    }

                    guardarVehiculo(txtPlaca.getText(), Integer.valueOf(txtNUnidad.getText()),
                            ID_EMPRESA,
                            con, conAux, txtModelo.getText(),
                            Integer.valueOf(spAño.getValue().toString()),
                            txtPropietario.getText(), txtInformacion.getText(),
                            imgName, txtMarca.getText(), txtNumMotor.getText(),
                            txtNumChasis.getText());
                } else {
                    JOptionPane.showMessageDialog(this, "CONDUCTOR Y AUXILIAR SON IGUALES",
                            "ERROR CONDUCTOR",
                            JOptionPane.ERROR_MESSAGE, ic);
                }
            } else {
                JOptionPane.showMessageDialog(this, "REVISE EL NÚMERO DE UNIDAD !",
                        "ERROR NUM UNIDAD",
                        JOptionPane.ERROR_MESSAGE, ic);
            }
        } else {
            JOptionPane.showMessageDialog(this, "LA PLACA YA EXISTE O NO ES VÁLIDA !",
                    "ERROR PLACA",
                    JOptionPane.ERROR_MESSAGE, ic);
        }
    }//GEN-LAST:event_btnguardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        super.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtMarcaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMarcaFocusLost
        txtMarca.setText(txtMarca.getText().toUpperCase());
    }//GEN-LAST:event_txtMarcaFocusLost

    private void txtNumMotorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumMotorFocusLost
        txtNumMotor.setText(txtNumMotor.getText().toUpperCase());
    }//GEN-LAST:event_txtNumMotorFocusLost

    private void txtNumChasisFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumChasisFocusLost
        txtNumChasis.setText(txtNumChasis.getText().toUpperCase());
    }//GEN-LAST:event_txtNumChasisFocusLost
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambiar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnguardar;
    private javax.swing.JComboBox cmbCondAux;
    private javax.swing.JComboBox cmbConductor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEtiquetaImagen;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JSpinner spAño;
    private javax.swing.JTextArea txtInformacion;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtNUnidad;
    private javax.swing.JTextField txtNumChasis;
    private javax.swing.JTextField txtNumMotor;
    private javax.swing.JTextField txtPlaca;
    private javax.swing.JTextField txtPropietario;
    // End of variables declaration//GEN-END:variables

    /**
     * Carga todos los conductores existentes
     * en la BD para relacionarlos con el
     * vehículo  (1) Auxiliares  (0) Conductores
     * @param condu ComboBox en donde se cargarán
     * @id Identificar del combo a llenar
     */
    private void cargarConductores(JComboBox condu, int id) {
        ArrayList<String[]> conductores = conec.buscarConductores("", 1);
        String[] nomConductores = new String[conductores.size() + id];
        int i = 0 + id;

        if (id == 1) {
            nomConductores[0] = "< Sin Auxiliar >";
        }
        for (String[] name : conductores) {
            nomConductores[i] = name[1];
            i++;
        }
        condu.setModel(new javax.swing.DefaultComboBoxModel(nomConductores));
    }

    /**
     * Carga el archivo de propiedades del sistema
     */
    private void leerProperties() {
        rb = ResourceBundle.getBundle("configuracion.configsystem");
    }

    /**
     * Carga la imagen por defecto para el vehículo
     */
    private void cargarImgDefault() {
        leerProperties();

        Icon fot = new ImageIcon(getClass().getResource("/interfaz/iconos/defaultveh.png"));
        if (fot.getIconHeight() == -1) {
            lblEtiquetaImagen.setText("Imagen no Encontrada");
            lblImagen.setText("Imagen no encontrada !");
        } else {
            lblImagen.setIcon(fot);
            lblImagen.setText("");
            lblEtiquetaImagen.setText("");
        }
        lblEtiquetaImagen.setText("defaultveh.png");
    }

    /**
     * Guarda los datos del nuevo Vehículo
     * 
     * @param placa
     * @param nUnidad
     * @param emp
     * @param con
     * @param conAux
     * @param color
     * @param modelo
     * @param año
     * @param pro
     * @param infor
     * @param img
     */
    private void guardarVehiculo(String placa,
            int nUnidad,
            String emp,
            String con,
            String conAux,
            String modelo,
            int año,
            String pro,
            String infor,
            String img,
            String mar,
            String nummo,
            String numcha) {

        String sql = "CALL SP_INSERT_VEHICULO('" + placa + "'," + nUnidad
                + ",'" + emp + "','" + con + "','" + conAux + "','"
                + modelo + "'," + año + ",'" + pro + "','" + infor + "','"
                + img + "','" + mar + "','" + nummo + "','" + numcha + "')";

        if (!bd.ejecutarSentencia(sql)) {
            ic = new ImageIcon(getClass().getResource("/interfaz/iconos/error.png"));
            JOptionPane.showMessageDialog(this, "NO SE PUDO GUARDAR EL REGISTRO",
                    "ERROR",
                    JOptionPane.INFORMATION_MESSAGE,
                    ic);
        } else {
            ic = new ImageIcon(getClass().getResource("/interfaz/iconos/correcto.png"));
            JOptionPane.showMessageDialog(this, "REGISTRO GUARDADO",
                    "OK",
                    JOptionPane.INFORMATION_MESSAGE,
                    ic);
            super.dispose();
        }

    }
}
