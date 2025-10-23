package Ventanas;
import controlador.ProductoController;
import modelo.Alimento;
import modelo.Tecnologia;
import modelo.Producto;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ActualizarProducto extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ActualizarProducto.class.getName());
    private Administrador adminView; 
    private Producto productoAEditar; 

    
    public ActualizarProducto() {
        initComponents();
        this.setTitle("Actualizar Producto");
        this.setLocationRelativeTo(null);
        establecerEstadoCamposEdicion(false);
    }
    
    public ActualizarProducto(Administrador adminView) {
        this();
        this.adminView = adminView;
        this.setLocationRelativeTo(adminView);
    }


    /**
     * Habilita o deshabilita los campos de edición y los botones de acción.
     * @param habilitado True para habilitar campos de edición, False para buscar.
     */
    private void establecerEstadoCamposEdicion(boolean habilitado) {
        jTextField2.setEditable(habilitado); 
        jTextField3.setEditable(habilitado); 
        
        jButton2.setEnabled(habilitado); // Botón Actualizar
        
        jTextField1.setEditable(!habilitado); 
        jButton1.setEnabled(!habilitado); // Botón Buscar

        if (!habilitado) {
            jTextField1.setText("");
            jTextField2.setText("");
            jTextField3.setText("");
            jLabel4.setText("Atributo Único:");
            productoAEditar = null; 
        }
    }
    

    /**
     * Carga la información del producto encontrado en los campos de texto.
     * @param producto El objeto Producto a editar.
     */
    private void cargarProductoEnCampos(Producto producto) {
        if (producto == null) return;
        
        jTextField1.setText(String.valueOf(producto.getCodigo()));
        jTextField2.setText(producto.getNombre());
        
        if (producto instanceof Tecnologia) {
            Tecnologia t = (Tecnologia) producto;
            jTextField3.setText(String.valueOf(t.getMesesGarantia()));
            jLabel4.setText("Meses Garantía (int):");
            jTextField3.setEditable(true); 
        } else if (producto instanceof Alimento) {
            Alimento a = (Alimento) producto;
            jTextField3.setText(a.getFechaCaducidad());
            jLabel4.setText("Fecha Caducidad (dd/mm/aaaa):");
            jTextField3.setEditable(true); 
        } else { 
            jTextField3.setText("");
            jTextField3.setEditable(false); // Inhabilita el campo
            jLabel4.setText("Atributo Único: N/A");
        }
        
        establecerEstadoCamposEdicion(true);
    }
                                                                                                                                                 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Actualizar Producto");

        jLabel2.setText("Codigo ");

        jLabel3.setText("Nombre");

        jLabel4.setText("Atributo Unico");

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Actualizar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Cancelar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(39, 39, 39)
                                .addComponent(jTextField3))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(73, 73, 73))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton1))
                                    .addComponent(jTextField2))))
                        .addGap(75, 75, 75)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(jButton2)
                .addGap(31, 31, 31)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String codigoStr = jTextField1.getText().trim();
    if (codigoStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Debe ingresar el código del producto a buscar.", "Error de Búsqueda", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    productoAEditar = ProductoController.buscarProducto(codigoStr);
    
    if (productoAEditar != null) {
        cargarProductoEnCampos(productoAEditar);
        JOptionPane.showMessageDialog(this, "Producto encontrado. Ya puede editar el Nombre y el Atributo Único.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, "No se encontró ningún producto con el código: " + codigoStr, "No Encontrado", JOptionPane.WARNING_MESSAGE);
        establecerEstadoCamposEdicion(false);
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (productoAEditar == null) {
            JOptionPane.showMessageDialog(this, "Primero debe buscar y cargar un producto válido.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // 1. Obtener Nuevos Valores
    String nuevoNombre = jTextField2.getText().trim();
    String nuevoAtributo = jTextField3.getText().trim(); 

    if (nuevoNombre.isEmpty()) {
        JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Validación", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    productoAEditar.setNombre(nuevoNombre);
    
    if (productoAEditar instanceof Tecnologia) {
        if (nuevoAtributo.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Los meses de garantía no pueden estar vacíos.", "Validación", JOptionPane.WARNING_MESSAGE);
             return;
        }
        try {
            int nuevaGarantia = Integer.parseInt(nuevoAtributo);
            ((Tecnologia) productoAEditar).setMesesGarantia(nuevaGarantia);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Los meses de garantía deben ser un número entero válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }
    } else if (productoAEditar instanceof Alimento) {
        if (nuevoAtributo.isEmpty()) {
             JOptionPane.showMessageDialog(this, "La fecha de caducidad no puede estar vacía.", "Validación", JOptionPane.WARNING_MESSAGE);
             return;
        }
        ((Alimento) productoAEditar).setFechaCaducidad(nuevoAtributo);
    } 
    
    ProductoController.guardarProductos();
    
    if (adminView != null) {
        adminView.actualizarTablaProductos();
    }
    
    JOptionPane.showMessageDialog(this, "Producto " + productoAEditar.getCodigo() + " actualizado exitosamente.");
    
    this.dispose(); 
    establecerEstadoCamposEdicion(false);

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new ActualizarProducto().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    // End of variables declaration//GEN-END:variables
}