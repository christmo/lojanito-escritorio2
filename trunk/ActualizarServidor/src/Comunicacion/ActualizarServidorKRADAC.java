/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacion;

import BaseDatos.BaseDatos;
import Utilitarios.Utilitarios;
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
    private BaseDatos bd;
    private ResultSet rs;
    private Utilitarios funciones = new Utilitarios();
    private int intFilasRespaldadas;
    private String tablaServidor = "server";

    public ActualizarServidorKRADAC(int filas, BaseDatos cb) {
        this.intFilasRespaldadas = filas;
        this.bd = cb;
    }

    @Override
    public void run() {
        ActualizarServidorConConexion();
        bd.CerrarConexion();
    }

    /**
     * Actualiza los estados de los vehiculos guardados localmente mientras se vaya
     * el internet, en el servidor KRADAC cuando haya ya sea alcanzable el servidor
     */
    private void ActualizarServidorConConexion() {
        if (intFilasRespaldadas > 0) {
            log.trace("Empezar Actualización al servidor de Kradac filas a insertar: {}", intFilasRespaldadas);
            try {
                InsertarFilasRespaldadasLocalesEnServidorKRADAC();
            } catch (NullPointerException ex) {
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
            tablaServidor = bd.getValorConfiguiracion("tabla_servidor");
            rs = bd.getFilasRespaldoLocalAsignaciones();
            while (rs.next()) {
                try {
                    long HoraAct = funciones.getHoraEnMilis() / 1000; // hora actual en segundos
                    long HoraInsert = rs.getLong("HORA_INSERT"); // BD trae tiempo en segundos
                    int MinDespacho = rs.getInt("HORA");
                    /**
                     * No es necesario dividir para 1000 en la base de datos se
                     * almacena los tiempos en segundos
                     */
                    minutos = ((HoraAct - HoraInsert) / 60) + MinDespacho;
                    boolean estadoInsersionServidor = InsertServidorKRADAC(
                            rs.getInt("N_UNIDAD"),
                            rs.getInt("COD_CLIENTE"),
                            rs.getString("ESTADO"),
                            (int) minutos,
                            rs.getString("FONO"),
                            rs.getString("USUARIO"),
                            rs.getString("DIRECCION"),
                            rs.getString("ESTADO_INSERT"));

                    if (estadoInsersionServidor) {
                        /**
                         * Borrar el respaldo ya que se ha guardado correctamente
                         * en el servidor de KRADAC
                         */
                        BorrarRespadoLocal(HoraInsert);
                        log.trace("Insercion en el servidor CORRECTA borrar el respaldo local: {} -> por actualizar [{}]", HoraInsert, rs.getRow());
                    } else {
                        log.trace("No se pudo insertar en el servidor KRADAC sigue el respaldo local -> por actualizar [{}]", rs.getRow());
                    }
                } catch (SQLException ex) {
                    int intCode = ex.getErrorCode();
                    if (intCode == 1146) {
                        log.trace("Tabla no existe: [" + ex.getMessage().split("'")[1] + "]");
                        break;
                    } else if (intCode == 1429) {
                        log.trace("No hay permisos: [" + ex.getMessage().split("'")[1] + " -> " + ex.getMessage().split("'")[3] + "]");
                        break;
                    } else {
                        log.trace("Error al ejecutar sentecia codigo[" + intCode + "]", ex);
                    }
                }
            }
        } catch (SQLException ex) {
            int intCode = ex.getErrorCode();
            if (intCode == 1146) {
                log.trace("Tabla no existe: [" + ex.getMessage().split("'")[1] + "]");
            } else {
                log.trace("Error al ejecutar sentecia codigo[" + intCode + "]", ex);
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
            String direccion,
            String estado_insert) throws SQLException {

        if (strTelefono.equals("null")) {
            strTelefono = "";
        }

        String sql = "INSERT INTO " + tablaServidor + "(N_UNIDAD,COD_CLIENTE,ESTADO,FONO,VALOR,ESTADO_INSERT,USUARIO,DIRECCION) "
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
                + estado_insert
                + "','"
                + usuario
                + "','"
                + direccion
                + "');";
        return bd.ejecutarSentencia(sql);
    }

    /**
     * Borrar respaldo local, luego de haber guardado el respaldo en el servidor
     * @param HoraInsert
     */
    private void BorrarRespadoLocal(long HoraInsert) throws SQLException {
        String sql = "DELETE FROM RESPALDO_ASIGNACION_SERVER WHERE HORA_INSERT = " + HoraInsert;
        bd.ejecutarSentencia(sql);
        log.trace("Respaldo borrado correctamente...");
    }
}
