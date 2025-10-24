package controlador;

import modelo.Cliente;

public class ClienteController {

    private static final int MAX_CLIENTES = 100;
    private static Cliente[] clientes = new Cliente[MAX_CLIENTES];
    private static String[] generos = new String[MAX_CLIENTES];
    private static String[] cumpleaños = new String[MAX_CLIENTES];
    private static String[] vendedores = new String[MAX_CLIENTES];
    private static int contador = 0;

    public static Cliente buscarClientePorCodigo(String codigo) {
        for (int i = 0; i < contador; i++) {
            if (clientes[i].getCodigo().equals(codigo)) {
                return clientes[i];
            }
        }
        return null;
    }

    public static boolean agregarCliente(Cliente c, String genero, String cumple, String vendedor) {
        if (buscarClientePorCodigo(c.getCodigo()) != null) return false;
        if (contador >= MAX_CLIENTES) return false;

        clientes[contador] = c;
        generos[contador] = genero;
        cumpleaños[contador] = cumple;
        vendedores[contador] = vendedor;
        contador++;
        return true;
    }

    public static String getGenero(String codigo) {
        for (int i = 0; i < contador; i++) {
            if (clientes[i].getCodigo().equals(codigo)) return generos[i];
        }
        return null;
    }

    public static String getCumpleaños(String codigo) {
        for (int i = 0; i < contador; i++) {
            if (clientes[i].getCodigo().equals(codigo)) return cumpleaños[i];
        }
        return null;
    }

    public static String getVendedor(String codigo) {
        for (int i = 0; i < contador; i++) {
            if (clientes[i].getCodigo().equals(codigo)) return vendedores[i];
        }
        return null;
    }

    public static Cliente[] obtenerClientes() {
        Cliente[] lista = new Cliente[contador];
        for (int i = 0; i < contador; i++) {
            lista[i] = clientes[i];
        }
        return lista;
    }
}
