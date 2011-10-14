/*                                       
 * Este proyecto pertenece a KRADAC soluciones tecnologicas                                      
 * Creando Tecnologia de Loja para el Mundo ;-)
 */

/*
 * LoginGUI.java
 *
 * Created on 02/08/2010, 05:57:26 PM
 */
package login;

import BaseDatos.ConexionBase;
import interfaz.INICIO;
import interfaz.Principal;
import interfaz.funcionesUtilidad;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sistemaoperativo.LevantarServicios;

/**
 * @author christmo
 */
public class LoginGUI extends javax.swing.JFrame {

    private static final Logger log = (Logger) LoggerFactory.getLogger(LoginGUI.class);
    private String strUser = null;
    private char[] chrPass = null;
    private String strPass = "";
    private ConexionBase bd = null;
    private ResultSet rs = null;
    private Properties arcConfig;
    //private String url_config = CargarRutaArchivoPropiedades();
    private funcionesUtilidad funciones = new funcionesUtilidad();
    public static String semillaPass = "KOMPRESORKR@D@C";
    private boolean entrar = false;

    /** Creates new form LoginGUI */
    public LoginGUI() {
        initComponents();
        lblMensaje.setVisible(false);
        this.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());
        jbtIngresar.setText("<html><center>INGRESAR</center></html>");
        jbtIngresar.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbtIngresar.setHorizontalTextPosition(SwingConstants.CENTER);

