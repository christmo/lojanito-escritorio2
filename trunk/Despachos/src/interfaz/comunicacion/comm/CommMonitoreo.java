/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.comunicacion.comm;

import BaseDatos.ConexionBase;
import configuracion.Copiar;
import interfaz.Principal;
import interfaz.TrabajoTablas;
import interfaz.funcionesUtilidad;
import interfaz.subVentanas.Despachos;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.pushingpixels.lafwidget.UiThreadingViolationException;

/**
 *
 * @author christmo
 */
public class CommMonitoreo extends Thread {

    private CommPortIdentifier id_Puerto;
    private SerialPort sPuerto;
    public Enumeration listaPuertos;
    private JLabel indicador;
    private JTextField jtTelefono;
    private JTable jtPorDespachar;
    /*Llenar campos en la tabla*/
    private ConexionBase bd;
    private ResultSet rs;
    private funcionesUtilidad funciones = new funcionesUtilidad();
    private DefaultTableModel dtm;
    private TrabajoTablas trabajarTabla;
    /*Despacho*/
    private Despachos despacho;
    private String strHora;
    private String intCodigo;
    private String strNombre;
    private String strDireccion;
    private String strBarrio;

    public CommMonitoreo() {
    }

    /**
     * Abrir el puerto con configuraciones por defecto
     * @param puerto
     */
    public CommMonitoreo(String puerto, ConexionBase bd) {
        this.bd = bd;
        if (!AbrirPuerto(puerto)) {
            System.err.println("No se pudo abrir el puerto");
        }
        if (!setParametros(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE)) {
            System.err.println("No se pudo setear puerto");
            CerrarPuerto();
        }
    }

    @Override
    public void run() {
        int cod = 0;
        String tel = "";

        boolean numero = false;
        boolean celular = false;
        String strNumero = "";
        String strCelular = "";
        String strNumeroCelularAnterior = "";
        int intContadorTimbreCelular = 1;//numero maximo antes de cortar automaticamente 8

        while (true) {
            cod = leerDatosCode();

            if (cod == 13) {
                if (strNumero.length() == 8) {
                    strNumero = "0" + strNumero;
                    timbrar(true, strNumero);
                    setDespachoCliente(strNumero);
                } else if (strNumero.length() == 9) {
                    timbrar(true, strNumero);
                    try {
                        setDespachoCliente(strNumero);
                    } catch (UiThreadingViolationException a) {
                    }
                }
            }

            if (cod == 10) {
                tel = "";
                numero = false;
            } else {
                tel += "" + (char) cod;
            }

            if (tel.equals("RING")) {
                if (!celular) {
                    timbrar(true, strNumero);
                }
                strNumero = "";
                strCelular = "";
                tel = "";
            } else if (tel.equals("NMBR = ")) {
                tel = "";
                numero = true;
                celular = false;
                intContadorTimbreCelular = 1;
                strNumeroCelularAnterior = "";
            } else if (tel.equals("+CLIP: \"")) {//+CLIP: "099362766",129,"",0,"",0
                tel = "";
                numero = true;
                celular = true;
            }

            if (numero) {
                strNumero = tel;
                if (celular) {
                    if (tel.length() == 9) {
                        strCelular = strNumero.substring(0, 9);
                        System.out.println("Numero Celular: " + strCelular);
                        timbrar(true, strCelular);
                        if (!strNumeroCelularAnterior.equals(strCelular)) {
                            try {
                                setDespachoCliente(strNumero);
                                strNumeroCelularAnterior = strNumero;
                                intContadorTimbreCelular++;
                            } catch (UiThreadingViolationException a) {
                            }
                        } else {
                            /*
                             * Antes que ingrese otra llamada del mismo celular es necesario
                             * que timbre ocho veces que es el maximo permitido, esto se realiza
                             * porque en la trama del numero celular se envia el numero
                             * constantemente en cada RING
                             */
                            //System.out.println("N: " + intContadorTimbreCelular);
                            if (intContadorTimbreCelular >= 8) {
                                intContadorTimbreCelular = 1;
                                strNumeroCelularAnterior = "";
                            } else {
                                intContadorTimbreCelular++;
                            }
                        }
                    }
                } else { // telefono convencional
                    timbrar(true, strNumero);
                }
            }
        }
    }

