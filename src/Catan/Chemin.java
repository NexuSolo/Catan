package Catan;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

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

    public void ajoutPort(LinkedList<Ressource> ressources,int espace,Plateau plateau,String sens) {
        if(!ressources.isEmpty()) {
            if(espace == 0) {
                int random = new Random().nextInt(ressources.size());
                Ressource ressource = ressources.get(random);
                ressources.remove(random);
                Port p = new Port(ressource);
                this.intersection1.setPort(p);
                this.intersection2.setPort(p);
                espace = 3;
            } 
            switch(sens) {
                case "Haut" : 
                    if (this.intersection2.getX() == plateau.cases.length ) {
                        sens = "Droite";
                        this.intersection2.getCheminB().ajoutPort(ressources, espace-1, plateau, sens);
                    } 
                    else {
                        this.intersection2.getCheminD().ajoutPort(ressources, espace-1, plateau, sens);
                    }
                    break;
                case "Gauche" :
                    this.intersection1.getCheminH().ajoutPort(ressources, espace-1, plateau, sens);
                break;
                case "Bas" :
                    if (this.intersection1.getX() == 1 ) {
                        sens = "Gauche";
                        this.intersection1.getCheminH().ajoutPort(ressources, espace-1, plateau, sens);
                    }
                    else {
                        this.intersection1.getCheminG().ajoutPort(ressources, espace-1, plateau, sens);
                    }
                    break;
                case "Droite" :
                    if (this.intersection2.getY() == plateau.cases.length ) {
                        sens = "Bas";
                        this.intersection2.getCheminG().ajoutPort(ressources, espace-1, plateau, sens);
                    } 
                    else {
                        this.intersection2.getCheminB().ajoutPort(ressources, espace-1, plateau, sens);
                    }
                    break; 
            }
            
        }

    }

    public Chemin routeIA(Joueur j,Intersection cible,Set<Intersection> parcourus) {
        if(parcourus.contains(intersection2) && parcourus.contains(intersection1)) {
            System.out.println("Erreur at "+intersection1.getX()+"y"+intersection1.getY()+" x"+intersection2.getX()+"y"+intersection2.getY());
            for (Intersection intersection : parcourus) {
                System.out.println("x"+intersection.getX()+"y"+intersection.getY());
            }
            return this;
        }
        if (route == null) {
            return this;
        }
        if (route != j) {
            System.out.println("what");
        }
            if(parcourus.contains(intersection1)) {
                return intersection2.routeIA(j,cible,parcourus);
            }
            else {
                return intersection1.routeIA(j,cible,parcourus);
            }
    }


    public int tailleRouteMax(Joueur j,LinkedList<Chemin> cheminParcourus,LinkedList<Intersection> parc){
        if (cheminParcourus.contains(this)) {
            return 0;
        } 
        else {
                cheminParcourus.add(this);
                if (route == j) {
                    return Math.max(intersection1.tailleRouteMax(j, cheminParcourus,parc),intersection2.tailleRouteMax(j, cheminParcourus,parc))+1;
                }
                else {
                    return 0;
                }
            }
    }

    public Color cheminToColor(){
        if(route == null) {
            return new Color(255, 89, 0);
        }
        return route.couleur;
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
