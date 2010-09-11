/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.comunicacion.mapa;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class sock {
    private static int port=200;

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(port);
            while (true) {
                try {
                    Socket s = ss.accept();
                    Writer out = new OutputStreamWriter(s.getOutputStream());
                    out = new BufferedWriter(out);
                    out.write("Hello " + s.getInetAddress() + " on port " + s.getPort() + "\r\n");
                    out.write("This is " + s.getLocalAddress() + " on port " + s.getLocalPort() + "\r\n");
                    out.flush();
                    s.close();
                } catch (IOException ex) {
                    // skip this connection; continue with the next
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(sock.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
