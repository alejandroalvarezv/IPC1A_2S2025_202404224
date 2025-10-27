package modelo;

public class ReporteVentasProducto {
    private String codigoProducto;
    private String nombre;
    private String categoria;
    private int cantidadVendida;
    private double ingresosGenerados;

    public ReporteVentasProducto(String codigoProducto, String nombre, String categoria, int cantidadVendida, double ingresosGenerados) {
        this.codigoProducto = codigoProducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidadVendida = cantidadVendida;
        this.ingresosGenerados = ingresosGenerados;
    }

    // Getters y Setters
    public String getCodigoProducto() { return codigoProducto; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    
    public int getCantidadVendida() { return cantidadVendida; }
    public void setCantidadVendida(int cantidadVendida) { this.cantidadVendida = cantidadVendida; }
    
    public double getIngresosGenerados() { return ingresosGenerados; }
    public void setIngresosGenerados(double ingresosGenerados) { this.ingresosGenerados = ingresosGenerados; }
    

}