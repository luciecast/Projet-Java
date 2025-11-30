import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class MapPanel extends JPanel {
    private Map mapData;
    private Player hero;
    private PlayerInfoPanel statsPanel;
    private int row = 0, col = 0;

    private Image playerImg, monsterImg, wallImg, floorImg, exitImg, potionImg;

    public MapPanel(Map mapData, Player hero, PlayerInfoPanel statsPanel) {
        this.mapData = mapData;
        this.hero = hero;
        this.statsPanel = statsPanel;

        setFocusable(true);

        playerImg = new ImageIcon("images/player.png").getImage();
        monsterImg = new ImageIcon("images/monster.png").getImage();
        wallImg = new ImageIcon("images/wall.png").getImage();
        floorImg = new ImageIcon("images/floor.jpg").getImage();
        exitImg = new ImageIcon("images/exit.png").getImage();
        potionImg = new ImageIcon("images/potion.png").getImage();

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                handleKey(e.getKeyCode());
            }
        });
    }

    private void handleKey(int key) {
        int nextRow = row;
        int nextCol = col;

        if (key == KeyEvent.VK_W) nextRow--;
        else if (key == KeyEvent.VK_S) nextRow++;
        else if (key == KeyEvent.VK_A) nextCol--;
        else if (key == KeyEvent.VK_D) nextCol++;
        else return;

        if (!isValidPosition(nextRow, nextCol)) return;
        char tile = mapData.getCell(nextRow, nextCol);
        if (tile == 'O') { 
            Toolkit.getDefaultToolkit().beep(); 
            return; 
        }
        if (tile == 'M') {
            String result = fightMonster();
            switch (result) {
                case "gagne":
                    mapData.clearCell(nextRow, nextCol);
                    JOptionPane.showMessageDialog(this, "Monstre vaincu !");
                    break;
                case "fuite":
                    JOptionPane.showMessageDialog(this, "Tu t'enfuis ! Le monstre reste.");
                    return;                           
                case "mort":
                    gameOver();
                    return;
            }
        }
        if (tile == 'S') {
            JOptionPane.showMessageDialog(this, "Félicitations ! Vous avez atteint la sortie !");
            System.exit(0);
        }
        row = nextRow;
        col = nextCol;
        statsPanel.updateInfo();
        repaint();
    }
    private boolean isValidPosition(int r, int c) {
        return r >= 0 && r < mapData.size && c >= 0 && c < mapData.size;
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this, "Vous êtes mort...");
        System.exit(0);
    }

    private String fightMonster() {
        Random rand = new Random();
        int monsterHp = 20 + rand.nextInt(30);
        String[] startOptions = {"Combattre", "S'enfuir"};
        int start = JOptionPane.showOptionDialog(
                this,
                "Un monstre apparaît avec " + monsterHp + " PV !",
                "Combat",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                startOptions,
                startOptions[0]
        );
        if (start == 1) return "fuite";
        while (true) {
            int dmg = hero.getTotalDamage() + rand.nextInt(5);
            monsterHp -= dmg;

            JOptionPane.showMessageDialog(
                    this,
                    "Tu attaques le monstre !\n-" + dmg + " PV\nPV restants : " + Math.max(monsterHp, 0)
            );
            if (monsterHp <= 0) {
                int xp = 10 + rand.nextInt(20);
                int gold = 15;
                hero.xp += xp;
                hero.gold += gold;
                return "gagne";
            }
            int mdmg = rand.nextInt(15);
            hero.hp -= mdmg;

            JOptionPane.showMessageDialog(
                    this,
                    "Le monstre t'attaque ! -" + mdmg + " PV\nTes PV : " + hero.hp
            );

            if (hero.hp <= 0) return "mort";
            String[] turnOptions = {"Continuer", "S'enfuir"};
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "Que veux-tu faire ?",
                    "Combat",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    turnOptions,
                    turnOptions[0]
            );
            if (choice == 1) {
                return "fuite"; 
            }
        }
    }

    // encoreunemethode pour le visuel mais pourla fenetre avec la map
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int size = Math.min(getWidth(), getHeight()) / mapData.size;

        for (int r = 0; r < mapData.size; r++) {
            for (int c = 0; c < mapData.size; c++) {
                char tile = mapData.getCell(r, c);
                int x = c * size;
                int y = r * size;

                g.drawImage(floorImg, x, y, size, size, this);

                switch (tile) {
                    case 'O': g.drawImage(wallImg, x, y, size, size, this); break;
                    case 'M': g.drawImage(monsterImg, x, y, size, size, this); break;
                    case 'S': g.drawImage(exitImg, x, y, size, size, this); break;
                }

                g.setColor(new Color(255, 255, 255, 30));
                g.drawRect(x, y, size, size);
            }
        }
        g.drawImage(playerImg, col * size, row * size, size, size, this);

    }
}
