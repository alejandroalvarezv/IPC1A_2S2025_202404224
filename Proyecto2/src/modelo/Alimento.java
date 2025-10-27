package modelo;
import java.io.Serializable;

public class Alimento extends ProductoPrecio implements Serializable { 
    private static final long serialVersionUID = 1L;
    
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
        return "Alimento{" +
                "codigo='" + getCodigo() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", categoria='" + getCategoria() + '\'' +
                ", material='" + getMaterial() + '\'' +
                ", stock=" + getStock() +
                ", precio=" + getPrecio() +
                ", fechaCaducidad='" + fechaCaducidad + '\'' +
                '}';
    }
}