package practica2;
import javax.swing.*;
import java.awt.*;
public class ModificarPersonaje extends JDialog {

    private JTextField txtArma, txtHP, txtAtaque, txtVelocidad, txtAgilidad, txtDefensa;
    private Personaje personaje;

    public ModificarPersonaje(JFrame parent, Personaje personaje) {
        super(parent, "Modificar Personaje: " + personaje.getNombre(), true);
        this.personaje = personaje;

        setSize(400, 400);
        setLayout(new GridLayout(7, 2));
        setLocationRelativeTo(parent);

        txtArma = new JTextField(personaje.getArma());
        txtHP = new JTextField(String.valueOf(personaje.getHp()));
        txtAtaque = new JTextField(String.valueOf(personaje.getAtaque()));
        txtVelocidad = new JTextField(String.valueOf(personaje.getVelocidad()));
        txtAgilidad = new JTextField(String.valueOf(personaje.getAgilidad()));
        txtDefensa = new JTextField(String.valueOf(personaje.getDefensa()));

        add(new JLabel("Arma:")); add(txtArma);
        add(new JLabel("HP (100-500):")); add(txtHP);
        add(new JLabel("Ataque (10-100):")); add(txtAtaque);
        add(new JLabel("Velocidad (1-10):")); add(txtVelocidad);
        add(new JLabel("Agilidad (1-10):")); add(txtAgilidad);
        add(new JLabel("Defensa (1-50):")); add(txtDefensa);

        JButton btnGuardar = new JButton("Guardar Cambios");
        btnGuardar.addActionListener(e -> guardarCambios());
        add(btnGuardar);
    }

    private void guardarCambios() {
        try {
            String arma = txtArma.getText().trim();
            int hp = Integer.parseInt(txtHP.getText().trim());
            int ataque = Integer.parseInt(txtAtaque.getText().trim());
            int velocidad = Integer.parseInt(txtVelocidad.getText().trim());
            int agilidad = Integer.parseInt(txtAgilidad.getText().trim());
            int defensa = Integer.parseInt(txtDefensa.getText().trim());

            if (arma.isEmpty()) throw new Exception("El campo Arma no puede estar vacío.");
            if (hp < 100 || hp > 500) throw new Exception("HP fuera de rango.");
            if (ataque < 10 || ataque > 100) throw new Exception("Ataque fuera de rango.");
            if (velocidad < 1 || velocidad > 10) throw new Exception("Velocidad fuera de rango.");
            if (agilidad < 1 || agilidad > 10) throw new Exception("Agilidad fuera de rango.");
            if (defensa < 1 || defensa > 50) throw new Exception("Defensa fuera de rango.");

            personaje.setArma(arma);
            personaje.setHp(hp);
            personaje.setAtaque(ataque);
            personaje.setVelocidad(velocidad);
            personaje.setAgilidad(agilidad);
            personaje.setDefensa(defensa);

            JOptionPane.showMessageDialog(this, "Personaje modificado:\n" + personaje);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Todos los valores numéricos deben ser enteros.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}