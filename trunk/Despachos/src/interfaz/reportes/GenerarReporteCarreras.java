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
 * @author root
 */
public class GenerarReporteCarreras {

    private ConexionBase bd;
    private HashMap campos;
    private String[] sesion;
    private String empresa;
    private String usuario;
    private InputStream RutaJasper;

    public GenerarReporteCarreras(ConexionBase cb, HashMap camp, String[] ses) {
        this.bd = cb;
        this.campos = camp;
        this.sesion = ses;
        this.empresa = bd.getEmpresa(sesion[1]);
        this.usuario = sesion[2];
        try {
            if (campos.get("radio").toString().equals("empresa")) {
                RutaJasper = getClass().getResourceAsStream("plantillas/carreras/CarrerasEmpresa.jrxml");
            } else if (campos.get("radio").toString().equals("cliente")) {
                RutaJasper = getClass().getResourceAsStream("plantillas/carreras/CarrerasCliente.jrxml");
            } else if (campos.get("radio").toString().equals("unidad")) {
                RutaJasper = getClass().getResourceAsStream("plantillas/carreras/CarrerasUnidad.jrxml");
            }
        } catch (NullPointerException ex) {
        }
    }

    /**
     * Genera segun los campos que se haya llenado
     */
    public void Generar() {
        try {
            if (campos.get("radio").toString().equals("empresa")) {
                if (campos.get("todo").toString().equals("true")) {
                    GenerarTotalCarreras();
                } else {
                    GenerarTotalCarrerasMes();
                }
            } else if (campos.get("radio").toString().equals("cliente")) {
                if (campos.get("todo").toString().equals("true")) {
                    GenerarTotalCarrerasCliente();
                } else {
                    GenerarTotalCarrerasClienteMes();
                }
            } else if (campos.get("radio").toString().equals("unidad")) {
                if (campos.get("todo").toString().equals("true")) {
                    GenerarTotalCarrerasUnidad();
                } else {
                    GenerarTotalCarrerasUnidadMes();
                }
            }
        } catch (NullPointerException ex) {
        }
    }

