package Catan.Joueurs;

import Catan.Chemin;
import Catan.Colonie;
import Catan.Intersection;
import Catan.Jeu;
import Catan.Joueur;
import Catan.Plateau;
import Catan.Ressource;

public class Humain extends Joueur{

    public Humain(String pseudo, String color) {
        super(pseudo, color);
    }

    public boolean placerColonie(Plateau plateau, boolean premierTour) {
        if(nombreColonies >= 5) {
            System.out.println("Le nombre maximum de colonie est de 5.");
            return false;
        }
        System.out.println("Ou voullez-vous placer votre colonie ? Exemple : 1:1HG représente l'emplacement en haut a gauche de la case x = 1 y = 1");
        System.out.println("Ou annuler l'action en écrivant \"Annuler\"");
        while(true) {
            String reponse = Jeu.scan();
            if(Jeu.MotToMotMinuscule(reponse).equals("annuler")) {
                return false;
            }
            Intersection inter = coordonéesToIntersection(plateau, reponse);
            if(inter != null) {
                if(colonieEstPlaceable(inter, premierTour)) {
                    if(!premierTour) {
                        if(super.possede(Ressource.BOIS) && super.possede(Ressource.ARGILE) && super.possede(Ressource.BLE) && super.possede(Ressource.LAINE)) {
                            super.removeRessource(Ressource.BOIS, 1);
                            super.removeRessource(Ressource.ARGILE,1);
                            super.removeRessource(Ressource.BLE,1);
                            super.removeRessource(Ressource.LAINE,1);
                        }
                        else {
                            System.out.print("Vous n'avez pas les ressources nécessaire. Il vous manque");
                            if(!super.possede(Ressource.BOIS)) {
                                System.out.print(" [BOIS]");
                            }
                            if(!super.possede(Ressource.ARGILE)) {
                                System.out.print(" [ARGILE]");
                            }
                            if(!super.possede(Ressource.BLE)) {
                                System.out.print(" [BLE]");
                            }
                            if(!super.possede(Ressource.LAINE)) {
                                System.out.print(" [LAINE]");
                            }
                            System.out.println();
                            return false;
                        }
                    }
                    inter.setColonie(new Colonie(this));
                    System.out.println("Vous avez placer une colonie en x = " + inter.x + ", y = " + inter.y);
                    plateau.affiche();
                    placerRoute(plateau, true, inter);
                    return true;
                }
                else {
                    System.out.println("Il doit y avoir au moins 2 routes entres 2 Colonies");
                    if(!premierTour) {
                        System.out.println("Votre colonie doit etre a coté d'une route");
                    }
                }
            }
            else {
                System.out.println("Les coordonnées sont mal écrites. Exemple : 1:1HG représente l'emplacement en haut a gauche de la case x = 1 y = 1");
            }
        }
    }

