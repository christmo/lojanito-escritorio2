/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import interfaz.Principal;
import interfaz.subVentanas.Despachos;

/**
 *
 * @author Usuario
 */
public class GuardarServidorKRADAC extends Thread {

    private ConexionBase bd;
    private Despachos desp;
    private boolean accion;

    /**
     * Guarda por separado los datos que van al servidor
     * @param bd
     * @param d
     * @param accion || true ASIGNACION || false LIBRE
     */
    public GuardarServidorKRADAC(Despachos d, boolean accion) {
        this.bd = new ConexionBase(Principal.arcConfig);
        this.desp = d;
        this.accion = accion;
    }

    @Override
    public void run() {
        //true ASIGNACION || false LIBRE
        if (accion) {
            InsertarAsignacionServidorKRADAC();
        } else {
            InsertarLibreServidorKRADAC();
        }
    }

    /**
     * Inserta la asignasion en el servidor kradac
     */
    public void InsertarAsignacionServidorKRADAC() {
        String sql = "INSERT INTO server(N_UNIDAD,COD_CLIENTE,ESTADO,HORA,FONO) VALUES ("
                + desp.getIntUnidad()
                + ","
                + desp.getIntCodigo()
                + ",'ASIGNADO',"
                + desp.getMinutosEntreClienteServidor()
                + ",'"
                + desp.getStrTelefono()
                + "');";
        bd.ejecutarSentencia(sql);
        System.err.println("KRADAC: " + sql);
        InsertarDespachoServidorKRADAC();
        bd.CerrarConexion();
    }

    /**
     * Inserta luego de asignado el ocupado del taxi cuando se despacho
     */
    public void InsertarDespachoServidorKRADAC() {
        String sql = "INSERT INTO server(N_UNIDAD,COD_CLIENTE,ESTADO,HORA,FONO) VALUES ("
                + desp.getIntUnidad() + "," + desp.getIntCodigo() + ",'OCUPADO'," + "-1" + ",'"
                + desp.getStrTelefono()
                + "');";
        System.err.println("KRADAC: " + sql);
        bd.ejecutarSentencia(sql);
    }

    /**
     * Inserta luego de asignado el ocupado del taxi cuando se despacho
     */
    public void InsertarLibreServidorKRADAC() {
        System.out.println("Enviar datos al Server...");
        String sql = "INSERT INTO server(N_UNIDAD,COD_CLIENTE,ESTADO,HORA,FONO) VALUES ("
                + desp.getIntUnidad() + "," + desp.getIntCodigo() + ",'LIBRE'," + "-2" + ",'"
                + desp.getStrTelefono()
                + "');";
        System.err.println("KRADAC: " + sql);
        bd.ejecutarSentencia(sql);
    }
}
