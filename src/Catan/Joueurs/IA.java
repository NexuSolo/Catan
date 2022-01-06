package Catan.Joueurs;

import Catan.Intersection;
import Catan.Jeu;
import Catan.Joueur;
import Catan.Plateau;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import Catan.*;


public class IA extends Joueur{
    Intersection prochaineColonie = null;
    Chemin prochaineRoute = null;


    public IA(String pseudo, String color) {
        super(pseudo, color);
    }

    @Override
    public boolean placerColonie(Jeu jeu, boolean premierTour, Intersection intersetcion) {
        Intersection[] intersectionTri = jeu.getPlateau().getIntersectionTri();
        int [] valeurInter = jeu.getPlateau().getValeurInter();
        int i = valeurInter.length-1;
        while (!colonieEstPlaceable(intersectionTri[i], premierTour)){
            i--;
        }
        Intersection inter = intersectionTri[i];
        inter.setColonie(new Colonie(this));
        colonies.add(inter);
        setProchaineColonie(jeu);
        if(!premierTour) {
            removeRessource(Ressource.BOIS, 1);
            removeRessource(Ressource.ARGILE,1);
            removeRessource(Ressource.BLE,1);
            removeRessource(Ressource.LAINE,1);
        }
        System.out.println("(BOT) "+ this  +"a placé une colonie en x = " + inter.x + ", y = " + inter.y);
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
       prochaineRoute();
        return true;
        
    }

    public void setProchaineColonie(Jeu jeu){
        if (this.prochaineColonie != null && colonieEstPlaceable(prochaineColonie, true)){
            return;
        }
        Map<Intersection,Integer> valeurIntersection = jeu.getPlateau().getValeurIntersection();
        Intersection[] intersectionTri = jeu.getPlateau().getIntersectionTri();
        int [] valeurInter = jeu.getPlateau().getValeurInter();
        Set<Intersection> coloniesPossible = new HashSet<>();
        for (Intersection colonie : colonies) {
            int x = colonie.getX();
            int y = colonie.getY();
            System.out.println("Inter départ : x"+x+"y"+y);
            if (x + 1 <= jeu.getPlateau().getLength()) {
                if (y + 1 <= jeu.getPlateau().getLength()) {
                    coloniesPossible.add(jeu.getPlateau().getIntersection(x+1, y+1));
                }
                if (y - 1 > 0) {
                    coloniesPossible.add(jeu.getPlateau().getIntersection(x+1, y-1));
                }
                if (x + 2 <= jeu.getPlateau().getLength()) {
                    coloniesPossible.add(jeu.getPlateau().getIntersection(x+2, y));
                }
            }
            if (x - 1 > 0) {
                if (y + 1 <= jeu.getPlateau().getLength()) {
                    coloniesPossible.add(jeu.getPlateau().getIntersection(x-1, y+1));
                }
                if (y - 1 > 0) {
                    coloniesPossible.add(jeu.getPlateau().getIntersection(x-1, y-1));
                }
                if (x - 2 > 0 ) {
                    coloniesPossible.add(jeu.getPlateau().getIntersection(x-2, y));
                }
            }
            if (y + 2 <= jeu.getPlateau().getLength()) {
                coloniesPossible.add(jeu.getPlateau().getIntersection(x, y+2));
            }
            if (y - 2 > 0) {
                coloniesPossible.add(jeu.getPlateau().getIntersection(x,y-2));
            }
        } 
        Set <Intersection> tmp = new HashSet<Intersection>();
        for (Intersection colonie : coloniesPossible) {
            if (colonieEstPlaceable(colonie,true)) { 
                tmp.add(colonie);
            }
        }
        coloniesPossible = tmp;
        // for (Intersection colonie : coloniesPossible){
        //     System.out.println("Inter x"+colonie.getX()+"y"+colonie.getY());
        // }
        Intersection ValMax = null;
        for (Intersection colonie : coloniesPossible){
            if(ValMax == null || valeurIntersection.get(colonie) > valeurIntersection.get(ValMax)) {
                ValMax = colonie;
            }
        }
        System.out.println("ValMax x"+ValMax.getX()+"y"+ValMax.getY());
        this.prochaineColonie = ValMax;
    }

