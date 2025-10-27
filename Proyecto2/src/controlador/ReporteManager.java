package controlador; // Puedes ponerla en 'controlador' o en un paquete 'servicios'

import modelo.Producto;
import modelo.ReporteVentasProducto;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

// Asumimos que existen PedidoController y ProductoController
// Importa tus clases de Pedido y Producto

public class ReporteManager {

    /**
     * Procesa todos los pedidos para calcular las ventas e ingresos por producto.
     * @return Array de ReporteVentasProducto con los datos agregados.
     */
    private static ReporteVentasProducto[] generarReporteBase() {
        // Simulación: En un sistema real, harías lo siguiente:
        // 1. Map<String, ReporteVentasProducto> mapaReporte = new HashMap<>();
        // 2. Pedido[] pedidosCompletados = PedidoController.obtenerPedidosCompletados();

        // **Para este ejemplo, simularé la data base que necesitas procesar:**
        Producto[] listaProductos = ProductoController.obtenerProductos();
        if (listaProductos == null || listaProductos.length == 0) {
            return new ReporteVentasProducto[0];
        }

        Map<String, ReporteVentasProducto> mapaReporte = new HashMap<>();
        
        // Simulación de agregación de ventas. DEBES reemplazar esto con tu lógica real:
        // Si tu modelo Producto ya tiene los campos 'contadorVentas' e 'ingresosGenerados', úsalos directamente.
        // Si no, debes iterar sobre los Pedidos para hacer este cálculo.
        
        for (Producto p : listaProductos) {
            if (p != null) {
                // Suponemos que Producto tiene getContadorVentas(), getPrecio() y getCategoria()
                int cantidad = p.getContadorVentas(); // DEBE obtener la cantidad vendida total
                // Asumiendo que getPrecio() devuelve el precio unitario
                double ingresos = cantidad * p.getPrecio(); 
                
                ReporteVentasProducto reporte = new ReporteVentasProducto(
                    p.getCodigo(),
                    p.getNombre(),
                    p.getCategoria(), // Asume que Producto tiene getCategoria()
                    cantidad,
                    ingresos
                );
                mapaReporte.put(p.getCodigo(), reporte);
            }
        }
        
        // Convertir el mapa de nuevo a un array para facilitar el reporte en PDF
        return mapaReporte.values().toArray(new ReporteVentasProducto[0]);
    }

    /**
     * Genera y ordena el reporte para Productos Más Vendidos (por Cantidad).
     * @return Object[] Array de ReporteVentasProducto, ordenado de mayor a menor.
     */
    public static Object[] obtenerProductosMasVendidos() {
        ReporteVentasProducto[] data = generarReporteBase();

        // 1. Ordenar por cantidad vendida (descendente)
        Arrays.sort(data, Comparator.comparingInt(ReporteVentasProducto::getCantidadVendida).reversed());

        return data;
    }

    /**
     * Genera y ordena el reporte para Productos Menos Vendidos (por Cantidad).
     * @return Object[] Array de ReporteVentasProducto, ordenado de menor a mayor.
     */
    public static Object[] obtenerProductosMenosVendidos() {
        ReporteVentasProducto[] data = generarReporteBase();

        // 1. Ordenar por cantidad vendida (ascendente)
        Arrays.sort(data, Comparator.comparingInt(ReporteVentasProducto::getCantidadVendida));

        return data;
    }
    
    // Puedes añadir obtenerTopIngresos(), ordenar por ingresos.
}