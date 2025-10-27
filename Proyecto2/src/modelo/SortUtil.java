package modelo; 

public class SortUtil {

    public enum CriterioProducto {
        CANTIDAD_VENDIDA_DESC, 
        CANTIDAD_VENDIDA_ASC   
    }
    
    public enum CriterioInventario {
        STOCK_ASC              
    }


    public static void bubbleSortProductos(ReporteVentasProducto[] arr, CriterioProducto criterio) {
        if (arr == null) return;
        int n = arr.length;
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                boolean shouldSwap = false;
                
                if (criterio == CriterioProducto.CANTIDAD_VENDIDA_DESC) {
                    if (arr[j].getCantidadVendida() < arr[j + 1].getCantidadVendida()) {
                        shouldSwap = true;
                    }
                } else if (criterio == CriterioProducto.CANTIDAD_VENDIDA_ASC) {
                    if (arr[j].getCantidadVendida() > arr[j + 1].getCantidadVendida()) {
                        shouldSwap = true;
                    }
                }
                
                if (shouldSwap) {
                    ReporteVentasProducto temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
    

    public static void bubbleSortInventario(ReporteInventario[] arr, CriterioInventario criterio) {
        if (arr == null) return;
        int n = arr.length;
        
        if (criterio == CriterioInventario.STOCK_ASC) {
            for (int i = 0; i < n - 1; i++) {
                for (int j = 0; j < n - i - 1; j++) {
                    // Ordenar Menor a Mayor por Stock (el más bajo primero)
                    if (arr[j].getStock() > arr[j + 1].getStock()) {
                        // Intercambio de elementos
                        ReporteInventario temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
                }
            }
        }
    }
}