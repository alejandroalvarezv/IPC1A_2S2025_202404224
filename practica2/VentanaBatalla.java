package practica2;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class VentanaBatalla extends JFrame {

    private JTextArea areaBitacora;

    public VentanaBatalla(Personaje p1, Personaje p2) {
        setTitle("Batalla: " + p1.getNombre() + " vs " + p2.getNombre());
        setSize(600, 400);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        areaBitacora = new JTextArea();
        areaBitacora.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaBitacora);
        add(scroll, BorderLayout.CENTER);

        // Mostrar inicio
        appendToBitacora("Inicia batalla entre " + p1.getNombre() + " y " + p2.getNombre());
        appendToBitacora("Hora: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));

        // Lógica de batalla en un hilo nuevo
        new Thread(() -> Batalla.simularBatalla(p1, p2, this)).start();
    }

    public void appendToBitacora(String texto) {
        SwingUtilities.invokeLater(() -> areaBitacora.append(texto + "\n"));
    }
}
