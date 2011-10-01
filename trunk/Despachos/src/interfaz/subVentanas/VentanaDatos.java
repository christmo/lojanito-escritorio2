
/*
 * VentanaDatos.java
 *
 * Created on 05/08/2010, 09:57:11 AM
 */
package interfaz.subVentanas;

import BaseDatos.ConexionBase;
import interfaz.comunicacion.mapa.ObtenerCoordenadasMapa;
import interfaz.funcionesUtilidad;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author root
 */
public class VentanaDatos extends javax.swing.JFrame {

    private Despachos datos = new Despachos();
    private ConexionBase bd;
    /**
     * Es una bandera para saber si actualizar la inforamcion <b>false</b> o insertar
     * los datos del cliente con <b>true</b>
     */
    private boolean accion = false;
    private ResultSet rs;
    private int filaSeleccionada = 0;
    private JTable tabla;
    /**
     * Bandera para saber si la orden viene del menu o desde la tabla de clientes
     * por despachar, cuando viene del menu es <b>true</b> y el cliente siempre se
     * inserta, cuando viene de la tabla con <b>false</b> se puede actualizar la info
     * o insertar nuevos datos...
     */
    private boolean menu = false;

    private VentanaDatos() {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());
    }

    /**
     * Constructor para llamar a la ventana de datos para ingresar nuevos clientes
     * desde el menu de opciones
     * @param menu
     * @param bd
     */
    public VentanaDatos(boolean menu, ConexionBase bd) {
        initComponents();
        this.bd = bd;
        this.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());
        jtTelefono.setEnabled(true);
        jtCodigo.setEnabled(true);
        this.menu = menu;
        initEdicionLatLon();
    }

    /**
     * Se utiliza este constructor cuando en la tabla por despachar no se pone un
     * telefono es un usuario sin telefono pero hay que visualizar los datos
     * @param despacho
     * @param bd
     * @param ActivarTelefono
     */
    public VentanaDatos(Despachos despacho, ConexionBase bd, int caso) {
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());
        this.datos = despacho;
        this.bd = bd;
        cargarDatos(datos);
        switch (caso) {
            case 1:
                jtTelefono.setEnabled(true);
                break;
            case 2:
                jtTelefono.setEnabled(true);
                break;
            case 3:
                jtTelefono.setEnabled(true);
                break;
        }
        this.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());
        initEdicionLatLon();
    }

    /****************** Modo solo lectura ********************************
     * Enviar en el estado false, para mostrar la ventana en modo de lectura de
     * información, no permitir cambiar los datos, se utiliza en la tabla de
     * despachados para mostrar la informacion del cliente
     * @param datosDespachados
     * @param estado //false para modo de lectura
     *********************************************************************/
    public VentanaDatos(Despachos datosDespachados, boolean estado, ConexionBase bd) {
        initComponents();
        this.datos = datosDespachados;
        this.bd = bd;
        this.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());
        cargarDatos(datos);
        estadoCampos(estado);
    }

    /**
     * Permite setear los valores a presentarse en la ventana para la tabla
     * PorDespachar lo que permitira la edicion de la informacions
     * @param despacho
     */
    public void setPorDespachar(Despachos despacho) {
        this.datos = despacho;
        cargarDatos(datos);
        estadoCampos(true);
        initEdicionLatLon();
    }

    /**
     * Permite setear los valores a presentarse en la ventana para la tabla
     * PorDespachar lo que permitira la edicion de la informacions
     * @param despacho
     */
    public void setPorDespachar(Despachos despacho, int caso) {
        this.datos = despacho;
        cargarDatos(datos);
        estadoCampos(true);
        switch (caso) {
            case 1:
                jtTelefono.setEnabled(true);
                break;
            case 2:
                jtTelefono.setEnabled(true);
                break;
            case 3:
                jtTelefono.setEnabled(true);
                break;
        }
        initEdicionLatLon();
    }

    /**
     * Inicializar los campos de edicion de latitud y longitud
     */
    private void initEdicionLatLon() {
        jcEditarCoord.setSelected(false);
        jtLatitud.setEditable(false);
        jtLongitud.setEditable(false);
    }

    /**
     * Permite setear los valores de la ventana para la tabla despachados,
     * esto no permitira editar los campos y no creara un objeto nuevo de la
     * ventana para la presentacion de la informacion
     * @param despacho
     * @param estado
     */
    public void setDespachados(Despachos despacho, boolean estado) {
        this.datos = despacho;
        cargarDatos(datos);
        estadoCampos(estado);
    }

    /**
     * Deshabilita los campos para que no se pueda cambiar la info cuando se
     * muestra los parametros en modo de lectura
     * @param estado
     */
    private void estadoCampos(boolean estado) {
        jtNombre.setEditable(estado);
        jtDireccion.setEditable(estado);
        jtNumeroCasa.setEditable(estado);
        jtBarrio.setEditable(estado);
        jtReferencia.setEditable(estado);
        jtNota.setEditable(estado);
        jbAceptar.setEnabled(estado);
        jbCodigo.setEnabled(estado);
        jtTelefono.setEnabled(estado);
        jtLatitud.setEditable(estado);
        jtLongitud.setEditable(estado);
        jcEditarCoord.setEnabled(estado);
        jbSalir.setVisible(true);
    }

    public void setDatosFila(JTable tabla, int fila) {
        this.filaSeleccionada = fila;
        this.tabla = tabla;
    }
    /**
     * Permite actualizar los datos del cliente cunado no tiene telefono
     * ni codigo, pero el cliente estan ingresado en la base de datos
     */
    boolean actualizarConNombre = false;

    /**
     * Carga cada uno de los Datos en los cuadros de texto
     * @param despacho
     */
    private void cargarDatos(Despachos despacho) {
        /**
         * El despacho tiene telefono
         */
        boolean booTelefono = false;
        String cod = "" + despacho.getIntCodigo();
        if (cod == null || cod.equals("") || cod.equals("0")) {
            //si no tiene codigo pero el cliente existe o no
            jbCodigo.setVisible(true);
            actualizarConCod = false;
            jtCodigo.setText("");
            menu = false;
            try {
                String tel = despacho.getStrTelefono();
                if (tel.equals("") || tel.equals("null")) {
                    jtNombre.setEditable(false);
                    jtDireccion.setEditable(false);
                    actualizarConNombre = true; //actualiza con nombre
                    booTelefono = true;
                }
            } catch (NullPointerException ex) {
                //Telefono es null no hacer nada
            }

        } else {
            //si tiene codigo el cliente actualizar de ley
            jbCodigo.setVisible(false);
            accion = false; //actualizar
            actualizarConCod = true;
            jtCodigo.setText(cod);
            menu = false;
        }

        jtTelefono.setText(despacho.getStrTelefono());
        jtNombre.setText(despacho.getStrNombre());
        jtDireccion.setText(despacho.getStrDireccion());
        jtBarrio.setText(despacho.getStrBarrio());
        jtNota.setText(despacho.getStrNota());

        if (despacho.getIntCodigo() != 0) {
            ObtenerDatosClienteConCodigo(despacho.getIntCodigo());
        } else {

            if (booTelefono) {
                /**
                 * La accion sera si con el telefono retorna datos hay que
                 * actualizar la info caso contrario hay que insertar los
                 * datos en la tabla de clientes
                 */
                accion = ObtenerDatosClienteConTelefono(despacho.getStrTelefono());
            } else {
                if (ObtenerDatosClienteConNombreYDireccion(despacho.getStrNombre(), despacho.getStrDireccion())) {
                    accion = false; //Actualizar la inforamcion
                } else {
                    accion = true; //-> insertar
                }
            }

        }
    }
    /**
     * Bandera que permite saber por que campo se va actualziar la informacion
     * true cuando el cliente tiene codigo y false si se va actualizar por telefono
     */
    boolean actualizarConCod;

    /**
     * Obtener datos del un cliente ingresado en la base que tiene un codigo
     * @param codigo
     */
    private void ObtenerDatosClienteConCodigo(int codigo) {
        String sql = "SELECT NUM_CASA_CLI, INFOR_ADICIONAL,LATITUD,LONGITUD FROM CLIENTES WHERE CODIGO=" + codigo;
        rs = bd.ejecutarConsultaUnDato(sql);

        try {
            String n_casa = rs.getString("NUM_CASA_CLI");
            String referencia = rs.getString("INFOR_ADICIONAL");
            String lat = rs.getString("LATITUD");
            String lon = rs.getString("LONGITUD");
            jtNumeroCasa.setText(n_casa);
            jtReferencia.setText(referencia);
            jtLatitud.setText(lat);
            jtLongitud.setText(lon);
        } catch (SQLException ex) {
            //System.err.println("No hay datos en el resulSet... Clase -> VentanaDatos :-)");
            jtNumeroCasa.setText("");
            jtReferencia.setText("");
        }
    }

    /**
     * Obtiene los datos de un cliente ingresado pero que no tiene codigo
     * Retorna falso cuando si obtiene datos esto es para tomar la accion de
     * actualizacion de los datos de la tabla de clientes
     * Retorna true cuando no obtiene valores para ese telefono, eso quiere decir
     * que la accion sera de insertar los nuevos datos ingresados
     * @param telefono
     */
    private boolean ObtenerDatosClienteConTelefono(String telefono) {
        String sql = "SELECT NUM_CASA_CLI, INFOR_ADICIONAL,LATITUD,LONGITUD FROM CLIENTES WHERE TELEFONO='" + telefono + "'";
        rs = bd.ejecutarConsultaUnDato(sql);

        try {
            String n_casa = rs.getString("NUM_CASA_CLI");
            String referencia = rs.getString("INFOR_ADICIONAL");
            String lat = rs.getString("LATITUD");
            String lon = rs.getString("LONGITUD");
            jtNumeroCasa.setText(n_casa);
            jtReferencia.setText(referencia);
            jtLatitud.setText(lat);
            jtLongitud.setText(lon);
            return false; // -> actualizar los datos
        } catch (SQLException ex) {
            //System.err.println("No hay datos en el resulSet... Clase -> VentanaDatos :-)");
            jtNumeroCasa.setText("");
            jtReferencia.setText("");
            return true; // -> insertar los datos
        }
    }

    /**
     * Comprueba si el cliente esta en la base de datos a partir de un nombre y una
     * direccion retorna <b>true si el cliente existe</b> lo cual indica que se debe
     * actualizar la info de ese cliente, y retorna <b>false si el cliente no existe</b>
     * lo que conlleva a que se inserte el nuevo dato
     * @param nombre
     * @param dir
     * @return boolean |true si existe el cliente el bd
     */
    private boolean ObtenerDatosClienteConNombreYDireccion(String nombre, String dir) {
        String sql = "SELECT NUM_CASA_CLI, INFOR_ADICIONAL,LATITUD,LONGITUD FROM CLIENTES WHERE NOMBRE_APELLIDO_CLI='" + nombre + "' AND DIRECCION_CLI='" + dir + "'";
        rs = bd.ejecutarConsultaUnDato(sql);

        try {
            String n_casa = rs.getString("NUM_CASA_CLI");
            String referencia = rs.getString("INFOR_ADICIONAL");
            String lat = rs.getString("LATITUD");
            String lon = rs.getString("LONGITUD");
            jtNumeroCasa.setText(n_casa);
            jtReferencia.setText(referencia);
            jtLatitud.setText(lat);
            jtLongitud.setText(lon);
            return true; // -> actualizar los datos
        } catch (SQLException ex) {
            //System.err.println("No hay datos en el resulSet... Clase -> VentanaDatos :-)");
            jtNumeroCasa.setText("");
            jtReferencia.setText("");
            return false; // -> insertar los datos
        }
    }

    /**
     * Limpia todos los campos
     */
    public void LimpiarCampos() {
        jtCodigo.setText("");
        jtTelefono.setText("");
        jtNombre.setText("");
        jtDireccion.setText("");
        jtBarrio.setText("");
        jtNota.setText("");
        jtNumeroCasa.setText("");
        jtReferencia.setText("");
        jtLatitud.setText("");
        jtLongitud.setText("");
        jbAceptar.setEnabled(true);
        jbSalir.setVisible(true);
    }

    /**
     * Permite insertar un cliente en la tabla de posisionamiento para que sea
     * dibujado en el mapa
     * @param codigoCliente
     * @param n_unidad
     */
    private void IngresarClienteMapa(String codigoCliente) {

        double lat = datos.getLatitud();
        double lon = datos.getLongitud();


        if (lon != 0 && lat != 0) {
            if (!codigoCliente.equals("") || !codigoCliente.equals("0")) {
                bd.InsertarClienteMapa(datos.getIntCodigo(), datos.getStrNombre(),
                        datos.getStrBarrio(), datos.getStrTelefono(), lat, lon);
            }
        }
    }

    /**
     * Guarda los datos de nuevos o editados del cliente en la base de datos
     */
    private void GuardarDatos() {
        boolean resultado = false;
        datos = getDatosNuevos();
        if (!datos.getStrNombre().equals("") && !datos.getStrDireccion().equals("")) {
            if (menu) {//Accion desde el menu siempre inserta clientes
                resultado = bd.InsertarClienteMenu(datos);
                if (!resultado) {
                    JOptionPane.showMessageDialog(this, "No se pudo guardar el cliente, ese número de teléfono esta asignado a otro cliente...", "Error", 0);
                }
            } else {
                if (accion) { // true inserta los datos nuevos
                    resultado = bd.InsertarCliente(datos);
                } else { // desde la tabla actualiza los datos cuando hay codigo
                    if (actualizarConCod) {
                        /**
                         * Actualiza cuando existe un codigo donde actualizar
                         */
                        resultado = bd.ActualizarClienteCod(datos);
                    } else {

                        if (actualizarConNombre) {
                            /**
                             * Actualiza cuando no hay ni cod ni tel, y actualiza
                             * los datos a partir del nombre y la direccion
                             */
                            resultado = bd.ActualizarClientePorNombre(datos);
                        } else {
                            /**
                             * Actualiza cuando no tiene codigo pero tiene un telefono
                             */
                            resultado = bd.ActualizarClienteTel(datos);
                        }

                    }
                    if (!resultado) {
                        JOptionPane.showMessageDialog(this, "No se pudo actualizar el cliente...", "Error", 0);
                    }
                }
            }
            LimpiarCampos();
            this.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "No se puede dejar el nombre y dirección en blanco...", "Error", 0);
        }
    }

    /**
     * Inserta la cambios en la tabla de datos por despachar de la interfaz principal
     * @param nota
     */
    private void insertarDatosTabla(String txt, int col) {
        try {
            if (!txt.equals("")) {
                tabla.setValueAt(txt, filaSeleccionada, col);
            }
        } catch (NullPointerException npx) {
        }
    }

    /**
     * Obtiene los datos nuevos o editados del formulario
     * @return Despachos
     */
    private Despachos getDatosNuevos() {
        Despachos d = new Despachos();
        try {
            try {
                d.setIntCodigo(Integer.parseInt(jtCodigo.getText()));
            } catch (NumberFormatException ex) {
                d.setIntCodigo(0);
            }
            d.setStrTelefono(jtTelefono.getText());
            d.setStrNombre(jtNombre.getText());
            d.setStrDireccion(jtDireccion.getText());
            d.setStrBarrio(jtBarrio.getText());
            d.setStrNota(jtNota.getText());
            d.setStrNumeroCasa(jtNumeroCasa.getText());
            d.setStrReferecia(jtReferencia.getText());
            try {
                d.setLatitud(Double.parseDouble(jtLatitud.getText()));
                d.setLongitud(Double.parseDouble(jtLongitud.getText()));
            } catch (NumberFormatException ex) {
                d.setLatitud(0);
                d.setLongitud(0);
                System.err.println("Latitud o longitud mal");
            }
        } catch (NullPointerException ex) {
            System.err.println("Null en la ventana de Datos");
        }
        return d;
    }

    /**
     * Convierete a mayusculas lo que se envie
     * @param txt
     * @return string
     */
    public String Mayusculas(String txt) {
        return txt.toUpperCase();
    }
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                new VentanaDatos().setVisible(true);
//            }
//        });
//    }

    /**
     * Permite escribir las coordenadas, con un clic desde el navegador
     * en los campos de latitud y longitud
     * @param latitud
     * @param longitud
     */
    public static void setCoordenadasMapa(String latitud, String longitud) {
        if (jcEditarCoord.isSelected()) {
            jtLatitud.setText(latitud);
            jtLongitud.setText(longitud);
        }
    }
    /**
     * Conxion mapa web KRADAC para recolectar las coordenadas para los
     * clientes
     */
    private ObtenerCoordenadasMapa sock;

    private void AbrirPuertoCoordenadas() {
        sock = new ObtenerCoordenadasMapa(bd);
    }

    public void CerrarPuertoCoordenadas() {
        try {
            sock.PararDeEscuchar();
        } catch (NullPointerException ex) {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpDatos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtCodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jtTelefono = new javax.swing.JTextField();
        jtNombre = new javax.swing.JTextField();
        jtDireccion = new javax.swing.JTextField();
        jtNumeroCasa = new javax.swing.JTextField();
        jtBarrio = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtReferencia = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtNota = new javax.swing.JTextArea();
        jbCodigo = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jtLatitud = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jtLongitud = new javax.swing.JTextField();
        jcEditarCoord = new javax.swing.JCheckBox();
        jbAceptar = new javax.swing.JButton();
        jbSalir = new javax.swing.JButton();

        setTitle("Clientes");
        setBackground(java.awt.Color.white);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel1.setText("Código:");

        jtCodigo.setFont(new java.awt.Font("Arial", 1, 24));
        jtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtCodigo.setEnabled(false);
        jtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtCodigoFocusLost(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel2.setText("Nombre:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel3.setText("Dirección:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel4.setText("# Casa:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel6.setText("Teléfono:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel9.setText("Barrio:");

        jtTelefono.setFont(new java.awt.Font("Arial", 1, 20));
        jtTelefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtTelefono.setEnabled(false);
        jtTelefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtTelefonoFocusLost(evt);
            }
        });

        jtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtNombreFocusLost(evt);
            }
        });

        jtDireccion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtDireccionFocusLost(evt);
            }
        });

        jtNumeroCasa.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtNumeroCasaFocusLost(evt);
            }
        });

        jtBarrio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtBarrioFocusLost(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel5.setText("Referencia:");

        jtReferencia.setColumns(20);
        jtReferencia.setLineWrap(true);
        jtReferencia.setRows(5);
        jtReferencia.setTabSize(0);
        jtReferencia.setHighlighter(null);
        jtReferencia.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jtReferencia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtReferenciaFocusLost(evt);
            }
        });
        jtReferencia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtReferenciaKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jtReferencia);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel11.setText("Nota:");

        jtNota.setColumns(20);
        jtNota.setLineWrap(true);
        jtNota.setRows(5);
        jtNota.setTabSize(0);
        jtNota.setHighlighter(null);
        jtNota.setMargin(new java.awt.Insets(5, 5, 5, 5));
        jtNota.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtNotaFocusLost(evt);
            }
        });
        jtNota.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtNotaKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jtNota);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addContainerGap(531, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
        );

        jbCodigo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/codigo.png"))); // NOI18N
        jbCodigo.setToolTipText("Generar Código");
        jbCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCodigoActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel7.setText("Latitud:");

        jtLatitud.setEditable(false);
        jtLatitud.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtLatitud.setText("0.0");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11));
        jLabel8.setText("Longitud:");

        jtLongitud.setEditable(false);
        jtLongitud.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtLongitud.setText("0.0");

        jcEditarCoord.setText("Editar Coordenadas del Cliente");
        jcEditarCoord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcEditarCoordActionPerformed(evt);
            }
        });

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/ok.png"))); // NOI18N
        jbAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAceptarActionPerformed(evt);
            }
        });

        jbSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/salir.png"))); // NOI18N
        jbSalir.setText("Cancelar");
        jbSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpDatosLayout = new javax.swing.GroupLayout(jpDatos);
        jpDatos.setLayout(jpDatosLayout);
        jpDatosLayout.setHorizontalGroup(
            jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDatosLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jpDatosLayout.createSequentialGroup()
                        .addComponent(jcEditarCoord)
                        .addContainerGap())
                    .addComponent(jLabel2)
                    .addGroup(jpDatosLayout.createSequentialGroup()
                        .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(14, 14, 14)
                        .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDatosLayout.createSequentialGroup()
                                .addComponent(jtNumeroCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                                .addComponent(jLabel9)
                                .addGap(27, 27, 27)
                                .addComponent(jtBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDatosLayout.createSequentialGroup()
                                .addComponent(jtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jbCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
                            .addComponent(jtDireccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jpDatosLayout.createSequentialGroup()
                        .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jbAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jpDatosLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtLatitud, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                        .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpDatosLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(6, 6, 6)
                                .addComponent(jtLongitud, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jbSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        jpDatosLayout.setVerticalGroup(
            jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(jbCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtNumeroCasa)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jcEditarCoord)
                .addGap(8, 8, 8)
                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jtLongitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtLatitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jbSalir))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpDatos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dialogSize = getSize();
        setLocation((screenSize.width-dialogSize.width)/2,(screenSize.height-dialogSize.height)/2);
    }// </editor-fold>//GEN-END:initComponents

    private void jbAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAceptarActionPerformed
        CerrarPuertoCoordenadas();
        GuardarDatos();
        String cod = jtCodigo.getText();
        IngresarClienteMapa(cod);
        this.dispose();
    }//GEN-LAST:event_jbAceptarActionPerformed

    private void jbCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCodigoActionPerformed
        if (jtCodigo.getText() == null || jtCodigo.getText().equals("")) {
            try {
                int cod = Integer.parseInt(bd.generarCodigo());
                jtCodigo.setText("" + cod);
                insertarDatosTabla(jtCodigo.getText(), 2);
            } catch (SQLException ex) {
                Logger.getLogger(VentanaDatos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException ex) {
                Logger.getLogger(VentanaDatos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jbCodigoActionPerformed

    private void jtNombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtNombreFocusLost
        jtNombre.setText(Mayusculas(jtNombre.getText()));
        insertarDatosTabla(jtNombre.getText(), 3);
    }//GEN-LAST:event_jtNombreFocusLost

    private void jtDireccionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtDireccionFocusLost
        jtDireccion.setText(Mayusculas(jtDireccion.getText()));
        insertarDatosTabla(jtDireccion.getText(), 5);
    }//GEN-LAST:event_jtDireccionFocusLost

    private void jtBarrioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtBarrioFocusLost
        jtBarrio.setText(Mayusculas(jtBarrio.getText()));
        insertarDatosTabla(jtBarrio.getText(), 4);
    }//GEN-LAST:event_jtBarrioFocusLost

    private void jtNumeroCasaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtNumeroCasaFocusLost
        jtNumeroCasa.setText(Mayusculas(jtNumeroCasa.getText()));
    }//GEN-LAST:event_jtNumeroCasaFocusLost

    private void jtReferenciaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtReferenciaFocusLost
        jtReferencia.setText(Mayusculas(jtReferencia.getText()));
    }//GEN-LAST:event_jtReferenciaFocusLost

    private void jtNotaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtNotaFocusLost
        jtNota.setText(Mayusculas(jtNota.getText()));
        insertarDatosTabla(jtNota.getText(), 9);
    }//GEN-LAST:event_jtNotaFocusLost

    private void jbSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbSalirActionPerformed
        CerrarPuertoCoordenadas();
        LimpiarCampos();
        this.dispose();
    }//GEN-LAST:event_jbSalirActionPerformed

    private void jtReferenciaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtReferenciaKeyPressed
        if (evt.getKeyCode() == 9) {
            jtNota.requestFocus();
        }
    }//GEN-LAST:event_jtReferenciaKeyPressed

    private void jtNotaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtNotaKeyPressed
        if (jbCodigo.isVisible()) {
            if (evt.getKeyCode() == 9) {
                jbAceptar.requestFocus();
            }
        } else {
            if (evt.getKeyCode() == 9) {
                jbSalir.requestFocus();
            }
        }
    }//GEN-LAST:event_jtNotaKeyPressed

    private void jtTelefonoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtTelefonoFocusLost
        funcionesUtilidad f = new funcionesUtilidad();
        String tel = jtTelefono.getText();
        if (f.isNumeric(tel)) {
            try {
                String telefono = f.validarTelefono(tel);
                jtTelefono.setText(telefono);
                rs = bd.getClientePorTelefono(telefono);
                String cod = rs.getString("CODIGO");
                if (!cod.equals(jtCodigo.getText())) {
                    try {
                        JOptionPane.showMessageDialog(this, "Ya existe un cliente ingresado con ese teléfono es:\n" + "Nombre del Cliente: " + rs.getString("NOMBRE_APELLIDO_CLI") + "\n" + "Codigo: " + rs.getString("CODIGO"), "Error...", 0);
                        jtTelefono.setText(datos.getStrTelefono());
                    } catch (SQLException ex) {
                        //Logger.getLogger(VentanaDatos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (SQLException ex) {
                //Logger.getLogger(VentanaDatos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {
            }
            insertarDatosTabla(jtTelefono.getText(), 1);
        } else {
            jtTelefono.setText("");
        }
    }//GEN-LAST:event_jtTelefonoFocusLost

    private void jcEditarCoordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcEditarCoordActionPerformed
        if (jcEditarCoord.isSelected()) {
            jtLatitud.setEditable(true);
            jtLongitud.setEditable(true);
            AbrirPuertoCoordenadas();
        } else {
            jtLatitud.setEditable(false);
            jtLongitud.setEditable(false);
            CerrarPuertoCoordenadas();
        }
    }//GEN-LAST:event_jcEditarCoordActionPerformed

    private void jtCodigoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtCodigoFocusLost
        String cod_cli = jtCodigo.getText();
        if (bd.clienteExiste(cod_cli)) {
            JOptionPane.showMessageDialog(this, "Ya hay otro cliente con ese código, es mejor utilizar el boton "
                    + "para generar un código automaticamente...", "Error", 0);
            jtCodigo.setText("");
            jtCodigo.requestFocus();
        }
    }//GEN-LAST:event_jtCodigoFocusLost
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbCodigo;
    private javax.swing.JButton jbSalir;
    private static javax.swing.JCheckBox jcEditarCoord;
    private javax.swing.JPanel jpDatos;
    private javax.swing.JTextField jtBarrio;
    private javax.swing.JTextField jtCodigo;
    private javax.swing.JTextField jtDireccion;
    public static javax.swing.JTextField jtLatitud;
    public static javax.swing.JTextField jtLongitud;
    private javax.swing.JTextField jtNombre;
    private javax.swing.JTextArea jtNota;
    private javax.swing.JTextField jtNumeroCasa;
    private javax.swing.JTextArea jtReferencia;
    private javax.swing.JTextField jtTelefono;
    // End of variables declaration//GEN-END:variables
}
