/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * INICIO.java
 *
 * Created on 09/08/2010, 04:04:21 PM
 */
package interfaz;

import BaseDatos.ConexionBase;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.SwingConstants;

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
    JDialog beasignarMulta = null;
    public static String sesion[] = null;
    private ConexionBase conec;

    /** Creates new form INICIO */
    public INICIO() {
        initComponents();
    }

    public INICIO(String strSesion[], ConexionBase bd) {
        this.conec = bd;
        INICIO.sesion = strSesion;
        initComponents();

        //this.setLocationRelativeTo(null);

        btnNvConductor.setText("<html><center>NUEVO<br>CONDUCTOR</center></html>");
        btnEditVehiculo.setText("<html><center>EDITAR<br>VEHICULO</center></html>");
        btnEditConductor.setText("<html><center>EDITAR<br>CONDUCTOR</center></html>");
        btnNewVehiculo.setText("<html><center>NUEVO<br>VEHICULO</center></html>");
        btnEstados.setText("<html><center>ESTADOS<br>TAXI</center></html>");
        btnBEMultas.setText("<html><center>EDITAR<br>ASIGNACION MULTAS</center></html>");
        btnTurnos.setText("<html><center>TURNOS</center></html>");
        btnDirectorio.setText("<html><center>DIRECTORIO</center></html>");
        btnReportes.setText("<html><center>REPORTES</center></html>");
        btnAsignarM.setText("<html><center>HISTORIAL DE <BR> MULTAS </center></html>");
        btnMultas.setText("<html><center>CREAR <BR> MULTAS </center></html>");

        btnNvConductor.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnNvConductor.setHorizontalTextPosition(SwingConstants.CENTER);


        btnEditVehiculo.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnEditVehiculo.setHorizontalTextPosition(SwingConstants.CENTER);

        btnEditConductor.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnEditConductor.setHorizontalTextPosition(SwingConstants.CENTER);

        btnNewVehiculo.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnNewVehiculo.setHorizontalTextPosition(SwingConstants.CENTER);

        btnEstados.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnEstados.setHorizontalTextPosition(SwingConstants.CENTER);

        btnBEMultas.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnBEMultas.setHorizontalTextPosition(SwingConstants.CENTER);

        btnTurnos.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnTurnos.setHorizontalTextPosition(SwingConstants.CENTER);

        btnDirectorio.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnDirectorio.setHorizontalTextPosition(SwingConstants.CENTER);

        btnReportes.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnReportes.setHorizontalTextPosition(SwingConstants.CENTER);

        btnAsignarM.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnAsignarM.setHorizontalTextPosition(SwingConstants.CENTER);

        btnMultas.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnMultas.setHorizontalTextPosition(SwingConstants.CENTER);


        //ICONO DE APLICACION
        this.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnNvConductor = new javax.swing.JButton();
        btnEstados = new javax.swing.JButton();
        btnEditVehiculo = new javax.swing.JButton();
        btnNewVehiculo = new javax.swing.JButton();
        btnTurnos = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnDirector = new javax.swing.JButton();
        btnEditConductor = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        btnBEMultas = new javax.swing.JButton();
        btnDirectorio = new javax.swing.JButton();
        btnMultas = new javax.swing.JButton();
        btnAsignarM = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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

        btnNvConductor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/chofer.png"))); // NOI18N
        btnNvConductor.setText("New Conductor");
        btnNvConductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNvConductorActionPerformed(evt);
            }
        });
        getContentPane().add(btnNvConductor, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 110, 85));

        btnEstados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/estados.png"))); // NOI18N
        btnEstados.setText("Estados Taxi");
        btnEstados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadosActionPerformed(evt);
            }
        });
        getContentPane().add(btnEstados, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, 110, 85));

        btnEditVehiculo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/bevehiculo.png"))); // NOI18N
        btnEditVehiculo.setText("B/E Vehiculos");
        btnEditVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditVehiculoActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 150, 110, 85));

        btnNewVehiculo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/vehiculo.png"))); // NOI18N
        btnNewVehiculo.setText("New Vehiculo");
        btnNewVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewVehiculoActionPerformed(evt);
            }
        });
        getContentPane().add(btnNewVehiculo, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, 110, 85));

        btnTurnos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/turnos.png"))); // NOI18N
        btnTurnos.setText("TURNOS");
        btnTurnos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTurnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTurnosActionPerformed(evt);
            }
        });
        getContentPane().add(btnTurnos, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 110, 85));

        btnReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/reporte.png"))); // NOI18N
        btnReportes.setText("REPORTES");
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });
        getContentPane().add(btnReportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 190, 110, 85));

        btnDirector.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/salir.png"))); // NOI18N
        btnDirector.setText("SALIR");
        btnDirector.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDirectorActionPerformed(evt);
            }
        });
        getContentPane().add(btnDirector, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 440, 130, 60));

        btnEditConductor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/bechofer.png"))); // NOI18N
        btnEditConductor.setText("B/E  conductor");
        btnEditConductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditConductorActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditConductor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 110, 85));

        btnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/configuracion.png"))); // NOI18N
        btnConfig.setText("CONFIG");
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });
        getContentPane().add(btnConfig, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, 110, 50));

        btnBEMultas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/bemulta.png"))); // NOI18N
        btnBEMultas.setText("B/E ASIGNACION DE MULTAS");
        btnBEMultas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBEMultasActionPerformed(evt);
            }
        });
        getContentPane().add(btnBEMultas, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 410, 130, 85));

        btnDirectorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/directorio.png"))); // NOI18N
        btnDirectorio.setText("DIRECTORIO");
        btnDirectorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDirectorioActionPerformed(evt);
            }
        });
        getContentPane().add(btnDirectorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 110, 85));

        btnMultas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/multas.png"))); // NOI18N
        btnMultas.setText("CREAR MULTAS");
        btnMultas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMultasActionPerformed(evt);
            }
        });
        getContentPane().add(btnMultas, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 270, 110, 85));

        btnAsignarM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/amulta.png"))); // NOI18N
        btnAsignarM.setText("ASIGNAR MULTAS");
        btnAsignarM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarMActionPerformed(evt);
            }
        });
        getContentPane().add(btnAsignarM, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 370, 130, 85));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDirectorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDirectorActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnDirectorActionPerformed

    private void btnNewVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewVehiculoActionPerformed
        if ((newVehiculo == null) || (!newVehiculo.isDisplayable())) {
            newVehiculo = new ingresoVehiculos(this, this.sesion, this.conec);
            newVehiculo.setSize(680, 595);
            newVehiculo.setLocationRelativeTo(this);
            newVehiculo.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            newVehiculo.setResizable(false);
            newVehiculo.setVisible(true);
        }
    }//GEN-LAST:event_btnNewVehiculoActionPerformed

    private void btnEditConductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditConductorActionPerformed
        if ((beConductor == null) || (!beConductor.isDisplayable())) {
            beConductor = new modConductor(this, this.conec);
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
            turnos = new modTurnos(this, this.conec);
            turnos.setSize(525, 250);
            turnos.setLocationRelativeTo(this);
            turnos.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            turnos.setResizable(false);
            turnos.setVisible(true);
        }

    }//GEN-LAST:event_btnTurnosActionPerformed

    private void btnNvConductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNvConductorActionPerformed
        if ((nConductor == null) || (!nConductor.isDisplayable())) {
            nConductor = new ingresoConductor(this, this.conec);
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
            editVehiculo = new modVehiculo(this, sesion, this.conec);
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

    private void btnBEMultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBEMultasActionPerformed
        if ((beasignarMulta == null) || (!beasignarMulta.isDisplayable())) {
            beasignarMulta = new modAsignarMultas(this, this.conec);
            beasignarMulta.setSize(700, 700);
            beasignarMulta.setLocationRelativeTo(this);
            beasignarMulta.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            beasignarMulta.setResizable(false);
            beasignarMulta.setVisible(true);
        }
    }//GEN-LAST:event_btnBEMultasActionPerformed

    private void btnDirectorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDirectorioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDirectorioActionPerformed

    private void btnMultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMultasActionPerformed
        // TODO add your handling code here:
        if ((multas == null) || (!multas.isDisplayable())) {
            multas = new Multas(this, this.conec);
            multas.setSize(500, 400);
            multas.setLocationRelativeTo(this);
            multas.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            multas.setResizable(false);
            multas.setVisible(true);
        }

    }//GEN-LAST:event_btnMultasActionPerformed

    private void btnAsignarMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarMActionPerformed
        // TODO add your handling code here:
        if ((asignarMulta == null) || (!asignarMulta.isDisplayable())) {
            asignarMulta = new asignarMultas(this, sesion, this.conec);
            asignarMulta.setSize(350, 450);
            asignarMulta.setLocationRelativeTo(this);
            asignarMulta.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            asignarMulta.setResizable(false);
            asignarMulta.setVisible(true);
        }
        btnAsignarM.setVerticalTextPosition(PROPERTIES);

    }//GEN-LAST:event_btnAsignarMActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new INICIO().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsignarM;
    private javax.swing.JButton btnBEMultas;
    private javax.swing.JButton btnConfig;
    private javax.swing.JButton btnDirector;
    private javax.swing.JButton btnDirectorio;
    private javax.swing.JButton btnEditConductor;
    private javax.swing.JButton btnEditVehiculo;
    private javax.swing.JButton btnEstados;
    private javax.swing.JButton btnMultas;
    private javax.swing.JButton btnNewVehiculo;
    private javax.swing.JButton btnNvConductor;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnTurnos;
    // End of variables declaration//GEN-END:variables
}
