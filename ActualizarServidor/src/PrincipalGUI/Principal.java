/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PrincipalGUI;

import BaseDatos.BaseDatos;
import Comunicacion.ActualizarServidorKRADAC;
import Utilitarios.Utilitarios;

import java.io.FileNotFoundException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kradac
 */
public class Principal {

     /**
     * Logger para guardar los log en un archivo y enviar por mail los de error
     */
    private static final Logger log = LoggerFactory.getLogger(Principal.class);

    public static void main(String[] args) {
        try {
            Utilitarios ut = new Utilitarios();
            Properties p = ut.obtenerArchivoPropiedades("configuracion.properties");
            BaseDatos bd = new BaseDatos(p);

            int filasRespaldadas = bd.getNumeroFilasRespaldoAsignacion();

            log.trace("Respaldar:["+filasRespaldadas+"]");

            ActualizarServidorKRADAC actualizar = new ActualizarServidorKRADAC(filasRespaldadas, bd);
            actualizar.start();


        } catch (FileNotFoundException ex) {
            log.trace(ex.getMessage(),ex);
        }
    }
}
