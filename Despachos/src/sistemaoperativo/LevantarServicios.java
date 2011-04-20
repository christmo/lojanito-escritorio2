/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaoperativo;

import java.util.Properties;

/**
 *
 * @author kradac
 */
public class LevantarServicios {

    /**
     * Levanta los servicios de mysql y apache
     * @param arcConfig
     */
    public static void LevantarWAMP(Properties arcConfig) {
        System.err.println("Levantando WAMP");
        if (System.getProperty("os.name").equals("Linux")) {
        } else {//Windows
            ComandosSistemaOperativo OSMYSQL = new ComandosSistemaOperativo("net start wampmysqld");
            ComandosSistemaOperativo OSAPACHE = new ComandosSistemaOperativo("net start wampapache");
        }
    }

    /**
     * Levantar TeamView para asistencia remota
     * @param arcConfig
     */
    public static void LevantarTeamViewer(Properties arcConfig) {
        System.err.println("Levantando TeamViewer");
        if (System.getProperty("os.name").equals("Linux")) {
        } else {//Windows
            if (arcConfig.getProperty("tv") != null
                    && !arcConfig.getProperty("tv").equals("")
                    && !arcConfig.getProperty("tv").equals("0")) {
                ComandosSistemaOperativo TV = new ComandosSistemaOperativo("net start TeamViewer" + arcConfig.getProperty("tv"));
            } else {
                ComandosSistemaOperativo TV = new ComandosSistemaOperativo("net start TeamViewer" + 6);
            }
        }
    }
}
