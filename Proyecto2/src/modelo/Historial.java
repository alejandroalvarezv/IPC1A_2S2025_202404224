package modelo;

import java.time.LocalDate;

public class Historial {
    private String codigoProducto;
    private int cantidad;
    private String fecha;

    public Historial(String codigoProducto, int cantidad) {
        this.codigoProducto = codigoProducto;
        this.cantidad = cantidad;
        this.fecha = LocalDate.now().toString(); // fecha de hoy
    }

    public String getCodigoProducto() { return codigoProducto; }
    public int getCantidad() { return cantidad; }
    public String getFecha() { return fecha; }
}
