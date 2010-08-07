/*
 * Principal.java
 *
 * Created on 03/08/2010, 06:28:35 PM
 */
package interfaz;

import BaseDatos.ConexionBase;
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
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import interfaz.comboBox.*;
import interfaz.subVentanas.VentanaDatos;
import interfaz.subVentanas.Despachos;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author root
 */
public final class Principal extends javax.swing.JFrame {

    private ConexionBase bd = new ConexionBase();
    private ResultSet rs;
    public static String[] sesion = null;
    private String[] strCabecerasColumnasVehiculos = null;
    private String strHora;
    private String strTelefono;
    private String strCodigo;
    private String strNombre;
    private String strDireccion;
    private String strBarrio;
    private int intMinutos;
    private int intUnidad;
    private int intAtraso;
    private String strNota;
    private ArrayList<String> strEncabezados;
    private Despachos despacho;
    /**
     * almacena los clientes Por Despachar
     */
    private ArrayList<Despachos> listaDespachosTemporales = new ArrayList<Despachos>();
    private ArrayList<Despachos> listaDespachados = new ArrayList<Despachos>();
    /**
     * Encabezado de las tablas de despachos
     */
    private DefaultTableModel dtm;

    /** Creates new form Principal */
    public Principal() {
        initComponents();
        this.setTitle("Despachos KRADAC || " + validarTurno() + " || " + sesion[2]);
        redimencionarTablaVehiculos();
        llenarComboEstados();
        tiempo.start();
    }

    public Principal(String[] info) {
        setSession(info);
        initComponents();
        Principal.main(null);
    }

    /**
     * Redimenciona la tabla de unidades segun el numero de vehiculos que estan
     * en la base de datos para cada empresa
     */
    private void redimencionarTablaVehiculos() {
        TableModel tm = new AbstractTableModel() {

            String headers[] = getEncabezadosTablaVehiculos();

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

            // Synthesize some entries using the data values & the row #
            public Object getValueAt(int row, int col) {
                return row;
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

        //Centrar el contenido de las Celdas
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
        tcr.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < strCabecerasColumnasVehiculos.length; i++) {
            jtVehiculos.getColumnModel().getColumn(i).setCellRenderer(tcr);
        }

        pintarEstadoTaxi(strEncabezados);
    }

    /**
     * Devuelve un arreglo de string que contiene los numeros
     * para cada uno de los vehiculos que posee la empresa, para ponerlos
     * como encabezado de la tabla de unidades
     * @return String[]
     */
    private String[] getEncabezadosTablaVehiculos() {
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
            JOptionPane.showMessageDialog(this, "No se pudo recuperar las cabeceras de las unidades para este usuario!!!", "Error", 0);
            System.err.println("No se pudo recuperar el número de unidades para este usuario!!!");
        } finally {
            /*
             * TODO: Se cierra toda la conexion a la base ya no se puede consultar
             * hasta no hacer una nueva conexion...
             */
            //bd.CerrarConexion();
        }
        //strEncabezados.add("1");
        //datosCast = new String[strEncabezados.size()];
        //strCabecerasColumnasVehiculos = strEncabezados.toArray(datosCast);
        return strCabecerasColumnasVehiculos;
    }

