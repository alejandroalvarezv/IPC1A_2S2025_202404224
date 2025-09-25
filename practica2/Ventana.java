package practica2;
import javax.swing.*;
import java.awt.*;

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
            new SimularBatalla(this).setVisible(true);
        });
        
        
        //Historial de batallas
        btnHistorial.addActionListener(e -> verHistorialBatallas(this));
        
        
        //crear panel y agregar botones
        JPanel panel = new JPanel();
        panel.add(btnAgregar);
        panel.add(btnModificar);
        panel.add(btnEliminar);
        panel.add(btnVisualizar);
        panel.add(btnBatalla);
        panel.add(btnHistorial);
        
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
                    if (p.getHp() > 0) { // Solo mostrar personajes vivos
                        sb.append(String.format(
                "ID: %d | Nombre: %s | Arma: %s | HP: %d | Ataque: %d | Defensa: %d | Agilidad: %d | Velocidad: %d%n",
                p.getId(), p.getNombre(), p.getArma(), p.getHp(), p.getAtaque(),
                p.getDefensa(), p.getAgilidad(), p.getVelocidad()));
                }
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
            

        
           public static void main(String[] args) {
        new Ventana();
        }
           }
