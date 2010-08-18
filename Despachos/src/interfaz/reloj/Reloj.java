/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.reloj;

import interfaz.Principal;
import java.util.Timer;
import java.util.TimerTask;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import javax.swing.JLabel;

public class Reloj {

    DateFormat df;
    SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyy-MM-dd");
    JLabel lblReloj;
    JLabel lblFecha;


    public Reloj(JLabel lblReloj,JLabel lblFecha) {
        this.lblReloj=lblReloj;
        this.lblFecha = lblFecha;
        Timer timer = new Timer();
        timer.schedule(new DoTick(), 0, 1000);	// do it every second
    }

    class DoTick extends TimerTask {

        public void run() {
            GregorianCalendar c = new GregorianCalendar();

            lblReloj.setText(sdfHora.format(c.getTime()));
            lblFecha.setText(sdfFecha.format(c.getTime()));

            //System.out.println(""+Principal.sesion[0] + " "+Principal.horaSalida);
            
            if(Principal.horaSalida.equals(sdfHora.format(c.getTime()))){
               Principal.ReiniciarTurno(Principal.sesion);
            }
        }
    }
}