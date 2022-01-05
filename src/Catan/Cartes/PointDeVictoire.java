package Catan.Cartes;

import Catan.Carte;
import Catan.Jeu;
import Catan.Joueur;

public class PointDeVictoire extends Carte{

    @Override
    public boolean utiliser(Joueur j,Jeu jeu) {
        System.out.println("Vous ne pouvez pas utiliser une carte Point de Victoire");
        return false;   
    }

    @Override
    public String toString() {
        return "Carte Point De Victoire";
    }
    
}
