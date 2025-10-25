package modelo;
public class Producto {
    private String codigo;
    private String nombre;
    private String categoria;
    private String material;
    private int stock;    

    public Producto(String codigo, String nombre, String categoria, String material, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.material = material;
        this.stock = stock;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCategoria() { return categoria; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

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
        return toCSVBase() + "," + "0.0";
    }    

    @Override
    public String toString() {
        return "Producto{" +
               "codigo='" + codigo + '\'' +
               ", nombre='" + nombre + '\'' +
               ", categoria='" + categoria + '\'' +
               ", material='" + material + '\'' +
               ", stock=" + stock +
               '}';
    }
}