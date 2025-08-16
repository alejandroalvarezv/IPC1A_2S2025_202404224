package com.mycompany.ipc1;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
public class Ipc1 {
public static void main(String[] args) {
Scanner scanner = new Scanner (System.in);
       int opcion = 0;
       
        final int Limite_personajes = 100;
        String[] nombres = new String[Limite_personajes];
        String[] armas = new String[Limite_personajes];
        int[] fuerzas = new int[Limite_personajes];
        String[][] habilidades = new String[Limite_personajes][5];
        int[] ids = new int[Limite_personajes];
        String[] historialPeleas = new String[100];
        int cantidad = 0;
        int idCounter = 1;
        int peleasRealizadas = 0;
       
        while (opcion != 9) {
            System.out.println("\n---- MENU ----");
            System.out.println("1. Agregar personajes");
            System.out.println("2. Modificar datos de personajes");
            System.out.println("3. Eliminar personajes");
            System.out.println("4. Ver datos de un personaje");
            System.out.println("5. Ver datos de personajes");
            System.out.println("6. Realizar pelea entre personajes");
            System.out.println("7. Ver historial de peleas");
            System.out.println("8. Ver datos del estudiante");
            System.out.println("9. Salir");
            
                while (true) {
            System.out.print("Elige una opcion: ");
                    if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); 
                    if (opcion >= 1 && opcion <= 9) {
            break; 
        } else {
            System.out.println("Error: La opcion debe estar entre 1 y 9.");
        }
                  } else {
            System.out.println("Error: Debes ingresar un numero valido.");
            scanner.nextLine(); 
    }
}


