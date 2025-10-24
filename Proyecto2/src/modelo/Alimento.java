package modelo;

public class Alimento extends Producto {
    private String fechaCaducidad;

    public Alimento(String codigo, String nombre, String material, String fechaCaducidad, int stock) {
        super(codigo, nombre, "Alimento", material, stock);
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
        return getCodigo() + "," + getNombre() + "," + getCategoria() + "," +
               getMaterial() + "," + getAtributoEspecifico() + "," + getStock();
    }
}
