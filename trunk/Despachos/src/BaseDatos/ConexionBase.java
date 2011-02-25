package BaseDatos;

import com.mysql.jdbc.Statement;
import interfaz.Principal;
import interfaz.funcionesUtilidad;
import interfaz.subVentanas.Clientes;
import interfaz.subVentanas.Despachos;
import interfaz.subVentanas.Pendientes;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    /**
     * Crea la conexion directamente a la base de datos de rastreosatelital
     * de kradac, parametros de la conexion quemados por defecto para la
     * maquina local
     */
    public ConexionBase(Properties conf) {
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
                    log.trace("Enlace de conexión con la base de datos falló, falta el archivo de configuración...");
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

//    private void reconectarBD() {
//        CerrarConexion();
//        log.info("Iniciar Reconexión a la base de datos...");
//        try {
//            conexion = DriverManager.getConnection(url, usr, pass);
//        } catch (SQLException ex) {
//            if (ex.getMessage().equals("Communications link failure")) {
//                log.trace("Enlace de conexión con la base de datos falló, falta el archivo de configuración...");
//            }
//        }
//        try {
//            st = (Statement) conexion.createStatement();
//        } catch (SQLException ex) {
//            java.util.logging.Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        log.info("Reconexión Base de Datos OK");
//    }

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
            log.trace("No se puede obtener la conexion...", ex);
        }
        try {
            st = (Statement) conexion.createStatement();
        } catch (SQLException ex) {
            log.trace("No se puede crear el estatement...", ex);
        } catch (NullPointerException ex) {
            log.trace("Conexión es NULL", ex);
        }
        log.trace("Conexion a Base de Datos OK: {}", bd);
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
            if (ex.getMessage().equals("No operations allowed after statement closed.")) {
                log.trace("Statement cerrado...");
            } else if (ex.getMessage().equals("Se realizó una consulta como null.")) {
                log.trace("Statement cerrado...");
            } else {
                log.trace("Error al consultar", ex);
            }
        } catch (NullPointerException ex) {
            log.trace("Null es statement");
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
            if (ex.getMessage().equals("No operations allowed after statement closed.")) {
                log.trace("Statement cerrado...");
            } else if (ex.getMessage().equals("Se realizó una consulta como null.")) {
                log.trace("Statement cerrado...");
            } else if (ex.getMessage().substring(0, 113).equals("No operations allowed after connection closed.Connection was implicitly closed due to underlying exception/error:")) {
                log.trace("Se desconectó el cable de RED del equipo...");
//                reconectarBD();
//                r = ejecutarConsultaStatement2(sql);
            } else {
                log.trace("Error al consultar [ejecutarConsultaStatement2]", ex);
            }
        } catch (NullPointerException ex) {
            log.trace("Null ese statement");
        }
        return r;
    }

    /**
     * Ejecuta una consulta en la base de datos, que devuelve valores
     * no es necesario recorrer el resultset
     * @param sql - debe ser Select
     * @return ResultSet
     */
    public ResultSet ejecutarConsultaUnDato(String sql) {
        //System.out.println("Consultar: " + sql);
        ResultSet rsCUD = null;
        try {
            Statement sta = (Statement) conexion.createStatement();
            rsCUD = sta.executeQuery(sql);
            log.trace(sql);
            rsCUD.next();
        } catch (SQLException ex) {
            System.out.println("ver: " + ex.getMessage());
            if (!ex.getMessage().equals("No operations allowed after statement closed.")) {
                log.trace("Statement cerrado [Reconectar]");
                //reconectarBD();
                //return ejecutarConsultaUnDato(sql);
            } else if (ex.getMessage().equals("Communications link failure\nLast packet sent to the server was 0 ms ago.")) {
                log.trace("Base de datos cerrada intencionalmente...");
                //reconectarBD();
                //return ejecutarConsultaUnDato(sql);
            } else if (ex.getMessage().equals("Server shutdown in progress")) {
                log.trace("Base de datos cerrada intencionalmente...");
                //reconectarBD();
                //return ejecutarConsultaUnDato(sql);
            }
        }
        return rsCUD;
    }

    /**
     * Ejecuta una consulta en la base de datos, que devuelve valores
     * no es necesario recorrer el resultset, no imprime la consulta
     * para ponerla en hilos donde no es necesario ver lo que sale...
     * @param sql - debe ser Select
     * @return ResultSet
     */
    public ResultSet ejecutarConsultaUnDatoNoImprimir(String sql) {
        try {
            rs = st.executeQuery(sql);
            //log.trace(sql);
            rs.next();
        } catch (SQLException ex) {
            if (!ex.getMessage().equals("No operations allowed after statement closed.")) {
                log.trace("Statement cerrado [Reconectar]");
//                reconectarBD();
//                return ejecutarConsultaUnDatoNoImprimir(sql);
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
            log.trace("", ex);
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
            log.info("Ejecutar: {}", sql);

            int rta = st.executeUpdate(sql);
            if (rta >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            String txt = ex.getMessage();
            try {
                txt = ex.getMessage().substring(0, 76);
                if (txt.equals("Unable to connect to foreign data source: Can't connect to MySQL server on '")) {
                    String[] ip_server = ex.getMessage().split("'");
                    System.err.println("****************\n* MySQL no se pudo conectar con la tabla del servidor KRADAC -> " + ip_server[2] + "...\n****************");
                    log.trace("MySQL no se pudo conectar con la tabla del servidor KRADAC -> " + ip_server[2] + "...");
                    return false;
                } else if (txt.equals("Got error 10000 'Error on remote system: 2003: Can't connect to MySQL server")) {
                    String[] ip_server = ex.getMessage().split("'");
                    System.err.println("****************\n* MySQL no se pudo conectar con la tabla FEDERADA del servidor KRADAC -> " + ip_server[3] + "...\n****************");
                    log.error("[Empresa: {}]MySQL no se pudo conectar con la tabla FEDERADA del servidor KRADAC -> " + ip_server[3] + "...", Principal.sesion[1]);
                    return false;
                } else if (txt.substring(0, 64).equals("Unable to connect to foreign data source: Access denied for user")) {
                    String[] ip_server = ex.getMessage().split("'");
                    System.err.println("****************\n* NO hay permiso para insertar en el servidor KRADAC -> " + ip_server[1] + " --> " + ip_server[3] + "\n****************");
                    log.error("[Empresa: {}]NO hay permiso para insertar en el servidor KRADAC -> " + ip_server[1] + " --> " + ip_server[3], Principal.sesion[1], ex);
                    return false;
                } else {
                    txt = ex.getMessage().substring(0, 15);
                }
            } catch (StringIndexOutOfBoundsException sex) {
                txt = ex.getMessage().substring(0, 15);
            }
            if (ex.getMessage().substring(0, 5).equals("Table")) {
                String[] texto = ex.getMessage().split("'");
                try {
                    if (texto[2].equals(" doesn") && texto[3].equals("t exist")) {
                        log.trace("La tabla no esta creada: [" + texto[1] + "]");
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                }
                return false;
            } else if (ex.getMessage().equals("Got timeout reading communication packets")) {
                System.err.println("No hay Conexion a internet -> no se pueden guardar los datos en la tabla del servidor...");
                log.trace("No hay Conexion a internet -> no se pueden guardar los datos en la tabla del servidor...");
                return false;
            } else if (ex.getMessage().equals("No operations allowed after statement closed.")) {
                System.err.println("****************\n* MySQL no se pudo conectar con la tabla del servidor, error al ejecutar la sentencia...\n****************");
                log.trace("MySQL no se pudo conectar con la tabla del servidor, error al ejecutar la sentencia...");
                return false;
            } else if (txt.equals("Duplicate entry")) {
                System.err.println("****************\n*" + "Error de Clave Primaria -> Usuario ya ingresado..." + "...\n****************");
                log.trace("Error de Clave Primaria -> Usuario ya ingresado...");
                return false;
            } else if (ex.getMessage().substring(0, 27).endsWith("Communications link failure")) {
                log.trace("Falla en la comunicación con la base de datos..");
                return false;
            } else if (ex.getMessage().equals("Column count doesn't match value count at row 1")) {
                log.error("[" + Principal.sesion[1] + "]Fallo al ejecutar: {}\n[excepciones]{}", sql, ex);
                return false;
            } else {
                log.trace("", ex);
                return false;
            }
        } catch (NullPointerException ex) {
            System.out.println("NULL en [339][ejecutarSentencia] ");
            return false;
        }
    }

    /**
     * Utilizar cuando de error de ResultSet cerrado por problemas de los hilos
     * @param sql
     * @return boolean
     */
    public boolean ejecutarSentenciaStatement2(String sql) {
        try {
            Statement st1 = (Statement) conexion.createStatement();

            log.info("Ejecutar: {}", sql);

            int rta = st1.executeUpdate(sql);

            if (rta >= 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException ex) {
            String txt = ex.getMessage();
            try {
                txt = ex.getMessage().substring(0, 76);
                if (txt.equals("Unable to connect to foreign data source: Can't connect to MySQL server on '")) {
                    String[] ip_server = ex.getMessage().split("'");
                    System.err.println("****************\n* MySQL no se pudo conectar con la tabla del servidor KRADAC -> " + ip_server[2] + "...\n****************");
                    log.trace("MySQL no se pudo conectar con la tabla del servidor KRADAC -> " + ip_server[2] + "...", ex);
                    return false;
                } else if (txt.equals("Got error 10000 'Error on remote system: 2003: Can't connect to MySQL server")) {
                    String[] ip_server = ex.getMessage().split("'");
                    System.err.println("****************\n* MySQL no se pudo conectar con la tabla FEDERADA del servidor KRADAC -> " + ip_server[3] + "...\n****************");
                    log.error("[Empresa: {}]MySQL no se pudo conectar con la tabla FEDERADA del servidor KRADAC -> " + ip_server[3] + "...", Principal.sesion[1], ex);
                    return false;
                } else if (txt.substring(0, 64).equals("Unable to connect to foreign data source: Access denied for user")) {
                    String[] ip_server = ex.getMessage().split("'");
                    System.err.println("****************\n* NO hay permiso para insertar en el servidor KRADAC -> " + ip_server[1] + " --> " + ip_server[3] + "\n****************");
                    log.error("[Empresa: {}]NO hay permiso para insertar en el servidor KRADAC -> " + ip_server[1] + " --> " + ip_server[3], Principal.sesion[1], ex);
                    return false;
                } else if (txt.substring(0, 46).equals("Unable to connect to foreign data source: Host")) {
                    log.trace("No se puede conectar desde un sitio externo, con una IP no registrada: {}", ex.getMessage().split("'")[1]);
                    return false;
                } else {
                    txt = ex.getMessage().substring(0, 15);
                }
            } catch (StringIndexOutOfBoundsException sex) {
                txt = ex.getMessage().substring(0, 15);
            }
            if (ex.getMessage().substring(0, 5).equals("Table")) {
                String[] texto = ex.getMessage().split("'");
                try {
                    if (texto[2].equals(" doesn") && texto[3].equals("t exist")) {
                        log.trace("La tabla no esta creada: [" + texto[1] + "]");
                    }
                } catch (ArrayIndexOutOfBoundsException aiob) {
                }
                return false;
            } else if (ex.getMessage().equals("Got timeout reading communication packets")) {
                System.err.println("No hay Conexion a internet -> no se pueden guardar los datos en la tabla del servidor...");
                log.trace("No hay Conexion a internet -> no se pueden guardar los datos en la tabla del servidor...");
                return false;
            } else if (ex.getMessage().equals("No operations allowed after statement closed.")) {
                System.err.println("****************\n* MySQL no se pudo conectar con la tabla del servidor, error al ejecutar la sentencia...\n****************");
                log.trace("MySQL no se pudo conectar con la tabla del servidor, error al ejecutar la sentencia...");
                return false;
            } else if (txt.equals("Duplicate entry")) {
                System.err.println("****************\n*" + "Error de Clave Primaria -> Usuario ya ingresado..." + "...\n****************");
                log.trace("Error de Clave Primaria -> Usuario ya ingresado...");
                return false;
            } else if (ex.getMessage().substring(0, 113).equals("No operations allowed after connection closed.Connection was implicitly closed due to underlying exception/error:")) {
                log.trace("Se desconectó el cable de RED del equipo...");
//                reconectarBD();
//                return ejecutarSentenciaStatement2(sql);
                return false;
            } else {
                log.trace("", ex);
                return false;
            }
        }
    }

    public ResultSet ejecutarConsultaUnDatoStatement2(String sql) {
        try {
            Statement st1 = (Statement) conexion.createStatement();

            log.info("Consultar: {}", sql);

            rsAux = st1.executeQuery(sql);
            rsAux.next();

            return rsAux;
        } catch (SQLException ex) {
            String msg = ex.getMessage().substring(0, 113);
            System.out.println(msg.length() + " - " + msg);
            if (msg.equals("No operations allowed after connection closed.Connection was implicitly closed due to underlying exception/error:")) {
                log.trace("Se desconectó el cable de RED del equipo...");
//                reconectarBD();
//                return ejecutarConsultaUnDatoStatement2(sql);
            } else {
                log.trace("", ex);
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
    public boolean ejecutarSentenciaHilo(String sql, String unidad) {
        try {
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
            log.trace("ResultSet closed - getNumeroCarerasPorVehiculo");
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
            conexion.close();
        } catch (SQLException ex) {
            log.trace("", ex);
        } catch (NullPointerException ex) {
            log.trace("NO está abierta la base de datos... [{}]", Principal.sesion[1]);
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
    private String estadoUnidad;

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

            if (estadoUnidad.equals("ASI") || estadoUnidad.equals("C")) {
                String sql = "CALL SP_INSERTAR_DESPACHOS("
                        + des.getIntCodigo() + ","
                        + "'" + getFechaActual() + "',"
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
     * Retorna el estado de la ultima unidad intentada despachar
     * @return String
     */
    public String getEstadoUnidad() {
        try {
            if (estadoUnidad.equals("")) {
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
     * Obtiene la fecha actual del sistema
     * @return String
     */
    public String getFechaActual() {
        Calendar calendario = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendario.getTime());
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
        }
        return "";
    }

    /**
     * Retorna el ultimo codigo a utilizar para un cliente
     * @return String
     * @throws SQLException
     */
    public String generarCodigo() throws SQLException {
        //String sql = "SELECT IFNULL(MAX(CODIGO),0) AS COD FROM CLIENTES";
        //ejecutarConsultaUnDato(sql);
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
            log.trace("", ex);
        }
        return rta;
    }

    /**
     * Devuelve todos los registros de vehiculos
     * que coincidan con el parámetro enviado
     * @param parametro Puede ser cédula o nombre
     * @param id  Identifica si es placa (0) o num unidad (1)
     * @return ArrayList<String[]> con los resultados encontrados
     * null si no encuentrada ningull
     */
    public ArrayList<String[]> buscarVehiculos(String parametro, int id) {

        ArrayList<String[]> rta = new ArrayList();

        String sql;
        if (id == 0) {
            sql = "SELECT PLACA, N_UNIDAD, ID_EMPRESA, ID_CON, CONDUCTOR_AUX, "
                    + " MODELO, ANIO,PROPIETARIO, INF_ADICIONAL, "
                    + "IMAGEN, MARCA, NUM_MOTOR, NUM_CHASIS, REG_MUNICIPAL FROM VEHICULOS WHERE PLACA LIKE '" + parametro + "%'";
        } else {
            sql = "SELECT PLACA, N_UNIDAD, ID_EMPRESA, ID_CON, CONDUCTOR_AUX, "
                    + "MODELO, ANIO,PROPIETARIO, INF_ADICIONAL, "
                    + "IMAGEN, MARCA, NUM_MOTOR, NUM_CHASIS, REG_MUNICIPAL FROM VEHICULOS WHERE N_UNIDAD = '" + parametro + "'";
        }

        ResultSet res = ejecutarConsulta(sql);
        try {
            while (res.next()) {
                String[] aux = new String[14];
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
                rta.add(aux);
            }
        } catch (SQLException ex) {
            log.trace("", ex);
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
            log.trace("", ex);
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
            log.trace("", ex);
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
            String reg_municipal) {

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
                + reg_municipal
                + ")";

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
            String sql = "SELECT DATE_FORMAT(HORA,'%H%:%i') AS HORA, "
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
            log.trace("", ex);
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
            String sql = "SELECT DATE_FORMAT(HORA,'%H%:%i') AS HORA, "
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
            log.trace("", ex);
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
            String sql = "SELECT DATE_FORMAT(HORA,'%H%:%i') AS HORA, "
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
            log.trace("", ex);
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
            log.trace("", ex);
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
            String sql = "SELECT DATE_FORMAT(HORA,'%H%:%i') AS HORA, "
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
            log.trace("", ex);
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
            log.trace("", ex);
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
            String sql = "SELECT USUARIO FROM USUARIOS";
            rs = ejecutarConsulta(sql);
            ArrayList<String> listaUsuarios = new ArrayList<String>();
            while (rs.next()) {
                listaUsuarios.add(rs.getString("USUARIO"));
            }
            datosCast = new String[listaUsuarios.size()];
            datosCast = listaUsuarios.toArray(datosCast);
            return datosCast;
        } catch (SQLException ex) {
            log.trace("", ex);
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
            log.trace("", ex);
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
                + funciones.encriptar(pass, "KOMPRESORKR@D@C") + "','"
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
            if (ex.getMessage().equals("Illegal operation on empty result set.")) {
                log.trace("Falta el nombre de la empresa en la base de datos...");
            } else {
                log.trace("", ex);
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
            log.trace("", ex);
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
            log.trace("", ex);
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
        String sql = "TRUNCATE POSICION_CLIENTES";
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
     * @deprecated 
     * @param particion
     * @param unidad
     * @param empresa
     * @param latitud
     * @param longitud
     * @param fecha
     * @param hora
     * @param est_taxi
     * @param vel
     * @param est_taxim
     * @return boolean
     */
    public boolean InsertarRecorridoTaxi(String particion,
            String unidad,
            String empresa,
            String latitud,
            String longitud,
            String fecha,
            String hora,
            String est_taxi,
            String vel,
            String est_taxim) {

        String sql = "CALL SP_INSERTAR_RECORRIDOS('"
                + particion + "',"
                + Integer.parseInt(unidad) + ",'"
                + empresa + "',"
                + Double.parseDouble(latitud) + ","
                + Double.parseDouble(longitud) + ",'"
                + fecha + "','"
                + hora + "','"
                + est_taxi + "',"
                + Double.parseDouble(vel) + ",'"
                + est_taxim
                + "')";
        return ejecutarSentenciaHilo(sql, unidad);
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
        return ejecutarSentenciaHilo(sql, unidad);
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
            log.trace("SQL:{}", sql, ex);
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
        } catch (NullPointerException ex) {
        }
        return 0;
    }

    /**
     * Obtiene todas las filas de los datos respaldados localmente de los estados
     * de asignacion, ocupado y libre de los vehiculos
     * @return ResultSet
     */
    public ResultSet getFilasRespaldoLocalAsignaciones() {
        String sql = "SELECT N_UNIDAD,COD_CLIENTE,ESTADO,FECHA,HORA,FONO,HORA_INSERT,USUARIO,DIRECCION FROM RESPALDO_ASIGNACION_SERVER";
        return rs = ejecutarConsulta(sql);
    }

    /**
     * Obtener los vehiculos que estan ocupados y asignados al momento de cerrar
     * el programa para activarlos para que no haya conflictos...
     * @return ResultSet
     */
    public ResultSet getUnidadesOcupadasAsignadas() {
        String sql = "SELECT A.N_UNIDAD, A.ID_CODIGO "
                + "FROM REGCODESTTAXI A, ( SELECT AUX.N_UNIDAD, MAX(CONCAT(AUX.FECHA,AUX.HORA)) AS TMP FROM REGCODESTTAXI AUX GROUP BY AUX.N_UNIDAD) AS B WHERE A.N_UNIDAD = B.N_UNIDAD AND CONCAT(A.FECHA,A.HORA) = B.TMP AND A.ID_CODIGO IN ('OCU','ASI')";
        return rs = ejecutarConsulta(sql);
    }

    /**
     * Solo funciona donde MySQL este instalado fisicamente, si se usa con wamp
     * poner la ruta donde se intalo wamp y mysql se puede usuatilizar para hacer
     * actualizaciones a las base de datos...
     * @param scriptpath
     * @param verbose
     * @return String
     */
    public String executeScript(String scriptpath, boolean verbose) {
        String output = null;
        try {
            String[] cmd = new String[]{"mysql",
                this.bd,
                "--user=" + this.usr,
                "--password=" + this.pass,
                "-e",
                "\"source " + scriptpath + "\""
            };
            System.err.println(cmd[0] + " " + cmd[1] + " "
                    + cmd[2] + " " + cmd[3] + " "
                    + cmd[4] + " " + cmd[5]);
            Process proc = Runtime.getRuntime().exec(cmd);
            if (verbose) {
                InputStream inputstream = proc.getInputStream();
                InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
                BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

                // read the output
                String line;
                while ((line = bufferedreader.readLine()) != null) {
                    System.out.println(line);
                }

                // check for failure
                try {
                    if (proc.waitFor() != 0) {
                        System.err.println("exit value = "
                                + proc.exitValue());
                    }
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * Obtiene la el ultimo estado asignado a una unidad, con el tiempo en que
     * fue asignado y el tiempo que debe de cambiar el estado
     * @param unidad
     * @return Resulset
     */
    public ResultSet obtenerTiempoDeAsignacionEstado(String unidad) {
        String sql = "SELECT FECHA,HORA,DATE_FORMAT(DATE_ADD(CONCAT(FECHA,' ',HORA), INTERVAL 30 MINUTE),'%T') AS HORA_FIN,ID_CODIGO "
                + "FROM REGCODESTTAXI "
                + "WHERE  N_UNIDAD = " + unidad
                + " AND CONCAT(FECHA,'-',HORA)=(SELECT MAX(CONCAT(FECHA,'-',HORA)) "
                + "FROM REGCODESTTAXI "
                + "WHERE N_UNIDAD = " + unidad + ") "
                + "GROUP BY CONCAT(FECHA,'-',HORA)";
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
                //java.util.logging.Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            String sql = "SELECT CODIGO,FECHA_INI,FECHA_FIN,HORA,MIN_RECUERDO,CUANDO_RECORDAR,NOTA,ESTADO "
                    + "FROM PENDIENTES WHERE ESTADO = 'AC'";
            r = ejecutarConsultaStatement2(sql);

            while (r.next()) {
                Pendientes p = new Pendientes();
                int cod = r.getInt("CODIGO");
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
            log.trace("", ex);
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
                    + "SELECT P.CUANDO_RECORDAR "
                    + "FROM PENDIENTES PCR "
                    + "WHERE P.CODIGO = PCR.CODIGO)='Lunes - Viernes', "
                    + "1,2 )) = (SELECT IF(DAYOFWEEK('" + fecha + "')IN(2,3,4,5,6),1,2)) AND P.HORA >= NOW() "
                    + "OR ( "
                    + "SELECT IF(("
                    + "SELECT P.CUANDO_RECORDAR "
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
            if (ex.getMessage().equals("")) {
            } else {
                log.trace("", ex);
            }
        }
        return null;
    }

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
}
