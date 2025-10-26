package controlador;
import java.io.*;
import modelo.Administrador;
import modelo.Cliente;
import modelo.Usuario;
import modelo.Vendedor;
import javax.swing.JOptionPane;

public class UsuarioController {

    private static Usuario[] usuarios = new Usuario[100]; 
    private static int contadorUsuarios = 0; 
    
    public static boolean agregarUsuario(Usuario u) {
        if (u == null) return false;
        
        if (buscarUsuarioPorCodigo(u.getCodigo()) != null) return false; 
        
        if (contadorUsuarios < usuarios.length) {
            usuarios[contadorUsuarios] = u;
            contadorUsuarios++;
            return true;
        }
        return false;
    }
    
    public static void cargarUsuarios() {
        if (contadorUsuarios == 0) {
            String rutaArchivo = "data/usuarios.csv";

            try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
                br.readLine(); 
                String linea;
                
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(",");

                    if (datos.length >= 4 && contadorUsuarios < usuarios.length) {
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
                        
                        if (usuario != null) {
                            agregarUsuario(usuario);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Advertencia: Archivo usuarios.csv no encontrado. Creando lista vacía.");
            } catch (IOException e) {
                System.out.println("Error al cargar usuarios: " + e.getMessage());
            }
        }
    }
    
        public static int cargarVendedoresMasivo(String rutaArchivo) {
            int vendedoresAgregados = 0;
    
        final String[] HEADERS_REQUERIDOS = {"CODIGO", "NOMBRE", "CONTRASENA", "GENERO"};
    
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String headerLine = br.readLine();
                if (headerLine == null) {
            System.out.println("El archivo CSV está vacío o sin encabezados.");
            return 0;
        }

        String[] headers = headerLine.toUpperCase().split(",");
        int codigoIndex = -1, nombreIndex = -1, contrasenaIndex = -1, generoIndex = -1;

        for (int i = 0; i < headers.length; i++) {
            String header = headers[i].trim();
            if (header.equals("CODIGO")) codigoIndex = i;
            else if (header.equals("NOMBRE")) nombreIndex = i;
            else if (header.equals("CONTRASENA") || header.equals("CONTRASEÑA")) contrasenaIndex = i;
            else if (header.equals("GENERO") || header.equals("GÉNERO")) generoIndex = i;
        }

        if (codigoIndex == -1 || nombreIndex == -1 || contrasenaIndex == -1 || generoIndex == -1) {
            JOptionPane.showMessageDialog(null, 
                "Error: El archivo CSV debe contener los encabezados: " + String.join(", ", HEADERS_REQUERIDOS), 
                "Error de Formato", 
                JOptionPane.ERROR_MESSAGE);
            return 0;
        }

        String linea;
        while ((linea = br.readLine()) != null) {
            String[] datos = linea.split(",");
            
            int maxIndex = Math.max(Math.max(codigoIndex, nombreIndex), Math.max(contrasenaIndex, generoIndex));
            if (datos.length > maxIndex) {
                
                String codigo = datos[codigoIndex].trim();
                String contrasena = datos[contrasenaIndex].trim();
                String nombre = datos[nombreIndex].trim();
                String genero = datos[generoIndex].trim();

                if (codigo.isEmpty() || contrasena.isEmpty() || nombre.isEmpty() || genero.isEmpty()) continue;
                
                Vendedor nuevoVendedor = new Vendedor(codigo, contrasena, nombre, genero);
                if (agregarUsuario(nuevoVendedor)) { 
                    vendedoresAgregados++;
                } else {
                    System.out.println("Vendedor con código " + codigo + " duplicado o arreglo lleno. No se agregó.");
                }
            }
        }
        
        guardarUsuarios(); 
        
    } catch (FileNotFoundException e) {
        JOptionPane.showMessageDialog(null, "Archivo no encontrado: " + rutaArchivo, "Error de Archivo", JOptionPane.ERROR_MESSAGE);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(null, "Error de lectura del archivo: " + e.getMessage(), "Error de IO", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error inesperado durante la carga masiva: " + e.getMessage(), "Error General", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
    return vendedoresAgregados;
}

    public static Object autenticar(String codigo, String contraseña) {
        for (int i = 0; i < contadorUsuarios; i++) {
            Usuario u = usuarios[i];
            if (u.getCodigo().equals(codigo) && u.getContrasena().equals(contraseña)) {
                return u; 
            }
        }
        return null; 
    }
    
    public static boolean eliminarUsuarioPorCodigo(String codigo) {
        int indiceAEliminar = -1;
    
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
            return true;
        }
            return false;
}

    // ------------------- Gestión de Vendedores -------------------
    
    public static Usuario buscarUsuarioPorCodigo(String codigo) {    
        for (int i = 0; i < contadorUsuarios; i++) {
            Usuario u = usuarios[i];
            if (u.getCodigo().equals(codigo)) {
                return u;
            }
        }
        return null;
    }
    
    public static boolean eliminarUsuario(String codigo) {
        int indiceAEliminar = -1;
        
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
            return true;
        }
        return false;
    }


    public static boolean crearVendedor(String codigo, String nombre, String genero, String contraseña) {
        if (buscarUsuarioPorCodigo(codigo) != null) {
            return false; 
        }
        Vendedor nuevoVendedor = new Vendedor(codigo, contraseña, nombre, genero); 
        return agregarUsuario(nuevoVendedor);
    }

    public static boolean eliminarVendedor(String codigo) {
        Usuario u = buscarUsuarioPorCodigo(codigo);
        if (u != null && u instanceof Vendedor) {
            return eliminarUsuario(codigo); 
        }
        return false;
    }

    // --- PERSISTENCIA ---

    public static void guardarUsuarios() {
        String rutaArchivo = "data/usuarios.csv";

        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaArchivo))) {
            pw.println("codigo,contraseña,nombre,tipo,genero"); 

            for (int i = 0; i < contadorUsuarios; i++) {
                Usuario u = usuarios[i];
                String linea;
                String genero = ""; 
                String tipo = u.getTipo(); 

                if (u instanceof Vendedor) {
                    Vendedor v = (Vendedor) u;
                    genero = v.getGenero();
                } 
                
                linea = String.format("%s,%s,%s,%s,%s", 
                    u.getCodigo(), 
                    u.getContrasena(), 
                    u.getNombre(), 
                    tipo,
                    genero 
                );
                pw.println(linea);
            }
            System.out.println("Usuarios guardados correctamente en " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }
    
    
    public static Vendedor[] obtenerVendedores() {
        int contadorVendedores = 0;
        
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
}