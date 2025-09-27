package practica2;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.File;
import java.io.FileReader;


public class Ventana extends JFrame {

    public static final int MAX_PERSONAJES = 100;
    public static Personaje[] personajes = new Personaje[MAX_PERSONAJES];
    public static int cantidadPersonajes = 0;
    public static final int MAX_HISTORIAL = 100;
    public static String[] historialNombres = new String[MAX_HISTORIAL];
    public static int cantidadHistorial = 0;
    public static final int MAX_BATALLAS = 100;
    public static HistorialBatalla[] historialBatallas = new HistorialBatalla[MAX_BATALLAS];
    public static int cantidadBatallas = 0;

    public static void agregarBatallaHistorial(HistorialBatalla batalla) {
        if (cantidadBatallas < MAX_BATALLAS) {
            historialBatallas[cantidadBatallas++] = batalla;
        }
    }
    


    public Ventana() {
        setTitle("Arena USAC");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //crear botones
        JButton btnAgregar = new JButton("Agregar Personaje");
        JButton btnModificar = new JButton("Modificar Personaje");
        JButton btnEliminar = new JButton("Eliminar Personaje");
        JButton btnVisualizar = new JButton("Visualizar Personajes");
        JButton btnBatalla = new JButton("Simular Batalla");
        JButton btnHistorial = new JButton("Historial de batallas");
        JButton btnBuscar = new JButton("Buscar Personaje");
        JButton btnGuardar = new JButton("Guardar Estado");
        JButton btnCargar = new JButton("Cargar Estado");
        JButton btnDatosEstudiante = new JButton("Mostrar datos del estudiante");

        //accion boton agregar
        btnAgregar.addActionListener(e -> {
            new AgregarPersonaje(this).setVisible(true);
        });
            
        //accion boton modificar
        btnModificar.addActionListener(e -> {
            String entrada = JOptionPane.showInputDialog(this, "Ingrese ID o Nombre del personaje:");
            if (entrada != null && !entrada.trim().isEmpty()) {
                Personaje encontrado = buscarPorNombreOID(entrada.trim());
                if (encontrado != null) {
                    new ModificarPersonaje(this, encontrado).setVisible(true);
            }else {
                    JOptionPane.showMessageDialog(this, "Personaje no encontrado.");
                }
            }
                });
        
        //accion boton eliminar
        btnEliminar.addActionListener(e -> {
            String entrada = JOptionPane.showInputDialog(this, "Ingrese ID o Nombre del personaje a eliminar:");
            if (entrada != null && !entrada.trim().isEmpty()) {
                Personaje encontrado = buscarPorNombreOID(entrada.trim());
                if (encontrado != null) {
                    int confirm = JOptionPane.showConfirmDialog(this,
                "Seguro que desea eliminar al personaje:\n" + encontrado + "?",
                "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean eliminado = eliminarPersonajePorNombreOID(entrada.trim());
                    if (eliminado){
                        JOptionPane.showMessageDialog(this, "Personaje eliminado correctamente.");
                    }else{
                        JOptionPane.showMessageDialog(this, "Error al eliminar el personaje.");
                    }
                }
            }else {
                    JOptionPane.showMessageDialog(this, "Personaje no encontrado.");
                }
            }
        });
        //accion boton visualizar
        btnVisualizar.addActionListener(e -> {
            String lista = obtenerListaPersonajes();
                JTextArea textArea = new JTextArea(lista);
                    textArea.setEditable(false);
                        JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(500, 300));
                JOptionPane.showMessageDialog(this, scrollPane, "Personajes Registrados", JOptionPane.INFORMATION_MESSAGE);
        });
        
        //simular batalla
        btnBatalla.addActionListener(e -> {
            if (cantidadPersonajes ==0){
                JOptionPane.showMessageDialog(null, "No hay personajes disponibles para realizar la batalla", "ERROR", JOptionPane.ERROR_MESSAGE);
            }else{
                new SimularBatalla(this).setVisible(true);
            }
        });
        
        
        //Historial de batallas
        btnHistorial.addActionListener(e -> verHistorialBatallas(this));
        
