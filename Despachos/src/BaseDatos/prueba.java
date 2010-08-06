
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

        for (int i=1; i<=45; i++)  {
            String sql1 = "INSERT INTO VEHICULOS(PLACA,N_UNIDAD,ID_EMPRESA) VALUES('POI-"+i+"','"+i+"','LN')";
            cb.ejecutarSentencia(sql1);
        }

        cb.CerrarConexion();

        // } catch (SQLException ex) {
        //    Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
        //}

    }
}
