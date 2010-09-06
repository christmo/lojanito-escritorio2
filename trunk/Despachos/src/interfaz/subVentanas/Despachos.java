package interfaz.subVentanas;

/**
 *
 * @author root
 */
public class Despachos {

    private String strHora;
    private String strTelefono;
    private int intCodigo;
    private String strNombre;
    private String strDireccion;
    private String strBarrio;
    private String strNumeroCasa;
    private String strReferecia;
    private int intMinutos;
    private int intUnidad;
    private int intAtraso;
    private String strNota;
    private int intID_Turno;
    private String strEstado;
    private String strUsuario;
    private String strFecha;
    private double latitud;
    private double longitud;

    public Despachos() {
    }

    public Despachos(String hora,
            String tel,
            String cod,
            String nom,
            String dir,
            String barrio,
            int minutos,
            int unidad,
            int atraso,
            String nota,
            int id_turno,
            String usuario) {
        this.strHora = hora;
        this.strTelefono = tel;
        try {
            this.intCodigo = Integer.parseInt(cod);
        } catch (NumberFormatException ex) {
            this.intCodigo = 0;
        }
        this.strNombre = nom;
        this.strDireccion = dir;
        this.strBarrio = barrio;
        this.intMinutos = minutos;
        this.intUnidad = unidad;
        this.intAtraso = atraso;
        this.strNota = nota;
        this.intID_Turno = id_turno;
        this.strUsuario = usuario;
    }

    public Despachos(String hora, String tel, String cod, String nom, String dir, String barrio, String nota) {
        this.strHora = hora;
        this.strTelefono = tel;
        try {
            this.intCodigo = Integer.parseInt(cod);
        } catch (NumberFormatException ex) {
            this.intCodigo = 0;
        }
        this.strNombre = nom;
        this.strDireccion = dir;
        this.strBarrio = barrio;
        this.strNota = nota;
    }

    public Despachos(String cod_cliente,
            String fecha,
            String hora,
            String tel,
            String nom,
            String dir,
            String barrio,
            String min,
            String unidad,
            String atraso,
            String nota,
            String estado,
            String id_turno,
            String usuario) {
        try {
            this.intCodigo = Integer.parseInt(cod_cliente);
        } catch (NumberFormatException ex) {
            this.intCodigo = 0;
        }
        this.strFecha = fecha;
        this.strHora = hora;
        this.strTelefono = tel;
        this.strNombre = nom;
        this.strDireccion = dir;
        this.strBarrio = barrio;
        try {
            this.intMinutos = Integer.parseInt(min);
        } catch (NumberFormatException ex) {
            this.intMinutos = 0;
        }
        try {
            this.intUnidad = Integer.parseInt(unidad);
        } catch (NumberFormatException ex) {
            this.intUnidad = 0;
        }
        try {
            this.intAtraso = Integer.parseInt(atraso);
        } catch (NumberFormatException ex) {
            this.intAtraso = 0;
        }
        this.strNota = nota;
        this.strEstado = estado;
        try {
            this.intID_Turno = Integer.parseInt(id_turno);
        } catch (NumberFormatException ex) {
            this.intID_Turno = 0;
        }
        this.strUsuario = usuario;
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
     * @return the intCodigo
     */
    public int getIntCodigo() {
        return intCodigo;
    }

    /**
     * @param intCodigo the intCodigo to set
     */
    public void setIntCodigo(int intCodigo) {
        this.intCodigo = intCodigo;
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

    /**
     * @return the intID_Turno
     */
    public int getIntID_Turno() {
        return intID_Turno;
    }

    /**
     * @param intID_Turno the intID_Turno to set
     */
    public void setIntID_Turno(int intID_Turno) {
        this.intID_Turno = intID_Turno;
    }

    /**
     * @return the strUsuario
     */
    public String getStrUsuario() {
        return strUsuario;
    }

    /**
     * @param strUsuario the strUsuario to set
     */
    public void setStrUsuario(String strUsuario) {
        this.strUsuario = strUsuario;
    }

    /**
     * @return the strFecha
     */
    public String getStrFecha() {
        return strFecha;
    }

    /**
     * @param strFecha the strFecha to set
     */
    public void setStrFecha(String strFecha) {
        this.strFecha = strFecha;
    }

    /**
     * @return the strEstado
     */
    public String getStrEstado() {
        return strEstado;
    }

    /**
     * @param strEstado the strEstado to set
     */
    public void setStrEstado(String strEstado) {
        this.strEstado = strEstado;
    }
}
