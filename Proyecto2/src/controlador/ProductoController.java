package controlador;
import modelo.Producto;
import modelo.Tecnologia;
import modelo.Alimento;
import modelo.ProductoPrecio;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProductoController {
    private static Producto[] productos = new Producto[100];
    private static int contadorProductos = 0;
    private static final String ARCHIVO_PRODUCTOS = "data/productos.csv";
    private static boolean productosCargados = false;
    
    private static final Logger logger = Logger.getLogger(ProductoController.class.getName());
    
    
    public static boolean agregarProducto(Producto p) {
        if (buscarProductoPorCodigo(p.getCodigo()) != null) {
            logger.log(Level.WARNING, "Intento de agregar producto duplicado: {0}", p.getCodigo());
            return false;
        }
        if (contadorProductos >= productos.length) {
            System.out.println("No se pueden agregar más productos, arreglo lleno.");
            logger.log(Level.SEVERE, "Arreglo de productos lleno. No se pudo agregar {0}", p.getCodigo());
            return false;
        }
        productos[contadorProductos++] = p;
        guardarProductos();
        return true;
    }

 
    public static boolean eliminarProducto(String codigo) {
        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] != null && productos[i].getCodigo().equals(codigo)) {
                
                for (int j = i; j < contadorProductos - 1; j++) {
                    productos[j] = productos[j + 1];
                }
                
                productos[contadorProductos - 1] = null;
                contadorProductos--;
                
                logger.log(Level.INFO, "Producto eliminado: {0}", codigo);
                
                guardarProductos();
                recargarProductos(); 
                return true;
            }
        }
        logger.log(Level.WARNING, "Intento de eliminar producto no encontrado: {0}", codigo);
        return false;
    }


  
    public static void cargarProductos() {
        if (productosCargados && Files.exists(Paths.get(ARCHIVO_PRODUCTOS))) {
            return;
        }
        
        contadorProductos = 0;
        Arrays.fill(productos, null);

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_PRODUCTOS))) {
            String linea;
            br.readLine(); 

            while ((linea = br.readLine()) != null && contadorProductos < productos.length) {
                String[] partes = linea.split(",");
                
                if (partes.length < 7) { 
                    logger.log(Level.WARNING, "Línea CSV incompleta (esperado 7 campos): {0}", linea);
                    continue;
                }

                String codigo = partes[0].trim();
                String nombre = partes[1].trim();
                String categoria = partes[2].trim();
                String material = partes[3].trim();
                String atributoEspecifico = partes[4].trim(); 
                int stock = 0;
                double precio = 0.0;

                try {
                    stock = Integer.parseInt(partes[5].trim());
                    precio = Double.parseDouble(partes[6].trim());
                } catch (NumberFormatException e) {
                    logger.log(Level.WARNING, "Stock o Precio inválido en línea: {0}", linea);
                    continue;
                }

                Producto nuevoProducto = null;
                
                if (categoria.equalsIgnoreCase("Tecnología")) {
                    try {
                        int garantia = Integer.parseInt(atributoEspecifico);
                        nuevoProducto = new Tecnologia(codigo, nombre, categoria, garantia, stock, precio);
                    } catch (NumberFormatException e) {
                        logger.log(Level.SEVERE, "ERROR: Fallo al parsear la garantía para " + codigo, e);
                        continue;
                    }
                } else if (categoria.equalsIgnoreCase("Alimento")) {
                    String fechaCaducidad = atributoEspecifico;
                    nuevoProducto = new Alimento(codigo, nombre, categoria, fechaCaducidad, stock, precio);
                } else {
                    // Producto General (usa 'material' como atributo no específico)
                    nuevoProducto = new ProductoPrecio(codigo, nombre, categoria, material, stock, precio);
                }

                if (nuevoProducto != null) {
                    productos[contadorProductos++] = nuevoProducto; 
                }
            }

            productosCargados = true;
            logger.log(Level.INFO, "Productos cargados: {0}", contadorProductos);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al cargar productos: " + e.getMessage(), e);
        }
    }

    
    public static boolean actualizarStock(String codigoProducto, int nuevaCantidad) {
        Producto producto = buscarProductoPorCodigo(codigoProducto);
        if (producto != null) {
            producto.setStock(nuevaCantidad);
            guardarProductos(); 
            return true;
        }
        return false;
    }
    

    public static void recargarProductos() {
        productosCargados = false; 
        cargarProductos();
    }
    
    
    public static Producto[] obtenerProductos() {
        Producto[] copia = new Producto[contadorProductos];
        System.arraycopy(productos, 0, copia, 0, contadorProductos);
        return copia;
    }

    public static Producto buscarProducto(String codigo) {
        return buscarProductoPorCodigo(codigo);
    }


    public static boolean cargarProductosMasivo(String ruta) {
        if (!Files.exists(Paths.get(ruta))) {
            System.out.println("Archivo no existe: " + ruta);
            return false;
        }

        return false;
    }
    
   
    public static void guardarProductos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_PRODUCTOS))) {
            bw.write("Codigo,Nombre,Categoria,Material,AtributoEspecifico,Stock,Precio");
            bw.newLine();

            for (int i = 0; i < contadorProductos; i++) {
                if (productos[i] != null) {
                    bw.write(productos[i].toCSV()); 
                    bw.newLine();
                }
            }
            logger.log(Level.INFO, "Productos guardados correctamente en {0}", ARCHIVO_PRODUCTOS);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al guardar productos: " + e.getMessage(), e);
        }
    }
    
    
    public static Producto buscarProductoPorCodigo(String codigo) {
        if (!productosCargados) {
            cargarProductos();
        }
        
        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] != null && productos[i].getCodigo().equals(codigo)) {
                return productos[i];
            }
        }
        return null;
    }
    
    
    public static boolean actualizarProducto(Producto p) {
        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] != null && productos[i].getCodigo().equals(p.getCodigo())) {
                productos[i] = p;
                guardarProductos();
                return true;
            }
        }
        return false;
    }
    
    
    public static boolean actualizarStockMasivo(String rutaArchivo) {
        return false;
    }

    public static boolean generarCSVStockActualizado(String rutaSalida) {
        return false;
    }

    public static boolean generarCSVStockCompleto(String rutaSalida) {
        return false;
    }

    public static boolean generarStockMasivo(String rutaArchivo) {
        return false;
    }
        
        

    public static boolean verificarStockParaPedido(String descripcionItems) {
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

            Producto p = buscarProductoPorCodigo(codigo);
            
            if (p == null || p.getStock() < cantidadRequerida) {
                logger.log(Level.INFO, "Fallo de stock: Producto {0} no encontrado o stock insuficiente.", codigo);
                return false;
            }
        }
        return true;
    }
        
        

    public static void descontarStockParaPedido(String descripcionItems) {
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

            Producto p = buscarProductoPorCodigo(codigo);
            
            if (p != null) {
                p.setStock(p.getStock() - cantidad);
                logger.log(Level.INFO, "Descontado {0} unidades de {1}.", new Object[]{cantidad, codigo});
            }
        }
        
        guardarProductos();
    }            
}