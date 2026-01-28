package TESTSPACEINVADERS;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Space Invaders 3D");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("SPACE INVADERS");
        title.setForeground(Color.GREEN);
        title.setFont(loadPixelFont(48f));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startButton = new JButton("DEMARRER");
        startButton.setFont(loadPixelFont(20f));
        startButton.setForeground(Color.BLACK);
        startButton.setBackground(Color.GREEN);
        startButton.setFocusPainted(false);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.addActionListener(e -> {
            dispose();
            GameWindow window = new GameWindow("Space Invaders 3D - JOGL");
            window.setVisible(true);
        });

        panel.add(Box.createVerticalGlue());
        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(startButton);
        panel.add(Box.createVerticalGlue());

        add(panel);

        setVisible(true);
    }

    private Font loadPixelFont(float size) {
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/textures/PressStart2P-Regular.ttf"));
            return font.deriveFont(size);
        } catch (Exception e) {
            return new Font("Monospaced", Font.BOLD, (int) size);
        }
    }
}
