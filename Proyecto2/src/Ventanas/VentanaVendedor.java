package Ventanas;
import modelo.ButtonColumn2;
import controlador.HistorialController; 
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import modelo.Vendedor;
import modelo.Producto;
import controlador.ProductoController;
import modelo.Cliente;
import controlador.ClienteController;
import java.util.logging.Logger;
import Ventanas.VentanaVendedor;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.File;
import modelo.Pedido; 
import modelo.ButtonColumn3;
import controlador.PedidoController; 
import java.time.format.DateTimeFormatter;


public class VentanaVendedor extends javax.swing.JFrame {
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(VentanaVendedor.class.getName());

    private Vendedor vendedorActual;

    public VentanaVendedor(Vendedor vendedor) {
        initComponents(); 
        this.vendedorActual = vendedor;
        this.setLocationRelativeTo(null);
        setTitle("Panel del Vendedor - " + vendedor.getNombre());
        controlador.UsuarioController.cargarUsuarios();
        
        ProductoController.cargarProductos(); 
        PedidoController.cargarPedidos(); 
        
        actualizarTablaProductos(); 
        actualizarTablaClientes(); 
        actualizarTablaPedidos();
    }
    

    public VentanaVendedor() {
        this(new Vendedor("TEMP001", "1234", "Temporal", "Otro"));
    }

    public void actualizarTablaProductos() {
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); 

        Producto[] productos = ProductoController.obtenerProductos();

        if (productos == null || productos.length == 0) {
            System.out.println("No hay productos cargados.");
            return;
        }

