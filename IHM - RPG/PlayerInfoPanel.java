import javax.swing.*;
import java.awt.*;

public class PlayerInfoPanel extends JPanel {

    public interface StoreOpener {
        void open(Player player);
    }

    private Player hero;
    private JLabel hpText, xpText, goldText, weaponText, potionText;
    private JButton usePotionBtn, storeBtn;

    public PlayerInfoPanel(Player hero, StoreOpener opener) {
        this.hero = hero;


        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(180, 300));
        setOpaque(false); 

        setBorder(BorderFactory.createLineBorder(new Color(100, 50, 0), 2)); 

        Font labelFont = new Font("Serif", Font.BOLD, 16);
        Color textColor = new Color(48, 140, 71); 

        hpText = createStyledLabel(labelFont, textColor);
        xpText = createStyledLabel(labelFont, textColor);
        goldText = createStyledLabel(labelFont, textColor);
        weaponText = createStyledLabel(labelFont, textColor);
        potionText = createStyledLabel(labelFont, textColor);

        add(hpText);
        add(xpText);
        add(goldText);
        add(weaponText);
        add(potionText);
        add(Box.createVerticalStrut(10));

        usePotionBtn = new JButton("Potion");
        storeBtn = new JButton("Magasin");

        styleButton(usePotionBtn, new Color(60, 30, 30));
        styleButton(storeBtn, new Color(30, 30, 60));

        add(usePotionBtn);
        add(storeBtn);

        usePotionBtn.addActionListener(e -> {
            if (hero.potions.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vous n'avez pas de potion !");
                return;
            }
            Potion p = hero.potions.remove(0);
            hero.hp += p.getHealing();
            JOptionPane.showMessageDialog(this, "PV restaurés : +" + p.getHealing() + " → PV actuels : " + hero.hp);
            updateInfo();
        });

        storeBtn.addActionListener(e -> opener.open(hero));
        updateInfo();
        
    }

    private JLabel createStyledLabel(Font font, Color color) {
        JLabel label = new JLabel();
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public void updateInfo() {
        hpText.setText("PV : " + hero.hp);
        xpText.setText("XP : " + hero.xp);
        goldText.setText("Or : " + hero.gold);
        weaponText.setText("Arme : " + (hero.equippedWeapon != null ? hero.equippedWeapon.getName() : "Aucune"));
        potionText.setText("Potions : " + hero.potions.size());
    }

}
