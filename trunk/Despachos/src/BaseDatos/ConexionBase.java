package BaseDatos;

import com.mysql.jdbc.Statement;
import interfaz.Principal;
import interfaz.funcionesUtilidad;
import interfaz.subVentanas.Clientes;
import interfaz.subVentanas.Despachos;
import interfaz.subVentanas.Pendientes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sistemaoperativo.LevantarServicios;

/**
 * @author christmo
 */
public class ConexionBase {

    /**
     * Logger para guardar los log en un archivo y enviar por mail los de error
     */
    private static final Logger log = LoggerFactory.getLogger(ConexionBase.class);
    private String driver, url, ip, usr, pass;
    /**
     * Nombre de la Base de datos
     */
    private String bd;
    private Connection conexion;
    private Statement st;
    private ResultSet rs = null;
    private Properties arcConfig;
    private funcionesUtilidad funciones = new funcionesUtilidad();
    private String estadoUnidad;

    /**
     * Crea la conexion directamente a la base de datos de rastreosatelital
     * de kradac, parametros de la conexion quemados por defecto para la
     * maquina local
     */
    public ConexionBase(Properties conf) throws NullPointerException {
        try {
            this.arcConfig = conf;
            driver = "com.mysql.jdbc.Driver";
            this.ip = arcConfig.getProperty("ip_base");
            this.bd = arcConfig.getProperty("base");
            this.usr = arcConfig.getProperty("user");
            this.pass = arcConfig.getProperty("pass");

            url = "jdbc:mysql://" + ip + "/" + bd;

            try {
                try {
                    Class.forName(driver).newInstance();
                } catch (InstantiationException ex) {
                    java.util.logging.Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    java.util.logging.Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                conexion = DriverManager.getConnection(url, usr, pass);
            } catch (SQLException ex) {
                if (ex.getMessage().equals("Communications link failure")) {
                    log.info("Enlace de conexión con la base de datos falló, falta el archivo de configuración...");
                }
            }
            try {
                st = (Statement) conexion.createStatement();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
            }

            log.info("Iniciar conexion a la base de datos...");
        } catch (NullPointerException ex) {
            throw new UnsupportedOperationException("nulo");
        }
    }

    /**
     * Crea una conexion a cualquier base de datos mysql, con parametros
     * de conexion indepenientes
     * @param ip - IP del servidor
     * @param bd - Nombre de la Base de datos
     * @param usr -  Nombre de Usuario
     * @param pass - Clave de la Base de datos
     */
    public ConexionBase(String ip, String bd, String usr, String pass) {
        driver = "com.mysql.jdbc.Driver";
        this.bd = bd;
        this.usr = usr;
        this.pass = pass;
        url = "jdbc:mysql://" + ip + "/" + bd;
        try {
            try {
                Class.forName(driver).newInstance();
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conexion = DriverManager.getConnection(url, usr, pass);
        } catch (SQLException ex) {
            log.info("No se puede obtener la conexion...", ex);
        }
        try {
            st = (Statement) conexion.createStatement();
        } catch (SQLException ex) {
            log.info("No se puede crear el estatement principal...", ex);
        } catch (NullPointerException ex) {
            log.info("Conexión es NULL", ex);
        }
        log.info("Conexion a Base de Datos OK: {}", bd);
    }

    /**
     * Permite hacer la reconexion a la base de datos...
     */
    public void reconectarBaseDatos() {
        try {
            st.close();
            conexion.close();
        } catch (Exception ex) {
        }
        try {
            conexion = DriverManager.getConnection(url, usr, pass);
            try {
                st = (Statement) conexion.createStatement();
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
            }
            log.debug("RECONEXIÓN BASE");
            contadorReconexiones = 0;
        } catch (SQLException ex) {
            String txt = ex.getMessage();
            int cod = ex.getErrorCode();
            if (txt.substring(0, 27).equals("Communications link failure")) {
                log.info("MySQL no esta corriendo, el servicio esta abajo...");
                LevantarServicios.LevantarWAMP();
                LevantarServicios.LevantarTeamViewer(getValorConfiguiracion("tv"));
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex1) {
                    java.util.logging.Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex1);
                }
                reconectarBaseDatos();
            } else {
                log.error("[" + Principal.sesion[1] + "][" + cod + "]{}", ex.getMessage(), ex);
                try {
                    Thread.sleep(30000);
                    log.info("Despues de saturar la base de datos con conexiones se espera 30 segundos...");
                } catch (InterruptedException ex1) {
                    java.util.logging.Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }

    /**
     * Ejecuta una consulta en la base de datos, que devuelve valores
     * es necesario recorrer el resultset
     * @param sql - debe ser Select
     * @return ResultSet
     */
    public ResultSet ejecutarConsulta(String sql) {
        //System.out.println("Consultar: " + sql);
        ResultSet r = null;
        try {
            Statement stat = (Statement) conexion.createStatement();
            r = stat.executeQuery(sql);
            log.trace(sql);

        } catch (SQLException ex) {
            int code = ex.getErrorCode();
            if (ex.getMessage().equals("No operations allowed after statement closed.")) {
                log.info("Statement cerrado...");
            } else if (ex.getMessage().equals("Se realizó una consulta como null.")) {
                log.info("Statement cerrado...");
            } else {
                log.info("[COD {}]Error al consultar: " + sql, code, ex);
            }
        } catch (NullPointerException ex) {
            log.info("Statement está nulo...");
        }
        return r;
    }

    public ResultSet ejecutarConsultaStatement2(String sql) {
        //System.out.println("Consultar: " + sql);
        ResultSet r = null;
        Statement st2 = null;
        try {
            st2 = (Statement) conexion.createStatement();
            r = st2.executeQuery(sql);
            log.trace(sql);
        } catch (SQLException ex) {
            int code = ex.getErrorCode();
            if (ex.getMessage().equals("No operations allowed after statement closed.")) {
                log.info("[COD {}]Statement cerrado...", code);
            } else if (ex.getMessage().equals("Se realizó una consulta como null.")) {
                log.info("[COD {}]Statement cerrado...", code);
            } else {
                if (ex.getMessage().length() > 113) {
                    try {
                        if (ex.getMessage().substring(0, 113).equals("No operations allowed after connection closed.Connection was implicitly closed due to underlying exception/error:")) {
                            log.info("[COD {}]Se desconectó el cable de RED del equipo...", code);
                            reconectarBaseDatos();
                        }
                    } catch (StringIndexOutOfBoundsException sinoe) {
                        log.info("[COD {}]Error diferente no tiene la longitud contemplada...", code);
                    }
                } else if (ex.getMessage().equals("Table 'regcodesttaxi' is marked as crashed and should be repaired")) {
                    repararTablaRota(ex.getMessage());
                } else {
                    log.error("[COD " + code + "] empresa[" + Principal.sesion[1] + "] mensaje[{}]" + sql, ex.getMessage(), ex);
                }
            }

        } catch (NullPointerException ex) {
            log.info("Statement esta nulo...");
        }
        return r;
    }

    /**
     * Permite mandar un comando de reparacion a la base de datos cuando se rompa
     * una tabla de mysql...
     * @param nombre tabla
     */
    private void repararTablaRota(String mensajeError) {
        String rutaTabla = mensajeError.split("'")[1];
        String[] tablaFull = rutaTabla.split("\r");
        String tabla = "";
        if (tablaFull.length == 3) {
            tabla = tablaFull[2];
            tabla = "r" + tabla.split("[.]")[0];
        } else {
            tablaFull = tablaFull[0].replace("\\", "#").split("#");
            if (tablaFull.length == 3) {
                tabla = tablaFull[2].split("[.]")[0];
            } else {
                tabla = tablaFull[0];
            }
        }
        String sqlReparar = "repair table " + tabla;

        ejecutarSentencia(sqlReparar);
        log.error("Empresa[" + Principal.sesion[1] + "]Reparada la tabla [" + tabla + "]");
    }

    /**
     * Ejecuta una consulta en la base de datos, que devuelve valores
     * no es necesario recorrer el resultset
     * @param sql - debe ser Select
     * @return ResultSet
     */
    public ResultSet ejecutarConsultaUnDato(String sql) throws UnsupportedOperationException {
        //System.out.println("Consultar: " + sql);
        ResultSet rsCUD = null;
        try {
            Statement sta = (Statement) conexion.createStatement();
            rsCUD = sta.executeQuery(sql);
            log.trace(sql);
            rsCUD.next();
        } catch (SQLException ex) {
            int code = ex.getErrorCode();
            switch (code) {
                case 1054:
                    String[] datos = ex.getMessage().split("'");
                    log.info("[COD {}]No se conoce la columna " + datos[1] + " en [" + datos[3] + "] \nSQL:" + sql, code);
                    break;
            }
            if (ex.getMessage().equals("Result consisted of more than one row")) {
                log.info("[COD {}]Tiene + de 1 estado la unidad a la misma hora -> {}", code, sql);
                throw new UnsupportedOperationException("Tiene mas de una fila");
            } else if (!ex.getMessage().equals("No operations allowed after statement closed.")) {
                log.info("[COD {}]Statement cerrado [Reconectar]", code);
                reconectarBaseDatos();
                //return ejecutarConsultaUnDato(sql);
            } else if (ex.getMessage().equals("Communications link failure\nLast packet sent to the server was 0 ms ago.")) {
                log.info("[COD {}]Base de datos cerrada intencionalmente CERRADA...", code);
                reconectarBaseDatos();
            } else if (ex.getMessage().equals("Server shutdown in progress")) {
                log.info("[COD {}]Base de datos cerrada intencionalmente CERRADA...", code);
                reconectarBaseDatos();
            } else {
                log.error("[COD: {" + code + "}] empresa[" + Principal.sesion[1] + "] mensaje[{}]", ex.getMessage(), ex);
            }
        }
        return rsCUD;
    }
    /**
     * Permite registrar el numero de intentos de conexion a la base de datos
     * al tratar de reconectar...
     */
    int contadorReconexiones = 0;

    /**
     * Ejecuta una consulta en la base de datos, que devuelve valores
     * no es necesario recorrer el resultset, no imprime la consulta
     * para ponerla en hilos donde no es necesario ver lo que sale...
     * @param sql - debe ser Select
     * @return ResultSet
     */
    public ResultSet ejecutarConsultaUnDatoNoImprimir(String sql) {
        try {
            st = (Statement) conexion.createStatement();
            rs = st.executeQuery(sql);
            //log.trace(sql);
            rs.next();
        } catch (SQLException ex) {
            int code = ex.getErrorCode();
            if (!ex.getMessage().equals("No operations allowed after statement closed.")) {
                if (contadorReconexiones <= 5) {
                    log.info("[COD {}]Statement cerrado [Reconectar]", code);
                    contadorReconexiones++;
                    reconectarBaseDatos();
                }
                return null;
            } else if (ex.getMessage().substring(0, 113).equals("No operations allowed after connection closed.Connection was implicitly closed due to underlying exception/error:")) {
                log.info("[COD {}]MySQL no esta corriendo, levantar el servicio...", code);
                return null;
            } else {
                log.error("[COD " + code + "][" + Principal.sesion[1] + "]{}", ex.getMessage(), ex);
                return null;
            }
        }
        return rs;
    }
    /**
     * Resul set auxiliar para las dobles consultas
     */
    ResultSet rsAux;

    /**
     * Ejecuta una consulta en la base de datos, que devuelve valores
     * no es necesario recorrer el resultset
     * @param sql - debe ser Select
     * @return ResultSet
     */
    public ResultSet ejecutarConsultaUnDatoAux(String sql) {
        //System.out.println("Consultar: " + sql);
        try {
            rsAux = st.executeQuery(sql);
            log.trace(sql);
            rsAux.next();
        } catch (SQLException ex) {
            log.trace("[COD {}] ejecutarConsultaUnDatoAux", ex.getErrorCode(), ex);
        }
        return rsAux;
    }

    /**
     * Ejecuta una sentencia en la base esta puede ser de INSERT, UPDATE O
     * DELETE
     * @param sql - Sentencias INSERT, UPDATE, DELETE
     * @return int - confirmacion del resultado 1 valido || 0 invalido
     */
    public boolean ejecutarSentencia(String sql) {
        try {
            st = (Statement) conexion.createStatement();

            log.trace("Ejecutar: {}", sql);

            int rta = st.executeUpdate(sql);
            if (rta >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            String txt = ex.getMessage();
            int code = ex.getErrorCode();

            switch (ex.getErrorCode()) {
                //Incorrect key file
                case 126:
                    repararTablaRota(txt);
                    return false;
                //tabla rota
                case 145:
                    repararTablaRota(txt);
                    return false;
                //tabla no creada
                case 1146:
                    String[] texto = ex.getMessage().split("'");
                    try {
                        log.info("[" + Principal.sesion[1] + "][COD:" + code + "] La tabla no esta creada: [" + texto[1] + "]");
                    } catch (ArrayIndexOutOfBoundsException aiob) {
                    }
                    return false;
                //Numero de columnas a insertar no coincide
                case 1136:
                    log.info("El numero de columnas a insertar no coincide "
                            + "con el numero de columnas de la tabla...", ex);
                    return false;
                //Error de sintaxis en SQL
                case 1064:
                    log.info("SQL Mal Formada:{}", sql, ex);
                    return false;
                //Error de clave Primaria
                case 1062:
                    log.info("Error de Clave Primaria -> Registro ya ingresado...");
                    return false;
                case 0:
                    log.info("[COD:" + code + "]Comunicación con la base datos falló -> ultimo paquete enviado mayor a 5ms...\nALERTA[" + sql + "]");
                    if (ex.getMessage().equals("No operations allowed after connection closed.Connection was implicitly closed due to underlying exception/error:")) {
                        log.info("[COD:" + code + "]Conexión base de datos cerrada...", ex);
                        return false;
                    } else if (ex.getMessage().equals("Connection.close() has already been called. Invalid operation in this state.")) {
                        log.info("[COD:" + code + "]Conexión base de datos cerrada... Connection.close()", ex);
                        return false;
                    } else if (ex.getMessage().equals("No operations allowed after connection closed.")) {
                        log.info("[COD:" + code + "]Conexión base de datos cerrada...");
                        return false;
                    }
                    return false;
                //No hay peromisos para insertar...
                case 1296:
                    String[] ip_server = ex.getMessage().split("'");
                    log.error("[COD:" + code + "][Empresa: {}]MySQL no tiene permisos para INSERTAR en la tabla FEDERADA del servidor KRADAC -> [" + ip_server[2] + " --> " + ip_server[4] + "]", Principal.sesion[1]);
                    return false;
            }

            String strMSG113;
            try {
                strMSG113 = txt.substring(0, 113);
            } catch (StringIndexOutOfBoundsException sex) {
                strMSG113 = txt;
            }
            try {
                txt = ex.getMessage().substring(0, 76);
                if (txt.equals("Unable to connect to foreign data source: Can't connect to MySQL server on '")) {
                    String[] ip_server = ex.getMessage().split("'");
                    System.err.println("****************\n* MySQL no se pudo conectar con la tabla del servidor KRADAC -> " + ip_server[2] + "...\n****************");
                    log.info("[COD:" + code + "]MySQL no se pudo conectar con la tabla del servidor KRADAC -> [" + ip_server[2] + "]");
                    return false;
                } else if (txt.equals("Got error 10000 'Error on remote system: 2003: Can't connect to MySQL server")) {
                    String[] ip_server = ex.getMessage().split("'");
                    System.err.println("****************\n* MySQL no se pudo conectar con la tabla FEDERADA del servidor KRADAC -> " + ip_server[3] + "...\n****************");
                    log.info("[COD:" + code + "][Empresa: {}]MySQL no se pudo conectar con la tabla FEDERADA del servidor KRADAC -> [" + ip_server[3] + "]", Principal.sesion[1]);
                    return false;
                } else if (txt.substring(0, 64).equals("Unable to connect to foreign data source: Access denied for user")) {
                    String[] ip_server = ex.getMessage().split("'");
                    System.err.println("****************\n* NO hay permiso para insertar en el servidor KRADAC -> " + ip_server[1] + " --> " + ip_server[3] + "\n****************");
                    if (contador == 0) {
                        log.error("[COD:" + code + "]No se puede conectar [" + Principal.sesion[1] + "] a la BD tiene una IP no registrada: {}", ip_server[1] + " --> " + ip_server[3]);
                        contador++;
                    } else {
                        if (contador == 20000) { //Envia un mail cada 15 minutos despues de que sucede esto
                            log.error("[COD:" + code + "][Empresa: {}]NO hay permiso para insertar en el servidor KRADAC -> " + ip_server[1] + " --> " + ip_server[3], Principal.sesion[1]);
                            contador = 1;
                        } else {
                            contador++;
                        }
                    }
                    return false;
                } else if (txt.substring(0, 46).equals("Unable to connect to foreign data source: Host")) {
                    if (contador == 0) {
                        log.error("[COD:" + code + "]No se puede conectar [" + Principal.sesion[1] + "] a la BD tiene una IP no registrada: {}", ex.getMessage().split("'")[1]);
                        contador++;
                    } else {
                        if (contador == 20000) { //Envia un mail cada 15 minutos despues de que sucede esto
                            log.error("[COD:" + code + "]No se puede conectar [" + Principal.sesion[1] + "] a la BD tiene una IP no registrada: {}", ex.getMessage().split("'")[1]);
                            contador = 1;
                        } else {
                            //log.trace("No se puede conectar [" + Principal.sesion[1] + "] a la BD tiene una IP no registrada: {}", ex.getMessage().split("'")[1]);
                            contador++;
                        }
                    }
                    return false;
                } else {
                    txt = ex.getMessage().substring(0, 15);
                }
            } catch (StringIndexOutOfBoundsException sex) {
                txt = ex.getMessage().substring(0, 15);
            }
            if (ex.getMessage().equals("Got timeout reading communication packets")) {
                System.err.println("No hay Conexion a internet -> no se pueden guardar los datos en la tabla del servidor...");
                log.info("[COD:" + code + "]No hay Conexion a internet -> no se pueden guardar los datos en la tabla del servidor...");
                return false;
            } else if (ex.getMessage().equals("No operations allowed after statement closed.")) {
                System.err.println("****************\n* MySQL no se pudo conectar con la tabla del servidor, error al ejecutar la sentencia...\n****************");
                log.info("[COD:" + code + "]MySQL no se pudo conectar con la tabla del servidor, error al ejecutar la sentencia...");
                return false;
            } else if (strMSG113.equals("No operations allowed after connection closed.Connection was implicitly closed due to underlying exception/error:")) {
                log.info("[COD:" + code + "]Se desconectó el cable de RED del equipo...");
                return false;
            } else if (ex.getMessage().equals("Can't write; duplicate key in table 'server'")) {
                log.info("[COD:" + code + "]Ya está ingresado el registro en el servidor", ex);
                return true;
            } else {
                log.error("[COD:" + code + "][" + Principal.sesion[1] + "]", ex);
                return false;
            }
        } catch (NullPointerException ex) {
            return false;
        }
    }
    /**
     * Contar cuantas veces ha salido el mensaje de ip no registrada para enviar
     * un mail a KRADAC
     */
    int contador = 0;

    /**
     * Utilizar cuando de error de ResultSet cerrado por problemas de los hilos
     * @param sql
     * @return boolean
     */
    public boolean ejecutarSentenciaStatement2(String sql) {
        try {
            Statement st1 = (Statement) conexion.createStatement();

            log.trace("Ejecutar: {}", sql);

            int rta = st1.executeUpdate(sql);

            if (rta >= 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            String txt = ex.getMessage();
            int code = ex.getErrorCode();
            try {
                switch (ex.getErrorCode()) {
                    //Tabla rota
                    case 126:
                        repararTablaRota(ex.getMessage());
                        return false;
                    //tabla no creada
                    case 1146:
                        String[] texto = ex.getMessage().split("'");
                        try {
                            log.info("[" + Principal.sesion[1] + "][COD:" + ex.getErrorCode() + "] La tabla no esta creada: [" + texto[1] + "]");
                        } catch (ArrayIndexOutOfBoundsException aiob) {
                        }
                        return false;
                    //Numero de columnas a insertar no coincide
                    case 1136:
                        log.info("El numero de columnas a insertar no coincide "
                                + "con el numero de columnas de la tabla:{}", sql);
                        return false;
                    //Error de sintaxis en SQL
                    case 1064:
                        log.info("SQL Mal Formada:{}", sql, ex);
                        return false;
                    //Error de clave Primaria
                    case 1062:
                        log.info("Error de Clave Primaria -> Registro ya ingresado:{}", sql);
                        return false;
                    case 0:
                        log.info("[COD:" + code + "]Comunicación con la base datos falló -> ultimo paquete enviado mayor a 5ms...", ex);
                        return false;
                }
                txt = ex.getMessage().substring(0, 76);
                if (txt.equals("Unable to connect to foreign data source: Can't connect to MySQL server on '")) {
                    String[] ip_server = ex.getMessage().split("'");
                    System.err.println("****************\n* MySQL no se pudo conectar con la tabla del servidor KRADAC -> " + ip_server[2] + "...\n****************");
                    log.info("[COD " + code + "]MySQL no se pudo conectar con la tabla del servidor KRADAC -> [" + ip_server[2] + "]");
                    return false;
                } else if (txt.equals("Got error 10000 'Error on remote system: 2003: Can't connect to MySQL server")) {
                    String[] ip_server = ex.getMessage().split("'");
                    System.err.println("****************\n* MySQL no se pudo conectar con la tabla FEDERADA del servidor KRADAC -> " + ip_server[3] + "...\n****************");
                    log.error("[COD " + code + "][Empresa: {}]MySQL no se pudo conectar con la tabla FEDERADA del servidor KRADAC -> " + ip_server[3] + "...", Principal.sesion[1], ex);
                    return false;
                } else if (txt.substring(0, 64).equals("Unable to connect to foreign data source: Access denied for user")) {
                    String[] ip_server = ex.getMessage().split("'");
                    System.err.println("****************\n* NO hay permiso para insertar en el servidor KRADAC -> " + ip_server[1] + " --> " + ip_server[3] + "\n****************");
                    if (contador == 0) {
                        log.error("[COD " + code + "]No se puede conectar [" + Principal.sesion[1] + "] a la BD tiene una IP no registrada: {}", ip_server[1] + " --> " + ip_server[3]);
                        contador++;
                    } else {
                        if (contador == 20000) { //Envia un mail cada 15 minutos despues de que sucede esto
                            log.error("[COD " + code + "][Empresa: {}]NO hay permiso para insertar en el servidor KRADAC -> " + ip_server[1] + " --> " + ip_server[3], Principal.sesion[1]);
                            contador = 1;
                        } else {
                            contador++;
                        }
                    }
                    return false;
                } else if (txt.substring(0, 46).equals("Unable to connect to foreign data source: Host")) {
                    if (contador == 0) {
                        log.error("[COD " + code + "]No se puede conectar [" + Principal.sesion[1] + "] a la BD tiene una IP no registrada: {}", ex.getMessage().split("'")[1]);
                        contador++;
                    } else {
                        if (contador == 20000) { //Envia un mail cada 15 minutos despues de que sucede esto
                            log.error("[COD " + code + "]No se puede conectar [" + Principal.sesion[1] + "] a la BD tiene una IP no registrada: {}", ex.getMessage().split("'")[1]);
                            contador = 1;
                        } else {
                            //log.trace("No se puede conectar [" + Principal.sesion[1] + "] a la BD tiene una IP no registrada: {}", ex.getMessage().split("'")[1]);
                            contador++;
                        }
                    }
                    return false;
                } else {
                    txt = ex.getMessage().substring(0, 15);
                }
            } catch (StringIndexOutOfBoundsException sex) {
                txt = ex.getMessage().substring(0, 15);
            }
            try {
                if (ex.getMessage().equals("Got timeout reading communication packets")) {
                    System.err.println("No hay Conexion a internet -> no se pueden guardar los datos en la tabla del servidor...");
                    log.info("[COD " + code + "]No hay Conexion a internet -> no se pueden guardar los datos en la tabla del servidor...");
                    return false;
                } else if (ex.getMessage().equals("No operations allowed after statement closed.")) {
                    System.err.println("****************\n* MySQL no se pudo conectar con la tabla del servidor, error al ejecutar la sentencia...\n****************");
                    log.info("[COD " + code + "]MySQL no se pudo conectar con la tabla del servidor, error al ejecutar la sentencia...");
                    return false;
                } else if (ex.getMessage().substring(0, 113).equals("No operations allowed after connection closed.Connection was implicitly closed due to underlying exception/error:")) {
                    log.info("[COD " + code + "]Se desconectó el cable de RED del equipo...");
                    reconectarBaseDatos();
                    return false;
                } else if (ex.getMessage().equals("Can't write; duplicate key in table 'server'")) {
                    log.info("[COD " + code + "]Ya está ingresado el registro en el servidor", ex);
                    return true;
                } else {
                    log.error("[COD: {}][" + Principal.sesion[1] + "]", ex.getErrorCode(), ex);
                    return false;
                }
            } catch (StringIndexOutOfBoundsException sex) {
                log.error("[COD: {}][" + Principal.sesion[1] + "]", ex.getErrorCode(), ex);
                return false;
            }
        }
    }

    public boolean ejecutarSentenciaActualizarServidorKradac(String sql) {
        try {
            Statement st1 = (Statement) conexion.createStatement();

            log.trace("Ejecutar: {}", sql);

            int rta = st1.executeUpdate(sql);

            if (rta >= 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            String txt = ex.getMessage();
            int code = ex.getErrorCode();
            try {
                txt = ex.getMessage().substring(0, 76);
                if (txt.equals("Unable to connect to foreign data source: Can't connect to MySQL server on '")) {
                    String[] ip_server = ex.getMessage().split("'");
                    System.err.println("****************\n* MySQL no se pudo conectar con la tabla del servidor KRADAC -> " + ip_server[2] + "...\n****************");
                    log.info("[COD:" + code + "]MySQL no se pudo conectar con la tabla del servidor KRADAC -> [" + ip_server[2] + "]");
                    return false;
                } else if (txt.equals("Got error 10000 'Error on remote system: 2003: Can't connect to MySQL server")) {
                    String[] ip_server = ex.getMessage().split("'");
                    System.err.println("****************\n* MySQL no se pudo conectar con la tabla FEDERADA del servidor KRADAC -> " + ip_server[3] + "...\n****************");
                    log.error("[COD:" + code + "][Empresa: {}]MySQL no se pudo conectar con la tabla FEDERADA del servidor KRADAC -> " + ip_server[3] + "...", Principal.sesion[1], ex);
                    return false;
                } else if (code == 1429) {
                    String[] ip_server = ex.getMessage().split("'");
                    if (contador == 0) {
                        try {
                            log.error("[COD:" + code + "]No se puede conectar [" + Principal.sesion[1] + "] a la BD tiene una IP no registrada: {}", ip_server[1] + " --> " + ip_server[3]);
                        } catch (ArrayIndexOutOfBoundsException aiobe) {
                            log.error("[COD:" + code + "]No se puede conectar [" + Principal.sesion[1] + "] a la BD tiene una IP no registrada: {}", ip_server[1]);
                        }
                        contador++;
                    } else {
                        if (contador == 20000) { //Envia un mail cada 15 minutos despues de que sucede esto
                            log.error("[COD:" + code + "][Empresa: {}]NO hay permiso para insertar en el servidor KRADAC -> " + ip_server[1] + " --> " + ip_server[3], Principal.sesion[1]);
                            contador = 1;
                        } else {
                            contador++;
                        }
                    }
                    return false;
                } else if (txt.substring(0, 46).equals("Unable to connect to foreign data source: Host")) {
                    if (contador == 0) {
                        log.error("[COD:" + code + "]No se puede conectar [" + Principal.sesion[1] + "] a la BD tiene una IP no registrada: {}", ex.getMessage().split("'")[1]);
                        contador++;
                    } else {
                        if (contador == 20000) { //Envia un mail cada 15 minutos despues de que sucede esto
                            log.error("[COD:" + code + "]No se puede conectar [" + Principal.sesion[1] + "] a la BD tiene una IP no registrada: {}", ex.getMessage().split("'")[1]);
                            contador = 1;
                        } else {
                            contador++;
                        }
                    }
                    return false;
                } else {
                    txt = ex.getMessage().substring(0, 15);
                }
            } catch (StringIndexOutOfBoundsException sex) {
                txt = ex.getMessage().substring(0, 15);
            }
            try {
                if (ex.getMessage().substring(0, 5).equals("Table")) {
                    String[] texto = ex.getMessage().split("'");
                    try {
                        if (texto[2].equals(" doesn") && texto[3].equals("t exist")) {
                            log.info("[COD:" + code + "]La tabla no esta creada: [" + texto[1] + "]");
                        }
                    } catch (ArrayIndexOutOfBoundsException aiob) {
                    }
                    return false;
                } else if (ex.getMessage().equals("Got timeout reading communication packets")) {
                    System.err.println("No hay Conexion a internet -> no se pueden guardar los datos en la tabla del servidor...");
                    log.info("[COD:" + code + "]No hay Conexion a internet -> no se pueden guardar los datos en la tabla del servidor...");
                    return false;
                } else if (ex.getMessage().equals("No operations allowed after statement closed.")) {
                    System.err.println("****************\n* MySQL no se pudo conectar con la tabla del servidor, error al ejecutar la sentencia...\n****************");
                    log.info("[COD:" + code + "]MySQL no se pudo conectar con la tabla del servidor, error al ejecutar la sentencia...");
                    return false;
                } else if (ex.getMessage().substring(0, 113).equals("No operations allowed after connection closed.Connection was implicitly closed due to underlying exception/error:")) {
                    log.info("[COD:" + code + "]Se desconectó el cable de RED del equipo...");
                    return false;
                } else if (ex.getMessage().equals("Can't write; duplicate key in table 'server'")) {
                    log.info("[COD:" + code + "]Ya está ingresado el registro en el servidor", ex);
                    return true;
                } else {
                    log.error("[COD:" + code + "]", ex);
                    return false;
                }
            } catch (StringIndexOutOfBoundsException siobex) {
                return false;
            }
        }
    }

    public ResultSet ejecutarConsultaUnDatoStatement2(String sql) {
        try {
            Statement st1 = (Statement) conexion.createStatement();

            log.trace("Consultar: {}", sql);

            rsAux = st1.executeQuery(sql);
            rsAux.next();

            return rsAux;
        } catch (SQLException ex) {
            String msg = ex.getMessage().substring(0, 113);
            int code = ex.getErrorCode();
            if (msg.equals("No operations allowed after connection closed.Connection was implicitly closed due to underlying exception/error:")) {
                log.info("[COD {}]Se desconectó el cable de RED del equipo...", code);
                reconectarBaseDatos();
            } else {
                log.info("[COD {}]", code, ex);
            }
        }
        return null;
    }

    /**
     * Ejecuta una sentencia en la base esta puede ser de INSERT, UPDATE O
     * DELETE el la misma solo que no presenta los errores en la base de datos
     * ni imprime la ejecucion del sql
     * @param sql - Sentencias INSERT, UPDATE, DELETE
     * @return int - confirmacion del resultado 1 valido || 0 invalido
     */
    public boolean ejecutarSentenciaHilo(String sql) {
        try {
            st = (Statement) conexion.createStatement();
            int rta = st.executeUpdate(sql);
            if (rta >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Obtiene el numero de Vehiculos que tiene la empresa
     * @return int
     */
    public int getNumeroCarerasPorVehiculo(String n_unidad, int id_turno, String user) {
        try {
            int unidad = Integer.parseInt(n_unidad);
            String sql = "SELECT SF_OBTENER_CARRERAS_TAXI('" + unidad + "'," + id_turno + ",'" + user + "')";
            rs = ejecutarConsultaUnDato(sql);
            int intNumero = Integer.parseInt(rs.getString(1));
            return intNumero;
        } catch (SQLException ex) {
            log.info("[COD {}]ResultSet closed - getNumeroCarerasPorVehiculo", ex.getErrorCode());
        }
        return 0;
    }

    /**
     * Trae la cadena de conexion a la base de datos
     * @return Connection
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Cierra la conexion con la base de datos
     * @return Connection
     * @throws SQLException
     */
    public void CerrarConexion() {
        try {
            st.close();
            rs.close();
            conexion.close();
            log.info("Cerrar conexion BD [OK]");
        } catch (SQLException ex) {
            log.info("[COD {}]Cerrar conexion BD [FALLO]", ex.getErrorCode(), ex);
        } catch (NullPointerException ex) {
            log.info("NO está abierta la base de datos... [{}]", Principal.sesion[1]);
        }
    }

    /**
     * Comprueba si una unidad existe
     * @param intUnidad
     * @return boolean -> true si existe
     */
    public boolean validarUnidad(int unidad) {
        try {
            String sql = "SELECT N_UNIDAD FROM VEHICULOS WHERE N_UNIDAD = '" + unidad + "'";
            rs = ejecutarConsultaUnDato(sql);
            int intNumero = rs.getInt("N_UNIDAD");

            if (unidad == intNumero) {
                return true;
            }
        } catch (SQLException ex) {
            return false;
        }
        return false;
    }

    /**
     * Insertar Despacho Cliente
     * @param des
     * @return boolean confirmacion del resultado 1 valido || 0 invalido
     */
    public boolean InsertarDespachoCliente(Despachos des, boolean estadoDespacho) {

        estadoUnidad = getEstadoUnidad(des.getIntUnidad());

        if (estadoUnidad != null) {
            if (des.getIntUnidad() == 0) {
                estadoUnidad = "C"; //cancelado
            }

            if (des.getStrTelefono() == null) {
                des.setStrTelefono("");
            }
            if (des.getStrNota() == null) {
                des.setStrNota("");
            }

            des.setStrFecha(verFechaDespacho(des.getStrHora()));

            if (estadoUnidad.equals("ASI") || estadoUnidad.equals("C")) {
                String sql = "CALL SP_INSERTAR_DESPACHOS("
                        + des.getIntCodigo() + ","
                        + "'" + des.getStrFecha() + "',"
                        + "'" + des.getStrHora() + "',"
                        + "'" + des.getStrTelefono() + "',"
                        + "'" + des.getStrNombre() + "',"
                        + "'" + des.getStrDireccion() + "',"
                        + "'" + des.getStrBarrio() + "',"
                        + des.getIntMinutos() + ","
                        + des.getIntUnidad() + ","
                        + des.getIntAtraso() + ","
                        + "'" + des.getStrNota() + "',"
                        + "'" + des.getStrEstado() + "',"
                        + des.getIntID_Turno() + ","
                        + "'" + des.getStrUsuario() + "'"
                        + ")";
                return ejecutarSentencia(sql);
            }
        }

        return false;
    }

    /**
     * Se emplea para saber si el despacho ingreso el dia actual o el anterior
     * para tener una fecha cooerente con la hora del despacho.
     * Se compra si la hora del despacho es menor a la hora actual, entonces se
     * tiene que restar en 1 dia a la fecha actual para insertar el despacho en
     * la base de datos con la fecha coherente, esto es para evitar el null.
     * @param horaDespacho
     * @return String
     */
    private String verFechaDespacho(String horaDespacho) {
        String[] arrHoraDes = horaDespacho.split(":");
        String[] arrHoraAct = funciones.getHora().split(":");
        String fechaActual = funciones.getFecha();
        if (Integer.parseInt(arrHoraDes[0]) > Integer.parseInt(arrHoraAct[0])) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateFormat.parse(fechaActual));
                cal.add(Calendar.DATE, -1);
                return dateFormat.format(cal.getTime());
            } catch (ParseException ex) {
                java.util.logging.Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fechaActual;
    }

    /**
     * Retorna el estado de la ultima unidad intentada despachar
     * @return String
     */
    public String getEstadoUnidad() {
        try {
            if (!estadoUnidad.equals("")) {
                String sql = "SELECT ETIQUETA FROM CODESTTAXI WHERE ID_CODIGO = '" + estadoUnidad + "'";
                rs = ejecutarConsultaUnDato(sql);
                return rs.getString("ETIQUETA");
            }
        } catch (SQLException ex) {
            return null;
        } catch (NullPointerException ex) {
            return null;
        }
        return null;
    }

    /**
     * Retorna la etiqueta de un estado dependiendo del codigo ques e envie
     * @return String
     */
    public String getEtiquetaEstadoUnidad(String codigo) {
        try {
            String sql = "SELECT ETIQUETA FROM CODESTTAXI WHERE ID_CODIGO = '" + codigo + "'";
            rs = ejecutarConsultaUnDato(sql);
            return rs.getString("ETIQUETA");
        } catch (SQLException ex) {
        }
        return null;
    }

    /**
     * Retorna el codigo de un etiqueta de estado segun el nombre de la etiqueta
     * @return String
     */
    public String getCodigoEtiquetaEstadoUnidad(String nombre) {
        try {
            String sql = "SELECT ID_CODIGO FROM CODESTTAXI WHERE ETIQUETA = '" + nombre + "'";
            rs = ejecutarConsultaUnDatoStatement2(sql);
            String codEstado = rs.getString("ID_CODIGO");
            return codEstado;
        } catch (SQLException ex) {
        } catch (NullPointerException ex) {
            return "NO HAY CODIGO...";
        }
        return null;
    }

    /**
     * Obtiene el ultimo estadod e la unidad que se le envie
     * @param unidad
     * @return String
     */
    public String getEstadoUnidad(int unidad) {
        String sql = "SELECT SF_ESTADO_UNIDAD(" + unidad + ")";
        try {
            ResultSet r = ejecutarConsultaUnDato(sql);
            return r.getString(1);
        } catch (SQLException ex) {
        } catch (UnsupportedOperationException ex) {
        }
        return null;
    }

    /**
     * Retorna el ultimo codigo a utilizar para un cliente
     * @return String
     * @throws SQLException
     */
    public String generarCodigo() throws SQLException {
        String sql = "SELECT DISTINCT CODIGO FROM CLIENTES ORDER BY CODIGO ASC";
        ResultSet r = ejecutarConsulta(sql);
        log.trace(sql);
        int i = 1;
        int j = 0;
        boolean inicial = true;
        int cod = 0;
        while (r.next()) {
            cod = r.getInt("CODIGO");
            /**
             * hace una sola vez la comprobacion si hay cero al inicio de la
             * lista true es que si tiene un 0
             */
            if (j == 0) {
                if (cod == 0) {
                    i = 0;
                    inicial = true;
                } else {
                    i = 1;
                    inicial = false;
                }
                j++;
            }
            if (inicial) {

                if (i == cod) {
                    i++;
                } else {
                    return "" + (i);
                }
            } else {
                if (i == cod) {
                    i++;
                } else {
                    return "" + (i);
                }
            }
        }

        return "" + i;
    }

    /**
     * Inserta un cliente o actualiza dependiendo del caso, si ya esta ingresado
     * el cliente en el sistema solo actualiza los datos del cliente
     * @param Despachos
     * @return confirmacion del resultado 1 valido || 0 invalido
     */
    public boolean InsertarCliente(Despachos des) {
        boolean validarCliente = validarTelefonoCliente(des.getStrTelefono(), des.getIntCodigo());
        boolean codigoEs0 = des.getIntCodigo() == 0;
        if (!validarCliente && codigoEs0) {
            /**
             * Hace esto si el codigo es 0 y el cliente NO esta ingresado en la
             * base de datos
             */
            String sql = "INSERT INTO CLIENTES(TELEFONO,CODIGO,NOMBRE_APELLIDO_CLI,DIRECCION_CLI, SECTOR, NUM_CASA_CLI,LATITUD,LONGITUD,INFOR_ADICIONAL)"
                    + " VALUES("
                    + "'" + des.getStrTelefono() + "',"
                    + des.getIntCodigo() + ","
                    + "'" + des.getStrNombre() + "',"
                    + "'" + des.getStrDireccion() + "',"
                    + "'" + des.getStrBarrio() + "',"
                    + "'" + des.getStrNumeroCasa() + "',"
                    + des.getLatitud() + ","
                    + des.getLongitud() + ","
                    + "'" + des.getStrReferecia() + "'"
                    + ")";

            return ejecutarSentencia(sql);
        } else if (!validarCliente && !codigoEs0) {
            /**
             * Hace si el codigo NO es 0 y el cliente NO esta ingresado
             * en la base de datos
             */
            String sql = "INSERT INTO CLIENTES(TELEFONO,CODIGO,NOMBRE_APELLIDO_CLI,DIRECCION_CLI, SECTOR, NUM_CASA_CLI,LATITUD,LONGITUD,INFOR_ADICIONAL)"
                    + " VALUES("
                    + "'" + des.getStrTelefono() + "',"
                    + des.getIntCodigo() + ","
                    + "'" + des.getStrNombre() + "',"
                    + "'" + des.getStrDireccion() + "',"
                    + "'" + des.getStrBarrio() + "',"
                    + "'" + des.getStrNumeroCasa() + "',"
                    + des.getLatitud() + ","
                    + des.getLongitud() + ","
                    + "'" + des.getStrReferecia() + "'"
                    + ")";

            return ejecutarSentencia(sql);
        }
        return false;
    }

    /**
     * Actualiza los campos de un cliente ingresado
     * @param des
     * @param codigo
     * @return boolean
     */
    public boolean ActualizarClienteConTelefono(Despachos des, String telefono) {
        String sql = "UPDATE CLIENTES SET "
                + "CODIGO=" + des.getIntCodigo() + ","
                + "NOMBRE_APELLIDO_CLI=" + "'" + des.getStrNombre() + "',"
                + "DIRECCION_CLI=" + "'" + des.getStrDireccion() + "',"
                + "SECTOR=" + "'" + des.getStrBarrio() + "',"
                + "NUM_CASA_CLI=" + "'" + des.getStrNumeroCasa() + "',"
                + "LATITUD=" + des.getLatitud() + ","
                + "LONGITUD=" + des.getLongitud() + ","
                + "INFOR_ADICIONAL=" + "'" + des.getStrReferecia() + "' "
                + "WHERE TELEFONO='" + telefono + "'";

        return ejecutarSentencia(sql);
    }

    /**
     * Inserta un cliente desde el menu de opciones del sistema
     * @param Despachos
     * @return confirmacion del resultado 1 valido || 0 invalido
     */
    public boolean InsertarClienteMenu(Despachos des) {
        boolean validarCliente = validarTelefonoCliente(des.getStrTelefono(), des.getIntCodigo());

        if (!validarCliente) {
            String sql = "INSERT INTO CLIENTES(TELEFONO,CODIGO,NOMBRE_APELLIDO_CLI,DIRECCION_CLI, SECTOR, NUM_CASA_CLI,LATITUD,LONGITUD,INFOR_ADICIONAL)"
                    + " VALUES("
                    + "'" + des.getStrTelefono() + "',"
                    + des.getIntCodigo() + ","
                    + "'" + des.getStrNombre() + "',"
                    + "'" + des.getStrDireccion() + "',"
                    + "'" + des.getStrBarrio() + "',"
                    + "'" + des.getStrNumeroCasa() + "',"
                    + des.getLatitud() + ","
                    + des.getLongitud() + ","
                    + "'" + des.getStrReferecia() + "'"
                    + ")";

            return ejecutarSentencia(sql);
        } else {
            return false;
        }
    }

    /**
     * Comprueba si ese numero de telefono ya esta ingresado para otro cliente
     * true si esta el telefono ingresado para el codigo ques e envia
     * @param telefono
     * @return String
     */
    public boolean validarTelefonoCliente(String telefono, int codigo) {
        try {
            String sql = "SELECT CODIGO FROM CLIENTES WHERE TELEFONO='" + telefono + "'";
            rsAux = ejecutarConsultaUnDato(sql);
            int cod = rsAux.getInt("CODIGO");
            if (codigo == cod) {
                return true;
            }
        } catch (SQLException ex) {
            return false;
        }
        return false;
    }

    /**
     * Actualiza los campos de un cliente ingresado
     * @param des
     * @param codigo
     * @return boolean
     */
    public boolean ActualizarClienteCod(Despachos des) {
        String sql = "UPDATE CLIENTES SET "
                + "TELEFONO=" + "'" + des.getStrTelefono() + "',"
                + "NOMBRE_APELLIDO_CLI=" + "'" + des.getStrNombre() + "',"
                + "DIRECCION_CLI=" + "'" + des.getStrDireccion() + "',"
                + "SECTOR=" + "'" + des.getStrBarrio() + "',"
                + "NUM_CASA_CLI=" + "'" + des.getStrNumeroCasa() + "',"
                + "LATITUD=" + des.getLatitud() + ","
                + "LONGITUD=" + des.getLongitud() + ","
                + "INFOR_ADICIONAL=" + "'" + des.getStrReferecia() + "' "
                + "WHERE CODIGO=" + des.getIntCodigo();

        return ejecutarSentencia(sql);
    }

    /**
     * Actualiza los datos de un cliente a partir de un telefono
     * @param des
     * @return boolean
     */
    public boolean ActualizarClienteTel(Despachos des) {
        String sql = "UPDATE CLIENTES SET "
                + "CODIGO=" + des.getIntCodigo() + ","
                + "NOMBRE_APELLIDO_CLI=" + "'" + des.getStrNombre() + "',"
                + "DIRECCION_CLI=" + "'" + des.getStrDireccion() + "',"
                + "SECTOR=" + "'" + des.getStrBarrio() + "',"
                + "NUM_CASA_CLI=" + "'" + des.getStrNumeroCasa() + "',"
                + "LATITUD=" + des.getLatitud() + ","
                + "LONGITUD=" + des.getLongitud() + ","
                + "INFOR_ADICIONAL=" + "'" + des.getStrReferecia() + "' "
                + "WHERE TELEFONO='" + des.getStrTelefono() + "'";

        return ejecutarSentencia(sql);
    }

    /**
     * Actualiza los datos del un cliente a partir de un nombre y direccion
     * @param despacho
     * @return booelan true si se actualiza
     */
    public boolean ActualizarClientePorNombre(Despachos des) {
        String sql = "UPDATE CLIENTES SET "
                + "CODIGO=" + des.getIntCodigo() + ","
                + "TELEFONO=" + "'" + des.getStrTelefono() + "',"
                + "SECTOR=" + "'" + des.getStrBarrio() + "',"
                + "NUM_CASA_CLI=" + "'" + des.getStrNumeroCasa() + "',"
                + "LATITUD=" + des.getLatitud() + ","
                + "LONGITUD=" + des.getLongitud() + ","
                + "INFOR_ADICIONAL=" + "'" + des.getStrReferecia() + "' "
                + "WHERE NOMBRE_APELLIDO_CLI='" + des.getStrNombre() + "' AND DIRECCION_CLI='" + des.getStrDireccion() + "'";

        return ejecutarSentencia(sql);
    }

    /**
     * Devuelve todos los registros de conductores
     * que coincidan con el parámetro enviado
     * @param parametro Puede ser cédula o nombre
     * @param id  Identifica si es cedula (0) o nombre (1)
     * @return ArrayList<String[]> con los resultados encontrados
     * null si no encuentrada ningull
     */
    public ArrayList<String[]> buscarConductores(String parametro, int id) {

        ArrayList<String[]> rta = new ArrayList();

        String sql;
        if (id == 0) {
            sql = "SELECT CEDULA_CONDUCTOR, NOMBRE_APELLIDO_CON, DIRECCION_CON,"
                    + " NUM_CASA_CON, TIPO_SANGRE, ESTADO_CIVIL, CONYUGE,"
                    + " MAIL, FOTO FROM CONDUCTORES WHERE CEDULA_CONDUCTOR "
                    + "= '" + parametro + "'";
        } else {
            sql = "SELECT CEDULA_CONDUCTOR, NOMBRE_APELLIDO_CON, DIRECCION_CON,"
                    + " NUM_CASA_CON, TIPO_SANGRE, ESTADO_CIVIL, CONYUGE, MAIL,"
                    + " FOTO FROM CONDUCTORES WHERE NOMBRE_APELLIDO_CON LIKE "
                    + "'%" + parametro.toUpperCase() + "%'";
        }

        ResultSet res = ejecutarConsulta(sql);
        try {
            while (res.next()) {
                String[] aux = new String[9];
                aux[0] = res.getString("CEDULA_CONDUCTOR");
                aux[1] = res.getString("NOMBRE_APELLIDO_CON");
                aux[2] = res.getString("DIRECCION_CON");
                aux[3] = res.getString("NUM_CASA_CON");
                aux[4] = res.getString("TIPO_SANGRE");
                aux[5] = res.getString("ESTADO_CIVIL");
                aux[6] = res.getString("CONYUGE");
                aux[7] = res.getString("MAIL");
                aux[8] = res.getString("FOTO");
                rta.add(aux);
            }
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }
        return rta;
    }

    /**
     * Devuelve todos los registros de vehiculos
     * que coincidan con el parámetro enviado
     * @param parametro Puede ser cédula o nombre
     * @param id  Identifica si es placa (0) o num unidad (1)
     * @return ArrayList<String[]> con los resultados encontrados
     * null si no encuentrada ninguno
     */
    public ArrayList<String[]> buscarVehiculos(String parametro, int id) {

        ArrayList<String[]> rta = new ArrayList();

        String sql;
        if (id == 0) {
            sql = "SELECT PLACA, N_UNIDAD, ID_EMPRESA, ID_CON, CONDUCTOR_AUX, "
                    + " MODELO, ANIO,PROPIETARIO, INF_ADICIONAL, "
                    + "IMAGEN, MARCA, NUM_MOTOR, NUM_CHASIS, REG_MUNICIPAL, SOAT FROM VEHICULOS WHERE PLACA LIKE '" + parametro + "%'";
        } else {
            sql = "SELECT PLACA, N_UNIDAD, ID_EMPRESA, ID_CON, CONDUCTOR_AUX, "
                    + "MODELO, ANIO,PROPIETARIO, INF_ADICIONAL, "
                    + "IMAGEN, MARCA, NUM_MOTOR, NUM_CHASIS, REG_MUNICIPAL, SOAT FROM VEHICULOS WHERE N_UNIDAD = '" + parametro + "'";
        }

        ResultSet res = ejecutarConsulta(sql);
        try {
            while (res.next()) {
                String[] aux = new String[15];
                aux[0] = res.getString("PLACA");
                aux[1] = res.getString("N_UNIDAD");
                aux[2] = res.getString("ID_EMPRESA");
                aux[3] = res.getString("ID_CON");
                aux[4] = res.getString("CONDUCTOR_AUX");
                aux[5] = res.getString("REG_MUNICIPAL");
                aux[6] = res.getString("MODELO");
                aux[7] = res.getString("ANIO");
                aux[8] = res.getString("PROPIETARIO");
                aux[9] = res.getString("INF_ADICIONAL");
                aux[10] = res.getString("IMAGEN");
                aux[11] = res.getString("MARCA");
                aux[12] = res.getString("NUM_MOTOR");
                aux[13] = res.getString("NUM_CHASIS");
                aux[14] = res.getString("SOAT");
                rta.add(aux);
            }
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }
        return rta;
    }

    /**
     * Comprueba si la placa ya existe en la BD
     * @param plc
     * @return
     */
    public boolean placaExiste(String plc) {
        try {
            String sql = "SELECT COUNT(PLACA)  AS NUM FROM VEHICULOS WHERE PLACA = '" + plc + "'";

            rs = ejecutarConsultaUnDato(sql);
            int intNumero = Integer.parseInt(rs.getString("NUM"));

            if (intNumero > 0) {
                return true;
            }
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }
        return false;
    }

    /**
     * Elimina el conductor que coincida con el parámetro
     * enviado
     * @param ced  Cedula a buscar
     * @return
     */
    public boolean eliminarConductor(String ced) {
        String sql = "DELETE FROM CONDUCTORES WHERE CEDULA_CONDUCTOR = '" + ced + "'";
        return ejecutarSentencia(sql);
    }

    /**
     * Elimina el vehiculo que coincida con el parámetro
     * enviado
     * @param placa  Placa que se buscará
     * @return
     */
    public boolean eliminarVehiculo(String placa) {
        String sql = "DELETE FROM VEHICULOS WHERE PLACA = '" + placa + "'";
        return ejecutarSentencia(sql);
    }

    /**
     * Obtiene el numero de Vehiculos que tiene la empresa
     * @return int
     */
    public String getNombreConductor(int id) {
        try {
            String sql = "SELECT NOMBRE_APELLIDO_CON FROM CONDUCTORES WHERE ID_CON = " + id + "";
            rs = ejecutarConsultaUnDato(sql);
            String nombre = rs.getString(1);
            return nombre;

        } catch (SQLException ex) {
            return null;
        }
    }

    /**
     * Determina si existe una unidad diferente a la actual
     * asignada a una placa.
     * @param placa
     * @param nUnidad
     * @return true si existe de lo contrario false
     */
    public boolean existeUnidad(String placa, int nUnidad) {
        try {
            String sql = "SELECT COUNT(PLACA) AS CANTIDAD FROM VEHICULOS "
                    + "WHERE N_UNIDAD = " + nUnidad + " AND PLACA <>'" + placa + "'";
            rs = ejecutarConsultaUnDato(sql);
            int existe = rs.getInt("CANTIDAD");
            if (existe > 0) {
                return true;
            }
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }

        return false;
    }

    /**
     * Verifica si un conductor ya posee una unidad asignada
     * diferente a la propia
     * 
     * @param nameCond  Nombre de Conductor
     * @param placa
     * @return
     */
    public boolean conductorAsignado(String nameCond, String placa) {
        try {
            String sql = "SELECT SF_CONDUCTOR_ASIGNADO('" + nameCond + "','" + placa + "') AS VALOR";
            rs = ejecutarConsultaUnDato(sql);
            int existe = rs.getInt("VALOR");
            if (existe > 0) {
                return true;
            }
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }
        return false;
    }

    /**
     * Actualizar un vehiculo
     * @param pl
     * @param numi
     * @param emp
     * @param con
     * @param conaux
     * @param model
     * @param an
     * @param pro
     * @param inf
     * @param img
     * @param mar
     * @param mot
     * @param cha
     * @param reg_municipal
     * @return boolean
     */
    public boolean actualizarVehiculo(String pl,
            int numi,
            String emp,
            String con,
            String conaux,
            String model,
            int an,
            String pro,
            String inf,
            String img,
            String mar,
            String mot,
            String cha,
            String reg_municipal,
            String soat) {

        String sql = "CALL SP_UPDATE_VEHICULO('"
                + pl + "',"
                + numi + ",'"
                + emp + "','"
                + con + "','"
                + conaux + "','"
                + model + "',"
                + an + ",'"
                + pro + "','"
                + inf + "','"
                + img + "','"
                + mar + "','"
                + mot + "','"
                + cha + "',"
                + reg_municipal + ",'"
                + soat
                + "')";

        return ejecutarSentencia(sql);

    }

    /**
     * Trae el Id de turno actual
     * @param validarTurno
     * @return int
     */
    public int getIdTurno(String validarTurno) {
        String[] hora = validarTurno.trim().split("-");
        try {
            String sql = "SELECT ID_TURNO FROM TURNOS WHERE HORA_INI ='" + hora[0] + "' AND HORA_FIN='" + hora[1] + "'";

            rs = ejecutarConsultaUnDato(sql);
            return Integer.parseInt(rs.getString("ID_TURNO"));
        } catch (SQLException ex) {
        }
        return -1;
    }

    /**
     * Recoje los datos de los datos depachados
     * @param usuario
     * @param id_turno
     * @param fecha
     * @return ArrayList<Despachos>
     */
    public ArrayList<Despachos> getDespachados(String usuario, int id_turno, String fecha) {
        ArrayList<Despachos> datos = new ArrayList<Despachos>();

        try {
            String sql = "SELECT DATE_FORMAT(HORA,'%H%:%i:%s') AS HORA, "
                    + "TELEFONO, "
                    + "COD_CLIENTE, "
                    + "NOMBRE_APELLIDO_CLI, "
                    + "DIRECCION_CLI, "
                    + "SECTOR, "
                    + "MINUTOS,N_UNIDAD,ATRASO,"
                    + "NOTA "
                    + "FROM ASIGNADOS "
                    + "WHERE FECHA='" + fecha + "' AND ESTADO='F' AND USUARIO='" + usuario + "' AND ID_TURNO=" + id_turno;
            rs = ejecutarConsulta(sql);

            while (rs.next()) {
                Despachos d = new Despachos();
                d.setStrHora(rs.getString("HORA"));
                d.setStrTelefono(rs.getString("TELEFONO"));
                d.setIntCodigo(rs.getInt("COD_CLIENTE"));
                d.setStrNombre(rs.getString("NOMBRE_APELLIDO_CLI"));
                d.setStrDireccion(rs.getString("DIRECCION_CLI"));
                d.setStrBarrio(rs.getString("SECTOR"));
                d.setIntMinutos(rs.getInt("MINUTOS"));
                d.setIntUnidad(rs.getInt("N_UNIDAD"));
                d.setIntAtraso(rs.getInt("ATRASO"));
                d.setStrNota(rs.getString("NOTA"));
                datos.add(d);
            }
            return datos;
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }
        return null;
    }

    /**
     * Obtiene la lista de despachados a partir de un numero de telefono
     * @param telefono
     * @param usuario
     * @param id_turno
     * @param fecha
     * @return ArrayList<Despachos>
     */
    public ArrayList<Despachos> buscarDespachadosPorTelefono(String telefono,
            String usuario,
            int id_turno,
            String fecha) {
        ArrayList<Despachos> datos = new ArrayList<Despachos>();

        try {
            String sql = "SELECT DATE_FORMAT(HORA,'%H%:%i:%s') AS HORA, "
                    + "TELEFONO, "
                    + "COD_CLIENTE, "
                    + "NOMBRE_APELLIDO_CLI, "
                    + "DIRECCION_CLI, "
                    + "SECTOR, "
                    + "MINUTOS,N_UNIDAD,ATRASO,"
                    + "NOTA "
                    + "FROM ASIGNADOS "
                    + "WHERE FECHA='" + fecha
                    + "' AND ESTADO='F' AND USUARIO='" + usuario
                    + "' AND ID_TURNO=" + id_turno
                    + " AND TELEFONO LIKE '" + telefono + "%'";
            rs = ejecutarConsulta(sql);

            while (rs.next()) {
                Despachos d = new Despachos();
                d.setStrHora(rs.getString("HORA"));
                d.setStrTelefono(rs.getString("TELEFONO"));
                d.setIntCodigo(rs.getInt("COD_CLIENTE"));
                d.setStrNombre(rs.getString("NOMBRE_APELLIDO_CLI"));
                d.setStrDireccion(rs.getString("DIRECCION_CLI"));
                d.setStrBarrio(rs.getString("SECTOR"));
                d.setIntMinutos(rs.getInt("MINUTOS"));
                d.setIntUnidad(rs.getInt("N_UNIDAD"));
                d.setIntAtraso(rs.getInt("ATRASO"));
                d.setStrNota(rs.getString("NOTA"));
                datos.add(d);
            }
            return datos;
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }
        return null;
    }

    /**
     * Obtiene la lista de despachados a partir de un nombre
     * @param telefono
     * @param usuario
     * @param id_turno
     * @param fecha
     * @return ArrayList<Despachos>
     */
    public ArrayList<Despachos> buscarDespachadosPorNombre(String nombre,
            String usuario,
            int id_turno,
            String fecha) {
        ArrayList<Despachos> datos = new ArrayList<Despachos>();

        try {
            String sql = "SELECT DATE_FORMAT(HORA,'%H%:%i:%s') AS HORA, "
                    + "TELEFONO, "
                    + "COD_CLIENTE, "
                    + "NOMBRE_APELLIDO_CLI, "
                    + "DIRECCION_CLI, "
                    + "SECTOR, "
                    + "MINUTOS,N_UNIDAD,ATRASO,"
                    + "NOTA "
                    + "FROM ASIGNADOS "
                    + "WHERE FECHA='" + fecha
                    + "' AND ESTADO='F' AND USUARIO='" + usuario
                    + "' AND ID_TURNO=" + id_turno
                    + " AND NOMBRE_APELLIDO_CLI LIKE '" + nombre + "%'";
            rs = ejecutarConsulta(sql);

            while (rs.next()) {
                Despachos d = new Despachos();
                d.setStrHora(rs.getString("HORA"));
                d.setStrTelefono(rs.getString("TELEFONO"));
                d.setIntCodigo(rs.getInt("COD_CLIENTE"));
                d.setStrNombre(rs.getString("NOMBRE_APELLIDO_CLI"));
                d.setStrDireccion(rs.getString("DIRECCION_CLI"));
                d.setStrBarrio(rs.getString("SECTOR"));
                d.setIntMinutos(rs.getInt("MINUTOS"));
                d.setIntUnidad(rs.getInt("N_UNIDAD"));
                d.setIntAtraso(rs.getInt("ATRASO"));
                d.setStrNota(rs.getString("NOTA"));
                datos.add(d);
            }
            return datos;
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }
        return null;
    }

    /**
     * Obtiene los datos de un cliente consultado por el nombre
     * @param nombre
     * @return ArrayList<Clientes>
     */
    public ArrayList<Clientes> buscarClientesPorNombre(String nombre) {
        ArrayList<Clientes> datos = new ArrayList<Clientes>();

        try {
            String sql = "SELECT "
                    + "NOMBRE_APELLIDO_CLI,"
                    + "TELEFONO,"
                    + "CODIGO,"
                    + "DIRECCION_CLI,"
                    + "SECTOR,"
                    + "NUM_CASA_CLI,"
                    + "INFOR_ADICIONAL "
                    + "FROM CLIENTES "
                    + "WHERE NOMBRE_APELLIDO_CLI LIKE '" + nombre + "%'";

            rs = ejecutarConsultaStatement2(sql);

            while (rs.next()) {
                Clientes c = new Clientes();
                c.setNombre(rs.getString("NOMBRE_APELLIDO_CLI"));
                c.setTelefono(rs.getString("TELEFONO"));
                c.setCodigo(rs.getString("CODIGO"));
                c.setDireccion(rs.getString("DIRECCION_CLI"));
                c.setBarrio(rs.getString("SECTOR"));
                c.setN_casa(rs.getString("NUM_CASA_CLI"));
                c.setReferencia(rs.getString("INFOR_ADICIONAL"));
                datos.add(c);
            }
            return datos;
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }
        return null;
    }

    /**
     * Obtiene la lista de despachados a partir de un codigo
     * @param telefono
     * @param usuario
     * @param id_turno
     * @param fecha
     * @return ArrayList<Despachos>
     */
    public ArrayList<Despachos> buscarDespachadosPorCodigo(String codigo,
            String usuario,
            int id_turno,
            String fecha) {
        ArrayList<Despachos> datos = new ArrayList<Despachos>();

        try {
            String sql = "SELECT DATE_FORMAT(HORA,'%H%:%i:%s') AS HORA, "
                    + "TELEFONO, "
                    + "COD_CLIENTE, "
                    + "NOMBRE_APELLIDO_CLI, "
                    + "DIRECCION_CLI, "
                    + "SECTOR, "
                    + "MINUTOS,N_UNIDAD,ATRASO,"
                    + "NOTA "
                    + "FROM ASIGNADOS "
                    + "WHERE FECHA='" + fecha
                    + "' AND ESTADO='F' AND USUARIO='" + usuario
                    + "' AND ID_TURNO=" + id_turno
                    + " AND COD_CLIENTE LIKE '" + codigo + "%'";
            rs = ejecutarConsulta(sql);

            while (rs.next()) {
                Despachos d = new Despachos();
                d.setStrHora(rs.getString("HORA"));
                d.setStrTelefono(rs.getString("TELEFONO"));
                d.setIntCodigo(rs.getInt("COD_CLIENTE"));
                d.setStrNombre(rs.getString("NOMBRE_APELLIDO_CLI"));
                d.setStrDireccion(rs.getString("DIRECCION_CLI"));
                d.setStrBarrio(rs.getString("SECTOR"));
                d.setIntMinutos(rs.getInt("MINUTOS"));
                d.setIntUnidad(rs.getInt("N_UNIDAD"));
                d.setIntAtraso(rs.getInt("ATRASO"));
                d.setStrNota(rs.getString("NOTA"));
                datos.add(d);
            }
            return datos;
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }
        return null;
    }

    /**
     * Obtiene la hora de salida del turno actual
     * @param id_Turno
     * @return String
     */
    public String getHoraNuevoTurno(int id_Turno) throws SQLException {
        String turno;
        try {
            String sql = "SELECT HORA_INI FROM TURNOS WHERE ID_TURNO=" + (id_Turno + 1);
            rs = ejecutarConsultaUnDato(sql);
            turno = rs.getString("HORA_INI");
            return turno;
        } catch (SQLException ex) {
            String sql1 = "SELECT HORA_INI FROM TURNOS WHERE ID_TURNO=" + (1);
            rs = ejecutarConsultaUnDato(sql1);
            return rs.getString("HORA_INI");
        }
    }

    /**
     * Obtiene los datos de un cliente a partir de un numero de telefono
     * @param telefono
     * @return
     */
    public ResultSet getClientePorTelefono(String telefono) {
        String sql = "SELECT CODIGO,"
                + "NOMBRE_APELLIDO_CLI,"
                + "DIRECCION_CLI,"
                + "SECTOR"
                + " FROM CLIENTES WHERE TELEFONO='" + telefono + "'";
        return ejecutarConsultaUnDato(sql);
    }

    /**
     * Obtiene el color de estado de un taxi para cuando esta despachado
     * @return String
     */
    public String getColorEstadoDespachado() {
        String sql = "SELECT COLOR FROM CODESTTAXI WHERE ID_CODIGO = 'ASI'";
        rs = ejecutarConsultaUnDato(sql);
        try {
            return rs.getString("COLOR");
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }
        return null;
    }

    /**
     * Recupera la lista de usuarios del sistema
     * @return String[]
     */
    public String[] getUsuarios() {
        try {
            String[] datosCast;
            String sql = "SELECT USUARIO FROM USUARIOS WHERE USUARIO <> 'KRADAC'";
            rs = ejecutarConsulta(sql);
            ArrayList<String> listaUsuarios = new ArrayList<String>();
            while (rs.next()) {
                listaUsuarios.add(rs.getString("USUARIO"));
            }
            datosCast = new String[listaUsuarios.size()];
            datosCast = listaUsuarios.toArray(datosCast);
            return datosCast;
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }
        return null;
    }

    /**
     * Obtiene la lista de turnos de la base de datos
     * @return String[]
     */
    public String[] getTurnos() {
        try {
            String[] datosCast;
            String sql = "SELECT CONCAT(HORA_INI,' - ',HORA_FIN) AS HORA FROM TURNOS";
            rs = ejecutarConsulta(sql);
            ArrayList<String> listaTurnos = new ArrayList<String>();
            while (rs.next()) {
                listaTurnos.add(rs.getString("HORA"));
            }
            datosCast = new String[listaTurnos.size()];
            datosCast = listaTurnos.toArray(datosCast);
            return datosCast;
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }
        return null;
    }

    /**
     * Obtiene lo datos de usuario
     * @param usuario
     * @return ResultSet
     */
    public ResultSet getDatosUsuario(String usuario) {
        String sql = "SELECT * FROM USUARIOS WHERE USUARIO='" + usuario + "'";
        return rs = ejecutarConsultaUnDato(sql);
    }

    /**
     * Actualiza la tabla de usuarios con el turno en que entró por ultima vez el
     * usuario
     * @param user
     * @param id_turno
     */
    public boolean actualziarTurnoUsuario(String user, int id_turno) {
        String sql = "UPDATE USUARIOS SET ID_TURNO=" + id_turno + " WHERE USUARIO ='" + user + "'";
        return ejecutarSentencia(sql);
    }

    /**
     * Inserta un nuevo usuario en la tabla de usuarios
     * @param emp
     * @param user
     * @param pass
     * @param nombre
     * @param dir
     * @param tel
     * @param id_turno
     * @param estado
     * @param oper
     * @param ci
     * @return boolean
     */
    public boolean insertarUsuario(String emp, String user, String pass,
            String nombre, String dir, String tel, int id_turno,
            String estado, String oper, String ci) {
        String sql = "CALL SP_INSERTAR_USUARIOS('"
                + emp + "','"
                + user + "','"
                + pass + "','"
                + nombre + "','"
                + dir + "','"
                + tel + "',"
                + id_turno + ",'"
                + estado + "','"
                + oper + "','"
                + ci
                + "')";
        return ejecutarSentencia(sql);
    }

    /**
     * Actualiza los datos del usuario
     * @param emp
     * @param user
     * @param pass
     * @param nombre
     * @param dir
     * @param tel
     * @param id_turno
     * @param estado
     * @param oper
     * @param ci
     * @return boolean
     */
    public boolean actualizarUsuario(String emp, String user, String pass,
            String nombre, String dir, String tel, int id_turno,
            String estado, String oper, String ci) {
        String sql = "CALL SP_ACTUALIZAR_USUARIOS('"
                + emp + "','"
                + user + "','"
                + pass + "','"
                + nombre + "','"
                + dir + "','"
                + tel + "',"
                + id_turno + ",'"
                + estado + "','"
                + oper + "','"
                + ci
                + "')";
        return ejecutarSentencia(sql);
    }

    /**
     * Elimina el usuario de la base de datos
     * @param usuario
     */
    public void eliminarUsuario(String usuario) {
        String sql = "DELETE FROM USUARIOS WHERE USUARIO='" + usuario + "'";
        ejecutarSentencia(sql);
    }

    /**
     * Obtiene el nombre de la empresa
     * @param id
     * @return String
     */
    public String getEmpresa(String id) {
        try {
            String sql = "SELECT NOMBRE_EMP FROM EMPRESAS WHERE ID_EMPRESA = '" + id + "'";
            rs = ejecutarConsultaUnDato(sql);
            return rs.getString("NOMBRE_EMP");
        } catch (SQLException ex) {
            int code = ex.getErrorCode();
            if (ex.getMessage().equals("Illegal operation on empty result set.")) {
                log.info("[COD {}]Falta el nombre de la empresa en la base de datos...", code);
            } else {
                log.info("[COD {}]", code, ex);
            }
        }
        return null;
    }

    /**
     * Obtiene el telefono del usuario que envia el mail por si se desea informarle
     * algo o comunicarse con el
     * @param idUser
     * @return
     */
    public String getTelefonoUsuario(String idUser) {
        try {
            String sql = "SELECT TELEFONO FROM USUARIOS WHERE USUARIO='" + idUser + "'";
            rs = ejecutarConsultaUnDato(sql);
            return rs.getString("TELEFONO");
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }
        return "";
    }

    /**
     * Codigo de activación de modem para el equipo
     * @param id_empres
     * @return String
     */
    public String getComandoActivarModem(String id_empres) {
        try {
            String sql = "SELECT MODEM FROM EMPRESAS WHERE ID_EMPRESA='" + id_empres + "'";
            rs = ejecutarConsultaUnDato(sql);
            return rs.getString("MODEM");
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }
        return "";
    }

    /**
     * Insertar los datos de un cliente en la tabla de Posiciones para que este
     * pueda ser graficado en el mapa
     * @param cod_cliente
     * @param n_unidad
     * @param latitud
     * @param longitud
     * @return boolean
     */
    public boolean InsertarClienteMapa(int cod_cliente, String strNombre, String strBarrio, String strTelefono, double latitud, double longitud) {
        String sql = "INSERT INTO POSICION_CLIENTES(COD_CLIENTE,N_UNIDAD,NOMBRE,BARRIO,FONO,LATITUD,LONGITUD,FECHA,HORA) "
                + "VALUES("
                + cod_cliente + ","
                + null + ",'"
                + strNombre + "','"
                + strBarrio + "','"
                + strTelefono + "',"
                + latitud + ","
                + longitud + ",'"
                + funciones.getFecha() + "','"
                + funciones.getHora()
                + "')";
        return ejecutarSentencia(sql);
    }

    /**
     * Obtiene la latitud y longitud de un cliente a partir de un codigo de
     * cliente
     * @param codigo
     * @return ResultSet
     */
    public ResultSet obtenerLatLonCliente(int codigo) {
        String sql = "SELECT LATITUD, LONGITUD FROM CLIENTES WHERE CODIGO =" + codigo;
        return ejecutarConsultaUnDato(sql);
    }

    /**
     * Elimina un cliente que se encuentra dibujado en el mapa desde la tabla
     * posicion_clientes
     * @param cod_cliente
     * @param n_unidad
     * @return boolean
     */
    public boolean EliminarClienteMapa(int cod_cliente, int n_unidad) {
        String sql = "DELETE FROM POSICION_CLIENTES WHERE COD_CLIENTE=" + cod_cliente + " AND N_UNIDAD=" + n_unidad;
        return ejecutarSentencia(sql);
    }

    /**
     * Elimina un cliente del mapa si solo tiene el cod del cliente
     * @param cod_cliente
     * @return boolean
     */
    public boolean EliminarClienteMapa(int cod_cliente) {
        String sql = "DELETE FROM POSICION_CLIENTES WHERE COD_CLIENTE=" + cod_cliente;
        return ejecutarSentencia(sql);
    }

    /**
     * Truncar la tabla de posicione al finalizar para cuando se inicie la aplicacion
     * no exista ningun cliente en el mapa
     * @return boolean
     */
    public boolean TruncarTablaPosicionesCliente() {
        String sql = "DELETE FROM POSICION_CLIENTES";
        return ejecutarSentencia(sql);
    }

    /**
     * Actualiza la unidad a un cliente que se encuentra dibujado en el mapa
     * @param cliente
     * @param unidad
     */
    public void ActualizarUnidadClienteMapa(int cliente, int unidad) {
        String sql = "UPDATE POSICION_CLIENTES SET N_UNIDAD=" + unidad + " WHERE COD_CLIENTE=" + cliente;
        ejecutarSentencia(sql);
    }

    /**
     * comprueba si un cliente ya esta ingresado en la base de datos
     * retorna false si no esta el cliente en la base de datos
     * @param cod_cli
     * @return boolean
     */
    public boolean clienteExiste(String cod_cli) {
        if (!cod_cli.equals("")) {
            try {
                String sql = "SELECT CODIGO FROM CLIENTES WHERE CODIGO = " + cod_cli;
                rs = ejecutarConsultaUnDato(sql);
                if (cod_cli.equals(rs.getString("CODIGO"))) {
                    return true;
                }
            } catch (SQLException ex) {
                return false;
            }
        }
        return false;
    }

    /**
     * Insertar recorrido que viene del servidor KRADAC para guardarlo en la bd
     * y mostrar los taxis en el mapa
     * @param particion
     * @param unidad
     * @param empresa
     * @param latitud
     * @param longitud
     * @param fecha
     * @param hora
     * @param vel
     * @param G1
     * @param G2
     * @return boolean
     */
    public boolean InsertarRecorridoTaxiNuevo(
            String particion,
            String unidad,
            String empresa,
            String latitud,
            String longitud,
            String fecha,
            String hora,
            String vel,
            String G1,
            String G2) {
        String sql = "CALL SP_INSERTAR_RECORRIDOS('"
                + particion + "',"
                + Integer.parseInt(unidad) + ",'"
                + empresa + "',"
                + Double.parseDouble(latitud) + ","
                + Double.parseDouble(longitud) + ",'"
                + fecha + "','"
                + hora + "',"
                + Double.parseDouble(vel) + ","
                + G1 + ","
                + G2
                + ")";
        return ejecutarSentenciaHilo(sql);
    }

    /**
     * Obtiene el nombre de los estados de los vehiculos dependiendo del estado
     * @param codigo
     * @return
     */
    public String getNombreEstadoUnidad(String codigo) {
        String sql = "SELECT ETIQUETA FROM CODESTTAXI WHERE ID_CODIGO='" + codigo + "'";
        rs = ejecutarConsultaUnDatoStatement2(sql);
        try {
            String nomEstadoUnidad = rs.getString("ETIQUETA");
            return nomEstadoUnidad;
        } catch (SQLException ex) {
            log.info("[COD " + ex.getErrorCode() + "]SQL:{}", sql, ex);
        }
        return null;
    }

    /**
     * Obtiene el numero de filas que tiene el archivo de respaldos de estados
     * de asignacion del carro
     * @return int
     */
    public int getNumeroFilasRespaldoAsignacion() {
        try {
            String sql = "SELECT COUNT(*) FROM RESPALDO_ASIGNACION_SERVER;";
            rs = ejecutarConsultaUnDatoNoImprimir(sql);
            return rs.getInt(1);
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        } catch (NullPointerException ex) {
        }
        return 0;
    }

    /**
     * Obtiene todas las filas de los datos respaldados localmente de los estados
     * de asignacion, ocupado y libre de los vehiculos, filtrando los registros
     * para que no obtenga los repetidos...
     * @return ResultSet
     */
    public ResultSet getFilasRespaldoLocalAsignaciones() {
        String sql = "SELECT N_UNIDAD,COD_CLIENTE,ESTADO,FECHA,HORA,FONO,HORA_INSERT,USUARIO,DIRECCION "
                + "FROM RESPALDO_ASIGNACION_SERVER "
                + "WHERE 1 "
                + "GROUP BY N_UNIDAD,COD_CLIENTE,ESTADO,FECHA,HORA,FONO,HORA_INSERT,USUARIO,DIRECCION";
        return rs = ejecutarConsulta(sql);
    }

    /**
     * Obtener los vehiculos que estan ocupados y asignados al momento de cerrar
     * el programa para activarlos para que no haya conflictos...
     * @return ResultSet
     */
    public ResultSet getUnidadesOcupadasAsignadas() {
        String sql = "SELECT A.N_UNIDAD, A.ID_CODIGO "
                + "FROM REGCODESTTAXI A, ( "
                + "SELECT AUX.N_UNIDAD, MAX(AUX.FECHA_HORA) AS TMP "
                + "FROM REGCODESTTAXI AUX "
                + "GROUP BY AUX.N_UNIDAD) AS B "
                + "WHERE A.N_UNIDAD = B.N_UNIDAD "
                + "AND A.FECHA_HORA = B.TMP "
                + "AND A.ID_CODIGO IN ('OCU','ASI')";
        return rs = ejecutarConsulta(sql);
    }

    /**
     * Se cambia el estado de la unidad que se envie como parámetro, el nuevo
     * estado solo debe venir como ID_Estado
     * @param estado
     * @param usuario
     * @param unidad
     */
    public void setCambiarEstadoUnidad(String estado, String usuario, String unidad) {
        String sql = "INSERT INTO REGCODESTTAXI VALUES ('" + estado + "','" + usuario + "','" + unidad + "',now())";
        ejecutarSentenciaStatement2(sql);
    }

    /**
     * Devuleve el ultimo estado de todas las unidades para presentarlo en la
     * interfaz...
     * @return ResultSet
     */
    public ResultSet getUnidadesPintarEstado() {
        String sql = "SELECT A.N_UNIDAD, A.ID_CODIGO FROM REGCODESTTAXI A, ( "
                + "SELECT AUX.N_UNIDAD, MAX(AUX.FECHA_HORA) AS TMP "
                + "FROM REGCODESTTAXI AUX GROUP BY AUX.N_UNIDAD) AS B "
                + "WHERE A.N_UNIDAD = B.N_UNIDAD AND A.FECHA_HORA = B.TMP";
        return ejecutarConsultaStatement2(sql);
    }

    /**
     * Obtiene la el ultimo estado asignado a una unidad, con el tiempo en que
     * fue asignado y el tiempo que debe de cambiar el estado
     * @param unidad
     * @return Resulset
     */
    public ResultSet obtenerTiempoDeAsignacionEstado(String unidad) {
        String sql = "SELECT TIME(FECHA_HORA) AS HORA,DATE_FORMAT(DATE_ADD(FECHA_HORA, INTERVAL 30 MINUTE),'%T') AS HORA_FIN,ID_CODIGO "
                + "FROM REGCODESTTAXI "
                + "WHERE N_UNIDAD = " + unidad
                + " AND FECHA_HORA=(SELECT MAX(FECHA_HORA) "
                + "FROM REGCODESTTAXI "
                + "WHERE N_UNIDAD = " + unidad + ") "
                + "GROUP BY FECHA_HORA";
        return rs = ejecutarConsultaUnDato(sql);
    }

    /**
     * Recupera un cliente dependiendo de su codigo
     * @param codigo
     * @return
     */
    public Clientes obtenerCliente(int codigo) {
        ResultSet res;
        if (codigo != 0) {
            try {
                String sql = "SELECT TELEFONO,CODIGO,NOMBRE_APELLIDO_CLI,DIRECCION_CLI,SECTOR,NUM_CASA_CLI,INFOR_ADICIONAL,LATITUD,LONGITUD " + "FROM CLIENTES " + "WHERE CODIGO = " + codigo;
                res = ejecutarConsultaUnDatoStatement2(sql);
                Clientes cliente = new Clientes();
                cliente.setCodigo("" + codigo);
                cliente.setNombre(res.getString("NOMBRE_APELLIDO_CLI"));
                cliente.setDireccion(res.getString("DIRECCION_CLI"));
                cliente.setBarrio(res.getString("SECTOR"));
                cliente.setN_casa(res.getString("NUM_CASA_CLI"));
                cliente.setTelefono(res.getString("TELEFONO"));
                cliente.setReferencia(res.getString("INFOR_ADICIONAL"));
                cliente.setLatitud(res.getLong("LATITUD"));
                cliente.setLongitud(res.getLong("LONGITUD"));
                return cliente;
            } catch (SQLException ex) {
                return null;
            }
        }
        return null;
    }

    /**
     * Obtiene todos los pondientes guardados
     * @return ArrayList<Pendientes>
     */
    public ArrayList<Pendientes> obtenerPendientesGuardados() {
        ArrayList<Pendientes> datos = new ArrayList<Pendientes>();
        ResultSet r = null;
        int cod = 0;
        try {
            String sql = "SELECT CODIGO,FECHA_INI,FECHA_FIN,HORA,MIN_RECUERDO,CUANDO_RECORDAR,NOTA,ESTADO "
                    + "FROM PENDIENTES WHERE ESTADO = 'AC'";
            r = ejecutarConsultaStatement2(sql);

            while (r.next()) {
                Pendientes p = new Pendientes();
                cod = r.getInt("CODIGO");
                Clientes c = obtenerCliente(cod);
                p.setCliente(c);
                p.setFechaFin(r.getString("FECHA_FIN"));
                p.setFechaIni(r.getString("FECHA_INI"));
                p.setHora(r.getString("HORA"));
                p.setMinRecuerdo(r.getInt("MIN_RECUERDO"));
                p.setCuandoRecordar(r.getString("CUANDO_RECORDAR"));
                p.setEstado(r.getString("ESTADO"));
                p.setNota(r.getString("NOTA"));
                datos.add(p);
            }
            return datos;
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }
        return null;
    }

    /**
     * Obtiene todos los despachos pendientes de una fecha determinada
     * @param fecha
     * @return ArrayList<Pendientes>
     */
    public ArrayList<Pendientes> obtenerPendientesGuardadosPorFecha(String fecha) {
        ArrayList<Pendientes> datos = new ArrayList<Pendientes>();
        ResultSet rsPendiente = null;
        try {

            String sql = "SELECT P.CODIGO,P.FECHA_INI,P.FECHA_FIN,P.HORA,P.MIN_RECUERDO,P.CUANDO_RECORDAR,P.NOTA,P.ESTADO "
                    + "FROM PENDIENTES P "
                    + "WHERE P.ESTADO = 'AC' "
                    + "AND '" + fecha + "' BETWEEN P.FECHA_INI AND P.FECHA_FIN "
                    + "AND P.HORA >= NOW() "
                    + "AND ( "
                    + "SELECT IF(("
                    + "SELECT DISTINCT P.CUANDO_RECORDAR "
                    + "FROM PENDIENTES PCR "
                    + "WHERE P.CODIGO = PCR.CODIGO)='Lunes - Viernes', "
                    + "1,2 )) = (SELECT IF(DAYOFWEEK('" + fecha + "')IN(2,3,4,5,6),1,2)) AND P.HORA >= NOW() "
                    + "OR ( "
                    + "SELECT IF(("
                    + "SELECT DISTINCT P.CUANDO_RECORDAR "
                    + "FROM PENDIENTES PCR "
                    + "WHERE P.CODIGO = PCR.CODIGO)='Lunes - Domingo',"
                    + "2,1)) = 2 AND P.HORA >= NOW()";

            rsPendiente = ejecutarConsultaStatement2(sql);

            while (rsPendiente.next()) {
                Pendientes p = new Pendientes();
                int cod = rsPendiente.getInt("CODIGO");
                Clientes c = obtenerCliente(cod);
                p.setCliente(c);
                p.setFechaFin(rsPendiente.getString("FECHA_FIN"));
                p.setFechaIni(rsPendiente.getString("FECHA_INI"));
                p.setHora(rsPendiente.getString("HORA"));
                p.setMinRecuerdo(rsPendiente.getInt("MIN_RECUERDO"));
                p.setCuandoRecordar(rsPendiente.getString("CUANDO_RECORDAR"));
                p.setEstado(rsPendiente.getString("ESTADO"));
                p.setNota(rsPendiente.getString("NOTA"));
                datos.add(p);
            }
            return datos;
        } catch (SQLException ex) {
            return null;
        }
    }

    /**
     * Guarda una nueva pendiente en la base de datos
     * @param pendiente
     * @return boolean -> true si guarda la nueva pendiente
     */
    public boolean guardarPendientes(Pendientes pendiente) {
        String sql = "CALL SP_INSERTAR_PENDIENTES("
                + pendiente.getCliente().getCodigo()
                + ",'"
                + pendiente.getFechaIni()
                + "','"
                + pendiente.getFechaFin()
                + "','"
                + pendiente.getHora()
                + "',"
                + pendiente.getMinRecuerdo()
                + ",'"
                + pendiente.getCuandoRecordar()
                + "','"
                + pendiente.getNota()
                + "','"
                + "AC')";
        return ejecutarSentencia(sql);
    }

    /**
     * Elimina la pendiente que corresponda con los siguientes parametros
     * @param cod
     * @param fecIni
     * @param fecFin
     * @param horaPend
     * @return boolean -> true si la sentencia se ejecuta correctamente
     */
    public boolean eliminarPendiente(String cod, String fecIni, String fecFin, String horaPend) {
        String sql = "CALL SP_ELIMINAR_PENDIENTE("
                + cod
                + ",'"
                + fecIni
                + "','"
                + fecFin
                + "','"
                + horaPend
                + "')";
        return ejecutarSentencia(sql);
    }

    /**
     * Permite verificar si en la base de datos existe ese nombre de usuario
     * para ya no dejarlo ingresar nuevamente, true si existe el usuario, false
     * si no esta ingresado en la base de datos.
     * @param usuario
     */
    public boolean validarNombreUsuario(String usuario) {
        String sql = "SELECT COUNT(*) FROM usuarios "
                + "WHERE USUARIO = '" + usuario + "'";
        ResultSet r = ejecutarConsultaUnDato(sql);
        try {
            if (r.getInt(1) == 0) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }
        return false;
    }

    /**
     * Obtiene el valor de la tabla de configuraciones de una determinada llave,
     * este metodo se utiliza para utilizar las configuraciones de la base de datos
     * y no del archivo de configuración
     * @param key
     * @return String
     */
    public String getValorConfiguiracion(String key) {
        try {
            String sql = "SELECT VALUE FROM CONFIGURACIONES WHERE `KEY`='" + key + "'";
            ResultSet rsConfig = ejecutarConsultaUnDato(sql);
            return rsConfig.getString("VALUE");
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }
        return null;
    }

    /**
     * Asigna un valor dependiendo de la llave que se le envie para modificar
     * las propiedades del sistema
     * @param key
     * @param valor
     * @return boolean
     */
    public boolean setValorConfiguiracion(String key, String valor) {
        String sql = "UPDATE CONFIGURACIONES SET "
                + "VALUE ='" + valor + "' "
                + "WHERE `KEY`='" + key + "'";
        return ejecutarSentencia(sql);
    }

    /**
     * Obtiene de la base de datos la fecha y la hora del ultimo despacho realizado
     * esto permite comprobar si se ha retrocedido la hora del computador
     * @return long
     */
    public long obtenerUltimaFechaHoraDespacho() {
        try {
            String sql = "SELECT UNIX_TIMESTAMP(MAX(concat(FECHA,' ',HORA))) AS FECHAHORA "
                    + "FROM ASIGNADOS "
                    + "WHERE ESTADO='F'";
            ResultSet rsConfig = ejecutarConsultaUnDato(sql);
            return rsConfig.getLong("FECHAHORA");
        } catch (SQLException ex) {
            log.info("[COD " + ex.getErrorCode() + "]Obteniendo la ultima fecha y hora de despacho...", ex);
        }
        return 0;
    }

    /**
     * Cambia el estado de Activo en la tabla ultimos_gps para prensentar las 
     * posiciociones de los vehiculos en el mapa, activo es false para bloqueo
     * @param unidades -> conjunto de todas las unidades a bloquear ej: 3,45,23,1,28
     * @return boolean
     */
    public void guardarBloqueoUnidad(String unidades) {
        String sql = "UPDATE ULTIMOS_GPS SET ACTIVO=FALSE WHERE N_UNIDAD IN (" + unidades + ")";
        ejecutarSentenciaHilo(sql);
        sql = "UPDATE ULTIMOS_GPS SET ACTIVO=TRUE WHERE N_UNIDAD NOT IN (" + unidades + ")";
        ejecutarSentenciaHilo(sql);
    }

    /**
     * Obtiene si esa unidad ha sido bloqueada desde el servidor KRADAC por 
     * falta de pago...
     * @param unidad
     * @return boolean
     */
    public boolean getEstadoUnidadPendientePago(String unidad) {
        String sql = "SELECT ACTIVO FROM ULTIMOS_GPS WHERE N_UNIDAD='" + unidad + "'";
        boolean rta = true;
        try {
            rta = ejecutarConsultaUnDato(sql).getBoolean("ACTIVO");
        } catch (SQLException ex) {
            if (ex.getErrorCode() != 0) {
                log.info("[COD {}]", ex.getErrorCode(), ex);
            }
        }

        return rta;
    }

    /**
     * Obtiene la calve de un usuario para saber si cambio la clave
     * @param user
     * @return String
     */
    public String getClaveUsuario(String user) {
        String sql = "SELECT CLAVE FROM USUARIOS WHERE USUARIO='" + user + "'";
        try {
            return ejecutarConsultaUnDatoAux(sql).getString("CLAVE");
        } catch (SQLException ex) {
            log.info("[COD {}]", ex.getErrorCode(), ex);
        }
        return null;
    }

    /**
     * Guarda la información del cliente que llama o del que se va a realizar el
     * despacho
     * @param despacho
     */
    public void guardarInformacionDeLlamada(Despachos des) {
        String fecha = des.getStrFecha();
        if (des.getStrFecha().equals("null") || des.getStrFecha() == null) {
            fecha = funciones.getFecha();
        }

        String sql = "INSERT INTO LLAMADAS("
                + "COD_CLIENTE,"
                + "FECHA_HORA,"
                + "TELEFONO,"
                + "NOMBRE_APELLIDO_CLI,"
                + "DIRECCION_CLI,"
                + "SECTOR,"
                + "MINUTOS,"
                + "N_UNIDAD,"
                + "USUARIO) "
                + "VALUES("
                + des.getIntCodigo()
                + ",'"
                + fecha + " " + des.getStrHora()
                + "','"
                + des.getStrTelefono()
                + "','"
                + des.getStrNombre()
                + "','"
                + des.getStrDireccion()
                + "','"
                + des.getStrBarrio()
                + "',"
                + des.getIntMinutos()
                + ","
                + des.getIntUnidad()
                + ",'"
                + des.getStrUsuario()
                + "'"
                + ")";
        ejecutarSentencia(sql);
    }
}
