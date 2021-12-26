package Catan;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Plateau {
    Case[][] cases;
    Map<String,LinkedList<Case>> valDe = new HashMap<String,LinkedList<Case>>();
    Case voleur;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

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
        Case[][] c = new Case[n+1][n+1];
        int randomDesertX = new Random().nextInt(n) + 1;
        int randomDesertY = new Random().nextInt(n) + 1;
        Intersection dHG = inter.get("" + randomDesertX + randomDesertY);
        Intersection dHD = inter.get("" + (randomDesertX+1) + randomDesertY);
        Intersection dBG = inter.get("" + randomDesertX + (randomDesertY+1));
        Intersection dBD = inter.get("" + (randomDesertX+1) + (randomDesertY+1));
        Chemin dH = dHG.cheminD;
        Chemin dB = dBG.cheminD;
        Chemin dG = dHG.cheminB;
        Chemin dD = dHD.cheminB;
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
                    Chemin H = HG.cheminD;
                    Chemin B = BG.cheminD;
                    Chemin G = HG.cheminB;
                    Chemin D = HD.cheminB;
                    c[y][x] = new Case(numeros.get(randomNumero), x, y, ressources.get(randomRessource), false, H, B, G, D, HG, HD, BG, BD);
                    numeros.remove(randomNumero);
                    ressources.remove(randomRessource);
                    ajouteValDe(c[y][x]);
                }
            }
        }
        cases = c;
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

    public void LancerDes(Joueur J) {
        int de1 = new Random().nextInt(6)+1;
        int de2 = new Random().nextInt(6)+1;
        int total = de1 + de2;
        String valeur = String.valueOf(total);
        if (total != 7) {
            LinkedList<Case> lol = valDe.get(valeur);
            for (Case c : lol) {
                c.production();
            }
        }
        else {
            deplaceVoleur(J);
        }     
       System.out.println(total);
    }

    private void deplaceVoleur(Joueur j) {
        System.out.println("Veuillez choisir où vous souhaitez déplacer le voleur "+j.pseudo);
        volRessource(j);
    }
    private void volRessource(Joueur j) {
        Map<String,Joueur> cibles = new HashMap<String,Joueur>();
        if (voleur.HG.getColonie() != null) {
            cibles.put(voleur.HG.getColonie().getJoueur().getPseudo(),voleur.HG.getColonie().getJoueur());
        }
        if (voleur.HD.getColonie() != null) {
            cibles.put(voleur.HD.getColonie().getJoueur().getPseudo(),voleur.HD.getColonie().getJoueur());
        }
        if (voleur.BG.getColonie() != null) {
            cibles.put(voleur.BG.getColonie().getJoueur().getPseudo(),voleur.BG.getColonie().getJoueur());
        }
        if (voleur.BD.getColonie() != null) {
            cibles.put(voleur.BD.getColonie().getJoueur().getPseudo(),voleur.BD.getColonie().getJoueur());
        }
        if (!cibles.isEmpty()) {
            if (cibles.size() > 1) {
                System.out.print("Veuillez choisir un joueur à qui voler une ressource aléatoire : ");
                for (String s : cibles.keySet()) {
                        System.out.print(cibles.get(s) + " " );
                }
            }
        }


    }
    public void affiche() {
        for (int y = 1; y < cases.length; y++) {
            System.out.print(cases[y][1].HG);
            for (int x = 1; x < cases.length; x++) {
                System.out.print(cases[y][x].H.toStringH());
                System.out.print(cases[y][x].HD);
            }
            System.out.println();
            System.out.print(cases[y][1].G.toStringV());
            for (int x = 1; x < cases[y].length; x++) {
                cases[y][x].affichageNum();
                System.out.print(cases[y][x].D.toStringV());
            }
            System.out.println();
            System.out.print(cases[y][1].G.toStringV());
            for (int x = 1; x < cases[y].length; x++) {
                cases[y][x].affichageRes();
                System.out.print(cases[y][x].D.toStringV());
            }
            System.out.println();
            System.out.print(cases[y][1].G.toStringV());
            for (int x = 1; x < cases[y].length; x++) {
                cases[y][x].affichagePts();
                System.out.print(cases[y][x].D.toStringV());
            }
            System.out.println();
        }
        System.out.print(cases[cases.length -1][1].BG);
        for (int x = 1; x < cases.length; x++) {
            System.out.print(cases[cases.length - 1][x].B.toStringH());
            System.out.print(cases[cases.length -1][x].BD);
        }
    }
    
}
