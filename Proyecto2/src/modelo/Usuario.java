package modelo;
import java.io.Serializable;

public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    
    protected String codigo;
    protected String contrasena; 
    protected String nombre;
    protected String genero;     
    protected String rol;        

    // CONSTRUCTOR BASE de 5 parámetros (Usado por Vendedor y para Carga CSV)
    public Usuario(String codigo, String contrasena, String nombre, String genero, String rol) {
        this.codigo = codigo;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.genero = genero;
        this.rol = rol;
    }
    
    // CONSTRUCTOR SIMPLIFICADO de 3 parámetros (Usado por Admin y Cliente)
    // Asigna un rol y género por defecto ("Otro")
    public Usuario(String codigo, String contrasena, String nombre, String rol) {
        this(codigo, contrasena, nombre, "Otro", rol); 
    }

    // Getters
    public String getCodigo() {
        return codigo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public String getGenero() { // Importante para la tabla del Administrador
        return genero;
    }

    public String getTipo() { // Es igual a getRol()
        return rol;
    }
    
    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}