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
        bd.CerrarConexion();
    }

    /**
     * Actualiza los estados de los vehiculos guardados localmente mientras se vaya
     * el internet, en el servidor KRADAC cuando haya ya sea alcanzable el servidor
     */
    private void ActualizarServidorConConexion() {
        if (ConexionServidorKRADAC()) {
            if (intFilasRespaldadas > 0) {
                log.trace("Empezar Actualización al servidor de Kradac filas a insertar: {}", intFilasRespaldadas);
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
                        rs.getString("DIRECCION"));

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
            }
        } catch (SQLException ex) {
            log.trace("Resulset cerrado...");
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

        if (strTelefono.equals("null")) {
            strTelefono = "";
        }

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
        return bd.ejecutarSentenciaActualizarServidorKradac(sql);
    }

    /**
     * Borrar respaldo local, luego de haber guardado el respaldo en el servidor
     * @param HoraInsert
     */
    private void BorrarRespadoLocal(long HoraInsert) {
        String sql = "DELETE FROM RESPALDO_ASIGNACION_SERVER WHERE HORA_INSERT = " + HoraInsert;
        bd.ejecutarSentenciaActualizarServidorKradac(sql);
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
