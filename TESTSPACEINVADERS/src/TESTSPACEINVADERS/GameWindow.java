package TESTSPACEINVADERS;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;

public class GameWindow extends JFrame {

    private final GLJPanel glPanel;
    private final AffichageJeu affichage;
    private final KeyInput keyInput;

    public GameWindow(String title) {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);

        GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2));
        glPanel = new GLJPanel(caps);

        affichage = new AffichageJeu();
        glPanel.addGLEventListener(affichage);

        keyInput = new KeyInput();
        glPanel.addKeyListener(keyInput);
        affichage.setKeyInput(keyInput);

        glPanel.setFocusable(true);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(glPanel, BorderLayout.CENTER);

        // Boucle de rendu 60 FPS
        new Timer(16, e -> glPanel.display()).start();

        // Forcer le focus clavier
        glPanel.addHierarchyListener(ev -> {
            if ((ev.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0
                    && glPanel.isShowing()) {
                glPanel.requestFocusInWindow();
            }
        });
    }
}
