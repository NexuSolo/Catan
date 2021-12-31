package Catan;

import java.awt.Color;
import java.util.LinkedList;

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

    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    
    public Intersection(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int tailleRouteMax(Joueur j,LinkedList<Chemin> cheminParcourus,LinkedList<Intersection> parc) {
        int[] valeurs ={0,0,0,0};
        if (parc.contains(this)){
            return 0;
        }
        else {
            parc.add(this);
        }
        if (colonie == null || colonie.joueur == j) {
            if (cheminH != null && !cheminParcourus.contains(cheminH)) {
                valeurs[0] = cheminH.tailleRouteMax(j, cheminParcourus, parc);
            }
            if (cheminG != null && !cheminParcourus.contains(cheminG)) {
                valeurs[1] = cheminG.tailleRouteMax(j, cheminParcourus, parc);
            }
            if (cheminB != null && !cheminParcourus.contains(cheminB)) {
                valeurs[2] = cheminB.tailleRouteMax(j, cheminParcourus, parc);
            }
            if (cheminD != null && !cheminParcourus.contains(cheminD)) {
                valeurs[3] = cheminD.tailleRouteMax(j, cheminParcourus, parc);
            }
            int max = 0;
            int max2 = 0;
            for (int i = 0; i < valeurs.length; i++) {
                if (valeurs[i] > max) {
                    int tmp = max;
                    max = valeurs[i];
                    max2 = tmp;
                } 
                else if (valeurs[i] > max2) {
                    max2 = valeurs[i];
                }
            }
            return max;  
        } 
        else {
            return 0;
        }
    

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
            if(colonie instanceof Ville) {
                return ANSI_BLUE_BACKGROUND + "●" + ANSI_RESET;
            }
            else {
                return ANSI_BLUE + "●" + ANSI_RESET;
            }
        }
        else if(colonie.joueur.couleur.equals(Color.RED)) {
            if(colonie instanceof Ville) {
                return ANSI_RED_BACKGROUND + "●" + ANSI_RESET;
            }
            else {
                return ANSI_RED + "●" + ANSI_RESET;
            }
        }
        else if(colonie.joueur.couleur.equals(Color.YELLOW)) {
            if(colonie instanceof Ville) {
                return ANSI_YELLOW_BACKGROUND + "●" + ANSI_RESET;
            }
            else {
                return ANSI_YELLOW + "●" + ANSI_RESET;
            }
        }
        else if(colonie.joueur.couleur.equals(Color.GREEN)) {
            if(colonie instanceof Ville) {
                return ANSI_GREEN_BACKGROUND + "●" + ANSI_RESET;
            }
            else {
                return ANSI_GREEN + "●" + ANSI_RESET;
            }
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
