package modelo;

public class Tecnologia extends Producto {
    private int mesesGarantia;

    public Tecnologia(String codigo, String nombre, String material, int mesesGarantia) {
        // CORRECCIÓN: Usar "Tecnología" con acento para que coincida con el Controller
        super(codigo, nombre, "Tecnología", material); 
        this.mesesGarantia = mesesGarantia;
    }

    public int getMesesGarantia() { return mesesGarantia; }
    public void setMesesGarantia(int mesesGarantia) { this.mesesGarantia = mesesGarantia; }

    @Override
    public String toString() {
        // CORRECCIÓN: Aseguramos el formato "Número meses" para que coincida con la lectura
        return super.toString() + "," + mesesGarantia + " meses"; 
    }
}