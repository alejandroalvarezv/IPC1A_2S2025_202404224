package controlador;

import modelo.Alimento;
import modelo.Producto;
import modelo.ReporteInventario;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator; // Permitido para ordenar arrays

public class InventarioManager {

    // --- Definición de Lógica de Negocio (Umbrales) ---
    private static final int UMBRAL_STOCK_CRITICO = 5;
    private static final int UMBRAL_STOCK_BAJO = 15;
    private static final long DIAS_CADUCIDAD_CRITICO = 30;

    /**
     * Procesa la lista de productos y la convierte en el modelo ReporteInventario.
     * @return Array de ReporteInventario[] (con posible inclusión de 'null').
     */
    private static ReporteInventario[] generarReporteInventarioBase() {
        Producto[] listaProductos = ProductoController.obtenerProductos();
        
        if (listaProductos == null || listaProductos.length == 0) {
            return new ReporteInventario[0];
        }

        // Creamos un nuevo array del mismo tamaño para el reporte
        ReporteInventario[] reporteInventarioArray = new ReporteInventario[listaProductos.length];
        
        for (int i = 0; i < listaProductos.length; i++) {
            Producto p = listaProductos[i];
            
            if (p != null) {
                // 1. Crear la instancia base del reporte
                ReporteInventario ri = new ReporteInventario(
                    p.getCodigo(),
                    p.getNombre(),
                    p.getCategoria(),
                    p.getStock(),
                    p.getFechaUltimaActualizacion()
                );

                // 2. Aplicar la lógica de negocio (Estado y Sugerencia)
                aplicarLogicaStock(ri);
                
                reporteInventarioArray[i] = ri;
            } 
            // Si p es null, el espacio en reporteInventarioArray[i] sigue siendo null.
        }
        
        return reporteInventarioArray;
    }

    /**
     * Aplica la lógica del umbral de stock.
     * (Este método auxiliar no necesita cambios)
     */
    private static void aplicarLogicaStock(ReporteInventario ri) {
        int stock = ri.getStock();
        
        if (stock <= UMBRAL_STOCK_CRITICO) {
            ri.setEstado("Crítico");
            ri.setSugerencia("¡Pedido Urgente! Stock agotándose.");
        } else if (stock <= UMBRAL_STOCK_BAJO) {
            ri.setEstado("Bajo");
            ri.setSugerencia("Programar próximo pedido de reposición.");
        } else {
            ri.setEstado("Normal");
            ri.setSugerencia("Stock suficiente.");
        }
    }


    // --- Métodos de Reporte para la Ventana Administrador ---

    /**
     * Implementa el reporte del jButton12: Reporte de Inventario General.
     * Filtra los nulos y luego ordena por Stock ascendente.
     */
    public static Object[] obtenerReporteInventarioGeneral() {
        ReporteInventario[] dataConNulos = generarReporteInventarioBase();
        
        // 1. Filtrar los elementos nulos (registros vacíos) para obtener un array limpio.
        // Contar elementos no nulos
        int count = 0;
        for (ReporteInventario ri : dataConNulos) {
            if (ri != null) {
                count++;
            }
        }
        
        // Crear el array final sin nulos
        ReporteInventario[] dataFinal = new ReporteInventario[count];
        int j = 0;
        for (ReporteInventario ri : dataConNulos) {
            if (ri != null) {
                dataFinal[j++] = ri;
            }
        }
        
        // 2. Ordenar por stock ascendente para ver los productos críticos primero
        Arrays.sort(dataFinal, Comparator.comparingInt(ReporteInventario::getStock));
        
        // Retorna Object[] como lo espera generarYGuardarReporte
        return dataFinal;
    }

    /**
     * Implementa el reporte del jButton16: Productos por Caducar (solo para Alimentos).
     * Usa un array temporal para recolectar solo los ítems que cumplen la condición.
     */
    public static Object[] obtenerProductosPorCaducar() {
        Producto[] listaProductos = ProductoController.obtenerProductos();
        
        if (listaProductos == null || listaProductos.length == 0) {
            return new ReporteInventario[0];
        }
        
        // Usaremos un array temporal del tamaño máximo, y después lo copiaremos al tamaño real.
        ReporteInventario[] tempArray = new ReporteInventario[listaProductos.length];
        int indiceReporte = 0;
        LocalDateTime hoy = LocalDateTime.now();

        for (Producto p : listaProductos) {
            // Solo procesar si es un Alimento
            if (p instanceof Alimento) {
                Alimento a = (Alimento) p;
                
                // Calcular diferencia de días
                long diasRestantes = ChronoUnit.DAYS.between(hoy, a.getFechaCaducidad());

                if (diasRestantes <= DIAS_CADUCIDAD_CRITICO && diasRestantes > 0) { // Solo productos que caducan
                    
                    ReporteInventario ri = new ReporteInventario(
                        a.getCodigo(),
                        a.getNombre(),
                        a.getCategoria(),
                        a.getStock(),
                        a.getFechaUltimaActualizacion()
                    );
                    
                    ri.setEstado("¡Caduca en " + diasRestantes + " días!");
                    ri.setSugerencia("Promocionar y Liquidar urgentemente.");
                    
                    tempArray[indiceReporte++] = ri;
                }
            }
        }
        
        // 1. Copiar los datos del array temporal al array final con el tamaño exacto
        ReporteInventario[] dataFinal = Arrays.copyOf(tempArray, indiceReporte);
        
        // 2. Ordenar por la fecha de caducidad (tendrías que añadir un getter al POJO)
        // Como ReporteInventario no tiene getFechaCaducidad(), lo ordenamos por Código por simplicidad.
        Arrays.sort(dataFinal, Comparator.comparing(ReporteInventario::getCodigo)); 
        
        return dataFinal;
    }
}