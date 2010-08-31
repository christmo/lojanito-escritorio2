/*
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
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import interfaz.comboBox.*;
import interfaz.comunicacion.comm.CommMonitoreo;
import interfaz.subVentanas.VentanaDatos;
import interfaz.subVentanas.Despachos;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
//import java.util.ResourceBundle;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kradac
 */
public final class Principal extends javax.swing.JFrame {

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
    /**
     * almacena los clientes Por Despachar
     */
    public static ArrayList<Despachos> listaDespachosTemporales = new ArrayList<Despachos>();
    private static ArrayList<Despachos> listaDespachados = new ArrayList<Despachos>();
    /**
     * Encabezado de las tablas de despachos
     */
    private static DefaultTableModel dtm;
    public static String turno;
    private static int id_Turno;
    private static funcionesUtilidad funciones = new funcionesUtilidad();

    /*Leer archivo de configuraciones*/
    //private ResourceBundle rb;
    public static Principal gui;
    /*Archivo de configuraciones*/
    public static Properties arcConfig;

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
    }

    /**
     * Constructor Por defecto
     */
    public Principal() {
        initComponents();
        ConfiguracionInicial();
    }

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
    public void ConfiguracionInicial() {
        ActualizarTurno();
        BarraTitulo();
        redimencionarTablaVehiculos();
        llenarComboEstados();
        LimpiarCargarTablaDespachados();
        IdentificadorLlamadas();
        jtTelefono.requestFocus();
        tiempo.start();
        Reloj();
        this.setExtendedState(MAXIMIZED_BOTH);
    }

    /**
     * Abre la comunicación serial para leer el numero de telefono del cliente
     */
    public void IdentificadorLlamadas() {
        //rb = ResourceBundle.getBundle("configuracion.configsystem");
        String puerto = arcConfig.getProperty("comm");
        //String puerto = rb.getString("comm");
        System.out.println("Puerto COMM: " + puerto);
        if (!puerto.equals("0")) {
            CommMonitoreo comm = new CommMonitoreo(puerto, bd);
            comm.setIndicadorLlamada(jtTelefono, jlIndicadorLlamada, jtPorDespachar);
            comm.start();
        }
    }

    /**
     * Actualiza la barra de titulo del frame
     */
    public void BarraTitulo() {
        setTitle("Despachos KRADAC || " + turno + " || " + sesion[2]);
        setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());
    }

    /**
     * Activa el Reloj en la interfaz
     */
    private void Reloj() {
        Reloj r = new Reloj(lblReloj, lblFecha);
    }

    /**
     * Limpia y carga con todos los valores pord efecto en la tabla de despachados
     */
    private static void LimpiarCargarTablaDespachados() {
        limpiarTablaDespachados();
        CargarTablaDespachados();
    }

    /**
     * Carga los clientes despachados en el turno actual
     */
    private static void CargarTablaDespachados() {
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
    public static void ActualizarTurno() {
        turno = validarTurno();
        id_Turno = bd.getIdTurno(validarTurno());
        ActualizarTurnoUsuario(sesion[0], id_Turno);
        try {
            horaNuevoTurno = bd.getHoraNuevoTurno(id_Turno);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Actualiza la tabla de usuarios con el turno en que entró por ultima vez el
     * usuario
     * @param user
     * @param id_turno
     */
    private static void ActualizarTurnoUsuario(String user, int id_turno) {
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
            tc.setMinWidth(25);
            jtVehiculos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            if (strEncColum.length > 40 && strEncColum.length <= 99) {
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
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException npe) {
            JOptionPane.showMessageDialog(null, "No se pudo recuperar las cabeceras de las unidades para este usuario!!!", "Error", 0);
            System.err.println("No se pudo recuperar el número de unidades para este usuario!!!");
        }

        return strCabecerasColumnasVehiculos;
    }

    /**
     * Retorna el turno que corresponde dependiendo de la hora del sistema
     * @return String
     */
    private static String validarTurno() {
        Calendar calendario = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("k:m:s");

        try {
            String sql = "SELECT SF_TURNOS('" + sdf.format(calendario.getTime()) + "')";
            rs = bd.ejecutarConsultaUnDato(sql);
            return rs.getString(1);
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jsVehiculos = new javax.swing.JScrollPane();
        jtVehiculos = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtPorDespachar = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtDespachados = new javax.swing.JTable();
        jbSalir = new javax.swing.JButton();
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
        lblFecha = new javax.swing.JLabel();
        lblReloj = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jtBuscarPorNombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtBuscarPorTelefono = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jtBuscarPorCodigo = new javax.swing.JTextField();
        jbMenu = new javax.swing.JButton();
        jlIndicadorLlamada = new javax.swing.JLabel();

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
                "Hora", "Télefono", "Código", "Nombre", "Dirección", "Barrio", "MIN", "UNI", "ATR", "NOTA"
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
        jtPorDespachar.getColumnModel().getColumn(0).setMinWidth(40);
        jtPorDespachar.getColumnModel().getColumn(0).setMaxWidth(40);
        jtPorDespachar.getColumnModel().getColumn(1).setMinWidth(75);
        jtPorDespachar.getColumnModel().getColumn(1).setMaxWidth(75);
        jtPorDespachar.getColumnModel().getColumn(2).setMinWidth(45);
        jtPorDespachar.getColumnModel().getColumn(2).setMaxWidth(45);
        jtPorDespachar.getColumnModel().getColumn(3).setPreferredWidth(150);
        jtPorDespachar.getColumnModel().getColumn(4).setPreferredWidth(250);
        jtPorDespachar.getColumnModel().getColumn(5).setMinWidth(75);
        jtPorDespachar.getColumnModel().getColumn(5).setMaxWidth(75);
        jtPorDespachar.getColumnModel().getColumn(6).setMinWidth(25);
        jtPorDespachar.getColumnModel().getColumn(6).setMaxWidth(25);
        jtPorDespachar.getColumnModel().getColumn(7).setMinWidth(25);
        jtPorDespachar.getColumnModel().getColumn(7).setMaxWidth(25);
        jtPorDespachar.getColumnModel().getColumn(8).setMinWidth(25);
        jtPorDespachar.getColumnModel().getColumn(8).setMaxWidth(25);
        jtPorDespachar.getColumnModel().getColumn(9).setMinWidth(100);
        jtPorDespachar.getColumnModel().getColumn(9).setMaxWidth(100);

        jtDespachados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Hora", "Teléfono", "Código", "Nombre", "Dirección", "Barrio", "MIN", "UNI", "ATR", "NOTA"
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
        jtDespachados.getColumnModel().getColumn(0).setMinWidth(40);
        jtDespachados.getColumnModel().getColumn(0).setMaxWidth(40);
        jtDespachados.getColumnModel().getColumn(1).setMinWidth(75);
        jtDespachados.getColumnModel().getColumn(1).setMaxWidth(75);
        jtDespachados.getColumnModel().getColumn(2).setMinWidth(45);
        jtDespachados.getColumnModel().getColumn(2).setMaxWidth(45);
        jtDespachados.getColumnModel().getColumn(3).setPreferredWidth(150);
        jtDespachados.getColumnModel().getColumn(4).setPreferredWidth(250);
        jtDespachados.getColumnModel().getColumn(5).setMinWidth(75);
        jtDespachados.getColumnModel().getColumn(5).setMaxWidth(75);
        jtDespachados.getColumnModel().getColumn(6).setMinWidth(25);
        jtDespachados.getColumnModel().getColumn(6).setMaxWidth(25);
        jtDespachados.getColumnModel().getColumn(7).setMinWidth(25);
        jtDespachados.getColumnModel().getColumn(7).setMaxWidth(25);
        jtDespachados.getColumnModel().getColumn(8).setMinWidth(25);
        jtDespachados.getColumnModel().getColumn(8).setMaxWidth(25);
        jtDespachados.getColumnModel().getColumn(9).setMinWidth(100);
        jtDespachados.getColumnModel().getColumn(9).setMaxWidth(100);

        jbSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/salir.png"))); // NOI18N
        jbSalir.setText("Salir");
        jbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalirActionPerformed(evt);
            }
        });

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

        lblFecha.setFont(new java.awt.Font("Arial", 1, 20));
        lblFecha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/calendario.png"))); // NOI18N
        lblFecha.setText("Fecha");

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
                .addComponent(jtBuscarPorNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
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

        jbMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/menu.jpg"))); // NOI18N
        jbMenu.setText("Menú");
        jbMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbMenuActionPerformed(evt);
            }
        });

        jlIndicadorLlamada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/nollamada.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsVehiculos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1013, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1013, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jlIndicadorLlamada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 251, Short.MAX_VALUE)
                        .addComponent(lblReloj)
                        .addGap(41, 41, 41)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1013, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbSalir)
                        .addGap(18, 18, 18)
                        .addComponent(jbMenu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 691, Short.MAX_VALUE)
                        .addComponent(lblFecha)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsVehiculos, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jlIndicadorLlamada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(11, 11, 11))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblReloj)
                        .addGap(18, 18, 18)))
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, 0, 46, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(lblFecha))
                .addContainerGap())
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1037)/2, (screenSize.height-800)/2, 1037, 800);
    }// </editor-fold>//GEN-END:initComponents

    private void jbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirActionPerformed
        bd.CerrarConexion();
        System.exit(0);
    }//GEN-LAST:event_jbSalirActionPerformed

    private void jtPorDespacharKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtPorDespacharKeyPressed
        cod = evt.getKeyCode();
        AccionesBorrado(cod, jtPorDespachar);
    }//GEN-LAST:event_jtPorDespacharKeyPressed

    /**
     * Borra las filas o las celdas dependiendo de la tecla que se precione
     * @param cod - codigo de la tecla
     * @param Tabla - tabla donde ejecutar la accion
     */
    private void AccionesBorrado(int cod, javax.swing.JTable Tabla) {
        int intFila = Tabla.getSelectedRow();
        int intCol = Tabla.getSelectedColumn();

        if (cod == 127 || cod == 8) {
            /**
             * borrar la celda que se haya seleccionado, esto se debe
             * ejecutar cuando se presione (<-) BackSpace o SUPRIMIR
             */
            if (intCol == 0 || intCol == 2 || intCol == 8) {
                System.out.println("No borrar esos campos...");
            } else {
                Tabla.setValueAt("", intFila, intCol);
            }
            if (intCol == 7) {
                BorrarColorDespachoVehiculo(Tabla);
            }
        } else if (cod == 118) {
            /**
             * borrar las celdas de nombre, direccion, barrio y nota
             * con F7
             */
            BorrarCamposFilaSeleccionadaPorDespachar(intFila);
        } else if (cod == 119) {
            /**
             * F8 Para borra toda la fila
             */
            BorrarFilaSeleccionadaPorDespachar(intFila);
        } else if (cod == 123) {
            /**
             * F12 Para despachar
             */
            DespacharCliente(intFila);
        } else if (cod == 155) {
            /**
             * Insert Ingresa una nueva fila para el despacho
             */
            NuevaFilaDespacho();
        } else if (cod == 10) {
            /**
             * TODO: hacer que se quede en la misma fila donde se encuentre
             * editando -> por ahora no se ejecutan los eventos
             */
            /*System.out.println("Enter: " + intFila);
            try {
            if (intFila == 0) {
            jtPorDespachar.setRowSelectionInterval(0, 0);
            } else {
            jtPorDespachar.setRowSelectionInterval(intFila - 1, intFila - 1);
            }

            } catch (IllegalArgumentException iae) {
            }*/
        }
    }

    /**
     * Pone el estado de activo a la unidad que se quiera borrar esto se da cuando
     * se asigna una unidad y luego se la quier quitar, en la tabla de clientes
     * pord espachar
     */
    private void BorrarColorDespachoVehiculo(JTable Tabla) {
        int intFila = jtPorDespachar.getSelectedRow();
        int intCol = jtPorDespachar.getSelectedColumn();
        try {
            String unidad = Tabla.getCellEditor(intFila, intCol).getCellEditorValue().toString();
            AsignarColorDespachoVehiculo(unidad, "ACTIVO");
        } catch (NumberFormatException nfex) {
        } catch (NullPointerException ex) {
        }
    }

    /**
     * Elimina una Fila de la Tabla PorDespachar que se haya seleccionado
     * @param intFila
     */
    private void BorrarFilaSeleccionadaPorDespachar(int intFila) {
        try {
            if (jtPorDespachar.isEditing()) {
                jtPorDespachar.getCellEditor().cancelCellEditing();
            }
            Icon img = new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/nollamada.png"));
            jlIndicadorLlamada.setIcon(img);

            if (despacho.getStrHora() != null) {
                if (!despacho.getStrHora().equals("") || jtPorDespachar.getValueAt(intFila, 0).equals("")) {
                    despacho.setStrHora(funciones.getHora());
                    despacho = getDatosPorDespachar();
                    despacho.setStrEstado("C");//cancelada
                    bd.InsertarDespachoCliente(despacho, false);
                } else {
                    despacho = getDatosPorDespachar();
                    despacho.setStrHora(funciones.getHora());
                    despacho.setStrEstado("C");//cancelada
                    bd.InsertarDespachoCliente(despacho, false);
                }
            } else {
                despacho.setStrHora(funciones.getHora());
                despacho = getDatosPorDespachar();
                despacho.setStrEstado("C");//cancelada
                bd.InsertarDespachoCliente(despacho, false);
            }

            if (despacho.getIntUnidad() != 0) {
                AsignarColorDespachoVehiculo("" + despacho.getIntUnidad(), "ACTIVO");
            }

            DefaultTableModel model = ((DefaultTableModel) jtPorDespachar.getModel());
            model.removeRow(intFila);
            listaDespachosTemporales.remove(intFila);
            InicializarVariables();
        } catch (IndexOutOfBoundsException iex) {
        } catch (NullPointerException nex) {
        }
    }

    /**
     * Limpia los campos de Nombre, Direccion, Barrio, Nota
     * para que se realice un despacho temporal a un mismo
     * telefono o codigo que los cambios no van a ser permanentes
     * @param intFila
     */
    private void BorrarCamposFilaSeleccionadaPorDespachar(int intFila) {
        try {
            jtPorDespachar.setValueAt("", intFila, 3);
            jtPorDespachar.setValueAt("", intFila, 4);
            jtPorDespachar.setValueAt("", intFila, 5);
            jtPorDespachar.setValueAt("", intFila, 9);
        } catch (IndexOutOfBoundsException iex) {
        }
    }

    /**
     * Envia los datos de la tabla PorDespachar a la tabla de Despachados,
     * guardando estos en la base de datos
     * @param intFila
     */
    private void DespacharCliente(int intFila) {
        if (jtPorDespachar.isEditing()) {
            jtPorDespachar.getCellEditor().stopCellEditing();
        } else {
            despacho = getDatosPorDespachar();
            despacho.setStrEstado("F"); //finalizado

            if (sePuedeDespachar(despacho, intFila)) {
                boolean r = setDatosTablaDespachados(despacho, jtDespachados);
                if (r) {
                    try {
                        AsignarColorDespachoVehiculo("" + despacho.getIntUnidad(), "ACTIVO");
                        setNumeroCarrerasRealizadasPorTaxi("" + despacho.getIntUnidad());
                        DefaultTableModel model = ((DefaultTableModel) jtPorDespachar.getModel());
                        model.removeRow(intFila);
                        listaDespachosTemporales.remove(intFila);
                        InicializarVariables();
                    } catch (IndexOutOfBoundsException iex) {
                    }
                }
            }
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
        jtPorDespachar.requestFocus();
        try {
            jtPorDespachar.setRowSelectionInterval(0, 0);
        } catch (IllegalArgumentException iarg) {
            //se produce cuando no hay filas en la tabla por despachar
        }
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
            JOptionPane.showMessageDialog(this, "Se debe ingresar una hora de despacho...", "Error...", 0);
            try {
                jtPorDespachar.setValueAt(funciones.getHora(), fila, 0);
            } catch (IndexOutOfBoundsException iex) {
            }
        } else if (d.getStrNombre() == null || d.getStrNombre().equals("")) {
            JOptionPane.showMessageDialog(this, "Se debe ingresar un nombre de cliente...", "Error...", 0);
        } else if (d.getStrDireccion() == null || d.getStrDireccion().equals("")) {
            JOptionPane.showMessageDialog(this, "Se debe ingresar una dirección del cliente...", "Error...", 0);
        } else if (d.getStrBarrio() == null || d.getStrBarrio().equals("")) {
            JOptionPane.showMessageDialog(this, "Se debe ingresar un barrio donde vive el cliente...", "Error...", 0);
        } else if (d.getIntMinutos() < 0) {
            JOptionPane.showMessageDialog(this, "La estimación de tiempo de llegada a recojer al cliente es incorrecta, tiene que ser mayor a 0...", "Error...", 0);
            jtPorDespachar.setValueAt("", fila, 6);
            jtPorDespachar.setValueAt("0", fila, 8);
        } else if (!validarUnidad(d.getIntUnidad())) {
            JOptionPane.showMessageDialog(this, "La unidad ingresada no es válida...", "Error...", 0);
        } else {
            resultado = true;
        }

        return resultado;
    }
    VentanaDatos ventanaDatos = null;

    private void jtPorDespacharMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtPorDespacharMousePressed
        int intClicks = evt.getClickCount();
        int intBoton = evt.getButton();

        if (intClicks == 1 && intBoton == 3) {
            try {
                /*
                 * Con telefono y codigo en la tabla por despachar abrir la
                 * ventana de datos normalmente
                 */
                if (!esNullFechaTablaPorDespachar()
                        && !esNullTelefonoTablaPorDespachar()
                        && !esNullCodigoTablaPorDespachar()) {
                    AbrirVentanaDatosCliente(1);
                } else {
                    /*
                     * Sin telefono, Con Codigo y Con Hora abrir la ventana
                     * habilitado el campo telefono para actualizar el telefono
                     * a ese codigo
                     */
                    if (!esNullFechaTablaPorDespachar()
                            && !esNullCodigoTablaPorDespachar()
                            && esNullTelefonoTablaPorDespachar()) {
                        AbrirVentanaDatosCliente(2);
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
                            AbrirVentanaDatosCliente(3);
                        } else {
                            /**
                             * Despacho temporal sin codigo mostrar la ventana
                             * por defecto si se quiere guardar se debe
                             * generar un codigo en la ventana
                             */
                            if (!esNullFechaTablaPorDespachar()
                                    && esNullCodigoTablaPorDespachar()
                                    && !esNullTelefonoTablaPorDespachar()) {
                                AbrirVentanaDatosCliente(1);
                            }
                        }
                    }
                }

            } //catch (NullPointerException ex) {
            //     JOptionPane.showMessageDialog(this, "No hay datos que mostrar...", "Error", 0);
            //   }
            catch (ArrayIndexOutOfBoundsException aex) {
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
    private void AbrirVentanaDatosCliente(int casoTelefono) {
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
            strDireccion = (String) jtPorDespachar.getValueAt(fila, 4);
            strBarrio = (String) jtPorDespachar.getValueAt(fila, 5);

            try {
                intMinutos = Integer.parseInt(jtPorDespachar.getValueAt(fila, 6).toString());
            } catch (NumberFormatException nfe) {
                //System.err.println("No hay Minutos...");
                //JOptionPane.showMessageDialog(this, "Solo debe ser número los campos de Minutos(MIN)...", "Error...", 0);
                intMinutos = 0;
                jtPorDespachar.setValueAt("", fila, 6);
            } catch (NullPointerException ex) {
                intMinutos = 0;
            }
            try {
                intUnidad = Integer.parseInt(jtPorDespachar.getValueAt(fila, 7).toString());
            } catch (NumberFormatException nfe) {
                //System.err.println("No hay Unidad...");
                //JOptionPane.showMessageDialog(this, "Solo debe ser número los campos de Unidad(UNI)...", "Error...", 0);
                jtPorDespachar.setValueAt("", fila, 7);
                intUnidad = 0;
            } catch (NullPointerException ex) {
                intUnidad = 0;
            }
            try {
                intAtraso = Integer.parseInt(jtPorDespachar.getValueAt(fila, 8).toString());
            } catch (NumberFormatException nfe) {
                //System.err.println("No hay Atraso...");
                JOptionPane.showMessageDialog(this, "Solo debe ser número los campos de Atraso(ATR)...", "Error...", 0);
                jtPorDespachar.setValueAt("0", fila, 8);
                intAtraso = 0;
            } catch (NullPointerException ex) {
                intAtraso = 0;
            }

            strNota = (String) jtPorDespachar.getValueAt(fila, 9);

            datos = new Despachos(strHora, strTelefono, intCodigo, strNombre, strDireccion, strBarrio,
                    intMinutos, intUnidad, intAtraso, strNota, id_Turno, sesion[0]);

        } catch (ArrayIndexOutOfBoundsException aiobe) {
            //System.err.println("Error de NULL -> No hay datos Seleccionados...");
            datos = new Despachos(null, null, null, "", "", "", 0, 0, 0, "", id_Turno, sesion[0]);
        }

        return datos;
    }

    private void jtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtTelefonoActionPerformed
        desPorTabla_Campo = false;
        //jtCodigo.setText(getBuscarPorTelefono());
        getBuscarPorTelefono();
        InicializarVariables();
    }//GEN-LAST:event_jtTelefonoActionPerformed

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
                despacho = new Despachos(strHora, strTelefono, intCodigo, strNombre, strDireccion, strBarrio, "");

                if (desPorTabla_Campo) { //Despacha por tabla
                    setDatosFila(despacho, jtPorDespachar);
                } else { //despacha por campo
                    setDatosTablas(despacho, jtPorDespachar);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Numero ingresado no valido...", "Error", 0);
                jtTelefono.setText("");
            }
        } catch (SQLException ex) {
            System.err.println("No hay ningún cliente con ese Teléfono...");
            if (desPorTabla_Campo) {
                despacho = new Despachos(
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
                jtPorDespachar.setRowSelectionInterval(0, 0);
            }
        }
        return intCodigo;
    }

    private void jtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtCodigoActionPerformed
        //jtTelefono.setText(getBuscarPorCodigo());
        getBuscarPorCodigo();
        InicializarVariables();
    }//GEN-LAST:event_jtCodigoActionPerformed

    /**
     * Obtener el código del usuario dependiendo del telefono ingresado
     * @return String
     */
    private String getBuscarPorCodigo() {
        intCodigo = jtCodigo.getText();
        try {
            String sql = "SELECT TELEFONO,"
                    + "NOMBRE_APELLIDO_CLI,"
                    + "DIRECCION_CLI,"
                    + "SECTOR"
                    + " FROM CLIENTES WHERE CODIGO='" + intCodigo + "'";
            rs = bd.ejecutarConsultaUnDato(sql);
            strTelefono = rs.getString("TELEFONO");
            strNombre = rs.getString("NOMBRE_APELLIDO_CLI");
            strDireccion = rs.getString("DIRECCION_CLI");
            strBarrio = rs.getString("SECTOR");
            strHora = funciones.getHora();
            despacho = new Despachos(strHora, strTelefono, intCodigo, strNombre, strDireccion, strBarrio, "");

            setDatosTablas(despacho, jtPorDespachar);
        } catch (SQLException ex) {
            //Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("No devuelve valores esa consulta...");
            jtTelefono.setText("");
        }
        return strTelefono;
    }

    private void btnColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnColorActionPerformed
        int[] numCol = jtVehiculos.getSelectedColumns();
        JButton editor = (JButton) cbEstadosTaxi.getEditor().getEditorComponent();
        String etiqueta = editor.getText();

        ArrayList<String> codVehiculo = new ArrayList();
        if (numCol.length > 0) {
            for (int i = 0; i < numCol.length; i++) {
                codVehiculo.add(strCabecerasColumnasVehiculos[numCol[i]]);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una Unidad primero", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        cambiarEstadoTaxi(etiqueta, codVehiculo);
        jtVehiculos.setCellSelectionEnabled(false);
        jtTelefono.requestFocus();
    }//GEN-LAST:event_btnColorActionPerformed

    /**
     * Cambia el estado de un TAXI en la BD
     * @param etq
     * @param codVh
     */
    private void cambiarEstadoTaxi(String etq, ArrayList<String> codVh) {
        int et = etiq.indexOf(etq);
        String codig = codigo.get(et);
        for (String i : codVh) {
            String sql = "INSERT INTO REGCODESTTAXI VALUES (now(),now(),'" + codig + "','" + sesion[0] + "','" + i + "')";
            bd.ejecutarSentencia(sql);
        }
        pintarEstadoTaxi(strEncabezados);
    }
    //Matriz de Relacion CODIGO - COLOR
    //Cada Elemento de codColor
    //[1][ID_CODIGO]  [2][COLOR]
    private static ArrayList<String> codigo = new ArrayList();
    private static ArrayList<String> color = new ArrayList();
    private static ArrayList<String> etiq = new ArrayList();
    private static Map etiqColor = new HashMap();

    private static void colorCodigosBD() {
        try {
            String sql = "SELECT ID_CODIGO,COLOR,ETIQUETA  FROM CODESTTAXI";
            rs = bd.ejecutarConsultaUnDato(sql);

            codigo.clear();
            color.clear();
            etiq.clear();
            do {
                codigo.add(rs.getString(1));
                color.add(rs.getString(2));
                etiq.add(rs.getString(3));
                etiqColor.put(rs.getString(3), rs.getString(2));
            } while (rs.next());

        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
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

        try {
            String sql = "SELECT TAB1.N_UNIDAD, TAB1.ID_CODIGO FROM REGCODESTTAXI AS TAB1, (SELECT aux.ID_CODIGO, MAX(CONCAT(aux.FECHA,aux.HORA)) AS TMP FROM REGCODESTTAXI aux GROUP BY aux.N_UNIDAD) AS TAB2 WHERE CONCAT(TAB1.FECHA,TAB1.HORA) = TAB2.TMP";
            rs = bd.ejecutarConsulta(sql);
            while (rs.next()) {
                unidadCodigoBD.put(rs.getString(1), rs.getString(2));
            } // ArrayList codigo color
            colorCodigosBD();

        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }

        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        tcr.setVerticalAlignment(SwingConstants.CENTER);

        for (int i = 0, len = jtVehiculos.getColumnCount(); i < len; i++) {
            TableColumn column = jtVehiculos.getColumn(jtVehiculos.getColumnName(i));
            column.setCellRenderer(new formatoTabla(encab, unidadCodigoBD, codigo, color));
        }
        jtVehiculos.repaint();
    }
    private int intMinutoAnt = 0;

    private void jtPorDespacharPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jtPorDespacharPropertyChange
        if (evt.getPropertyName().equals("tableCellEditor")) {
            int intFila = jtPorDespachar.getSelectedRow();
            int intCol = jtPorDespachar.getSelectedColumn();

            if (intCol == 6) { //Cuando cambie la celda de Munitos
                //------
                Icon img = new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/nollamada.png"));
                jlIndicadorLlamada.setIcon(img);
                jtTelefono.setText("");
                //------
                jtPorDespachar.setValueAt(funciones.getHora(), intFila, 0);
                jtPorDespachar.setValueAt("0", intFila, 8);
                try {
                    intMinutos = Integer.parseInt((String) jtPorDespachar.getValueAt(intFila, 6));
                    intAtraso = Integer.parseInt((String) jtPorDespachar.getValueAt(intFila, 8));

                    if (intAtraso != 0) {
                        int res = (intMinutoAnt - intMinutos) + intAtraso;
                        jtPorDespachar.setValueAt("" + res, intFila, 8);
                        intMinutoAnt = intMinutos;
                    } else {
                        jtPorDespachar.setValueAt("" + (intMinutos * -1), intFila, 8);
                        intMinutoAnt = intMinutos;
                    }
                } catch (NumberFormatException nfe) {
                    jtPorDespachar.setValueAt("", intFila, 6);
                } catch (ArrayIndexOutOfBoundsException ex) {
                }
            }

            if (intCol == 1) { //Cuando cambie la celda de telefono
                try {
                    if (cod == 10 || cod >= 37 && cod <= 40 || cod == 9) {
                        actualizarFilaCampoTelefono(intFila, intCol);
                    }
                } catch (NullPointerException nex) {
                    System.err.println("No hay telefono recuperado de la celda...");
                }
                filaAnt = intFila;
            }

            if (intCol == 3 || intCol == 4 || intCol == 5 || intCol == 9) {
                try {
                    jtPorDespachar.setValueAt(funciones.getHora(), intFila, 0);
                } catch (NullPointerException ex) {
                } catch (ArrayIndexOutOfBoundsException aex) {
                }
            }

            if (intCol == 7) {
                if (jtPorDespachar.isEditing()) {
                    jtPorDespachar.getCellEditor().cancelCellEditing();
                } else {
                    try {
                        String unidad = "0";
                        try {
                            unidad = jtPorDespachar.getValueAt(intFila, intCol).toString();
                        } catch (NullPointerException ex) {
                        }
                        String strEstadoUnidad = "";
                        try {
                            strEstadoUnidad = bd.getEstadoUnidad(Integer.parseInt(unidad));
                        } catch (NumberFormatException nfex) {
                        }

                        if (strEstadoUnidad.equals("AC")) {

                            jtPorDespachar.setValueAt(funciones.getHora(), intFila, 0);
                            AsignarColorDespachoVehiculo(unidad, "ASIGNADO");

                        } else {
                            String estado = bd.getEtiquetaEstadoUnidad(strEstadoUnidad);
                            if (estado != null) {
                                JOptionPane.showMessageDialog(this, "No se puede asignar una carrera a esa unidad no está Activa...\nEstado de la unidad: " + estado, "Error", 0);
                            } else {
                                JOptionPane.showMessageDialog(this, "No se puede asignar una carrera a esa unidad no está Activa...\nEstado de la unidad: " + "No se ha asignado uno...", "Error", 0);
                            }
                            jtPorDespachar.setValueAt("", intFila, intCol);
                        }
                    } catch (ArrayIndexOutOfBoundsException aex) {
                    } catch (NullPointerException ex) {
                    }
                }
            }

            Mayuculas(jtPorDespachar, intFila);
        }
    }//GEN-LAST:event_jtPorDespacharPropertyChange

    /**
     * Permite asignar el color de despachado a los taxis que esten en la espera
     * de la finalización de la carrera en la tabla de clientes por despachar
     * @param unidad
     */
    private void AsignarColorDespachoVehiculo(String unidad, String estado) {
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
    private int filaAnt = -1;

    /**
     * Actualiza la fila de clientes pord espachar cuandos se ingresa un telefono
     * directamente en la tabla
     * @param intFila
     * @param intCol
     */
    private void actualizarFilaCampoTelefono(int intFila, int intCol) {
        try {
            String tel = jtPorDespachar.getValueAt(intFila, 1).toString();
            if (funciones.isNumeric(tel)) {
                jtTelefono.setText(tel);
                desPorTabla_Campo = true; //despachar por la tabla de despachos
                getBuscarPorTelefono();
            } else {
                JOptionPane.showMessageDialog(this, "Número no válido...", "Error...", 0);
                jtPorDespachar.setValueAt("", intFila, intCol);
                jtTelefono.setText("");
            }
        } catch (NullPointerException ne) {
        }
    }

    private void jbEliminarFilaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEliminarFilaActionPerformed
        int intFila = jtPorDespachar.getSelectedRow();
        BorrarFilaSeleccionadaPorDespachar(intFila);
    }//GEN-LAST:event_jbEliminarFilaActionPerformed

    private void jbLimpiarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimpiarCamposActionPerformed
        int intFila = jtPorDespachar.getSelectedRow();
        BorrarCamposFilaSeleccionadaPorDespachar(intFila);
    }//GEN-LAST:event_jbLimpiarCamposActionPerformed

    private void jbNuevoDespachoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNuevoDespachoActionPerformed
        NuevaFilaDespacho();
    }//GEN-LAST:event_jbNuevoDespachoActionPerformed

    private void jbDespacharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbDespacharActionPerformed
        int intFila = jtPorDespachar.getSelectedRow();
        DespacharCliente(intFila);
    }//GEN-LAST:event_jbDespacharActionPerformed

    private void jtDespachadosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtDespachadosMousePressed
        int intClicks = evt.getClickCount();
        int intBoton = evt.getButton();

        if (intClicks == 1 && intBoton == 1) {
            try {
                if (!jtDespachados.getValueAt(getIntFilaSeleccionadaDespachados(), 1).equals("")) {

                    if (ventanaDatos == null) {
                        ventanaDatos = new VentanaDatos(getDatosDespachados(), false);
                        ventanaDatos.setDatosFila(jtDespachados, getIntFilaSeleccionadaDespachados());
                        ventanaDatos.setVisible(true);
                    } else {
                        ventanaDatos.setDespachados(getDatosDespachados(), false);
                        ventanaDatos.setDatosFila(jtDespachados, getIntFilaSeleccionadaDespachados());
                        ventanaDatos.setVisible(true);
                        ventanaDatos.setLocationRelativeTo(this);
                    }

                }
            } catch (NullPointerException ex) {
                JOptionPane.showMessageDialog(this, "No hay datos que mostrar...", "Error", 0);
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
        }
    }//GEN-LAST:event_jtVehiculosMousePressed

    private void jbMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbMenuActionPerformed
        if ((menu == null) || (!menu.isDisplayable())) {
            menu = new INICIO(sesion, Principal.bd);
            menu.setLocationRelativeTo(this);
        }
    }//GEN-LAST:event_jbMenuActionPerformed

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
            LimpiarCargarTablaDespachados();
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
            strDireccion = (String) jtDespachados.getValueAt(fila, 4);
            strBarrio = (String) jtDespachados.getValueAt(fila, 5);
            intMinutos = Integer.parseInt(jtDespachados.getValueAt(fila, 6).toString());
            intUnidad = Integer.parseInt(jtDespachados.getValueAt(fila, 7).toString());
            intAtraso = Integer.parseInt(jtDespachados.getValueAt(fila, 8).toString());
            strNota = (String) jtDespachados.getValueAt(fila, 9);
            datos = new Despachos(strHora, strTelefono, intCodigo, strNombre, strDireccion, strBarrio,
                    intMinutos, intUnidad, intAtraso, strNota, id_Turno, sesion[0]);
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            //System.err.println("Error de NULL -> No hay datos Seleccionados...");
        }
        return datos;
    }

    /**
     * Ingresa una nueva fila para despachar
     */
    private void NuevaFilaDespacho() {
        dtm = (DefaultTableModel) jtPorDespachar.getModel();
        String[] inicial = {funciones.getHora(), "", "", "", "", "", "", "", "0", ""};
        dtm.insertRow(0, inicial);
        jtPorDespachar.setRowSelectionInterval(0, 0);
    }

    /**
     * Convierte a mayusculas todas las letras de una fila de la tabla
     * @param Tabla
     * @param intFila
     */
    public void Mayuculas(JTable Tabla, int intFila) {
        int intTotalColumnas = Tabla.getColumnCount();
        for (int i = 0; i < intTotalColumnas; i++) {
            try {
                String txt = Tabla.getValueAt(intFila, i).toString().toUpperCase();
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
     * Comprueba el tiempo en que se piensa recojer al cliente por cada minuto
     * para saber si el taxista se atrasa o no
     */
    Timer tiempo = new Timer(60000, new ActionListener() {

        public void actionPerformed(ActionEvent ae) {
            int N_filas = jtPorDespachar.getRowCount();
            for (int i = 0; i < N_filas; i++) {
                String dato = (String) jtPorDespachar.getValueAt(i, 8);
                String minuto = (String) jtPorDespachar.getValueAt(i, 6);
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
    public static void ReiniciarTurno() {
        ActualizarTurno();
        LimpiarCargarTablaDespachados();
        //redimencionarTablaVehiculos();
        gui.setTitle("Despachos KRADAC || " + turno + " || " + sesion[2]);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnColor;
    private static javax.swing.JComboBox cbEstadosTaxi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbDespachar;
    private javax.swing.JButton jbEliminarFila;
    private javax.swing.JButton jbLimpiarCampos;
    private javax.swing.JButton jbMenu;
    private javax.swing.JButton jbNuevoDespacho;
    private javax.swing.JButton jbSalir;
    private javax.swing.JLabel jlIndicadorLlamada;
    private javax.swing.JScrollPane jsVehiculos;
    private javax.swing.JTextField jtBuscarPorCodigo;
    private javax.swing.JTextField jtBuscarPorNombre;
    private javax.swing.JTextField jtBuscarPorTelefono;
    private javax.swing.JTextField jtCodigo;
    private static javax.swing.JTable jtDespachados;
    private static javax.swing.JTable jtPorDespachar;
    private javax.swing.JTextField jtTelefono;
    private static javax.swing.JTable jtVehiculos;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblReloj;
    // End of variables declaration//GEN-END:variables
    TrabajoTablas trabajarTabla;

    /**
     * Ingresa un despacho en la tabla de clientes por Despachar en la
     * Parte superior de la Ventana
     * @param des
     * @param tabla
     */
    public void setDatosTablas(Despachos des, JTable tabla) {
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
    public void setDatosFila(Despachos des, JTable tabla) {
        //jtTelefono.setText(des.getStrTelefono());
        listaDespachosTemporales.add(des);

        LlenarFila(filaAnt, tabla, des);
    }

    public void LlenarFila(int fila, JTable tabla, Despachos d) {
        tabla.setValueAt(d.getStrHora(), fila, 0);
        tabla.setValueAt(d.getStrTelefono(), fila, 1);
        if (d.getIntCodigo() == 0) {
            tabla.setValueAt("", fila, 2);
        } else {
            tabla.setValueAt("" + d.getIntCodigo(), fila, 2);
        }
        tabla.setValueAt(d.getStrNombre(), fila, 3);
        tabla.setValueAt(d.getStrDireccion(), fila, 4);
        tabla.setValueAt(d.getStrBarrio(), fila, 5);
        tabla.setValueAt("", fila, 6);
        tabla.setValueAt("", fila, 7);
        tabla.setValueAt("0", fila, 8);
        tabla.setValueAt(d.getStrNota(), fila, 9);
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
                JOptionPane.showMessageDialog(this, "No se puede despachar esa unidad no está Asignada...\nEstado de la unidad: " + "No se ha asignado uno...", "Error", 0);
            }
            jtPorDespachar.setValueAt("0", fila, 7);
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
            System.out.println("No hay fila");
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
}
