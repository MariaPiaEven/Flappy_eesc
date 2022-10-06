import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Flappy extends Canvas implements KeyListener {

    protected BufferedImage image;
    protected int largeurEcran = 600;
    protected int hauteurEcran = 600;

    protected boolean pause = false;

    protected boolean perdu = false;

    protected Oiseau oiseau;

//    protected Tuyau tuyau;

    protected ArrayList<Tuyau> listeTuyau = new ArrayList<>();

    protected ArrayList<Deplacable> listeDeplacable = new ArrayList<>();
    protected ArrayList<Sprite> listeSprite = new ArrayList<>();

    protected int nombreTuyau = 5;

    protected int ecartTuyau = (largeurEcran + 100) / nombreTuyau;

    public Flappy() throws InterruptedException {

        try {
            image = ImageIO.read(new File("src/main/resources/fond.jpg"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JFrame fenetre = new JFrame("Flappy");
        //On récupère le panneau de la fenetre principale
        JPanel panneau = (JPanel) fenetre.getContentPane();
        //On définie la hauteur / largeur de l'écran
        panneau.setPreferredSize(new Dimension(largeurEcran, hauteurEcran));
        setBounds(0, 0, largeurEcran, hauteurEcran);
        //On ajoute cette classe (qui hérite de Canvas) comme composant du panneau principal
        panneau.add(this);

        fenetre.pack();
        fenetre.setResizable(false);
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
        fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fenetre.requestFocus();
        fenetre.addKeyListener(this);

        //On indique que le raffraichissement de l'ecran doit être fait manuellement.
        createBufferStrategy(2);
        setIgnoreRepaint(true);
        this.setFocusable(false);

        demarrer();
    }

    public void initialiser() {

        pause = false;
        perdu = false;

        //si c'est la première initialisation
        if (oiseau == null) {

            for (int i = 0; i < 10; i++) {
                Nuage nuage = new Nuage(largeurEcran, hauteurEcran);
                listeDeplacable.add(nuage);
                listeSprite.add(nuage);
            }

            for (int i = 0; i < nombreTuyau; i++) {
                Tuyau tuyau = new Tuyau(300, largeurEcran, hauteurEcran, ecartTuyau, i);
                listeDeplacable.add(tuyau);
                listeSprite.add(tuyau);
                listeTuyau.add(tuyau);
            }

            oiseau = new Oiseau(hauteurEcran);
            listeDeplacable.add(oiseau);
            listeSprite.add(oiseau);

        } else {
            for (Deplacable deplacable : listeDeplacable) {
                deplacable.reinitialiser(largeurEcran, hauteurEcran);
            }

        }
    }

    public void demarrer() throws InterruptedException {

        long indexFrame = 0;

        long point = 0;

        initialiser();

        Font police = new Font("Calibri", Font.BOLD, 24);

        while (true) {

            indexFrame++;
            Graphics2D dessin = (Graphics2D) getBufferStrategy().getDrawGraphics();

            //-----------------------------
            //reset dessin
            dessin.setColor(Color.white);
//            dessin.fillRect(0, 0, largeurEcran, hauteurEcran);
            dessin.drawImage(image, 0, 0, null);

            for (Sprite sprite : listeSprite) {
                sprite.dessiner(dessin);
            }

            //affichage HUD
            dessin.setColor(Color.white);
            dessin.setFont(police);
            dessin.drawString(
                    String.valueOf(point),
                    largeurEcran - 100,
                    50);

            if (!perdu) {

                if (!pause) {

                    //-------Si jamais l'oiseau est tombé au terrre-----------
                    if (oiseau.getY() > hauteurEcran - oiseau.getLargeur()) {
                        perdu = true;
                    } else {
                        point++;

                        for (Deplacable deplacable : listeDeplacable) {
                            deplacable.deplacer(largeurEcran, hauteurEcran);
                        }

                        for (Tuyau tuyau : listeTuyau) {
                            if (Sprite.testCollision(oiseau, tuyau)) {
                                System.out.println("perdu");
                                perdu = true;
                            }

                        }

                    }
                } else {
                    dessin.setColor(new Color(0, 0, 0, 0.1f));
                    dessin.fillRect(0, 0, largeurEcran, hauteurEcran);
                    dessin.setFont(police);
                    dessin.setColor(Color.white);

                    int largeurText = dessin.getFontMetrics().stringWidth("Jeu en pause");

                    dessin.drawString(
                            "Jeu en pause",
                            largeurEcran / 2 - largeurText / 2,
                            hauteurEcran / 2);
                }

            } else {
                dessin.setColor((new Color(0.8f, 0, 0, 0.3f)));
                dessin.fillRect(0, 0, largeurEcran, hauteurEcran);
                dessin.setColor(Color.white);
                dessin.setFont(police);
                int largeurText = dessin.getFontMetrics().stringWidth("Vous avez perdu");

                dessin.drawString(
                        "Vous avez perdu",
                        largeurEcran / 2 - largeurText / 2,
                        hauteurEcran / 2);
            }

            //-----------------------------
            dessin.dispose();
            getBufferStrategy().show();
            Thread.sleep(1000 / 60);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Flappy();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            oiseau.sauter();
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            initialiser();
        }

        if (e.getKeyCode() == KeyEvent.VK_P) {

            //inverser un boolean
            pause = !pause;
        }
    }
}
