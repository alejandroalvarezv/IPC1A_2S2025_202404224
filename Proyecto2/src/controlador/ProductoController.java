package controlador;
import modelo.Producto;
import modelo.Tecnologia;
import modelo.Alimento;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        return false; 
    }
    if (contadorProductos >= productos.length) {
        System.out.println("No se pueden agregar más productos, arreglo lleno.");
        return false;
    }
    productos[contadorProductos++] = p;
    guardarProductos(); 
    return true;
}

    
    public static boolean eliminarProducto(String codigo) {
    for (int i = 0; i < contadorProductos; i++) {
        if (productos[i] != null && productos[i].getCodigo().equals(codigo)) {
            productos[i] = null;

            for (int j = i; j < contadorProductos - 1; j++) {
                productos[j] = productos[j + 1];
            }
            productos[contadorProductos - 1] = null;
            contadorProductos--;

            guardarProductos();

            recargarProductos();
            return true;
        }
    }
    return false; 
}


    
    public static void cargarProductos() {
    if (productosCargados || !Files.exists(Paths.get(ARCHIVO_PRODUCTOS))) {
        productosCargados = true;
        return;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_PRODUCTOS))) {
        String linea;
        br.readLine();

        while ((linea = br.readLine()) != null && contadorProductos < productos.length) {
            String[] partes = linea.split(",");
            
            if (partes.length < 6) {
                System.out.println("ADVERTENCIA: Línea CSV con formato incorrecto o incompleto: " + linea);
                continue;
            }

            String codigo = partes[0].trim();
            String nombre = partes[1].trim();
            String categoria = partes[2].trim();
            String material = partes[3].trim();
            String atributoEspecifico = partes[4].trim();
            int stock = 0;

            try {
                stock = Integer.parseInt(partes[5].trim());
            } catch (NumberFormatException e) {
                System.out.println("ADVERTENCIA: Stock inválido en línea: " + linea + ", se pone 0 por defecto.");
                stock = 0;
            }

            Producto nuevoProducto = null;

            if (categoria.equalsIgnoreCase("Tecnología")) {
                try {
                    int garantia = Integer.parseInt(atributoEspecifico.replace(" meses", "").trim());
                    nuevoProducto = new Tecnologia(codigo, nombre, material, garantia, stock);
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Fallo al parsear la garantía para " + codigo);
                    continue; 
                }
            } else if (categoria.equalsIgnoreCase("Alimento")) {
                nuevoProducto = new Alimento(codigo, nombre, material, atributoEspecifico, stock);
            } else {
                // Categoría general
                nuevoProducto = new Producto(codigo, nombre, categoria, material, stock);
            }

            if (nuevoProducto != null && buscarProductoPorCodigo(codigo) == null) {
                productos[contadorProductos++] = nuevoProducto;
            }
        }

        productosCargados = true;

    } catch (IOException e) {
        System.out.println("Error al cargar productos: " + e.getMessage());
    }
}

    
    public static boolean actualizarStock(String codigoProducto, int nuevaCantidad) {
        Producto producto =buscarProductoPorCodigo(codigoProducto);
        if (producto != null) {
            return actualizarProducto(producto); 
        }
        return false;
    }
    
        public static void recargarProductos() {
    productosCargados = false;
    cargarProductos();
    }
    
    
