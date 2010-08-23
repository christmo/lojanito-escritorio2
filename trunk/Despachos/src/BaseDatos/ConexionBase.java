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
import java.util.ResourceBundle;
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
    private ResourceBundle rb;

    /**
     * Crea la conexion directamente a la base de datos de rastreosatelital
     * de kradac, parametros de la conexion quemados por defecto para la
     * maquina local
     */
    public ConexionBase() {
        rb = ResourceBundle.getBundle("configuracion.configsystem");
        driver = "com.mysql.jdbc.Driver";
        this.ip = rb.getString("ip_base");
        this.bd = rb.getString("base");
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
            int intNumero = Integer.parseInt(rs.getString(1));

            if (unidad == intNumero) {
                return true;
            }
        } catch (SQLException ex) {
            //Logger.getLogger(ConexionBase.class.getName()).log(Level.SEVERE, null, ex);
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

            if (estadoUnidad.equals("AC") || estadoUnidad.equals("C")) {
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
            String sql = "SELECT ETIQUETA FROM CODESTTAXI WHERE ID_CODIGO = '" + estadoUnidad + "'";
            rs = ejecutarConsultaUnDato(sql);
            return rs.getString("ETIQUETA");
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
     * Inserta un cliente
     * @param Despachos
     * @return confirmacion del resultado 1 valido || 0 invalido
     */
    public boolean InsertarCliente(Despachos des) {
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

    /**
     * Actualiza los campos de un cliente ingresado
     * @param des
     * @param codigo
     * @return boolean
     */
    public boolean ActualizarCliente(Despachos des, int codigo) {
        String sql = "UPDATE CLIENTES SET "
                + "TELEFONO=" + "'" + des.getStrTelefono() + "',"
                + "NOMBRE_APELLIDO_CLI=" + "'" + des.getStrNombre() + "',"
                + "DIRECCION_CLI=" + "'" + des.getStrDireccion() + "',"
                + "SECTOR=" + "'" + des.getStrBarrio() + "',"
                + "NUM_CASA_CLI=" + "'" + des.getStrNumeroCasa() + "',"
                + "LATITUD=" + des.getLatitud() + ","
                + "LONGITUD=" + des.getLongitud() + ","
                + "INFOR_ADICIONAL=" + "'" + des.getStrReferecia() + "'"
                + "WHERE CODIGO=" + codigo;

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
            System.out.println("Turno:" + turno);
            return turno;
        } catch (SQLException ex) {
            System.out.println("El turno que sigue es el primero...");
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
}
