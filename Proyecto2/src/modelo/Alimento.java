package modelo;

public class Alimento extends Producto {
    private String fechaCaducidad;

    public Alimento(String codigo, String nombre, String material, String fechaCaducidad) {
        super(codigo, nombre, "Alimento", material);
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    @Override
    public String getAtributoEspecifico() {
        return fechaCaducidad;
    }
}
