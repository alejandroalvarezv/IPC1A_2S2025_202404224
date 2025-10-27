package modelo;

import java.time.LocalDateTime;

public class ReporteInventario {
    private String codigo;
    private String nombre;
    private String categoria;
    private int stock;
    private String estado; 
    private LocalDateTime fechaUltimaActualizacion; 
    private String sugerencia;

    public ReporteInventario(String codigo, String nombre, String categoria, int stock, LocalDateTime fecha) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        this.stock = stock;
        this.fechaUltimaActualizacion = fecha;
    }

    // Getters y Setters necesarios:
    public int getStock() { return stock; }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getSugerencia() { return sugerencia; }
    public void setSugerencia(String sugerencia) { this.sugerencia = sugerencia; }
    public LocalDateTime getFechaUltimaActualizacion() { return fechaUltimaActualizacion; } 
}