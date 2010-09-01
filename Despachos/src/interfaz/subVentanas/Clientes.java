/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.subVentanas;

/**
 *
 * @author root
 */
public class Clientes {
    private String nombre;
    private String telefono;
    private String codigo;
    private String Direccion;
    private String barrio;
    private String N_casa;
    private String Referencia;
    private long latitud;
    private long longitud;

    public Clientes() {
    }

    public Clientes(String nombre, String telefono, String codigo, String Direccion, String barrio, String N_casa, String Referencia) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.codigo = codigo;
        this.Direccion = Direccion;
        this.barrio = barrio;
        this.N_casa = N_casa;
        this.Referencia = Referencia;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the Direccion
     */
    public String getDireccion() {
        return Direccion;
    }

    /**
     * @param Direccion the Direccion to set
     */
    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    /**
     * @return the barrio
     */
    public String getBarrio() {
        return barrio;
    }

    /**
     * @param barrio the barrio to set
     */
    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    /**
     * @return the N_casa
     */
    public String getN_casa() {
        return N_casa;
    }

    /**
     * @param N_casa the N_casa to set
     */
    public void setN_casa(String N_casa) {
        this.N_casa = N_casa;
    }

    /**
     * @return the Referencia
     */
    public String getReferencia() {
        return Referencia;
    }

    /**
     * @param Referencia the Referencia to set
     */
    public void setReferencia(String Referencia) {
        this.Referencia = Referencia;
    }

    /**
     * @return the latitud
     */
    public long getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(long latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the longitud
     */
    public long getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(long longitud) {
        this.longitud = longitud;
    }
}
