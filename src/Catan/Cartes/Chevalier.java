package Catan.Cartes;

import Catan.Carte;
import Catan.Jeu;
import Catan.Joueur;

public class Chevalier extends Carte{

    @Override
    public boolean utiliser(Joueur j,Jeu jeu) {
        jeu.vue.setAction(jeu.vue.actionPrincipale(false));
        jeu.getPlateau().deplaceVoleur(jeu, j);
        j.addChevalier(jeu);
        return true;
    }

    @Override
    public String toString() {
        return "Carte Chevalier";
    }
    
}
