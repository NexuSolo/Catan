package Catan;

public abstract class Carte {
    boolean utilisable = false;
    
    public void setUtilisable(boolean utilisable) {
        this.utilisable = utilisable;
    }

    public abstract boolean utiliser(Joueur j,Jeu jeu);
}
