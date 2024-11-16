package presentacion;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class POOBvsZombiesGUI extends JFrame {

    // Atributos
    private JPanel menuPanel;
    private JComboBox<String> modalidadComboBox, modoJuegoComboBox, dificultadComboBox;
    private JButton exitButton;
    private JButton playButton;

    public POOBvsZombiesGUI() {
        super("POOBvsZombies");
        prepareElements();
        prepareMenu();
        prepareActions();
    }

    // Configuración general de la ventana
    private void prepareElements() {
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width / 2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Configura el menú
    private void prepareMenu() {
        // Panel principal
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBackground(new Color(8, 105, 14));
        setContentPane(contentPane);
        ImageIcon icon = new ImageIcon("C:\\Users\\adm\\Desktop\\POOBvsZombies\\Imagenes\\Menu_proyecto.png");
        JLabel imageLabel = new JLabel(icon);
        contentPane.add(imageLabel, BorderLayout.NORTH); // Añadir la imagen en la parte superior

        // Panel del menú centrado abajo
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(8, 105, 14));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        menuPanel.add(Box.createVerticalGlue());

        // Añadimos secciones con los colores personalizados
        JComboBox<String> comboBox1 = new JComboBox<>(new String[]{"Player vs Player (PvsP)", "Player vs Machine (PvsM)", "Machine vs Machine (MvsM)"});
        comboBox1.setBackground(new Color(135, 87, 11)); // Fondo blanco
        comboBox1.setForeground(new Color(219, 195, 54)); // Texto negro
        menuPanel.add(createLabeledPanel("Selecciona Modalidad:", comboBox1));

        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JComboBox<String> comboBox2 = new JComboBox<>(new String[]{"Día"});
        comboBox2.setBackground(new Color(135, 87, 11)); // Fondo blanco
        comboBox2.setForeground(new Color(219, 195, 54)); // Texto negro
        menuPanel.add(createLabeledPanel("Selecciona Modo de Juego:", comboBox2));

        menuPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JComboBox<String> comboBox3 = new JComboBox<>(new String[]{"Fácil", "Medio", "Difícil"});
        comboBox3.setBackground(new Color(135, 87, 11)); // Fondo blanco
        comboBox3.setForeground(new Color(219, 195, 54)); // Texto negro
        menuPanel.add(createLabeledPanel("Selecciona Nivel de Dificultad:", comboBox3));

        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Panel para los botones "Salir" y "Jugar"
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonsPanel.setBackground(new Color(8, 105, 14));

        // Botón de salida
        exitButton = new JButton("Salir");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMaximumSize(new Dimension(150, 40));
        exitButton.setBackground(new Color(127, 121, 172));
        exitButton.setForeground(new Color (48, 228, 30));
        buttonsPanel.add(exitButton);

        // Botón de jugar
        playButton = new JButton("Jugar");
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setMaximumSize(new Dimension(150, 40));
        playButton.setBackground(new Color(127, 121, 172));
        playButton.setForeground(new Color (48, 228, 30));
        buttonsPanel.add(playButton);

        // Añadir los botones al panel de menú
        menuPanel.add(buttonsPanel); // Ahora agregamos el panel de botones

        menuPanel.add(Box.createVerticalGlue());

        // Agregar el menú al contenedor principal en la parte inferior
        contentPane.add(menuPanel, BorderLayout.CENTER);
    }



    // Crea un panel con un JLabel y un JComboBox centrado
    private JPanel createLabeledPanel(String labelText, JComboBox<String> comboBox) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.setBackground(new Color(8, 105, 14));

        panel.add(label);

        comboBox.setPreferredSize(new Dimension(200, 30));
        comboBox.setMaximumSize(new Dimension(200, 30));
        panel.add(comboBox);

        return panel;
    }

    private void prepareActions() {
        // Agregar acción al botón de salida
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mostrar confirmación al hacer clic en el botón "Salir"
                confirmExit();
            }
        });

        // Agregar acción al botón de jugar
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("El juego ha comenzado");
            }
        });

        // Agregar un WindowListener para manejar el cierre con la "X"
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Cerrar la ventana sin confirmar cuando se oprime la "X"
                System.exit(0);  // Cerrar la aplicación sin confirmación
            }
        });
    }

    // Método para confirmar la salida al hacer clic en el botón "Salir"
    private void confirmExit() {
        int opcion = JOptionPane.showConfirmDialog(
                POOBvsZombiesGUI.this,
                "¿Seguro que quieres salir del juego?",
                "Abandonar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (opcion == JOptionPane.YES_OPTION) {
            System.exit(0);  // Cerrar la aplicación si el usuario confirma
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            POOBvsZombiesGUI gui = new POOBvsZombiesGUI();
            gui.setVisible(true);
        });
    }
}
