package modelo;
public class Vendedor extends Usuario {
    private String genero;
    private int ventasConfirmadas;

    public Vendedor(String codigo, String contraseña, String nombre, String genero) {
        super(codigo, contraseña, nombre);
        this.genero = genero;
        this.ventasConfirmadas = 0;
    }

    
    public String getGenero() {
        return genero;
    }

    public int getVentasConfirmadas() {
        return ventasConfirmadas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void aumentarVentas() {
        ventasConfirmadas++;
    }
    
    @Override
    public String getTipo() {
        return "Vendedor";
    }
}