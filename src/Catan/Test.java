package Catan;

import java.util.Random;
import java.awt.Color;

public class Test {

    public static void main(String[] args) {
        Jeu j = new Jeu();
        j.getJoueurs().get(0).addRessource(Ressource.BOIS, 1);
        j.getJoueurs().get(0).addRessource(Ressource.BLE, 2);
        j.getJoueurs().get(0).addRessource(Ressource.LAINE, 2);
        j.getJoueurs().get(0).addRessource(Ressource.ARGILE, 2);
        while (true) {
            j.getJoueurs().get(0).placerColonie(j.getPlateau(), true, false);
            j.getPlateau().affiche();
        }
    }
}