public static Producto[] obtenerProductos() {
    Producto[] copia = new Producto[contadorProductos];
    for (int i = 0; i < contadorProductos; i++) {
        copia[i] = productos[i];
    }
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

    try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
        String linea;
        br.readLine(); 

        while ((linea = br.readLine()) != null && contadorProductos < productos.length) {
            String[] partes = linea.split(",");
            if (partes.length < 5) { 
                System.out.println("Línea CSV con formato incorrecto: " + linea);
                continue;
            }

            String codigo = partes[0].trim();
            String nombre = partes[1].trim();
            String categoria = partes[2].trim();
            String material = partes[3].trim();
            String atributoEspecifico = partes[4].trim();
            int stock = 0; 
            if (partes.length >= 6) {
                try {
                    stock = Integer.parseInt(partes[5].trim());
                } catch (NumberFormatException ignored) {}
            }

            Producto nuevoProducto = null;

            if (categoria.equalsIgnoreCase("Tecnología")) {
                try {
                    int garantia = Integer.parseInt(atributoEspecifico.replace(" meses", "").trim());
                    nuevoProducto = new Tecnologia(codigo, nombre, material, garantia, stock);
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Fallo al parsear la garantía para " + codigo);
                    continue;
                }
            } else if (categoria.equalsIgnoreCase("Alimento")) {
                nuevoProducto = new Alimento(codigo, nombre, material, atributoEspecifico, stock);
            } else {
    
                nuevoProducto = new Producto(codigo, nombre, categoria, material, stock);
            }

            if (nuevoProducto != null && buscarProductoPorCodigo(codigo) == null) {
                productos[contadorProductos++] = nuevoProducto;
            }
        }

        guardarProductos();
        return true;

    } catch (IOException e) {
        System.out.println("Error al cargar productos: " + e.getMessage());
        return false;
    }
    
        }
   
    public static void guardarProductos() {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_PRODUCTOS))) {
        bw.write("Codigo,Nombre,Categoria,Material,AtributoEspecifico,Stock");
        bw.newLine();

        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] != null) {
                bw.write(productos[i].toCSV());
                bw.newLine();
            }
        }
        System.out.println("Productos guardados correctamente en " + ARCHIVO_PRODUCTOS);
    } catch (IOException e) {
        System.out.println("Error al guardar productos: " + e.getMessage());
    }
}
    
    
    public static Producto buscarProductoPorCodigo(String codigo) {
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
    if (!Files.exists(Paths.get(rutaArchivo))) {
        System.out.println("Archivo no existe: " + rutaArchivo);
        return false;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
        String linea;
        br.readLine();

        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length < 2) {
                System.out.println("Línea inválida en CSV de stock: " + linea);
                continue;
            }

            String codigo = partes[0].trim();
            int cantidad = 0;
            try {
                cantidad = Integer.parseInt(partes[1].trim());
            } catch (NumberFormatException e) {
                System.out.println("Stock inválido en línea: " + linea);
                continue;
            }

            Producto p = buscarProductoPorCodigo(codigo);
            if (p != null) {
                p.setStock(p.getStock() + cantidad);
            } else {
                System.out.println("Producto no encontrado: " + codigo);
            }
        }

        guardarProductos();
        return true;

    } catch (IOException e) {
        System.out.println("Error al actualizar stock masivo: " + e.getMessage());
        return false;
    }
}

        public static boolean generarCSVStockActualizado(String rutaSalida) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaSalida))) {
        // Cabecera
        bw.write("Codigo,Nombre,Categoria,Stock");
        bw.newLine();

        for (Producto p : productos) {
            if (p != null) {
                bw.write(p.getCodigo() + "," +
                         p.getNombre() + "," +
                         p.getCategoria() + "," +
                         p.getStock());
                bw.newLine();
            }
        }

        System.out.println("CSV de stock actualizado creado correctamente en: " + rutaSalida);
        return true;

    } catch (IOException e) {
        System.out.println("Error al generar CSV de stock actualizado: " + e.getMessage());
        return false;
    }
}

        public static boolean generarCSVStockCompleto(String rutaSalida) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaSalida))) {
        // Cabecera completa
        bw.write("Codigo,Nombre,Categoria,Material,AtributoEspecifico,Stock");
        bw.newLine();

        for (Producto p : productos) {
            if (p != null) {
                bw.write(p.toCSV()); 
                bw.newLine();
            }
        }

        System.out.println("CSV completo de productos creado correctamente en: " + rutaSalida);
        return true;

    } catch (IOException e) {
        System.out.println("Error al generar CSV completo: " + e.getMessage());
        return false;
    }
}

       public static boolean generarStockMasivo(String rutaArchivo) {
    if (!Files.exists(Paths.get(rutaArchivo))) {
        System.out.println("Archivo no existe: " + rutaArchivo);
        return false;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
        String linea;
        br.readLine(); // Saltar encabezado
        while ((linea = br.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length != 2) continue;

            String codigo = partes[0].trim();
            int cantidad = 0;
            try {
                cantidad = Integer.parseInt(partes[1].trim());
            } catch (NumberFormatException e) {
                System.out.println("Cantidad inválida en línea: " + linea);
                continue;
            }

            Producto producto = buscarProductoPorCodigo(codigo);
            if (producto != null) {
                producto.setStock(cantidad); 
            } else {
                System.out.println("Producto no encontrado: " + codigo);
            }
        }

        guardarProductos(); 
        return true;
    } catch (IOException e) {
        System.out.println("Error al cargar stock: " + e.getMessage());
        return false;
    }
}  
}