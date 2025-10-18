package controlador;
import java.io.*;
import modelo.Administrador;
import modelo.Cliente;
import modelo.Usuario;
import modelo.Vendedor;

public class UsuarioController {

    private static Usuario[] usuarios = new Usuario[100]; 
    private static int contadorUsuarios = 0; 
    public static boolean agregarUsuario(Usuario u) {
        if (u == null) return false;
        
        if (contadorUsuarios < usuarios.length) {
            usuarios[contadorUsuarios] = u;
            contadorUsuarios++;
            return true;
        }
        return false;
    }
    
    //agregar usuarios
    public static void cargarUsuarios() {
        
        if (contadorUsuarios ==0){
            String rutaArchivo = "data/usuarios.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            br.readLine();
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length == 4 && contadorUsuarios < usuarios.length) {
                    String codigo = datos[0].trim();
                    String contraseña = datos[1].trim();
                    String nombre = datos[2].trim();
                    String tipo = datos[3].trim();
                    String genero = (datos.length > 4) ? datos[4].trim() : "";
                    Usuario usuario = null;

                    switch (tipo.toLowerCase()) {
                        case "administrador":
                            usuario = new Administrador(codigo, contraseña, nombre);
                            break;
                        case "vendedor":
                            usuario = new Vendedor(codigo, contraseña, nombre, genero);
                            break;
                        case "cliente":
                            usuario = new Cliente(codigo, contraseña, nombre);
                            break;
                        default:
                            System.out.println("Tipo de usuario desconocido: " + tipo);
                    }
                    
                    if (usuario != null){
                        agregarUsuario(usuario);
                    }
                }else if (datos.length > 0){
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
            }
        }

    // Método para autenticar usuario por código y contraseña
        public static Usuario autenticar(String codigo, String contraseña) {
            for (int i = 0; i < contadorUsuarios; i++) {
            Usuario u = usuarios[i];
            if (u.getCodigo().equals(codigo) && u.getContraseña().equals(contraseña)) {
                return u;
            }
        }
            return null; // no encontrado o incorrecto
    }

    // ------------------- Gestión de Vendedores -------------------
    // --- BUSCAR USUARIO (CORREGIDO) ---
        public static Usuario buscarUsuarioPorCodigo(String codigo) {      
            for (int i = 0; i < contadorUsuarios; i++) {
                Usuario u = usuarios[i];
                    if (u.getCodigo().equals(codigo)) {
                return u;
            }
        }
        return null;
    }
        // --- ELIMINAR USUARIO (MÉTODO ESPECÍFICO PARA ARREGLOS) ---
    // Este método es crucial para reemplazar usuarios.remove(u)
    public static boolean eliminarUsuario(String codigo) {
        int indiceAEliminar = -1;
        
        // 1. Buscar el índice
        for (int i = 0; i < contadorUsuarios; i++) {
            if (usuarios[i].getCodigo().equals(codigo)) {
                indiceAEliminar = i;
                break;
            }
        }

        if (indiceAEliminar != -1) {
            // 2. Mover los elementos restantes hacia adelante para llenar el espacio
            for (int i = indiceAEliminar; i < contadorUsuarios - 1; i++) {
                usuarios[i] = usuarios[i + 1];
            }
            // 3. Limpiar el último elemento y decrementar el contador
            usuarios[contadorUsuarios - 1] = null;
            contadorUsuarios--;
            return true;
        }
        return false;
    }

    // --- GESTIÓN DE VENDEDORES (ADAPTADA) ---

        public static boolean crearVendedor(String codigo, String nombre, String genero, String contraseña) {
            if (buscarUsuarioPorCodigo(codigo) != null) {
                return false; // ya existe un usuario con ese código
            }
        Vendedor nuevoVendedor = new Vendedor(codigo, contraseña, nombre, genero); 
        return agregarUsuario(nuevoVendedor); // Usamos el método seguro
    }

        public static boolean eliminarVendedor(String codigo) {
            Usuario u = buscarUsuarioPorCodigo(codigo);
            if (u != null && u instanceof Vendedor) {
            return eliminarUsuario(codigo); // Usamos el método de eliminación de arreglos
        }
            return false;
    }

    // --- PERSISTENCIA (MÉTODO CLAVE FALTANTE) ---

        public static void guardarUsuarios() {
            String rutaArchivo = "data/usuarios.csv";

            try (PrintWriter pw = new PrintWriter(new FileWriter(rutaArchivo))) {
            // Escribir la cabecera del archivo
                pw.println("codigo,contraseña,nombre,tipo,genero"); 

            // Recorrer todos los usuarios guardados
                for (int i = 0; i < contadorUsuarios; i++) {
                    Usuario u = usuarios[i];
                    String linea;
                
                // Usamos Polimorfismo y Herencia para obtener datos
                if (u instanceof Vendedor v) {
                    // El vendedor tiene datos extra
                    linea = String.format("%s,%s,%s,%s,%s", 
                        v.getCodigo(), 
                        v.getContraseña(), 
                        v.getNombre(), 
                        v.getTipo(), // Vendedor
                        v.getGenero()
                    );
                } else {
                    // Admin y Cliente solo tienen 4 campos principales
                    linea = String.format("%s,%s,%s,%s,%s", 
                        u.getCodigo(), 
                        u.getContraseña(), 
                        u.getNombre(), 
                        u.getTipo(),
                        "" // Campo de genero vacío
                    );
                }
                pw.println(linea);
            }
            System.out.println("Usuarios guardados correctamente en " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }
    
        public static Vendedor[] obtenerVendedores() {
            int contadorVendedores = 0;
        // 1. Contar cuántos vendedores hay
            for (int i = 0; i < contadorUsuarios; i++) {
                if (usuarios[i] instanceof Vendedor) {
                    contadorVendedores++;
            }
        }
        
        // 2. Crear el arreglo de salida y llenarlo
        Vendedor[] listaVendedores = new Vendedor[contadorVendedores];
        int j = 0;
        for (int i = 0; i < contadorUsuarios; i++) {
            if (usuarios[i] instanceof Vendedor v) {
                listaVendedores[j] = v;
                j++;
            }
        }
        return listaVendedores;
    }
}