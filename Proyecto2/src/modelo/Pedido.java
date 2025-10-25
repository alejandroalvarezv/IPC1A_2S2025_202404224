package modelo;
import java.time.LocalDateTime;
import java.util.UUID;
import java.time.format.DateTimeFormatter; 

public class Pedido {
    
    private String idPedido;
    private String codigoCliente;
    private String descripcionItems; 
    private double total;
    private LocalDateTime fechaCreacion;
    private String estado; 

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public Pedido(String codigoCliente, String descripcionItems, double total) {
        this.idPedido = UUID.randomUUID().toString().substring(0, 8).toUpperCase(); 
        this.codigoCliente = codigoCliente;
        this.descripcionItems = descripcionItems;
        this.total = total;
        this.fechaCreacion = LocalDateTime.now(); 
        this.estado = "Pendiente"; 
    }
    
    
    public void setIdPedido(String id) {
    this.idPedido = id;
    }   

    public Pedido(String idPedido, String codigoCliente, String descripcionItems, double total, 
                  LocalDateTime fechaCreacion, String estado) {
        this.idPedido = idPedido;
        this.codigoCliente = codigoCliente;
        this.descripcionItems = descripcionItems;
        this.total = total;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }
    
    // --- Getters y Setters ---

    public String getIdPedido() { return idPedido; }
    public String getCodigoCliente() { return codigoCliente; }
    public String getDescripcionItems() { return descripcionItems; }
    public double getTotal() { return total; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public String getEstado() { return estado; }

    public void setEstado(String estado) { this.estado = estado; }
}
