package proyecto1;
import java.util.Scanner;
public class Proyecto1 {      
static Producto[] productos = new Producto[100]; 
static int totalProductos = 0;
static String usuario = "Alejandro";
static String[] bitacora = new String[100];
static int totalRegistrosBitacora = 0;
public static void registrarBitacora(String tipoAccion, boolean fueCorrecta) {
    String estado = fueCorrecta ? "Correcta" : "Errónea";
    String fechaHora = java.time.LocalDateTime.now().toString().replace("T", " ");
    String registro = "[" + fechaHora + "] Accion: " + tipoAccion + 
                      " | Estado: " + estado + 
                      " | Usuario: " + usuario;

    bitacora[totalRegistrosBitacora++] = registro;
}
public static void main(String[] args) {
Scanner scanner = new Scanner (System.in);
int opcion;
        
        do{
            System.out.println("-----Menu Principal-----");
            System.out.println("1. Agregar producto");
            System.out.println("2. Buscar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Registrar venta");
            System.out.println("5. Bitacora");
            System.out.println("6. Generacion de reportes");
            System.out.println("7. Datos del estudiante");
            System.out.println("8. Salir");
            System.out.print("Selecciona una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
        
            
            switch (opcion){
                case 1:
                    if (totalProductos >= productos.length) {
                        System.out.println("No se pueden agregar mas productos.\n");
                        break;
                    }

                    System.out.println("----- Agregar Producto -----");
                    System.out.print("Codigo del producto: ");
                    String codigo = scanner.nextLine();

                    boolean existe = false;
                    for (int i = 0; i < totalProductos; i++) {
                        if (productos[i].getCodigo().equalsIgnoreCase(codigo)) {
                            existe = true;
                            break;
                        }
                    }

                    if (existe) {
                        System.out.println("El codigo ya existe.\n");
                        registrarBitacora("Agregar producto", false);

                        break;
                    }

                    System.out.print("Nombre del producto: ");
                    String nombre = scanner.nextLine();

                    System.out.print("Categoria: ");
                    String categoria = scanner.nextLine();

                    double precio;
                    do {
                        System.out.print("Precio: ");
                        precio = scanner.nextDouble();
                        if (precio <= 0) System.out.println("El precio debe ser mayor que 0.");
                    } while (precio <= 0);

                    int cantidad;
                    do {
                        System.out.print("Cantidad: ");
                        cantidad = scanner.nextInt();
                        if (cantidad < 0) System.out.println("La cantidad no puede ser negativa.");
                    } while (cantidad < 0);
                    scanner.nextLine();

                    productos[totalProductos] = new Producto(nombre, categoria, precio, cantidad, codigo);
                    totalProductos++;

                    System.out.println("Producto agregado correctamente.\n");
                    registrarBitacora("Agregar producto", true);
                    break;
                    
                    
                    
                case 2:
                    System.out.println("-----Buscar producto-----");
                    System.out.println("Ingresa nombre, categoria o codigo del producto: ");
                    String criterio = scanner.nextLine().toLowerCase();
                    
                    boolean encontrado = false;
                    for (int i=0; i <totalProductos; i++){
                        Producto p = productos[i];
                        if(p.getNombre().toLowerCase().contains(criterio)||
                           p.getCodigo().toLowerCase().contains(criterio)||
                           p.getCategoria().toLowerCase().contains(criterio)){
                        System.out.println(p);
                        encontrado = true;
                    }
                    }
                    if(!encontrado){
                        System.out.println("Producto no encontrado");
                        registrarBitacora("Buscar producto", encontrado);                
                    }
                    break;
                    
                    
                                                                          
                case 3:
                    System.out.println("-----Elimar producto-----");
                    System.out.println("Ingrese el codigo del producto a eliminar: ");
                    String codigoeliminar = scanner.nextLine();                    
                    int eliminar = -1;
                    
                    
                    for (int i = 0; i <totalProductos; i++){
                        if (productos[i].getCodigo().equalsIgnoreCase(codigoeliminar)){
                            eliminar = i;
                        break;    
                        }
                    }
                    if (eliminar == -1){
                        System.out.println("Producto no encontrado con ese codigo");
                        registrarBitacora("Eliminar producto", false);
                    }else{
                        System.out.println("Producto encontrado");
                        System.out.println(productos[eliminar]);
                        System.out.print("Estas seguro que deseas eliminar el producto, (escribe si/no): ");
                        String confirmacion = scanner.nextLine();
                        
                        if (confirmacion.equalsIgnoreCase("si")){
                            for (int i = eliminar; i < totalProductos -1; i++){
                                productos[i] = productos[i+1];
                            }
                            
                            productos[totalProductos -1]= null;
                            totalProductos--;
                            
                        System.out.println("Producto eliminado");
                        registrarBitacora("Eliminar producto", true);
                        }else{
                            System.out.println("Eliminacion cancelada");
                        }
                    }
                    break;
                    
                    
                case 4:
                    System.out.println("-----Registro de la venta-----");
                    System.out.println("Ingrese el codigo del producto: ");
                    String codigoVenta = scanner.nextLine();
                    
                    Producto productoVenta = null;
                    for(int i=0; i < totalProductos; i++){
                        if (productos[i].getCodigo().equalsIgnoreCase(codigoVenta)){
                            productoVenta = productos[i];
                            break;
                        }
                    }
                    if (productoVenta == null){
                        System.out.println("Producto no encontrado");
                        registrarBitacora("Registrar venta", false);
                        break;
                    }
                    
                    System.out.println("Producto encontrado: " + productoVenta);
                    System.out.println("Cantidad a vender: ");
                    int cantidadVenta = scanner.nextInt();
                    scanner.nextLine();
                    
                    if (cantidadVenta <= 0){
                        System.out.println("La cantidad debe ser mayor a 0");
                    }else if (cantidadVenta > productoVenta.getCantidad()){
                        System.out.println("No hay stock disponible");
                    }else {
                        productoVenta.disminuirCantidad(cantidadVenta);
                        double totalVenta = productoVenta.getPrecio() * cantidadVenta;
                        java.time.LocalDateTime fechaHora = java.time.LocalDateTime.now();
                        String fechaHoraFormateada = fechaHora.toString().replace("T", " ");
                        
                    try {
                        java.io.FileWriter writer = new java.io.FileWriter("ventas.txt", true);
                        writer.write("Código: " + productoVenta.getCodigo() +
                                " | Cantidad: " + cantidadVenta +
                                " | Fecha y Hora: " + fechaHoraFormateada +
                                " | Total: Q" + String.format("%.2f", totalVenta) + "\n");
                        writer.close();
                        System.out.println("Venta registrada exitosamente");
                        registrarBitacora("Registrar venta", true);
                    }catch (java.io.IOException e){
                        System.out.println("Error al registrar la venta: " + e.getMessage());
                    }                        
                    }
                    break;
                    
                    
                    
                case 5:
                    System.out.println("-----Bitacora-----");
                        if (totalRegistrosBitacora == 0) {
                            System.out.println("No se han registrado acciones aun.");
                        } else {
                            for (int i = 0; i < totalRegistrosBitacora; i++) {
                                System.out.println(bitacora[i]);
                            }
                        }
                    break;
                    
                    
                
                case 6:
                    System.out.println("-----Generar reportes-----");
                    System.out.println("1. Reporte de stock");
                    System.out.println("2. Reporte de ventas");
                    System.out.println("Seleccione una opcion: ");
                    int opcionReporte = scanner.nextInt();
                    scanner.nextLine();
                    
                    if (opcionReporte ==1){
                        Reportes.generarReporteStockPDF(productos, totalProductos);
                        registrarBitacora("Generar reporte de stock", true);
                    } else if (opcionReporte ==2){
                        Reportes.generarReporteVentasPDF();
                        registrarBitacora("Generar reporte de ventas", true);
                    } else {
                        System.out.println("Opcion invalida");
                    }
                    break;
                
                
                case 7:
                    System.out.println("Alejandro Emmanuel Alvarez Velasquez");
                    System.out.println("Carnet: 202404224");
                    System.out.println("Laboratorio IPC  - Seccion: A");
                    System.out.println("alejandroalvarezv");
                    break;
                    
                    
                case 8:
                    System.out.println("Salir");
            }
                }while (opcion !=8);
                    scanner.close();
    }
            
    }