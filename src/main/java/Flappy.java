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

    protected Oiseau oiseau;

//    protected Tuyau tuyau;

    protected ArrayList<Tuyau> listeTuyau = new ArrayList<>();

    protected ArrayList<Deplacable> listeDeplacable = new ArrayList<>();
    protected ArrayList<Sprite> listeSprite = new ArrayList<>();

    public Flappy() throws InterruptedException {

        try {
            image = ImageIO.read(new File("src/main/resources/fond.png"));

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

        //si c'est la première initialisation
        if (oiseau == null) {

//            tuyau = new Tuyau(200, hauteurEcran, largeurEcran);
//            Nuage nuage = new Nuage(largeurEcran, hauteurEcran);

//            listeDeplacable.add(tuyau);
//            listeDeplacable.add(nuage);

//            listeSprite.add(tuyau);
//            listeSprite.add(nuage);

            //ajout nuages
            for (int i = 0; i < 3; i++) {
                Nuage nuage = new Nuage(largeurEcran, hauteurEcran);
                listeDeplacable.add(nuage);
                listeSprite.add(nuage);
            }

            for (int i = 0; i < 3; i++) {
                Tuyau tuyau = new Tuyau(100, largeurEcran, hauteurEcran);
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

        initialiser();

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

            if (!pause) {

                //-------Si jamais l'oiseau est tombé au terrre-----------
                if (oiseau.getY() > hauteurEcran - oiseau.getLargeur()) {
                    System.out.println("perdu");
                    pause = true;
                } else {
                    //------sinon si le jeu continu-------
//                    oiseau.deplacer();
//                    tuyau.deplacer();

                    for (Deplacable deplacable : listeDeplacable) {
                        deplacable.deplacer(largeurEcran, hauteurEcran);
                    }

                    for (Tuyau tuyau : listeTuyau) {
                        if (Sprite.testCollision(oiseau, tuyau)) {
                            System.out.println("perdu");
                            pause = true;
                        }

                    }

                }
            } else {
                dessin.setColor(new Color(0, 0, 0, 0.1f));
                dessin.fillRect(0, 0, largeurEcran, hauteurEcran);
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
