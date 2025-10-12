package proyecto2;
public class Administrador extends Usuario {

    public Administrador(String codigo, String contraseña, String nombre) {
        super(codigo, contraseña, nombre);
    }

    @Override
    public String getTipo() {
        return "Administrador";
    }
}