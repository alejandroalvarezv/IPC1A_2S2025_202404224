package proyecto2;

public abstract class Usuario {
    protected String codigo;
    protected String contrasena;
    protected String nombre;

    public Usuario(String codigo, String contrasena, String nombre) {
        this.codigo = codigo;
        this.contrasena = contrasena;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract String getTipo();
}
