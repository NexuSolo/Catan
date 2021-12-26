package Catan;

import java.awt.Color;
import java.util.LinkedList;

public abstract class Joueur {
    public final Color couleur;
    public final String pseudo;
    private LinkedList<Ressource> ressources;
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

    public String toString() {
        return (pseudo + " " + couleur);
    }

    public abstract boolean placerColonie(Plateau plateau,boolean premierTour, boolean gratuit);

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
    
}
