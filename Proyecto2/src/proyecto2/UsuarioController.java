package proyecto2;
import java.io.*;
import java.util.Vector;

public class UsuarioController {

    private static Vector<Usuario> usuarios = new Vector<>();

    // Cargar usuarios desde archivo CSV
    public static void cargarUsuarios() {
        usuarios.clear();

        String rutaArchivo = "data/usuarios.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");

                if (datos.length == 4) {
                    String codigo = datos[0].trim();
                    String contrasena = datos[1].trim();
                    String nombre = datos[2].trim();
                    String tipo = datos[3].trim();

                    Usuario usuario = null;

                    switch (tipo.toLowerCase()) {
                        case "administrador":
                            usuario = new Administrador(codigo, contrasena, nombre);
                            break;
                        case "vendedor":
                            usuario = new Vendedor(codigo, contrasena, nombre);
                            break;
                        case "cliente":
                            usuario = new Cliente(codigo, contrasena, nombre);
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
    public static Usuario autenticar(String codigo, String contrasena) {
        for (Usuario u : usuarios) {
            if (u.getCodigo().equals(codigo) && u.getContrasena().equals(contrasena)) {
                return u;
            }
        }
        return null; // no encontrado o incorrecto
    }
}
