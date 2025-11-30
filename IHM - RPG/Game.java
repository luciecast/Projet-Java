

import java.util.Scanner;

import javax.swing.JOptionPane;

import java.util.Random;



public class Game {
    Scanner scanner = new Scanner(System.in);
    Player player;
    Map map;
    int positionx = 0, positiony = 0;

    public void intro() {
        System.out.println("\n"+
            " · · · · · · ------------ \\/ ------------ · · · · · ·\n " +
            "     !!  Bienvenue dans le RPG 'Direction X'  !!\n "+
            "· · · · · · ------------ /\\ ------------ · · · · · ·\n " );
        System.out.println();
        System.out.println("Dans les terres reculées d'Aerthas, un royaume nommé Valdoria lutte pour sa survie.");
        System.out.println("Les ombres s'étendent, les monstres se multiplient, et les anciens remparts ne suffisent plus.");
        System.out.println("Le roi Eldrin a lancé un appel à tous les aventuriers du continent.");
        System.out.println();
        System.out.println("Parmi eux, un jeune héros a répondu à l'appel... tiens ? Qui es-tu ?");
        System.out.print("Quel est le nom de ce héros ? ");
        String name = scanner.nextLine();
        System.out.println();
        System.out.println("Bienvenue, " + name + " !\n");
        System.out.println("Pour servir Valdoria, tu dois choisir ta voie :");
        System.out.println("1. Sorcier - Maître des arcanes");
        System.out.println("2. Chevalier - Défenseur du royaume");
        System.out.println("3. Elfe - Gardien des forêts\n");

        String caste = "";
        Weapon starterWeapon = null;

        while (true) {
            System.out.print("Quel est ton choix ? (1/2/3) ");
            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                caste = "Sorcier";
                starterWeapon = new Weapon() {
                    public void display() {
                        System.out.println("Catalyseur magique");
                        System.out.println("Un cristal vibrant d'énergie mystique.");
                    }
                    public int getDamage() { return 5; }
                    public String getName() { return "Catalyseur magique"; }
                };
                System.out.println("\nTu as choisi la voie du Sorcier.");
                System.out.println("Tu reçois un catalyseur magique (5 dégâts) pour canaliser tes sorts.");
                break;
            } else if (choice.equals("2")) {
                caste = "Chevalier";
                starterWeapon = new Weapon() {
                    public void display() {
                        System.out.println("Dague de combat");
                        System.out.println("Une lame courte, rapide et précise.");
                    }
                    public int getDamage() { return 5; }
                    public String getName() { return "Dague de combat"; }
                };
                System.out.println("\nTu as choisi la voie du Chevalier.");
                System.out.println("Tu reçois une dague de combat (5 dégâts) pour défendre le royaume.");
                break;
            } else if (choice.equals("3")) {
                caste = "Elfe";
                starterWeapon = new Weapon() {
                    public void display() {
                        System.out.println("Fronde sylvestre");
                        System.out.println("Une arme légère faite de lianes enchantées.");
                    }
                    public int getDamage() { return 5; }
                    public String getName() { return "Fronde sylvestre"; }
                };
                System.out.println("\nTu as choisi la voie de l'Elfe.");
                System.out.println("Tu reçois une fronde sylvestre (5 dégâts) liée à la magie de la nature.");
                break;
            } else {
                System.out.println("Choix invalide. Réessaie.");
            }
        }

