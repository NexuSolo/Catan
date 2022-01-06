package Catan.Cartes;

import Catan.Carte;
import Catan.Jeu;
import Catan.Joueur;

public class ConstructionRoute extends Carte{

    @Override
    public boolean utiliser(Joueur j,Jeu jeu) {
        j.placerRoute(jeu,true,null, null, false);
        j.placerRoute(jeu,true,null, null, false);
        return true;
    }
    
    @Override
    public String toString() {
        return "Carte ConstructionRoute";
    }
}
