/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.reloj;

import interfaz.Principal;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JLabel;

public class Reloj {

    DateFormat df;
    SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
    SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyy-MM-dd");
    JLabel lblReloj;
    JLabel lblFecha;

    public Reloj(JLabel lblReloj, JLabel lblFecha) {
        this.lblReloj = lblReloj;
        this.lblFecha = lblFecha;
        Cronometro c = new Cronometro();
        Timer timer = new Timer();
        timer.schedule(c, 0, 1000);	// do it every second
    }

    class Cronometro extends TimerTask {

        boolean parpadeo=false;

        public void run() {
            GregorianCalendar c = new GregorianCalendar();

            lblReloj.setText(sdfHora.format(c.getTime()));
            lblFecha.setText(sdfFecha.format(c.getTime()));

            long menos5minutos = (convertirHora(Principal.horaNuevoTurno)).getTime() - 30000;

            if(sdfHora.format(c.getTime()).equals(sdfHora.format(menos5minutos))){
                parpadeo=true;
            }

            if (parpadeo) {
                if (getUltimosSegundos(c.getTime())) {
                    lblReloj.setForeground(Color.RED);
                } else {
                    lblReloj.setForeground(Color.BLACK);
                }
            }else{
                lblReloj.setForeground(Color.BLACK);
            }

            if (Principal.horaNuevoTurno.equals(sdfHora.format(c.getTime()))) {
                parpadeo = false;
                if (Principal.gui != null) {
                    Principal.ReiniciarTurno();
                }
            }
        }

        /**
         * Obtiene verdadero si los segundos son pares y falso si no lo son
         * para hacer parpadear los colores del reloj
         * @param hora
         * @return boolean
         */
        private boolean getUltimosSegundos(Date hora) {
            SimpleDateFormat sdfHora = new SimpleDateFormat("ss");
            int seg = Integer.parseInt(sdfHora.format(hora));
            if ((seg % 2) == 0) {
                return true;
            } else {
                return false;
            }
        }

        private Date convertirHora(String hora) {
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            try {
                Date today = df.parse(hora);
                //System.out.println("Today = " + df.format(today));
                return today;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