        player = new Player(name, caste);
        player.inventory.add(starterWeapon);
        player.equippedWeapon = starterWeapon;
        System.out.println("\nTon aventure commence maintenant, " + name + " de Valdoria !\n");
    }


    public void start() {   
        intro();
        map = new Map(5);
        while (player.hp > 0) {
            System.out.println("1. Magasin");
            System.out.println("2. Inventaire");
            System.out.println("3. Se déplacer");
            System.out.println("4. Utiliser une potion");

            String choice = scanner.nextLine();

            switch (choice) {   
                case "1": shop(); break;
                case "2": player.showInventory(); break;
                case "3": move(); break;
                case "4": player.usePotion(); break;
                default: System.out.println("Choix invalide.");
            }
        }
        System.out.println("Vous êtes mort dans le donjon...");
    }

    private void shop() {
        System.out.println("\n Magasin ");
        System.out.println("1. Épée (30 or)");
        System.out.println("2. Arc (20 or)");
        System.out.println("3. Faux (40 or)");
        System.out.println("4. Potion de vie (15 or)");
        String choice = scanner.nextLine();
        Weapon item = null;
        int cost = 0;

        switch (choice) {
            case "1": 
                item = new Sword(); 
                cost = 30; 
            break;
            case "2": 
                item = new Bow(); 
                cost = 20; 
            break;
            case "3": 
                item = new Scythe(); 
                cost = 40; 
            break;
            case "4":
                Potion potion = new Potion();
                if (player.gold >= potion.getPrice()) {
                    player.gold -= potion.getPrice();
                    player.potions.add(potion);
                    System.out.println("Vous avez acheté une potion !");
                } else {
                    System.out.println("Pas assez d'or.");
                }
                return;
            default: return;
        }

        if (player.gold >= cost) {
            player.gold -= cost;
            player.inventory.add(item);
            player.equippedWeapon = item;
            System.out.println("Vous avez acheté et équipé : " + item.getName());
            item.display();
        } else {
            System.out.println("Pas assez d'or.");
        }
    }

    private void move() {
        map.display(positionx, positiony);
        System.out.println("Déplacement : w/a/s/d (Haut/Gauche/Bas/Droite)");
        String position = scanner.nextLine().toLowerCase();
        int nouvellePositionx = positionx, nouvellePositiony = positiony;

        switch (position) {
            case "w": 
                nouvellePositionx--; 
            break;
            case "s": 
                nouvellePositionx++; 
                break;
            case "a": 
                nouvellePositiony--; 
                break;
            case "d": 
                nouvellePositiony++; 
                break;
        }

        if (nouvellePositionx < 0 || nouvellePositiony < 0 || nouvellePositionx >= map.size || nouvellePositiony >= map.size) {
            System.out.println("Impossible.");
            return;
        }

        char cell = map.getCell(nouvellePositionx, nouvellePositiony);
        if (cell == 'M') {
            fight();
            map.clearCell(nouvellePositionx, nouvellePositiony);
        } else if (cell == 'O') {
            destroyObstacle();
            map.clearCell(nouvellePositionx, nouvellePositiony);
        } else if (cell == 'S') {
            System.out.println("Vous avez trouvé la sortie !");
            System.exit(0);
        }

        positionx = nouvellePositionx;
        positiony = nouvellePositiony;
    }

    private void fight() {
        Random rand = new Random();
        int monsterHp = rand.nextInt(30) + 20;
        System.out.println("Un monstre apparaît ! PV : " + monsterHp);

        while (monsterHp > 0 && player.hp > 0) {
            System.out.println("1. Attaquer  2. Fuir");
            String action = scanner.nextLine();
            if (action.equals("2")) return;

            int damage = player.getTotalDamage() + rand.nextInt(5);
            monsterHp -= damage;
            System.out.println("Vous infligez " + damage + " dégâts.");

            if (monsterHp > 0) {
                int hit = rand.nextInt(15);
                player.hp -= hit;
                System.out.println("Le monstre riposte ! -" + hit + " PV");
            }
        }

        if (player.hp > 0) {
            int xp = rand.nextInt(20) + 10;
            player.xp += xp;
            System.out.println("Monstre vaincu ! XP + " + xp);
        }
    }    

    private void destroyObstacle() {
        System.out.println("Un obstacle bloque le chemin.");
        System.out.println("1. Détruire  2. Contourner");
        String choice = scanner.nextLine();
        if (choice.equals("1")) {
            if (new Random().nextDouble() < 0.7) {
                System.out.println("Obstacle détruit !");
                player.xp += 5;
            } else {
                System.out.println("Échec ! -10 PV");
                player.hp -= 10;
            }
        } else {
            System.out.println("Vous contournez l'obstacle.");
        }
    }












    public void moveKey(String direction) {
    int nouvellePositionx = positionx;
    int nouvellePositiony = positiony;

    switch(direction) {
        case "w": nouvellePositionx--; break;
        case "s": nouvellePositionx++; break;
        case "a": nouvellePositiony--; break;
        case "d": nouvellePositiony++; break;
    }

    if (nouvellePositionx < 0 || nouvellePositiony < 0 || nouvellePositionx >= map.size || nouvellePositiony >= map.size) return;

    char cell = map.getCell(nouvellePositionx, nouvellePositiony);
    if (cell == 'M') { JOptionPane.showMessageDialog(null, "Combat !"); map.clearCell(nouvellePositionx, nouvellePositiony); }
    else if (cell == 'O') { map.clearCell(nouvellePositionx, nouvellePositiony); }
    else if (cell == 'S') { JOptionPane.showMessageDialog(null, "Vous avez gagné !"); System.exit(0); }

    positionx = nouvellePositionx;
    positiony = nouvellePositiony;
}


}
