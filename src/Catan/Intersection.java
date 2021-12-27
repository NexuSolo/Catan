package Catan;

import java.awt.Color;

public class Intersection {
    public final int x, y;
    private Colonie colonie = null;
    public Port port = null;
    private Chemin cheminH, cheminB, cheminG, cheminD;

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
        /**
         * Regarder la prochaine intersection,
         *   --P--                         2 tirets P 2 tirets
         *  /     \                 /  
         * ?-------?-------?-------?
         * 
         * 
         */
        // if (port != null) {
        //     return "P";
        // }
        if(colonie == null) {
            return "?";
        }
        else if(colonie.joueur.couleur.equals(Color.BLUE)) {
            return ANSI_BLUE + "●" + ANSI_RESET;
        }
        else if(colonie.joueur.couleur.equals(Color.RED)) {
            return ANSI_RED + "●" + ANSI_RESET;
        }
        else if(colonie.joueur.couleur.equals(Color.YELLOW)) {
            return ANSI_YELLOW + "●" + ANSI_RESET;
        }
        else if(colonie.joueur.couleur.equals(Color.GREEN)) {
            return ANSI_GREEN + "●" + ANSI_RESET;
        }
        return "ERREUR INTERSECTION"; // code qui ne peut s'executer
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Chemin getCheminH() {
        return cheminH;
    }

    public Chemin getCheminG() {
        return cheminG;
    }

    public Chemin getCheminB() {
        return cheminB;
    }

    public Chemin getCheminD() {
        return cheminD;
    }

    public Colonie getColonie() {
        return colonie;
    }

    public Port getPort() {
        return port;
    }

    public void setCheminH(Chemin cheminH) {
        this.cheminH = cheminH;
    }

    public void setCheminG(Chemin cheminG) {
        this.cheminG = cheminG;
    }

    public void setCheminB(Chemin cheminB) {
        this.cheminB = cheminB;
    }

    public void setCheminD(Chemin cheminD) {
        this.cheminD = cheminD;
    }

    public void setColonie(Colonie colonie) {
        this.colonie = colonie;
    }

    public void setPort(Port port) {
        this.port = port;
    }


}
