import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    private MapPanel mapPanel;      
    private PlayerInfoPanel infoPanel;

    public GameWindow(Player hero, Map mapData) {
        setTitle("Donjon - Aventure");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        infoPanel = new PlayerInfoPanel(hero, player -> {
            WeaponStoreDialog store = new WeaponStoreDialog(this, player);
            store.setVisible(true);
            mapPanel.requestFocusInWindow();
            infoPanel.updateInfo();
        });

        mapPanel = new MapPanel(mapData, hero, infoPanel);
        setLayout(new BorderLayout());
        add(mapPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.EAST);

        setVisible(true);
        mapPanel.requestFocusInWindow();
    }
}
