package Catan.Joueurs;

import Catan.Intersection;
import Catan.Jeu;
import Catan.Joueur;
import Catan.Plateau;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import Catan.*;


public class IA extends Joueur{
    Intersection prochaineColonie = null;
    Chemin prochaineRoute = null;
    boolean blocage = false;

    public IA(String pseudo, String color) {
        super(pseudo, color);
    }

    @Override
    public boolean placerColonie(Jeu jeu, boolean premierTour, boolean secondTour, Intersection intersetcion) {
        if (nombreColonies >= 5){
            return false;
        }
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
        prochaineRoute();
        if(premierTour) {
            placerRoute(jeu, true, inter, null, true);
        }
        if (secondTour) {
            freeRessource(jeu,inter);
        }
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
            else {
                System.out.println("Intersection x"+colonie.getX()+" y"+colonie.getY()+" rejetée");
            }
        }
        coloniesPossible = tmp;
        Intersection ValMax = null;
        for (Intersection colonie : coloniesPossible){
            if(ValMax == null || valeurIntersection.get(colonie) > valeurIntersection.get(ValMax)) {
                ValMax = colonie;
            }
        }
        if (ValMax == null) {
            LinkedList<Intersection> randColonie = new LinkedList<Intersection>();
            for(int x = 1;x <= jeu.getPlateau().getLength(); x++) {
                for (int y = 1;y <= jeu.getPlateau().getLength();y++) {
                   if( colonieEstPlaceable(jeu.getPlateau().getIntersection(x, y),true)) {
                      randColonie.add(jeu.getPlateau().getIntersection(x, y));
                   }
                }
            }
            int r = new Random().nextInt(randColonie.size());
            ValMax = randColonie.get(r);
        }
        // if (ValMax == null) {
        //     blocage = true;
        // }
        this.prochaineColonie = ValMax;
    }

    public void prochaineRoute(){
        Set<Intersection> parcourus = new HashSet<Intersection>();
        Intersection plusProche = null;
        for(Intersection colonie : colonies) {
            if (plusProche == null || prochaineColonie.distance(plusProche) > prochaineColonie.distance(colonie)) {
                plusProche = colonie;
            }
        }
        parcourus.add(plusProche);
        Chemin prochaineRoute = null;
        if (prochaineColonie.getY() < plusProche.getY()) {
            if(plusProche.getCheminH().getRoute() == null || plusProche.getCheminH().getRoute() == this) {
               prochaineRoute = plusProche.getCheminH().routeIA(this,prochaineColonie,parcourus);
            }
            else if (prochaineColonie.getX() <= plusProche.getX()) {
                if(plusProche.getCheminG().getRoute() == null || plusProche.getCheminG().getRoute() == this) {
                    prochaineRoute = plusProche.getCheminG().routeIA(this,prochaineColonie,parcourus);
                }
            }
            else if(plusProche.getCheminD().getRoute() == null || plusProche.getCheminD().getRoute() == this) {
                prochaineRoute = plusProche.getCheminD().routeIA(this,prochaineColonie,parcourus);
            }
            else if(plusProche.getCheminB().getRoute() == null || plusProche.getCheminB().getRoute() == this) {
                prochaineRoute = plusProche.getCheminB().routeIA(this,prochaineColonie,parcourus);
            }
        }
        else if (prochaineColonie.getX() <= plusProche.getX()) {
            if(plusProche.getCheminG().getRoute() == null || plusProche.getCheminG().getRoute() == this) {
                prochaineRoute = plusProche.getCheminG().routeIA(this,prochaineColonie,parcourus);
            }
            else if (prochaineColonie.getY() > plusProche.getY()) {
                if(plusProche.getCheminB().getRoute() == null || plusProche.getCheminB().getRoute() == this) {
                    prochaineRoute = plusProche.getCheminB().routeIA(this,prochaineColonie,parcourus);
                }
            }
            else if(plusProche.getCheminH().getRoute() == null || plusProche.getCheminH().getRoute() == this) {
                prochaineRoute = plusProche.getCheminH().routeIA(this,prochaineColonie,parcourus);
            }
            else if(plusProche.getCheminD().getRoute() == null || plusProche.getCheminD().getRoute() == this) {
                prochaineRoute = plusProche.getCheminD().routeIA(this,prochaineColonie,parcourus);
            }
        }
        else if (prochaineColonie.getX() != plusProche.getX()) {
            if(plusProche.getCheminB().getRoute() == null || plusProche.getCheminB().getRoute() == this) {
                prochaineRoute = plusProche.getCheminB().routeIA(this,prochaineColonie,parcourus);
            }
        }
        else if(plusProche.getCheminD().getRoute() == null || plusProche.getCheminD().getRoute() == this) {
            prochaineRoute = plusProche.getCheminD().routeIA(this,prochaineColonie,parcourus);
        }
        if (prochaineRoute != null) {
            this.prochaineRoute= prochaineRoute;
            // prochaineRoute.setRoute(this);
            // System.out.println("Intersection 1 :x"+prochaineRoute.getIntersection1().getX()+"y"+prochaineRoute.getIntersection1().getY()+" x"+prochaineRoute.getIntersection2().getX()+"y"+prochaineRoute.getIntersection2().getY());
        }
        else {
            System.out.println(":(");
            for (Chemin route : routes){
                if (route.getIntersection1().getCheminH() != null ) {
                    if (route.getIntersection1().getCheminH().getRoute() == null) {
                        this.prochaineRoute = route.getIntersection1().getCheminH();
                        return;
                    }
                }
                if (route.getIntersection1().getCheminG() != null ) {
                    if (route.getIntersection1().getCheminG().getRoute() == null) {
                        this.prochaineRoute = route.getIntersection1().getCheminG();
                        return;
                    }
                }
                if (route.getIntersection1().getCheminB() != null ) {
                    if (route.getIntersection1().getCheminB().getRoute() == null) {
                        this.prochaineRoute = route.getIntersection1().getCheminB();
                        return;
                    }
                }
                if (route.getIntersection1().getCheminD() != null ) {
                    if (route.getIntersection1().getCheminD().getRoute() == null) {
                        this.prochaineRoute = route.getIntersection1().getCheminD();
                        return;
                    }
                }
                if (route.getIntersection2().getCheminH() != null ) {
                    if (route.getIntersection2().getCheminH().getRoute() == null) {
                        this.prochaineRoute = route.getIntersection2().getCheminH();
                        return;
                    }
                }
                if (route.getIntersection2().getCheminG() != null ) {
                    if (route.getIntersection2().getCheminG().getRoute() == null) {
                        this.prochaineRoute = route.getIntersection2().getCheminG();
                        return;
                    }
                }
                if (route.getIntersection2().getCheminB() != null ) {
                    if (route.getIntersection2().getCheminB().getRoute() == null) {
                        this.prochaineRoute = route.getIntersection2().getCheminB();
                        return;
                    }
                }
                if (route.getIntersection2().getCheminD() != null ) {
                    if (route.getIntersection2().getCheminD().getRoute() == null) {
                        this.prochaineRoute = route.getIntersection2().getCheminD();
                        return;
                    }
                }
            }
        }
    }
    
    @Override
    public boolean placerRoute(Jeu jeu, boolean gratuit, Intersection premierTour, Chemin cheminn, boolean premierTourB) {
        System.out.println("Colonie prochaine = x"+prochaineColonie.getX() +"y"+prochaineColonie.getY());
        if (!gratuit) {
            removeRessource(Ressource.BOIS);
            removeRessource(Ressource.ARGILE);
        }
        if (premierTour != null) {
            if (prochaineColonie.getY() < premierTour.getY()) {
                if (premierTour.getCheminH()!=null) {
                    premierTour.getCheminH().setRoute(this);
                    routes.add(premierTour.getCheminH());
                    setTailleRoute(jeu);
                }
                else if (premierTour.getCheminG()!=null) {
                    premierTour.getCheminG().setRoute(this);
                    routes.add(premierTour.getCheminG());
                    setTailleRoute(jeu);
                }
                else if (premierTour.getCheminD()!=null) {
                    premierTour.getCheminD().setRoute(this);
                    routes.add(premierTour.getCheminD());
                    setTailleRoute(jeu);
                }
                else if (premierTour.getCheminB()!=null) {
                    premierTour.getCheminB().setRoute(this);
                    routes.add(premierTour.getCheminB());
                    setTailleRoute(jeu);
                }
            }
            else if (prochaineColonie.getY() < premierTour.getY()) {
                if (premierTour.getCheminH()!=null) {
                    premierTour.getCheminH().setRoute(this);
                    routes.add(premierTour.getCheminH());
                    setTailleRoute(jeu);
                }
                else if (premierTour.getCheminG()!=null) {
                    premierTour.getCheminG().setRoute(this);
                    routes.add(premierTour.getCheminG());
                    setTailleRoute(jeu);
                }
                else if (premierTour.getCheminD()!=null) {
                    premierTour.getCheminD().setRoute(this);
                    routes.add(premierTour.getCheminD());
                    setTailleRoute(jeu);
                }
                else if (premierTour.getCheminB()!=null) {
                    premierTour.getCheminB().setRoute(this);
                    routes.add(premierTour.getCheminB());
                    setTailleRoute(jeu);
                }
            }
            else if (prochaineColonie.getY() >= premierTour.getY()) {
                if (premierTour.getCheminD()!=null) {
                    premierTour.getCheminD().setRoute(this);
                    routes.add(premierTour.getCheminD());
                    setTailleRoute(jeu);
                }
                else if (premierTour.getCheminH()!=null) {
                    premierTour.getCheminH().setRoute(this);
                    routes.add(premierTour.getCheminH());
                    setTailleRoute(jeu);
                }
                else if (premierTour.getCheminB()!=null) {
                    premierTour.getCheminB().setRoute(this);
                    routes.add(premierTour.getCheminB());
                    setTailleRoute(jeu);
                }
                else if (premierTour.getCheminG()!=null) {
                    premierTour.getCheminG().setRoute(this);
                    routes.add(premierTour.getCheminG());
                    setTailleRoute(jeu);
                }
            }
            else {
                if (premierTour.getCheminB()!=null) {
                    premierTour.getCheminB().setRoute(this);
                    routes.add(premierTour.getCheminB());
                    setTailleRoute(jeu);
                }
                else if (premierTour.getCheminG()!=null) {
                    premierTour.getCheminG().setRoute(this);
                    routes.add(premierTour.getCheminG());
                    setTailleRoute(jeu);
                }
                else if (premierTour.getCheminD()!=null) {
                    premierTour.getCheminD().setRoute(this);
                    routes.add(premierTour.getCheminD());
                    setTailleRoute(jeu);
                }
                else if (premierTour.getCheminH()!=null) {
                    premierTour.getCheminH().setRoute(this);
                    routes.add(premierTour.getCheminH());
                    setTailleRoute(jeu);
                }
            }
        }
        else {
            prochaineRoute.setRoute(this);
            routes.add(prochaineRoute);
            prochaineRoute();
        }
        jeu.getPlateau().affiche();
        return false;
    }

    @Override
    public void defausseVoleur(Jeu jeu) {
        if(this.ressources.size() > 7) {
            int cartesADefausser = this.ressources.size()/2  ;
            while(cartesADefausser > 0) {
                int r = new Random().nextInt(ressources.size());
                System.out.println(this + "a défaussé "+ressources.get(r));
                ressources.remove(r);
                cartesADefausser--;
            }

        }
    }

    @Override
    public void deplaceVoleur(Jeu jeu) {
        System.out.println(this+ " va choisir où va être placé le voleur ");
        int random1 = new Random().nextInt(jeu.getPlateau().getLength()-1)+1;
        int random2 = new Random().nextInt(jeu.getPlateau().getLength()-1)+1;
        System.out.println("x = " + random1 + " y = " + random2);
        jeu.getPlateau().setVoleur(jeu.getPlateau().getCase(random1,random2));
    }

    @Override
    public void tour(Jeu jeu) {
        System.out.println("Tour de "+ this);
        cartesUtilisables();
        jeu.getPlateau().LancerDes(jeu, this, jeu.getJoueurs());
        if(possede(Ressource.BLE,2) && possede(Ressource.ROCHE,3)) {
            for (Intersection colonie : colonies) {
                if(!(colonie.getColonie() instanceof Ville) ) {
                    placerVille(jeu, colonie);
                    System.out.println(this+"a placé une ville en " + colonie.getX()+"y"+colonie.getY());
                }
            }
        }
        if(!blocage && prochaineColonie == null || !colonieEstPlaceable(prochaineColonie,true)){
            setProchaineColonie(jeu);      
        }
        if (possede(Ressource.BOIS) && possede(Ressource.ARGILE)) {
            if (!blocage && colonieEstPlaceable(prochaineColonie,false)) {
                if(possede(Ressource.LAINE) && possede(Ressource.BLE)){
                        placerColonie(jeu,false, false, prochaineColonie);
                }
            }
            else {
                placerRoute(jeu, false, null, prochaineRoute, false);

                setTailleRoute(jeu);
            }

        }
        System.out.println(this + " taille route" + this.getTailleRoute());
    }

    @Override
    public boolean echange(Jeu jeu) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "(BOT)"+super.toString();
    }

    @Override
    public boolean placerVille(Jeu jeu, Intersection intersection) {
        if (nombreVilles >= 4){
            return false;
        }
        removeRessource(Ressource.ROCHE,3);
        removeRessource(Ressource.BLE,2);
        intersection.setColonie(new Ville(this));
        return true;
    }
    
}
