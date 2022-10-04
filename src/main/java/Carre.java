import java.awt.*;

public abstract class Carre extends Sprite {

    protected int largeur;

    public Carre(int x, int y, int largeur, Color couleur) {
        super(x, y, couleur);
        this.largeur = largeur;
    }

    public Carre(int x, int y, int largeur) {
        super(x, y);
        this.largeur = largeur;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }
}