        //Buscar personajes
        btnBuscar.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del personaje:");
                if (nombre != null && !nombre.trim().isEmpty()) {
                    buscarPersonajePorNombre(nombre.trim());
                } else {
                    JOptionPane.showMessageDialog(this, "Entrada inválida.");
                }
        });
        
        //Mostrar datos del estudiante
        btnDatosEstudiante.addActionListener(e -> verDatosEstudiante());

        
        
        btnGuardar.addActionListener(e -> guardarEstadoEnArchivo());
        btnCargar.addActionListener(e -> cargarEstadoDesdeArchivo());

        
        
        //crear panel y agregar botones
        JPanel panel = new JPanel();
        panel.add(btnAgregar);
        panel.add(btnModificar);
        panel.add(btnEliminar);
        panel.add(btnVisualizar);
        panel.add(btnBatalla);
        panel.add(btnHistorial);
        panel.add(btnBuscar);
        panel.add(btnGuardar);
        panel.add(btnCargar);
        panel.add(btnDatosEstudiante);
        
        add(panel);
        setVisible(true);
        }

        //ver duplicados
            public static boolean nombreDuplicado(String nombre) {
                for (int i = 0; i < cantidadPersonajes; i++) {
                     if (personajes[i].getNombre().equalsIgnoreCase(nombre)) {
                return true;
                }
            }
            return false;
            }
       
        //Agregar personaje
            public static boolean agregarPersonaje(Personaje p) {
                if (cantidadPersonajes >= MAX_PERSONAJES) {
            return false;
            }
                personajes[cantidadPersonajes] = p;
                cantidadPersonajes++;
                    return true;
            }
        
            
        //Buscar por nombre o ID
            public static Personaje buscarPorNombreOID(String entrada) {
                for (int i = 0; i < cantidadPersonajes; i++) {
                    Personaje p = personajes[i];
                        if (p.getNombre().equalsIgnoreCase(entrada)) {
                return p;
            }
            try {
                int id = Integer.parseInt(entrada);
                if (p.getId() == id) {
                    return p;
                }
            } catch (NumberFormatException ignored) {}
        }
        return null;
    }
                
            public static void agregarNombreAlHistorial(String nombre) {
                if (cantidadHistorial < MAX_HISTORIAL) {
                    historialNombres[cantidadHistorial] = nombre;
                    cantidadHistorial++;
                }
            }
            
            
            public static boolean eliminarPersonajePorNombreOID(String entrada) {
                for (int i = 0; i < cantidadPersonajes; i++) {
                    Personaje p = personajes[i];
                        if (p.getNombre().equalsIgnoreCase(entrada) || String.valueOf(p.getId()).equals(entrada)) {
                            agregarNombreAlHistorial(p.getNombre());
                    for (int j = i; j < cantidadPersonajes - 1; j++) {
                        personajes[j] = personajes[j + 1];
                    }
                    personajes[cantidadPersonajes - 1] = null;
                    cantidadPersonajes--;
                    return true;
                        }
                }
                return false;
            }
            
            
            public static String obtenerListaPersonajes() {
                StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < cantidadPersonajes; i++) {
                        Personaje p = personajes[i];
                        String estado = (p.getHp()>0) ? "Vivo" : "Muerto";
                                
                        sb.append(String.format(
                        "ID: %d | Nombre: %s | Arma: %s | HP: %d | Ataque: %d | Defensa: %d | Agilidad: %d | Velocidad: %d | Estado: %s%n",
                        p.getId(), p.getNombre(), p.getArma(), p.getHp(), p.getAtaque(),
                        p.getDefensa(), p.getAgilidad(), p.getVelocidad(), estado));
            }

                    if (sb.length() == 0) {
                return "No hay personajes vivos registrados.";
            }

                return sb.toString();
        }       
            
            public static void verHistorialBatallas(JFrame parent) {
                if (cantidadBatallas == 0) {
                    JOptionPane.showMessageDialog(parent, "No hay batallas registradas.");
                return;
            }

            StringBuilder sb = new StringBuilder();
                for (int i = 0; i < cantidadBatallas; i++) {
                    sb.append(historialBatallas[i].toString()).append("\n----------------------\n");
            }

                JTextArea textArea = new JTextArea(sb.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 400));
                JOptionPane.showMessageDialog(parent, scrollPane, "Historial de Batallas", JOptionPane.INFORMATION_MESSAGE);
            }
            
            
            public static void buscarPersonajePorNombre(String nombreBuscado) {
                boolean encontrado = false;
                    int totalBatallas = 0;
                    int ganadas = 0;
                    int perdidas = 0;

                for (int i = 0; i < cantidadPersonajes; i++) {
                    Personaje p = personajes[i];
                if (p.getNombre().equalsIgnoreCase(nombreBuscado)) {
                    encontrado = true;

            String mensaje = "ID: " + p.getId() + "\n"
                    + "Nombre: " + p.getNombre() + "\n"
                    + "Arma: " + p.getArma() + "\n"
                    + "HP: " + p.getHp() + "\n"
                    + "Ataque: " + p.getAtaque() + "\n"
                    + "Defensa: " + p.getDefensa() + "\n"
                    + "Agilidad: " + p.getAgilidad() + "\n"
                    + "Velocidad: " + p.getVelocidad() + "\n";

            // Contar batallas ganadas y perdidas
            for (int j = 0; j < cantidadBatallas; j++) {
                HistorialBatalla h = historialBatallas[j];
                if (h.getParticipante1().equalsIgnoreCase(nombreBuscado) || h.getParticipante2().equalsIgnoreCase(nombreBuscado)) {
                    totalBatallas++;
                    if (h.getGanador().equalsIgnoreCase(nombreBuscado)) {
                        ganadas++;
                    } else {
                        perdidas++;
                    }
                }
            }

            mensaje += "\nTotal de batallas: " + totalBatallas
                    + "\nGanadas: " + ganadas
                    + "\nPerdidas: " + perdidas;

            // Mostrar con JOptionPane (ventana emergente)
            JOptionPane.showMessageDialog(null, mensaje);
            break;
                }
            }

                    if (!encontrado) {
                        JOptionPane.showMessageDialog(null, "Personaje no encontrado.");
                }
            }
            
            
            public static void guardarEstadoEnArchivo() {
                try {
                    String timestamp = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date());
                    String fileName = "ArenaUsac-" + timestamp + ".txt";
                    PrintWriter writer = new PrintWriter(fileName, "UTF-8");

            // Guardar personajes en formato separado por ';'
                writer.println("--- PERSONAJES ---");
                    for (int i = 0; i < cantidadPersonajes; i++) {
                Personaje p = personajes[i];
                writer.printf("%d;%s;%s;%d;%d;%d;%d;%d%n",
                p.getId(), p.getNombre(), p.getArma(), p.getHp(), p.getAtaque(),
                p.getDefensa(), p.getAgilidad(), p.getVelocidad());
        }

            // Guardar batallas en formato separado por ';'
                writer.println("--- HISTORIAL DE BATALLAS ---");
                    for (int i = 0; i < cantidadBatallas; i++) {
                HistorialBatalla h = historialBatallas[i];
                writer.printf("%d;%s;%s;%s;%s%n",
                h.getNumero(), h.getFecha(), h.getParticipante1(),
                h.getParticipante2(), h.getGanador());
            }

                writer.close();
                    JOptionPane.showMessageDialog(null, "Estado guardado en: " + fileName);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage());
            }
        }
      

            
            
            
            public static void cargarEstadoDesdeArchivo() {
                JFileChooser fileChooser = new JFileChooser();
                    int seleccion = fileChooser.showOpenDialog(null);

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    try {
                File file = fileChooser.getSelectedFile();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String linea;
                    boolean leyendoPersonajes = false;
                    boolean leyendoBatallas = false;

                        cantidadPersonajes = 0;
                        cantidadBatallas = 0;

            while ((linea = reader.readLine()) != null) {
                if (linea.startsWith("--- PERSONAJES ---")) {
                    leyendoPersonajes = true;
                    leyendoBatallas = false;
                    continue;
                } else if (linea.startsWith("--- HISTORIAL DE BATALLAS ---")) {
                    leyendoPersonajes = false;
                    leyendoBatallas = true;
                    continue;
                }

                if (leyendoPersonajes && !linea.isBlank()) {
                    String[] partes = linea.split(";");
                    int id = Integer.parseInt(partes[0]);
                    String nombre = partes[1];
                    String arma = partes[2];
                    int hp = Integer.parseInt(partes[3]);
                    int ataque = Integer.parseInt(partes[4]);
                    int defensa = Integer.parseInt(partes[5]);
                    int agilidad = Integer.parseInt(partes[6]);
                    int velocidad = Integer.parseInt(partes[7]);

                    personajes[cantidadPersonajes++] = new Personaje(id, nombre, arma, hp, ataque, defensa, agilidad, velocidad);
                } else if (leyendoBatallas && !linea.isBlank()) {
                    String[] partes = linea.split(";");
                    int numero = Integer.parseInt(partes[0]);
                    String fecha = partes[1];
                    String p1 = partes[2];
                    String p2 = partes[3];
                    String ganador = partes[4];

                    historialBatallas[cantidadBatallas++] = new HistorialBatalla(numero, fecha, p1, p2, ganador);
                }
            }

            reader.close();
            JOptionPane.showMessageDialog(null, "Estado cargado desde: " + file.getName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar: " + e.getMessage());
        }
    }
}

            private void verDatosEstudiante() {
                String info = "Datos del estudiante:\n"
                    + "Nombre: Alejandro Emmanuel Alvarez Velasquez\n"
                    + "Carné: 202404224\n"
                    + "Curso: LAB IPC\n"
                    + "Proyecto: Arena USAC\n"
                    + "Seccion: A\n"
                    + "Usuario de Github: alejandroalvarezv";

                JOptionPane.showMessageDialog(this, info, "Datos del Estudiante", JOptionPane.INFORMATION_MESSAGE);
            }
            
            
            

           public static void main(String[] args) {
                new Ventana();
        }
           }