        try {
            arcConfig = funcionesUtilidad.obtenerArchivoPropiedades("configsystem.properties");
            try {
                bd = new ConexionBase(arcConfig);
                LevantarServicios.LevantarTeamViewer(bd.getValorConfiguiracion("tv"));
            } catch (UnsupportedOperationException ux) {
                String ms = ux.getMessage();
                if (ms.equals("base")) {
                    JOptionPane.showMessageDialog(null,
                            "Error Grave -> No se puede iniciar el Sistema:\n\n"
                            + "NO ES POSIBLE ACCEDER AL SERVIDOR DE BASE DE DATOS...\n\n"
                            + "NOMBRE DE LA BASE DATOS: " + arcConfig.getProperty("base"),
                            "Error...", 0);
                    log.trace("Error al ACCEDER AL SERVIDOR de la base de Datos: "
                            + arcConfig.getProperty("base") + " --> " + ux);
                    System.exit(1);
                } else if (ms.equals("servidor")) {
                    JOptionPane.showMessageDialog(null,
                            "Error Grave -> No se puede iniciar el Sistema:\n\n"
                            + "NO ES POSIBLE ABRIR O INGRESAR A LA BASE DE DATOS ESPECIFICADA...\n\n"
                            + "NOMBRE DE LA BASE DATOS: " + arcConfig.getProperty("base"),
                            "Error...", 0);
                    log.trace("Error al tratar de abrir la base de Datos: "
                            + arcConfig.getProperty("base") + " --> " + ux);
                    System.exit(1);
                } else if (ms.equals("nulo")) {
                    JOptionPane.showMessageDialog(null,
                            "Error Grave -> No se puede iniciar el Sistema:\n\n"
                            + "NO ES POSIBLE ABRIR O INGRESAR A LA BASE DE DATOS ESPECIFICADA...\n\n"
                            + "NOMBRE DE LA BASE DATOS: " + arcConfig.getProperty("base"),
                            "Error...", 0);
                    log.trace("La base de datos no existe...[{}]", arcConfig.getProperty("base"));
                    System.exit(1);
                }
                //UIConfiguracion config = new UIConfiguracion(url_config);
                //this.dispose();
                LevantarServicios.LevantarWAMP();
                bd = new ConexionBase(arcConfig);
            }
            existenDirectorios();
            entrar = true;
        } catch (FileNotFoundException ex) {
            log.trace("No se encontró el archivo de configuración...");
            lblMensaje.setText("No se encontró el archivo de configuración...");
            lblMensaje.setVisible(true);
        }
    }

    public LoginGUI(Properties arcConfig) {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());
        jbtIngresar.setText("<html><center>INGRESAR</center></html>");
        jbtIngresar.setVerticalTextPosition(SwingConstants.BOTTOM);
        jbtIngresar.setHorizontalTextPosition(SwingConstants.CENTER);
        this.arcConfig = arcConfig;
    }

    /**
     * Hace el cargado del archivo de propiedades en el direcctorio temporal
     * del sistema
     * @return String
     * @deprecated 
     */
    private String CargarRutaArchivoPropiedades() {
        if (System.getProperty("os.name").equals("Linux")) {
            System.out.println("Sistema Operativo: " + System.getProperty("os.name"));
            return System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "configsystem.properties";
        } else {
            System.out.println("Sistema Operativo: " + System.getProperty("os.name"));
            return System.getProperty("java.io.tmpdir") + "configsystem.properties";
        }
    }

    /**
     * Ingreso al sistema
     */
    private void ingresar() {
        strUser = null;
        chrPass = null;
        strPass = "";


        strUser = jtUser.getText();
        chrPass = jpPass.getPassword();

        for (int i = 0; i < chrPass.length; i++) {
            strPass += chrPass[i];
        }

        if (entrar) {
            log.trace("Iniciar sistema de Despachos... ;-)");
            try {
                /**
                 * Semilla del password: KOMPRESORKR@D@C
                 * MD5(MD5(MD5(clave)  + MD5(semil)));
                 */
                strPass = funciones.encriptar(strPass, semillaPass);
                String sql = "SELECT ID_EMPRESA,USUARIO,CLAVE,NOMBRE_USUARIO,ESTADO,OPERADOR "
                        + "FROM USUARIOS "
                        + "WHERE USUARIO = '" + strUser + "' AND CLAVE = '" + strPass + "'";

                rs = bd.ejecutarConsultaUnDato(sql);
                try {
                    String estado = rs.getString("ESTADO");

                    if (estado.equals("Activo")) {

                        String usuarioBase = rs.getString("USUARIO");
                        String claveBase = rs.getString("CLAVE");

                        boolean boolUsuario = (usuarioBase.toUpperCase().equals(strUser.toUpperCase()));
                        boolean boolClave = (claveBase.equals(strPass));

                        if (boolUsuario) {
                            if (boolClave) {
                                try {
                                    int intN_Rol = obtenerRolUsuario(rs.getString("OPERADOR"));
                                    /**
                                     * Sesion -> Arreglo de 4 datos en el orden que se muestra
                                     * [0]usuario,
                                     * [1]id_empresa,
                                     * [2]Nombre_del_Usuario,
                                     * [3]rol -> numero del rol del usuario
                                     */
                                    String sesion[] = {
                                        strUser,
                                        rs.getString("ID_EMPRESA"),
                                        rs.getString("NOMBRE_USUARIO"),
                                        "" + intN_Rol};

                                    log.trace("ROL: {}", rs.getString("OPERADOR"));

                                    String pago = bd.getValorConfiguiracion("not_pago");

                                    if (pago.equals("si") || pago.equals("SI")) {
                                        /**
                                         * Validación para cargar toda la interfaz del programa
                                         * se valida que sea de rol Operador para bloquear menu
                                         * o Super que pueden ver todo es SuperKRADAC ;-)
                                         */
                                        if (intN_Rol != 2 && (intN_Rol != 3 || intN_Rol == 4)) {//!= de Solo Lectura y Administrador
                                            if (intN_Rol != 0) {//!= de Sin Rol
                                                Principal pantalla = new Principal(sesion, bd, arcConfig);
                                            } else {
                                                JOptionPane.showMessageDialog(this, "Este usuario no tiene un rol asignado, comuniquese con el administrador del sistema...", "Error...", 0);
                                            }
                                        } else {
                                            INICIO menu = new INICIO(sesion, bd, arcConfig);
                                            menu.setLocationRelativeTo(this);
                                            menu.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                                            menu.setResizable(false);
                                        }
                                    } else {
                                        JOptionPane.showMessageDialog(this,
                                                "Sentimos informarle que el servicio de despachos se ha cancelado por falta de pago...\n"
                                                + "Debe comunicarse con el departamento técnico de KRADAC para habilitar el sistema.",
                                                "Comunicado...",
                                                JOptionPane.INFORMATION_MESSAGE);
                                    }
                                    this.dispose();
                                } catch (NullPointerException ex) {
                                    JOptionPane.showMessageDialog(this, "No ha sido asignado un ROL a este usuario...\nComunicarse con el administrador del sistema...", "Error", 0);
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Clave incorrecta", "Error", 0);
                                jpPass.setFocusCycleRoot(true);
                                jpPass.setText("");
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "Usuario incorrecto", "Error", 0);
                            jtUser.setFocusCycleRoot(true);
                            jtUser.setText("");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Este usuario a sido inactivado por el administrador del sistema, comuniquese con el inmediatamente para que le den acceso,"
                                + "\ncaso contrario no podrá acceder al sistema de despachos...", "Error", 0);
                    }
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(this, "El estado de este usuario es indefinido [Activo|Inactivo]...\nComunicarse con el administrador del sistema...", "Error", 0);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Comprobar si el usuario y la clave son correctos...", "Error", 0);
                jpPass.setFocusCycleRoot(true);
            } catch (NullPointerException npe) {
                log.trace("No hay acceso a la base de datos");
                JOptionPane.showMessageDialog(this, "No hay acceso a la base de datos, comprobar si la clave de la base de datos es corercta en el archivo de configuración...", "Error", 0);
                LevantarServicios.LevantarWAMP();
            }
        }
    }

    /**
     * Se envia el nombre de rol y este lo devuelve como un número dependiendo
     * de los roles que se tenga en la app<BR/>
     * 0 -> Sin Rol<BR/>
     * 1 -> Operador<BR/>
     * 2 -> Solo Lectura<BR/>
     * 3 -> Administrador<BR/>
     * 4 -> Super<BR/>
     * @param rol
     * @return int -> numero de rol al que pertenece el usuario
     */
    private int obtenerRolUsuario(String rol) {
        if (rol.equals("Operador")) {
            return 1;
        } else if (rol.equals("Solo Lectura")) {
            return 2;
        } else if (rol.equals("Administrador")) {
            return 3;
        } else if (rol.equals("Super")) {
            return 4;
        } else {
            return 0;
        }
    }

    /**
     * Creacion del directorio de imagenes para conductores y vehiculos
     */
    private void existenDirectorios() {
        try {
            String srcDirProyecto = bd.getValorConfiguiracion("dirProyecto");
            System.out.println("Dir Proyecto: " + srcDirProyecto);
            if (srcDirProyecto != null) {
                String con = bd.getValorConfiguiracion("dirImgConductores");
                if (con != null) {
                    File dirConductores = new File(srcDirProyecto + con);
                    if (!dirConductores.exists()) {
                        dirConductores.mkdir();
                        System.out.println("Creado Directorio: " + con);
                    } else {
                        System.out.println("Directorio: " + con + " ya existe...");
                    }
                }

                String veh = bd.getValorConfiguiracion("dirImgVehiculos");
                if (veh != null) {
                    File dirVehiculos = new File(srcDirProyecto + veh);
                    if (!dirVehiculos.exists()) {
                        dirVehiculos.mkdir();
                        System.out.println("Creado Directorio: " + veh);
                    } else {
                        System.out.println("Directorio: " + veh + " ya existe...");
                    }
                }
            }
        } catch (NullPointerException ex) {
            LevantarServicios.LevantarWAMP();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
//                    UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceOfficeBlue2007LookAndFeel");
//                    UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceDustLookAndFeel");
//                    UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel");
                    UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceNebulaBrickWallLookAndFeel");
//                    UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceSaharaLookAndFeel");

                } catch (Exception e) {
                    System.out.println("Problemas al cargar Temas Substance");
                }

                new LoginGUI().setVisible(true);

            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jtUser = new javax.swing.JTextField();
        jpPass = new javax.swing.JPasswordField();
        jbtIngresar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblMensaje = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ingreso al Sistema...");
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jLabel1.setText("Usuario:");

        jLabel2.setText("Clave:");

        jtUser.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtUserFocusLost(evt);
            }
        });
        jtUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtUserKeyPressed(evt);
            }
        });

        jpPass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jpPassFocusGained(evt);
            }
        });
        jpPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jpPassKeyPressed(evt);
            }
        });

        jbtIngresar.setText("Ingresar");
        jbtIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtIngresarActionPerformed(evt);
            }
        });
        jbtIngresar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbtIngresarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jbtIngresar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpPass, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtUser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jpPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jbtIngresar))
        );

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/kradac.jpg"))); // NOI18N

        lblMensaje.setForeground(new java.awt.Color(255, 0, 0));
        lblMensaje.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMensaje.setText("Texto");
        lblMensaje.setToolTipText("En la carpeta del programa debe existir un archivo con las configuraciones del programa, archivo con extensión .proreties...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(7, 7, 7)
                .addComponent(lblMensaje)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dialogSize = getSize();
        setLocation((screenSize.width-dialogSize.width)/2,(screenSize.height-dialogSize.height)/2);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtIngresarActionPerformed
        ingresar();
    }//GEN-LAST:event_jbtIngresarActionPerformed

    private void jpPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jpPassKeyPressed
        int cod = evt.getKeyCode();
        if (cod == 10) {
            ingresar();
        } else if (cod == 27) {
            System.exit(0);
        }
    }//GEN-LAST:event_jpPassKeyPressed

    private void jpPassFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jpPassFocusGained
        jpPass.setText("");
    }//GEN-LAST:event_jpPassFocusGained

    private void jtUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtUserKeyPressed
        int cod = evt.getKeyCode();
        if (cod == 10) {
            ingresar();
        } else if (cod == 27) {
            System.exit(0);
        }
    }//GEN-LAST:event_jtUserKeyPressed

    private void jtUserFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtUserFocusLost
        jtUser.setText(jtUser.getText().toUpperCase());
    }//GEN-LAST:event_jtUserFocusLost

    private void jbtIngresarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbtIngresarKeyPressed
        if (evt.getKeyCode() == 10) {
            ingresar();
        }
    }//GEN-LAST:event_jbtIngresarKeyPressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbtIngresar;
    private javax.swing.JPasswordField jpPass;
    private javax.swing.JTextField jtUser;
    private javax.swing.JLabel lblMensaje;
    // End of variables declaration//GEN-END:variables
}
