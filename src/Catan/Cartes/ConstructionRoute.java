package Catan.Cartes;

import Catan.Carte;
import Catan.Jeu;
import Catan.Joueur;

public class ConstructionRoute extends Carte{

    @Override
    public boolean utiliser(Joueur j,Jeu jeu) {
        j.placerRoute(jeu,true,null);
        j.placerRoute(jeu,true,null);
        return true;
    }
    
    @Override
    public String toString() {
        return "Carte ConstructionRoute";
    }
}
