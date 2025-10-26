package modelo;
public class Producto {
    private String codigo;
    private String nombre;
    private String categoria;
    private String material;
    private int stock;
    private double precio;

    public Producto(String codigo, String nombre, String categoria, String material, int stock, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.material = material;
        this.stock = stock;
        this.precio = precio; 
    }

    public Producto(String codigo, String nombre, String categoria, String material, int stock) {
        this(codigo, nombre, categoria, material, stock, 0.0);
    }
    
    // Getters
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public String getMaterial() { return material; }
    public int getStock() { return stock; }
    public double getPrecio() { return precio; } // 🚀 Nuevo Getter

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setMaterial(String material) { this.material = material; }
    public void setStock(int stock) { this.stock = stock; }
    public void setPrecio(double precio) { this.precio = precio; } // 🚀 Nuevo Setter

    public String getAtributoEspecifico() {    
        return "";    
    }

    public String toCSVBase() {
        return codigo + "," + 
                nombre + "," + 
                categoria + "," + 
                material + "," + 
                getAtributoEspecifico() + "," + 
                stock;
    }
    
    public String toCSV() {
        return toCSVBase() + "," + this.precio;
    }    

    @Override
    public String toString() {
        return "Producto{" +
                "codigo='" + codigo + '\'' +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                ", material='" + material + '\'' +
                ", stock=" + stock +
                ", precio=" + precio +
                '}';
    }
}