package modelo;
public class Vendedor extends Usuario {
    
    private int ventasConfirmadas;

    public Vendedor(String codigo, String contrasena, String nombre, String genero) {
        super(codigo, contrasena, nombre, genero, "Vendedor"); 
        this.ventasConfirmadas = 0;
    }

    public void aumentarVentas() {
        ventasConfirmadas++;
    }

    // Getter 
    public int getVentasConfirmadas() {
        return ventasConfirmadas;
    }
    
    @Override
    public String getTipo() {
        return "Vendedor";
    }
}