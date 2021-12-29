package Catan.Cartes;

import Catan.Carte;
import Catan.Jeu;
import Catan.Joueur;
import Catan.Ressource;

public class Invention extends Carte{

    @Override
    public boolean utiliser(Joueur j,Jeu jeu) {
        Ressource obtenue = null;
        while(obtenue == null){
            System.out.println("Veuillez choisir quelle matière première vous voulez obtenir");
            String reponse = Jeu.scan();
            obtenue = j.stringToRessource(reponse);
            if(obtenue == null){
                System.out.println("Ressource inexistante");
            }
        }
        j.addRessource(obtenue);
        obtenue = null;
        while(obtenue == null){
            System.out.println("Veuillez choisir  une secondes fois quelle matière première vous voulez obtenir");
            String reponse = Jeu.scan();
            obtenue = j.stringToRessource(reponse);
            if(obtenue == null){
                System.out.println("Ressource inexistante");
            }
        }
            if (obtenue != null) {
            System.out.println("Vous avez obtenu un(e) " + obtenue);
            j.addRessource(obtenue);
        }
        return true;
        
    }

    @Override
    public String toString() {
        return "Carte Invention";
    }
    
}
