package controlador;

import modelo.Producto;
import modelo.Tecnologia;
import modelo.Alimento;
import modelo.ProductoPrecio;
import java.io.*; 
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProductoController {
    private static Producto[] productos = new Producto[100];
    private static int contadorProductos = 0;
    
    private static final String RUTA_ARCHIVO_SER = "productos.ser"; 
    
    private static final Logger logger = Logger.getLogger(ProductoController.class.getName());
    
    // ------------------- PERSISTENCIA BINARIA (.SER) -------------------


    public static void cargarProductos() {
        contadorProductos = 0;
        productos = new Producto[100]; 
        
        try (FileInputStream fileIn = new FileInputStream(RUTA_ARCHIVO_SER);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            contadorProductos = (int) objectIn.readObject();
            productos = (Producto[]) objectIn.readObject();

            logger.log(Level.INFO, "Productos cargados por serialización: {0}", contadorProductos);

        } catch (FileNotFoundException e) {
            logger.log(Level.INFO, "Archivo serializado no encontrado. Iniciando con lista vacía.");
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Error al cargar o deserializar productos: " + e.getMessage(), e);
            contadorProductos = 0;
            productos = new Producto[100];
        }
    }
    

    public static void guardarProductos() {
        try (FileOutputStream fileOut = new FileOutputStream(RUTA_ARCHIVO_SER);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(contadorProductos);
            objectOut.writeObject(productos);
            
            logger.log(Level.INFO, "Productos guardados correctamente por serialización en {0}", RUTA_ARCHIVO_SER);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al guardar productos por serialización: " + e.getMessage(), e);
        }
    }
    
    // ------------------- CARGA MASIVA CSV (NUEVA FUNCIONALIDAD) -------------------

    /**
     * Lee productos desde un archivo CSV y los agrega a la lista de productos.
     * El formato asumido por línea es: codigo,nombre,categoria,atributoUnico,precio
     * * @param rutaArchivo La ruta completa del archivo CSV.
     * @return El número de productos agregados exitosamente.
     */
    public static int cargarProductosMasivoCSV(String rutaArchivo) {
        // Cargar el estado actual antes de agregar nuevos (para verificar duplicados)
        cargarProductos(); 

        int productosCargados = 0;
        int lineaActual = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            
            while ((linea = br.readLine()) != null) {
                lineaActual++;
                
                if (lineaActual == 1) continue; 
                
                String[] datos = linea.split(",");
                
                if (datos.length < 5) {
                    logger.log(Level.WARNING, "Línea {0} incompleta. Se saltó la línea: {1}", new Object[]{lineaActual, linea});
                    continue; 
                }
                
                String codigo = datos[0].trim();
                String nombre = datos[1].trim();
                String categoria = datos[2].trim();
                String atributoEspecifico = datos[3].trim();
                String precioStr = datos[4].trim();

                try {
                    double precioInicial = Double.parseDouble(precioStr);
                    
                    Producto nuevoProducto = crearProductoDesdeDatos(codigo, nombre, categoria, atributoEspecifico, precioInicial);
                    
                    if (nuevoProducto != null) {

                        if (buscarProductoPorCodigoSoloMemoria(codigo) == null && contadorProductos < productos.length) {
                             productos[contadorProductos++] = nuevoProducto;
                             productosCargados++;
                        } else {
                            logger.log(Level.WARNING, "Producto con código {0} ya existe o el array está lleno. No se cargó desde CSV.", codigo);
                        }
                    }
                } catch (NumberFormatException e) {
                    logger.log(Level.WARNING, "Error de formato numérico en el precio de la línea {0}. Se saltó la línea.", lineaActual);
                }
            }

            guardarProductos(); 
            
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error de lectura del archivo CSV en ruta: {0}", rutaArchivo);
            logger.log(Level.SEVERE, "Detalle del error: " + e.getMessage(), e);
        }
        
        return productosCargados;
    }

    private static Producto crearProductoDesdeDatos(String codigo, String nombre, String categoria, String atributoEspecifico, double precioInicial) {
    int stock = 0; 
    
    switch (categoria.toUpperCase()) {
        
        case "GENERALES":
            case "GENERAL":
            String material = atributoEspecifico;
            return new ProductoPrecio(codigo, nombre, categoria, material, stock, precioInicial);
            
        case "TECNOLOGÍA":
        case "TECNOLOGIA":
            try {
                int garantia = Integer.parseInt(atributoEspecifico.trim()); 
                return new Tecnologia(codigo, nombre, categoria, garantia, stock, precioInicial);
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Error al parsear Garantía para Tecnología. Producto ({0}) no cargado. Detalle: {1}", new Object[]{codigo, e.getMessage()});
                return null;
            }
            
        case "ALIMENTO":
            String fechaCaducidad = atributoEspecifico;
            return new Alimento(codigo, nombre, categoria, fechaCaducidad, stock, precioInicial);
            
        default:
            logger.log(Level.WARNING, "Categoría desconocida ({0}) para el producto {1}. No se pudo crear la instancia.", new Object[]{categoria, codigo});
            return null;
    }
}
    
    private static Producto buscarProductoPorCodigoSoloMemoria(String codigo) {
        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] != null && productos[i].getCodigo().equals(codigo)) {
                return productos[i];
            }
        }
        return null;
    }
    
    // ------------------- MÉTODOS DE NEGOCIO (AJUSTADOS) -------------------
    
    public static boolean agregarProducto(Producto p) {
        cargarProductos(); // Asegurar que el estado actual está en memoria

        if (buscarProductoPorCodigoSoloMemoria(p.getCodigo()) != null) {
            logger.log(Level.WARNING, "Intento de agregar producto duplicado: {0}", p.getCodigo());
            return false;
        }
        if (contadorProductos >= productos.length) {
            System.out.println("No se pueden agregar más productos, arreglo lleno.");
            logger.log(Level.SEVERE, "Arreglo de productos lleno. No se pudo agregar {0}", p.getCodigo());
            return false;
        }
        
        productos[contadorProductos++] = p;
        guardarProductos(); // Guardar después de la modificación
        return true;
    }

    public static boolean eliminarProducto(String codigo) {
        cargarProductos(); 

        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] != null && productos[i].getCodigo().equals(codigo)) {
                
                for (int j = i; j < contadorProductos - 1; j++) {
                    productos[j] = productos[j + 1];
                }
                
                productos[contadorProductos - 1] = null; 
                contadorProductos--;
                
                logger.log(Level.INFO, "Producto eliminado: {0}", codigo);
                
                guardarProductos(); 
                return true;
            }
        }
        logger.log(Level.WARNING, "Intento de eliminar producto no encontrado: {0}", codigo);
        return false;
    }


    public static void recargarProductos() {
        cargarProductos();
    }
    
    public static boolean actualizarStock(String codigoProducto, int nuevaCantidad) {
        cargarProductos(); 
        
        Producto producto = buscarProductoPorCodigoSoloMemoria(codigoProducto); // Usar solo memoria
        if (producto != null) {
            producto.setStock(nuevaCantidad);
            guardarProductos(); 
            return true;
        }
        return false;
    }
    
    public static Producto[] obtenerProductos() {
        cargarProductos(); 
        
        Producto[] copia = new Producto[contadorProductos];
        System.arraycopy(productos, 0, copia, 0, contadorProductos);
        return copia;
    }

    public static Producto buscarProducto(String codigo) {

        return buscarProductoPorCodigo(codigo);
    }
    
    public static modelo.Producto buscarProductoPorCodigo(String codigo) {
    if (productos == null) {
        cargarProductos();
    }
    
    for (int i = 0; i < contadorProductos; i++) {
        modelo.Producto p = productos[i];
        
        if (p != null && p.getCodigo().equalsIgnoreCase(codigo)) {
            return p;
        }
    }
    return null;
}

    public static boolean actualizarProducto(Producto p) {
        cargarProductos(); 

        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] != null && productos[i].getCodigo().equals(p.getCodigo())) {
                productos[i] = p;
                guardarProductos();
                return true;
            }
        }
        return false;
    }


    public static boolean cargarProductosMasivo(String ruta) {
        int cargados = cargarProductosMasivoCSV(ruta);
        return cargados > 0;
    }
    

    public static boolean actualizarStockMasivo(String rutaArchivo) {
        logger.log(Level.WARNING, "La actualización masiva desde archivo ya no está soportada en el modo de serialización simple.");
        return false;
    }

    public static boolean generarCSVStockActualizado(String rutaSalida) {
        logger.log(Level.WARNING, "La generación de CSV ya no está soportada en el modo de serialización simple.");
        return false;
    }

    public static boolean generarCSVStockCompleto(String rutaSalida) {
        logger.log(Level.WARNING, "La generación de CSV ya no está soportada en el modo de serialización simple.");
        return false;
    }

    public static boolean generarStockMasivo(String rutaArchivo) {
        logger.log(Level.WARNING, "La funcionalidad de stock masivo ya no está soportada en el modo de serialización simple.");
        return false;
    }
    
    
    public static boolean verificarStockParaPedido(String descripcionItems) {
        cargarProductos(); 
        
        if (descripcionItems == null || descripcionItems.trim().isEmpty()) return false;

        String[] items = descripcionItems.split(",");
        
        for (String item : items) {
            String[] partes = item.trim().split(":");
            if (partes.length != 2) continue;
            
            String codigo = partes[0].trim();
            int cantidadRequerida;
            try {
                cantidadRequerida = Integer.parseInt(partes[1].trim());
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Cantidad requerida inválida para código: {0}", codigo);
                continue;
            }

            Producto p = buscarProductoPorCodigoSoloMemoria(codigo); // Usar solo memoria
            
            if (p == null || p.getStock() < cantidadRequerida) {
                logger.log(Level.INFO, "Fallo de stock: Producto {0} no encontrado o stock insuficiente.", codigo);
                return false;
            }
        }
        return true;
    }
    
    public static void descontarStockParaPedido(String descripcionItems) {
        cargarProductos(); 

        if (descripcionItems == null || descripcionItems.trim().isEmpty()) return;

        String[] items = descripcionItems.split(",");
        
        for (String item : items) {
            String[] partes = item.trim().split(":");
            if (partes.length != 2) continue;
            
            String codigo = partes[0].trim();
            int cantidad;
            try {
                cantidad = Integer.parseInt(partes[1].trim());
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Cantidad de descuento inválida para código: {0}", codigo);
                continue;
            }

            Producto p = buscarProductoPorCodigoSoloMemoria(codigo); // Usar solo memoria
            
            if (p != null) {
                p.setStock(p.getStock() - cantidad);
                logger.log(Level.INFO, "Descontado {0} unidades de {1}.", new Object[]{cantidad, codigo});
            }
        }
        
        guardarProductos(); 
    }    
    
    
    public static int cargarStockMasivoCSV(String rutaArchivo) {
        cargarProductos(); 

        int productosActualizados = 0;
        int lineaActual = 0;

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(rutaArchivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                lineaActual++;
                
                if (lineaActual == 1) continue; // Saltar el encabezado si existe

                String[] datos = linea.split(",");

                if (datos.length < 2) {
                    System.err.println("Advertencia: Línea " + lineaActual + " incompleta. Saltada: " + linea);
                    continue;
                }

                String codigo = datos[0].trim();
                String sCantidad = datos[1].trim();
                int cantidad;

                try {
                    cantidad = Integer.parseInt(sCantidad);
                } catch (NumberFormatException e) {
                    System.err.println("Error: Cantidad inválida '" + sCantidad + "' en línea " + lineaActual + ". Saltada.");
                    continue;
                }
                
                // 1. Buscar el producto existente
                modelo.Producto p = buscarProductoPorCodigo(codigo); 

                if (p != null) {
                    // 2. Aumentar el stock del producto
                    p.setStock(p.getStock() + cantidad);
                    productosActualizados++;
                } else {
                    System.err.println("Advertencia: Producto con código " + codigo + " no encontrado. Saltado.");
                }
            }
            
            // 3. Guardar todos los productos con el stock actualizado
            guardarProductos(); 

        } catch (java.io.IOException e) {
            System.err.println("Error de lectura del archivo CSV de Stock: " + e.getMessage());
        }
        
        return productosActualizados;
    }
    
    
    
    
    
}