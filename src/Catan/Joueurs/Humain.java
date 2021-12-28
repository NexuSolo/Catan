package Catan.Joueurs;

import Catan.Case;
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

    public boolean placerColonie(Plateau plateau, boolean premierTour, boolean gratuit) {
        if(nombreColonies >= 5) {
            System.out.println("Le nombre maximum de colonie est de 5.");
            return false;
        }
        System.out.println("Ou voulez-vous placer votre colonie ? Exemple : 1:1HG représente l'emplacement en haut à gauche de la case x = 1 y = 1");
        System.out.println("Ou annuler l'action en écrivant \"Annuler\"");
        while(true) {
            String reponse = Jeu.scan();
            if(Jeu.MotToMotMinuscule(reponse).equals("annuler")) {
                return false;
            }
            Intersection inter = coordonéesToIntersection(plateau, reponse);
            if(inter != null) {
                if(ColonieEstPlaceable(inter, premierTour)) {
                    if(!gratuit) {
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

    public Case coordonéesToCase(Plateau p,String s) {
        if(s.length() == 3 && Jeu.estNombre(s.substring(0,1)) && Jeu.estNombre(s.substring(2,3))) {
            int x = Integer.valueOf(s.substring(0,1));
            int y = Integer.valueOf(s.substring(2,3));
            if (x > 0 && x < p.getLength() && y > 0 && y < p.getLength() ) {
                return p.getCase(x, y);
            }
        }
        return null;
    }

    public boolean ColonieEstPlaceable(Intersection inter, boolean premierTour) {
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

    public void defausseVoleur(){
        if(this.ressources.size() > 7) {
            int cartesADefausser = this.ressources.size()/2  ;
            while( cartesADefausser > 0 ) {
                System.out.println("Le voleur s'empare de vos cartes, veuillez choisir lesquels défausser");
                System.out.println("Il vous reste "+cartesADefausser+" carte(s) à défausser");
                System.out.print("Vous possédez ");
                this.afficheRessource();
                String scan = Jeu.scan();
                Ressource defaussee = stringToRessource(scan);
                System.out.println("Vous allez défausser " + defaussee);
                while(defaussee == null || !this.possede(defaussee)) {
                    if (defaussee == null) {
                        System.out.println("Ressource invalide");
                    } 
                    else{
                        System.out.println("Vous ne possédez pas cette ressource");
                    }
                    scan = Jeu.scan();
                    defaussee = stringToRessource(scan);
                }
                this.removeRessource(defaussee);
                cartesADefausser--;
            }
        }
        
    }

    @Override
    public void deplaceVoleur(Plateau p) {
        System.out.println("Ou voulez-vous placer le voleur ? Exemple : 1:1 représente l'emplacement en haut à gauche de la case x = 1 y = 1 ");
        String scan = Jeu.scan();
        Case c = coordonéesToCase(p,scan);
        while (c == null) {
            System.out.println("Case invalide, veuillez réinsérer des coordonnées (Rappel \"2:3\" renvoie la case x = 2 et y = 3) ");
            scan = Jeu.scan();
            c = coordonéesToCase(p,scan);
        }
        p.setVoleur(c);
        
    }
    
}