        for (Producto p : productos) {
            if (p != null) {
                model.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNombre(),
                    p.getCategoria(),
                    p.getStock(),
                    "Ver historial" 
                });
            }
        }

        int columnaBoton = model.findColumn("Acciones");
        new ButtonColumn2(jTable1, columnaBoton, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int fila = Integer.parseInt(e.getActionCommand());
                String codigoProducto = model.getValueAt(fila, 0).toString();
                mostrarHistorial(codigoProducto);
            }
        });
    }

    private void mostrarHistorial(String codigoProducto) {
        String historial = HistorialController.obtenerHistorial(codigoProducto);

        JOptionPane.showMessageDialog(this,
                historial,
                "Historial de ingresos - Producto " + codigoProducto,
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void actualizarTablaClientes() {
    DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
    model.setRowCount(0);

    // ⭐ 1. Llamar al controlador de Usuarios (donde reside el array estático)
    // Asegúrate de que esta línea esté usando 'controlador.UsuarioController'
    modelo.Cliente[] clientes = controlador.UsuarioController.obtenerClientes(); 

    if (clientes == null || clientes.length == 0) {
        System.out.println("No hay clientes cargados.");
        return;
    }

    for (modelo.Cliente c : clientes) {
        if (c != null) {
            model.addRow(new Object[]{
                c.getCodigo(),
                c.getNombre(),
                c.getGenero(),      
                c.getCumpleanos()   
            });
        }
    }
}
            
        public void actualizarTablaPedidos() {
            DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
            model.setRowCount(0); 
    
            Pedido[] todosLosPedidos = PedidoController.obtenerPedidosActivos();
            DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                if (todosLosPedidos == null) return;
    
            for (Pedido p : todosLosPedidos) {
                if (p != null && p.getEstado().equals("Pendiente")) {
                    Cliente cliente = ClienteController.buscarClientePorCodigo(p.getCodigoCliente());
                    String nombreCliente = (cliente != null) ? cliente.getNombre() : "Desconocido";

            model.addRow(new Object[]{
                p.getIdPedido(),
                p.getFechaCreacion().format(displayFormatter),
                p.getCodigoCliente(),
                nombreCliente,
                String.format("%.2f", p.getTotal()), 
                "Confirmar" 
            });
        }
    }
    
    int columnaBoton = model.findColumn("Opciones");
    new ButtonColumn3(jTable3, columnaBoton, new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            int fila = Integer.parseInt(e.getActionCommand());
            String idPedido = model.getValueAt(fila, 0).toString();
            confirmarPedido(idPedido); 
        }
    });
}

        private void confirmarPedido(String idPedido) {
            Pedido pedido = PedidoController.buscarPedidoPorId(idPedido);
    
        if (pedido == null || !pedido.getEstado().equals("Pendiente")) {
            JOptionPane.showMessageDialog(this, "Error: Pedido no encontrado o ya ha sido procesado.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    

        String descripcionItems = pedido.getDescripcionItems();
            if (!ProductoController.verificarStockParaPedido(descripcionItems)) {
        JOptionPane.showMessageDialog(this, 
            "STOCK INSUFICIENTE. El pedido no puede ser confirmado.", 
            "Fallo de Stock", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    ProductoController.descontarStockParaPedido(descripcionItems);
    
    if (PedidoController.actualizarEstadoPedido(idPedido, "Confirmado")) {
        
        vendedorActual.aumentarVentas(); 
        
        JOptionPane.showMessageDialog(this, 
            "Pedido " + idPedido + " CONFIRMADO con éxito.\nVentas confirmadas por usted: " + vendedorActual.getVentasConfirmadas(), 
            "Pedido Confirmado", JOptionPane.INFORMATION_MESSAGE);
            
        actualizarTablaPedidos(); 
        actualizarTablaProductos(); 
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el estado del pedido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }  
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Código", "Nombre", "Categoría", "Stock", "Acciones"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton2.setText("Cargar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setText("Agregar Stock");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(55, 55, 55))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(38, 38, 38))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(55, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(87, 87, 87)
                .addComponent(jButton2)
                .addGap(185, 185, 185))
        );

        jTabbedPane1.addTab("Productos", jPanel1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "Genero", "Fecha de Cumpleaños"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton3.setText("Crear");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Cargar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Actualizar");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Eliminar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(44, 44, 44))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 393, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jButton3)
                        .addGap(42, 42, 42)
                        .addComponent(jButton4)
                        .addGap(47, 47, 47)
                        .addComponent(jButton5)
                        .addGap(47, 47, 47)
                        .addComponent(jButton6)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Clientes", jPanel2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Fecha de Generacion", "Codigo Cliente", "Nombre Cliente", "Total", "Opciones"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pedidos", jPanel3);

        jButton7.setText("Cerrar Sesion");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(319, 319, 319)
                        .addComponent(jButton7)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton7)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
    fileChooser.setDialogTitle("Seleccionar Archivo CSV para Cargar Stock");
    
    javax.swing.filechooser.FileNameExtensionFilter filter = 
        new javax.swing.filechooser.FileNameExtensionFilter("Archivos de Stock CSV", "csv");
    fileChooser.setFileFilter(filter);

    int userSelection = fileChooser.showOpenDialog(this);

    if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
        java.io.File fileToLoad = fileChooser.getSelectedFile();
        String rutaArchivo = fileToLoad.getAbsolutePath();
        
        int cargados = controlador.ProductoController.cargarStockMasivoCSV(rutaArchivo);
        
        if (cargados > 0) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                cargados + " producto(s) con stock actualizado exitosamente.", 
                "Carga Masiva Exitosa", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            
            actualizarTablaProductos(); 
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "No se actualizó el stock de ningún producto. Revise el archivo CSV o la consola para errores.", 
                "Advertencia de Carga", javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }
    

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Ventanas.AgregarStock agregarStock = new Ventanas.AgregarStock(this, vendedorActual); 
        agregarStock.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        EliminarCliente ventanaEliminar = new EliminarCliente(this);
        ventanaEliminar.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
    CrearCliente ventanaCrear = new CrearCliente(this); 
    ventanaCrear.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
    javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
    fileChooser.setDialogTitle("Seleccionar Archivo CSV de Clientes para Carga Masiva");
    
    javax.swing.filechooser.FileNameExtensionFilter filter = 
        new javax.swing.filechooser.FileNameExtensionFilter("Archivos CSV", "csv");
    fileChooser.setFileFilter(filter);

    int userSelection = fileChooser.showOpenDialog(this);

    if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
        java.io.File fileToLoad = fileChooser.getSelectedFile();
        String rutaArchivo = fileToLoad.getAbsolutePath();
        
        try {
            int cargados = controlador.UsuarioController.cargarClientesMasivoCSV(rutaArchivo);
            
            if (cargados > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, 
                    cargados + " cliente(s) cargado(s) exitosamente desde CSV.", 
                    "Carga Masiva Exitosa", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                
                actualizarTablaClientes(); 
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, 
                    "No se cargó ningún cliente. Revise el formato del archivo.", 
                    "Advertencia de Carga", javax.swing.JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error al procesar el archivo CSV de Clientes.", e);
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Ocurrió un error inesperado al procesar el archivo. Verifique la consola.", 
                "Error Crítico de Carga", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        ActualizarCliente ventanaActualizar = new ActualizarCliente(this);
        ventanaActualizar.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        int confirmacion = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro que desea cerrar la sesión?", 
            "Confirmar Cierre de Sesión", 
            JOptionPane.YES_NO_OPTION
    );
    
    if (confirmacion == JOptionPane.YES_OPTION) {
        this.dispose(); 
      
        Ventanas.Login login = new Ventanas.Login(); 
        login.setVisible(true);
    }
    }//GEN-LAST:event_jButton7ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new VentanaVendedor().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    // End of variables declaration//GEN-END:variables
}