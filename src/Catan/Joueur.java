package Catan;

import java.awt.Color;
import java.util.LinkedList;

public class Joueur {
    Color couleur;
    String pseudo;
    LinkedList<Ressource> ressources;
    LinkedList<Carte> cartes;
    int point;
    LinkedList<Colonie> propriete;
    LinkedList<Port> ports;

    public Joueur(String pseudo, String color) {
        this.pseudo = pseudo;
    }
    
}
