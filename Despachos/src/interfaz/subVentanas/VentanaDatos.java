
/*
 * VentanaDatos.java
 *
 * Created on 05/08/2010, 09:57:11 AM
 */
package interfaz.subVentanas;

import BaseDatos.ConexionBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author root
 */
public class VentanaDatos extends javax.swing.JDialog {

    private Despachos datos = new Despachos();
    private ConexionBase bd = new ConexionBase();
    private boolean accion = false;
    private ResultSet rs;
    private int filaSeleccionada = 0;
    private JTable tabla;

    /** Creates new form VentanaDatos */
    public VentanaDatos() {
        initComponents();
    }

    public VentanaDatos(Despachos despacho) {
        initComponents();
        this.datos = despacho;
        cargarDatos(datos);
    }

    /**
     * Enviar en el estado false, para mostrar la ventana en modo de lectura de
     * información, no permitir cambiar los datos
     * @param datosDespachados
     * @param estado //false para modo de lectura
     */
    public VentanaDatos(Despachos datosDespachados, boolean estado) {
        initComponents();
        this.datos = datosDespachados;
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
        jbSalir.setVisible(true);
    }

    public void setDatosFila(JTable tabla, int fila) {
        this.filaSeleccionada = fila;
        this.tabla = tabla;
    }

