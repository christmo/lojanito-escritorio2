package interfaz.comunicacion.servidorBD;

import BaseDatos.ConexionBase;
import interfaz.Principal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.Icon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author christmo
 */
public class ConsultaRecorridosServidorBD extends Thread {

    /**
     * Logger para guardar los log en un archivo y enviar por mail los de error
     */
    private static final Logger log = LoggerFactory.getLogger(GuardarServidorKRADAC.class);
    private static int PUERTO;// = 666;
    private static String DIRECCION;
    private static Socket echoSocket;
    private String empresa;
    private static ConexionBase bd;
    private static BufferedReader entrada;
    private static PrintStream salida;
    private CronometroReconexion crono = new CronometroReconexion();
    private static Icon senal = new javax.swing.ImageIcon(ConsultaRecorridosServidorBD.class.getResource("/interfaz/iconos/senal.png"));
    private static Icon nosenal = new javax.swing.ImageIcon(ConsultaRecorridosServidorBD.class.getResource("/interfaz/iconos/nosenal.png"));
    /**
     * Guarda una bandera de que si se estan recupenado datos desde el servidor
     * de KRADAC, entonces tambien se puede insertar datos alli....
     */
    public static boolean HayInternet;

    public ConsultaRecorridosServidorBD(String empresa, ConexionBase cb) {
        this.empresa = empresa;
        bd = cb;

        ConsultaRecorridosServidorBD.DIRECCION = Principal.arcConfig.getProperty("ip_kradac");
        try {
            ConsultaRecorridosServidorBD.PUERTO = Integer.parseInt(Principal.arcConfig.getProperty("puerto_kradac"));
            crono.IniciarCrono();
        } catch (NumberFormatException ex) {
            System.err.println("Revisar el archivo de propiedades la ip y el puerto del servidor de KRADAC...");
        }
    }

