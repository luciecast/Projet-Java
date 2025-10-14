package Batailles_de_Cartes;
import java.util.ArrayList;
import java.util.Collections;

public class Bataille {
    public static void main(String[] args) {
    	
    	ArrayList<Carte> paquetdeCarte = new ArrayList<>();
        Joueur joueur1 = new Joueur();
        Joueur joueur2 = new Joueur();
        
        for (String couleur : Carte.CouleurS) {
            for (int i = 0; i <Carte.ValeurS.length; i++) {
            	paquetdeCarte.add(new Carte(couleur, i));
            }
        }
        
        Collections.shuffle(paquetdeCarte);
        
        for (int i = 0; i < paquetdeCarte.size(); i++) {
            if (i % 2 == 0) {
                joueur1.ajouterCarte(paquetdeCarte.get(i));
            } else {
                joueur2.ajouterCarte(paquetdeCarte.get(i));
            }
        }

        for (int i = 0; i < 26; i++) {
            Carte c1 = joueur1.tirerCarte();
            Carte c2 = joueur2.tirerCarte();

            System.out.println("Joueur 1 a joue " + c1.afficherLesCartes());
            System.out.println("Joueur 2 a joue " + c2.afficherLesCartes());

            if( c1.comparer(c2)>0) {
            	joueur1.ajouterPoint();
            	System.out.println("joueur 1 a gagne");
            } else if( c1.comparer(c2)<0) {
            	
                	joueur2.ajouterPoint();
                	System.out.println("joueur 2 a gagne");
            }else if( c1.comparer(c2)==0) {
                    System.out.println("egalite");
            }         

            System.out.println("Joueur 1 : " + joueur1.getPoints() + " et Joueur 2 : " + joueur2.getPoints());
        }

        if (joueur1.getPoints() > joueur2.getPoints()) {
            System.out.println("joueur 1 a gagne");
        } else if (joueur2.getPoints() > joueur1.getPoints()) {
            System.out.println("joueur 2 a gagne");
        } else {
            System.out.println("egalite");
        }
    }
}
