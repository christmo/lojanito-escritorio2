package BaseDatos;

import com.mysql.jdbc.Statement;
import interfaz.subVentanas.Despachos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        this.pass = "";
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
     * @return boolean confirmacion del resultado 1 valido || 0 invalido
     */
    public boolean InsertarDespachoCliente(Despachos des) {
        String estado = getEstadoUnidad(des.getIntUnidad());
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
        String sql = "SELECT ID_CODIGO FROM REGCODESTTAXI WHERE N_UNIDAD=" + unidad + " AND FECHA = (SELECT MAX(FECHA) FROM REGCODESTTAXI WHERE N_UNIDAD=" + unidad + ")";
        try {
            ResultSet r = ejecutarConsultaUnDato(sql);
            return r.getString("ID_CODIGO");
        } catch (SQLException ex) {
            //Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Esta unidad no tiene estado en esta fecha...");
        }
        return "";
    }

    /**
     * Retorna el ultimo codigo a utilizar para un cliente
     * @return String
     * @throws SQLException
     */
    public String generarCodigo() throws SQLException {
        String sql = "SELECT MAX(CODIGO) AS COD FROM CLIENTES";
        ejecutarConsultaUnDato(sql);
        return rs.getString("COD");
    }

    /**
     * Inserta un cliente
     * @param Despachos
     * @return confirmacion del resultado 1 valido || 0 invalido
     */
    public boolean InsertarCliente(Despachos des) {
        String sql = "INSERT INTO CLIENTES(TELEFONO,CODIGO,NOMBRE_APELLIDO_CLI,DIRECCION_CLI, SECTOR, NUM_CASA_CLI,LATITUD,LONGITUD,INFOR_ADICIONAL)"
                + " VALUES("
                + "'" + des.getStrTelefono() + "',"
                + "'" + des.getStrCodigo() + "',"
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

    /**
     * Actualiza los campos de un cliente ingresado
     * @param des
     * @param codigo
     * @return boolean
     */
    public boolean  ActualziarCliente(Despachos des,int codigo) {
        String sql = "UPDATE CLIENTES SET "
                + "TELEFONO="+ "'" + des.getStrTelefono() + "',"
                + "NOMBRE_APELLIDO_CLI="+ "'" + des.getStrNombre() + "',"
                + "DIRECCION_CLI="+ "'" + des.getStrDireccion() + "',"
                + "SECTOR="+ "'" + des.getStrBarrio() + "',"
                + "NUM_CASA_CLI="+ "'" + des.getStrNumeroCasa() + "',"
                + "LATITUD="+ des.getLatitud() + ","
                + "LONGITUD="+ des.getLongitud() + ","
                + "INFOR_ADICIONAL="+"'"+des.getStrReferecia()+"'"
                + "WHERE CODIGO="+codigo;
                
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
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
                    + "IMAGEN, MARCA, NUM_MOTOR, NUM_CHASIS FROM VEHICULOS WHERE PLACA LIKE '" + parametro + "%'";
        } else {
            sql = "SELECT PLACA, N_UNIDAD, ID_EMPRESA, ID_CON, CONDUCTOR_AUX, "
                    + "MODELO, ANIO,PROPIETARIO, INF_ADICIONAL, "
                    + "IMAGEN, MARCA, NUM_MOTOR, NUM_CHASIS FROM VEHICULOS WHERE N_UNIDAD = '" + parametro + "'";
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
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            //Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            //Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);            
            return null;
        }        
    }
}