    /**
     * Carga cada uno de los Datos en los cuadros de texto
     * @param despacho
     */
    private void cargarDatos(Despachos despacho) {
        String cod = "" + despacho.getIntCodigo();
        if (cod == null || cod.equals("") || cod.equals("0")) {
            jbCodigo.setVisible(true);
            accion = true; //insertar
            jtCodigo.setText("");
        } else {
            jbCodigo.setVisible(false);
            accion = false; //actualizar
            jtCodigo.setText(cod);
        }

        jtTelefono.setText(despacho.getStrTelefono());
        jtNombre.setText(despacho.getStrNombre());
        jtDireccion.setText(despacho.getStrDireccion());
        jtBarrio.setText(despacho.getStrBarrio());
        jtNota.setText(despacho.getStrNota());
        String sql = "SELECT NUM_CASA_CLI, INFOR_ADICIONAL,LATITUD,LONGITUD FROM CLIENTES WHERE CODIGO='" + despacho.getIntCodigo() + "'";
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
            System.err.println("No hay datos en el resulSet... Clase -> VentanaDatos.java :-)");
            //Logger.getLogger(VentanaDatos.class.getName()).log(Level.SEVERE, null, ex);
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
        jbSalir.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpDatos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtCodigo = new javax.swing.JTextField();
        jbAceptar = new javax.swing.JButton();
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
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jtLatitud = new javax.swing.JTextField();
        jtLongitud = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtReferencia = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtNota = new javax.swing.JTextArea();
        jbCodigo = new javax.swing.JButton();
        jbSalir = new javax.swing.JButton();

        setTitle("Clientes");
        setBackground(java.awt.Color.white);
        setIconImage(null);
        setResizable(false);

        jLabel1.setText("Código:");

        jtCodigo.setFont(new java.awt.Font("Arial", 1, 24));
        jtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtCodigo.setEnabled(false);

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/ok.png"))); // NOI18N
        jbAceptar.setText("Aceptar");
        jbAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAceptarActionPerformed(evt);
            }
        });

        jLabel2.setText("Nombre:");

        jLabel3.setText("Dirección:");

        jLabel4.setText("# Casa:");

        jLabel6.setText("Teléfono:");

        jLabel9.setText("Barrio:");

        jtTelefono.setFont(new java.awt.Font("Arial", 1, 24));
        jtTelefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jtTelefono.setEnabled(false);

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

        jLabel7.setText("Latitud:");

        jLabel8.setText("Longitud:");

        jtLatitud.setText("0.0");

        jtLongitud.setText("0.0");

        jLabel5.setText("Referencia:");

        jtReferencia.setColumns(20);
        jtReferencia.setRows(5);
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

        jLabel11.setText("Nota:");

        jtNota.setColumns(20);
        jtNota.setRows(5);
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
                .addContainerGap(499, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
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
                    .addComponent(jLabel2)
                    .addGroup(jpDatosLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(28, 28, 28)
                        .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDatosLayout.createSequentialGroup()
                                .addComponent(jtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel6)
                                .addGap(6, 6, 6)
                                .addComponent(jtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(jpDatosLayout.createSequentialGroup()
                        .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpDatosLayout.createSequentialGroup()
                                .addComponent(jtNumeroCasa, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9)
                                .addGap(23, 23, 23)
                                .addComponent(jtBarrio, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jpDatosLayout.createSequentialGroup()
                        .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpDatosLayout.createSequentialGroup()
                                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jbAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jpDatosLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(26, 26, 26)
                                        .addComponent(jtLatitud, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jpDatosLayout.createSequentialGroup()
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6)
                                        .addComponent(jtLongitud, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))
                                    .addComponent(jbSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtLatitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jtLongitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jpDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jbSalir))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dialogSize = getSize();
        setLocation((screenSize.width-dialogSize.width)/2,(screenSize.height-dialogSize.height)/2);
    }// </editor-fold>//GEN-END:initComponents

    private void jbAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAceptarActionPerformed
        if (jtCodigo.getText().equals("") || jtCodigo.getText() == null) {
            this.setVisible(false);
        } else {
            GuardarDatos();
        }
    }//GEN-LAST:event_jbAceptarActionPerformed

    /**
     * Guarda los datos de nuevos o editados del cliente en la base de datos
     */
    private void GuardarDatos() {
        boolean resultado = false;
        datos = getDatosNuevos();
        if (!datos.getStrNombre().equals("") && !datos.getStrDireccion().equals("")) {
            if (accion) {
                resultado = bd.InsertarCliente(datos);
                if (!resultado) {
                    JOptionPane.showMessageDialog(this, "No se pudo guardar el cliente...", "Error", 0);
                }
            } else {
                resultado = bd.ActualizarCliente(datos, datos.getIntCodigo());
                if (!resultado) {
                    JOptionPane.showMessageDialog(this, "No se pudo actualizar el cliente...", "Error", 0);
                }
            }
            bd.CerrarConexion();
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
            d.setIntCodigo(Integer.parseInt(jtCodigo.getText()));
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

    private void jbCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCodigoActionPerformed
        if (jtCodigo.getText() == null || jtCodigo.getText().equals("")) {
            try {
                int cod = Integer.parseInt(bd.generarCodigo()) + 1;
                jtCodigo.setText("" + cod);
                insertarDatosTabla(jtCodigo.getText(), 2);
            } catch (SQLException ex) {
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
        insertarDatosTabla(jtDireccion.getText(), 4);
    }//GEN-LAST:event_jtDireccionFocusLost

    private void jtBarrioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtBarrioFocusLost
        jtBarrio.setText(Mayusculas(jtBarrio.getText()));
        insertarDatosTabla(jtBarrio.getText(), 5);
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
        bd.CerrarConexion();
        LimpiarCampos();
        this.setVisible(false);
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

    /**
     * Convierete a mayusculas lo que se envie
     * @param txt
     * @return string
     */
    public String Mayusculas(String txt) {
        return txt.toUpperCase();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new VentanaDatos().setVisible(true);
            }
        });
    }
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
    private javax.swing.JPanel jpDatos;
    private javax.swing.JTextField jtBarrio;
    private javax.swing.JTextField jtCodigo;
    private javax.swing.JTextField jtDireccion;
    private javax.swing.JTextField jtLatitud;
    private javax.swing.JTextField jtLongitud;
    private javax.swing.JTextField jtNombre;
    private javax.swing.JTextArea jtNota;
    private javax.swing.JTextField jtNumeroCasa;
    private javax.swing.JTextArea jtReferencia;
    private javax.swing.JTextField jtTelefono;
    // End of variables declaration//GEN-END:variables
}