    /*
     * Genera un reporte diario del total de carreras desde que empeso a funcionar
     * el sistema
     */
    private void GenerarTotalCarreras() {

        String sql = "SELECT "
                + "ASIGNADOS.`FECHA` AS ASIGNADOS_FECHA,"
                + "COUNT(*) AS TOTAL "
                + "FROM "
                + "`ASIGNADOS` ASIGNADOS "
                + "WHERE "
                + "ASIGNADOS.`ESTADO`='F' "
                + "GROUP BY ASIGNADOS_FECHA";

        System.out.println(sql);

        Map parametro = new HashMap();
        parametro.put("sql", sql);
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Genera un reporte de todos los dias de un mes determinado
     */
    private void GenerarTotalCarrerasMes() {

        String sql = "SELECT "
                + "ASIGNADOS.`FECHA` AS ASIGNADOS_FECHA,"
                + "COUNT(*) AS TOTAL "
                + "FROM "
                + "`ASIGNADOS` ASIGNADOS "
                + "WHERE "
                + "ASIGNADOS.`ESTADO`='F' "
                + "AND "
                + "MONTH(ASIGNADOS.`FECHA`) = '$P!{mes}' "
                + "GROUP BY ASIGNADOS_FECHA";

        System.out.println(sql);

        System.out.println("Mes: " + campos.get("NombreMes"));

        Map parametro = new HashMap();
        parametro.put("sql", sql);

        parametro.put("mes", campos.get("mes"));
        parametro.put("NombreMes", campos.get("NombreMes"));

        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    private void GenerarTotalCarrerasCliente() {

        String sql = "SELECT "
                + "ASIGNADOS.`COD_CLIENTE` AS CODIGO,"
                + "CLIENTES.`NOMBRE_APELLIDO_CLI` AS NOMBRE,"
                + "COUNT(*) AS TOTAL "
                + "FROM "
                + "`ASIGNADOS` ASIGNADOS, `CLIENTES` CLIENTES "
                + "WHERE ASIGNADOS.`ESTADO`='F' "
                + "AND ASIGNADOS.`COD_CLIENTE` = CLIENTES.`CODIGO` "
                + "GROUP BY CLIENTES.`NOMBRE_APELLIDO_CLI` "
                + "ORDER BY TOTAL DESC";

        System.out.println(sql);

        Map parametro = new HashMap();
        parametro.put("sql", sql);
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    private void GenerarTotalCarrerasClienteMes() {
        String sql = "SELECT "
                + "ASIGNADOS.`COD_CLIENTE` AS CODIGO,"
                + "CLIENTES.`NOMBRE_APELLIDO_CLI` AS NOMBRE,"
                + "COUNT(*) AS TOTAL "
                + "FROM "
                + "`ASIGNADOS` ASIGNADOS, `CLIENTES` CLIENTES "
                + "WHERE ASIGNADOS.`ESTADO`='F' "
                + "AND ASIGNADOS.`COD_CLIENTE` = CLIENTES.`CODIGO` "
                + "AND MONTH(ASIGNADOS.`FECHA`) = '$P!{mesCli}' "
                + "GROUP BY CLIENTES.`NOMBRE_APELLIDO_CLI` "
                + "ORDER BY TOTAL DESC";

        System.out.println(sql);

        System.out.println("Mes: " + campos.get("NombreMesCli"));

        Map parametro = new HashMap();
        parametro.put("sql", sql);

        parametro.put("mesCli", campos.get("mesCli"));
        parametro.put("NombreMesCli", campos.get("NombreMesCli"));

        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    private void GenerarTotalCarrerasUnidad() {
        String sql = "SELECT "
                + "ASIGNADOS.`N_UNIDAD` AS N_UNIDAD,"
                + "COND.`NOMBRE_APELLIDO_CON` AS NOMBRE,"
                + "COUNT(*) AS TOTAL "
                + "FROM "
                + "`ASIGNADOS` ASIGNADOS, `VEHICULOS` VEH, `CONDUCTORES` COND "
                + "WHERE ASIGNADOS.`ESTADO`='F' "
                + "AND ASIGNADOS.`N_UNIDAD` = VEH.`N_UNIDAD` "
                + "AND VEH.`ID_CON`=COND.`ID_CON` "
                + "GROUP BY ASIGNADOS.`N_UNIDAD` "
                + "ORDER BY ASIGNADOS.`N_UNIDAD` ASC";

        System.out.println(sql);

        Map parametro = new HashMap();
        parametro.put("sql", sql);
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    private void GenerarTotalCarrerasUnidadMes() {
        String sql = "SELECT "
                + "ASIGNADOS.`N_UNIDAD` AS N_UNIDAD,"
                + "COND.`NOMBRE_APELLIDO_CON` AS NOMBRE,"
                + "COUNT(*) AS TOTAL "
                + "FROM "
                + "`ASIGNADOS` ASIGNADOS, `VEHICULOS` VEH, `CONDUCTORES` COND "
                + "WHERE ASIGNADOS.`ESTADO`='F' "
                + "AND MONTH(ASIGNADOS.`FECHA`) = '$P!{mesUni}' "
                + "AND ASIGNADOS.`N_UNIDAD` = VEH.`N_UNIDAD` "
                + "AND VEH.`ID_CON`=COND.`ID_CON` "
                + "GROUP BY ASIGNADOS.`N_UNIDAD` "
                + "ORDER BY ASIGNADOS.`N_UNIDAD` ASC";

        System.out.println(sql);

        System.out.println("Mes: " + campos.get("NombreMesUni"));

        Map parametro = new HashMap();
        parametro.put("sql", sql);

        parametro.put("mesUni", campos.get("mesUni"));
        parametro.put("NombreMesUni", campos.get("NombreMesUni"));

        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }
}
