/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.comunicacion.mapa;

import interfaz.Principal;
import interfaz.subVentanas.VentanaDatos;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class ObtenerCoordenadasMapa extends Thread {

//    private static int puerto = 65000;
    private static int puerto;
    boolean escuchar = true;
    ServerSocket ss;
    Socket s;

    /*public static void main(String[] args) {
    new ObtenerCoordenadasMapa();
    }*/
    public ObtenerCoordenadasMapa() {
        ObtenerCoordenadasMapa.puerto = Integer.parseInt(Principal.arcConfig.getProperty("puerto_mapa"));
        this.start();
    }

    @Override
    public void run() {
        try {
            ss = new ServerSocket(puerto);
            System.out.println("Escuchar el puerto: " + puerto);
            InputStream is;
            s = ss.accept();
            char caracter;

            int contarSeparador = 0;
            String datosMapa = "";
            int cod;

            while (escuchar) {
                try {
                    is = s.getInputStream();

                    cod = is.read();
                    //System.out.println("Cod: " + cod);
                    if (cod == 10) {
                        s.close();
                        s = ss.accept();
                        datosMapa = "";
                        contarSeparador = 0;
                    } else {
                        caracter = (char) cod;

                        if (caracter == '%') {
                            contarSeparador++;
                        }
                        if (contarSeparador == 2) {
                            System.out.println("Coordenadas: " + datosMapa);
                            procesarCoordenadas(datosMapa);
                        } else {
                            datosMapa += "" + caracter;
                            //System.out.println("" + datosMapa);
                        }

                    }
                } catch (IOException ex) {
                    // skip this connection; continue with the next
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ObtenerCoordenadasMapa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void PararDeEscuchar() {
        try {
            System.out.println("Cerrar conexion al parar el hilo...");
            s.close();
            ss.close();
            this.escuchar = false;
        } catch (IOException ex) {
            Logger.getLogger(ObtenerCoordenadasMapa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void procesarCoordenadas(String datos) {
        String[] coord = datos.split("%");
        System.out.println("Lat: " + coord[1]);
        System.out.println("Lon: " + coord[0]);

        try {
            VentanaDatos.setCoordenadasMapa(coord[1], coord[0]);
        } catch (NullPointerException ex) {
            System.out.println("No hay ventana de datos...");
        }
    }
}
