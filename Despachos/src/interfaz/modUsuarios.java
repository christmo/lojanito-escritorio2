/*
 * modUsuarios.java
 *
 * Created on 25/08/2010, 09:06:26 AM
 */
package interfaz;

import BaseDatos.ConexionBase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import login.LoginGUI;

/**
 * @author christmo
 */
public class modUsuarios extends javax.swing.JDialog {

    private ConexionBase bd;
    private ResultSet rs;
    private String[] usuarios;
    private funcionesUtilidad fun = new funcionesUtilidad();

    /** Creates new form modTurnos */
    public modUsuarios(JFrame padre, ConexionBase conec) {
        super(padre, "Crear Modificar Usuarios");
        this.bd = conec;
        super.setIconImage(new ImageIcon(getClass().getResource("/interfaz/iconos/kradac_icono.png")).getImage());

        initComponents();
        this.usuarios = CargarUsuarios();
        CargarTurnos();
    }

    /**
     * Codigo generado por Netbeans
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jtNombre = new javax.swing.JTextField();
        jtDireccion = new javax.swing.JTextField();
        jtTelefono = new javax.swing.JTextField();
        jtUsuario = new javax.swing.JTextField();
        jpClave = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        jcTurnos = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jcEstado = new javax.swing.JComboBox();
        jcOperador = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jtCedula = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jlUsuarios = new javax.swing.JList();
        jbLimpiarCampos = new javax.swing.JButton();
        jbNuevoUsuario = new javax.swing.JButton();
        jbAceptar = new javax.swing.JButton();
        jbEliminar = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("USUARIOS");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos del Usuario"));

        jLabel3.setText("Nombre:");

        jLabel4.setText("Dirección:");

        jLabel5.setText("Teléfono:");

        jLabel6.setText("Usuario:");

        jLabel7.setText("Clave:");

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

        jtTelefono.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtTelefonoFocusLost(evt);
            }
        });

        jtUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtUsuarioFocusLost(evt);
            }
        });

        jpClave.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jpClaveFocusGained(evt);
            }
        });

        jLabel8.setText("Turnos:");

        jcTurnos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar Turno" }));

        jLabel9.setText("Estado:");

        jLabel10.setText("Rol:");

        jcEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "Inactivo" }));

        jcOperador.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Operador", "Solo Lectura", "Administrador", "Super" }));

        jLabel2.setText("Cédula:");

        jtCedula.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtCedulaFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(jtDireccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(jtUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(jpClave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(jcTurnos, javax.swing.GroupLayout.Alignment.TRAILING, 0, 270, Short.MAX_VALUE)
                    .addComponent(jcEstado, 0, 270, Short.MAX_VALUE)
                    .addComponent(jcOperador, 0, 270, Short.MAX_VALUE)
                    .addComponent(jtTelefono, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                    .addComponent(jtCedula, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtCedula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jpClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jcEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jcOperador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Lista de Usuarios"));

        jlUsuarios.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jlUsuarios.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jlUsuariosValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jlUsuarios);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                .addContainerGap())
        );

        jbLimpiarCampos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/limpiar.png"))); // NOI18N
        jbLimpiarCampos.setToolTipText("Limpia los campos de texto...");
        jbLimpiarCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbLimpiarCamposActionPerformed(evt);
            }
        });

        jbNuevoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/guardar.png"))); // NOI18N
        jbNuevoUsuario.setText("GUARDAR");
        jbNuevoUsuario.setToolTipText("Añade un Usuario Nuevo, despues de haber ingresado los datos...");
        jbNuevoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbNuevoUsuarioActionPerformed(evt);
            }
        });

        jbAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/cancelar.png"))); // NOI18N
        jbAceptar.setText("CERRAR");
        jbAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbAceptarActionPerformed(evt);
            }
        });

        jbEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/eliminar.png"))); // NOI18N
        jbEliminar.setToolTipText("Elimina un usuario seleccionado...");
        jbEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 589, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jbAceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 239, Short.MAX_VALUE)
                        .addComponent(jbEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbLimpiarCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbNuevoUsuario)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jbNuevoUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                        .addComponent(jbLimpiarCampos, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                        .addComponent(jbEliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                    .addComponent(jbAceptar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbLimpiarCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbLimpiarCamposActionPerformed
        LimpiarCampos();
}//GEN-LAST:event_jbLimpiarCamposActionPerformed

    private void LimpiarCampos() {
        jtNombre.setText("");
        jtCedula.setText("");
        jtDireccion.setText("");
        jtTelefono.setText("");
        jtUsuario.setText("");
        jtUsuario.setEditable(true);
        jpClave.setText("");
        jcTurnos.setSelectedIndex(0);
        jcEstado.setSelectedIndex(0);
        jcOperador.setSelectedIndex(0);
        jlUsuarios.clearSelection();
    }

    private void jbNuevoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbNuevoUsuarioActionPerformed
        String nombre = jtNombre.getText();
        if (nombre.equals("")) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un nombre y apellido del usuario...", "Error...", 0);
        } else {
            String ci = jtCedula.getText();
            if (ci.equals("")) {
                JOptionPane.showMessageDialog(this, "Debe ingresar la cédula del usuario...", "Error...", 0);
            } else {
                String dir = jtDireccion.getText();
                if (dir.equals("")) {
                    JOptionPane.showMessageDialog(this, "Debe ingresar la dirección del usuario...", "Error...", 0);
                } else {
                    String tel = jtTelefono.getText();
                    if (tel.equals("")) {
                        JOptionPane.showMessageDialog(this, "Debe ingresar un teléfono del usuario...", "Error...", 0);
                    } else {
                        String user = jtUsuario.getText();
                        if (user.equals("")) {
                            JOptionPane.showMessageDialog(this, "Debe ingresar un nombre de usuario...", "Error...", 0);
                        } else {
                            char a[] = jpClave.getPassword();
                            String pass = "";
                            for (int i = 0; i < a.length; i++) {
                                pass += a[i];
                            }

                            if (pass.equals("")) {
                                JOptionPane.showMessageDialog(this, "Debe ingresar una clave para el usuario...", "Error...", 0);
                            } else {
                                if (jcTurnos.getSelectedIndex() == 0) {
                                    JOptionPane.showMessageDialog(this, "Debe seleccionar un turno para el usuario...", "Error...", 0);
                                } else {
                                    if (!usuarioIngresado(user)) {
                                        boolean r = bd.insertarUsuario(
                                                Principal.sesion[1],
                                                user,
                                                fun.encriptar(pass, LoginGUI.semillaPass),
                                                nombre,
                                                dir,
                                                tel,
                                                jcTurnos.getSelectedIndex(),
                                                jcEstado.getSelectedItem().toString(),
                                                jcOperador.getSelectedItem().toString(),
                                                ci);
                                        if (r) {
                                            Icon ic = new ImageIcon(getClass().getResource("/interfaz/iconos/correcto.png"));
                                            JOptionPane.showMessageDialog(this, "Datos guardados correctamente...", "Mensaje...", 1, ic);
                                            this.usuarios = CargarUsuarios();
                                            LimpiarCampos();
                                        } else {
                                            JOptionPane.showMessageDialog(this, "No pudo guardar el usuario...", "Error...", 0);
                                        }
                                    } else {
                                        String clave = bd.getClaveUsuario(user);
                                        if (!clave.equals(pass)) {
                                            clave = fun.encriptar(pass, LoginGUI.semillaPass);
                                        }
                                        boolean r = bd.actualizarUsuario(
                                                Principal.sesion[1],
                                                user,
                                                clave,
                                                nombre,
                                                dir,
                                                tel,
                                                jcTurnos.getSelectedIndex(),
                                                jcEstado.getSelectedItem().toString(),
                                                jcOperador.getSelectedItem().toString(),
                                                ci);
                                        if (r) {
                                            Icon ic = new ImageIcon(getClass().getResource("/interfaz/iconos/correcto.png"));
                                            JOptionPane.showMessageDialog(this, "Datos actualizados correctamente...", "Mensaje...", 1, ic);
                                            this.usuarios = CargarUsuarios();
                                            LimpiarCampos();
                                        } else {
                                            JOptionPane.showMessageDialog(this, "No se pudo actualizar los datos del usuario...", "Error...", 0);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
}//GEN-LAST:event_jbNuevoUsuarioActionPerformed

    private void jbAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbAceptarActionPerformed
        this.dispose();
}//GEN-LAST:event_jbAceptarActionPerformed

    private void jbEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbEliminarActionPerformed
        try {
            String usuario = jlUsuarios.getSelectedValue().toString();
            if (!usuario.equals("KRADAC") && !usuario.equals("KRC")) {
                int op = JOptionPane.showConfirmDialog(this, "Desea eliminar el usuario: " + usuario, "Mensaje...", JOptionPane.OK_CANCEL_OPTION);
                if (op == 0) {
                    EliminarUsuario(usuario);
                    LimpiarCampos();
                    this.usuarios = CargarUsuarios();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Al usuario KRADAC no se lo puede eliminar...", "Error...", 0);
                LimpiarCampos();
            }
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un usuario...", "Error...", 0);
        }
}//GEN-LAST:event_jbEliminarActionPerformed

    private void jlUsuariosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jlUsuariosValueChanged
        try {
            String usuario = jlUsuarios.getSelectedValue().toString();
            CargarDatosUsuario(usuario);
        } catch (NullPointerException ex) {
        }
    }//GEN-LAST:event_jlUsuariosValueChanged

    private void jtNombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtNombreFocusLost
        jtNombre.setText(jtNombre.getText().toUpperCase());
    }//GEN-LAST:event_jtNombreFocusLost

    private void jtCedulaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtCedulaFocusLost
        String ci = jtCedula.getText().toUpperCase();
        jtCedula.setText(ci);
        if (fun.isNumeric(ci)) {
            if (!fun.esCedulaValida(ci)) {
                JOptionPane.showMessageDialog(this, "Cédula no es válida...", "Error...", 0);
                jtCedula.setText("");
            }
        } else {
            jtCedula.setText("");
        }
    }//GEN-LAST:event_jtCedulaFocusLost

    private void jtDireccionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtDireccionFocusLost
        jtDireccion.setText(jtDireccion.getText().toUpperCase());
    }//GEN-LAST:event_jtDireccionFocusLost

    private void jtTelefonoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtTelefonoFocusLost
        String tel = fun.validarTelefono(jtTelefono.getText());
        if (!fun.isNumeric(tel)) {
            jtTelefono.setText("");
        } else {
            if (!fun.validarTel(tel)) {
                jtTelefono.setText("");
            } else {
                jtTelefono.setText(tel);
            }
        }
    }//GEN-LAST:event_jtTelefonoFocusLost

    private void jtUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtUsuarioFocusLost
        String usuario = jtUsuario.getText().toUpperCase().trim();

        if (jtUsuario.isEditable()) {
            if (!usuario.equals("")) {
                if (validarUsuario(usuario)) {
                    JOptionPane.showMessageDialog(this, "Este nombre de usuario ya está siendo utilizado por otra persona, por favor ingresar otro...", "Error...", 0);
                    jtUsuario.setText("");
                    jtUsuario.requestFocus();
                } else {
                    jtUsuario.setText(usuario);
                }
            }
        }
    }//GEN-LAST:event_jtUsuarioFocusLost

    private void jpClaveFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jpClaveFocusGained
        jpClave.setText("");
    }//GEN-LAST:event_jpClaveFocusGained

    private void CargarDatosUsuario(String usuario) {
        try {
            rs = bd.getDatosUsuario(usuario);
            jtNombre.setText(rs.getString(4));
            jtCedula.setText(rs.getString(10));
            jtDireccion.setText(rs.getString(5));
            jtTelefono.setText(rs.getString(6));
            jtUsuario.setText(rs.getString(2));
            jtUsuario.setEditable(false);
            jpClave.setText(rs.getString(3));
            try {
                jcTurnos.setSelectedIndex(Integer.parseInt(rs.getString(7)));
                jcEstado.setSelectedIndex(Integer.parseInt(rs.getString(8)));
                jcOperador.setSelectedIndex(Integer.parseInt(rs.getString(9)));
            } catch (NullPointerException ex) {
            } catch (NumberFormatException nfe) {
            } catch (IllegalArgumentException ex) {
            }
        } catch (SQLException ex) {
            Logger.getLogger(modUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Carga todos los usuario de la base de datos
     */
    private String[] CargarUsuarios() {
        String[] usuarios1 = bd.getUsuarios();
        jlUsuarios.setListData(usuarios1);
        return usuarios1;
    }

