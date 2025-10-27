package modelo;
import java.io.Serializable;

public class Vendedor extends Usuario {
    
    private int ventasConfirmadas;

    public Vendedor(String codigo, String nombre, String genero, String contrasena) {
        super(codigo, nombre, genero, contrasena, "Vendedor"); 
        this.ventasConfirmadas = 0;
    }

    public Vendedor(String codigo, String nombre) {
        super(codigo, "1234", nombre, "Otro", "Vendedor");
        this.ventasConfirmadas = 0;
    }

    public void aumentarVentas() {
        ventasConfirmadas++;
    }

    public int getVentasConfirmadas() {
        return ventasConfirmadas;
    }
    
    @Override
    public String getTipo() {
        return "Vendedor";
    }
}
