import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tuyau extends Rectangle implements Deplacable {

    protected BufferedImage image;

    public Tuyau(int hauteur, int hauteurEcran, int largeurEcran) {
        super(largeurEcran - 100, hauteurEcran - hauteur, 100, hauteur);
        x -= (int)(Math.random() * largeurEcran);
        y -= (int)(Math.random() * largeurEcran);

        try {
            BufferedImage imageOriginal = ImageIO.read(new File("src/main/resources/tuyau.png"));
            image = Utils.resizeImage(imageOriginal,100,100);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void dessiner(Graphics2D dessin) {
        dessin.setColor(couleur);
//        dessin.fillRect(x, y, largeur, hauteur);
        dessin.drawImage(image, x, y,null);
    }

    @Override
    public void deplacer(int largeurEcran, int hauteurEcran) {
        x -= 2;
    }

    public void reinitialiser(int largeurEcran, int hauteurEcran) {
        x = (int) (Math.random() * largeurEcran);
        y = (int) (Math.random() * (hauteurEcran / 2));
    }
}
