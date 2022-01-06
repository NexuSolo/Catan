package Catan;
public class Colonie {
    public final Joueur joueur;

    public Colonie(Joueur joueur) {
        this.joueur = joueur;
        joueur.nombreColonies++;
        joueur.ajouterPoint();
    }
    
    public Joueur getJoueur() {
        return joueur;
    }
}