    public boolean placerRoute(Plateau plateau, boolean gratuit, Intersection premierTour) {
        if(premierTour != null) {
            System.out.print("Placer une route a coté de votre nouvelle colonie en x = " + premierTour.getX() + " y = " + premierTour.getY() + ". ");
            if(premierTour.getCheminH() != null) {
                System.out.print("[H] ");
            }
            if(premierTour.getCheminB() != null) {
                System.out.print("[B] ");
            }
            if(premierTour.getCheminG() != null) {
                System.out.print("[G] ");
            }
            if(premierTour.getCheminD() != null) {
                System.out.print("[D]");
            }
            System.out.println();
            while (true) {
                String reponse = Jeu.MotToMotMinuscule(Jeu.scan());
                if(premierTour.getCheminH() != null && reponse.equals("h")) {
                    if(routeEstPlaceable(premierTour.getCheminH())) {
                        premierTour.getCheminH().setRoute(this);
                        return true;
                    }
                }
                else if(premierTour.getCheminB() != null && reponse.equals("b")) {
                    if(routeEstPlaceable(premierTour.getCheminB())) {
                        premierTour.getCheminB().setRoute(this);
                        return true;
                    }
                }
                else if(premierTour.getCheminG() != null && reponse.equals("g")) {
                    if(routeEstPlaceable(premierTour.getCheminG())) {
                        premierTour.getCheminG().setRoute(this);
                        return true;
                    }
                }
                else if(premierTour.getCheminD() != null && reponse.equals("d")) {
                    if(routeEstPlaceable(premierTour.getCheminD())) {
                        premierTour.getCheminD().setRoute(this);
                        return true;
                    }
                }
            }
        }
        System.out.println("Ou voullez-vous placer votre route ? Exemple : 1:1G représente le chemin a gauche de la case x = 1 y = 1");
        System.out.println("Ou annuler l'action en écrivant \"Annuler\"");
        while (true) {
            String reponse = Jeu.scan();
            if(Jeu.MotToMotMinuscule(reponse).equals("annuler")) {
                return false;
            }
            Chemin chemin = coordonéesToChemin(plateau, reponse);
            if(chemin != null) {
                if(routeEstPlaceable(chemin)) {
                    if(!gratuit) {
                        if(super.possede(Ressource.BOIS) && super.possede(Ressource.ARGILE)) {
                            super.removeRessource(Ressource.BOIS, 1);
                            super.removeRessource(Ressource.ARGILE,1);
                        }
                        else {
                            System.out.print("Vous n'avez pas les ressources nécessaire. Il vous manque");
                            if(!super.possede(Ressource.BOIS)) {
                                System.out.print(" [BOIS]");
                            }
                            if(!super.possede(Ressource.ARGILE)) {
                                System.out.print(" [ARGILE]");
                            }
                            return false;
                        }
                    }
                    chemin.setRoute(this);
                    System.out.println("Vous avez placer une route en x = " + ", y = ");
                    return true;
                }
            }
            else {
                System.out.println("Les coordonnées sont mal écrites. Exemple : 1:1G représente le chemin a gauche de la case x = 1 y = 1");
            }
        }
    }

    public Intersection coordonéesToIntersection(Plateau plateau, String s) {
        if(s.length() == 5 && Jeu.estNombre(s.substring(0,1)) && Jeu.estNombre(s.substring(2,3))) {
            int x = Integer.valueOf(s.substring(0,1));
            int y = Integer.valueOf(s.substring(2,3));
            if(x >= 1 && x <= plateau.getLength() && y >= 1 && y <= plateau.getLength()) {
                if(Character.toLowerCase(s.charAt(3)) == 'h') {
                    if(Character.toLowerCase(s.charAt(4)) == 'g') {
                        return plateau.getCase(x, y).getHG();
                    }
                    else if(Character.toLowerCase(s.charAt(4)) == 'd') {
                        return plateau.getCase(x, y).getHD();
                    }
                }
                else if(Character.toLowerCase(s.charAt(3)) == 'b') {
                    if(Character.toLowerCase(s.charAt(4)) == 'g') {
                        return plateau.getCase(x, y).getBG();
                    }
                    else if(Character.toLowerCase(s.charAt(4)) == 'd') {
                        return plateau.getCase(x, y).getBD();
                    }
                }
            }
        }
        return null;
    }

    public Chemin coordonéesToChemin(Plateau plateau, String s) {
        if(s.length() == 4 && Jeu.estNombre(s.substring(0,1)) && Jeu.estNombre(s.substring(2,3))) {
            int x = Integer.valueOf(s.substring(0,1));
            int y = Integer.valueOf(s.substring(2,3));
            if(x >= 1 && x <= plateau.getLength() && y >= 1 && y <= plateau.getLength()) {
                if(Character.toLowerCase(s.charAt(3)) == 'h') {
                    return plateau.getCase(x, y).getH();
                }
                if(Character.toLowerCase(s.charAt(3)) == 'b') {
                    return plateau.getCase(x, y).getB();
                }
                if(Character.toLowerCase(s.charAt(3)) == 'g') {
                    return plateau.getCase(x, y).getG();
                }
                if(Character.toLowerCase(s.charAt(3)) == 'd') {
                    return plateau.getCase(x, y).getD();
                }
            }
        }
        return null;
    }

