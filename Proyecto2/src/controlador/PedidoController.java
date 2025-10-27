package controlador;

import modelo.Pedido;
import java.io.*;
import modelo.Cliente; 

public class PedidoController {

    private static final int MAX_PEDIDOS = 200;
    private static final String RUTA_ARCHIVO_SER = "pedidos.ser";

    private static Pedido[] pedidos = new Pedido[MAX_PEDIDOS];
    private static int contador = 0; 
    private static int nextId = 1;  

 
    public static void cargarPedidos() {
        contador = 0;
        nextId = 1;
        pedidos = new Pedido[MAX_PEDIDOS];

        try (FileInputStream fileIn = new FileInputStream(RUTA_ARCHIVO_SER);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            contador = (int) objectIn.readObject();
            nextId = (int) objectIn.readObject();
            pedidos = (Pedido[]) objectIn.readObject();

            System.out.println("Carga de Pedidos exitosa. Registros cargados: " + contador);

        } catch (FileNotFoundException e) {
            System.out.println("Archivo de pedidos serializado no encontrado. Inicializando lista vacía.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar o deserializar el archivo de pedidos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    public static void guardarPedidos() {
        try (FileOutputStream fileOut = new FileOutputStream(RUTA_ARCHIVO_SER);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(contador);
            objectOut.writeObject(nextId);
            objectOut.writeObject(pedidos);
            
        } catch (IOException e) {
            System.err.println("Error al guardar los pedidos por serialización: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // ------------------- NEGOCIO -------------------
    
    public static boolean agregarPedido(Pedido p) {
        cargarPedidos(); 

        if (contador >= MAX_PEDIDOS) {
            System.err.println("ERROR: Límite máximo de pedidos alcanzado.");
            return false;
        }
        
        p.setIdPedido(String.valueOf(nextId)); 
        nextId++; 
        
        pedidos[contador] = p;
        contador++;
        
        guardarPedidos(); 
        return true;
    }
    

    public static Pedido[] obtenerPedidosActivos() {
        cargarPedidos();
        
        Pedido[] lista = new Pedido[contador];
        System.arraycopy(pedidos, 0, lista, 0, contador);
        return lista;
    }
    
    public static Pedido buscarPedidoPorId(String id) {
        cargarPedidos();
        
        for (int i = 0; i < contador; i++) {
            if (pedidos[i].getIdPedido().equals(id)) {
                return pedidos[i];
            }
        }
        return null;
    }
    

    public static boolean actualizarEstadoPedido(String idPedido, String nuevoEstado) {
        cargarPedidos();
        
        Pedido p = buscarPedidoPorId(idPedido);
        if (p != null) {
            p.setEstado(nuevoEstado);
            guardarPedidos(); 
            return true;
        }
        return false;
    }
}