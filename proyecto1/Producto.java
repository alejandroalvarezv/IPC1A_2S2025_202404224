package proyecto1;
public class Producto {
    private String nombre;
    private String categoria;
    private double precio;
    private int cantidad;
    private String codigo;

    public Producto(String nombre,  String categoria, double precio, int cantidad, String codigo) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.cantidad = cantidad;
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }
    
    public String getCategoria(){
        return categoria;
    }

    @Override
    public String toString() {
        return "Codigo: " + codigo +
               ", Nombre: " + nombre +
               ", Categoría: " + categoria +
               ", Precio: Q" + precio +
               ", Cantidad: " + cantidad;
    }
    public int getCantidad(){
    return cantidad;
}
    public void disminuirCantidad(int cantidadVendida){
        this.cantidad -= cantidadVendida;
}
    public double getPrecio(){
        return precio;
}
}    