package interfaz;

import BaseDatos.ConexionBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Kradac
 */
public class Multas extends javax.swing.JDialog {

    funcionesUtilidad objUtilidad = new funcionesUtilidad();
    ConexionBase bd;
    ResultSet rs;

    /** Creates new form modTurnos */
    public Multas(JFrame padre, ConexionBase con) {

        super(padre, "Multas");
        super.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());

        this.bd = con;

        initComponents();
        consultarCodigoMultas();
        cmbCodigoM.setVisible(false);
        cmbCodigoM.setSelectedIndex(0);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        txtCodMulta = new javax.swing.JTextField();
        txtValor = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        btnBuscar2 = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnGuardar1 = new javax.swing.JButton();
        btnEliminar1 = new javax.swing.JButton();
        cmbCodigoM = new javax.swing.JComboBox();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 290, 140, 40));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel1.setText("INGRESAR MULTAS");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 10, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("Código Multa:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setText("Valor:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, -1, 20));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel5.setText("Descripción:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, 20));

        txtDescripcion.setColumns(20);
        txtDescripcion.setRows(3);
        txtDescripcion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescripcionFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(txtDescripcion);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 300, 60));
        getContentPane().add(txtCodMulta, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 80, -1));
        getContentPane().add(txtValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 50, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("$");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 20, 20));

        btnBuscar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/buscar.png"))); // NOI18N
        btnBuscar2.setText("BUSCAR");
        btnBuscar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscar2ActionPerformed(evt);
            }
        });
        getContentPane().add(btnBuscar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, -1, -1));

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/modificar.png"))); // NOI18N
        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        getContentPane().add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, -1, -1));

        btnGuardar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/guardar.png"))); // NOI18N
        btnGuardar1.setText("GUARDAR");
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, -1, -1));

        btnEliminar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/rojo.png"))); // NOI18N
        btnEliminar1.setText("ELIMINAR");
        btnEliminar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnEliminar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 230, -1, 40));

        cmbCodigoM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione" }));
        cmbCodigoM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbCodigoMMouseClicked(evt);
            }
        });
        cmbCodigoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCodigoMActionPerformed(evt);
            }
        });
        getContentPane().add(cmbCodigoM, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        try {
            super.dispose();
        } catch (Throwable ex) {
            Logger.getLogger(Multas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtDescripcionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescripcionFocusLost
        //txtInformacion.setText(txtInformacion.getText().toUpperCase());
}//GEN-LAST:event_txtDescripcionFocusLost

    private void btnBuscar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscar2ActionPerformed
        cmbCodigoM.setVisible(true);
        /*if (!txtCodMulta.getText().equals("")) {
            buscarMulta(txtCodMulta.getText());
        } else {
            JOptionPane.showMessageDialog(this, "INGRESE UN CODIGO DE MULTA PARA BUSCAR",
                    "FALTA INFORMACION",
                    JOptionPane.ERROR_MESSAGE);
        }*/
}//GEN-LAST:event_btnBuscar2ActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
       if (!txtCodMulta.getText().equals("") && objUtilidad.isDouble(txtValor.getText())) {
            if (verificarCodMulta(txtCodMulta.getText())) {

                if (modificarRegistro(txtCodMulta.getText(), txtDescripcion.getText(), Double.parseDouble(txtValor.getText()))) {
                    JOptionPane.showMessageDialog(this, "EL REGISTRO SE MODIFICO EXITOSAMENTE",
                            "REGISTRO MODIFICADO",
                            JOptionPane.INFORMATION_MESSAGE);
                    limpiar();
                } else {
                    JOptionPane.showMessageDialog(this, "EL REGISTRO NO SE PUDO GUARDAR EN LA BASE DE DATOS",
                            "ERROR AL MODIFICAR EL REGISTRO",
                            JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "PARA MODIFICAR UN REGISTRO, ÉSTE DEBE ESTAR GUARDADO PREVIAMENTE",
                        "REGISTRO NO MODIFICADO",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        if (!txtCodMulta.getText().equals("")) {

            if (objUtilidad.isDouble(txtValor.getText())) {

                if (!verificarCodMulta(txtCodMulta.getText())) {

                    if (guardarRegistro(txtCodMulta.getText(), txtDescripcion.getText(), Double.parseDouble(txtValor.getText()))) {
                        JOptionPane.showMessageDialog(this, "REGISTRO GUARDADO",
                                "TRANSACCION EXITOSA",
                                JOptionPane.INFORMATION_MESSAGE);
                        limpiar();
                    } else {
                        JOptionPane.showMessageDialog(this, "EL REGISTRO NO SE PUDO GUARDAR EN LA BASE DE DATOS",
                                "ERROR AL GUARDAR REGISTRO",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "EL CODIGO DE MULTA QUE INGRESO YA EXISTE",
                            "EL REGISTRO NO SE PUDO GUARDAR",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "INGRESE CODIGO DE LA MULTA",
                    "FALTAN CAMPOS OBLIGATORIOS",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardar1ActionPerformed

    private void btnEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar1ActionPerformed
        if (!txtCodMulta.getText().equals("")) {

            if (verificarCodMulta(txtCodMulta.getText())) {
                if (eliminarRegistro(txtCodMulta.getText())) {
                    limpiar();
                    JOptionPane.showMessageDialog(this, "REGISTRO ELIMINADO EXITOSAMENTE",
                            "REGISTRO ELIMINADO",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }else
                JOptionPane.showMessageDialog(this, "EL CODIGO DE MULTA INGRESADO NO EXISTE",
                    "NO SE PUDO ELIMINAR EL REGISTRO",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "DEBE INGRESAR UN CODIGO DE MULTA PARA ELIMINAR REGISTRO",
                    "NO SE PUDO ELIMINAR EL REGISTRO",
                    JOptionPane.ERROR_MESSAGE);
        }
}//GEN-LAST:event_btnEliminar1ActionPerformed

    private void cmbCodigoMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCodigoMActionPerformed
    if(cmbCodigoM.getSelectedIndex()>0){
            txtCodMulta.setText(cmbCodigoM.getSelectedItem().toString());
            buscarMulta(txtCodMulta.getText());
    }
}//GEN-LAST:event_cmbCodigoMActionPerformed

    private void cmbCodigoMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbCodigoMMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cmbCodigoMMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar2;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEliminar1;
    private javax.swing.JButton btnGuardar1;
    private javax.swing.JButton btnModificar;
    private javax.swing.JComboBox cmbCodigoM;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtCodMulta;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables

    private void limpiar() {
        txtCodMulta.setText("");
        txtDescripcion.setText("");
        txtValor.setText("");
    }

    private boolean guardarRegistro(String codMulta, String strDescripcion, double dblValor) {
        String sql = "INSERT INTO COD_MULTAS(COD_MULTA,DESCRIPCION,VALOR) "
                + "VALUES('" + codMulta + "','" + strDescripcion + "'," + dblValor + ")";
        System.out.println("consulta realizada");
        if (bd.ejecutarSentencia(sql)) {
            return true;
        }
        return false;
    }

    private boolean modificarRegistro(String codMulta, String strDescripcion, double dblValor) {
        String sql = "UPDATE COD_MULTAS SET DESCRIPCION='" + strDescripcion + "', VALOR=" + dblValor + " WHERE COD_MULTA='" + codMulta + "'";
        System.out.println("consulta realizada");
        if (bd.ejecutarSentencia(sql)) {
            return true;
        }
        return false;
    }

    private boolean eliminarRegistro(String strCodMulta) {

        String sql = "delete from COD_MULTAS where COD_MULTA='" + strCodMulta + "'";
        System.out.println("consulta realizada");
        if (bd.ejecutarSentencia(sql)) {
            return true;
        } else {

            JOptionPane.showMessageDialog(this, "NO SE PUDO ELIMINAR EL REGISTRO DE LA BASE DE DATOS",
                    "NO SE PUDO ELIMINAR EL REGISTRO",
                    JOptionPane.ERROR_MESSAGE);

            return false;
        }
    }

    private void buscarMulta(String strCodMulta) {
        String sql = "select * from COD_MULTAS where COD_MULTA='" + strCodMulta + "' ";
        System.out.println("consulta realizada");
        try {
            rs = bd.ejecutarConsultaUnDato(sql);
            if (rs != null) {
                txtCodMulta.setText(rs.getString(2));
                txtDescripcion.setText(rs.getString(3));
                txtValor.setText(rs.getString(4));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(Multas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "EL CODIGO DE MULTA INGRESADO NO EXISTE",
                    "REGISTRO NO ENCONTRADO",
                    JOptionPane.ERROR_MESSAGE);
            limpiar();
        }
    }

    private boolean verificarCodMulta(String strCodMulta) {
        String strCod_multa = null;
        try {
            String sql = "select COD_MULTA from COD_MULTAS where COD_MULTA='" + strCodMulta + "'";
            System.out.println("consulta realizada");
            rs = bd.ejecutarConsultaUnDato(sql);
            strCod_multa = rs.getString(1);
            System.out.println("el codigo de la multa obtenido es: " + strCod_multa);
        } catch (SQLException ex) {
            //Logger.getLogger(Multas.class.getName()).log(Level.SEVERE, null, ex);
        }

        try{
            if (strCod_multa.equals(strCodMulta)) {
                return true;
            }
        }catch(NullPointerException ex){         

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
}
