import java.util.ArrayList;

public class Player {
    String name;
    String caste;
    int hp;
    int mana;
    int force;
    int gold;
    int xp;
    ArrayList<Weapon> inventory = new ArrayList<>();
    Weapon equippedWeapon;
    ArrayList<Potion> potions = new ArrayList<>();
    
    public Player(String name, String caste) {
        this.name = name;
        this.caste = caste;
        this.gold = 50;
        this.xp = 0;

        switch (caste) {
            case "Sorcier": hp = 70; mana = 100; force = 5; break;
            case "Chevalier": hp = 100; mana = 30; force = 10; break;
            case "Elfe": hp = 80; mana = 60; force = 7; break;
        }
    }

    public void showInventory() {
        System.out.println("\n--- Inventaire ---");
        System.out.println("Or : " + gold);
        System.out.println("XP : " + xp);
        System.out.println("PV : " + hp);
        System.out.println("Arme équipée : " + (equippedWeapon != null ? equippedWeapon.getName() : "Aucune"));
        System.out.println("Armes :");
        System.out.println("Objets :");
        for (Weapon w : inventory) {
            System.out.println("- " + w.getName() + " (Dégâts : " + w.getDamage() + ")");
        }
        System.out.println("Potions : " + potions.size());
    }

    public int getTotalDamage() {
        return force + (equippedWeapon != null ? equippedWeapon.getDamage() : 0);
    }

     public void usePotion() {
    if (potions.isEmpty()) {
        System.out.println("Vous n'avez pas de potion !");
        return;
    }

    Potion p = potions.remove(0);
    hp += p.getHealing();
    System.out.println("Vous utilisez une potion !");
    System.out.println("PV restaurés : +" + p.getHealing() + " → PV actuels : " + hp);
}
}
