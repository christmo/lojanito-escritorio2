package BaseDatos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class prueba {

    public static void main(String[] args) {
//        try {
            ConexionBase cb = new ConexionBase("localhost", "rastreosatelital", "root", "");

            //String sql = "SELECT * FROM USUARIOS WHERE USUARIO = '" + "KRADAC" + "' AND CLAVE = '" + "KRADAC" + "'";

            //ResultSet rs = cb.ejecutarConsulta(sql);
            //System.out.println("ID: " + rs.getString(3));

            for (int i = 39; i <= 39; i++) {
                //String sql1 = "INSERT INTO CLIENTES(TELEFONO,CODIGO,NOMBRE_APELLIDO_CLI) VALUES('"+(7257917+i)+"','"+i+"','"+"Prueba "+i+"')";
                String sql1 = "INSERT INTO VEHICULOS(PLACA,N_UNIDAD,ID_EMPRESA) VALUES('Placa" + i + "'," + i + ",'LN')";
                System.out.println("" + sql1);
                cb.ejecutarSentencia(sql1);
            }

            cb.CerrarConexion();

//        } catch (SQLException ex) {
//            Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
//        }

        //String txt = "|$$ g??������U020726.000,A,0358.6581,S,070,,912.4230,W,24.33,48.91,02081,A*65|0.8|$$ h??������U020747";
        //for(int i=0; i<txt.length(); i++){
        //System.out.println("" + (int)txt.charAt(i));
        //}
    }
}
