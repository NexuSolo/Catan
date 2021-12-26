package Catan;

import java.awt.Color;

public class Chemin {
    private final Intersection intersection1, intersection2;
    private Joueur route = null;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public Chemin(Intersection intersection1, Intersection intersection2) {
        if(intersection1.y < intersection2.y) {
            this.intersection1 = intersection1;
            this.intersection2 = intersection2;
            this.intersection1.setCheminB(this);
            this.intersection2.setCheminH(this);

        }
        else if(intersection1.y > intersection2.y) {
            this.intersection1 = intersection2;
            this.intersection2 = intersection1;
            this.intersection1.setCheminB(this);;
            this.intersection2.setCheminH(this);;
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
            this.intersection1.setCheminD(this);;
            this.intersection2.setCheminG(this);;
        }
    }

    @Override
    public String toString() {
        return "Intersection1 = (" + intersection1.toString() + ") intersection 2 = (" + intersection2.toString() + ")";
    }

    public String toStringH() {
        if(intersection1.getCheminD().route == null) {
            return "-------";
        }
        else if(intersection1.getCheminD().route.couleur.equals(Color.BLUE)) {
            return ANSI_BLUE + "-------" + ANSI_RESET;
        }
        else if(intersection1.getCheminD().route.couleur.equals(Color.RED)) {
            return ANSI_RED + "-------" + ANSI_RESET;
        }
        else if(intersection1.getCheminD().route.couleur.equals(Color.YELLOW)) {
            return ANSI_YELLOW + "-------" + ANSI_RESET;
        }
        else if(intersection1.getCheminD().route.couleur.equals(Color.GREEN)) {
            return ANSI_GREEN + "-------" + ANSI_RESET;
        }
        return null;
    }

    public String toStringV() {
        if(intersection1.getCheminB().route == null) {
            return "|";
        }
        else if(intersection1.getCheminB().route.couleur.equals(Color.BLUE)) {
            return ANSI_BLUE + "|" + ANSI_RESET;
        }
        else if(intersection1.getCheminB().route.couleur.equals(Color.RED)) {
            return ANSI_RED + "|" + ANSI_RESET;
        }
        else if(intersection1.getCheminB().route.couleur.equals(Color.YELLOW)) {
            return ANSI_YELLOW + "|" + ANSI_RESET;
        }
        else if(intersection1.getCheminB().route.couleur.equals(Color.GREEN)) {
            return ANSI_GREEN + "|" + ANSI_RESET;
        }
        return null;
    }

    public Intersection getIntersection1() {
        return intersection1;
    }

    public Intersection getIntersection2() {
        return intersection2;
    }

    public Joueur getRoute() {
        return route;
    }

    public void setRoute(Joueur route) {
        this.route = route;
    }
    
}
