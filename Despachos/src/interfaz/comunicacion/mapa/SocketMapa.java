/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.comunicacion.mapa;

import interfaz.subVentanas.VentanaDatos;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class SocketMapa extends Thread {

    static final int PUERTO = 65000;
    public InputStream is;
    public OutputStream os;
    boolean escuchar = true;
    private ServerSocket skServidor;

    public SocketMapa() {
        try {
            skServidor = new ServerSocket(PUERTO);
            activarHilo();
        } catch (IOException ex) {
            System.out.println("Mensaje: " + ex.getMessage());
            CerrarPuerto();
        }
    }

    /**
     * Activa el hilo para que cree un servidor que escuche todo por el
     * puerto determinado
     */
    private void activarHilo() {
        this.start();
    }

    /**
     * Cerrar el puerto en el que esta escuchando el servidor
     */
    public void CerrarPuerto() {
        System.out.println("Cerrar Puerto Coordenadas");
        escuchar = false;
        try {
            skServidor.close();
        } catch (IOException ex) {
            Logger.getLogger(SocketMapa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
        }
    }

    @Override
    public void run() {
        try {
            //try {
            System.out.println("Escucho el puerto " + PUERTO);
            Socket skCliente = null;


            try {
                skCliente = skServidor.accept();

                System.out.println("Aceptar");

                //PrintWriter out = new PrintWriter(skCliente.getOutputStream(), true);
                //PrintWriter out = new PrintWriter(skCliente.getOutputStream(), true);


                int i = -1;
                String datosMapa = "";


                int contarSeparador = 0;


                char caracter;


                while (escuchar) {

                    is = skCliente.getInputStream();

                    i = is.read();

                    //if (i != -1) {
                    caracter = (char) i;
                    //System.out.println("Code: " + i);


                    if (i != 10) {
                        if (caracter == '%') {
                            contarSeparador++;


                        }
                        if (contarSeparador == 2) {
                            System.out.println("Coordenadas: " + datosMapa);
                            procesarCoordenadas(datosMapa);
                            //escuchar = false;
                        } else {
                            datosMapa += "" + caracter;
                            escuchar = true;


                        }
                    } else {
                        datosMapa = "";
                        contarSeparador = 0;
                        escuchar = false;
                        CerrarPuerto();
                        new SocketMapa();
                    } //}

                }
            } catch (SocketException se) {
                System.err.println("No se puede aceptar conexiones socket cerrado...");
            }

        } catch (IOException ex) {
            Logger.getLogger(SocketMapa.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private void procesarCoordenadas(String datos) {
        String[] coord = datos.split("%");
        //System.out.println("Lat: " + coord[1]);
        //System.out.println("Lon: " + coord[0]);

        try {
            VentanaDatos.setCoordenadasMapa(coord[1], coord[0]);


        } catch (NullPointerException ex) {
            System.out.println("No hay ventana de datos...");


        }
    }

    /*public static void main(String[] arg) {
    new SocketMapa();
    }*/
}
