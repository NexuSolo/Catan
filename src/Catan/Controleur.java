package Catan;

import java.io.IOException;

import Catan.Joueurs.Humain;

public class Controleur {
    private Jeu jeu;
    private Vue vue;

    Controleur(Jeu jeu) throws IOException, InterruptedException {
        this.jeu = jeu;
        this.vue = new Vue(jeu,this);
    }

    void clicToAction(String s,Humain j) throws IOException, InterruptedException{
        j.reponseToAction(jeu, s, this);
    }

    public class echange {
        public int bois = 0;
        public int argile = 0;
        public int laine = 0;
        public int ble = 0;
        public int roche = 0;



    }

    
}
