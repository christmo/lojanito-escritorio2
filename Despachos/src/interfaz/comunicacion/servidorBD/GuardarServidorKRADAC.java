package interfaz.comunicacion.servidorBD;

import BaseDatos.ConexionBase;
import interfaz.Principal;
import interfaz.subVentanas.Despachos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author christmo
 */
public class GuardarServidorKRADAC extends Thread {

    /**
     * Logger para guardar los log en un archivo y enviar por mail los de error
     */
    private static final Logger log = LoggerFactory.getLogger(GuardarServidorKRADAC.class);
    private ConexionBase bd;
    private Despachos desp;
    private boolean accion;

    /**
     * Guarda por separado los datos que van al servidor
     * @param bd
     * @param d
     * @param accion || true ASIGNACION || false LIBRE
     */
    public GuardarServidorKRADAC(Despachos d, boolean accion, ConexionBase cb) {
        this.bd = cb;
        this.desp = d;
        this.accion = accion;//true ASIGNACION || false LIBRE
        log.debug("Enpezar a Guardar al servidor Kradac...");
    }

    @Override
    public void run() {
        //true ASIGNACION || false LIBRE
        if (accion) {
            InsertarAsignacionServidorKRADAC();
        } else {
            InsertarLibreServidorKRADAC();
        }
    }

