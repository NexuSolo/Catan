package Catan.Joueurs;

import java.io.IOException;
import java.util.LinkedList;

import Catan.*;
import Catan.Cartes.*;

public class Humain extends Joueur{

    public Humain(String pseudo, String color) {
        super(pseudo, color);
    }

    public boolean placerColonie(Jeu jeu, boolean premierTour, boolean secondTour, Intersection intersection) throws IOException, InterruptedException {
        if(nombreColonies >= 5) {
            System.out.println("Le nombre maximum de colonie est de 5.");
            if(jeu.graphique) {
                jeu.vue.getTerminal().append("Le nombre maximum de colonie est de 5. \n");
                jeu.vue.repaint();
                jeu.vue.revalidate();
            }
            return false;
        }
        if(jeu.graphique) {
            if(premierTour || possedeRessourcesColonie().size() == 0) {
                if(intersection.getColonie() == null) {
                    if(colonieEstPlaceable(intersection, premierTour)) {
                        if(!premierTour) {
                            super.removeRessource(Ressource.BOIS, 1);
                            super.removeRessource(Ressource.ARGILE,1);
                            super.removeRessource(Ressource.BLE,1);
                            super.removeRessource(Ressource.LAINE,1);
                        }
                        intersection.setColonie(new Colonie(this));
                        if(secondTour) {
                            freeRessource(jeu, intersection);
                        }
                        colonies.add(intersection);
                        jeu.vue.getTerminal().append("Vous avez placer une colonie en x = " + intersection.x + ", y = " + intersection.y + "\n");
                        jeu.vue.repaint();
                        jeu.vue.revalidate();
                        if(intersection.port != null) {
                            if(intersection.port.getRessource() == null) {
                                ports.add(intersection.port);
                            }
                            else if(intersection.port.getRessource().equals(Ressource.BOIS)) {
                                ports.add(intersection.port);
                            }
                            else if(intersection.port.getRessource().equals(Ressource.ARGILE)) {
                                ports.add(intersection.port);
                            }
                            else if(intersection.port.getRessource().equals(Ressource.BLE)) {
                                ports.add(intersection.port);
                            }
                            else if(intersection.port.getRessource().equals(Ressource.LAINE)) {
                                ports.add(intersection.port);
                            }
                            else if(intersection.port.getRessource().equals(Ressource.ROCHE)) {
                                ports.add(intersection.port);
                            }
                        }
                        jeu.vue.refresh(this, true, false);
                        if(premierTour) {
                            jeu.vue.actionPlacerRoute(true);
                            while(true) {
                                if(jeu.vue.getActions() && jeu.vue.getSelectionChemin() != null) {
                                    if(placerRoute(jeu, true, intersection, jeu.vue.getSelectionChemin(), true)) {
                                        break;
                                    }
                                    else {
                                        jeu.vue.setSelectionChemin(null);
                                    }
                                }
                                Thread.sleep(5);
                            }
                        }
                        jeu.vue.repaint();
                        jeu.vue.revalidate();
                        return true;
                    }
                    jeu.getPlateau().affiche();
                }
                else {
                    jeu.vue.getTerminal().append("Cette intersection appartient deja a un joueur" + "\n");
                    jeu.vue.repaint();
                    jeu.vue.revalidate();
                    return false;
                }
            }
            else {
                jeu.vue.getTerminal().append("Vous n'avez pas assez de ressources" + "\n");
                jeu.vue.repaint();
                jeu.vue.revalidate();
                return false;
            }
        }
        else {
            System.out.println("Ou voulez-vous placer votre colonie ? Exemple : 1:1HG représente l'emplacement en haut à gauche de la case x = 1 y = 1");
            if(!premierTour) {
                System.out.println("Ou annuler l'action en écrivant \"Annuler\"");
            }
            while(true) {
                String reponse = Jeu.scan();
                if(reponse.equals("annuler") && !premierTour) {
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
                        colonies.add(inter);
                        if(secondTour) {
                            freeRessource(jeu, inter);
                        }
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
                            placerRoute(jeu, true, inter, null, true);
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
        return false;
    }

    public boolean placerRoute(Jeu jeu, boolean gratuit, Intersection premierTour, Chemin cheminn, boolean premierTourB) {
        if(jeu.graphique) {
            if(cheminn.getRoute() == null) {
                if(premierTourB) {
                    if(cheminn.getIntersection1().equals(premierTour) || cheminn.getIntersection2().equals(premierTour)) {
                        cheminn.setRoute(this);
                        addRoute(cheminn);
                        setTailleRoute(jeu);
                        return true;
                    }
                    else {
                        jeu.vue.getTerminal().append("Vous devez placer une route a coté de vottre nouvelle colonie  \n");
                        jeu.vue.repaint();
                        jeu.vue.revalidate();
                        return false;
                    }
                }
                else {
                    if(routeEstPlaceable(cheminn)) {
                        if(!gratuit) {
                            removeRessource(Ressource.BOIS);
                            removeRessource(Ressource.ARGILE);
                            removeRessource(Ressource.LAINE);
                            removeRessource(Ressource.BLE);
                        }
                        cheminn.setRoute(this);
                        addRoute(cheminn);
                        setTailleRoute(jeu);
                        return true;
                    }
                    else {
                        jeu.vue.getTerminal().append("Vous devez placer une route a coté d'une de vos colonies  \n");
                        jeu.vue.repaint();
                        jeu.vue.revalidate();
                        return false;
                    }
                }
            }
            else {
                jeu.vue.getTerminal().append("Cette route appartiens deja a un joueur \n");
                jeu.vue.repaint();
                jeu.vue.revalidate();
                return false;
            }
        }
        else {
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
                    String reponse = Jeu.scan();
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
                if(reponse.equals("annuler") && !gratuit) {
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
                        System.out.println("Vous avez placé une route");
                        return true;
                    }
                }
                else {
                    System.out.println("Les coordonnées sont mal écrites. Exemple : 1:1G représente le chemin a gauche de la case x = 1 y = 1");
                }
            }
        }
    }

    public boolean placerVille(Jeu jeu, Intersection intersection) {
        if(nombreVilles >= 4) {
            System.out.println("Le nombre maximum de ville est de 4.");
            if(jeu.graphique) {
                jeu.vue.getTerminal().append("Le nombre maximum de ville est de 4. \n");
                jeu.vue.repaint();
                jeu.vue.revalidate();
            }
            return false;
        }
        if(jeu.graphique) {
            if(possedeRessourcesVille().size() == 0) {
                if(intersection.getColonie() != null) {
                    if(intersection.getColonie().getJoueur().equals(this)) {
                        removeRessource(Ressource.ROCHE, 3);
                        removeRessource(Ressource.BLE, 2);
                        intersection.setColonie(new Ville(this));
                        jeu.vue.getTerminal().append("Félicitations vous avez transformé votre colonie en ville ! \n");
                        jeu.vue.repaint();
                        jeu.vue.revalidate();
                        System.out.println("Félicitations vous avez transformé votre colonie en ville !");
                        nombreVilles++;
                        nombreColonies--;
                        return true;
                    }
                    else {
                        jeu.vue.getTerminal().append("Cette colonie ne vous appartient pas \n");
                        jeu.vue.repaint();
                        jeu.vue.revalidate();
                        return false;
                    }
                }
                else {
                    jeu.vue.getTerminal().append("Cette intersection n'est pas une colonie \n");
                    jeu.vue.repaint();
                    jeu.vue.revalidate();
                    return false;
                }
            }
            else {
                jeu.vue.getTerminal().append("Vous n'avez pas les ressources nécessaires \n");
                jeu.vue.repaint();
                jeu.vue.revalidate();
                return false;
            }
        }
        else {
            System.out.println("Où voulez vous transformer votre colonie en Ville ? Exemple 1:1HG transforme la colonie en haut a gauche en ville");
            System.out.println("Ou annuler l'action en écrivant \"Annuler\"");
            while(true) {
                String reponse = Jeu.scan();
                if(reponse.equals("annuler")) {
                    return false;
                }
                Intersection inter = coordonéesToIntersection(jeu.getPlateau(), reponse);
                if(inter != null) {
                    if(inter.getColonie() != null) {
                        if(inter.getColonie().getJoueur().equals(this)) {
                            if(!(inter.getColonie() instanceof Ville)) {
                                removeRessource(Ressource.ROCHE, 3);
                                removeRessource(Ressource.BLE, 2);
                                inter.setColonie(new Ville(this));
                                System.out.println("Félicitations vous avez transformer votre colonie en ville !");
                                nombreVilles++;
                                nombreColonies--;
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
    }

    public Intersection coordonéesToIntersection(Plateau plateau, String s) {
        try {
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
        } catch (Exception e) {
            return null;
        }
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
    
    public void defausseVoleur(Jeu jeu){
        if(ressources.size() > 7) {
            if(jeu.graphique) {
                int i = ressources.size()/2 + ressources.size()%2;
                jeu.vue.setAction(jeu.vue.actionVoleurDefausse(this, new int[5]));
                while(ressources.size() != i) {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("FINI");
            }
            else {
                int cartesADefausser = ressources.size()/2  ;
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
        
    }
    
    @Override
    public void deplaceVoleur(Jeu jeu) {
        Case c = null;
        if(jeu.graphique) {
            jeu.getPlateau().affiche();
            jeu.vue.repaint();
            jeu.vue.revalidate();
            c = jeu.vue.getSelectionCase();
        }
        else {
            System.out.println("Ou voulez-vous placer le voleur ? Exemple : 1:1 représente l'emplacement en haut à gauche de la case x = 1 y = 1 ");
            String scan = Jeu.scan();
            c = coordonéesToCase(jeu.getPlateau(),scan);
            while (c == null) {
                System.out.println("Case invalide, veuillez réinsérer des coordonnées (Rappel \"2:3\" renvoie la case x = 2 et y = 3) ");
                scan = Jeu.scan();
                c = coordonéesToCase(jeu.getPlateau(),scan);
            }
        }
        jeu.getPlateau().setVoleur(c);
    }
    
    @Override
    public void tour(Jeu jeu) throws IOException, InterruptedException {
        addRessource(Ressource.ARGILE,20);
        addRessource(Ressource.BOIS,20);
        addRessource(Ressource.LAINE,20);
        addRessource(Ressource.BLE,20);
        addRessource(Ressource.ROCHE,20);
        addCarte(new Chevalier());
        addCarte(new Chevalier());
        addCarte(new Chevalier());
        addCarte(new Chevalier());
        cartesUtilisables();
        afficheCartes();
        System.out.println(cartesUtilisables);
        jeu.getPlateau().LancerDes(jeu, this, jeu.getJoueurs());
        if(jeu.graphique){
            jeu.vue.refresh(this, false, false);
        }
        while (true) {
            if(jeu.graphique) {
                if(jeu.vue.getTourFini()) {
                    jeu.vue.setTourFini(false);
                    jeu.actuel = jeu.getJoueurs().get(jeu.joueurSuivant());
                    break;
                }
            }
            else {
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
                String reponse = Jeu.scan();
                if(reponse.equals("fin")) {
                    jeu.actuel = jeu.getJoueurs().get(jeu.joueurSuivant());
                    break;
                }
                reponseToAction(jeu, reponse, jeu.getControl());
            }
            Thread.sleep(5);
        }
    }

    public boolean echange(Jeu jeu) throws IOException, InterruptedException {
        System.out.println("Voullez vous echanger avec la banque ou d'autres joueurs ? [Banque] [Joueur]");
        System.out.println("Ou annuler l'action en écrivant \"Annuler\"");
        while (true) {
            String reponse = Jeu.scan();
            if(reponse.equals("annuler")) {
                return false;
            }
            else if(reponse.equals("banque")) {
                echangeBanque(jeu, null);
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

    public boolean[] portPossede(Jeu jeu, boolean affiche) throws IOException, InterruptedException {
        boolean[] res = new boolean[6];
        if(ports.size() == 0) {
            System.out.println("Vous ne possedez pas de port, vous devez echanger 4 ressources du même type pour en obtenir une nouvelle.");
            if(jeu.graphique && affiche) {
                jeu.vue.getTerminal().append("Vous ne possedez pas de port, vous devez echanger 4 ressources du même type pour en obtenir une nouvelle." + "\n");
                jeu.vue.refresh(this, false, true);
            }
        }
        else {
            for (Port port : ports) {
                if(port.getRessource() == null) {
                    res[5] = true;
                    System.out.println("Vous possedez un port générale, vous devez echanger 3 ressources du même type pour en obtenir une nouvelle.");
                    if(jeu.graphique && affiche) {
                        jeu.vue.getTerminal().append("Vous possedez un port générale, vous devez echanger 3 ressources du même type pour en obtenir une nouvelle." + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
                }
                else {
                    if(port.getRessource().equals(Ressource.BOIS)) {
                        res[0] = true;
                        System.out.println("Vous possedez un port de bois, vous pouvez echanger 2 bois pour obtenir une nouvelle ressource");
                        if(jeu.graphique && affiche) {
                            jeu.vue.getTerminal().append("Vous possedez un port de bois, vous pouvez echanger 2 bois pour obtenir une nouvelle ressource" + "\n");
                            jeu.vue.refresh(this, false, true);
                        }
                    }
                    else if(port.getRessource().equals(Ressource.ARGILE)) {
                        res[1] = true;
                        System.out.println("Vous possedez un port d'argile, vous pouvez echanger 2 d'argiles pour obtenir une nouvelle ressource");
                        if(jeu.graphique && affiche) {
                            jeu.vue.getTerminal().append("Vous possedez un port d'argile, vous pouvez echanger 2 d'argiles pour obtenir une nouvelle ressource" + "\n");
                            jeu.vue.refresh(this, false, true);
                        }
                    }
                    else if(port.getRessource().equals(Ressource.LAINE)){
                        res[2] = true;
                        System.out.println("Vous possedez un port de laine, vous pouvez echanger 2 laines pour obtenir une nouvelle ressource");
                        if(jeu.graphique && affiche) {
                            jeu.vue.getTerminal().append("Vous possedez un port de laine, vous pouvez echanger 2 laines pour obtenir une nouvelle ressource" + "\n");
                            jeu.vue.refresh(this, false, true);
                        }
                    }
                    else if(port.getRessource().equals(Ressource.BLE)){
                        res[3] = true;
                        System.out.println("Vous possedez un port de blé, vous pouvez echanger 2 blés pour obtenir une nouvelle ressource");
                        if(jeu.graphique && affiche) {
                            jeu.vue.getTerminal().append("Vous possedez un port de blé, vous pouvez echanger 2 blés pour obtenir une nouvelle ressource" + "\n");
                            jeu.vue.refresh(this, false, true);
                        }
                    }
                    else if(port.getRessource().equals(Ressource.ROCHE)){
                        res[4] = true;
                        System.out.println("Vous possedez un port de roche, vous pouvez echanger 2 roches pour obtenir une nouvelle ressource");
                        if(jeu.graphique && affiche) {
                            jeu.vue.getTerminal().append("Vous possedez un port de roche, vous pouvez echanger 2 roches pour obtenir une nouvelle ressource" + "\n");
                            jeu.vue.refresh(this, false, true);
                        }
                    }
                }
            }
        }
        return res;
    }

    public void echangeBanque(Jeu jeu, int[] n) throws IOException, InterruptedException {
        boolean[] p = portPossede(jeu, false);
        int[] ressources = new int[5];
        if(!jeu.graphique) {
            System.out.println("Choisissez les ressources a echanger. Exemple -1Bois ou +1Roche");
            System.out.println("Ou effectuer l'échange en écrivant \"Echange\"");
            System.out.println("Ou annuler l'action en écrivant \"Annuler\"");
            while(true){
                System.out.println("Vous avez actuellement un echange de " + ressources[0] + " bois, " + ressources[1] + " argile, " + ressources[2] + " laine, " + ressources[3] + " blé, " + ressources[4] + " roche.");
                String reponse = Jeu.scan();
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
        }
        else {
            ressources = n;
        }
        int[] coeff = coefficientsPort(jeu, ressources,p[0],p[1],p[2],p[3],p[4],p[5]);
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
                if(jeu.graphique) {
                    if(ressources[0] < 0) {
                        if(!possede(Ressource.BOIS,Math.abs(ressources[0]))) {
                            jeu.vue.getTerminal().append("Vous n'avez pas assez de bois" + "\n");
                            jeu.vue.refresh(this, false, true);
                            return;
                        }
                    }
                    if(ressources[1] < 0) {
                        if(!possede(Ressource.ARGILE,Math.abs(ressources[1]))) {
                            jeu.vue.getTerminal().append("Vous n'avez pas assez d'argile" + "\n");
                            jeu.vue.refresh(this, false, true);
                            return;
                        }
                    }
                    if(ressources[2] < 0) {
                        if(!possede(Ressource.LAINE,Math.abs(ressources[2]))) {
                            jeu.vue.getTerminal().append("Vous n'avez pas assez de laine" + "\n");
                            jeu.vue.refresh(this, false, true);
                            return;
                        }
                    }
                    if(ressources[3] < 0) {
                        if(!possede(Ressource.BLE,Math.abs(ressources[3]))) {
                            jeu.vue.getTerminal().append("Vous n'avez pas assez de blé" + "\n");
                            jeu.vue.refresh(this, false, true);
                            return;
                        }
                    }
                    if(ressources[4] < 0) {
                        if(!possede(Ressource.ROCHE,Math.abs(ressources[4]))) {
                            jeu.vue.getTerminal().append("Vous n'avez pas assez de roche" + "\n");
                            jeu.vue.refresh(this, false, true);
                            return;
                        }
                    }
                }
                if(ressources[0] > 0) {
                    addRessource(Ressource.BOIS, ressources[0]);
                    System.out.println("Vous avez gagné " + ressources[0] + " bois");
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Vous avez gagné " + ressources[0] + " bois" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
                }
                else {
                    if(ressources[0] != 0) {
                        removeRessource(Ressource.BOIS, Math.abs(ressources[0]));
                        System.out.println("Vous avez échangé " + Math.abs(ressources[0]) + " bois");
                        if(jeu.graphique) {
                            jeu.vue.getTerminal().append("Vous avez échangé " + Math.abs(ressources[0]) + " bois" + "\n");
                            jeu.vue.refresh(this, false, true);
                        }
                    }
                }
                if(ressources[1] > 0) {
                    addRessource(Ressource.ARGILE, ressources[1]);
                    System.out.println("Vous avez gagné " + ressources[1] + " argile");
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Vous avez gagné " + ressources[1] + " argile" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
                }
                else {
                    if(ressources[1] != 0) {
                        removeRessource(Ressource.ARGILE, Math.abs(ressources[1]));
                        System.out.println("Vous avez échangé " + Math.abs(ressources[1]) + " argile");
                        if(jeu.graphique) {
                            jeu.vue.getTerminal().append("Vous avez échangé " + Math.abs(ressources[1]) + " argile" + "\n");
                            jeu.vue.refresh(this, false, true);
                        }
                    }
                }

                if(ressources[2] > 0) {
                    addRessource(Ressource.LAINE, ressources[2]);
                    System.out.println("Vous avez gagné " + ressources[2] + " laine");
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Vous avez gagné " + ressources[2] + " laine" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
                }
                else {
                    if(ressources[2] != 0) {
                        removeRessource(Ressource.LAINE, Math.abs(ressources[2]));
                        System.out.println("Vous avez échangé " + Math.abs(ressources[2]) + " laine");
                        if(jeu.graphique) {
                            jeu.vue.getTerminal().append("Vous avez échangé " + Math.abs(ressources[2]) + " laine" + "\n");
                            jeu.vue.refresh(this, false, true);
                        }
                    }
                }
                
                if(ressources[3] > 0) {
                    addRessource(Ressource.BLE, ressources[3]);
                    System.out.println("Vous avez gagné " + ressources[3] + " blé");
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Vous avez gagné " + ressources[3] + " blé" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
                }
                else {
                    if(ressources[3] != 0) {
                        removeRessource(Ressource.BLE, Math.abs(ressources[3]));
                        System.out.println("Vous avez échangé " + Math.abs(ressources[3]) + " blé");
                        if(jeu.graphique) {
                            jeu.vue.getTerminal().append("Vous avez échangé " + Math.abs(ressources[3]) + " blé" + "\n");
                            jeu.vue.refresh(this, false, true);
                        }
                    }
                }

                if(ressources[4] > 0) {
                    addRessource(Ressource.ROCHE, ressources[4]);
                    System.out.println("Vous avez gagné " + ressources[4] + " roche");
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Vous avez gagné " + ressources[4] + " roche" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
                }
                else {
                    if(ressources[4] != 0) {
                        removeRessource(Ressource.ROCHE, Math.abs(ressources[4]));
                        System.out.println("Vous avez échangé " + Math.abs(ressources[4]) + " roche");
                        if(jeu.graphique) {
                            jeu.vue.getTerminal().append("Vous avez échangé " + Math.abs(ressources[4]) + " roche" + "\n");
                            jeu.vue.refresh(this, false, true);
                        }
                    }
                }
                System.out.println();
            }
            else {
                System.out.println("Vos ressources ne sont pas équilibrer");
                if(jeu.graphique) {
                    jeu.vue.getTerminal().append("Vos ressources ne sont pas équilibrer" + "\n");
                    jeu.vue.revalidate();
                    jeu.vue.repaint();
                }
            }
        }
    }

    public int[] coefficientsPort(Jeu jeu, int[] tab, boolean bois, boolean argile, boolean laine, boolean ble, boolean roche, boolean global) throws IOException, InterruptedException {
        int[] res = new int[5];
        if(tab[0] < 0) {
            if(bois) {
                if(tab[0] % 2 == 0) {
                    res[0] = tab[0]/2;
                }
                else {
                    System.out.println("Échange invalide. Vous possedez un port de bois, le nombre de bois donné doit etre de 2 ou 4 ou 6");
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Échange invalide. Vous possedez un port de bois, le nombre de bois donné doit etre de 2 ou 4 ou 6" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
                    return null;
                }
            }
            else if(global) {
                if(tab[0] % 3 == 0) {
                    res[0] = tab[0]/3;
                }
                else {
                    System.out.println("Échange invalide. Vous possedez un port générale, le nombre de bois donné doit etre de 3 ou 6 ou 9");
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Échange invalide. Vous possedez un port générale, le nombre de bois donné doit etre de 3 ou 6 ou 9" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
                    return null;
                }
            }
            else {
                if(tab[0] % 4 == 0) {
                    res[0] = tab[0]/4;
                }
                else {
                    System.out.println("Échange invalide. Le nombre de bois donné doit etre de 4 ou 8 ou 12");
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Échange invalide. Le nombre de bois donné doit etre de 4 ou 8 ou 12" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
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
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Échange invalide. Vous possedez un port de argile, le nombre de argile donné doit etre de 2 ou 4 ou 6" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
                    return null;
                }
            }
            else if(global) {
                if(tab[1] % 3 == 0) {
                    res[1] = tab[1]/3;
                }
                else {
                    System.out.println("Échange invalide. Vous possedez un port de générale, le nombre de argile donné doit etre de 3 ou 6 ou 9");
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Échange invalide. Vous possedez un port de générale, le nombre de argile donné doit etre de 3 ou 6 ou 9" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
                    return null;
                }
            }
            else {
                if(tab[1] % 4 == 0) {
                    res[1] = tab[1]/4;
                }
                else {
                    System.out.println("Échange invalide. Le nombre de argile donné doit etre de 4 ou 8 ou 12");
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Échange invalide. Le nombre de argile donné doit etre de 4 ou 8 ou 12" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
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
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Échange invalide. Vous possedez un port de laine, le nombre de laine donné doit etre de 2 ou 4 ou 6" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
                    return null;
                }
            }
            else if(global) {
                if(tab[2] % 3 == 0) {
                    res[2] = tab[2]/3;
                }
                else {
                    System.out.println("Échange invalide. Vous possedez un port de générale, le nombre de laine donné doit etre de 3 ou 6 ou 9");
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Échange invalide. Vous possedez un port de générale, le nombre de laine donné doit etre de 3 ou 6 ou 9" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
                    return null;
                }
            }
            else {
                if(tab[2] % 4 == 0) {
                    res[2] = tab[2]/4;
                }
                else {
                    System.out.println("Échange invalide. Le nombre de laine donné doit etre de 4 ou 8 ou 12");
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Échange invalide. Le nombre de laine donné doit etre de 4 ou 8 ou 12" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
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
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Échange invalide. Vous possedez un port de blé, le nombre de blé donné doit etre de 2 ou 4 ou 6" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
                    return null;
                }
            }
            else if(global) {
                if(tab[3] % 3 == 0) {
                    res[3] = tab[3]/3;
                }
                else {
                    System.out.println("Échange invalide. Vous possedez un port de générale, le nombre de blé donné doit etre de 3 ou 6 ou 9");
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Échange invalide. Vous possedez un port de générale, le nombre de blé donné doit etre de 3 ou 6 ou 9" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
                    return null;
                }
            }
            else {
                if(tab[3] % 4 == 0) {
                    res[3] = tab[3]/4;
                }
                else {
                    System.out.println("Échange invalide. Le nombre de blé donné doit etre de 4 ou 8 ou 12");
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Échange invalide. Le nombre de blé donné doit etre de 4 ou 8 ou 12" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
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
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Échange invalide. Vous possedez un port de roche, le nombre de roche donné doit etre de 2 ou 4 ou 6" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
                    return null;
                }
            }
            else if(global) {
                if(tab[4] % 3 == 0) {
                    res[4] = tab[4]/3;
                }
                else {
                    System.out.println("Échange invalide. Vous possedez un port de générale, le nombre de roche donné doit etre de 3 ou 6 ou 9");
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Échange invalide. Vous possedez un port de générale, le nombre de roche donné doit etre de 3 ou 6 ou 9" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
                    return null;
                }
            }
            else {
                if(tab[4] % 4 == 0) {
                    res[4] = tab[4]/4;
                }
                else {
                    System.out.println("Échange invalide. Le nombre de roche donné doit etre de 4 ou 8 ou 12");
                    if(jeu.graphique) {
                        jeu.vue.getTerminal().append("Échange invalide. Le nombre de roche donné doit etre de 4 ou 8 ou 12" + "\n");
                        jeu.vue.refresh(this, false, true);
                    }
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

    // public void echangeJouer(Jeu jeu) {
    //     //TODO
    // }

    public void reponseToAction(Jeu jeu, String reponse, Controleur control) throws IOException, InterruptedException {
        //IMPLEMENTER VERSION GRAPHIQUE
        LinkedList<Ressource> l;
        switch (reponse) {
            default:
                System.out.println("Commande invalide");
                break;
            case "colonie":
                l = possedeRessourcesColonie();
                if(l.size() == 0) {
                    placerColonie(jeu, false, false, null);
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
                    placerRoute(jeu, false, null, null, false);
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
                    placerVille(jeu, null);
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
                        utiliserCarte(jeu,null);
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

    public void utiliserCarte(Jeu jeu,Carte utilisee){
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
            System.out.println("thumbsup");
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
