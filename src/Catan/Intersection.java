package Catan;

import java.awt.Color;

public class Intersection {
    int x, y;
    Colonie colonie = null;
    Port port = null;
    Chemin cheminH, cheminB, cheminG, cheminD;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    
    public Intersection(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void afficheChemin() {
        if(this.cheminH != null) {
            System.out.println("h");
        }
        if(this.cheminB != null) {
            System.out.println("b");
        }
        if(this.cheminG != null) {
            System.out.println("g");
        }
        if(this.cheminD != null) {
            System.out.println("d");
        }
    }

    public String toString() {
        if(colonie == null) {
            return "?";
        }
        else if(colonie.joueur.couleur == Color.BLUE) {
            return ANSI_BLUE + "●" + ANSI_RESET;
        }
        else if(colonie.joueur.couleur == Color.RED) {
            return ANSI_RED + "●" + ANSI_RESET;
        }
        else if(colonie.joueur.couleur == Color.YELLOW) {
            return ANSI_YELLOW + "●" + ANSI_RESET;
        }
        else if(colonie.joueur.couleur == Color.GREEN) {
            return ANSI_GREEN + "●" + ANSI_RESET;
        }
        return null; // code qui ne peut s'executer
    }

}
