package practica2;
import javax.swing.*;
import java.awt.*;
public class SimularBatalla extends JDialog {

    private JComboBox<String> combo1, combo2;

    public SimularBatalla(JFrame parent) {
        super(parent, "Seleccionar personajes para batalla", true);
        setSize(400, 200);
        setLayout(new GridLayout(3, 2));
        setLocationRelativeTo(parent);

        combo1 = new JComboBox<>();
        combo2 = new JComboBox<>();

        // Agregar personajes vivos al combo
        for (int i = 0; i < Ventana.cantidadPersonajes; i++) {
            Personaje p = Ventana.personajes[i];
            if (p.getHp() > 0) {
                combo1.addItem(p.getNombre() + " (ID: " + p.getId() + ")");
                combo2.addItem(p.getNombre() + " (ID: " + p.getId() + ")");
            }
        }

        add(new JLabel("Personaje 1:")); add(combo1);
        add(new JLabel("Personaje 2:")); add(combo2);

        JButton btnIniciar = new JButton("Iniciar Batalla");
        btnIniciar.addActionListener(e -> iniciarBatalla());
        add(btnIniciar);
    }

    private void iniciarBatalla() {
        String seleccion1 = (String) combo1.getSelectedItem();
        String seleccion2 = (String) combo2.getSelectedItem();

        if (seleccion1.equals(seleccion2)) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar dos personajes distintos.");
            return;
        }

        // Obtener IDs desde el texto del combo
        int id1 = Integer.parseInt(seleccion1.split("ID: ")[1].replace(")", ""));
        int id2 = Integer.parseInt(seleccion2.split("ID: ")[1].replace(")", ""));

        Personaje p1 = Ventana.buscarPorNombreOID(String.valueOf(id1));
        Personaje p2 = Ventana.buscarPorNombreOID(String.valueOf(id2));

        if (p1 == null || p2 == null || p1.getHp() <= 0 || p2.getHp() <= 0) {
            JOptionPane.showMessageDialog(this, "Ambos personajes deben existir y estar vivos.");
            return;
        }

        new VentanaBatalla(p1, p2).setVisible(true);
        dispose(); // cerrar selección
    }
}
