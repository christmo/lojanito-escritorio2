package interfaz.comunicacion.servidorBD;

import BaseDatos.ConexionBase;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author kradac
 */
public class ConsultaRecorridosServidorBD extends Thread {

    private static final int PUERTO = 666;
    private static final String DIRECCION = "200.0.29.117";
    private Socket echoSocket;
    private String empresa;
    private ConexionBase bd;

    public ConsultaRecorridosServidorBD(String empresa, ConexionBase bd) {
        try {
            System.err.println("Iniciar conexion con el server BD...");
            echoSocket = new Socket(DIRECCION, PUERTO);
            this.empresa = empresa;
            this.bd = bd;
        } catch (UnknownHostException ex) {
            Logger.getLogger(ConsultaRecorridosServidorBD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Logger.getLogger(ConsultaRecorridosServidorBD.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getMessage().equals("No route to host: connect")) {
                System.err.println("Conexion rechasada por el servidor de BD, No se pudo conectar...");
            }
        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                try {
                    echoSocket = new Socket(DIRECCION, PUERTO);
                    GuardarDatosRecorridos();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(ConsultaRecorridosServidorBD.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    //Logger.getLogger(ConsultaRecorridosServidorBD.class.getName()).log(Level.SEVERE, null, ex);
                    if (ex.getMessage().equals("No route to host: connect")) {
                        System.err.println("No hay conexion, puede ser porque no hay internet, el cable de red esta desconectado o el servidor de BD esta offline...");
                    }
                }
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
        } catch (NullPointerException ex) {
        }
    }

    /**
     * Guarda los datos de los recorridos
     * @param datoVehiculo
     * @param bd
     */
    private void GuardarDatosRecorridos(String datoVehiculo, ConexionBase bd) {
        String[] recorrido = datoVehiculo.split(",");
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

            BufferedReader entrada = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
            PrintStream salida = new PrintStream(echoSocket.getOutputStream(), true);

            System.out.println("Empresa:"+empresa);

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
            salida.close();
            entrada.close();
            echoSocket.close();

            cast = new String[nuevosDatos.size()];

            datos = nuevosDatos.toArray(cast);
            System.out.println("Datos Recuperados: " + datos.length);
            return datos;
        } catch (Exception e) {
            System.err.println("Exception:  " + e);
        }
        return null;
    }
}
