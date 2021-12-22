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
        LinkedList<int[]> coordonées = new LinkedList<int[]>();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                int[] a = new int[2];
                a[0] = i;
                a[1] = j;
                coordonées.add(a);
            }
        }
        LinkedList<Ressource> ressources = new LinkedList<Ressource>();
        for (int i = 0; i < n-1; i++) {
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
        for (Integer integer : numeros) {
            System.out.println(integer);
        }
        System.out.println(numeros.size());
    }
    
}