    public void prochaineRoute(){
        System.out.println("EEE");
        Intersection plusProche = null;
        for(Intersection colonie : colonies) {
            if (plusProche == null || prochaineColonie.distance(plusProche) > prochaineColonie.distance(colonie)) {
                plusProche = colonie;
            }
        }
        Chemin prochaineRoute = null;
        if (prochaineColonie.getY() <= plusProche.getY()) {
            if(plusProche.getCheminH().getRoute() == null || plusProche.getCheminH().getRoute() == this) {
               prochaineRoute = plusProche.getCheminH().routeIA(this,prochaineColonie,plusProche);
            }
            if (prochaineColonie.getX() <= plusProche.getX()) {
                if(plusProche.getCheminG().getRoute() == null || plusProche.getCheminG().getRoute() == this) {
                    prochaineRoute = plusProche.getCheminG().routeIA(this,prochaineColonie,plusProche);
                }
            }
            if(plusProche.getCheminD().getRoute() == null || plusProche.getCheminD().getRoute() == this) {
                prochaineRoute = plusProche.getCheminD().routeIA(this,prochaineColonie,plusProche);
            }
            if(plusProche.getCheminB().getRoute() == null || plusProche.getCheminB().getRoute() == this) {
                prochaineRoute = plusProche.getCheminB().routeIA(this,prochaineColonie,plusProche);
            }
        }
        if (prochaineColonie.getX() <= plusProche.getX()) {
            if(plusProche.getCheminG().getRoute() == null || plusProche.getCheminG().getRoute() == this) {
                prochaineRoute = plusProche.getCheminG().routeIA(this,prochaineColonie,plusProche);
            }
            if (prochaineColonie.getY() > plusProche.getY()) {
                if(plusProche.getCheminB().getRoute() == null || plusProche.getCheminB().getRoute() == this) {
                    prochaineRoute = plusProche.getCheminB().routeIA(this,prochaineColonie,plusProche);
                }
            }
            if(plusProche.getCheminH().getRoute() == null || plusProche.getCheminH().getRoute() == this) {
                prochaineRoute = plusProche.getCheminH().routeIA(this,prochaineColonie,plusProche);
            }
            if(plusProche.getCheminD().getRoute() == null || plusProche.getCheminD().getRoute() == this) {
                prochaineRoute = plusProche.getCheminD().routeIA(this,prochaineColonie,plusProche);
            }
        }
        if (prochaineColonie.getX() != plusProche.getX()) {
            if(plusProche.getCheminB().getRoute() == null || plusProche.getCheminB().getRoute() == this) {
                prochaineRoute = plusProche.getCheminB().routeIA(this,prochaineColonie,plusProche);
            }
        }
        if(plusProche.getCheminD().getRoute() == null || plusProche.getCheminD().getRoute() == this) {
            prochaineRoute = plusProche.getCheminD().routeIA(this,prochaineColonie,plusProche);
        }
        if (prochaineRoute == null) {
            System.out.println(":(");
        }
        this.prochaineRoute= prochaineRoute;
        prochaineRoute.setRoute(this);
        System.out.println("Intersection 1 :x"+prochaineRoute.getIntersection1().getX()+"y"+prochaineRoute.getIntersection1().getY()+" x"+prochaineRoute.getIntersection2().getX()+"y"+prochaineRoute.getIntersection2().getY());
    }

    @Override
    public boolean placerRoute(Jeu jeu, boolean gratuit, Intersection premierTour, Chemin cheminn, boolean premierTourB) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void defausseVoleur(Jeu jeu) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deplaceVoleur(Jeu jeu) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void tour(Jeu jeu) {
        cartesUtilisables();
        
    }

    @Override
    public boolean echange(Jeu jeu) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
