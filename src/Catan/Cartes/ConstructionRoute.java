package Catan.Cartes;

import Catan.Carte;
import Catan.Jeu;
import Catan.Joueur;

public class ConstructionRoute extends Carte{

    @Override
    public boolean utiliser(Joueur j,Jeu jeu) {
        j.placerRoute(jeu.getPlateau(),true,null);
        j.placerRoute(jeu.getPlateau(),true,null);
        return true;
    }
    
    @Override
    public String toString() {
        return "Carte ConstructionRoute";
    }
}
