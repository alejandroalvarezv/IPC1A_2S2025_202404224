package proyecto1;
import java.util.Scanner;
public class Proyecto1 {      
static Producto[] productos = new Producto[100]; 
static int totalProductos = 0;
static String usuario = "Alejandro";
static String[] bitacora = new String[100];
static int totalRegistrosBitacora = 0;
public static void registrarBitacora(String tipoAccion, boolean fueCorrecta) {
    String estado = fueCorrecta ? "Correcta" : "Erronea";
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
            //Menu Principal
            System.out.println("-----Menu Principal-----");
            System.out.println("1. Agregar producto");
            System.out.println("2. Buscar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Registrar venta");
            System.out.println("5. Bitacora");
            System.out.println("6. Generacion de reportes");
            System.out.println("7. Datos del estudiante");
            System.out.println("8. Salir");
            
            try{
                System.out.print("Selecciona una opcion: ");
                opcion = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Por favor, ingresa un numero del 1 al 8.");
                    opcion = 0;
                
            }
                
            
            switch (opcion){
                
                        //Case 1 Agrega productos
                case 1:{
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

                    double precio=0;
                    boolean precioValido = false;
                    do {
                        System.out.print("Precio: ");
                        String inputPrecio = scanner.nextLine();
                        try{
                            precio = Double.parseDouble(inputPrecio);
                            if (precio > 0) {
                                precioValido = true;
                            }else{
                                System.out.println("El precio debe ser mayor que 0.");
                            }
                        }catch (NumberFormatException e){
                            System.out.println("Entrada invalida. Por favor, ingresa un numero valido para el precio.");
                        }
                    } while (!precioValido);

                    int cantidad=0;
                    boolean cantidadValida = false;
                    do {
                        System.out.print("Cantidad: ");
                        String inputCantidad = scanner.nextLine();
                        try {
                            cantidad = Integer.parseInt(inputCantidad);
                            if (cantidad >= 0) {
                                cantidadValida = true;
                            }else{
                                System.out.println("La cantidad no puede ser negativa.");
                            }
                        }catch (NumberFormatException e) {
                            System.out.println("Entrada invalida. Por favor, ingresa un numero entero valido para la cantidad.");
                        }
                    } while (!cantidadValida);

                    productos[totalProductos] = new Producto(nombre, categoria, precio, cantidad, codigo);
                    totalProductos++;

                    System.out.println("Producto agregado correctamente.\n");
                    registrarBitacora("Agregar producto", true);
                    break;
                    }
                    
                    
                            //case 2 Buscar Productos
                case 2:
                    System.out.println("-----Buscar producto-----");
                    System.out.println("Ingresa nombre, categoria o codigo del producto: ");
                    String criterio = scanner.nextLine().toLowerCase();
                    
                    if (criterio.isEmpty()) {
                        System.out.println("Debe ingresar un criterio para buscar.");
                        break;
                    }

                    
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
                    
                    
                          // Case 3 Elimina productos                                                
                case 3:
                    System.out.println("-----Elimar producto-----");
                    System.out.println("Ingrese el codigo del producto a eliminar: ");
                    String codigoeliminar = scanner.nextLine().trim();
                    
                    if (codigoeliminar.isEmpty()) {
                        System.out.println("El codigo no puede estar vacio.");
                    break;
                    }
                    
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
                    
                                //case 4 Regitra las ventas realizadas
                case 4:
                    System.out.println("-----Registro de la venta-----");
                    System.out.println("Ingrese el codigo del producto: ");
                    String codigoVenta = scanner.nextLine().trim();
                    
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
                    
                    int cantidadVenta = 0;
                    boolean cantidadValida = false;
                    
                    do {
                        System.out.println("Cantidad a vender: ");
                        String inputCantidad = scanner.nextLine().trim();
                        
                        try {
                            cantidadVenta = Integer.parseInt(inputCantidad);
                            if (cantidadVenta <= 0) {
                                System.out.println("La cantidad debe ser mayor a 0.");
                            } else if (cantidadVenta > productoVenta.getCantidad()) {
                                System.out.println("No hay suficiente stock disponible.");
                            } else {
                                cantidadValida = true;
                            }
                        }catch (NumberFormatException e) {
                            System.out.println("Entrada invalida. Ingresa un numero entero valido.");
                        }
                        
                    }while (!cantidadValida);
                    
                        productoVenta.disminuirCantidad(cantidadVenta);
                        double totalVenta = productoVenta.getPrecio() * cantidadVenta;
                        java.time.LocalDateTime fechaHora = java.time.LocalDateTime.now();
                        String fechaHoraFormateada = fechaHora.toString().replace("T", " ");
                        
                    try {
                        java.io.FileWriter writer = new java.io.FileWriter("ventas.txt", true);
                        writer.write("Codigo: " + productoVenta.getCodigo() +
                                " | Cantidad: " + cantidadVenta +
                                " | Fecha y Hora: " + fechaHoraFormateada +
                                " | Total: Q" + String.format("%.2f", totalVenta) + "\n");
                        writer.close();
                        System.out.println("Venta registrada exitosamente");
                        registrarBitacora("Registrar venta", true);
                        
                    }catch (java.io.IOException e){
                        System.out.println("Error al registrar la venta: " + e.getMessage());
                        registrarBitacora("Registrar venta - error al guardar", false);
                    }                        
                    break;
                    
                    
                                //Case 5 Muestra acciones realizadas por el usuario
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
                    
                    
                                //Genera reportes en PDF
                case 6:
                    System.out.println("-----Generar reportes-----");
                    System.out.println("1. Reporte de stock");
                    System.out.println("2. Reporte de ventas");
                    System.out.println("Seleccione una opcion: ");
                    
                    String input = scanner.nextLine().trim();
                        int opcionReporte = 0;
                    
                    try {
                        opcionReporte = Integer.parseInt(input);
                    }catch (NumberFormatException e) {
                            System.out.println("Entrada invalida. Por favor, ingresa un número (1 o 2).");
                        break;
                    }
                    
                    if (opcionReporte ==1){
                        Reportes.generarReporteStockPDF(productos, totalProductos);
                        registrarBitacora("Generar reporte de stock", true);
                    } else if (opcionReporte ==2){
                        Reportes.generarReporteVentasPDF();
                        registrarBitacora("Generar reporte de ventas", true);
                    } else {
                        System.out.println("Opcion invalida. Elige 1 o 2");
                    }
                    break;
                
                
                case 7:
                    System.out.println("-----Datos del Estudiante-----");
                    System.out.println("Alejandro Emmanuel Alvarez Velasquez");
                    System.out.println("Carnet: 202404224");
                    System.out.println("Laboratorio IPC  - Seccion: A");
                    System.out.println("Github: alejandroalvarezv");
                    break;
                    
                    
                case 8:
                    System.out.println("Salir");
                    break;
            }
                }while (opcion !=8);
                    scanner.close();
    } 
    }