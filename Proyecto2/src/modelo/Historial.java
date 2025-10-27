package modelo;

import java.io.Serializable;
import java.time.LocalDate;
// Se recomienda usar java.time.format.DateTimeFormatter para un formato específico, 
// pero se mantiene la importación mínima para LocalDate.

public class Historial implements Serializable {
    
    // Es buena práctica añadir el ID de versión
    private static final long serialVersionUID = 1L; 
    
    private String codigoProducto;
    private int cantidad;
    // La fecha se guarda como String
    private String fecha; 

    public Historial(String codigoProducto, int cantidad) {
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        // Se asegura que la fecha se establece automáticamente al crear el objeto.
        // Si hay un error de compilación en esta línea, probablemente se deba a que
        // su proyecto no está configurado para usar Java 8 o superior.
        this.fecha = LocalDate.now().toString(); 
    }

    // Getters
    public String getCodigoProducto() {
        return codigoProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getFecha() {
        return fecha;
    }
    
    // Nota: Los setters no se implementan ya que el historial es inmutable.
}