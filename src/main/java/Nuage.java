import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Nuage extends Rectangle implements Deplacable {

    protected BufferedImage image;
    protected int vitesse = (int) (Math.random() * 5) + 1;

    public Nuage(int largeurEcran, int hauteurEcran) {
        super(0, 0, 0, 0, new Color(0, 0, 0, 0.05f));

        double ratio = Math.random();

        largeur = (int) (45 + 150 * ratio);
        hauteur = (int) (25 + 100 * ratio);

        reinitialiser(largeurEcran, hauteurEcran);

        try {

            BufferedImage imageOriginal;

            double hasard = Math.random();

            if(hasard > 0.66f) {
                imageOriginal = ImageIO.read(new File("src/main/resources/nuage.png"));
            } else if (hasard> 0.33f){
                imageOriginal = ImageIO.read(new File("src/main/resources/nuage2.png"));
            } else {
                imageOriginal = ImageIO.read(new File("src/main/resources/nuage3.png"));
            }


            image = Utils.resizeImage(imageOriginal, largeur, hauteur);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void dessiner(Graphics2D dessin) {
        dessin.setColor(couleur);
//        dessin.fillRect(x, y, largeur, hauteur);
        dessin.drawImage(image, x, y, null);
    }

    @Override
    public void deplacer(int largeurEcran, int hauteurEcran) {
        x -= vitesse;
        //x = x -1;
        //x -= 1;

        if (x < -largeur) {
            x = largeurEcran;
        }
    }

    @Override
    public void reinitialiser(int largeurEcran, int hauteurEcran) {
        x = (int) (Math.random() * largeurEcran);
        y = (int) (Math.random() * (hauteurEcran / 2));
    }
}
