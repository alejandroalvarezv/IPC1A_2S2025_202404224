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

public class ProductoController {
    private static Producto[] productos = new Producto[100];
    private static int contadorProductos = 0;
    private static final String ARCHIVO_PRODUCTOS = "data/productos.csv";
    private static boolean productosCargados = false;
    
    
    public static void cargarProductos() {
        if (productosCargados || !Files.exists(Paths.get(ARCHIVO_PRODUCTOS))) {
            productosCargados = true;
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_PRODUCTOS))) {
            String linea;
            br.readLine(); // Saltar el encabezado (header)

            while ((linea = br.readLine()) != null && contadorProductos < productos.length) {
                String[] partes = linea.split(",");
                // Asegurar que hay al menos 5 partes para Código, Nombre, Cat, Mat, Attr
                if (partes.length < 5) {
                    System.out.println("ADVERTENCIA: Línea CSV con formato incorrecto o incompleto: " + linea);
                    continue; 
                }
                
                String codigo = partes[0];
                String nombre = partes[1];
                String categoria = partes[2];
                String material = partes[3];
                String atributoEspecifico = partes[4]; 

                Producto nuevoProducto = null;
                
                // Lógica de recreación del objeto (casting inverso)
                // ⚠️ La categoría AHORA usa el acento para COINCIDIR con la subclase Tecnología.java
                if (categoria.equals("Tecnología")) { 
                    try {
                        // El toString() de Tecnología ahora tiene " meses", por lo que esta lógica funciona.
                        int garantia = Integer.parseInt(atributoEspecifico.replace(" meses", "").trim());
                        nuevoProducto = new Tecnologia(codigo, nombre, material, garantia);
                    } catch (NumberFormatException ignored) { 
                        System.out.println("ERROR: Fallo al parsear la garantía para " + codigo);
                    }
                } else if (categoria.equals("Alimento")) {
                    // El producto Alimento se crea sin problema
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
            // El producto se debe buscar en el array de productos cargados
            if (productos[i] != null && productos[i].getCodigo().equals(codigo)) {
                return productos[i];
            }
        }
        return null;
    }

    public static boolean agregarProducto(Producto p) {
        // ... (Tu código es correcto) ...
        if (p == null || contadorProductos >= productos.length) {
            return false;
        }
        
        if (buscarProductoPorCodigo(p.getCodigo()) != null) {
            System.out.println("Error: El código de producto ya existe.");
            return false;
        }

        productos[contadorProductos] = p;
        contadorProductos++;
        return true;
    }
    
    public static Producto[] obtenerProductos() {
        cargarProductos();
        
        Producto[] listaActual = new Producto[contadorProductos];
        System.arraycopy(productos, 0, listaActual, 0, contadorProductos);
    return listaActual;
    }
    
    public static void guardarProductos() {
        // ... (Tu código es correcto, sobrescribe con la lista completa) ...
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
