package controlador;

import modelo.Pedido;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import modelo.Cliente;

public class PedidoController {

    private static final int MAX_PEDIDOS = 200;
    private static Pedido[] pedidos = new Pedido[MAX_PEDIDOS];
    private static int contador = 0;
    private static int nextId = 1; 
    private static final String ARCHIVO_PEDIDOS = "pedidos.csv";
    private static final DateTimeFormatter FORMATTER = Pedido.FORMATTER; 

    // ------------------- PERSISTENCIA -------------------

    public static void cargarPedidos() {
        contador = 0;
        nextId = 1; 
        int maxId = 0; 
        
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_PEDIDOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (contador >= MAX_PEDIDOS) break;

                String[] datos = linea.split(",");
                if (datos.length == 6) {
                    try {
                        String id = datos[0];
                        String cliente = datos[1];
                        String desc = datos[2];
                        double total = Double.parseDouble(datos[3]);
                        LocalDateTime fecha = LocalDateTime.parse(datos[4], FORMATTER);
                        String estado = datos[5];
                        
                        try {
                            int currentId = Integer.parseInt(id);
                            if (currentId > maxId) {
                                maxId = currentId;
                            }
                        } catch (NumberFormatException ignored) {
                        }
                        
                        pedidos[contador] = new Pedido(id, cliente, desc, total, fecha, estado);
                        contador++;
                    } catch (Exception e) {
                        System.err.println("Error al parsear línea de pedido: " + linea + " | Error: " + e.getMessage());
                    }
                }
            }
            
            nextId = maxId + 1;
            
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de pedidos no encontrado. Iniciando con lista vacía.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error de lectura al cargar pedidos.");
        }
    }
    
    public static void guardarPedidos() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO_PEDIDOS))) {
            for (int i = 0; i < contador; i++) {
                Pedido p = pedidos[i];
                String linea = String.format("%s,%s,%s,%.2f,%s,%s",
                    p.getIdPedido(), 
                    p.getCodigoCliente(), 
                    p.getDescripcionItems(), 
                    p.getTotal(),
                    p.getFechaCreacion().format(FORMATTER), 
                    p.getEstado());
                pw.println(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al guardar los pedidos en el archivo.");
        }
    }
    
    // ------------------- NEGOCIO -------------------
    
    public static boolean agregarPedido(Pedido p) {
        if (contador >= MAX_PEDIDOS) return false;
        
        p.setIdPedido(String.valueOf(nextId)); 
        nextId++; 
        
        pedidos[contador] = p;
        contador++;
        guardarPedidos(); 
        return true;
    }
    
    public static Pedido[] obtenerPedidosActivos() {
        Pedido[] lista = new Pedido[contador];
        System.arraycopy(pedidos, 0, lista, 0, contador);
        return lista;
    }
    
    public static Pedido buscarPedidoPorId(String id) {
        for (int i = 0; i < contador; i++) {
            if (pedidos[i].getIdPedido().equals(id)) {
                return pedidos[i];
            }
        }
        return null;
    }
    
    public static boolean actualizarEstadoPedido(String idPedido, String nuevoEstado) {
        Pedido p = buscarPedidoPorId(idPedido);
        if (p != null) {
            p.setEstado(nuevoEstado);
            guardarPedidos(); 
            return true;
        }
        return false;
    }
}