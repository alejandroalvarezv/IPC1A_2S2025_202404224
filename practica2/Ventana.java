package practica2;

import javax.swing.*;
import java.awt.*;

public class Ventana extends JFrame {

    public static final int MAX_PERSONAJES = 100;
    public static Personaje[] personajes = new Personaje[MAX_PERSONAJES];
    public static int cantidadPersonajes = 0;

    public Ventana() {
        setTitle("Arena USAC");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnAgregar = new JButton("Agregar Personaje");

        btnAgregar.addActionListener(e -> {
            new AgregarPersonaje(this).setVisible(true);
        });

        JPanel panel = new JPanel();
        panel.add(btnAgregar);

        add(panel);
        setVisible(true);
    }

    public static boolean nombreDuplicado(String nombre) {
        for (int i = 0; i < cantidadPersonajes; i++) {
            if (personajes[i].getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    public static boolean agregarPersonaje(Personaje p) {
        if (cantidadPersonajes >= MAX_PERSONAJES) {
            return false;
        }
        personajes[cantidadPersonajes] = p;
        cantidadPersonajes++;
        return true;
    }

    public static void main(String[] args) {
        new Ventana();
    }
}
