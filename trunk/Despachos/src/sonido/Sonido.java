/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sonido;

import interfaz.funcionesUtilidad;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author kradac
 */
public class Sonido extends Thread {

    private int segundos = 1;
    private String nombre = "";

    public Sonido(String nombreWAV, int seg) {
        segundos = seg;
        nombre = nombreWAV;
        this.start();
    }

    @Override
    public void run() {
        if (!nombre.equals("") || segundos != 0) {
            try {
                AudioInputStream source;
                source = funcionesUtilidad.obtenerArchivoSonidoWAV(nombre);
                try {
                    DataLine.Info info = new DataLine.Info(Clip.class, source.getFormat());
                    Clip clip = (Clip) AudioSystem.getLine(info);
                    clip.open(source);
                    clip.start();
                } catch (NullPointerException nex) {
                    System.out.println("No hay archivo de sonido... :-(");
                    Beep();
                }
                try {
                    Thread.sleep(segundos * 1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Sonido.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Sonido.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Sonido.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Beep();
        }
    }

    /**
     * Emite un sonido por defecto cuando no se encuentra el verdadero sonido
     * de pendientes
     */
    public void Beep() {
        Toolkit.getDefaultToolkit().beep();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Sonido.class.getName()).log(Level.SEVERE, null, ex);
        }
        Toolkit.getDefaultToolkit().beep();
    }
}
