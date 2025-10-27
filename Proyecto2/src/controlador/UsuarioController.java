package controlador;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.io.*;
import modelo.Administrador;
import modelo.Cliente;
import modelo.Usuario;
import modelo.Vendedor;

public class UsuarioController {

    private static Usuario[] usuarios = new Usuario[100]; 
    private static int contadorUsuarios = 0; 
    
    private static final String ARCHIVO_USUARIOS_SER = "usuarios.ser";
    private static final Logger logger = Logger.getLogger(UsuarioController.class.getName());
    

    public static void cargarUsuarios() {

        usuarios = new Usuario[100];
        contadorUsuarios = 0;
        
        File archivo = new File(ARCHIVO_USUARIOS_SER);
        
        if (archivo.exists() && archivo.length() > 0) {
             try (FileInputStream fileIn = new FileInputStream(ARCHIVO_USUARIOS_SER);
                  ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

                 contadorUsuarios = (int) objectIn.readObject();
                 usuarios = (Usuario[]) objectIn.readObject();

                 System.out.println("Usuarios cargados por serialización: " + contadorUsuarios);

             } catch (IOException | ClassNotFoundException e) {
                 System.out.println("Error al cargar o deserializar usuarios: " + e.getMessage());
                 inicializarAdministrador(); 
             }
        } else {
            System.out.println("Advertencia: Archivo serializado " + ARCHIVO_USUARIOS_SER + " no encontrado. Inicializando Admin.");
            inicializarAdministrador();
        }
    }
    
        public static Cliente[] obtenerClientes() {
    
    int contadorClientes = 0;
    
    for (int i = 0; i < contadorUsuarios; i++) {
        if (usuarios[i] instanceof Cliente) {
            contadorClientes++;
        }
    }
    
    Cliente[] listaClientes = new Cliente[contadorClientes];
    int j = 0;
    
    for (int i = 0; i < contadorUsuarios; i++) {
        if (usuarios[i] instanceof Cliente) {
            listaClientes[j] = (Cliente) usuarios[i];
            j++;
        }
    }
    return listaClientes;
}
    
    
    
    public static void guardarUsuarios() {
        try (FileOutputStream fileOut = new FileOutputStream(ARCHIVO_USUARIOS_SER);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(contadorUsuarios);
            objectOut.writeObject(usuarios);
            
            System.out.println("Usuarios guardados correctamente por serialización en " + ARCHIVO_USUARIOS_SER);

        } catch (IOException e) {
            System.out.println("Error al guardar usuarios por serialización: " + e.getMessage());
        }
    }
    

    public static void inicializarAdministrador() {
        if (contadorUsuarios == 0) {
            
            Administrador admin = new Administrador("admin", "IPC1A", "Administrador Principal"); 
            
            if (buscarUsuarioPorCodigoSoloMemoria("admin") == null) {
                if (contadorUsuarios < usuarios.length) {
                    usuarios[contadorUsuarios] = admin;
                    contadorUsuarios++;
                    guardarUsuarios(); // GUARDA INMEDIATAMENTE
                    System.out.println("✅ Administrador inicial creado: admin / IPC1A");
                }
            }
        }
    }
    

    private static Usuario buscarUsuarioPorCodigoSoloMemoria(String codigo) {
        for (int i = 0; i < contadorUsuarios; i++) {
            Usuario u = usuarios[i];
            if (u != null && u.getCodigo().equals(codigo)) {
                return u;
            }
        }
        return null;
    }
    
    public static boolean agregarUsuario(Usuario u) {
        if (u == null) return false;
        
        if (buscarUsuarioPorCodigoSoloMemoria(u.getCodigo()) != null) return false; 
        
        if (contadorUsuarios < usuarios.length) {
            usuarios[contadorUsuarios] = u;
            contadorUsuarios++;
            guardarUsuarios(); // Guardar después de modificar
            return true;
        }
        return false;
    }
    
