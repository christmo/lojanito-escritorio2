package BaseDatos;

import com.mysql.jdbc.Statement;
import interfaz.subVentanas.Despachos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class ConexionBase {

    private String driver, url, ip, bd, usr, pass;
    private Connection conexion;
    private Statement st;
    private ResultSet rs;

    /**
     * Crea la conexion directamente a la base de datos de rastreosatelital
     * de kradac, parametros de la conexion quemados por defecto para la
     * maquina local
     */
    public ConexionBase() {
        driver = "com.mysql.jdbc.Driver";
        this.ip = "localhost";
        this.bd = "rastreosatelital";
        this.usr = "root";
        this.pass = "kradac";
        url = "jdbc:mysql://" + ip + "/" + bd;
        try {
            Class.forName(driver).newInstance();
            conexion = DriverManager.getConnection(url, usr, pass);
            st = (Statement) conexion.createStatement();
            System.out.println("Conexion a Base de Datos: " + bd + " Ok");
        } catch (Exception exc) {
            System.out.println("Error al tratar de abrir la base de Datos: " + bd + " : " + exc);
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
            Class.forName(driver).newInstance();
            conexion = DriverManager.getConnection(url, usr, pass);
            st = (Statement) conexion.createStatement();
            System.out.println("Conexion a Base de Datos: " + bd + " Ok");
        } catch (Exception exc) {
            System.out.println("Error al tratar de abrir la base de Datos: " + bd + " : " + exc);
        }
    }

    /**
     * Ejecuta una consulta en la base de datos, que devuelve valores
     * es necesario recorrer el resultset
     * @param sql - debe ser Select
     * @return ResultSet
     */
    public ResultSet ejecutarConsulta(String sql) {
        System.out.println("Consultar: " + sql);
        try {
            rs = st.executeQuery(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    /**
     * Ejecuta una consulta en la base de datos, que devuelve valores
     * no es necesario recorrer el resultset
     * @param sql - debe ser Select
     * @return ResultSet
     */
    public ResultSet ejecutarConsultaUnDato(String sql) {
        System.out.println("Consultar: " + sql);
        try {
            rs = st.executeQuery(sql);
            rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    /**
     * Ejecuta una sentencia en la base esta puede ser de INSERT, UPDATE O
     * DELETE
     * @param sql - Sentencias INSERT, UPDATE, DELETE
     * @return int - confirmacion del resultado 1 valido || 0 invalido
     */
    public boolean ejecutarSentencia(String sql) {
        try {
            System.out.println("Ejecutar: " + sql);
            int rta = st.executeUpdate(sql);
            if (rta == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            //CerrarConexion();
        }
    }

    /**
     * Obtiene el numero de Vehiculos que tiene la empresa
     * @return int
     */
    private int getNumeroVehiculos(String strEmpresa) {
        try {
            String sql = "SELECT COUNT(*) FROM VEHICULOS WHERE ID_EMPRESA = '" + strEmpresa + "'";
            rs = ejecutarConsultaUnDato(sql);
            int intNumero = Integer.parseInt(rs.getString(1));
            return intNumero;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
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
    public Connection CerrarConexion() {
        try {
            conexion.close();
            System.out.println("Base de datos Cerrada...");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        conexion = null;
        return conexion;
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
            int intNumero = Integer.parseInt(rs.getString(1));

            if (unidad == intNumero) {
                return true;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Insertar Despacho Cliente
     * @param des
     * @return
     */
    public boolean InsertarDespachoCliente(Despachos des) {
        String estado = getEstadoUnidad(des.getIntUnidad(), getFechaActual());
        if (estado.equals("AC")) {
            String sql = "INSERT INTO ASIGNADOS(TELEFONO,COD_CLIENTE,N_UNIDAD,FECHA, HORA, MINU,ATRASO,NOTA,ESTADO)"
                    + " VALUES("
                    + "'" + des.getStrTelefono() + "',"
                    + "'" + des.getStrCodigo() + "',"
                    + des.getIntUnidad() + ","
                    + "'" + getFechaActual() + "',"
                    + "'" + des.getStrHora() + "',"
                    + "'" + des.getIntMinutos() + "',"
                    + "'" + des.getIntAtraso() + "',"
                    + "'" + des.getStrNota() + "',"
                    + "'" + estado + "'"
                    + ")";
            return ejecutarSentencia(sql);
        }
        return false;
    }

    public String getFechaActual() {
        Calendar calendario = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendario.getTime());
    }

    public String getEstadoUnidad(int unidad, String fecha) {
        String sql = "SELECT ID_CODIGO FROM REGCODESTTAXI WHERE FECHA='" + fecha + "' AND N_UNIDAD=" + unidad;
        try {
            ResultSet r = ejecutarConsultaUnDato(sql);
            return r.getString("ID_CODIGO");
        } catch (SQLException ex) {
            //Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Esta unidad no tiene estado en esta fecha...");
        }
        return "";
    }
}
