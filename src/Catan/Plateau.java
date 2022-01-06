package Catan;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import Catan.Cartes.*;
import Catan.Joueurs.Humain;

import java.util.TreeMap;

public class Plateau {
    Case[][] cases;
    Map<String,LinkedList<Case>> valDe = new HashMap<String,LinkedList<Case>>();
    Case voleur;
    Map <Intersection,Integer> valeurIntersection;
    Intersection[] intersectionTri;
    int[] valeurInter;

    private LinkedList<Carte> cartes = new LinkedList<Carte>();{
        for (int i = 0; i < 14; i++) {
            cartes.add(new Chevalier());
        }
        for (int i = 0; i < 5; i++) {
            cartes.add(new PointDeVictoire());
        }
        for (int i = 0; i < 2; i++) {
            cartes.add(new Monopole());
            cartes.add(new ConstructionRoute());
            cartes.add(new Invention());
        }        
    }

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_LIGHT_BLUE = "\u001B[2;34m";

    public Plateau(int n) {
        Map<String,Intersection> inter = new HashMap<String,Intersection>();
        for (int i = 1; i <= n+1; i++) {
            for (int j = 1; j <= n+1; j++) {
                inter.put("" + i + j,new Intersection(i,j));
            }
        }
        LinkedList<Chemin> chemins = new LinkedList<Chemin>();
        for (int i = 1; i <= n+1; i++) {
            for (int j = 1; j <= n+1; j++) {
                if(i < n+1) {
                    chemins.add(new Chemin(inter.get("" + i + j),inter.get("" + (i+1) + j)));
                }
                if(j < n+1) {
                    chemins.add(new Chemin(inter.get("" + i + j),inter.get("" + i + (j+1))));
                }
            }
        }
        LinkedList<Ressource> ressources = new LinkedList<Ressource>();
        for (int i = 0; i < ((n*n)-1)/5; i++) {
            ressources.add(Ressource.BOIS);
            ressources.add(Ressource.BLE);
            ressources.add(Ressource.LAINE);
            ressources.add(Ressource.ARGILE);
            ressources.add(Ressource.ROCHE);
        }
        int reste = ((n*n)-1)%5;
        LinkedList<Ressource> ressourcesReste = new LinkedList<Ressource>();
        ressources.add(Ressource.BOIS);
        ressourcesReste.add(Ressource.BLE);
        ressourcesReste.add(Ressource.LAINE);
        ressourcesReste.add(Ressource.ARGILE);
        ressourcesReste.add(Ressource.ROCHE);
        for (int i = 0; i < reste -1; i++) {
            int random = new Random().nextInt(ressourcesReste.size());
            ressources.add(ressourcesReste.get(random));
            ressourcesReste.remove(random);
        }
        LinkedList<Integer> numeros = new LinkedList<Integer>();
        for (int i = 0; i < ((n*n)-1)/10; i++) {
            numeros.add(2);
            numeros.add(3);
            numeros.add(4);
            numeros.add(5);
            numeros.add(6);
            numeros.add(8);
            numeros.add(9);
            numeros.add(10);
            numeros.add(11);
            numeros.add(12);
        }
        LinkedList<Integer> numerosReste = new LinkedList<Integer>();
        numerosReste.add(2);
        numerosReste.add(3);
        numerosReste.add(4);
        numerosReste.add(5);
        numerosReste.add(6);
        numerosReste.add(8);
        numerosReste.add(9);
        numerosReste.add(10);
        numerosReste.add(11);
        numerosReste.add(12);
        reste = ((n*n)-1)%10;
        for (int i = 0; i < reste; i++) {
            int random = new Random().nextInt(numerosReste.size());
            numeros.add(numerosReste.get(random));
            numerosReste.remove(random);
        }
        creationValDe();
        valeurIntersection = new HashMap<Intersection,Integer>();
        Case[][] c = new Case[n+1][n+1];
        int randomDesertX = new Random().nextInt(n) + 1;
        int randomDesertY = new Random().nextInt(n) + 1;
        Intersection dHG = inter.get("" + randomDesertX + randomDesertY);
        Intersection dHD = inter.get("" + (randomDesertX+1) + randomDesertY);
        Intersection dBG = inter.get("" + randomDesertX + (randomDesertY+1));
        Intersection dBD = inter.get("" + (randomDesertX+1) + (randomDesertY+1));
        valeurIntersection.put(dHG,0);
        valeurIntersection.put(dHD,0);
        valeurIntersection.put(dBG,0);
        valeurIntersection.put(dBD,0);
        Chemin dH = dHG.getCheminD();
        Chemin dB = dBG.getCheminD();
        Chemin dG = dHG.getCheminB();
        Chemin dD = dHD.getCheminB();
        voleur = new Case(0, randomDesertX ,randomDesertY, null, true, dH, dB, dG, dD, dHG, dHD, dBG, dBD);
        c[randomDesertY][randomDesertX] = voleur;
        for (int y = 1; y <= n; y++) {
            for (int x = 1; x <= n; x++) {
                if(c[y][x] == null) {
                    int randomRessource = new Random().nextInt(ressources.size());
                    int randomNumero = new Random().nextInt(numeros.size());
                    Intersection HG = inter.get("" + x + y);
                    Intersection HD = inter.get("" + (x+1) + y);
                    Intersection BG = inter.get("" + x + (y+1));
                    Intersection BD = inter.get("" + (x+1) + (y+1));
                    Chemin H = HG.getCheminD();
                    Chemin B = BG.getCheminD();
                    Chemin G = HG.getCheminB();
                    Chemin D = HD.getCheminB();
                    c[y][x] = new Case(numeros.get(randomNumero), x, y, ressources.get(randomRessource), false, H, B, G, D, HG, HD, BG, BD);
                    numeros.remove(randomNumero);
                    ressources.remove(randomRessource);
                    ajouteValDe(c[y][x]);
                    if(valeurIntersection.containsKey(HG)){
                        valeurIntersection.replace(HG,valeurIntersection.get(HG) +(7 - Math.abs(c[y][x].numero-7) - 1 ));
                    }
                    else {
                        valeurIntersection.put(HG,(7 - Math.abs(c[y][x].numero-7) - 1 ));
                    }
                    if(valeurIntersection.containsKey(HD)){
                        valeurIntersection.replace(HD,valeurIntersection.get(HD) + (7 - Math.abs(c[y][x].numero-7) - 1 ));
                    }
                    else {
                        valeurIntersection.put(HD,(7 - Math.abs(c[y][x].numero-7) - 1 ));
                    }
                    if(valeurIntersection.containsKey(BG)){
                        valeurIntersection.replace(BG,valeurIntersection.get(BG) + (7 - Math.abs(c[y][x].numero-7) - 1 ));
                    }
                    else {
                        valeurIntersection.put(BG,(7 - Math.abs(c[y][x].numero-7) - 1 ));
                    }
                    if(valeurIntersection.containsKey(BD)){
                        valeurIntersection.replace(BD,valeurIntersection.get(BD) + (7 - Math.abs(c[y][x].numero-7) - 1 ));
                    }
                    else {
                        valeurIntersection.put(BD,(7 - Math.abs(c[y][x].numero-7) - 1 ));
                    }
                }
            }
        }
        cases = c;
        ressources.clear();
        ressources.add(Ressource.BOIS);
        ressources.add(Ressource.BLE);
        ressources.add(Ressource.LAINE);
        ressources.add(Ressource.ARGILE);
        ressources.add(Ressource.ROCHE);
        switch (n) {
            case 4 : cases[1][1].getH().ajoutPort(ressources, 0, this, "Haut"); 
                break;
            case 5 :
                for (int i = 0; i < 2; i++) {
                    ressources.add(null); 
                }
                cases[1][1].getH().ajoutPort(ressources, 0, this, "Haut"); 
                break;
            case 6 :  
                for (int i = 0; i < 3; i++) {
                    ressources.add(null); 
                }
                cases[1][1].getH().ajoutPort(ressources, 0, this, "Haut"); 
                break;
            case 7 : 
                for (int i = 0; i < 4; i++) {
                    ressources.add(null); 
                }
                cases[1][1].getH().ajoutPort(ressources, 0, this, "Haut"); 
                break;
            case 8 :
                for (int i = 0; i < 6; i++) {
                    ressources.add(null); 
                }
                cases[1][1].getH().ajoutPort(ressources, 0, this, "Haut"); 
                break;
            }
        setTableauxIntersection(valeurIntersection);
    }

