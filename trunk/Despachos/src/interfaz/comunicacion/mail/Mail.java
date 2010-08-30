/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.comunicacion.mail;

/**
 *
 * @author Usuario
 */
public class Mail {

    public static void main(String[] args) {
        if (SMTPConfig.sendMail(
                "Desde Java Mail", //Asunto
                " Christian esta enviando esto desde la compu\n ya sabe.", //mensaje
                " soporte@kradac.com")) { //Para
            System.out.println("envío Correcto");
        } else {
            System.out.println("envío Fallido");
        }
    }
}
