package Batailles_de_Cartes;
import java.util.ArrayList;

public class Joueur {
    private ArrayList<Carte> cartes;
    private int gagnePoint;

    public Joueur() {
        cartes = new ArrayList<>();
        gagnePoint = 0;
    }

    public void ajouterCarte(Carte carte) {
        cartes.add(carte);
    }

    public Carte tirerCarte() {
        if (!cartes.isEmpty()) {
            return cartes.remove(0);
        }
        return null;
    }

    public void ajouterPoint() {
    	gagnePoint++;
    }

    public int getPoints() {
        return gagnePoint;
    }
}
