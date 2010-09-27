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
public class GenerarReporteMultas {

    private ConexionBase bd;
    private HashMap campos;
    private String[] sesion;
    private String empresa;
    private String usuario;
    private InputStream RutaJasper;

    public GenerarReporteMultas(ConexionBase cb, HashMap camp, String[] ses) {
        this.bd = cb;
        this.campos = camp;
        this.sesion = ses;
        this.empresa = bd.getEmpresa(sesion[1]);
        this.usuario = sesion[2];
        try {
            if (campos.get("cat").toString().equals("tiposMultas")) {
                RutaJasper = getClass().getResourceAsStream("plantillas/multas/Multas.jrxml");
            } else if (campos.get("cat").toString().equals("asignadas")) {
                RutaJasper = getClass().getResourceAsStream("plantillas/multas/MultasAsignadas.jrxml");
            } else if (campos.get("cat").toString().equals("pagadas")) {
                RutaJasper = getClass().getResourceAsStream("plantillas/multas/MultasPagadas.jrxml");
            } else if (campos.get("cat").toString().equals("porPagar")) {
                RutaJasper = getClass().getResourceAsStream("plantillas/multas/MultasAsignadas.jrxml");
            }
        } catch (NullPointerException ex) {
        }
    }

    /**
     * Genera segun los campos que se haya llenado
     */
    public void Generar() {
        try {
            if (campos.get("cat").toString().equals("tiposMultas")) {
                GenerarTiposMultas();
            } else if (campos.get("cat").toString().equals("asignadas")) {
                if (campos.get("op").toString().equals("total")) {
                    GenerarTotalMultasAsinadas();
                } else {
                    GenerarTotalMultasAsignadasMensual();
                }
            } else if (campos.get("cat").toString().equals("pagadas")) {
                if (campos.get("op").toString().equals("total")) {
                    GenerarTotalMultasPagadas();
                } else {
                    GenerarTotalMultasPagadasMensual();
                }
            } else if (campos.get("cat").toString().equals("porPagar")) {
                if (campos.get("op").toString().equals("total")) {
                    GenerarTotalMultasPorPagar();
                } else {
                    GenerarTotalMultasPorPagarMensual();
                }
            }
        } catch (NullPointerException ex) {
        }
    }

    /**
     * genera el reporte de los tipos de multa con los que cuenta la empresa
     */
    private void GenerarTiposMultas() {
        Map parametro = new HashMap();
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Genera el reporte de todas las multas asignadas en la empresa a todas las
     * undiades
     */
    private void GenerarTotalMultasAsinadas() {
        Map parametro = new HashMap();
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Genera el reporte de todas las multas puestas a las unidades en un mes
     * determinado
     */
    private void GenerarTotalMultasAsignadasMensual() {
        String sql = "SELECT "
                + "MULTAS_ASIGNADAS.`USUARIO` AS MULTAS_ASIGNADAS_USUARIO,"
                + "MULTAS_ASIGNADAS.`N_UNIDAD` AS MULTAS_ASIGNADAS_N_UNIDAD,"
                + "MULTAS_ASIGNADAS.`FECHA` AS MULTAS_ASIGNADAS_FECHA,"
                + "MULTAS_ASIGNADAS.`HORA` AS MULTAS_ASIGNADAS_HORA,"
                + "MULTAS_ASIGNADAS.`COD_MULTA` AS MULTAS_ASIGNADAS_COD_MULTA,"
                + "COD_MULTAS.`VALOR` AS COD_MULTAS_VALOR "
                + "FROM "
                + "`MULTAS_ASIGNADAS` MULTAS_ASIGNADAS,"
                + "`COD_MULTAS` COD_MULTAS "
                + "WHERE "
                + "MULTAS_ASIGNADAS.`COD_MULTA` = COD_MULTAS.`COD_MULTA` "
                + "AND "
                + "MONTH(MULTAS_ASIGNADAS.`FECHA`) = '$P!{mes}'";

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

    /**
     * Genera un reporte de todas las multas pagadas por los conductores de las
     * unidades de toda la empresa
     */
    private void GenerarTotalMultasPagadas() {
        Map parametro = new HashMap();
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Genera un reporte de todas las multas pagadas en un mes determinado
     */
    private void GenerarTotalMultasPagadasMensual() {
        String sql = "SELECT "
                + "MULTAS_PAGADAS.`USUARIO` AS MULTAS_PAGADAS_USUARIO,"
                + "MULTAS_ASIGNADAS.`N_UNIDAD` AS MULTAS_ASIGNADAS_N_UNIDAD,"
                + "MULTAS_ASIGNADAS.`FECHA` AS MULTAS_ASIGNADAS_FECHA,"
                + "MULTAS_PAGADAS.`FECHA` AS MULTAS_PAGADAS_FECHA,"
                + "MULTAS_PAGADAS.`HORA` AS MULTAS_PAGADAS_HORA,"
                + "MULTAS_ASIGNADAS.`HORA` AS MULTAS_ASIGNADAS_HORA,"
                + "MULTAS_ASIGNADAS.`COD_MULTA` AS MULTAS_ASIGNADAS_COD_MULTA,"
                + "COD_MULTAS.`VALOR` AS COD_MULTAS_VALOR "
                + "FROM "
                + "`MULTAS_PAGADAS` MULTAS_PAGADAS,"
                + "`MULTAS_ASIGNADAS` MULTAS_ASIGNADAS,"
                + "`COD_MULTAS` COD_MULTAS "
                + "WHERE "
                + "MULTAS_PAGADAS.`ID_ASIG` = MULTAS_ASIGNADAS.`ID_ASIG` "
                + "AND MULTAS_ASIGNADAS.`COD_MULTA` = COD_MULTAS.`COD_MULTA`"
                + "AND "
                + "MONTH(MULTAS_PAGADAS.`FECHA`) = '$P!{mes}'";

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

    /**
     * Genera el reporte de todas las multas por pagar de la empres
     */
    private void GenerarTotalMultasPorPagar() {
        String sql = "SELECT "
                + "MA.`USUARIO` AS MULTAS_ASIGNADAS_USUARIO,"
                + "MA.`N_UNIDAD` AS MULTAS_ASIGNADAS_N_UNIDAD,"
                + "MA.`FECHA` AS MULTAS_ASIGNADAS_FECHA,"
                + "MA.`HORA` AS MULTAS_ASIGNADAS_HORA,"
                + "MA.`COD_MULTA` AS MULTAS_ASIGNADAS_COD_MULTA, "
                + "COD_MULTAS.`VALOR` AS COD_MULTAS_VALOR "
                + "FROM "
                + "`MULTAS_ASIGNADAS` MA, "
                + "`COD_MULTAS` COD_MULTAS "
                + "WHERE "
                + "MA.`COD_MULTA` = COD_MULTAS.`COD_MULTA` "
                + "AND "
                + "MA.`ID_ASIG` <> ALL (SELECT MP.`ID_ASIG` FROM `MULTAS_PAGADAS` MP);";

        System.out.println(sql);

        Map parametro = new HashMap();
        parametro.put("sql", sql);

        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Genera un reporte de todas las multas por pagar de un mes determinado
     */
    private void GenerarTotalMultasPorPagarMensual() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
