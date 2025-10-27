package controlador;
import modelo.Cliente;
import java.io.*;

public class ClienteController {

    private static final int MAX_CLIENTES = 100;
    private static Cliente[] clientes = new Cliente[MAX_CLIENTES];
    private static String[] generos = new String[MAX_CLIENTES];
    private static String[] cumpleaños = new String[MAX_CLIENTES];
    private static String[] vendedores = new String[MAX_CLIENTES];
    private static int contador = 0;
    private static final String ARCHIVO_CLIENTES_SER = "clientes.ser";

    /*
     * NOTA IMPORTANTE: Para que la serialización funcione correctamente,
     * la clase 'modelo.Cliente' DEBE implementar java.io.Serializable.
     * Ejemplo: public class Cliente implements Serializable { ... }
     */

    private static void cargarClientesDesdeArchivo() {
        contador = 0;

        try (FileInputStream fileIn = new FileInputStream(ARCHIVO_CLIENTES_SER);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            contador = (int) objectIn.readObject();

            clientes = (Cliente[]) objectIn.readObject();
            generos = (String[]) objectIn.readObject(); 
            cumpleaños = (String[]) objectIn.readObject();
            vendedores = (String[]) objectIn.readObject();

            System.out.println("✅ Clientes cargados por Serialización: " + contador);

        } catch (FileNotFoundException e) {
            System.out.println("Archivo de clientes serializados no encontrado. Iniciando con lista vacía.");
            clientes = new Cliente[MAX_CLIENTES];
            generos = new String[MAX_CLIENTES];
            cumpleaños = new String[MAX_CLIENTES];
            vendedores = new String[MAX_CLIENTES];
            contador = 0;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error de I/O o versión de clase al cargar clientes serializados.");
            e.printStackTrace();
        }
    }

    public static void guardarClientesEnArchivo() {
        try (FileOutputStream fileOut = new FileOutputStream(ARCHIVO_CLIENTES_SER);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(contador);

            objectOut.writeObject(clientes);
            objectOut.writeObject(generos); 
            objectOut.writeObject(cumpleaños);
            objectOut.writeObject(vendedores);

            System.out.println("💾 Clientes serializados y guardados en " + ARCHIVO_CLIENTES_SER);

        } catch (IOException e) {
            System.err.println("Error al guardar los clientes por serialización.");
            e.printStackTrace();
        }
    }

    public static String cargarClientesMasivo(String rutaArchivo) {
        int clientesCargados = 0;
        int clientesRechazados = 0;

        cargarClientesDesdeArchivo();

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            if ((linea = br.readLine()) == null) {
                return "ERROR: Archivo CSV vacío.";
            }

            String[] headers = linea.split(",");
            
            int indiceCodigo = -1;
            int indiceNombre = -1;
            int indiceGenero = -1;
            int indiceCumpleanos = -1;
            int indiceContrasena = -1;

            for (int i = 0; i < headers.length; i++) {
                String header = headers[i].trim().toLowerCase();
                if (header.equals("codigo")) indiceCodigo = i;
                else if (header.equals("nombre")) indiceNombre = i;
                else if (header.equals("genero")) indiceGenero = i;
                else if (header.equals("cumpleanos")) indiceCumpleanos = i;
                else if (header.equals("contrasena")) indiceContrasena = i;
            }

            if (indiceCodigo == -1 || indiceNombre == -1 || indiceGenero == -1 ||
                indiceCumpleanos == -1 || indiceContrasena == -1)
            {
                return "ERROR: El archivo debe contener las columnas: Codigo, Nombre, Genero, Contrasena y Cumpleanos.";
            }

            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                String[] campos = linea.split(",");

                if (campos.length <= Math.max(indiceContrasena, 
                    Math.max(indiceCumpleanos, Math.max(indiceGenero, Math.max(indiceCodigo, indiceNombre))))) 
                {
                     clientesRechazados++;
                     continue;
                }

                try {
                    String codigo = campos[indiceCodigo].trim();
                    String nombre = campos[indiceNombre].trim();
                    String genero = campos[indiceGenero].trim();
                    String cumpleanos = campos[indiceCumpleanos].trim();
                    String contrasena = campos[indiceContrasena].trim();

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
        cargarClientesDesdeArchivo();
        return buscarClienteEnArray(codigo); 
    }

    public static boolean agregarCliente(Cliente c, String genero, String cumple, String vendedor) {
        cargarClientesDesdeArchivo();

        if (buscarClienteEnArray(c.getCodigo()) != null) return false;
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

        cargarClientesDesdeArchivo();

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
        cargarClientesDesdeArchivo();

        int indiceEliminar = -1;

        for (int i = 0; i < contador; i++) {
            if (clientes[i].getCodigo().equals(codigo)) {
                indiceEliminar = i;
                break;
            }
        }

        if (indiceEliminar != -1) {
            // Desplazamiento de elementos para simular la eliminación en el array
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
        cargarClientesDesdeArchivo();
        for (int i = 0; i < contador; i++) {
            if (clientes[i].getCodigo().equals(codigo)) return generos[i];
        }
        return null;
    }

    public static String getCumpleaños(String codigo) {
        cargarClientesDesdeArchivo();
        for (int i = 0; i < contador; i++) {
            if (clientes[i].getCodigo().equals(codigo)) return cumpleaños[i];
        }
        return null;
    }

    public static String getVendedor(String codigo) {
        cargarClientesDesdeArchivo();
        for (int i = 0; i < contador; i++) {
            if (clientes[i].getCodigo().equals(codigo)) return vendedores[i];
        }
        return null;
    }

    public static Cliente[] obtenerClientes() {
        cargarClientesDesdeArchivo();
        Cliente[] lista = new Cliente[contador];
        for (int i = 0; i < contador; i++) {
            lista[i] = clientes[i];
        }
        return lista;
    }
}