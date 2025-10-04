package proyecto2;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Proyecto2 extends JFrame {

    private HashMap<String, String[]> usuarios = new HashMap<>();
    private JFrame ventanaActual;

    public Proyecto2() {
        usuarios.put("admin", new String[]{"IPC1A", "Administrador"});
        usuarios.put("vendedor1", new String[]{"pass123", "Vendedor"});
        usuarios.put("cliente1", new String[]{"pass456", "Cliente"});
        
        mostrarPantallaLogin();
    }

    private void mostrarPantallaLogin() {
        if (ventanaActual != null) ventanaActual.dispose();

        JFrame loginFrame = new JFrame("Inicio de Sesión");
        loginFrame.setSize(350, 200);
        loginFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(new GridLayout(4, 2));

        JLabel lblCodigo = new JLabel("Código:");
        JTextField txtCodigo = new JTextField();

        JLabel lblPassword = new JLabel("Contraseña:");
        JPasswordField txtPassword = new JPasswordField();

        JButton btnLogin = new JButton("Iniciar Sesión");
        JLabel lblMensaje = new JLabel("");

        btnLogin.addActionListener(e -> {
            String codigo = txtCodigo.getText().trim();
            String pass = new String(txtPassword.getPassword()).trim();

            if (!usuarios.containsKey(codigo)) {
                lblMensaje.setText("Usuario no registrado.");
            } else {
                String[] datos = usuarios.get(codigo);
                String passCorrecta = datos[0];
                String rol = datos[1];

                if (!pass.equals(passCorrecta)) {
                    lblMensaje.setText("Contraseña incorrecta.");
                } else {
                    switch (rol) {
                        case "Administrador":
                            mostrarVistaAdministrador(codigo);
                            break;
                        case "Vendedor":
                            mostrarVistaVendedor(codigo);
                            break;
                        case "Cliente":
                            mostrarVistaCliente(codigo);
                            break;
                        default:
                            lblMensaje.setText("Rol desconocido.");
                    }
                }
            }
        });

        loginFrame.add(lblCodigo);
        loginFrame.add(txtCodigo);
        loginFrame.add(lblPassword);
        loginFrame.add(txtPassword);
        loginFrame.add(new JLabel("")); // espacio vacío para cuadrícula
        loginFrame.add(btnLogin);
        loginFrame.add(lblMensaje);
        loginFrame.add(new JLabel("")); // completar para 8 elementos

        loginFrame.setVisible(true);
        ventanaActual = loginFrame;
    }

    private void mostrarVistaAdministrador(String codigo) {
        JFrame adminFrame = crearVentana("Administrador - " + codigo);
        adminFrame.setLayout(new FlowLayout());
        adminFrame.add(new JLabel("Bienvenido, Administrador"));
        mostrarBotonLogout(adminFrame);
    }

    private void mostrarVistaVendedor(String codigo) {
        JFrame vendedorFrame = crearVentana("Vendedor - " + codigo);
        vendedorFrame.setLayout(new FlowLayout());
        vendedorFrame.add(new JLabel("Bienvenido, Vendedor"));
        mostrarBotonLogout(vendedorFrame);
    }

    private void mostrarVistaCliente(String codigo) {
        JFrame clienteFrame = crearVentana("Cliente - " + codigo);
        clienteFrame.setLayout(new FlowLayout());
        clienteFrame.add(new JLabel("Bienvenido, Cliente"));
        mostrarBotonLogout(clienteFrame);
    }

    private JFrame crearVentana(String titulo) {
        if (ventanaActual != null) ventanaActual.dispose();

        JFrame frame = new JFrame(titulo);
        frame.setSize(400, 150);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        ventanaActual = frame;
        return frame;
    }

    private void mostrarBotonLogout(JFrame frame) {
        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.addActionListener(e -> mostrarPantallaLogin());
        frame.add(btnLogout);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Proyecto2());
    }
}
