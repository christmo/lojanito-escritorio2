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
public class GenerarReporteDespachos {

    private ConexionBase bd;
    private HashMap campos;
    private String[] sesion;
    private String empresa;
    private String usuario;
    private InputStream RutaJasper;

    public GenerarReporteDespachos(ConexionBase cb, HashMap camp, String[] ses) {
        this.bd = cb;
        this.campos = camp;
        this.sesion = ses;
        this.empresa = bd.getEmpresa(sesion[1]);
        this.usuario = sesion[2];
        RutaJasper = getClass().getResourceAsStream("plantillas/Despachados.jrxml");
    }

    /**
     * Genera segun los campos que se haya llenado
     */
    public void Generar() {
        if (campos.get("todo").equals(true)) {
            GenerarTodosLosDespachosFinalizados();
        } else {
            if (!campos.get("cod").equals("")) {
                GenerarPorCodigo();
            } else {
                if (!campos.get("uni").equals("")) {
                    GenerarPorUnidad();
                } else {
                    if (!campos.get("turno").equals("")) {
                        RutaJasper = getClass().getResourceAsStream("plantillas/DespachadosTurnos.jrxml");
                        GenerarTodosLosDespachosPorTurno();
                    }
                }
            }
        }
    }

    /**
     * Genera el reporte de todas las carreras realizadas por un cliente
     */
    public void GenerarPorCodigo() {
        String cod = campos.get("cod").toString();
        String sqlCodigo = "SELECT "
                + "ASIGNADOS.`COD_CLIENTE` AS ASIGNADOS_COD_CLIENTE,"
                + "ASIGNADOS.`FECHA` AS ASIGNADOS_FECHA,"
                + "ASIGNADOS.`HORA` AS ASIGNADOS_HORA,"
                + "ASIGNADOS.`TELEFONO` AS ASIGNADOS_TELEFONO,"
                + "ASIGNADOS.`NOMBRE_APELLIDO_CLI` AS ASIGNADOS_NOMBRE_APELLIDO_CLI,"
                + "ASIGNADOS.`DIRECCION_CLI` AS ASIGNADOS_DIRECCION_CLI,"
                + "ASIGNADOS.`NOTA` AS ASIGNADOS_NOTA,"
                + "ASIGNADOS.`N_UNIDAD` AS ASIGNADOS_N_UNIDAD,"
                + "ASIGNADOS.`MINUTOS` AS ASIGNADOS_MINUTOS,"
                + "ASIGNADOS.`SECTOR` AS ASIGNADOS_SECTOR,"
                + "ASIGNADOS.`ATRASO` AS ASIGNADOS_ATRASO,"
                + "ASIGNADOS.`ESTADO` AS ASIGNADOS_ESTADO"
                + " FROM `ASIGNADOS` ASIGNADOS "
                + "WHERE ASIGNADOS.`ESTADO` = 'F' "
                + "AND ASIGNADOS.`COD_CLIENTE` = $P{cod} "
                + "AND ASIGNADOS.`FECHA` BETWEEN '$P!{fechaIni}' AND '$P!{fechaFin}'";//= '$P!{fecha}' "
        //+ "AND ASIGNADOS.`HORA` BETWEEN '$P!{horaIni}' AND '$P!{horaFin}'";

        System.out.println("SQL: " + sqlCodigo);
        String txt = "para el cliente número: " + cod;

        Map parametro = new HashMap();
        parametro.put("sql", sqlCodigo);
        parametro.put("cod", Integer.parseInt(cod));
        parametro.put("fechaIni", campos.get("fechaIni"));
        parametro.put("fechaFin", campos.get("fechaFin"));
        //parametro.put("horaIni", campos.get("horaIni"));
        //parametro.put("horaFin", campos.get("horaFin"));
        parametro.put("quien", txt);
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Genera el reporte de todas las carreras realizadas por una unidad
     */
    public void GenerarPorUnidad() {
        String unidad = campos.get("uni").toString();
        String sqlUnidad = "SELECT "
                + "ASIGNADOS.`COD_CLIENTE` AS ASIGNADOS_COD_CLIENTE,"
                + "ASIGNADOS.`FECHA` AS ASIGNADOS_FECHA,"
                + "ASIGNADOS.`HORA` AS ASIGNADOS_HORA,"
                + "ASIGNADOS.`TELEFONO` AS ASIGNADOS_TELEFONO,"
                + "ASIGNADOS.`NOMBRE_APELLIDO_CLI` AS ASIGNADOS_NOMBRE_APELLIDO_CLI,"
                + "ASIGNADOS.`DIRECCION_CLI` AS ASIGNADOS_DIRECCION_CLI,"
                + "ASIGNADOS.`NOTA` AS ASIGNADOS_NOTA,"
                + "ASIGNADOS.`N_UNIDAD` AS ASIGNADOS_N_UNIDAD,"
                + "ASIGNADOS.`MINUTOS` AS ASIGNADOS_MINUTOS,"
                + "ASIGNADOS.`SECTOR` AS ASIGNADOS_SECTOR,"
                + "ASIGNADOS.`ATRASO` AS ASIGNADOS_ATRASO,"
                + "ASIGNADOS.`ESTADO` AS ASIGNADOS_ESTADO"
                + " FROM `ASIGNADOS` ASIGNADOS "
                + "WHERE ASIGNADOS.`ESTADO` = 'F' "
                + "AND ASIGNADOS.`N_UNIDAD` = $P{uni} "
                + "AND ASIGNADOS.`FECHA` BETWEEN '$P!{fechaIni}' AND '$P!{fechaFin}'";//= '$P!{fecha}' "
        //+ "AND ASIGNADOS.`HORA` BETWEEN '$P!{horaIni}' AND '$P!{horaFin}'";

        System.out.println("SQL: " + sqlUnidad);
        String txt = "por la unidad número: " + unidad;

        Map parametro = new HashMap();
        parametro.put("sql", sqlUnidad);
        parametro.put("uni", unidad);
        parametro.put("fechaIni", campos.get("fechaIni"));
        parametro.put("fechaFin", campos.get("fechaFin"));
        //parametro.put("horaIni", campos.get("horaIni"));
        //parametro.put("horaFin", campos.get("horaFin"));
        parametro.put("quien", txt);
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Genera la lista de todos los Despachos Finalizados de la empresa
     */
    public void GenerarTodosLosDespachosFinalizados() {
        String sql = "SELECT "
                + "ASIGNADOS.`COD_CLIENTE` AS ASIGNADOS_COD_CLIENTE,"
                + "ASIGNADOS.`FECHA` AS ASIGNADOS_FECHA,"
                + "ASIGNADOS.`HORA` AS ASIGNADOS_HORA,"
                + "ASIGNADOS.`TELEFONO` AS ASIGNADOS_TELEFONO,"
                + "ASIGNADOS.`NOMBRE_APELLIDO_CLI` AS ASIGNADOS_NOMBRE_APELLIDO_CLI,"
                + "ASIGNADOS.`DIRECCION_CLI` AS ASIGNADOS_DIRECCION_CLI,"
                + "ASIGNADOS.`NOTA` AS ASIGNADOS_NOTA,"
                + "ASIGNADOS.`N_UNIDAD` AS ASIGNADOS_N_UNIDAD,"
                + "ASIGNADOS.`MINUTOS` AS ASIGNADOS_MINUTOS,"
                + "ASIGNADOS.`SECTOR` AS ASIGNADOS_SECTOR,"
                + "ASIGNADOS.`ATRASO` AS ASIGNADOS_ATRASO,"
                + "ASIGNADOS.`ESTADO` AS ASIGNADOS_ESTADO"
                + " FROM `ASIGNADOS` ASIGNADOS "
                + "WHERE ASIGNADOS.`ESTADO` = 'F'"
                + "AND ASIGNADOS.`FECHA` BETWEEN '$P!{fechaIni}' AND '$P!{fechaFin}'";//= '$P!{fecha}'";

        System.out.println(sql);
        String txt = "por todas las unidades:";

        Map parametro = new HashMap();
        parametro.put("sql", sql);
        parametro.put("fechaIni", campos.get("fechaIni"));
        parametro.put("fechaFin", campos.get("fechaFin"));
        parametro.put("quien", txt);
        parametro.put("empresa", empresa);
        parametro.put("usuario", usuario);

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }

    /**
     * Genera la lista de todos los Despachos Finalizados de la empresa
     */
    public void GenerarTodosLosDespachosPorTurno() {
        String sql = "SELECT "
                + "ASIGNADOS.`COD_CLIENTE` AS ASIGNADOS_COD_CLIENTE,"
                + "ASIGNADOS.`FECHA` AS ASIGNADOS_FECHA,"
                + "ASIGNADOS.`HORA` AS ASIGNADOS_HORA,"
                + "ASIGNADOS.`TELEFONO` AS ASIGNADOS_TELEFONO,"
                + "ASIGNADOS.`NOMBRE_APELLIDO_CLI` AS ASIGNADOS_NOMBRE_APELLIDO_CLI,"
                + "ASIGNADOS.`DIRECCION_CLI` AS ASIGNADOS_DIRECCION_CLI,"
                + "ASIGNADOS.`NOTA` AS ASIGNADOS_NOTA,"
                + "ASIGNADOS.`N_UNIDAD` AS ASIGNADOS_N_UNIDAD,"
                + "ASIGNADOS.`MINUTOS` AS ASIGNADOS_MINUTOS,"
                + "ASIGNADOS.`SECTOR` AS ASIGNADOS_SECTOR,"
                + "ASIGNADOS.`ATRASO` AS ASIGNADOS_ATRASO,"
                + "ASIGNADOS.`ESTADO` AS ASIGNADOS_ESTADO"
                + " FROM `ASIGNADOS` ASIGNADOS "
                + "WHERE ASIGNADOS.`ESTADO` = 'F' "
                + "AND CONCAT(ASIGNADOS.`FECHA`,'-',ASIGNADOS.`HORA`) "
                + "BETWEEN CONCAT('$P!{fechaIni}','-',(SELECT TURNOS.`HORA_INI` from `TURNOS` TURNOS where TURNOS.`ID_TURNO` = $P!{turno})) "
                + "AND CONCAT(IF((SELECT TURNOS.`HORA_INI` from `TURNOS` TURNOS where TURNOS.`ID_TURNO` = $P!{turno}) > (SELECT TURNOS.`HORA_FIN` from `TURNOS` TURNOS where TURNOS.`ID_TURNO` = $P!{turno}),"
                + "'$P!{fechaIni}' + INTERVAL 1 DAY,'$P!{fechaIni}'),'-',(SELECT TURNOS.`HORA_FIN` from `TURNOS` TURNOS where TURNOS.`ID_TURNO` = $P!{turno})) "
                + "AND ASIGNADOS.`ID_TURNO`= $P!{turno} "
                + "AND ASIGNADOS.`USUARIO` = '$P!{user}' ";

        System.out.println(sql);
        String txt = "por todas las unidades:";

        Map parametro = new HashMap();
        parametro.put("sql", sql);
        parametro.put("fechaIni", campos.get("fechaIni"));
        parametro.put("fechaFin", campos.get("fechaIni"));
        parametro.put("turno", campos.get("turno"));
        parametro.put("quien", txt);
        parametro.put("empresa", empresa);
        parametro.put("user", campos.get("user"));
        parametro.put("usuario", sesion[2]);
        parametro.put("turnotxt", campos.get("turnotxt"));
        parametro.put("nombre_user", campos.get("nombre_user"));

        GenerarReporte.Generar(parametro, RutaJasper, bd);
    }
}
