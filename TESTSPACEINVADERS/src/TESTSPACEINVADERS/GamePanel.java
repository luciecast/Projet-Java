package TESTSPACEINVADERS;

import java.util.ArrayList;
import java.util.List;

public class GamePanel {

    public Player player;
    public List<Alien> aliens = new ArrayList<>();
    public List<Projectile> projectiles = new ArrayList<>();

    public GamePanel() {
        reset();
    }

    public void reset() {
        player = new Player(0.0f, -0.8f);
        aliens.clear();
        projectiles.clear();

        int rows = 4;
        int cols = 8;
        float startX = -0.8f;
        float startY = 0.6f;
        float dx = 0.2f;
        float dy = 0.15f;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                float x = startX + c * dx;
                float y = startY - r * dy;
                aliens.add(new Alien(x, y));
            }
        }
    }
}