    public void setTableauxIntersection(Map<Intersection,Integer> valeurIntersection){
        Intersection [] triInter = new Intersection[valeurIntersection.size()];
        int [] triInterInt = new int[valeurIntersection.size()];
        int acc = 0;
        for (Intersection inter : valeurIntersection.keySet()){
            triInter[acc] = inter;
            triInterInt[acc] = valeurIntersection.get(inter);
            acc++;
        }
        for (int i = 0; i < triInterInt.length - 1; i++) {
            if (triInterInt[i] > triInterInt[i+1]) {
                int j = i;
                int tmp = triInterInt[i];
                Intersection tmpI = triInter[i];

                triInterInt[i] = triInterInt[i+1];
                triInter[i] = triInter[i+1];
                triInterInt[i+1] = tmp;
                triInter[i+1] = tmpI;
                while (j > 0 && triInterInt[j] < triInterInt[j-1]) {
                    tmp = triInterInt[j];
                    tmpI = triInter[j];

                    triInterInt[j] = triInterInt[j-1];
                    triInter[j] = triInter[j-1];
                    triInterInt[j-1] = tmp;
                    triInter[j-1] = tmpI;
                    j--;
                }
            }
            intersectionTri = triInter;
            valeurInter = triInterInt;
        }
    } 


    public void az(){
        for(String s : valDe.keySet()) {
            System.out.println("VALEUR "+s);
            for(Case c : valDe.get(s)) {
                System.out.println(" X : " + c.getX() + " Y : " + c.getY() + "");
            }
        }
    }
    private void ajouteValDe(Case case1) {
        String s = String.valueOf(case1.getNumero());
        valDe.get(s).add(case1);
    }