            switch (opcion) {
                //Agregar personajes
                case 1:
                if (cantidad >= Limite_personajes) {
                        System.out.println("Limite de personajes alcanzado.");
                        break;
                    }
                    System.out.print("Nombre del personaje: ");
                    String nombre = scanner.nextLine().trim();
                if (nombre.isEmpty()) {
                    System.out.println("El nombre no puede estar vacio.");
                        break;
                }
                // Verificar si ya existe el nombre
                boolean existe = false;
                    for (int i = 0; i < cantidad; i++) {
                        if (nombres[i].equalsIgnoreCase(nombre)) {
                            existe = true;
                                break;
            }
            }
                if (existe) {
                    System.out.println("Ese nombre ya existe.");
                        break;
    }

                // Solicitar arma del personaje
                    System.out.print("Arma del personaje: ");
                    String arma = scanner.nextLine().trim();

                // Validar que el arma no esté vacía
                if (arma.isEmpty()) {
                    System.out.println("El arma no puede estar vacia.");
                        break;
    }

                // Solicitar 5 habilidades
                    System.out.println("Ingrese 5 habilidades");
                        for (int i = 0; i < 5; i++) {
                    while (true) {
                    System.out.print("Habilidad " + (i + 1) + ": ");
                        String habilidad = scanner.nextLine().trim();
                            if (habilidad.isEmpty()) {
                    System.out.println("La habilidad no puede estar vacia.");
                        } else {
                habilidades[cantidad][i] = habilidad;
                break;
            }
        }
    }

                // Solicitar fuerza entre 1 y 100
                    int fuerza;
                        while (true) {
                    System.out.print("Fuerza (1-100): ");
                        if (scanner.hasNextInt()) {
                    fuerza = scanner.nextInt();
                            scanner.nextLine(); 
                        if (fuerza >= 1 && fuerza <= 100) {
                break;
            } else {
                System.out.println("La fuerza debe estar entre 1 y 100.");
            }
        } else {
            System.out.println("Error, debe ser un numero.");
            scanner.nextLine(); 
        }
    }

                // Guardar el personaje
                   nombres[cantidad] = nombre;
                   armas[cantidad] = arma;
                   fuerzas[cantidad] = fuerza;
                   ids[cantidad] = idCounter++;  
                   cantidad++;

                System.out.println("Personaje agregado con exito.");
                break;
                    
                    
                //Modificar personajes
                case 2:
                    System.out.print("Ingrese ID o nombre del personaje a modificar: ");
                    String entrada = scanner.nextLine().trim();
                        int posicion = -1;

                    try {
                        int id = Integer.parseInt(entrada);
                        posicion = buscarPorId(ids, cantidad, id);
                        } catch (NumberFormatException e) {
                    for (int i = 0; i < cantidad; i++) {
                if (nombres[i].equalsIgnoreCase(entrada)) {
                    posicion = i;
                break;
            }
        }
    }

                if (posicion == -1) {
                    System.out.println("Personaje no encontrado.");
                break;
    }

                // Mostrar datos actuales
                    System.out.println("Datos actuales:");
                    System.out.println("ID: " + ids[posicion]);
                    System.out.println("Nombre: " + nombres[posicion]);
                    System.out.println("Arma: " + armas[posicion]);
                    System.out.println("Fuerza: " + fuerzas[posicion]);
                    System.out.println("Habilidades:");
                        for (int j = 0; j < 5; j++) {
                    System.out.println(" - " + habilidades[posicion][j]);
    }

                        
                // Modificar arma
                    System.out.print("Nueva arma (Enter para mantener actual): ");
                        String nuevaArma = scanner.nextLine().trim();
                            if (!nuevaArma.isEmpty()) {
                        armas[posicion] = nuevaArma;
    }

                // Modificar habilidades
                    System.out.println("Nuevas habilidades (Enter para mantener cada una):");
                        for (int i = 0; i < 5; i++) {
                    System.out.print("Habilidad " + (i + 1) + ": ");
                    String nuevaHab = scanner.nextLine().trim();
                        if (nuevaHab.isEmpty()) {
                            habilidades[posicion][i] = nuevaHab;
        }
    }

                // Modificar fuerza
                    System.out.print("Nueva fuerza (1-100, Enter para mantener actual): ");
                        String fuerzaInput = scanner.nextLine().trim();
                            if (fuerzaInput.isEmpty()) {
                    try {
                        int nuevaF = Integer.parseInt(fuerzaInput);
                    if (nuevaF >= 1 && nuevaF <= 100) {
                        fuerzas[posicion] = nuevaF;
                } else {
                    System.out.println("Fuerza fuera de rango.");
            }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida.");
        }
    }

                    System.out.println("Personaje modificado correctamente.");
                        break;
         
                // Eliminar personaje
                case 3: 
                    System.out.print("Ingrese el ID del personaje a eliminar: ");
                        int idDel = scanner.nextInt();
                            scanner.nextLine();

                        int posicioneliminar = buscarPorId(ids, cantidad, idDel);
                        if (posicioneliminar == -1) {
                    System.out.println("Personaje no encontrado.");
                        break;
    }

                // Mostrar datos antes de confirmar
                    System.out.println("¿Desea eliminar el siguiente personaje?");
                    System.out.println("ID: " + ids[posicioneliminar]);
                    System.out.println("Nombre: " + nombres[posicioneliminar]);
                    System.out.println("Arma: " + armas[posicioneliminar]);
                    System.out.println("Fuerza: " + fuerzas[posicioneliminar]);
                    System.out.println("Habilidades:");
                        for (int j = 0; j < 5; j++) {
                    System.out.println(" - " + habilidades[posicioneliminar][j]);
    }

                    System.out.print("Escriba 'SI' para confirmar eliminacion: ");
                        String confirmacion = scanner.nextLine().trim();

                            if (!confirmacion.equalsIgnoreCase("SI")) {
                    System.out.println("Eliminacion cancelada.");
                        break;
    }

                // Eliminar personaje desplazando los elementos
                       for (int i = posicioneliminar; i < cantidad - 1; i++) {
                        nombres[i] = nombres[i + 1];
                        armas[i] = armas[i + 1];
                        fuerzas[i] = fuerzas[i + 1];
                          ids[i] = ids[i + 1];
                       for (int j = 0; j < 5; j++) {
                          habilidades[i][j] = habilidades[i + 1][j];
        }
    }

                cantidad--;
                    System.out.println("Personaje eliminado correctamente.");
                        break;

                //VER DATOS DE UN PERSONAJE  
                case 4: 
                    System.out.print("Ingrese el ID del personaje a consultar: ");
                        int idVer = scanner.nextInt();
                    scanner.nextLine();

                    int posicionver = buscarPorId(ids, cantidad, idVer);

                        if (posicionver == -1) {
                    System.out.println("No se encontro un personaje con ese ID.");
            } else {
                    System.out.println("----- DATOS DEL PERSONAJE -----");
                    System.out.println("ID: " + ids[posicionver]);
                    System.out.println("Nombre: " + nombres[posicionver]);
                    System.out.println("Arma: " + armas[posicionver]);
                    System.out.println("Nivel de poder (fuerza): " + fuerzas[posicionver]);
                    System.out.println("Habilidades:");
            for (int j = 0; j < 5; j++) {
                    System.out.println(" - " + habilidades[posicionver][j]);
        }
    }
    break;
                // Ver listado de personajes
                case 5: 
                        if (cantidad == 0) {
                    System.out.println("No hay personajes registrados en el sistema.");
                        } else {
                    System.out.println("----- LISTADO DE PERSONAJES -----");
                        for (int i = 0; i < cantidad; i++) {
                    System.out.println("ID: " + ids[i] +
                               " | Nombre: " + nombres[i] +
                               " | Nivel de poder: " + fuerzas[i]);
        }
    }
                    break;
                // Realizar pelea entre personajes
                case 6: 
                        if (cantidad < 2) {
                    System.out.println("Se necesitan al menos 2 personajes para una pelea.");
                    break;
    }

                    System.out.print("ID del primer personaje: ");
                        int id1 = scanner.nextInt();
                    System.out.print("ID del segundo personaje: ");
                        int id2 = scanner.nextInt();
                    scanner.nextLine();

                int i1 = buscarPorId(ids, cantidad, id1);
                int i2 = buscarPorId(ids, cantidad, id2);

                    if (i1 == -1 || i2 == -1) {
                    System.out.println("Uno o ambos personajes no existen.");
                break;
    }

                    if (i1 == i2) {
                    System.out.println("No puedes enfrentar a un personaje consigo mismo.");
                break;
    }

                // Determinar ganador
                String ganador;
                    if (fuerzas[i1] > fuerzas[i2]) {
                    ganador = nombres[i1];
                } else if (fuerzas[i2] > fuerzas[i1]) {
                    ganador = nombres[i2];
                } else {
                    ganador = "Empate";
    }

                // Fecha y hora y regitro de pelea
                LocalDateTime fechaHora = LocalDateTime.now();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                    String fechaFormateada = fechaHora.format(formato);

                String pelea = nombres[i1] + " (" + fuerzas[i1] + ") vs " +
                   nombres[i2] + " (" + fuerzas[i2] + ") -> Ganador: " + ganador +
                   " | Fecha: " + fechaFormateada;

            historialPeleas[peleasRealizadas++] = pelea;

            
                    System.out.println("--- Resultado de la pelea ---");
                    System.out.println(pelea);
                break;
                    
                
                // Ver historial de peleas
                case 7: 
                        if (peleasRealizadas == 0) {
                    System.out.println("No hay peleas registradas en el historial.");
                        } else {
                    System.out.println("--- Historial de Peleas ---");
                        for (int i = 0; i < peleasRealizadas; i++) {
                    System.out.println((i + 1) + ". " + historialPeleas[i]);
        }
    }
                break;
                
                
                // Ver datos del estudiante
                case 8: 
                System.out.println("\n--- Datos del Estudiante ---");
                System.out.println("Nombre: Alejandro Emmanuel Alvarez Velasquez");       
                System.out.println("Carne: 202404224");        
                System.out.println("Curso: IPC1");
                System.out.println("Seccion: A");               
            break;
                    
                    
            case 9:
                    System.out.println("Finalizado");
                    break;

                default:
                    System.out.println("Opción no valida.");
                    break;
            }       
            } 
        scanner.close();

}
                static int buscarPorId(int[] ids, int cantidad, int idBuscado) {
                    for (int i = 0; i < cantidad; i++) {
                        if (ids[i] == idBuscado) return i;
        }
                    return -1;
}
}