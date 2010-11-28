/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.security.CodeSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Properties;
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

            //FileOutputStream fos = new FileOutputStream(ruta + "\\" + nombreFoto + ".jpg");
            FileOutputStream fos = new FileOutputStream(ruta + System.getProperty("file.separator") + nombreFoto + ".jpg");

            FileChannel canalFuente = fis.getChannel();
            FileChannel canalDestino = fos.getChannel();
            canalFuente.transferTo(0, canalFuente.size(), canalDestino);
            fis.close();
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(funcionesUtilidad.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (NullPointerException ex) {
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
            return true;
        } else {
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
            if (id_Puerto.getPortType() == 1) {
                p.add(id_Puerto.getName());
            }
            i++;
        }
        puertos = new String[p.size()];
        return (String[]) p.toArray(puertos);
    }

    /**
     * Procesa el mensaje que se va a enviar a la unidad para que no sobre pase
     * el numero maximo de caracteres a enviar. POR AHORA 100
     * @param mensaje
     * @return String
     */
    public String procesarMensaje(String mensaje) {
        int max = 100;//numero maximo de caracteres por mensaje
        String strMenAux = mensaje;

        strMenAux = eliminarEspaciosMayoresA1(strMenAux);
        strMenAux = eliminarEspaciosInicialesFinales(strMenAux);

        int intLonMensaje = strMenAux.length();

        if (intLonMensaje <= max) {
            return strMenAux;
        } else {
            strMenAux = limitarTamanoMensaje(mensaje, max);
            return strMenAux;
        }
    }

    /**
     * Limita el mensaje a un numero maximo de caracteres
     * @param mensaje
     * @param max
     * @return String
     */
    private String limitarTamanoMensaje(String mensaje, int max) {
        int lon = mensaje.length();
        String strTexto = "";
        if (lon > max) {
            for (int i = 0; i < max; i++) {
                strTexto += mensaje.charAt(i);
            }
        } else {
            strTexto = mensaje;
        }
        return strTexto;
    }

    /**
     * Elimina los espacios en blanco que pudieran encontrarse al inicio o al
     * final de un mensaje
     * @param mensaje
     * @return String
     */
    private String eliminarEspaciosInicialesFinales(String mensaje) {
        //Elimina todos los espacios que se encuentren al inicio o al final de la frace
        Pattern patron = Pattern.compile("^[ ]+|[ ]+$");
        Matcher encaja = patron.matcher(mensaje);
        String resultado = encaja.replaceAll("");
        return resultado;
    }

    /**
     * Elimina todos los espacios que esten entre el texto del mensaje para solo
     * dejar uno entre las separaciones de las palabras
     * @param mensaje
     * @return String
     */
    private String eliminarEspaciosMayoresA1(String mensaje) {
        //Elimina todos los espacios mayores a 1 que se encuentren dentro del mensaje
        Pattern patron = Pattern.compile("[ ]+");
        Matcher encaja = patron.matcher(mensaje);
        String resultado = encaja.replaceAll(" ");
        return resultado;
    }

    //Principal
    public String encriptar(String clave, String semil) {
        try {
            String md5Clave = MD5(MD5(MD5(clave) + MD5(semil)));
            return md5Clave;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(funcionesUtilidad.class.getName()).log(Level.SEVERE, null, ex);
            return clave;
        }
    }

    //Auxiliar
    private String MD5(String clave) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(clave.getBytes(), 0, clave.length());
        String md5 = new BigInteger(1, md.digest()).toString(16);
        return md5;
    }

    /**
     * Trae el archivo properties que se encuentre en el direcctorio del jar
     * @param arc -> "configsystem.properties" nombre del archivo properties
     * @return Properties
     */
    public static Properties obtenerArchivoPropiedades(String arc) {
        Properties prop = null;
        try {
            CodeSource codeSource = funcionesUtilidad.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            File jarDir = jarFile.getParentFile();

            if (jarDir != null && jarDir.isDirectory()) {
                File propFile = new File(jarDir, arc);
                prop = new Properties();
                prop.load(new BufferedReader(new FileReader(propFile.getAbsoluteFile())));
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(funcionesUtilidad.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            System.err.println("No se encuentra el archivo: " + ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(funcionesUtilidad.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prop;
    }
}
