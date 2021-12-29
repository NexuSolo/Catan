package Catan.Joueurs;

import Catan.*;

public class Humain extends Joueur{

    public Humain(String pseudo, String color) {
        super(pseudo, color);
    }

    public boolean placerColonie(Plateau plateau, boolean premierTour) {
        if(nombreColonies >= 5) {
            System.out.println("Le nombre maximum de colonie est de 5.");
            return false;
        }
        System.out.println("Ou voulez-vous placer votre colonie ? Exemple : 1:1HG représente l'emplacement en haut à gauche de la case x = 1 y = 1");
        if(!premierTour) {
            System.out.println("Ou annuler l'action en écrivant \"Annuler\"");
        }
        while(true) {
            String reponse = Jeu.scan();
            if(Jeu.MotToMotMinuscule(reponse).equals("annuler") && !premierTour) {
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
                    if(inter.port != null) {
                        if(inter.port.getRessource() == null) {
                            ports.add(inter.port);
                        }
                        else if(inter.port.getRessource().equals(Ressource.BOIS)) {
                            ports.add(inter.port);
                        }
                        else if(inter.port.getRessource().equals(Ressource.ARGILE)) {
                            ports.add(inter.port);
                        }
                        else if(inter.port.getRessource().equals(Ressource.BLE)) {
                            ports.add(inter.port);
                        }
                        else if(inter.port.getRessource().equals(Ressource.LAINE)) {
                            ports.add(inter.port);
                        }
                        else if(inter.port.getRessource().equals(Ressource.ROCHE)) {
                            ports.add(inter.port);
                        }
                    }
                    plateau.affiche();
                    if(premierTour) {
                        placerRoute(plateau, true, inter);
                    }
                    point++;
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
                        plateau.affiche();
                        return true;
                    }
                }
                else if(premierTour.getCheminB() != null && reponse.equals("b")) {
                    if(routeEstPlaceable(premierTour.getCheminB())) {
                        premierTour.getCheminB().setRoute(this);
                        plateau.affiche();
                        return true;
                    }
                }
                else if(premierTour.getCheminG() != null && reponse.equals("g")) {
                    if(routeEstPlaceable(premierTour.getCheminG())) {
                        premierTour.getCheminG().setRoute(this);
                        plateau.affiche();
                        return true;
                    }
                }
                else if(premierTour.getCheminD() != null && reponse.equals("d")) {
                    if(routeEstPlaceable(premierTour.getCheminD())) {
                        premierTour.getCheminD().setRoute(this);
                        plateau.affiche();
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
                            System.out.println();
                            return false;
                        }
                    }
                    chemin.setRoute(this);
                    plateau.affiche();
                    System.out.println("Vous avez placer une route en x = " + ", y = "); //TODO
                    return true;
                }
            }
            else {
                System.out.println("Les coordonnées sont mal écrites. Exemple : 1:1G représente le chemin a gauche de la case x = 1 y = 1");
            }
        }
    }

    public boolean placerVille(Plateau plateau) {
        System.out.println("Ou voulez vous transformer votre colonie en Ville ? Exemple 1:1HG transforme la colonie en haut a gauche en ville");
        System.out.println("Ou annuler l'action en écrivant \"Annuler\"");
        while(true) {
            String reponse = Jeu.MotToMotMinuscule(Jeu.scan());
            if(reponse.equals("annuler")) {
                return false;
            }
            Intersection inter = coordonéesToIntersection(plateau, reponse);
            if(inter != null) {
                if(inter.getColonie() != null) {
                    if(inter.getColonie().getJoueur().equals(this)) {
                        if(!(inter.getColonie() instanceof Ville)) {
                            //removeRessource(Ressource.ROCHE, 3);
                            //removeRessource(Ressource.BLE, 2);
                            inter.setColonie(new Ville(this));
                            System.out.println("Félicitation vous avez transformer votre colonie en ville !");
                            point++;
                            return true;
                        }
                        else {
                            System.out.println("Vous avez deja une ville a cet emplacement");
                        }
                    }
                    else {
                        System.out.println("Vous ne possedez pas cette colonie");
                    }
                }
                else {
                    System.out.println("Vous ne possedez pas de colonie a cet endroit");
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
            if(inter.getCheminH() != null) {
                if(inter.getCheminH().getRoute().equals(this)) {
                    return true;
                }
            }
            if(inter.getCheminB() != null) {
                if(inter.getCheminB().getRoute().equals(this)) {
                    return true;
                }
            }
            if(inter.getCheminG() != null) {
                if(inter.getCheminG().getRoute().equals(this)) {
                    return true;
                }
            }
            if(inter.getCheminD() != null) {
                if(inter.getCheminD().getRoute().equals(this)) {
                    return true;
                }
            }
            return false;
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
    
    public void defausseVoleur(){
        if(this.ressources.size() > 7) {
            int cartesADefausser = this.ressources.size()/2  ;
            if(cartesADefausser > 0) {
                System.out.println("Le voleur s'empare de vos cartes, veuillez choisir lesquels défausser");
            }
            while( cartesADefausser > 0 ) {
                System.out.println("Il vous reste "+cartesADefausser+" carte(s) à défausser");
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
        p.affiche();
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
    
    @Override
    public void tour(Jeu jeu) {
        jeu.getPlateau().LancerDes(this, jeu.getJoueurs());
        while (true) {
            afficheRessource();
            System.out.println(this + " Quelle action voulez vous faire ? [Colonie] [Route] [Ville] [Développement] [Echange] [Fin]");
            String reponse = Jeu.MotToMotMinuscule(Jeu.scan());
            if(reponse.equals("fin")) {
                break;
            }
            reponseToAction(jeu.getPlateau(), reponse);
        }

    }

    private void reponseToAction(Plateau plateau, String reponse) {
        switch (reponse) {
            default:
                System.out.println("Commande invalide");
                break;
            case "colonie":
                placerColonie(plateau, false);
                break;
            case "route":
                placerRoute(plateau, false, null);
                break;
            case "ville":
                placerVille(plateau);
                break;
            case "développement":
                break;
            case "echange":
                break;
        }
    }

}
