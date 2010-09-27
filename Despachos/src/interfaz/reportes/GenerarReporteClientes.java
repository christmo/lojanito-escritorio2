/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz.reportes;

import BaseDatos.ConexionBase;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author christmo
 */
public class GenerarReporteClientes {

    private ConexionBase bd;
    private HashMap campos;
    private String[] sesion;
    private String empresa;
    private String usuario;
    private InputStream RutaJasper;

    public GenerarReporteClientes(ConexionBase cb, HashMap camp, String[] ses) {
        this.bd = cb;
        this.campos = camp;
        this.sesion = ses;
        this.empresa = bd.getEmpresa(sesion[1]);
        this.usuario = sesion[2];
        RutaJasper = getClass().getResourceAsStream("plantillas/Clientes.jrxml");
    }

    /**
     * Genera segun los campos que se haya llenado
     */
    public void Generar() {
        if (campos.get("todo").toString().equals("true")) {
            System.out.println("" + campos.get("todo"));
            GenerarTodosLosClientes();
        } else {
            if (!campos.get("cod").equals("")) {
                System.out.println("" + campos.get("cod"));
                GenerarPorCodigo();
            } else {
                if (!campos.get("tel").equals("")) {
                    System.out.println("" + campos.get("tel"));
                    GenerarPorTelefono();
                } else {
                    if (!campos.get("nom").equals("")) {
                        System.out.println("" + campos.get("nom"));
                        GenerarPorNombre();
                    }
                }
            }
        }

    }

    /**
     * Generar el reporte de los clientes por un codigo determinado solo se obtendra
     * un serultado si existe...
     */
    private void GenerarPorCodigo() {
        String cod = campos.get("cod").toString();
        String sqlCodigo = "SELECT"
                + " CLIENTES.`TELEFONO` AS CLIENTES_TELEFONO,"
                + " CLIENTES.`CODIGO` AS CLIENTES_CODIGO,"
                + " CLIENTES.`NOMBRE_APELLIDO_CLI` AS CLIENTES_NOMBRE_APELLIDO_CLI,"
                + " CLIENTES.`DIRECCION_CLI` AS CLIENTES_DIRECCION_CLI,"
                + " CLIENTES.`SECTOR` AS CLIENTES_SECTOR,"
                + " CLIENTES.`INFOR_ADICIONAL` AS CLIENTES_INFOR_ADICIONAL,"
                + " CLIENTES.`NUM_CASA_CLI` AS CLIENTES_NUM_CASA_CLI "
                + " FROM"
                + " `CLIENTES` CLIENTES"
                + " WHERE CLIENTES.`CODIGO` = $P{cod}";

        System.out.println("SQL: " + sqlCodigo);

        Map parametro = new HashMap();
        parametro.put("sql", sqlCodigo);
        parametro.put("cod", Integer.parseInt(cod));
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Generar el reporte de los clientes por un telefono determinado solo se obtendra
     * un serultado si existe...
     */
    private void GenerarPorTelefono() {
        String telefono = campos.get("tel").toString();
        String sqlTelefono = "SELECT"
                + " CLIENTES.`TELEFONO` AS CLIENTES_TELEFONO,"
                + " CLIENTES.`CODIGO` AS CLIENTES_CODIGO,"
                + " CLIENTES.`NOMBRE_APELLIDO_CLI` AS CLIENTES_NOMBRE_APELLIDO_CLI,"
                + " CLIENTES.`DIRECCION_CLI` AS CLIENTES_DIRECCION_CLI,"
                + " CLIENTES.`SECTOR` AS CLIENTES_SECTOR,"
                + " CLIENTES.`INFOR_ADICIONAL` AS CLIENTES_INFOR_ADICIONAL,"
                + " CLIENTES.`NUM_CASA_CLI` AS CLIENTES_NUM_CASA_CLI "
                + " FROM"
                + " `CLIENTES` CLIENTES"
                + " WHERE CLIENTES.`TELEFONO` = '$P!{tel}'";

        System.out.println("SQL: " + sqlTelefono);

        Map parametro = new HashMap();
        parametro.put("sql", sqlTelefono);
        parametro.put("tel", telefono);
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Genera el reporte de todos los clientes que tengan ese nombre
     */
    private void GenerarPorNombre() {
        String nombre = campos.get("nom").toString();
        String sqlNombre = "SELECT"
                + " CLIENTES.`TELEFONO` AS CLIENTES_TELEFONO,"
                + " CLIENTES.`CODIGO` AS CLIENTES_CODIGO,"
                + " CLIENTES.`NOMBRE_APELLIDO_CLI` AS CLIENTES_NOMBRE_APELLIDO_CLI,"
                + " CLIENTES.`DIRECCION_CLI` AS CLIENTES_DIRECCION_CLI,"
                + " CLIENTES.`SECTOR` AS CLIENTES_SECTOR,"
                + " CLIENTES.`INFOR_ADICIONAL` AS CLIENTES_INFOR_ADICIONAL,"
                + " CLIENTES.`NUM_CASA_CLI` AS CLIENTES_NUM_CASA_CLI "
                + " FROM"
                + " `CLIENTES` CLIENTES"
                + " WHERE CLIENTES.`NOMBRE_APELLIDO_CLI` LIKE '$P!{nom}%'";

        System.out.println("SQL: " + sqlNombre);

        Map parametro = new HashMap();
        parametro.put("sql", sqlNombre);
        parametro.put("nom", nombre);
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Genera la lista de todos los clientes de la empresa
     */
    private void GenerarTodosLosClientes() {
        String sql = "SELECT"
                + " CLIENTES.`TELEFONO` AS CLIENTES_TELEFONO,"
                + " CLIENTES.`CODIGO` AS CLIENTES_CODIGO,"
                + " CLIENTES.`NOMBRE_APELLIDO_CLI` AS CLIENTES_NOMBRE_APELLIDO_CLI,"
                + " CLIENTES.`DIRECCION_CLI` AS CLIENTES_DIRECCION_CLI,"
                + " CLIENTES.`SECTOR` AS CLIENTES_SECTOR,"
                + " CLIENTES.`INFOR_ADICIONAL` AS CLIENTES_INFOR_ADICIONAL,"
                + " CLIENTES.`NUM_CASA_CLI` AS CLIENTES_NUM_CASA_CLI "
                + " FROM"
                + " `CLIENTES` CLIENTES"
                + " ORDER BY CLIENTES.`CODIGO` ASC";

        System.out.println(sql);

        Map parametro = new HashMap();
        parametro.put("sql", sql);
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }
}
