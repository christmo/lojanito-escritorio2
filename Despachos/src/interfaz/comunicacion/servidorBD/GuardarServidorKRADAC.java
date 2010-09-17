package interfaz.comunicacion.servidorBD;

import BaseDatos.ConexionBase;
import interfaz.Principal;
import interfaz.funcionesUtilidad;
import interfaz.subVentanas.Despachos;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author christmo
 */
public class GuardarServidorKRADAC extends Thread {

    private ConexionBase bd;
    private Despachos desp;
    private boolean accion;
    private funcionesUtilidad funciones = new funcionesUtilidad();
    private ResultSet rs;

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
    }

    @Override
    public void run() {
        //true ASIGNACION || false LIBRE
        if (accion) {
            InsertarAsignacionServidorKRADAC();
        } else {
            InsertarLibreServidorKRADAC();
        }
        ActualizarServidorConConexion();
    }

    /**
     * Actualiza los estados de los vehiculos guardados localmente mientras se vaya
     * el internet, en el servidor KRADAC cuando haya ya sea alcanzable el servidor
     */
    private void ActualizarServidorConConexion() {
        if (ConexionServidorKRADAC()) {
            if (bd.getNumeroFilasRespaldoAsignacion() > 0) {
                InsertarFilasRespaldadasLocalesEnServidorKRADAC();
            }
        }
    }

    /**
     * Insertar las filas que NO hayan podido guardarse en el servidor al momento
     * de su ejecucion y que estan almacenadas en la tabla de respaldos local
     */
    private void InsertarFilasRespaldadasLocalesEnServidorKRADAC() {
        try {
            long minutos;
            rs = bd.getFilasRespaldoLocalAsignaciones();
            while (rs.next()) {
                long HoraAct = funciones.getHoraEnMilis();
                long HoraInsert = rs.getLong("HORA_INSERT");
                int MinDespacho = rs.getInt("HORA");
                minutos = (((HoraAct - HoraInsert) / 1000) / 60) + MinDespacho;
                System.err.println("Minutos desde la Desconexion:" + minutos);
                boolean estadoInsersionServidor = InsertServidorKRADAC(
                        rs.getInt("N_UNIDAD"),
                        rs.getInt("COD_CLIENTE"),
                        rs.getString("ESTADO"),
                        (int) minutos,
                        rs.getString("FONO"));
                if (estadoInsersionServidor) {
                    /**
                     * Borrar el respaldo ya que se ha guardado correctamente
                     * en els ervidor de KRADAC
                     */
                    BorrarRespadoLocal(HoraInsert);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(GuardarServidorKRADAC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Insertar datos en el servidor de KRADAC PARA LOS DESPACHOS
     * @param intUnidad
     * @param intCodCliente
     * @param strEstado
     * @param minutos -> numero de minutos que han pasado desde que se despacho
     * @param strTelefono
     * @return boolean
     */
    private boolean InsertServidorKRADAC(int intUnidad, int intCodCliente, String strEstado, int minutos, String strTelefono) {
        String sql = "INSERT INTO server(N_UNIDAD,COD_CLIENTE,ESTADO,FONO,VALOR) "
                + "VALUES ("
                + intUnidad
                + ","
                + intCodCliente
                + ",'"
                + strEstado
                + "','"
                + strTelefono
                + "',"
                + minutos
                + ");";
        return bd.ejecutarSentenciaStatement2(sql);
    }

    /**
     * Borrar respaldo local, luego de haber guardado el respaldo en el servidor
     * @param HoraInsert
     */
    private void BorrarRespadoLocal(long HoraInsert) {
        String sql = "DELETE FROM RESPALDO_ASIGNACION_SERVER WHERE HORA_INSERT = " + HoraInsert;
        bd.ejecutarSentenciaStatement2(sql);
    }

    /**
     * Comprueba si el servidor de base de datos de KRADAC es alcanzable para
     * realizar transacciones, con esto sabremos que si hay conexion a internet
     * @return boolean
     */
    private boolean ConexionServidorKRADAC() {
        /*try {
        //InetAddress address = InetAddress.getByName("200.0.29.117");
        InetAddress address = InetAddress.getByName(Principal.arcConfig.getProperty("ip_kradac"));
        // Try to reach the specified address within the timeout
        // periode. If during this periode the address cannot be
        // reach then the method returns false.
        boolean reachable = address.isReachable(1000);
        System.out.println("Es alcanzable el server KRADAC: " + reachable);
        return reachable;
        } catch (IOException ex) {
        Logger.getLogger(GuardarServidorKRADAC.class.getName()).log(Level.SEVERE, null, ex);
        return false;
        }*/
        System.err.println("Insertar server KRADAC: " + ConsultaRecorridosServidorBD.HayInternet);
        return ConsultaRecorridosServidorBD.HayInternet;
    }

    /**
     * Inserta la asignasion en el servidor kradac
     */
    public void InsertarAsignacionServidorKRADAC() {
        String sql = "INSERT INTO server(N_UNIDAD,COD_CLIENTE,ESTADO,HORA,FONO) VALUES ("
                + desp.getIntUnidad()
                + ","
                + desp.getIntCodigo()
                + ",'ASIGNADO',"
                + desp.getMinutosEntreClienteServidor()
                + ",'"
                + desp.getStrTelefono()
                + "');";
        if (!bd.ejecutarSentencia(sql)) {
            System.err.println("Respaldar ASIGNADO...");
            String sql2 = "INSERT INTO RESPALDO_ASIGNACION_SERVER(N_UNIDAD,COD_CLIENTE,ESTADO,FECHA,HORA,FONO,HORA_INSERT) "
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
                    + ");";
            bd.ejecutarSentencia(sql2);
        }
        System.err.println("KRADAC: " + sql);
        InsertarDespachoServidorKRADAC();
    }

    /**
     * Inserta luego de asignado el ocupado del taxi cuando se despacho
     */
    public void InsertarDespachoServidorKRADAC() {
        String sql = "INSERT INTO server(N_UNIDAD,COD_CLIENTE,ESTADO,HORA,FONO) "
                + "VALUES ("
                + desp.getIntUnidad() + "," + desp.getIntCodigo() + ",'OCUPADO'," + "-1" + ",'"
                + desp.getStrTelefono()
                + "');";
        System.err.println("KRADAC: " + sql);
        if (!bd.ejecutarSentencia(sql)) {
            System.err.println("Respaldar OCUPADO...");
            String sql2 = "INSERT INTO RESPALDO_ASIGNACION_SERVER(N_UNIDAD,COD_CLIENTE,ESTADO,FECHA,HORA,FONO,HORA_INSERT) "
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
                    + ");";
            bd.ejecutarSentencia(sql2);
        }
    }

    /**
     * Inserta luego de asignado el ocupado del taxi cuando se despacho
     */
    public void InsertarLibreServidorKRADAC() {
        System.out.println("Enviar datos al Server...");
        String sql = "INSERT INTO server(N_UNIDAD,COD_CLIENTE,ESTADO,HORA,FONO) "
                + "VALUES ("
                + desp.getIntUnidad() + "," + desp.getIntCodigo() + ",'LIBRE'," + "-2" + ",'"
                + desp.getStrTelefono()
                + "');";
        System.err.println("KRADAC: " + sql);
        if (!bd.ejecutarSentencia(sql)) {
            System.err.println("Respaldar LIBRE...");
            String sql2 = "INSERT INTO RESPALDO_ASIGNACION_SERVER(N_UNIDAD,COD_CLIENTE,ESTADO,FECHA,HORA,FONO,HORA_INSERT) "
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
                    + ");";
            bd.ejecutarSentencia(sql2);
        }
    }
}
