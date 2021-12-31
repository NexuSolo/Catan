package Catan;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;

public abstract class Joueur {
    public final Color couleur;
    public final String pseudo;
    protected LinkedList<Ressource> ressources = new LinkedList<Ressource>();
    private LinkedList<Carte> cartes = new LinkedList<Carte>();
    protected int point;
    public int nombreColonies = 0;
    public int nombreVilles = 0;
    protected LinkedList<Port> ports = new LinkedList<Port>();

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

    public abstract boolean placerColonie(Plateau plateau,boolean premierTour);

    public abstract boolean placerRoute(Plateau plateau, boolean gratuit, Intersection premierTour);

    public abstract void defausseVoleur();

    public abstract void deplaceVoleur(Plateau p);

    public abstract void tour(Jeu jeu);

    public abstract boolean echange(Jeu jeu);

    public int calculPoint() {
        int res = point;
        for (Carte carte : cartes) {
            //TODO
        }
        return res;
    }

    public void afficheRessource() {
        int bois = 0;
        int ble = 0;
        int roche = 0;
        int laine = 0;
        int argile = 0;
        for (Ressource r : ressources) {
            switch(r.toString()) {
                case "BOIS":
                    bois++;
                    break;
                case "BLE":
                    ble++;
                    break;
                case "ROCHE":
                    roche++;
                    break;
                case "LAINE":
                    laine++;
                    break;
                case "ARGILE":
                    argile++;
                    break;
            }
        }
        System.out.println("Vous avez : " + bois + " bois " + argile + " argile " + laine + " laine " + ble + " blé " + roche + " roche ");
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
}
