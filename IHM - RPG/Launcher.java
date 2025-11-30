import javax.swing.*;
public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Intro RPG");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            IntroPanel intro = new IntroPanel();
            frame.setContentPane(intro);
            frame.setVisible(true);
        });
    }
}
