package proyecto2;
public class Vendedor extends Usuario {

    public Vendedor(String codigo, String contrasena, String nombre) {
        super(codigo, contrasena, nombre);
    }

    @Override
    public String getTipo() {
        return "Vendedor";
    }
}