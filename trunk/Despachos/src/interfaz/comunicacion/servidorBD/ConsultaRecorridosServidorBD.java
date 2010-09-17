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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;

/**
 * @author kradac
 */
public class ConsultaRecorridosServidorBD extends Thread {

    private static int PUERTO;// = 666;
    private static String DIRECCION;
    private static Socket echoSocket;
    private String empresa;
    private ConexionBase bd;
    private static BufferedReader entrada;
    private static PrintStream salida;
    CronometroReconexion crono = new CronometroReconexion();
    private static Icon senal = new javax.swing.ImageIcon(ConsultaRecorridosServidorBD.class.getResource("/interfaz/iconos/senal.png"));
    private static Icon nosenal = new javax.swing.ImageIcon(ConsultaRecorridosServidorBD.class.getResource("/interfaz/iconos/nosenal.png"));
    /**
     * Guarda una bandera de que si se estan recupenado datos desde el servidor
     * de KRADAC, entonces tambien se puede insertar datos alli....
     */
    public static boolean HayInternet;

    public ConsultaRecorridosServidorBD(String empresa, ConexionBase bd) {
        this.empresa = empresa;
        this.bd = bd;

        ConsultaRecorridosServidorBD.DIRECCION = Principal.arcConfig.getProperty("ip_kradac");
        try {
            ConsultaRecorridosServidorBD.PUERTO = Integer.parseInt(Principal.arcConfig.getProperty("puerto_kradac"));
            crono.IniciarCrono();
        } catch (NumberFormatException ex) {
            System.err.println("Revisar el archivo de propiedades la ip y el puerto del servidor de KRADAC...");
        }
    }

    public static void AbrirPuerto() {
        try {
            try {
                echoSocket = new Socket(DIRECCION, PUERTO);
                System.err.println("Iniciar conexion con el server BD...");
            } catch (UnknownHostException ex) {
                //Principal.lblSenal.setIcon(senal);
                cerrarConexionServerKradac();
                AbrirPuerto();
                Logger.getLogger(ConsultaRecorridosServidorBD.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                //Logger.getLogger(ConsultaRecorridosServidorBD.class.getName()).log(Level.SEVERE, null, ex);
                PonerIconoNOSenal();
                if (ex.getMessage().equals("No route to host: connect")) {
                    System.err.println("Conexion rechasada por el servidor de BD, No se pudo conectar...");
                    cerrarConexionServerKradac();
                    try {
                        Thread.sleep(1000);
                        AbrirPuerto();
                    } catch (InterruptedException ex1) {
                        Logger.getLogger(ConsultaRecorridosServidorBD.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        } catch (StackOverflowError m) {
            System.out.println("Memoria Chao:" + m.getMessage());
        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                GuardarDatosRecorridos();
                ConsultaRecorridosServidorBD.sleep(5000);
            } catch (InterruptedException ex) {
                //Logger.getLogger(ConsultaRecorridosServidorBD.class.getName()).log(Level.SEVERE, null, ex);
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
            String[] tramas = getDatosServidor(empresa);
            for (String trama : tramas) {
                //System.out.println("" + trama);
                GuardarDatosRecorridos(trama, bd);
            }
            crono.reiniciar();
        } catch (NullPointerException ex) {
            System.err.println("NO se recuperaron datos -> No se guardo nada...");
        }
    }

    /**
     * Guarda los datos de los recorridos
     * @param datoVehiculo
     * @param bd
     */
    private void GuardarDatosRecorridos(String datoVehiculo, ConexionBase bd) {
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
        ESTADO_TAXI: W
        VEL: 0.14
        ESTADO_TAXIMETRO: A
        ----
         */
        try {
            bd.InsertarRecorridoTaxi(recorrido[0], recorrido[1], recorrido[2], recorrido[3], recorrido[4], recorrido[5], recorrido[6], recorrido[7], recorrido[8], recorrido[9]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("No se recuperaron datos para esa compañia...");
        }
    }

    /**
     * Se conectar al servidor de Kradac a obtener las ultimas posiciones de todos
     * los vehiculos de la empresa para guardarlos en la tabla de recorridos local
     * para mostrar esos vehiculos en el mapa
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

            System.out.println("Empresa:" + empresa);

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
            System.out.println("Datos Recuperados: " + datos.length);
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
                System.out.println("Socket cerrado con servidor KRADAC...");
            } catch (NullPointerException ex) {
            }
        } catch (IOException ex) {
            Logger.getLogger(ConsultaRecorridosServidorBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Pone el icono en la interfaz Principal de señal
     */
    private static void PonerIconoSenal() {
        Principal.lblSenal.setIcon(senal);
        HayInternet = true;
    }

    /**
     * Pone el icono en la interfaz Principal de Sin Señal no se estan guardando los
     * datos recuperados desde el servidor
     */
    private static void PonerIconoNOSenal() {
        Principal.lblSenal.setIcon(nosenal);
        HayInternet = false;
    }
}
