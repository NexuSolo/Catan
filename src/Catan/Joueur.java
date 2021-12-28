package Catan;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Random;

public abstract class Joueur {
    public final Color couleur;
    public final String pseudo;
    protected LinkedList<Ressource> ressources = new LinkedList<Ressource>();
    private LinkedList<Carte> cartes;
    private int point;
    public int nombreColonies = 0;
    public int nombreVilles = 0;
    private LinkedList<Port> ports;

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

    public abstract boolean placerColonie(Plateau plateau,boolean premierTour, boolean gratuit);

    public abstract void defausseVoleur();

    public abstract void deplaceVoleur(Plateau p);

    public void afficheRessource() {
        for (Ressource r : ressources) {
            System.out.print(r + " ");
        }
        System.out.println();
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
        }
        else {
            System.out.println("Il n'y a rien à voler chez la victime ...");
        }
    }
}
