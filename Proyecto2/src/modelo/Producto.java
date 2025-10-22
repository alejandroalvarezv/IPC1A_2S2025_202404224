package modelo;
public class Producto {
    protected String codigo;
    protected String nombre;
    protected String categoria;
    protected String material; 

    public Producto(String codigo, String nombre, String categoria, String material) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.material = material;
    }
    

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public String getMaterial() { return material; }

    @Override
    public String toString() {
        return codigo + "," + nombre + "," + categoria + "," + material;
    }
}
