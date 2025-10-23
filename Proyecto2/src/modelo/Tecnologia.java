package modelo;

public class Tecnologia extends Producto {
    private int mesesGarantia;

    public Tecnologia(String codigo, String nombre, String material, int mesesGarantia) {
        super(codigo, nombre, "Tecnología", material);
        this.mesesGarantia = mesesGarantia;
    }

    public int getMesesGarantia() {
        return mesesGarantia;
    }

    public void setMesesGarantia(int mesesGarantia) {
        this.mesesGarantia = mesesGarantia;
    }

    @Override
    public String getAtributoEspecifico() {
        return mesesGarantia + " meses";
    }
}
