/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.comunicacion.servidorBD;

import BaseDatos.ConexionBase;
import interfaz.Principal;
import interfaz.funcionesUtilidad;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kradac
 */
public class EnvioMensajesUnidades extends Thread {

    private static final Logger log = LoggerFactory.getLogger(EnvioMensajesUnidades.class);
    private int PUERTO;// = 666;
    private String DIRECCION;
    private String empresa;
    private String mensaje;
    private String unidad;
    private ConexionBase bd;
    private Socket mensajeSocket;
    private BufferedReader entrada;
    private PrintStream salida;
    private funcionesUtilidad funciones = new funcionesUtilidad();

    public EnvioMensajesUnidades(String empresa, String unidad, String mensaje, ConexionBase db) {
        this.empresa = empresa;
        this.unidad = unidad;
        this.mensaje = funciones.procesarMensaje(mensaje);
        this.bd = db;

        DIRECCION = Principal.arcConfig.getProperty("ip_kradac");
        try {
            PUERTO = Integer.parseInt(Principal.arcConfig.getProperty("puerto_kradac"));
            AbrirPuerto();
        } catch (NumberFormatException ex) {
            System.err.println("Revisar el archivo de propiedades la ip y el puerto del servidor de KRADAC...");
        }
    }

    @Override
    public void run() {
        System.out.println("Iniciar el hilo del mensaje....");
        enviarMensajeUnidad();
        System.out.println("Fin hilo mensaje");
    }

    private void enviarMensajeUnidad() {
        try {
            entrada = new BufferedReader(new InputStreamReader(mensajeSocket.getInputStream()));
            salida = new PrintStream(mensajeSocket.getOutputStream(), true);

            String cmdMensaje = "$$2##" + empresa + "##" + unidad + "##" + mensaje + "$$\n";
            salida.print(cmdMensaje);

            String respuesta;
            if ((respuesta = entrada.readLine()) != null) {
                System.out.println("Mensaje Enviado: " + respuesta);
            }

        } catch (Exception e) {
            System.out.println("Error al enviar el mensaje");
            log.trace("Error al enviar un mensaje\n {}", e);
        }
    }

    /**
     * Abre el puerto de comunicación entre el servidor y las centrales
     */
    private void AbrirPuerto() {
        try {
            try {
                mensajeSocket = new Socket(DIRECCION, PUERTO);
                System.err.println("Iniciar conexion con el server BD...");
            } catch (UnknownHostException ex) {
                cerrarConexionServerKradac();
                AbrirPuerto();
                log.error("{}", Principal.sesion[1]);
            } catch (IOException ex) {
                if (ex.getMessage().equals("No route to host: connect")) {
                    System.err.println("Conexion rechasada por el servidor de KRADAC, No se pudo conectar...");
                    cerrarConexionServerKradac();
                    try {
                        Thread.sleep(1000);
                        AbrirPuerto();
                    } catch (InterruptedException ex1) {
                        log.error("{}", Principal.sesion[1]);
                    }
                }
            }
        } catch (StackOverflowError m) {
            System.out.println("Memoria Chao:" + m.getMessage());
        }
    }

    /**
     * Cierra la conexion con el servidor de KRADAC
     */
    private void cerrarConexionServerKradac() {
        try {
            try {
                salida.close();
                entrada.close();
            } catch (NullPointerException ex) {
            }
            try {
                mensajeSocket.close();
                System.out.println("Socket cerrado con servidor KRADAC...");
            } catch (NullPointerException ex) {
            }
        } catch (IOException ex) {
            log.error("{}", Principal.sesion[1]);
        }
    }
}
