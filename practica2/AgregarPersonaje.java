package practica2;
import javax.swing.*;
import java.awt.*;

public class AgregarPersonaje extends JDialog {

    private JTextField txtNombre, txtArma, txtHP, txtAtaque, txtVelocidad, txtAgilidad, txtDefensa;

    public AgregarPersonaje(JFrame parent) {
        super(parent, "Agregar Personaje", true);
        setSize(400, 400);
        setLayout(new GridLayout(8, 2));
        setLocationRelativeTo(parent);

        txtNombre = new JTextField();
        txtArma = new JTextField();
        txtHP = new JTextField();
        txtAtaque = new JTextField();
        txtVelocidad = new JTextField();
        txtAgilidad = new JTextField();
        txtDefensa = new JTextField();

        add(new JLabel("Nombre:"));
        add(txtNombre);
        add(new JLabel("Arma:"));
        add(txtArma);
        add(new JLabel("HP (100-500):"));
        add(txtHP);
        add(new JLabel("Ataque (10-100):"));
        add(txtAtaque);
        add(new JLabel("Velocidad (1-10):"));
        add(txtVelocidad);
        add(new JLabel("Agilidad (1-10):"));
        add(txtAgilidad);
        add(new JLabel("Defensa (1-50):"));
        add(txtDefensa);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarPersonaje());
        add(btnGuardar);
    }

    private void guardarPersonaje() {
        try {
            String nombre = txtNombre.getText().trim();
            String arma = txtArma.getText().trim();
            int hp = Integer.parseInt(txtHP.getText());
            int ataque = Integer.parseInt(txtAtaque.getText());
            int velocidad = Integer.parseInt(txtVelocidad.getText());
            int agilidad = Integer.parseInt(txtAgilidad.getText());
            int defensa = Integer.parseInt(txtDefensa.getText());

            // Validaciones
            if (nombre.isEmpty() || arma.isEmpty()) {
                throw new Exception("Nombre y Arma no pueden estar vacíos.");
            }
            if (hp < 100 || hp > 500) throw new Exception("HP fuera de rango.");
            if (ataque < 10 || ataque > 100) throw new Exception("Ataque fuera de rango.");
            if (velocidad < 1 || velocidad > 10) throw new Exception("Velocidad fuera de rango.");
            if (agilidad < 1 || agilidad > 10) throw new Exception("Agilidad fuera de rango.");
            if (defensa < 1 || defensa > 50) throw new Exception("Defensa fuera de rango.");

            // Verificar duplicado
            if (Ventana.nombreDuplicado(nombre)) {
                throw new Exception("Ya existe un personaje con ese nombre.");
            }

            // Crear y agregar
            Personaje nuevo = new Personaje(nombre, arma, hp, ataque, velocidad, agilidad, defensa);
            boolean agregado = Ventana.agregarPersonaje(nuevo);

            if (!agregado) {
                throw new Exception("No se puede agregar más personajes. Capacidad llena.");
            }

            JOptionPane.showMessageDialog(this, "Personaje agregado:\n" + nuevo);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Todos los valores numéricos deben ser números enteros.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}