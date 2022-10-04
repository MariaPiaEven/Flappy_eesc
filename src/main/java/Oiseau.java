import java.awt.*;

public class Oiseau extends Carre implements Deplacable {
    protected float vitesseVertical;

    protected final static int HAUTEUR_OISEAU = 40;

    public Oiseau(int x, int y) {
        super(x, y, HAUTEUR_OISEAU);
        this.vitesseVertical = 0;
    }

    public Oiseau(int hauteurEcran) {
        super(50, 0, HAUTEUR_OISEAU);
        reinitialiser(hauteurEcran);
        this.vitesseVertical = 0;
    }

    public void reinitialiser(int hauteurEcran) {
        y = hauteurEcran / 2 - HAUTEUR_OISEAU / 2;
        vitesseVertical = 0;
    }

    @Override
    public void dessiner(Graphics2D dessin) {
        dessin.setColor(couleur);
        dessin.fillRect(x, y, largeur, largeur);
    }

    @Override
    public void deplacer() {

        //Petite correction de la gravité, pour eviter un temps de flotement
        //Si la vitesse est comprise entre -0,1 et -0,9
        if (vitesseVertical % 10 != 0 && vitesseVertical < 0) {
            y -= vitesseVertical - 0.05f;
        } else {
            y -= vitesseVertical;

        }
        vitesseVertical -= 0.05f;

        if (y < 0) {
            vitesseVertical *= Math.abs(vitesseVertical) * -1;
        }
    }

    public void sauter() {

    }

    public float getVitesseVertical() {
        return vitesseVertical;
    }

    public void setVitesseVertical(float vitesseVertical) {
        this.vitesseVertical = vitesseVertical;
    }
}