    /**
     * Carga todos los turnos de la base de datos
     */
    private void CargarTurnos() {
        String[] turnos = bd.getTurnos();
        for (int i = 0; i < turnos.length; i++) {
            jcTurnos.addItem(turnos[i]);

        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbAceptar;
    private javax.swing.JButton jbEliminar;
    private javax.swing.JButton jbLimpiarCampos;
    private javax.swing.JButton jbNuevoUsuario;
    private javax.swing.JComboBox jcEstado;
    private javax.swing.JComboBox jcOperador;
    private javax.swing.JComboBox jcTurnos;
    private javax.swing.JList jlUsuarios;
    private javax.swing.JPasswordField jpClave;
    private javax.swing.JTextField jtCedula;
    private javax.swing.JTextField jtDireccion;
    private javax.swing.JTextField jtNombre;
    private javax.swing.JTextField jtTelefono;
    private javax.swing.JTextField jtUsuario;
    // End of variables declaration//GEN-END:variables

    /**
     * Usuario esta en la lista para actualizar los datos
     * @param user
     * @return boolean
     */
    private boolean usuarioIngresado(String user) {
        for (int i = 0; i < usuarios.length; i++) {
            if (user.equals(usuarios[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Eliminar un usuario de la base de datos
     * @param usuario
     */
    private void EliminarUsuario(String usuario) {
        bd.eliminarUsuario(usuario);
    }

    /**
     * Valida si el usuario ya está ingresado en la base de datos
     * true si existe false si no esta.
     * @param usuario
     * @return
     */
    private boolean validarUsuario(String usuario) {
        return bd.validarNombreUsuario(usuario);
    }
}
