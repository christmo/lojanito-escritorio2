/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author root
 */
public class TCP extends Thread {

    static final int PUERTO = 666;
    public InputStream is;
    public OutputStream os;

    public TCP() {

        this.start();

    }

    @Override
    public void run() {
        try {
            ServerSocket skServidor = new ServerSocket(PUERTO);

            System.out.println("Escucho el puerto " + PUERTO);

            Socket skCliente = skServidor.accept();
            PrintWriter out = new PrintWriter(skCliente.getOutputStream(), true);
            String datos = "";

            int i=1;

            while (true) {

                is = skCliente.getInputStream();

                i=is.read();

                
                //System.out.println("\n");

                //datos += (char) is.read();
                System.out.println((char)i+" EX: " + Integer.toHexString(i) + " Binar: "+Integer.toBinaryString(i));

                
                //System.out.println(""+datos.getBytes());
                //String utfStr = new String(datos.getBytes("UTF-8"), "UTF-8");
                 //System.out.println("Conversion: "+utfStr);
                //System.out.print("Sending string: '" + datos + "'\n");
                //out.print(datos);

            }

            //System.out.println("Demasiados clientes por hoy");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] arg) {
        new TCP();
    }
}
