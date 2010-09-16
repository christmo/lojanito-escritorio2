/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.comunicacion.servidorBD;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class CronometroReconexion extends Thread {

    private boolean seguir = true;
    int i = 1;

    public CronometroReconexion() {

        seguir = true;
        i = 1;
    }

    @Override
    public void run() {
        System.out.println("Iniciar hilo: " + seguir);
        while (seguir) {

            //System.out.println("T:" + i);
            i++;
            if (i == 60) {
                System.err.println("Reiniciar la conexion con el servidor...");
                ConsultaRecorridosServidorBD.cerrarConexionServerKradac();
                ConsultaRecorridosServidorBD.AbrirPuerto();
                i = 1;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CronometroReconexion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    CronometroReconexion c;

    public void IniciarCrono() {
        this.start();
        seguir = true;
        i = 1;
        System.out.println("Iniciar");

    }

    public void reiniciar() {
        i = 1;
    }
}
