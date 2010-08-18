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
public class asignarMultas extends javax.swing.JDialog {

    funcionesUtilidad objUtilidad = new funcionesUtilidad();
    ConexionBase bd;
    ResultSet rs;
    public static String strUsuario;

    /** Creates new form modTurnos */
    public asignarMultas(JFrame padre,String strSesion[], ConexionBase con) {
        super(padre, "Asignacion de Multas");
        super.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());

        this.bd = con;
        asignarMultas.strUsuario = strSesion[0];
        initComponents();
        txtFecha.setText(objUtilidad.getFecha());
        txtHora.setText(objUtilidad.getHora());
        consultarNrosUnidad();
        consultarCodigoMultas();
        cmbEstado.setSelectedIndex(0); 
        cmbCodigoM.setSelectedIndex(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCancelar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        cmbEstado = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbCodigoM = new javax.swing.JComboBox();
        txtHora = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cmbNroUnidad = new javax.swing.JComboBox();

        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cerrar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        getContentPane().add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 340, -1, -1));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/guardar.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, -1, -1));

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione", "Pendiente", "Pagada" }));
        getContentPane().add(cmbEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 120, -1));

        jLabel7.setText("ESTADO:");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        jLabel6.setText("CODIGO MULTA:");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        cmbCodigoM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione" }));
        cmbCodigoM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCodigoMActionPerformed(evt);
            }
        });
        getContentPane().add(cmbCodigoM, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 120, -1));

        txtHora.setEditable(false);
        getContentPane().add(txtHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, 80, -1));

        jLabel5.setText("HORA:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, -1, -1));

        jLabel4.setText("FECHA:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, -1, -1));

        txtFecha.setEditable(false);
        getContentPane().add(txtFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 100, -1));

        jLabel3.setText("Nro. UNIDAD:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel1.setText("ASIGNAR MULTA");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, -1, -1));

        cmbNroUnidad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione" }));
        cmbNroUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbNroUnidadActionPerformed(evt);
            }
        });
        getContentPane().add(cmbNroUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 120, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        try {
            super.dispose();
        } catch (Throwable ex) {
            Logger.getLogger(Multas.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if(cmbNroUnidad.getSelectedIndex()>=0 && cmbCodigoM.getSelectedIndex()>0 && cmbEstado.getSelectedIndex()>0)
            if(objUtilidad.isNumeric(cmbNroUnidad.getSelectedItem().toString())){
                if(cmbEstado.getSelectedItem().equals("Pendiente")){
                    if(guardarRegistro(Integer.parseInt(cmbNroUnidad.getSelectedItem().toString()),txtFecha.getText(),txtHora.getText(),cmbCodigoM.getSelectedItem().toString(), cmbEstado.getSelectedIndex())){
                        JOptionPane.showMessageDialog(this, "REGISTRO GUARDADO EXITOSAMENTE",
                            "REGISTRO GUARDADO",
                            JOptionPane.INFORMATION_MESSAGE);
                        limpiar();
                    }
                }else
                    JOptionPane.showMessageDialog(this,"EL ESTADO DE LA MULTA ES INCORRECTO",
                            "VERIFIQUE EL ESTADO DE LA MULTA",
                            JOptionPane.INFORMATION_MESSAGE);

            }else
                JOptionPane.showMessageDialog(this, "EL NUMERO DE UNIDAD DEBE SER NUMERICO",
                        "VALOR DEL CAMPO INCORRECTO",
                        JOptionPane.ERROR_MESSAGE);
        else
            JOptionPane.showMessageDialog(this, "FALTAN SELECCIONAR ALGUNOS CAMPOS",
                        "FALTAN DATOS",
                        JOptionPane.ERROR_MESSAGE);

}//GEN-LAST:event_btnGuardarActionPerformed

    private void cmbCodigoMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCodigoMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCodigoMActionPerformed

    private void cmbNroUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbNroUnidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbNroUnidadActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox cmbCodigoM;
    private javax.swing.JComboBox cmbEstado;
    private javax.swing.JComboBox cmbNroUnidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtHora;
    // End of variables declaration//GEN-END:variables

    private void limpiar() {
        cmbNroUnidad.setSelectedIndex(-1);
        cmbEstado.setSelectedIndex(-1);
        cmbCodigoM.setSelectedIndex(-1);
    }

    private boolean guardarRegistro(int intNroUnidad, String strFecha, String strHora, String strCodMulta, int intEstado ) {
        String sql = "INSERT INTO MULTAS(USUARIO, N_UNIDAD, FECHA, HORA, COD_MULTA,ESTADO)"
                   + "VALUES('"+ strUsuario +"',"+ intNroUnidad +",'"+ strFecha +"','"+ strHora +"','"+ strCodMulta +"',"+intEstado+")";
        System.out.println("consulta realizada");
        if (bd.ejecutarSentencia(sql)) {
            return true;
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
            //return arrayCodigos;
        } catch (SQLException ex) {
            //Logger.getLogger(Multas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "NO EXISTEN AUN MULTAS REGISTRADAS",
                    "NO EXISTEN MULTAS",
                    JOptionPane.ERROR_MESSAGE);
            limpiar();
        }
    }

    private void consultarNrosUnidad() {
        String sql = "SELECT N_UNIDAD FROM VEHICULOS";
        System.out.println("consulta realizada");
        //ArrayList arrayCodigos= new ArrayList();
        try {
            rs = bd.ejecutarConsulta(sql);
            while (rs.next()) {
                cmbNroUnidad.addItem(rs.getString("N_UNIDAD"));
            }
            //return arrayCodigos;
        } catch (SQLException ex) {
            //Logger.getLogger(Multas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "NO EXISTEN UNIDADES INGRESADAS",
                    "ERROR",
                    JOptionPane.ERROR_MESSAGE);
            limpiar();
        }
    }
   
}
