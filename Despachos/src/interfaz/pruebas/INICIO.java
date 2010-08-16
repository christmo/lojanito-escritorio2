/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * INICIO.java
 *
 * Created on 09/08/2010, 04:04:21 PM
 */
package interfaz.pruebas;

import javax.swing.ImageIcon;
import javax.swing.JDialog;

/**
 *
 * @author Usuario
 */
public class INICIO extends javax.swing.JFrame {

    JDialog turnos = null;
    JDialog nConductor = null;
    JDialog editVehiculo = null;
    JDialog beConductor = null;
    JDialog newVehiculo = null;
    JDialog multas = null;
    JDialog asignarMulta = null;

    /** Creates new form INICIO */
    public INICIO() {
        initComponents();

        this.setLocationRelativeTo(null);

        btnNvConductor.setText("<html>NUEVO<br>CONDUCTOR</html>");
        btnEditVehiculo.setText("<html>BUSCAR / EDITAR<br>VEHICULO</html>");
        btnEditConductor.setText("<html>BUSCAR / EDITAR<br>CONDUCTOR</html>");
        btnNewVehiculo.setText("<html>NUEVO<br>VEHICULO</html>");
        btnEstados.setText("<html>ESTADOS<br>TAXI</html>");
        btnDespacho.setText("<html>DESPACHO <BR> TAXIS</html>");

        //ICONO DE APLICACION
        this.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnNvConductor = new javax.swing.JButton();
        btnEstados = new javax.swing.JButton();
        btnEditVehiculo = new javax.swing.JButton();
        btnDespacho = new javax.swing.JButton();
        btnNewVehiculo = new javax.swing.JButton();
        btnTurnos = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnDirector = new javax.swing.JButton();
        btnEditConductor = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        btnAsignarM = new javax.swing.JButton();
        btnDirectorio = new javax.swing.JButton();
        btnMultas1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MENU PRINCIPAL");
        setIconImages(null);
        setName("frmInicio"); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNvConductor.setText("New Conductor");
        btnNvConductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNvConductorActionPerformed(evt);
            }
        });
        getContentPane().add(btnNvConductor, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 110, 85));

        btnEstados.setText("Estados Taxi");
        btnEstados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadosActionPerformed(evt);
            }
        });
        getContentPane().add(btnEstados, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 410, 110, 85));

        btnEditVehiculo.setText("B/E Vehiculos");
        btnEditVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditVehiculoActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 180, 110, 85));

        btnDespacho.setText("DESPACHO TAXIS");
        btnDespacho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDespachoActionPerformed(evt);
            }
        });
        getContentPane().add(btnDespacho, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 210, 110, 85));

        btnNewVehiculo.setText("New Vehiculo");
        btnNewVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewVehiculoActionPerformed(evt);
            }
        });
        getContentPane().add(btnNewVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 70, 110, 85));

        btnTurnos.setText("TURNOS");
        btnTurnos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTurnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTurnosActionPerformed(evt);
            }
        });
        getContentPane().add(btnTurnos, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, 110, 85));

        btnReportes.setText("REPORTES");
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });
        getContentPane().add(btnReportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 370, 110, 85));

        btnDirector.setText("AYUDA");
        btnDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDirectorActionPerformed(evt);
            }
        });
        getContentPane().add(btnDirector, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 450, 110, 50));

        btnEditConductor.setText("B/E  conductor");
        btnEditConductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditConductorActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditConductor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 110, 85));

        btnConfig.setText("CONFIG");
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });
        getContentPane().add(btnConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 110, 50));

        btnAsignarM.setText("ASIGNAR MULTAS");
        btnAsignarM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarMActionPerformed(evt);
            }
        });
        getContentPane().add(btnAsignarM, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 390, 130, 85));

        btnDirectorio.setText("DIRECOTORIO");
        btnDirectorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDirectorioActionPerformed(evt);
            }
        });
        getContentPane().add(btnDirectorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 110, 85));

        btnMultas1.setText("CREAR MULTAS");
        btnMultas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMultas1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnMultas1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 280, 110, 85));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDirectorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDirectorActionPerformed

    private void btnDespachoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDespachoActionPerformed
    }//GEN-LAST:event_btnDespachoActionPerformed

    private void btnNewVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewVehiculoActionPerformed
        //ID DE EMPRESA DEBE SER ENVIADO POR EL CONSTRUCTOR
        //PARAMETROS DE SESION
        if ((turnos == null) || (!turnos.isDisplayable())) {
            newVehiculo = new ingresoVehiculos(this, "LN");
            newVehiculo.setSize(680, 595);
            newVehiculo.setLocationRelativeTo(this);
            newVehiculo.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            newVehiculo.setResizable(false);
            newVehiculo.setVisible(true);
        }
    }//GEN-LAST:event_btnNewVehiculoActionPerformed

    private void btnEditConductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditConductorActionPerformed
        if ((turnos == null) || (!turnos.isDisplayable())) {
            beConductor = new modConductor(this);
            beConductor.setSize(705, 720);
            beConductor.setLocationRelativeTo(this);
            beConductor.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            beConductor.setResizable(false);
            beConductor.setVisible(true);
        }
    }//GEN-LAST:event_btnEditConductorActionPerformed

    private void btnEstadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEstadosActionPerformed

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
    }//GEN-LAST:event_btnConfigActionPerformed

    private void btnTurnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTurnosActionPerformed

        if ((turnos == null) || (!turnos.isDisplayable())) {
            turnos = new modTurnos(this);
            turnos.setSize(525, 250);
            turnos.setLocationRelativeTo(this);
            turnos.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            turnos.setResizable(false);
            turnos.setVisible(true);
        }

    }//GEN-LAST:event_btnTurnosActionPerformed

    private void btnNvConductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNvConductorActionPerformed
        if ((nConductor == null) || (!nConductor.isDisplayable())) {
            nConductor = new ingresoConductor(this);
            nConductor.setSize(650, 550);
            nConductor.setLocationRelativeTo(this);
            nConductor.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            nConductor.setResizable(false);
            nConductor.setVisible(true);
        }
    }//GEN-LAST:event_btnNvConductorActionPerformed

    private void btnEditVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditVehiculoActionPerformed

        //PASAR VARIABLES DE SESION
        //POR EL CONSTRUCTOR
        if ((editVehiculo == null) || (!editVehiculo.isDisplayable())) {
            editVehiculo = new modVehiculo(this, "LN");
            editVehiculo.setSize(725, 770);
            editVehiculo.setLocationRelativeTo(this);
            editVehiculo.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            editVehiculo.setResizable(false);
            editVehiculo.setVisible(true);
        }
    }//GEN-LAST:event_btnEditVehiculoActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnReportesActionPerformed

    private void btnAsignarMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarMActionPerformed

        if ((turnos == null) || (!turnos.isDisplayable())) {
            asignarMulta = new asignarMulta(this);
            asignarMulta.setSize(525, 400);
            asignarMulta.setLocationRelativeTo(this);
            asignarMulta.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            asignarMulta.setResizable(false);
            asignarMulta.setVisible(true);
        }
    }//GEN-LAST:event_btnAsignarMActionPerformed

    private void btnDirectorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDirectorioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDirectorioActionPerformed

    private void btnMultas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMultas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMultas1ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new INICIO().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsignarM;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnDespacho;
    private javax.swing.JButton btnDirector;
    private javax.swing.JButton btnDirectorio;
    private javax.swing.JButton btnEditConductor;
    private javax.swing.JButton btnEditVehiculo;
    private javax.swing.JButton btnEstados;
    private javax.swing.JButton btnMultas1;
    private javax.swing.JButton btnNewVehiculo;
    private javax.swing.JButton btnNvConductor;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnTurnos;
    // End of variables declaration//GEN-END:variables
}