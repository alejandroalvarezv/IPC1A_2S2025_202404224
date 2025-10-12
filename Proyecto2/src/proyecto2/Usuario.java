package proyecto2;

public abstract class Usuario {
    protected String codigo;
    protected String contraseña;
    protected String nombre;

    public Usuario(String codigo, String contraseña, String nombre) {
        this.codigo = codigo;
        this.contraseña = contraseña;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getNombre() {
        return nombre;
    }
    
     public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public abstract String getTipo();
}
