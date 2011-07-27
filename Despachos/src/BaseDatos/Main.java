/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Main.java
 *
 * @author www.javadb.com
 */
public class Main {

    /**
     * Extends the size of an array.
     */
    public void sendPostRequest(ConexionBase bd) {

        int i = 100000;
        String sql = "";
        while (i <= 999999) {
            //Build parameter string
            String data = "?hflagsubmit=1&cmbcriterio=1&cmbprovincia=7&txttelefono=3" + i;
            try {
                //http://www.cnt.gob.ec/cntapp/guia104/php/guia_cntat.php?hflagsubmit=1&cmbcriterio=1&cmbprovincia=7&txttelefono=2584510
                // Send the request
                URL url = new URL("http://www.cnt.gob.ec/cntapp/guia104/php/guia_cntat.php" + data);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

                //write parameters
                //writer.write(data);
                //writer.flush();

                // Get the response
                StringBuffer answer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                String[] lineaLocalidad;
                String[] campos;
                System.out.println("[" + i + "]");
                while ((line = reader.readLine()) != null) {
                    //answer.append(line);
                    try {
                        lineaLocalidad = line.split("Localidad");
                        // System.out.println("[" + line.length() + "]" + line.substring(line.length() - 250, line.length()));
                        //System.out.println("" + lineaLocalidad[1]);
                        campos = lineaLocalidad[1].split("<td>");
                        System.out.println("" + campos[1].replace("</td>", "") + "\n" + campos[2].replace("</td>", "") + "\n" + campos[3].replace("</td>", ""));
                        sql = "INSERT INTO CNT_LOJA VALUES('" + campos[1].replace("</td>", "") + "','" + campos[2].replace("</td>", "") + "','" + campos[3].replace("</td>", "") + "')";
                        bd.ejecutarSentencia(sql);
                    } catch (IndexOutOfBoundsException ex) {
                    }
                }
                writer.close();
                reader.close();
                i++;
                //Output the response
                //System.out.println(answer.toString());
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    /**
     * Starts the program
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConexionBase bd = new ConexionBase("localhost", "rastreosatelital", "root", "");
        new Main().sendPostRequest(bd);
    }
}
