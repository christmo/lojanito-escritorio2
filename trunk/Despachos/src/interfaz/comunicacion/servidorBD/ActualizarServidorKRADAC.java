/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.comunicacion.servidorBD;

import BaseDatos.ConexionBase;
import interfaz.funcionesUtilidad;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author christmo
 */
public class ActualizarServidorKRADAC extends Thread {

    /**
     * Logger para guardar los log en un archivo y enviar por mail los de error
     */
    private static final Logger log = LoggerFactory.getLogger(ActualizarServidorKRADAC.class);
    private ConexionBase bd;
    private ResultSet rs;
    private funcionesUtilidad funciones = new funcionesUtilidad();
    private int intFilasRespaldadas;

    public ActualizarServidorKRADAC(int filas, ConexionBase cb) {
        this.intFilasRespaldadas = filas;
        this.bd = cb;
    }

    @Override
    public void run() {
        ActualizarServidorConConexion();
    }

    /**
     * Actualiza los estados de los vehiculos guardados localmente mientras se vaya
     * el internet, en el servidor KRADAC cuando haya ya sea alcanzable el servidor
     */
    private void ActualizarServidorConConexion() {
        if (ConexionServidorKRADAC()) {
            if (intFilasRespaldadas > 0) {
                log.debug("Empezar Actualizacion al servidor de Kradac filas a insertar:{}", intFilasRespaldadas);
                try {
                    InsertarFilasRespaldadasLocalesEnServidorKRADAC();
                } catch (NullPointerException ex) {
                }
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
                boolean estadoInsersionServidor = InsertServidorKRADAC(
                        rs.getInt("N_UNIDAD"),
                        rs.getInt("COD_CLIENTE"),
                        rs.getString("ESTADO"),
                        (int) minutos,
                        rs.getString("FONO"),
                        rs.getString("USUARIO"),
                        rs.getString("DIRECCION"));

                if (estadoInsersionServidor) {
                    /**
                     * Borrar el respaldo ya que se ha guardado correctamente
                     * en el servidor de KRADAC
                     */
                    BorrarRespadoLocal(HoraInsert);
                    log.trace("Insercion en el servidor correcta borrar el respaldo local: {}", HoraInsert);
                } else {
                    log.trace("No se pudo insertar en el servidor KRADAC sigue el respaldo local...");
                }
            }
        } catch (SQLException ex) {
            if (ex.getMessage().equals("Operation not allowed after ResultSet closed")) {
            } else {
                //log.error("{}", Principal.sesion[1]);
            }
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
    private boolean InsertServidorKRADAC(int intUnidad,
            int intCodCliente,
            String strEstado,
            int minutos,
            String strTelefono,
            String usuario,
            String direccion) {
        String sql = "INSERT INTO server(N_UNIDAD,COD_CLIENTE,ESTADO,FONO,VALOR,ESTADO_INSERT,USUARIO,DIRECCION) "
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
                + ",'"
                + "RES"
                + "','"
                + usuario
                + "','"
                + direccion
                + "');";
        return bd.ejecutarSentenciaStatement2(sql);
    }

    /**
     * Borrar respaldo local, luego de haber guardado el respaldo en el servidor
     * @param HoraInsert
     */
    private void BorrarRespadoLocal(long HoraInsert) {
        String sql = "DELETE FROM RESPALDO_ASIGNACION_SERVER WHERE HORA_INSERT = " + HoraInsert;
        bd.ejecutarSentenciaStatement2(sql);
        log.trace("Respaldo borrado correctamente...");
    }

    /**
     * Comprueba si el servidor de base de datos de KRADAC es alcanzable para
     * realizar transacciones, con esto sabremos que si hay conexion a internet
     * @return boolean
     */
    private boolean ConexionServidorKRADAC() {
        return ConsultaRecorridosServidorBD.HayInternet;
    }
}