    private void creationValDe() {
        LinkedList<Case> deux = new LinkedList<Case>();
        LinkedList<Case> trois = new LinkedList<Case>();
        LinkedList<Case> quatre = new LinkedList<Case>();
        LinkedList<Case> cinq = new LinkedList<Case>();
        LinkedList<Case> six = new LinkedList<Case>();
        LinkedList<Case> huit = new LinkedList<Case>();
        LinkedList<Case> neuf = new LinkedList<Case>();
        LinkedList<Case> dix = new LinkedList<Case>();
        LinkedList<Case> onze = new LinkedList<Case>();
        LinkedList<Case> douze = new LinkedList<Case>();
        valDe.put("2",deux);
        valDe.put("3",trois);
        valDe.put("4",quatre);
        valDe.put("5",cinq);
        valDe.put("6",six);
        valDe.put("8",huit);
        valDe.put("9",neuf);
        valDe.put("10",dix);
        valDe.put("11",onze);
        valDe.put("12",douze);
    }

    public void LancerDes(Jeu jeu, Joueur J,LinkedList<Joueur> listeJoueurs) {
        int de1 = new Random().nextInt(6)+1;
        int de2 = new Random().nextInt(6)+1;
        int total = de1 + de2;
        System.out.println("Le résultat des dés est " + total);
        if(jeu.graphique) {
            jeu.vue.resetTerminal();
            jeu.vue.getTerminal().append("Le résultat des dés est " + total + "\n");
            jeu.vue.getTerminal().repaint();
            jeu.vue.getTerminal().revalidate();
        }
        String valeur = String.valueOf(total);
        if (total != 7) {
            LinkedList<Case> lol = valDe.get(valeur);
            for (Case c : lol) {
                c.production(jeu);
            }
        }
        else {
            for (Joueur joueurs : listeJoueurs) {
                // joueurs.defausseVoleur(jeu);
            }
            deplaceVoleur(jeu, J);
        }     
    }

    public void deplaceVoleur(Jeu jeu, Joueur j) {
        if(jeu.graphique && j instanceof Humain) {
            jeu.vue.setAction(jeu.vue.actionVoleur());
            while(jeu.vue.getSelectionCase() == null || !jeu.vue.getActions()) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("fin");
            j.deplaceVoleur(jeu);
        }
        else {
            System.out.println("Veuillez choisir où vous souhaitez déplacer le voleur " + j);
            j.deplaceVoleur(jeu);
        }
        volRessource(j);
    }

