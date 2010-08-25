/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ingresoEstados.java
 *
 * Created on 24-ago-2010, 15:32:11
 */
package interfaz;

import BaseDatos.ConexionBase;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Kradac
 */
public class ingresoEstados extends javax.swing.JDialog {

    private Color col = Color.ORANGE;
    private Color color;
    private Icon ic;
    private ConexionBase bd;
    private ResultSet rs;

    public ingresoEstados(JFrame padre, ConexionBase con) {
        super(padre, "Crear nuevo Estado");
        super.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());

        this.bd = con;
        initComponents();
    }

     @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        lblCod = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtCodigo = new javax.swing.JTextField();
        lblCod1 = new javax.swing.JLabel();
        txtEtiqueta = new javax.swing.JTextField();
        lblCod2 = new javax.swing.JLabel();
        btnPaleta = new javax.swing.JButton();
        txtColor = new javax.swing.JTextField();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("CREAR ESTADO DE VEHICULO");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(76, 29, -1, -1));

        lblCod.setText("CODIGO :");
        add(lblCod, new org.netbeans.lib.awtextra.AbsoluteConstraints(68, 67, -1, -1));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(43, 178, 118, 45));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cerrar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 178, 118, 45));

        txtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodigoFocusLost(evt);
            }
        });
        add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 64, 80, -1));

        lblCod1.setText("ETIQUETA :");
        add(lblCod1, new org.netbeans.lib.awtextra.AbsoluteConstraints(59, 98, -1, -1));

        txtEtiqueta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEtiquetaFocusLost(evt);
            }
        });
        add(txtEtiqueta, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 95, 160, -1));

        lblCod2.setText("COLOR :");
        add(lblCod2, new org.netbeans.lib.awtextra.AbsoluteConstraints(74, 126, -1, -1));

        btnPaleta.setText("Escoger");
        btnPaleta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaletaActionPerformed(evt);
            }
        });
        add(btnPaleta, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 130, -1, -1));

        txtColor.setBackground(new java.awt.Color(51, 255, 51));
        txtColor.setEditable(false);
        add(txtColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 40, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        ic = new ImageIcon(getClass().getResource("/interfaz/iconos/error.png"));
        if (!txtCodigo.getText().isEmpty()) {
            if (!txtEtiqueta.getText().isEmpty()) {

                if (crearCodigo(txtCodigo.getText(), txtEtiqueta.getText(),
                        String.valueOf(txtColor.getBackground().getRGB()))) {

                    ic = new ImageIcon(getClass().getResource("/interfaz/iconos/correcto.png"));

                    JOptionPane.showMessageDialog(this, "REGISTRO GUARDADO",
                            "OK",
                            JOptionPane.INFORMATION_MESSAGE,
                            ic);

                    Principal.llenarComboEstados();
                    super.dispose();

                } else {
                    JOptionPane.showMessageDialog(this, "NO SE PUDO GUARDAR EL REGISTRO",
                            "ERROR",
                            JOptionPane.INFORMATION_MESSAGE,
                            ic);
                    System.out.println("Clase: ingresoEstados.java Metodo: btnGuardarActionPerformed");
                }
            } else {
                JOptionPane.showMessageDialog(this, "DEBE ESPECIFICAR UNA ETIQUETA",
                        "FALTA ETIQUETA",
                        JOptionPane.INFORMATION_MESSAGE,
                        ic);
            }
        } else {
            JOptionPane.showMessageDialog(this, "DEBE ESPECIFICAR UN CODIGO",
                    "FALTA CODIGO",
                    JOptionPane.INFORMATION_MESSAGE,
                    ic);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        super.dispose();
}//GEN-LAST:event_btnCancelarActionPerformed

    private void btnPaletaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaletaActionPerformed
        color = JColorChooser.showDialog(this, "Seleccione un color", col);
        if (color != null) {
            txtColor.setBackground(new Color(color.getRGB()));
        }
    }//GEN-LAST:event_btnPaletaActionPerformed

    private void txtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodigoFocusLost
        txtCodigo.setText(txtCodigo.getText().toUpperCase());
    }//GEN-LAST:event_txtCodigoFocusLost

    private void txtEtiquetaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEtiquetaFocusLost
        txtEtiqueta.setText(txtEtiqueta.getText().toUpperCase());
    }//GEN-LAST:event_txtEtiquetaFocusLost
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnPaleta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblCod;
    private javax.swing.JLabel lblCod1;
    private javax.swing.JLabel lblCod2;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtEtiqueta;
    // End of variables declaration//GEN-END:variables

    /**
     * Crea el nuevo codigo en la Base de Datos
     * @param cod
     * @return
     */
    private boolean crearCodigo(String cod, String et, String col) {
        if (!codigoExiste(cod)) {
            String sql;
            sql = "INSERT INTO CODESTTAXI VALUES ('" + cod + "','" + et + "','" + col + "')";
            if (bd.ejecutarSentencia(sql)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica si el codigo enviado ya existe
     * @param cod
     * @return
     */
    private boolean codigoExiste(String cod) {
        String sql;
        sql = "SELECT COUNT(ID_CODIGO) AS NUM FROM CODESTTAXI WHERE ID_CODIGO = '" + cod + "'";
        rs = bd.ejecutarConsulta(sql);
        int cant = 0;
        try {
            while (rs.next()) {
                String[] aux = new String[9];
                cant = rs.getInt("NUM");
            }
            if (cant > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