    /**
     * Abre el puerto al servidor KRADAC para consulta de recorridos
     */
    public static void AbrirPuerto() {
        try {
            try {
                echoSocket = new Socket(DIRECCION, PUERTO);
                log.trace("Conectado con [" + DIRECCION + "] puerto [" + PUERTO + "]");
            } catch (UnknownHostException ex) {
                cerrarConexionServerKradac();
                AbrirPuerto();
                log.error("{}", Principal.sesion[1]);
            } catch (IOException ex) {
                PonerIconoNOSenal();
                if (ex.getMessage().equals("No route to host: connect")) {
                    System.err.println("Conexion rechasada por el servidor de KRADAC, No se pudo conectar...");
                    cerrarConexionServerKradac();
                    try {
                        Thread.sleep(1000);
                        AbrirPuerto();
                    } catch (InterruptedException ex1) {
                        log.error("{} error de interrupcion de hilo", Principal.sesion[1], ex1);
                    }

                }
            }
        } catch (StackOverflowError m) {
        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                GuardarDatosRecorridos();
                ConsultaRecorridosServidorBD.sleep(5000);
            } catch (InterruptedException ex) {
                System.err.println("" + ex.getMessage());
            }
        }
    }

    /**
     * Guarda las tramas que se recuperen de los ultimos recorridos en la base 
     * de datos
     */
    public void GuardarDatosRecorridos() {
        try {
            //String[] tramas = getDatosServidor(empresa);
            String[] tramas = getDatosServidorNuevo(empresa);
            for (String trama : tramas) {
                //GuardarDatosRecorridos(trama, bd);
                GuardarDatosRecorridosNuevo(trama, bd);
            }
            crono.reiniciar();
        } catch (NullPointerException ex) {
        }
    }

    /**
     * Guarda los datos de los recorridos
     * @param datoVehiculo
     * @param bd
     */
    private void GuardarDatosRecorridosNuevo(String datoVehiculo, ConexionBase bd) {
        String[] recorrido = datoVehiculo.split(",");

        PonerIconoSenal();
        /**
        ----
        ID PARTICION: 20100909
        N_UNIDAD: 43
        ID_EMPRESA: LN
        LAT: -3.99473
        LON: -79.2105116666667
        FECHA: 2010-09-09
        HORA: 09:44:36
        VEL: 0.14
        G1: estado del TAXIMETRO pudiendo ser 1 (ON) || 0 (OFF)
        G2: estado del TAXI pudiendo ser 1 (LIBRE) || 0 (OCUPADO)
        ----
         */
        try {
            //bd.InsertarRecorridoTaxi(recorrido[0], recorrido[1], recorrido[2], recorrido[3], recorrido[4], recorrido[5], recorrido[6], recorrido[7], recorrido[8], recorrido[9]);
            bd.InsertarRecorridoTaxiNuevo(recorrido[0], recorrido[1], recorrido[2], recorrido[3], recorrido[4], recorrido[5], recorrido[6], recorrido[7], recorrido[8], recorrido[9]);
        } catch (ArrayIndexOutOfBoundsException ex) {
        }
    }

    /**
     * Se conectar al servidor de Kradac a obtener las ultimas posiciones de todos
     * los vehiculos de la empresa para guardarlos en la tabla de recorridos local
     * para mostrar esos vehiculos en el mapa
     * @deprecated
     * @param empresa
     * @return String[]
     */
    private String[] getDatosServidor(String empresa) {
        ArrayList<String> nuevosDatos = new ArrayList<String>();
        String[] cast = null;
        String[] datos = null;
        try {

            entrada = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            salida = new PrintStream(echoSocket.getOutputStream(), true);

            salida.print(empresa + "\r\n");
            boolean salir = false;
            String dato;
            while (!salir) {
                if ((dato = entrada.readLine()) != null) {
                    String[] pos = dato.split("#");
                    for (int i = 0; i < pos.length; i++) {
                        nuevosDatos.add(pos[i]);
                        if (i == pos.length - 1) {
                            salir = true;
                        } else {
                            salir = false;
                        }
                    }
                }
            }

            cast = new String[nuevosDatos.size()];

            datos = nuevosDatos.toArray(cast);
            return datos;
        } catch (Exception e) {
            cerrarConexionServerKradac();
            AbrirPuerto();
        }
        return null;
    }

    private String[] getDatosServidorNuevo(String empresa) {
        ArrayList<String> nuevosDatos = new ArrayList<String>();
        String[] cast = null;
        String[] datos = null;
        try {

            entrada = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            salida = new PrintStream(echoSocket.getOutputStream(), true);

            salida.print("$$1##" + empresa + "$$\n");
            boolean salir = false;
            String dato;
            while (!salir) {
                if ((dato = entrada.readLine()) != null) {
                    String[] pos = dato.split("#");
                    for (int i = 0; i < pos.length; i++) {
                        nuevosDatos.add(pos[i]);
                        if (i == pos.length - 1) {
                            salir = true;
                        } else {
                            salir = false;
                        }
                    }
                }
            }

            cast = new String[nuevosDatos.size()];

            datos = nuevosDatos.toArray(cast);
            return datos;
        } catch (Exception e) {
            cerrarConexionServerKradac();
            AbrirPuerto();
        }
        return null;
    }

    /**
     * Cierra la conexion con el servidor de KRADAC
     */
    public static void cerrarConexionServerKradac() {
        PonerIconoNOSenal();
        try {
            try {
                salida.close();
                entrada.close();
            } catch (NullPointerException ex) {
            }
            try {
                echoSocket.close();
            } catch (NullPointerException ex) {
            }
        } catch (IOException ex) {
            log.error("{}", Principal.sesion[1]);
        }
    }
    static int contador = 0;

    /**
     * Pone el icono en la interfaz Principal de señal
     */
    private static void PonerIconoSenal() {
        Principal.lblSenal.setIcon(senal);
        HayInternet = true;
        try {
            if (Principal.arcConfig.getProperty("actualizar_respaldos").equals("si")
                    || Principal.arcConfig.getProperty("actualizar_respaldos").equals("SI")
                    && !Principal.arcConfig.getProperty("actualizar_respaldos").equals("")
                    && Principal.arcConfig.getProperty("actualizar_respaldos") != null) {

                int filasRespaldadas = bd.getNumeroFilasRespaldoAsignacion();

                if (contador == 0) {
                    ConexionBase base = new ConexionBase(Principal.arcConfig);
                    ActualizarServidorKRADAC actualizarServer = new ActualizarServidorKRADAC(filasRespaldadas, base);
                    actualizarServer.start();
                    contador++;
                }
            }
        } catch (NullPointerException nex) {
            log.trace("No se a especificado la directiva [actualizar_respaldos] en el archivo de configuración...");
        }
    }

    /**
     * Pone el icono en la interfaz Principal de Sin Señal no se estan guardando los
     * datos recuperados desde el servidor
     */
    private static void PonerIconoNOSenal() {
        Principal.lblSenal.setIcon(nosenal);
        HayInternet = false;
        contador = 0;
    }
}