    /**
     * Retorna el turno que corresponde dependiendo de la hora del sistema
     * @return String
     */
    private String validarTurno() {
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

    /**
     * Devuelve hora y minutos actuales del sistema
     * @return String
     */
    public String getHora() {
        Calendar calendario = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("k:m");
        return sdf.format(calendario.getTime());
    }

    private void llenarComboEstados() {
        //Extracción de la BD
        colorCodigosBD();

        //Carga de Codigos de Estado del Taxi
        cbEstadosTaxi.setModel(new javax.swing.DefaultComboBoxModel(etiq.toArray()));

        cbEstadosTaxi.setEditable(true);
        cbEstadosTaxi.setRenderer(new ColorCellRenderer(codigo, color));
        ColorComboBoxEditor editor = new ColorComboBoxEditor(etiqColor);
        cbEstadosTaxi.setEditor(editor);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
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
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cbEstadosTaxi = new javax.swing.JComboBox();
        btnColor = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Despachos KRADAC");

        jsVehiculos.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jsVehiculos.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jsVehiculos.setAutoscrolls(true);
        jsVehiculos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Hora", "Télefono", "Código", "Nombre", "Dirección", "Barrio", "MIN", "UNI", "ATR", "NOTA"
            }
        ));
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
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtPorDespacharKeyTyped(evt);
            }
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
                "Hora", "Teléfono", "Código", "Nombre", "Dirección", "Barrio", "MIN", "UNI", "ATR", "NOTA"
            }
        ));
        jtDespachados.setColumnSelectionAllowed(true);
        jtDespachados.getTableHeader().setReorderingAllowed(false);
        jtDespachados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtDespachadosKeyPressed(evt);
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

        jbSalir.setText("Salir");
        jbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalirActionPerformed(evt);
            }
        });

        jLabel1.setText("Teléfono:");

        jtTelefono.setFont(new java.awt.Font("Arial", 1, 20));
        jtTelefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtTelefonoActionPerformed(evt);
            }
        });

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
                    .addComponent(jtTelefono, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtCodigo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

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
            .addComponent(jLabel3)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(cbEstadosTaxi, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btnColor))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbEstadosTaxi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnColor))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbSalir)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE)
                    .addComponent(jsVehiculos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 510, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsVehiculos, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 166, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbSalir)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1026)/2, (screenSize.height-800)/2, 1026, 800);
    }// </editor-fold>//GEN-END:initComponents

    private void jbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirActionPerformed
        bd.CerrarConexion();
        System.exit(0);
    }//GEN-LAST:event_jbSalirActionPerformed

    private void jtPorDespacharKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtPorDespacharKeyPressed
        int cod = evt.getKeyCode();
        System.out.println("Pulsado: " + cod);
        AccionesBorrado(cod, jtPorDespachar);
        //letras desde el 65 al 90
    }//GEN-LAST:event_jtPorDespacharKeyPressed

    /**
     * Borra las filas o las celdas dependiendo de la tecla que se precione
     * @param cod - codigo de la tecla
     * @param Tabla - tabla donde ejecutar la accion
     */
    private void AccionesBorrado(int cod, javax.swing.JTable Tabla) {
        int intFila = Tabla.getSelectedRow();

        if (cod == 127 || cod == 8) {
            /**
             * borrar la celda que se haya seleccionado, esto se debe
             * ejecutar cuando se presione (<-) BackSpace
             */
            Tabla.setValueAt("", Tabla.getSelectedRow(), Tabla.getSelectedColumn());
        } else if (cod == 118) {
            /**
             * borrar las celdas de nombre, direccion, barrio y nota
             * con F7
             */
            /*int intTotalColumnas = Tabla.getColumnCount();
            for (int i = 0; i < intTotalColumnas; i++) {
            Tabla.setValueAt("", intFila, i);
            }*/
            Tabla.setValueAt("", intFila, 3);
            Tabla.setValueAt("", intFila, 4);
            Tabla.setValueAt("", intFila, 5);
            Tabla.setValueAt("", intFila, 9);
        } else if (cod == 119) {
            /**
             * F8 Para borra toda la fila
             */
            try {
                DefaultTableModel model = ((DefaultTableModel) jtPorDespachar.getModel());
                model.removeRow(intFila);
                listaDespachosTemporales.remove(intFila);
            } catch (IndexOutOfBoundsException iex) {
            }
        } else if (cod == 123) {
            /**
             * F12 Para despachar
             */
            DespacharCliente(intFila);
        }
    }

    public void DespacharCliente(int intFila) {
        despacho = getDatosPorDespachar();

        if (sePuedeDespachar(despacho, intFila)) {
            boolean r = setDatosTablaDespachados(despacho, jtDespachados);
            if (r) {
                try {
                    DefaultTableModel model = ((DefaultTableModel) jtPorDespachar.getModel());
                    model.removeRow(intFila);
                    listaDespachosTemporales.remove(intFila);
                } catch (IndexOutOfBoundsException iex) {
                }
            }
        }
    }

    /**
     * Borrar el cotenido de la celda antes de escribir algo
     * TODO: implementar este metodo v2
     */
    private void BorrarParaEscribir(int cod, JTable tabla) {
        //numeros 48/57 y del 96/105
        int fila = tabla.getSelectedRow();
        int col = tabla.getSelectedColumn();

        tabla.setValueAt("", fila, col);
    }

    private boolean sePuedeDespachar(Despachos d, int fila) {
        boolean resultado = false;
        try {
            if (d.getStrHora().equals("")) {
                JOptionPane.showMessageDialog(this, "Se debe ingresar una hora de despacho...", "Error...", 0);
                jtPorDespachar.setValueAt(getHora(), fila, 0);
            } else if (d.getStrNombre().equals("")) {
                JOptionPane.showMessageDialog(this, "Se debe ingresar un nombre de cliente...", "Error...", 0);
            } else if (d.getStrDireccion().equals("")) {
                JOptionPane.showMessageDialog(this, "Se debe ingresar una dirección del cliente...", "Error...", 0);
            } else if (d.getStrBarrio().equals("")) {
                JOptionPane.showMessageDialog(this, "Se debe ingresar un barrio donde vive el cliente...", "Error...", 0);
            } else if (d.getIntMinutos() < 0) {
                JOptionPane.showMessageDialog(this, "La estimación de tiempo de llegada a recojer al cliente es incorrecta, tiene que ser mayor a 0...", "Error...", 0);
                int col = jtPorDespachar.getSelectedColumn();
                jtPorDespachar.setValueAt("0", fila, 6);
                jtPorDespachar.setValueAt("0", fila, 8);
            } else if (!validarUnidad(d.getIntUnidad())) {
                JOptionPane.showMessageDialog(this, "La unidad ingresada no es válida...", "Error...", 0);
            } else {
                resultado = true;
            }
        } catch (NullPointerException nex) {
        }
        return resultado;
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        System.out.println(jtPorDespachar.getSelectedRow() + " " + jtPorDespachar.getSelectedColumn());

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jtDespachadosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtDespachadosKeyPressed
        int cod = evt.getKeyCode();
        AccionesBorrado(cod, jtDespachados);
    }//GEN-LAST:event_jtDespachadosKeyPressed

    private void jtPorDespacharMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtPorDespacharMousePressed
        int intClicks = evt.getClickCount();
        int intBoton = evt.getButton();
        System.out.println("clc: " + intClicks + " bot: " + intBoton);
        VentanaDatos ventanaDatos = null;
        try {
            if (intClicks == 1 && intBoton == 3) {
                ventanaDatos = new VentanaDatos(getDatosPorDespachar());
                if (!ventanaDatos.isVisible()) {
                    ventanaDatos.setVisible(true);
                }
            }
            if (intClicks == 1 && intBoton == 1) {
                despacho = getDatosPorDespachar();
            }
        } catch (NullPointerException npe) {
            System.err.println("No se puede mostrar la ventana, no hay datos...");
        }

    }//GEN-LAST:event_jtPorDespacharMousePressed

    /**
     * Recoje los datos de las fila seleccionada y crea un objeto con todos esos datos
     * @return Despachos
     */
    private Despachos getDatosPorDespachar() {
        int fila = jtPorDespachar.getSelectedRow();
        Despachos datos = null;
        try {
            strHora = (String) jtPorDespachar.getValueAt(fila, 0);
            strTelefono = (String) jtPorDespachar.getValueAt(fila, 1);
            strCodigo = (String) jtPorDespachar.getValueAt(fila, 2);
            strNombre = (String) jtPorDespachar.getValueAt(fila, 3);
            strDireccion = (String) jtPorDespachar.getValueAt(fila, 4);
            strBarrio = (String) jtPorDespachar.getValueAt(fila, 5);
            try {
                intMinutos = Integer.parseInt(jtPorDespachar.getValueAt(fila, 6).toString());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Solo debe ser número los campos de Minutos(MIN)...", "Error...", 0);
                jtPorDespachar.setValueAt("0", fila, 6);
            }
            try {
                intUnidad = Integer.parseInt(jtPorDespachar.getValueAt(fila, 7).toString());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Solo debe ser número los campos de Unidad(UNI)...", "Error...", 0);
                jtPorDespachar.setValueAt("0", fila, 7);
            }
            try {
                intAtraso = Integer.parseInt(jtPorDespachar.getValueAt(fila, 8).toString());
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Solo debe ser número los campos de Atraso(ATR)...", "Error...", 0);
                jtPorDespachar.setValueAt("0", fila, 8);
            }
            strNota = (String) jtPorDespachar.getValueAt(fila, 9);

            datos = new Despachos(strHora, strTelefono, strCodigo, strNombre,
                    strDireccion, strBarrio, intMinutos, intUnidad, intAtraso, strNota);
        } catch (NullPointerException npe) {
            //System.err.println("Error de NULL -> No hay datos Seleccionados...");
        } catch (ArrayIndexOutOfBoundsException aiobe) {
            //System.err.println("Error de NULL -> No hay datos Seleccionados...");
        }

        return datos;
    }

    private void jtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtTelefonoActionPerformed
        jtCodigo.setText(getBuscarPorTelefono());
        jtTelefono.setText("");
    }//GEN-LAST:event_jtTelefonoActionPerformed

    /**
     * Obtener el código del usuario dependiendo del telefono ingresado
     * @return String
     */
    private String getBuscarPorTelefono() {
        strTelefono = jtTelefono.getText();
        try {
            String sql = "SELECT CODIGO,"
                    + "NOMBRE_APELLIDO_CLI,"
                    + "DIRECCION_CLI,"
                    + "SECTOR"
                    + " FROM CLIENTES WHERE TELEFONO='" + strTelefono + "'";
            rs = bd.ejecutarConsultaUnDato(sql);
            strCodigo = rs.getString("CODIGO");
            strNombre = rs.getString("NOMBRE_APELLIDO_CLI");
            strDireccion = rs.getString("DIRECCION_CLI");
            strBarrio = rs.getString("SECTOR");
            strHora = getHora();
            despacho = new Despachos(strHora, strTelefono, strCodigo, strNombre, strDireccion, strBarrio, "");

            setDatosTablas(despacho, jtPorDespachar);
        } catch (SQLException ex) {
            //Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("No devuelve valores esa consulta...");
        }
        return strCodigo;
    }

    private void jtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtCodigoActionPerformed
        jtTelefono.setText(getBuscarPorCodigo());
        jtCodigo.setText("");
    }//GEN-LAST:event_jtCodigoActionPerformed

    /**
     * Obtener el código del usuario dependiendo del telefono ingresado
     * @return String
     */
    private String getBuscarPorCodigo() {
        strCodigo = jtCodigo.getText();
        try {
            String sql = "SELECT TELEFONO,"
                    + "NOMBRE_APELLIDO_CLI,"
                    + "DIRECCION_CLI,"
                    + "SECTOR"
                    + " FROM CLIENTES WHERE CODIGO='" + strCodigo + "'";
            rs = bd.ejecutarConsultaUnDato(sql);
            strTelefono = rs.getString("TELEFONO");
            strNombre = rs.getString("NOMBRE_APELLIDO_CLI");
            strDireccion = rs.getString("DIRECCION_CLI");
            strBarrio = rs.getString("SECTOR");
            strHora = getHora();
            despacho = new Despachos(strHora, strTelefono, strCodigo, strNombre, strDireccion, strBarrio, "");

            setDatosTablas(despacho, jtPorDespachar);
        } catch (SQLException ex) {
            //Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("No devuelve valores esa consulta...");
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
    }//GEN-LAST:event_btnColorActionPerformed

    /**
     * Cambia el estado de un TAXI en la BD
     * @param etq
     * @param codVh
     */
    private void cambiarEstadoTaxi(String etq, ArrayList<String> codVh) {

        int et = etiq.indexOf(etq);
        String cod = codigo.get(et);

        for (String i : codVh) {
            String sql = "INSERT INTO REGCODESTTAXI VALUES (now(),now(),'" + cod + "','" + sesion[0] + "','" + i + "')";
            bd.ejecutarSentencia(sql);
        }
        pintarEstadoTaxi(strEncabezados);
    }
    //Matriz de Relacion CODIGO - COLOR
    //Cada Elemento de codColor
    //[1][ID_CODIGO]  [2][COLOR]
    ArrayList<String> codigo = new ArrayList();
    ArrayList<String> color = new ArrayList();
    ArrayList<String> etiq = new ArrayList();
    Map etiqColor = new HashMap();

    private void colorCodigosBD() {
        try {
            ConexionBase cb = new ConexionBase();
            String sql = "SELECT ID_CODIGO,COLOR,ETIQUETA  FROM CODESTTAXI";
            rs = cb.ejecutarConsultaUnDato(sql);

            codigo.clear();
            color.clear();
            etiq.clear();
            do {
                codigo.add(rs.getString(1));
                color.add(rs.getString(2));
                etiq.add(rs.getString(3));
                etiqColor.put(rs.getString(3), rs.getString(2));
            } while (rs.next());
            cb.CerrarConexion();
        } catch (SQLException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Coloca el color de fondo de las celdas
     * especificadas según el estado del taxi.
     * @param encab  columnas a pintar
     */
    private void pintarEstadoTaxi(ArrayList<String> encab) {
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

        for (int i = 0, len = jtVehiculos.getColumnCount(); i < len; i++) {
            TableColumn column = jtVehiculos.getColumn(jtVehiculos.getColumnName(i));
            column.setCellRenderer(new formatoTabla(encab, unidadCodigoBD, codigo, color));
        }
        jtVehiculos.repaint();
    }

    private void jtPorDespacharKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtPorDespacharKeyTyped
//
    }//GEN-LAST:event_jtPorDespacharKeyTyped

    private void jtPorDespacharPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jtPorDespacharPropertyChange
        int intFila = jtPorDespachar.getSelectedRow();
        int intCol = jtPorDespachar.getSelectedColumn();
        intMinutos = 0;
        if (intCol == 6) {
            try {
                intMinutos = Integer.parseInt((String) jtPorDespachar.getValueAt(intFila, 6));
            } catch (NumberFormatException es) {
                System.err.println("Solo Numeros en la columna de Minutos");
            }

            jtPorDespachar.setValueAt("" + (intMinutos * -1), intFila, 8);
        }

        Mayuculas(jtPorDespachar, intFila);
    }//GEN-LAST:event_jtPorDespacharPropertyChange

    /**
     * Convierte a mayusculas todas las letras de una fila de la tabla
     * @param Tabla
     * @param intFila
     */
    public void Mayuculas(JTable Tabla, int intFila) {
        int intTotalColumnas = Tabla.getColumnCount();
        for (int i = 0; i < intTotalColumnas; i++) {
            try {
                Tabla.setValueAt(Tabla.getValueAt(intFila, i).toString().toUpperCase(), intFila, i);
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
                new Principal().setVisible(true);
            }
        });


    }

    public void setSession(String[] datos) {
        Principal.sesion = datos;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnColor;
    private javax.swing.JComboBox cbEstadosTaxi;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbSalir;
    private javax.swing.JScrollPane jsVehiculos;
    private javax.swing.JTextField jtCodigo;
    private javax.swing.JTable jtDespachados;
    private javax.swing.JTable jtPorDespachar;
    private javax.swing.JTextField jtTelefono;
    private javax.swing.JTable jtVehiculos;
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
     * Ingresa Un despacho a la Tabla de de Despachados en la parte inferior
     * @param des
     * @param tabla
     * @return boolean -> truesi se pudo insertar en la base de datos
     */
    public boolean setDatosTablaDespachados(Despachos des, JTable tabla) {
        boolean r = bd.InsertarDespachoCliente(des);
        int fila = jtPorDespachar.getSelectedRow();

        if (!r) {
            JOptionPane.showMessageDialog(this, "No se puede despachar esa unidad no está activa...", "Error", 0);
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
}
