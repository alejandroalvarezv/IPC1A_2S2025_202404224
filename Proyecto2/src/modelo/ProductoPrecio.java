package modelo;
public class ProductoPrecio extends Producto {
    private double precio;

    public ProductoPrecio(String codigo, String nombre, String categoria, String material, int stock, double precio) {
        super(codigo, nombre, categoria, material, stock); 
        this.precio = precio;
    }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    
    @Override
    public String toCSV() {
        return toCSVBase() + "," + this.precio;
    }
}