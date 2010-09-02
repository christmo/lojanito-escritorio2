/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UIConfiguracion.java
 *
 * Created on 26/08/2010, 12:40:07 PM
 */
package configuracion;

import interfaz.funcionesUtilidad;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import login.LoginGUI;

/**
 *
 * @author root
 */
public class UIConfiguracion extends javax.swing.JDialog {

    private static ResourceBundle rb;
    private static Properties arcConfig;
    private static String url;
    private static FileWriter w;
    private static File archivo;
    private static BufferedWriter bw;
    private funcionesUtilidad f = new funcionesUtilidad();

    public UIConfiguracion() {
        initComponents();
    }

    /**
     * Creates new form UIConfiguracion
     * @param home
     */
    public UIConfiguracion(String home) {
        initComponents();
        UIConfiguracion.url = home;
        CargarPuertos();
        CrearArchivoPropiedades(url);

        this.setVisible(true);
    }

    private void CargarPuertos() {

        String[] pu = f.ListaPuertos();
        for (int i = 0; i < pu.length; i++) {
            jcPuertosComm.addItem(pu[i]);

        }
    }

    public static void CrearArchivoPropiedades(String url) {
        archivo = new File(url);
        try {
            w = new FileWriter(archivo);
            bw = new BufferedWriter(w);

            rb = ResourceBundle.getBundle("configuracion.configsystem");

            arcConfig = convertResourceBundleToProperties(rb);

            bw.write("#\n# Archivo generado cuando no encuentra la configuración inicial\n#\n");
            bw.write("ip_base = " + arcConfig.getProperty("ip_base") + "\n");
            bw.write("base = " + arcConfig.getProperty("base") + "\n");
            bw.write("comm = " + arcConfig.getProperty("comm") + "\n");
            bw.write("puerto_base = " + arcConfig.getProperty("puerto_base") + "\n");
            bw.write("user = " + arcConfig.getProperty("user") + "\n");
            bw.write("pass = " + arcConfig.getProperty("pass") + "\n");

            bw.write("dirImgConductores = " + arcConfig.getProperty("dirImgConductores") + "\n");
            bw.write("dirImgVehiculos = " + arcConfig.getProperty("dirImgVehiculos") + "\n");

            bw.close();

            System.out.println("Archivo escrito en: " + url);

        } catch (IOException ex) {
            Logger.getLogger(UIConfiguracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Convert ResourceBundle into a Properties object.
     *
     * @param resource a resource bundle to convert.
     * @return Properties a properties version of the resource bundle.
     */
    private static Properties convertResourceBundleToProperties(ResourceBundle resource) {
        Properties properties = new Properties();

        Enumeration<String> keys = resource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            properties.put(key, resource.getString(key));
        }

        return properties;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jbAceptar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jbCancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jtPuertoBase = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jcPuertosComm = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jtBD = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jtUser = new javax.swing.JTextField();
        jpPass = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        jtIP = new javax.swing.JTextField();

        setTitle("Configuración Inicial");
        setAlwaysOnTop(true);
        setResizable(false);

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/ok.png"))); // NOI18N
        jbAceptar.setText("Aceptar");
        jbAceptar.setToolTipText("Aceptar");
        jbAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAceptarActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Configuración Sistema");

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/cancelar.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jbCancelar.setToolTipText("Cancelar");
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Puertos"));

        jLabel4.setText("Base:");

        jtPuertoBase.setText("3306");

        jLabel5.setText("COM:");

        jcPuertosComm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(4, 4, 4)
                .addComponent(jtPuertoBase, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcPuertosComm, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtPuertoBase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jcPuertosComm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre de Base de Datos:"));

        jtBD.setText("rastreosatelital");

        jLabel1.setText("Usuario:");

        jLabel3.setText("Passwor:");

        jtUser.setText("root");

        jpPass.setText("kradac");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtBD, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtUser, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                            .addComponent(jpPass, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jtBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jpPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Dirección o IP del Servidor:"));

        jtIP.setText("localhost");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jtIP, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jtIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jbAceptar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(jbCancelar)
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jbAceptar))
                .addGap(93, 93, 93))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-366)/2, (screenSize.height-403)/2, 366, 403);
    }// </editor-fold>//GEN-END:initComponents

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jbCancelarActionPerformed

    private void jbAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAceptarActionPerformed
        comprobarArchivo();
    }//GEN-LAST:event_jbAceptarActionPerformed

    private void comprobarArchivo() {
        try {
            arcConfig.load(new FileInputStream(archivo));

            String ip = arcConfig.getProperty("ip_base");
            if (ip.equals("null") || ip.equals("")) {
                arcConfig.setProperty("ip_base", jtIP.getText());

            }
            String base = arcConfig.getProperty("base");
            if (base.equals("null") || base.equals("")) {
                arcConfig.setProperty("base", jtBD.getText());
            }
            String user = arcConfig.getProperty("user");
            if (user.equals("null") || user.equals("")) {
                arcConfig.setProperty("user", jtUser.getText());
            }
            String pass = arcConfig.getProperty("pass");
            if (pass.equals("null") || pass.equals("")) {
                arcConfig.setProperty("pass", f.getPasswordDesdeChar(jpPass.getPassword()));
            }

            String puertoBase = arcConfig.getProperty("puerto_base");
            if (puertoBase.equals("0") || puertoBase.equals("")) {
                arcConfig.setProperty("puerto_base", jtPuertoBase.getText());
            }

            String comm = arcConfig.getProperty("comm");
            if (comm.equals("0")) {
                arcConfig.setProperty("comm", jcPuertosComm.getSelectedItem().toString());
            }

            arcConfig.store(new FileOutputStream(archivo), null);

            System.out.println("Archivo Guardado con exito...\nIniciar Aplicación nuevamente...");
            this.dispose();
            LoginGUI log = new LoginGUI(arcConfig);
            log.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(UIConfiguracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @param args the command line arguments
     */
    /*public static void main(String args[]) {
    java.awt.EventQueue.invokeLater(new Runnable() {

    public void run() {
    new UIConfiguracion().setVisible(true);
    }
    });
    }*/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCancelar;
    private javax.swing.JComboBox jcPuertosComm;
    private javax.swing.JPasswordField jpPass;
    private javax.swing.JTextField jtBD;
    private javax.swing.JTextField jtIP;
    private javax.swing.JTextField jtPuertoBase;
    private javax.swing.JTextField jtUser;
    // End of variables declaration//GEN-END:variables
}