/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.comunicacion;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import javax.swing.JTextField;

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

    public CommMonitoreo() {
    }

    /**
     * Abrir el puerto con configuraciones por defecto
     * @param puerto
     */
    public CommMonitoreo(String puerto) {
        if (!AbrirPuerto(puerto)) {
            System.err.println("No se pudo abrir el puerto");
        }
        if (!setParametros(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE)) {
            System.err.println("No se pudo setear puerto");
            CerrarPuerto();
        }
    }

    /**
     * Muestra la lista de puertos seriales y paralelos del equipo
     */
    public void ListaPuertos() {
        listaPuertos = CommPortIdentifier.getPortIdentifiers();

        while (listaPuertos.hasMoreElements()) {
            id_Puerto = (CommPortIdentifier) listaPuertos.nextElement();
            System.out.println("Id: " + id_Puerto.getName() + " tipo: " + id_Puerto.getPortType());
        }
    }

    @Override
    public void run() {
        int cod = 0;
        String tel = "";

        boolean fecha = false;
        boolean hora = false;
        boolean numero = false;

        String strFecha = "";
        String strHora = "";
        String strNumero = "";

        while (true) {
            //System.out.println("" + i++);

            cod = leerDatosCode();

            //System.out.println("cod: "+cod);

            if (cod == 13) {
                //System.out.println("Fecha: " + strFecha);
                if (strFecha.length() == 4) {
                    enviarDatos(strFecha);
                }
                //System.out.println("Hora: " + strHora);
                if (strHora.length() == 4) {
                    enviarDatos(strHora);
                }
                //System.out.println("Numero: " + strNumero);
                if (strNumero.length() == 8) {
                    enviarDatos(strNumero);
                    timbrar(true, strNumero);
                }
                //puerto.enviarDatos("\n");
            }

            if (cod == 10) {
                tel = "";
                fecha = false;
                hora = false;
                numero = false;
                timbrar(false,"");
            } else {
                tel += "" + (char) cod;
                System.out.println(tel);
            }

            if (tel.equals("RING")) {
                System.out.println("timbre");
                timbrar(true,strNumero);
                strNumero="";
                tel = "";
            } else if (tel.equals("DATE=")) {
                tel = "";
                fecha = true;
                hora = false;
                numero = false;
            } else if (tel.equals("TIME=")) {
                tel = "";
                hora = true;
                fecha = false;
                numero = false;
            } else if (tel.equals("NMBR=")) {
                tel = "";
                numero = true;
                hora = false;
                fecha = false;
            }

            if (fecha) {
                strFecha = tel;
                System.out.println("Fecha: " + strFecha);
            } else if (hora) {
                strHora = tel;
                System.out.println("Hora: " + strHora);
            } else if (numero) {
                strNumero = tel;
                System.out.println("Numero: " + strNumero);
                timbrar(true, strNumero);
            }

        }
    }

    /**
     * Setea el objeto de la UI que va a representar al indicador de la llamada
     * este tiene que ser un JLabel
     * @param indicador
     */
    public void setIndicadorLlamada(JTextField jtTelefono,JLabel indicador) {
        this.jtTelefono = jtTelefono;
        this.indicador = indicador;
    }

    /**
     * Recibe true para activar el icono de llama entrante o sin llamada
     * @param timbre boolean
     */
    public void timbrar(boolean timbre,String strNumero) {
        if (timbre) {
            Icon img = new javax.swing.ImageIcon(getClass().getResource("/puertocomm/icons/t1.png"));
            this.indicador.setIcon(img);
            jtTelefono.setText(strNumero);
        } else {
            Icon img = new javax.swing.ImageIcon(getClass().getResource("/puertocomm/icons/1.png"));
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
            sPuerto = (SerialPort) id_Puerto.open("MonitoreoKradac", 60000); //tiempo de bloqueo 1m
            return true;
        } catch (PortInUseException ex) {
            Logger.getLogger(CommMonitoreo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPortException ex) {
            Logger.getLogger(CommMonitoreo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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
        }
        return false;
    }

    /**
     * Permite hacer la lectura de los datos que se envien desde el puerto serie
     * @return InputStream
     */
    /*public InputStream recibirDatos() {
    try {
    return sPuerto.getInputStream();
    } catch (IOException ex) {
    Logger.getLogger(CommMonitoreo.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
    }*/
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
    private boolean CerrarPuerto() {
        sPuerto.close();
        return true;
        //id_Puerto.removePortOwnershipListener(this);
    }
}
