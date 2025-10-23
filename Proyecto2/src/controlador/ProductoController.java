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
                if (partes.length < 5) {
                    System.out.println("ADVERTENCIA: Línea CSV con formato incorrecto o incompleto: " + linea);
                    continue; 
                }
                
                String codigo = partes[0].trim();
                String nombre = partes[1].trim();
                String categoria = partes[2].trim();
                String material = partes[3].trim();
                String atributoEspecifico = partes[4].trim(); 

                Producto nuevoProducto = null;
                
                if (categoria.equals("Tecnología")) { 
                    try {
                        int garantia = Integer.parseInt(atributoEspecifico.replace(" meses", "").trim());
                        nuevoProducto = new Tecnologia(codigo, nombre, material, garantia);
                    } catch (NumberFormatException ignored) { 
                        System.out.println("ERROR: Fallo al parsear la garantía para " + codigo);
                    }
                } else if (categoria.equals("Alimento")) {
                    nuevoProducto = new Alimento(codigo, nombre, material, atributoEspecifico);
                } else if (categoria.equals("Generales")) {
                    nuevoProducto = new Producto(codigo, nombre, categoria, material);
                }

                if (nuevoProducto != null && buscarProductoPorCodigo(codigo) == null) {
                    productos[contadorProductos++] = nuevoProducto;
                }
            }
            productosCargados = true;
            System.out.println("DEBUG: Se cargaron " + contadorProductos + " productos en memoria.");
        } catch (IOException e) {
            System.out.println("Error al cargar productos: " + e.getMessage());
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

    public static boolean eliminarProducto(String codigo) {
        int indiceAEliminar = -1;

        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] != null && productos[i].getCodigo().equals(codigo)) {
                indiceAEliminar = i;
                break;
            }
        }

        if (indiceAEliminar == -1) {
            logger.warning("Intento de eliminar código no existente: " + codigo);
            return false;
        }

        for (int i = indiceAEliminar; i < contadorProductos - 1; i++) {
            productos[i] = productos[i + 1];
        }

        productos[contadorProductos - 1] = null;
        contadorProductos--;

        guardarProductos(); 
        logger.info("Producto eliminado exitosamente y archivo actualizado: " + codigo);
        return true;
    }

    public static Producto buscarProducto(String codigo) {
        return buscarProductoPorCodigo(codigo);
    }
    

    /**
     * Método auxiliar privado: Añade el producto a la memoria (Array) sin guardarlo en el archivo.
     * @param p Producto a añadir.
     * @return true si se agregó, false si hay error o duplicado.
     */
    private static boolean agregarProductoInterno(Producto p) {
        if (p == null || contadorProductos >= productos.length) {
            return false;
        }
        
        if (buscarProductoPorCodigo(p.getCodigo()) != null) {
            return false;
        }

        productos[contadorProductos] = p;
        contadorProductos++;
        return true;
    }
    
    public static boolean agregarProducto(Producto p) {
        if (agregarProductoInterno(p)) {
            guardarProductos(); 
            return true;
        }
        System.out.println("Error: El código de producto ya existe o la capacidad está llena.");
        return false;
    }
    

    public static void cargarProductosMasivo(String rutaArchivo) {
        int productosAgregados = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            br.readLine();
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length < 5) {
                    System.out.println("Error de formato (esperaba 5 columnas): " + linea);
                    continue;
                }

                String codigo = datos[0].trim();
                String nombre = datos[1].trim();
                String categoria = datos[2].trim();
                String material = datos[3].trim();
                String atributoEspecifico = datos[4].trim();

                if (codigo.isEmpty() || nombre.isEmpty() || categoria.isEmpty()) {
                    System.out.println("Error: Datos incompletos → " + linea);
                    continue;
                }

                if (buscarProductoPorCodigo(codigo) != null) {
                    System.out.println("Advertencia: Producto con código " + codigo + " ya existe. Saltando...");
                    continue;
                }

                Producto nuevoProducto = null;

                if (categoria.equalsIgnoreCase("Tecnología")) {
                    try {
                        int garantia = Integer.parseInt(atributoEspecifico.replace(" meses", "").trim());
                        nuevoProducto = new Tecnologia(codigo, nombre, material, garantia);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Garantía inválida para producto " + codigo);
                        continue;
                    }
                } else if (categoria.equalsIgnoreCase("Alimento")) {
                    nuevoProducto = new Alimento(codigo, nombre, material, atributoEspecifico);
                } else if (categoria.equalsIgnoreCase("Generales")) {
                    nuevoProducto = new Producto(codigo, nombre, categoria, material);
                }

                if (nuevoProducto != null && agregarProductoInterno(nuevoProducto)) {
                    productosAgregados++;
                    System.out.println("Producto cargado en memoria: " + codigo);
                }
            }

            if (productosAgregados > 0) {
                guardarProductos(); 
                System.out.println("INFO: Se agregaron " + productosAgregados + " productos nuevos al archivo CSV.");
            }
            System.out.println("Carga masiva completada desde: " + rutaArchivo);

        } catch (IOException e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
        }
    }
    
    public static boolean actualizarProducto(Producto productoAEditar) {
        if (productoAEditar == null) {
            return false;
        }

        String codigo = productoAEditar.getCodigo();
        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] != null && productos[i].getCodigo().equals(codigo)) {
                productos[i] = productoAEditar;
                
                guardarProductos(); 
                return true;
            }
        }
        return false;
    }
    
    public static Producto[] obtenerProductos() {
        cargarProductos();
        
        Producto[] listaActual = new Producto[contadorProductos];
        System.arraycopy(productos, 0, listaActual, 0, contadorProductos);
    return listaActual;
    }
    
    public static void guardarProductos() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_PRODUCTOS))) {
            bw.write("Codigo,Nombre,Categoria,Material,AtributoEspecifico");
            bw.newLine();

        for (int i = 0; i < contadorProductos; i++) {
            if (productos[i] !=null){
                bw.write(productos[i].toString());
                bw.newLine();
            }
        }
        System.out.println("Productos guardados correctamente en " + ARCHIVO_PRODUCTOS);
    } catch (IOException e) {
        System.out.println("Error al guardar productos: " + e.getMessage());
        }
    }    
}