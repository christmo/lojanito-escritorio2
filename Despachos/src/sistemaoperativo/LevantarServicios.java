/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaoperativo;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author christmo
 */
public class LevantarServicios {

    private static final Logger log = (Logger) LoggerFactory.getLogger(LevantarServicios.class);

    /**
     * Levanta los servicios de mysql y apache
     * @param arcConfig
     */
    public static void LevantarWAMP() {
        log.trace("Levantando WAMP");
        if (System.getProperty("os.name").equals("Linux")) {
            /**
             * TODO: Levantar los servicios en Linux
             */
        } else {//Windows
            ComandosSistemaOperativo OSMYSQL = new ComandosSistemaOperativo("net start wampmysqld");
            ComandosSistemaOperativo OSAPACHE = new ComandosSistemaOperativo("net start wampapache");
        }
    }

    /**
     * Levantar TeamView para asistencia remota
     * @param versionTV
     */
    public static void LevantarTeamViewer(String versionTV) {
        log.trace("Levantando TeamViewer");
        if (System.getProperty("os.name").equals("Linux")) {
            /**
             * TODO: Levantar los servicios en Linux
             */
        } else {//Windows
            //String versionTV = bd.obtenerValorConfiguiracion("tv");
            if (versionTV != null && !versionTV.equals("") && !versionTV.equals("0")) {
                ComandosSistemaOperativo TV = new ComandosSistemaOperativo("net start TeamViewer" + versionTV);
            } else {
                ComandosSistemaOperativo TV = new ComandosSistemaOperativo("net start TeamViewer" + 6);
            }
        }
    }
}
