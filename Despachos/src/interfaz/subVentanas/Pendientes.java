/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaz.subVentanas;

/**
 *
 * @author kradac
 */
public class Pendientes {
    private Clientes cliente;
    private String fechaIni;
    private String fechaFin;
    private String hora;
    private String nota;
    private int minRecuerdo;
    private String cuandoRecordar;
    private String estado;

    /**
     * @return the cliente
     */
    public Clientes getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the fechaIni
     */
    public String getFechaIni() {
        return fechaIni;
    }

    /**
     * @param fechaIni the fechaIni to set
     */
    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }

    /**
     * @return the fechaFin
     */
    public String getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * @param hora the hora to set
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * @return the nota
     */
    public String getNota() {
        return nota;
    }

    /**
     * @param nota the nota to set
     */
    public void setNota(String nota) {
        this.nota = nota;
    }

    /**
     * @return the minRecuerdo
     */
    public int getMinRecuerdo() {
        return minRecuerdo;
    }

    /**
     * @param minRecuerdo the minRecuerdo to set
     */
    public void setMinRecuerdo(int minRecuerdo) {
        this.minRecuerdo = minRecuerdo;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the cuandoRecordar
     */
    public String getCuandoRecordar() {
        return cuandoRecordar;
    }

    /**
     * @param cuandoRecordar the cuandoRecordar to set
     */
    public void setCuandoRecordar(String cuandoRecordar) {
        this.cuandoRecordar = cuandoRecordar;
    }
}
