package modelo;
import java.io.Serializable;

public class Tecnologia extends ProductoPrecio implements Serializable { 
    private static final long serialVersionUID = 1L;
    
    private int garantia; 

    public Tecnologia(String codigo, String nombre, String categoria, int garantia, int stock, double precio) {
        super(codigo, nombre, categoria, "Técnico", stock, precio); 
        this.garantia = garantia;
    }

    public int getGarantia() { return garantia; }
    public void setGarantia(int garantia) { this.garantia = garantia; }

    @Override
    public String getAtributoEspecifico() {
        return String.valueOf(garantia); 
    }

    @Override
    public String toString() {
        return "Tecnologia{" +
                "codigo='" + getCodigo() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", categoria='" + getCategoria() + '\'' +
                ", material='" + getMaterial() + '\'' +
                ", stock=" + getStock() +
                ", precio=" + getPrecio() +
                ", garantia=" + garantia + " meses" +
                '}';
    }
}