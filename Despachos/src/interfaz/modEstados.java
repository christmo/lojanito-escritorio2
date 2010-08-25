/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * modEstados.java
 *
 * Created on 24-ago-2010, 19:37:06
 */
package interfaz;

import BaseDatos.ConexionBase;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Kradac
 */
public class modEstados extends javax.swing.JDialog {

    private ConexionBase bd;

    public modEstados(JFrame padre, ConexionBase con) {
        super(padre, "Modificar Estados");
        super.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());

        initComponents();
        this.bd = con;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstEstados = new javax.swing.JList();
        txtEtiqueta = new javax.swing.JTextField();
        txtColor = new javax.swing.JTextField();
        btnCambiarColor = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setViewportView(lstEstados);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 58, 115, 155));
        add(txtEtiqueta, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 98, 115, -1));

        txtColor.setEditable(false);
        add(txtColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 63, -1));

        btnCambiarColor.setText("Cambiar");
        add(btnCambiarColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(239, 149, -1, -1));

        jLabel1.setText("Etiqueta :");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 78, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel2.setText("MODIFICAR ESTADOS DE VEHICULO");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));

        jLabel3.setText("Color :");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 129, -1, -1));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("CERRAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 220, -1, 50));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/rojo.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(328, 70, 120, 50));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(328, 131, -1, 50));
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        super.dispose();
}//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
    }//GEN-LAST:event_btnGuardarActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambiarColor;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lstEstados;
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtEtiqueta;
    // End of variables declaration//GEN-END:variables

    private void cargarEstadosExistentes() {
        DefaultListModel modelo = new DefaultListModel();

    }
}
