package modelo;
import java.io.Serializable;

public class Usuario implements Serializable {
    
    private String codigo;
    private String nombre;
    private String contrasena;
    private String genero;
    private String rol; 
       

    public Usuario(String codigo, String contrasena, String nombre, String genero, String rol) {
        this.codigo = codigo;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.genero = genero;
        this.rol = rol;
    }
    
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
}