    public static Object autenticar(String codigo, String contraseña) {
        if (contadorUsuarios == 0) {
            cargarUsuarios(); 
        }

        for (int i = 0; i < contadorUsuarios; i++) {
            Usuario u = usuarios[i];
            if (u.getCodigo().equals(codigo) && u.getContrasena().equals(contraseña)) {
                return u; 
            }
        }
        return null; 
    }
    
    public static Usuario buscarUsuarioPorCodigo(String codigo) {   
        // ⭐ LA CLAVE: ELIMINAR LA LLAMADA A cargarUsuarios()
        // Asumimos que la carga inicial ya se hizo en autenticar o en el Main.
        
        for (int i = 0; i < contadorUsuarios; i++) {
            Usuario u = usuarios[i];
            if (u != null && u.getCodigo().equals(codigo)) {
                return u;
            }
        }
        return null;
    }
    
    public static boolean eliminarUsuarioPorCodigo(String codigo) {
        // ⭐ LA CLAVE: ELIMINAR LA LLAMADA A cargarUsuarios()
        
        int indiceAEliminar = -1;
        // ... (resto de la lógica de eliminación) ...

        for (int i = 0; i < contadorUsuarios; i++) {
            if (usuarios[i].getCodigo().equals(codigo)) {
                indiceAEliminar = i;
                break;
            }
        }

        if (indiceAEliminar != -1) {
            for (int i = indiceAEliminar; i < contadorUsuarios - 1; i++) {
                usuarios[i] = usuarios[i + 1];
            }
            usuarios[contadorUsuarios - 1] = null; 
            contadorUsuarios--;
            guardarUsuarios(); // Guardar después de modificar
            return true;
        }
        return false;
    }

    // --- Métodos de Gestión (Aseguramos que llamen a las funciones de persistencia) ---
    
    public static boolean eliminarUsuario(String codigo) {
        return eliminarUsuarioPorCodigo(codigo); 
    }

    public static boolean crearVendedor(String codigo, String nombre, String genero, String contraseña) {
        // No hay riesgo aquí porque solo usa buscarUsuarioPorCodigo() y agregarUsuario()
        if (buscarUsuarioPorCodigo(codigo) != null) {
            return false; 
        }
        Vendedor nuevoVendedor = new Vendedor(codigo, contraseña, nombre, genero); 
        return agregarUsuario(nuevoVendedor); 
    }

    public static boolean eliminarVendedor(String codigo) {
        // No hay riesgo aquí.
        Usuario u = buscarUsuarioPorCodigo(codigo);
        if (u != null && u instanceof Vendedor) {
            return eliminarUsuario(codigo); 
        }
        return false;
    }
    
    public static Vendedor[] obtenerVendedores() {
        // ⭐ LA CLAVE: ELIMINAR LA LLAMADA A cargarUsuarios()
        // Si no se carga al inicio, este método no funcionará,
        // pero la carga ya se hace de forma controlada en autenticar.
        
        int contadorVendedores = 0;
        // ... (resto de la lógica de obtener vendedores) ...
        
        for (int i = 0; i < contadorUsuarios; i++) {
            if (usuarios[i] instanceof Vendedor) {
                contadorVendedores++;
            }
        }
        
        Vendedor[] listaVendedores = new Vendedor[contadorVendedores];
        int j = 0;
        for (int i = 0; i < contadorUsuarios; i++) {
            if (usuarios[i] instanceof Vendedor) {
                listaVendedores[j] = (Vendedor) usuarios[i];
                j++;
            }
        }
        return listaVendedores;
    }

