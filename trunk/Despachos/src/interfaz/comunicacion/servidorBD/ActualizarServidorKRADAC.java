/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.comunicacion.servidorBD;

import BaseDatos.ConexionBase;
import interfaz.Principal;
import interfaz.funcionesUtilidad;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class ActualizarServidorKRADAC extends Thread {

    private ConexionBase bd;
    private ResultSet rs;
    private funcionesUtilidad funciones = new funcionesUtilidad();
    private int intFilasRespaldadas;

    public ActualizarServidorKRADAC(int filas) {
        this.intFilasRespaldadas = filas;
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
                System.err.println("Actualizar el Servidor KRADAC -> Despachos sin conexion...");
                this.bd = new ConexionBase(Principal.arcConfig);
                InsertarFilasRespaldadasLocalesEnServidorKRADAC();
                bd.CerrarConexion();
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
                //System.err.println("Minutos desde la Desconexion:" + minutos);
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
        //System.err.println("Insertar server KRADAC: " + ConsultaRecorridosServidorBD.HayInternet);
        return ConsultaRecorridosServidorBD.HayInternet;
    }
}
