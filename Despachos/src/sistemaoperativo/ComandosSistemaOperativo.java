/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaoperativo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 * Bajar y subir servicios remotamente desde el sistema de despachos
 * @author kradac
 */
public class ComandosSistemaOperativo extends Thread {

    private String cmd;

    /**
     * Enviar un comando para que se ejecute en la consola de win por ahora
     * @param comando
     */
    public ComandosSistemaOperativo(String comando) {
        this.cmd = comando;
        this.start();
    }

    @Override
    public void run() {
        SwingWorker hilo = new SwingWorker() {

            @Override
            protected Object doInBackground() throws Exception {
                if (System.getProperty("os.name").equals("Linux")) {
                } else {//Windows
                    enviarComandoConsolaWindows(cmd + "\n");
                    //promtConsolaWindows(cmd + "\n");
                }
                return null;
            }
        };

        hilo.execute();
    }

    /**
     * Envia comandos a la consola de windows no imprime los resultados de la
     * operacion utilizar para levantar servicios
     */
    public static void enviarComandoConsolaWindows(String comando) {
        OutputStream stdin = null;
        InputStream stdout = null;
        InputStream stderr = null;
        String line = comando;
        try {
            Process process = Runtime.getRuntime().exec("cmd.exe");
            stdin = process.getOutputStream();
            stdout = process.getInputStream();
            stderr = process.getErrorStream();
            stdin.write(line.getBytes());
            stdin.flush();
            stdin.close();

            BufferedReader brCleanUp = new BufferedReader(new InputStreamReader(stdout));
            while ((comando = brCleanUp.readLine()) != null) {
                System.out.println(""+comando);
                if (comando.equals("El servicio solicitado ya ha sido iniciado.")) {
                    System.out.println("El servicio solicitado ya ha sido iniciado:" + line);
                } else if (comando.equals("\"net\" no se reconoce como un comando interno o externo,")) {
                    System.out.println("\"net\" no se reconoce como un comando interno o externo,");
                } else if (comando.equals("El servicio de wampapache no ha podido iniciarse.")) {
                    System.out.println("El servicio de wampapache no ha podido iniciarse.");
                } else if (comando.equals("El servicio de wampmysqld no ha podido iniciarse.")) {
                    System.out.println("El servicio de wampmysqld no ha podido iniciarse.");
                }
            }
            brCleanUp = new BufferedReader(new InputStreamReader(stderr));
            while ((comando = brCleanUp.readLine()) != null) {
                if (comando.equals("El servicio solicitado ya ha sido iniciado.")) {
                    System.out.println("El servicio solicitado ya ha sido iniciado:" + line);
                } else if (comando.equals("\"net\" no se reconoce como un comando interno o externo,")) {
                    System.out.println("\"net\" no se reconoce como un comando interno o externo,");
                } else if (comando.equals("El servicio de wampapache no ha podido iniciarse.")) {
                    System.out.println("El servicio de wampapache no ha podido iniciarse.");
                } else if (comando.equals("El servicio de wampmysqld no ha podido iniciarse.")) {
                    System.out.println("El servicio de wampmysqld no ha podido iniciarse.");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ComandosSistemaOperativo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Ejecuta el comando en la consola por ahora solo comando que no retornen
     * nada
     * @param comando
     */
    private void promtConsolaWindows(String comando) {
        OutputStream stdin = null;
        InputStream stderr = null;
        InputStream stdout = null;
        try {
            // launch EXE and grab stdin/stdout and stderr
            Process process = Runtime.getRuntime().exec("cmd.exe");
            stdin = process.getOutputStream();
            stderr = process.getErrorStream();
            stdout = process.getInputStream();
            stdin.write(comando.getBytes());
            stdin.flush();
            stdin.close();
            // clean up if any output in stdout
            BufferedReader brCleanUp = new BufferedReader(new InputStreamReader(stdout));
            while ((comando = brCleanUp.readLine()) != null) {
                System.out.println("[Stdout] " + comando);
            }
            brCleanUp.close();
            // clean up if any output in stderr
            brCleanUp = new BufferedReader(new InputStreamReader(stderr));
            while ((comando = brCleanUp.readLine()) != null) {
                System.out.println("[Stdout] " + comando);
            }
            brCleanUp.close();
        } catch (IOException ex) {
            Logger.getLogger(ComandosSistemaOperativo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
