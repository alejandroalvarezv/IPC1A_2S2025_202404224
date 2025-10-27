package modelo;
import java.io.Serializable;

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String codigo;
    private String nombre;
    private String contrasena;
    private String genero; // <-- Debe existir aquí
    private String rol; 
       

    public Usuario(String codigo, String contrasena, String nombre, String genero, String rol) {
        this.codigo = codigo;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.genero = genero;
        this.rol = rol;
    }
    
    // Constructor de respaldo si lo usas en otros modelos (ej: Administrador)
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

    public String getGenero() { 
        return genero;
    }

    public String getTipo() { 
        return rol;
    }
    
    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public void setGenero(String genero) { 
        this.genero = genero;
    }
}