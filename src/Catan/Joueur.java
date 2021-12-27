package Catan;

import java.awt.Color;
import java.util.LinkedList;

public abstract class Joueur {
    public final Color couleur;
    public final String pseudo;
    private LinkedList<Ressource> ressources = new LinkedList<Ressource>();
    private LinkedList<Carte> cartes;
    private int point;
    public int nombreColonies = 0;
    public int nombreVilles = 0;
    private LinkedList<Port> ports;

    public Joueur(String pseudo, String couleur) {
        this.pseudo = pseudo;
        this.couleur = stringToColor(couleur);
    }

    public static Color stringToColor(String s) {
        if(s.equals("bleu")) {
            return new Color(0,0,255);
        }
        else if(s.equals("rouge")) {
            return new Color(255,0,0);
        }
        else if(s.equals("vert")) {
            return new Color(0,255,0);
        }
        else if(s.equals("jaune")) {
            return new Color(255,255,0);
        }
        return null;
    }

    public boolean possede(Ressource ressource,int nombre){
        for(Ressource r : ressources){
            if (r == ressource){
                nombre--;
                if (nombre < 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean possede(Ressource ressource){
        return possede(ressource, 1);
    }

    public void addRessource(Ressource ressource,int nombre) {
        while(nombre != 0) {
           this.ressources.add(ressource);
           nombre--; 
        }
    }

    public void removeRessource(Ressource ressource,int nombre) {
        if (possede(ressource,nombre)) {
            while(nombre != 0) {
                this.ressources.remove(ressource);
                nombre--; 
             }
        }
        else {
            System.out.println("Erreur : Pas Assez de Ressources !");
        }
    }

    public abstract boolean placerColonie(Plateau plateau,boolean premierTour, boolean gratuit);

    public void afficheRessource() {
        for (Ressource r : ressources) {
            System.out.print(r + " ");
        }
    }

    public LinkedList<Ressource> getRessources() {
        return ressources;
    }
    
    public String getPseudo() {
        return pseudo;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return this.pseudo + " ("+this.couleur+")";
    }
}
