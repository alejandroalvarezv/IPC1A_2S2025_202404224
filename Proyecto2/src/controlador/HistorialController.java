package controlador;
import modelo.Historial;
import java.io.*;

public class HistorialController {

    private static final String RUTA_ARCHIVO = "historial.txt";

    public static void agregarHistorial(Historial h) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RUTA_ARCHIVO, true))) {
            bw.write(h.getCodigoProducto() + ";" + h.getCantidad() + ";" + h.getFecha());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String obtenerHistorial(String codigoProducto) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(RUTA_ARCHIVO))) {
            String linea;
            boolean hayRegistros = false;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                if (partes.length == 3 && partes[0].equals(codigoProducto)) {
                    hayRegistros = true;
                    sb.append("Cantidad: ").append(partes[1])
                      .append(" | Fecha: ").append(partes[2])
                      .append("\n");
                }
            }
            if (!hayRegistros) sb.append("No hay historial para este producto.");
        } catch (FileNotFoundException e) {
            sb.append("No hay historial registrado aún.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
