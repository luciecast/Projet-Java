package lecteurfichier;

public class FileReader {
    public static void main(String[] args) {

        interfaceLecteurCd lecteur = new TxtFileReader();

        lecteur.lireFichier("texte1");
        System.out.println("\nA l'endroit :");
        lecteur.alendroit();

        System.out.println("\nA l'envers :");
        lecteur.alenvers();

        System.out.println("\nPalindrome :");
        lecteur.palindrome();

        System.out.println("\nComparaison :");
        boolean identiques = lecteur.comparer("texte1", "texte2");
        System.out.println("Les fichiers sont identiques ? " + identiques);
        
    }
}
