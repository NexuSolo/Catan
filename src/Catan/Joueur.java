package Catan;

import java.awt.Color;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import Catan.Cartes.PointDeVictoire;

public abstract class Joueur {
    public final Color couleur;
    public final String pseudo;
    protected LinkedList<Ressource> ressources = new LinkedList<Ressource>();
    private LinkedList<Carte> cartes = new LinkedList<Carte>();
    protected boolean cartesUtilisables = true;
    protected int point;
    protected LinkedList<Chemin>routes = new LinkedList<>();
    private  int tailleRoute = 0;
    public int nombreColonies = 0;
    public int nombreVilles = 0;
    private int nombreChevalier = 0;
    protected LinkedList<Port> ports = new LinkedList<Port>();
    protected LinkedList<Intersection> colonies = new LinkedList<Intersection>();

    public Joueur(String pseudo, String couleur) {
        this.pseudo = pseudo;
        this.couleur = stringToColor(couleur);
    }

    public static Color stringToColor(String s) {
        if(s.equals("bleu")) {
            return new Color(0,0,255);
        }
        else if(s.equals("rouge")) {
            return new Color(255,0,0);
        }
        else if(s.equals("vert")) {
            return new Color(0,255,0);
        }
        else if(s.equals("jaune")) {
            return new Color(255,255,0);
        }
        return null;
    }

    public String colorToString(Color color) {
        if (color.equals(Color.RED)) {
            return "rouge";
        }
        if (color.equals(Color.GREEN)) {
            return "vert";
        }
        if (color.equals(Color.BLUE)){
            return "bleu";
        }
        if (color.equals(Color.YELLOW)) {
            return "jaune";
        }
        return null;
    }

