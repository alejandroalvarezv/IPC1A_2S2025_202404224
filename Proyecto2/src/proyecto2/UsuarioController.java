package proyecto2;

import java.io.*;
import java.util.Vector;

public class UsuarioController {

    private static Vector<Usuario> usuarios = new Vector<>();

    // Cargar usuarios desde archivo CSV general
    public static void cargarUsuarios() {
        usuarios.clear();

        String rutaArchivo = "data/usuarios.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length == 4) {
                    String codigo = datos[0].trim();
                    String contraseña = datos[1].trim();
                    String nombre = datos[2].trim();
                    String tipo = datos[3].trim();

                    Usuario usuario = null;

                    switch (tipo.toLowerCase()) {
                        case "administrador":
                            usuario = new Administrador(codigo, contraseña, nombre);
                            break;
                        case "vendedor":
                            // Aquí falta genero y ventas confirmadas en CSV, pero en usuarios.csv solo están 4 campos.
                            // Para cargar vendedores masivamente usa el método cargarVendedoresDesdeCSV aparte.
                            // Por ahora, solo crea el vendedor con estos datos.
                            usuario = new Vendedor(codigo, contraseña, nombre);
                            break;
                        case "cliente":
                            usuario = new Cliente(codigo, contraseña, nombre);
                            break;
                        default:
                            System.out.println("Tipo de usuario desconocido: " + tipo);
                    }

                    if (usuario != null) {
                        usuarios.add(usuario);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
    }

    // Método para autenticar usuario por código y contraseña
    public static Usuario autenticar(String codigo, String contraseña) {
        for (Usuario u : usuarios) {
            if (u.getCodigo().equals(codigo) && u.getContraseña().equals(contraseña)) {
                return u;
            }
        }
        return null; // no encontrado o incorrecto
    }

    // ------------------- Gestión de Vendedores -------------------

    // Crear vendedor
    public static boolean crearVendedor(String codigo, String nombre, String genero, String contraseña) {
        if (buscarUsuarioPorCodigo(codigo) != null) {
            return false; // ya existe un usuario con ese código
        }
        Vendedor nuevoVendedor = new Vendedor(codigo, contraseña, nombre); // 0 ventas confirmadas
        usuarios.add(nuevoVendedor);
        return true;
    }

    // Actualizar vendedor (solo nombre y contraseña)
    public static boolean actualizarVendedor(String codigo, String nuevoNombre, String nuevaContraseña) {
        Usuario u = buscarUsuarioPorCodigo(codigo);
        if (u != null && u instanceof Vendedor) {
            Vendedor v = (Vendedor) u;
            v.setNombre(nuevoNombre);
            v.setContraseña(nuevaContraseña);
            return true;
        }
        return false;
    }

    // Eliminar vendedor por código
    public static boolean eliminarVendedor(String codigo) {
        Usuario u = buscarUsuarioPorCodigo(codigo);
        if (u != null && u instanceof Vendedor) {
            return usuarios.remove(u);
        }
        return false;
    }

    // Buscar usuario por código
    public static Usuario buscarUsuarioPorCodigo(String codigo) {
        for (Usuario u : usuarios) {
            if (u.getCodigo().equals(codigo)) {
                return u;
            }
        }
        return null;
    }

    // Cargar vendedores desde CSV específico
    public static void cargarVendedoresDesdeCSV(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    String codigo = datos[0].trim();
                    String nombre = datos[1].trim();
                    String genero = datos[2].trim();
                    String contraseña = datos[3].trim();

                    // Validar unicidad
                    if (buscarUsuarioPorCodigo(codigo) == null) {
                        crearVendedor(codigo, nombre, genero, contraseña);
                    } else {
                        System.out.println("Código duplicado, vendedor no creado: " + codigo);
                    }
                } else {
                    System.out.println("Formato incorrecto en línea: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al cargar vendedores desde CSV: " + e.getMessage());
        }
    }
}