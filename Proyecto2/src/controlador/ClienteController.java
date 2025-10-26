package controlador;
import modelo.Cliente;
import java.io.*; 
import java.util.HashMap;
import java.util.Map;

public class ClienteController {

    private static final int MAX_CLIENTES = 100;
    private static Cliente[] clientes = new Cliente[MAX_CLIENTES];
    private static String[] generos = new String[MAX_CLIENTES];
    private static String[] cumpleaños = new String[MAX_CLIENTES];
    private static String[] vendedores = new String[MAX_CLIENTES];
    private static int contador = 0;
    private static final String ARCHIVO_CLIENTES = "clientes.csv"; 

    private static void cargarClientesDesdeArchivoInterno() {
        contador = 0; 
        
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_CLIENTES))) {
            String linea;
            
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue; 
                
                if (contador >= MAX_CLIENTES) {
                    break;
                }

                String[] datos = linea.split(",");
                
                if (datos.length >= 6) { 
                    Cliente c = new Cliente(datos[0], datos[1], datos[2]); 
                    
                    clientes[contador] = c;
                    generos[contador] = datos[3];
                    cumpleaños[contador] = datos[4];
                    vendedores[contador] = datos[5];
                    contador++;
                } else {
                    System.err.println("❌ Cliente Ignorado (Columnas insuficientes): Encontró " + datos.length + " campos. Línea: " + linea);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de clientes (datos extra) no encontrado. Iniciando con lista vacía.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al leer el archivo de clientes.");
        }
    }
    
    public static void guardarClientesEnArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_CLIENTES))) {
            
            for (int i = 0; i < contador; i++) {
                Cliente c = clientes[i];
                String linea = String.format("%s,%s,%s,%s,%s,%s", 
                    c.getCodigo(), c.getContrasena(), c.getNombre(), generos[i], cumpleaños[i], vendedores[i]);
                
                pw.println(linea);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar los clientes en el archivo.");
        }
    }

    public static String cargarClientesMasivo(String rutaArchivo) {
        int clientesCargados = 0;
        int clientesRechazados = 0;
        
        cargarClientesDesdeArchivoInterno(); 
        
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            
            if ((linea = br.readLine()) == null) {
                return "ERROR: Archivo CSV vacío.";
            }
            
            String[] headers = linea.split(",");
            Map<String, Integer> colIndices = new HashMap<>();
            
            for (int i = 0; i < headers.length; i++) {
                colIndices.put(headers[i].trim().toLowerCase(), i);
            }

            if (!colIndices.containsKey("codigo") || !colIndices.containsKey("nombre") || 
                !colIndices.containsKey("genero") || !colIndices.containsKey("cumpleanos") ||
                !colIndices.containsKey("contrasena")) 
            {
                return "ERROR: El archivo debe contener las columnas: Codigo, Nombre, Genero, Contrasena y Cumpleanos.";
            }
            
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                
                String[] campos = linea.split(",");
                
                if (campos.length < colIndices.size()) {
                     clientesRechazados++;
                     continue;
                }
                
                try {
                    String codigo = campos[colIndices.get("codigo")].trim();
                    String nombre = campos[colIndices.get("nombre")].trim();
                    String genero = campos[colIndices.get("genero")].trim();
                    String cumpleanos = campos[colIndices.get("cumpleanos")].trim();
                    String contrasena = campos[colIndices.get("contrasena")].trim();
                    
                    if (codigo.isEmpty() || nombre.isEmpty() || contrasena.isEmpty()) {
                        clientesRechazados++;
                        continue;
                    }
                    if (buscarClienteEnArray(codigo) != null) { 
                         clientesRechazados++; 
                         continue;
                    }
                    if (contador >= MAX_CLIENTES) { 
                        clientesRechazados++;
                        break; 
                    }

                    Cliente nuevoCliente = new Cliente(codigo, contrasena, nombre); 
                    
                    clientes[contador] = nuevoCliente;
                    generos[contador] = genero;
                    cumpleaños[contador] = cumpleanos;
                    vendedores[contador] = "SISTEMA_CSV"; 
                    contador++;
                    clientesCargados++;

                    UsuarioController.agregarUsuario(nuevoCliente); 
                    
                } catch (Exception e) {
                    System.err.println("Error al procesar línea de cliente: " + linea + " | Error: " + e.getMessage());
                    clientesRechazados++;
                }
            }
            
            guardarClientesEnArchivo(); 
            UsuarioController.guardarUsuarios(); 

        } catch (FileNotFoundException e) {
            return "ERROR: El archivo seleccionado no fue encontrado.";
        } catch (IOException e) {
            return "ERROR de lectura del archivo: " + e.getMessage();
        }
        
        String resumen = String.format("Carga finalizada:\n- Clientes cargados con éxito: %d\n- Clientes omitidos (duplicados/errores): %d", 
                                       clientesCargados, clientesRechazados);
        
        return resumen;
    }



    private static Cliente buscarClienteEnArray(String codigo) {
        for (int i = 0; i < contador; i++) {
            if (clientes[i].getCodigo().equals(codigo)) {
                return clientes[i];
            }
        }
        return null;
    }
    
    public static Cliente buscarClientePorCodigo(String codigo) {
        cargarClientesDesdeArchivoInterno(); 
        
        for (int i = 0; i < contador; i++) {
            if (clientes[i].getCodigo().equals(codigo)) {
                return clientes[i];
            }
        }
        return null;
    }
    

    public static boolean agregarCliente(Cliente c, String genero, String cumple, String vendedor) {
        cargarClientesDesdeArchivoInterno();
        
        if (buscarClientePorCodigo(c.getCodigo()) != null) return false;
        if (contador >= MAX_CLIENTES) return false;

        clientes[contador] = c;
        generos[contador] = genero;
        cumpleaños[contador] = cumple;
        vendedores[contador] = vendedor;
        contador++;
        
        UsuarioController.agregarUsuario(c);
        
        guardarClientesEnArchivo(); 
        UsuarioController.guardarUsuarios(); 
        
        return true;
    }

    public static boolean actualizarCliente(
        String codigo,
        String nuevoNombre,
        String nuevoGenero,
        String nuevoCumple,
        String nuevaContrasena) {

        cargarClientesDesdeArchivoInterno();

        int indiceCliente = -1;
        for (int i = 0; i < contador; i++) {
            if (clientes[i].getCodigo().equals(codigo)) {
                indiceCliente = i;
                break;
            }
        }

        if (indiceCliente != -1) {
            clientes[indiceCliente].setNombre(nuevoNombre);
            clientes[indiceCliente].setContrasena(nuevaContrasena);
            generos[indiceCliente] = nuevoGenero;
            cumpleaños[indiceCliente] = nuevoCumple;

            guardarClientesEnArchivo(); 
            UsuarioController.guardarUsuarios(); 

            return true;
        }
        
        return false; 
    }
    
    public static boolean eliminarCliente(String codigo) {
        cargarClientesDesdeArchivoInterno();

        int indiceEliminar = -1;

        for (int i = 0; i < contador; i++) {
            if (clientes[i].getCodigo().equals(codigo)) {
                indiceEliminar = i;
                break;
            }
        }

        if (indiceEliminar != -1) {
            for (int i = indiceEliminar; i < contador - 1; i++) {
                clientes[i] = clientes[i + 1];
                generos[i] = generos[i + 1];
                cumpleaños[i] = cumpleaños[i + 1];
                vendedores[i] = vendedores[i + 1];
            }

            contador--;
            
            UsuarioController.eliminarUsuarioPorCodigo(codigo); 
            
            guardarClientesEnArchivo();
            UsuarioController.guardarUsuarios();

            return true;
        }

        return false; 
    }
                        

    public static String getGenero(String codigo) {
        cargarClientesDesdeArchivoInterno(); 
        for (int i = 0; i < contador; i++) {
            if (clientes[i].getCodigo().equals(codigo)) return generos[i];
        }
        return null;
    }

    public static String getCumpleaños(String codigo) {
        cargarClientesDesdeArchivoInterno(); 
        for (int i = 0; i < contador; i++) {
            if (clientes[i].getCodigo().equals(codigo)) return cumpleaños[i];
        }
        return null;
    }

    public static String getVendedor(String codigo) {
        cargarClientesDesdeArchivoInterno(); 
        for (int i = 0; i < contador; i++) {
            if (clientes[i].getCodigo().equals(codigo)) return vendedores[i];
        }
        return null;
    }

    public static Cliente[] obtenerClientes() {
        cargarClientesDesdeArchivoInterno(); 
        Cliente[] lista = new Cliente[contador];
        for (int i = 0; i < contador; i++) {
            lista[i] = clientes[i];
        }
        return lista;
    }
}