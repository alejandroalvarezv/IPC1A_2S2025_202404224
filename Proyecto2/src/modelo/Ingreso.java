package modelo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Ingreso {
    private String usuario;
    private String codigoProducto;
    private String nombreProducto;
    private int cantidad;
    private LocalDateTime fechaHora;

    public Ingreso(String usuario, String codigoProducto, String nombreProducto, int cantidad) {
        this.usuario = usuario;
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.fechaHora = LocalDateTime.now();
    }

    public String toCSV() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return fechaHora.format(formato) + "," + usuario + "," + codigoProducto + "," +
               nombreProducto + "," + cantidad;
    }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getUsuario() { return usuario; }
    public String getCodigoProducto() { return codigoProducto; }
    public int getCantidad() { return cantidad; }
}