    /**
     * Inserta la asignasion en el servidor kradac
     */
    public void InsertarAsignacionServidorKRADAC() {
        String sql;
        if (desp.getStrTelefono() == null
                || desp.getStrTelefono().equals("null")
                || desp.getStrTelefono().equals("")) {
            sql = "INSERT INTO ASIGNADOS_LOCAL("
                    + "N_UNIDAD,"
                    + "COD_CLIENTE,"
                    + "ESTADO,"
                    + "VALOR,"
                    + "ESTADO_INSERT,"
                    + "USUARIO,"
                    + "DIRECCION"
                    + ") "
                    + "VALUES ("
                    + desp.getIntUnidad()
                    + ","
                    + desp.getIntCodigo()
                    + ",'ASIGNADO'"
                    + ","
                    + desp.getMinutosEntreClienteServidor()
                    + ",'"
                    + "ACT"
                    + "','"
                    + Principal.sesion[2]
                    + "','"
                    + desp.getStrDireccion()
                    + "')";
        } else {
            sql = "INSERT INTO ASIGNADOS_LOCAL("
                    + "N_UNIDAD,"
                    + "COD_CLIENTE,"
                    + "ESTADO,"
                    + "FONO,"
                    + "VALOR,"
                    + "ESTADO_INSERT,"
                    + "USUARIO,"
                    + "DIRECCION"
                    + ") "
                    + "VALUES ("
                    + desp.getIntUnidad()
                    + ","
                    + desp.getIntCodigo()
                    + ",'ASIGNADO','"
                    + desp.getStrTelefono()
                    + "',"
                    + desp.getMinutosEntreClienteServidor()
                    + ",'"
                    + "ACT"
                    + "','"
                    + Principal.sesion[2]
                    + "','"
                    + desp.getStrDireccion()
                    + "')";
        }
        if (bd.ejecutarSentencia(sql)) {
            /**
             * El respaldo y la insersión dentro del servidor de KRADAC se lo
             * realiza en la base de datos, para eso hay un trigger en la tabla
             * rastreosatelital.asignados_local que hace lo mismo que el del server KRADAC
             * pero tambien hace la ejecución de un procedimiento SP_INSERTAR_RESPALDAR_SERVER
             * el cual trata de insertar en la tabla federada si lo logra todo bien
             * pero si no se hace un insert en la tabla de respaldos locales estos subiran
             * al servidor cuando el icono de red cambie o cuando hay conexión, de
             * esta manera se elimina el retardo que existe cuando no se puede
             * alcanzar el insert a la tabla federada del servidor KRADAC
             */
            /**
             * Si se guarda dentro de la tabla de asignados local solo mostrar
             * el mensaje.
             */
            log.trace("--Insertar ASIGNADO Local Trigger Server--> {}", sql);
        }


        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            log.error("{}", Principal.sesion[1]);
        }
        InsertarDespachoServidorKRADAC();
    }

    /**
     * Inserta luego de asignado el ocupado del taxi cuando se despacho
     */
    public void InsertarDespachoServidorKRADAC() {
        String sql;
        if (desp.getStrTelefono() == null
                || desp.getStrTelefono().equals("null")
                || desp.getStrTelefono().equals("")) {
            sql = "INSERT INTO ASIGNADOS_LOCAL("
                    + "N_UNIDAD,"
                    + "COD_CLIENTE,"
                    + "ESTADO,"
                    + "VALOR,"
                    + "ESTADO_INSERT,"
                    + "USUARIO,"
                    + "DIRECCION"
                    + ") "
                    + "VALUES ("
                    + desp.getIntUnidad()
                    + ","
                    + desp.getIntCodigo()
                    + ",'OCUPADO',"
                    + "-2"
                    + ",'"
                    + "ACT"
                    + "','"
                    + Principal.sesion[2]
                    + "','"
                    + desp.getStrDireccion()
                    + "');";
        } else {
            sql = "INSERT INTO ASIGNADOS_LOCAL("
                    + "N_UNIDAD,"
                    + "COD_CLIENTE,"
                    + "ESTADO,"
                    + "FONO,"
                    + "VALOR,"
                    + "ESTADO_INSERT,"
                    + "USUARIO,"
                    + "DIRECCION"
                    + ") "
                    + "VALUES ("
                    + desp.getIntUnidad()
                    + ","
                    + desp.getIntCodigo()
                    + ",'OCUPADO','"
                    + desp.getStrTelefono() + "',"
                    + "-2"
                    + ",'"
                    + "ACT"
                    + "','"
                    + Principal.sesion[2]
                    + "','"
                    + desp.getStrDireccion()
                    + "');";
        }

        if (bd.ejecutarSentencia(sql)) {
            /**
             * El respaldo y la insersión dentro del servidor de KRADAC se lo
             * realiza en la base de datos, para eso hay un trigger en la tabla
             * rastreosatelital.asignados_local que hace lo mismo que el del server KRADAC
             * pero tambien hace la ejecución de un procedimiento SP_INSERTAR_RESPALDAR_SERVER
             * el cual trata de insertar en la tabla federada si lo logra todo bien
             * pero si no se hace un insert en la tabla de respaldos locales estos subiran
             * al servidor cuando el icono de red cambie o cuando hay conexión, de
             * esta manera se elimina el retardo que existe cuando no se puede
             * alcanzar el insert a la tabla federada del servidor KRADAC
             */
            log.trace("--Insertar OCUPADO Local Trigger Server--> {}", sql);
        }
    }

    /**
     * Inserta luego de asignado el ocupado del taxi cuando se despacho, valor
     * es -2 para reconocer en el servidor que es el esatado de libre
     */
    public void InsertarLibreServidorKRADAC() {
        String sql;
        if (desp.getStrTelefono() == null
                || desp.getStrTelefono().equals("null")
                || desp.getStrTelefono().equals("")) {
            sql = "INSERT INTO ASIGNADOS_LOCAL("
                    + "N_UNIDAD,"
                    + "COD_CLIENTE,"
                    + "ESTADO,"
                    + "VALOR,"
                    + "ESTADO_INSERT,"
                    + "USUARIO,"
                    + "DIRECCION"
                    + ") "
                    + "VALUES ("
                    + desp.getIntUnidad()
                    + ","
                    + desp.getIntCodigo()
                    + ",'LIBRE',"
                    + "-2"
                    + ",'"
                    + "ACT"
                    + "','"
                    + Principal.sesion[2]
                    + "','"
                    + desp.getStrDireccion()
                    + "');";
        } else {
            sql = "INSERT INTO ASIGNADOS_LOCAL("
                    + "N_UNIDAD,"
                    + "COD_CLIENTE,"
                    + "ESTADO,FONO,"
                    + "VALOR,"
                    + "ESTADO_INSERT,"
                    + "USUARIO,"
                    + "DIRECCION"
                    + ") "
                    + "VALUES ("
                    + desp.getIntUnidad()
                    + ","
                    + desp.getIntCodigo()
                    + ",'LIBRE','"
                    + desp.getStrTelefono()
                    + "',"
                    + "-2"
                    + ",'"
                    + "ACT"
                    + "','"
                    + Principal.sesion[2]
                    + "','"
                    + desp.getStrDireccion()
                    + "');";
        }

        if (bd.ejecutarSentencia(sql)) {
            /**
             * El respaldo y la insersión dentro del servidor de KRADAC se lo
             * realiza en la base de datos, para eso hay un trigger en la tabla
             * rastreosatelital.asignados_local que hace lo mismo que el del server KRADAC
             * pero tambien hace la ejecución de un procedimiento SP_INSERTAR_RESPALDAR_SERVER
             * el cual trata de insertar en la tabla federada si lo logra todo bien
             * pero si no se hace un insert en la tabla de respaldos locales estos subiran
             * al servidor cuando el icono de red cambie o cuando hay conexión, de
             * esta manera se elimina el retardo que existe cuando no se puede
             * alcanzar el insert a la tabla federada del servidor KRADAC
             */
            log.trace("--Insertar LIBRE Local Trigger Server--> {}", sql);
        }
    }
}
