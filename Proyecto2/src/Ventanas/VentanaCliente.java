package Ventanas;

import modelo.ButtonColumn3;
import modelo.Cliente;
import modelo.Producto;
import modelo.Pedido;
import controlador.ProductoController;
import controlador.PedidoController;
import modelo.ProductoPrecio;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Component;
import java.awt.BorderLayout; 
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaCliente extends javax.swing.JFrame {
	
	private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VentanaCliente.class.getName());

	// --- VARIABLES DE INSTANCIA ---
	private Cliente clienteActual;
	private final String[] COLUMNAS_CARRITO_CON_BOTON = {"Código", "Nombre", "Cantidad", "Precio U.", "Subtotal", "Eliminar"};	
	private final String[] COLUMNAS_HISTORIAL = {"ID Pedido", "Fecha", "Total", "Estado"};	
	private JTable jTableCarrito; 
	private JTable jTableHistorial; 
	private JLabel lblTotalCarrito; 
	private JButton btnConfirmarCompra;

	
	public VentanaCliente() {
		this(new Cliente("C999", "1234", "Invitado"));
	}
	
	public VentanaCliente(Cliente cliente) {
		this.clienteActual = cliente;
		initComponents();
		this.setLocationRelativeTo(null);
		setTitle("Módulo Cliente - " + cliente.getNombre());
		
		ProductoController.cargarProductos();
		PedidoController.cargarPedidos();
		
		prepararTablas();
	}
	
