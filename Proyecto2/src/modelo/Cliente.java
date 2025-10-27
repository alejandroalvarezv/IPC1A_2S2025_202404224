package modelo;

import java.io.Serializable;

public class Cliente extends Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String cumpleanos;
    public Cliente(String codigo, String contrasena, String nombre, String genero, String cumpleanos) {
        super(codigo, contrasena, nombre, genero, "Cliente");
        this.cumpleanos = cumpleanos;
    }
    

    public Cliente(String codigo, String contrasena, String nombre, String genero) {
        this(codigo, contrasena, nombre, genero, "N/A"); 
    }

    public Cliente(String codigo, String contrasena, String nombre) {
        this(codigo, contrasena, nombre, "Otro", "N/A"); 
    }

    @Override
    public String getTipo() {
        return "Cliente";
    }

    public String getCumpleanos() {
        return cumpleanos;
    }

    public void setCumpleanos(String cumpleanos) {
        this.cumpleanos = cumpleanos;
    }
    
}