package interfaz.comunicacion.servidorBD;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sistemaoperativo.ComandosSistemaOperativo;

/**
 * @author christmo
 */
public class ActualizadorDespachosServidorKRADAC extends Thread {

    /**
     * Logger para guardar los log en un archivo y enviar por mail los de error
     */
    private static final Logger log = LoggerFactory.getLogger(ActualizadorDespachosServidorKRADAC.class);
    private String empresa;
    private String comando;

    public ActualizadorDespachosServidorKRADAC(String empresa) {
        this.empresa = empresa;
    }

    @Override
    public void run() {
        while (true) {
            try {
                comando = "java -jar ActualizarServidor.jar " + empresa;
                ComandosSistemaOperativo cmd = new ComandosSistemaOperativo(comando);
                ActualizadorDespachosServidorKRADAC.sleep(3600000);//60 minutos
            } catch (InterruptedException ex) {
                log.info("{}", ex.getMessage(), ex);
            }
        }
    }
}