    /**
     * busca un cliente por el numero de telefono para despacharlo, devuelve el
     * codigo del usuario a ser despachado
     * @param strTelefono
     * @return String
     */
    private String setDespachoCliente(String strTelefono) {

        try {
            if (!strTelefono.equals("")) {
                rs = bd.getClientePorTelefono(strTelefono);
                intCodigo = rs.getString("CODIGO");
                strNombre = rs.getString("NOMBRE_APELLIDO_CLI");
                strDireccion = rs.getString("DIRECCION_CLI");
                strBarrio = rs.getString("SECTOR");
                strHora = funciones.getHora();
                despacho = new Despachos(strHora, strTelefono, intCodigo, strNombre, strDireccion, strBarrio, "");


                setDatosTablas(despacho, jtPorDespachar);

            } else {
                JOptionPane.showMessageDialog(null, "Numero ingresado no valido...", "Error", 0);
                jtTelefono.setText("");
            }
        } catch (SQLException ex) {
            System.err.println("No hay ningún cliente con ese Teléfono...");

            dtm = (DefaultTableModel) jtPorDespachar.getModel();
            String[] inicial = {
                funciones.getHora(),
                funciones.validarTelefono(jtTelefono.getText()),
                "",
                "",
                "",
                "",
                "",
                "",
                "0",
                ""};
            dtm.insertRow(0, inicial);
        }
        return intCodigo;
    }

    /**
     * Ingresa un despacho en la tabla de clientes por Despachar en la
     * Parte superior de la Ventana
     * @param des
     * @param tabla
     */
    private void setDatosTablas(Despachos des, JTable tabla) {
        trabajarTabla = new TrabajoTablas();
        Principal.listaDespachosTemporales.add(des);

        dtm = (DefaultTableModel) tabla.getModel(); //modelo de la tabla PorDespachar

        trabajarTabla.InsertarFilas(tabla, des, dtm);
    }

    /**
     * Setea el objeto de la UI que va a representar al indicador de la llamada
     * este tiene que ser un JLabel
     * @param indicador
     */
    public void setIndicadorLlamada(JTextField jtTelefono, JLabel indicador, JTable jtPorDespachar) {
        this.jtTelefono = jtTelefono;
        this.indicador = indicador;
        this.jtPorDespachar = jtPorDespachar;
    }

    /**
     * Recibe true para activar el icono de llama entrante o sin llamada
     * @param timbre boolean
     */
    private void timbrar(boolean timbre, String strNumero) {
        if (timbre) {
            Icon img = new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/llamada.png"));
            this.indicador.setIcon(img);
            jtTelefono.setText(strNumero);
        } else {
            Icon img = new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/nollamada.png"));
            this.indicador.setIcon(img);
            jtTelefono.setText("");
        }
    }

