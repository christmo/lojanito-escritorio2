/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.pruebas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z]{2,9}"
                + "[-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,4})$");
        mat = pat.matcher(correo);
        if (mat.find()) {
            System.out.println(correo);
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
        SimpleDateFormat sdf = new SimpleDateFormat("k:m");
        return sdf.format(calendario.getTime());
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
        } else {
            return "";
        }
    }
}
