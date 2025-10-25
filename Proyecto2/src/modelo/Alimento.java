package modelo;
public class Alimento extends ProductoPrecio { 
    private String fechaCaducidad;

    public Alimento(String codigo, String nombre, String categoria, String fechaCaducidad, int stock, double precio) {
        super(codigo, nombre, categoria, "Orgánico", stock, precio); 
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getFechaCaducidad() { return fechaCaducidad; }
    public void setFechaCaducidad(String fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }

    @Override
    public String getAtributoEspecifico() {
        return fechaCaducidad;
    }

    @Override
    public String toString() {
        return toCSV(); 
    }
}