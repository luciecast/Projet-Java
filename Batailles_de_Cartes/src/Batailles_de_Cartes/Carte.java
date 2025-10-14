package Batailles_de_Cartes;

public class Carte {
    private String couleur;
    private int valeur;
    
    public static final String[] CouleurS = {"Coeur", "Carreau", "Trèfle", "Pique"};
    public static final String[] ValeurS = {"AS", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Valet", "Dame", "Roi"};

    public Carte(String couleur, int valeur) {
        this.couleur = couleur;
        this.valeur = valeur;
    }

    public String getCouleur() {
        return couleur;
    }
    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }


    public int getValeur() {
        return valeur;
    }


    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

    public int comparer(Carte cartes) {
    	
    	if (this.valeur> cartes.valeur) {
    		return 1;
    	} else if (this.valeur< cartes.valeur) {
    		return -1;
    	}else {return 0;
    	
    	}
       
    }

    public String afficherLesCartes() {
        return valeur + " de " + couleur;
    }
}
