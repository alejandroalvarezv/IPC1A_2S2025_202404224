package modelo;
import java.io.Serializable;

public class ProductoPrecio extends Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    public ProductoPrecio(String codigo, String nombre, String categoria, String material, int stock, double precio) {
        super(codigo, nombre, categoria, material, stock, precio); 
    }

}