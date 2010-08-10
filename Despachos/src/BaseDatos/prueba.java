
package BaseDatos;

import java.sql.ResultSet;

/**
 *
 * @author root
 */
public class prueba {

    public static void main(String[] args) {
        // try {
        ConexionBase cb = new ConexionBase();

        String sql = "SELECT * FROM USUARIOS WHERE USUARIO = '" + "KRADAC" + "' AND CLAVE = '" + "KRADAC" + "'";

        ResultSet rs = cb.ejecutarConsulta(sql);
        //System.out.println("ID: " + rs.getString(3));

        for (int i=15; i<=20; i++)  {
            String sql1 = "INSERT INTO CLIENTES(TELEFONO,CODIGO,NOMBRE_APELLIDO_CLI) VALUES('"+(7257917+i)+"','"+i+"','"+"Prueba "+i+"')";
            //System.out.println(""+sql1);
            cb.ejecutarSentencia(sql1);
        }

        cb.CerrarConexion();

        // } catch (SQLException ex) {
        //    Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
        //}

    }
}