    public boolean possede(Ressource ressource,int nombre){
        if(nombre < 1) {
            return true;
        }
        for(Ressource r : ressources){
            if (r == ressource){
                nombre--;
                if (nombre < 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean possede(Ressource ressource){
        return possede(ressource, 1);
    }

    public LinkedList<Ressource> possedeRessourcesRoute() {
        LinkedList<Ressource> res = new LinkedList<Ressource>();
        if(!possede(Ressource.BOIS)) {
            res.add(Ressource.BOIS);
        }
        if(!possede(Ressource.ARGILE)) {
            res.add(Ressource.ARGILE);
        }
        return res;
    }

    public LinkedList<Ressource> possedeRessourcesColonie() {
        LinkedList<Ressource> res = new LinkedList<Ressource>();
        if(!possede(Ressource.BOIS)) {
            res.add(Ressource.BOIS);
        }
        if(!possede(Ressource.ARGILE)) {
            res.add(Ressource.ARGILE);
        }
        if(!possede(Ressource.LAINE)) {
            res.add(Ressource.LAINE);
        }
        if(!possede(Ressource.BLE)) {
            res.add(Ressource.BLE);
        }
        return res;
    }

    public LinkedList<Ressource> possedeRessourcesVille() {
        LinkedList<Ressource> res = new LinkedList<Ressource>();
        if(!possede(Ressource.BLE,2)) {
            res.add(Ressource.BLE);
            if(!possede(Ressource.BLE)) {
                res.add(Ressource.BLE);
            }
        }
        if(!possede(Ressource.ROCHE,3)) {
            res.add(Ressource.ROCHE);
            if(!possede(Ressource.ROCHE,2)) {
                res.add(Ressource.ROCHE);
                if(!possede(Ressource.ROCHE)) {
                    res.add(Ressource.ROCHE);
                }
            }
        }
        return res;
    }

    public LinkedList<Ressource> possedeRessourcesDeveloppement() {
        LinkedList<Ressource> res = new LinkedList<Ressource>();
        if(!possede(Ressource.LAINE)) {
            res.add(Ressource.LAINE);
        }
        if(!possede(Ressource.BLE)) {
            res.add(Ressource.BLE);
        }
        if(!possede(Ressource.ROCHE)) {
            res.add(Ressource.ROCHE);
        }
        return res;
    }

    public void addRessource(Ressource ressource,int nombre) {
        while(nombre != 0) {
           this.ressources.add(ressource);
           nombre--; 
        }
    }

    public void addRessource(Ressource ressource){
        addRessource(ressource,1);
    }

    public void removeRessource(Ressource ressource,int nombre) {
        if (possede(ressource,nombre)) {
            while(nombre != 0) {
                this.ressources.remove(ressource);
                nombre--; 
            }
        }
        else {
            System.out.println("Erreur : Pas Assez de Ressources !");
        }
    }

    public void removeRessource(Ressource ressource){
        removeRessource(ressource,1);
    }

    public Ressource stringToRessource(String ressource){
        switch(ressource.toUpperCase()){
            default : return null;
            case "BOIS" : return Ressource.BOIS;
            case "ARGILE" : return Ressource.ARGILE;
            case "BLE" : return Ressource.BLE;
            case "BLÉ" : return Ressource.BLE;
            case "ROCHE" : return Ressource.ROCHE;
            case "LAINE" : return Ressource.LAINE;
        }
    }

    public void addCarte(Carte e){
        cartes.add(e);
    }
    public void removeCarte(Carte e){
        cartes.remove(e);
    }

    public abstract boolean placerColonie(Jeu jeu,boolean premierTour, Intersection intersection) throws IOException, InterruptedException;

    public abstract boolean placerRoute(Jeu jeu, boolean gratuit, Intersection premierTour, Chemin cheminn, boolean premierTourB);

    public abstract void defausseVoleur(Jeu jeu);

    public abstract void deplaceVoleur(Jeu jeu);

    public abstract void tour(Jeu jeu) throws IOException, InterruptedException;

    public abstract boolean echange(Jeu jeu) throws IOException, InterruptedException;

    public int calculPoint(boolean ArmeeLaPlusPuissante,boolean RouteLaPlusLongue,boolean PdV) {
        int res = point;
        if (ArmeeLaPlusPuissante) {
            res += 2;
        }
        if (RouteLaPlusLongue) {
            res += 2;
        }
        if (PdV) {
            for (Carte carte : cartes) {
                if (carte instanceof PointDeVictoire){
                    res++;
                }
            }
        }
        return res;
    }

    public void afficheRessource() {
        int[] tab = listeRessources();
        System.out.println("Vous avez : " + tab[0] + " bois " + tab[1] + " argile " + tab[2] + " laine " + tab[3] + " blé " + tab[4] + " roche ");
    }

    public LinkedList<Ressource> getRessources() {
        return ressources;
    }
    
    public String getPseudo() {
        return pseudo;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return this.pseudo + " ("+colorToString(this.couleur)+")";
    }

    public void volRessource(Joueur victime) {
        if (!victime.getRessources().isEmpty()){
            int random = new Random().nextInt(victime.getRessources().size());
            Ressource volee = victime.getRessources().get(random);
            victime.removeRessource(volee);
            this.addRessource(volee);
            System.out.println("Vous avez volé " + volee + "à " + victime);
        }
        else {
            System.out.println("Il n'y a rien à voler chez la victime ...");
        }
    }

    public void addChevalier(Jeu jeu) {
        nombreChevalier++;
        if (nombreChevalier > 2 ) {
            jeu.setArmeeLaPlusPuissante(this);
        }
    }

    public int getNombreChevalier() {
        return this.nombreChevalier;
    }

    public void achatDeveloppement(Plateau plateau) {
        if(possede(Ressource.ROCHE) && possede(Ressource.BLE) && possede(Ressource.LAINE)) {
            removeRessource(Ressource.ROCHE);
            removeRessource(Ressource.BLE);
            removeRessource(Ressource.LAINE);
            int random = new Random().nextInt(plateau.getCartes().size());
            Carte achetee = plateau.getCartes().get(random);
            addCarte(achetee);
            plateau.getCartes().remove(random);
            System.out.println("Vous avez obtenu une carte "+achetee);
            //plateau.afficheCartes();
        }
        else {
            System.out.print("Vous n'avez pas les ressources nécessaire. Il vous manque");
            if(!possede(Ressource.LAINE)) {
                System.out.print(" [LAINE]");
            }
            if(!possede(Ressource.BLE)) {
                System.out.print(" [BLE]");
            }
            if(!possede(Ressource.ROCHE)) {
                System.out.print(" [ROCHE]");
            }
            System.out.println();
        }
    }

    public void afficheCartes() {
        int chevalier = 0;
        int monopole = 0;
        int ConstructionRoute = 0;
        int Invention = 0;
        int PointDeVictoire = 0;
        for (Carte c : cartes) {
            switch(c.toString().toLowerCase()) {
                case "carte chevalier":
                    chevalier++;
                    break;
                case "carte monopole":
                    monopole++;
                    break;
                case "carte constructionroute":
                    ConstructionRoute++;
                    break;
                case "carte invention":
                    Invention++;
                    break;
                case "carte point de victoire":
                    PointDeVictoire++;
                    break;
            }
        }
        System.out.println("Vous avez : " + PointDeVictoire + " carte(s) PointDeVictoire, " + chevalier + " carte(s) Chevalier, " +  + Invention + " carte(s) Invention, " + monopole + " carte(s) Monopole, " + ConstructionRoute + " ConstructionRoute ");
    }

    public boolean possede(Carte c){
        String s = "";
        for(Carte carte : cartes ) {
            if (c instanceof Carte) {
                if(s.equals("") && !carte.utilisable) {
                    s+= "Vous possedez la carte mais elle n'est pas encore utilisable, veuillez attendre le prochain tour";
                }
                else {
                    return true;
                }
            }
        }
        if (!s.equals("") ) {
            System.out.println(s);
        }
        else {
            System.out.println("Vous ne possédez pas cette carte");
        }
        return false;
    }

    public void cartesInutilisables() {
        for(Carte c : cartes) {
            c.setUtilisable(false);
        }
        cartesUtilisables = false;
    }

    public void cartesUtilisables() {
        for(Carte c : cartes) {
            c.setUtilisable(true);
        }
        cartesUtilisables = true;
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


    public void addRoute(Chemin c){
        routes.add(c);
    }

    public void setTailleRoute(Jeu jeu){
        //TODO : Appel dès que route placée
        LinkedList<Integer> tailleRoute = new LinkedList<Integer>();
        LinkedList<Chemin> cheminParcourus = new LinkedList<>();
        LinkedList<Intersection> parc = new LinkedList<>();
        for(Chemin c : routes) {
            cheminParcourus.clear();
            parc.clear();
            tailleRoute.add(c.tailleRouteMax(this, cheminParcourus,parc));
        }
        if (tailleRoute.size() == 0) {
            this.tailleRoute = 0;
        }
        int max = 0;
        for (Integer i : tailleRoute) {
            if (i > max) {
                max = i;
            }
        }
        this.tailleRoute = max;
        if (this.tailleRoute > 4) {
            jeu.setRouteLaPlusLongue(this);
        }
    }

    public LinkedList<Carte> getCartes() {
        return cartes;
    }

    public int getTailleRoute() {
        return tailleRoute;
    }

    public int[] listeRessources() {
        LinkedList<Ressource> ressources = getRessources();
        int[] res = new int[5];
        for (Ressource r : ressources) {
            if(r.equals(Ressource.BOIS)) {
                res[0]++;
            }
            if(r.equals(Ressource.ARGILE)) {
                res[1]++;
            }
            if(r.equals(Ressource.LAINE)) {
                res[2]++;
            }
            if(r.equals(Ressource.BLE)) {
                res[3]++;
            }
            if(r.equals(Ressource.ROCHE)) {
                res[4]++;
            }
        }
        return res;
    }

    public Joueur getsuivant(LinkedList<Joueur> joueurs) {
        return null;
    }

    public LinkedList<Intersection> getColonies() {
        return colonies;
    }

    public LinkedList<Port> getPorts() {
        return ports;
    }


}