    public void volRessource(Joueur j) {
        LinkedList<Joueur> cibles = new LinkedList<>();
        if (voleur.getHG().getColonie() != null) {
            cibles.add(voleur.getHG().getColonie().getJoueur());
        }
        if (voleur.getHD().getColonie() != null) {
            if (!cibles.contains(voleur.getHD().getColonie().getJoueur())) {
                cibles.add(voleur.getHD().getColonie().getJoueur());
            }
        }
        if (voleur.getBG().getColonie() != null) {
            if (!cibles.contains(voleur.getBG().getColonie().getJoueur())) {
                cibles.add(voleur.getBG().getColonie().getJoueur());
            }
        }
        if (voleur.getBD().getColonie() != null) {
            if (!cibles.contains(voleur.getBD().getColonie().getJoueur())) {
                cibles.add(voleur.getBD().getColonie().getJoueur());
            }
        }
        if (cibles.contains(j)){
            cibles.remove(j);
        }
        Joueur victime = null;
        if (!cibles.isEmpty()) {
            if (cibles.size() > 1) {
                if (j instanceof Humain){
                    do{
                        System.out.print("Veuillez choisir la couleur du joueur à qui vous voulez voler une ressource aléatoire : ");
                        for (Joueur v : cibles) {
                            System.out.print(v + " ");
                        }
                        System.out.println();
                        String scan = Jeu.scan();
                        for (Joueur v : cibles) {
                            if (v.couleur.equals(Joueur.stringToColor(scan.toLowerCase()))) {
                                victime = v;
                            }
                        }
                        
                    } while(victime == null);
                }
                else {
                    int r = new Random().nextInt(cibles.size());
                    victime = cibles.get(r);
                }
            }
            else {
                victime = cibles.get(0);
            }
            j.volRessource(victime);
        }
        else {
            System.out.println("Vous n'avez personne à qui voler (┬┬﹏┬┬))");
        }
    }
    public void affiche() {
        for (int y = 1; y < cases.length; y++) {
            if( y == 1 ) {
                System.out.print(vague(2));
                for(int x = 1; x < cases.length; x++) {
                    if(cases[y][x].getHG().getPort() != null) {
                        System.out.print(cases[y][x].getHG().getPort() + vague(7));
                    }
                    else {
                        System.out.print(vague(8));
                    }
                    if ( x == cases.length - 1 ) {
                        if(cases[y][x].getHD().getPort() != null) {
                            System.out.print(cases[y][x].getHD().getPort());
                        }
                    }
                }
                System.out.println();
                System.out.print(vague(2));
            }
            else{
                if(cases[y][1].getHG().getPort() != null) {
                    System.out.print(cases[y][1].getHG().getPort()+" ");
                }
                else {
                    System.out.print(vague(2));
                }
            }
            System.out.print(cases[y][1].getHG());
            for (int x = 1; x < cases.length; x++) {
                System.out.print(cases[y][x].getH().toStringH());
                System.out.print(cases[y][x].getHD());
                if (x == cases.length - 1 && y != 1) {
                    if(cases[y][x].getHD().getPort() != null) {
                        System.out.print(" "+cases[y][x].getHD().getPort());
                    }
                }
            }
            System.out.println();
            System.out.print(vague(2) +cases[y][1].getG().toStringV());
            for (int x = 1; x < cases[y].length; x++) {
                cases[y][x].affichageNum();
                System.out.print(cases[y][x].getD().toStringV());
            }
            System.out.println();
            System.out.print(vague(2) +cases[y][1].getG().toStringV());
            for (int x = 1; x < cases[y].length; x++) {
                cases[y][x].affichageRes();
                System.out.print(cases[y][x].getD().toStringV());
            }
            System.out.println();
            System.out.print(vague(2) + cases[y][1].getG().toStringV());
            for (int x = 1; x < cases[y].length; x++) {
                cases[y][x].affichagePts();
                System.out.print(cases[y][x].getD().toStringV());
            }
            System.out.println();
        }
        System.out.print(vague(2) + cases[cases.length -1][1].getBG());
        for (int x = 1; x < cases.length; x++) {
            System.out.print(cases[cases.length - 1][x].getB().toStringH());
            System.out.print(cases[cases.length -1][x].getBD());
        }
        System.out.println();
        System.out.print(vague(2));
        for(int x = 1; x < cases.length; x++) {
            if(cases[cases.length -1][x].getBG().getPort() != null) {
                System.out.print(cases[cases.length -1][x].getBG().getPort() + vague(7));
            }
            else {
                System.out.print(vague(8));
            }
            if ( x == cases.length - 1 ) {
                if(cases[cases.length -1][x].getBD().getPort() != null) {
                    System.out.print(" "+cases[cases.length -1][x].getBD().getPort());
                }
            }
        }
        System.out.println();
    }

    public String vague(int i) {
        String s = ANSI_LIGHT_BLUE;
        for(int j = 0; j < i; j++) {
            s += "~";
        }
        return s + ANSI_RESET;
    }

    public Case getCase(int x, int y) {
        if(x >= cases.length || y >= cases.length) {
            return null;
        }
        return cases[y][x];
    }

    public int getLength() {
        return cases.length;
    }

    public void setVoleur(Case c) {
        voleur.setVoleur(false);
        c.setVoleur(true);
        this.voleur = c;
    }

    public LinkedList<Carte> getCartes() {
        return cartes;
    }
    public void afficheCartes(){
        for(Carte c : cartes) {
            System.out.println(c);
        }
    }
    public Intersection getIntersection(int x,int y) {
        if( x <= 0 || x > cases.length || y <= 0 || y > cases.length) {
            System.out.println("Erreur coordonnées de l'intersection");
            return null;
        }
        if ( x == cases.length ) {
            if ( y < cases.length) {
                return getCase(x-1,y).getHD();
            }
            else {
                return getCase(x-1,y-1).getBD();
            }
        }
        if ( y == cases.length) {
            if ( x < cases.length) {
                return getCase(x,y-1).getBG();
            }
            else {
                return getCase(x-1,y-1).getBD();
            }
        }
        return getCase(x,y).getHG();
    }
    
    public Intersection[] getIntersectionTri() {
        return intersectionTri;
    }

    public int[] getValeurInter() {
        return valeurInter;
    }

    public Map<Intersection, Integer> getValeurIntersection() {
        return valeurIntersection;
    }

    
}
