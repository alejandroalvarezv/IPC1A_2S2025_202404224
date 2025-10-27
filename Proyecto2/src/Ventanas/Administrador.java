package Ventanas;
import controlador.UsuarioController;
import controlador.ProductoController;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Vendedor;
import modelo.Producto;
import modelo.Tecnologia;
import modelo.Alimento;
import modelo.ButtonColumn;
import java.util.logging.Logger;
import java.util.logging.Level;
import modelo.ReporteVentasProducto; 
import modelo.ReporteInventario;
import modelo.ReportePDFGenerator; 
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.io.File; 
import javax.swing.filechooser.FileNameExtensionFilter;


public class Administrador extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(Administrador.class.getName());

    public Administrador() {
        initComponents(); 
        this.setTitle("Módulo de Administración");
        this.setLocationRelativeTo(null);
        refreshAdminView();
    }

    public void refreshAdminView() {
        actualizarTablaVendedores();
        actualizarTablaProductos();
    }

    public void actualizarTablaVendedores() {
        UsuarioController.cargarUsuarios();
        String[] nombresColumnas = {"Código", "Nombre", "Género", "Ventas"};
        DefaultTableModel modelo = new DefaultTableModel(nombresColumnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Vendedor[] listaVendedores = UsuarioController.obtenerVendedores();

        if (listaVendedores != null) {
            for (Vendedor v : listaVendedores) {
                if (v != null) {
                    modelo.addRow(new Object[]{
                        v.getCodigo(),
                        v.getNombre(),
                        v.getGenero(),
                        v.getVentasConfirmadas()
                    });
                }
            }
        }
        jTable1.setModel(modelo);
    }

    
    public void actualizarTablaProductos() {
        ProductoController.cargarProductos();
        
        String[] nombresColumnas = {"Código", "Nombre", "Categoría", "Ver Detalle"};
        DefaultTableModel modelo = new DefaultTableModel(nombresColumnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
        };

        Producto[] listaProductos = ProductoController.obtenerProductos();

        if (listaProductos != null) {
            for (Producto p : listaProductos) {
                if (p == null) continue;

                String categoria;
                if (p instanceof Tecnologia) {
                    categoria = "Tecnología";
                } else if (p instanceof Alimento) {
                    categoria = "Alimento";
                } else {
                    categoria = "General";
                }

                modelo.addRow(new Object[]{
                    p.getCodigo(),
                    p.getNombre(),
                    categoria,
                    "Ver Detalle"
                });
            }
        }

        jTable2.setModel(modelo);

        new ButtonColumn(jTable2, 3, e -> {
            int row = Integer.parseInt(e.getActionCommand());
            String codigo = jTable2.getValueAt(row, 0).toString();

            Producto producto = ProductoController.buscarProducto(codigo);

            if (producto != null) {
                String detalle = obtenerDetalleProducto(producto);
                JOptionPane.showMessageDialog(this, detalle, 
                        "Detalles del Producto", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                        "No se encontró el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }


        private String obtenerDetalleProducto(Producto producto) {
            if (producto instanceof Tecnologia) {
                Tecnologia t = (Tecnologia) producto;
            return "Categoría: Tecnología\nGarantía: " + t.getGarantia() + " meses";
            } else if (producto instanceof Alimento) {
                Alimento a = (Alimento) producto;
            return "Categoría: Alimento\nFecha de caducidad: " + a.getFechaCaducidad();
            } else {
            return "Categoría: General\nMaterial: " + producto.getMaterial();
    }
}
        
        
        
        private void generarYGuardarReporte(Object[] data, String tipoReporte, String tituloPDF) {
        if (data == null || data.length == 0) {
            JOptionPane.showMessageDialog(this, "No hay datos para generar el reporte: " + tituloPDF, "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte: " + tituloPDF);
        
        // Sugerir nombre de archivo. El generador PDF añadirá el timestamp.
        fileChooser.setSelectedFile(new File(tipoReporte + ".pdf")); 

        int seleccion = fileChooser.showSaveDialog(this);
        
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            String rutaBaseDirectorios = fileChooser.getSelectedFile().getParent(); 
            
            // Llama al método estático del ReportePDFGenerator
            String rutaFinal = ReportePDFGenerator.generarReporte(data, tipoReporte, tituloPDF, rutaBaseDirectorios);
            
            if (rutaFinal != null) {
                JOptionPane.showMessageDialog(this, 
                        "Reporte generado con éxito en:\n" + rutaFinal, 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                        "Error al generar el archivo PDF. Revise la consola.", 
                        "Error de PDF", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


// --- MANEJADORES DE EVENTOS PARA BOTONES DE REPORTES (jButton10 - jButton16) ---

        
        
        
        
        
        


    
    @SuppressWarnings("unchecked")
                          
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPaneModulos = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        tablaVendedores = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "Género", "Cantidad de ventas confirmadas"
            }
        ));
        tablaVendedores.setViewportView(jTable1);

        jButton1.setText("Crear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("Actualizar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton2.setText("Cargar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Eliminar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tablaVendedores, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(55, 55, 55)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(103, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tablaVendedores, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPaneModulos.addTab("Vendedores", jPanel1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Código", "Nombre", "Categoría", "Ver Detalles"
            }
        ));
        jScrollPane1.setViewportView(jTable2);

        jButton5.setText("Crear");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Actualizar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Eliminar");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Cargar");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jButton5)
                .addGap(51, 51, 51)
                .addComponent(jButton6)
                .addGap(42, 42, 42)
                .addComponent(jButton7)
                .addGap(53, 53, 53)
                .addComponent(jButton8)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPaneModulos.addTab("Productos", jPanel2);

        jButton10.setText("Productos mas Vendidos");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setText("Productos Menos Vendidos");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setText("Reporte de Inventario");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jButton13.setText("Reporte de Ventas por Vendedor");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setText("Reporte de Clientes Activos");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setText("Reporte Financiero");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setText("Productos por Caducar");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton10)
                            .addComponent(jButton11)
                            .addComponent(jButton12))
                        .addGap(97, 97, 97)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton15)
                            .addComponent(jButton14)
                            .addComponent(jButton13)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(310, 310, 310)
                        .addComponent(jButton16)))
                .addContainerGap(215, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton10)
                    .addComponent(jButton13))
                .addGap(42, 42, 42)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton11)
                    .addComponent(jButton14))
                .addGap(53, 53, 53)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12)
                    .addComponent(jButton15))
                .addGap(69, 69, 69)
                .addComponent(jButton16)
                .addContainerGap(116, Short.MAX_VALUE))
        );

        jTabbedPaneModulos.addTab("Reportes", jPanel3);

        jButton9.setText("Cerrar Sesion");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jTabbedPaneModulos, javax.swing.GroupLayout.PREFERRED_SIZE, 856, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(388, 388, 388)
                        .addComponent(jButton9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jTabbedPaneModulos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton9)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
    fileChooser.setDialogTitle("Seleccionar Archivo CSV de Vendedores para Carga Masiva");
    
    // 2. Filtrar para que solo muestre archivos CSV (.csv)
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de Datos CSV", "csv");
    fileChooser.setFileFilter(filter);

    // 3. Mostrar el diálogo de apertura de archivo
    int userSelection = fileChooser.showOpenDialog(this);

    if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
        // El usuario seleccionó un archivo
        java.io.File fileToLoad = fileChooser.getSelectedFile();
        String rutaArchivo = fileToLoad.getAbsolutePath();
        
        // 4. Llamar al método del controlador
        try {
            // Llamamos al nuevo método de carga masiva de Vendedores
            int cargados = UsuarioController.cargarVendedoresMasivoCSV(rutaArchivo);
            
            // 5. Mostrar resultado y actualizar la tabla
            if (cargados > 0) {
                JOptionPane.showMessageDialog(this, 
                    cargados + " vendedor(es) cargado(s) exitosamente desde CSV.", 
                    "Carga Masiva Exitosa", JOptionPane.INFORMATION_MESSAGE);
                
                // Refrescar la tabla de vendedores
                actualizarTablaVendedores(); 
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se cargó ningún vendedor. Revise el formato del archivo o si los códigos ya existen/el archivo está vacío.", 
                    "Advertencia de Carga", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al procesar el archivo CSV de Vendedores en la interfaz: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(this, 
                "Ocurrió un error inesperado al procesar el archivo. Verifique el formato.", 
                "Error Crítico de Carga", JOptionPane.ERROR_MESSAGE);
        }
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        EliminarVendedor EliminarVendedorWindow = new EliminarVendedor(this);
        EliminarVendedorWindow.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CrearVendedor crearVendedorWindow = new CrearVendedor(this);
        crearVendedorWindow.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ActualizarVendedor ActualizarVendedorWindow = new ActualizarVendedor(this);
        ActualizarVendedorWindow.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        CrearProducto crearProductoWindow = new CrearProducto(this); 
        crearProductoWindow.setVisible(true);    
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        ActualizarProducto actualizarProductoWindow = new ActualizarProducto(this);
        actualizarProductoWindow.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        EliminarProducto eliminarProductoWindow = new EliminarProducto(this);
        eliminarProductoWindow.setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
    fileChooser.setDialogTitle("Seleccionar Archivo CSV de Productos para Carga Masiva");
    
    // 2. Filtrar para que solo muestre archivos CSV (.csv)
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de Datos CSV", "csv");
    fileChooser.setFileFilter(filter);

    // 3. Mostrar el diálogo de apertura de archivo
    int userSelection = fileChooser.showOpenDialog(this);

    if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
        // El usuario seleccionó un archivo
        java.io.File fileToLoad = fileChooser.getSelectedFile();
        String rutaArchivo = fileToLoad.getAbsolutePath();
        
        // 4. Llamar al método del controlador
        try {
            // Utilizamos el método que lee el CSV e integra los productos
            int cargados = ProductoController.cargarProductosMasivoCSV(rutaArchivo);
            
            // 5. Mostrar resultado y actualizar la tabla
            if (cargados > 0) {
                JOptionPane.showMessageDialog(this, 
                    cargados + " producto(s) cargado(s) exitosamente desde CSV.", 
                    "Carga Masiva Exitosa", JOptionPane.INFORMATION_MESSAGE);
                
                // Refrescar la tabla para que se vean los productos nuevos
                actualizarTablaProductos(); 
            } else {
                JOptionPane.showMessageDialog(this, 
                    "No se cargó ningún producto. Revise el formato del archivo o si los códigos ya existen/el archivo está vacío.", 
                    "Advertencia de Carga", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al procesar el archivo CSV en la interfaz: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(this, 
                "Ocurrió un error inesperado al procesar el archivo. Verifique el formato.", 
                "Error Crítico de Carga", JOptionPane.ERROR_MESSAGE);
        }
    }

    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
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
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        Object[] data = ReporteVentasProducto.obtenerProductosMasVendidos();
    generarYGuardarReporte(data, "ProductosMasVendidos", "Reporte: Productos Más Vendidos");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        Object[] data = ReporteVentasProducto.obtenerProductosMenosVendidos();

    // 2. Generar y Guardar el reporte
    generarYGuardarReporte(data, "ProductosMenosVendidos", "Reporte: Productos Menos Vendidos");
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        Object[] data = ReporteInventario.obtenerReporteInventarioGeneral();

    // 2. Generar y Guardar el reporte
    generarYGuardarReporte(data, "InventarioGeneral", "Reporte de Inventario General");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        Object[] data = ReporteVentasProducto.obtenerVentasPorVendedor();

    // 2. Generar y Guardar el reporte
    generarYGuardarReporte(data, "VentasPorVendedor", "Reporte de Ventas por Vendedor");
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        Object[] data = UsuarioController.obtenerClientesActivos(); 

    // 2. Generar y Guardar el reporte
    generarYGuardarReporte(data, "ClientesActivos", "Reporte de Clientes Activos");
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        Object[] data = new Object[]{}; // Reemplaza con el método real, ej: ReporteFinanciero.obtenerData()
    if (data.length == 0) {
        JOptionPane.showMessageDialog(this, "El Reporte Financiero aún no tiene datos para mostrar.", "Info", JOptionPane.INFORMATION_MESSAGE);
        return;
    }
    // 2. Generar y Guardar el reporte
    generarYGuardarReporte(data, "ReporteFinanciero", "Reporte Financiero General");

    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        Object[] data = ReporteInventario.obtenerProductosPorCaducar();

    // 2. Generar y Guardar el reporte
    generarYGuardarReporte(data, "ProductosPorCaducar", "Reporte: Productos por Caducar");

    }//GEN-LAST:event_jButton16ActionPerformed


    public static void main(String args[]) {
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
        java.awt.EventQueue.invokeLater(() -> new Administrador().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPaneModulos;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JScrollPane tablaVendedores;
    // End of variables declaration//GEN-END:variables
}