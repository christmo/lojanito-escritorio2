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
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Kradac
 */
public class modEstados extends javax.swing.JDialog {

    private ConexionBase bd;
    private ResultSet rs;
    private ArrayList<String[]> estados;
    private Icon ic;

    public modEstados(JFrame padre, ConexionBase con) {
        super(padre, "Modificar Estados");
        super.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());

        initComponents();
        this.bd = con;

        cargarEstadosExistentes();
        habilitarComponentes(false);
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
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lstEstados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstEstados.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstEstadosValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstEstados);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 115, 155));

        txtEtiqueta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEtiquetaFocusLost(evt);
            }
        });
        getContentPane().add(txtEtiqueta, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 98, 150, -1));

        txtColor.setEditable(false);
        getContentPane().add(txtColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 63, -1));

        btnCambiarColor.setText("Cambiar");
        btnCambiarColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambiarColorActionPerformed(evt);
            }
        });
        getContentPane().add(btnCambiarColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(239, 149, -1, -1));

        jLabel1.setText("Estados");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel2.setText("MODIFICAR ESTADOS DE VEHICULO");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 20, -1, -1));

        jLabel3.setText("Color :");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 129, -1, -1));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("CERRAR");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(197, 220, -1, 50));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/rojo.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(328, 70, 120, 50));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.setEnabled(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(328, 131, 120, 50));

        jLabel4.setText("Etiqueta :");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 78, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        super.dispose();
}//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (!estados.get(lstEstados.getSelectedIndex())[0].equals("DEFAULT")) {
            String codig = estados.get(lstEstados.getSelectedIndex())[0];
            if (!codig.equals("ASI") && !codig.equals("OCU") && !codig.equals("AC")) {
                if (eliminarEstado(codig)) {

                    ic = new ImageIcon(getClass().getResource("/interfaz/iconos/correcto.png"));
                    JOptionPane.showMessageDialog(this, "REGISTRO ELIMINADO",
                            "OK",
                            JOptionPane.INFORMATION_MESSAGE,
                            ic);
                    Principal.llenarComboEstados();

                } else {
                    ic = new ImageIcon(getClass().getResource("/interfaz/iconos/error.png"));
                    JOptionPane.showMessageDialog(this, "NO SE PUDO ELIMINAR EL REGISTRO",
                            "ERROR",
                            JOptionPane.INFORMATION_MESSAGE,
                            ic);
                }
                habilitarComponentes(false);
                cargarEstadosExistentes();
                txtEtiqueta.setText("");
                txtColor.setBackground(new Color(236, 233, 216));
            } else {
                JOptionPane.showMessageDialog(this, 
                        "No se puede eliminar este estado, es requerido para el correcto funcionamiento del programa...",
                        "Error...", 0);
            }
        } else {
            ic = new ImageIcon(getClass().getResource("/interfaz/iconos/error.png"));
            JOptionPane.showMessageDialog(this, "NO SE PUEDE ELIMINAR EL ESTADO POR DEFECTO",
                    "ERROR",
                    JOptionPane.INFORMATION_MESSAGE,
                    ic);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed


        ic = new ImageIcon(getClass().getResource("/interfaz/iconos/error.png"));

        if (!estados.get(lstEstados.getSelectedIndex())[0].equals("DEFAULT")) {

            if (!txtEtiqueta.getText().trim().isEmpty()) {

                if (actualizarEstado(estados.get(lstEstados.getSelectedIndex())[0],
                        txtEtiqueta.getText(), String.valueOf(txtColor.getBackground().getRGB()))) {
                    ic = new ImageIcon(getClass().getResource("/interfaz/iconos/correcto.png"));
                    JOptionPane.showMessageDialog(this, "REGISTRO ACTUALIZADO",
                            "OK",
                            JOptionPane.INFORMATION_MESSAGE,
                            ic);
                    Principal.llenarComboEstados();
                    Principal.redimencionarTablaVehiculos();
                } else {
                    ic = new ImageIcon(getClass().getResource("/interfaz/iconos/error.png"));
                    JOptionPane.showMessageDialog(this, "NO SE PUDO ACTUALIZAR EL REGISTRO",
                            "ERROR",
                            JOptionPane.INFORMATION_MESSAGE,
                            ic);
                }
            } else {

                JOptionPane.showMessageDialog(this, "LA ETIQUETA NO PUEDE ESTAR VACIA",
                        "ERROR",
                        JOptionPane.INFORMATION_MESSAGE,
                        ic);
            }
        } else {
            JOptionPane.showMessageDialog(this, "NO SE PUEDE MODIFICAR EL ESTADO POR DEFECTO",
                    "ERROR",
                    JOptionPane.INFORMATION_MESSAGE,
                    ic);
        }
        habilitarComponentes(false);
        cargarEstadosExistentes();
        txtEtiqueta.setText("");
        txtColor.setBackground(new Color(236, 233, 216));
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void lstEstadosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstEstadosValueChanged
        if (lstEstados.getSelectedIndex() > -1) {
            txtEtiqueta.setText(estados.get(lstEstados.getSelectedIndex())[1]);
            txtColor.setBackground(new Color(Integer.parseInt(estados.get(lstEstados.getSelectedIndex())[2])));
            habilitarComponentes(true);
        }
    }//GEN-LAST:event_lstEstadosValueChanged

    private void btnCambiarColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambiarColorActionPerformed

        Color color = JColorChooser.showDialog(this, "Seleccione un color", txtColor.getBackground());

        if (color != null) {
            txtColor.setBackground(color);
            //System.out.println(color.getRGB());
        }

    }//GEN-LAST:event_btnCambiarColorActionPerformed

    private void txtEtiquetaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEtiquetaFocusLost
        txtEtiqueta.setText(txtEtiqueta.getText().toUpperCase());
    }//GEN-LAST:event_txtEtiquetaFocusLost
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCambiarColor;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList lstEstados;
    private javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtEtiqueta;
    // End of variables declaration//GEN-END:variables

    /**
     * Carga los Estados existentes en la BD
     * a la lista
     */
    private void cargarEstadosExistentes() {
        estados = new ArrayList<String[]>();
        DefaultListModel modelo = new DefaultListModel();
        String sql = "SELECT ID_CODIGO, ETIQUETA, COLOR FROM CODESTTAXI";
        try {
            rs = bd.ejecutarConsulta(sql);
            while (rs.next()) {
                String[] aux = new String[6];
                aux[0] = rs.getString("ID_CODIGO");
                aux[1] = rs.getString("ETIQUETA");
                aux[2] = rs.getString("COLOR");
                estados.add(aux);
                modelo.addElement(aux[0]);
            }
        } catch (SQLException ex) {
            System.out.println("**ERROR** clase: modEstados metodo: cargarEstadosExistentes");
        }
        lstEstados.setModel(modelo);
    }

    /**
     * Habilita o deshabilita los componentes gráficos
     * @param est true o false
     */
    private void habilitarComponentes(boolean est) {
        btnEliminar.setEnabled(est);
        btnGuardar.setEnabled(est);
        txtEtiqueta.setEditable(est);
        btnCambiarColor.setEnabled(est);
    }

    /**
     * Elimina un Estado en base a su código
     * @param cod
     * @return true o false
     */
    private boolean eliminarEstado(String cod) {
        String sql = "DELETE FROM CODESTTAXI WHERE ID_CODIGO = '" + cod + "'";
        if (bd.ejecutarSentencia(sql)) {
            //en el historico se cambia todos los estados a por defecto
            sql = "UPDATE REGCODESTTAXI SET ID_CODIGO = 'DEFAULT' WHERE ID_CODIGO = '" + cod + "'";
            if (bd.ejecutarSentencia(sql)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Actualiza los datos del estado en base a su codigo
     * @param cod
     * @return true o false
     */
    private boolean actualizarEstado(String cod, String et, String col) {
        String sql = "UPDATE CODESTTAXI SET ETIQUETA = '" + et
                + "', COLOR = '" + col + "' WHERE ID_CODIGO = '" + cod + "'";
        if (bd.ejecutarSentencia(sql)) {
            return true;
        }
        return false;
    }
}
