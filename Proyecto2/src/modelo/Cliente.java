package modelo;
public class Cliente extends Usuario {

    public Cliente(String codigo, String contraseña, String nombre) {
        super(codigo, contraseña, nombre, "otro", "Cliente");
    }

    @Override
    public String getTipo() {
        return "Cliente";
    }
}