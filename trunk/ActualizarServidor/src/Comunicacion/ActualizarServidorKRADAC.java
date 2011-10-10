/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacion;

import BaseDatos.BaseDatos;
import PrincipalGUI.Principal;
import Utilitarios.Utilitarios;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
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
    private String DIRECCION;
    private int PUERTO;
    private Socket mensajeSocket;
    private BufferedReader entrada;
    private PrintStream salida;

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
                    switch (intCode) {

                        case 1146:
                            log.trace("Tabla no existe: [" + ex.getMessage().split("'")[1] + "]");
                            break;
                        case 1429:
                            try {
                                //Unable to connect to foreign data source: Host '186.42.209.202' is not allowed to connect to this MySQL se
                                if (ex.getMessage().contains("Unable to connect to foreign data source: Host")) {
                                    log.error("IP sin permisos[" + ex.getMessage().split("'")[1] + "][{}]", Principal.EMPRESA);
                                    enviarIPNuevaAlServidor(ex.getMessage().split("'")[1], Principal.EMPRESA);
                                    //Unable to connect to foreign data source: Can't connect to MySQL server on '200.0.29.121' (10060)
                                } else if (ex.getMessage().contains("Unable to connect to foreign data source: Can't")) {
                                    log.error("No se puede guardar los datos en el servidor con IP[" + ex.getMessage().split("'")[2] + "][{}]", Principal.EMPRESA);
                                } else {
                                    log.trace("No hay permisos: [" + ex.getMessage().split("'")[1] + " -> " + ex.getMessage().split("'")[3] + "][{}]",
                                            Principal.EMPRESA, ex);
                                }
                            } catch (ArrayIndexOutOfBoundsException aiobe) {
                                log.trace("No hay permisos nueva ip: [" + ex.getMessage().split("'")[1] + "]", ex);
                            }
                            System.exit(0);
                            break;
                        default:
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

    /**
     * Enviar la IP de conexión cuando el ISP cambia la ip publica a los clientes
     * de la base de datos al enviar al respaldo.
     * @param strIP
     * @param strEmpresa 
     */
    private void enviarIPNuevaAlServidor(String strIP, String strEmpresa) {
        DIRECCION = bd.getValorConfiguiracion("ip_kradac");
        try {
            PUERTO = Integer.parseInt(bd.getValorConfiguiracion("puerto_kradac"));
            AbrirPuerto();
        } catch (NumberFormatException ex) {
            System.err.println("Revisar el archivo de propiedades la ip y el puerto del servidor de KRADAC...");
        }
        try {
            salida = new PrintStream(mensajeSocket.getOutputStream(), true);

            String cmdMensaje = "$$4##" + strEmpresa + "##" + strIP + "$$\n";
            salida.print(cmdMensaje);
            
            log.trace("Nueva IP enviada:[{}]", strIP);

            cerrarConexionServerKradac();

        } catch (Exception e) {
            log.trace("Error al enviar un mensaje {}", e.getMessage());
        }
    }

    /**
     * Abre el puerto de comunicación entre el servidor y las centrales
     */
    private void AbrirPuerto() {
        try {
            try {
                mensajeSocket = new Socket(DIRECCION, PUERTO);
                log.trace("Iniciar conexion con el server [{}] para enviar IP:", DIRECCION);
            } catch (UnknownHostException ex) {
                cerrarConexionServerKradac();
            } catch (IOException ex) {
                if (ex.getMessage().equals("No route to host: connect")) {
                    cerrarConexionServerKradac();
                }
            }
        } catch (StackOverflowError m) {
        }
    }

    /**
     * Cierra la conexion con el servidor de KRADAC
     */
    private void cerrarConexionServerKradac() {
        try {
            try {
                salida.close();
                entrada.close();
            } catch (NullPointerException ex) {
            }
            try {
                mensajeSocket.close();
                log.trace("Cerrar conexion con el server para enviar IP...");
            } catch (NullPointerException ex) {
            }
        } catch (IOException ex) {
        }
    }
}
