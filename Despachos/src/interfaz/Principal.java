    /*                                         
 * Cuando escribi este codigo, solo Dios y YO entendiamos lo que estaba haciendo                                        
 * AHORA, solo Dios lo entiende --> ;-) ja ja
 *
 * Principal.java
 *
 * Created on 03/08/2010, 06:28:35 PM
 */
package interfaz;

import BaseDatos.ConexionBase;

import interfaz.reloj.Reloj;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import interfaz.comboBox.*;
import interfaz.comunicacion.comm.CommMonitoreo;
import interfaz.comunicacion.servidorBD.ActualizadorDespachosServidorKRADAC;
import interfaz.comunicacion.servidorBD.ConsultaRecorridosServidorBD;
import interfaz.comunicacion.servidorBD.ConsultaUnidadesBloqueadas;
import interfaz.comunicacion.servidorBD.EnvioMensajesUnidades;
import interfaz.comunicacion.servidorBD.GuardarServidorKRADAC;

import interfaz.subVentanas.Clientes;
import interfaz.subVentanas.ConsultaClientes;
import interfaz.subVentanas.VentanaDatos;
import interfaz.subVentanas.Despachos;
import interfaz.subVentanas.Pendientes;
import interfaz.subVentanas.PendientesGUI;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sonido.Sonido;

/**
 *
 * @author christmo
 */
public final class Principal extends javax.swing.JFrame {

    // <editor-fold defaultstate="collapsed" desc="Variables Globales">
    private static TrabajoTablas trabajarTabla;
    //--------------------------------------------------------------------------
    // Variables Globales
    //--------------------------------------------------------------------------
    /**
     * Logger para guardar los log en un archivo y enviar por mail los de error
     */
    private static final Logger log = LoggerFactory.getLogger(Principal.class);
    /**
     * Variables Publicas
     */
    /**
     * Mantiene los datos de la sesion
     * [0]=nickName
     * [1]=id_empresa
     * [2]=Nombre Completo del Usuario
     */
    public static String[] sesion = null;
    /**
     * Mantiene la hora de salida para hacer el cambio de turno al terminar la
     * jornada laboral
     */
    public static String horaNuevoTurno = null;
    /**
     * Variables Privadas
     */
    private static ConexionBase bd;
    private static ResultSet rs;
    private static String[] strCabecerasColumnasVehiculos = null;
    private String strHora;
    private String strTelefono;
    private String intCodigo;
    private String strNombre;
    private String strDireccion;
    private String strBarrio;
    private int intMinutos;
    private int intUnidad;
    private int intAtraso;
    private String strNota;
    private static ArrayList<String> strEncabezados;
    private Despachos despacho;
    private int intFilaSeleccionada;
    private int cod; //codigo de la tecla presionada
    private boolean desPorTabla_Campo = false; //false es un despacho por campo, true es un despacho por tabla
    private INICIO menu;
    private long fechaHoraUltimoDespacho;
    private GregorianCalendar calendar;
    /**
     * almacena los clientes Por Despachar
     */
    public static ArrayList<Despachos> listaDespachosTemporales = new ArrayList<Despachos>();
    private static ArrayList<Despachos> listaDespachados = new ArrayList<Despachos>();
    /**
     * Lista de pendientes de esta fecha para controlar cuando se cumpla alguna...
     */
    public static ArrayList<Pendientes> listaPendientesFecha = new ArrayList<Pendientes>();
    /**
     * Encabezado de las tablas de despachos
     */
    private static DefaultTableModel dtm;
    public static String turno;
    private static int id_Turno;
    private static funcionesUtilidad funciones = new funcionesUtilidad();
    /**
     * Objeto de esta misma interfaz para hacer uso de las constantes de la clase
     */
    public static Principal gui;

    /*Archivo de configuraciones*/
    public static Properties arcConfig;

    /*Puerto Serial*/
    private CommMonitoreo comm;
    //Matriz de Relacion CODIGO - COLOR
    //Cada Elemento de codColor
    //[1][ID_CODIGO]  [2][COLOR]
    private static ArrayList<String> codigo = new ArrayList();
    private static ArrayList<String> color = new ArrayList();
    private static ArrayList<String> etiq = new ArrayList();
    private static Map etiqColor = new HashMap();
    /**
     * Permite controlar si se cambia la unidad antes de despachar la carrera
     */
    private boolean CampoUnidadCambio = false;
    private VentanaDatos ventanaDatos = null;
    private int intMinutoAnt = 0;
    private int contador = 0;
    private String UnidadAntesDeBorrar;
    private int filaAnt = -1;
    /**
     * Comprueba el tiempo en que se piensa recojer al cliente por cada minuto
     * para saber si el taxista se atrasa o no
     */
    Timer tiempo = new Timer(60000, new ActionListener() {

        public void actionPerformed(ActionEvent ae) {
            int N_filas = jtPorDespachar.getRowCount();
            for (int i = 0; i < N_filas; i++) {
                String dato = (String) jtPorDespachar.getValueAt(i, 8);
                String minuto = (String) jtPorDespachar.getValueAt(i, 7);
                try {
                    if (!dato.equals("") && !minuto.equals("")) {
                        int atraso = Integer.parseInt(dato);
                        if (!minuto.equals("0")) {
                            jtPorDespachar.setValueAt("" + (atraso + 1), i, 8);
                        }
                    }
                } catch (NullPointerException ex) {
                }
            }
        }
    });

    /**
     * Setea los datos de la sesion a una variable global en este entorno
     * @param sesion
     */
    public void setSession(String[] datos) {
        Principal.sesion = datos;
    }