    public boolean colonieEstPlaceable(Intersection inter, boolean premierTour) {
        if(inter.getColonie() != null) {
           return false; 
        }
        if(inter.getCheminH() != null) {
            if(inter.getCheminH().getIntersection1().getColonie() != null) {
                return false;
            }
        }
        if(inter.getCheminB() != null) {
            if(inter.getCheminB().getIntersection2().getColonie() != null) {
                return false;
            }
        }
        if(inter.getCheminG() != null) {
            if(inter.getCheminG().getIntersection1().getColonie() != null) {
                return false;
            }
        }
        if(inter.getCheminD() != null) {
            if(inter.getCheminD().getIntersection2().getColonie() != null) {
                return false;
            }
        }
        if(!premierTour) {
            if(inter.getCheminH().getRoute().equals(this)) {
                return true;
            }
            else if(inter.getCheminB().getRoute().equals(this)) {
                return true;
            }
            else if(inter.getCheminG().getRoute().equals(this)) {
                return true;
            }
            else if(inter.getCheminD().getRoute().equals(this)) {
                return true;
            }
            else {
                return false;
            }
        }
        return true;
    }

    public boolean routeEstPlaceable(Chemin chemin) {
        if(chemin.getRoute() == null) {
            if((chemin.getIntersection1().getColonie() != null && chemin.getIntersection1().getColonie().getJoueur().equals(this)) ||(chemin.getIntersection2().getColonie() != null && chemin.getIntersection2().getColonie().getJoueur().equals(this))) {
                return true;
            }
            else {
                if(chemin.getIntersection1().getCheminH() != null && chemin.getIntersection1().getCheminH().getRoute() != null && chemin.getIntersection1().getCheminH().getRoute().equals(this)) {
                    return true;
                }
                else if(chemin.getIntersection1().getCheminB() != null && chemin.getIntersection1().getCheminB().getRoute() != null && chemin.getIntersection1().getCheminB().getRoute().equals(this)) {
                    return true;
                }
                else if(chemin.getIntersection1().getCheminG() != null && chemin.getIntersection1().getCheminG().getRoute() != null && chemin.getIntersection1().getCheminG().getRoute().equals(this)) {
                    return true;
                }
                else if(chemin.getIntersection1().getCheminD() != null && chemin.getIntersection1().getCheminD().getRoute() != null && chemin.getIntersection1().getCheminD().getRoute().equals(this)) {
                    return true;
                }
                else if(chemin.getIntersection2().getCheminH() != null && chemin.getIntersection2().getCheminH().getRoute() != null && chemin.getIntersection2().getCheminH().getRoute().equals(this)) {
                    return true;
                }
                else if(chemin.getIntersection2().getCheminB() != null && chemin.getIntersection2().getCheminB().getRoute() != null && chemin.getIntersection2().getCheminB().getRoute().equals(this)) {
                    return true;
                }
                else if(chemin.getIntersection2().getCheminG() != null && chemin.getIntersection2().getCheminG().getRoute() != null && chemin.getIntersection2().getCheminG().getRoute().equals(this)) {
                    return true;
                }
                else if(chemin.getIntersection2().getCheminD() != null && chemin.getIntersection2().getCheminD().getRoute() != null && chemin.getIntersection2().getCheminD().getRoute().equals(this)) {
                    return true;
                }
                else {
                    System.out.println("Vous devez avoir une colonie ou une route juxtaposé a votre route");
                }
            }
        }
        else {
            System.out.println("Vous ne pouvez pas placer de route sur une route deja existante");
        }
        return false;
    }
    
}
