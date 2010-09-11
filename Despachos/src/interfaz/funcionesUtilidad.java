/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.comm.CommPortIdentifier;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Usuario
 */
public class funcionesUtilidad {

    /**
     * Verifica si la cédula ingresada es válida
     * @param cedula
     * @return
     */
    public boolean esCedulaValida(String cedula) {

        int NUMERO_DE_PROVINCIAS = 24;
        if (!((cedula.length() == 10) && cedula.matches("^[0-9]{10}$"))) {
            return false;
        }
        int prov = Integer.parseInt(cedula.substring(0, 2));

        if (!((prov > 0) && (prov <= NUMERO_DE_PROVINCIAS))) {
            return false;
        }

        int[] d = new int[10];

        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.parseInt(cedula.charAt(i) + "");
        }

        int imp = 0;
        int par = 0;

        for (int i = 0; i < d.length; i += 2) {
            d[i] = ((d[i] * 2) > 9) ? ((d[i] * 2) - 9) : (d[i] * 2);
            imp += d[i];
        }

        for (int i = 1; i < (d.length - 1); i += 2) {
            par += d[i];
        }

        int suma = imp + par;

        int d10 = Integer.parseInt(String.valueOf(suma + 10).substring(0, 1)
                + "0") - suma;

        d10 = (d10 == 10) ? 0 : d10;

        return d10 == d[9];
    }

    /**
     * Carga una imagen a la aplicación presentando
     * su nombre en el campo Foto.
     */
    public File cargarImagen(JLabel name, JLabel img) {
        JFileChooser fileChooser = new JFileChooser();

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de Imagen",
                "jpg",
                "jpeg",
                "bmp",
                "png",
                "gif");
        fileChooser.setFileFilter(filter);

        int seleccion = fileChooser.showOpenDialog(name);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File fichero = fileChooser.getSelectedFile();
            name.setText(fichero.getName());
            Icon fot = new ImageIcon(fichero.getAbsolutePath());
            File Ffoto = fichero;
            img.setIcon(fot);
            img.setText("");
            return Ffoto;
        }
        return null;
    }

    /**
     * Valida si una entrada es un correo válido
     * @param correo Valor a comparar
     * @return
     */
    public boolean isEmail(String correo) {
        Pattern pat = null;
        Matcher mat = null;
        pat = Pattern.compile("[a-zA-Z0-9_-].*{2,}@[a-zA-Z0-9_-]{2,}\\.[a-zA-Z]{2,4}(\\.[a-zA-Z]{2,4})?$");
        mat = pat.matcher(correo);
        if (mat.find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Comprueba si una cadena es un número válido
     * @param cadena
     * @return
     */
    public boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Comprueba si una cadena es un número válido
     * @param cadena
     * @return
     */
    public boolean isDouble(String cadena) {
        try {
            Double.parseDouble(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Guarda la imagen en un directorio predeterminado
     * con un nombre único generado automaticamente.
     * @param ruta Origen de la foto
     * @param foto Destino de la foto
     */
    public String guardarImagen(File foto, String ruta) {
        String nombreFoto = null;
        try {
            FileInputStream fis = null;
            fis = new FileInputStream(foto);
            // la ruta va con doble slash \\
            Calendar nombreUnico = new GregorianCalendar();
            nombreUnico.setTimeInMillis(System.currentTimeMillis());
            nombreFoto =
                    String.valueOf(nombreUnico.get(Calendar.YEAR))
                    + String.valueOf(nombreUnico.get(Calendar.MONTH))
                    + String.valueOf(nombreUnico.get(Calendar.DAY_OF_MONTH))
                    + String.valueOf(nombreUnico.get(Calendar.HOUR_OF_DAY))
                    + String.valueOf(nombreUnico.get(Calendar.MINUTE))
                    + String.valueOf(nombreUnico.get(Calendar.SECOND));

            FileOutputStream fos = new FileOutputStream(ruta + "\\" + nombreFoto + ".jpg");
            FileChannel canalFuente = fis.getChannel();
            FileChannel canalDestino = fos.getChannel();
            canalFuente.transferTo(0, canalFuente.size(), canalDestino);
            fis.close();
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(funcionesUtilidad.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return nombreFoto + ".jpg";
    }

    /**
     * Devuelve hora y minutos actuales del sistema
     * @return String
     */
    public String getHora() {
        Calendar calendario = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
        String[] txtHoraMinutos = sdf.format(calendario.getTime()).split(":");
        /*String txtHora = "";
        for (String hora : txtHoraMinutos) {
            if (hora.length() == 1) {
                txtHora += "0" + hora;
            } else {
                txtHora += hora;
            }
        }
        return txtHora.substring(0, 2) + ":" + txtHora.substring(2);*/
        System.out.println("Hora: "+sdf.format(calendario.getTime()));
        return sdf.format(calendario.getTime());
    }

    /**
     * Devuelve hora y minutos actuales del sistema en milisegundos
     * @return String
     */
    public long getHoraEnMilis() {
        Calendar calendario = new GregorianCalendar();
        return calendario.getTimeInMillis();
    }

    /**
     * Trae la fecha actual
     * @return String
     */
    public String getFecha() {
        Calendar calendario = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendario.getTime());
    }

    /**
     * Comprueba si el numero de telefono ingresado tiene el 0 al inicio o si el
     * el numero de telefono no tiene letras
     * @param tel
     * @return String
     */
    public String validarTelefono(String tel) {
        int lon = tel.length();
        if (lon == 9) {
            if (isNumeric(tel)) {
                return tel;
            } else {
                return "";
            }
        } else if (lon == 8) {
            return "0" + tel;
        } else if (lon == 7) {
            return "07" + tel;
        } else if (lon == 6) {
            return "072" + tel;
        } else {
            return "";
        }
    }

    /**
     * Valida si el numero de telefono tiene 9 caracteres
     * @param text
     * @return boolean
     */
    public boolean validarTel(String text) {
        if (text.length() == 9) {
            System.out.println("tiene 9");
            return true;
        } else {
            System.out.println("NO tiene 9");
            return false;
        }
    }

    /**
     * Obtiene el password de las cajas de password que lo extraen en
     * vector de char
     * @param chrPass
     * @return String
     */
    public String getPasswordDesdeChar(char[] chrPass) {
        String strPass = "";

        for (int i = 0; i < chrPass.length; i++) {
            strPass += chrPass[i];
        }
        return strPass;
    }
    /**
     * Communicacion Serial
     */
    private Enumeration listaPuertos;
    private CommPortIdentifier id_Puerto;

    /**
     * Muestra la lista de puertos seriales y paralelos del equipo
     */
    public String[] ListaPuertos() {
        listaPuertos = CommPortIdentifier.getPortIdentifiers();
        String[] puertos = null;
        ArrayList p = new ArrayList();
        int i = 0;
        while (listaPuertos.hasMoreElements()) {
            id_Puerto = (CommPortIdentifier) listaPuertos.nextElement();
            //System.out.println("Id: " + id_Puerto.getName() + " tipo: " + id_Puerto.getPortType());
            if(id_Puerto.getPortType()==1){
                p.add(id_Puerto.getName());
            }
            i++;
        }
        puertos = new String[p.size()];
        return (String[])p.toArray(puertos);
    }
}
