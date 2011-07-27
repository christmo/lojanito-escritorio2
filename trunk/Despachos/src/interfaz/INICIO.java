/*
 * INICIO.java
 *
 * Created on 09/08/2010, 04:04:21 PM
 */
package interfaz;

import BaseDatos.ConexionBase;
import interfaz.comunicacion.mail.VentanaMail;
import interfaz.navegador.BrowserControl;
import interfaz.reportes.Reportes;
import interfaz.subVentanas.VentanaDatos;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.SwingConstants;

/**
 *
 * @author christmo
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
    JDialog nEstado = null;
    JDialog modEstados = null;
    JDialog modUsuarios = null;
    JDialog reportes = null;
    private String sesion[] = null;
    private ConexionBase conec;
    private Properties arcConfig;
    /**
     * Rol del usuario logueado en el sistema
     * 0 -> Sin Rol
     * 1 -> Operador
     * 2 -> Solo Lectura
     * 3 -> Administrador
     */
    private int intRol;

    /** Creates new form INICIO */
    public INICIO() {
        initComponents();
    }

    public INICIO(String strSesion[], ConexionBase bd, Properties archivo) {
        this.conec = bd;
        this.sesion = strSesion;
        this.arcConfig = archivo;
        this.intRol = Integer.parseInt(strSesion[3]);
        initComponents();

        try {
            lblEmpresa.setText(bd.getEmpresa(strSesion[1]));
        } catch (NullPointerException ex) {
        }

        btnNvConductor.setText("<html><center>NUEVO<br>CONDUCTOR</center></html>");
        btnEditVehiculo.setText("<html><center>EDITAR<br>VEHICULO</center></html>");
        btnEditConductor.setText("<html><center>EDITAR<br>CONDUCTOR</center></html>");
        btnNewVehiculo.setText("<html><center>NUEVO<br>VEHICULO</center></html>");
        btnEstados.setText("<html><center>ESTADOS<br>DEL TAXI</center></html>");
        btnEditarMultas.setText("<html><center>EDITAR<br> MULTAS</center></html>");
        btnTurnos.setText("<html><center>TURNOS</center></html>");
        btnDirectorio.setText("<html><center>DIRECTORIO</center></html>");
        btnReportes.setText("<html><center>REPORTES</center></html>");
        btnAsignarM.setText("<html><center>ASIGNAR <BR> MULTA</center></html>");
        btnMultas.setText("<html><center>CREAR MULTAS</center></html>");
        btnConfig.setText("<html><center>CONFIG</center></html>");
        jbUsuarios.setText("<html><center>USUARIOS</center></html>");
        jbClientes.setText("<html><center>CLIENTES</center></html>");
        jbProblemas.setText("<html><center>PROBLEMAS</center></html>");
        jbMapa.setText("<html><center>MAPA</center></html>");
        jbMailKradac.setText("<html><center>MAIL KRADAC</center></html>");
        btnSalir.setText("<html><center>CERRAR</center></html>");

        btnModEstados.setText("<html><center>EDITAR<br> ESTADOS</center></html>");

        btnModEstados.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnModEstados.setHorizontalTextPosition(SwingConstants.CENTER);

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

        btnEditarMultas.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnEditarMultas.setHorizontalTextPosition(SwingConstants.CENTER);

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

        btnConfig.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnConfig.setHorizontalTextPosition(SwingConstants.CENTER);

        jbUsuarios.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbUsuarios.setHorizontalTextPosition(SwingConstants.CENTER);

        jbClientes.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbClientes.setHorizontalTextPosition(SwingConstants.CENTER);

        jbProblemas.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbProblemas.setHorizontalTextPosition(SwingConstants.CENTER);

        jbMapa.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbMapa.setHorizontalTextPosition(SwingConstants.CENTER);

        jbMailKradac.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbMailKradac.setHorizontalTextPosition(SwingConstants.CENTER);

        btnSalir.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnSalir.setHorizontalTextPosition(SwingConstants.CENTER);


        //ICONO DE APLICACION
        this.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());
        this.setVisible(true);

        validarRolUsuario();
        btnConfig.setVisible(false);
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
        btnSalir = new javax.swing.JButton();
        btnEditConductor = new javax.swing.JButton();
        btnEditarMultas = new javax.swing.JButton();
        btnDirectorio = new javax.swing.JButton();
        btnMultas = new javax.swing.JButton();
        btnAsignarM = new javax.swing.JButton();
        lblNombreAplicacion = new javax.swing.JLabel();
        lblEmpresa = new javax.swing.JLabel();
        btnModEstados = new javax.swing.JButton();
        jbUsuarios = new javax.swing.JButton();
        jbClientes = new javax.swing.JButton();
        jbProblemas = new javax.swing.JButton();
        jbMapa = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        jbMailKradac = new javax.swing.JButton();

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

        btnNvConductor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/chofer.png"))); // NOI18N
        btnNvConductor.setText("New Conductor");
        btnNvConductor.setPreferredSize(new java.awt.Dimension(186, 58));
        btnNvConductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNvConductorActionPerformed(evt);
            }
        });

        btnEstados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/estados.png"))); // NOI18N
        btnEstados.setText("Estados Taxi");
        btnEstados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadosActionPerformed(evt);
            }
        });

        btnEditVehiculo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/modauto.png"))); // NOI18N
        btnEditVehiculo.setText("B/E Vehiculos");
        btnEditVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditVehiculoActionPerformed(evt);
            }
        });

        btnNewVehiculo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/auto.png"))); // NOI18N
        btnNewVehiculo.setText("New Vehiculo");
        btnNewVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewVehiculoActionPerformed(evt);
            }
        });

        btnTurnos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/turnos.png"))); // NOI18N
        btnTurnos.setText("TURNOS");
        btnTurnos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTurnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTurnosActionPerformed(evt);
            }
        });

        btnReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/reporte.png"))); // NOI18N
        btnReportes.setText("REPORTES");
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/salir.png"))); // NOI18N
        btnSalir.setText("SALIR");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnEditConductor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/bechofer.png"))); // NOI18N
        btnEditConductor.setText("B/E  conductor");
        btnEditConductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditConductorActionPerformed(evt);
            }
        });

        btnEditarMultas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/bemulta.png"))); // NOI18N
        btnEditarMultas.setText("B/E ASIGNACION DE MULTAS");
        btnEditarMultas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarMultasActionPerformed(evt);
            }
        });

        btnDirectorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/directorio.png"))); // NOI18N
        btnDirectorio.setText("DIRECTORIO");
        btnDirectorio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDirectorioActionPerformed(evt);
            }
        });

        btnMultas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/multas.png"))); // NOI18N
        btnMultas.setText("CREAR MULTAS");
        btnMultas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMultasActionPerformed(evt);
            }
        });

        btnAsignarM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/amulta.png"))); // NOI18N
        btnAsignarM.setText("ASIGNAR MULTAS");
        btnAsignarM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarMActionPerformed(evt);
            }
        });

        lblNombreAplicacion.setFont(new java.awt.Font("Nimbus Roman No9 L", 1, 36));
        lblNombreAplicacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNombreAplicacion.setText("KOMPRESOR");

        lblEmpresa.setFont(new java.awt.Font("Arial Black", 1, 18));
        lblEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEmpresa.setText("...");

        btnModEstados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/modEstados.png"))); // NOI18N
        btnModEstados.setText("Estados Taxi");
        btnModEstados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModEstadosActionPerformed(evt);
            }
        });

        jbUsuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/usuario.png"))); // NOI18N
        jbUsuarios.setText("Usuarios");
        jbUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbUsuariosActionPerformed(evt);
            }
        });

        jbClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/llamada.png"))); // NOI18N
        jbClientes.setText("Clientes");
        jbClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbClientesActionPerformed(evt);
            }
        });

        jbProblemas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/email.png"))); // NOI18N
        jbProblemas.setText("Problemas");
        jbProblemas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbProblemasActionPerformed(evt);
            }
        });

        jbMapa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/mapa.png"))); // NOI18N
        jbMapa.setText("Mapa");
        jbMapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbMapaActionPerformed(evt);
            }
        });

        btnConfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/configuracion.png"))); // NOI18N
        btnConfig.setText("Config");

        jbMailKradac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/mailK.png"))); // NOI18N
        jbMailKradac.setText("Mail Kradac");
        jbMailKradac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbMailKradacActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblNombreAplicacion, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblEmpresa, javax.swing.GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnDirectorio, javax.swing.GroupLayout.PREFERRED_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(btnEditConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(btnNvConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(jbUsuarios, javax.swing.GroupLayout.PREFERRED_SIZE, 110, Short.MAX_VALUE))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbClientes, 0, 0, Short.MAX_VALUE)
                            .addComponent(btnEditVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(btnNewVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, Short.MAX_VALUE)
                            .addComponent(btnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 110, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnModEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnAsignarM, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jbMapa, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                                    .addComponent(btnMultas, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jbMailKradac, 0, 0, Short.MAX_VALUE)
                                    .addComponent(btnEditarMultas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jbProblemas, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 306, Short.MAX_VALUE)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNombreAplicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEmpresa)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNewVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNvConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditConductor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAsignarM, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModEstados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditVehiculo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReportes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditarMultas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDirectorio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMultas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbUsuarios, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbClientes, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                        .addComponent(jbMapa, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                        .addComponent(jbMailKradac, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)))
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbProblemas, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnNewVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewVehiculoActionPerformed
        if ((newVehiculo == null) || (!newVehiculo.isDisplayable())) {
            newVehiculo = new ingresoVehiculos(this, sesion, this.conec, arcConfig);
            newVehiculo.setSize(740, 590);
            newVehiculo.setLocationRelativeTo(this);
            newVehiculo.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            newVehiculo.setResizable(false);
        }
        newVehiculo.setVisible(true);
    }//GEN-LAST:event_btnNewVehiculoActionPerformed

    private void btnEditConductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditConductorActionPerformed
        if ((beConductor == null) || (!beConductor.isDisplayable())) {
            beConductor = new modConductor(this, this.conec, arcConfig);
            beConductor.setSize(705, 700);
            beConductor.setLocationRelativeTo(this);
            beConductor.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            beConductor.setResizable(false);
        }
        beConductor.setVisible(true);
    }//GEN-LAST:event_btnEditConductorActionPerformed

    private void btnEstadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadosActionPerformed
        if ((nEstado == null) || (!nEstado.isDisplayable())) {
            nEstado = new ingresoEstados(this, this.conec);
            nEstado.setSize(354, 267);
            nEstado.setLocationRelativeTo(this);
            nEstado.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            nEstado.setResizable(false);
        }
        nEstado.setVisible(true);
    }//GEN-LAST:event_btnEstadosActionPerformed

    private void btnTurnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTurnosActionPerformed

        if ((turnos == null) || (!turnos.isDisplayable())) {
            turnos = new modTurnos(this, this.conec);
            turnos.setSize(525, 250);
            turnos.setLocationRelativeTo(this);
            turnos.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            turnos.setResizable(false);
        }
        turnos.setVisible(true);

    }//GEN-LAST:event_btnTurnosActionPerformed

    private void btnNvConductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNvConductorActionPerformed
        if ((nConductor == null) || (!nConductor.isDisplayable())) {
            nConductor = new ingresoConductor(this, this.conec, arcConfig);
            nConductor.setSize(757, 512);
            nConductor.setLocationRelativeTo(this);
            nConductor.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            nConductor.setResizable(false);
        }
        nConductor.setVisible(true);
    }//GEN-LAST:event_btnNvConductorActionPerformed

    private void btnEditVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditVehiculoActionPerformed

        if ((editVehiculo == null) || (!editVehiculo.isDisplayable())) {
            editVehiculo = new modVehiculo(this, sesion, this.conec, arcConfig);
            editVehiculo.setSize(819, 700);
            editVehiculo.setLocationRelativeTo(this);
            editVehiculo.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            editVehiculo.setResizable(false);
        }
        editVehiculo.setVisible(true);
    }//GEN-LAST:event_btnEditVehiculoActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        if ((reportes == null) || (!reportes.isDisplayable())) {
            reportes = new Reportes(this, sesion, this.conec);
            reportes.setLocationRelativeTo(this);
            reportes.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            reportes.setResizable(false);
        }
        reportes.setVisible(true);
    }//GEN-LAST:event_btnReportesActionPerformed

    private void btnEditarMultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarMultasActionPerformed
        if ((beasignarMulta == null) || (!beasignarMulta.isDisplayable())) {
            beasignarMulta = new modAsignarMultas(this, this.conec, sesion);
            beasignarMulta.setSize(538, 635);
            beasignarMulta.setLocationRelativeTo(this);
            beasignarMulta.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            beasignarMulta.setResizable(false);
        }
        beasignarMulta.setVisible(true);
    }//GEN-LAST:event_btnEditarMultasActionPerformed

    private void btnDirectorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDirectorioActionPerformed
        // TODO añadir funcionalidad al boton de directorio telefonico
    }//GEN-LAST:event_btnDirectorioActionPerformed

    private void btnMultasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMultasActionPerformed
        if ((multas == null) || (!multas.isDisplayable())) {
            multas = new Multas(this, this.conec);
            multas.setSize(496, 400);
            multas.setLocationRelativeTo(this);
            multas.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            multas.setResizable(false);
        }
        multas.setVisible(true);

    }//GEN-LAST:event_btnMultasActionPerformed

    private void btnAsignarMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarMActionPerformed
        if ((asignarMulta == null) || (!asignarMulta.isDisplayable())) {
            asignarMulta = new asignarMultas(this, sesion, this.conec);
            asignarMulta.setSize(350, 354);
            asignarMulta.setLocationRelativeTo(this);
            asignarMulta.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            asignarMulta.setResizable(false);
        }
        asignarMulta.setVisible(true);

    }//GEN-LAST:event_btnAsignarMActionPerformed

    private void btnModEstadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModEstadosActionPerformed
        if ((modEstados == null) || (!modEstados.isDisplayable())) {
            modEstados = new modEstados(this, this.conec);
            modEstados.setSize(500, 320);
            modEstados.setLocationRelativeTo(this);
            modEstados.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            modEstados.setResizable(false);
        }
        modEstados.setVisible(true);
    }//GEN-LAST:event_btnModEstadosActionPerformed

    private void jbUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbUsuariosActionPerformed
        if ((modUsuarios == null) || (!modUsuarios.isDisplayable())) {
            modUsuarios = new modUsuarios(this, this.conec);
            modUsuarios.setLocationRelativeTo(this);
            modUsuarios.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            modUsuarios.setResizable(false);
        }
        modUsuarios.setVisible(true);
    }//GEN-LAST:event_jbUsuariosActionPerformed
    private VentanaDatos v;
    private void jbClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbClientesActionPerformed
        if ((v == null) || (!v.isDisplayable())) {
            v = new VentanaDatos(true, conec);
            v.setLocationRelativeTo(this);
            v.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            v.setResizable(false);
        }
        v.setVisible(true);
    }//GEN-LAST:event_jbClientesActionPerformed
    private VentanaMail mail;
    private void jbProblemasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbProblemasActionPerformed
        if ((mail == null) || (!mail.isDisplayable())) {
            mail = new VentanaMail(conec, sesion);
            mail.setLocationRelativeTo(this);
            mail.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            mail.setResizable(false);
        }
        mail.setVisible(true);
    }//GEN-LAST:event_jbProblemasActionPerformed

    private void jbMapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbMapaActionPerformed
        String url = "http://" + arcConfig.getProperty("ip_mapa_local")
                + ":"
                + arcConfig.getProperty("puerto_mapa_local")
                + "/"
                + arcConfig.getProperty("url")
                + arcConfig.getProperty("url_home");
        BrowserControl.displayURL(url);
    }//GEN-LAST:event_jbMapaActionPerformed

    private void jbMailKradacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbMailKradacActionPerformed
        String url = "http://mail.kradac.com";
        BrowserControl.displayURL(url);
    }//GEN-LAST:event_jbMailKradacActionPerformed

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
    private javax.swing.JButton btnDirectorio;
    private javax.swing.JButton btnEditConductor;
    private javax.swing.JButton btnEditVehiculo;
    private javax.swing.JButton btnEditarMultas;
    private javax.swing.JButton btnEstados;
    private javax.swing.JButton btnModEstados;
    private javax.swing.JButton btnMultas;
    private javax.swing.JButton btnNewVehiculo;
    private javax.swing.JButton btnNvConductor;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnTurnos;
    private javax.swing.JButton jbClientes;
    private javax.swing.JButton jbMailKradac;
    private javax.swing.JButton jbMapa;
    private javax.swing.JButton jbProblemas;
    private javax.swing.JButton jbUsuarios;
    private javax.swing.JLabel lblEmpresa;
    private javax.swing.JLabel lblNombreAplicacion;
    // End of variables declaration//GEN-END:variables

    /**
     * Muestra solo los botones necesario dependiendo del tipo de rol
     * Rol del usuario logueado en el sistema
     * 0 -> Sin Rol
     * 1 -> Operador
     * 2 -> Solo Lectura
     * 3 -> Administrador
     */
    private void validarRolUsuario() {
        switch (intRol) {
            case 1:
                controlRolOperador();
                break;
            case 2:
                controlRolSoloLectura();
                break;
            case 3:
                //controlAdministrador();
            default:
        }
    }

    /**
     * Asignar permisos para los operadores del sistema, estos pueden:
     * Asignar Multas
     * Reportes
     * Clientes
     * Mapa
     * Mail
     */
    private void controlRolOperador() {
        boolean op = false;
        jbUsuarios.setEnabled(op);
        btnTurnos.setEnabled(op);
        btnEstados.setEnabled(op);
        btnModEstados.setEnabled(op);

        btnNvConductor.setEnabled(op);
        btnNewVehiculo.setEnabled(op);

        btnEditConductor.setEnabled(op);
        btnEditVehiculo.setEnabled(op);

        btnEditarMultas.setEnabled(op);
        btnMultas.setEnabled(op);
    }

    /**
     * Permisos para usuarios de solo lectura, no se puede hacer más que ver
     * Reportes
     * Mapa
     * Mail
     */
    private void controlRolSoloLectura() {
        boolean op = false;
        btnNvConductor.setEnabled(op);
        btnNewVehiculo.setEnabled(op);

        btnEditConductor.setEnabled(op);
        btnEditVehiculo.setEnabled(op);

        btnEstados.setEnabled(op);
        btnModEstados.setEnabled(op);
        btnTurnos.setEnabled(op);

        btnAsignarM.setEnabled(op);
        btnEditarMultas.setEnabled(op);
        btnMultas.setEnabled(op);

        jbUsuarios.setEnabled(op);
        jbClientes.setEnabled(op);

    }
}
