package interfaz.comunicacion.servidorBD;

import BaseDatos.ConexionBase;
import interfaz.Principal;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author christmo
 */
public class ConsultaUnidadesBloqueadas extends Thread {

    /**
     * Logger para guardar los log en un archivo y enviar por mail los de error
     */
    private static final Logger log = LoggerFactory.getLogger(ConsultaUnidadesBloqueadas.class);
    private static int PUERTO;// = 666;
    private static String DIRECCION;
    private static Socket echoSocket;
    private String empresa;
    private static ConexionBase bd;
    private static BufferedReader entrada;
    private static PrintStream salida;

    public ConsultaUnidadesBloqueadas(String empresa, ConexionBase cb) {
        this.empresa = empresa;
        bd = cb;
        consultrarDireccionServer();
    }

    private static void consultrarDireccionServer() {
        //ConsultaRecorridosServidorBD.DIRECCION = Principal.arcConfig.getProperty("ip_kradac");
        ConsultaUnidadesBloqueadas.DIRECCION = bd.getValorConfiguiracion("ip_kradac");
        try {
            //ConsultaRecorridosServidorBD.PUERTO = Integer.parseInt(Principal.arcConfig.getProperty("puerto_kradac"));
            ConsultaUnidadesBloqueadas.PUERTO = Integer.parseInt(bd.getValorConfiguiracion("puerto_kradac"));
        } catch (NumberFormatException ex) {
            System.err.println("Revisar el archivo de propiedades la ip y el puerto del servidor de KRADAC...");
        }

    }

    /**
     * Abre el puerto al servidor KRADAC para consulta de recorridos
     */
    private static void AbrirPuerto() {
        try {
            try {
                consultrarDireccionServer();
                echoSocket = new Socket(DIRECCION, PUERTO);
                log.info("Conectado con [" + DIRECCION + "] puerto [" + PUERTO + "]");
            } catch (UnknownHostException ex) {
                cerrarConexionServerKradac();
                AbrirPuerto();
                log.error("{}", Principal.sesion[1], ex);
            } catch (IOException ex) {
                if (ex.getMessage().equals("No route to host: connect")) {
                    log.info("Conexion rechasada por el servidor de KRADAC, No se pudo conectar...");
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
                GuardarDatosUnidadesBloqueadas();
                ConsultaUnidadesBloqueadas.sleep(30000);//30 segundos
            } catch (InterruptedException ex) {
                log.info("{}", ex.getMessage(), ex);
            }
        }
    }

    /**
     * Guarda las tramas que se recuperen de la unidades bloqueadas por falta de
     * pago
     */
    private void GuardarDatosUnidadesBloqueadas() {
        try {
            String unidades = getDatosUnidadesBloqueadas(empresa);
            if (unidades != null) {
                bd.guardarBloqueoUnidad(unidades.trim());
            }
        } catch (NullPointerException ex) {
        }
    }

    /**
     * Obtiene el numero de las unidades que estan marcadas en el servidor como
     * deudores y a los cuales se les tiene que cortar el rastreo
     * @param empresa
     * @return String[]
     */
    private String getDatosUnidadesBloqueadas(String empresa) {
        try {
            entrada = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            salida = new PrintStream(echoSocket.getOutputStream(), true);

            salida.print("$$3##" + empresa + "$$\n");
            String dato;
            if ((dato = entrada.readLine()) != null) {
                if (dato.substring(dato.length() - 1).equals(",")) {
                    dato = dato.substring(0, dato.length() - 1);
                    return dato;
                }
            }
        } catch (Exception e) {
            cerrarConexionServerKradac();
            AbrirPuerto();
        }
        return null;
    }

    /**
     * Cierra la conexion con el servidor de KRADAC
     */
    private static void cerrarConexionServerKradac() {
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
            log.error("{}", Principal.sesion[1], ex);
        }
    }

}
