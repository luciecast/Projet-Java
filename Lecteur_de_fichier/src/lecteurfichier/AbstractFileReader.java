package lecteurfichier;

import java.io.*;
import java.util.*;

public abstract class AbstractFileReader implements interfaceLecteurCd {
    protected List<String> lignes = new ArrayList<>();

  @Override
    public void lireFichier(String chemin) {
        lignes.clear();
        try (
            FileInputStream fis = new FileInputStream(chemin);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr)
        ) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                lignes.add(ligne);
            }
        } catch (IOException e) {
            System.out.println("Erreur de lecture : " + e.getMessage());
        }
    }


    @Override
    public void alendroit() {
        for (String ligne : lignes) {
            System.out.println(ligne);
        }
    }

    @Override
    public void alenvers() {
        for (int i = lignes.size() - 1; i >= 0; i--) {
            System.out.println(lignes.get(i));
        }
    }

    @Override
    public void palindrome() {
        for (String ligne : lignes) {
            String rev = "";
            for (int i = ligne.length() - 1; i >= 0; i--) {
                rev += ligne.charAt(i); 
            }
            System.out.println(rev);
        }
    }

 
    @Override
    public boolean comparer(String fichier1, String fichier2) {
        List<String> lignes1 = new ArrayList<>();
        List<String> lignes2 = new ArrayList<>();

        try (
            DataInputStream dis1 = new DataInputStream(new FileInputStream(fichier1));
            DataInputStream dis2 = new DataInputStream(new FileInputStream(fichier2));
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(dis1));
            BufferedReader reader2 = new BufferedReader(new InputStreamReader(dis2))
        ) {
            String ligne;
            while ((ligne = reader1.readLine()) != null) {
                lignes1.add(ligne);
            }
            while ((ligne = reader2.readLine()) != null) {
                lignes2.add(ligne);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture des fichiers : " + e.getMessage());
            return false;
        }

        return lignes1.equals(lignes2);
    }


}
