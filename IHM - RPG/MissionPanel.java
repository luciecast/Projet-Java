import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MissionPanel extends JPanel {
    private String[] missionText = {
        "Quelques jours plus tard, notre héros reçoit une mission urgente.",
        "Un ancien donjon s'est réveillé, libérant des créatures cauchemardesques.",
        "Sans hésiter, il s'équipe et part affronter les ténèbres...",
        "Mais au cœur du donjon, un piège se referme sur lui.",
        "Il est bloqué. Il doit trouver un moyen de s'échapper..."
    };

    private int currentLine = 0;
    private String displayedText = "";
    private Timer textTimer;
    private int charIndex = 0;
    private Image dungeonImg;
    private Player player;

    public MissionPanel(Player player) {
        this.player = player;
        setLayout(null);
        setBackground(Color.BLACK);

        try {
            dungeonImg = new ImageIcon("images/fond2.png").getImage(); 
        } catch (Exception e) { e.printStackTrace(); }

        startLineTimer();

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (charIndex >= missionText[currentLine].length()) {
                    if (currentLine < missionText.length - 1) {
                        currentLine++;
                        displayedText = "";
                        charIndex = 0;
                        startLineTimer();
                    } else {
                        Window window = SwingUtilities.getWindowAncestor(MissionPanel.this);
                        window.dispose(); 
                        Map map = new Map(8); 
                        new GameWindow(player, map); 
                    }
                } else {
                    textTimer.stop();
                    displayedText = missionText[currentLine];
                    charIndex = missionText[currentLine].length();
                    repaint();
                }
            }
        });
    }

    private void startLineTimer() {
        textTimer = new Timer(50, e -> {
            String line = missionText[currentLine];
            if (charIndex < line.length()) {
                displayedText += line.charAt(charIndex);
                charIndex++;
                repaint();
            } else {
                textTimer.stop();
                repaint();
            }
        });
        textTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();

        if (dungeonImg != null) g.drawImage(dungeonImg, 0, 0, w, h, this);

        int boxHeight = 160;
        int padding = 20;
        int boxX = padding;
        int boxY = h - boxHeight - padding;
        int boxWidth = w - 2 * padding;

        g.setColor(new Color(0, 0, 0, 180));
        g.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);
        g.setColor(Color.WHITE);
        g.drawRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);

        g.setFont(new Font("Serif", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        FontMetrics fm = g.getFontMetrics();
        int lineHeight = fm.getHeight();
        int x = boxX + 20;
        int y = boxY + 40;

        String[] words = displayedText.split(" ");
        String line = "";
        for (String word : words) {
            String testLine = line.isEmpty() ? word : line + " " + word;
            int testWidth = fm.stringWidth(testLine);
            if (testWidth > boxWidth - 40) {
                g.drawString(line, x, y);
                line = word;
                y += lineHeight;
            } else {
                line = testLine;
            }
        }
        if (!line.isEmpty()) g.drawString(line, x, y);

        if (charIndex >= missionText[currentLine].length() && currentLine < missionText.length - 1) {
            g.setFont(new Font("Serif", Font.PLAIN, 14));
            g.drawString("Cliquer pour continuer...", boxX + boxWidth - 180, boxY + boxHeight - 20);
        }
    }
}