// ----------------------------------------------------------------------
	
	private void prepararTablas() {
		cargarTablaProductos();

		this.jTableCarrito = jTable1; // Asignar la tabla de Swing a tu referencia de lógica
		this.btnConfirmarCompra = jButton2; // Asignar el botón de Swing a tu referencia de lógica
		
		lblTotalCarrito = new JLabel("Total: $0.00");
		
		jPanel2.setLayout(new BorderLayout());
		
		jPanel2.add(jScrollPane1, BorderLayout.CENTER);
		
		JPanel panelPieCarrito = new JPanel(new BorderLayout());
		panelPieCarrito.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen
		panelPieCarrito.add(lblTotalCarrito, BorderLayout.WEST);
		panelPieCarrito.add(jButton2, BorderLayout.EAST);
		
		jPanel2.add(panelPieCarrito, BorderLayout.SOUTH);
		
		this.jTableHistorial = jTable3; // Asignar la tabla de Swing a tu referencia de lógica

		implementarBotonEliminarCarrito();
		cargarMisPedidos();
	}

        
        public void cargarTablaProductos() {
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            String[] nombresColumnas = {"Codigo", "Nombre", "Categoria", "Stock", "Acciones"};
		if (model.getColumnCount() != nombresColumnas.length) {
			model.setColumnIdentifiers(nombresColumnas);
		}
		
		model.setRowCount(0);	

		Producto[] productos = ProductoController.obtenerProductos();

		if (productos == null) return;
		
		for (Producto p : productos) {
			if (p != null) {
				model.addRow(new Object[]{
					p.getCodigo(),
					p.getNombre(),
					p.getCategoria(),
					p.getStock(),
					"Agregar al carrito" 
				});
			}
		}

	int columnaBoton = model.findColumn("Acciones");
		
            if (columnaBoton != -1) {
		new ButtonColumn3(jTable2, columnaBoton, new ActionListener() {
		@Override
	
                
        public void actionPerformed(ActionEvent e) {
            int filaModelo = Integer.parseInt(e.getActionCommand());
				
                try {
            String codigo = model.getValueAt(filaModelo, 0).toString();
            Producto productoBase = ProductoController.buscarProductoPorCodigo(codigo);
                if (productoBase != null && productoBase instanceof ProductoPrecio) {
                    ProductoPrecio productoConPrecio = (ProductoPrecio) productoBase;

                    ComprarCarrito ventanaCompra = new ComprarCarrito(productoConPrecio, VentanaCliente.this);
                    ventanaCompra.setVisible(true);

                } else {
            JOptionPane.showMessageDialog(null, "Error: Producto no encontrado o sin precio.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
        logger.log(java.util.logging.Level.SEVERE, "Error al procesar el clic del botón", ex);
                }
		}
	});
		}
	}

 
	public void agregarProductoAlCarritoDesdeVentana(ProductoPrecio p, int cantidad) {
		
		if (jTableCarrito == null) return;
		
		DefaultTableModel carritoModel = (DefaultTableModel) jTableCarrito.getModel();
		double precioUnitario = p.getPrecio();
		double subtotal = precioUnitario * cantidad;

		boolean productoEncontrado = false;
		
		for (int i = 0; i < carritoModel.getRowCount(); i++) {
			Object codigoActualObj = carritoModel.getValueAt(i, 0);	
			
			if (codigoActualObj != null && codigoActualObj.toString().equals(p.getCodigo())) {
				
				Object cantidadActualObj = carritoModel.getValueAt(i, 2);
				Object totalActualObj = carritoModel.getValueAt(i, 4);
				
				if(cantidadActualObj != null && totalActualObj != null){
					int cantidadActual = Integer.parseInt(cantidadActualObj.toString());
					double totalActual = Double.parseDouble(totalActualObj.toString().replace(",", "."));
					
					int nuevaCantidad = cantidadActual + cantidad;
					double nuevoTotal = totalActual + subtotal;
					
					carritoModel.setValueAt(nuevaCantidad, i, 2);	
					carritoModel.setValueAt(String.format("%.2f", nuevoTotal), i, 4);	
					productoEncontrado = true;
					break;
				}
			}
		}

		if (!productoEncontrado) {
			carritoModel.addRow(new Object[]{
				p.getCodigo(),
				p.getNombre(),
				cantidad,
				String.format("%.2f", precioUnitario),
				String.format("%.2f", subtotal),
				"Eliminar" 
			});
		}
		
		cargarTablaProductos();	
		actualizarTotalGeneralCarrito();
		JOptionPane.showMessageDialog(this, p.getNombre() + " agregado al carrito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	}


	private void implementarBotonEliminarCarrito() {
		if (jTableCarrito == null) return;
		
		DefaultTableModel carritoModel = (DefaultTableModel) jTableCarrito.getModel();
		carritoModel.setColumnIdentifiers(COLUMNAS_CARRITO_CON_BOTON);
		
		int columnaBotonEliminar = carritoModel.findColumn("Eliminar");	
		
		if (columnaBotonEliminar != -1) {
			new ButtonColumn3(jTableCarrito, columnaBotonEliminar, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int filaModelo = Integer.parseInt(e.getActionCommand());
					eliminarProductoDelCarrito(filaModelo);
				}
			});
		} else {
			logger.warning("No se encontró la columna 'Eliminar' para el botón en jTableCarrito.");
		}
	}


        
	private void eliminarProductoDelCarrito(int filaModelo) {
		if (jTableCarrito == null) return;
		DefaultTableModel carritoModel = (DefaultTableModel) jTableCarrito.getModel();
		
		Object codigoObj = carritoModel.getValueAt(filaModelo, 0);
		Object cantidadObj = carritoModel.getValueAt(filaModelo, 2);
		Object nombreObj = carritoModel.getValueAt(filaModelo, 1);
		
		if (codigoObj == null || cantidadObj == null || nombreObj == null) {
			JOptionPane.showMessageDialog(this, "Error: Datos incompletos en la fila del carrito.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String codigo = codigoObj.toString();
		int cantidad = Integer.parseInt(cantidadObj.toString());
		String nombre = nombreObj.toString();
		
		Producto productoBase = ProductoController.buscarProductoPorCodigo(codigo);
		
		if (productoBase != null) {
			int nuevoStock = productoBase.getStock() + cantidad;
			productoBase.setStock(nuevoStock);
			ProductoController.actualizarStock(codigo, nuevoStock);	
			
			carritoModel.removeRow(filaModelo);
			
			cargarTablaProductos();	
			actualizarTotalGeneralCarrito();	
			
			JOptionPane.showMessageDialog(this, nombre + " eliminado del carrito. Stock devuelto. Nuevo stock: " + nuevoStock,	
										  "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Error: Producto no encontrado en el inventario para devolver stock.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}


        
	private void actualizarTotalGeneralCarrito() {
		if (jTableCarrito == null || lblTotalCarrito == null) return;
		DefaultTableModel carritoModel = (DefaultTableModel) jTableCarrito.getModel();
		double totalGeneral = 0.0;
		
		for (int i = 0; i < carritoModel.getRowCount(); i++) {
			Object subtotalObj = carritoModel.getValueAt(i, 4); 
			
			if (subtotalObj != null) { 
				String subtotalStr = subtotalObj.toString().replace(",", ".");	
				try {
					 totalGeneral += Double.parseDouble(subtotalStr);
				} catch (NumberFormatException e) {
					 logger.log(java.util.logging.Level.WARNING, "Valor de subtotal no válido: " + subtotalStr, e);
				}
			}
		}
		
		lblTotalCarrito.setText(String.format("Total: $%.2f", totalGeneral));
	}



	private void generarPedido() {
		if (jTableCarrito == null) return;
		
		DefaultTableModel carritoModel = (DefaultTableModel) jTableCarrito.getModel();
		if (carritoModel.getRowCount() == 0) {
			JOptionPane.showMessageDialog(this, "El carrito está vacío.", "Advertencia", JOptionPane.WARNING_MESSAGE);
			return;
		}

		StringBuilder descripcionItems = new StringBuilder();
		double totalPedido = 0.0;

		for (int i = 0; i < carritoModel.getRowCount(); i++) {
			Object codigoObj = carritoModel.getValueAt(i, 0);
			Object cantidadObj = carritoModel.getValueAt(i, 2);
			Object subtotalObj = carritoModel.getValueAt(i, 4);
			
			if (codigoObj == null || cantidadObj == null || subtotalObj == null) {
				logger.warning("Fila incompleta en el carrito. Saltando.");
				continue;
			}
			
			String codigo = codigoObj.toString();
			int cantidad = Integer.parseInt(cantidadObj.toString());
			double subtotal = Double.parseDouble(subtotalObj.toString().replace(",", "."));
			
			descripcionItems.append(codigo).append(":").append(cantidad);
			if (i < carritoModel.getRowCount() - 1) {
				descripcionItems.append(",");
			}
			totalPedido += subtotal;
		}
		
		Pedido nuevoPedido = new Pedido(
			clienteActual.getCodigo(),	
			descripcionItems.toString(),	
			totalPedido
		);
		
		if (PedidoController.agregarPedido(nuevoPedido)) {
			
		JOptionPane.showMessageDialog(this,	
				"Pedido " + nuevoPedido.getIdPedido() + " registrado. El vendedor lo revisará.",	
				"Pedido Creado", JOptionPane.INFORMATION_MESSAGE);
				
			carritoModel.setRowCount(0);	
			actualizarTotalGeneralCarrito();	
			cargarMisPedidos();	
			
		} else {
			JOptionPane.showMessageDialog(this, "Error: No se pudo registrar el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}


	public void cargarMisPedidos() {
            if (jTableHistorial == null) return;
		
                DefaultTableModel model = (DefaultTableModel) jTableHistorial.getModel();
		
		model.setColumnIdentifiers(COLUMNAS_HISTORIAL);
		model.setRowCount(0);

		Pedido[] todosLosPedidos = PedidoController.obtenerPedidosActivos();	
		DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            if (todosLosPedidos == null || clienteActual == null) return;
		
		for (Pedido p : todosLosPedidos) {
			if (p != null && p.getCodigoCliente().equals(clienteActual.getCodigo())) {	
				model.addRow(new Object[]{
					p.getIdPedido(),
					p.getFechaCreacion().format(displayFormatter),
					String.format("%.2f", p.getTotal()),
					p.getEstado(),
				});
			}
		}
	}

	private JTable obtenerJTableEnPanel(JPanel panel) {
		  if (panel == null) return null;
		  for (Component comp : panel.getComponents()) {
			  if (comp instanceof JScrollPane) {
				  JScrollPane scroll = (JScrollPane) comp;
				  if (scroll.getViewport().getView() instanceof JTable) {
					  return (JTable) scroll.getViewport().getView();
				  }
			  }
		  }
		  return null;
	  }

	
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "Categoria", "Stock", "Acciones"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 781, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Productos", jPanel1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo Producto", "Nombre", "Cantidad", "Precio", "Total", "Opciones"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton2.setText("Realizar Pedido");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(14, 14, 14))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2))
        );

        jTabbedPane2.addTab("Carrito de Compras", jPanel2);

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Codigo", "Fecha de Confirmacion", "Total"
            }
        ));
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 757, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Historial de Compras", jPanel3);

        jButton1.setText("Cerrar Sesion");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 803, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(347, 347, 347)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
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
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        generarPedido();
    }//GEN-LAST:event_jButton2ActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new VentanaCliente().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    // End of variables declaration//GEN-END:variables
}
