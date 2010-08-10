
package interfaz.subVentanas;

/**
 *
 * @author root
 */
public class Despachos {

    private String strHora;
    private String strTelefono;
    private String strCodigo;
    private String strNombre;
    private String strDireccion;
    private String strBarrio;
    private String strNumeroCasa;
    private String strReferecia;
    private int intMinutos;
    private int intUnidad;
    private int intAtraso;
    private String strNota;

    private double latitud;
    private double longitud;

    public Despachos() {
    }

    public Despachos(String hora, String tel, String cod, String nom, String dir,
            String barrio, int minutos, int unidad, int atraso, String nota) {
        this.strHora = hora;
        this.strTelefono = tel;
        this.strCodigo = cod;
        this.strNombre = nom;
        this.strDireccion = dir;
        this.strBarrio = barrio;
        this.intMinutos = minutos;
        this.intUnidad = unidad;
        this.intAtraso = atraso;
        this.strNota = nota;
    }

    public Despachos(String tel, String cod, String nom, String dir, String barrio, String nota) {
        this.strTelefono = tel;
        this.strCodigo = cod;
        this.strNombre = nom;
        this.strDireccion = dir;
        this.strBarrio = barrio;
        this.strNota = nota;
    }

    public Despachos(String hora, String tel, String cod, String nom, String dir, String barrio, String nota) {
        this.strHora = hora;
        this.strTelefono = tel;
        this.strCodigo = cod;
        this.strNombre = nom;
        this.strDireccion = dir;
        this.strBarrio = barrio;
        this.strNota = nota;
    }

    /**
     * @return the strHora
     */
    public String getStrHora() {
        return strHora;
    }

    /**
     * @param strHora the strHora to set
     */
    public void setStrHora(String strHora) {
        this.strHora = strHora;
    }

    /**
     * @return the strTelefono
     */
    public String getStrTelefono() {
        return strTelefono;
    }

    /**
     * @param strTelefono the strTelefono to set
     */
    public void setStrTelefono(String strTelefono) {
        this.strTelefono = strTelefono;
    }

    /**
     * @return the strCodigo
     */
    public String getStrCodigo() {
        return strCodigo;
    }

    /**
     * @param strCodigo the strCodigo to set
     */
    public void setStrCodigo(String strCodigo) {
        this.strCodigo = strCodigo;
    }

    /**
     * @return the strNombre
     */
    public String getStrNombre() {
        return strNombre;
    }

    /**
     * @param strNombre the strNombre to set
     */
    public void setStrNombre(String strNombre) {
        this.strNombre = strNombre;
    }

    /**
     * @return the strDireccion
     */
    public String getStrDireccion() {
        return strDireccion;
    }

    /**
     * @param strDireccion the strDireccion to set
     */
    public void setStrDireccion(String strDireccion) {
        this.strDireccion = strDireccion;
    }

    /**
     * @return the strBarrio
     */
    public String getStrBarrio() {
        return strBarrio;
    }

    /**
     * @param strBarrio the strBarrio to set
     */
    public void setStrBarrio(String strBarrio) {
        this.strBarrio = strBarrio;
    }

    /**
     * @return the strNumeroCasa
     */
    public String getStrNumeroCasa() {
        return strNumeroCasa;
    }

    /**
     * @param strNumeroCasa the strNumeroCasa to set
     */
    public void setStrNumeroCasa(String strNumeroCasa) {
        this.strNumeroCasa = strNumeroCasa;
    }

    /**
     * @return the strReferecia
     */
    public String getStrReferecia() {
        return strReferecia;
    }

    /**
     * @param strReferecia the strReferecia to set
     */
    public void setStrReferecia(String strReferecia) {
        this.strReferecia = strReferecia;
    }

    /**
     * @return the intMinutos
     */
    public int getIntMinutos() {
        return intMinutos;
    }

    /**
     * @param intMinutos the intMinutos to set
     */
    public void setIntMinutos(int intMinutos) {
        this.intMinutos = intMinutos;
    }

    /**
     * @return the intUnidad
     */
    public int getIntUnidad() {
        return intUnidad;
    }

    /**
     * @param intUnidad the intUnidad to set
     */
    public void setIntUnidad(int intUnidad) {
        this.intUnidad = intUnidad;
    }

    /**
     * @return the intAtraso
     */
    public int getIntAtraso() {
        return intAtraso;
    }

    /**
     * @param intAtraso the intAtraso to set
     */
    public void setIntAtraso(int intAtraso) {
        this.intAtraso = intAtraso;
    }

    /**
     * @return the strNota
     */
    public String getStrNota() {
        return strNota;
    }

    /**
     * @param strNota the strNota to set
     */
    public void setStrNota(String strNota) {
        this.strNota = strNota;
    }

    /**
     * @return the longitud
     */
    public double getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the latitud
     */
    public double getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
}
