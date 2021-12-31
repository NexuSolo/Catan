package Catan.Joueurs;

import Catan.Colonie;
import Catan.Intersection;
import Catan.Jeu;
import Catan.Joueur;
import Catan.Plateau;

public class IA extends Joueur{

    public IA(String pseudo, String color) {
        super(pseudo, color);
    }

    @Override
    public boolean placerColonie(Jeu jeu, boolean premierTour) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean placerRoute(Jeu jeu, boolean gratuit, Intersection premierTour) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void defausseVoleur() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deplaceVoleur(Plateau p) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void tour(Jeu jeu) {
        cartesUtilisables();
        
    }

    @Override
    public boolean echange(Jeu jeu) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
