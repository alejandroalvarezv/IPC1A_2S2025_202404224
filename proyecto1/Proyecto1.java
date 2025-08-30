package proyecto1;
import java.util.Scanner;
public class Proyecto1 {      
static Producto[] productos = new Producto[100]; 
static int totalProductos = 0;
public static void main(String[] args) {
Scanner scanner = new Scanner (System.in);
int opcion;
        
        do{
            System.out.println("-----Menu Principal-----");
            System.out.println("1. Agregar producto");
            System.out.println("2. Buscar producto");
            System.out.println("3. Eliminar producto");
            System.out.println("4. Registrar venta");
            System.out.println("5. Generar reportes");
            System.out.println("6. Ver datos del estudiante");
            System.out.println("7. Bitacora");
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
                        }else{
                            System.out.println("Eliminacion cancelada");
                        }
                    }
                    break;
                    
                case 4:
                    System.out.println("Registro de la venta");
                    break;
                case 5:
                    System.out.println("Generar reportes");
                    break;
                    
                    
                
                case 6:
                    System.out.println("Alejandro Emmanuel Alvarez Velasquez");
                    System.out.println("Carnet: 202404224");
                    System.out.println("Laboratorio IPC  - Seccion: A");
                    System.out.println("alejandroalvarezv");
                    break;
                
                
                case 7:
                    System.out.println("BITACORA");
                    break;
                case 8:
                    System.out.println("Salir");
            }
                }while (opcion !=8);
                    scanner.close();
    }
            
    }