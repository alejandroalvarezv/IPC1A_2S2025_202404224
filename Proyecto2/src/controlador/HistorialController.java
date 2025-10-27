package controlador;
import modelo.Historial;
import java.io.*;

public class HistorialController {

    private static final int MAX_REGISTROS = 5000; 
    private static final String RUTA_ARCHIVO_SER = "historial.ser";

    private static Historial[] registros = new Historial[MAX_REGISTROS];
    private static int contador = 0; 
    
    // --- MÉTODOS DE PERSISTENCIA BINARIA (.SER) ---

    /**
     * Carga el contador y el array de registros desde el archivo serializado.
     */
    private static void cargarHistorial() {
        contador = 0;
        registros = new Historial[MAX_REGISTROS];

        try (FileInputStream fileIn = new FileInputStream(RUTA_ARCHIVO_SER);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            contador = (int) objectIn.readObject();
            // 2. Leer el array completo
            registros = (Historial[]) objectIn.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("Archivo de historial serializado no encontrado. Inicializando vacío.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar o deserializar el archivo de historial: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    private static void guardarHistorial() {
        try (FileOutputStream fileOut = new FileOutputStream(RUTA_ARCHIVO_SER);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(contador);
            objectOut.writeObject(registros);
            
            System.out.println("💾 Historial serializado guardado.");

        } catch (IOException e) {
            System.err.println("Error al guardar el historial por serialización: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void agregarHistorial(Historial h) {
        cargarHistorial(); 
        
        if (contador < MAX_REGISTROS) {
            registros[contador] = h;
            contador++;
            
            guardarHistorial();
        } else {
            System.err.println("ERROR: Límite de registros de historial alcanzado.");
        }
    }

    /**
     * Obtiene el historial de un producto específico, iterando sobre el array cargado.
     */
    public static String obtenerHistorial(String codigoProducto) {
        cargarHistorial();
        
        StringBuilder sb = new StringBuilder();
        boolean hayRegistros = false;
        
        for (int i = 0; i < contador; i++) {
            Historial h = registros[i];
            
            if (h != null && h.getCodigoProducto().equals(codigoProducto)) {
                hayRegistros = true;
                sb.append("Cantidad: ").append(h.getCantidad())
                  .append(" | Fecha: ").append(h.getFecha())
                  .append("\n");
            }
        }
        
        if (!hayRegistros) {
            sb.append("No hay historial para este producto.");
        }
        
        return sb.toString();
    }
}