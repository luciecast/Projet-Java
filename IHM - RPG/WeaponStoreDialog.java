import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class WeaponStoreDialog extends JDialog {
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> list = new JList<>(listModel);

    // Si player est non-null, achats ajoutent l'arme à l'inventaire
    public WeaponStoreDialog(Frame owner, Player player) {
         super(owner, "Magasin d’armes", true); 
        setLayout(new BorderLayout(8, 8));
        setSize(360, 320);
        setLocationRelativeTo(owner);

        // Catalogue avec Supplier
        List<Object[]> catalog = new ArrayList<>();
        catalog.add(new Object[]{"Épée", 30, (Supplier<Weapon>) () -> new Sword()});
        catalog.add(new Object[]{"Arc", 20, (Supplier<Weapon>) () -> new Bow()});
        catalog.add(new Object[]{"Faux", 40, (Supplier<Weapon>) () -> new Scythe()});
        catalog.add(new Object[]{"Potion de vie", 15, (Supplier<Potion>) () -> new Potion()});

        for (Object[] row : catalog) {
            listModel.addElement(row[0] + " — " + row[1] + " or");
        }
        add(new JScrollPane(list), BorderLayout.CENTER);

        JLabel info = new JLabel(player == null
                ? "Exploration: pas d’achat réel (depuis le menu)."
                : "Or: " + player.gold + " — Sélectionnez un item et cliquez Acheter.");
        add(info, BorderLayout.NORTH);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton buyButton = new JButton("Acheter");
        JButton closeButton = new JButton("Fermer");
        actions.add(buyButton);
        actions.add(closeButton);
        add(actions, BorderLayout.SOUTH);

        buyButton.addActionListener(e -> {
            int idx = list.getSelectedIndex();
            if (idx < 0) return;

            String name = catalog.get(idx)[0].toString();
            int price = (int) catalog.get(idx)[1];
            Object supplierObj = catalog.get(idx)[2];

            if (player == null) {
                JOptionPane.showMessageDialog(this, "Pas d’achat depuis ce contexte.");
                return;
            }

            if (player.gold < price) {
                JOptionPane.showMessageDialog(this, "Pas assez d’or pour " + name);
                return;
            }

            player.gold -= price;

            // Création de l’objet via Supplier
            if (supplierObj instanceof Supplier) {
                Object item = ((Supplier<?>) supplierObj).get();

                if (item instanceof Weapon) {
                    Weapon w = (Weapon) item;
                    player.inventory.add(w);
                    player.equippedWeapon = w;
                    JOptionPane.showMessageDialog(this, "Acheté et équipé : " + w.getName());
                } else if (item instanceof Potion) {
                    Potion p = (Potion) item;
                    player.potions.add(p);
                    JOptionPane.showMessageDialog(this, "Potion achetée !");
                }
            }

            info.setText("Or: " + player.gold);
        });

        closeButton.addActionListener(e -> dispose());
    }
}
