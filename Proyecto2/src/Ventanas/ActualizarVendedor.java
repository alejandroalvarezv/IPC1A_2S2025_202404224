package Ventanas;
import controlador.UsuarioController;
import modelo.Vendedor;
import javax.swing.JOptionPane;
import modelo.Usuario;
public class ActualizarVendedor extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ActualizarVendedor.class.getName());

    private Administrador adminView;
    private Vendedor vendedorAEditar;     
    
    
    public ActualizarVendedor() {
        initComponents();
        establecerEstadoCampos(false);
    }
    
    public ActualizarVendedor(Administrador adminView) {
        this(); 
        this.adminView = adminView;
        this.setLocationRelativeTo(adminView);
    }
    // -------------------------------------------------------------------------
    // LÓGICA DE CONTROL (Métodos Auxiliares)
    // -------------------------------------------------------------------------

    private void establecerEstadoCampos(boolean habilitado) {
        Nombre.setEditable(habilitado);
        Contrasena.setEditable(habilitado);
        Actualizar.setEnabled(habilitado);
        
        if (!habilitado) {
            Nombre.setText("");
            Contrasena.setText("");
        }
    }

    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        CodigoBuscar = new javax.swing.JTextField();
        Nombre = new javax.swing.JTextField();
        Contrasena = new javax.swing.JTextField();
        Buscar = new javax.swing.JButton();
        Actualizar = new javax.swing.JButton();
        Cancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Actualizar Vendedor");

        jLabel2.setText("Código");

        jLabel3.setText("Nombre");

        jLabel4.setText("Contraseña");

        Contrasena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContrasenaActionPerformed(evt);
            }
        });

        Buscar.setText("Buscar");
        Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarActionPerformed(evt);
            }
        });

        Actualizar.setText("Actualizar");
        Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarActionPerformed(evt);
            }
        });

        Cancelar.setText("Cancelar");
        Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(144, 144, 144))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(35, 35, 35)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(CodigoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Nombre)
                            .addComponent(Contrasena)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(Actualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Cancelar)))
                .addContainerGap(101, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel1)
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(CodigoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Buscar))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(Contrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Actualizar)
                    .addComponent(Cancelar))
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarActionPerformed
        if (vendedorAEditar == null) {
            JOptionPane.showMessageDialog(this, "Primero debe buscar y cargar un vendedor válido.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // 2. Obtener y Validar Nuevos Valores
    String nuevoNombre = Nombre.getText().trim();
    String nuevaContrasena = Contrasena.getText().trim(); 

    if (nuevoNombre.isEmpty() || nuevaContrasena.isEmpty()) {
        JOptionPane.showMessageDialog(this, "El nombre y la contraseña no pueden estar vacíos.", "Validación", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // 3. Actualizar el objeto Vendedor en memoria
    vendedorAEditar.setNombre(nuevoNombre);
    vendedorAEditar.setContrasena(nuevaContrasena);
    
    // 4. Persistir los cambios (Guardar toda la lista al CSV)
    UsuarioController.guardarUsuarios();
    
    // 5. Notificar a la ventana principal para refrescar la tabla
    if (adminView != null) {
        adminView.actualizarTablaVendedores();
    }
    
    JOptionPane.showMessageDialog(this, "Vendedor " + vendedorAEditar.getCodigo() + " actualizado exitosamente.");
    
    this.dispose(); 

    }//GEN-LAST:event_ActualizarActionPerformed

    private void CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarActionPerformed
        this.dispose(); 
    }//GEN-LAST:event_CancelarActionPerformed

    private void ContrasenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContrasenaActionPerformed

    }//GEN-LAST:event_ContrasenaActionPerformed

    private void BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarActionPerformed
        String codigo = CodigoBuscar.getText().trim();
    
            if (codigo.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Ingrese el código del vendedor.", "Validación", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // 1. Buscar el usuario
    Usuario u = UsuarioController.buscarUsuarioPorCodigo(codigo);
    
    // 2. Validar que exista y sea un Vendedor
    if (u != null && u instanceof Vendedor) {
        this.vendedorAEditar = (Vendedor) u;
        
        Nombre.setText(vendedorAEditar.getNombre());
        Contrasena.setText(vendedorAEditar.getContrasena());
        establecerEstadoCampos(true);
        CodigoBuscar.setEditable(false);
        
    } else {
        establecerEstadoCampos(false);
        this.vendedorAEditar = null;
        JOptionPane.showMessageDialog(this, "Vendedor con código " + codigo + " no encontrado.", "Error de Búsqueda", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_BuscarActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new ActualizarVendedor().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Actualizar;
    private javax.swing.JButton Buscar;
    private javax.swing.JButton Cancelar;
    private javax.swing.JTextField CodigoBuscar;
    private javax.swing.JTextField Contrasena;
    private javax.swing.JTextField Nombre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}