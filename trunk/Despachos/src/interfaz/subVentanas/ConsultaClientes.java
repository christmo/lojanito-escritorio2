/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ConsultaClientes.java
 *
 * Created on 01/09/2010, 10:11:54 AM
 */
package interfaz.subVentanas;

import BaseDatos.ConexionBase;
import interfaz.Principal;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author root
 */
public class ConsultaClientes extends javax.swing.JDialog {

    private ConexionBase bd;
    private ArrayList<Clientes> listaClientes = new ArrayList<Clientes>();
    private static DefaultTableModel dtm;

    /** Creates new form ConsultaClientes */
    public ConsultaClientes() {
        initComponents();
        configuracionInicial();
    }

    public ConsultaClientes(ConexionBase cb) {
        initComponents();
        this.bd = cb;
        this.setVisible(true);
        configuracionInicial();
    }

    private void configuracionInicial() {
        jtNombreCliente.requestFocus();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jtDespachar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtBusqueda = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jtNombreCliente = new javax.swing.JTextField();
        jbBuscar = new javax.swing.JButton();
        jbCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Busqueda de Clientes");

        jtDespachar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/up.png"))); // NOI18N
        jtDespachar.setText("Despachar");
        jtDespachar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtDespacharActionPerformed(evt);
            }
        });

        jtBusqueda.setAutoCreateRowSorter(true);
        jtBusqueda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Teléfono", "Código", "Nombre", "Dirección", "Barrio", "# Casa", "Nota"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtBusqueda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jtBusqueda.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jtBusqueda);
        jtBusqueda.getColumnModel().getColumn(0).setMinWidth(75);
        jtBusqueda.getColumnModel().getColumn(0).setMaxWidth(75);
        jtBusqueda.getColumnModel().getColumn(1).setMinWidth(50);
        jtBusqueda.getColumnModel().getColumn(1).setMaxWidth(50);
        jtBusqueda.getColumnModel().getColumn(2).setPreferredWidth(150);
        jtBusqueda.getColumnModel().getColumn(3).setPreferredWidth(250);
        jtBusqueda.getColumnModel().getColumn(4).setMinWidth(75);
        jtBusqueda.getColumnModel().getColumn(4).setMaxWidth(75);
        jtBusqueda.getColumnModel().getColumn(5).setMinWidth(60);
        jtBusqueda.getColumnModel().getColumn(5).setMaxWidth(60);
        jtBusqueda.getColumnModel().getColumn(6).setMinWidth(100);
        jtBusqueda.getColumnModel().getColumn(6).setMaxWidth(100);

        jLabel1.setText("Nombre:");

        jtNombreCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtNombreClienteActionPerformed(evt);
            }
        });
        jtNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtNombreClienteKeyPressed(evt);
            }
        });

        jbBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/buscar.png"))); // NOI18N
        jbBuscar.setText("Buscar");
        jbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbBuscarActionPerformed(evt);
            }
        });

        jbCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/interfaz/iconos/salir.png"))); // NOI18N
        jbCancelar.setText("Cancelar");
        jbCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 133, Short.MAX_VALUE)
                        .addComponent(jbCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtDespachar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jtDespachar, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jbCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-1024)/2, (screenSize.height-294)/2, 1024, 294);
    }// </editor-fold>//GEN-END:initComponents

    private void jtNombreClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtNombreClienteActionPerformed
        String nombre = jtNombreCliente.getText();
        jtNombreCliente.setText(nombre.toUpperCase());
        ConsultarClienteNombre(nombre);
    }//GEN-LAST:event_jtNombreClienteActionPerformed

    private void jbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbBuscarActionPerformed
        String nombre = jtNombreCliente.getText();
        jtNombreCliente.setText(nombre.toUpperCase());
        ConsultarClienteNombre(nombre);
    }//GEN-LAST:event_jbBuscarActionPerformed

    private void jtNombreClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtNombreClienteKeyPressed
        int cod = evt.getKeyCode();
        if (cod == 27) {
            this.dispose();
        }
    }//GEN-LAST:event_jtNombreClienteKeyPressed

    private void jtDespacharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtDespacharActionPerformed
        try {
            int fila = jtBusqueda.getRowCount() - (jtBusqueda.getSelectedRow() + 1);
            Clientes c = listaClientes.get(fila);
            Principal.ingresarClientePorDespachar(c);
            this.dispose();
        } catch (IndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente para despacharlo...", "Error...", 0);
        }
    }//GEN-LAST:event_jtDespacharActionPerformed

    private void jbCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_jbCancelarActionPerformed

    /**
     * Llena la tabla de los clientes buscados
     * @param nombre
     */
    private void ConsultarClienteNombre(String nombre) {
        limpiarTablaBusqueda();
        try {
            listaClientes = bd.buscarClientesPorNombre(nombre);
            dtm = (DefaultTableModel) jtBusqueda.getModel();

            for (Clientes c : listaClientes) {
                String[] datos = {
                    c.getTelefono(),
                    c.getCodigo(),
                    c.getNombre(),
                    c.getDireccion(),
                    c.getBarrio(),
                    c.getN_casa(),
                    c.getReferencia()
                };
                dtm.insertRow(0, datos);
            }
        } catch (NullPointerException nex) {
        }
    }

    /**
     * Limpia las filas de la tabla
     */
    private void limpiarTablaBusqueda() {
        listaClientes.clear();
        dtm = (DefaultTableModel) jtBusqueda.getModel();
        int n_filas = jtBusqueda.getRowCount();
        for (int i = 0; i < n_filas; i++) {
            dtm.removeRow(0);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton jbBuscar;
    private javax.swing.JButton jbCancelar;
    private static javax.swing.JTable jtBusqueda;
    private javax.swing.JButton jtDespachar;
    private javax.swing.JTextField jtNombreCliente;
    // End of variables declaration//GEN-END:variables
}
