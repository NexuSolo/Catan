package Catan;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class Jeu {
    LinkedList<Joueur> joueurs;
    Joueur chevalierLePlusPuissant;
    Joueur RouteLaPlusLongue;
    Plateau plateau;

    public void initialisation(int n) {
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
    
}