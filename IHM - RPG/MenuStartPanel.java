import javax.swing.*;
import java.awt.*;

public class MenuStartPanel extends JPanel {

    public interface StartGame {
        void start(String heroName, String heroClass);
    }

    private JTextField nameInput;
    private JComboBox<String> classChoice;
    private JButton playButton;
    private JButton shopButton;

    public MenuStartPanel(StartGame callback) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Bienvenue dans RPG 'Direction X'");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        formPanel.add(new JLabel("Nom du héros :"));
        nameInput = new JTextField();
        formPanel.add(nameInput);

        formPanel.add(new JLabel("Choisir la classe :"));
        classChoice = new JComboBox<>(new String[]{"Sorcier", "Chevalier", "Elfe"});
        formPanel.add(classChoice);

        add(formPanel, BorderLayout.CENTER);
        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        shopButton = new JButton("Magasin d’armes");
        playButton = new JButton("Jouer !");
        buttonsPanel.add(shopButton);
        buttonsPanel.add(playButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        playButton.addActionListener(e -> callback.start(getHeroName(), getHeroClass()));
        nameInput.addActionListener(e -> playButton.doClick());

        shopButton.addActionListener(e -> {
            WeaponStoreDialog shop = new WeaponStoreDialog(null, null);
            shop.setVisible(true);
        });
    }

    public String getHeroName() {
        String text = nameInput.getText().trim();
        return text.isEmpty() ? "Héros" : text;
    }

    public String getHeroClass() {
        return (String) classChoice.getSelectedItem();
    }
}
