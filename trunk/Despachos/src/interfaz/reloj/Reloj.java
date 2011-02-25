/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.reloj;

import BaseDatos.ConexionBase;
import interfaz.Principal;
import interfaz.funcionesUtilidad;
import interfaz.subVentanas.Pendientes;
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
    private ConexionBase bd;
    private funcionesUtilidad funciones = new funcionesUtilidad();

    public Reloj(JLabel lblReloj, JLabel lblFecha, ConexionBase cb) {
        this.lblReloj = lblReloj;
        this.lblFecha = lblFecha;
        this.bd = cb;
        Cronometro c = new Cronometro();
        Timer timer = new Timer();
        timer.schedule(c, 0, 1000);	// do it every second
        Principal.listaPendientesFecha = bd.obtenerPendientesGuardadosPorFecha(funciones.getFecha());
    }

    class Cronometro extends TimerTask {

        boolean parpadeo = false;

        public void run() {
            GregorianCalendar c = new GregorianCalendar();

            lblReloj.setText(sdfHora.format(c.getTime()));
            lblFecha.setText(sdfFecha.format(c.getTime()));

            long menos5minutos = (convertirHora(Principal.horaNuevoTurno)).getTime() - 300000;
            long horaActual = convertirHora(sdfHora.format(c.getTime())).getTime();
            long horaSalida = convertirHora(Principal.horaNuevoTurno).getTime();

            if (sdfHora.format(c.getTime()).equals(sdfHora.format(menos5minutos))
                    || horaActual >= menos5minutos && horaActual <= horaSalida) {
                parpadeo = true;
            }

            if (parpadeo) {
                if (getUltimosSegundos(c.getTime())) {
                    lblReloj.setForeground(Color.RED);
                } else {
                    lblReloj.setForeground(Color.BLACK);
                }
            } else {
                lblReloj.setForeground(Color.BLACK);
            }

            if (Principal.horaNuevoTurno.equals(sdfHora.format(c.getTime()))) {
                parpadeo = false;
                if (Principal.gui != null) {
                    Principal.ReiniciarTurno();
                }
            }

            /**
             * Pendientes
             */
            comprobarHoraPendientes(horaActual);

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

        /**
         * Permite convertir una hora en string a date, ojo las
         * horas que se envien por aqui tendran el año 1970, este
         * metodo permite el trabajo con horas mas no con fechas
         * @param hora
         * @return Date
         */
        private Date convertirHora(String hora) {
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            try {
                Date today = df.parse(hora);
                return today;
            } catch (ParseException e) {
            }
            return null;
        }

        private void comprobarHoraPendientes(long hora) {
            long horaPendMenosRecuerdo;
            long horaLanzamiento;
            Pendientes p;
            //System.out.println("->" + Principal.listaPendientesFecha.size());

            if (Principal.listaPendientesFecha.size() > 0) {
                int i = 0;
                while (i < Principal.listaPendientesFecha.size()) {
                    p = Principal.listaPendientesFecha.get(i);
                    horaPendMenosRecuerdo = convertirHora(p.getHora()).getTime() - (p.getMinRecuerdo() * 60 * 1000);
                    //System.out.println("Rocordatorio Pendiente: " + horaPendMenosRecuerdo + "==" + hora);
                    if (horaPendMenosRecuerdo == hora) {
                        System.out.println("Lanzar Recordatorio...");
                        Principal.lanzarMensajePendiente(Principal.listaPendientesFecha.get(i));
                    }
                    horaLanzamiento = convertirHora(p.getHora()).getTime();
                    //System.out.println("Pendiente: " + horaLanzamiento + "==" + hora);
                    if (horaLanzamiento == hora) {
                        System.out.println("Lanzar Pendiente...");
                        Principal.lanzarPendiente(Principal.listaPendientesFecha.get(i));
                        Principal.listaPendientesFecha.remove(i);
                        i--;
                    }
                    i++;
                }
            }

        }
    }
}
