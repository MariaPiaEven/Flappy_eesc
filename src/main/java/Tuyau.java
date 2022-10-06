import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tuyau extends Rectangle implements Deplacable {

    protected BufferedImage image;
    protected int ecart;
    protected int ordreApparition;

    public Tuyau(int hauteur, int hauteurEcran, int largeurEcran, int ecart, int ordreApparition) {
        super(100);
        this.ecart = ecart;
        this.ordreApparition = ordreApparition;
       reinitialiser( largeurEcran, hauteurEcran);



        try {

            BufferedImage imageOriginal = ImageIO.read(new File("src/main/resources/tuyau-haut-bout.png"));

//            BufferedImage imageOriginal1 = ImageIO.read(new File("src/main/resources/tuyau-bas-bout.png"));

            //Pour redimensioner
            image = Utils.resizeImage(imageOriginal, 100, 100);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void dessiner(Graphics2D dessin) {
        dessin.setColor(couleur);
       dessin.fillRect(x, y, largeur, hauteur);
        dessin.drawImage(image, x, y, null);
    }

    @Override
    public void deplacer(int largeurEcran, int hauteurEcran) {

        x-= 5;

        if (x < -largeur){
            reinitialiser(largeurEcran,hauteurEcran);
        }

    }

    public void reinitialiser(int largeurEcran, int hauteurEcran) {

        x = largeurEcran + ecart * ordreApparition;

        hauteur = (int) (Math.random() * 300);
        y = hauteurEcran - hauteur;
    }
}
