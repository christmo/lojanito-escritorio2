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

    /**
     * Recive todos los parametros para enviar un mensaje de texto al taximetro
     * @param empresa
     * @param unidad
     * @param mensaje -> nombre%barrio%direccion -> en este formato la trama
     * @param db
     */
    public EnvioMensajesUnidades(String empresa, String unidad, String mensaje, ConexionBase db) {
        this.empresa = empresa;
        this.unidad = unidad;
        this.mensaje = funciones.procesarMensaje(mensaje);
        this.bd = db;
    }

    @Override
    public void run() {
        DIRECCION = bd.getValorConfiguiracion("ip_kradac");
        try {
            PUERTO = Integer.parseInt(bd.getValorConfiguiracion("puerto_kradac"));
            AbrirPuerto();
        } catch (NumberFormatException ex) {
            System.err.println("Revisar el archivo de propiedades la ip y el puerto del servidor de KRADAC...");
        }
        enviarMensajeUnidad();
    }

    /**
     * Permite enviar el mensaje que le llegara al taximetro del chofer,
     * esto se debe enviar en este formato para que el servidor lo procese
     * y lo envie
     */
    private void enviarMensajeUnidad() {
        try {
            entrada = new BufferedReader(new InputStreamReader(mensajeSocket.getInputStream()));
            salida = new PrintStream(mensajeSocket.getOutputStream(), true);

            String cmdMensaje = "$$2##" + empresa + "##" + unidad + "##" + mensaje + "$$\n";
            salida.print(cmdMensaje);
            /**
             * TODO: guardar los mensajes en una tabla para hacer auditoria
             */
            log.trace("Mensaje: {} Unidad: {}", mensaje, unidad);

            String respuesta;
            if ((respuesta = entrada.readLine()) != null) {
                log.trace("Viene de KRADAC: {}", respuesta);
            } else {
                log.trace("NO hay respuesta del servidor -> esta abajo el server KRADAC");
            }

            cerrarConexionServerKradac();

        } catch (Exception e) {
            log.trace("Error al enviar un mensaje {}", e.getMessage());
        }
    }

    /**
     * Abre el puerto de comunicación entre el servidor y las centrales
     */
    private void AbrirPuerto() {
        try {
            try {
                mensajeSocket = new Socket(DIRECCION, PUERTO);
                log.trace("Iniciar conexion con el server [{}] para enviar mensajes...", DIRECCION);
            } catch (UnknownHostException ex) {
                cerrarConexionServerKradac();
            } catch (IOException ex) {
                if (ex.getMessage().equals("No route to host: connect")) {
                    cerrarConexionServerKradac();
                }
            }
        } catch (StackOverflowError m) {
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
                log.trace("Cerrar conexion con el server para enviar mensajes...");
            } catch (NullPointerException ex) {
            }
        } catch (IOException ex) {
        }
    }
}
