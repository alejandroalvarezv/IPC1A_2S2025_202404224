package proyecto2;
public class Cliente extends Usuario {

    public Cliente(String codigo, String contrasena, String nombre) {
        super(codigo, contrasena, nombre);
    }

    @Override
    public String getTipo() {
        return "Cliente";
    }
}