package Catan.Joueurs;

import Catan.Colonie;
import Catan.Intersection;
import Catan.Joueur;
import Catan.Plateau;

public class IA extends Joueur{

    public IA(String pseudo, String color) {
        super(pseudo, color);
    }

    @Override
    public boolean placerColonie(Plateau plateau, boolean premierTour) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean placerRoute(Plateau plateau, boolean gratuit, Intersection premierTour) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
