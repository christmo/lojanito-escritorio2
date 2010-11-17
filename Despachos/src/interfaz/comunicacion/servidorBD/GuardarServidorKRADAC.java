package interfaz.comunicacion.servidorBD;

import BaseDatos.ConexionBase;
import interfaz.Principal;
import interfaz.funcionesUtilidad;
import interfaz.subVentanas.Despachos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.util.logging.Level;
//import java.util.logging.Logger;
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
    private funcionesUtilidad funciones = new funcionesUtilidad();

    /**
     * Guarda por separado los datos que van al servidor
     * @param bd
     * @param d
     * @param accion || true ASIGNACION || false LIBRE
     */
    public GuardarServidorKRADAC(Despachos d, boolean accion) {
        this.bd = new ConexionBase(Principal.arcConfig);
        this.desp = d;
        this.accion = accion;
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
        bd.CerrarConexion();
    }

    /**
     * Inserta la asignasion en el servidor kradac
     */
    public void InsertarAsignacionServidorKRADAC() {
        String sql = "INSERT INTO server(N_UNIDAD,COD_CLIENTE,ESTADO,FONO,VALOR,ESTADO_INSERT,USUARIO) VALUES ("
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
                + "')";
        if (!bd.ejecutarSentencia(sql)) {
            //System.err.println("Respaldar ASIGNADO...");
            ConexionBase cb = new ConexionBase(Principal.arcConfig);
            String sql2 = "INSERT INTO RESPALDO_ASIGNACION_SERVER(N_UNIDAD,COD_CLIENTE,ESTADO,FECHA,HORA,FONO,HORA_INSERT,USUARIO) "
                    + "VALUES ("
                    + desp.getIntUnidad()
                    + ","
                    + desp.getIntCodigo()
                    + ",'ASIGNADO','"
                    + funciones.getFecha()
                    + "',"
                    + desp.getMinutosEntreClienteServidor()
                    + ",'"
                    + desp.getStrTelefono()
                    + "',"
                    + funciones.getHoraEnMilis()
                    + ",'"
                    + Principal.sesion[2]
                    + "');";
            cb.ejecutarSentencia(sql2);
            log.trace("Fallo Asignacion, Respaldo insert Server Kradac: {}", sql2);
            cb.CerrarConexion();
        } else {
            log.trace("Exito Asignacion guardada server KRADAC: {}", sql);
        }
        System.err.println("KRADAC: " + sql);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            //Logger.getLogger(GuardarServidorKRADAC.class.getName()).log(Level.SEVERE, null, ex);
            log.error("{}", Principal.sesion[1]);
        }
        InsertarDespachoServidorKRADAC();
    }

    /**
     * Inserta luego de asignado el ocupado del taxi cuando se despacho
     */
    public void InsertarDespachoServidorKRADAC() {
        String sql = "INSERT INTO server(N_UNIDAD,COD_CLIENTE,ESTADO,FONO,VALOR,ESTADO_INSERT,USUARIO) "
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
                + "');";
        System.err.println("KRADAC: " + sql);
        if (!bd.ejecutarSentencia(sql)) {
            //System.err.println("Respaldar OCUPADO...");
            ConexionBase cb = new ConexionBase(Principal.arcConfig);
            String sql2 = "INSERT INTO RESPALDO_ASIGNACION_SERVER(N_UNIDAD,COD_CLIENTE,ESTADO,FECHA,HORA,FONO,HORA_INSERT,USUARIO) "
                    + "VALUES ("
                    + desp.getIntUnidad()
                    + ","
                    + desp.getIntCodigo()
                    + ",'"
                    + "OCUPADO"
                    + "','"
                    + funciones.getFecha()
                    + "',"
                    + "0"
                    + ",'"
                    + desp.getStrTelefono()
                    + "',"
                    + funciones.getHoraEnMilis()
                    + ",'"
                    + Principal.sesion[2]
                    + "');";
            cb.ejecutarSentencia(sql2);
            log.trace("Fallo Despacho, Respaldo insert Server Kradac: {}", sql2);
            cb.CerrarConexion();
        }else {
            log.trace("Exito Despacho guardada server KRADAC: {}", sql);
        }
    }

    /**
     * Inserta luego de asignado el ocupado del taxi cuando se despacho
     */
    public void InsertarLibreServidorKRADAC() {
        System.out.println("Enviar datos al Server...");
        String sql = "INSERT INTO server(N_UNIDAD,COD_CLIENTE,ESTADO,FONO,VALOR,ESTADO_INSERT,USUARIO) "
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
                + "');";
        System.err.println("KRADAC: " + sql);
        if (!bd.ejecutarSentencia(sql)) {
            //System.err.println("Respaldar LIBRE...");
            ConexionBase cb = new ConexionBase(Principal.arcConfig);
            String sql2 = "INSERT INTO RESPALDO_ASIGNACION_SERVER(N_UNIDAD,COD_CLIENTE,ESTADO,FECHA,HORA,FONO,HORA_INSERT,USUARIO) "
                    + "VALUES ("
                    + desp.getIntUnidad()
                    + ","
                    + desp.getIntCodigo()
                    + ",'"
                    + "LIBRE"
                    + "','"
                    + funciones.getFecha()
                    + "',"
                    + "0"
                    + ",'"
                    + desp.getStrTelefono()
                    + "',"
                    + funciones.getHoraEnMilis()
                    + ",'"
                    + Principal.sesion[2]
                    + "');";
            cb.ejecutarSentencia(sql2);
            log.trace("Fallo Liberacion, Respaldo insert Server Kradac: {}", sql2);
            cb.CerrarConexion();
        }else {
            log.trace("Exito Liberacion guardada server KRADAC: {}", sql);
        }
    }
}