    /**
     * Abre el puerto de comunicación serial, se bloquea para su uso por 60s
     * @param comm
     * @return boolean -> true si no hay problemas al abrir el puerto
     */
    private boolean AbrirPuerto(String comm) {
        try {
            id_Puerto = CommPortIdentifier.getPortIdentifier(comm);
            sPuerto = (SerialPort) id_Puerto.open("MonitoreoKradac", 5000); //tiempo de bloqueo 1m
            return true;
        } catch (PortInUseException ex) {
            //Logger.getLogger(CommMonitoreo.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Puerto del modem está en uso por otra apicación...\nModificar los paramatros de inicio si no quiere usar el identificador de llamadas.", "error", 0);
            System.err.println("Cerrar Apicación - puerto en uso o no hay puerto serial disponible...");
            System.exit(0);
        } catch (NoSuchPortException ex) {
            JOptionPane.showMessageDialog(null, "No se ha cargado el driver COMM de java:\n\n" + ex + "\n\nNo se puede cargar la aplicación...", "error", 0);
            //JOptionPane.showMessageDialog(null, "Se ha producido el siguiente error:\n\n" + ex + "\n\nNo se puede cargar la aplicación...", "error", 0);
            CargarDriverCOMM();
            Logger.getLogger(CommMonitoreo.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        return false;
    }
    /*
     * Librerias para windows para leer el puerto comm desde java
     */
    String strWin32 = "win32com.dll";
    String strComm = "comm.jar";
    String strProp = "javax.comm.properties";

    /**
     * Copia el driver para que pueda java leer el puerto com en windows
     */
    public void CargarDriverCOMM() {
        Copiar copia = new Copiar();

        String dirLib = System.getProperty("java.home") + System.getProperty("file.separator") + "lib/";
        String dirBin = System.getProperty("java.home") + System.getProperty("file.separator") + "bin/";
        //System.out.println(""+dirLib);
        //System.out.println(""+dirBin);
        //System.out.println(""+System.getProperty("user.dir")+System.getProperty("file.separator")+"lib");

        File jar = new File(System.getProperty("java.class.path"));
        String srcDirCOMM = jar.getPath().substring(0, jar.getPath().length() - jar.getName().length()) + "comm/";

        System.out.println("PATH: " + srcDirCOMM + strWin32);
        System.out.println("PATH: " + srcDirCOMM + strComm);
        System.out.println("PATH: " + srcDirCOMM + strProp);

        File win32 = new File(srcDirCOMM + strWin32);
        File comm = new File(srcDirCOMM + strComm);
        File com_prop = new File(srcDirCOMM + strProp);



        /*if (srcDir.isDirectory()) {
        if (!dstDir.exists()) {
        dstDir.mkdir();
        }
        String[] children = srcDir.list();
        for (int i=0; i<children.length; i++) {
        copyDirectory(new File(srcDir, children[i]),
        new File(dstDir, children[i]));
        }
        } else {
        copy(srcDir, dstDir);
        }*/


    }

    /**
     * Configuración de parametros para el puerto
     * @param baudRate
     * @param dataBits
     * @param stopBits
     * @param paridad
     * @return boolean
     */
    private boolean setParametros(int baudRate, int dataBits, int stopBits, int paridad) {
        try {
            sPuerto.setSerialPortParams(baudRate, dataBits, stopBits, paridad);
            return true;
        } catch (UnsupportedCommOperationException ex) {
            Logger.getLogger(CommMonitoreo.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Cerrar Apicación");
            System.exit(0);
        }
        return false;
    }

    /**
     * Permite hacer la lectura de los datos que se envien desde el puerto serie
     * @return InputStream
     */
    public InputStream recibirDatos() {
        try {
            return sPuerto.getInputStream();
        } catch (IOException ex) {
            Logger.getLogger(CommMonitoreo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Permite escuchar lo que llega por el puerto serial
     * @return String
     */
    public String leerDatos() {
        InputStream is = null;
        try {
            is = sPuerto.getInputStream();
            return "" + ((char) is.read());
        } catch (IOException ex) {
            Logger.getLogger(CommMonitoreo.class.getName()).log(Level.SEVERE, null, ex);
        }/*catch(IllegalStateException ex){
        System.err.println("Error -> Puerto Cerrado...");
        }*/
        return null;
    }

    public int leerDatosCode() {
        InputStream is = null;
        try {
            is = sPuerto.getInputStream();
            return is.read();
        } catch (IOException ex) {
            Logger.getLogger(CommMonitoreo.class.getName()).log(Level.SEVERE, null, ex);
        }/*catch(IllegalStateException ex){
        System.err.println("Error -> Puerto Cerrado...");
        }*/ catch (NullPointerException nex) {
            Logger.getLogger(CommMonitoreo.class.getName()).log(Level.SEVERE, null, nex);
        }
        return 0;
    }

    /**
     * Permite abrir un canal de comunicación para enviar datos por el puerto abierto
     */
    public void enviarDatos(String mensaje) {
        OutputStream os = null;
        try {
            os = sPuerto.getOutputStream();
            os.write(mensaje.getBytes());
        } catch (IOException ex) {
            Logger.getLogger(CommMonitoreo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Propietario que esta utilizando el puerto
     * @return String
     */
    public String getPropietario() {
        return id_Puerto.getCurrentOwner();
    }

    /**
     * Ontiene el estado de ring del telefono
     * @return boolean
     */
    public boolean getEstadoRing() {
        return sPuerto.isRI();
    }

    /**
     * Notificar el estado de ring true lo habilita
     * @param ri
     */
    public void notificarEstadoRing(boolean ri) {
        sPuerto.notifyOnRingIndicator(ri);
    }

    /**
     * Cerrar el puerto abierto
     */
    public boolean CerrarPuerto() {
        sPuerto.close();
        return true;
        //id_Puerto.removePortOwnershipListener(this);
    }
}