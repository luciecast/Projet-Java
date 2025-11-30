import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IntroPanel extends JPanel {

    // texte pournkle debut de lhistoire
    private String[] lignesHistoire = {
        "Dans les terres reculées d'Aerthas, un royaume nommé Valdoria lutte pour sa survie.",
        "Les ombres s'étendent, les monstres se multiplient, et les anciens remparts ne suffisent plus.",
        "Le roi Eldrin a lancé un appel à tous les aventuriers du continent.",
        "Parmi eux, un jeune héros a répondu à l'appel... tiens ? Qui es-tu ?"
    };

    private int ligneActuelle = 0;
    private String texteAffiche = "";
    private Timer minuteurTexte;
    private int indiceCaractere = 0;

    private Image imageFond, imageHero;
    private JTextField champNom;
    private JComboBox<String> listeClasse;
    private JButton boutonCommencer;
    private boolean saisieVisible = false;

    public IntroPanel() {
        setLayout(null);
        setBackground(Color.BLACK);

        try {
            imageFond = new ImageIcon("images/fond.jpg").getImage();
            imageHero = new ImageIcon("images/player.png").getImage();
        } catch (Exception e) { e.printStackTrace(); }

        demarrerMinuteurLigne();

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (!saisieVisible) {
                    if (indiceCaractere >= lignesHistoire[ligneActuelle].length()) {
                        if (ligneActuelle < lignesHistoire.length - 1) {
                            ligneActuelle++;
                            texteAffiche = "";
                            indiceCaractere = 0;
                            demarrerMinuteurLigne();
                        } else {
                            saisieVisible = true;
                            repaint();
                        }
                    } else {
                        minuteurTexte.stop();
                        texteAffiche = lignesHistoire[ligneActuelle];
                        indiceCaractere = lignesHistoire[ligneActuelle].length();
                        repaint();
                    }
                }
            }
        });
    }

    private void demarrerMinuteurLigne() {
        minuteurTexte = new Timer(50, e -> {
            String ligne = lignesHistoire[ligneActuelle];
            if (indiceCaractere < ligne.length()) {
                texteAffiche += ligne.charAt(indiceCaractere);
                indiceCaractere++;
                repaint();
            } else {
                minuteurTexte.stop();
                repaint();
            }
        });
        minuteurTexte.start();
    }

    private void demarrerJeu() {
        String nom = champNom.getText().trim();
        if (nom.isEmpty()) nom = "Héros";
        String classe = (String) listeClasse.getSelectedItem();

        Player joueur = new Player(nom, classe);
        JFrame fenetre = (JFrame) SwingUtilities.getWindowAncestor(this);
        fenetre.setContentPane(new MissionPanel(joueur));
        fenetre.revalidate();
    }

    private void styliserComposant(JComponent comp) {
        comp.setFont(new Font("Serif", Font.BOLD, 16));
        comp.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2)); 
    }


    /*c'est unemethode pour le visuel fait par l'ia et des videos que jai regarder
     pour rendre l'interface plus plaisante et immersive. */
     
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int largeur = getWidth();
        int hauteur = getHeight();

        if (imageFond != null) g.drawImage(imageFond, 0, 0, largeur, hauteur, this);
        if (imageHero != null) g.drawImage(imageHero, 50, hauteur - 200, 100, 100, this);

        int hauteurBoite = 180;
        int marge = 20;
        int boiteX = marge;
        int boiteY = hauteur - hauteurBoite - marge;
        int largeurBoite = largeur - 2 * marge;

        g.setColor(new Color(0, 0, 0, 180));
        g.fillRoundRect(boiteX, boiteY, largeurBoite, hauteurBoite, 20, 20);
        g.setColor(Color.WHITE);
        g.drawRoundRect(boiteX, boiteY, largeurBoite, hauteurBoite, 20, 20);
        g.setFont(new Font("Serif", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        FontMetrics fm = g.getFontMetrics();
        int hauteurLigne = fm.getHeight();
        int x = boiteX + 20;
        int y = boiteY + 40;

        String[] mots = texteAffiche.split(" ");
        String ligne = "";
        for (String mot : mots) {
            String testLigne = ligne.isEmpty() ? mot : ligne + " " + mot;
            int largeurTest = fm.stringWidth(testLigne);
            if (largeurTest > largeurBoite - 40) {
                g.drawString(ligne, x, y);
                ligne = mot;
                y += hauteurLigne;
            } else {
                ligne = testLigne;
            }
        }
        if (!ligne.isEmpty()) g.drawString(ligne, x, y);
        if (saisieVisible) {
            int largeurChamp = 200;
            int hauteurChamp = 30;

            int nomX = (largeur - largeurChamp) / 2;
            int nomY = (hauteur - (hauteurChamp * 3 + 20)) / 2 - 40;

            int classeX = nomX;
            int classeY = nomY + hauteurChamp + 10;

            int boutonX = nomX;
            int boutonY = classeY + hauteurChamp + 10;

            if (champNom == null) {
                champNom = new JTextField();
                champNom.setBounds(nomX, nomY, largeurChamp, hauteurChamp);
                styliserComposant(champNom);
                add(champNom);
    
                JLabel etiquetteClasse = new JLabel("Quel est votre catégorie ?");
                etiquetteClasse.setBounds(classeX, classeY, largeurChamp, hauteurChamp);
                etiquetteClasse.setForeground(Color.WHITE);
                etiquetteClasse.setFont(new Font("Serif", Font.BOLD, 16));
                etiquetteClasse.setHorizontalAlignment(SwingConstants.CENTER);
                add(etiquetteClasse);

                // ComboBox juste en dessous du label
                listeClasse = new JComboBox<>(new String[]{"Sorcier", "Chevalier", "Elfe"});
                listeClasse.setBounds(classeX, classeY + hauteurChamp + 5, largeurChamp, hauteurChamp);
                styliserComposant(listeClasse);
                add(listeClasse);

                // Bouton
                boutonCommencer = new JButton("Commencer l'aventure");
                boutonCommencer.setBounds(boutonX, boutonY + hauteurChamp + 15, largeurChamp, hauteurChamp);
                styliserComposant(boutonCommencer);
                boutonCommencer.addActionListener(e -> demarrerJeu());
                add(boutonCommencer);
            }
        }
        if (!saisieVisible && indiceCaractere >= lignesHistoire[ligneActuelle].length() && ligneActuelle < lignesHistoire.length - 1) {
            g.setFont(new Font("Serif", Font.PLAIN, 14));
            g.drawString("Cliquer pour continuer...", boiteX + largeurBoite - 180, boiteY + hauteurBoite - 20);
        }
    }

    public static void main(String[] args) {
        JFrame fenetre = new JFrame("Intro RPG");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(800, 600);
        fenetre.add(new IntroPanel());
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
    }
}
