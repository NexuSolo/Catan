package Catan;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import Catan.Joueurs.Humain;
import Catan.Joueurs.IA;

public class Jeu {
    LinkedList<Joueur> joueurs = new LinkedList<Joueur>();
    Joueur chevalierLePlusPuissant = null;
    Joueur RouteLaPlusLongue = null;
    Plateau plateau;

    public Jeu() {
        String reponse; 
        while (true) {
            System.out.println("Voullez vous une interface graphique ? [Oui][Non]");
            reponse = MotToMotMinuscule(scan());
            if(reponse.equals("oui")) {
                break;
            }
            else if(reponse.equals("non")) {
                break;
            }
        }
        while (true) {
            System.out.println("Combien voullez vous de joueur ? [3][4]");
            reponse = MotToMotMinuscule(scan());
            if(reponse.equals("3")) {
                break;
            }
            else if(reponse.equals("4")) {
                break;
            }
        }
        int nbrJoueur = Integer.valueOf(reponse);
        LinkedList<String> couleurDisponible = new LinkedList<String>();
        couleurDisponible.add("bleue");
        couleurDisponible.add("rouge");
        couleurDisponible.add("jaune");
        couleurDisponible.add("vert");
        for (int i = 1; i <= nbrJoueur; i++) {
            boolean IA = true;
            while (IA) {
                if(i == 1) {
                    IA = false;
                    break;
                }
                System.out.println("Le joueur " + i + " est il une IA ? [Oui][Non]");
                reponse = MotToMotMinuscule(scan());
                if(reponse.equals("oui")) {
                    break;
                }
                else if(reponse.equals("non")) {
                    IA = false;
                    break;
                }
            }
            String pseudo;
            if(!IA) {
                System.out.println("Quelle est le pseudo du joueur " + i + " ?");
                pseudo = scan();
            }
            else {
                pseudo = "Bot " + i;
            }
            while (true) {
                System.out.print("Quelle est la couleur du joueur " + i + " ? ");
                for (String string : couleurDisponible) {
                    System.out.print("[" + Character.toUpperCase(string.charAt(0)));
                    System.out.print(string.substring(1, string.length()) + "]");
                }
                System.out.println();
                reponse = MotToMotMinuscule(scan());
                if(couleurDisponible.contains(reponse)) {
                    couleurDisponible.remove(reponse);
                    if(reponse.equals("bleue")) {
    
                        break;
                    }
                    if(reponse.equals("rouge")) {
    
                        break;
                    }
                    if(reponse.equals("jaune")) {
    
                        break;
                    }
                    if(reponse.equals("vert")) {
    
                        break;
                    }
                }
            }
            System.out.println("pseudo = " + pseudo);
            System.out.println("couleur = " + reponse);
            if(IA) {
                joueurs.add(new IA(pseudo, reponse));
            }
            else {
                joueurs.add(new Humain(pseudo, reponse));
            }
        }
        int taille = 0;
        while (true) {
            if(joueurs.size() == 3) {
                System.out.println("Quelle taille de plateau souhaitez-vous ? [5][6][7][8][9][10]");
            }
            else {
                System.out.println("Quelle taille de plateau souhaitez-vous ? [6][7][8][9][10][11]");
            }
            reponse = scan();
            if(estNombre(reponse) && Integer.valueOf(reponse) >= 5 && Integer.valueOf(reponse) <= 11) {
                if(reponse.equals("5") && joueurs.size() == 3) {
                    taille = 5;
                    break;
                }
                if(reponse.equals("11") && joueurs.size() == 4) {
                    taille = 11;
                    break;
                }
                if(!reponse.equals("5") && !reponse.equals("11")) {
                    taille = Integer.valueOf(reponse);
                    break;
                }
            }
        }
        // while (true) {
        //     plateau = new Plateau(taille);
        // }
    }

    public void initialisationPlateau(int n) {
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
        Case[][] c = new Case[n+1][n+1];
        int randomDesertX = new Random().nextInt(n) + 1;
        int randomDesertY = new Random().nextInt(n) + 1;
        System.out.println(randomDesertX +" " + randomDesertY);
        Intersection dHG = inter.get("" + randomDesertX + randomDesertY);
        Intersection dHD = inter.get("" + (randomDesertX+1) + randomDesertY);
        Intersection dBG = inter.get("" + randomDesertX + (randomDesertY+1));
        Intersection dBD = inter.get("" + (randomDesertX+1) + (randomDesertY+1));
        Chemin dH = dHG.cheminD;
        Chemin dB = dBG.cheminD;
        Chemin dG = dHG.cheminB;
        Chemin dD = dHD.cheminB;
        c[randomDesertY][randomDesertX] = new Case(0, randomDesertX ,randomDesertY, null, true, dH, dB, dG, dD, dHG, dHD, dBG, dBD);
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
                }
            }
        }
        plateau = new Plateau(c);
    }

    public static boolean estNombre(String s) {
        try {
            Integer.valueOf(s);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String scan() {
        Scanner sc = new Scanner(System.in);
        return sc.next();
    }

    public static String MotToMotMinuscule(String s) {
        return s.toLowerCase();
    }

    public static void main(String[] args) {
        new Jeu();
    }
    
}