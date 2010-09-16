package BaseDatos;

import com.mysql.jdbc.Statement;
import interfaz.funcionesUtilidad;
import interfaz.subVentanas.Clientes;
import interfaz.subVentanas.Despachos;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author root
 */
public class ConexionBase {

    private String driver, url, ip, usr, pass;
    /**
     * Nombre de la Base de datos
     */
    private String bd;
    private Connection conexion;
    private Statement st;
    private ResultSet rs = null;
    //private ResourceBundle rb;
    private Properties arcConfig;
    private funcionesUtilidad funciones = new funcionesUtilidad();

    /**
     * Crea la conexion directamente a la base de datos de rastreosatelital
     * de kradac, parametros de la conexion quemados por defecto para la
     * maquina local
     */
    public ConexionBase(Properties conf) {
        System.out.println("Conexion a la base con Archivo de Configuracion...");
        try {
            this.arcConfig = conf;
            driver = "com.mysql.jdbc.Driver";
            this.ip = arcConfig.getProperty("ip_base");
            this.bd = arcConfig.getProperty("base");
            this.usr = arcConfig.getProperty("user");
            this.pass = arcConfig.getProperty("pass");

            url = "jdbc:mysql://" + ip + "/" + bd;

            try {
                Class.forName(driver).newInstance();
                conexion = DriverManager.getConnection(url, usr, pass);
                st = (Statement) conexion.createStatement();
                System.out.println("Conexion a Base de Datos: " + bd + " Ok");
            } catch (Exception exc) {
                String msg = exc.toString().substring(32, 76);
                if (msg.toString().equals("MySQLSyntaxErrorException: Unknown database ")) {
                    throw new UnsupportedOperationException("servidor");
                }
                if (msg.toString().equals("CommunicationsException: Communications link")) {
                    throw new UnsupportedOperationException("base");
                }
            }
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
            Class.forName(driver).newInstance();
            conexion = DriverManager.getConnection(url, usr, pass);
            st = (Statement) conexion.createStatement();
            System.out.println("Conexion a Base de Datos: " + bd + " Ok");
        } catch (Exception exc) {
            String msg = exc.toString().substring(32, 76);
            if (msg.toString().equals("MySQLSyntaxErrorException: Unknown database ")) {
                JOptionPane.showMessageDialog(null, "Error Grave -> No se puede iniciar el Sistema:\n\n NO ES POSIBLE ABRIR O INGRESAR A LA BASE DE DATOS ESPECIFICADA... \n\n NOMBRE DE LA BASE DATOS: " + bd, "Error...", 0);
                System.err.println("Error al tratar de abrir la base de Datos: " + bd + " --> " + exc);
            }
            if (msg.toString().equals("CommunicationsException: Communications link")) {
                JOptionPane.showMessageDialog(null, "Error Grave -> No se puede iniciar el Sistema:\n\n NO ES POSIBLE ACCEDER AL SERVIDOR DE BASE DE DATOS... \n\n NOMBRE DE LA BASE DATOS: " + bd, "Error...", 0);
                System.err.println("Error al ACCEDER AL SERVIDOR de la base de Datos: " + bd + " --> " + exc);
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
        System.out.println("Consultar: " + sql);
        try {
            rsAux = st.executeQuery(sql);
            rsAux.next();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            System.out.println("Ejecutar: " + sql);
            int rta = st.executeUpdate(sql);
            if (rta >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
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
            System.err.println("Unidad: " + unidad + " --> Coordenadas Nuevas");
            if (rta >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Unidad: " + unidad + " --> Coordenadas ya ingresadas");
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
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            int intNumero = rs.getInt("N_UNIDAD");

            if (unidad == intNumero) {
                return true;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            //Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (NullPointerException ex) {
            //throw new UnsupportedOperationException("SinEstado");
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
            //Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            rs = ejecutarConsultaUnDato(sql);
            return rs.getString("ID_CODIGO");
        } catch (SQLException ex) {
            //Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
        String sql = "SELECT IFNULL(MAX(CODIGO),0) AS COD FROM CLIENTES";
        ejecutarConsultaUnDato(sql);
        return rs.getString("COD");
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
            System.out.println("Entrar Por aqui si el codigo es 0");
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
            System.out.println("Entrar Por aqui si el codigo NO es 0");
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
            System.out.println("Este codigo de vuelve: " + cod + " = " + codigo);
            if (codigo == cod) {
                System.err.println("Ya hay un cliente con ese telefono...");
                return true;
            }
        } catch (SQLException ex) {
            return false;
            //Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * 
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
     * @return
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
            String cha) {

        String sql = "CALL SP_UPDATE_VEHICULO('" + pl + "'," + numi
                + ",'" + emp + "','" + con + "','" + conaux + "','" + model + "'," + an + ",'" + pro
                + "','" + inf + "','" + img + "','" + mar + "','" + mot + "','" + cha + "')";

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
            //Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            System.out.println(sql);
            rs = ejecutarConsulta(sql);

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
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
    public boolean InsertarClienteMapa(int cod_cliente, double latitud, double longitud) {
        String sql = "INSERT INTO POSICION_CLIENTES(COD_CLIENTE,N_UNIDAD,LATITUD,LONGITUD,FECHA,HORA) "
                + "VALUES("
                + cod_cliente + ","
                + null + ","
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
                //Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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
    Despachos demo;

    public void ImprimirDespacho(Despachos desp) {
        System.out.println("-*-\n" + "Nombre: " + desp.getStrNombre());
        System.out.println("Codigo: " + desp.getIntCodigo());
        System.out.println("Unidad: " + desp.getIntUnidad() + "\n-/-");
    }

    /**
     * Obtiene el nombre de los estados de los vehiculos dependiendo del estado
     * @param codigo
     * @return
     */
    public String getNombreEstadoUnidad(String codigo) {
        String sql = "SELECT ETIQUETA FROM CODESTTAXI WHERE ID_CODIGO='" + codigo + "'";
        rs = ejecutarConsultaUnDato(sql);
        try {
            return rs.getString("ETIQUETA");
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
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
}