    /**
     * Configuracion inicial de la ventana para que se ejecuten cuando se cargue
     * y cree el objeto al momento de llamar a esta ventana
     */
    private void ConfiguracionInicial() {
        actualizarTurno();
        actualizarBarraTitulo();
        redimencionarTablaVehiculos();
        llenarComboEstados();
        limpiarCargarTablaDespachados();
        IdentificadorLlamadas();


        jtTelefono.requestFocusInWindow();

        tiempo.start();

        leerRecorridosServidorKRADAC();
        leerUnidadesBloqueadasServidorKRADAC();
        actualizadorDespachosServidorKRADAC();

        Reloj();
        this.setExtendedState(MAXIMIZED_BOTH);

        ponerAlarmaPendiente(false);

        /**
         * Despachar con la tecla F12
         */
        jtPorDespachar.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "Despachar");
        jtPorDespachar.getActionMap().put("Despachar", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                /**
                 * Metodo generico para despachar se lo utiliza en todos los
                 * lugares donde se despacha carreras
                 */
                validarDespacharCliente();
            }
        });

        /**
         * Limpiar los campos de la tabla por despachar F7
         */
        jtPorDespachar.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0), "LimpiarCampos");
        jtPorDespachar.getActionMap().put("LimpiarCampos", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                try {
                    jtPorDespachar.getCellEditor().stopCellEditing();
                } catch (NullPointerException ex) {
                }
                int intFila = jtPorDespachar.getSelectedRow();
                borrarCamposFilaSeleccionadaPorDespachar(intFila);
            }
        });

        /**
         * Eliminar la fila F8
         */
        jtPorDespachar.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0), "EliminarFila");
        jtPorDespachar.getActionMap().put("EliminarFila", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                /**
                 * Metodo generico para borrar las filas de una manera segura
                 * llamarlo desde todos los lugares donde se hagan borrado de
                 * filas
                 */
                borrarFilasFormaSegura();
            }
        });

        /**
         * Insertar una nueva fila cuando se precione la tecla Insert
         */
        jtPorDespachar.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), "InsertarFila");
        jtPorDespachar.getActionMap().put("InsertarFila", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                try {
                    jtPorDespachar.getCellEditor().stopCellEditing();
                } catch (NullPointerException ex) {
                }
                nuevaFilaDespacho();
            }
        });

        /**
         * Parar la edición de la celda cuando se precione enter y mantener el
         * enfoque en la misma fila
         */
        jtPorDespachar.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
        jtPorDespachar.getActionMap().put("Enter", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                try {
                    jtPorDespachar.getCellEditor().stopCellEditing();
                } catch (NullPointerException ex) {
                }
            }
        });

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                salirVentanaPrincipal();
            }
        });
    }

    /**
     * Abre la comunicación serial para leer el numero de telefono del cliente
     */
    private void IdentificadorLlamadas() {
        String puerto = bd.getValorConfiguiracion("comm");
        String separador = bd.getValorConfiguiracion("separador");
        //System.out.println("Puerto COMM: " + puerto);
        log.trace("Puerto COMM: {}", puerto);
        //System.out.println("Empresa: " + sesion[1]);
        log.trace("Empresa: {}", sesion[1]);

        if (!puerto.equals("0")) {
            comm = new CommMonitoreo(puerto, bd);

            comm.enviarDatos("AT" + separador);

            String comando = bd.getComandoActivarModem(sesion[1]);
            //System.out.println("Comando MODEM: " + comando);
            log.trace("Comando MODEM: {}", comando);

            try {
                comm.enviarDatos(comando + separador);
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                java.util.logging.Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            }
            comm.setIndicadorLlamada(jtTelefono, jlIndicadorLlamada, jtPorDespachar);
            comm.start();
        }
    }

    /**
     * Actualiza la barra de titulo del frame
     */
    private void actualizarBarraTitulo() {
        setTitle("Despachos KRADAC - " + sesion[1] + " || " + turno + " || " + sesion[2]);
        setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());
    }

    /**
     * Activa el Reloj en la interfaz
     */
    private void Reloj() {
        Reloj r = new Reloj(lblReloj, lblFecha, bd);
    }

    /**
     * Ejecuta el hilo para realizar los las consultas de los ultimos datos de
     * las unidades para dibujarlas en el mapa de forma local
     */
    private void leerRecorridosServidorKRADAC() {
        try {
            String getGPS = bd.getValorConfiguiracion("posicion_gps");
            if (getGPS.equals("si") || getGPS.equals("SI")
                    && !getGPS.equals("") && getGPS != null) {
                ConsultaRecorridosServidorBD conServidor = new ConsultaRecorridosServidorBD(sesion[1], bd);
                conServidor.start();
            }
        } catch (NullPointerException ex) {
            log.trace("No se a especificado la directiva [posicion_gps] en la base de datos...");
        }
    }

    /**
     * Ejecuta el hilo para realizar las consultas de las unidades que han
     * sido bloqueadas por falta de pago.
     */
    private void leerUnidadesBloqueadasServidorKRADAC() {
        ConsultaUnidadesBloqueadas unidadesBloquedas = new ConsultaUnidadesBloqueadas(sesion[1], bd);
        unidadesBloquedas.start();
    }

    /**
     * Ejecuta el programa que actualiza los despachos dentro del servidor kradac
     */
    private void actualizadorDespachosServidorKRADAC() {
        ActualizadorDespachosServidorKRADAC actualizador = new ActualizadorDespachosServidorKRADAC(sesion[1]);
        actualizador.start();
    }

    /**
     * Limpia y carga con todos los valores pord efecto en la tabla de despachados
     */
    private static void limpiarCargarTablaDespachados() {
        limpiarTablaDespachados();
        cargarTablaDespachados();
    }

    /**
     * Carga los clientes despachados en el turno actual
     */
    private static void cargarTablaDespachados() {
        listaDespachados.clear();
        try {
            listaDespachados = bd.getDespachados(sesion[0], id_Turno, funciones.getFecha());
            dtm = (DefaultTableModel) jtDespachados.getModel();

            for (Despachos d : listaDespachados) {
                String[] datos = {
                    d.getStrHora(),
                    d.getStrTelefono(),
                    "" + d.getIntCodigo(),
                    d.getStrNombre(),
                    d.getStrBarrio(),
                    d.getStrDireccion(),
                    "" + d.getIntUnidad(),
                    "" + d.getIntMinutos(),
                    "" + d.getIntAtraso(),
                    d.getStrNota()
                };
                dtm.insertRow(0, datos);
            }
        } catch (NullPointerException nex) {
        }
    }

    /**
     * Limpia las filas de la tabla despachados
     */
    private static void limpiarTablaDespachados() {
        dtm = (DefaultTableModel) jtDespachados.getModel();
        int n_filas = jtDespachados.getRowCount();
        for (int i = 0; i < n_filas; i++) {
            dtm.removeRow(0);
        }
    }

    /**
     * setea el string del turno y el id del turno actual
     */
    public static void actualizarTurno() {
        turno = validarTurno();
        try {
            id_Turno = bd.getIdTurno(validarTurno());
            actualizarTurnoUsuario(sesion[0], id_Turno);
            try {
                horaNuevoTurno = bd.getHoraNuevoTurno(id_Turno);
            } catch (SQLException ex) {
                log.error("{}", sesion[1], ex);
            }
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "Iniciar nuevamente - Creando turnos por defecto y estados de taxi por defecto...", "Error...", 1);
            log.trace("Creando turnos por defecto - Cerrar Aplicación...");
            bd.ejecutarSentencia("INSERT INTO TURNOS VALUES(1,'6:0:0','13:59:59')");
            bd.ejecutarSentencia("INSERT INTO TURNOS VALUES(2,'14:0:0','21:59:59')");
            bd.ejecutarSentencia("INSERT INTO TURNOS VALUES(3,'22:0:0','5:59:59')");

            log.trace("Creando estados de taxi por defecto - Cerrar Aplicación...");
            bd.ejecutarSentencia("INSERT INTO CODESTTAXI VALUES ('AC', 'ACTIVO', '-13369549')");
            bd.ejecutarSentencia("INSERT INTO CODESTTAXI VALUES ('ASI', 'ASIGNADO', '-23123326')");
            bd.ejecutarSentencia("INSERT INTO CODESTTAXI VALUES ('OCU', 'OCUPADO', '-34512')");
            bd.ejecutarSentencia("INSERT INTO CODESTTAXI VALUES ('TXM', 'TAXIMETRO', '-64032')");
            System.exit(0);
        }
    }

    /**
     * Actualiza la tabla de usuarios con el turno en que entró por ultima vez el
     * usuario
     * @param user
     * @param id_turno
     */
    private static void actualizarTurnoUsuario(String user, int id_turno) {
        bd.actualziarTurnoUsuario(user, id_turno);
    }

    /**
     * Redimenciona la tabla de unidades segun el numero de vehiculos que estan
     * en la base de datos para cada empresa
     */
    public static void redimencionarTablaVehiculos() {
        TableModel tm = new AbstractTableModel() {

            String headers[] = getEncabezadosTablaVehiculos();
            String rows[] = getFilasNumeroDespachos();

            public int getColumnCount() {
                return headers.length;
            }

            public int getRowCount() {
                return 1;
            }

            @Override
            public String getColumnName(int col) {
                return headers[col];
            }

            /**
             * Trae el valor actual
             */
            public Object getValueAt(int row, int col) {
                return rows[col];
            }

            @Override
            public void setValueAt(Object a, int row, int col) {
                rows[col] = a.toString();
                fireTableDataChanged();
            }
        };

        jtVehiculos.setModel(tm);

        //Ajustar el ancho de las columnas de la tabla de vehiculos
        String[] strEncColum = strCabecerasColumnasVehiculos;
        for (String col : strEncColum) {
            TableColumn tc = jtVehiculos.getColumn(col);
            if (strEncColum.length >= 1 && strEncColum.length <= 99) {
                tc.setMaxWidth(25);
                jtVehiculos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            } else if (strEncColum.length > 99) {
                tc.setMaxWidth(32);
                jtVehiculos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            }
        }

        pintarEstadoTaxi(strEncabezados);
    }

    /**
     * Actualiza la tabla de unidades para contabilizar el numero de carreras
     * que lleva realizadas
     */
    private void setNumeroCarrerasRealizadasPorTaxi(String n_unidad) {
        int cantidad = bd.getNumeroCarerasPorVehiculo(n_unidad, id_Turno, sesion[0]);
        int col = jtVehiculos.getColumn(n_unidad).getModelIndex();
        jtVehiculos.setValueAt(cantidad, 0, col);
    }

    /**
     * Trae el total de carreras por unidad para ponerlas en la tabla de
     * unidades String[]
     * @return
     */
    public static String[] getFilasNumeroDespachos() {
        String[] cant = new String[strCabecerasColumnasVehiculos.length];

        for (int i = 0; i < strCabecerasColumnasVehiculos.length; i++) {
            cant[i] = "" + bd.getNumeroCarerasPorVehiculo(strCabecerasColumnasVehiculos[i], id_Turno, sesion[0]);
        }
        return cant;
    }

    /**
     * Devuelve un arreglo de string que contiene los numeros
     * para cada uno de los vehiculos que posee la empresa, para ponerlos
     * como encabezado de la tabla de unidades
     * @return String[]
     */
    private static String[] getEncabezadosTablaVehiculos() {
        strEncabezados = new ArrayList<String>();
        String[] datosCast;
        try {
            String sql = "SELECT N_UNIDAD FROM VEHICULOS WHERE ID_EMPRESA = '" + sesion[1] + "' GROUP BY N_UNIDAD ASC";
            rs = bd.ejecutarConsulta(sql);
            while (rs.next()) {
                strEncabezados.add(rs.getString(1));
            }
            datosCast = new String[strEncabezados.size()];
            strCabecerasColumnasVehiculos = strEncabezados.toArray(datosCast);
            return strCabecerasColumnasVehiculos;
        } catch (SQLException ex) {
            log.error("{}", sesion[1], ex);
        } catch (NullPointerException npe) {
            JOptionPane.showMessageDialog(null, "No se pudo recuperar las unidades para este usuario!!!", "Error", 0);
            //System.err.println("No se pudo recuperar el número de unidades para este usuario!!!");
            log.trace("No se pudo recuperar el número de unidades para este usuario!!!");
        }

        return strCabecerasColumnasVehiculos;
    }

    /**
     * Retorna el turno que corresponde dependiendo de la hora del sistema
     * @return String
     */
    private static String validarTurno() {
        Calendar calendario = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        try {
            String sql = "SELECT SF_TURNOS('" + sdf.format(calendario.getTime()) + "')";
            rs = bd.ejecutarConsultaUnDato(sql);
            return rs.getString(1);
        } catch (SQLException ex) {
            if (ex.getMessage().equals("Operation not allowed after ResultSet closed")) {
                System.out.println("ResultSet cerrado...");
            } else {
                log.error("{}", sesion[1], ex);
            }
        }
        return null;
    }

    public static void llenarComboEstados() {
        //Extracción de la BD
        colorCodigosBD();

        //Carga de Codigos de Estado del Taxi
        cbEstadosTaxi.setModel(new javax.swing.DefaultComboBoxModel(etiq.toArray()));

        cbEstadosTaxi.setEditable(true);
        cbEstadosTaxi.setRenderer(new ColorCellRenderer(codigo, color));
        ColorComboBoxEditor editor = new ColorComboBoxEditor(etiqColor);
        cbEstadosTaxi.setEditor(editor);
    }

    /**
     * Permite ejecutar acciones que se realicen al momento de precionar el boton
     * de salir
     */
    private void salirVentanaPrincipal() {
        log.trace("Empezar a salir del sistema... :-|");
        activarUnidadesOcupadasAsignadas();
        log.trace("Activadas las unidades asignadas...");
        bd.TruncarTablaPosicionesCliente();
        log.trace("Eliminados los clientes del mapa...");
        bd.CerrarConexion();
        try {
            comm.CerrarPuerto();
            log.trace("Cerrar puerto comm...");
        } catch (NullPointerException ex) {
        }
        log.trace("Salir de la aplicacion de despachos... :-)");
        System.exit(0);
    }

    /**
     * Activa todas las unidades que esten ocupadas cuando se cierre el sistema
     */
    private void activarUnidadesOcupadasAsignadas() {
        rs = bd.getUnidadesOcupadasAsignadas();
        try {
            while (rs.next()) {
                removerDespachoDeTemporal(rs.getString(1));
                //String sql = "INSERT INTO REGCODESTTAXI VALUES (now(),now(),'" + "AC" + "','" + sesion[0] + "','" + rs.getString(1) + "')";
                //bd.ejecutarSentencia(sql);
                bd.setCambiarEstadoUnidad("AC", sesion[0], rs.getString(1));
            }
        } catch (SQLException ex) {
            if (ex.getMessage().equals("Operation not allowed after ResultSet closed")) {
                log.trace("ResultSet cerrado...");
            } else {
                log.error("{}", sesion[1], ex);
            }
        } catch (NullPointerException ex) {
        }
    }

    /**
     * Borra las filas o las celdas dependiendo de la tecla que se precione
     * @param cod - codigo de la tecla
     * @param Tabla - tabla donde ejecutar la accion
     */
    private void AccionesBorrado(int cod, javax.swing.JTable Tabla) {
        int intFila = Tabla.getSelectedRow();
        int intCol = Tabla.getSelectedColumn();
        try {
            if (cod == 127 || cod == 8) {//teclas suprimir y backspace
                /**
                 * borrar la celda que se haya seleccionado, esto se debe
                 * ejecutar cuando se presione (<-) BackSpace o SUPRIMIR
                 */
                if (intCol == 0 || intCol == 2 || intCol == 8 || intCol == 6) {
                    //System.out.println("No borrar esos campos...");
                } else {
                    Tabla.setValueAt("", intFila, intCol);
                }
                /**
                 * Columna de Unidad
                 */
                if (intCol == 6) {
                    /**
                     * Revisa cuando borra el campo que se borre la asignacion
                     */
                    //BorrarColorDespachoVehiculo(Tabla);
                }
                /**
                 * Columna de Minutos
                 */
                if (intCol == 7) {
                    Tabla.setValueAt("", intFila, intCol);
                    Tabla.setValueAt("0", intFila, 8);
                }
            }
            /**
             * Borrar el texto antes de insertar algo nuevo, este rango de teclas
             * corresponde a todo el teclado de caracteres imprimibles
             */
            if (cod >= 48 && cod <= 105) {
                String tmp = "" + jtPorDespachar.getValueAt(intFila, intCol);
                if (!tmp.equals("")) {
                    if (intCol == 6) {
                        UnidadAntesDeBorrar = tmp;
                        CampoUnidadCambio = true;
                    } else {
                        CampoUnidadCambio = false;
                    }
                }
                if (intCol != 8) { //Borras lo que tengan las celdas menos las 8 ATRASO
                    jtPorDespachar.setValueAt("", intFila, intCol);
                }
            }
        } catch (ArrayIndexOutOfBoundsException aidx) {
        }
    }

    /**
     * Elimina una Fila de la Tabla PorDespachar que se haya seleccionado
     * @param intFila
     */
    private void borrarFilaSeleccionadaPorDespachar(int intFila) {

        if (jtPorDespachar.isEditing()) {
            jtPorDespachar.getCellEditor().cancelCellEditing();
        }
        Icon img = new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/nollamada.png"));
        jlIndicadorLlamada.setIcon(img);

        if (despacho.getStrHora() != null) {
            String hora = "";
            try {
                hora = jtPorDespachar.getValueAt(intFila, 0).toString();
            } catch (NullPointerException ex) {
            } catch (ArrayIndexOutOfBoundsException aex) {
            }

            if (!despacho.getStrHora().equals("") || hora.equals("")) {
                despacho = getDatosPorDespachar();
                despacho.setStrHora(funciones.getHora());
                despacho.setStrEstado("C");//cancelada
                bd.InsertarDespachoCliente(despacho, false);
            } else {
                despacho = getDatosPorDespachar();
                despacho.setStrHora(funciones.getHora());
                despacho.setStrEstado("C");//cancelada
                bd.InsertarDespachoCliente(despacho, false);
            }
        } else {
            despacho = getDatosPorDespachar();
            despacho.setStrHora(funciones.getHora());
            despacho.setStrEstado("C");//cancelada
            bd.InsertarDespachoCliente(despacho, false);
        }

        String cod_cli = "";
        try {
            cod_cli = jtPorDespachar.getValueAt(intFila, 2).toString();
        } catch (NullPointerException ex) {
            //System.out.println("No tiene Codigo de Cliente");
        } catch (ArrayIndexOutOfBoundsException aex) {
        }

        String unidad = "";

        try {
            unidad = jtPorDespachar.getValueAt(intFila, 6).toString();
        } catch (NullPointerException ex) {
            //System.out.println("No tiene Unidad");
        } catch (ArrayIndexOutOfBoundsException aex) {
        }

        if (despacho.getIntUnidad() != 0) {
            String etiquetaActivo = bd.getEtiquetaEstadoUnidad("AC");
            setColorDespachoVehiculo(unidad, etiquetaActivo);
        }
        quitarClienteMapaLocal(cod_cli, unidad);

        DefaultTableModel model = ((DefaultTableModel) jtPorDespachar.getModel());
        try {
            model.removeRow(intFila);
        } catch (ArrayIndexOutOfBoundsException ex) {
            //System.out.println("Se quiere borrar algo fuera del rango");
        }
        InicializarVariables();

    }

    /**
     * Limpia los campos de Nombre, Direccion, Barrio, Nota
     * para que se realice un despacho temporal a un mismo
     * telefono o codigo que los cambios no van a ser permanentes
     * @param intFila
     */
    private void borrarCamposFilaSeleccionadaPorDespachar(int intFila) {
        try {
            jtPorDespachar.setValueAt("", intFila, 3);
            jtPorDespachar.setValueAt("", intFila, 4);
            jtPorDespachar.setValueAt("", intFila, 5);
            jtPorDespachar.setValueAt("", intFila, 9);
            jtBuscarPorNombre.setText("");
            jtBuscarPorTelefono.setText("");
            jtBuscarPorCodigo.setText("");
            limpiarCargarTablaDespachados();
        } catch (IndexOutOfBoundsException iex) {
            jtBuscarPorNombre.setText("");
            jtBuscarPorTelefono.setText("");
            jtBuscarPorCodigo.setText("");
            limpiarCargarTablaDespachados();
        }
    }

    /**
     * Envia los datos de la tabla PorDespachar a la tabla de Despachados,
     * guardando estos en la base de datos
     * @param intFila
     * @return boolean -> true si se despacho el cliente correctamente
     */
    private boolean DespacharCliente(int intFila) {
        intFilaSeleccionada = intFila;
        if (jtPorDespachar.isEditing()) {
            jtPorDespachar.getCellEditor().cancelCellEditing();
        } else {
            despacho = getDatosPorDespachar();
            despacho.setStrEstado("F"); //finalizado

            if (sePuedeDespachar(despacho, intFila)) {
                /**
                 * Inserta el despacho en la tabla de despachados
                 */
                boolean despachar = setDatosTablaDespachados(despacho, jtDespachados);
                guardarClienteSinCodigo(despacho);
                if (despachar) {
                    String estadoOcupado = bd.getNombreEstadoUnidad("OCU");
                    setColorDespachoVehiculo("" + despacho.getIntUnidad(), estadoOcupado);
                    setNumeroCarrerasRealizadasPorTaxi("" + despacho.getIntUnidad());

                    /**
                     * Envia los datos al servidor de Kradac, cuando el despacho
                     * se realizo correctamente
                     **********************************************/
                    java.awt.EventQueue.invokeLater(new Runnable() {

                        public void run() {
                            insertarAsignacionDespachoServidorKradac();
                            /**
                             * Remover la fila de la tabla de clientes por despachar
                             */
                            DefaultTableModel model = ((DefaultTableModel) jtPorDespachar.getModel());
                            model.removeRow(intFilaSeleccionada);

                            InicializarVariables();
                        }
                    });
                    /**********************************************/
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Metodo Generico para despachar clientes este se debe llamar en todos los
     * lugares por los que se despache clientes, en el boton del taxi y con f12
     * por ahora si se implementara una nueva forma de despachar se lo deberia
     * llamar a este metodo.
     */
    private void validarDespacharCliente() {
        try {
            jtPorDespachar.getCellEditor().stopCellEditing();
        } catch (NullPointerException ex) {
        }

        int intFila = jtPorDespachar.getSelectedRow();

        String cod_cli = null;
        String unidad = null;
        try {
            try {
                cod_cli = jtPorDespachar.getValueAt(intFila, 2).toString();
            } catch (NullPointerException ex) {
                cod_cli = "";
            }
            unidad = jtPorDespachar.getValueAt(intFila, 6).toString();
            try {
                String strCampoMinutos = jtPorDespachar.getValueAt(intFila, 7).toString();
                if (!strCampoMinutos.equals("") && !strCampoMinutos.equals("0")) {
                    /*
                     * Comprobar si no se ha cambiado la hora del equipo a una
                     * fecha anterior al ultimo despacho
                     */
                    fechaHoraUltimoDespacho = bd.obtenerUltimaFechaHoraDespacho();
                    calendar = new GregorianCalendar();
                    long horaActual = (calendar.getTimeInMillis() / 1000);
                    if (fechaHoraUltimoDespacho <= horaActual) {
                        /**
                         * Comprueba si la unidad no ha sido bloqueada por falta
                         * de pago desde el servidor KRADAC
                         */
                        if ((bd.getEstadoUnidadPendientePago(unidad)
                                && ConsultaRecorridosServidorBD.HayInternet)
                                || !(ConsultaRecorridosServidorBD.HayInternet)) {
                            /**
                             * Comprueba si se despacho correctamente para
                             * quitar los datos del cliente del mapa
                             */
                            boolean seDespacho = DespacharCliente(intFila);
                            if (seDespacho) {
                                quitarClienteMapaLocal(cod_cli, unidad);
                            } else {
                                JOptionPane.showMessageDialog(Principal.gui,
                                        "Falta ingresar el tiempo estimado de llegada\n"
                                        + "a recoger el pasajero...",
                                        "Error...", 0);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this,
                                    "Esta unidad ha sido bloqueada por falta de PAGO, no se podrá despachar "
                                    + "más carreras hasta que no se comunique con KRADAC\n"
                                    + "para que sea habilitada nuevamente...", "Error...", 0);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Hay un problema con la hora y fecha del equipo, igualar el reloj de su computador,\n"
                                + "caso contrario no le dejará despachar las carreras...", "Error...", 0);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");
                        log.error("Problema de hora del equipo\n\n\n"
                                + "Se quiere despachar con hora del equipo anterior al último despacho...\n\n"
                                + "Empresa:\t" + sesion[1]
                                + "\nUsuario:\t" + sesion[2]
                                + "\nFecha y hora último Despacho:\t" + sdf.format(fechaHoraUltimoDespacho * 1000)
                                + "\nFecha y hora actual del equipo:\t" + sdf.format(calendar.getTime())
                                + "\n\n");
                    }
                }
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(Principal.gui,
                        "Falta ingresar el tiempo estimado de llegada\n"
                        + "a recoger el pasajero...",
                        "Error...", 0);
            }
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(Principal.gui,
                    "Debe ingresar la unidad que recogerá al pasajero",
                    "Error...", 0);
        } catch (ArrayIndexOutOfBoundsException aiobe) {
        }

        jtCodigo.requestFocusInWindow();

    }

    /**
     * Guarda un cliente que llamo pero que no tiene codigo, para que cuando
     * vuelva a llamar sepa que cliente es.
     * @param Despacho
     */
    private void guardarClienteSinCodigo(Despachos des) {
        if (!des.getStrTelefono().equals("")) {
            des.setStrNumeroCasa("");
            des.setStrReferecia("");
            bd.InsertarCliente(des);
        }
    }

    /**
     * Permite poner todo por defecto cuando se realice un despacho exitoso
     * o fallido
     */
    private void InicializarVariables() {
        jtTelefono.setText("");
        jtCodigo.setText("");
        resetValDespacho();
    }

    /**
     * Valida si los campos requeridos para el despacho estan ingresados
     * @param d
     * @param fila
     * @return boolean -> true si se puede despachar
     */
    private boolean sePuedeDespachar(Despachos d, int fila) {
        boolean resultado = false;

        if (d.getStrHora() == null || d.getStrHora().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Se debe ingresar una hora de despacho...",
                    "Error...", 0);
            try {
                jtPorDespachar.setValueAt(funciones.getHora(), fila, 0);
            } catch (IndexOutOfBoundsException iex) {
            }
        } else if (d.getStrNombre() == null || d.getStrNombre().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Se debe ingresar un nombre de cliente...",
                    "Error...", 0);
        } else if (d.getStrDireccion() == null || d.getStrDireccion().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Se debe ingresar una dirección del cliente...",
                    "Error...", 0);
        } else if (d.getStrBarrio() == null || d.getStrBarrio().equals("")) {
            JOptionPane.showMessageDialog(this,
                    "Se debe ingresar un barrio donde vive el cliente...",
                    "Error...", 0);
        } else if (d.getIntMinutos() < 0) {
            JOptionPane.showMessageDialog(this,
                    "La estimación de tiempo de llegada a recojer al cliente es incorrecta, "
                    + "tiene que ser mayor a 0...",
                    "Error...", 0);
            try {
                jtPorDespachar.setValueAt("", fila, 7);
                jtPorDespachar.setValueAt("0", fila, 8);
            } catch (IndexOutOfBoundsException iex) {
            }
        } else if (!validarUnidad(d.getIntUnidad())) {
            JOptionPane.showMessageDialog(this,
                    "La unidad ingresada no es válida...",
                    "Error...", 0);
            jtPorDespachar.setValueAt("", fila, 6);
        } else {
            resultado = true;
        }

        return resultado;
    }

    /**
     * Comprueba si es nulo el valor que se recoje de la tabla, en el campo Fecha
     * para presentar los datos de ese cliente en esa fila
     * @return boolean -> true si es null o vacio
     */
    private boolean esNullFechaTablaPorDespachar() {
        boolean boolNull = jtPorDespachar.getValueAt(getIntFilaSeleccionada(), 0) == null;
        if (!boolNull) {
            if (jtPorDespachar.getValueAt(getIntFilaSeleccionada(), 0).equals("")) {
                boolNull = true;
            } else {
                boolNull = false;
            }
        }
        return boolNull;
    }

    /**
     * Comprueba si es nulo el valor que se recoje de la tabla, en el campo telefono
     * para presentar los datos de ese cliente en esa fila
     * @return boolean -> true si es null o vacio
     */
    private boolean esNullTelefonoTablaPorDespachar() {
        boolean boolTelefonoNull = jtPorDespachar.getValueAt(getIntFilaSeleccionada(), 1) == null;
        if (!boolTelefonoNull) {
            if (jtPorDespachar.getValueAt(getIntFilaSeleccionada(), 1).equals("")) {
                boolTelefonoNull = true;
            } else {
                boolTelefonoNull = false;
            }
        }
        return boolTelefonoNull;
    }

    /**
     * Comprueba si es nulo el valor que se recoje de la tabla, en el campo codigo
     * para presentar los datos de ese cliente en esa fila
     * @return boolean -> true si es null o vacio
     */
    private boolean esNullCodigoTablaPorDespachar() {
        boolean boolNull = jtPorDespachar.getValueAt(getIntFilaSeleccionada(), 2) == null;
        if (!boolNull) {
            if (jtPorDespachar.getValueAt(getIntFilaSeleccionada(), 2).equals("")) {
                boolNull = true;
            } else {
                boolNull = false;
            }
        }
        return boolNull;
    }

    /**
     * Crea un nuevo objeto de la ventana de datos si no esta creado,
     * y si esta creado pero oculto muestra la ventana con los datos 
     * actualizados
     * 
     * @param ConTelefono -> verdadero si esta el telefono en la tabla falso si no hay telefono
     */
    private void abrirVentanaDatosCliente(int casoTelefono) {
        switch (casoTelefono) {
            case 1:
                if (ventanaDatos == null) {
                    ventanaDatos = new VentanaDatos(getDatosPorDespachar(), bd, 1);
                    ventanaDatos.setDatosFila(jtPorDespachar, getIntFilaSeleccionada());
                    ventanaDatos.setVisible(true);
                } else {
                    ventanaDatos.setPorDespachar(getDatosPorDespachar(), 1);
                    ventanaDatos.setDatosFila(jtPorDespachar, getIntFilaSeleccionada());
                    ventanaDatos.setVisible(true);
                    ventanaDatos.setLocationRelativeTo(this);
                }
                break;
            case 2:
                /**
                 * Mostar la ventana de datos del cliente que esta en la tabla por despachar
                 * pero que no tiene un telefono
                 */
                if (ventanaDatos == null) {
                    ventanaDatos = new VentanaDatos(getDatosPorDespachar(), bd, 2);
                    ventanaDatos.setDatosFila(jtPorDespachar, getIntFilaSeleccionada());
                    ventanaDatos.setVisible(true);
                } else {
                    ventanaDatos.setPorDespachar(getDatosPorDespachar(), 2);
                    ventanaDatos.setDatosFila(jtPorDespachar, getIntFilaSeleccionada());
                    ventanaDatos.setVisible(true);
                    ventanaDatos.setLocationRelativeTo(this);
                }
                break;
            case 3:
                /**
                 * Mostar la ventana de datos del cliente que esta en la tabla por despachar
                 * pero que no tiene un telefono
                 */
                if (ventanaDatos == null) {
                    ventanaDatos = new VentanaDatos(getDatosPorDespachar(), bd, 3);
                    ventanaDatos.setDatosFila(jtPorDespachar, getIntFilaSeleccionada());
                    ventanaDatos.setVisible(true);
                } else {
                    ventanaDatos.setPorDespachar(getDatosPorDespachar(), 3);
                    ventanaDatos.setDatosFila(jtPorDespachar, getIntFilaSeleccionada());
                    ventanaDatos.setVisible(true);
                    ventanaDatos.setLocationRelativeTo(this);
                }
                break;
        }
        ventanaDatos.setAlwaysOnTop(true);
    }

    /**
     * Pone la Hora en la columna de hora cuando no se hace un despacho auntomatico
     */
    public void PonerHoraParaDespachar() {
        int fila = jtPorDespachar.getSelectedRow();
        int col = jtPorDespachar.getSelectedColumn();

        if (col == 0) {
            jtPorDespachar.setValueAt(funciones.getHora(), fila, col);
        }
    }

    /**
     * Recoje los datos de las fila seleccionada y crea un objeto con todos esos datos
     * @return Despachos
     */
    private Despachos getDatosPorDespachar() {
        Despachos datos = null;

        int fila = 0;
        try {
            fila = getIntFilaSeleccionada();

            strHora = (String) jtPorDespachar.getValueAt(fila, 0);
            strTelefono = (String) jtPorDespachar.getValueAt(fila, 1);
            intCodigo = (String) jtPorDespachar.getValueAt(fila, 2);
            strNombre = (String) jtPorDespachar.getValueAt(fila, 3);
            strBarrio = (String) jtPorDespachar.getValueAt(fila, 4);
            strDireccion = (String) jtPorDespachar.getValueAt(fila, 5);

            try {
                intMinutos = Integer.parseInt(jtPorDespachar.getValueAt(fila, 7).toString());
            } catch (NumberFormatException nfe) {
                intMinutos = 0;
                jtPorDespachar.setValueAt("", fila, 7);
            } catch (NullPointerException ex) {
                intMinutos = 0;
            }
            try {
                intUnidad = Integer.parseInt(jtPorDespachar.getValueAt(fila, 6).toString());
            } catch (NumberFormatException nfe) {
                jtPorDespachar.setValueAt("", fila, 6);
                intUnidad = 0;
            } catch (NullPointerException ex) {
                intUnidad = 0;
            }
            try {
                intAtraso = Integer.parseInt(jtPorDespachar.getValueAt(fila, 8).toString());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this,
                        "Solo debe ser número los campos de Atraso(ATR)...",
                        "Error...", 0);
                jtPorDespachar.setValueAt("0", fila, 8);
                intAtraso = 0;
            } catch (NullPointerException ex) {
                intAtraso = 0;
            }

            strNota = (String) jtPorDespachar.getValueAt(fila, 9);

            datos = new Despachos(strHora, strTelefono, intCodigo, strNombre, strDireccion, strBarrio,
                    intMinutos, intUnidad, intAtraso, strNota, id_Turno, sesion[0]);

        } catch (ArrayIndexOutOfBoundsException aiobe) {
            datos = new Despachos(null, null, null, "", "", "", 0, 0, 0, "", id_Turno, sesion[0]);
        }

        return datos;
    }

    /**
     * Obtener el código del usuario dependiendo del telefono ingresado
     * @return String
     */
    private String getBuscarPorTelefono() {

        strTelefono = funciones.validarTelefono(jtTelefono.getText());
        try {
            if (!strTelefono.equals("")) {
                rs = bd.getClientePorTelefono(strTelefono);
                intCodigo = rs.getString("CODIGO");
                strNombre = rs.getString("NOMBRE_APELLIDO_CLI");
                strDireccion = rs.getString("DIRECCION_CLI");
                strBarrio = rs.getString("SECTOR");
                strHora = funciones.getHora();
                despacho = new Despachos(funciones.getHoraEnMilis(),
                        strHora,
                        strTelefono,
                        intCodigo,
                        strNombre,
                        strDireccion,
                        strBarrio,
                        "");

                if (desPorTabla_Campo) { //Despacha por tabla
                    setDatosFila(despacho, jtPorDespachar);
                    setClienteMapaLocal("" + despacho.getIntCodigo(), strNombre, strBarrio, strTelefono);
                } else { //despacha por campo telefono
                    setDatosTablas(despacho, jtPorDespachar);
                    setClienteMapaLocal("" + despacho.getIntCodigo(), strNombre, strBarrio, strTelefono);
                    jtPorDespachar.setColumnSelectionInterval(6, 6);
                    jtPorDespachar.setRowSelectionInterval(0, 0);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Numero ingresado no valido...",
                        "Error", 0);
                jtTelefono.setText("");
            }
        } catch (SQLException ex) {
            if (desPorTabla_Campo) {
                despacho = new Despachos(
                        funciones.getHoraEnMilis(),
                        "",
                        funciones.getFecha(),
                        funciones.getHora(),
                        jtPorDespachar.getValueAt(filaAnt, 1).toString(),
                        "",
                        "",
                        "",
                        "",
                        "",
                        "0",
                        "",
                        "",
                        "" + id_Turno,
                        sesion[0]);
                setDatosFila(despacho, jtPorDespachar);
            } else {
                dtm = (DefaultTableModel) jtPorDespachar.getModel();
                String[] inicial = {
                    funciones.getHora(),
                    funciones.validarTelefono(jtTelefono.getText()),
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "0",
                    ""};
                dtm.insertRow(0, inicial);
                jtPorDespachar.setColumnSelectionInterval(3, 3);
                jtPorDespachar.setRowSelectionInterval(0, 0);

            }
        }
        return intCodigo;
    }

    /**
     * Obtener el telefono del usuario dependiendo del codigo ingresado
     * @return String
     */
    private String getBuscarPorCodigo() {

        intCodigo = jtCodigo.getText();
        try {
            String sql = "SELECT CODIGO,"
                    + "TELEFONO,"
                    + "NOMBRE_APELLIDO_CLI,"
                    + "DIRECCION_CLI,"
                    + "SECTOR"
                    + " FROM CLIENTES WHERE CODIGO='" + intCodigo + "'";
            rs = bd.ejecutarConsultaUnDato(sql);
            intCodigo = rs.getString("CODIGO");
            strTelefono = rs.getString("TELEFONO");
            strNombre = rs.getString("NOMBRE_APELLIDO_CLI");
            strDireccion = rs.getString("DIRECCION_CLI");
            strBarrio = rs.getString("SECTOR");
            strHora = funciones.getHora();

            despacho = new Despachos(funciones.getHoraEnMilis(),
                    strHora,
                    strTelefono,
                    intCodigo,
                    strNombre,
                    strDireccion,
                    strBarrio,
                    "");

            setDatosTablas(despacho, jtPorDespachar);
            setClienteMapaLocal("" + despacho.getIntCodigo(), strNombre, strBarrio, strTelefono);

            jtPorDespachar.setColumnSelectionInterval(6, 6);
            jtPorDespachar.setRowSelectionInterval(0, 0);
            jtPorDespachar.requestFocus();

        } catch (SQLException ex) {
            jtTelefono.setText("");
        } catch (NullPointerException ex) {
            jtTelefono.setText("");
        }
        return strTelefono;
    }

    /**
     * Activa y pone en libre las unidades seleccionadas en el servidor, y remueve
     * el registro del despacho de la lista de despachos temporales
     * @param codVehiculo
     */
    private void removerDespachoDeTemporal(String unidad) {

        for (int i = 0; i < listaDespachosTemporales.size(); i++) {
            if (listaDespachosTemporales.get(i).getIntUnidad() == Integer.parseInt(unidad)) {
                /**
                 * Guarda la liberacion de la unidad en el servidor de Kradac
                 */
                GuardarServidorKRADAC server = new GuardarServidorKRADAC(listaDespachosTemporales.get(i), false, bd);
                server.start();
                listaDespachosTemporales.remove(i);
                break;
            }
        }
    }

    /**
     * Cambia el estado de un TAXI en la BD
     * @param etq
     * @param codVh
     */
    private void cambiarEstadoTaxi(String etq, ArrayList<String> codVh) {
        int et = etiq.indexOf(etq);
        String codig = codigo.get(et);

        for (String i : codVh) {
            //String sql = "INSERT INTO REGCODESTTAXI VALUES (now(),now(),'" + codig + "','" + sesion[0] + "','" + i + "')";
            //bd.ejecutarSentenciaStatement2(sql);
            bd.setCambiarEstadoUnidad(codig, sesion[0], i);
            /*
             * Si es AC significa que va activar esa unidad por tal motivo hay que
             * remover todo el despacho del temporal y guardar la liberacion en el
             * servidor KRADAC
             */
            if (bd.getCodigoEtiquetaEstadoUnidad(etq).equals("AC")) {
                removerDespachoDeTemporal(i);
            }
        }
        pintarEstadoTaxi(strEncabezados);
    }

    private static void colorCodigosBD() {
        try {
            String sql = "SELECT ID_CODIGO,COLOR,ETIQUETA  FROM CODESTTAXI";
            ResultSet rsColor = bd.ejecutarConsultaUnDato(sql);

            codigo.clear();
            color.clear();
            etiq.clear();
            do {
                codigo.add(rsColor.getString(1));
                color.add(rsColor.getString(2));
                etiq.add(rsColor.getString(3));
                etiqColor.put(rsColor.getString(3), rsColor.getString(2));
            } while (rsColor.next());

        } catch (SQLException ex) {
            if (ex.getMessage().equals("Operation not allowed after ResultSet closed")) {
                log.trace("ResultSet rsColor cerrado...");
                colorCodigosBD();
                log.trace("Reconsultar colorCodigosBD()");
            } else if (ex.getMessage().equals("Query generated no fields for ResultSet")) {
                log.trace("No se obtuvieron campos para el ResultSet...");
            } else if (ex.getMessage().equals("Illegal operation on empty result set.")) {
                log.trace("No hay estados para los taxis, crear estados por defecto...");
            } else {
                log.error("{}", sesion[1], ex);
            }
        } catch (NullPointerException ex) {
            //System.out.println("NULL AL PINTAR EL ESTADO... colorCodigosBD()");
        }
    }

    /**
     * Coloca el color de fondo de las celdas
     * especificadas según el estado del taxi.
     * @param encab  columnas a pintar
     */
    private static void pintarEstadoTaxi(ArrayList<String> encab) {
        //Clave valor
        //[n_unidad]==>[id_codigo]
        Map unidadCodigoBD = new HashMap();
        ResultSet rsPintarEstadoTaxi = null;
        try {
            try {
                //String sql = "SELECT A.N_UNIDAD, A.ID_CODIGO FROM REGCODESTTAXI A, ( SELECT AUX.N_UNIDAD, MAX(CONCAT(AUX.FECHA,AUX.HORA)) AS TMP FROM REGCODESTTAXI AUX GROUP BY AUX.N_UNIDAD) AS B WHERE A.N_UNIDAD = B.N_UNIDAD AND CONCAT(A.FECHA,A.HORA) = B.TMP";
                //rsPintarEstadoTaxi = bd.ejecutarConsultaStatement2(sql);
                rsPintarEstadoTaxi = bd.getUnidadesPintarEstado();
                while (rsPintarEstadoTaxi.next()) {
                    try {
                        unidadCodigoBD.put(rsPintarEstadoTaxi.getString(1), rsPintarEstadoTaxi.getString(2));
                    } catch (NullPointerException ex) {
                        //System.err.println("Null al obtener unidad y id_cod...");
                    }
                } // ArrayList codigo color
                colorCodigosBD();

            } catch (SQLException ex) {
                if (ex.getMessage().equals("Operation not allowed after ResultSet closed")) {
                    log.error("ResultSet rsPintarEstadoTaxi cerrado");
                } else if (ex.getMessage().equals("Query generated no fields for ResultSet")) {
                    log.error("NO hay campos para el resulset rsPintarEstadoTaxi");
                } else if (ex.getMessage().equals("After end of result set")) {
                } else {
                    log.error("{}", sesion[1], ex);
                }
            }

            DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
            tcr.setHorizontalAlignment(SwingConstants.CENTER);
            tcr.setVerticalAlignment(SwingConstants.CENTER);

            for (int i = 0, len = jtVehiculos.getColumnCount(); i < len; i++) {
                TableColumn column = jtVehiculos.getColumn(jtVehiculos.getColumnName(i));
                column.setCellRenderer(new formatoTabla(encab, unidadCodigoBD, codigo, color));
            }
            jtVehiculos.repaint();
        } catch (NullPointerException ex) {
            System.out.println("NULL AL PINTAR EL ESTADO... pintarEstadoTaxi()");
        }
    }

    /**
     * Permite enviar a la base el estado activo para la unidad seleccionada,
     * por mal manejo se ingresan dos estado diferentes en la base a la misma hora
     * y el programa no puede decidir cual es el que es válido entonces se inicia
     * nuevamente.
     * @param intFila
     */
    private void activarUnidadConDosEstados(int intFila) {
        String unidad = jtPorDespachar.getValueAt(intFila, 6).toString();
        log.trace("REACTIVAR LA UNIDAD[{}]", unidad);
        setColorDespachoVehiculo(unidad, bd.getNombreEstadoUnidad("AC"));
        jtPorDespachar.setValueAt("", intFila, 6);
    }

    /**
     * Permite enviar el mensaje con la información del cliente a la unidad
     * asignada
     */
    private void enviarMensajeUnidadAsignada(int intFila, int intCol) {
        String unidad = "0";
        String nombreCliente = "";
        String dirCliente = "";
        String barrioCliente = "";
        try {
            unidad = jtPorDespachar.getValueAt(intFila, intCol).toString();
            nombreCliente = jtPorDespachar.getValueAt(intFila, 3).toString();
            dirCliente = jtPorDespachar.getValueAt(intFila, 5).toString();
            barrioCliente = jtPorDespachar.getValueAt(intFila, 4).toString();
        } catch (NullPointerException ex) {
        }

        /**
         * Trama para que sean leidas por el taximetro, separadas por %
         */
        String mensaje = "";
        if (barrioCliente.equals("") || barrioCliente.equals("0")) {
            mensaje = "" + nombreCliente + "%" + dirCliente;
        } else {
            mensaje = "" + nombreCliente + "%" + barrioCliente + " | " + dirCliente;
        }
        System.err.println("Mensaje: " + mensaje);

        EnvioMensajesUnidades enviarMensajeUnidad = new EnvioMensajesUnidades(sesion[1], unidad, mensaje, bd);
        enviarMensajeUnidad.start();
    }

    /**
     * Agrega el despacho que se inserte por la tabla que no tenga codigo ni telefono
     * a la lista de despachos temporales para luego ser enviado al servidor
     * @return boolean |true si lo inserto, false y se actualiza uno existente...
     */
    private boolean insertarActualizarDespachoTemporalListaTMP() {
        Despachos desTMP = getDatosPorDespachar();
        if (desTMP.getIntUnidad() != 0) {
            long hora = funciones.getHoraEnMilis();

            if (hora != 0) {
                desTMP.setHoraDeAsignacion(hora);
            } else {
                Calendar c = new GregorianCalendar();
                desTMP.setHoraDeAsignacion(c.getTimeInMillis());
            }

            /**
             * Permite sabes si arcutializar con true con false para añadir
             * un despacho a la lista que esta en memoria
             */
            boolean actualizar = false;
            int intFila = jtPorDespachar.getSelectedRow();
            int i = 0;
            for (Despachos d : listaDespachosTemporales) {
                try {
                    actualizar = d.getStrHora().equals(jtPorDespachar.getValueAt(intFila, 0));
                    if (actualizar) {
                        break;
                    }
                } catch (NullPointerException ex) {
                }
                i++;
            }

            desTMP.setFilaTablaTMP(intFila);

            if (actualizar) {
                desTMP.setHoraEnMilis(listaDespachosTemporales.get(i).getHoraEnMilis());
                listaDespachosTemporales.remove(i);
                listaDespachosTemporales.add(desTMP);
                return false;
            } else {
                desTMP.setHoraEnMilis(funciones.getHoraEnMilis());
                listaDespachosTemporales.add(desTMP);
                return true;
            }
        }
        return false;
    }

    /**
     * Activa la unidad que se quiere borrar y se le quita el estado de asignado
     * @param cod_cli
     */
    private void activarUnidadBorrada(String cod_cli) {
        String estadoActivo = bd.getNombreEstadoUnidad("AC");
        setColorDespachoVehiculo(UnidadAntesDeBorrar, estadoActivo);
        quitarClienteMapaLocal(cod_cli, UnidadAntesDeBorrar);
    }

    /**
     * Pone el estado de asignado a una unidad cuando se asigna una unidad
     * a un cliente en la tabla por despachar
     * @param intFila
     * @param intCol
     * @param cod_cli
     */
    private void actualizarAsignacion(int intFila, int intCol, String cod_cli) {
        String unidad = "0";
        try {
            unidad = jtPorDespachar.getValueAt(intFila, intCol).toString();
        } catch (NullPointerException ex) {
        }
        String strEstadoUnidad = "";
        try {
            if (validarUnidad(Integer.parseInt(unidad))) {
                strEstadoUnidad = bd.getEstadoUnidad(Integer.parseInt(unidad));

                if (strEstadoUnidad != null && strEstadoUnidad.equals("AC")) {

                    String estadoAsignado = bd.getNombreEstadoUnidad("ASI");
                    setColorDespachoVehiculo(unidad, estadoAsignado);

                    actualizarClienteMapaLocal(cod_cli, unidad);

                } else {
                    String estado = bd.getEtiquetaEstadoUnidad(strEstadoUnidad);
                    if (estado != null) {
//                        JOptionPane.showMessageDialog(this, "No se puede asignar una carrera a esa unidad, no está Activa...\nEstado de la unidad: " + estado, "Error", 0);
                        int r = JOptionPane.showConfirmDialog(this, "No se puede asignar una carrera a esa unidad, no está Activa...\nEstado de la unidad: " + estado
                                + "\n<html><b>¿Activar esta unidad?</b></html>", "Error", 0);
                        if (r == 0) {
                            setColorDespachoVehiculo(unidad, bd.getNombreEstadoUnidad("AC"));
                            jtPorDespachar.setColumnSelectionInterval(6, 6);
                            jtPorDespachar.setRowSelectionInterval(intFila, intFila);
                            jtPorDespachar.requestFocus();
                        }
                    } else {
                        int r = JOptionPane.showConfirmDialog(this, "No se puede asignar una carrera a esa unidad, no está Activa..."
                                + "\n<html><b>¿Activar esta unidad?</b></html>", "Error", 0);
                        if (r == 0) {
                            setColorDespachoVehiculo(unidad, bd.getNombreEstadoUnidad("AC"));
                        }
                    }
                    jtPorDespachar.setValueAt("", intFila, intCol);
                }
            } else {
                jtPorDespachar.setValueAt("", intFila, intCol);
                activarUnidadBorrada(cod_cli);
            }
        } catch (NumberFormatException nfex) {
        }
    }

    /**
     * Permite asignar el color de despachado a los taxis que esten en la espera
     * de la finalización de la carrera en la tabla de clientes por despachar
     * @param unidad
     */
    private void setColorDespachoVehiculo(String unidad, String estado) {
        int col = -1;
        for (int i = 0; i < jtVehiculos.getColumnCount(); i++) {
            if (jtVehiculos.getColumnName(i).equals(unidad)) {
                col = i;
            }
        }
        try {
            int[] numCol = {col};
            String etiqueta = estado;

            ArrayList<String> codVehiculo = new ArrayList();

            for (int i = 0; i < numCol.length; i++) {
                codVehiculo.add(strCabecerasColumnasVehiculos[numCol[i]]);
            }

            cambiarEstadoTaxi(etiqueta, codVehiculo);
        } catch (IllegalArgumentException iae) {
        } catch (ArrayIndexOutOfBoundsException iex) {
        }
    }

    /**
     * Actualiza la fila de clientes pord espachar cuandos se ingresa un telefono
     * directamente en la tabla
     * @param intFila
     * @param intCol
     */
    private void actualizarFilaCampoTelefono(int intFila, int intCol) {
        try {
            String tel = jtPorDespachar.getValueAt(intFila, 1).toString();

            if (!tel.equals("")) {
                if (funciones.isNumeric(tel)) {
                    jtTelefono.setText(tel);
                    desPorTabla_Campo = true; //despachar por la tabla de despachos
                    getBuscarPorTelefono();
                } else {
                    JOptionPane.showMessageDialog(this, "Número no válido...", "Error...", 0);
                    jtPorDespachar.setValueAt("", intFila, intCol);
                    jtTelefono.setText("");
                }
            }
        } catch (NullPointerException ne) {
        }
    }

    /**
     * Permite utilizar un metodo generico para borrar las filas para llamarlo
     * desde donde se proceda a borrar las filas de la tabla de clietnes por
     * despachar
     */
    private void borrarFilasFormaSegura() {
        try {
            jtPorDespachar.getCellEditor().stopCellEditing();
        } catch (NullPointerException ex) {
        }
        int intFila = jtPorDespachar.getSelectedRow();
        borrarFilaSeleccionadaPorDespachar(intFila);
    }

    /**
     * Permite mostrar el mensaje de asignacion de las unidades, hora en que se
     * asigna y hora que deberia quitarse la asignacion
     * @param unidad
     */
    private void obtenerHoraDeAsignacionEstado(String unidad) {
        rs = bd.obtenerTiempoDeAsignacionEstado(unidad);
        try {
            if (!rs.getString("ID_CODIGO").equals("AC")
                    && !rs.getString("ID_CODIGO").equals("ASI")
                    && !rs.getString("ID_CODIGO").equals("OCU")) {
                String mensaje = "<html>La unidad <b>" + unidad + "</b> entró en estado: <b>" + rs.getString("ID_CODIGO") + "</b></html>\n"
                        + "Hora de asignación:  " + rs.getString("HORA") + "\n"
                        + "Hora de finalización: " + rs.getString("HORA_FIN");
                JOptionPane.showMessageDialog(this, mensaje, "Información...", 1);
            }
        } catch (SQLException ex) {
            //java.util.logging.Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
        }
        jtVehiculos.setCellSelectionEnabled(false);
    }

    /*
     * Ventana de consultar los clientes por nombre
     */
    private ConsultaClientes cliente;
    /**
     * Ventana para registrar los pendientes de despacho
     */
    private PendientesGUI pendientes;

    /**
     * Permite hacer un filtrado de todos los despachados y mostrar en la tabla 
     * @param campo --> "nombre"||"telefono"||"codigo" --> campos a filtrar
     */
    private void buscarDespachados(String campo, String patron) {
        if (!patron.equals("") || patron != null) {
            if (campo.equals("nombre")) {
                limpiarTablaDespachados();
                jtBuscarPorCodigo.setText("");
                jtBuscarPorTelefono.setText("");

                buscarDespachadosPorNombre(patron);

            } else if (campo.equals("telefono")) {
                limpiarTablaDespachados();
                jtBuscarPorNombre.setText("");
                jtBuscarPorCodigo.setText("");

                buscarDespachadosPorTelefono(patron);

            } else if (campo.equals("codigo")) {
                limpiarTablaDespachados();
                jtBuscarPorNombre.setText("");
                jtBuscarPorTelefono.setText("");

                buscarDespachadosPorCodigo(patron);

            }
        } else {
            limpiarCargarTablaDespachados();
        }
    }

    /**
     * Buscar clientes despachados por un telefono
     * @param telefono
     */
    private void buscarDespachadosPorTelefono(String telefono) {
        try {
            listaDespachados = bd.buscarDespachadosPorTelefono(telefono, sesion[0], id_Turno, funciones.getFecha());
            dtm = (DefaultTableModel) jtDespachados.getModel();

            for (Despachos d : listaDespachados) {
                String[] datos = {
                    d.getStrHora(),
                    d.getStrTelefono(),
                    "" + d.getIntCodigo(),
                    d.getStrNombre(),
                    d.getStrDireccion(),
                    d.getStrBarrio(),
                    "" + d.getIntMinutos(),
                    "" + d.getIntUnidad(),
                    "" + d.getIntAtraso(),
                    d.getStrNota()
                };
                dtm.insertRow(0, datos);
            }
        } catch (NullPointerException nex) {
        }
    }

    /**
     * Buscar clientes despachados por un nombre
     * @param nombre
     */
    private void buscarDespachadosPorNombre(String nombre) {
        try {
            listaDespachados = bd.buscarDespachadosPorNombre(nombre, sesion[0], id_Turno, funciones.getFecha());
            dtm = (DefaultTableModel) jtDespachados.getModel();

            for (Despachos d : listaDespachados) {
                String[] datos = {
                    d.getStrHora(),
                    d.getStrTelefono(),
                    "" + d.getIntCodigo(),
                    d.getStrNombre(),
                    d.getStrDireccion(),
                    d.getStrBarrio(),
                    "" + d.getIntMinutos(),
                    "" + d.getIntUnidad(),
                    "" + d.getIntAtraso(),
                    d.getStrNota()
                };
                dtm.insertRow(0, datos);
            }
        } catch (NullPointerException nex) {
        }
    }

    /**
     * Buscar clientes despachados por un codigo
     * @param nombre
     */
    private void buscarDespachadosPorCodigo(String codigo) {
        try {
            listaDespachados = bd.buscarDespachadosPorCodigo(codigo, sesion[0], id_Turno, funciones.getFecha());
            dtm = (DefaultTableModel) jtDespachados.getModel();

            for (Despachos d : listaDespachados) {
                String[] datos = {
                    d.getStrHora(),
                    d.getStrTelefono(),
                    "" + d.getIntCodigo(),
                    d.getStrNombre(),
                    d.getStrDireccion(),
                    d.getStrBarrio(),
                    "" + d.getIntMinutos(),
                    "" + d.getIntUnidad(),
                    "" + d.getIntAtraso(),
                    d.getStrNota()
                };
                dtm.insertRow(0, datos);
            }
        } catch (NullPointerException nex) {
        }
    }

    /**
     * Obtiene la fila seleccioanda de la tabla despachados
     * @return int
     */
    private int getIntFilaSeleccionadaDespachados() {
        return jtDespachados.getSelectedRow();
    }

    /**
     * Obtiene los datos de la fila seleccionada de los datos de la tabla
     * despachados
     * @return Despachos
     */
    private Despachos getDatosDespachados() {
        Despachos datos = null;

        int fila = 0;
        try {
            fila = getIntFilaSeleccionadaDespachados();

            strHora = (String) jtDespachados.getValueAt(fila, 0);
            strTelefono = (String) jtDespachados.getValueAt(fila, 1);
            intCodigo = (String) jtDespachados.getValueAt(fila, 2);
            strNombre = (String) jtDespachados.getValueAt(fila, 3);
            strBarrio = (String) jtDespachados.getValueAt(fila, 4);
            strDireccion = (String) jtDespachados.getValueAt(fila, 5);
            intUnidad = Integer.parseInt(jtDespachados.getValueAt(fila, 6).toString());
            intMinutos = Integer.parseInt(jtDespachados.getValueAt(fila, 7).toString());
            intAtraso = Integer.parseInt(jtDespachados.getValueAt(fila, 8).toString());
            strNota = (String) jtDespachados.getValueAt(fila, 9);
            datos = new Despachos(strHora, strTelefono, intCodigo, strNombre, strDireccion, strBarrio,
                    intMinutos, intUnidad, intAtraso, strNota, id_Turno, sesion[0]);
        } catch (ArrayIndexOutOfBoundsException aiobe) {
        }
        return datos;
    }

    /**
     * Ingresa una nueva fila para despachar
     */
    private void nuevaFilaDespacho() {
        dtm = (DefaultTableModel) jtPorDespachar.getModel();
        String[] inicial = {funciones.getHora(), "", "", "", "", "", "", "", "0", ""};
        dtm.insertRow(0, inicial);
        jtPorDespachar.setRowSelectionInterval(0, 0);
    }

    /**
     * Convierte a mayusculas todas las letras de una fila de la tabla y le quita
     * el caracter de comillas simple ' para que no cause problemas en la base de
     * datos al construir la sentencia sql
     * @param Tabla
     * @param intFila
     */
    public void convertirMayuculas(JTable Tabla, int intFila) {
        int intTotalColumnas = Tabla.getColumnCount();
        for (int i = 0; i < intTotalColumnas; i++) {
            try {
                String txt = Tabla.getValueAt(intFila, i).toString().toUpperCase();
                txt = txt.replaceAll("'", "");
                if (i == 1) {
                    Tabla.setValueAt(funciones.validarTelefono(txt), intFila, i);
                } else {
                    Tabla.setValueAt(txt, intFila, i);
                }
            } catch (NullPointerException npe) {
            } catch (ArrayIndexOutOfBoundsException aex) {
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                gui = new Principal();
                gui.setVisible(true);
            }
        });
    }

    /**
     * Permite iniciar los controles con los datos del nuevo turno y el mismo
     * usuario
     */
    public static void reiniciarTurno() {
        actualizarTurno();
        limpiarCargarTablaDespachados();
        //redimencionarTablaVehiculos();
        gui.setTitle("Despachos KRADAC - " + sesion[1] + " || " + turno + " || " + sesion[2]);
    }

    /**
     * Ingresa un despacho en la tabla de clientes por Despachar en la
     * Parte superior de la Ventana
     * @param des
     * @param tabla
     */
    public static void setDatosTablas(Despachos des, JTable tabla) {
        trabajarTabla = new TrabajoTablas();
        listaDespachosTemporales.add(des);

        dtm = (DefaultTableModel) tabla.getModel(); //modelo de la tabla PorDespachar

        trabajarTabla.InsertarFilas(tabla, des, dtm);
    }

    /**
     * Ingresa un despacho en la tabla de clientes por Despachar en la
     * Parte superior de la Ventana
     * @param des
     * @param tabla
     */
    private void setDatosFila(Despachos des, JTable tabla) {
        listaDespachosTemporales.add(des);
        LlenarFila(filaAnt, tabla, des);
    }

    private void LlenarFila(int fila, JTable tabla, Despachos d) {
        try {
            tabla.setValueAt(d.getStrHora(), fila, 0);
            tabla.setValueAt(d.getStrTelefono(), fila, 1);
            if (d.getIntCodigo() == 0) {
                tabla.setValueAt("", fila, 2);
            } else {
                tabla.setValueAt("" + d.getIntCodigo(), fila, 2);
            }
            tabla.setValueAt(d.getStrNombre(), fila, 3);
            tabla.setValueAt(d.getStrBarrio(), fila, 4);
            tabla.setValueAt(d.getStrDireccion(), fila, 5);
            tabla.setValueAt(d.getStrNota(), fila, 9);
        } catch (ArrayIndexOutOfBoundsException ex) {
        }
    }

    /**
     * Ingresa Un despacho a la Tabla de de Despachados en la parte inferior
     * @param des
     * @param tabla
     * @return boolean -> truesi se pudo insertar en la base de datos
     */
    public boolean setDatosTablaDespachados(Despachos des, JTable tabla) {
        boolean r = bd.InsertarDespachoCliente(des, true);
        int fila = jtPorDespachar.getSelectedRow();

        if (!r) {
            String estado = bd.getEstadoUnidad();
            if (estado != null) {
                JOptionPane.showMessageDialog(this, "No se puede despachar esa unidad no está Asignada...\nEstado de la unidad: " + estado, "Error", 0);
            } else {
                JOptionPane.showMessageDialog(this, "No se puede despachar la unidad asignada ya que tiene un ESTADO, conflicto de estados en el mismo tiempo"
                        + "\nse activará nuevamente esta unidad...", "Error", 0);
                activarUnidadConDosEstados(fila);
            }
            jtPorDespachar.setValueAt("0", fila, 6);
        } else {
            trabajarTabla = new TrabajoTablas();
            listaDespachados.add(des);
            dtm = (DefaultTableModel) tabla.getModel(); ////modelo de la tabla Despachados
            trabajarTabla.InsertarFilasDespachados(tabla, des, dtm);
        }
        return r;
    }

    /**
     * Comprueba si una unidad existe
     * @param intUnidad
     * @return boolean -> true si existe
     */
    private boolean validarUnidad(int intUnidad) {
        return bd.validarUnidad(intUnidad);
    }

    /**
     * @return the intFilaSeleccionada
     */
    public int getIntFilaSeleccionada() {
        try {
            intFilaSeleccionada = jtPorDespachar.getSelectedRow();
        } catch (NullPointerException ex) {
            //System.out.println("No hay fila");
        }
        return intFilaSeleccionada;
    }

    /**
     * @param intFilaSeleccionada the intFilaSeleccionada to set
     */
    public void setIntFilaSeleccionada(int intFilaSeleccionada) {
        this.intFilaSeleccionada = intFilaSeleccionada;
    }

    /**
     * Pone valores por defecto al objeto despacho para su posterior llenado
     * esto se usa cuando se elimina una columna que tambiens e registra en la
     * base para que no vaya con valores null y cause problemas.
     */
    private void resetValDespacho() {
        despacho = new Despachos("", "", "", "", "", "", 0, 0, 0, "", id_Turno, sesion[0]);
        intMinutos = 0;
    }

    /**
     * Saber los codigos que estan listos para despachar para la generación
     * de un nuevo codigo se deberia tomar en cuenta esto, por ahora no da problemas
     * si llegara a suceder algun error en la generacion del los codigos se debe
     * usar este metodo
     * @return String[] codigos por despachar
     */
    public static String[] getCodigosPorDespachar() {
        ArrayList<String> codigosLista = new ArrayList<String>();
        Object c = null;
        String[] datosCast;
        for (int i = 0; i < jtPorDespachar.getRowCount(); i++) {
            c = jtPorDespachar.getValueAt(i, 2);
            if (c != null) {
                codigosLista.add(c.toString());
            }
        }
        datosCast = new String[codigosLista.size()];
        return codigosLista.toArray(datosCast);
    }

    /**
     * Inserta un nuevo despacho desde la tabla de buscar clientes por nombre,
     * alli se asigna true, si es falso se ingresa un cliente de color rojo
     * sirve para ingresar los despachos pendientes
     * @param cliente
     * @param desp -> true negro | false rojo
     */
    public static void ingresarClientePorDespachar(Clientes cliente, String nota) {
        Despachos despacharClienteNombre = new Despachos();
        despacharClienteNombre.setStrHora(funciones.getHora());
        try {
            despacharClienteNombre.setIntCodigo(Integer.parseInt(cliente.getCodigo()));
        } catch (NullPointerException ex) {
            despacharClienteNombre.setIntCodigo(0);
        }
        despacharClienteNombre.setStrTelefono(cliente.getTelefono());
        despacharClienteNombre.setStrNombre(cliente.getNombre());
        despacharClienteNombre.setStrDireccion(cliente.getDireccion());
        despacharClienteNombre.setStrBarrio(cliente.getBarrio());
        despacharClienteNombre.setStrReferecia(cliente.getReferencia());
        despacharClienteNombre.setStrNumeroCasa(cliente.getN_casa());
        despacharClienteNombre.setStrNota(nota);
        /**
         * Es verdadero si se ingresa el cliente desde la tabla de busqueda de clientes
         * por el nombre
         */
        setDatosTablas(despacharClienteNombre, jtPorDespachar);
    }

    /**
     * Permite insertar un cliente en la tabla de posisionamiento para que sea
     * dibujado en el mapa
     * @param codigoCliente
     * @param n_unidad
     */
    private void setClienteMapaLocal(String codigoCliente, String strNombre, String strBarrio, String strTelefono) {
        try {
            rs = bd.obtenerLatLonCliente(Integer.parseInt(codigoCliente));
            double lat = rs.getDouble("LATITUD");
            double lon = rs.getDouble("LONGITUD");
            if (lon != 0 && lat != 0) {
                if (!codigoCliente.equals("") || !codigoCliente.equals("0")) {
                    bd.InsertarClienteMapa(Integer.parseInt(codigoCliente), strNombre, strBarrio, strTelefono, lat, lon);
                }
            }
        } catch (SQLException ex) {
            log.trace("{} - Illegal operation on empty result set", sesion[1]);
        }
    }

    /**
     * Quitar cliente del mapa
     * @param codigoCliente
     * @param n_unidad
     */
    private void quitarClienteMapaLocal(String codigoCliente, String n_unidad) {
        try {
            if (!codigoCliente.equals("")) {
                if (!n_unidad.equals("")) {
                    bd.EliminarClienteMapa(Integer.parseInt(codigoCliente), Integer.parseInt(n_unidad));
                } else {
                    bd.EliminarClienteMapa(Integer.parseInt(codigoCliente));
                }
            }
        } catch (NumberFormatException ex) {
        }
    }

    /**
     * Pone la unidad que recogera al cliente en el mapa
     * @param cod_cli
     * @param unidad
     */
    private void actualizarClienteMapaLocal(String cod_cli, String unidad) {
        if (!cod_cli.equals("") || !cod_cli.equals("0")) {
            bd.ActualizarUnidadClienteMapa(Integer.parseInt(cod_cli), Integer.parseInt(unidad));
        }
    }

    /**
     * Actualiza en memoria los cambios que haya en las unidades para poder calcular
     * el tiempo de despacho y de asignacion en el servidor
     * @param idx
     */
    private void actualizarListaTMPHoraIngreso(int idx, String horaAnt) {
        String horaTbl = horaAnt;
        String horaDspch = "";

        for (int i = 0; i < listaDespachosTemporales.size(); i++) {
            horaDspch = listaDespachosTemporales.get(i).getStrHora();
            if (horaTbl.equals(horaDspch)) {
                listaDespachosTemporales.get(i).setStrHora(jtPorDespachar.getValueAt(idx, 0).toString());
            }
        }
    }

    /**
     * Realiza la insersion del despacho y de la asignacion de la aunidad
     * en el servidor de Kradac
     */
    private void insertarAsignacionDespachoServidorKradac() {
        int intFila = jtPorDespachar.getSelectedRow();

        long minutos;
        int unidadTabla = 0;
        long horaDespacho = 0;
        long horaDes = 0;

        for (Despachos d : listaDespachosTemporales) {
            unidadTabla = Integer.parseInt(jtPorDespachar.getValueAt(intFila, 6).toString());

            if (d.getIntUnidad() == unidadTabla) {
                horaDespacho = funciones.getHoraEnMilis();

                if (horaDespacho != 0) {
                    d.setHoraDeDespacho(horaDespacho);
                } else {
                    Calendar c = new GregorianCalendar();
                    horaDes = c.getTimeInMillis();
                    d.setHoraDeDespacho(horaDes);
                }
                minutos = ((d.getHoraDeDespacho() - d.getHoraDeAsignacion()) / 1000) / 60;
                d.setMinutosEntreClienteServidor(Integer.parseInt("" + minutos));
                GuardarServidorKRADAC server = new GuardarServidorKRADAC(d, true, bd);
                server.start();

                break;
            }
        }
    }

    /**
     * Muestra el mensaje de aviso que se tiene que despachar un cliente
     * @param Pendientes
     */
    public static void lanzarMensajePendiente(Pendientes p) {
        ponerAlarmaPendiente(true);
        System.out.println("Lanzar mensaje Pendiente");
        lblRecordar.setText("" + p.getMinRecuerdo());
        String cod = "";
        try {
            cod = p.getCliente().getCodigo();
        } catch (NullPointerException ex) {
            cod = "0";
        }

        String nombre = "";
        try {
            nombre = p.getCliente().getNombre();
        } catch (NullPointerException ex) {
            nombre = "";
        }
        lblCliente.setText(cod + " - " + nombre + " - " + p.getHora());
    }

    /**
     * Ingresa en la tabla de clientes por despachar el cliente pendiente
     * @param Pendientes
     */
    public static void lanzarPendiente(Pendientes p) {
        ponerAlarmaPendiente(false);
        ingresarClientePorDespachar(p.getCliente(), p.getNota());

        /**
         * Permite lanzar el sonido independientemente cuando haya pendientes
         * dobles no se duerma el reloj y no haya congestiones
         */
        Thread hiloSonido = new Thread(new Runnable() {

            public void run() {
                try {
                    Sonido sonido = new Sonido(bd.getValorConfiguiracion("sonido_nueva_pendiente"), Integer.parseInt(bd.getValorConfiguiracion("tiempo_sonido")));
                } catch (NumberFormatException ex) {
                    Sonido sonido = new Sonido("", 0);
                }
            }
        });
        hiloSonido.start();
    }

    /**
     * Pone el icono de alarma cuando hay una pendiente y muestra el panel de
     * los datos del cliente que se va despachar
     * @param boolean
     */
    private static void ponerAlarmaPendiente(boolean op) {
        lblAlarmaPendiente.setVisible(op);
        jpPendiente.setVisible(op);
        if (op == true) {
            Thread hiloSonido = new Thread(new Runnable() {

                public void run() {
                    try {
                        Sonido sonido = new Sonido(bd.getValorConfiguiracion("sonido_pendiente"), Integer.parseInt(bd.getValorConfiguiracion("tiempo_sonido")));
                    } catch (NumberFormatException ex) {
                        Sonido sonido = new Sonido("", 0);
                    }
                }
            });
            hiloSonido.start();
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructores">
    //--------------------------------------------------------------------------
    // Constructores
    //--------------------------------------------------------------------------
    /**
     * Constructor para recibir los datos de sesion desde el menu principal
     * @param String[] sesion -> (0)=nickUsuario,(1)=id_empresa,(2)=NombreUsuario
     */
    public Principal(String[] info, ConexionBase conec) {
        setSession(info);
        Principal.main(null);
        Principal.bd = conec;
    }

    public Principal(String[] info, ConexionBase conec, Properties archivo) {
        setSession(info);
        Principal.main(null);
        Principal.bd = conec;
        Principal.arcConfig = archivo;
        log.trace("Iniciar la aplicacion de despachos...");
        log.trace("Usuario: {}", sesion[2]);
        log.trace("Rol: {}", sesion[3]);
    }

    /**
     * Constructor Por defecto
     */
    public Principal() {
        initComponents();
        ConfiguracionInicial();
    }

    // </editor-fold>
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jsVehiculos = new javax.swing.JScrollPane();
        jtVehiculos = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtPorDespachar = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtDespachados = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtTelefono = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jtCodigo = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cbEstadosTaxi = new javax.swing.JComboBox();
        btnColor = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jbEliminarFila = new javax.swing.JButton();
        jbLimpiarCampos = new javax.swing.JButton();
        jbNuevoDespacho = new javax.swing.JButton();
        jbDespachar = new javax.swing.JButton();
        lblReloj = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jtBuscarPorNombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtBuscarPorTelefono = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jtBuscarPorCodigo = new javax.swing.JTextField();
        jlIndicadorLlamada = new javax.swing.JLabel();
        jlSenalInternet = new javax.swing.JLabel();
        jpPendiente = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblRecordar = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblCliente = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jbSalir = new javax.swing.JButton();
        jbMenu = new javax.swing.JButton();
        jbBuscarClienteNombre = new javax.swing.JButton();
        jbPendientes = new javax.swing.JButton();
        lblFecha = new javax.swing.JLabel();
        lblSenal = new javax.swing.JLabel();
        lblAlarmaPendiente = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jsVehiculos.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsVehiculos.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jsVehiculos.setAutoscrolls(true);
        jsVehiculos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jtVehiculos.setFont(new java.awt.Font("Tahoma", 1, 12));
        jtVehiculos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jtVehiculos.setCellSelectionEnabled(true);
        jtVehiculos.getTableHeader().setReorderingAllowed(false);
        jtVehiculos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jtVehiculosMousePressed(evt);
            }
        });
        jsVehiculos.setViewportView(jtVehiculos);
        jtVehiculos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        jtPorDespachar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Hora", "Télefono", "Código", "Nombre", "Barrio", "Dirección", "UNI", "MIN", "ATR", "NOTA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, true, true, true, true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtPorDespachar.getTableHeader().setReorderingAllowed(false);
        jtPorDespachar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jtPorDespacharMousePressed(evt);
            }
        });
        jtPorDespachar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtPorDespacharFocusLost(evt);
            }
        });
        jtPorDespachar.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jtPorDespacharPropertyChange(evt);
            }
        });
        jtPorDespachar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtPorDespacharKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jtPorDespachar);
        jtPorDespachar.getColumnModel().getColumn(0).setMinWidth(62);
        jtPorDespachar.getColumnModel().getColumn(0).setMaxWidth(62);
        jtPorDespachar.getColumnModel().getColumn(1).setMinWidth(80);
        jtPorDespachar.getColumnModel().getColumn(1).setMaxWidth(80);
        jtPorDespachar.getColumnModel().getColumn(2).setMinWidth(50);
        jtPorDespachar.getColumnModel().getColumn(2).setMaxWidth(50);
        jtPorDespachar.getColumnModel().getColumn(3).setPreferredWidth(150);
        jtPorDespachar.getColumnModel().getColumn(4).setPreferredWidth(150);
        jtPorDespachar.getColumnModel().getColumn(5).setPreferredWidth(250);
        jtPorDespachar.getColumnModel().getColumn(6).setMinWidth(30);
        jtPorDespachar.getColumnModel().getColumn(6).setMaxWidth(30);
        jtPorDespachar.getColumnModel().getColumn(7).setMinWidth(28);
        jtPorDespachar.getColumnModel().getColumn(7).setMaxWidth(28);
        jtPorDespachar.getColumnModel().getColumn(8).setMinWidth(28);
        jtPorDespachar.getColumnModel().getColumn(8).setMaxWidth(28);
        jtPorDespachar.getColumnModel().getColumn(9).setMinWidth(100);
        jtPorDespachar.getColumnModel().getColumn(9).setMaxWidth(100);

        jtDespachados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hora", "Teléfono", "Código", "Nombre", "Barrio", "Dirección", "UNI", "MIN", "ATR", "NOTA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtDespachados.getTableHeader().setReorderingAllowed(false);
        jtDespachados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jtDespachadosMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(jtDespachados);
        jtDespachados.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jtDespachados.getColumnModel().getColumn(0).setMinWidth(62);
        jtDespachados.getColumnModel().getColumn(0).setMaxWidth(62);
        jtDespachados.getColumnModel().getColumn(1).setMinWidth(80);
        jtDespachados.getColumnModel().getColumn(1).setMaxWidth(80);
        jtDespachados.getColumnModel().getColumn(2).setMinWidth(50);
        jtDespachados.getColumnModel().getColumn(2).setMaxWidth(50);
        jtDespachados.getColumnModel().getColumn(3).setPreferredWidth(150);
        jtDespachados.getColumnModel().getColumn(4).setPreferredWidth(150);
        jtDespachados.getColumnModel().getColumn(5).setPreferredWidth(250);
        jtDespachados.getColumnModel().getColumn(6).setMinWidth(30);
        jtDespachados.getColumnModel().getColumn(6).setMaxWidth(30);
        jtDespachados.getColumnModel().getColumn(7).setMinWidth(28);
        jtDespachados.getColumnModel().getColumn(7).setMaxWidth(28);
        jtDespachados.getColumnModel().getColumn(8).setMinWidth(28);
        jtDespachados.getColumnModel().getColumn(8).setMaxWidth(28);
        jtDespachados.getColumnModel().getColumn(9).setMinWidth(100);
        jtDespachados.getColumnModel().getColumn(9).setMaxWidth(100);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Teléfono:");

        jtTelefono.setFont(new java.awt.Font("Arial", 1, 20));
        jtTelefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtTelefonoActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("Código:");

        jtCodigo.setFont(new java.awt.Font("Arial", 1, 20));
        jtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtCodigoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtTelefono, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(jtCodigo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("Cambiar Estado:");

        btnColor.setText("Cambiar");
        btnColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnColorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(cbEstadosTaxi, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnColor)
                        .addContainerGap())
                    .addComponent(jLabel3)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnColor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbEstadosTaxi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jbEliminarFila.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/eliminar.png"))); // NOI18N
        jbEliminarFila.setToolTipText("Eliminar Filas Seleccionada (F8)");
        jbEliminarFila.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEliminarFilaActionPerformed(evt);
            }
        });

        jbLimpiarCampos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/limpiar.png"))); // NOI18N
        jbLimpiarCampos.setToolTipText("Limpia los campos de texto para hacer un cambio y despacho temporal (F7)");
        jbLimpiarCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimpiarCamposActionPerformed(evt);
            }
        });

        jbNuevoDespacho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/mas.png"))); // NOI18N
        jbNuevoDespacho.setToolTipText("Añade una fila para despachar... (Insert)");
        jbNuevoDespacho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNuevoDespachoActionPerformed(evt);
            }
        });

        jbDespachar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/enviar.png"))); // NOI18N
        jbDespachar.setToolTipText("Despachar (F12)");
        jbDespachar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbDespacharActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jbNuevoDespacho, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbLimpiarCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbEliminarFila, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbDespachar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jbNuevoDespacho, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbDespachar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbLimpiarCampos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbEliminarFila, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        lblReloj.setFont(new java.awt.Font("Arial", 1, 36));
        lblReloj.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReloj.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/reloj.png"))); // NOI18N
        lblReloj.setText("Hora");
        lblReloj.setFocusable(false);

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setText("Nombre:");

        jtBuscarPorNombre.setColumns(15);
        jtBuscarPorNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtBuscarPorNombreActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel5.setText("Teléfono:");

        jtBuscarPorTelefono.setColumns(15);
        jtBuscarPorTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtBuscarPorTelefonoActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText("Código:");

        jtBuscarPorCodigo.setColumns(15);
        jtBuscarPorCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtBuscarPorCodigoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(1, 1, 1)
                .addComponent(jtBuscarPorNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(3, 3, 3)
                .addComponent(jtBuscarPorTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(6, 6, 6)
                .addComponent(jtBuscarPorCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtBuscarPorNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtBuscarPorTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jtBuscarPorCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addContainerGap())
        );

        jlIndicadorLlamada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/nollamada.png"))); // NOI18N

        jLabel7.setText("Se debe despachar el cliente en");

        lblRecordar.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblRecordar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRecordar.setText("min");

        jLabel8.setText("min");

        lblCliente.setFont(new java.awt.Font("Tahoma", 1, 14));
        lblCliente.setText("cliente");

        javax.swing.GroupLayout jpPendienteLayout = new javax.swing.GroupLayout(jpPendiente);
        jpPendiente.setLayout(jpPendienteLayout);
        jpPendienteLayout.setHorizontalGroup(
            jpPendienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPendienteLayout.createSequentialGroup()
                .addGroup(jpPendienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpPendienteLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblRecordar)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel8))
                    .addComponent(lblCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpPendienteLayout.setVerticalGroup(
            jpPendienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpPendienteLayout.createSequentialGroup()
                .addGroup(jpPendienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblRecordar, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );

        jbSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/salir.png"))); // NOI18N
        jbSalir.setText("Salir");
        jbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalirActionPerformed(evt);
            }
        });

        jbMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/menu.jpg"))); // NOI18N
        jbMenu.setText("Menú");
        jbMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbMenuActionPerformed(evt);
            }
        });

        jbBuscarClienteNombre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/buscar.png"))); // NOI18N
        jbBuscarClienteNombre.setText("Clientes");
        jbBuscarClienteNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarClienteNombreActionPerformed(evt);
            }
        });

        jbPendientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/pendientes.png"))); // NOI18N
        jbPendientes.setText("Pendientes");
        jbPendientes.setActionCommand("jbPendientes");
        jbPendientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbPendientesActionPerformed(evt);
            }
        });

        lblFecha.setFont(new java.awt.Font("Arial", 1, 20));
        lblFecha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/calendario.png"))); // NOI18N
        lblFecha.setText("Fecha");

        lblSenal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/nosenal.png"))); // NOI18N

        lblAlarmaPendiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/alarma.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jbSalir)
                .addGap(18, 18, 18)
                .addComponent(jbMenu)
                .addGap(18, 18, 18)
                .addComponent(jbBuscarClienteNombre)
                .addGap(18, 18, 18)
                .addComponent(jbPendientes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 310, Short.MAX_VALUE)
                .addComponent(lblAlarmaPendiente)
                .addGap(18, 18, 18)
                .addComponent(lblSenal)
                .addGap(18, 18, 18)
                .addComponent(lblFecha))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAlarmaPendiente, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
            .addComponent(lblSenal, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jbSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                .addComponent(jbMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                .addComponent(jbBuscarClienteNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                .addComponent(jbPendientes, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblFecha, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsVehiculos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1001, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1001, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jlIndicadorLlamada)
                        .addGap(18, 18, 18)
                        .addComponent(jpPendiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                        .addComponent(lblReloj)
                        .addGap(41, 41, 41)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1001, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jlSenalInternet)
                        .addGap(111, 111, 111))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsVehiculos, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblReloj)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jpPendiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jlIndicadorLlamada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(11, 11, 11)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, 0, 46, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlSenalInternet)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1037)/2, (screenSize.height-793)/2, 1037, 793);
    }// </editor-fold>//GEN-END:initComponents

    private void jbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirActionPerformed
        salirVentanaPrincipal();
    }//GEN-LAST:event_jbSalirActionPerformed

    private void jtPorDespacharKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtPorDespacharKeyPressed
        cod = evt.getKeyCode();
        AccionesBorrado(cod, jtPorDespachar);
    }//GEN-LAST:event_jtPorDespacharKeyPressed

    private void jtPorDespacharMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtPorDespacharMousePressed
        int intClicks = evt.getClickCount();
        int intBoton = evt.getButton();

        //System.out.println("C:" + intClicks + " B:" + intBoton);

        try {
            jtPorDespachar.getCellEditor().stopCellEditing();
        } catch (NullPointerException ex) {
        }

        if (intClicks == 1 && intBoton == 3) {
            try {
                ventanaDatos.CerrarPuertoCoordenadas();
            } catch (NullPointerException ex) {
            }
            try {
                /*
                 * Con telefono y codigo en la tabla por despachar abrir la
                 * ventana de datos normalmente
                 */
                if (!esNullFechaTablaPorDespachar()
                        && !esNullTelefonoTablaPorDespachar()
                        && !esNullCodigoTablaPorDespachar()) {
                    abrirVentanaDatosCliente(1);
                } else {
                    /*
                     * Sin telefono, Con Codigo y Con Hora abrir la ventana
                     * habilitado el campo telefono para actualizar el telefono
                     * a ese codigo
                     */
                    if (!esNullFechaTablaPorDespachar()
                            && !esNullCodigoTablaPorDespachar()
                            && esNullTelefonoTablaPorDespachar()) {
                        abrirVentanaDatosCliente(2);
                    } else {
                        /**
                         * Tiene fecha pero es nulo el codigo y el telefono
                         * muestra la ventana para hacer una nueva insersion
                         * si se le asigna un codigo, caso contrario es despacho
                         * temporal
                         */
                        if (!esNullFechaTablaPorDespachar()
                                && esNullCodigoTablaPorDespachar()
                                && esNullTelefonoTablaPorDespachar()) {
                            abrirVentanaDatosCliente(3);
                        } else {
                            /**
                             * Despacho temporal sin codigo mostrar la ventana
                             * por defecto si se quiere guardar se debe
                             * generar un codigo en la ventana
                             */
                            if (!esNullFechaTablaPorDespachar()
                                    && esNullCodigoTablaPorDespachar()
                                    && !esNullTelefonoTablaPorDespachar()) {
                                abrirVentanaDatosCliente(1);
                            }
                        }
                    }
                }

            } catch (ArrayIndexOutOfBoundsException aex) {
            }
        }
        if (intClicks == 1 && intBoton == 1) {
            despacho = getDatosPorDespachar();
            PonerHoraParaDespachar();
            int filaAcc = getIntFilaSeleccionada();
            try {
                if (filaAcc != filaAnt) {
                    if (filaAnt != -1) {
                        actualizarFilaCampoTelefono(filaAnt, 1);
                    }
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
            }
        }
    }//GEN-LAST:event_jtPorDespacharMousePressed

    private void jtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtTelefonoActionPerformed
        desPorTabla_Campo = false;
        getBuscarPorTelefono();
        InicializarVariables();
    }//GEN-LAST:event_jtTelefonoActionPerformed

    private void jtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtCodigoActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                getBuscarPorCodigo();
                InicializarVariables();
            }
        });
    }//GEN-LAST:event_jtCodigoActionPerformed

    private void btnColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnColorActionPerformed
        int[] numCol = jtVehiculos.getSelectedColumns();
        JButton editor = (JButton) cbEstadosTaxi.getEditor().getEditorComponent();
        String etiqueta = editor.getText();
        String codEtiqueta = bd.getCodigoEtiquetaEstadoUnidad(etiqueta);

        if (!codEtiqueta.equals("OCU") && !codEtiqueta.equals("ASI")) {
            ArrayList<String> codVehiculo = new ArrayList();
            if (numCol.length > 0) {
                for (int i = 0; i < numCol.length; i++) {
                    codVehiculo.add(strCabecerasColumnasVehiculos[numCol[i]]);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Debe seleccionar una Unidad primero",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }

            cambiarEstadoTaxi(etiqueta, codVehiculo);
            //insertar estado en el servidor...

            jtVehiculos.setCellSelectionEnabled(false);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Este estado no se puede asignar desde aqui...",
                    "Error...", 0);
        }
        jtTelefono.requestFocus();
    }//GEN-LAST:event_btnColorActionPerformed

    /************************************************
    Propiedad de Cambio de la Tabla de Clientes Por Despachar
     ************************************************/
    private void jtPorDespacharPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jtPorDespacharPropertyChange
        if (evt.getPropertyName().equals("tableCellEditor")) {
            int intFila = jtPorDespachar.getSelectedRow();
            int intCol = jtPorDespachar.getSelectedColumn();

            if (intCol == 7) { //Cuando cambie la celda de Munitos
                try {
                    /**
                     * Antes de poner los minutos de llegada a recojer el cliente
                     * se requiere que esté seleccionada una unidad
                     */
                    String strCampoUnidad = "";
                    try {
                        strCampoUnidad = jtPorDespachar.getValueAt(intFila, 6).toString();
                    } catch (NullPointerException ex) {
                    }
                    if (!strCampoUnidad.equals("") && !strCampoUnidad.equals("0")) {
                        jtPorDespachar.setValueAt("0", intFila, 8);

                        intMinutos = Integer.parseInt((String) jtPorDespachar.getValueAt(intFila, 7));
                        intAtraso = Integer.parseInt((String) jtPorDespachar.getValueAt(intFila, 8));

                        if (intAtraso != 0) {
                            int res = (intMinutoAnt - intMinutos) + intAtraso;
                            jtPorDespachar.setValueAt("" + res, intFila, 8);
                            intMinutoAnt = intMinutos;
                        } else {
                            jtPorDespachar.setValueAt("" + (intMinutos * -1), intFila, 8);
                            intMinutoAnt = intMinutos;
                        }
                    } else {
                        contador++;
                        if (contador == 2) {
                            JOptionPane.showMessageDialog(Principal.gui,
                                    "Debe seleccionar la unidad que recogerá al pasajero en ese tiempo...",
                                    "Error...", 0);
                            try {
                                jtPorDespachar.getCellEditor().stopCellEditing();
                            } catch (NullPointerException ex) {
                            }
                            jtPorDespachar.setValueAt("", intFila, 7);
                            contador = 0;
                        }
                    }
                } catch (NumberFormatException nfe) {
                    jtPorDespachar.setValueAt("", intFila, 7);
                } catch (ArrayIndexOutOfBoundsException ex) {
                }
            }

            if (intCol == 1) { //Cuando cambie la celda de telefono
                try {
                    if (cod == 10 || cod >= 37 && cod <= 40 || cod == 9) {
                        actualizarFilaCampoTelefono(intFila, intCol);
                    }
                } catch (NullPointerException nex) {
                    //System.err.println("No hay telefono recuperado de la celda...");
                }
                filaAnt = intFila;
            }

            if (intCol == 3 || intCol == 4 || intCol == 5 || intCol == 9) {
                /**
                 * Cuando cambie cualquiera de estas celdas poner la hora en
                 * el campo de la hora
                 */
                String horaAntes = "";
                try {
                    try {
                        horaAntes = jtPorDespachar.getValueAt(intFila, 0).toString();
                    } catch (NullPointerException ex) {
                    }
                    //Poner una nueva hora cuandos edite alguno de estos campos
                    jtPorDespachar.setValueAt(funciones.getHora(), intFila, 0);
                    //actualizar la lista de despachos temporales en memoria
                    //para poder encontrar este cambio por la hora
                    actualizarListaTMPHoraIngreso(intFila, horaAntes);
                } catch (NullPointerException ex) {
                } catch (ArrayIndexOutOfBoundsException aex) {
                }
            }

            /**
             * CAMBIO EN LA CELDA DE UNIDAD
             */
            if (intCol == 6) { // cambio de la celda de unidad
                //Poner el icono de colgar el telefono
                Icon img = new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/nollamada.png"));
                jlIndicadorLlamada.setIcon(img);
                jtTelefono.setText("");

                /**
                 * Actualiza el tiempo en que se asigna una unidad a un cliente
                 * para su despacho
                 */
                insertarActualizarDespachoTemporalListaTMP();

                try {
                    String cod_cli = "";
                    try {
                        cod_cli = jtPorDespachar.getValueAt(intFila, 2).toString();
                    } catch (ArrayIndexOutOfBoundsException aex) {
                        cod_cli = "0";
                    }
                    if (jtPorDespachar.isEditing()) {
                        jtPorDespachar.getCellEditor().cancelCellEditing();
                    } else {
                        String nom_cli = "";
                        String dir_cli = "";
                        try {
                            nom_cli = jtPorDespachar.getValueAt(intFila, 3).toString();
                            dir_cli = jtPorDespachar.getValueAt(intFila, 5).toString();

                            if (!nom_cli.equals("") && !dir_cli.equals("")) {
                                if (CampoUnidadCambio) {
                                    activarUnidadBorrada(cod_cli);
                                    actualizarAsignacion(intFila, intCol, cod_cli);
                                } else {
                                    actualizarAsignacion(intFila, intCol, cod_cli);
                                }
                                /**
                                 * Enviar mensaje de direccion y cliente a la unidad
                                 * si esta especificado en el archivo de propiedades
                                 */
                                try {
                                    String sendSMS = bd.getValorConfiguiracion("enviar_mensajes");
                                    if (sendSMS.equals("si") || sendSMS.equals("SI")
                                            && !sendSMS.equals("") && sendSMS != null) {
                                        /**
                                         * ENVIAR MENSAJE A LA UNIDAD ASIGNADA
                                         */
                                        enviarMensajeUnidadAsignada(intFila, intCol);
                                    }
                                } catch (NullPointerException nex) {
                                    log.trace("No se a especificado la directiva [enviar_mensajes] en el archivo de configuración...");
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Primero ingresar el nombre del cliente y la dirección, antes de asignar una unidad...", "Error", 0);
                                jtPorDespachar.setValueAt("", intFila, 6);
                            }
                        } catch (NullPointerException ex) {
                            JOptionPane.showMessageDialog(this, "<html>La <b>UNIDAD</b> ingresada no existe...</html>", "Error", 0);
                            jtPorDespachar.setValueAt("", intFila, 6);
                        } catch (UnsupportedOperationException ex) {
                            activarUnidadConDosEstados(intFila);
                        } catch (ArrayIndexOutOfBoundsException ex) {
                        }
                    }
                } catch (NullPointerException ex) {
                    jtPorDespachar.setValueAt("", intFila, 2);
                }
            }
            convertirMayuculas(jtPorDespachar, intFila);
        }
    }//GEN-LAST:event_jtPorDespacharPropertyChange

    private void jbEliminarFilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEliminarFilaActionPerformed
        borrarFilasFormaSegura();
    }//GEN-LAST:event_jbEliminarFilaActionPerformed

    private void jbLimpiarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimpiarCamposActionPerformed
        int intFila = jtPorDespachar.getSelectedRow();
        borrarCamposFilaSeleccionadaPorDespachar(intFila);
    }//GEN-LAST:event_jbLimpiarCamposActionPerformed

    private void jbNuevoDespachoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNuevoDespachoActionPerformed
        nuevaFilaDespacho();
    }//GEN-LAST:event_jbNuevoDespachoActionPerformed

    private void jbDespacharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDespacharActionPerformed
        validarDespacharCliente();
    }//GEN-LAST:event_jbDespacharActionPerformed

    private void jtDespachadosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtDespachadosMousePressed
        int intClicks = evt.getClickCount();
        int intBoton = evt.getButton();

        if (intClicks == 1 && intBoton == 1) {
            try {
                if (ventanaDatos == null) {
                    ventanaDatos = new VentanaDatos(getDatosDespachados(), false, bd);
                    ventanaDatos.setDatosFila(jtDespachados, getIntFilaSeleccionadaDespachados());
                    ventanaDatos.setVisible(true);
                } else {
                    ventanaDatos.setDespachados(getDatosDespachados(), false);
                    ventanaDatos.setDatosFila(jtDespachados, getIntFilaSeleccionadaDespachados());
                    ventanaDatos.setVisible(true);
                    ventanaDatos.setLocationRelativeTo(this);
                }
            } catch (ArrayIndexOutOfBoundsException aex) {
            }
        }
    }//GEN-LAST:event_jtDespachadosMousePressed

    private void jtBuscarPorTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtBuscarPorTelefonoActionPerformed
        String txt = jtBuscarPorTelefono.getText();
        jtBuscarPorTelefono.setText(txt.toUpperCase());
        buscarDespachados("telefono", txt.toUpperCase());
    }//GEN-LAST:event_jtBuscarPorTelefonoActionPerformed

    private void jtBuscarPorCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtBuscarPorCodigoActionPerformed
        String txt = jtBuscarPorCodigo.getText();
        jtBuscarPorCodigo.setText(txt.toUpperCase());
        buscarDespachados("codigo", txt.toUpperCase());
    }//GEN-LAST:event_jtBuscarPorCodigoActionPerformed

    private void jtBuscarPorNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtBuscarPorNombreActionPerformed
        String txt = jtBuscarPorNombre.getText();
        jtBuscarPorNombre.setText(txt.toUpperCase());
        buscarDespachados("nombre", txt.toUpperCase());
    }//GEN-LAST:event_jtBuscarPorNombreActionPerformed

    private void jtVehiculosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtVehiculosMousePressed
        if (evt.getButton() == 1 && evt.getClickCount() == 1) {
            jtVehiculos.setCellSelectionEnabled(true);
        } else if (evt.getButton() == 1 && evt.getClickCount() == 2) {
            String unidad = strCabecerasColumnasVehiculos[jtVehiculos.getSelectedColumn()];
            obtenerHoraDeAsignacionEstado(unidad);
        }
    }//GEN-LAST:event_jtVehiculosMousePressed

    private void jbMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbMenuActionPerformed
        if ((menu == null) || (!menu.isDisplayable())) {
            menu = new INICIO(sesion, Principal.bd, arcConfig);
            menu.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            menu.setResizable(false);
        }
        menu.setVisible(true);
        menu.setLocationRelativeTo(this);
    }//GEN-LAST:event_jbMenuActionPerformed

    private void jbBuscarClienteNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarClienteNombreActionPerformed
        if ((cliente == null) || (!cliente.isDisplayable())) {
            cliente = new ConsultaClientes(Principal.bd);
            cliente.setLocationRelativeTo(this);
            cliente.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            cliente.setResizable(false);
        }
    }//GEN-LAST:event_jbBuscarClienteNombreActionPerformed

    private void jbPendientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbPendientesActionPerformed
        if ((pendientes == null) || (!pendientes.isDisplayable())) {
            pendientes = new PendientesGUI(Principal.bd);
            pendientes.setLocationRelativeTo(this);
            pendientes.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            pendientes.setResizable(false);
        }
    }//GEN-LAST:event_jbPendientesActionPerformed

    private void jtPorDespacharFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtPorDespacharFocusLost
        jtCodigo.requestFocus();
    }//GEN-LAST:event_jtPorDespacharFocusLost
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnColor;
    private static javax.swing.JComboBox cbEstadosTaxi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbBuscarClienteNombre;
    private javax.swing.JButton jbDespachar;
    private javax.swing.JButton jbEliminarFila;
    private javax.swing.JButton jbLimpiarCampos;
    private javax.swing.JButton jbMenu;
    private javax.swing.JButton jbNuevoDespacho;
    private javax.swing.JButton jbPendientes;
    private javax.swing.JButton jbSalir;
    private javax.swing.JLabel jlIndicadorLlamada;
    private javax.swing.JLabel jlSenalInternet;
    private static javax.swing.JPanel jpPendiente;
    private javax.swing.JScrollPane jsVehiculos;
    private javax.swing.JTextField jtBuscarPorCodigo;
    private javax.swing.JTextField jtBuscarPorNombre;
    private javax.swing.JTextField jtBuscarPorTelefono;
    private javax.swing.JTextField jtCodigo;
    private static javax.swing.JTable jtDespachados;
    private static javax.swing.JTable jtPorDespachar;
    private javax.swing.JTextField jtTelefono;
    private static javax.swing.JTable jtVehiculos;
    public static javax.swing.JLabel lblAlarmaPendiente;
    private static javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblFecha;
    private static javax.swing.JLabel lblRecordar;
    private javax.swing.JLabel lblReloj;
    public static javax.swing.JLabel lblSenal;
    // End of variables declaration//GEN-END:variables
}
