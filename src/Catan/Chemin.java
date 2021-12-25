package Catan;

import java.awt.Color;

public class Chemin {
    public Intersection intersection1, intersection2;
    Joueur route = null;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public Chemin(Intersection intersection1, Intersection intersection2) {
        if(intersection1.y < intersection2.y) {
            this.intersection1 = intersection1;
            this.intersection2 = intersection2;
            this.intersection1.cheminB = this;
            this.intersection2.cheminH = this;

        }
        else if(intersection1.y > intersection2.y) {
            this.intersection1 = intersection2;
            this.intersection2 = intersection1;
            this.intersection1.cheminB = this;
            this.intersection2.cheminH = this;
        }
        else {
            if(intersection1.x < intersection2.x) {
                this.intersection1 = intersection1;
                this.intersection2 = intersection2;
            }
            else {
                this.intersection1 = intersection2;
                this.intersection2 = intersection1;
            }
            this.intersection1.cheminD = this;
            this.intersection2.cheminG = this;
        }
    }

    @Override
    public String toString() {
        return "Intersection1 = (" + intersection1.toString() + ") intersection 2 = (" + intersection2.toString() + ")";
    }

    public String toStringH() {
        if(intersection1.cheminD.route == null) {
            return "-------";
        }
        else if(intersection1.cheminD.route.couleur == Color.BLUE) {
            return ANSI_BLUE + "-------" + ANSI_RESET;
        }
        else if(intersection1.cheminD.route.couleur == Color.RED) {
            return ANSI_RED + "-------" + ANSI_RESET;
        }
        else if(intersection1.cheminD.route.couleur == Color.GREEN) {
            return ANSI_GREEN + "-------" + ANSI_RESET;
        }
        else if(intersection1.cheminD.route.couleur == Color.GREEN) {
            return ANSI_GREEN + "-------" + ANSI_RESET;
        }
        return null;
    }

    public String toStringV() {
        if(intersection1.cheminB.route == null) {
            return "|";
        }
        else if(intersection1.cheminB.route.couleur == Color.BLUE) {
            return ANSI_BLUE + "|" + ANSI_RESET;
        }
        else if(intersection1.cheminB.route.couleur == Color.RED) {
            return ANSI_RED + "|" + ANSI_RESET;
        }
        else if(intersection1.cheminB.route.couleur == Color.GREEN) {
            return ANSI_GREEN + "|" + ANSI_RESET;
        }
        else if(intersection1.cheminB.route.couleur == Color.GREEN) {
            return ANSI_GREEN + "|" + ANSI_RESET;
        }
        return null;
    }
    
}
