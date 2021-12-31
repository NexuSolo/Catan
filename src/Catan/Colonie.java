package Catan;
public class Colonie {
    public final Joueur joueur;

    public Colonie(Joueur joueur) {
        this.joueur = joueur;
        joueur.setPoint(joueur.getPoint() + 1);
        joueur.nombreColonies++;
    }
    
    public Joueur getJoueur() {
        return joueur;
    }
}
