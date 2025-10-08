package proyecto2;
public class Administrador extends Usuario {

    public Administrador(String codigo, String contrasena, String nombre) {
        super(codigo, contrasena, nombre);
    }

    @Override
    public String getTipo() {
        return "Administrador";
    }
}