    // --- MÉTODOS MASIVOS DESHABILITADOS ---
    public static int cargarVendedoresMasivoCSV(String rutaArchivo) {
    // 1. Cargar el estado actual antes de agregar nuevos
    cargarUsuarios(); // Asegura que el array 'usuarios' esté actualizado

    int vendedoresCargados = 0;
    int lineaActual = 0;

    try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
        String linea;
        
        while ((linea = br.readLine()) != null) {
            lineaActual++;
            
            if (lineaActual == 1) continue; // Saltar el encabezado si lo hay (ej: codigo,nombre,genero,contrasena)
            
            String[] datos = linea.split(",");
            
            // Asegurar que tenemos 4 columnas
            if (datos.length < 4) {
                logger.log(Level.WARNING, "Línea {0} incompleta. Se saltó la línea: {1}", new Object[]{lineaActual, linea});
                continue; 
            }
            
            String codigo = datos[0].trim();
            String nombre = datos[1].trim();
            String genero = datos[2].trim();
            String contrasena = datos[3].trim(); // Asegúrate de manejar el hash de contraseña si lo usas

            // 2. Crear el objeto Vendedor
            Vendedor nuevoVendedor = new Vendedor(codigo, nombre, genero, contrasena); 
            
            // 3. Agregar el vendedor, verificando duplicados y espacio
            // Se asume que agregarUsuario() contiene la lógica de validación
            if (agregarUsuario(nuevoVendedor)) {
                 vendedoresCargados++;
            }
        }

        // 4. Guardar todo al final de la carga masiva
        guardarUsuarios(); 
        
    } catch (IOException e) {
        logger.log(Level.SEVERE, "Error de lectura del archivo CSV de Vendedores: " + e.getMessage(), e);
    }
    
    return vendedoresCargados;
}
    
    public static int cargarClientesMasivoCSV(String rutaArchivo) {
    cargarUsuarios(); // Asegura que el array 'usuarios' esté actualizado

    int clientesCargados = 0;
    int lineaActual = 0;

    try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(rutaArchivo))) {
        String linea;
        
        while ((linea = br.readLine()) != null) {
            lineaActual++;
            
            // ⭐ TRATAMIENTO DEL ENCABEZADO: El primer ejemplo (CL-001...) es el encabezado
            // Puedes usar una bandera o verificar el formato del encabezado.
            // Si el archivo siempre empieza con ese formato no estándar (CL-001,IPC1A...), 
            // siempre saltamos la primera línea.
            if (lineaActual == 1) continue; 
            
            String[] datos = linea.split(",");
            
            // ⭐ CAMBIO CRUCIAL: Esperar 6 columnas (y usar solo las primeras 5)
            // Si tiene menos de 5 datos (los esenciales), la salta. Si tiene 6, usa los primeros 5.
            if (datos.length < 5) { 
                logger.log(java.util.logging.Level.WARNING, "Línea {0} incompleta. Se saltó la línea: {1}", new Object[]{lineaActual, linea});
                continue; 
            }
            
            String codigo = datos[0].trim();
            String contrasena = datos[1].trim(); // Este campo es la contraseña
            String nombre = datos[2].trim();
            String genero = datos[3].trim();
            String cumpleanos = datos[4].trim(); // Este campo es el cumpleaños
            // El campo datos[5].trim() se ignora (SISTEMA_CSV)

            // 1. Crear el objeto Cliente (Llamando al constructor de 5 argumentos)
            // Ya que el archivo tiene 5 datos relevantes, usamos el constructor de 5.
            modelo.Cliente nuevoCliente = new modelo.Cliente(codigo, contrasena, nombre, genero, cumpleanos); 
            
            // 2. Agregar el cliente
            // Recuerda: el método agregarUsuario() usará guardarUsuarios() si tiene éxito.
            if (agregarUsuario(nuevoCliente)) {
                 clientesCargados++;
            }
        }

        // 3. Guardar todo al final de la carga masiva (solo por si no se guardó en el bucle)
        guardarUsuarios(); 
        
    } catch (java.io.IOException e) {
        logger.log(java.util.logging.Level.SEVERE, "Error de lectura del archivo CSV de Clientes: " + e.getMessage(), e);
    }
    
    return clientesCargados;
}
    
}
    
    
