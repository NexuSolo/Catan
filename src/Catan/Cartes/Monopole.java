package Catan.Cartes;

import Catan.Carte;
import Catan.Jeu;
import Catan.Joueur;
import Catan.Ressource;

public class Monopole extends Carte{

    @Override
    public boolean utiliser(Joueur j,Jeu jeu) {
        Ressource monopole = null;
        while(monopole == null){
            System.out.println("Veuillez choisir quelle matière première vous voulez monopoliser");
            String reponse = Jeu.scan();
            monopole = j.stringToRessource(reponse);
            if(monopole == null){
                System.out.println("Ressource inexistante");
            }
        }
        int ressVolees = 0;
        for(Joueur joueur : jeu.getJoueurs()){
            if (joueur != j) {
                while(joueur.possede(monopole)) {
                    joueur.removeRessource(monopole);
                    j.addRessource(monopole);
                    ressVolees++;
                }
            }
        }
        System.out.println("Vous avez volé "+ressVolees+" ressources, quel enculé.");
        return true;
        
    }

    @Override
    public String toString() {
        return "Carte Monopole";
    }
    
}
