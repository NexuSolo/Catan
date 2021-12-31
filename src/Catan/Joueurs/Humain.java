package Catan.Joueurs;

import java.util.LinkedList;

import Catan.*;
import Catan.Cartes.*;

public class Humain extends Joueur{

    public Humain(String pseudo, String color) {
        super(pseudo, color);
    }

    public boolean placerColonie(Jeu jeu, boolean premierTour) {
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
            Intersection inter = coordonéesToIntersection(jeu.getPlateau(), reponse);
            if(inter != null) {
                if(colonieEstPlaceable(inter, premierTour)) {
                    if(!premierTour) {
                        super.removeRessource(Ressource.BOIS, 1);
                        super.removeRessource(Ressource.ARGILE,1);
                        super.removeRessource(Ressource.BLE,1);
                        super.removeRessource(Ressource.LAINE,1);
                    }
                    inter.setColonie(new Colonie(this));
                    nombreColonies++;
                    point++;
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
                    jeu.getPlateau().affiche();
                    if(premierTour) {
                        placerRoute(jeu, true, inter);
                    }
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

    public boolean placerRoute(Jeu jeu, boolean gratuit, Intersection premierTour) {
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
                        addRoute(premierTour.getCheminH());
                        setTailleRoute(jeu);
                        jeu.getPlateau().affiche();
                        return true;
                    }
                }
                else if(premierTour.getCheminB() != null && reponse.equals("b")) {
                    if(routeEstPlaceable(premierTour.getCheminB())) {
                        premierTour.getCheminB().setRoute(this);
                        addRoute(premierTour.getCheminB());
                        setTailleRoute(jeu);
                        jeu.getPlateau().affiche();
                        return true;
                    }
                }
                else if(premierTour.getCheminG() != null && reponse.equals("g")) {
                    if(routeEstPlaceable(premierTour.getCheminG())) {
                        premierTour.getCheminG().setRoute(this);
                        addRoute(premierTour.getCheminG());
                        setTailleRoute(jeu);
                        jeu.getPlateau().affiche();
                        return true;
                    }
                }
                else if(premierTour.getCheminD() != null && reponse.equals("d")) {
                    if(routeEstPlaceable(premierTour.getCheminD())) {
                        premierTour.getCheminD().setRoute(this);
                        addRoute(premierTour.getCheminD());
                        setTailleRoute(jeu);
                        jeu.getPlateau().affiche();
                        return true;
                    }
                }
            }
        }
        System.out.println("Ou voullez-vous placer votre route ? Exemple : 1:1G représente le chemin a gauche de la case x = 1 y = 1");
        if (!gratuit) {
            System.out.println("Ou annuler l'action en écrivant \"Annuler\"");
        }
        while (true) {
            String reponse = Jeu.scan();
            if(Jeu.MotToMotMinuscule(reponse).equals("annuler") && !gratuit) {
                return false;
            }
            Chemin chemin = coordonéesToChemin(jeu.getPlateau(), reponse);
            if(chemin != null) {
                if(routeEstPlaceable(chemin)) {
                    if(!gratuit) {
                        super.removeRessource(Ressource.BOIS, 1);
                        super.removeRessource(Ressource.ARGILE,1);
                    }
                    chemin.setRoute(this);
                    addRoute(chemin);
                    setTailleRoute(jeu);
                    jeu.getPlateau().affiche();
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
        if(nombreVilles >= 4) {
            System.out.println("Le nombre maximum de ville est de 4.");
            return false;
        }
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
                            removeRessource(Ressource.ROCHE, 3);
                            removeRessource(Ressource.BLE, 2);
                            inter.setColonie(new Ville(this));
                            System.out.println("Félicitation vous avez transformer votre colonie en ville !");
                            nombreVilles++;
                            nombreColonies--;
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
        cartesUtilisables();
        jeu.getPlateau().LancerDes(this, jeu.getJoueurs());
        while (true) {
            afficheRessource();
            System.out.print(this + ", quelle action voulez vous faire ?");
            if(possedeRessourcesRoute().size() == 0) {
                System.out.print(" [Route]");
            }
            if(possedeRessourcesColonie().size() == 0) {
                System.out.print(" [Colonie]");
            }
            if(possedeRessourcesVille().size() == 0){
                System.out.print(" [Ville]");
            }
            if(possedeRessourcesDeveloppement().size() == 0) {
                System.out.print(" [Développement]");
            }
            System.out.println(" [Echange] [Fin]");
            String reponse = Jeu.MotToMotMinuscule(Jeu.scan());
            if(reponse.equals("fin")) {
                break;
            }
            reponseToAction(jeu, reponse);
        }
    }

    public boolean echange(Jeu jeu) {
        System.out.println("Voullez vous echanger avec la banque ou d'autres joueurs ? [Banque] [Joueur]");
        System.out.println("Ou annuler l'action en écrivant \"Annuler\"");
        while (true) {
            String reponse = Jeu.MotToMotMinuscule(Jeu.scan());
            if(reponse.equals("annuler")) {
                return false;
            }
            else if(reponse.equals("banque")) {
                echangeBanque(jeu);
                return true;
            }
            else if(reponse.equals("joueur")) {
                echange(jeu);
                return true;
            }
            else {
                System.out.println("Action invalide");
            }
        }
    }

    public void echangeBanque(Jeu jeu) {
        boolean generale = false;
        boolean bois = false;
        boolean argile = false;
        boolean laine = false;
        boolean ble = false;
        boolean roche = false;
        if(ports.size() == 0) {
            System.out.println("Vous ne possedez pas de port, vous devez echanger 4 ressources du même type pour en obtenir une nouvelle.");
        }
        else {
            for (Port port : ports) {
                if(port.getRessource() == null) {
                    generale = true;
                    System.out.println("Vous possedez un port générale, vous devez echanger 3 ressources du même type pour en obtenir une nouvelle.");
                }
                else {
                    if(port.getRessource().equals(Ressource.BOIS)) {
                        bois = true;
                        System.out.println("Vous possedez un port de bois, vous pouvez echanger 2 bois pour obtenir une nouvelle ressource");
                    }
                    else if(port.getRessource().equals(Ressource.ARGILE)) {
                        argile = true;
                        System.out.println("Vous possedez un port d'argile, vous pouvez echanger 2 d'argiles pour obtenir une nouvelle ressource");
                    }
                    else if(port.getRessource().equals(Ressource.LAINE)){
                        laine = true;
                        System.out.println("Vous possedez un port de laine, vous pouvez echanger 2 laines pour obtenir une nouvelle ressource");
                    }
                    else if(port.getRessource().equals(Ressource.BLE)){
                        ble = true;
                        System.out.println("Vous possedez un port de blé, vous pouvez echanger 2 blés pour obtenir une nouvelle ressource");
                    }
                    else if(port.getRessource().equals(Ressource.ROCHE)){
                        roche = true;
                        System.out.println("Vous possedez un port de roche, vous pouvez echanger 2 roches pour obtenir une nouvelle ressource");
                    }
                }
            }
        }
        int[] ressources = new int[5];
        System.out.println("Choisissez les ressources a echanger. Exemple -1Bois ou +1Roche");
        System.out.println("Ou effectuer l'échange en écrivant \"Echange\"");
        System.out.println("Ou annuler l'action en écrivant \"Annuler\"");
        while(true){
            System.out.println("Vous avez actuellement un echange de " + ressources[0] + " bois, " + ressources[1] + " argile, " + ressources[2] + " laine, " + ressources[3] + " blé, " + ressources[4] + " roche.");
            String reponse = Jeu.MotToMotMinuscule(Jeu.scan());
            if(reponse.equals("annuler")) {
                return;
            }
            if(reponse.equals("echange")) {
                break;
            }
            int[] temp;
            temp = ligneEchange(reponse,ressources);
            if(temp != null) {
                ressources = temp;
            }
        }
        int[] coeff = coefficientsPort(ressources,bois,argile,laine,ble,roche,generale);
        if(coeff != null) {
            int ajout = 0;
            int supprimer = 0;
            for (int i : coeff) {
                if(i < 0) {
                    supprimer += i;
                }
                else {
                    ajout += i;
                }
            }
            if(supprimer + ajout == 0) {
                if(ressources[0] > 0) {
                    addRessource(Ressource.BOIS, ressources[0]);
                    System.out.println("Vous avez gagné " + ressources[0] + " bois");
                }
                else {
                    if(ressources[0] != 0) {
                        removeRessource(Ressource.BOIS, Math.abs(ressources[0]));
                        System.out.println("Vous avez échangé " + Math.abs(ressources[0]) + " bois");
                    }
                }

                if(ressources[1] > 0) {
                    addRessource(Ressource.ARGILE, ressources[1]);
                    System.out.println("Vous avez gagné " + ressources[1] + " argile");
                }
                else {
                    if(ressources[1] != 0) {
                        removeRessource(Ressource.ARGILE, Math.abs(ressources[1]));
                        System.out.println("Vous avez échangé " + Math.abs(ressources[1]) + " argile");
                    }
                }

                if(ressources[2] > 0) {
                    addRessource(Ressource.LAINE, ressources[2]);
                    System.out.println("Vous avez gagné " + ressources[2] + " laine");
                }
                else {
                    if(ressources[2] != 0) {
                        removeRessource(Ressource.LAINE, Math.abs(ressources[2]));
                        System.out.println("Vous avez échangé " + Math.abs(ressources[2]) + " laine");
                    }
                }
                
                if(ressources[3] > 0) {
                    addRessource(Ressource.BLE, ressources[3]);
                    System.out.println("Vous avez gagné " + ressources[3] + " blé");
                }
                else {
                    if(ressources[3] != 0) {
                        removeRessource(Ressource.BLE, Math.abs(ressources[3]));
                        System.out.println("Vous avez échangé " + Math.abs(ressources[3]) + " blé");
                    }
                }

                if(ressources[4] > 0) {
                    addRessource(Ressource.ROCHE, ressources[4]);
                    System.out.println("Vous avez gagné " + ressources[4] + " roche");
                }
                else {
                    if(ressources[4] != 0) {
                        removeRessource(Ressource.ROCHE, Math.abs(ressources[4]));
                        System.out.println("Vous avez échangé " + Math.abs(ressources[4]) + " roche");
                    }
                }
                System.out.println();
            }
            else {
                System.out.println("Vos ressources ne sont pas équilibrer");
            }
        }
    }

    public int[] coefficientsPort(int[] tab, boolean bois, boolean argile, boolean laine, boolean ble, boolean roche, boolean global) {
        int[] res = new int[5];
        if(tab[0] < 0) {
            if(bois) {
                if(tab[0] % 2 == 0) {
                    res[0] = tab[0]/2;
                }
                else {
                    System.out.println("Échange invalide. Vous possedez un port de bois, le nombre de bois donné doit etre de 2 ou 4 ou 6");
                    return null;
                }
            }
            else if(global) {
                if(tab[0] % 3 == 0) {
                    res[0] = tab[0]/3;
                }
                else {
                    System.out.println("Échange invalide. Vous possedez un port de générale, le nombre de bois donné doit etre de 3 ou 6 ou 9");
                    return null;
                }
            }
            else {
                if(tab[0] % 4 == 0) {
                    res[0] = tab[0]/4;
                }
                else {
                    System.out.println("Échange invalide. Le nombre de bois donné doit etre de 4 ou 8 ou 12");
                    return null;
                }
            }
        }
        else {
            res[0] = tab[0];
        }
        if(tab[1] < 0) {
            if(argile) {
                if(tab[1] % 2 == 0) {
                    res[1] = tab[1]/2;
                }
                else {
                    System.out.println("Échange invalide. Vous possedez un port de argile, le nombre de argile donné doit etre de 2 ou 4 ou 6");
                    return null;
                }
            }
            else if(global) {
                if(tab[1] % 3 == 0) {
                    res[1] = tab[1]/3;
                }
                else {
                    System.out.println("Échange invalide. Vous possedez un port de générale, le nombre de argile donné doit etre de 3 ou 6 ou 9");
                    return null;
                }
            }
            else {
                if(tab[1] % 4 == 0) {
                    res[1] = tab[1]/4;
                }
                else {
                    System.out.println("Échange invalide. Le nombre de argile donné doit etre de 4 ou 8 ou 12");
                    return null;
                }
            }
        }
        else {
            res[1] = tab[1];
        }
        if(tab[2] < 0) {
            if(laine) {
                if(tab[2] % 2 == 0) {
                    res[2] = tab[2]/2;
                }
                else {
                    System.out.println("Échange invalide. Vous possedez un port de laine, le nombre de laine donné doit etre de 2 ou 4 ou 6");
                    return null;
                }
            }
            else if(global) {
                if(tab[2] % 3 == 0) {
                    res[2] = tab[2]/3;
                }
                else {
                    System.out.println("Échange invalide. Vous possedez un port de générale, le nombre de laine donné doit etre de 3 ou 6 ou 9");
                    return null;
                }
            }
            else {
                if(tab[2] % 4 == 0) {
                    res[2] = tab[2]/4;
                }
                else {
                    System.out.println("Échange invalide. Le nombre de laine donné doit etre de 4 ou 8 ou 12");
                    return null;
                }
            }
        }
        else {
            res[2] = tab[2];
        }
        if(tab[3] < 0) {
            if(ble) {
                if(tab[3] % 2 == 0) {
                    res[3] = tab[3]/2;
                }
                else {
                    System.out.println("Échange invalide. Vous possedez un port de blé, le nombre de blé donné doit etre de 2 ou 4 ou 6");
                    return null;
                }
            }
            else if(global) {
                if(tab[3] % 3 == 0) {
                    res[3] = tab[3]/3;
                }
                else {
                    System.out.println("Échange invalide. Vous possedez un port de générale, le nombre de blé donné doit etre de 3 ou 6 ou 9");
                    return null;
                }
            }
            else {
                if(tab[3] % 4 == 0) {
                    res[3] = tab[3]/4;
                }
                else {
                    System.out.println("Échange invalide. Le nombre de blé donné doit etre de 4 ou 8 ou 12");
                    return null;
                }
            }
        }
        else {
            res[3] = tab[3];
        }
        if(tab[4] < 0) {
            if(roche) {
                if(tab[4] % 2 == 0) {
                    res[4] = tab[4]/2;
                }
                else {
                    System.out.println("Échange invalide. Vous possedez un port de roche, le nombre de roche donné doit etre de 2 ou 4 ou 6");
                    return null;
                }
            }
            else if(global) {
                if(tab[4] % 3 == 0) {
                    res[4] = tab[4]/3;
                }
                else {
                    System.out.println("Échange invalide. Vous possedez un port de générale, le nombre de roche donné doit etre de 3 ou 6 ou 9");
                    return null;
                }
            }
            else {
                if(tab[4] % 4 == 0) {
                    res[4] = tab[4]/4;
                }
                else {
                    System.out.println("Échange invalide. Le nombre de roche donné doit etre de 4 ou 8 ou 12");
                    return null;
                }
            }
        }
        else {
            res[4] = tab[4];
        }
        return res;
    }

    public int[] ligneEchange(String reponse, int[] tab) {
        Ressource ressource = null;
        int val = 0;
        if(reponse.length() >= 5) {
            if(Jeu.estNombre(reponse.substring(0, 3))) {
                if(Jeu.estRessource(reponse.substring(3))) {
                    ressource = stringToRessource(reponse.substring(3));
                    val = Integer.valueOf(reponse.substring(0, 3));
                }
                else {
                    System.out.println("Format invalide. Veuillez indiquer l'échange de chaque ressource 1 a la fois. Exemple -4Bois puis +1Ble");
                    return null;
                }
            }
            else if(Jeu.estNombre(reponse.substring(0, 2))) {
                if(Jeu.estRessource(reponse.substring(2))) {
                    ressource = stringToRessource(reponse.substring(2));
                    val = Integer.valueOf(reponse.substring(0, 2));
                }
                else {
                    System.out.println("Format invalide. Veuillez indiquer l'échange de chaque ressource 1 a la fois. Exemple -4Bois puis +1Ble");
                    return null;
                }
            }
            else {
                System.out.println("Format invalide. Veuillez indiquer l'échange de chaque ressource 1 a la fois. Exemple -4Bois puis +1Ble");
                return null;
            }
        }
        else {
            System.out.println("Format invalide. Veuillez indiquer l'échange de chaque ressource 1 a la fois. Exemple -4Bois puis +1Ble");
            return null;
        }
        if(ressource.equals(Ressource.BOIS)) {
            if(val < 0) {
                if(tab[0] > 0) {
                    if(possede(ressource,val - tab[0])) {
                        tab[0] += val;
                    }
                    else {
                        System.out.println("Vous ne possedez pas assez de bois");
                        return null;
                    }
                }
                else {
                    if(possede(ressource,-(val + tab[0]))) {
                    tab[0] += val;
                    }
                    else {
                        System.out.println("Vous ne possedez pas assez de bois");
                        return null;
                    }
                }
            }
            else{
                tab[0] += val;
            }
        }
        else if(ressource.equals(Ressource.ARGILE)) {
            if(val < 0) {
                if(tab[1] > 0) {
                    if(possede(ressource,val - tab[1])) {
                        tab[0] += val;
                    }
                    else {
                        System.out.println("Vous ne possedez pas assez d'argile");
                        return null;
                    }
                }
                else {
                    if(possede(ressource,-(val + tab[1]))) {
                    tab[1] += val;
                    }
                    else {
                        System.out.println("Vous ne possedez pas assez d'argile");
                        return null;
                    }
                }
            }
            else{
                tab[1] += val;
            }
        }
        else if(ressource.equals(Ressource.LAINE)) {
            if(val < 0) {
                if(tab[2] > 0) {
                    if(possede(ressource,val - tab[2])) {
                        tab[2] += val;
                    }
                    else {
                        System.out.println("Vous ne possedez pas assez de laine");
                        return null;
                    }
                }
                else {
                    if(possede(ressource,-(val + tab[2]))) {
                    tab[2] += val;
                    }
                    else {
                        System.out.println("Vous ne possedez pas assez de laine");
                        return null;
                    }
                }
            }
            else{
                tab[2] += val;
            }
        }
        else if(ressource.equals(Ressource.BLE)) {
            if(val < 0) {
                if(tab[3] > 0) {
                    if(possede(ressource,val - tab[3])) {
                        tab[3] += val;
                    }
                    else {
                        System.out.println("Vous ne possedez pas assez de blé");
                        return null;
                    }
                }
                else {
                    if(possede(ressource,-(val + tab[3]))) {
                    tab[3] += val;
                    }
                    else {
                        System.out.println("Vous ne possedez pas assez de blé");
                        return null;
                    }
                }
            }
            else{
                tab[3] += val;
            }
        }
        else {
            if(val < 0) {
                if(tab[4] > 0) {
                    if(possede(ressource,val - tab[4])) {
                        tab[4] += val;
                    }
                    else {
                        System.out.println("Vous ne possedez pas assez de roche");
                        return null;
                    }
                }
                else {
                    if(possede(ressource,-(val + tab[4]))) {
                    tab[4] += val;
                    }
                    else {
                        System.out.println("Vous ne possedez pas assez de roche");
                        return null;
                    }
                }
            }
            else{
                tab[4] += val;
            }
        }
        return tab;
    }

    public void echangeJouer(Jeu jeu) {
        //TODO
    }

    private void reponseToAction(Jeu jeu, String reponse) {
        LinkedList<Ressource> l;
        switch (reponse) {
            default:
                System.out.println("Commande invalide");
                break;
            case "colonie":
                l = possedeRessourcesColonie();
                if(l.size() == 0) {
                    placerColonie(jeu, false);
                }
                else {
                    System.out.print("Il vous manque ");
                    for (Ressource ressource : l) {
                        System.out.print("[" + ressource + "] ");
                    }
                    System.out.println();
                }
                break;
            case "route":
                l = possedeRessourcesRoute();
                if(l.size() == 0) {
                    placerRoute(jeu, false, null);
                }
                else {
                    System.out.print("Il vous manque ");
                    for (Ressource ressource : l) {
                        System.out.print("[" + ressource + "] ");
                    }
                    System.out.println();
                }
                break;
            case "ville":
                l = possedeRessourcesVille();
                if(l.size() == 0) {
                    placerVille(jeu.getPlateau());
                }
                else {
                    System.out.print("Il vous manque ");
                    for (Ressource ressource : l) {
                        System.out.print("[" + ressource + "] ");
                    }
                    System.out.println();
                }
                break;
            case "developpement":
            String rep = "";
            while (true) {
                System.out.println("Souhaitez vous [acheter] ou [utiliser] une carte ?");
                rep = Jeu.scan();
                if (rep.equals("annuler")) {
                    return;
                }
                else if(rep.equals("acheter")) {
                    achatDeveloppement(jeu.getPlateau());
                    afficheCartes();
                    return;
                }
                else if (rep.equals("utiliser")) {
                    if(cartesUtilisables){
                        utiliserCarte(jeu);
                    } 
                    else {
                        System.out.println("Vous ne pouvez pas utiliser de carte ce tour ci.");
                    }
                    return;
                }
                else {
                    System.out.println("Commande incorrecte");       
                }
            }
            case "echange":
                echange(jeu);
                break;
        }
    }

    public void utiliserCarte(Jeu jeu){
        Carte utilisee = null;
        while(utilisee == null ) {
            System.out.println("Quelle carte voulez vous utiliser ? Ou écrivez \"Annuler\" pour revenir en arrière.");
            afficheCartes();
            String reponse = Jeu.scan();
            if (reponse.toLowerCase().equals("annuler")){
                return;
            }
            utilisee = stringToCarte(reponse);
            if (utilisee == null) {
                System.out.println("Carte Inexistante");
            } 
            else if(!possede(utilisee) ) {
                utilisee = null;
            }
        }
        if (utilisee.utiliser(this, jeu)) {
            removeCarte(utilisee);
            cartesInutilisables();
        }
    }


    public Carte stringToCarte(String reponse) {
        switch(reponse.toLowerCase()) {
            default : return null;
            case "chevalier" : return new Chevalier();
            case "pointdevictoire" : return new PointDeVictoire();
            case "point de victoire" : return new PointDeVictoire();
            case "monopole" : return new Monopole();
            case "invention" : return new Invention();
            case "construction route" : return new ConstructionRoute();
            case "constructionroute" : return new ConstructionRoute();
        }
    }

  




}
