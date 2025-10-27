package modelo;
import java.io.Serializable;

public class Administrador extends Usuario {

    public Administrador(String codigo, String contraseña, String nombre) {
        super(codigo, contraseña, nombre, "otro", "Administrador"); 
    }

    @Override
    public String getTipo() {
        return "Administrador";
    }
}