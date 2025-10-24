package modelo;

public class Tecnologia extends Producto {
    private int garantia; // meses

    public Tecnologia(String codigo, String nombre, String material, int garantia, int stock) {
        super(codigo, nombre, "Tecnología", material, stock);
        this.garantia = garantia;
    }

    public int getGarantia() { return garantia; }
    public void setGarantia(int garantia) { this.garantia = garantia; }

    @Override
    public String getAtributoEspecifico() {
        return garantia + " meses";
    }

    @Override
    public String toString() {
        return getCodigo() + "," + getNombre() + "," + getCategoria() + "," +
               getMaterial() + "," + getAtributoEspecifico() + "," + getStock();
    }
}
